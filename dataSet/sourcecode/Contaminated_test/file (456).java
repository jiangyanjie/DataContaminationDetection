package restaurant.restaurant_davidmca.roles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import restaurant.restaurant_davidmca.Check;
import restaurant.restaurant_davidmca.Menu;
import restaurant.restaurant_davidmca.Order;
import restaurant.restaurant_davidmca.Table;
import restaurant.restaurant_davidmca.gui.HostGui;
import restaurant.restaurant_davidmca.gui.WaiterGui;
import restaurant.restaurant_davidmca.interfaces.Cashier;
import restaurant.restaurant_davidmca.interfaces.Customer;
import restaurant.restaurant_davidmca.interfaces.Waiter;
import base.BaseRole;
import base.ContactList;
import base.Location;
import base.PersonAgent.EnumJobType;
import base.interfaces.Person;
import city.gui.trace.AlertTag;

/**
 * Restaurant Waiter Agent
 */

public class DavidWaiterRole extends BaseRole implements Waiter {
	public List<MyCustomer> myCustomers = Collections
			.synchronizedList(new ArrayList<MyCustomer>());
	private List<Check> pendingChecks = Collections
			.synchronizedList(new ArrayList<Check>());
	public WaiterGui waiterGui = null;
	private Cashier cashier;
	private DavidHostRole host;
	private Semaphore isOnBreak = new Semaphore(0, true);
	private boolean breakRequested;
	private boolean breakResponse;
	
