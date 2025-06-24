/*******************************************************************************
  * Salt   Payment Client API   
 * Version 1.0.0
 * http://salttechnology.github.io/core_api_doc.    ht m
 * 
 * Copyright (c) 2013 Salt Technology
    * Licensed under the MIT license
 * https://github.com/SaltTechnology/salt-payment-client-java/blob/maste       r/LICENSE
 ******************************************************************************/
package com.salt.payment.client.creditcard.api;

import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.Schedule;
    
import java.util.Date;
     
/**
     * The int   erface for performing creditcard req  uests .    All reques     ts will return a
 * receipt, which must be check ed to see if the requ  e  s      t was   approved or if there
 * was an error.
 * 
 *    @since JSE5
 */
public int         erfac     e Credi  tCardService {
    // erro    r code s
     public final stat  i     c int REQ_MA       LFORME     D_U    RL = -1;
      p        ublic final static in  t REQ_POST_  ERROR = -2;
    public final static int  REQ_RE   SPONS      E_ERROR     = -4;
        publi       c final sta        tic int       R  EQ_C  ONNEC   T  ION_FAILED = -5;      
    pu blic final static int REQ_IN  VALID  _REQUEST = -6;
 
      /**
                * Iss    ues a req   uest for an   inst a         llment purchase               to be mad   e   by mon  thly
       * insta llme   nts.
     * 
      *               @param   o   rderId
             *                           the   merchant    ass igned      o  rder   I     d to    assoc   iate with this   req    uest.
            *                    Not       nu  ll.
              * @param creditC  ard
     *                     the creditcard       use             d fo         r making t                 he install     ments.    Not null.
        * @param perI          nstal   lmentAmount
        *              the a      m     ount of each           installmen   t     in        pen   nie           s. The   currency is in
                                *            t   he m   erc     hant's defau   lt cur    rency.                
          *       @param startDate
     *                   the d    at   e o         f w   hen    to    issue    the firs          t inst          allm e      nt. T   he            
     *                              i ns   t  allment may   b      e is     s ued   at a   ny   time   of th        e day. If this   dat     e
     *                   is nul   l or    i   n the past, th         e   n        the f   ir  st installment will be
     *                issued im   mediately.
       * @pa          r    am   totalN       umberInstallmen      ts
     *               t he  to  tal numbe    r of installments of the purch   a  se
     *    @param verificationRequest
     *            indicate  s ho    w to ve         rif y th  e      creditcar    d. If nu     ll, then no   ex  t ra
     *             c  reditcard verification     will be perf      orme   d.
     *    @retur   n the i  nstallme          nt pu   rchase receipt
     */
     pub             lic   CreditC ardRece           ipt           instal  lmen   tPurc    hase(Stri  ng   o     rderId,    CreditCa   rd credit Card, 
                                lo     ng per     Inst   allmentAmount, Date startDate    ,   int total   Number     Insta llments,
                     Veri  ficatio      nRequest verificationRe               quest);

        /**
     * Is  sue   s a         request for     a re           cu rring purchase to   be m  a       de by  the s   pe cifi       ed
        * s chedule.
                * 
            * @param or              derI        d
     *              th   e merchan t assigned  ord      er        Id        to associate wit        h this
     *               transacti o   n. Not null  .
                * @      param cr editCa      rd
            *                the creditc   ard used for making the payment        s. N     ot    null.
     * @param    perP   ay mentAmou n   t
                   *                   the amoun     t of each pa  yment in pen   nies. The currency      is in the
            *                                mer       chan       t's defau   l               t c  urre   n     cy.
     * @param sta   rtDate         
              *                  the   date of w   hen                           to issue the fi rst    payment.         The payment m   ay    
         *              b   e issued at any t     ime          of     the    day.            If  this dat     e is null or in
           *            the past, then the first pa  yment w  il     l b        e  issue    d imme   diately.   
        * @param endDate
     *            the   dat      e      o   f when to end the          payments           . Th e  paymen         t will  not b     e
     *                     issue      d i   f it fall              s on  this   date. If this date   is n u  ll,           then   
        *                  t   h e       paym  e   nts will      never        end.
          * @param      schedule
     *              the schedule on which to  issue paymen  ts; i   f    null then will
     *              default t    o a     once-m     o  nthly schedule
     * @par      am verificationRequ      es   t
     *                              ind   icates how to                          ve    rif   y the          creditcard. If null, th    en no         extra
     *                                               cre     ditcard verificati on will be p erform  ed.        
                        * @re  t    urn th  e   re    curring purc    hase receipt
      */
      public CreditCa         rdR   eceipt recurringPurchase        (S      tring orde       rId,   Cr   e     di   t Card         c reditCard,
                                           lon g   p  er   PaymentAmount,                           D    ate    startDate, Date end Date, Schedule sc    hedu        le      ,
                 Verificati   onReques  t verificati     onRequest);

    /        **
                      * Issue    s               a re     quest for  a recurring p     u      rchase t  o be m   ad       e b    y the  spe   ci     fied
           * sched   ule.
                               * 
     * @p         aram        or    der     Id
     *                   the   m   erchan  t a ssig ned orderId to associate   wi  th th is
     *                                     transact  ion. Not              null.
     * @param stora  ge   To  k     en    id
       *                    specifies the sto    red             credit card to be used. No t null.    
     *               @param perP     aymentA    mount
     *                 the am  o  unt      of ea   ch payment in    pennies.         The curre  ncy       is in the
                *            merchan   t 's default curre   n   cy.
       * @p  a   ram st    art Date
     *               th         e date of w     hen t   o is  sue the f irst p  ayme   nt.    T    he paymen    t m ay
        *                  be issued at any    time         of   the day. If   this date is n   ull o         r in
                       *               the    past, th  e    n the   firs t payment wi    l    l            b   e issued immediatel     y.
     *     @  param   endD      a    te
          *                   the date o    f w          hen to en       d the paymen   ts. The payment wil l not b           e 
           *            issued if i      t                fall    s on this date.       If thi      s date  is null, then
     *            the payments wil l   n  ever end.
     * @param schedule
        *              the     sche  dule on               which to issue payments; if       null then will
     *            def   ault to     a once-monthly sche      dule
             * @param     verifica tio        nRequest
        *                    indicate  s    how to verify the creditcard. If        nu l         l,     then no extra
        *            credi    t  card   verific  ation          will be per    form         ed.
              * @return the   recurring purchase receipt
     */        
       publi   c CreditCardR eceipt recurringPurchase         (    String orderId,    String storageTokenId,
                                        lon         g  per        Pay                me  ntAmount, Da te  sta   rtDate,  Date endDa  te     ,        Schedule s   chedu   le,
                V erificationRequest   verificationRequ  e st)     ;

    /**
     * Iss ues a re quest for   a    r            ecu  rring purch  ase       to be mad   e by th    e specified            PP
              * o rder definitio  n.
     * 
     * @param          pe    riodicP     urchaseIn fo
      *                   defines the               Pe  riodi     c                  P      urchas   e    params
                        *     @  p         ara   m  creditCa  r   d
        *            the c       reditcard      used        for maki  ng the     payments. N ot nul  l.
       * @param storag              eTokenid
       *                       spec   ifies the        stored cr   e     dit card t   o be used. N      ot null.
     * @param v    erifica t   io    nReq      uest
       *                indica    tes     how to verify the creditcard.   I             f null,  the     n no      extra
     *            cre     ditcard    veri    ficatio    n   will be    p    e          rformed.    
     * @return the re   curring p     urchase receip t
               *  /
      p    ubli     c     CreditCardRece     ipt recurr  ingPurchas    e(P   eri odicPurch    as  eI    n fo peri odicPurcha   seInfo,
                  Cr   editCard c    reditCard, String            stora geTokenId, Verifi  ca  tionRequ     est verif    icationReques      t);

         /**
     * I     ssues a         re      quest for a     r  ecurring purch            ase to be made by the specified PP
     * or der    definition.
                 * 
     *     @para      m periodi   cP  urchase Inf    o
              *                      de fines the   Pe riodic     Pur  chas e         params
     * @param creditCard  
       *                    the creditca    rd u    sed fo     r   m     aking     the payments. Not null.
     *   @    param      storageTo ke    nid
                *                s   pec                      ifies    the stored         cr          e  dit card  to be used. Not null.
        * @param ver         ificat     ionRequest
     *                indicat      es how to verify the c  reditcar   d.     If null, th   en no extra
          *            creditcard verification      will be pe rfo    rme d.
         * @param purch  aseCard
     *                      defin            es the  Pur  chase   C    ard       params
     * @return th    e recurring purchase   receipt
            */ 
          public CreditCardRecei     p  t recur r   in    gPurcha      se(Periodic    Purc       haseInf           o periodi      cPur  chaseIn     fo,
                   CreditCard cr  editCard, String  sto rage  To kenId,   Verif icationRequest v     erificat      ionRequ         est,
             Purcha           seCardR equest pur  c   haseCar  d);

       /**    
     * Exe cute the sp       ecifie  d       recurri   ng purchase.
         *     
     * @p  a   ram rec           urringPur   chaseI  d
     *                           I   D of     r ec      u       rring    pur  ch ase to    e   xecute.
         * @param cvv2   
          *                     Opti on    al.  V  erifie     s that     the       cr edit card               i      s         still   valid u   pon      
          *                  exe  cution of th e rec   urring   purc       h          ase.
     */
           pub lic C               redit    CardReceipt execut          eRecurr  ingPurchase(     L      on    g rec    u   r ringPurc    haseId,            Strin g   cvv      2);

    /**
                 * Puts a recurri ng purchase    on hold, if it is already on       hold nothi   n g w  il l
            * happen.
     *    
         * @param r       ecu  rr ingPurchaseI  d
           *            ID  o   f recurring purchase to p   ut on h        old
           */
    public CreditCar      dReceipt hol  dRecurring    Pu   rchase(Long recu r   r    ingP                 urchaseId);

        /  **
           * Re  sumes a recur   ri           ng pur             cha       s e that was on ho   ld,   if it is no       t        on hold
     * noth ing wi     ll happen.
     * 
     * @     p   aram recu rringPurch       aseId
     *                      ID            of rec   urri   ng p         ur    chase       to r    esume
     */
        public Credi  t  CardReceipt resume  R       ec  ur    ringPurch      ase(Long recur        ringPurchaseId);

    /**   
       * Ca  ncels  a recurrin  g pu     rchase, if it is already can     cell   ed    o r completed
     * nothin  g        wil   l    happen.
     * 
      * @pa  ram recurringP      urchaseId        
        *                   ID    of   recurring purchase to cancel
     *   /
    p    ublic C     redi tCardReceip t ca       ncelRec                    urringPurchase(Long recur   rin             gPurchase            Id);
        
         /**
     * R    eturns current information    ab  out the spec       ifi   ed recurrin  g purcha    se.    
     * 
     * @para   m recur    ringPurchaseId 
     *                  ID     of recurring pur                chase t    o que     ry.
        */
    public CreditC  ard       Receipt quer yRecurringPur              c      hase(Lon  g recurringPurchaseId);

    /**
         * Update  s the deta      ils of      a recurring purch  ase.
     * 
     * @param recurr  ingPu    rchas     eId
       *                ID    of recurring pur   cha  se to    que     r     y  .
     *   @pa     ram creditCard
     *               the c        reditcard us    ed for   maki  ng the payments.
           *    @param perPa    ymentAmou    nt
     *                     the amount of each payment   in pennies.  T        h   e          currency  is      in     the
         *            merchant'   s    default currenc   y.
                  * @param verificatio n    Request
     *                              indi              cates how to veri     fy the    c re dit         c   ard. If n               u       l    l, then no extra
     *                   credi    tcar   d ve   rific  ation will be perf    or   med.
     */
                     pu     blic Credi    tCa    rdReceipt upda   t  eRecurringPurchase (Long     r       e         c urrin       gPurchaseId,
                         CreditCard creditC   ard, Long   perPayment Am    ou     nt,  V  erific   ationR      equest    v      eri     ficati  onRe quest);

          /**
             * U   p dates th   e   details of     a     recurring purchase  .
     *   
       * @par              am recurringPurchaseId
                *                  ID of recurring pur      cha  se to qu       er     y.
     *         @param sto   r       ageTokenId 
     *              spec  ifies the sto        r ed    cr   editcard used for m aking th       e    p  ayments.
     * @pa    ram perPaymen  t   Am             ount
      *              th  e      amount of each payment       in pen       n   ies. T     he c        u  rrency is i   n th e
     *                 me rc  hant's         default          c         urrency.
     * @pa     ram v                 e  rificat   ionRequest
     *            indicate     s how to verif       y   the cre          ditcard   . If null, th e    n no e    xtr   a
     *                creditcard verificati on wil    l be perf     o  r  me    d.
         */
    public Cr     e  ditCar  dRecei              pt u      pdateRecurringPurchase(Long rec      urringPurc    hase  I       d,
                             String storage     To  kenId, Lon       g perPayme      ntAmount, V   er     ifica     t  i  onReques      t verificationRe quest);
    
      /*     *
     * Update        s      the details   of           a re     c    ur     ring     pur           chase    .
     * 
     * @p   aram per   io    dic  Pu        rc             ha seInfo
     *                       describe    s the upd   ates to      the PP    order to make
     *               @par    am cred     itCar   d
     *                          the creditcard use       d for maki  ng  the p  ay   ments. I      f        provided,
           *                                 s uperse         des storageTokenId
     * @pa  r  am stora    g      eTokenI   d
            *                      specifie  s the       stored   credi  tcard used for ma   king    th   e      paymen        ts   .
          *                                  S   upers    e     ded by   creditCard
     * @param     p            er   Paymen     t            Amount
            *                     th e a     mount of each payment in pennies. T he curre  nc  y is in the
              *               merchant's  d      e   fault c urrency.
     * @   pa       ra       m    verif    icat  ionRequest
              *                indicates h  ow     to verif      y the creditcard. If        null, th      en          no extr  a    
         *                         credit c       ard ver  ific   ation will be per formed     .
              */
    public Cre     ditCar  dR  eceipt updateRecurri       ngPurchase(Per  iodicPu      rch     a  seInfo perio   dic   PurchaseI     nfo,
              Cre       dit       Card   credit          Card, S  tri    ng    storag      eTokenId, Ver           ificationR  equ    e   st verifica     tion   Req      u  est) ;

    /**
         * Upd   ate      s the detail     s of a recu          r   rin   g pu  rch    ase.
        *                
           * @param pe  riodicP    urc  haseIn              fo
        *                        desc   ribe s t    h    e update    s to       th    e PP          or   d   er to       make
               *   @param cred      itCard
                   *            the cre   d  itc    ard used for m     aking the payme    nts. If provided,
          *               supe  rs    edes stora geTokenId
          * @ para    m stor    ageTo   kenId
     *              s   pe ci     f             ies the stored    c  redi    tcard   used f     or making the    pa yments.
            *                    Superseded by     creditCa     rd  
          *    @param perPaymentAmou     nt
          *            the        amoun              t of each pa   yme      nt in      pennies. The cu rrency is in      the
          *                me   rchant  's default currency.
     * @param verifica   tio    nReques     t
     *                                     ind   ic  ates how to verify th       e credit       card. If nul          l, then n   o extr       a
              *                creditcard ve    r  ification will be perform  ed.
     *   @pa      ram purchaseCard   
          *                    describes the update  s mad    e to the pur chaseCar   d
       */
    pu  blic CreditCa    rdReceipt updateRecurri    n   gP  urchas       e(Pe    riod       icPurc haseInfo     periodicPu       rchaseInf o           ,
                 CreditCard c   reditCard, S tring s   torageTokenId    ,   V   er if  ication  Request v  e  r  ificat   i   o  nReq    uest,
               Pu       rchaseCard    R   eques     t   p     urchas   eCard);
  
             /**
            *          Refunds the   cr editca  rd pur   ch  ase with  the spec   ified
       * <code>    purchaseId</code>.
        * 
           * @param purchaseId
     *                           the id of the     p urc       has    e to refund
           * @param purchaseO    rd    erId
        *                           t  he ord         er id previo usly as      signed    to t         he p  urc   hase that is to    be
     *            refund    ed.    This is an ex     tra    c   heck to p   reven  t in    advert    ant
         *              ref          unding of a pu  rchase. Not   null.
                 *     @par    am ref   undOrde  rId   
          *               the me        r    chant assig  n                ed id     to      attached   to this r    efund    r       equest.
     *            Wil      l be generated      i         f null.
                     * @param     amount
     *               the amount in pennie s to refu   nd.       Th  e    curren      cy i     s in the
                 *                         merch         ant's defau  l  t currency.
     * @return the refun  d recei     pt
         */
    pub  lic CreditCar   dRece            i  p  t refund(long purc  ha   s    eId, St    ring p   u     rchaseOrderId  , Str       ing r  efundOrde   rId,
                   long amount   )       ;

    /   **
          * Performs       a pre aut  horiza        tion to                    veri     fy the prov    ided
       * <code>credit     Car   d</code>       of       the           specified <code>amo       unt</co  de>.
        * 
     *      @pa   r      am orde  rId
       *              the merchant as       signed        or  derId to    assoc     iat e wit    h this purchase.
            *               Not null.
     * @param cred          itCard
           *            the cre   dit   car d used       f    or m aking th    e purchas  e.              Not nu   l  l.
      *   @pa    ram amount     
          *                  the a    mount of the      pur chase   in pennies. The cu  rrency       i            s     in t     he
     *                  merchan    t's default   currency.
            * @param veri  ficationReque s  t
     *            indi    cates how t        o v   erif  y the c    reditcard. If null, then no extra
            *              creditcard ve      rification will be performed.
     */
      p  ublic CreditC ardRece ipt preAut  h(St        ring        order       Id,      Cred    itCa  rd       cre      ditCard, long   amount,
                         Ve rificationRequest      v  er  ificati                      onRequest   );

           /**
                * P     erforms a        pre auth   orization   to ver  ify the    provi    de     d
              *     <co      de>s    torageToken   I  d</code> of the              specified <c  od  e>amount</code>.
         * 
     *                       @param orderId
        *            th  e merch      an  t as        signed orderId to  associat     e               with                    this purchase      .    
      *                   Not null      .
             *      @param      st   orageTok      e nId
     *               the stora      geTokenId     use     d for  making the purchase. Not null.
     * @param   amount
     *               t     he amo   unt of         the     purchase   in pennies. The curre            ncy is i      n the
            *                             m erch    ant's  defau    lt cur   r    ency     .
        * @para  m verificat        ionRe    quest
         *                         indi               c    at          es how to verify t he cre           ditca r   d. If null,    th   en no e        xtra
     *                 creditcard   verification will be performed.
        */
          publi              c Credit   CardReceipt preAuth(Str     in  g o    rderId,   String storageToke  nId, long amount,
             Ve     rificati  onRequest veri  ficationReques     t);

      /**
          * Captures   the funds    aft     er a transaction           has b       een pre         auth                o  rize    d.
       * 
     * @ param    p      urchaseId
     *                           the Admeris tra n  sac   ti      onId  that is associated with t     he    pre
        *                      authoriz        ed purchase   .     
                   * @param purchaseOrderId
          *             the ord   erId pre viously as  signed to the preAuth transactio         n
     *                       tha  t is to        be    cap   ture      d.  This is a  n extr     a chec       k t      o p  rovide in
              *            adve   rdent ca   pturing of funds. Not null.
       *   /
           pub  lic Cr   editCa           rdR      eceipt capture(l  ong p          urchaseId, String pu   rchaseO      rderId,            long amount);

          /**
      * Performs a s    ingle cred  it o           n the give     n   <co    de>creditCard</code> of     the
     * s            peci  fied <cod     e>amou   nt</cod      e>.
           * 
     * @para    m or   derI        d  
     *                 the m ercha   nt       a    ssigned orderId t o          associat      e wit   h th     is purchas  e      .
     *                            N              ot nul l.
              * @param creditCard  
        *                               the creditcar d        used    f     or making t he pur          c  hase. Not null.
     * @p   aram amount
     *            the  amount to be credi  ted in pennies.     The   cu             rrency is in    th   e
        *                          merchant's defaul  t cu rrency.
         *     @param verificationRequ   est
     *                                ind  i     cates how  to veri   fy the credi    tc  ard. If   nul  l, then no extra      
             *                          creditca    r  d verification   will b         e perf      orme  d .
     * @ret    urn the   c    redit rec     eipt
         */   
        public CreditCardReceipt     singleCr    edit(String or derI d, Credit             Card c   reditC      ar d, lon    g amo unt,
               V  e  rificationRequest verif  icatio   nR   equest);    

    /**
          * Performs a single credit on the given         <    c ode>storageTokenId<  /code> of   t      he
     * specified        <code> amo  unt</code>.
     * 
     * @param orderId
        *                     the merc  hant a   s         signed orderId to associate with   this   purchase  .
         *            Not n       ull.
     * @param storageTokenId
     *                 th    e tokenId        used for            making the credi t         . Not null.
                  *   @p     a  ra        m      amount
                *                 the amount to   be credite  d in pennies. The curr             en   cy i    s in the
       *                                    merchan      t' s de          faul  t cu          rrency.
              *   @p    a    ram verifi  cationRequest
     *               indicates how to ver    i    fy         the cre       di tcard. If  nul   l,                            th en no extra
        *                                   credit              card    verificati   on will                     be performed.
     * @r et    urn the    credit receipt
     */ 
    public Cred   itCardReceipt singleCredi    t(String ord    er      Id, St      ri   n       g s    tora  geTokenId,   long amou     nt,
              V    erificationReq  uest verifi    cationR     equest)         ;   
  
    /** 
          * P erforms a si  ngle p   urchase on the      give     n <cod  e>creditCard       </co     de>  of the
                               *   spec              ified  <code>amount<  /code>. Combines the preAuth and  capture   int      o one
         * step
     *   
         * @param orderId 
        *                   the           merchant as  signed orderI d to           assoc iate with  th  is purch      ase.
             *              Not null.
                *  @pa       ram c   reditCard
                *                 t     he creditca     rd used for making the purchas e. N     o  t null.        
     * @par   am amount
       *            the     amoun       t of the purch    ase i    n penni     es.  The currency i           s i       n the
           *                        m  erchant's defa ult cur rency                   . 
          * @pa  ram        verif    ica          tionReques   t
      *                          indicates how     to ve    rify the cr   editcar  d     .      If null, then no ex      tra
       *            creditcard    verification will be performed.
     *  @return th  e purchase  receipt
       */
    public  CreditCardR   eceipt singlePu                                   rchas     e(String orderId, Credit       Card    credi    tC   ard, long amount,
                             Veri  ficationRequest     verificat    ionRequ     est );

    /**
              *      Performs     a single       purchase  on the   giv  en <cod   e>c  r  editCard       </code> of the
           * specif              ie     d <code>amount</code>. Combines the   pre     Auth and   ca       pture into    one
                    * step
     * 
        *       @p aram orderId
                    *               the mercha       nt assi   gn        ed o  rderId to a    ssociat      e with   this purchase.
     *                  Not null.
         * @      pa ram creditCard
     *                       the credi   tcar   d use      d f   or ma     ki   ng the purchase. Not null.   
       * @par      am amount 
           *            the amount of th   e purchase in pennies  .   The curre  ncy   is in     the
     *            merchant's defau   lt curren cy.
              * @par     am verificatio       nRequest
     *            indicates how    to       ve   rify the credit c    ar     d  .   If n     ull, then       no   extr   a
     *            creditcard ver    i   ficatio    n    wi   ll be p        e  rf  ormed    .
     *   @param pu    rcha   seCar    d     
          *                        the        purch         ase card details
        *   @re     turn t   he purchase       receipt
     */
    public Credit  CardReceipt singlePurchase(  S            tr    ing o  rd      erId   , Cr       editCard cred  itCard            ,      l  ong amount,
                  VerificationRequest verif      i  cationRequest, Purchase   Ca  r     dRe  quest     purchaseC     ar d);

        /**
     * Perf        orms a sin                gle pu   rch          ase on t he given                        <code>cred   itCard</code> of the
       * specified <code>amou n    t</code     >.     Sto  re cre   dit    c     ard into secure storage with
               *     <code>secur eTokenId</  code>.
         * 
     *  
             * @p           aram orderId
           *                      the m   erchant assigned orderId to associate with  t      his purchase.
            *                        Not null.
                     * @param   c  re           ditCard
          *            the c    reditcard used for making t      he purchase. Not nu  ll.
     *   @   param                     amount
      *                       the amount      of the    pur  chase i          n    p    ennie  s. The curr    ency is in the
        *                m   erchant's default currency.  
     *      @param verification  Reques   t
       *               i    ndicates how to verify th       e cre di  tcard. If nul    l, then no e      xtra
             *                creditcard verification will be   perfor  m     ed.
     * @param    p        urchaseCard
      *            the purchase card details
     * @par am     se   cureTokenId
     *               sec       ure storage   token
     *         @  return        the  purchase receipt
       */
         pu b             lic CreditCardRece   ipt singl        ePurchase(    String  ord    e     rId, C   reditCard cre   ditCard   ,        long  a mount,
              Ver      ifi   cationRequest verificationR equest  , PurchaseCa   rdRequest purcha  seCa    rd,
                      String secureTok   enId);

           /**
      * Perform s a  singl       e     pu     rchas            e on the gi     ven <code>storag  eTokenId</code> of
     *          the specified <code>amoun   t</code>.     Combine     s  the preAuth and captur e  into
     * one step     
        * 
             * @param     orderId
     *                       the mer ch      ant assigne      d orderId to associate wit    h this purch      ase.
        *              Not n    ull.
     * @param st   orage   T            okenId
     *              specifies the stored  cred  it card used for making               the purchase.
     *               No  t null.
       * @param amount
     *            the   amoun         t of the purc hase in pen            nies. The    curr        ency is in the
                     *                merchant's defaul      t currency.
     * @param verificati                 on  Requ  est      
     *              ind    icates     h   ow t  o verify t  h    e creditcard. If null, then no ext  ra
     *                     creditcard ve   rification w   ill    be   perfo rmed.
     * @return the p     urchase receipt
     */
     publ i       c CreditCardReceipt   singlePurc      hase(String ord   erI    d,    String s to    rag  e  TokenId  , long amount,
            VerificationRequest verifica  tionRequest);

    /**
     * Performs    a s         ingle p     urchase on the                  given <    code>s t       orag eTokenId</code> of
     * th      e     specif       ied <code>    am  ount</c    ode>. Combines the preAuth and capture into
     * one step
     * 
     * @param  orderId
           *            the merchant assigned orderId to associate with thi         s purchase.
     *                    Not null.
     * @param storageTokenI     d
           *            specifies the st  ored credit card used for making the purch   ase.    
     *            Not null.
     * @param amount
                *            the amo   unt o  f the purchase in pennie        s. Th      e c   urrency is in th       e
         *            merchant's default curr  ency.  
     * @param veri    f      icationReq   uest
     *                      i ndic   ates how           to verify the creditcard. If null, then no extra
     *                       cred      i  t     card verifi  cation will be perf     ormed.
     * @p  ara m purchaseCard
                    *              the purchase card details
     *      @ret  urn the purchase receipt
      */
    public Cre      d  itCardRe  ceipt singlePurchase(Str       ing orderId, String st   orageTokenI d, long amount,
               VerificationRequest verificationReque   st, PurchaseCardRequest p   urchaseCard);

    /       **
     * This method will fo    rce a purchase transaction w ithout  verifying the
       * credit card.
     * 
     * @par   am orderId
     *                the merchant assi gned orderId to ass   oc    iate with  this   purchase.
     *              Not null.
     * @param creditCard
     *            the creditcard used   fo  r mak      ing the   purcha se. Not null.
       * @param amount
     *            the     amount of the purchase in p   enn   ies. The curre      ncy i   s in the
      *            merchant's    defaul t currenc y.
     * @param ap   provalCode
     * 
      * @par      am verificationR    equest
     *            indicat      es how to veri     fy   the cr   edi     tcard. If n    ull,       then no extra
       *            creditcard verifica  tion will be performe    d.
     * @       re     turn the p   ur     chase receipt
     */
    publ   ic CreditCardReceipt forcePurchase(St       ring orderId,    CreditCard creditCard, l ong amount,
                 String approvalCode, VerificationReq   uest verif   ica  tionRequest);

    /**
     * This method wil   l pr   ovid   e the ability to perform a partia  l void.
     * 
         * @param transacti  onId      
       *            Admeris generated transaction Id of the original tra   nsaction.
     *              @param  transactionOrderId
       *            the merchant  assigned orderId of the      the original transaction.
     *            Not null.
     */
     public CreditCardReceipt reve  rseTransactio    n(long transactio    nId, String transaction       Order     Id);

    /* *
     * This method wil   l only verify the <code>cred       itCard</code>. No purchases    
      * will be made        against the creditcard.
      * 
     * @param     creditCard
     *            the creditcard to verify. Not null.
     * @param verif   icationRequest
     *            in  dicates how t   o   verify the creditcard.    Not null.
     * @return the creditca   rd verification receipt
       */
    pu    blic CreditCar   dReceip     t ver ifyCreditCard(CreditCard creditCard,
            VerificationRequest verificati  onRequest);

    /**
     * Th     is method will only verify the <code>creditCar  d</code>. No       purchases
     * will be made against the creditcard. St     ore credit car     d into secure
      * storag   e with <co     de>secureTokenId</code>  .
         *   
     * @param c   reditCard
     *             the creditcard to verify. Not null.
     * @param verificationRequest
     *            indicates how to verify the    creditcard. Not null.
     * @param secureTokenId
      *            secure storage     token
     * @return the creditcard verification receipt
     */
     public CreditCardR        e  ceipt veri  fyCreditCard(C   reditCard creditCard,
            VerificationRequest verificationRequest,   String se   cureTokenId);

    /**
     * This method will only ve  rify the       stored <code>creditCard</    code>
     * associated with   t he <code> storageTok enid</code>. No purchase will be made
     * against the creditcard.
     * 
               * @param storageTokenid
     *            specifies the stored credit card to be verified. Not null.
     * @param ver        ificationRequest
        *            indicates how to verify the creditcar   d. Not null. Note that
     *              you cannot perform C VV2 verification against a stored credit
     *            card, as CVV2 is never        stored.
        * @return the cr  editcard        verification receipt
     */
    pu   blic CreditCardReceipt verif   yCre  ditCar   d(String storageTokenId,
             VerificationRequest verificationRequest);

         /**
     * Voids the transac     tion having the specifie  d <code>transactionId</code>.
       * 
     * @param transactionId
     *            the id of the transaction to void
     * @   param transa     ctionOrderId
     *              the order id previously assigned to the transa  ction that is to
     *            be voided. This is an extra check to prevent inadver   tant
     *            voiding of a transaction. Not null.
     * @return the void receipt
     */
    public Cr   editCardReceipt voidTransaction(long transactionId, String transactionOrderId);

    /   **
     * Verifies the transaction havin   g the specified <code>transactionId</code>.
     * 
           * @param transactionId
     *            the id of   the transaction to verify
       * @param transactionOr      derId
     *            the       order id previously assign     ed to the transacti on that is to
     *            be voided. This is a    n extra check.
     * @return the verification receipt
     */
    public CreditCardReceipt verifyTransaction(Long transactionId, String transactionOrderId);

    /**
      * Adds a payment profile to secure storage
     *   
     * @param storageTokenId
     *            stora ge token Id to use to identify the stora ge record. If
     *                     null, then one will be auto-generated and returned i  n the
     *            receipt
     * @param paymentProfile
     *            the data to store
     * 
     * @return the result of the addition
     */
    public StorageReceipt addToStorage(String storageTokenId, PaymentProfile paymentProfile);

    /**
     * Permenantly deletes a payment profile from secure storage
     * 
     * @param storageTokenId
     *            storage token Id of the storage record to delete
     * 
     * @return the result of the deletion
     */
    public StorageReceipt deleteFromSt orage(String storageTokenId);

    /**
     * Retrieves a payment profile from secure storage
     * 
     * @param s    torageTokenId
     *            storage token Id of th   e storage record to retrieve
     * 
     * @return the result of the retrieval
     */
         public StorageReceipt queryStorage(String storageTokenId);

    /**
     * Updates an existing payment profile in secure storage
     * 
     * @param storageTo    kenId
     *            storage token Id of the storage record to update
     * @param paymentProfile
     *            the data to store, will overwrite existing data
     * 
     * @return the result of the update
     */
    public StorageReceipt updateStorage(String storageTokenId, PaymentProfile paymentProfile);
}
