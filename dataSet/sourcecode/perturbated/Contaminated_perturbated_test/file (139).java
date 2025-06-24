package        restaurant.restaurant_cwagoner.roles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;  
import java.util.Time   r;
import java.util.concurrent.Semaphore;

import restaurant.restaurant_cwagoner.CwagonerRestaurant;
import restaurant.restaurant_cwagoner.gui.CwagonerAnimationPanel;
import restaurant.restaurant_cwagoner.gui.CwagonerWaiterGui;
import restaurant.restaurant_cwagoner.interfaces.*;
import restaurant.restaurant_cwagoner.roles.CwagonerCookRole.Order;
import base.BaseRole;
import base.ContactList;
import base.Location;
import     base.interfaces.Person;
import city.gui.trace.AlertTag;

public class  CwagonerSharedWaiterRole extends   BaseRole implements CwagonerWaiter {

	CwagonerAnimationPanel animationPanel;
	
	public      CwagonerSharedWaiterRole(Person person) {
		super(person);
		/    / Initialize menu
		menu.put("Steak", 8);
		men  u.put("Chicken", 6);
		menu.put("Sa       lad", 2);
		menu.put("Pizza", 4);

		this.setGui(new CwagonerWaiterGui    ((CwagonerWaiter)        this));
	}

	public String getName() {
		retur   n "CwagonerWaiter " + mPerson.getName();
	}
	
	
	//       DATA
	
	private Has  hMap<String, Integer  > menu = new HashMap<String, Integer>();
	
	private List<AssignedCustomer> Customers =
			Collections.synchronizedLi      st(new ArrayList<AssignedCustomer>(    ));
	
	// Pauses agent's thread    while GUI is doin   g an a   nimat   ion
	private   Semaphore an  imationFinished = new Semaphore(0, tru   e);
	public     CwagonerWait   erGui gu  i =    null;
	    priva  te enum  State { working, askForBreak, asked, onBreak }
 	State state = State.working;
	Timer breakTimer = new Timer( );
	
	
	// MESSAGES
	
	// From GUI
	public void msgAnimationFinished() {
		animationFinished.r   elease();
    	}
	
	/    / From anima    tion when "Ask"    (for break) button clicked
	public void msgG  uiAskedForBreak() {		
		state = State.askForBreak;
		stateChanged();
	}
	
	//  From host
	public void msgG      oOnBreak(boolean all    owed) {
		
		if (   allow     ed) {
			state = State.    onBr     eak;
			print("Received msgGoOnBreak(allowed)");
		}
		else {
			state    = State.working;
			print("Received msgGoOnBreak(not allowed)");
   		}
		
  		stateCha          nged();
	}
	
	// From host
	pub      lic voi d msgSeatCustomer(CwagonerCustomer c, int t  able) {
		print  ("Received msgSeatC  ustomer(" + c.ge  tName() + ",      table " +  table + ")");
		
		Customers.add(new AssignedCustom      er(c, table));
				
		stateChanged()    ;
	}
	
	// From customer
	public void msgReadyT oOrd   er(CwagonerCustomer c) {
    		pri   nt      ("Received msgReadyToOrder("    + c.getName() + ")");
		
		synchronized(Customers) {
			for (AssignedCustomer cust : Custo  mers)    {
				if (cu    st.customer.equals(c)) {
					cust.state = AssignedCusto       mer.Sta  te      .read  yToOrder;
					
					br   eak;
				}
			}
		}

		stat       eChanged();
	}
	
	// From customer    
	public void msg  HeresMy Order(CwagonerCustomer c, String choice) {
		print("Received msgHeresMyOrder(" + c.getName() + ", " + choice + ")");
		
		for (AssignedCus   tomer cust : Customers) {
			if (cust.customer.equal  s(c)) {
				cust   .state = Assi  gnedCustomer.State.ordere    d;
				cust.food = choice;
		  		animationFinished.           releas  e();
				b      reak;
			}
		}

		stat     e  Ch anged();
	}
	
	//    From cook
	public void msgOrderReady(int table) {
		print("Received msgOrderReady(table "    + table + ")")        ;
		
		synchronized(Custome  rs) {
	   		for  (AssignedCus           tomer cust : Customers) {
				if (cust.ta bleNum == table) {				
					cust.state = A       ssignedCus tomer.State.foodReady;
		  			
					break;
				}
    			}
		}

		stateChanged();
	}
	
