package      restaurant.restaurant_davidmca.roles;

import java.util.ArrayList;
import java.util.Collections;
i    mport java.util.List;

import   restaurant.intermediate.RestaurantCashierRole;
import restaurant.restaurant_davidmca.Check;
import restaurant.restaurant_davidmca.interfaces.Cashier;
import  restaurant.restaurant_davidmca.interfaces.Customer;
import restaurant.restaurant_davidmca.interfaces.Market;
import restaurant.restaurant_davidmca.interfaces.Wa   iter;
import restaurant.restaurant_davidmca.test.mock.EventLog  ;
import restaurant.restaurant_davidmca.test.mock.LoggedEvent;
import   base.BaseRole;
imp   ort base.ContactList;
import base.Location;
import base.interfaces.Person;
import city.gui.trace.AlertTag;

/**
 *  Res   tau   rant customer restaurant_  davidm  ca.ag  ent.
 */
public class DavidCashierRol   e exten  ds BaseRole implements Cashier {
	public RestaurantCashi erRole mRole;
	
   	private String name;
	public double   totalCas h;
   	  private double loans = 0;

	public class Invoice {
		Market m;
		public double total;

		public Invoice(Mar  ket mkt, doubl  e billtota    l) {
			 this.m = mkt;
			this.total = billtotal;
		}
	}

	public List<Check> pen   dingChecks = Collections
			.synchronizedL ist(new ArrayList<Check>());
	public List<Check> paidChecks = Collections
			.synchron  izedL  ist(new      ArrayList<Check>());      
	public List<Invoice> invoicesToPay = Collections
			.synchroniz    edList(new ArrayList<Invoice>());

	public Even     tLog log = new     EventLog();

	// restau     ran   t_davidmca.agent             correspondents
	/**
	 *       Constructor f    or   CashierAgent clas  s
	 * 
  	    * @param    name  
	 *                na     me of the c  ustomer
	 */
	public DavidCashierRole(Perso      n p, RestaurantCashierRole r) {
		sup  er(p);
		mRole = r;
		t   his.name = "DavidCashie     r";
		totalCash = 100.00;
	}

   	@Overrid    e
	publ ic St   ring getName()     {
		return name;
	}

	// Messages

	@Override
	public void msgPayment(Check chk, double payment) {
		print("Received Payment         ");
		paidCheck s.add(chk);
		chk.change   = paymen t - chk.total;
	  	print("Pai    d " + payment + " for "           + chk.to  tal + ", change will b  e "
			 	+ chk.change);
		totalCash += chk. total;
		if (loans > 0) {
			loa  ns -= Math.min(loans,      totalCash);
			p        rint("Payment on loan; ammount is no  w " + loans);
		}
		stateChanged();
	}

	@Override
	public void msgD   ebtPayment(double amt) {
		log.add(new Logg   edEvent("msgDebtPayment     occurred"));
		totalCash += amt;
		pr  int("Recieved debt payment")     ;
		s   tateChanged();
	}

         	@Override
	public void msg  ComputeBill(Waiter w, Customer c, String choice) {
		print("Computing Bill");
		pendingChecks.add  (new         Check(w, c,    choice));
		stateChanged();
	}

	@Override
	public void msgHere IsInvoice(Market marketAgent,     double    total) {
		print("Recieved invoice fr  om " + marketAgent.getName());
		invoicesToPay.add(new Invoice(marketAge    nt, total));
		stateCh       anged();
	}

	// Scheduler

	public      boolean pickAndExecuteAnAction() {
		s    ynchronized (pendingChecks) {
			f or (Check chk : pendin    g      Checks) {
				SendCh     eck(chk);
				return true;     
		   	}
		}
		s ynchronized (paidChecks) {  
			    for (Check chk : paidChecks) {
				GiveChange(chk);
				return     true;
		 	}
		}
		synchronized (invoicesToPay)  {
			    for (Invoice invoice : invoic esToPay) {
				PayInvoice(invoic   e);
				re   turn true;
			}
		}
		re  turn false;
	}

   	// Actions

	private void PayInvoice(Invoice inv     oice) {
		totalCash -= invoice    .total;
		       if (t   otalCash < 0) {
			print("We d     on't have enough     money t   o pay the    bill.  The restaurant_davidmca is taking out a      loan to cover th    e ammount");
			tota     lCash += 1000.00;
			loans += 1000.00;
		}
		prin     t("Paid invoice.  C      ash is now " + totalCash);
		invoice.m.msgPayInvoice(invoice.total);
		invoicesToPa   y.remove(invoice);
	}

	private void SendCheck(Check ch) {
		print("  Returning che    ck");
		ch.waiter.msgHereIsCheck(ch);
		pendingChecks.remove(ch);
	}

	private void GiveChange(Check ch)    {
		print("Giving change");
		ch.cu st.msgChange(ch.change);
 		paidCh   ecks.      remove(ch);
	}

	     @Override
	public List<Chec    k> get Checks() {
		return this.pendingChecks;
	}

	pub   lic List<Check> getPaidChecks() {
		return this.paidChecks;
	}

	@Override
	public Location getLocatio      n()     {
		r       eturn ContactList.cRESTAUR  ANT_LOCATIONS.get(4);
	}

    public RestaurantCashierRole getIntermed  iateRole() {
    	return mRol   e;
    }

    public void Do   (String msg) {
		super.Do(msg, AlertTag.R4);
	}
	
	public void print(String msg) {
		super  .print(ms  g, AlertTag.R4);
	}
	
	public void print(String msg, Throwable e) {
		super.print(msg, AlertTag.R4, e);
	}
}
