package fi.samumu.first.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;






import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactsHelperTest {


	@Before
	public void init() throws SQLException{
		DbHelper.getInstance().init();
		
		try(Connection connection = DbHelper.getConnection(); Statement stmt = connection.createStatement()){
			
			stmt.execute("TRUNCATE TABLE contacts");	
			stmt.execute("ALTER TABLE contacts ALTER COLUMN id RESTART WITH 1");
		}
	}
	
	@After
	public void close(){
		DbHelper.getInstance().close();
	}
	
	@Test
	public void testLoad() throws SQLException {
		
		List<Contact> contacts = ContactsHelper.getInstance().getContacts();
		Assert.assertNotNull(contacts);
		Assert.assertTrue(contacts.isEmpty());	
		
		try(Connection connection =  DbHelper.getConnection(); Statement stmt = connection.createStatement()){
			stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Albert Attard', 'albert.attard@gmail.com')");
			stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Samu Mukkala', 'samumukkala@gmail.com')");
			stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Teppo Testaaja', 'teppotestaaja@gmail.com')");
			
			contacts = ContactsHelper.getInstance().getContacts();
			Assert.assertNotNull(contacts);
			Assert.assertEquals(3, contacts.size());
			
			Contact contact = contacts.get(0);
			Assert.assertNotNull(contact);
			Assert.assertEquals(1L, contact.getId());
			Assert.assertEquals("Albert Attard", contact.getName());
			Assert.assertEquals("albert.attard@gmail.com", contact.getContacts());
			
			contact = contacts.get(2);
			Assert.assertNotNull(contact);
			Assert.assertEquals(3L, contact.getId());
			Assert.assertEquals("Teppo Testaaja", contact.getName());
			Assert.assertEquals("teppotestaaja@gmail.com", contact.getContacts());
			
		}
		
	}
	
	
	
}
