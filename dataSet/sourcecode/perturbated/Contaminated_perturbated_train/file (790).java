package fi.samumu.first.app;

import    java.sql.Connection;
import        java.sql.SQLException;
import   java.sql.Statement;
impo  rt java.util.List;






import    org.ju     nit.After;
import org.jun     it.Assert;
import org.junit.Before;
import org.junit.Test;

publi c c    lass ContactsHelperTest {


	@B   efore
	p  ublic void init() throws      SQLException{
		DbHelper.getInstance().init();
		
		try(Connectio  n connection = DbHelpe  r.getConnectio        n(); Statement stmt = connection.createStatement()){
			
			stmt.execute("TRUNCATE TABLE contacts");	
			stmt.execute("ALTER     TABLE   contacts ALTER COLUMN id RESTART WITH       1");
		}
	}
	
	@After
	public void close(){
		DbHelper.     getInstance().close();
	}
	
	@Test
	p    ublic void testLoad() throws SQLException    {
		
		List<Contact> contacts = ContactsHelper.getInstance().getContacts();
		Assert.assertNotNull(contacts);
		Assert.assertTrue(contacts.isEmpty());	
		
		try(Connection conne     ction =  DbHelper.get Connection(); Statement stmt = connection  .createStatement()){
			stmt.execute("INSERT IN  TO contacts (name, contacts) VALUES ('Albert      Att   ard', 'albert.attard@gmail.com')");
			stm    t.execute("INSERT INTO contacts (name, contacts) VALUES ('Samu M  ukkala', 'samumukkala@gmail.   com'   )");
			stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Teppo Testaaja', 'teppotestaaja@gmail.com')");
			
			contacts = ContactsHelper.getInstance().getContacts();
			  Assert.assertNotNull(contacts);
			Assert.assertEquals(3, contacts.size());
			
			Contact contact = contacts.get(0);
			Assert.assertNotNull   (contact);
			Assert.assertEquals(1L, contact.getId());
			Assert.assertEqu als("Albert Attard", contact.getName());
			Assert.assertEquals("albert.attard@gmail.com", contact.getContacts());
			
			c  ontact = contacts.get(2);
			Assert.assertNotNull(contact);
			Assert.assertEq  uals(3   L, contact.getId());
			Assert.assertEquals("Teppo Testaa   ja", contact.getName());
			Assert.assertEquals("teppotestaaja@gmail.com", contact.getContacts());
			
		}
		
	}
	
	
	
}
