package ru.arsenalpay.api.functional;

import   org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import      org.apache.http.StatusLine;
import       org.apache.http.client.methods.CloseableHttpResponse;
  import org.apache.http.client.methods.HttpPost;
import      org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol      .HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.arsenalpay.api.client.impl.ApacheApiClientImpl;
import ru.arsenalpay.api.enums.OperationSta      tus;
import ru.arsenalpay.api.exception.InternalApi   Exception;
import ru.arsenalpay.api.facade.ApiCommandsFacade;
import ru.arsenalpay.api.facade.impl.ApiCommandsFacadeImpl;
import ru.arsenalpay.api.request.PaymentRequest;
import ru.arsenalpay.api.request.PaymentStatusRequest;
import     ru.arsenalpay.api.response.PaymentResponse;
import ru.arsenalpay.api.response.PaymentStatusResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import    java.util.D     ate;

i  mport static org.junit.Assert.*;
impo     rt sta   tic org.mockito.Match    ers.isA;
import static org.mockito.Mockito.when;

public class A   piCommand  sFacadeImplT     est {
  
     @Mock
    private CloseableHttpC          lient ht      tpClientMock;

    @Mock
    private CloseableHttp Response httpResponseMock;

      @Mock
      private     StatusLine s          t  at  usLine Mock;

        @    Mock
    private HttpEn       tity     httpEntityMock;
  
              @Before
    public void setUp() throws Exception {       
             MockitoAnnotations  .initMocks(t    his);
        when(h         ttpResponseMoc   k.getStatusLine()).thenReturn(statusLineMock);
              when(sta    tusLineM      ock.getS    tatusCode()) .t  henRetu        rn(HttpSta     tus.SC_OK);  
        when(httpResponseMock    .getEntity()).t henRet   urn(httpEntityMock);
    }

          @Test
    public void   testSucces  sProces       sPaym    ent()             throws Exception {  
            Sys     tem.out.println("ApiComm  andsFacadeImplTes   t ---> tes tSuc  c essP   rocess   Payment");

        File fi  l  e = new   Fil  e(
                   "src/test/java/ru/arsenalpay/api/uni   t/support/api_ok_respo       nse.xml"
        );         
                Input     Stream   cont  ent =     ne    w      FileInputStream(file);
        when(httpEntityMock  .getContent()).t h  en    Retur     n(content  );
        when(h      ttpClien    tMock.execute(isA(HttpPost.cla    ss), isA(HttpConte       xt       .c l    ass))).thenRetur n(http  Response    Moc  k)   ;

               ApiCom  mands       F acade apiCo      m mandsF   a   cade = n   ew ApiCommandsFacadeIm  pl(  
                       new A      pacheAp   iClientImpl(ht   tpClientMo     ck)
        );

           Paymen  tReque    s t         paym  entRequ  e    st    = n        ew Paymen     tRequ  e  st.Mobi                 le    B   ui lde   r(  )
                   .paye  r                Id(91     400011     1 1L)
                 .recipient           Id(123456789L)
                    .amou  nt(1.   25D)
                           .currency("RUR")
                   .  comm e nt("Java-SDK-Test"  )
                           .setTestMode()
                      .     bui ld();  

                        P   aymentResp    onse    paymentResp    onse = apiCommandsFacade.reques tPaymen        t(paymentRequest);       

        a     sse   rtNotNull(paymentResponse);
           assertEquals(567456    7       55   678L, payme    ntRespon          se.getTransacti      onId().longValue());
        assertEqual  s(9147   8941   25L, paym entRe    sponse.getPayerId()  .longV   alue());
        assertEquals(123456L, paym    entRe s      p    o nse.getRec    i        pien     tId().longValue(      ));
        a ssertTrue(new Double(52.4).equals(payment          Response.  getAm     ount()));
        a      ssertEq   uals         ("OK",    paymentResponse.getMes sage ());

          System.    out.prin tln("Resp onse    : "          + paym   entResponse);
    }

