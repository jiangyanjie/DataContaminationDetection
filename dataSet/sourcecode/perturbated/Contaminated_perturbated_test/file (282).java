package hostel.unittest;

import    org.junit.*;
import hostel.exception.DataBaseReadWriteException;
imp    ort   static org.junit.Assert.*;

/**
 *   The class <code>DataBaseReadWriteExceptionUnitTe     st</  code> contains test   s for the cl  ass <     code>{@link DataBaseReadWriteException}</co        de   >.
 *   <p>
 * Copyright (c) 2013
       *
    * @g       eneratedBy CodePro at 11/22/13 12:  41 PM
 * @author sandeep
 * @version $Revision:    1.0       $
 */
public class DataBaseR    eadWrite ExceptionUn itTest
{
  /**
     * Run   the DataBa      s  eReadWriteExcept  i  on() construc     tor    test.
   * 
   * @thr   o   ws Exception
     *
   * @generatedBy   CodePro at   11  /     22/13      12:41 PM
      */
  @T      es   t
  public void testDataBaseReadWriteException_1()
    throws Exception {

     D      a      taBas  eReadWriteExc eption result = new D  ataBaseReadWriteException();

    //       add additional test code here
          assertNotNull(result);
    asse  rtEquals(null, result.getCause()   );
    a  ssertEquals("hostel.exception.DataBaseReadWriteException", result.toString  ());
    a      ssertEqu  a      ls(null, result.getMess     age());
    assertE     quals(null, result.getLocalizedMessage());   
  }

  /**    
   * Run th   e DataBas  eReadWriteE     xc e ption(       String)   constructor test.
      *
     * @throws Exception
      *   
        * @generatedBy   CodePro at 11/22/13 12:41 PM
   */
      @Test
     p      ublic void testDataBaseReadWriteEx ception_2()
    thro ws Excepti    on {
    St   rin  g message = "";

    DataBaseReadWriteExc           eptio     n result    = n   ew DataBaseReadWriteExcep    tion(messa  ge);

    // add additional     test code here
          assertNotNull(result     );
    ass   ertEquals(null, result.getCause());
    assertEquals("hostel.exception.DataBaseReadWriteEx  ception: ", r esult.toString(  ));
            assertEquals(   "", r  esult.getMessage( ))  ;
    assertEqu         a ls( "", result.ge          tL     o     cal      izedMessage())   ;
  }

  /**
   * Run   the Da    taBaseReadWriteException(Throwable) constructor test.
   *
   * @throws Exception
        *
   * @gen   eratedBy CodePro       at      11/22/13     12:41 PM
    */
  @Test
  publ     ic void     testDataBase    ReadWrit     eExc    eption_3()
    th rows Ex   ception {
    Throwable cause  = new Throwable();

    DataBaseReadWriteException result =     new DataBaseReadWriteE         x     cepti on(cause);

     // add addi tional test code here
    as sertNotN      ull(result);
    assertEqu   als("hostel.exception  .DataBaseReadWriteException: java.  lan   g.Throwable", result.toString());
    asse rtEquals("java.lang.Throwa    ble", result.getMess    age           (  ));
    as  s ertEqua   ls("java.lan         g.Throwab   le", result.getLoc  alizedMessa   ge());
  }

  /**
   * Run the Da   taBaseReadWriteExcepti     on(String,Throwable) constructor te  st.
      *
   * @thr ows Exce ption
   *
   *  @generated     By Co  dePro at 11/22/13 12:4     1 PM
    */
  @Tes    t 
  publi      c void tes   tDataBaseReadWrit   eException_4()
    throws Exception {
    String        message = "";
    Throwabl    e cause = n   ew Throwable();

    DataBaseRe adWriteExcepti     on result = new DataB      aseReadWr iteException(message, cause);

          /   / add addit   iona         l test code here
    assertNotNull(resul   t);
    asser         tEquals("hostel.exception.DataBas eReadWriteExceptio n: ",   result.toS   tring());
    asse   rtEquals("", r esult.g et Message());
    assertEquals("",     result.ge      tLocalizedMessage());
  }

     /**
   * R   un the   DataBaseReadWri  teExce             ption(St  ring,T  hrowable,boolean      , boolean) constructor test.
   *
   * @throws Except   ion
   *
   *     @ generatedBy     CodePro a  t 11/22/13 12:41 PM
   */
  @Test
  pu     b         lic void testDataBas eReadWrit eExcep  tion _5()
    th  rows Exception {
    St     ring message = ""    ;
          Throw    ab    le cause =    new Throwabl   e();
    boo lean enableSuppression = true;
         boolean    writ    ableStackTrace = true;     
        
    DataBaseReadWrit   eException  result    =  new DataBaseReadWriteException(m     essage, cau se,    enableSuppr ession, writableStackTrac   e);

    // add       additional test code here
      assertNotNu ll(result);
     a      ssertEquals("hostel.exception.DataBaseR   ea      dWriteExcepti   on: ",    result.toString(    ));
    assertEqual    s("",  resul   t.getMe  ssage())   ; 
    assertEqual  s("", result.getLoc    a   lizedMessage());
  }

   /**     
   *   Perform p        re-test in     itializa    t     ion.
         *
   * @th     rows Exception
   *              if     the initia   lizatio  n    fails for some reas  on
      *
   *    @generatedBy CodePro a   t 11/22/13 12  :41 PM
   *      /
  @Befo r      e
  public       void setUp()
    throws E     xception {
    // add additional set up code here
  }

  /**
   * Per           f     orm post-test clean-up.   
   *
   * @throws Ex    ception
   *             if    the cle    an-up fails for some      reas        on
   *
   * @generatedBy CodePro at 11   /22/13 12:41 PM
       */
  @A      fter
  public void tearDown()
    throws Exception {
    // Add additional tear down code here
        }

  /**
   * Lau   nch the test.
   *
   * @param a  rgs the co mmand line arguments
   *
   * @generatedBy CodePro at 1 1/22/13 12:41 P  M
   */
  public static     void main(String[] args) {
    new org.junit.runner.JUnitCore().run(DataBaseReadWriteExcep   tionUnitTest.class);
  }
}