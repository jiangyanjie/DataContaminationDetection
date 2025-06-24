package hostel.unittest;

import org.junit.*;
import hostel.core.Contact;
import hostel.core.InformationObject;
import hostel.core.Address;
import static org.junit.Assert.*;

/**
 * The class <code>ContactUnitTest</code> contains tests for the class <code>{@link Contact}</code>.
 * <p>
 * Copyright (c) 2013
 *
 * @generatedBy CodePro at 11/22/13 12:41 PM
 * @author sandeep
 * @version $Revision: 1.0 $
 */
public class ContactUnitTest
{
  /**
   * Run the Contact() constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testContact_1()
    throws Exception {

    Contact result = new Contact();

    // add additional test code here
    assertNotNull(result);
    assertEquals(null, result.getFaceBook());
    assertEquals(null, result.getWebUrl());
    assertEquals(null, result.getPhone());
    assertEquals(null, result.getEmail());
    assertEquals("Contact : \nPhone = null\nEmail = null\nFaceBook = null\nWebUrl = null\n", result.toString());
  }

  /**
   * Run the Contact(Contact) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testContact_2()
    throws Exception {
    Contact contactInfo = new Contact("", "", "", "");

    Contact result = new Contact(contactInfo);

    // add additional test code here
    assertNotNull(result);
    assertEquals("", result.getFaceBook());
    assertEquals("", result.getWebUrl());
    assertEquals("", result.getPhone());
    assertEquals("", result.getEmail());
    assertEquals("Contact : \nPhone = \nEmail = \nFaceBook = \nWebUrl = \n", result.toString());
  }

  /**
   * Run the Contact(String,String,String,String) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testContact_3()
    throws Exception {
    String phone = "";
    String email = "";
    String faceBook = "";
    String webUrl = "";

    Contact result = new Contact(phone, email, faceBook, webUrl);

    // add additional test code here
    assertNotNull(result);
    assertEquals("", result.getFaceBook());
    assertEquals("", result.getWebUrl());
    assertEquals("", result.getPhone());
    assertEquals("", result.getEmail());
    assertEquals("Contact : \nPhone = \nEmail = \nFaceBook = \nWebUrl = \n", result.toString());
  }

  /**
   * Run the boolean equals(Object) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testEquals_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    Object obj = new Contact("", "", "", "");

    boolean result = fixture.equals(obj);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean equals(Object) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testEquals_2()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    Object obj = null;

    boolean result = fixture.equals(obj);

    // add additional test code here
    assertEquals(false, result);
  }

  /**
   * Run the boolean equals(Object) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testEquals_3()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    Object obj = new Object();

    boolean result = fixture.equals(obj);

    // add additional test code here
    assertEquals(false, result);
  }

  /**
   * Run the boolean equals(Object) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testEquals_4()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    Object obj = new Contact("", "", "", "");

    boolean result = fixture.equals(obj);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean equals(Object) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testEquals_5()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    Object obj = new Contact("", "", "", "");

    boolean result = fixture.equals(obj);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean equals(Object) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testEquals_6()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    Object obj = new Contact("", "", "", "");

    boolean result = fixture.equals(obj);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean equals(Object) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testEquals_7()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    Object obj = new Contact("", "", "", "");

    boolean result = fixture.equals(obj);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean equals(Object) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testEquals_8()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    Object obj = new Contact("", "", "", "");

    boolean result = fixture.equals(obj);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean equals(Object) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testEquals_9()
    throws Exception {
    Contact fixture = new Contact("", "", "", (String) null);
    Object obj = new Contact("", "", "", (String) null);

    boolean result = fixture.equals(obj);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the String getEmail() method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testGetEmail_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");

    String result = fixture.getEmail();

    // add additional test code here
    assertEquals("", result);
  }

  /**
   * Run the String getFaceBook() method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testGetFaceBook_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");

    String result = fixture.getFaceBook();

    // add additional test code here
    assertEquals("", result);
  }

  /**
   * Run the String getPhone() method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testGetPhone_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");

    String result = fixture.getPhone();

    // add additional test code here
    assertEquals("", result);
  }

  /**
   * Run the String getWebUrl() method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testGetWebUrl_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");

    String result = fixture.getWebUrl();

    // add additional test code here
    assertEquals("", result);
  }

  /**
   * Run the int hashCode() method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testHashCode_1()
    throws Exception {
    Contact fixture = new Contact("", (String) null, (String) null, "");

    int result = fixture.hashCode();

    // add additional test code here
    assertEquals(923521, result);
  }

  /**
   * Run the int hashCode() method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testHashCode_2()
    throws Exception {
    Contact fixture = new Contact((String) null, "", "", (String) null);

    int result = fixture.hashCode();

    // add additional test code here
    assertEquals(923521, result);
  }

  /**
   * Run the boolean match(InformationObject) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testMatch_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    InformationObject objectInfo = new Contact("", "", "", "");

    boolean result = fixture.match(objectInfo);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean match(InformationObject) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testMatch_2()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    InformationObject objectInfo = null;

    boolean result = fixture.match(objectInfo);

    // add additional test code here
    assertEquals(false, result);
  }

  /**
   * Run the boolean match(InformationObject) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testMatch_3()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    InformationObject objectInfo = new Address();

    boolean result = fixture.match(objectInfo);

    // add additional test code here
    assertEquals(false, result);
  }

  /**
   * Run the boolean match(InformationObject) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testMatch_4()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    InformationObject objectInfo = new Contact("", "", "", "");

    boolean result = fixture.match(objectInfo);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean match(InformationObject) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testMatch_5()
    throws Exception {
    Contact fixture = new Contact("", (String) null, "", "");
    InformationObject objectInfo = new Contact("", (String) null, "", "");

    boolean result = fixture.match(objectInfo);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean match(InformationObject) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testMatch_6()
    throws Exception {
    Contact fixture = new Contact("", (String) null, (String) null, "");
    InformationObject objectInfo = new Contact("", (String) null, (String) null, "");

    boolean result = fixture.match(objectInfo);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean match(InformationObject) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testMatch_7()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    InformationObject objectInfo = new Contact("", "", "", "");

    boolean result = fixture.match(objectInfo);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean match(InformationObject) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testMatch_8()
    throws Exception {
    Contact fixture = new Contact((String) null, (String) null, (String) null, "");
    InformationObject objectInfo = new Contact((String) null, (String) null, (String) null, "");

    boolean result = fixture.match(objectInfo);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the boolean match(InformationObject) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testMatch_9()
    throws Exception {
    Contact fixture = new Contact((String) null, (String) null, (String) null, (String) null);
    InformationObject objectInfo = new Contact((String) null, (String) null, (String) null, (String) null);

    boolean result = fixture.match(objectInfo);

    // add additional test code here
    assertEquals(true, result);
  }

  /**
   * Run the void setEmail(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testSetEmail_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    String email = "";

    fixture.setEmail(email);

    // add additional test code here
  }

  /**
   * Run the void setFaceBook(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testSetFaceBook_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    String faceBook = "";

    fixture.setFaceBook(faceBook);

    // add additional test code here
  }

  /**
   * Run the void setPhone(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testSetPhone_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    String phone = "";

    fixture.setPhone(phone);

    // add additional test code here
  }

  /**
   * Run the void setWebUrl(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testSetWebUrl_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");
    String webUrl = "";

    fixture.setWebUrl(webUrl);

    // add additional test code here
  }

  /**
   * Run the String toString() method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testToString_1()
    throws Exception {
    Contact fixture = new Contact("", "", "", "");

    String result = fixture.toString();

    // add additional test code here
    assertEquals("Contact : \nPhone = \nEmail = \nFaceBook = \nWebUrl = \n", result);
  }

  /**
   * Perform pre-test initialization.
   *
   * @throws Exception
   *         if the initialization fails for some reason
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Before
  public void setUp()
    throws Exception {
    // add additional set up code here
  }

  /**
   * Perform post-test clean-up.
   *
   * @throws Exception
   *         if the clean-up fails for some reason
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @After
  public void tearDown()
    throws Exception {
    // Add additional tear down code here
  }

  /**
   * Launch the test.
   *
   * @param args the command line arguments
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  public static void main(String[] args) {
    new org.junit.runner.JUnitCore().run(ContactUnitTest.class);
  }
}