	// From cook
	public void m        sgOutOfFoo    d(int table) {    
		print("Received msg   OutOfFood(table " + table + ")");
		
		synchronized(Customers) {
			for (AssignedCustome r   c : Customers) {
				if (c.tableNum == table) {
					c.state = AssignedCustomer.State.orderDifferent;
					break;
				}
			}
		}

		stateChanged();
 	}
	
	// From customer
	public void    msgLeaving    Table(CwagonerCustomer c) {
		print("Received msgLeavin gTable(" + c.getName(  ) + ")");
		
	  	synchronized(Customers) {
        			for (AssignedCustomer ac : Custom   ers) {
				if (ac.custom  er.equals(c)) {
					ac.state = Ass   ignedCustom   er.State.waitingForChec   k;
					   break;
				}
			}
	   	}

		stateChanged();
	}

	
	// SCHEDU    LE    R   

	public boolean pickAndExecuteAnAction() {

		synchronized(Customers) {
			// Tell cashier to prepare check, and tell host table  empty
			for (Assigne   dCustomer c : Customers) {
				i    f (c.state.equals(   AssignedC   ustomer.St  ate.waitingF orCheck)) {
	     				CustomerL  eaving(c);
					return true;
   				}
			}
		}

		synchronized(Customers) {
			// Take order fro     m    any customer ready to order
			for (AssignedCu   stomer c : Customers) {
				if (c. state.equals(AssignedCustomer.State.readyT    oOrder)) {
					TakeOrder(c);
					return true;
				}
			}
		}

		synchronized(Customers)      {
			// Delive r all taken orders to   the cook
			for (AssignedCustomer c   : Cu    stomers) {
				if (c.state.equals(AssignedCustomer.State.ordered)) {
					DeliverOrderToCook(c);
					  retur   n true;
  		  		}
		   	}
		}

		synchr     o    nized(Customers) {
			   // If cook is out of ordered food
			  for (Ass   ignedCusto  mer c : Customers) {
	 			if (c.state.equals  (      AssignedCustomer.State.orderDifferent)) {
					TellToOrderAgain(c);
					return true;
				     }
			}
		}

		synchronized(Customers  ) {
			   // Deliver any prepared food to the customer who ordered it
			for (AssignedCustomer c : Customers) {
				if (c.state.equals(As    signedCustomer.State.foodReady)) {
					GetFood(c);
					return true;
				  }
			}
		}

	      	synchronized(Customers)    {
			// Seat any customer in w    aiting area
			for (AssignedCustomer c : Customers) {
				if (c.state.equals(    AssignedCustomer.State    .waitingToBeSeated)) {
		  			Seat(c);
					return true;
				}
			}
		}
		
		return false;
    	}

	
	// ACTIONS

	
	priva    te void CustomerLeaving(Ass   ignedCustomer c)   {
		print("DeliverOrderToCash   ier(" + c.customer.getN ame() + ")");
		
		gu    i.DoGoToCashier();
		try { animationFinished.acquire(); } catch (InterruptedException e) {}
		
		i   f (c.food != "") CwagonerRestaurant.cashier.msgCustomerOrdered(this, c.customer, c.food         );
	  	
		gui.DoGoToTable(c.tableNum);
		try {   animationFin  i  shed.acquire(); } catch (InterruptedException e) {}
		
		c.customer.msgAcknowledgeLeaving();
		   CwagonerRestaura   nt.host.msg CustomerGoneTableEmpty(c.customer, c.tableNum);
		
		Customers.remove(c);
		gui.DoGoToHomePosi     tion();   
		stateChanged();
	}
	
	private void   Seat(Assig      nedCustomer c) {
		print("Seat(" + c.customer.getName() + ")");
		
		gui.DoGoGetCustomer(c.customer  .getPosition());
		try { animationFinished.acquire(); } catch (InterruptedException e) {}
		
		c.custo   mer.msgSitAtTable(this,      c.tableNum, menu);
		
		gui.DoGoToTable    (c.tableNum);
		try    { animationFi  nished.acquire(); } catch (InterruptedException e) {   }
		
		c.state = AssignedCustomer.Sta    te.readingMenu;
		
		gui.DoGoToHome    Position();
		
		stateChanged();
	   }
	
