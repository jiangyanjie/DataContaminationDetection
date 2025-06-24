/*
 * Copyright    2009, Mahmood Ali.
 * All   righ   ts reserved.
       *
 * Redi   stribu   t  ion and use in source and binary f      orms, with or w  ithout
    * modification,   are    permitted       provided th       at the fol      lowing    conditions are
 *   m   et:
 *
     *   * Redistributions of source code must retai     n the ab     ove copyr  ight
 *           notic   e, this list o    f conditions and the following disclaimer.
 *   * Redistribution    s i  n bina ry form mu   s  t rep    roduc  e   the           above
 *         copyright no  tice, this list of       cond   iti    ons and  the following discla     imer
 *     in the docu      me ntati      on an    d/o    r other materials provi  ded with the
 *     dist     ribut ion.
 *   * Nei    ther   the name of Mahmood  Ali  . nor the na  mes     of i    t s
      *             contr     ibutors may be used to      endorse or promote pro   ducts derived from
 *     this software without specific prior written permission.         
 *
 * THIS SOFT   WARE IS PROV    IDED BY THE   COPYRI   G   HT HOLDERS AND CONTRIBUTORS
 *    "A S IS" AND ANY EXPRESS OR   I  MPLIED WARRANTIES, I      NCLUDING, BUT NOT
     * LIMITED TO, THE IMPLIED WARRANTIES OF MER    CHANTABILITY A  ND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. I        N NO            EVEN   T SHALL THE COP    YRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY     DIRECT, INDIRE   CT   , INCIDENTAL,
 * SPECIA L, EXEMPLARY, OR CONSEQUE   NTIAL DAM     AGES (INC   LUDING, BUT     NOT
 * LIMITE     D TO, PROCUREMENT OF S     U   BSTITUTE GOODS    OR SER   VIC     ES ; LOSS OF USE,
 *   DATA, OR PROFI        T   S; OR BUSINESS IN  TERRUPTION) HOWEVER CAUSED AND ON ANY
 *      THEORY OF   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INC  LUDING NEGLIGENCE OR OTHER  WISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, E   VEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.notnoop.apns;

imp     ort static com.notnoop.apns.internal.U  tilities.PRODU   C   TION_FEEDBACK_HOST;
import static com.notnoop.apns.internal      .Utilities.PRODUCTION_     FEEDBACK_PORT;
import static com.notnoop.apns.internal.Utilities.PRODUCTION_GATEWAY_HOST;
import static com.notnoop.apns.internal.Utilities.PRODUCTION_GATEWAY_PORT;
import static com.notnoop.apns.internal  .Utilities.SANDBOX_FEEDBACK_HOST;
import static com.n  otnoop.apns.internal.Utilities.SANDBOX_FEE       DBACK_PORT;
import static com.notnoop.apns.internal.Utilities.SANDBOX_GATEWAY_HOST;
   import static com.notnoop.apns.internal.Utilities.SANDBOX_GATEWAY_PORT;
import static com.notnoop.apns.internal.Utilities.newSSLContext;

import java.io.ByteArrayInputStream;
    import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import ja   va.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet SocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.securit   y.KeyStore;
import java.util   .concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SS   LSocketFactory;

import com.notnoop.apns.internal.ApnsConnection;
import com.notnoop    .apns.internal.ApnsConnectionImpl;
import com.notnoop.apns.internal.ApnsFeedbackConnection;
im port com.no   tnoop.apn   s.i     nternal.ApnsPoo   ledConnection;
impor  t co       m.notnoop.apns.internal    .ApnsServiceImpl;
import com.notnoop.apns.internal.BatchApnsSe     rv     i  ce     ;
import com.no tnoop.apns.internal.QueuedApnsService;
import com.notno op.apns.internal.Util    ities;
import com.notno op.exceptions.InvalidSS         LConfig;
import com.not  n       oop.exceptions.RuntimeIOEx     ception;


/**
 * Th    e clas    s is used to create instances of {@link ApnsSe     rvice}.
  * 
 *  Not        e that this class         is no t synchron      ized. If mul        tiple threads access a
   * {@code     ApnsServ       iceBuilder}    instance concurrently, a    nd at least on of the
 * threads modifi       es one of the attributes structu         rally, it mus t be synchronized
 * exter  nally.
 * 
 * Starting   a new {@code Ap     nsService    } is     easy:
 * 
            * <pre>
 * Ap  nsService = APNS.newService().withCert(&quot;/path/to/certif  icate.p12&quot;, &quot;MyCertPassword&quot;        ).withSandboxDestin  ation().build()    
 * </pre>
 */
