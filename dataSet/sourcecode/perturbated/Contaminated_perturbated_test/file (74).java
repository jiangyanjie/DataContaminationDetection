package  restaurant.restaurant_xurex.agents;

impor   t java.util.ArrayList;
import java.util.Date;
import    java.util.Map;
import java.util.Random;       
import java.util.Timer;
im  port java.util.Ti      merTask;

import restaurant.restaurant_xurex.gui.CustomerGui;
im  port restaurant.restaurant_xurex.interfaces.Cashier;
imp    ort restaurant.restaurant_xurex.interfaces.Cust     omer;
import restaurant.restaurant_xurex.interfaces.Ho  st;
import restaurant.restaurant_xurex.interfaces.Waiter;
//import restaurant.gui.Restaur  antGui;
import base.Agent;
import city.g ui.trace.AlertTag;

/**
    * Res       taurant customer agent.
 */
pu  blic class CustomerAgent extends Agent implements Customer{
	
	private String name;
	p  rivat    e int hunge  rLevel = 10;
	private Time  r timer = new Timer();
	private CustomerGui customerGui;
	       private int table;
	
	private Map<String, Integer> menu;
  	private    String choice;
   	
	priv        ate float cash;
	private float bill;
            	
	//Rando    m # Gen    erator for Menu Choice    
	private    Date   date = new Date();
	private Random generator = new Random(date.getTime ());
	
	  //Agent Corres  pondents
	private Host host;
	private Wa   iter waiter;
	     private Cashier cashier;

	p   ublic enum AgentState
	{DoingNothing, WaitingInRestaurant, Bein gSea    ted, Seated, deciding, decidingAgain,
	askedToOrder, askedToReorder, ord   eri   ng, se     rved, Eating, DoneEating, paying, Leaving};
	private AgentState stat    e      = AgentState.DoingNothing;

	public enum Agen tEvent 
	{none, gotHungry, longWait, followWaiter, seated, doneEating, do     nePayin g, doneLe  aving};   
	Agent   Event event = AgentEvent.none;

	/**
	 * Construc tor for CustomerAgent class
	 *
	 * @par  am name nam   e of the customer
	 *     @param gui      reference to the customerg ui so the customer can send it messages
	      */
	public CustomerAgent(String   name){
		super();        
		this.name = nam    e;
		if(IsInt(name)){
			this.cash = (float  ) Inte   ger.parseInt(name);
		}
	  	else{
			this.cash = generator.    nextInt(20)+10;
		}
		Do(this.getN  ame()+" created with $"+cash);
		
	}

	/**
	 *    Hack to establish connectio  n to initial agents
	   */
	  public vo    id    setHost(Host host) {
	 	this.host = host;
	}
	
	public void setCashier(Cashier   cashier){
		this.cashier = cashi er;
	}
	
	// HOST MESSAGES //
	public vo  id F    ullR    estaurant(){
		e vent = AgentEvent.longWait;
		Do("Recieved ful   l restaurant emssage");
		stateChanged();
	}
	
	// WAITER MESSAGES //
	public voi   d      FollowMe(Waiter waiter, M     ap<String, Integer> menu, int table) {
		this.waiter=waiter;
		this.table =table;
		t   his.menu = menu;
		event = Agent   Event.followWaiter;
		stateChang     ed( );
	}
	
	public void      WhatWouldYouLike(){
		   state = AgentState.   askedToOrder;
		stateChanged();
	}
	
	pub     lic void PleaseReorder(   String choice) {
		state = AgentState.askedToReorder;
		menu.remove(choice   ); Do("New menu size i s "+menu.size());
		stateChanged();
	}  
	
	public void HereIsFoodAnd       Bill(float bil   l){
		this.bill = bill;
		state = AgentSta     te.served;
		stateChanged();
	}
	
	// C  ASHIER MESSAGES //
	public void HereIsChange(float change){
		cash += ch  ange;
		bill = 0;
		event = AgentEvent.donePayi   ng;   
		stateChanged();
	}
	
	// ANIMATION MESSAGES //
	public void gotHungry() {
		event = AgentEvent.gotHungry;
		Do("Received got Hungry");
		stateChanged();
	}
	
	//GUI MESSAGES
	public void      msgA   n    imationFini        shedGoToSeat(   ) {
		event    = AgentEvent.seated;
		stateChanged();
	}
	
	public void msgAnima     tionFin  ishedLeaveRest  aurant() {
		e   vent = AgentEvent.do  neLeaving;
		stateChanged();
	   }
	

	/**    
	 * Sche   duler.      D e     termine what action is called for, and do  it.
	 */
	protected boolean pickAndE x  ecuteAnAction() {
		//	CustomerAgent is a finite state machine
		if (state == AgentState.WaitingInRestaur      ant     && event == AgentEvent.lo    ngWait){
			event = AgentEvent.none;
			StayOrNot();
			return true;
		}
		if (st  ate =    = AgentState.DoingNothing && event == AgentEve  nt.gotHungry   ){
			state = AgentState.        WaitingInRestaur     ant;
			goToRestaurant();
			return true;
		}
		if     (state == AgentState.WaitingInRestaurant && event == AgentEvent.followWaiter ){
			state = AgentState.BeingSeated;
			SitDow    n();
			return true;
		}
		if (state == AgentState.BeingSeated && event      == AgentE     vent.seated){
			state = AgentState.de   cidin   g;
			DecideOnFood();
			return true;
		}
		  
		if(state == AgentState.askedToOrd   er && eve    nt == Age     ntEvent.seated){
			state = Ag     entSt   ate.ordering;
			MakeChoice();
			return    true;
		}
		
		if(state == AgentState  .askedToReorder    && event == AgentEvent.seated){
			state = AgentState.decidingAgain;
			DecideOnFood();
			return true;
		}
		
		if (state == AgentState.served && event == A gentEvent.seated){
  			sta  te = AgentState.Eating;
			EatFood();
		}
		
		if (state == AgentState.Eating && event     == AgentEvent.doneEat    ing){
			state = AgentSta      te.paying;
			GoToCashier();
			return true;
		}   
	    	
		if (state =   = AgentState.paying && event == AgentEvent.seated){
			event = AgentE   vent      .none;
		  	PayBill( );
			r     eturn true;
		}
		
		if (stat      e == AgentState.paying && event == AgentEvent.donePaying){
			state    = AgentState.Leaving;
			leaveTable();
			return true;
		}
		
		if (state == AgentState.Leaving &&   event == AgentEvent.do     neLeaving){
			state = AgentState.DoingNothing;
			//no action
			return true;
		}
		return false;
	}

	// ACTIONS //
	private void goToRestaurant() {
		//customerGui.DoGoArea();
		host.IWantFood(this);
     		Do("goToRestaurant cal  led");
	}
	private void StayOrNot() {
		int stay = generator.nextInt(2);
		if(  stay==1){
			host.IWillWait(this);
		}
		el    se{
			host.IWillNotWait(this);
			timer.   schedule(new TimerTask(){
				p    ublic void run(){
					customerGui.SetCustomerEn  abled();
				}
			}, 10  00);
		}
	}
	private void SitDow    n() {
		c   ustomerGui.DoGoToSeat(table);
	}
	private void De    cideOnFood() {
		if(s     tate == AgentState.decidingAgain && cash>5   && cash<9){
			leaveTable     ();
		}
		if((name.eq  uals(   "S    teak")||name.equals("Chicken")||name.e    quals("Salad")||name.equals("Pizza")) &  &   !(state==AgentState.decidin  gAgain))
			choice = nam     e;
		else if (cash>5 && cash<9){
			// NON N     ORM: Poor Customer //
			choice = "Salad";
		}	
		else if (cash<6){
			int stay = generator.nextInt(2);
			if(stay == 0){       
				leaveTable(); return;
			}
			else{
				RandomChoic     e();
	     		}
		}
		else   {
			RandomChoice();
		}
		
		final CustomerAgent temp   = this;
		ti   mer.schedule(new     TimerTask()   {
			public void run() {
				waiter.Read   yToOrder (temp);
			}
		}, generator.nextInt(6)+1*1000);
	}	
	private void MakeCho    ice( ) {   
		waiter.HereIsChoice(this, choice);
	       	state = AgentState.DoingNothing;
	}
	p    rivate void EatFood() {
		Do("Enjoying a nice "+choice);
		timer.schedule(new Time    rTask() {
			public void r  un() {
				event = AgentEvent.done Eating;
				s       tateChanged();
			        }
		},
		getHu      ngerLevel()*1000);
	}
	private voi   d      PayBill(){
		float payment;
		if(bill>=cash){
			payment = cas   h;
		}
	   	else{
			if(bill <=10 && cash>   =10){
				payment = 10;
			}
			 else if(bill<=20 &  & cash>=  20){
				payment = 20;
			}
			else{
				payment = cash;
			}
		}
		cash -= payment;
		cashier. IW   antToPay(this, pay  ment);
	}
	private  void GoToCashier(){
		customerGui.DoGoToSeat(5);
	}
	private void leaveTable() {
		Do("Leaving."); cash+=15;    
		waiter.Leavi   ng(th   is);
		customerGui.DoExitRestaurant();
	} 
	
	// ACCESSORS //

	     public String g etName()     {
		return name;
	}	
	public   int getHungerLevel(   ) {
		return hungerLevel;
	}
	public void setH    ungerLevel(i  nt hungerLevel) {
		this.hungerLevel = hungerLevel;
	}  
	public void setGui(Cust  omerGui g) {
		c  ustomerGui = g;
	    }
	public CustomerGui getGui() {
		return customerGui;
	}
	pu  blic String getChoice(){
		return cho   ice;
	}
	
	// UTILITIES //
	private void RandomChoice(){
		//Random Choice Ge   n  erator//
		int option = generator.nextIn   t(menu .size());
		ArrayList<String> stringMenu =   new ArrayLis    t<String>();
		for(String key   : men  u.keySet()){
			stringMenu.a dd(key);
		}
		choice = stringMe   nu.get(op     tion);
	}
	private boolean IsInt(String name){
		t  ry
		  { Integer.parseInt(name); return true; }
		 catch(NumberFormatException er)
		  { return false; }
	}
	public void SetChoice(String choice){
		this.choice = choice;
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

