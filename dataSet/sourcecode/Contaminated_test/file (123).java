/**
 * 
 */
package com.bcs.gui;

import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.Test;

import com.dvd.renter.entity.Customer;
import com.dvd.renter.entity.DVD;
import com.gui.generictablemodel.GenericTableModel;

/**
 * @author Sewwandi
 * 
 */
public class customTableModelTest {

	/**
	 * Test method for
	 * {@link com.dvd.renter.gui.generictablemodel.GenericTableModel#getDataVector()}
	 * .
	 */
	@Test
	public void testGetCustomers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.dvd.renter.gui.generictablemodel.GenericTableModel#setDataVector(java.util.Vector)}
	 * .
	 */
	@Test
	public void testSetCustomers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.dvd.renter.gui.generictablemodel.GenericTableModel#getColumnCount()}
	 * .
	 */
	@Test
	public void testGetColumnCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.dvd.renter.gui.generictablemodel.GenericTableModel#getColumnName(int)}
	 * .
	 */
	@Test
	public void testGetColumnNameInt() {
		Customer d = new Customer();
		DVD dv = new DVD();
		com.gui.generictablemodel.GenericTableModel<Customer> cust = new GenericTableModel<Customer>(
				d);
		cust.getColumnName(0);
		System.out.println(cust.getColumnName(0));
		// fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.dvd.renter.gui.generictablemodel.GenericTableModel#getRowCount()}
	 * .
	 */
	@Test
	public void testGetRowCount() {
		Vector<Customer> cs = new Vector<Customer>();
		cs.add(new Customer(1, "sdfdf", "asdfdf23234"));
		GenericTableModel<Customer> g = new GenericTableModel<Customer>(
				new Customer());
		g.setDataVector(cs);
		g.getRowCount();
		System.out.println(g.getRowCount());

	}

	/**
	 * Test method for
	 * {@link com.dvd.renter.gui.generictablemodel.GenericTableModel#getValueAt(int, int)}
	 * .
	 */
	@Test
	public void testGetValueAt() {
		Vector<Customer> cs = new Vector<Customer>();
		cs.add(new Customer(1, "sdfdf", "asdfdf23234"));
		GenericTableModel<Customer> g = new GenericTableModel<Customer>(
				new Customer());
		g.setDataVector(cs);
		g.getValueAt(0, 0);
		System.out.println("getValue at 00: " + g.getValueAt(0, 0).toString());
	}

}
