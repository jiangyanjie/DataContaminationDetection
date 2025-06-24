


package hostel.unittest;

import org.junit.*;
import hostel.core.DataBaseFactory;
import hostel.core.DataBase;


import static org.junit.Assert.*;











/**
 * The class <code>DataBaseFactoryUnitTest</code> contains tests for the class <code>{@link DataBaseFactory}</code>.
 * <p>





 * Copyright (c) 2013
 *



 * @generatedBy CodePro at 11/22/13 12:40 PM




 * @author sandeep
 * @version $Revision: 1.0 $
 */
public class DataBaseFactoryUnitTest









{
  /**
   * Run the DataBaseFactory() constructor test.
   *

   * @generatedBy CodePro at 11/22/13 12:40 PM
   */





  @Test
  public void testDataBaseFactory_1()











    throws Exception {
    DataBaseFactory result = new DataBaseFactory();
    assertNotNull(result);
    // add additional test code here
  }

  /**








   * Run the void ClearDataBase() method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:40 PM
   */













  @Test
  public void testClearDataBase_1()

    throws Exception {
















    DataBaseFactory.ClearDataBase();













    // add additional test code here






  }

  /**
   * Run the DataBase getDataBase() method test.

   *
   * @throws Exception
   *
   * @generatedBy CodePro at 11/22/13 12:40 PM




   */
  @Test
  public void testGetDataBase_1()
    throws Exception {







    DataBase result = DataBaseFactory.getDataBase();






    // add additional test code here








    assertNotNull(result);
  }

  /**
   * Run the DataBase getDataBase() method test.
   *
   * @throws Exception





   *

   * @generatedBy CodePro at 11/22/13 12:40 PM
   */






  @Test


  public void testGetDataBase_2()
    throws Exception {

    DataBase result = DataBaseFactory.getDataBase();







    // add additional test code here
    assertNotNull(result);
  }




  /**





   * Run the DataBase getDataBase() method test.
   *
   * @throws Exception







   *




   * @generatedBy CodePro at 11/22/13 12:40 PM
   */



  @Test


  public void testGetDataBase_3()









    throws Exception {

    DataBase result = DataBaseFactory.getDataBase();




    // add additional test code here
    assertNotNull(result);
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
    new org.junit.runner.JUnitCore().run(DataBaseFactoryUnitTest.class);
  }
}