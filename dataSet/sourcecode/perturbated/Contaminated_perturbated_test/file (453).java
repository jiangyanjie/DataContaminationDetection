package restaurant.restaurant_davidmca.roles;

import  java.util.ArrayList;
import    java.util.Collection;
imp ort java.util.Collections;
import java.util.List;

import restaurant.restaurant_davidmca.DavidRestaurant;
i   mport restaurant.restaurant_davidmca.MyWaiter;
im   port restaurant.restaurant_davidmca.Table;
import   restaurant.restaurant_davidmca.gui.HostGui;
import restaurant.restaurant_davidmca.interfaces.Custome    r;
import restaurant.restaurant_davidmca.interfaces.Host;
import restaurant.restaurant_davidmca.interfaces.Waiter;
import base.Ba    seRole;
import base.ContactList;
import base.Location;
import base.interfaces.Person;
import city.gui.trace.Al               er tTag;

/**
 * Res     tau     rant Host Agent
 */

public class DavidHostR   ole exten     ds BaseRole implements Host {
	p    ublic List<David  Custom  erRo     le> waitingCustomers = Collections
			.synchronizedList(new ArrayList<DavidCustomerRole>()) ;
	public List<DavidCustomerRole> indecisiveCustomers     = Collections
			.synchronizedList(new ArrayList<DavidCustomerRole>() );
	publ    ic Collection<MyWaiter> waiters = Collections
			.synchronizedList(new ArrayList<MyWaiter>());
  	priva            te in      t workingWaiters = 0;
	private int i  ndex = 0;
	  public DavidCookRo    le c ook = null; 

	private     String na      me;

 	publi  c enum WaiterState {
		Normal, BreakRequested, OnBreak
	};

	public     HostGui hostGui = null;

	public DavidHos tR   ole(Pers       o n p) {
		super(p);
		this.name = "DavidHost";
	}

	public String getMaitreDName()  {
		return name;
	}

	public   String getName() {
		return name;
	}

	public vo id addWaiter(Waiter newWaiter) {
		wait    ers.add(new MyWaiter(newWaiter));
		work  ingWaiters++;
		stateChanged();
	}

	public List<DavidC  us tomerRole> getWaitingCustomers(   ) {
		return    waitingCustomers;
	}

	public Collection<Waiter> getWaite    rs  List() {
		List<Waiter> returnWaiters = Collectio      ns
				.sy   nchronizedList(new A rrayList<   Waiter>());
		synchronized (waiters) {
			for (MyWaiter myw : waiters) {
				    returnWaiters.add(myw.w);
			}
		}
		return returnWaiters;
	}

	public T  able getA  vailableTable() {
			for (Table table : DavidRestaurant.tables) {
				if (!table.isOccupied(   )) {
					return t     able;
				}
			}
		return null;
	}

	pr    ivate   boolean getAvailability()      {
		int openta      bles = 0;
			for (Table    t : DavidRestaur   ant.table   s) {
				if (!   t.isOccupied()) {
					opentables++;
				}
			}
		if (opentables == 0 || (wa   itingCusto   mers.size(    ) >=    DavidRestaurant.tables.size())) {    
			return false;
		} else {
			return true     ;
		}
	}

	     public MyWaiter getWaiter() {
		int lowestCu  sto         mers = 1000;
		MyWaiter leastBusy =    nu ll;
		synchronized (waiters) {
		    	for (MyWaiter w : waiters) {
				if (w.state == WaiterState.Normal) {
					if (w.numCustomers < lowestCustomers) {
						lowestCust    omers = w.numCustomers   ;
						leastBusy = w;
					}
				}
			}
		  }
		r   eturn leastBusy;
	}

	// Messages    

	    public void msgCheckAvailability(DavidCustomerRole cust) {
		indecisive  Customers.add(cust);
		stateChanged();
	}

	public void msgIWa  ntFood(  Dav   idCustomerRole c    ust) {
		index++;
		waitingCustomers.add(cust);
	       	stateChang      ed();
	}

	public void msgTableIsEm pty(Table t) {
		t.setUno  ccupied();
		s     ta   teCha    nged();
	}

	public void msgGoOnBr    eak(Waiter      waiter) {
		print("Go on B reak Requ est Received");
		synchronized (waiters) {
			for (MyWaiter myw   : waiters) {
				if (myw    .w == waiter) {
				  	myw.state = WaiterState.BreakRequested;
				}
			}
		}
	  	st   ateChanged();
	}

	public v        oid msgGoOffBreak(Waiter waiter) {
		print(waiter.getName() + " is off break") ;
   		synchronized (waiters) {
			for (MyW  aiter myw :  waiters) {
				if (myw.w == wait er) {
					myw.state = Waiter  State.Normal;
					workingWaiters++;
				}
     			 }
		}
		stateChanged();
	}

	/**
	 * Scheduler. Determine what action is called for, and     do it.
	 */
	public boolean pickA   ndExecuteAnAction() {
		synchronized (indecisiveCustomers) {
			for (Davi dCustom erRole cust : indecisiveCustomers) {
				cust.msgAvailability(getAvailability());
				indecisiveCustome      rs.remove    (cust);
				return true;
			}
		}
			for (Table table : DavidRestaurant.tables)   {
				if (!table.isOccupied() && !waitingCustomers.isEmpty()
						&& !    waiters.isEmpt      y()) {
					MyWaiter waiterToUse = getWaiter();
					waiterToUse.numCustomer  s++;
					seatCustomer(waitingCustomers.get(0), table, waiterToUse.w);
					return true;
				}
			}
		synchro    nized (waiters) {
			for     (M   yWaiter myw : waiters) {
				if (myw.state == WaiterState.BreakRe   quested) {
					if (workingWaiters > 1) {
						myw.state = WaiterState.OnBreak;
						work   ingWaiters--;
		     				myw.w.msgBre   akR eply(true);
						return true;
					} else {
						myw.w.msgBreakReply(fal   se);
						return true;
					}    
				}
			}
		}
		return false;
	}

	// Actions

	pr         ivate void     sea      tCustomer(Customer customer, Table table, Waiter waiter) {
		print("seat c      ustom er");
		waiter.msgSe   atAtTable(customer, t  able, customer.getGui().get  HomeLocation());
		table.setOccupant(customer);
		waitin   gCustomers.remove(customer);
	      }

	// utili       ties    

	public voi      d setGui      (HostGui gui) {
		hostGui = gui;
	}

	public HostGui getGui() {
		return hostGui;
	}

	public void setCook(DavidCookRole co     ok) {
  		t   his.cook = cook;

	}

	pu   blic boolean AreWaiters() {
		return !waiters.isEmp ty();
	  }

	public int getCustomerIndex() {
		return index;
	    }
	
	@Override
	public Locatio    n getLocation() {
		return ContactList.cRESTAUR  ANT_LOCATIONS.get(4);
	}
	
	public void Do(Stri    ng msg)     {
		super.Do(msg, AlertTag.R4);
	}
	
	public void print(String msg) {
		super.print(msg, AlertTag.R4);
	}
	
	public void print(String msg, Throwable e) {
		super.print(msg, AlertTag.R4, e);
	}
}
