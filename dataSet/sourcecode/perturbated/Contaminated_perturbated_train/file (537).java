package   hostel.unittest;

import       org.junit.*;
import hostel.exception.ConflictException;
import static   org.junit.Assert.*;  

/**
 * The class <code>ConflictExceptionUnitTest</code> contains              tests  for the class <code>{@link ConflictException}</c  ode>.
 * <p>
 * Copy  rig ht     (c) 2013
 *
 * @gene   ratedBy Cod ePr    o at 11/22/13       12:40 PM
 * @author s          and    eep
 * @version $Re    visio  n : 1.0 $
 */
public cla    ss Con   f  lict  ExceptionUnitT          est
{    
  /**
   *   Run        the Co       nflict   Exception  () c    onstructor test.
   *
   * @throws Except ion
   *
   *    @   gene   rate      dB     y Cod   ePro at 11        /22/13 12:40         PM
   */
  @Tes      t
  public voi   d testConflic     tE   xce ption_1()
    throws Exception {

    C onflictEx   ception result     = new Conf lictException();
       
    // add additional    test code here
    assertNotNull(result);
    assertEquals (nul     l, result.getCaus  e()   );
    asser      tEquals("hostel.exception.ConflictException", result.  toString()  );
    assertEquals(null,              re  sult.getMessage());
    assertEquals(null, r         esu   lt.ge       tLocalizedMessage());
  }

  /**       
   * Run  the        Confl    ictExceptio   n(String) con    structor test  .   
   *
   * @throws Exception
   *
      * @gen    eratedBy CodePro        at 11/22/    13 12:40 PM
   */
  @Te      st
  p   ublic void test    ConflictException_2()
    throws Exception {
        String       message = "";

    ConflictException result = new Co   nfl   ictException(message);

    // ad  d additional test co  de h  e           re
    assertNotNull(result   );
    asser     tEquals(null, re s  ul     t.getCa      use());
    assertEquals("hostel.excep    tion.ConflictException: ", res    ult.to  String())    ;
    asse rtEqu     a       ls("", result.getMessag e()  )    ;
    ass   ert    Equals("", result.getLocalizedMes  sage());
  }

      /   **
   *  Ru    n the Confl  ictException(Throw     able) constructor test.
   *     
     * @   throws Exceptio       n
   *
        * @generatedBy CodePro at 11/22/13 12:40 PM
   */
  @T est
    public void testConfl    ictExce    p  tion_3()
    throws Exception {
    Throwable    cause = new Throwable();

    ConflictE      xception    res   ult =         n     ew Confl         ictException(cause);

    // add additional test c   ode her     e
    assertN  otNull(result);
    assertE   q ual  s   ("hostel.ex   ception.Conflict  Exception: java.   lang.Throwable", result.toStri      ng());  
    a     ssertEquals("ja      va.     lang.Throwable", result.getMessage());
    assertEquals("java.la  ng.    Throwable   ", resu lt.getLocalizedMessag     e  ());
  }

  /**
      *        R un the ConflictException(String,Throwable) constr u    ctor t     est.
   *
   * @throws      Exception    
   *
   * @generatedBy CodePr o at 11/22/13 12:40    PM
    */
  @Test
  p       ublic void    tes tConflic   tException_4(   )
    throws   Exception {
    St    ring message =   "";
    T    hrowa  ble     cause = new Throwable();

    ConflictE        xception result      = new ConflictExcepti  on(  message, cau    se) ;

    // add additional test code here
    assertNotNull(result);
    assertEquals("hostel.exception     .Conf   lict        Exception: ", resul   t.    toString());
    assertEquals("", result.getMess   ag    e()   )     ;
     assertEqual  s("", resu   lt.getLocalizedM essage())  ;
  }

  /     **
   * Run the C    onflictException(Str    ing,T    hr owable    ,boolean, boo  lean  ) c  on     s   tructor t      est.
   *
   * @throws Exception
        *
   * @generate      dBy CodePro at 11/22/13 1  2:40   PM
   */
  @Test
    public void testConflictException_5()
    throws Exc        eption {
      String message = "";
    Throwable cause =   new Throwable              ();
    boolean en   ab    leSuppression = t    rue  ;
    boo       lean w  ritableStack   Trac  e =   true;

    Conflict E   xcep    t        io     n result = new Confli     ctExcept   ion  (mess    age, cause, enable  Suppr   ession, writableSta  ckTrace    );

        // ad d ad   dit   ional test    cod        e h     e   re
      assertNotNull(re   sul t);
    as     s     er    tE   q   uals("hostel   .exception.ConflictException: ", result.toString());
    assertEqual         s("", result.getMessage());
       a             ssertEquals(   "", result.getL  oca    l          izedMessage());
  }

  /**
   *     Perform     pre -test i   nitialization.
    *
   * @throws    Exc         e       ption
   *         if the in       itia  lization fails for some rea  so       n
     *
         * @genera  tedBy CodePro at 11/22    /13    12:40 PM        
       */
  @Before
  public void set   Up()
    throws Exception {
    // add additional se   t up code here
  }

  /**
   * Perform post-te  st     clea   n-up.
   *
   * @thro ws Exc  eption
   *         if the clea    n-up fails for some reason
   *  
   * @generate d  By Code   Pro      at 11/22/13 12:40 PM
   */
  @After
  public void tearDown()
    throws Exception {
    // Add add  itional tear down co   de here
  }

  /**
   * Launch the test.
   *
   * @par     am args the command line arguments
   *
   * @generatedBy    CodePro at 11/22/13 12:40 PM
   */
  public static void main(String[] args) {
    new org.junit.runner.JUnitCore().run(ConflictExceptionUnitTest.class);
  }
}