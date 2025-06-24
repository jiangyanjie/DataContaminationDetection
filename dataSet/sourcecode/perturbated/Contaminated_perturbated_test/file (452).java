package restaurant.restaurant_davidmca.roles;

import      java.util.ArrayList;
import java.util.Collections;
import java.util.List;
i      mport java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import restaurant.restaurant_davidmca.Check;
import restaurant.restaurant_davidmca.Menu;
import restaurant.restaurant_davidmca.Ta   ble;
import restaurant.restaurant_davidmca.gui.CustomerGui;
import restaurant.restaurant_davidmca.interfaces.C ashier;
import restaurant.restaurant_davidmca.interfaces.Customer;
import restaurant.restaurant_davidmca.interfaces.H ost;
import restaurant.restaurant_davidmca.interfaces.Waiter;
import base.BaseRole;
import base.ContactList;
import base.Locat   ion;
import base.Perso       nAgent;
import base.interfaces.Person;
i    mport city  .gui.trace.   AlertTag;

/**
 * Restaurant customer restaurant_davidmca.agent.
 */
public class   DavidCustomerRole   e    xte  nds BaseRole implements Customer {
	pr  ivate String na    me;
	private int hungerL evel = 4000;      // determines length of meal
	privat   e double mymoney =   0;
	private dou   ble debt = 0;
	private Check myc    heck = n    ull;
	Timer timer = new     Timer();
	private Custo  merGui customerGui;

	// restaurant_davidmca.agent correspondent s
	priv ate Wait   er  waiter;     
	private Host host;
	private Table tab       le;
	private Menu menu;
	private Cashier cash   ;

	private String myc      hoice      ;
	private       boolean availability;

	// privat  e boolean isHungry = false; //hack for gui
	public e num AgentSta  te {
		DoingN  othing,       Waiting  InRestaurant, BeingSeated  , Seated, Choo  singFood, Eating, Pa ying, L  eaving
	};

   	private AgentState state = Ag   entState.DoingNothing;//    The st      art sta te 

	p     ublic enum AgentEvent {
		none, gotHungry, arrived, followWaiter, seated, pickFood, Waiting, s   tartEating, doneEating, requestCheck, AskedFor    Check, Payi  ng, doneP   ay  ing,        R    eadyToLeave,     doneLeaving
	   };

	AgentEvent event = Ag    entE  vent.    none     ;

	/**
	 * Constructor f   or      CustomerAgent class
	 * 
	 * @   param name
	   *            name       of the customer
	 */
	public DavidCustomerRole(Person person) {
		super(person);
		this.  name = person.getNam  e();
		this.availability = true;
	}

	@Override
	public void setHost(Host host) {
		this.host = ho  st;
	}

	@Override
	public double getMoney() {
		     ret  urn this.mymoney;
	}

	@Overrid      e  
	public Stri   ng getCustomerName() {
		return name;
	}

	// Messages

	public void msgAvailability(boolean      availability) { 
	  	this.availability = av  ailability;
		s    tateChanged();
	}

	@Override
	public    v  oid msgChange(Double ch   ange) {
		mym    oney += change;
		event = AgentEvent.ReadyToLeave;
	  	print(        "Got change, my balance is     : " + mymone   y);
	  	   if (mymoney < 0) {   
	 		debt = mymoney;
			print("Had  s       ome debt, will pay next time");
		}
		stateChanged();
	}

	@Override
	public void msgHereIsCheck(Ch eck chk) {
		print(" Received check    ");
		myc  heck = chk;
		event = AgentEv    ent.Paying;
		stateChange  d();
	}

	@Override
	public void gotHu           ngry() { // from anima    tion
		print("I'm hungry");
		event = AgentEvent.gotHungry;
		stateChanged()    ;
	}

	@Overr   ide
	public void msgAni   mationFinishedGoToSeat() {
   		// from animation
		event = AgentEvent.seate  d;
		stateChanged();
	  }

	@Override
	public void msgAnim   ationFinishedLeaveRestaurant() {
		// from     animation
		event = AgentEvent.doneLea ving;
		    ((PersonAgent) mPerson).msgRoleFinished();
		stateChanged(     );
	}

