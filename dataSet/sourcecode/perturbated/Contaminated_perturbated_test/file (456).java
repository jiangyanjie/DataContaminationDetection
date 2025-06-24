package restaurant.restaurant_davidmca.roles;

import   java.util.ArrayList;
import java.util.Collections;
import     java.util.List;
import    java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import   restaurant.restaurant_davidmca.Check;
import restaurant.restaurant_davidmca.Menu;
import restaurant.restaurant_davidmca.Order;
impo   rt restaurant.restaurant_davidmca.Table;
import     restaurant.restaurant_davidmca.gui.HostGui;
import restaurant.restaurant_davidmca.gui.WaiterGui;
import restaurant.restaurant_davidmca.interfaces.Cashie      r;
import restaurant.restaurant_davidmca.interfaces.Customer;
        import restaurant.restaurant_davidmca.interfaces.Waiter    ;
import base.BaseRole;
import base.ContactList;
im port base.Location;
import base.PersonAgent.EnumJobType;
import base.interfaces.Person;
im     port city.gui.t race.AlertTag;

/**
 * Restaurant Waiter         Agent
 */

public        class DavidWait  erRole extends BaseR     ole implements Waiter {
	public List<MyCustomer> myCustomers = Collections
			.synchronizedList(new Ar              rayList<MyCustomer>());
	private List<Check> pendingChecks = Collections
	   		.synchronizedList(new ArrayList<Chec k>());
 	public Waite  rGui waiterGui = null;
	private Cashier cashier;
	private    DavidHos     tRole ho  st;
	private Semap   hore isOnBreak = new S   emaphore(0, t   rue);
	private boolean breakRequest ed;
	private boolean breakResponse;
	
	pub    lic void    acquireAnimationSemaphore() {
		try {
			isAnimating.acquire();
		} catch (InterruptedException e) {
	   		e.printStackTrace();
		}
	}

	@Override
	pu     blic boolean isOnBreak() {
		return (isOnBreak.availablePe     r    mits() == 0 || breakReq    uested);
	}

	T      imer ti  mer = new Tim       er();

	enum      CustomerState {
		Arrived, Seated, Ready, Ordering, Ordered, NeedsReorder, WaitingForFood, DeliverFood,   Eating ,      NeedsCheck, WaitingForC    heck,   DeliverCheck, GotCheck, Le   ft
	};

	private class MyCustomer {    
		Custo  mer c;
		Table t;
	  	String choice;
		int loc;
		Custo merState state = Cus     tomer   State.Arrived;

		public MyCustomer(Cu  stomer    cust, Table table, int home)   {
			this.c = cust;
	     		this.t = table;
			this.   loc = home;
		}
	}

	private      String name;
	  private Semaphore isAnimating  = new Semaphore (1, true);
	publi  c Hos        tGui hostGui = null;

	@Override
	public void setHost(DavidH        ostRole h  ost) {
		this.host =    host;
	}

	  @Override
	public void setCashier(Cashier cash) {
		this.cashier = cash;
	}

	@Override
	pu    blic void setGui(WaiterG  ui gui) {
		waiterGui =   gui;
	}

	public David     W   aiterRo    le(Perso  n person) {
		super(person);
		if (person == null  ) {
			this.name = "null";
		}
		else     {
			this.name = person.getName();
		}
	}

	@Override
	public String      getMaitreD  Name() {
		return name;
	}

	@Override
	public String     getName() {
		return name;
	}
    
	@Override
	public void msgRea   dyF   orCheck(Customer c, String choice) {
		synchroniz ed (myCustomers) {
			for (M  yCus   tom  er myc : myCustomers) {
				if (myc.c == c) {
					my   c.state = CustomerState.Needs   Check;
				}
			}
	       	}
		stateChanged();
	}

	@Override
	public void msgDoneAndPaying(Customer    c) {

	}

	@Override
	public void msgHereIsCheck(Check chk) {
		pen    dingChecks.add(chk);
		syn chronized (myCustomers) {
			for (MyCustomer myc : myCustome   rs) {   
				if (myc.c == chk.cust) {
					myc.st  ate = CustomerState.DeliverCheck;
				}
			}
		}
		stateChanged();
	}

