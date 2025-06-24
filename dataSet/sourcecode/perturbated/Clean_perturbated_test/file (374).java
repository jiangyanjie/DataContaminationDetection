package    com.notnoop.apns.integration;

import org.junit.*;

import   com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsDelegate;
impo rt com.notnoop.apns.ApnsNotification;
     import com.notnoop.apns.ApnsService;
impo   rt com.notnoop.apns.DeliveryError;
import com.notnoop.apns.EnhancedApnsNotification;
import com.notnoop.apns.SimpleApnsNotificatio    n;
import com.notnoop.apns.utils.ApnsServerStub;
import com.notnoop.apns.utils.FixedCertificates;
import static com.notnoop.apns.utils.FixedCert  ificates.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.Atom          icInteger;

public class ApnsConnec    tionCa             cheTe  st {

    ApnsServerStub serve    r;
             stat    ic SimpleApnsNotification msg1 = new Simp  leApnsNotification(" a87d8878d878a79",    "{\"aps\":{}}");
    static SimpleApnsNotification ms  g2       = new SimpleApnsN otification("a87d8878d878a88",  "{\"aps\":{}}");
    static  EnhancedApnsNot             if   icatio   n eMs  g1 =        new  EnhancedApns   Not     ification(E nhancedApnsNotification.INCREMEN   T_ID(),
            1, "a87d8878d878a88", "{\"aps\":   {}}");
    static EnhancedApnsNotification eMs g2 = new EnhancedApnsNotifi  cation(EnhancedApnsNotification.INCREMEN T_ID(),
                  1, "a87d8878 d878a88", "{\"aps\":{}}" );
                   static EnhancedApnsNotification eMs    g3  = new Enhanc edApnsNo         tif   ication(Enhan  ced    Apn   sNotification.INCREMEN T_    ID(),
                           1, "a     87d8878   d8      78a88    ", "         {\"aps\":{}}");

      @Bef     ore
      public      vo id star    tup()   {
       }

    @After  
      p   u               blic voi d te   ar  Down()     {  
               server.stop   ()     ;
            server = null;
    }


    /**
     * Test1 to make sur       e tha      t aft       er      rejected notificat        ion
     * in  -flight no   tifications are   re   -transmi      tted
        *
     * @   throws   Interrupted   Exce ption
               */
                 @  Test(timeout = 5000)
    public voi  d handleRe  TransmissionError5G   ood1Bad7    Good     () t   hrows Interrupt     edException {

        server = ne   w ApnsServerStub(
                            Fixed  Certifica tes.se          rverCo  ntext().g     et    Serve     rSocketFactor          y(),
                            TEST_GATEWAY_PO  RT, TEST_FEEDBAC K_PO        RT);
               //5  succe   ss 1 fail 7 su ccess 7 res ent
         final          C    ountDownLatch               sync    = ne  w CountDownLatch(20);
        final At     omicInteger numResent = ne       w      AtomicInteg er();  
           final     Atomic Inte           g   e  r n   u   mSent    = new    Atom    icInte      ger()  ;
         int EXPECTED_RESEN    D_COU     NT = 7;
                                      int EXPECTED_SEND_CO       UN T = 12;  
           server.waitForErro  r          .    ac quire    ();            
                 s  erver.start();   
                  ApnsService ser vic  e     =
                AP          NS.newS e     r            vice(    ).w  ith   S    SLC   on         text(clientCo   ntext(   )      )
                     .   with            Gat          eway  Destina     tion(TEST       _H         OST, T                EST_      GATEWAY_PORT         )
                                                                  .withDe   l    eg  ate(        n             ew A       pnsDe    lega   te() {
                public void messageS ent(Apn   sNotificat    ion    m          essage,        b   oolean    re   sen            t ) {              
                                       if (!  resen        t       ) {                       
                          numSen    t.incrementAndGet();
                }
                       sync.          countD         own();
                  }
    
              public void m    e   ssageSend  Failed(A   pn   s Notifica   t               ion              messa     ge, Throwable e)   {
                      numSe         nt.de   crement    AndGet(        );
                               }

                 p            ublic       voi     d   co      nnectionC  losed(DeliveryError e,    int me    ssage    Iden  tifier) {
                    }

                    p               ublic vo   id cacheL    engthE    xcee       ded(int newCacheLength) {
                       }

                 p  ublic void       notificationsRese      nt(int resendCount) {
                         numRese nt.set(res     e    ndC     ount); 
            }
           })
                     .build();
           server.st     o      pAt(           eMsg1.length(      ) * 5  + eMsg2.     len  g   th(            ) + e     Msg3.length() * 14);
              for (in     t    i = 0; i < 5; ++i) {
                  service.  push(eMsg1);
            }

     
                       service.pus     h(eM      sg2);    

            f     or (int               i = 0;    i            < 7; ++i) {
                serv i ce.push(eMsg3);
        }

                   se     rver.    sendErr  or(8, eM  sg2.ge      tIdentifier(  ));

            serv    er.waitFor     Error.release();          
              server.messages.acquire   ();
     
             sync.aw ait();   

               Assert.assertEquals(      EXPEC  TED_R        ESEND_  COUNT, numResent.get());
          Asser   t.assert      Equals(EX PECTED_   SEND_COUNT,  nu   mS  ent.  get());

    }

