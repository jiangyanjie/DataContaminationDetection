package hostel.unittest;

import org.junit.*;
import hostel.exception.DataBaseReadWriteException;
import static org.junit.Assert.*;

/**
 * The class <code>DataBaseReadWriteExceptionUnitTest</code> contains tests for the class <code>{@link DataBaseReadWriteException}</code>.
 * <p>
 * Copyright (c) 2013
 *
 * @generatedBy CodePro at 11/22/13 12:41 PM
 * @author sandeep
 * @version $Revision: 1.0 $
 */
public class DataBaseReadWriteExceptionUnitTest
{
  /**
   * Run the DataBaseReadWriteException() constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testDataBaseReadWriteException_1()
    throws Exception {

    DataBaseReadWriteException result = new DataBaseReadWriteException();

    // add additional test code here
    assertNotNull(result);
    assertEquals(null, result.getCause());
    assertEquals("hostel.exception.DataBaseReadWriteException", result.toString());
    assertEquals(null, result.getMessage());
    assertEquals(null, result.getLocalizedMessage());
  }

  /**
   * Run the DataBaseReadWriteException(String) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testDataBaseReadWriteException_2()
    throws Exception {
    String message = "";

    DataBaseReadWriteException result = new DataBaseReadWriteException(message);

    // add additional test code here
    assertNotNull(result);
    assertEquals(null, result.getCause());
    assertEquals("hostel.exception.DataBaseReadWriteException: ", result.toString());
    assertEquals("", result.getMessage());
    assertEquals("", result.getLocalizedMessage());
  }

  /**
   * Run the DataBaseReadWriteException(Throwable) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testDataBaseReadWriteException_3()
    throws Exception {
    Throwable cause = new Throwable();

    DataBaseReadWriteException result = new DataBaseReadWriteException(cause);

    // add additional test code here
    assertNotNull(result);
    assertEquals("hostel.exception.DataBaseReadWriteException: java.lang.Throwable", result.toString());
    assertEquals("java.lang.Throwable", result.getMessage());
    assertEquals("java.lang.Throwable", result.getLocalizedMessage());
  }

  /**
   * Run the DataBaseReadWriteException(String,Throwable) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testDataBaseReadWriteException_4()
    throws Exception {
    String message = "";
    Throwable cause = new Throwable();

    DataBaseReadWriteException result = new DataBaseReadWriteException(message, cause);

    // add additional test code here
    assertNotNull(result);
    assertEquals("hostel.exception.DataBaseReadWriteException: ", result.toString());
    assertEquals("", result.getMessage());
    assertEquals("", result.getLocalizedMessage());
  }

  /**
   * Run the DataBaseReadWriteException(String,Throwable,boolean,boolean) constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public void testDataBaseReadWriteException_5()
    throws Exception {
    String message = "";
    Throwable cause = new Throwable();
    boolean enableSuppression = true;
    boolean writableStackTrace = true;

    DataBaseReadWriteException result = new DataBaseReadWriteException(message, cause, enableSuppression, writableStackTrace);

    // add additional test code here
    assertNotNull(result);
    assertEquals("hostel.exception.DataBaseReadWriteException: ", result.toString());
    assertEquals("", result.getMessage());
    assertEquals("", result.getLocalizedMessage());
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
    new org.junit.runner.JUnitCore().run(DataBaseReadWriteExceptionUnitTest.class);
  }
}