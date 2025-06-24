package ag.twittersimulation.user;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractUserLoaderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRemoveSpaces() {
		String stringWithSpaces = new String(" fsdf, fdfiij   998jnndDSA hdsdDD9 98 bn bv   v ");
		assertEquals("fsdf,fdfiij998jnndDSAhdsdDD998bnbvv", AbstractUserLoader.RemoveSpaces(stringWithSpaces));
	}
	
	@Test
	public void testGetFollowerName() {
		String testUsersLine = new String("TommyJonson follows Jane, Tim, Tom, Shane87, willyisfree88");
		assertEquals("TommyJonson", AbstractUserLoader.GetFollowerName(testUsersLine));
	}
	
	@Test
	public void testGetFolloweeNames() {
		String testUsersLine = new String("TommyJonson follows Jane, Tim, Tom, Shane87, willyisfree88");
		ArrayList<String> followeeNames = new ArrayList<String>();
		followeeNames.add("Jane");
		followeeNames.add("Tim");
		followeeNames.add("Tom");
		followeeNames.add("Shane87");
		followeeNames.add("willyisfree88");
		assertEquals(followeeNames, AbstractUserLoader.GetFolloweeNames(testUsersLine));
	
	}
	
	@Test
	public void testAddUsers() {
		TreeMap<String, User> users = new TreeMap<String, User>();
		ArrayList<String> followeeNames = new ArrayList<String>();
		followeeNames.add("Jane");
		followeeNames.add("Tim");
		followeeNames.add("Tom");
		followeeNames.add("Shane87");
		followeeNames.add("willyisfree88");
		String followerName = new String("Jim");
		users = AbstractUserLoader.AddUsers(users, followerName, followeeNames);
		assertEquals("Jane", users.get("Jane").getName());
		assertEquals("Tim", users.get("Tim").getName());
		assertEquals("Tom", users.get("Tom").getName());
		assertEquals("Shane87", users.get("Shane87").getName());
		assertEquals("willyisfree88", users.get("willyisfree88").getName());
		assertEquals("Jim", users.get("Jim").getName());
		assertEquals(followeeNames, users.get("Jim").getFollows());		
	}
	

}
