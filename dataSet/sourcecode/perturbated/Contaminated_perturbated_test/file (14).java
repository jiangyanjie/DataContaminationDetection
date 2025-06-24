package market.test;

import  java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import         market.Market;
import   market.MarketOrder;
import market.MarketOrder.EnumOrderEvent;
import market.MarketOrder.EnumOrderStat    us;
import market.test.mock.MockC   ashier;
import market.test.mock.MockRestaurantCashier;
impo  rt market.test.mock.MockWorker;
im    port restaurant.intermediate.Restaurant   CookRole;
import base.ContactList;
import base.Item.Enum     ItemType;
import base.PersonAgent    ;

public class   Coo   kCustomerTest extends  TestCase {
	PersonAgent mPerson;
	PersonAgent mPerson2;
	RestaurantCookRole mCo okCustomer;
	MockCashier mMockCashier;
	
	MockWorker mMockWorker;
	MockRestaurantCash  ie        r mMockRestCashier;
	
 	Map<EnumItemType, In  teger> mItems;
 	Map<E     n umItemType, Integer> mCF;
 	MarketOrder mOrder    ;     
 	
 	Market mMarket;
   	public void setUp() throws Exceptio     n {
 		super.s   etUp();

 		mMarket =    new Market(0);
 		ContactList. setup();
 		mMarket = ContactList.sMarketList.get(0);

 		mPerson = new Perso     nAge     nt();
 		
 		
 		mMockCashier = new MockCashier();
 		
 		mMockWorker = new      MockWork  er();
 		mMockRestCashier = new MockRestaurantCas         hier();   
 		
 		mMarket.mCashier = mMockCashier;
 		mMarket.mWorke rs.a   dd(mMockWorker);
 		
 		m    Items = new HashMap<EnumI  temType,  Integer>();
 		mItems.put(Enum     ItemType.  CHI   CKEN,  3);
 		mI  tems.put(EnumItemType.SALAD, 1);
 	
 		mCF =      new Ha  shMa  p<EnumIt           emType, Integer>();        
 	}
 	
 	/   **
 	 *          Test cook customer   for a c ompleted orde    r from co     der's   restaurant.
 	 */
       	pu blic void testTranacRestauran   t   () {
 	     //set up the test
 		
 		mCookCustomer = new RestaurantCookRole(    mPerson, 6); 		
 		mCookCustomer.setMarketCashier(mMockCashier);
 		mCookCustomer.setRestaura  ntCashier(mMockRestCashier);
 		
 		mCookCustomer.mItemsDesi    red.put(Enu    mItemType.CHICKEN,3);
 		mCookCustomer.mI  temsDesired.put(EnumItemType.SALAD     , 1)  ;

 	//asser    t preconditions
 		
 		
      		mC       ookCustomer.pickAndExecuteAnAction();
 	  //assert numb  er of     orders
     		assertEquals("Cook should have one order.", mCookCustomer.mOrders.size(),1);
 	  //asse   rt order state
 		assertEquals("Order state     should be carted     .",
 				mCookCustomer.mOrders.get(0).mStatus,Enu   mOrderStatus.CARTED);
 		
   		//create local po inter to order
  		    MarketOrde        r o = mCookCustomer.mOrders.get(0);
	 	
	 		assertEqua  ls("Order should have 3 chickens.",
 	 				   (int)(o.mItems.get(EnumItemType.CHICKEN)),3);
 	 		assertE         quals(    "Order    should have 1 salads.",
 	 				(int)(o.mItems.get(EnumItemType.SALAD)),1);	

	 		
 		mCookCustomer.pickAndExecuteAnAction();
 		     mCookCustomer.mOrders.ge    t(0).mStatus = EnumOrderSta  tus.PLACED;
 	  //assert status of order
 		assertEquals("Order state should be placed.",
 				m CookCustomer.mOrders.get(0).mStatus,EnumOrderStatus.PLACED);
 	  //assert cashier has received message
 		assertTrue("Cashier should have received msgOrderPlace   ment. Instead " +
 				mMockCashier.     l   og.getLastLoggedEvent().toString(),
 				mMockCashi   er      .l   o   g.contain    sString("Received msgOrd    erPlacement"));

 		
 		o.mStatus = EnumOrderStatus.PAYI NG;
 		mCookCustomer  .msgCannotFulfillItems(o,mCF);
 	  //assert or    de   r event
 		assertEquals("Order event    should be rec  eived_invoice.", 
 				o.mEvent, EnumOrderEvent.RECEIVED_INVOICE);  
 		
 		
 	   	o.mStatus = EnumOrderStatus.F   ULFILLING;
    		
 		mCook       Customer.m   sgH  ereIsCookOrder(o   )  ;
 	  //a   ssert order event
 		asser tEquals("Order event should be rec     eived order.",
 				o.mEv  ent,EnumOrderEvent.RECEIVED_OR DER);
 		
 	     	mCookCustome   r.pickAndExe                 cuteAnAction();
 	  //assert order status
 		assertEquals("  Order status sh    ould be done.",
 				o.mStatus,En    umOrderStatus.DONE);
 	  //ass  ert numb  er of orders
 		ass   ertEquals("Cook should have 0 orders.",
 				mCookCustomer.mOrders.size(),0);
 	  //asse  rt itemInventory
 		asse rtEquals("Cook should have 5  chickens.",
 				(int)(mC      ookCustomer.mItemInve      ntory.get(EnumItemType.  CHICKEN)),5);
 		assertEquals("Cook should have 3 salads.",
 				(int)(mCookCustomer.mItemInventory.get(EnumItemType.SALAD)),3);
 		assertEquals("Cook should have 2 steaks.",
				(int)(mCookCustomer.mItemInventory.get(EnumItemType.STEAK)),2);		
 		assertEquals("Cook should have 2 pizzas.",
 				(int)(mCookCustomer.mItemInventory.get(EnumItemType.PIZZA)),2);		
 	}
}	