	public void acquireAnimationSemaphore() {
		try {
			isAnimating.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isOnBreak() {
		return (isOnBreak.availablePermits() == 0 || breakRequested);
	}

	Timer timer = new Timer();

	enum CustomerState {
		Arrived, Seated, Ready, Ordering, Ordered, NeedsReorder, WaitingForFood, DeliverFood, Eating, NeedsCheck, WaitingForCheck, DeliverCheck, GotCheck, Left
	};

	private class MyCustomer {
		Customer c;
		Table t;
		String choice;
		int loc;
		CustomerState state = CustomerState.Arrived;

		public MyCustomer(Customer cust, Table table, int home) {
			this.c = cust;
			this.t = table;
			this.loc = home;
		}
	}

	private String name;
	private Semaphore isAnimating = new Semaphore(1, true);
	public HostGui hostGui = null;

	@Override
	public void setHost(DavidHostRole host) {
		this.host = host;
	}

	@Override
	public void setCashier(Cashier cash) {
		this.cashier = cash;
	}

	@Override
	public void setGui(WaiterGui gui) {
		waiterGui = gui;
	}

	public DavidWaiterRole(Person person) {
		super(person);
		if (person == null) {
			this.name = "null";
		}
		else {
			this.name = person.getName();
		}
	}

	@Override
	public String getMaitreDName() {
		return name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void msgReadyForCheck(Customer c, String choice) {
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.c == c) {
					myc.state = CustomerState.NeedsCheck;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgDoneAndPaying(Customer c) {

	}

	@Override
	public void msgHereIsCheck(Check chk) {
		pendingChecks.add(chk);
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.c == chk.cust) {
					myc.state = CustomerState.DeliverCheck;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgSeatAtTable(Customer c, Table t, int home) {
		myCustomers.add(new MyCustomer(c, t, home));
		stateChanged();
	}

	@Override
	public void msgReadyToOrder(Customer c) {
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.c == c) {
					myc.state = CustomerState.Ready;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgHereIsMyOrder(Customer c, String choice) {
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.c == c) {
					myc.state = CustomerState.Ordered;
					myc.choice = choice;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgOutOfFood(String choice) {
		print("Looks like we're out of " + choice);
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.WaitingForFood) {
					if (myc.choice.equals(choice)) {
						myc.state = CustomerState.NeedsReorder;
					}
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgOrderIsReady(Order order) {
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.t.tableNumber == order.table) {
					myc.state = CustomerState.DeliverFood;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgDoneEating(Customer c) {
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.c == c) {
					myc.state = CustomerState.Left;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgBreakReply(Boolean response) {
		breakResponse = response;
		breakRequested = true;
		stateChanged();
	}

	@Override
	public void msgDoneAnimating() {
		isAnimating.release();
	}

	/**
	 * Scheduler. Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.Arrived) {
					FollowMe(myc);
					return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.Ready) {
					WhatWouldYouLike(myc);
					return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.NeedsReorder) {
					ReOrder(myc);
					return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.Ordered) {
					HereIsAnOrder(myc);
					return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.DeliverFood) {
					HereIsYourOrder(myc);
					return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.NeedsCheck) {
					ObtainCheck(myc);
					return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.DeliverCheck) {
					HereIsCheck(myc);
					return true;
				}
			}
		}
		synchronized (myCustomers) {
			for (MyCustomer myc : myCustomers) {
				if (myc.state == CustomerState.Left) {
					ClearTable(myc);
					return true;
				}
			}
		}
		if (breakRequested && breakResponse && myCustomers.isEmpty()) {
			breakRequested = false;
			breakResponse = false;
			TakeABreak();
			return true;
		}
		waiterGui.DoGoToFront();
		acquireAnimationSemaphore();
		return false;
	}

	/**
	 * Actions
	 */

	private void ObtainCheck(MyCustomer myc) {
		print("Customer needs check, obtaining it");
		cashier.msgComputeBill(this, myc.c, myc.choice);
		myc.state = CustomerState.WaitingForCheck;
	}

	private void HereIsCheck(MyCustomer myc) {
		myc.state = CustomerState.GotCheck;
		print("Delivering check");
		waiterGui.DoGoToTable(myc.t);
		acquireAnimationSemaphore();
		Check thischk = null;
		synchronized(pendingChecks) {
			for (Check chk: pendingChecks) {
				if (chk.cust == myc.c) {
					thischk = chk;
				}
			}
		}
		myc.c.msgHereIsCheck(thischk);
		pendingChecks.remove(thischk);
	}

	private void FollowMe(MyCustomer myc) {
		print("Follow me");
        waiterGui.DoGoToCustomer(myc.loc);
        acquireAnimationSemaphore();
        waiterGui.DoGoToTable(myc.t);
        myc.c.msgFollowMe(this, myc.t);
        acquireAnimationSemaphore();
        myc.state = CustomerState.Seated;
	}

	@Override
	public void RequestBreak() {
		print("Request Break");
		host.msgGoOnBreak(this);
	}

	private void TakeABreak() {
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
		print("What Would You Like?");
		waiterGui.DoGoToTable(myc.t);
		acquireAnimationSemaphore();
		myc.c.msgWhatWouldYouLike(new Menu());
		myc.state = CustomerState.Ordering;
	}

	private void ReOrder(MyCustomer myc) {
		print("Please reorder");
		Menu revisedMenu = new Menu();
		revisedMenu.removeItem(myc.choice);
		revisedMenu.setReOrder();
		waiterGui.DoGoToTable(myc.t);
		acquireAnimationSemaphore();
		myc.c.msgWhatWouldYouLike(revisedMenu);
		myc.state = CustomerState.Ordering;
	}

	private void HereIsAnOrder(MyCustomer myc) {
		waiterGui.DoGoToKitchen();
		acquireAnimationSemaphore();
		myc.state = CustomerState.WaitingForFood;
		host.cook.msgHereIsAnOrder(this, myc.choice, myc.t);
	}

	private void HereIsYourOrder(MyCustomer myc) {
		waiterGui.DoGoToKitchen();
		acquireAnimationSemaphore();
		waiterGui.setLabelText(myc.choice);
		waiterGui.DoGoToTable(myc.t);
		acquireAnimationSemaphore();
		myc.c.msgHereIsYourOrder();
		myc.state = CustomerState.Eating;
		waiterGui.setLabelText("");
	}

	private void ClearTable(MyCustomer myc) {
		print("Clear table");
		myCustomers.remove(myc);
		host.msgTableIsEmpty(myc.t);
	}

	// utilities

	@Override
	public void setGui(HostGui gui) {
		hostGui = gui;
	}

	@Override
	public WaiterGui getGui() {
		return waiterGui;
	}

	@Override
	public void startThread() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Location getLocation() {
		return ContactList.cRESTAURANT_LOCATIONS.get(4);
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
	
	public void fired(){
		waiterGui.setFired(true);
		
		mPerson.msgRoleFinished();
		mPerson.assignNextEvent();
		
		mPerson.removeRole(this);
		mPerson.setJobType(EnumJobType.NONE);
	}
}