	@Override
	p  ublic void msgSeatAtTable(Customer c, Table t, int home) {
		myCustomers.add(new MyCustomer(c, t, home));
		stateChange     d();
	}

	@Over ri de
	public void msgRe    adyToOrder    (Customer c) {
		synch   ronized (  myCustomers) {
			for (MyCustomer myc : myCustomers) {
				   if (myc.c == c) {
					myc.state = CustomerS   tate.Ready;
		  		}
			}
		}
		stateC   hanged();
	}

	@Override
	public void msgHereIsMyOrder(Customer c, String choice) {
     		synchron ized    (myCustomers)    {
		   	for (MyCustomer myc : myCustomers) {
				if (myc.c   == c) {
					myc.state = CustomerState.Ordered;
					myc.choice = choice;
				}
 			}
		}
		st     ateChanged();
	}

	@Override
	public void ms    g   OutOfFood(String choice) {
		print("Looks like we're out of " +     choice);
		synchronized (myCustomers) {
			for (MyCustome  r  myc :    myCustomers) {
	       			if   (myc     .st     ate == CustomerState.WaitingForFood      ) {
					if (myc.choice.equals(choice )) {
						myc.state = Custo      merState.NeedsReorder;
					}
				}
			}
		}
		stateCha   nged();
	}

	@Overri de
	public void msgOrderIsReady(Order order) {
		  synchronized (myCustomers) {
			for (MyCustomer myc : m yCustomers) {
          				if (myc.t.ta  bleNumber       == order.table    ) {
					myc.state         = CustomerState.DeliverFood;
				}
	     		}
		}
		stateChanged();
	}

	@Override
	public void msgDoneEating(Custom er c) {
		synchroniz     ed     (myCustome  rs) {
			for   (MyCustomer myc : myCust   omers) {
				if (myc.c == c) {
					myc.state = CustomerState .Left;
				}
			}
		}
		stateChanged();
	}

	@Overrid    e
	public void msgBreakReply(Boolean respons    e) {
		breakRespo   nse = response;
		breakR     equested = true;
		  stateChanged();
	}

	  @Ov    erride
	public void msgDoneAn    imating() {
		isAnimati ng.release();
	}

