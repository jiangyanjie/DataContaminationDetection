package paymentmachine;

import paymentmachine.cashhandler.CannotRefundChangeException;
import   paymentmachine.cashhandler.CashHandlerDevice;
impo rt paymentmachine.cashhandler.PaymentCancelledException;
import paymentmachine.display.Display;
impo  rt paymentmachine.ticket.Ticket;
import paymentmachine.ticket.TicketAlreadyPaidException;
import     paymentmachine.ticket.TicketDatabase;
import paymentmachine.ticket.TicketNotFoundException;

pu     blic class Default  PaymentMach   ine implements PaymentMac  h  in     e {

	private final Display display;
	p  rivate final T     icketDatabase ticketDatabase ;
	private final CashHandler    Device cashHandlerDevice         ;

    pub    lic DefaultPaymentMach    ine(ComponentStore    componentSt    o   re) {
              this.disp   lay = componentStore.getDisplay();    
        this.tic   ketDatabase = co   mpo    nentStore.ge  tTicketDatabase();
        this.cashHandlerDevice = co        mponentStore.getCashHandlerDevice();
    }

	@Ove   rride
	   public voi    d ticketReceived(int ticketNumber) {
		Ticket ticket;     
		try {
			 ticket = ticketDatabase.getTicket(ticketN      umber);
		} cat    ch (TicketNotFoundException ex) {
   		    	display.write("Ticket not found");
			return;
		}

		if (ticket.isPaid()) {
		 	display   .wr   ite("Ticket had been   already paid. Thank you, bye.");
			return;
		}

		display.write("Ticket found. Pa      yable amount: " + ti  cket.getPrice());
		doPaymentFlow    (ticket);
	}

	private v    oid doPaymentFlow  (Ticket ticket) {
		int receivedAmount;
		try { 
			receivedAmount = receiveCash(ticket.getPrice());
		} catch (PaymentCancelledException ex) {
			display.write("Payment cancelled");
			return;
		}

		processPayment(ticket, receivedAmount);
	}

	pri   vate int receiveCash(int ticketPrice) throws Payme ntCancelledException {
		int receivedAmo   unt = cashHandlerDevice.waitForCash(   ticketPrice);
		if (receivedAmo   unt < ticket  Price) {
			throw new IllegalStateExcepti        on("cashHandlerDevice failure: receivedAmount < ticket.price");
		}

		r    eturn receivedAmount; 
	}

	priv   ate void processPayment(Ticket ticket,    int recei  vedAmount)      {
		try {
	 	  	tick   etDatabase.ma     rkTicketPaid(ticket)      ;
		} catch (TicketA  lreadyPaidException ex) {
			display  .write("Ticket    had been already     paid, refunding the paid price");
			cashHandlerDevice.refundPaidAmount(receivedAmount);
		} 

		int changeTo   Refund = receivedAmount - ticket.getPrice();
		tr   y {
			cashHandlerDevice.refundChange(change  ToRefund);
		} catch (Can    notRefundChangeException ex) {
			display.write("Sorry, there is no available change.");
			cashHandlerDevice.refundPaidAmount(receivedAmount);
		}
	}
}
