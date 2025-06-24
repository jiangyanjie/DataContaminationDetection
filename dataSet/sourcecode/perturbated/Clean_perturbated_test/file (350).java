package     ru.arsenalpay.api.facade.impl;

import ru.arsenalpay.api.client.ApiClient;
import ru.arsenalpay.api.client.ApiResponse;
import   ru.arsenalpay.api.client.impl.ApacheApiClientImpl;
impo   rt ru.arsenalpay.api.command.ApiCommand;
import ru.arsenalpay.api.command.ApiCommandProducer;
import ru.arsenalpay.api.command.impl.InitPayMkProducer;
import ru.arsenalpay.api.command.impl.InitPayMkStatusProduce    r;
import ru.arsenalpay.api.exception.ArsenalPayApiException;
import ru.arsenalpay.api.exception.InternalApiException;
import ru.arsenalpay.api.facade.ApiCommandsFacade;
import     ru.arsenalpay.api.merchant.MerchantCredentials;
im   port ru.arsenalpay.api.request.PaymentRequest;
import ru.arsenalpay.api.request.PaymentStatusRequest;
import ru.arsenalpay.api.response.PaymentResponse;
import ru.arsenalpay.api.response.PaymentStatusResponse;  
import ru.arsenalpay.api.util.Configuration;
import     ru.arsenalpay.api.util.LoggerManager;
import ru.arsenalpay.api.util.MultiThreadedHttpClient;

import   java.io.IOException;

/*   *
        * <p>  T he main and now   a single implementation of ApiCommands  Facade    .</p>
 *
 * <p>By   default (      default const   ructor) for communications with ArsenalPay Api Server          use ApacheApiClie     ntImp  l
 * configur   ed for con   cu  rrency     environm  ent . But if you want you   can use your ow         n implementation of ApiClient inter    face.
  * In sp   ecial some      cases it   may b    e re    levant for example if you want to create http con   nections usin  g     one tool throughout
 * th   e application or you for    some reason are not  satisfied with the d  efault implem        entatio          n.
 * We do so, because w  e understand that it i      s your application a    nd you      master it.
 * W      e appreciate this freedom and do not impose anything.<      /p    >
 *
 * @see ru.      arsenalpay.api.facade.   ApiCommandsFacade
 * @see ru.arsenalpay.api.client.ApiClient
 * @see ru.ars    enalpay.api.client.im    p   l.Apa cheApiClien     tImpl
 *
 * @author ada  mether    
    */ 
public        clas     s ApiCommandsFa   cadeImpl impl  emen   t        s ApiCommandsF        acad     e {

    /**     
     * Api      C      lient con   cr  ete        impl  ementation
        */
          private f    i  nal Ap  iClient a    piC    lient;
         
    /**
                     * Pri   vate secret merc    hant credentials
     *  /
      private fin  al Merchant    Crede   nt    i     als  c   redentials;

    /        **
           * Th      is is        default c    on  str      ucto     r w    ith             Ap  acheApiClientIm    p l as ApiCl        ient impl     emen   tation.
          * HttpClient is configured for   work in co  ncurrency     e               nvironment  . 
     */
    public ApiCommandsFa       cadeImpl() {
        this.     apiClient = new ApacheApi    C   lientImpl(
                    MultiThrea  ded   HttpClien  t.getInstance().getHttpClient()
             );
        this.c  redentials        = new Mercha   ntC    redent  ials(
                       Configurati     on.g   etP   r  op("merchant     .id"),
                    C    onf     iguration.ge    t Prop("m  e rchant  .        secret")
                    );    
                            LoggerMa    nager    .init();
    }

      /**
     * This const   ructor is        for you   r f     reedom.
     * Simply i    mplement A  piClient in   terface with your             favorite http client with   your config  uration and
     *           A     pi  CommandsFacade wi ll use it for     communication with Ars enalPay Server               AP           I.
     * @param apiClien         t -- implementation of ApiClient      interfa  ce
        */
    public A   piCommandsF     a        cadeIm   pl  (  ApiClient apiClient)        {
          this.api  Client   = apiClient;
            this.credentials  = ne    w Mercha       n  tCredentia   ls(
                      Co   nfigura   tion    .getPr       op    ("merch    a  nt.id"),
                       Configuration.    ge      tProp("merchant.secr et")
                   );
          LoggerManager   .init();
    }

        /**
     * Http     Client i   s configured for work in concurren     cy env         ironment.
     *   MerchantCred      ent    ials are i    d and secret gi      ven to  you at    registration.
     * Ta      ke a note: merchantCredential  s witc   h were set in conf   /sdk.prope  rties will be ignore d!
     */
    public ApiComman  dsFacadeIm  pl     (Merc  hantCr edentials merc          han  tCredentials) {
          this.apiCl        ient = n   ew ApacheApiClientImpl(
                     M   ultiThreadedHttpClient .get   Insta           nce().getHttp         Client()  
        );        
        th  is.c      red    entials = merchantCred    ential    s;
                 LoggerManager  .init();   
          }

    /**
        * Th    is con     struc     tor is for your abs    olute   free   do   m.
     * This      is co    nstructor wi   th ApacheApiCl      ien tImpl as ApiC lient imple     ment     at ion   and     mercha    ntCredentials     .
                  * HttpClient is configured    fo   r work in concurrency enviro       nmen   t   .
     * Mercha  ntCredential   s are    id and secret   giv     en to     you a        t re      gistra       tion.
        * Ta      ke a note   :   merchantCre  dentials witch were set in   c  onf/sdk.p  rope    rties     will b   e ignored!
         */
                         public ApiComma    nds Fac adeImpl(ApiClient apiC       lient, Mer      chantCreden   tials merchantCredentials) {
            this.api Client = apiC   lient;
              this.credent  ials = mercha     ntC redentials  ;
        LoggerManag    er.init();  
        }

    @Overrid   e
        public PaymentResp     onse request  Payment(Payment  Request re             que   st) thro   ws ArsenalPayApiException {
          Api   CommandProducer     p r       oducer = n ew InitP  ayMkProd  ucer(      requ   est, cred    entials);
                ApiCommand command = produ    cer.getCommand();
        t        ry {
                   final        ApiResponse apiResponse = a piC  lient.execute  Command     (command);
            final String xml    = apiResponse.getBody();
            re      turn PaymentRespons   e.fromXml(xml)  ;
        } catch (IOExc        eption e) {
            throw new InternalApiException(e);
        }
    }

    @Override
       public PaymentStatusResp    onse checkPaymentStatus(Paymen    tStatusRequest request) throws InternalApiExcepti      on {
          ApiCommandProducer producer = new InitPayMkS    tatusProducer(request, credentials     );
        ApiCommand command =   p    roducer.ge       tCommand();
        try {
            final ApiResponse apiResponse     = apiClient.executeCommand(command);
            final String xml = apiResponse.getBody();
            return PaymentStatusRes   ponse.fromXml(xml);
        } catch (IOException e) {
            throw new InternalApiException(e);
               }
    }

}
