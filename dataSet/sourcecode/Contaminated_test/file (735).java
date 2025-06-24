package paymentmachine;

import paymentmachine.cashhandler.CannotRefundChangeException;
import paymentmachine.cashhandler.CashHandlerDevice;
import paymentmachine.cashhandler.PaymentCancelledException;
import paymentmachine.display.Display;
import paymentmachine.ticket.Ticket;
import paymentmachine.ticket.TicketAlreadyPaidException;
import paymentmachine.ticket.TicketDatabase;
import paymentmachine.ticket.TicketNotFoundException;

public class DefaultPaymentMachine implements PaymentMachine {

	private final Display display;
	private final TicketDatabase ticketDatabase ;
	private final CashHandlerDevice cashHandlerDevice ;

    public DefaultPaymentMachine(ComponentStore componentStore) {
        this.display = componentStore.getDisplay();
        this.ticketDatabase = componentStore.getTicketDatabase();
        this.cashHandlerDevice = componentStore.getCashHandlerDevice();
    }

	@Override
	public void ticketReceived(int ticketNumber) {
		Ticket ticket;
		try {
			ticket = ticketDatabase.getTicket(ticketNumber);
		} catch (TicketNotFoundException ex) {
			display.write("Ticket not found");
			return;
		}

		if (ticket.isPaid()) {
			display.write("Ticket had been already paid. Thank you, bye.");
			return;
		}

		display.write("Ticket found. Payable amount: " + ticket.getPrice());
		doPaymentFlow(ticket);
	}

	private void doPaymentFlow(Ticket ticket) {
		int receivedAmount;
		try {
			receivedAmount = receiveCash(ticket.getPrice());
		} catch (PaymentCancelledException ex) {
			display.write("Payment cancelled");
			return;
		}

		processPayment(ticket, receivedAmount);
	}

	private int receiveCash(int ticketPrice) throws PaymentCancelledException {
		int receivedAmount = cashHandlerDevice.waitForCash(ticketPrice);
		if (receivedAmount < ticketPrice) {
			throw new IllegalStateException("cashHandlerDevice failure: receivedAmount < ticket.price");
		}

		return receivedAmount;
	}

	private void processPayment(Ticket ticket, int receivedAmount) {
		try {
			ticketDatabase.markTicketPaid(ticket);
		} catch (TicketAlreadyPaidException ex) {
			display.write("Ticket had been already paid, refunding the paid price");
			cashHandlerDevice.refundPaidAmount(receivedAmount);
		}

		int changeToRefund = receivedAmount - ticket.getPrice();
		try {
			cashHandlerDevice.refundChange(changeToRefund);
		} catch (CannotRefundChangeException ex) {
			display.write("Sorry, there is no available change.");
			cashHandlerDevice.refundPaidAmount(receivedAmount);
		}
	}
}
