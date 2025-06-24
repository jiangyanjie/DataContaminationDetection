package fi.samumu.first.app;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactTest {
	
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
	public void testDelete() throws SQLException{
		
		try(Connection connection = DbHelper.getConnection(); Statement stmt = connection.createStatement()){
			stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Albert Attard', 'albert.attard@gmail.com')");
			stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Samu Mukkala', 'samumukkala@gmail.com')");
			stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Teppo Testaaja', 'teppotestaaja@gmail.com')");
			
			List<Contact> contacts = ContactsHelper.getInstance().getContacts();
			Assert.assertEquals(3, contacts.size());
			
			final Contact contact = contacts.get(1);
			Assert.assertNotEquals(-1, contact.getId());
			
			contact.delete();
			Assert.assertEquals(-1, contact.getId());
			
			contacts = ContactsHelper.getInstance().getContacts();
			Assert.assertEquals(2,  contacts.size());
			Assert.assertEquals(1L,  contacts.get(0).getId());
			Assert.assertEquals(3L, contacts.get(1).getId());
			
			
		}
		
	}
	
	
	
	@Test
	public void testSave() throws SQLException{
		
		final Contact c = new Contact();
		c.setName("Samu Mukkala");
		c.setContacts("samumukkala@gmail.com");
		Assert.assertEquals(-1L, c.getId());
		c.save();
		Assert.assertEquals(1L, c.getId());
		
		try(Connection connection = DbHelper.getConnection(); Statement stmt = connection.createStatement()){			
			try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM contacts")){
				Assert.assertTrue("Count should return at least one row", rs.next());
				Assert.assertEquals(1L, rs.getLong(1));
				Assert.assertFalse("Count should not return at least one row", rs.next());
			}
			
			try (ResultSet rs = stmt.executeQuery("SELECT * FROM contacts")){
				Assert.assertTrue("Select should return at least one row", rs.next());
				Assert.assertEquals(1L, rs.getLong("id"));
				Assert.assertEquals("Samu Mukkala", rs.getString("name"));
				Assert.assertEquals("samumukkala@gmail.com", rs.getString("contacts"));
				Assert.assertFalse("Select should not return at least one row", rs.next());
			}
		}
		
		c.setName("mukkala samu");
		c.save();
		
		Assert.assertEquals(1L, c.getId());
		Assert.assertEquals("mukkala samu", c.getName());
		Assert.assertEquals("samumukkala@gmail.com", c.getContacts());
		
		final List<Contact> contacts = ContactsHelper.getInstance().getContacts();
		Assert.assertEquals(1,  contacts.size());
		
		final Contact contact = contacts.get(0);
		Assert.assertEquals(1L, contact.getId());
		Assert.assertEquals("mukkala samu", contact.getName());
		Assert.assertEquals("samumukkala@gmail.com", contact.getContacts());
		
		
	}

}
