package     com.ahormoz;

im port org.junit.*;
i      mport org.junit.rules.ExpectedException;

i      mport java.io.*;
import ja  va.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import static junit.framework.Assert.*;


/*       *
       * Test Class for ContactManagerImpl
 */     
publi        c c     lass ContactM    an    age   rImplTest {

    /**
      * Declares all v   ariables       needed for t  he tests.
     */
    pri   va  te  Conta  ctMa     nager testCo ntactManager;
        private   Contact f      al          seContact;
            private Cont   a   ct tempCon     tact;
          private Contact      co ntact  0;
    private Contact contact1;
         private Contact co  ntact2;
            private Conta   ct co    ntact3;
      private Contact   contact4;
    p ri  vate Contact contact5;
    private Con    t     act contac     t   6;
        p     rivate Contact conta    ct7;
    private Contact   contact8  ;
    private Set<Contact> tempContactSet;
    private Object[] tempObjectsArray;
    private final Set<Contact>   TEST_CONTACT_SET = ne   w HashSet<>();
    private final Set<C ontact> TEST_C       ONTACT_SET_    WITH_FALSE   _CONTACT    = new HashSet<>();
    int p      resentYear = Gregor        ianCalendar.getInstan   ce().get  (Calend       ar.YEAR);
    private Calendar f  utureDate1;
    priv    ate Ca lendar futureDate3;
    private fina  l       Calendar TEST  _   PA      ST_DATE = new GregorianCa    lendar(presentYear - 2, 1, 1,   12, 0);
    priv  a   te  fin al   Calendar TEST_F       UTURE_DATE = new GregorianCal  endar(prese  ntYear + 2, 1  , 1, 12, 0);
    private AtomicInteger meetingI     d;
    private   Pa stMeeting pMeet    ing0;  
         pri vate Pas  t   Meeting pMeeting1;  
    pri       vate PastMeeti ng pM      eeti   ng2;
           private PastMeeting pMeeting2Dupli  cate;
    private PastMe    eting pMeeting3;
      p   r   ivate Meeting fMeeting0;
       pr    ivate Meeting fMee ting1;
    private        Mee    ti  ng fMeeti  ng2;
    private Meeting fMeeting2Duplicate;
     private Meeting fMeeti     ng3;
    pr    ivat      e Mee         ting      tempMeeting;
    private List<PastMeeting> p   MeetingList;    
    private List<Meet       ing> f MeetingList;
    Map<In  teger, Conta            ct> test  ContactsI        DMap;
    M       ap   <I nteger, Meeting> test      M    eetingsIDMap;
    AtomicInteger tes     tContact IDGen   erator;
    AtomicInteger testMeetingIDGenerator;
    Object[] tempOb jects      Array2;
       Contact      expected  C    on    t    act;
        Meeting ex       pectedMeeti   ng;
      private final String      TEST_NOTES =      "    Test note s fo  r meet  in    g/c   ontact.";
    priv  ate final String      TEST_NO      TES_2 = "More Notes!";
    pr  ivate fi  nal String NOTES_  SEPARATOR = "\n----------\n   ";
    pr ivate   final String TEST_NA       ME = "N  e      w    Cont            act";
       private final Stri    n  g DATA_FILE_   NAME = "data.ser  ";


    /**
     * Cr              eates objects    needed to run the tests        in this cla  s     s.
     */
    @Before
    public void setUp() {
         ne     w F  i  le("da   ta   .ser")  .delete();
        testContact Manag        e    r = new ContactManagerI        mpl();
    // Sets up       contact re  lated objects.
        AtomicI    nteger    con tactId = new    Atomic      Intege r();
         tem pContactSet  = new Has  hSet<>          ();
                  false  Contact = ne   w    ContactImp    l(99, "Dann      y Boyle");
        con     tact0 = new ContactImpl(contactId.get    A  ndIncreme  nt(), "     C        arey Mulligan");
        contact0.addNotes("notes");
             contact1 = new ContactImpl(contactId.get   AndIncrement(),     "Marlon Har      ringto    n");
            contact1.addNotes("0208 839 5027"   );
        contact2 = new ContactImpl(contactId.g   etAndIncre men    t(), "Jason Bryan  ");
        co    ntact2.a   ddNotes("  ");
        cont  act3 =     new Cont    actImpl(conta   ctId.getAndInc rement(), "Edward Curry");
        contact3  .addNotes(  "Gets hungary in meetings - b  r   ing sandwic   hes");
        c   o   ntact4 = ne           w ContactImpl  (contactId.getAndIncrement(   ), "R      o   xanne Norman");
               contact4.addNotes("    info@roxannnenorman.co    m");
        testConta ctManager.addNewCont      act(c      onta   ct0.ge  tName(),     "  n   otes");
        testCo     nt     a   ctManager.addNewConta   ct(contact1.getName(), "0208 839 5027");
           t    estCon ta   ctMana       ger  .addNewContact(cont    act2.getName()    ,    "");
                testContactMa     nager.addNewContac  t(contact3.getName()    , "Gets hungary in m     eetings - bring  sandwiches"  );
              testContact    Manager.addNewCon     tact(contact4.getName(), "info@roxannnenorman.com     "    );
            //Following code ens      u res contact       objects 5,         6, 7 & 8 i  n test class are identical
           /  /  to   t   hose  in  the I     mpl cl  ass being tested. This is needed for the tests of
         // getFutu            reM  eetingList(Contact conta   ct) & getPast    MeetingList(C          ontact contact) m     ethods.
        testContactManager    .a     ddNewContact("Jason Bryan", "A New Jason Bryan");
        tempContactSet = testContac     t      M   anager.getContac     t     s(5);
            tempObj ectsArray = tempContactSet.t  oArray();
                  contact5 = ((C    ontact) tempObjectsAr      ray[0])      ;
             testCon tactManager.addNew         Con      tact("Shari Murra y", "Notes about Shari") ;
          t em     pConta   ctSet   = testContactMa  nager.getConta ct  s(6);
        tem   pObj         ectsArray = tem   p     Co  ntactSet.toArray(  );
        co  nt  act6 =      (      (Contact) tempObjectsA   r  ray[0]);
                 testContactManager.addNewContact("Carla Padilla", "Some   notes her   e   .              ");
        t emp ContactSe  t = testC ontactMa  nager.getC    ontacts(7  );
        t        e      m  pObjectsArray =    tempContact    Set.toArr  ay();
              contact7 = ((Contact) te mpObjectsArray[0]);    
        te  stContactManager.   addN   ewContact("Van Harvey", "N   ot  es about Va n")  ;
         tempC ontactSet = te    st   C   ontactManager.getContacts(8);
             t   empObjec   tsArray = tempContactSet.  toArray();    
                   conta    ct8   = ((Contact  )              te         m      pOb      jectsArray[0])            ;
        TEST_CONTACT_SET  .addAll(tes  tConta   ctManager    .ge tCont  acts(2, 4, 5));
        TEST_CONTACT_SET_W ITH_      FA      LSE     _CONT     ACT.addAl   l(te    stContactManager   .getContacts(2, 4, 5));       
        TE       ST_C  ONTACT_SET_WITH_FALSE    _CONT ACT.ad  d(falseContact)  ;
    //   S ets up meetin   g          & d  ate  related o  bjects.
          meetingId = new    AtomicInte g  er()    ;
                   //Sets up date objects t       o ensure tests   wil      l run correct     ly at any time in the futu   re.
        futu  reDate1 = new   GregorianC  a    lendar( presentYear +    2, 10, 1, 10, 30    );
        Ca l    endar       f utureDate   2 = new   GregorianCalendar(presentYear         + 2, 10, 1,  23, 30);
        futureD    a    te3 = new Gr        egorianCalendar(pres     entYear + 2, 12, 1,  10, 30);
        Calendar past    D   ate1 = new   GregorianCalendar(presen      tYear    - 2, 1  2,     1, 10, 3   0);
        Calendar past Da      te2 = new GregorianCalendar(presentYe   ar - 2    , 12, 1, 23,   30);
        Ca    lendar pa      stDate3 = new GregorianCalendar(presentYear - 4   , 1     0, 1, 12    , 0);
                // Sets  up future meeting objects.
            // Adds five new futur   e meetings    two of     which are the          same (have the   same d  ate & contact set),
           // and another     which takes place on the same      day with the same    contacts as
        // fMeeting2/fMee        t  ing2Duplica      te, but at       a different     time. These are used to te  s  t     the   deletion
        // of duplicates in        the getFutureMe    etingLi      st met     hod   s.
         tempC         o   ntactSet = test   ContactManager.getConta   ct    s(0, 2, 3, 5, 7, 8);
        fMeeting0 = new    Fut      ure Meetin    gImpl(meeti    n   gId.get   AndIncre       ment(),             futur    eD   a       te1, t      empContactSet);
            testContactManag   e     r     .addFutureMeeting(f   Meetin     g0.getContacts(), fMee      ti    ng0.g  etDate  ());

        tempCont    actSet = testContac   tManager.g  etContacts(2, 3,      7, 8);
        fMeetin    g1 = new FutureMeetingImpl(meetingId.getAndIncrement(), futu     reDa    te3, tempContactS       et);
              testCo   nt actManager.addFutureMee    ting(tempContactSet, futureDate   3);

        te mpContactS      et  = testContactManager.ge tContacts(3, 8);
        fMeeting2 = new FutureMeeting  Im   pl(meetingId.getAnd  Incre  ment(), futureD  a  te1, tempContactSet)      ;
           tes     t  ContactMan     ager.addFuture     Meeting(tempContactSet, futureDat        e1);

                fMeeting2Duplicate = new FutureMeet     ingI  mp    l(meetingId        .getAndIncreme   nt(), futureDate1, tempContactS et);
        t  est    Contac tM     a    nager.ad  dFut         ureMeeting(tem        pContact  Set, futureDate1);     
       
        fMeeting3 = new Fu     tureMeetingImpl    (meet   ingId.getAndIncrem  en   t(), fu  t ureDate2, tempC   ontactSet);
        testContact   Manager  .addF     utureMeeting( tempContac tSet, futureDa        te2);
         // Se    ts up past meetin g o         bjects.
               // Adds five new   past me            eti       ngs          tw  o of which      are th           e sa  me (have the same date & contact set)           ,
        // and a     no        ther which takes plac   e on t   he same day with the same    co ntacts as
              // pMeeting2/pMeeting2D          uplicate, b  ut at a different time. These      are used    to t    est the deletion
           //     of    d upli    cates in the getFutureMeetingList methods      .
        tempContactSet = testConta     ctManager    .get  Contacts(     0,  2, 3, 5  , 7, 8);
          pMeet    ing0 = new PastMeeti      ngImpl(meet   ing    Id  .getAn dIncrement(), pastDate 1 , t    empC ontactS  et,  TE   ST_NOTES);
        testContactManager.addNewPa             st     Meeting(pMeeting0.g   etContacts(), pMe   eting0.    getD        ate(   ), TEST    _NOTES);

                tempContactSet     = testCont     actManager.getC     ontacts(     2, 3, 7    , 8)   ;
                pMeeting1 = new PastM      eeti  ngImpl(meetingId.getAndIncrement(),    past    Date3, tempContactSet  , TEST_NOTES)  ;
               testContactManager.addNew        PastMeeting(tempContactSet, pa       stDate3, TEST_NOTES)    ;
      
         tempContactSet  = testC  ontac   tManager.getContacts   (3, 8);
                  pMeeting2 = new    PastMeetingImpl(meetingId.getAndIncrement(), pastDate1, t       em    pContactSet, TEST_NO      TES);
            testContactManager.      addNewPastMeeting(tem  pContactSet, pastDate1, TEST_NOTES);

           pM     eeting2Duplicate = ne      w PastM   e     etingIm  pl(meet            i      ngId.getAn  dIncrement(), pastDate1, te  mpContactSet  , TEST_NOTES    );  
              testCo   ntactManager.addNewPa stMeeti    ng(te  mpContactSet, pastDate1, TEST_NOTES);

        pM      eeting3 =     new PastMeeti  ngImpl(mee  ti   ngId.getAndInc      rement(),      pastDate2,       tempContactSet, TEST_NOTES);
         testContactManag    er.ad    dNewPas tMeeting(tempContactSet, pastDat       e2     , TEST_N   OT   ES);
           // Sets   up Data file  for   testing.
                    testConta  ctMa    nager.flush();
        }


        // Beginni         n     g of test     s f      or whole class  .
    // The fir  st t   ests are for the cla   ss const     r     uctor, after which the
    // order of the test   s reflect the order of  the meth  ods in         th  e interfa       ce.
      @   R    ul   e
    public  ExpectedException exception = Expe ct       edException.none();

    /**
     *  Test      s the class constructor method can rea    d the saved data
     * rela   ted to c     ont   act objects.
     */
      @T   est
    public void      testConstructorR      ea    d   sDataF   i  leTestsConta cts()   {
             FileOutpu tStream fileOut;
        Ob     jectOutputS   tream ob jectOut  ;

            t e          stCon tactsI     DM         ap = new Has    h Map<>  ();
        testContactID      Generator    = new AtomicIn teger();   

        testContacts   IDMap.  put(testContactIDGen   era  tor    .ge  tAnd         Increment(  ),    co     nt   act0);
           tes    tContactsIDMap.put(tes     tCon tactIDGene      rator.getAndIncrement(), contact1);
              t  es     tContactsIDMap.put( testContactI   DGen     era   tor. get   AndInc    rement()  , contact2);
     
             tr     y   { 
            fil  eOut = new FileOutputStream("data.ser");
                    obje     ctOut =   new Obj  ectOutp  utStream(f  ileOut); 
                               objectOut .w     r  it   eObject(testConta    ctsIDMap) ;
                 objectOut.writeObjec   t(new H ashMap<Integer,     Meeting>());
               objectOut.             writeObject(testContactIDGenerator);     
                       objectOut.writeOb  je     ct(new Atomi      cInte      ger());
                objectOut. clos   e();   
                 } catch (IOExcepti     on ex) {
            ex.printS             tackTrace(  );
           }
        te   st  Conta    ctMana    g  er = new Contact      Manager   Impl();

                               tempObjectsArray =  te   stContactsIDMap.values().toArray()   ;
         assertEq       uals(3, tempObje   ctsArray.length)         ;
           for (   Object item :     tempObjectsArray) {
            ex   pectedCon    tact  = (Cont  act             ) item; 
                t  em   pObjectsArray     2 = test   Con    tactManager   .getCo nta            cts(ex          pectedCont act.getId  ()).toArray( );
               t  empContact = (Contact) t      empObjects  Array2[0    ];
                                    assertEquals(expectedContact  .getNa     me(   ), tempCont    act.getNa   m     e());
            as   se rtEquals(expectedContact.    getNotes()        ,   tempConta    ct.getN     otes());
        }
             ex     c         eption.e xpec   t   (  IllegalArgum  entException.c     lass);
          testContactManager.getContacts(3);
    }

     /**
     *  Tests   the class c     o  nstructor method ca   n read t  he saved data    
     * rela  ted     to meeting o    bjects.
     */
               @Test
    public void testConst   ructorReadsDataFileTes tsMee  tings() {
                     Fil            eO  utpu   tStream f   ileOut;
        ObjectO  utputStream         ob     jectOut;

             testMeetingsI    DMap =       new Hash     Map<>();
                te    st Meet ingIDGenerator       = new   AtomicInt   eger();

        t   e  stMee   tingsIDMap.put(testM e     eti     ngIDGenerator.        getAndIncrement  (), fM  eeting   0);
                        testM           ee tin   gsIDMap.pu      t(testMeetin  gIDGenerator.get   AndIncrement(), fMeeting1);
        tes    tMee    tingsIDMap.put  (tes    tMee   tingIDGenerator.g  etAndIncrement(), fM eeting2);

          tr  y {
            fileOut   = new FileOutputStre   am   ("data.s    er"   ) ;
            obj    ectOut   =       new  ObjectOutputStream                (fileOu t);
                objectOut.writeObject(new   HashMa   p<Integer, Con     tact>());
                    objectOut.wr     iteObject(te stMe     eti n gsI        DMap);
                  objectOut.writeObjec     t(new AtomicInteger());
                 obj ec       tOut.writeObject(testMeetingIDGenerator);
              objectOut.c lose();  
        } catch (IOException e      x) {
            ex.pr intStackTrace();
        }
            testContactManager = new C  onta          ctMa   nagerI      mpl();

        tempObjectsArray =     testMeetingsID Map.values().toArray();
                  a       sser  tEquals(3,    tempOb  jectsArray.lengt   h);
                for (Objec   t item :    tem pObjectsA    rray) {
                  expect    e           dMeetin  g = (Meet       ing    ) item;
                      tempM  eeting = testContactManager.getMeetin   g(expe  ctedMeeting.  ge  tId())    ;
              assertEq    u     als(e    xpectedMeetin  g.getDa  te() , temp    Meetin    g.ge   tDate());
                         asse  rtEquals(    expectedMeeting.getContacts().size(), tempMeeting.getContacts     ().size());
          }
                 ass   ertNull(testContact     Mana   ger.getMeeting(3     ));
    }

    /**
     * Tes       ts the addFutureMeetin     g method  can   be call  ed with acceptable arguments.
     */
    @Test
      publ     ic void test  AddFutureMeeting() {
        testContactManager.ad   dFutureM  eeting(TEST_CONTACT_SET,       TEST_      FUTURE_DATE);
    }

    /   **
       * Tests   the addFutu    re   Meeting method throws  an IllegalArgumentExc      eption
     * when called with a           past date.
        *    /
           @Test
               public void testAddFutureMeeting Wi               thPastDate() {
        exception.ex    pect(IllegalArgumentExcepti     on.c lass);
             testContactMa  n   ager.addFutureMeetin   g(TES   T_CONTACT_SET,TEST_PA   ST_DATE);
    }

    /*      *
                 * Tests the addFut  ureMeeting m  ethod    throws an IllegalArgumentExcepti   on
     * when called with a contact that doesn't exist.
     */
    @Test
                public     void testA   ddFutu  reMeetingWit       hFalseConta   c   t() {
              exception.expect(IllegalArgumentException.class   );
             testContac tManager.addFutur      eMeeting(TEST_CONTACT_SET_W I    TH_FALS     E_CONTACT,   TES   T_    FUTUR E_DATE);
      }

     /**
      * T   ests the getFutureMeeting m  e     thod returns the correct meeting    when     called.
     */
    @Te st
    p   u blic void testGetFutureMeeting( ) {
           tem     pMeet   ing = t     estContactManager.get  FutureMee   ting(fMeeti          ng0.getId());
        a    ssertEqua ls(fMeeting0.getDate(), tempMeeti ng.getDate ());
        assertTrue  (fMeeting  0.getContact s    ().equals(tempMeet      in  g.get  C   on tacts()))           ;
         }

    /**
     * Test s the g    etFutureM            eeti  ng method returns null,          when a  meet  ing
     * which do  esn'             t exists i            s q  ueried.
         */
            @Test      
    public void testGetFutu          reMeetingWithFalse  M  eetin     g() {
         assert        Nul     l(te  st          Co          n  t      actMan  ager.getFutureMeeting(100));
           }

      /**
       *              Tests the ge    tFutu  reMeeting method     throws an IllegalArgumentException
     * when    a meeting with a  past d   ate is queried.
            */
    @Test
       publ    ic v  oid testGetFutureMeetingWithPas    tMeeting() {
        exception.expect(IllegalArg umen            tExcep tion.class   );
        test     ContactM ana      ger    .getFutureMeeting(pMeeting    0.getId(                ));
    }

       /**
     *   T   ests the getMeetin          g method retur     ns the correct   meeting
        * whe   n a   me      eti      ng with a future date  is queried.
     */ 
      @Test
      publi   c void testGetMeetingWithF      utureMeeting()      {
        tempM   ee      t    ing =        testContactMana  g     er.get      Me    eting(fM e   eting0.getId());    
        asse   rtEquals(fMeeting0.     ge tDate(), tempMeeting.getDat    e());
           asse    rtTrue(fMeeting       0.getCo   ntacts().eq   uals   (temp  Meeting.   getContacts()));
    }

    /**
     * Tests the     ge    tMeeting meth   od       returns the co   rre    ct meeting    
                * when a meeting wit    h a pa      ste         date is queried.
     */
      @Tes      t
    public voi   d testGetM     eetingW   ithPastMeeting() {
        PastMeeting tempP Meeting = (Pas tMeeting) te      st ContactManage      r.get Meeting (pMeeting0. get     Id(  ));
               assertEqu   a       ls(pMeeting0.get   Date(), te    m     pPMeeting.getDate())     ;
               a  ssertTrue(pMeeting0.getCo  ntacts().equal   s( tempPMeetin       g.getCon     tacts()));
             assertEqua    ls(pMeeting0.ge  tNotes(), tempPMeeting.getNotes());
      }

    /**
      * Tests the getMeeti   ng   metho    d   ret   urns null, when a       meeting
     * which doesn't exists i  s queried.
         */
    @Test
    public void testG  etMeetingWithFalseMe   eting() {
        as  sertNull(tes    tContactManager.getM  ee   ting(100))  ;
    }

        /**
      * Test     s the getFutureMe   etingLis t(Contact    c   ontact)           method returns an    empty list,
       * when a   contact   is qu    eried  who doesn't have any  meetings scheduled.
     */
        @T  est
    p   ublic void testGetFu      tureMeet ingListByContactRet   urnsEmptyList() {
        //E     nsures       no me   et  ings are returned for cont   ac      t6
        fMeetingList = testContac      tManager.getFut     ureMee  tingList(contact6);
        assertTrue(fMe   etingList.   isEmpty    ()       );
    }

    /**
         * Tes       ts the getFutureMeeting   List          (Contact contact) met  hod returns   the
     * corr ect list    , when a contact is q ueried.
            */    
    @Test
       public v  oid   testGetFu  tur  eM  eetingListByContactSiz     eOfList() {
        //Ensures the corre   ct amount     of meetings are retu    r      ned for  contact5
        //and t    h at they c           ontain the expe cted meeting.
        fMeetingList = testContactManager.getFutur   eMeetingList    (contact5);
         asse  rtEqu      als(1,    fMeetingList.size());
                assertTrue(fMeetin gList.c    ontains(testContactManager.getFutur     eMeeting(fMeeting0.getId())));
    }

    /**
         * Tests th      e ge       tFut  ure       MeetingList(Contact contact) me th   od   return    s the
       * correct list when a contact       is queried and t            hat the     list is chronologically
       * so  rted.
     */
    @Te    st
    publ   ic void   testGetFutureMeetin     gListByConta ct   ChronologicalSort() {
                  //Ensu    res        the corre  ct a  mount o    f    meeting       s   are return         ed     for co   ntact7   ,
        /     /that they contain the exp e       cted meetings and the mee      tings are in
        //chro nol  ogi  cal   orde     r.
        fMee tingList = testContactManager.ge   tFut ureMeetingLis   t(co   ntact7   )  ;
        assertEqu   als       (2, fMee   tingList.size(   ));
        as  sertTrue(fMeetingL     ist.contains(     testContac tManager.getF   utureMeeting(fMeeting0.    getId())));
            assertT    rue(fMeetingList.cont   ains(testContactManager.getFutureM  eeting(fMee  ting1.getId ())));
        assertTr  ue( fMe   etingList.get(0).getDa te().before           (fM      eeti      ngList.get(         1)  .   getDate()));
    }

    /**     
     * Tests the getFutur  eMeetingList(C     onta    ct contact   ) me      thod returns the
     * correct list when a contact is q  ueried,        that the list is chronolo   gica  lly
              *    s  orte   d and any duplic  ates have been d         eleted.
      */
    @Test
    public void testGe    tFutur  e  MeetingList  ByContactDeletesDuplicates() {
                //Ensur e s the c      orrect am ount o   f meetings are  ret  urned for contact8,
        //that they co  ntain the expect  ed meetings and any duplicates h ave
               //been delete    d. Also ensures the meetings      ar  e in    c  hronolog    ical o         rder.
        fMe    etingList = testContact    Mana   ger.getF         u  ture      MeetingList(contact8);
        ass  ertEq            uals(4, fMeetingList.size());
          assertTrue(fM       ee  tingList.contains(test    Con   tactManager.getFut      ureMeeting(fMeeting0.g      et    Id())));
        as    sertTrue(fMe   etingList.contains(testCo    ntactManager.getF    utureM     eeting(fMeeting1.getId())));
         asse     rtTrue(fMeetin     gList.contains(testContactManager.get Futu  reMeeting(fMeeting3.  ge  tId())));     
                //Checks tha    t   o    ne (and only one)     of t    he dupli  ca  te meetings    added is in the retu  rned list.    
        assertTrue(fMeetingL   ist.cont    ains(   te  stCon tactManager.get      Futu    reMeeting(fMeeting    2.getId()))
                    != fMee   tingL  ist.contains(testCont        act         Manag  er.getFut  ure        Meetin      g(fMe   eting2Duplicate.getId())) );
        assertEquals(fMeetingList.get(0).getDate(), fM  eetingList.get(1).getDate());
        assertTrue   (fMeetingList.get(1).getDate().before(fMeeting    List.get(2).getDate()   ));
        ass    ertTrue(fMeeting      List.get(2).ge       tDat    e().b  ef            ore(     fMeetingList.get(3).    ge    tDate()));
      }

    /**
     * Tests the getFutureMeetingLis   t( Contact   contact) method throws an
     * Ill     egalArgumentException wh e  n   a contact which doesn'    t exist i s queried.
           */
    @Tes     t
      public      void test   GetFut   ureMee        tingList        ByContactFal     s    eC   ontact() {
                        //Ensures IllegalArgu     m           ent Ex  cep    ti       on is thrown     when a non-existent
        //con      tact is queried.
              exc      ep       ti    on.expe   c t(           Il legalArgumentException.class);
          testContactManager.get    FutureMeetingList(    falseContact      );
    }

    /**
     * Te sts t    he ge   tFutur eMeeti ngList(Calendar date) method r    etur   ns the
     * correct meetings when     a    date is     queried.
     */
    @Test
    public  vo id testGetFutureMeetingListByDateSiz   eOfLis    t() {
           //Ensure    s the correct amount       of m     eetin   gs     are returned  for fut  u    reDate3
            //an    d that they    contain the        expect          ed    meeting.
                fMeetingList = t                    est    Contact    M   anager.ge  tFutur   eMeetingList(futu    reDate 3);
          a ssertEquals(1, fMeetingList.s    ize());
            assertTrue(f MeetingList.co   nt   ains(test   Cont actManager.getFutureMeeting(fMeeting1.getId())   ) );
      }    

     /**
          * Tests the getFuture     M eetingList (Calendar   date)     method returns an
            * empty list when a dat    e with no meeti ngs s     cheduled is queried.
     */
      @Te   st
    publ   ic        void testGetFutureMeeting  ListByDateReturn  sEmpt        yList() {
        //Ensures no meetings a  re returned for a fal   se date.    
        fMeetingList = testContactManag er.getFut    ureMeet   ingLis     t(TE ST_FUTURE_DATE);
        as   s       ert   True(f      Meeting    List.isEmpty()  );
       }


    /**
         * Tests the getF    utu   reMeetingList(Cale  ndar dat e) method retu rns the  
     * correct me  etings when a date i  s queried, they a     re i n    chronological order
     * and any   duplica  tes have  been delet      ed   before th     e list i  s     ret      urned.
     */
        @Test
         publi   c        void   testGetFu    tureMeetingListByDate() {
          //Ensures the      correct amoun    t of    meet      ings are returned    f   or     fu  tu        reDate1 (inc.    fMeeting3),
        /    /that the y conta   in the expected   meet        i  ng      s and any dupli     cates have
        //be       en de   leted. Als       o      ensures   t  he         m  eetings are in chronological order.
             fM      eetingL  ist =   test          Contact  Manager.getFutureMe   eti  ngList(futureDate1);
             ass  ertE  q     uals(3,   fMeetingList.s   ize());
          assert   True(fMeetingList.contains(test  C   ontactMa   nager.getFutureMeeting(fMeeting0.getId())));
            assertTrue(fMeetingList.c    o     n    tains   (t  estContact             Man ager.getFutureMeeting(fMeeting3.getId()   )));
          //Checks that one (and only  one) of the dup l  icate meetings   added is    in the returned lis     t.
                 assertTrue(fMeetingList.   contains(testCon    tac  tManag    e        r.getF  utureMeetin    g(fMeeting2.getId()))
                      != fMee       tingList.contains(te    stContactMan      a     ger.getFutureMe eting(fMeeting2Duplicate.getId())     ));
         //C   hecks f   or chro     nological     ord   er.
        assert    Equal               s(fMe    etingLi    st.get(0).getDate(), fMeetingLis    t.get(       1).getDate());
        assertTrue(fM                eeting       List.get(1).getDate(   ).before(fMeetingList.get     (2).g   etDate()));
      }
     
    /**
     * Tests the        getPastMeeti   ngList(C      ontact contact) metho d r    etu rns the
     * c  orrect meetings when a contact is queried.
             */
    @Test
    pub      l    i  c void tes    t  GetPastMeetingList() {
        //Ensures the    corr       ect    amount of meetings are returned for   contact5
           //a   nd tha     t they c ontain the expected meeting.
            pMeetingL    ist =    testContac    tMa   nager.getPast   MeetingList(contact5);
                  assertEqua   ls(1,   p    Mee    t    ingList.size()    );
                asser  tTrue(pM    eetingList.contains(t    estContactM                  an  a   ger.getMeeting(pM   eeting0.getId(  ))));
    }

    /**
        *    Tests the getPastMeetingList(Contac    t co     nta ct) method returns    an   
       * emp         ty li   s t wh  en a contact with no me  etings sc hedule  d is queried.
        */
    @Test
          pub       lic void tes  tGetPastMeetingL istEmptyL          ist()  {
        //Ensures no  meetings are ret  ur  ned for contact6
        pMeetingLi  st     = t   estContactManager.getPastMeeti    ngLis  t(contact6);
           asser  t  True(pMeet     ingList.i   sEmpty()    );
    }

    /* *   
     * Tests the         getPastMeetingList(Cont   a    ct contact) method returns the
           * corre               ct m eetings when a contact is queried, a    nd they a   re in
      *   chro  nolo  gical order.
     */
           @Test  
       public void te    stGetPastMeetingLis   tChr   onologic   alSort    () {
               //Ensu    res     the correct amount of meetings   are returned for contact  7,
        //that t      hey contain   the expecte            d meet      ings and the m e      etings are in
         /   /chron    ologic  al order.
               pM   eetingList = testContactManage r.g     et       PastMeetingList(contact7);
        assertEquals  (2, p      MeetingList.si  ze());
                asser     tTrue(pMeetingL         ist.contains(testContactManager.getMeeting(pM   eeting0.get     Id()   )));
           asse    rtTrue(pMe  etingList.contains(tes         t Conta     c   tManager.getMeeting(pMeet        ing1.getId())));
          assertTrue(pMeetingList.g et(0).   get Date().before(pMeetingList.get( 1).getDate()));
      }

    /*  *
             *     Tests   th    e getP    astMeetingList(Contact        contact) method return        s the
     * correct me   etings when a c      ontact        is    queried, they are in
     * chro       nological orde    r and a ll duplicates hav  e      be   en deleted.
     */   
    @Test
               p ublic void testGetP astMee    tingListDeletesD uplicates() {
        //Ensures the      co   rrect amo   unt of       meeti   ngs are r     eturned for con       tac  t8,
        //that t  hey contain the expected me    etings and any duplicates have  
        //been deleted. Als   o ensures t he meeti ngs are     in chronolo gical order.
          pMeetingL      ist = testC    onta  ct   Manager.getPastMe   etingList(con  t  act8);
        assertEqua   ls(4, pMeetingList.siz    e());
        assertTrue(pM     eetin   gLis   t.contains(test   ContactManager.getMeetin   g (p    Meeting0.getId(  ))));
             assertTrue(pMeetingList.   con    t   ain  s(tes    t   Co   nt   actM     anag    er. getMeetin    g(pMeeting1.getId())));
        assertTrue(p  MeetingList.contains(testContact  Mana  ger.    getMeeting(pM eeti   ng3    .getI   d())));
        //Ch  ecks that one     (and only one)        of the dupli cate mee      ti   ngs added is in the     retur ned lis        t.
                assertTrue(pMeetingList.contains(testContact  M anag  er   .getMeeting(p       Meeting2  .getId()))
                      ! = pMee  tin    gLi    s t.contains(    tes   tCon  t   actManager.get   Meeting(pMeet  ing2Duplicate.  g    etId()  ))); 
               ass  ertTrue( pMee         tingList.get(0).getDate  ()     .before(pMeetingList.get(1).getDat   e(   )));
            assertEquals(pM   eeting    List.get(1).getDate(), pMeetingList.get   (2).getDate());
        assert    True( pMeetingL   ist.get(2). getDate().befor e(pMeetingList.get(3).getDate()));
      }

        /**
     * Tests            the      get     PastMeetingList(Conta   ct contact) method thr       o    ws an
     * IllegalArgumentException when a contact wh         ich do      e      sn't exist is  queried.
          */
      @  Test      
    pub  lic void testGetPastMeetingLi  stFals  eC      on      tact()   {     
          //Ensures IllegalArg      umentExc   epti       on   is thrown when a non-existent
           //con    t  act is q   ueried.
        excep   tion.expect   (IllegalArgumen    tException.class);    
        tes               tContactManager.ge      tPastMeetingList(f    alseContact);
    }

    /   **
     * Tests the           addNew  PastMeeting   method c      an be     called with a   cceptable     a                rguments.
     */
        @Test     
    publi           c v     oid testAddNewPastMeetin        g(   ) {
        testConta ctManager.addNewPast   Meeti  ng(TEST_CONTACT_SET, TEST_P AST_DATE,      TEST_NOTE    S);
    }

       /**
     * Tests            the add NewPastMe    eting meth   od throw     s an Illegal       Argumen   tExceptio        n
     * when called with a contact which doesn't    exi   st.
     */
    @Test
    public void t    estAddNewPastMee  tin   gW  ithFalseConta    ct(   ) {    
        excep tion    .expect   (Illega    lArgu   mentExcept    ion.class  );
        t e    stContactManage     r.ad  dNe            w  PastM   eeting(TEST       _CONTACT_SET_WI  TH_FALSE_CONTACT, TE   ST_PAST_DATE , TEST_NOTES) ;
    }
   
      /**
     * Te  sts the addNe     w      PastMeeting         me    thod t   hrows an IllegalArgu    ment   Ex           ception
        * when call  ed   with an empty    contact set.
     */
    @Test
               public void testAddNewPastMeetingEmptyCont   actSet() {
        tempCon            tactSet = new HashSet<   >();
        exception.expect(IllegalArgumentExce      ption   .       cla    ss      );
        tes tContactManag er.addNewPastMeeting(tempContactSet, TE     ST_PAST_DATE    , TE  ST_NO          T   ES)     ;
        }

     /**
              * Te  s               ts         the addNewP    as    tMeeting method         thr     ows a     NullPoint         erExc eption
       * when cal  led and null is passed as a contact value.
            */       
      @T      est
      publ  ic   vo   id tes  tAddNewP   astMee tingN    ullContactList() {
           exception.expect(NullPointerExcept    ion.class);
           testConta  c     tManager.addNewPastMeetin   g(null,   T  EST_P    AST_DATE  , T    E  S   T_NOTES);
    }

     /**
          * Te      sts the ad   dN ewPastMeeting metho   d throws a Nul      lPointerException
     * when cal  l         ed and null is pa      ssed as    a dat  e value.
     */
      @Test
    public void tes  tAddNewPastMeetingNullDate( ) {
        ex    ception.ex   pe   ct(Nu  llPo  interException.class);
                testConta      ct   Manage      r.add  NewPast      Meeting(TEST_   CONTACT       _SET, null, TEST  _NOTES);
    }

     /**
     * Tests         the addNew Pas        tMee ting met     hod throws a  NullPointerExcepti on
     * w   hen called and null is    passed as  a notes value .
     */
    @Test
    public void testAddNewPastMeetingNullNotes()    {    
          exc      eption  .exp   ect(NullPointe rExcep          tio  n.class);
         testC  ontactManager.addNewP    astMeeting(TEST_CONTACT_SE T,  TES     T_P         AS   T_DATE, null);
        }

    /**
     * Tests t      h      e addMeetingNotes m   ethod can be called to ad  d notes ot pa   st meeti   ng.
         */
       @Te     st
    public voi d testAd    dMeetingNotesToPastMeet  i    ng    () {
          testC     ontact Ma  nager.addMeetingN  otes(pMee  ting0.   get   Id(), TEST_NOTES_2)   ;
            Str     ing expectedNotes = pMeeting0.getNote     s() + TEST_NOTES_2 + NOTES_                S      EPARAT   OR;
            assertEquals(ex    pectedNotes,  ((Past   Me  et    in gImpl)testC    ontactMa   nager.getMeeting(p           Me  e     tin   g      0.getI     d())).getNotes());     
    }

    /**
     *    Tests the addMeeting  Notes method throws an    IllegalArgum       entException
        *  when ca  lled with a m      e  eting     which doesn't exist.
        */
    @ Test  
      public void testAddMeetingNot           esT  oFalseMeeting() {
                exception.expe             ct(IllegalArgu   mentExce    ptio   n.clas      s);
             t    est     ContactManager.addMeeting    Notes(483, "Some    Notes   !");
    }

    /*    *
       * Tests the addMeetingN    otes me     thod   converts a future m   eeting to a past m    e  eti       ng
     *         when called on a futur     e meeting  with a past date.
           */
     @Test
                     public void te   stAddMeet in   gNotesTo      Futur    eMee  ti  ngWithPastDa   te()   {
                       Calendar sho rtFutureDate     = Ca   l        e  ndar.getInstan  ce();
            shortF        u tureD       ate     .add(Calenda r. MILLISE                         COND, 45);
        Meet    ing convertedM  eeting = new FutureMe  et   ingImpl(meetin      gId.getAnd Increm      ent(     ), shortFut     ureDat      e, TEST_CONT ACT_SET);
        testContac tManage r.addFutureMeeting(convertedMeet   ing.getCon     t    acts(), co nvertedMeeting.ge      tDate());
         try {
                          T hread.slee    p       (50)    ;
               }    catch(In             terru  pted          Exception  ex) {
                       Th  read.currentThread( ).interrup  t();
        }
             testCo     nta    ctManager.addMeeti             ngNotes(con   verte dMeeting.getId(), T      E               ST_NOTES   _2);
           assert     Equals(TEST_NOTE   S_2 + NO   TES_SEPA   RATOR, ((Pas  tMeeting) testContactMa     nager.  getMee    ting   (conver   tedMeeting.getId())).getN   otes());
            assertTrue( testConta   ctMana ger.g  etMeeting(c o    nvert   edMeeting.getId()) instanceof Past       Me   etin  g);
           }

       /**
         * T  ests                the ad dMe    etingNote    s m ethod throws an IllegalStateException
       *  when ca     lled with a meet       ing whi    ch is sche  du            led for the future.
           */
          @T   est
    p    ub            lic void test       AddMeetingNotesToFu  tureMeetingW   ithFu    tur   eDa  te() {
               exception.expect(IllegalStateExcep    tion.class);
            tes     t    Co   ntactManager.ad       dMee tingNotes(fMee    ting0.getId()   , TEST_NOTES);
           }
 
    /* *  
          *   Tests         the      addMeetingNotes    method throw  s a NullPoin  te  rEx    ception
        * when called with  null meeting notes.    
     *    /
     @Test      
    p ublic void testAddNu  llMe etingNo t  es() {
        exception.expect(Null   Poi        nterException.cl  ass);
        tes     tCont     ac tManager.add            MeetingN  otes( pMeeting0.getI   d()    , null);
    }

    /*  *
           * Test s the addNewContact     metho        d can be ca   ll    ed with acce  ptable arguments.
     *    /
    @Test
      public        void test   AddNewCo               ntact(   )       {
        testContactMana   ger.ad    dNewContact(TEST               _NAME,        TEST_NOTES);
    }

    /**
        * T ests the    addN ewContact  method  throws a NullPointerExcepti   on
     * when    calle  d w    ith nu   ll notes.
     */
    @Test
    p ublic void testA   ddNewContactWithNullNotes()  {
        except    ion.expect(NullPointerExceptio n.class);
        testContactManager.addNewCon ta   ct(TEST_NAME, nul         l);
            }

    /**
     * Tests the add  NewContact met  h  od thr   ows a NullPointerException
     *    when call   ed wi th         a nu  ll c  on  t   act.
      */
    @Test
       publi  c void   testAddNe   wContact WithNu      llName() {
                  exc eption.expect(Null            PointerExce       ptio   n .c   lass);
         testContactManager.addNewConta       ct      (null, TEST_ NOTES);
          }

    /**
     * Tests the getContact(int... id          s) returns the expect    ed value
     * when called    for o   ne contact.
           * /
    @Test
    public voi d testGetOneContactByI   D() {
        tempContactS   e      t = t  estC    ontact  Manager.  getCon       tacts(co  ntact1.getId());
        tempObjectsArray = tempCo  ntact Set.toArray    ();
        tempContact =    (Contact) t     empObje   ctsArr ay[0];

           assertTrue(t         empObjectsArray.length = = 1);
        assertEqua      ls(contac t1. getId(), tempCo  ntac t.   g    etId());
        assertEqual s(contact1.g  etName(), tempContact.get   Name())        ;
        assert    Equals(conta       ct1.getNotes(), te  mpCo     ntact.getNotes());
    }

    /**
                    * Tests           the getCo     ntact (int... i        ds) r  eturns the e  xpected value
     * when called for m  ultiple con   tacts  .
     */
    @Test
    pub    lic void testGetMulti p    leContactsByI D()   {
         t   empCont    actSet = tes     tContactM       a nager.      getContacts(0,   2,    4    );
             temp  Obje  ct     sArray =   tempCo        ntac   t   Set.t        oArray();

             bool      ean co ntact0Fo       und = false;
           boolea    n contact2Found = false;
        b      oo  lean        con   tact4Fo und = fals  e;
        for (int     i = 0; i < 3; i++)  {
              tempContact = (C    ontact) tempObjectsArray[i];
      
                 if (0 == tempContact.getId()){
                              asser tEqu   als(contact0.getName(  ), t   empContact.getName());
                assert         Equals(contact0.getN otes ()  , tempCon       tact.ge     tNotes())    ;
                  contact0Found        = true;
                } else if (2 == tempC   ontact   .getId()){
                   ass      ertEquals(con            tact2.getName(), tempContact.getName());
                     assertEquals(contac   t2.g       etNotes(), tem         pContact.getNotes());
                contact2Found = true;
            } else {
                 assertEquals(4, temp    Contact.g    et Id());
                          assertEquals(contact4.getN           ame(), tempContact.getNa      me());
                a  ssertEquals(co       nta           ct4.getNotes(), tempContact   .getNo      te        s());
                        contact      4Found = true;
               }
        }
        assertTrue(   tempObj ect    sArray.lengt   h == 3);
        assertTrue   (contact0Found)  ;
           assertTrue(contact2Found);
        ass        ertTrue(contact4F ound);
    }

    /**
     * Tests       the getContact(int... ids) th   rows an   Illeg        alArgumentException
      * when called on a contact that doesn't exist.
     */
          @Te   st
    public void testGe      tContactsByIDWithFalseID() {
        exception.expect(I  llegalAr    gumentEx            ce pt  ion.class);
        testContactManager.getContacts(50);
     }

    /**
     * Tests the getContact(Str     ing name) retur       ns the expected value
     * whe     n called for one       contact.
     */
         @Test
    p   ublic void testGetContactsByName() {
        temp  ContactSet = te stContactM   anager.g   etContacts("     Edwa    rd Curry");
            tempObjectsArra           y = tempC   ontactSet.toArray();
        tempCont  act = (Contact) tempObjectsArray[0];

           asse   rtEquals(contact3    .getId(), tempCon tact.get Id());
        assertEquals(con    tact3.getName(), tempCo    ntact.getName());
        assertEqual s(   contact3 .getNotes(), tempContact.getNotes()  );
             assertTrue(tempObjectsArray.length == 1);
    }

    /**
     * Tests th    e    getContact(Strin    g name)  returns the e   xp  ected size
       * set    when called for m ultiple contacts with a full name as its argument.
     */
      @Test
    public void testGetMultipleContactsByName() {
              tempContactSet = testContactManager.getContacts("Jaso   n Bry    an");
        tempObjectsArray = tempContac       tSet.toAr     ray();
             asse    rt   Equals(2, tempObject   sAr  ray.length);
    }

    /* *
     * Tests the getContact(String name) returns the ex  pected s    ize
      * set when called for multiple con           tacts with a name segmen t as    it's argument.
      */     
    @Test
    publ  ic void testGetM    ult  ipleCont   actsByNameSegment() {
        tempContactSet = test ContactManager.getContacts("an");
        tempObject    sArray = te     m   pContactSet.toArray();
        assertEquals(5, tempObjectsArra y.length);
    }

    /**
     * Tests the getContact(String  name) method throws a NullPointe       rException
       * when called with a n   ull string as it's argument.
     *      /
     @Test
      public void testGetContac tsByNameNull Name() {
        //Ne     cessary to ensure correct version of
        //getContacts method   is called.
        String null    String   = null;
        exception.expect(NullPointerException    .class);
               testContactManager.getContacts(nul  lString);
       }

      /**
     * Tes    ts the getContact(String name) method throws a NullPointerEx  cepti    on
     * when called wit   h an empty     string as it's argument.
     */
    @Test
    public void testGe          tContactsByNameEmptyNam   e() {
        exception .expect(NullPointerExcept ion.class);
         testContactManager.getContacts("");
    }

    /**
     * Tests the getContact(String na me) method  returns an empty s  et
     * when called wit  h a contact the doesn't exist as it  's argument.
     */
    @Test
      public void testGetContactsByFalseNameReturnsEmp tySet() {
        tempContactSet = testCon  tactManager.getContacts     (f    alseCo  ntact.getName());
        assertTrue(tempContactSet.isEmpty());
    }

    /**
     * Tests that the flush method creates a data file.
     */
    @Test
    public void testFlushD    ataFileCreated() {
           //Tests     if data file ex  ists.
          testContactManager.     flush();
            try
        {
             new FileInputStream(DATA_FILE_NAME);
        }
        catch (IOException ex){
            ex.printStackTrace();
                        fail();
        }
    }

    /**
     * Tests that the flush method creates       a data file and it conta    ins the right objects.
     */
    @Test
    @SuppressWarnings("unchecked")
    p ublic void testFlushDataFileHasContent() {
        testContactManager.flush();   
        FileInputStream fileIn;
          ObjectInputS     tream ob jectIn;
        try
        {
            fileIn = new FileInputStream(DATA_FILE  _NAME);
            objectIn = new ObjectInputStream   (fileIn);
            testContactsIDMap = (HashMap<Integer, Contact>) objectIn.readObject();
            testMeetingsIDMap = (Has    hMap<Integer, Mee      ting>) objectIn.readObject();
                 testContactIDGenerator = (AtomicInteger     ) objectIn.readObject();
               testMeetingIDGenerator = (Atomi   cInteger) objectIn.readObject();
             objectIn.close();
        }
         catch (IOException | ClassN otFoundExc  eption ex){
                    ex.printStackTrac   e();
            fail();
        }
    }

      /**
     * Tests that the flush method creates a data file and it has the
     * correct content.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testFlushD    ataFileHasCorrectContent() {
        testContactManager.  flush();
        FileIn   putStream fileIn;
         ObjectInput  Stream objectI  n;
        try
               {
            fileIn = new FileInputStream(DATA_FILE_NAME);
            objectIn = new ObjectInputStream(   fileIn);
            testC   ontactsIDMap = (HashMap<Integer, Contact>) objectIn.readObject();
            testMeetingsIDMap = (HashMap<Integer, Meeting>) objectIn.readObject();
            testContactIDGenerator = (AtomicInteger) objectIn.readObject();
            testMeetingIDGenerator = (AtomicIntege  r) objectIn.readObject();
            objectIn.close();
        }
        catch (IOException | ClassNotFoundException ex){
            ex.printStackTrace();
            fail();
        }
        assertEquals(9,  testContactsIDMap.size());
        assertEquals(10, testMeetingsIDMap.size());
        assertEquals(9, testContactIDGenerator.get());
        assertEquals(10, testMeetingIDGenera      tor.get());

        tempObjectsArray = testContactsIDMap.values().toArray();
        for (Object item : tempObjectsArray) {
            tempContact = (Conta ct) item;
            tempObjectsArray2 = testContactManager.getContacts(tempContact.getId()).toArray();
            expectedContact = (Contact) tempObjectsArray2[0];
            assertEquals(expectedContact.getName(), tempContact.getName());
            assertEquals(expectedContact.getNotes(), tempContact.getNotes());
        }

        tempObjectsArray = testMeetingsIDMap.values().toArray();
        for (Object item : tempObjectsArray) {
            tempMeeting = (Meeting) item;
            expectedMeeting = testContactManager.getMeeting(tempMeeting.getId());
            assertEquals(expectedMeeting.getDate(), tempMeeting.getDate());
            assertEquals(expectedMeeting .getContacts().size(), tempMeeting.getContacts().size());
        }
    }
}





























