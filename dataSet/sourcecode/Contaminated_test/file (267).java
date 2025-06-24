package project;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseHandlerTest {

	private DatabaseHandler databaseHandler;	

	private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/test";
	private final String USER = "user";
	private final String PASSWORD = "password";
	
	// Create an array of items
	private ArrayList<String[]> itemsPeople = new ArrayList<String[]>();
	private ArrayList<String[]> itemsOrder = new ArrayList<String[]>();
		
	
	@Before
	public void setUp() throws Exception {
		databaseHandler = new DatabaseHandler(CONNECTION_STRING, USER, PASSWORD);
		
		itemsPeople.add(new String[]{"1", "OLA", "HANSEN", "TIMOTEIVN", "SANDNES"});
		itemsPeople.add(new String[]{"2", "TOVE", "SVENDSON", "BORGVN", "STAVANGER"});
		itemsPeople.add(new String[]{"3", "KARI", "PETTERSEN", "STORGT", "STAVANGER"});
		
		itemsOrder.add(new String[]{"10", "2000", "1"});
		itemsOrder.add(new String[]{"11", "2001", "2"});
		itemsOrder.add(new String[]{"12", "2002", "2"});
	}

	@After
	public void tearDown() throws Exception {
	
		cleanDatabase();
	}
	
	public void cleanDatabase() {
		// Delete all entries from test table 
		databaseHandler.execute("TRUNCATE PERSON");
		databaseHandler.execute("TRUNCATE ORDERS");
	}

	@Test
	public void testDatabaseHandler() throws SQLException {
		// Open the database connection
		databaseHandler.openConnection();
				
		assertNotNull(databaseHandler.connection);
		assertEquals(false, databaseHandler.connection.isClosed());
	}

	@Test
	public void testAdd() {
				
		databaseHandler.add(itemsPeople, "person");

		ArrayList<Person> personResult = databaseHandler.getAllPeople();
		
		assertEquals(3, personResult.size());
		
		assertEquals("1", personResult.get(0).getPersonID());
		assertEquals("OLA", personResult.get(0).getLastName());
		assertEquals("HANSEN", personResult.get(0).getFirstName());
		assertEquals("TIMOTEIVN", personResult.get(0).getStreet());
		assertEquals("SANDNES", personResult.get(0).getCity());
		
		assertEquals("2", personResult.get(1).getPersonID());
		assertEquals("TOVE", personResult.get(1).getLastName());
		assertEquals("SVENDSON", personResult.get(1).getFirstName());
		assertEquals("BORGVN", personResult.get(1).getStreet());
		assertEquals("STAVANGER", personResult.get(1).getCity());
		
		
		assertEquals("3", personResult.get(2).getPersonID());
		assertEquals("KARI", personResult.get(2).getLastName());
		assertEquals("PETTERSEN", personResult.get(2).getFirstName());
		assertEquals("STORGT", personResult.get(2).getStreet());
		assertEquals("STAVANGER", personResult.get(2).getCity());
		
		assertFalse(personResult.isEmpty());
		
		databaseHandler.add(itemsOrder, "order");
		ArrayList<Order> orderResult = databaseHandler.getAllOrders();

		assertEquals(3, orderResult.size());
		
		assertEquals("10", orderResult.get(0).getOrderID());
		assertEquals("2000", orderResult.get(0).getOrderNo());
		assertEquals("1", orderResult.get(0).getPersonId());
		
		assertEquals("11", orderResult.get(1).getOrderID());
		assertEquals("2001", orderResult.get(1).getOrderNo());
		assertEquals("2", orderResult.get(1).getPersonId());

		assertEquals("12", orderResult.get(2).getOrderID());
		assertEquals("2002", orderResult.get(2).getOrderNo());
		assertEquals("2", orderResult.get(2).getPersonId());
		
		assertFalse(orderResult.isEmpty());
		
		cleanDatabase();
	}

	@Test
	public void testGetPersonByOrder() {

		// Insert data to the database
		databaseHandler.add(itemsPeople, "person");
		databaseHandler.add(itemsOrder, "order");

		// Get results
		ArrayList<Person> result = databaseHandler.getPersonByOrder();
		
		// Test results
		assertEquals(2, result.size());
		
		// First person
		assertEquals("1", result.get(0).getPersonID());
		assertEquals("OLA", result.get(0).getLastName());
		assertEquals("HANSEN", result.get(0).getFirstName());
		assertEquals("TIMOTEIVN", result.get(0).getStreet());
		assertEquals("SANDNES", result.get(0).getCity());
		
		// First persons orders
		assertEquals(1, result.get(0).getOrders().size());
		
		assertEquals("10", result.get(0).getOrders().get(0).getOrderID());
		assertEquals("2000", result.get(0).getOrders().get(0).getOrderNo());
		assertEquals("1", result.get(0).getOrders().get(0).getPersonId());
		
		// Second person
		assertEquals("2", result.get(1).getPersonID());
		assertEquals("TOVE", result.get(1).getLastName());
		assertEquals("SVENDSON", result.get(1).getFirstName());
		assertEquals("BORGVN", result.get(1).getStreet());
		assertEquals("STAVANGER", result.get(1).getCity());
		
		// Second persons orders
		assertEquals("11", result.get(1).getOrders().get(0).getOrderID());
		assertEquals("2001", result.get(1).getOrders().get(0).getOrderNo());
		assertEquals("2", result.get(1).getOrders().get(0).getPersonId());
		
		assertEquals("12", result.get(1).getOrders().get(1).getOrderID());
		assertEquals("2002", result.get(1).getOrders().get(1).getOrderNo());
		assertEquals("2", result.get(1).getOrders().get(1).getPersonId());
		
		assertFalse(result.isEmpty());
		assertFalse(result.get(0).getOrders().isEmpty());
		assertFalse(result.get(1).getOrders().isEmpty());
		
		cleanDatabase();
	}

}