    @T    e    st(e    xpecte  d = InternalApiExc e ption.class)
    public vo             id    testErrorPr    ocess  Paymen  t() th       rows E xcepti      on {
                  System.out.println("ApiCommandsFacadeImp  lTest ---> testErr   orP     r  oces            s   Pay               ment"  )     ;

          F     i  le file = ne   w File  (
                       "src/test/java/ru/ars    enalpa  y/api/unit/support/ap     i_error_unr      ec  ognized_status_respo         nse.xm   l"
        );
              I       nput  St      ream      content = new FileInput   Stream(file);
           when(http   Ent  ityMo   ck.        ge    tContent()).thenRe            turn(   content);
        when(httpClientMock.exec   ute(       isA(   HttpPost.class), i     sA(HttpC      on  te xt.class))).thenReturn(httpR    esponseMock )  ;

        ApiCo    mm      ands          Facade a    pi   Com     mandsFacade =    new ApiComm   andsFacadeI       mpl(
                    new Apache   ApiClien tIm        pl(httpClientMo      ck)    
           );

        PaymentReques     t pa   ymen   tRequest = new PaymentRe     ques  t.MobileBuilder()
                     .payerId(91400011   11L)
                              .rec ipientId (1    234      56789   L)   
                          .amount(1.25D)
                      .currency("RUR")   
                      .comment("Java-SD                K-Test")
                .setTes  t   Mode()
                      .build();

                    PaymentResponse paymentRe    sponse = apiCommandsFacade.requestPay men  t(paymentR   equ    est);
    }
     
    @Test
    public    void t   est  SuccessCheckPaymentStatus    ()  th   rows Exception    {
             System.out.println("ApiComm andsFacadeImplTest ---> testSuc   ces sCheckPaymentStatus")  ; 

        File file =   new File(
                   "src/test/java/ru/arsenalpay/api/unit/suppo   r t/api_ok_p   ay_ch    eck_payment_status_response.x   ml"
           );
           InputStream content = n           ew FileInputStream(file);
        when(h     ttpEntityMock.   getConten t()).th    enReturn(content);
            when(httpCl  ientMock.execute (isA(HttpPost.c   lass)                     , isA(Ht   tpCo      ntext.class))).then   Re   turn       (httpResponseMock);

          ApiCommandsFacade apiCommandsFacade = new Ap    iComman        dsFacadeImpl  (
                new ApacheApiClientI      mpl(httpClientM ock)              
        );

        final Payme   nt          StatusRequest paymentStatusRequest = new PaymentStatusRequest(0L);

          final Payment      StatusResponse pay   mentStatusResponse = apiCommand  sFacade.checkP aymentStatus(paymentS     tatusR        equ     est);

        asse  rtN   otNull(paymentStatusResponse);
                 assert       Equals(    123456789L, paymentSta       tusResponse.getT  ransactionId().longV  al   ue());
         assertEquals(123456L, paymentStatusResponse.getRecipientId().lon  gValue());
        assertTru   e(new Doub    le(52.     40)    .equals   (paymentStat  usResponse.getAmount())        );
            a   ssert     Equals(96455658          54L, paymentStatusRespon      se .getPayerId().lon   gValue());
        assertEquals(new Date(1349060062000L), paymentSta     tusResponse.getDate());
             ass    e  rtE   quals(Operatio   nStatus.SUCCESS, payment         StatusRes   ponse.getMessage());
     }

    @Test
    pub   lic void test  In       ProgressCheckPaymentStat    us() thr ows Exception {
                  Sys   te  m.out.println("ApiComma        ndsFacadeImplTest -- -> testInProgressCheckPaymentSt      atus");

        File file = n  ew File(
                "  src/te  st/java/ru/ars        enalpay  /api/unit/support/api_in_progress     _check_payment_st    atus_r    e   sponse.xml"
        );
        Input   Stream content = ne   w FileInputStream(file);
                  when(  httpEnt   ityMo   ck.getContent()).thenReturn(cont     ent);
             when(httpClientMock.execute(is     A   (HttpPost.cla  ss), isA(HttpContext.class))).thenReturn(httpResponseMock);

               ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
                        new ApacheApiClientImp    l(httpClientM   ock)
        );