	@Override
	public void msgAnimationFinishedGoToWaitingArea() {
		ev  ent    = AgentEvent.arrived;
		stateChanged();
	}

	@Override
	     public void msgFollowMe(Waiter w, Table t      )    {
		waiter = w;
		table = t;
		event = AgentEvent.followWaiter;
		stateChanged()  ;   
	}

	@Override
	public     void msgWhatWouldYouLi      k  e(Menu m) {
		menu    =     m;
		event = Ag     entEvent.pickFood;
		stateChanged     (     );
	}

	@Overr    ide
	public void msgHereIsYourOrder() {
		event = AgentEvent.startEating;
	   	stateChanged();
	}
     
	/**
	 * Scheduler. Determine what action is called for, and do it.
	    */
	public boolean   pickAn  dExecuteAnAction() {
		// CustomerAgent is a finite state machine

		if (state     == AgentState.DoingNot hing && event == AgentE  vent.gotHung     ry) {
			state = AgentState.WaitingInRestaurant ;
			CheckAv  ailability      ();
	  		goToRestaura   nt();
			return tr      ue;
		}
		if (state == AgentState.WaitingInRestaurant      
				&& event == Ag    entEvent.followWaiter) {
			state = Agen t   State.BeingSeated;
      			SitDown();
			return true;
		}
		if   (state == AgentState.BeingS   eated && event == Agen  tEvent.seated)   {
			state = AgentState.Sea       ted;
	 		cal lWaiter();
			return true;
		}
	   	if (state == AgentS   tate.Seated && event == AgentEve  nt.pickFood) {
			state = AgentSta     te.Seated;
			chooseFood();
			return tru    e;
		    }

	      	if (state == AgentState.Seated && event == AgentEvent.startEating) {
			state = AgentState.Eating;
			EatFood();
			ret urn true;
		}

		if (state == AgentSt ate.Eating && eve   nt == AgentEvent.doneEating)       {
			state = AgentState.Paying;
			Reques   tC  heck();
			r   eturn tr   ue ;
		}

		if (state == AgentState.Paying && event =  =  AgentEvent.Paying) {
		     	DoneAndPaying   ();
			return true;
		}

		 if (state == AgentState.Paying && event == AgentEvent.ReadyToLeave) {
			state     = AgentState.Leaving;
			leaveT    able();
			return   true;
		}

		if (state =   = AgentState.Sea    ted && e  vent == AgentEv   ent. ReadyToLeave) {
			// customer leaving  becau se they couldn't afford anything
			state = AgentState.L     eaving;
			leaveTable();
			return t  rue;
  		}

		if (state ==   Agen    tState.Leaving &  & event == AgentEvent.doneLeaving)   {
			state = AgentState.DoingNothing;
			// no act   ion
			return true;
		}
		return fa  lse;
	}

	// Actions

	private void CheckAvailability() {
		host.msgCheckAvailability(this);
	}

	private void goToRestaurant(     ) {
		customerGui.DoGoToWaitingArea();
		if   (this.getName().startsWith("hack"))     {
	  		String moneyhack = this.getName().substring(4,
					this   .getName().length());
			this.mymoney    += Double.parse    Double(mo    neyh   ack);
			print("Added $" + moneyhack + " t o wallet");
		} else          {
      			this.mymoney +=   40;
			print("Added $40 to wallet");
		}
		if (debt < 0) {
			print("Paying debt");
			cash.msgDebtPayment(debt);
		    	my     money += debt;
		}
		if (availabi  lity) {
			host.msgIWantFood(this);
			  Do("Going to restau  r   ant_davidmca");
		} el    se if (!availability) {
			Random rand = new Random();
			int randvalue    = rand.nextInt(1000);
			@SuppressWa      rnings("  unused")
			int  stay = randvalue % 2;    
//			switch (stay) {
//			ca se 0:
				Do("Resta    urant full, decided to go anyway");
				host.msgIWantFood(this);  
				Do("Going     t   o restaurant_davidm   ca");
//				break;
//			case 1:
//				Do("Restaurant full, decided to leave");
//			 	event = Ag      entEvent.ReadyToLeave;
//				customerGui.DoExitRestaurant();
//				((Per   sonAgent) mPerson).m   sgRoleFinished(this);
//	     			brea   k;
//			}
		}
	}