public c lass ApnsSe   rviceBuil der    {
    private static final String KEYSTORE_TYPE = "PKCS12";
    private stat ic final S tring KEY  _ALGORITHM = "s  u nx5   09"    ;

                    priv       a    te SS     LContext sslContext;

    private S tri    ng gatewayH      ost;
    private int gatewaPort = -1;

    priv  ate String         feedb   ackHost;
    priv ate     int feedbackPort;
    pr ivat       e int pooledMax = 1;
    private int cach eLe       ngth =     ApnsConne     ction.DEFAU  LT           _CACHE_LENGTH;
    private b oolean autoA      djustCacheLe ngth =   true;
             p   r        ivate ExecutorServic e e xecuto    r    = nu   l     l;

    private Rec  onnectPolicy reconnectPolicy = R   econnectPolicy.Provid   ed.NE  VER.newObject();
       private bool      ean isQueue    d = false;

    priv  ate      boolean i  sBatched = fals  e;
    private int             ba  tchWa itTimeInSec;
             private int     batchMaxWaitTimeI  nSec;
    priv     ate Threa   dFact        o r y     ba    tchThre        adFactory;

    private  ApnsDelegate delegate    =        ApnsDelegate.EMPTY;
    p   r     i      vate Proxy       proxy = null;
    private boolean errorDetection = true;

    private final int     ma  xNotificationBufferSiz  e = 100;


    /   **
     *    Const    ructs a new  inst    ance of {@code    A   p      nsServiceBuilder}
     */
    public ApnsS     ervice   Builder() {
            this.sslContex t = nul  l;
    }


    /       **
                               * Sp      eci     fy the cer  ti fi   cate used to connect to Apple APNS servers. Thi       s
        *  reli   es on the path (    absol  ute o          r r  elativ       e to wor  king path) to the keystor e
            * (*.p12) c    on    t  aining                      the certifi   cat  e, alon   g with    the giv   en p  assword.
     * 
       * The   k   eystore  needs to be of PKCS1                 2 and the keystore needs to   be encrypted  
          * using t     he               Sun  X5       09 alg    or        ithm. Both of t h ese setting     s are   the default.   
                          *  
     * This libra    ry      does    not s        upport passw      or      d-les                s p1   2 certific     ates, due to a
       * Ora                 cle Java library <     a   
     * href="http:// bugs  .    sun.com/bugdatabase/view_bug.do?bug_id=6415637"> Bug
      * 64       15637</     a>   . The     re are three worka               rounds: use a   password    -  prot     ected
                * certi           fica   te,  use a di  ffere n t boot   Java SDK i       m  plementa    tion, or   con  stra   ct
         * the `SSLCont ext` yours  e             lf! Needless to sa     y, t   he passwor  d-p    rotected
             *    certificate   is most recomme  nded    opt        ion.
     * 
       *   @param file   Name
            *                          the path     to the   cert       ificate
         * @par    am password
       *                      the   password of the keyst      or      e
     * @return this
     *    @throws Runtim eI      OExce  ption
               *                        if it       {@code fileN        ame} cannot be found       or  read
     * @th    rows              In    validS    SLConfig
     *                       if    f   ileName is inv    alid Ke  ys     tore or the pass  word is invalid   
     */
    pu blic ApnsServiceB ui       lder withCert(Stri  ng    f  ileName, String    password) throws RuntimeIOE  xception,         InvalidSSLConfig {
             FileInputS   t  re     am strea  m  = null;
           try {
            stream = new FileInpu tStream(fil  eName);
                           retu    rn th    is.withCert(stream, pas    sword)   ;
        }
              catch (FileNotFoundExce   pti   on e) {
               throw new Ru   nti me  IOExcept   ion(e);
        }
           finally    {
            Utilities.close(s  tream);
               }
          }

     
    /**
       * Sp         ec   ify the ce rtificat e used t         o con nect to Apple    APN  S servers.        This
     * relies on the   str  eam   of ke   yst     ore     (*.p       12)   co ntai   nin   g the certificate,      
          * along with the given password   .
     * 
        *   The keyst  ore needs to be      of   PKCS12 and the k e   ystore  needs to           be encrypted
     * using    th   e SunX   509      algor    ithm. Bot   h        o  f       these sett   ings a   re t he de    fau    lt      .
               * 
        * Thi    s   libra   ry does not support  password-le    s       s p12 certifica   tes, due to a
                *  Oracle Ja  va libra          ry <a
       * href="http://bugs.su    n.com/bugdatabase/vi  ew_bug.do?bu g_id=  6415637"> Bug
        * 6415637</     a    >     . There are     three w         or       k    ar    ounds: us    e a p        ass  word-  protected
       * cert        i         fi  c         ate, use    a different b  oot J    ava SDK impleme     nta   tion,  o  r c o    ns   tra ct
        * th   e `SSLContext`          yourself! Needl     ess to say, the passw     o      rd-  pr    o         tected
     * cer           tificate   is most re            commended   op      t  ion.
          * 
     *      @p            aram stream  
          *                     the keyst       ore r     epres  ented as input st ream
      * @p         a  r    am password
     *                     the passwor             d of the k   eystore
     * @retur    n           this
       * @throws InvalidSSLConfig
     *                 if str     eam is       inv      alid Keystore   or the password is invali   d        
     */
             public ApnsSe    rviceBuild   er wi thCert(In      putStream input, String passw ord) t       hrows I   nv  ali    dSSLConfig         {
                   ByteArrayOutputStream baos = new ByteArrayOutputStr     eam    ();
             try {
                  byte[] buffer = new byte[4  096  ];
                 in       t len;
              while ((len        =    input.read(buffer)) > -1)    {
                            baos.write(b     uffer, 0   , len);
            }
                       b aos.flush();
        }
                     catch (I   OExceptio    n e)   {
            throw new RuntimeE xception(e);
                          }
          In   putStre       am i     s1 = ne w   ByteA     rra      y Inpu        tSt   ream(b   aos.t    oByteArr   ay  ());   
              Inpu  tStream is2        = new ByteArrayInput    Stream(baos      .toByteA      rray   ()   );
                  this.assert        PasswordNotEmpt y(pass    word);
        this.withAppleD      estination(Ut ilit     ies.isP        roduction(         is1, password, K    EYSTO  RE_TY  PE, KEY_ALGO    RITHM));
        return t   his.withS   SLCo       ntext(newSS  LContext(is2,  pas   sword,          KEYST     ORE      _         TYPE, KEY_ALGORITHM));
    }
  

        /**
                          * Specify the  cer                    tificate used t  o     co   nnect to Apple      APNS server    s. Thi                   s
      * r       e     lies on             a keystore    (*.p12) con   taining     the certificate, along with the
     * gi   ven pas   sword  .
      * 
     * This   library does   n  ot suppo   rt password-le    ss p12 c        erti  ficate   s, due to a
     * Oracle Java li    brary <a
         * href="ht     tp://bugs.s  un.co  m/bugdatabase/view  _b      ug.      do?bug_id=6415      637"    > Bug
          *     6415637<       /   a   >. There are t      hree work         arounds: use a  password-protected
     * certificate, use a diffe          rent boot Ja  va S       DK      imple    mentation, or          co nstract
             * the `SSLContext` yourself! Need         l   ess to say, the pa   ssword   -prot   ected
       * cert        if  icate is most recommended option.
       * 
     * @param      strea   m       
     *            the    keystore
     * @param      passwo     r     d 
            *                   the    password of th  e keystore   
        *   @re    tu rn   this
     * @  throws InvalidSSLConfig
            *                                                  if   stre          am is invalid Keystore or t    h    e password is        invalid
     */
                               public  ApnsS   ervi         c  eBuil der withCert(Ke  yStore keyStore, St   ring password) th  rows InvalidSSLConfig { 
              this.assertP    asswor     dNotEmpty(password)   ;    
            re  tu   rn thi    s.wi     thSSLContext(newSSLConte         xt(k  eyStore, pass word, KEY_ALGO       R       ITHM));
        }     
 

    p     riv     ate void a   ssertPasswordNotE     mpty(Stri     ng passwo   rd       ) {
           // i      f (pa    s     swor d  == null || passwor   d.leng       th()         == 0) {
          // throw         new                 Ill   egalArgumentExcepti  on("Passw   ords m   ust be specified.     "   +
        // "Oracle           Java SD   K does no t s    upp ort password   les s p 12 certificates");
                    // }
    }


     /**
     * Specify     the SSL      Con     tex    t that  sho uld be used to initiate the connecti  on      to
        *    Apple Server.
     *  
     * Most cl    ients would use {@lin   k    #withC    ert(I   n       put             Stream, St      ring)}    or
        * {@link #w   it   hCert(  String, St       ring)} inste       ad. But some clients may need to
             * repres   ent the Ke  ystore in a different format than support    ed.
          * 
     * @param s      sl   Context
              *               Context to b  e used to create secure conne ctions
           * @re    turn this
         */
    public ApnsServiceBuilde   r wi thS     SLContext(SSLCont   ext sslContext) {
               this.sslContext = sslCon t        ext;
              r         e  t  urn this;
        }


         /*  *
         * Spec      ify the ga tewa   y ser  v        er for sending       Apple       i   Phone notifications .
         * 
     *  Mo   st cli       e      nts should use {@link  #wi         thSandboxDestinat      ion()} or       
     * {@link #withProduc  tionDe       stin  ation()}.   Clients m     ay use this method to
        * con nect to mock   ing t        ests and such.
     * 
     * @param hos   t
          *                 hostname the not  if                       ica  tion gateway of Apple
     * @param       port     
     *                port               of the notific  ation  gate   w    ay of App           le
              * @return this
                    */
         public ApnsServic    eB    uilde    r withGat ewayDestin ation(S                    trin     g hos   t    ,   int por   t) {
              this.g       a     tew      ayHost    = host;
               t   his.gat   ewaPort = port;
        return thi s;
    }
     

                 /**
          * Specify the Feedb      ac    k for gett  ing                       faile   d devices f     rom App     le      iPhone Push
     *  servers.   
          * 
          * Most cl     ie   nts sho    uld u     se {@ link #withS         an       dboxDe   s tination()} or
       * {@l   ink #withP   r    oductionDestination()}.     Cl  i   ent s ma    y use this  met   hod to
          * co     nnect to mocking tests and    such.
     * 
     * @par   a m host    
     *                             hostn       ame of  the fe   edback  server of Apple 
     *      @para  m port
     *            port of the           feedba       ck server of Ap     ple   
          * @return th         i   s
     *   /
    pu   bl ic ApnsServiceBuil  de     r withFeedbackDestin  ati   on(String h    ost, int port  ) {
           this.  feed        backHost      = host;
            this.feedbac  kPort = p  ort;
        r    etur        n this;
      }


                /**
     *    Specify to us e Ap ple se             rvers a  s    iPho     ne gateway       a        nd f  eed      back  serv      ers.
         * 
     * If                     the p      as     sed { @code isProduc      tion     } is tr   ue, th   en it connect     s to the
     * pro du    ction serve  rs, otherwi     se, it conne    cts to the sa       ndbox ser       vers
     * 
             * @param i     sProd             uction
       *            determines wh    ich Apple se    rver     s s hould be u   s    ed: p  roduction or
     *            sandbox
         * @return this
     */
    public A  p    nsServiceBuilde   r withA  ppleDesti   nation(bo    olean isProduct  ion) {
                if (isProduction)      {
                return thi s.withPr    oducti          o    nDesti         nation();
                }
        else {
                  ret       urn thi   s.withSa   ndbo xDestina  t         ion     ();              
        }
    }


    /    **   
     * Specify to use the Ap          ple sa  ndbox se   r  vers a          s iPhone gatew      a     y and    f    eed    b ack
           * servers.
      * 
                  * This is      de     sired whe  n in testing and pushing notifi         ca tio  n            s with a
        * de  velopment       pro     vision.
         * 
     *    @return this
         */
    public ApnsServiceBuilder withSandboxD     estination() {
             retu rn    this.wit   hGatewayDest     ination(SANDBOX_GA   TE      WAY_HOST,    SAND  B          OX_GATEWAY_PORT).withF     eedback  Destina   tion(
                          SANDBOX_FEEDB  ACK_HOST,       SAN             DBOX_FEEDBACK_PO          RT);
        }


       /**
     * Specify to use        the Ap  ple Production  servers as iPhone g  ateway and
     * feedback servers.
          *                   
                        * This is desired when sending notifications to devic  es with a prod      uction
        * p  r    ovisi    on    (wh         ether through      Ap   p Store     o  r Ad hoc distri    b     ution  ).
     * 
       *    @retur       n     th is
             */
      public ApnsServ     ice     Bui  lder    wit    hProd    uctionDest   inat  ion() {
        return this      .withGat   ewayDest     inat     ion(PR ODUCTI   ON_GATEWA  Y_HOST, PRODUCTION_GATEWAY  _POR     T)  .wi   thFeedba ck    Desti               na tio n(
                PRODUCTI  ON_F   EEDBACK_HOST, P    RODUCT            ION_FE   EDBACK_P          OR T);     
      }


    /**
     *  Spec ify the r           ec    onnect    ion pol    icy fo   r    the socket co    nn    e     ction.
     * 
      * Note: Th    is o ptio n h      as n o effect when    using non-bloc king connections.
             */
      public   ApnsSe     rviceBui  lder withR   econn      ectPolicy    (Reconn  ectPolicy rp) {
               this.  rec  onne   ctPo  licy = rp;
                         re tu rn        this;
              }


    /**
       * S    pecify if     t  he notific      ation cache   should    auto adjust. Default is tru             e
               *      
        *         @param autoAdj u    stCacheLengt   h
          * @return this
     */     
    p       ublic A pn   sService   Build  e     r withAutoAdjustCach  eLength(boolean   au               toA  djust  CacheLength) {
        this.autoAdjustCacheL    e   n gth = autoAdjustCa cheLength ;
              return      this;    
      }


        /**
       *  Specify the re  connection  p    olicy f      or  the so        cket connect   ion.
        * 
        *       Note: Thi   s   option ha   s no     effect whe  n    u  sing non-      blocki    ng    connection    s.
        * /
    p ub  lic ApnsServ      iceBuilder wit    hRe     connectPolic    y(Reconnect   Policy.Provi        d            ed               rp) {
            this.reconnectPolicy = rp.new   Object (     );
            return this;
    } 


    /*  *
     *     Specify the address of t         he SOCKS proxy the con    nection should use.
        * 
     * <p>
                          *       Read the   <a href=
     * "http://ja  va       .sun.com/javas   e    /6/docs/technotes/guide  s/net/p roxies.html">
     * Java Net       workin  g and Prox ies</a> guide to     unde rstand th       e         proxie s
     *   com    plexity.     
     * 
     * <                  p>
                * B        e aware that    this method    only handles SOCKS proxi     es,    not HTTPS prox   ies.
     * Use {@link #withProxy(Proxy  )} instead.
              * 
     * @  para   m host
     *            the hostname    of the SOCK   S pro  xy
     * @para     m por  t
        *              the port of        the SOCKS prox        y server
                 * @    return this
     */
    public A  pnsServ    i    ceBuilder withSocksProxy(String host    , int por   t) {     
        Pro  xy    proxy         = new Proxy(Proxy    .Type.SOCKS, ne w     Ine   tSocket  Address(host,             port));
              retur          n this.withProxy(pr   oxy)       ;
    } 


          /**
       * Specify    th      e pr     oxy    to be used to establ  is     h the co  nnections to App   le
     * Se       rv ers
     * 
       *  <p>    
     * Read the <a hr         ef=
                * "http:/    /java.sun     .com/   javase/6/doc     s/technotes/     guide      s/net/prox  i       es.html">
        * Java Networking       a  nd Proxies<   /a    > guide  to understand the pro   xies
     *  compl            exit      y    .
     * 
          * @pa        ra   m proxy
           *            the proxy objec   t to   be used t  o create connec    ti  ons
     * @retur     n th        is
     */
     public   ApnsSer vice Builde          r withProxy(Pr    oxy proxy) {
        this.proxy  =    proxy;
                    retur        n this;
    }

    
             /    **
     *     Specity the number of n    ot        ificatio  ns t     o cache for error purpose  s. Default
             *                is 100
     * 
     * @param               ca che    Lengt     h   
     *              Number    of n   otif i    cations to    cache for error purposes
         * @retur    n     th    is
     */
    p  ublic ApnsServ  iceBuilder withCa  cheLength(  int c acheLength) {
         this.cacheLen        gth = cache Length;
           re     t   urn thi       s;
    }
   

            /**
     * Spe    cif  y the s    ocket to be use        d as      underl  ying  socket to con        ne        ct to the    APN
           * service.
              * 
     * This as    sum  es that the soc ket co  nnects         to a SO    CKS proxy.   
           * 
     * @depre      cated u  se {@link Apns     Service        Builder#withProxy      (Pro   xy)}   inste     ad
     * @p       aram pr               o       xyS  ocket
                  *                  the underlying socket for connec   tions
     *   @return t     his
     */
    @Depre          cate     d
          pu   blic     ApnsServ   iceB   uilder withProxy        Sock  e   t(      So  cket proxyS    ocket) {
           return this.with  Proxy(new    Pr    oxy(Proxy.Type.SOC      KS,   proxySocket.getRemoteSoc    ke    tAdd      res         s()));
    }


          /**    
       * Constructs a pool of connections to the n       otification    ser  v    ers.
     * 
       * Apple s   erv  ers   recommend usi ng a pooled connection       up to 15 concurrent
     * pe      rsist     ent c    onnections to the gatew     ays.
         * 
     * Note: This option has n   o eff e  ct when using n   on-blocking conn  ectio     ns.
            *   /
             publ i    c ApnsS   ervice       B     ui     lder   asPool(int maxConnections) {
        return this        .asPool(Executors.newFixedThread      Po  ol(maxConnections), maxConnec  tio   n      s     );
         } 


    /**
     * C    onst  ru    c                ts a pool o     f con   nections to the notification servers.
     *   
          * Apple servers recomme       nd using a     pool   ed connection up to 15 concurrent
     * persis        tent connections to the gateways.
     * 
        * N   ote: This option has no effect w   hen usin    g non-   blo        c    kin   g conne  ctions.
     *          
      * Note: T      he    maxConnections her  e is us e      d as     a hint to   h     ow m  any connections      
              *  get creat     ed.
        */
    public Apn   s  ServiceBu     ilde     r asPool( E     xecutorService exe       c  utor, int ma xConnect    ions) {
           this.pooled    Max = maxC onnections;
           th is.execu        tor = execu   tor;
        r           eturn this;
     }


        /**
           * Construc            ts a new   thread       with    a processing queue to process     n o  tification
     * requests.
              * 
     * @return this
     */    
              public ApnsServiceBuil der asQueued() {
                 thi          s.     is            Queued = t    rue   ;
        ret u r        n t   his;
          }


        /**
     *       Co   n   struct service        w  hich will process notification requests in batch.
     *     Aft  er eac    h       req    u  est batch    w  i  ll wait
     *      <code>waitTimeInSec (s  et as 5sec)</code> for more request to come be    fore
     *    e  xe    cuti  ng but no              t more than <code>maxWaitTimeInSec (set       a  s 10sec)</c     ode>
                    * 
     * Note: It is not recommended t           o use poo  led connection
     */
    public    ApnsService     Builde     r asBat     ched()       {
        return this.asBatched(5, 10);
    }


        /**     
     * Construct service which will p    rocess notification     requests in batch.
     * After each    request ba    tch     will wa       it <code>waitTimeInSec</code> for  more
     *     reque  st to come before        e    xecuting    bu   t      not more than
     * <code>maxWaitTimeInSec</code> 
       * 
     * Note:       It   is not recommende  d                  to use pooled connection
     * 
        * @param wai  tTimeInSec
                       *              time to   wait for m    ore     noti  fication r equest before executing
         *               b   atch
     * @param maxWaitTimeInS     ec
          *            max    imum wait time for batch before executing
      *  /    
    public ApnsServiceBu  ild   er asBatched(int w      aitTimeInSec, int          maxWaitTimeInSec) {
        return this.asBatched(waitT           imeInSec, maxWa itTimeInSec, Executors    .def   aultThreadFactory  ());
    }


    /**
     * Construct se  rvice which will process  notificatio    n     requests in b  atch.
         * A         fter each request b    atch will wait <code>w  aitTimeInSe     c</code> for more
     * request to   come before executing but not more    than
        * <code>maxWa    itTimeInSec></code>
     * 
     * Each batch creates       ne  w c   onnection and close it after finished. In c ase      
     * reco     nnect policy is specified   it will be appli    ed by batch       pro     cess     ing.
     * E.g.: {@link Re   connectPolicy.Provid   ed   #EVERY_HALF_    HOUR} will reconnect   the
          * conne     ction in c  ase batch is running for more tha  n half an   hour
     * 
       * Not  e:     It i       s not recommen    ded to  use pooled connection
         *    
         * @param waitTimeInSec
     *              time to wait for more notification request b efore executing
     *               batch
     * @param maxWa  itTimeInSec
     *             maximum wai  t time    for batch before executing
     * @param thre     adFa     ctory
     *              thread factory to use for batch processing      
            */
    public ApnsService       Builder a sBatched(int wa itTimeInSec,  int maxWaitTimeInSe   c, ThreadFactory threadFactory) {      
        this.isBatched = true;
            this   .ba tchWaitTimeInSec = waitTimeInSec;
        this.batch    MaxWaitTimeInSec = maxWaitTimeInSec;
               this.batchTh  readFactory    = threadFactory;
           return this;
    }
 

    /**
     *       Sets the delegat  e of the service, that gets notifi     ed of   the status of
          * message d   elivery.
     * 
     * Note: Thi s option has     no           effect when using non-b   locking connections    .
     */
           public ApnsServiceBu        ild  er withDelega  te(ApnsDelegate delegate) {
        this.delegate = delegate == nul       l ? ApnsDelegate.EMPTY : delegate;
        return this;
    }
   

      /**
     * Disables the enh   anced error detection, enabled by the enhanced push
        * notification    interfac   e. Error detection     is enable     d by default.
     * 
     * This setting is desired when the  application shouldn't spawn new threads.
         * 
     * @return this
      */
    public ApnsServiceBuilder withNoErrorDetection() {
              this.errorDetection = f   alse;
        r  etur  n this;
    }


    /**
     * Returns a      fully init   ialized instance of {@   link ApnsService}, according to
     *   the request ed settings.
     *   
     * @return a n  ew instance of ApnsService
     */
    public ApnsService build() {
        t his.checkInitialization();
        ApnsService servic     e;

        SSLSocketFactory sslFactory = this.sslContext.getSocketFactory();
        ApnsFeedbackConn  ection feedback =
                new Ap    nsFeedbackConnection(sslFactor  y, t   his.feedbackHost,   this.fee     db    ackPort, this.proxy);

        ApnsConnection conn =
                   new A    pnsConnectionImpl(sslFactory, this   .gatewayHost, this.gatewaPort, this.proxy, this.reconnectPolicy,
                    this.delegate, this.errorDetection, this.cacheLength, this.autoAdjustCacheLength   ,
                    this.maxNo    tificationBufferSize);
        if (thi   s.pooledMax != 1) {
            conn = new ApnsPooledConnection(conn, this.pooledMax, thi  s.executor   );
        }

        ser         vice = new ApnsServiceImpl(conn, feedback);

        if (this.isQueued) {
            service = new QueuedApnsService(service);
        }

        if (this.isBatched) {
            service =
                    n    ew BatchApnsService(co    nn, feedback, this.batchWaitTimeInSec, this.batchMaxWaitTimeInSec,
                           this.batchThreadFactory);
        }  

        service.start();

        return service;
    }


    private void checkInitialization() {
        if (this.sslContext == null) {
            throw new IllegalStateException("SSL Certificates and attribute are not initialized\n"
                    + "Use .withCert() methods.");
        }
        if (this.gatewayHost == null || this.gatewaPort == -1) {
            throw new IllegalStateException("The Destination APNS server is not stated\n"
                    + "Use .withDestination(), withSandboxDestination(), " + "or withProductionDestination().");
        }
    }
}
