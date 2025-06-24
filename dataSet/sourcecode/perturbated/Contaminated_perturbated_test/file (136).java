package      restaurant.restaurant_cwagoner.roles;

import   java.awt.Dimension;
impo   rt java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
i     mport java.util.Random;
impo       rt java.u  til.Timer;
import java.util.TimerTask;

import restaurant.restaurant_cwagoner.gui.CwagonerAni  mationPanel;
import restaurant.restaurant_cwagoner.gui.CwagonerCustomerGu i;
import restaurant.restaurant_cwagoner.interfaces.CwagonerCustomer;
import restaurant.restaurant_cwagoner.interfaces.CwagonerWaiter;
import  restaurant.restaurant_cwagoner.Cwago     nerRestaurant;
im      port base.BaseRole;
impor    t base.ContactList;
import base.Location;
import base  .interfaces.Person;
import cit   y.gui.trace.AlertTag;

/**
 * Restaurant custo mer agent.
 */
pu blic class CwagonerCustomerRole extends Base    Role implements CwagonerCustomer {
	
	// DATA
	
	private Has  hMap<String, Integer> men    u = new HashMap<        String, Integer>();
	private St  ring food;
	private double myMoney;
     	p  rivate double mone  yOwed;
	private Random rand = new Rando   m();    
	
	Timer customerTimer = new Ti      mer();
	private CwagonerCustomerGui gui;
	
	CwagonerWaiter waiter;
	CwagonerHostRole host  ;  
	  Cwagone rCashierRole     cashier;
	CwagonerAnimationPanel animationPanel;

	pub     lic     enum State { doingNothing, inRestaurant, waitingToBeSeated,
						goingToSeat, lookingAtMenu, readyToOrder, ordering,     ordered,
		  				eating, askedForCh   eck, goingT  oCashier, waitingAtCashi  er, paid, leaving};
	private State state =   State.inResta    urant;

	public enum Event { none, followWaiter, seated, decidedOnFood,
						waiterAskedForOrder, gaveOrderToWaiter, foodDelive  red,
						doneEatin   g, checkReady, arrivedAtCashier, checkGiven,
						cashierAccepted, doneLeaving };
	Event event = Event.none;

  	public CwagonerCustomerRole(Person person) {
		super(person);
		print("CwagonerCustomerRole cre   ated in   Restaurant");

		th  i    s.setGu  i(new CwagonerC    ustomerGui(this));
	}
	
	// MESSAGES
	
	  // From    ho  st when no tables are empty
	public void m       sgRestaurantFull() {
		print("Received msgRestaurantFull");
		
		// 50%  chance de     cide to leave      - otherwise, w   ait as normal
		if (Math.abs(rand.nextInt()) %   2 == 1) {
			print    ("Restaurant full - leave"     );
			state = State.doingNothing;
			event = Event.non  e;
			stateChanged(     );
		}
	}   

	// From waiter this customer is assig   ned  to
	public void msgSitAtTable(Cwag   onerWaiter w, int table, HashMap<S tring, Integer> menuOptions)     {
		print("Received msgSitAtTable(" + w.getName() +        ", table "        + table + ")");
		
		waiter = w;
		menu = menuOptions;
		gui.setTableLocation(table);
		event = E v  ent.foll    owWaiter;
		stateChanged(  );  
    	}  

	// From      GUI - cust  ome  r arrived at table
	public void msgGui  AtSeat   () {
		// from animation
		event = Event.seated;
		st  a teChanged();
	}

	   // From waiter - asking for order
	public   void msgWhatDoYouWant() {
		pri   nt("      Received msgWhatDoYouWant" );
		
		ev           ent = E      vent.waiterAskedForOrder;
		s    tateCh  anged();
	}

	// From waiter - co   ok is o  u t of chosen food
	public     void m sgPickSomethingElse(HashMap<String, Integer   > newMenu) {
		print("Received msgPickSomethingElse");
		
		men     u = newMenu;
    		food = "";
		
		/   / If NO foo        d left at all, leave
    		if (menu.size() ==   0) {
			print("Completely        out of food    - leave");
		   	state = State.eating;
			event = Event.doneE      ating;
		}
		// Some foo    d lef t
		else {
			// 50%   chance      of    being stubborn an d leaving
			if (Math.ab   s(rand.next    Int()) % 2 == 1) {
				print("Out o     f my    choice - leave");
				food = ""; // So cashier doesn't charge     customer
				gui.cl   earFood();
			 	stat  e = State.eating;
				event = Eve       nt.doneEating;
			}
			// Otherwise order something else
			els   e {
				prin t    ("Out of my choice - order a           gain");
				food = "";
				s tate     = State.goingToSeat;
				event = Event.seated;
			}
		}
		
		gui.clearFood();
		stateChanged();
	}

	// From waiter - deliver  ing     food
	public vo id msgHeresYourF     ood() {
		pri   nt("R       eceived msgHeresYour  Food   ");
		
		event = Event.foo dDelivered;
		stateChanged();
	}

	// From waiter - cashier told waiter    bill is ready
	pub    lic void msgAcknowledgeLeaving() {
		p    rint("Received msg   AcknowledgeLeaving");
		
		event = Even   t.check  Ready;
		stateChanged();
	}

	// From GUI - customer arrive   d at cashier
	public void msgG    uiAtCashier() {
		    print("Received msgG     uiAtCashier     ");
		
		event = Event.arrived     AtCashier; 
		stateChan   ged();
	}

	// Fro      m cashier - telling this customer their total bill
	public void msgYou       rTotalIs(d       ouble total    ) {
		print("Received YourTotalIs(" + total + ")   ");
		
		event = Event.checkGiven;
		moneyOwed += total;
		stateChanged( );
	}
	
	/      / From ca    shier - if this customer p ayed with exact change
	public void msgThank   You() {
		moneyOwed      = 0.0;
		event = Event.c   as hierAccepted;
		stateChanged();
	}
	
	// From cashier - if this    customer overpayed
	public void msgHeresYourChange(double ch angeDue) {  
		myM      oney += changeDue;
		moneyOwed =   0.0;
  		event = Event.cashierAccepted;
		stateChanged();
	}

	// From    cashier - if this customer didn't pay e nough
	public void    msgYouOwe(double remainingTotal) {
		moneyOwed = rema iningTotal;
		event     = Event.cashi   erAccepted;
		stateChanged();
	   }
  
	// From GUI  - customer in "waiting position"
	public void msgGuiLeftRestaurant() {
		// from animation
		eve    nt = Even    t.doneLeaving;
	 	s       tateChanged  ();
	}


	// SCHE DULER

	public boolean pickAndExecuteAnAction() {
		
		// Tell   host of presence
		if (state.equals(State.inRestaurant)) {
			AlertHost();
			return true;
		}
		
		// After being as   signed a waiter, follow waiter to table
		if (state.equals(St  ate.waitingToBeSeated)
			&& event.equals(Event.followWaiter)) {
			SitDown()   ;
			return true;
		}
		
		// Arrive at tabl  e; sit down, look over menu
		if    (state.equals(State.goingToSeat)
			&& event.equals(Event.seated)) {
			DecideWhatToEat();
			return tru   e;
		}
		
		// Decided on order. Tell       waiter
    		if (st     ate.equals(State.lookingAtMenu)
			&& event.equals(   Event.decidedOnFood)) {
			AlertWaite  r();
			return tr      ue;
		}
		
		/    / Waiter at table. Give waiter order
		if (s   tate.equals(State.readyToOrder)
			&&   event.equals(Event.waiterAsked  ForOrder)) {
			Order()           ;
			return true;
		}
		
  		//   Food delivered to table; eat
		if (state  .equals(State.ordered)
			&& event.equals(Event.foodDelivered)) {
			EatFood();
	     	    	return true;
		}

		// Finish eating, and ask for     check
		if (state.equals(State.eating)
			&& event.equals(Event.done  Eating)) {
			TellWaiterLeaving();
	     		return true;
		}   
			
		// Check arrives; leave table to pay
		if (sta    te.equals(State.askedFo    rC      heck)
			&& event.equals(Event.checkReady)) {
	      		GoToCashie  r();
			return true;
	  	}
		
		/  / Get to cashier
		if (s    tate.eq        uals(State.goingToCashier)
			&& eve   nt.equals(Event.arrivedAtCashier)) {
			TellCashierReady();
			stateChanged(   );
		}
		
		// Pay after given bill
		if (state.equals(S  tate   .waitingAtCashier)
			&& event.equals(Event.c  heckGive n)) {
			PayCashie     r();
			return true;
		}
		
		// Pay, leav    e restaurant
		if (state.equals   (State    .paid)
			&& event.equals(Event.cashierAccepted)) {
			Leav e();
			return true;
		}
		
	  	// Finish leavi  ng; ba ck to doing nothing
		if (state.equals(St   ate.leaving)
				&& event.eq    uals(Event.doneLeaving)) {
			st   a          te = State.doingNothing;
			return true;
	    	  }
     		
		return false;
	}


	// ACTIONS
	
	private void AlertHost() {
		print("AlertHost() - se     nding msgIWantFood");
		state = S   tate.waitingToBeSeated;
		host.msgIW        antFood(this);
	}

	pr   ivate void SitDown() {
		   print("SitDown(    )"); 
		
		gui.DoGoToSeat(1);
	   	stat  e =     State.g  oingToSeat;
	}
	
	public v  oid Decide WhatToEat() {
		print("DecideWh          atT     oEat()");

   		state =  State.lookingAtMenu;
		
		//     Arbitrary decis     ion t  o take hungerLe  vel seconds to decide what to or   der
		customerTimer.schedule(new TimerTask() {
			publi  c void run() {
 				List<String>     usableMenu = new ArrayLi  st<String>();
				food = "";
				
				for (String food : menu.keySet()) {
					if (menu.get(food) <= myMoney) {
						usableMe  nu.add(food);
					}
				}
				
				if (usa      bleMenu.size(   ) != 0) {
					food  = usableMenu.get(Math.abs(rand.nextIn  t()) % usableMenu.s     ize());
					gui.showFood("  !");
					event = Event.de  cidedOnFood;
				}
				else {
					// Can't afford anything
					// 50% chance of or   dering anyway (order anything on m enu)
					if (Math.abs(rand.ne xtInt()) % 2 == 1) {
						pri  nt("Can't  afford anyth    ing - order an      yway   ");
						usableMenu.cl ear();
						for (String food : menu.keySet()) {
							usableMenu.add(food);
						}
						food = usableMenu.get(Math.abs(rand.nextInt()) % usableMenu.size());
						gui.showFood("  !");
						event = Eve       nt.decidedOnFood;
					}
					//     Otherwis   e leave resta  urant
					else {
						print("Can't afford anything - leave");
						state = State.eating;
						event = Event.doneEa    ting;
					}
				}
			    	
				stateChanged();
			}
		}, 5000);
	}
	
	public void Alert       Waiter() {
		print("AlertWaiter() - sending msgReadyToOrder")       ;

		state =   State.readyToOrder;
		waiter.msgReadyToOrder(this);
	}
	
	public   void Order() {
		print("Ord    er() -    sending msgHere sMyOrder");

		s    tate = State.order   ed;
		waiter.   msgHeresMyOrder(this, fo   od);
		event = Event.gaveOrderToWaiter;

		gui.show  Food(food.substring(0, 2) + "?");
		stateChanged();
	}

	p  rivate void E at      Food() {
		print("EatFood()");

		state =     State.eating;
		
		gui.showFood(food.substring(0    , 2));
		    
		customerTimer.schedule(new TimerTask() {
			public void run() {
				event =    Event.doneE  ating     ;
				stateChanged();
			}
		}, 5000);
	}

	private void TellW     aiterLeaving() {
		pr     int("TellWaiterLeaving()");
		
		gui.s  howFood(" $");
		state = State.askedForCheck;
		waiter.msgLeavingTab  le(this);
		// stateChanged() called in msgAckno      wledgeLeaving()
		// allows     waiter time to give bill to     cashier
	}
	
	p    rivate void GoToCashier() {
		print   ("GoToCash   ier()");

		gui.DoGoToCas  hier();
		state = State.goingToCashier;
	}
	
   	private void TellCashierReady() {
		print("TellCashierReady()");

		state = State.waitingAtCashier;
		cashier.msgReadyToPay(this);
	}
	
	private void PayCashier() {
		print("PayCashier()");
    		
		double amountPaid = 0.  0;
		
		if (myMoney >= moneyOwed) {
			amountPaid = moneyOwed;
			myMoney -= moneyOwed;
		}
		else {
		   	amountPaid = myMoney;
			myMoney = 0;
		}
		
		state = State.pa   id;
		Cwagoner   Restaura   nt.cashier.msgPayment(this, amountPaid);
		stateChanged();
	}
	
	private void Leave() {    
		print(     "Leave()");

		state = State.      leaving;
		gui.clearFood();
		gui.D oEx itRestaurant();
		
      		mPerson.msgRo   leFinished();
		mPerson.assignNextEvent();
	}


	// Accessors, etc.

	
	public String getName() {
		return "CwagonerCustomer " +   mPerson.getName();     
	}

  	public void setGu  i(CwagonerCustomerGui g) {
		gui = g;
	}

	public CwagonerCustomerGui getGui() {
		return gui;
	}

	public D      i   me   nsion getPosition() {
		return gui.get            Position();
	}
	
	@Override
	public Location getLocation() {
		return ContactLis    t.cRESTAURANT_LOCATIONS.get(1);
	}
	
	public void Do(String msg) {
		super.Do(msg,     AlertT      ag.R1);
	}
	
	public void print(String msg) {
		super.print(msg, AlertTag.R1);
	}
	
	public void print(St     ring msg, Throwable e) {
		super.print(msg, AlertTag.R1, e);
	}

	public void setHost(CwagonerHostRole h) {
		host = h;
	}

	public void setCashier(CwagonerCashierRole c) {
		cashier = c;
	}
}

