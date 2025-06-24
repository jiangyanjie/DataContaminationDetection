package restaurant.restaurant_davidmca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import restaurant.restaurant_davidmca.gui.CookGui;
import restaurant.restaurant_davidmca.gui.CustomerGui;
import restaurant.restaurant_davidmca.gui.WaiterGui;
import restaurant.restaurant_davidmca.roles.DavidCashierRole;
import restaurant.restaurant_davidmca.roles.DavidCookRole;
import restaurant.restaurant_davidmca.roles.DavidCustomerRole;
import restaurant.restaurant_davidmca.roles.DavidHostRole;
import restaurant.restaurant_davidmca.roles.DavidWaiterRole;
import restaurant.restaurant_davidmca.roles.DavidWaiterRoleShared;
import base.Gui;

public class DavidRestaurant {
	
	/*
	 * Data
	 */

	public static DavidHostRole host;
	public static DavidCookRole cook;
	public static DavidCashierRole cashier;
	public static Vector<DavidCustomerRole> customers;
	public static List<Gui> guis;
	public static int customerCount = 0;
	public static int waiterCount = 0;
	private final int NUMTABLES = 4;
	public static Collection<Table> tables;
	private int[] xpositions = { 0, 200, 300, 400 };
	private int[] ypositions = { 0, 100, 200, 300 };
	
	public DavidRestaurant() {
		customers = new Vector<DavidCustomerRole>();
		guis = Collections.synchronizedList(new ArrayList<Gui>());
		tables = Collections.synchronizedList(new ArrayList<Table>());
		for (int ix = 1; ix < NUMTABLES; ix++) {
			tables.add(new Table(ix, xpositions[ix], ypositions[ix], 1));
		}
	}
	
	public static void addCook(DavidCookRole ck) {
		cook = ck;
		CookGui g = new CookGui(cook);
		cook.setGui(g);
		host.setCook(cook);
		guis.add(g);
	}

	public static void addCustomer(DavidCustomerRole cust) {
		CustomerGui g = new CustomerGui(cust, customerCount++);
		guis.add(g);
		cust.setHost(host);
		cust.setCashier(cashier);
		cust.setGui(g);
		g.setHungry();
		customers.add(cust);
	}

	public static void addWaiter(DavidWaiterRole waiter) {
		WaiterGui g = new WaiterGui(waiter, waiterCount++);
		guis.add(g);
		g.DoGoToFront();
		waiter.setHost(host);
		waiter.setGui(g);
		host.addWaiter(waiter);
		waiter.setCashier(cashier);
	}

	public static void addSharedWaiter(DavidWaiterRoleShared waiter) {
		WaiterGui g = new WaiterGui(waiter, waiterCount++);
		guis.add(g);
		g.DoGoToFront();
		waiter.setHost(host);
		waiter.setCook(cook);
		waiter.setGui(g);
		host.addWaiter(waiter);
		waiter.setCashier(cashier);
	}	
}