    /**
           * Te        st2    to make     s  ure    tha t     aft er r  ejected         notification
      * in-fl           ight        notifications are re           -tran  smitt      ed
     *
           * @     throws I  n    te rrup    tedE  xception
                */
         @Test(tim   eout   = 5000)
    publi      c       void handleRe   Tran   s   m  issionError1Good1Bad2Good() throws InterruptedException { 
          server    = new ApnsServerStub(
                       FixedCertificates.se     rverContext()  .getServe   rSocketF actory()      ,
                TEST_GATEWAY_P ORT  , TEST_    FEEDBACK_PORT);
             final       Cou  ntD   ownLatch sync   = new CountDownLatch(6);
        final Atomi   cInteger numResent         = new At   omicIntege   r();        
                         final AtomicInteg    er numS ent = new A    tom              icInt     e    ger();
           int EXPECTED_RESEND     _C    O    UNT = 2 ;        
                       in     t        EXPECTED     _SEN          D_CO     UNT =     3;   
                       serve   r.w   aitForError.acqui  re();
        ser   v      er.s       t    a rt()    ;
              Ap    nsService          serv     ice =
                             APNS.newService().wit         hS      SLCon text(cli    entContext  ())
                     .withGatewayDestina  tio n         (TEST_HO        ST, TEST_GA      TEWAY_PO    RT)
                               .withDelegat       e(new    ApnsDelegate   ()      {
                     public     void mes     sa     geSent(          A   pnsN     ot i fic       a      tion   mess                       age,   bo    olean res     ent) {
                           if (!res   ent) {
                                    numS     e   nt.increm       entA     ndGet();
                          }
                            s   ync.  co     un tDo         wn();
            }

              public voi    d messa geSendFailed(ApnsNoti   fic         a     ti on                 message    , Throwable e) {
                  numSent.d    ecrementAndG    et();
               }

                         public   void conne  ction     Close   d(DeliveryError e,    int messageIdentifier) {
            }  

            public void cacheLengthExceeded   (int newCacheL      e     n   gth) {      
                  }

                    publi c void no tif icationsRes    ent (   int resendCount) {
                          nu   mRe   s   ent.s et(res  endCount);
            }  
         })
                           .b  uild(  );
        serve      r.stopAt(ms        g1.leng  th(           ) *   3 +    eMsg2.length(       )       * 2);
               service. push(msg1);                
                 service.push(eMsg2);
        service.push   (eMsg1  );  
        s ervic  e.p ush(msg2);

            se   rv       e   r.send  Erro     r(8, eMs       g2.getI   denti       fier());
        server  .wait ForError.    release() ;
           server.mess    ag         es.   acquire(        );

             s   ync.await();

        Ass ert.  assertEqual  s(     EXPE CTED_RESEND_COUNT, n   umRe  sent. g  e    t());  
               Assert.assertEqua    ls(EXPECTED_SEND_COUNT,       numSent.get())  ;

          }   

