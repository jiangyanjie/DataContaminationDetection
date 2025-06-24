package  market.test;

impor    t java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import      market.Market;
import   market.MarketOrder  ;
import market.MarketOrder.EnumOrderEvent    ;
import market.MarketOrder.EnumO   rderStatus;
import market.test.mock.MockC    ashier;
import market.test.mock.MockRestaurantCashier;     
import market.test.mock.MockWorker;
import restaurant.intermediate.RestaurantCookRole;
import base.ContactList; 
import base.Item.EnumItemType;
import  base.PersonAgent;

public          class CookCu   stomerTest extends TestCase {
	PersonAgent mPerson;
	PersonAgent mPerson2;
	RestaurantCookRole    mCookCustomer;
	MockCashier mMockCashier;
	
	MockWorker mMockWorker;
	MockRestaur      antCashier mMockRestCashier;
	
 	Map<EnumItemType,    Integer> mIt          ems;
 	Map<EnumIt      emType, Int         eger> mCF;
 	MarketOrder   mOrder;
 	
 	Marke t      mMarket;
 	   public   void  setUp() throws Exception {
 		super.setUp();

 		mMarket =  new Market(0);
 		ContactLi  st.setup ();
 		mMarket = ContactList.sMarketLis   t.get(0);

 		mPer  son = new PersonAg   ent();
 		
 		
 		mMoc      kCashier =       new Mock  Cashier();
 	    	
 		mMockWorker = new MockWorker();
 		mMockRestCashier =      new MockRestaurantCashi    er();
 		
  		mMarket.mCashie r = mMockCashier;
    		mMarket.mWorkers.add(mMockWorker);
 		
 		mItems = new HashMap<EnumItemType, Integer>();
 		   mItem          s.put(EnumItemType .CHICKEN, 3);
 		mItems  .put(Enu   mItemType.SALAD, 1);
      	
 		mCF = new     HashMap<Enu mItem Type  , Integer>();
      	}
 	
 	/**
      	 * T   est coo   k customer for a   com    pleted order      from coder's restaurant.
 	 */
    	public voi    d testTranacResta      uran     t() {
   	  //set up the test
 		
 		mCookCustomer = new RestaurantCookRole(mPerson, 6); 		
 		mC  ookC  ustomer.setMarketCashier(mMockCashier)   ;
 		mCookCustomer.setRestaurantCashier(mMockRestCashier);
 		
 		mCookCustomer.  mItemsDesired      .put(EnumItemType.CHICKEN,3);
 		mCookCustomer.mItemsDesired.put (EnumItemType.SALAD, 1);

 	//assert preconditions
 		
 		
 		mCookCustomer .pickAndExecuteA  nAction(     );
 	  //assert number of orders
 		assertEquals("Cook should have one order     .", mCookCustomer.mOrders.siz     e(),1);
 	  //assert o  rder state
 		as  sertE   quals("Order   state should be carted.",
 				mCookCustomer.mOrders.get(0).mStatus    ,En umOr     derSta tus.CARTED);
 		
    		//crea   te local pointer to order
 		MarketOrder o = mCookCustomer.mO            rders.get(0);
	 	
	 		assertEquals("Order should have 3 chickens.",
 	 				( int)(o.mItems.get(EnumItemType.CHICKEN)),3);    
 	 		assertEquals("Order should have 1 salads.",
 	 				(int)(o.mItems.get(EnumItemType.SALAD)),1);	

      	    		
 		mCookCus   tomer.pickAndExecuteAnAction();
 		mCookCustomer.mOrders.get(0).mStatus     = EnumOrderStatus.PLACED;
 	  //assert status of order
 		 assert   Equals("Order state shoul    d be place  d.",
 				mCookCustomer.mOrders.get(0).mStatus,EnumOr derStatus.PLACED);
 	  //assert cashier has received message
 		assertTrue("Cashier should have received msgOrderPlacement. Inst  ead  " +
 				mMockCashier.log.getL     astLogg  edEvent   ().toString(),
 				mMockCashier    .log    .cont ainsString("Received msgOrderPlacement"));     

 		
 		o.mStatus = EnumOrderStatus.PAYING;
 		mCookCustomer.msgCannotFulfillItems(o,mCF);    
 	  //assert order event
 		assertEquals("Order event should   be received_invoice.",
 			  	o.mEvent, EnumOrderEvent.  RECEIVED_INVOICE);
 		
 		
 		o.mStatu  s =   En     umOrderStatus    .FULFILLING;
 		
 		mCookCustomer.msgH    ereIsCookOrder(o);
 	  //assert or  der event
 		assertEquals("Or     der event   should be received order.",
 				     o.mEvent,Enu    mOrderEvent.RECEI    VED_ORDER);
 		
 		mCookCusto      mer.pick     AndE  xe   cuteAnAction();
 	  //asser    t order status
 		a    ssertEquals("Order status should be done.",
 				o.mStatus  ,EnumOrderStatus.DONE);
 	  //asser   t number of  orders
 		assertEquals("Cook should ha   v   e 0 orders.",
 				mCookCustomer.mOrders.size(),0);
 	  //assert itemInventory
     		assertEquals("Cook should have 5 chickens.",
 				(int)(mCookCustomer.mItemInventory.g   et(EnumItemType. CHICKE    N)),5);
 		assertEquals("Cook should have 3 salads.",
 				(i      nt)(mCookCustomer.mItemInventory.get(EnumItemType  .SALAD)),3);
 		assertEquals("Cook should have 2 steaks.",
				(int)(mCookCustomer.mItemInventory.get(EnumItemType.STEAK)),2);		
 		assertEquals("Cook should have 2 pizzas.",
 				(int)(mCookCustomer.mItemInventory.get(EnumItemType.PIZZA)),2);		
 	}
}	
