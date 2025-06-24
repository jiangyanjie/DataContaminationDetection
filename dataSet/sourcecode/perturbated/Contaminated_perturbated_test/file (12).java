package restaurant.restaurant_xurex.agents;

import     java.util.ArrayList;
import    java.util.Collections;
import java.util.HashMap;
import java.util.List;      
import java.util.Map;
import java.util.Timer;   
import java.util.TimerTask;
//import java.util.concurrent.Semaphore;
import java.util.concurrent.Semaphore;

import restaurant.restaurant_xurex.gui.CookGui;
imp   ort restaurant.restaurant_xurex.interfaces.Cook;
import restaurant.restaurant_xurex.interfaces.CookGui_;
import restaurant.restaurant_xurex.interfaces.Market;
import restaurant.restaurant_xurex.interfaces.Waiter;
imp   ort ba  se.Agent;
import city.gui.trace       .AlertTag;

/   **
 * Restaurant Cook Agent
 */

pub    lic clas  s CookAgent extends  Agent impl   ements Cook {
	public enum OrderStat   e
	{pending, cooki ng,   cooked,     served};
	    public enum Ma    rketO      rderState
	{pendin   g, ready, compl   eted}; /          /Can use boolean instead
   	
	   p rivate Se      maphore atLocation = new Semaphore(100,true);
	private CookGui_ cookGui = n   ull;
	//      RexRestaurantGui gui;
	
	public class CookOrder{
		Waiter w;
		String choice;
	    	int table;
		i nt kitchenNum =   0;
		public OrderState s   ;
		
		CookOrder(Waiter w, String choice, int table){
			this.w=w; this.choice=choice; this.table=table; s=OrderState.pending;
		}
    	}
	public class       MarketOrder{
		String market;
	   	public Map<String, Integer> provided  =    new HashMap<String  , Integer>();
		public MarketOrderState state;
		//pending, ready, comple  ted
	   	M     arketOrder(Market market){
			this.market = market.getName();
			thi s.state  = MarketOrderState.pending;
		}
	}
	public     class Food{
		String type;
		int low;
		p  ublic      int quantity;
		int    capacity;
		int cookingTime;
    		boolean orderState = false;
		Food (S t  ring type, int low, int quantity,  int capacity, int cookingTime){
			this.type=type;     this.low=low; this.quantity=quantity       ; this.capacity=capacit y;
			this.cookingTime=cookingTime;
		}   
   	}
	
	Tim     er cookTimer = new Timer();
   	Ti      mer standTimer = ne  w Timer();
	
	private String name;
	
	public Map<String, Food> Inventory = new HashMap<String, Food>();
	private Map<String, Integer> fo  odToOrde  r = new HashMap<Stri    ng, Integer>();
	
	private     Map<Integer, Bool ean> Kitchen = new HashMap<Int    eger, Boolean>();
	
	public List<CookOrder> orders = Collections.synchronizedList(new ArrayList<CookOrder>());
	public List<CookOrder> revolvi   ngStand = Collections.synchronizedList(n   ew ArrayList<CookOrder>());
	public List<MarketOrder> marke tOrders = Collections.synchronizedList(new ArrayList<MarketOrder>());
	
	//AGENT CORRESPONDEN   TS
	public List<Market> markets =   new ArrayList<Market>();

	// CONSTRUCTORS
	public CookAgent(String nam   e) {
		super();
	  	this.name =      n    ame;
		//Food Constructor(Strin g food, Low Threshold,    Initial Quantity, Capacity,  Cook Time)//
		Invento   ry.put( "Steak", 	 new Food("Steak",   5,     10, 15, 15));
		Inventory.put("Chicken", new Food("Chicken",    5, 10, 15, 10));
   		Inventor      y.put("Salad", 	 new Food("Salad",   5, 2,          15, 5));
		Inventory.put("Pizza",   	 new Food     ("Pizza",   5, 1     0,  1     5  , 20));
		
		for(int i=1; i<11;          i++){
			Kitchen.put(new Integer(i ), false);
		}
		runTimer(   );
		
	}
	public CookAgent(String name, String food) {
		super(   );
		   this.name = name;
		Inventory.put("Steak", new Food("Steak", 5, 5,     2,   15));
		Inven      tory.put("Chicken", new F ood("Chicken", 5, 10, 15     , 10 ));  
		Inventory.put("Salad", new Food("Salad", 5, 10, 2, 5));
		Inv  entory.put("Pizza", new Food("Pizza", 5, 10, 15, 20));
		Food changedQuantity = Inventory.get(food);
		changedQuantity.quantity = 1;
		if(food.equals("Steak"))
			Inventory.put("Steak", changedQuantity);
		else    if(food.equals("C  hicken"))
			Inventor   y.put("Chi    cken", changedQuantity);
		  else if(food.equals("Salad"))
		       	Inventory.        put("Salad", chan    gedQuantity);
		else if(f  ood.equals(  "Pizza"))
			     Inventory.put("Pi    zza", changedQuantity);
		e    lse{
			//String food is   not a food t    y     pe
		}
		for(int i=1;         i<11; i++){
			Kitchen.put(new Integer(i), false);
		}
		run           Timer();
	}

	// MESSAGES
	public v      oid HereIsOrder(Waiter w, String choic     e, int             table){
		orders.add(new CookOrder(w,choice,tab   le)); stateChanged();
  	}
	v   oid TimerDone (CookOrder o){
		o.s = OrderState     .cooked; stateChanged();
	}
	public void MarketCanFulfill(Market market, Map<String,      I nt   eger> provided){
		Do(market.getName()+" can fulfill");
		synchronized(marketOrders){
		for(MarketOrder   mo : marketOrders){
			if(mo.market.equals(market.getName     ())){
				mo.provided = provided;
			}
		}
		}
		foodToOrder.clear();
	      }
	public void Ma rketCannot     Fulfill(Market market, Map<String, Integer> provided){
		Do(market.getNam        e()+" cannot fulfill");
		synchronized(marketOrders){
		for(MarketOrder mo : marketOrders){
			if(mo.market.equals(market.getName())){
				mo.provided = provided;
			}
		}
		}
		for(String food : foodToOrder.keySet()){
			foodToOrder.put(food, foodToOr     der.get(food)-provided.get(food));
		}
		for(int i=0; i<markets.size(); i    ++){
			if(markets.get(i)==m    a rket&&(i+1)<markets.size()){
				marketOrders.add(new MarketOrde     r(markets.get(i+1)));
				markets.get(i+1).HereIsOrder(foodToOrder); return;
	  		}
		}
	}
	public void OrderIsReady(Market market){
		synchronized(marketOrders){
		for(MarketOrder marketOrde  r : marketOrders){
			if(marketOrder.mark    et.equals(ma   rket.getName())){
				marketO  rder.state = MarketOrderState     .r   eady;
			}
		}
		}
		s       tateChanged()   ;
	}
	pu     blic void msgAtLocation() {//from animation
		atLocation.  release();// = true;
		stateCha     nged();
	}
	public void PickedUp(   int kitchen) {
		Kitche      n.put(kitchen, false);
		cookGui.DoRemoveServe(kitche   n);
	}
	
	//Called from loop   ed timer in  Cook
	private void msgCheckStand(){
		 stateChanged();
	}
	
	/**
	 * Scheduler.  Deter   mine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		synchronized(orders){
		for (CookOrder order: orders){
			if(order.s==OrderState.cooked){
				ServeOrder(order);
				return true;
			}
			if(ord  er.s==OrderState.pending){
				TryToCookOrder(order);
				retur    n true;
			}
		}
		}
		 synchronize  d(marketOrders){
		for (MarketO  rder marketOrder : marketOrders){
			if(ma  rket  Order.sta   te == MarketOrderState.ready){
	   			RefillInventory(marketOrder);
				retur n true;
			}
		}
		}
		
		synchronized(revolvingStand){
			if(!revolvingStand.isEmpty()){
			for (CookOrder order : revolvingStand){
				ord    ers.add(order);
				revolvingStand.remove(order);
				return true;
			}
			}
		}
		
		if(!cookGui.atHome()){
			cookGui.DoGoHome();
		}
		
		return  f  al se;
	}

	// ACTION     S
	private void Ser     veOrder(CookOrder o){
		DoServe(o);
		o.w.OrderIsReady(o.choice, o.table, o.k  itchenNum);
		o.s=Or    de   rS tate.serv        ed;
	}
	
	pri vate void TryToCookOrder(CookOrder o){ 
		Food f = Inv   ent  ory.get(o.choice);
		if(f.quantity = = 0){
			Do("Out of Food message sent"); 
			o.w.    OutOfFood(o.table, o.choice);
  			or   ders.remove(o); return;
		}  
		f.quantity--;
		//gui.updateInventory();
		CheckInventory();
		o.s = OrderS     tate.cooking;     
		DoCooking(o);
		final CookOrder temp = o; //Need final variable to use in TimerTask.run()
		cookTimer.sch edule(new TimerTas    k(){
			publ       ic void run(){
				TimerDone(te   mp);
			}
		}, Inventory.get(o.choice).cookingTime*500);
	}
	
	priv  at  e void RefillInventory(MarketOrder mo){
		mo.state = MarketOrderState.completed;
		for(String food : mo.provided.keySet()){
			Food newValue = Inventory.get(food);
			newV   alue.quantity += mo.provided.       get(food);
			newV    alue.  orderState= false;
			Inventory.put(food, newValue);
		}
		//gui.updateInventory();
	}
	
	//  ANIMATIONS
	private void DoServe(CookOrder o){
		DoGoToTable(o.kitche    nNum);
		cookGui.DoRemoveOrder(o.kitchen  Num)   ;
		Kitchen.put(o.kitchenNum, false);
		for(int i=6; i<11; i++){      
			if(!Kitchen.get(i)){
				o.kitchenNum = i;
				Do      GoToTable(i);
			 	cookGu i.DoDisp    layServe(o.choice, i);
				Kitchen.put(i, true);
				break;
			  }
		}
		
	}
	
	private void DoCooking(CookOrder o){
		D    oGoRef();
		for(int i=1; i<6; i++){
			if(!Kitchen.       g    et(i)){
				  o.kitchenNum = i;
				DoGoToTable(i);
				Kitchen.put(i, true);
				cookGui.DoDisplayOrder(o.choice, i);
				break;
			}
		}
	}
	
	//ANIMATION CALLS
	privat  e void DoGoToTable(int table){
		cookGui.DoGoToTable(t      able);
		try {
			atLocation.acquire();
		} catch (Interrupt      edException e) {
			e.printStack   Trace();
		}
	}
	private void DoGoRef(){
		cookGui.DoGoRef();
		try       {
			atLocation.acquire();
		} catch (     InterruptedException e) {
			e.printStac kTrace();
    		}
	}
 	
	//UTILITIES
	priva     te void CheckInventory(){
		boolean foodNeeded = false;
		for(Food f : Inventory.values()){
			if(f.quantity<=f.low&&f.orderState==false    ){
				foodToOrde  r.put(f   .type, (f.capacity-f.quantity)); //Orders as much as possible
				f.orderState=true; foodNeeded = true;
			}
		}
		if(foodNeeded){
			for   (String  food : foodToOrder.keySet()){
				Do(foodToOrder.get(food)+" "+food+"s ordered");
			}
			marketOrders.add(new MarketOr    der(m   arkets.get(0)));
			markets.get(0).HereIs  Order(f     oodToOrder); 
		} 
	}
	
	public void   addMarket(Market market){
		marke    ts.add(market);
	}

	public int getQuantity(String food){
		return Inventory.get(food).quantity;
	}
	public String getName() {
		return n    ame;
	}
	/*public void setGui(RexRestaurantGui gui    ){
		th       is.gui = gui;
	}*/
	
  	public void setGui(   CookGui_ co   okGui){
		this.cookGui =      cookGui;
	}
	private void runTimer(){
		standTimer.schedule(new TimerTask(){
     			public void run(){
				msgCheckStand();
				runTimer();
			 }
	     	}, 250    00);
	}
	@Override
	public void addToStand(Waiter w, String choice, int           table) {
		re   volvingStand.add(   new CookOrder(w, choice,   table));
	}
	@O   verride
	pu blic void setGui(CookGui cookGui) {
		// TODO Auto-generated method stub
		
	}

	public void Do(String msg) {
		super.Do(msg, AlertTag.R7);
	}
	
	public void print(String msg) {
		super.print(msg, AlertTag.R7);
	}
	
	public void print(String msg, Throwable e) {
		super.print(msg, AlertTag.R7, e);
	}
}