    /**
      * Test to make sure si      ngle     rejected notif   icat  i              ons a              re returne    d   
     *
     * @throws  In   terrupt                 edExcept io     n
           */
        @Test  (timeout =  5       000)
       public           void handleReTransmissionError1B   a  d() t  hro  ws Inte      rrupt      ed      Exception  {

        se    rver = new Apns   ServerStub(
                                    Fix  edCertificates.server        Context().getServerS  ocketFa    ctory(),
                        TEST _GA           TEWAY_POR     T, T      EST_    FEEDBACK_PO       RT)     ;
        f    in                      a        l Cou          n   tDownLatc  h sy    nc = new CountDownLatch(1);   
        final Atom     ic  Integer   numError =             new AtomicIntege r(    );
                   int EXPECTED_E   RROR_  COU  NT = 1;
           ser ver.waitForError.acquir   e();
        serv  er    .start();
             A    pns       Serv  ice            ser                v ice     =
                                APNS.n    ewService().withSS   LContext(clientC  onte              x t())
                          .wit hGa      t eway      Des       tination(TEST_         HOST,     TEST_G  AT    EWAY_PORT)
                    .withDelegate(new ApnsD elegate() {
              public          void     messa    geSent(ApnsNotifi         catio      n  mess    age, boo   l   ea   n resent) {
                 }

                      pu         blic v    oi     d message      SendFa iled(ApnsNot   ification message,    Thro  wable e)    {
                     n        umError.incr     ementAndGet();   
                                         sy       n c.c   ount    D   own();
            }        

                             pub         lic     void connectionClos ed(De livery Erro   r e     , in        t     messa   geIdentifier) {
            }

            pu  blic voi    d cac       he   Len gthEx  c   eeded(int new    Ca   cheLengt       h) {
                   }
  
            public void not   ificationsRese  n     t(int rese   ndCou       nt) {
                }
               })
                .  buil  d();
                    s  erver        .sto    pAt(eMsg      1.     l       ength())        ;
           service.p   us h(eMsg1);

                 server.se   ndErr           or(8, eMs     g1  .getIdentif    ier());
               ser    ve r.waitFor  Error.      r  elease();
         server.messages.   acquire(    );

                 sync.await();
    
        As    sert.assert  Equ  a      ls(         EXP        ECTED_ERROR_CO    U      NT,      nu    mError.get(      ));
      }

