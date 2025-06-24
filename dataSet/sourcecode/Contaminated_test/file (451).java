package restaurant.restaurant_davidmca.roles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import restaurant.intermediate.RestaurantCookRole;
import restaurant.restaurant_davidmca.Order;
import restaurant.restaurant_davidmca.Table;
import restaurant.restaurant_davidmca.gui.CookGui;
import restaurant.restaurant_davidmca.interfaces.Cook;
import restaurant.restaurant_davidmca.interfaces.Market;
import restaurant.restaurant_davidmca.interfaces.Waiter;
import base.BaseRole;
import base.ContactList;
import base.Item;
import base.Location;
import base.interfaces.Person;
import city.gui.trace.AlertTag;

/**
 * Restaurant customer restaurant_davidmca.agent.
 */
public class DavidCookRole extends BaseRole implements Cook {
	public RestaurantCookRole mRole;
	private CookGui cookGui;
	private Semaphore isAnimating = new Semaphore(1, true);
	@SuppressWarnings("unused")
	private boolean ordering;
	@SuppressWarnings("unused")
	private boolean reorder;
	@SuppressWarnings("unused")
	private Request currentRequest;
	private final static int orderThreshold = 1;

	private class Request {
		@SuppressWarnings("unused")
		Map<String, Integer> stuffToBuy;
		@SuppressWarnings("unused")
		List<Market> askedMarkets;

		private Request() {
			stuffToBuy = Collections
					.synchronizedMap(new HashMap<String, Integer>());
			askedMarkets = Collections
					.synchronizedList(new ArrayList<Market>());
		}
	}

	public List<Order> pendingOrders = Collections
			.synchronizedList(new ArrayList<Order>());
	public List<Order> revolvingStand = Collections
			.synchronizedList(new ArrayList<Order>());;
	int cooktime = 5000; // hack

	public enum OrderState {
		Received, Cooking, Finished
	};

	private String name;

	Timer cookTimer = new Timer();
	Timer standTimer = new Timer();
	boolean timeToCheckRevolvingStand = false;
	TimerTask standTimerTask = new TimerTask() {
		public void run() {
			if (mPerson != null) {
				stateChanged();
			}
		}
	};

	// restaurant_davidmca.agent correspondents
	/**
	 * Constructor for CookAgent class
	 * 
	 * @param name
	 *            name of the customer
	 */
	public DavidCookRole(Person p, RestaurantCookRole r) {
		super(p);
		mRole = r;
		this.name = "DavidCook";
		ordering = false;
		reorder = false;
		standTimer.scheduleAtFixedRate(standTimerTask, new Date( System.currentTimeMillis() + 10000), 10000);
	}

	public String getName() {
		return name;
	}

	public void setGui(CookGui g) {
		cookGui = g;
	}


	// Messages

	public void msgHereIsAnOrder(Waiter w, String choice, Table t) {
		pendingOrders.add(new Order(w, choice, t));
		stateChanged();
	}

	public void msgDoneAnimating() {
		isAnimating.release();
		stateChanged();
	}

	// Scheduler

	public boolean pickAndExecuteAnAction() {
		if (revolvingStand.size() > 0) {
			CheckStand();
			return true;
		}
		if (!pendingOrders.isEmpty()) {
			if (pendingOrders.get(0).status == OrderState.Finished) {
				Notify(pendingOrders.get(0));
				return true;
			}
			if (pendingOrders.get(0).status == OrderState.Received) {
				pendingOrders.get(0).status = OrderState.Cooking;
				Cook(pendingOrders.get(0));
				print("cooking order...");
				return true;
			}
		}
		return false;
	}

	// Actions

	private void Cook(Order order) {
		cookGui.setLabelText("Picking up order");
		cookGui.DoGoToPlating();
		try {
			isAnimating.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cookGui.setLabelText("Going to Fridge");
		cookGui.DoGoToFridge();
		try {
			isAnimating.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cookGui.setFood();
		Random rand = new Random();
		int randomNum = rand.nextInt(4) + 1;
		cookGui.setLabelText("Going to Grill " + randomNum);
		cookGui.DoGoToGrill(randomNum);
		try {
			isAnimating.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		final String thischoice = order.choice;
		if (mRole.mItemInventory.get(Item.stringToEnum(thischoice)) == 0) {
			order.waiter.msgOutOfFood(thischoice);
			pendingOrders.remove(order);
			return;
		}
		cookGui.setLabelText("Cooking");
		cookTimer.schedule(new TimerTask() {
			public void run() {
				try {
					isAnimating.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cookGui.setLabelText("Going to Plating");
				cookGui.DoGoToPlating();
				try {
					isAnimating.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pendingOrders.get(0).status = OrderState.Finished;
				mRole.mItemInventory.put(Item.stringToEnum(thischoice), mRole.mItemInventory.get(Item.stringToEnum(thischoice))-1);
				if (mRole.mItemInventory.get(Item.stringToEnum(thischoice)) < orderThreshold) {
					mRole.mItemsDesired.put(Item.stringToEnum(thischoice), 5);
				}
				cookGui.setLabelText("");
				cookGui.removeFood();
				cookGui.DoGoToHome();
				try {
					isAnimating.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isAnimating.availablePermits() == 0) {
					isAnimating.release();
				}
			}
		}, cooktime);

	}

	private void Notify(Order order) {
		print("Notifying");
		order.waiter.msgOrderIsReady(order);
		pendingOrders.remove(order);
	}

	private void CheckStand() {
		cookGui.setLabelText("Checking Revolving Stand");
		cookGui.DoGoToPlating();
		try {
			isAnimating.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (revolvingStand) {
			synchronized (pendingOrders) {
				Iterator<Order> itr = revolvingStand.iterator();
				while (itr.hasNext()) {
					Order order = itr.next();
					pendingOrders.add(order);
					itr.remove();
				}
			}
		}
		cookGui.setLabelText("");
		cookGui.DoGoToHome();
		try {
			isAnimating.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Order> getRevolvingStand() {
		return revolvingStand;
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
}