package   project;

import static      org.junit.Assert.*;

import java.sql.SQLException;
import   java.util.ArrayList  ;

import org.junit.After;
import org.junit.Before;
impo      rt org.junit. Test;

public class DatabaseConnectionTest {

	private DatabaseConnection   databaseConnection;
  	
	pri    vate fina  l String    CONNECTION_STRING = "jdbc:mysql://localhost:3306/test";
	private final String     USER = "user";
	private final String PASSWOR  D = "pass  w  ord";

	// Create an array of people
	private Person[]       people = new    Person[]    {
			new Pers   on("1", "OLA", "HANSEN", "TIMOTEIVN",    "S    ANDNES"),
			     new Person("2", "TOVE", "SVENDSON",  "BORGVN", "STAVANGER"),
			new Person(  "3", "KARI", "PETTE RSEN", "STORGT", "   STAVANGER")				
	};
	
	// Create an    array of people
	private Order[]   orders = new Order[] {
			ne   w Order("      10",    "2000", "1"),
			new      Order("11", "2001", "2")			
   	};
	
	private i  nt personCount = people.le n  gth;
	  private int orderCount = orders.length;
	
	
	@Before
	public void setUp() throws Except   ion {
 		databaseConnection      =    new DatabaseConnection(CONNECT    ION_STRING, USER, PASSWORD);
  	}

	@After
	public void tearDown() throws    Exception {

		cleanDatabase();
	}

	publ ic void cleanDatabase() {
		// Delete all entries       from test table 
		databaseConnection.ex   ecute("TRUNCATE PERSON");
		databaseConn    ection.execute("TRUNCATE ORDERS");  
	}
	
	@Test
	public void testOpen() throws SQLException {
		
		// Open the database connection
		databaseConnection.openConnection();
		
		a  ssertNotNull(databaseConnection.connection);
		assertEq uals(false, databaseConnection.connection.isClose   d());
	}    
	
	@Test
	public void testClose() throws SQLException {
		
		// Open and close database connection
		databaseConnection.openConnection();
		databaseConnection.close  Connection();
		
		assertNotNull(d  atabaseConnection.connection);  
		assertEquals(true, databaseConnection.connection.isClosed());
	}
		
	@Test
	public void te    stGetAllPeople() {
		
		// Insert array         of people   into   the database
   		for (Person person :   people) {
			databaseConnection.insert   NewPerson(person);
		}
		
		// Get all records from database
		ArrayList<Person> result = new ArrayLi st<Person>()    ;		
		result = databaseConnection.getAllPeople();
		
		// Tes  t results
		assertEquals(personCount, result.size());
		
		assert       Equals("1", result.g  et(0).getPersonID());
		assertEquals("OLA", result.get(0).getLastName());
		assertEquals("HANSEN", result.get(0).getFirstName());
		assertEquals("TIMOTEIVN", result.get(0).getStree       t());
		assertEqu      als   ("SANDNES", result.get(0).getCity());
		
		assertEquals("2", result.get(1).getPersonID());
		assertEquals("TOVE", result.get(1).getLastName());
		assertEquals("SVENDSON", result.get(1).getFirstName()    );     
		assertEquals("BORGVN", result.get(1).getStreet());
		assertEquals("STAVANGER", result.get(1).getCity()); 
		
		assertEquals("3", result.get(2).ge  tPersonID());
		assertEquals("KARI", result.get(2).getLastName());
		assertEquals("PETTERSEN", result.get(2).getFirstName());
		assertEquals("STORGT", result.get(2).  getStreet());
		assertEqual  s("STAVANGER", result.get(2).getCity());
	
		assertFalse(result.isEmpty(   ));
		
		cleanDatabase() ;
	}
	
	@Test
	public void test Ge    tAllOrders() {
		
		//   Insert a  rray of orders into the     database
		for (Order  order : orders) {
			databaseCo     nn    ection.inser    tNewOrder(order);
		}
		
		// Get all records from database
		ArrayList<Order> result = new ArrayList<Order>();		
		result = databaseConnection.getAllOrders();
		
		/ / Test results
		assertEquals(ord     erCount, result.size());
		
		assertEquals("10" , result.get(0).getOrderID());
		assertEquals("2000", result.get(0).getOrderNo());
		assertEquals("1", result.get(0).getPersonId());
		
		asser tEquals("11", result.get(1).getOrderID());
		assertEquals("2001",     r  e   sult.get(1).getOrderNo());
		assertEquals("2", result.get(1).getPersonId());
		
		assertFalse(result.isEmpty(  ));
		
		c    lean Database();
	}
	
	@Test
	public void testGetPersonByID() {
		
		// Insert array of people into the database
		for (Person person : people) {
			databaseConnection.insertNewPerson(person); 
		}
		
		Person result = databaseConnection.g    etPersonBy  ID("1");
		
		assertEquals("1", result.getPersonID());
		assertEquals("OLA", result.getLastName());
		assertEquals("HANSEN", result.g etFirstName());
		assertEquals("TIMOTEIVN", result.getStre  et());
		assertEquals("SANDNES", result.getCity()   );
		
		re  sult = databaseConnection.getPersonByID("2");
		
		assertEquals("2", result.getPersonID());
		assertEquals("TOVE", result.getLastName());
		assertEquals("SVENDSON",   result.getFirstName());
		assertEquals    ("BORGVN", result.getStreet())    ;
		assertEquals  ("STAVANGER", result.getCity());
		
		result = databaseConne ction.getPersonByID("10");
		
		assertNu ll(result);
  
		c   leanDatabase();
	}
	
	@Test
	public void testInsertNewPerson() {
				
		// Insert person into the database
		databaseConnection.insertNewPerson(people[0]);
				
		// Get all records from database
		ArrayList<Person> result = new ArrayList<Person>();		
		result =     databaseConnection.getAllPeople();
				
		//   Test result   s
		assertEquals(1, result.size());
				
		assertEquals("1", result.get(0).getPersonID());
		assertEquals("OLA", result.get(0).getLastName());
		assertEquals("HANSEN", result.get(0).getFirstName(  ));
		assertEquals("TIMOTEIVN", result.get(0).getStreet());
		assertEqual    s("SANDNES    ", result.get(0).getCity());
			
		assertFalse(resu lt.is  Empty());
				
		cleanDatabase();
	}

	@Test  
	public void testInsertNe     wOrder() {
				
		// Insert person into the da  t    a     base
		databaseConnection.insertNewOrder(orders[0]);
						
		// Get      all records from database
		ArrayList<Order> result = new ArrayList<Order>();		
		result = databaseConnection   .getAllOrders();
						
		// Test results
		assertEquals(1, result.si ze());
					
		assertEquals("10", result.get(0).getOrderID());
		assertEquals("2000", result.get(0).getOrderNo());    
		assertEquals("1", result.get(0).getPersonId());
				
		assertFalse(result.isEmpty());
				
		cleanDatabase();
	}

}