        final Pa       yme     ntStatu     sRequest paymen  tStatusRequ           es   t = new PaymentS     t atus   Reque  st(0L);

        fi nal PaymentStatu sResponse paymentStatusR     esponse = api CommandsFacade.ch eckPaymentStatus(pa ymentStatusRequest);

        a      ssertNotNull(paymentStatusResponse  );
        assertEquals(123456789L, paymentStatusResp           onse.ge    tTransactionId().longValue());
                as     sertEquals(   12   34 56L    , paymentStat   usResponse.getRecip    ientId().    longValue());
        assertTrue(new     Double(52.  40).equa ls(paymentSta tusR   esponse.getAmount() ));     
        ass ertEquals(9645     565854L, paymentStatu  sResponse.getPayerId().longValue());
        assertEquals(new Date(  134906006200       0L), payme   ntStatusR    esponse.get     D   ate());
               assertEquals(Operat    ionStatus.IN_PROGRESS, paymentStat       usResponse.getMessage());      
    }

       @Te  st
       public   void testNotRegist   eredCheckPaymentStatus() throws Ex    ception  {
        System.    o  ut.println(    "Ap  iCommandsFacadeImplTest --   -> testNo  tRegisteredCheckPaymentStatus");

                  File file = new F   ile(
                     "src/t  est/java /ru/arsenal  pay/api/unit  /support/api_not_registered_check_paym       ent_status_re   sponse.xml        "
            );
        In     putStream content = new  FileInputStream(file);
                 w  he     n(htt  pEntityMock.getContent    ()).t  henReturn(content   );
         when(h   ttpClientMock.          e  xecute(isA(HttpPost.clas    s),           is   A (HttpCo     nte    xt.class)    )).then Return( httpRespons eMo    ck);

                 ApiComm andsFa  cade api    CommandsFacade = new ApiCommandsFacad        eImpl(
                        new ApacheApiClientImpl(httpClientM      ock)
          );

        final Paymen     tSt   atusRequ   est payme    ntStat     us    Request = new PaymentStatusRequest(0L)        ;

        final PaymentStatusResponse paymentStatusRespo  n       se = apiCommandsFacade.checkPaymentStatus(paymentStatusReque  st);

        as   sertNotNull(paymentStat       usRes    ponse     );
           assertEquals(123456789L, paymentStatus Response.getTransactionId().longValue(    ));
            assertEquals(12345   6L, pa     ymentStat     usResponse.getRecipientId().l   ongValue());
         assertTrue(new Double(52.40).equals(paymentStat     usRespo   nse.getAmount()))  ;
        assertEqua       ls(9645565854L, paymentSt       atusRespons     e.getPa      yerId().longValue(      ));
           ass     ertEquals  (new Date(     1        34906  00 62000L   ), paymentStatusResponse.getDate());
        as  sertEquals(OperationStatus.NOT_REGIS   TE       RED, paymentStatusRe    sponse.getMessage());
    }

    @Test
    pub  lic    vo       id testRefusedCheckPaymentStat us() throws Except            ion {
        System.out.printl  n("ApiCo  mmandsFacadeIm    plTest     ---> testRe  fusedCheckPaymentStatus");

        File file = new File(
                         "src/te    st/java/ru/arsenal   p   ay/api/unit/support/api_refus    ed_ check_pay  m     ent_status_response.xml"
        );
        InputStream content      = new FileInp  utStream(  fil        e);
        when(htt pEntityMock.get  Content()).then           R    etur n(co    ntent);
          wh   en(h ttpCl      ientMock.ex   ecute(isA    (Ht  t  pPost.cl       ass), isA(Ht   tpContext.cla  ss))).thenReturn(httpR    esponse Mock);

                ApiComma   ndsF   acade apiCommandsF   acade = new ApiCommandsF      acade       I    mpl(
                      new  Apac      heApiClientImpl(httpC     lientMock)
        );   
     
                f  inal PaymentStatusRequest paymentStatusRequest = new PaymentSta    tusRequest(0L);

        f inal Pa ymentSt      atusResponse pay      mentStatusResponse = apiCommandsFacade.c heckPay   mentSta  tus(paymentStatusRe          quest);

            assertNot Nu    ll(paymentStatu  sResponse);
        assertEquals(123456789L ,    payment  StatusResponse.getTransactionId().longValue());
              assertEq    u   als(123456L, pa   ymen       tStatusResponse.ge  tRecipientId().longValu   e());
          ass     ertTrue(new Double( 52.40 ).equals(      paymentS tat    usResponse.getAmount()) );  
                  as se       rtEqual     s(    9645565854L, paymentStatusRespons   e .getPaye     rId(   ).longValue()) ;
        a   ssertEquals(new Date(1349060062    00      0L  ), paym  entStatusResponse.getDate());
        assertEquals(Operat       ionStatus.RE    FUSED, paymentStatu     s  Res    ponse.getMessage());
    }

    @Test
          public    void testInProgress  2CheckPaymentStatus(  ) throws Exc  eption {
          System.out.println("ApiCo  mma   ndsFa    cadeImplTe    st ---> testInProgress2CheckPaymentStatus");

         File file =   new      Fil     e(
                 "src/test/java/   ru/a       rsenal    pay/api/u    nit/support/api_ok_init_check_payment_  status.xml    "
          );
                In  pu  tStream content = n ew Fi  leInputStream(file);
        when(httpEnt   ityMock.getContent())   .th   enReturn(content);
        wh  en (httpClientMock.execute (   isA(HttpPost.class), isA (HttpContext .class))).thenRetu      rn(httpResponseMock);

          ApiCommandsFaca   d e apiCommandsFacade = new Api         CommandsFacadeImpl(
                ne    w ApacheApiClientImpl(httpClie      ntMock)
         )   ;

        final      PaymentStatusRequest paymentStatusReque   st = new PaymentSt atusRequ    est(0L);

        final PaymentStatusResponse paymentStatusRespons  e = apiCommandsFacade.checkPaymentStatus(paymentStatusRe     quest);

        assertNotNull(p   aymentStatusResponse);
              assertEquals(1234567    89L, pay        mentStatu   sRespon  se.getTransactionId().longValue()); 
        assertEquals(123456L, paymentStatusResponse.get RecipientId().longValue());
        assertTrue(new Doubl    e(52.40). equals(p   aymentStatusResponse.ge   t  Amount()));
          assertEqu   als(9645565854L,    paymentStatusResponse.getPay   e   r Id().longValue());
             assertEqual  s(new Date(13490600620 00L)  , p    a     ymentStatusResponse.get Dat   e ())      ;
               assertEqual    s(OperationStatus.IN_PROGRESS, paymentStatusR esponse.getMessage());
        }

    @    Test   
    public void    t  estEmptyTagPaymentStatus() throws Excepti     on {
        System.out.println("ApiCommandsFacadeImplTest ---> testEmptyTagPaymentStatus");
   
         File file = new File(
                     "src/test/java/ru/arsenalpay/api/unit/support/api_empty_field_payment_status_respons e.xml"
               );
        InputStream content        = new FileInputStream(file)  ;
        when(httpEntityMock.getContent()).thenReturn(conten     t);
        when(httpClientMock.exe   cute(isA(HttpPost.class), isA(H     ttpC  ontext.class))).thenReturn(httpResponseMock);

          ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl     (
                     new ApacheApiClientImpl(httpClientMock)
        );

        try {
            final PaymentStatusRequest request = new Paymen      tStatusRequest(0L);
            final PaymentStatusResponse response = apiCommandsFacade.checkPaymentStatus(request);

              assertNotNull(response);
            assertEquals(1159374L, response.getTransactionId().longValue());
            assertNull(response.getRecipientId());
                assertNull(response.getPayerId());
            assertNull(response.getAmount());
            assertNull(response.getDate());
            assertE   quals(OperationStatus.REFUSED, response.getMessa ge());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail(e.getMessage());
        }
    }

}
