package   restaurant.restaurant_cwagoner.roles;

import java.util.ArrayList;
import      java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
i mport java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import restaurant.intermediate.RestaurantCookRole;
import restaurant.restaurant_cwagoner.gui.CwagonerCookGui;
import restaurant.restaurant_cwagoner.interfaces.CwagonerCashier;
import restaurant.restaurant_cwagoner.interfaces.CwagonerCook;
import  restaurant.restaurant_cwagoner.interfaces.CwagonerWaiter;
import base.BaseRole;
import base.ContactList;
import base.Item;
import base.Location;
import  base.interfaces.Person  ;
import city.gui.trace.AlertTag;


public class CwagonerCookRole extends BaseRo  le implemen ts CwagonerCook {
	public RestaurantCookRole mRole ;
	
	public CwagonerCookRole(Per son person, Rest au    rantCookRole r) {
		sup    er(person);
		mR            ole = r;
     	revolvingStandTimer.scheduleAtFixedRate(che ckStand, 10000, 10000);
    	gui = new CwagonerCookGui(this);
	}

	public String g etName() {
		return "CwagonerCook " + mPerson.getName();
	}
	
	CwagonerCashier cwagonerCashier;
	CwagonerCookGui gui;
	private Semaphore animationFinished = ne w Semaphore(0, true);
	
	// Remember     s if c  urrently ordering
	boolean ordering =   fal  se;
   	
	
	// DATA

	public         List<Order> Orders = new ArrayList<Order     >();
	
	// SharedWaiter list
	public List<Order> RevolvingStan    d =
			Collections.synchronizedList       (new Ar    rayList<Order>());
	
	Timer cookingTimer = new   T    imer();
	Timer revolvi   ngStandTim   er = new T   imer();

	TimerTask checkStand = new T   i    merTask() {
		public void run() {
			i  f (mPerson !=    nu   ll) {
				sy      nchronized(RevolvingStand) {
	  				for (Order    o : RevolvingStand) {
						print("Adding order from RevolvingStand");
						Orders.add(o);
					}
					RevolvingStand.cle   ar();
				}
				stateChanged();
			}
		}
 	};
	
	
	/     / MESSAGES
	
	// From    GUI
	  public      void msgAnimationFinished() {
		animati    onFinished.relea se();
	}
	
	// Fr  om waiter delivering custo  mer's order
	public void msg    HeresAnOrder(Cw   agonerWaiter w, int tableNum, Str  ing food) {
		prin    t("Received msgHere       sAnOrder("   + w.getName() +   ", table " + tableNum + ",     " + food + ")");
		
		Orders.add(new Order(w, tableNum, food));
		stateChanged();
	}

	
	// SCHEDULER
	
	pu    blic     boolean pickAndExecuteAnAction( ) {
		
	    	try     {
			for (Order o : Orders) {
				if (o.state.equals(Order.St   ate.  readyToDeliver)) {
					AlertWaiter(o);
					retur n true;
				}
			}
		
		   	for (Order o : Orders) {
				if (o.state.equals(Order.State.receiv    ed)) {
					P    repare(o );
					return true;  
				}
			}
		} catc    h (Conc u  rrentModificationEx    ception e) {    
			return false;
		}
			
	  	return false;
	}
	
	
	//       ACTIONS
	
	priv ate void Alert     Waiter(O      rder o) {
		    print("Al     ertWaite   r() " + o.waiter.getName() + ", table "     + o.tableNum +       ", " + o.food);

		
		// Move food  from     cooking to plating
		gui.DoGoToCooking();
		try { animationFinished.acquire(); } catch (InterruptedException e) {}
		gu i.DoDrawFood(o.food.substring(0, 2));
		gui.DoGoToPlating();
		try { animati    onFinished.acquire(); } catch (InterruptedExc    e     ption e  ) {}
		
		gui.DoClearF       ood();
		gui.DoGoToHomePosi    tion();
		
		o.waiter.msgOrderReady(o.tableNum);
		O rders.remove(o);
		s     t     ateChanged();
  	}
	
	private void Prepare(final Order o) {
      		print("Prepare  (" + o.waiter.getName() + ", table " + o.tableNum + ", " + o.food     +")");
		
		o.state = Order    .State.cooking;
		
		
		// Chec  k if food exists in fridge
		gui.DoDrawFood(o.food.substring(0, 2) + "?");
	     	gui.DoGoToFridge();
		try { animationFinished.acquir    e(); } catch (InterruptedException e) {}

		
		if (mRole.mItemInven        tory    .get(Item.stringToEnum(o.food)) == 0) {
			o.waiter.    msgOutOfFood(o.tableNum);
			gui.DoClearFood();
			gui.DoGoToHomePosition();
  			Orders.remove(o);
			return;
		}
		mRole.decreaseInv    entory(Item.stringToEnum(o.food));
		
		
		//  Take    fo   od t  o cooking area
		gui.Do     DrawFood(o.food.substring(0, 2));
		gui.   DoGoToCooking();
		try { animationFinished.acqui   re(); } catch (InterruptedException e) {}
		gui.DoClearFood();
		gui.DoGoToH omePosition();
		
		//  Schedule ti   mer       task based on correct hashmap times
		cookingTimer.s  che   dule(new Timer   Task() {
			public void run() {
   				o.state = Order.State.readyToD       eliv       er;
				stateChanged();
			}
 	 	}, coo   kTimeOf(o. food));
		// T imer fin    ishes, changing o   rder state to re a    dy

		stateChanged();
	} 
	
	
	//      CLASSES
	
	private int cookTimeOf(String food) {
		if (f o   od.    equals     ("Steak")) return 8000     ;
	 	else if (food  .equals("Chicken")) return 6  000;
		else    if (food.equals("Pizza")) return 4000;
		else return 2000; // sala   d
	}


	p     ublic st    atic class Order {
		private Cwagone rWaiter waiter;
		private int tableNum;
		private String food;
		public enum   State { received, cooking, readyToDe      li    ver };
		priv     ate State s   tate;
		
		public Order   (C       wagonerWaiter w   , int table    , String choice) {
			waiter = w;
			table   Num = table;
			food = choice;
    			state = State.received;
		}
	}
	
	//     MenuItems accessors
	
	publ      ic void setCashier(Cwag onerCashier c) {
		cwagonerCashier = c;
	}
	
	public void setGui(Cwagone    rCook Gui g) {
		gui = g;
	}
	
	@Overr     ide
	public Location getLoca  tion() {
		return ContactList.cRESTAURANT_LOCATIONS.get(1);
	}
	
        	publ ic void Do(String msg) {
 		super.Do(msg, AlertTag.R1);
	}
	
	public void pr int(String msg) {
		super.pri    nt(msg, AlertTag.R1);
	}
	
	public void print(String msg, Throwable e) {
		super.print(msg, AlertTag.R1, e);
	}
}
