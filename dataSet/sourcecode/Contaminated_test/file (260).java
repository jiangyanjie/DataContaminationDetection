package project;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseConnectionTest {

	private DatabaseConnection databaseConnection;
	
	private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/test";
	private final String USER = "user";
	private final String PASSWORD = "password";

	// Create an array of people
	private Person[] people = new Person[] {
			new Person("1", "OLA", "HANSEN", "TIMOTEIVN", "SANDNES"),
			new Person("2", "TOVE", "SVENDSON", "BORGVN", "STAVANGER"),
			new Person("3", "KARI", "PETTERSEN", "STORGT", "STAVANGER")				
	};
	
	// Create an array of people
	private Order[] orders = new Order[] {
			new Order("10", "2000", "1"),
			new Order("11", "2001", "2")			
	};
	
	private int personCount = people.length;
	private int orderCount = orders.length;
	
	
	@Before
	public void setUp() throws Exception {
		databaseConnection = new DatabaseConnection(CONNECTION_STRING, USER, PASSWORD);
	}

	@After
	public void tearDown() throws Exception {

		cleanDatabase();
	}

	public void cleanDatabase() {
		// Delete all entries from test table 
		databaseConnection.execute("TRUNCATE PERSON");
		databaseConnection.execute("TRUNCATE ORDERS");
	}
	
	@Test
	public void testOpen() throws SQLException {
		
		// Open the database connection
		databaseConnection.openConnection();
		
		assertNotNull(databaseConnection.connection);
		assertEquals(false, databaseConnection.connection.isClosed());
	}
	
	@Test
	public void testClose() throws SQLException {
		
		// Open and close database connection
		databaseConnection.openConnection();
		databaseConnection.closeConnection();
		
		assertNotNull(databaseConnection.connection);
		assertEquals(true, databaseConnection.connection.isClosed());
	}
		
	@Test
	public void testGetAllPeople() {
		
		// Insert array of people into the database
		for (Person person : people) {
			databaseConnection.insertNewPerson(person);
		}
		
		// Get all records from database
		ArrayList<Person> result = new ArrayList<Person>();		
		result = databaseConnection.getAllPeople();
		
		// Test results
		assertEquals(personCount, result.size());
		
		assertEquals("1", result.get(0).getPersonID());
		assertEquals("OLA", result.get(0).getLastName());
		assertEquals("HANSEN", result.get(0).getFirstName());
		assertEquals("TIMOTEIVN", result.get(0).getStreet());
		assertEquals("SANDNES", result.get(0).getCity());
		
		assertEquals("2", result.get(1).getPersonID());
		assertEquals("TOVE", result.get(1).getLastName());
		assertEquals("SVENDSON", result.get(1).getFirstName());
		assertEquals("BORGVN", result.get(1).getStreet());
		assertEquals("STAVANGER", result.get(1).getCity());
		
		assertEquals("3", result.get(2).getPersonID());
		assertEquals("KARI", result.get(2).getLastName());
		assertEquals("PETTERSEN", result.get(2).getFirstName());
		assertEquals("STORGT", result.get(2).getStreet());
		assertEquals("STAVANGER", result.get(2).getCity());
	
		assertFalse(result.isEmpty());
		
		cleanDatabase();
	}
	
	@Test
	public void testGetAllOrders() {
		
		// Insert array of orders into the database
		for (Order order : orders) {
			databaseConnection.insertNewOrder(order);
		}
		
		// Get all records from database
		ArrayList<Order> result = new ArrayList<Order>();		
		result = databaseConnection.getAllOrders();
		
		// Test results
		assertEquals(orderCount, result.size());
		
		assertEquals("10", result.get(0).getOrderID());
		assertEquals("2000", result.get(0).getOrderNo());
		assertEquals("1", result.get(0).getPersonId());
		
		assertEquals("11", result.get(1).getOrderID());
		assertEquals("2001", result.get(1).getOrderNo());
		assertEquals("2", result.get(1).getPersonId());
		
		assertFalse(result.isEmpty());
		
		cleanDatabase();
	}
	
	@Test
	public void testGetPersonByID() {
		
		// Insert array of people into the database
		for (Person person : people) {
			databaseConnection.insertNewPerson(person);
		}
		
		Person result = databaseConnection.getPersonByID("1");
		
		assertEquals("1", result.getPersonID());
		assertEquals("OLA", result.getLastName());
		assertEquals("HANSEN", result.getFirstName());
		assertEquals("TIMOTEIVN", result.getStreet());
		assertEquals("SANDNES", result.getCity());
		
		result = databaseConnection.getPersonByID("2");
		
		assertEquals("2", result.getPersonID());
		assertEquals("TOVE", result.getLastName());
		assertEquals("SVENDSON", result.getFirstName());
		assertEquals("BORGVN", result.getStreet());
		assertEquals("STAVANGER", result.getCity());
		
		result = databaseConnection.getPersonByID("10");
		
		assertNull(result);

		cleanDatabase();
	}
	
	@Test
	public void testInsertNewPerson() {
				
		// Insert person into the database
		databaseConnection.insertNewPerson(people[0]);
				
		// Get all records from database
		ArrayList<Person> result = new ArrayList<Person>();		
		result = databaseConnection.getAllPeople();
				
		// Test results
		assertEquals(1, result.size());
				
		assertEquals("1", result.get(0).getPersonID());
		assertEquals("OLA", result.get(0).getLastName());
		assertEquals("HANSEN", result.get(0).getFirstName());
		assertEquals("TIMOTEIVN", result.get(0).getStreet());
		assertEquals("SANDNES", result.get(0).getCity());
			
		assertFalse(result.isEmpty());
				
		cleanDatabase();
	}

	@Test
	public void testInsertNewOrder() {
				
		// Insert person into the database
		databaseConnection.insertNewOrder(orders[0]);
						
		// Get all records from database
		ArrayList<Order> result = new ArrayList<Order>();		
		result = databaseConnection.getAllOrders();
						
		// Test results
		assertEquals(1, result.size());
					
		assertEquals("10", result.get(0).getOrderID());
		assertEquals("2000", result.get(0).getOrderNo());
		assertEquals("1", result.get(0).getPersonId());
				
		assertFalse(result.isEmpty());
				
		cleanDatabase();
	}

}
