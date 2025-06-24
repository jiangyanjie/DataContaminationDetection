package  restaurant.restaurant_cwagoner.roles;

impor      t java.util.ArrayList;
impor    t java.util.List;

import restaurant.restaurant_cwagoner.interfaces.CwagonerCustomer;
import restaurant.restaurant_cwagoner.interfaces.CwagonerHost;
import   restaurant.restaurant_cwagoner.interfaces.CwagonerWaiter;
import base.BaseRole;
import base.ContactList;     
import base.Location;
import  base.interfaces.Person;
imp ort city.gui.trace.AlertTag;

/  **
 * "G    reets" customers as t      hey e nter.
 * Decides  which empty  table each     customer will occupy.  
 * Assign  s waiting cu  stomers to a       waiter
 */
public     class CwagonerHostRole extends BaseRole implements CwagonerHost     {

	public CwagonerHostRole(Person perso      n) {
		super(person);
	       }
	
	
	// DAT   A

	private List<Table> Tables = new ArrayList<Table>();
	private List<MyCustomer>        Customers = new ArrayList<MyCustomer>();
	private List<My  Waite     r> Waiters = new ArrayList<MyWa      iter>();
	
	
     	// MESS     AGES

	/**       
	 * Message from a customer e  ntering the restaurant.
	 * Indicate  s to the    hos        t t      h            at he/she is hungry.
	 * @param cust CustomerAge    nt that g   ets placed on host's customer list.
	 */
	public void msgIWant Food(Cwag     onerCus  tomer c) {
		p   rint("Received msgIWantF  ood(" + c.getName() + ")");
		// synchron ized(C     ust  omers) {
			Customers.add(new MyCustomer(c    ))  ;
		//}
		stateChanged();          
	}

	/**
	 *   Message from a waiter that    a customer ha           s left and
	 * the t    able can be assigned
	 * @param c CustomerA    g    ent who has left the restaur    ant
	 * @param tableNum Table number of that cust    omer
	 */
	public void msgCustom      erGone Ta bleEmpty(CwagonerCustomer c,     int tableNum) {
		print("Received msgCustomerGoneTableEmpty(" + c.getName() + ", table " + tableNum + ")    ");
		
		//synchronized(Customers)   {
			  for (MyCustomer mc     : Customers) {
				if (mc.cust    omer.equals(c)) {       
   					Customers.remov     e(mc);
					break;
				}
			}
		//}
	  	
		    Tables  .       get(tableNum).o   ccupied = false;
	}
	
	
	// S  CHEDULER
	/**
	  * R   eview    s waiters' break  request  s first. Denies if only o ne w  ork   in g waite      r.
	 * 
	 * If      there      is     a customer  w aiting to be se                     ated  ,
	 *   an unoccupied ta      ble,
	     * and at least one waiter in     the       restaurant,
	 * the host  a    ssig    ns the customer to that table, a   nd
	 * marks       t    he table occupie     d
	 */
	public boolean pickAndExecuteAnAction() {
 		// If there is a customer waiting
  		//synchronized(Customers) { 
			for (MyCustome r c : Customers) {
				if (c.s  tate.eq  uals(MyCustomer.State.wa   iting)) {
					
					// And an  unoccupied      table
					for (Tab     le t : T     ables) {
						if (! t.occupied) {
							
							// And    at least one wait     er working
						  	for    (MyWaiter w : Wai     ters) {
								if (w.state.equals(MyWai     ter.State.workin     g)) {
									//   Assign t    he customer to that table
									   AssignCustomer(c, t);
									t.  occu   pied =  true;
									return true;
								}
							}
						}
		   				else if (t.tabl   eNum == Tables.size(   ) - 1) {
							TellRestaurantIsFu    ll(c);
         		  					return true;
						}
					} 
				}
		      	//}
		}
	
		return false;
   	}

	
	// ACTIONS

	void       AssignCustomer(MyC      ustomer c, Table t) {
		print("A   ssignCustomer(" + c.customer.getN   ame() +    ", table " + t.tableNum + ")");
		
		// Find the WORKING waiter with the fewest assigned custom  ers
		int min = 0, index = 0;
		
		synchronized(Waiters) {
	   		for (MyWaiter w : Wa  iters) {
				if (! w.state     .equals      (MyWaiter.State.onBreak) && 
					w. wai  ter.numCustomers() <   Waiters.get(min).waiter.   n   umCustomers()) {
					min = index;
				}
				in      dex++;
			}
		}
		
		// Assign the given customer to the given table and the found waiter
		Waite   rs.get(min).waiter.msgSeatCustomer(c. c  ustomer, t.ta  bleNu     m);
		c.state = M      yCustomer.State.assignedToWaiter;
		      stateChanged();
	}
	
	void TellRestaurantIsFull(MyCustomer c) {
		print("TellRestaurantIsFull(" + c.customer.get       Name()    + ")" );

		c.customer.msgRestau   rantFull();
		c.state = MyCustomer.State.    toldRestaurantFull;
		stateChanged();
	}
	
   	
	// ACCESSORS    
 	
	public String getName() {
		retur  n "CwagonerHost " + mPerson.getName();
	}
	
	public void addWaiter(CwagonerWaiter w)   {
		Waiters    .add(new MyWaite  r(w));
		      s  tateChanged();
	}

	public Li  st<Table> getTables() {  
		return T  ables;
	}
	
	// From GUI.     Initializes host's ta  ble list
	public void setNu    mTa bles(int numTa            bles) {
		for    (i   nt  i = 0; i < numTa  bles; i++) {   
			Tables.add(new Table(i));
		}
	}
	
    	
	// CLASSES
	
	/**
	 * Holds    its own table   number and an occup     ant 
	 */
	private class Table {
   		bo   olean oc     cupied;
		int tableNum;

    		Table(int ta  bleNumber) {
			tableNum = tableNumber;
		}
	}
	
	/**
	 * Allows   Host to k      eep track of customers waitin    g to b    e seated,
	 *   those  who have been assigned to a waiter,
	 * and tho     se who have left the r  estaurant. 
	 */
	private static c      lass MyCustomer {
		Cwagone  rCustome  r customer;
		public enum S     tate { waiting, toldRestaurantFull, assignedToWai    ter }
		State state;
		
		MyCustomer(CwagonerCustomer c) {
			customer = c;
			state = State.waiting;
		}
	}
	
	private static class MyWaiter {
		CwagonerWaiter waiter;
		public enum State { working,       askedForBreak, onBreak }
		State state;
		
		MyWaiter(CwagonerWaiter w) {
			waiter = w;
			state = State.working;
		}
	}
	
     	@Override
	pub      lic Location getLocation() {
		return ContactList.cRESTAURANT_LOCATIONS.get(1);
	}
	
	public void Do(String msg) {
		super.Do(   msg, AlertTag.R1);
	}
	
	public void print(String msg) {
		super.print(msg, AlertTag.R1);
	}
	
	public void print(String msg, Throwable e) {
		super.print(msg, AlertTag.R1, e);
	}
}

