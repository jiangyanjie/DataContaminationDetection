package hostel.unittest;

import org.junit.*;
import hostel.exception.ConflictException;
import static org.junit.Assert.*;

/**
 * The class <code>ConflictExceptionUnitTest</code> contains tests for the class <code>{@link ConflictException}</code>.
 * <p>
 * Copyright (c) 2013
 *
 * @generatedBy CodePro at 11/22/13 12:40 PM
 * @author sandeep
 * @version $Revision: 1.0 $
 */
public class ConflictExceptionUnitTest
{
  /**
   * Run the ConflictException() constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:40 PM
   */
  @Test
  public void testConflictException_1()
    throws Exception {

    ConflictException result = new ConflictException();

    // add additional test code here
    assertNotNull(result);
    assertEquals(null, result.getCause());
    assertEquals("hostel.exception.ConflictException", result.toString());
    assertEquals(null, result.getMessage());
    assertEquals(null, result.getLocalizedMessage());
  }

  /**
   * Run the ConflictException(String) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:40 PM
   */
  @Test
  public void testConflictException_2()
    throws Exception {
    String message = "";

    ConflictException result = new ConflictException(message);

    // add additional test code here
    assertNotNull(result);
    assertEquals(null, result.getCause());
    assertEquals("hostel.exception.ConflictException: ", result.toString());
    assertEquals("", result.getMessage());
    assertEquals("", result.getLocalizedMessage());
  }

  /**
   * Run the ConflictException(Throwable) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:40 PM
   */
  @Test
  public void testConflictException_3()
    throws Exception {
    Throwable cause = new Throwable();

    ConflictException result = new ConflictException(cause);

    // add additional test code here
    assertNotNull(result);
    assertEquals("hostel.exception.ConflictException: java.lang.Throwable", result.toString());
    assertEquals("java.lang.Throwable", result.getMessage());
    assertEquals("java.lang.Throwable", result.getLocalizedMessage());
  }

  /**
   * Run the ConflictException(String,Throwable) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:40 PM
   */
  @Test
  public void testConflictException_4()
    throws Exception {
    String message = "";
    Throwable cause = new Throwable();

    ConflictException result = new ConflictException(message, cause);

    // add additional test code here
    assertNotNull(result);
    assertEquals("hostel.exception.ConflictException: ", result.toString());
    assertEquals("", result.getMessage());
    assertEquals("", result.getLocalizedMessage());
  }

  /**
   * Run the ConflictException(String,Throwable,boolean,boolean) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:40 PM
   */
  @Test
  public void testConflictException_5()
    throws Exception {
    String message = "";
    Throwable cause = new Throwable();
    boolean enableSuppression = true;
    boolean writableStackTrace = true;

    ConflictException result = new ConflictException(message, cause, enableSuppression, writableStackTrace);

    // add additional test code here
    assertNotNull(result);
    assertEquals("hostel.exception.ConflictException: ", result.toString());
    assertEquals("", result.getMessage());
    assertEquals("", result.getLocalizedMessage());
  }

  /**
   * Perform pre-test initialization.
   *
   * @throws Exception
   *         if the initialization fails for some reason
   *
   * @generatedBy CodePro at 11/22/13 12:40 PM
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
   * @generatedBy CodePro at 11/22/13 12:40 PM
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
   * @generatedBy CodePro at 11/22/13 12:40 PM
   */
  public static void main(String[] args) {
    new org.junit.runner.JUnitCore().run(ConflictExceptionUnitTest.class);
  }
}