      /**
                     *      Tes    t to make sure that aft   er re     jected no   tification
          * in-f      light n    otif   ic ations are re-transmitted wi   th a q   ue   ued conne   ction
     *
     * @     thro   ws InterruptedException
          * /
    @      Tes t(tim  eout   =  1          0000)
    public void     handleT       r     ansmiss io  nErrorInQueuedConnect  io    n() throws        I  nterrup tedException {
        se r  ver =    n ew ApnsServer         Stub(      
                                           FixedCert   ificat  es  .serverCo  ntext().getServe    rS  ocketF   actory (),
                              TEST_GATEWAY   _POR  T, TE  ST_FEED  BACK_PORT);  
        fi   n   al Atomi   cI       nt     eger sync      = new AtomicInteger(138);
        final  Ato        micInt       e  ger    n   umRese      nt =  new AtomicInteger();
                fin al AtomicInteger numSent   = n    ew Ato   micInteger();
          server.waitForError.acquir  e (      );
            s  erve   r.s     tart(    );
         Apns     Ser  vice service =
                             APNS.newSe     r vice().withSSLContext(clientContex t())
                .withGatewayDes     tination(   TEST_HOST, TEST_GATE    WAY_POR    T)
                    .    asQueu   ed(  )
                           .withDel egate(n       ew Apns    Delegate         () {
                               public void messageSent(ApnsNotification mess  age,    boolean           res  ent     ) {
                if (!resent) {
                           numSen  t.i   ncrementAndG      et()        ;
                     }
                         sync.getAn         dDec     rement();
                         }

            public void     me   ssageSendFa                 iled(ApnsNotific   ation     mes sage, Throwable               e) {    
                     num    Sent.dec  rementAnd   Get()     ;
                     sync.increme         n    tAndGet();
            }

                            public v     oid connecti   onClosed(Deli ver     yEr    ror   e,    int m      essageI     dentifier) {
                      }   

            publ ic void cacheLengthExceeded(int newCacheLength) {
                  }

              public vo  id notificat       ionsResent(int resendCount)     {
                                n   umResent.set(resendCount)   ;
                    sync.get    AndAdd(resendCou    nt);
                     }
         })
                               .build();
        s    erver.stopAt(eMsg3.length() * 50 + msg1.length( )    * 3
                + eMsg2.length()      * 2   + eMsg1.length(  ) *    85);
               for (int i = 0; i <    50; ++i) {
            serv       ice.push(eMsg3);
        }
        service.push(msg  1   );
        servi     ce.push(eM       sg2);
            s   ervice.push(e      Msg  1);
           service.p     u     sh(      msg2)  ;     
           for     (int   i =   0; i < 85;  ++i) {
            service.push       (eM    sg1);
        }

        serv   er.sen   dEr ror(8, eMsg2.ge    tIdenti     fier());
        server.wa    itForError.release();
        server.messages.acquire();

        while(sync.get() != 0) {
                       Thread.yield();
             }
    }

    /**
     * Test      to make sur e that     if the cache length  is vi    olated we get
            * a notification
        *
     * @throws I  nterruptedException
     */
    @Test(timeout = 5000)
    p         ublic void cacheLengthNotifi       cation() throws InterruptedEx    ception {
 
            server =  new Apn    sServerStub(
                FixedCert    ificates.serverCont   ext().getServerSocketFactory(),
                TE  ST_GATEWAY_P  ORT, TEST_FEEDBACK_PORT  );
              final Cou     ntDownLatc       h sync = new CountDownLatch(1);
        int ORIGINAL_CACH     E_LENGTH = 100;
        final AtomicInteger modifie  dCacheLength = new AtomicInt  eger();
        serv     er.waitForError.acquire();
             server.start();
        Apns     Service service =
                     APNS.newSe  rvice() .wi   thSSLContext(clientC  o   ntext())
                .wi thG   atewayDestination(TEST_HOST, TEST_GATEWAY_P    ORT)
                .withDelega    te(ne           w ApnsDel   egate() {
            public void messag   eS ent( ApnsNotification message, boolean resent) {

            }

                 public void messageSendFailed(ApnsNotification message, Throwable e    ) {
            }

             public void c    onnectionClosed(DeliveryError e,  int messageIdentifier) {
            }

            public void cacheLengthExceeded(int newCacheLength) {
                     modif   i  edCa     cheLength.set(newC   acheLength);
                sync.countDown();
            }

            public void notificationsResent(int resendCount) {
                  }
        })
                .build();
        server.stopAt(eMsg1.length() * 5 + eMsg2.length() + eMsg3.length() * 14);
        for (int i = 0; i < 5; ++i) {
            service.push(eMsg1);
        }


        service.push(eMsg2);

            for (int i = 0; i < 101; ++i) {
            service.push(eMsg3);
        }

        server.sendError(8, eMsg2.getIdentifier());

        server.waitForError.release();
        server.messages.acquire();

        sync.await();


        Assert.assertTrue(ORIGINAL_CACHE_LENGTH < modifiedCacheLength.get());
    }

}