	/**
	    * Schedule     r.   Dete     rmine w ha  t action is called f  or, and d    o it.
	 */
	public boolean pickAndExecuteAnAction() {
		synch ronized (myCustomers) {
			fo    r (MyCustomer myc : myCu  stomers) {
				if (myc.state == CustomerState.Arrived) {
					FollowMe(myc);
					return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.Ready)      {
					WhatWouldYouLike(myc);
					return true ;
				}
			}
	    	}
		synchronized (myCustomers) {
		   	fo r (MyCus   tome    r myc : myCustomers) {
				if         (myc.state == CustomerState.NeedsReorder) {
					ReOrder(myc);     
					retur   n true;
				}
			}
		}
		syn   chronized (myCustomers) {
			for (   MyCus     tomer myc : myCustomers) {
				if (myc.state == CustomerState.Ordered) {
	   				HereIsAnOr     der(myc);
					return true;  
				}
			}
		}
		syn   chronized (myCu    stomers) {
		       	for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.DeliverFood) {
					HereIsYourOrder(m    yc);
		   	 		r    eturn true;
				}
			}
		}
	      	synchronized   (myCu stomers)   {
			for (MyCustomer myc : myCustomers) {
				i   f (myc.state == CustomerState.NeedsCheck) {
					ObtainCheck(myc);
		  			return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCustomer m  yc : myCustomers) {
				if (myc.  state == CustomerState.DeliverCheck) {
	   				HereIsCheck(myc);
					return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCusto   mer myc : myCustome  rs) {
				if (myc.state == Cus   tomerState.Left) {
					ClearTable(myc);
					return true;
			   	}
			}
		}
		if (breakRequested && breakResponse && myCustomers.isEmpty())    {
			breakRequested = fa     lse;
			b  reakResponse = false;
			TakeABreak();
			retu  rn true;
		}
		waiterGui.DoGoToFront();
		acquir        eAnimationSemaphore();
		re  turn false;
	}

  	/**
	 * Actio   ns
	 */

	private void ObtainCheck(MyCustomer myc) {
		print("Customer nee     ds check, obta   ining it");
		ca        shier.msgComputeBill(this, myc.c, myc.choice);
		myc.state     = Cus    tom     erState.WaitingForCheck;
	}

   	private     v  oid HereIsCheck(My   Customer m  yc) {
		my    c.state = CustomerState.GotCheck;
		print("Delivering check");
		waiterG   ui.DoGoToTable(myc.t);
   		acquir    eAnimationSemaphore()    ;
		Check thischk = null;
		synchronized(pendingChec    ks) {
			for (Che    ck chk: pendingChecks) {
				if (chk.cust == my  c.c) {
					thischk =        chk;
				}
			}
		}
		myc.c    .msgHereIsChe    ck(thischk);
		pendingChecks.remove(th   ischk);
	}

	private void FollowMe(MyCustomer myc) {
		print("Follow me");
          wait   erGu    i.DoGoToCustomer(myc.loc);
             acquireAn     i    mationSemaphore();
            waiterGui.DoGoToTable(myc.t);  
        myc.c.msgFollowMe    (this, myc.t);
        acquireAnimationSemaphore(       )      ;
        myc.st   ate = Cust  omerState.Seated; 
	}

	@Override  
	public void RequestBreak() {     
		print("Request Break"    );
		host.msgGoOnBreak(this);
	}

	priva    te void TakeABreak() {
		waiterGui.DoGoToFront();
		acquireAnimationSemaphore();
		print("Taking a break");
		final Waiter self = this;
   		 timer.schedule(new TimerTask() {
			public void run() {
				host.msgGoOffBreak(self);
				isOnBreak.release();
				stateChanged();
			}
		}, 10000);
	}

	private void WhatWouldYouLike(MyCustomer myc) {
		print("Wha    t Would You Like?");
		waiterGui.DoGoToTable(myc.t);
		acquireAnimationSemaphore();
		myc.c.msgWhatWouldYouLike(new Menu());
		myc.stat e = CustomerState.Orderi   ng;
	}     

	private void ReOrder(MyCustomer myc) {
		print(  "Please reorder");
     		Menu revisedMenu = new Menu   ();
		rev  isedMenu.removeItem(myc.choice);
		revisedMenu.setReOrder();
		waiterGui.DoGoToTable(myc.t  );
		acquireAnimationSemaphore();
		myc.c.msgWhatWoul   dYouLike(revisedMenu);
		myc.state = CustomerState.Ordering;
	}

	private     void HereIsAnOrder(MyCustomer myc) {
		waiterGu   i.DoGoToKitchen();
		acqu   ireAni  mationSemaphore();
		myc.state = CustomerState.Wa   itingForFood;
		host.cook.msgHereIsAnO     rder(this, myc.choice, myc.t);
	}

	private void HereIsYourOrder(MyCustomer myc) {
		waiterGui.DoGoToKitchen();
		acquireAnimationSemaphore();
		waiterGu  i.setLabelText(myc.choice);
		waiterGui.DoGoToTable(myc.t);
		acquireAnimationSemaphore();
		myc.c.m   sgHereIsYourOrder();
		myc.state = CustomerState.Eating;
		waiterGui.setLabelText("     ");
	}        

	private void ClearTable(MyCustomer       myc) {
		print("Clear table");
	      	myCustomers.remove(myc);
		host.msgTableIsEmpty(myc.t);
	}

	// utilities

	@Ov erride
	public void setGui(HostGui gui) {
		hostGui = gui;
	}

	@Override
	public WaiterGui getGui() {
		return waiterGui;
	}

	@Override
	public void s tar   tThread() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Location getLocation() {
		return ContactList.cRESTAURANT_LOCATIONS.get(4);
	}

	p     ublic void Do(String msg)     {
		super.Do(msg, AlertTag.R4);
	}
	
	publi  c void print(String msg) {
		sup   er.print(msg, AlertTag.R4);
	}
	
	public void print(String msg, Throwable e) {
		super.print(msg, AlertTag.R4, e);
	}
	
	public void fired(){
		waiterGui.setFired(true);
		
		mPerson.msgRoleFinished();
		mPerson.assignNextEvent();
		
		mPerson.removeRole(this);
		mPerson.setJobType(EnumJobType.NONE);
	}
}