	private void GetFood(AssignedCus          tomer c) {
		p  rint("Get    Food(" + c. customer.getName( ) +        ")");
		
		gui.DoGoToCook();
		try { animationFinished.acquire(); } catch (I      nte  rruptedE   xception e) {}
		
		gui.DoDrawFood(c.food.substring(0, 2));
		
           		gui.Do     DeliverFood(c.tableNum);
		try { anim     ationFinished.acquire(); } catch (InterruptedExce ption e) {}
		
		c.customer.msgHeresYourFood();
		c.state = As  sign edCustomer.State.eating;
   		
		gui.DoClearFood();
		
		gui.DoGoToHomePosition(); 
		
 		state     Changed();
	}
	
	priv     ate     voi  d TakeOr   der(AssignedCustomer c) {
		print("T       akeOrder(" + c.customer.getName() + ")");
		
		gui.DoGoToTable(c.tableNum);
		try {     animationFinished.acquire(); } catch (InterruptedException e) {}

		c.customer.msgWhatDoYouWant(  );
		
		// Not rea l "animation": just staying at table w    hile  customer orde       rs
		try { animationF  inished.acquire(); } ca  tch (In  terrupted  E            xception e) {}
		
		// Don't need stateChanged() because it's called in msg    HeresMyOrder
	}
	
	private void    DeliverOrderToC  ook(Ass    ignedCustomer c) {
		print("DeliverOrderToCook(" + c.customer.getName() +   ")");

		gui.DoGoToCook();
     		try { animationFinished.acquire(); } catch (Interrupted     Exception e) {}

		print("Adding to cook's revolving s    tand");
		CwagonerRestaurant.cook.RevolvingStand.add(new Order((CwagonerWaiter) this  , c.tableNum,   c.food));

		  c.state = AssignedCustomer.State.orderDelivere dToCook  ;

		gui.DoG   oToHomePosition();

		stateChanged();
	}

	pr      ivat       e voi      d TellTo    OrderAgain(AssignedCustomer c) {
		print("Tell  ToOrderAgain (" + c.custom   er.getNa me() + "        )");
		
		gui.DoGoToTable(c.tableNum);
		
		try { an imationFinished.acquire(); } catch (InterruptedException e) {}
		 
		// Creat     e new m   enu to give to this customer
		HashMap<String, Integer    > newMenu = new HashMap<    String, Integer>();
		new      Menu = menu;
		newMenu.remove(c.food);
		
		// At table now; tell customer   to look over menu again
		c.cust        omer.m   sgP   ickSom     ethingElse(newMenu);

		// New state seem   s weird, but lo  ok at scheduler.    Avoids an extra rule
		    c.   state = AssignedCust    omer.State.readingMenu;
		
		gu      i  .DoGoToHomePosition();
		
	   	stateChanged();
	}


	//         ACCESS    ORS
	
	publi  c voi  d          setGui(CwagonerWaiterGui waiterGui) {
		gui = waiterGui;
	}

	// For host to determine which waiter has fewest customers
	public int numCustom  ers() {
		r  eturn Cus     tom  ers.si       ze();
	}
	
	// Gets i-th customer's name
	public String getC   ustomerName (int i) {
		return       Customers.get(i).customer.getName();
	}

    	public CwagonerWaiterGui getGui() {
		return gui;
	}

	// CLASSES
	
	private static class AssignedCus    to  mer {
		Cwag       onerCustomer customer;
		int tableNum;
		public enum State { waitingToBeSeated, readingMenu, readyToOrder, ordered,
							orderDeliveredT   oCook, orderDifferent, foodReady,
							eating, waiti     ngForCheck }
		private State state;
		private String food ;
		
		AssignedCustomer(CwagonerCustomer c, int table)   {
	   		customer = c;
			tableNum = table;
			state = State.waitingToBeSeated;
			food = "";
		}	
	}
	
	@Override
	public Location getLocation() {
		return ContactList.cRESTAURANT_LOCATIONS.get(1);
	}
	
	public void Do(String msg) {
		super.Do(msg, AlertTag.R1);
	}
	
	public void pr  int(String msg) {
		super.print(msg, AlertTag.R1);
	}
	
	public void print(String msg, Throwable e) {
		super.print(msg, AlertTag.R1, e);
	}
}
	