	private void   SitDown() {
		Do("Being seated. Going to table");
		customerGui.DoGoToSeat(table.getX(), table.getY());
	}

	p      rivate void callWaiter() {
		pri nt(     "Ready to order");
		w     aiter.msgReadyToOrder(this);
	}

	private void   chooseFood()     {        
		List<String> canAfford = Collectio       ns
	  			.synchronizedList(new ArrayList<String>());
		List<String> entrees = Collecti     ons
				.synchronizedList(new ArrayList<String>());
		entrees =    menu.getItemList();
		synchronized (entrees) {
			for (String entree : entrees) {
				i   f     (menu.getPrice(entree) <= mymoney) {
					canAfford.add(entree);
			  	}  
			}
		}
		if (canAfford.size() > 0) {
			R   andom generator = new Random();
			int choice = gen    erator.nextInt(canAfford.size());
			mychoice     = canAfford.get(choice);
			customerGui.setL   abelText(mychoice + "?");
			waiter.msgHer  eIsMyOrder(th   is, m        ychoice);  
			event = AgentEvent.Waiting;
		} else {
			pri     nt("Cannot   afford any ite   ms");
			i   nt decide   ;
			if (men   u       .isReOrder() == true) {
				decide = 0;
			} else {
				Rand   om ran  d2 = new Random();
	    			int randvalue = rand2.nextInt(1000);
				decide = randvalue % 2;
	   		}
			switch (decide) {
	    		case 0    :
				print("Decided to leave");
				event = AgentEvent.R   eadyToLeave;
				break;
			case 1:
				print("Order Anyway");
				mychoi   ce = menu.pickItem();
				customerGui.setLabelText(mych    oice + "?");
				waiter.msgHer   e         IsMyOrder(this, myc  hoice);
				event = AgentEvent.Waiting;
				break;
			}
		}
	}  

	private void EatFood() {
		Do ("Eating Food: " +   mychoice) ;
		customerGui.s   etLabelText(mychoic      e);
		timer.schedule(new TimerTask() {
			Object cookie = 1;

			public void run() {
				print("Done eating, cookie=" + cookie);
   				event = AgentEve nt.doneEating;
				// isHungry = false;
				stateChanged();
			}
		}, getHungerLev    el());// how long to wait before running task
	}

	private void RequestCheck() {
		print("Requested Check");
		waite   r.ms gReadyForCheck(this, mychoice   );
		event = AgentEve           n t.AskedForCheck;
		stateChanged();
	}

	private void D oneAndPaying() {
		print("Done and Paying");
//		ContactList.SendPayment(mPerson.getSSN(), ((BaseRole) cash).getSSN    (), mycheck.total);
		cash.msgPaymen   t(mycheck, mymoney) ;
		mymoney = 0;
		waiter.msgDoneAndPaying(this);
		event = AgentEvent.donePaying;
	}

	private void leaveTabl   e() {
		c ustomerGui.setLa    belText(""  );
		Do("Leaving .");
		waiter.msgDoneEatin    g(this);
		customerGui.DoExitRestaurant();
		
		mPerson.msgRole Finished();      
		mPerson.assignNextEvent();
	}

	// Accessors , etc.

	@Overr ide
	public String                getName() {
		return name;
	}

	@Override
	public int getHungerLevel() {
		return hungerLevel;
	}

	@Override
	public vo   id setHungerL      evel(int  hungerLevel) {
		this.hungerLevel = hungerLevel;
	}

	// @Overri  de
	// public String toString() {
	// return "customer " + getName();
	// }

	@Override
	  public void setGui(C   ustomerGui g) {
		customerGui = g;
	}

	@Override
	public CustomerGui getGui() {
		return customerGui;
	}

	@Override
	public void setCashier(Cashier ca) {
		this.cash = ca;
	}
	
	@Override
	public Location getLocation() {
		return ContactList.cRESTAURANT_L OCATIONS.get(4);
	}

	public void Do(String msg) {
		super.Do(msg, AlertTag.R4);
	}
	
	public void print(String msg) {
		super.print(msg, AlertTag.R4);
	}
	
	public void print(String msg, Throwable e) {
		super.print(msg, AlertTag.R4, e);
	}
}
