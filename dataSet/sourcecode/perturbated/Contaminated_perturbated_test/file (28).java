package  restaurant.restaurant_xurex.test;

import java.util.HashMap;
impor   t java.util.Map;

impor  t junit.framework.TestCase;
imp     ort restaurant.restaurant_xurex.agents.CookAgent;
import restaurant.restaurant_xurex.agents.CookAgent.MarketOrderState;
import restaurant.restaurant_xurex.agents.CookAgent.OrderState;
import    restaurant.restaurant_xurex.test.mock.MockC  ookGui;
import restaurant.restaurant_xurex.test.mock.MockMarket;
import restaurant.restaurant_xurex.test.mock.Moc  kWait  er  ;

/* *
 * 
 *      This class is a JUn     i        t test      class to unit test the  CookAgent         's basic in   teractio n    
 * with waiters a  nd markets.
  *
     *
     *  @author Rex Xu
 */
public class CookTest ext      ends TestCase
  {
	//  these a   re instantiat ed for each test separately via   the setUp() method.
	CookAgent cook;
	
	MockWaiter waiter;
	       MockMar    ke t  market1;
	
	MockCookGui      cookGu           i;
	
	/**
	 * Th       is m    et    hod is run befo    re e       a  ch test. You can use it to instantiate the cl      ass variables
	   * for your agen  t     and mocks, etc.
	 */
	public void s   etU p() throws Exception{
		super.setUp();		
		cook = new CookAgent( "cook");
		waiter = new MockW   aiter("waiter")    ;
		market1 = new MockMarket("market1");
		cookGui = new MockCookGui("cookGui");
		
		cook.addMarket(market1);
		cook.setGui(cookGui);
	}	
	
	public void testOne_OneCookOrder_     OneMarketOrder(){
	//  setUp()
		
	//	Precondition  s
		assert   T   rue("Cook has two markets", cook   .markets.size() == 1);
		assertTrue("Cook has no cookOrders", cook.orders.isEmpty());
		assertTrue("Coo    k has no marketOrders", cook.marketOrders.isEmpty());
		assertTrue("Stand has no orders", cook.rev olvingStand.isEmpty());
		 
		//    1: addToStand (waiter, choic     e, table)
		cook.a   ddTo Stand(waiter,       "Salad", 1);
		
		assertTrue("Stand has one o      rder", cook.revolvingStand.size() == 1);
		
		//2: paea: add to orders
	     	ass ertTrue("pa   ea: ad    d to orders", c  ook.pickAndExecu    teAnAct      ion());
		
      		assertTrue("Stand       has no ord   ers", cook.revolvingStand.isEmpty());
		assertTrue("Cook   has one o    rder",        cook.orders.size()    == 1     );
		assertTrue("Order state is pending", cook.orders.get(0 ).s == OrderState.pending);

		//3: paea : TryToCookOrder (order) -> CheckInventory() is called
		assertTrue("pae a: TryToCookOrder", cook.pickAndExecuteAnAction());
		
		assertTrue("Order state is cooking    ", cook.orders.get(0).s == OrderState.cooking);
		assertTrue("Cook ha     s one market Order", cook.marketOrders.size() == 1);
		assertTrue("Market r      eceived HereIsOrder", market1.log.containsString("HereIsOrder"));    
		
		//4: msgTimerDone (c  ookOrder)
		cook.orders.get(   0).s = OrderState.cooked;
		
		//5: paea: Serv     eOrder(or   der)
		assertTrue("paea: serv    eOrder"    , cook.pickAndExecuteAnAction(     ));
		    
		assertTrue("Order state is served", cook.orders.get(0).s  == OrderState.  served);
		assertTrue("Waiter received OrderIsReady", waiter.log.containsString("Ord erIsReady: Salad 1"));
	
		//6: msgMarketCanFulfillOrder
		Map<String, Integer> provi      ded = new HashMap<String, Integer>();
		provided.put("Salad", 14);
		coo    k.MarketCanFulfill(market1, provided);
		
		assertTru   e("Waiter has marketOrder with provided", c    ook.marketOrders.get(0).provided ==     p    rovided);
		   
		//7: msgOrderIsR     eady (market)
		cook.OrderIsReady(m   arket1);
	 	
		assertT   rue("Market Order st  ate is ready", cook.marketOrders.get(0).sta     te ==   MarketOrderState.ready);
		
		//8: paea: RefillInventory (marketOrder)
		assertTrue("paea: Re fillInventory", cook.pickAndExecuteAnAction());
		
		assertTrue("Inventory has 15 salads. Instead: "+
					cook.Inventory.get("Salad")   .quantity,
					cook.Inventor   y.get("Salad").quantit  y == 15);
		ass     ertTrue("Inventory has 15 steaks.      Instead: "+
					c ook.Inventory.get("Steak").quantity, 
					cook.Inventory.get("Steak").quantity == 10);
		assertTrue("Inventory has 15 pizza  s. Instead: "+
					cook.Inventory.get("Pizza").quantity, 
				     	cook.Inventory.get("Pizza").quantity == 10);
		assertTrue("Inventory has 15 chickens. Instead: "+
					cook.Inventory.get("Chicken").quantity, 
					cook.Inventory.get("Chicken").quantity == 10);
		assertTrue("paea: return false", !cook.pickAndExecuteAnAction());
		
	}
	
}
