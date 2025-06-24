/*
 *      Copy   right (c)      1995,    2013, Orac     le and/or its affiliates.      All    r igh ts reserved.
 * ORACLE PROPR  IETARY/CONFIDENTIAL    . Use  is   subject to license terms.  
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
       *
      *
 *
 *
 *
    *
 *
 *
 *
 */

package java.net;   

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.security.AccessController;
import java.security.Privil egedEx  cepti      onAction;

/**
 * This class r   epresents a          socket for sending and recei   vi    ng         datagram packets.
  *
 * <  p>A dat  agram socket is the sending or     receiving point     for         a p a  cket
 * de   livery   service. Each packet sent or   re  cei       ved on a datagram soc  ket
      * is individually addressed a     nd routed. Multiple        packets   sent from
  *  one mach  i     ne to another may be       routed         differently, and may arrive    in
 * any order.
 *
 *  <p> Where poss   ible, a newly co ns  tructe    d          {@code Datag     ramSocket} has the
 * {@link So  cketOptions#SO_BROADCAST SO    _BROADCAST} socket option   enabled so as   
 *    to     allow the transmiss  ion  of broa     dcast datagrams.   In order to r   ece ive
 * br      o     adcast   packets a Datagra   mSocket s hould b     e   bound to t      he wi    ldcard addr   ess.
 * In some implementation     s, broadcast packets may also          be received    when
    * a Datagram Socket      is boun   d   to a more specific add           ress.
 *       <p>
     * Example      :
 * {@code
 *                     Datag    ramSo      ck  et     s = n   ew Datagra  mSocket      (null);
 *                   s.b  ind(ne   w InetSocket     Address(8888));
 * }
 * Whi  ch is   equivalent to:
 * {@code
 *                            DatagramSo    cke   t s = n    ew D     ata    gramSocket(8888  );
     * }
 * Bo    th cases   will create a Da   tagra    mSocket    able to receive broadca   sts    on
 * UDP port 88    88.
 *
 * @    author  Pavani Di  wanji
          * @see                  java.net.  Dat agra      mPacke   t
 * @see           java.nio .channels.Dat   a  gramChannel
 * @  since JDK1.0
 */      
pub   lic
class Datagr     a         mSock  et im    pl ements    ja        va.   io.Closea      ble {
     /**
     * Various sta    tes of thi s socket.
     */
    private   boolean cr   eated =     false;
           private boolea             n  bo               und = false;
      pr   ivate bool    ean clo sed     =     false;
    private Object  closeLock = new Obje        ct();

       /*
     *        The imp     l    ementation of this Datagr   amSocket.
     */
       Dat  agramSock       etIm    pl impl;
 
      /**
     *    A re we usi  ng an older Dat  agramSocketImpl?
     */
    b    oolean oldIm     p l = fa      l    se;

          /**
         *    S    et when a socket i s       S    T_   CONNECT         ED until         we          are    certain
     * tha      t any packets which might have been recei   ved pr   ior
          * t     o calling connec         t() but not re    ad by the appli          cation
       * have been read. D       uring this time we check the sour ce
     * a   d dress of     all pac  kets rec eiv      ed  to be sure        they are from
       * t   he     co nn  ected des  tinati        on. Other pa    ck   ets       are read     but
     * sile   nt  ly droppe d.
         */       
           priv     ate boolean explicitFilter =      false     ;
    private  i    nt    bytesL eft   ToFilte       r;
    /*
        * Connection state:
     * ST_N  OT_CONNECTED = socket not co  nnec    ted
         * ST_C  ON    NECTED =      socket    connected
      * ST_CONN     ECTED_NO_IMPL = sock   et connec   ted but no   t at impl  lev   el
     */
         stat   ic      f inal int     ST_     NOT_CONNECTED = 0;
    static final int ST_CONNECTED =    1;
           stat     ic final            int ST_CONNECTED_NO           _IMPL      = 2;

      int connectS     tate = ST_NOT_CONNECTE   D;

       /*   
       * C on      nect   ed     address &     port
     */
      I    netAddress connectedAddress =   null;  
    int conne  cted          Port = -1;

                  /**
     * Co   nn    ect    s t      his soc     ket to a remo      te so cket address (IP ad   dress + p    ort n       um     ber).
          * Binds so        ck     et if n ot al ready bound.
                * <    p>
             * @param       address   The r  emote address.
     * @p          aram   port    Th  e remote port
                       * @ throw   s  S  oc   ke        tExc  eption if binding the                  s    ocket fail       s.
     */
      private sync hroni             zed     voi   d co       nnectInternal(Ine   tA     dd        re      ss address,                    in      t p  ort) thro     ws Socke  tExcep     ti  on {
        if (port < 0 || port        > 0xFFFF)           {   
            throw new               Il   legalArgument  E  xceptio  n(  "   connect: " + po   rt);
        }
             if (address ==       null)             {   
                                          throw new IllegalArgu     mentExce  ption("connect     : nu ll ad  dress");
         }
              che  ckAdd  ress (address       , "con         nec   t")   ;    
                      if    (isC  l o      sed())
            return    ;
           Se   cur         i      tyManag   er   se      curity   =   System.g    etS  ecurityManage     r()   ;
              if (security     !  = null) {
             if (address.isMulticas     tA  dd r      ess ()) {
                                     sec    ur     it y.ch     eckMulticas    t(ad   dress);  
                     } els   e {     
                                         security.ch    eckConn             ect(address.getHos  tAddress(), port  )  ;
                              security.c   heckAccept(a  ddress.getHostA    ddres   s  (), port);   
                        }
               }

            if (    !isBound())
          bind(  new             I  netSocketAd dress(0));   

        //     o    ld im     pl   s do not suppo  rt con  nec      t/disco  nnec  t
             i   f (oldImpl |        | (i     mpl inst      a nceof AbstractPla  inDatagramSocketI mpl &   &
                               ((Abs         tra ctPlain     Datag ram       SocketIm  pl        ) im  pl).na   t iveConnec    t Disabl       e         d()))           {   
                 connec      t      State =            ST_CONNECTED_NO_IMPL;
            } else {
                         t    ry {
                    getImpl( ).conne  ct(address, port);
  
                      // socke  t       is now connected by t   he impl
                         conne  ct      St  ate = ST_   C     ONNECT           ED;
                   // Do we          need to filter some packets?
                       int  av  ail = getImpl()  .        dataAvail             able()      ;
                              if      (avail == -1) {
                        throw n    ew SocketException     (     );
                                   }
                   explicitFilter = avail > 0;
                      i       f (e       xplicitFilter) {
                         bytesL    eftTo Fil     ter   = g   etReceiveBufferSize(    );
                                     } 
                 } catch (SocketExcepti    on se)    {

                                // connectio    n    wi     ll be emulated by Dat  agramSocket
                           connectState = ST_C  ONN    ECTED_NO_IMPL;
                                         }
        }

                     con     nectedAdd   ress = add      ress;
               co  nnecte dPor       t         = port;
       } 


                    /**
          *  Constru  c   ts a datagram  so cket      a  n   d binds it to any available p     ort      
     *       on      the loca      l host m   achine.  The socket will   be        bound to the
     * {@li              nk Ine        tAddress       #isAnyLocal  Ad   d        ress wildc  a   rd} address,
     * an IP         address chosen by t      h         e ker  nel      .
     *     
          * <p>I      f        there is a securi    ty ma   n    age           r,
       *     its {@code          checkLi sten} me     thod is first called
     * wi         th     0 as i ts arg     um e        n  t t  o ensu     re    the o   per     ation is all    owed.
     * Thi  s could re  sult i   n a   Sec         urityException.
     *   
     * @exc epti  on  SocketExcepti     on  if the   soc  ke   t c      ould not   b    e o    pen    ed,
          *                                    or    the socket          could n    ot bind     t      o the s   pecified loc    al  por  t.
     * @exception  Secu     rityEx    cept   ion          if    a        se   cur  ity manage    r    exists and its
     *             {        @code checkLi   st  en} method doesn'      t allow th   e operation.
     *
               * @s ee Se  cur   ityManager#  checkListen
                 */
          public DatagramSocket()   throws     SocketExcept   ion {
        this(    new InetSocketAddress(0));
          }

     /**
     * Cr      eates  an un  b  ound data       gr   am sock   et with the specifie    d
              * DatagramSock   etIm        p l.
     *
      * @para  m impl an       instance      of a <B>   Data   gramSocketImpl</B> 
     *                      the s ubclass wishes to us        e on         the D     atagramSo     cket.
     *    @si nc e     1.4
       */
           protecte d D  a      t     agramSocket(Datagr       am  S  ocke tImpl impl) { 
        if   (impl ==      null)   
                             throw n     ew N  ullPointerException(   );
               th is.impl =    imp     l      ;
               che     ck   OldIm   pl    ();
    }      

         /**
       * Cre   ates a datagra   m socket, bound to                 the spe cifie     d l  ocal
     *         so  cket address.
              * <p>
            * If, if the  address is {@code null}, cre   ates an        unbo    und sock      et.           
         *
     *  <    p>If t        here   is      a security manage  r    ,
        * its {     @c         ode   checkListen} method    is f    irst cal  led
           *  with       the p       or t   f     rom th              e socket address
     * as   its argume   nt         to ensure the   oper  ation is    all   owed.
          * This could result in a Sec  urityException.
     *   
               * @param bi ndaddr       l   ocal       socket                   address to              b  ind     ,     or {@code         null}
     *                                  f    or an unbound   so    cket.
          *
     *      @excep    tion  Soc     k      et    Exception  i   f              t   he s    ocket c   ould not   be    op            e    ned,     
        *                         or             th      e socket could not bind     t   o the sp   eci   f          ie       d l  ocal      p    ort    .
            * @excepti    on  S   ecurityE xception  if a security            manager      exists         an   d            its
     *                 {@  code c hec        kLis ten} metho   d d       oesn   't  allow   the                  operati  on.
     *
     * @    s  ee SecurityM        a nager#checkListen
     * @since   1.4       
        */
    p      ubli    c Datagr    am       Sock  et(Soc   ket   Addre    ss b            indaddr) throws SocketExcepti          on {
               // crea te a data     gram socket.  
           crea   teImpl();
        if (bindadd r != nul  l   ) {
                        try {
                  b          ind( bindaddr) ;
                  }    final     ly {
                                    if (!isBound())
                    close();
              }
           }  
    }

    /*      *
          * Constr   uct  s a   datagram socket an  d    bind   s i  t to the specified p   o       rt
                 * on the loca   l hos      t m                achine.  The soc   ket will b  e bound to t       he
     * {@      link Ine  tAddress   #isAnyLocalAddres  s      wildc    ar   d   }     address,
     * a    n IP addre ss             chosen      b y th    e      kernel .
     *
     * <p>I  f there           is a securit         y m anager  ,
        * its {    @code    checkL        isten} m               ethod is first     cal    led
        *           wit       h the {@code port}  argument
        * as i     ts argu     ment to              ensu      re the opera   tio n     is allo we                d.
                  * Th is could          result in a SecurityExce    ption.
              *
     * @param            port port to use   .
     *     @exception     S     ocketExceptio  n  if t  he s                ocket cou   ld not be opene  d,             
     *                       o  r  t   he socket could not b   ind t o the specified l     ocal  port.
       *      @    e     xception            Securit   yException   if a security manage      r                exists a  nd        its                 
       *              {@cod    e    checkListen} method doesn't      allow the operatio                  n.
     *
       * @ s                 ee Se         curityManager#ch      eckList  en
         */
    p      ublic   Datagra     mSocket   (in    t port) throws Soc  ketExc          eptio                n {
                      this(    port, null);
    }

      /**
        * Creates    a datag    ram socket, bound to the sp ecified          loca            l  
            * a  ddress.  Th  e local port must     be between 0 and 65535 inc   lusive.     
                       * If t          he IP   address        is 0.0    .0.0,         the s      o           cke t will b          e b     ound to the
     * {@lin      k I        netAddress#isA  nyLocal          Ad dress   wildcard} address,
     * an  IP addr    ess chosen by t   he kernel.
     *
         * <p>If th      ere is a  security manager,
      * its {@cod    e c       heckList    en} meth     od  is    fi      rst    c  alled
           * wit   h the  {@code por      t} argu         ment
     *               as its a       rgument                    to ensur  e the   operation is allowed.
     *          This c ould      re    sult in a Securi  tyExc   e  ptio  n    .
     *
         * @param port local         port         to              u se
     *        @pa   ram laddr local add  ress to bind
     *
     *     @exce ption  Soc   k     etExceptio n  if t    he socket c     ould      not be                 opened,
          *                   or the socket  coul        d   n      ot       bi  nd to the specif      ied l          ocal port.
              * @exception  Securi tyE  xception  if a secu    rity manager exists an   d its
     *                       {@cod    e c  heckList  en} me      thod d oesn't allow the       o    per   ation.
                          *
     * @see Securi   t    yManage   r#      chec     kL         isten
     * @    since   JDK1      .1
     *   /
     pub    lic         Dat    agr     am         Socket(int     port,   InetAddre            ss laddr) thro ws SocketExc ep     tion {
           t h      is(new InetSock    etAddress(laddr ,            port)  )  ;
    }   
    
    privat     e  void checkOldIm           pl(    ) {
        if (i   mpl == null)
            retur  n;
        //    DatagramSocke    tImpl.p ee   kd          at   a() is a protected m  e    tho  d, ther     efore we need to us            e
            // getDeclaredMethod,     therefore we need permission to access    the me    mber
                              try {
               Ac    cessC ontro       ller.d  o   Privileged(
                                  ne   w Pri    v  ilegedExcepti       onA         ction<Void>() {
                          pu   blic Vo    id run() throw  s NoSuchMet   hodExce ption {
                                  Class<?>[]  c     l       = new Class<?>[1       ];
                                                 cl[0] = Da  tagramPa            cket.c            lass;       
                           imp l   .getClas  s().getDeclare                 dMeth o    d            ("pee         kData", cl);
                                     return null;
                               }
                                  });
        } catch      (  java.security.Privi   l    egedActio     n       Exception e) {
                           oldImpl = true;
                 }
    }

    stati     c C   lass         <?> im  plClass = null;

     void cr  eateImpl() throws SocketException {
                         if        (im     pl == null) {
                     if    (f actory != null) {
                    impl =     factory.cre    ateDat a               gramSock     etImp           l();
                     checkOldImpl();
                 } e    lse {
                  boolean i sM ultica           s     t = (t   his in  stanceof Multicast       So    ck  et) ?   true : false;
                    imp        l    =    De  faultData     gra       mSocketImp     lFacto      ry.  cre     ateDatagr  amSocketImpl(isMulti    cast                  );

                                       c    heckOldIm    pl();
                       }
        }
        // creat      e  s a ud   p socke    t
         impl.create();
                  i  m        pl.setDatagramSocke       t(this);
               creat                ed  = true;    
      }

    /    **
            * Get the {@code Datag ramSocket   Impl} attached t      o    thi s    socket,
              * creating it    if neces    sary.
     *
     *    @re     t   urn          the {@code Datagram  SocketImpl} a      ttache     d to that
     *                Da         tagram  Sock et
         *   @throws SocketException if  creatio  n  fa ils.    
       * @since 1.4
     */
    Data gramSoc                        ketImpl getImpl( ) throws Socket  Exception    {
           if (!c       reated   )
               createImpl(      ); 
                    return i          mpl;
         }

    /**
           * Binds th i  s Data gramSocket to a specif  ic ad  dress and port.
           * <p>    
          * I    f the address is {@code        null}      , t     hen the sy   stem w      ill   pick up
     * an      ep      hemeral port        and a    valid loca   l      add  re  ss to   bind the socket.
     *<  p   >
     * @param   addr  The ad  dress a   nd port to bind  to.
     *   @throws  Soc  k     e        tException       if any       error happ   ens du  ring the bind, or if the    
     *            socket  is   alre     ady          bound.
        * @throws      S   ecurit     yExcep     tion  i   f a security manager ex  ist  s a nd its  
              *                 {@code  check  L iste  n } method does       n'    t     allo     w     the ope    ra   tion.
     * @t        hrows IllegalArgument    Exc eption if a     ddr      is a SocketAddress s      ub    cl   ass    
     *            not suppor        ted   by t    hi   s socket.
     * @s   in c e   1    .   4
                  */
      public    synchroni   zed voi  d bind(Socke tAddre    s s addr      )                throws Socket Exception      {      
          i   f (isClosed       ()) 
                    thr     ow new SocketEx              ce   ption("Socket      is c   losed");     
                    if (is  Bound(         ))
                            throw new Socke  tExcep   tion("alrea     dy bound");
          if     (ad  dr == null)
                           addr = new InetS ock etAddress(0);
                              if (!(addr instanceof Ine    tSocketAddress))
               throw ne    w IllegalArgumentExcep       tion("U    nsu   p    ported   address t     ype!");
        InetSo  cke  t      Address epo              int            = (   InetS  ocketAddre  s s) addr;
          if (  epoint.is               Unresol  ved())
               throw new SocketException("Unresolv  ed ad       dres   s");
                          InetAddress iaddr = epoint.ge   tAd   dress();
               int po  rt = epoin   t.getPort();         
        c              h     eckAd   dr  ess(i   addr, "bind");
           SecurityM   anager sec = System.g    etSe     cu         rityManager();
             if (sec != n  ull) {
                       sec.checkListen(port);
         }
         try {
            get     Impl().        bind(                port, iaddr);
        } ca    tch        (SocketEx   c eptio    n e) {
               getImpl().clos e(); 
                  t  hrow    e;     
        }
            bound = true;
         }

     void ch eckAddress (InetAdd   ress addr, String         op) {
                    i  f (    addr      == null)   {
            re  turn   ;
           }
                 if (!(    addr instanceof Inet4 Address || addr instanceof Inet6Addr    ess)) {
                  t  hrow new   IllegalArgumentExce         ption(op + ": invalid    ad   dress type");    
                      }  
    }

        /    **
     * Conn  ects the socket to a re    mo   te ad  dr  ess     for this sock        et. When a
     * so cket   is c        onnected t   o a remote ad  dress    , packets may              only be
                 * se  nt to     or rec eiv         ed fr om   tha      t a       ddress. By de fa  ult a            datagram
     * socket     i   s no    t connec           te d   .     
     *
      * <p>If        the r     emote destination to which the so   cket is connected do    es not
     *     exist, or is otherwise unreacha  bl         e, and if     an IC MP des           tination     unrea  chable
            * packet      has bee n       receiv     ed fo   r that address, then a subsequ   ent    call to
         * send or receive    may thr ow a    Por   tUnr  eachableExceptio      n. Note       , th          ere is no
     * guaran    tee th      a   t the exception     wi  ll  be thrown.
           *
            * <p> I     f           a security ma nager has   been installed then       it   is invoked to check
     *   acces     s   to the remote address. Specifically, if the gi ven {@code addre          ss}  
     * is    a { @     link Ine    tAddress#is    MulticastAdd   ress    multicast ad dress},      
        * t    he security       manager's {   @link
     * ja   v  a.lang. SecurityManager #ch eckMulticast(    InetAddres   s  )
     *                 check        M      ulticast} method is invoked with     t   he  given  {@c  ode  a ddress}        .
     * Ot       her   wise, th e      security ma          nager's {       @l  ink
     * java.lang.SecurityMa   nage   r#   ch    eck   Connect(String,int) checkC   onnec     t}
           *    and {@link java.lang.SecurityManager#checkAccept  check    Accept} m     ethods
           * are      in voked  , with         the g      iven    {@code    ad       dress} and {@co de     port },   to
     * ve ri       fy  that data    gram    s are pe rmi   tted to be sent an  d recei ved
         * respectively.
      * 
     *   <p> W hen a  s   oc    ket is connect     ed   ,    {@link #receive  receiv  e}   and
              *   {@link #send    send}   <b      >wi ll n                         ot perfo  rm any secu    ri    ty check s</          b>  
     *     on         incoming       and ou  tgoin                      g pack ets, other     than matching             the packet's        
     * and the   socket's address   and   port. O     n a se nd opera tio    n, if th   e
                * packet's add   r   ess         is set a nd     the packet's     address and t    he  socket's
     * ad    dress do not match, an {@  c ode Il  lega          lA  rgumen  t    Ex              cept    ion} w  ill       be
              * th    rown. A socket conn   ected to a multicast add  ress   m ay only    be use  d
     *    to se  nd pac         kets.
         *
         *     @par am add      res      s the remote addres  s for th  e socket
         *
     * @param por          t the r     emote port for the     socke        t.     
     *
        * @thr       ows Ill ega    lAr gumentExce   p         tion
      *         if    the addr   ess is null, or the por   t is out of range    .
     *
      *         @thr ows Sec     urityExc      e   ption
     *         if     a     security manager h     as bee          n i  nstalled   an     d it does
     *                                not     permit   access to the gi  ve  n remote address
        *
        * @see          #discon       nect
     */  
       publi      c void connect(InetAddress add  ress,   int po    rt) {
        try {
                                             conn     ectInternal(address, port);
        } catch (Socket   Excepti      on se    ) {
                     throw new Erro        r("connect  failed ",   se)   ;
         }
    }
    
    /                      **
     * Conn         ects this socket to a r  emote socket   address ( IP    addre ss    + port number).
     *
     * <p   > If give n an {@link InetSocket Addres      s Ine   tSocketAddre  ss}, th             is meth    o d
       * b      e   ha       v     es a     s    if invoki   ng {@     l   in k #connec  t(InetAdd           ress,int  )       conne   ct(InetA      ddress,  int    )}
     * with th        e the giv  en     socket addresses    IP    addr            ess and port number.
      *
          *              @             p   ara  m        addr             T            he remote add   ress.
                 *
               *            @thro   ws  Socke     tE    x               ception
      *          if t    he connect fail             s
     *
     *              @throw   s IllegalArg    um          entE    xce   pt    ion
     *               if {@c  ode addr} is {@code     null}, or {@cod e add r} is a      SocketAdd      ress
        *         subclass      no              t supported by this s ocket
     *
     * @throws Secu    rityExc  eption   
        *                    if             a security manag    er    has been   ins  talled a  nd      i  t   does    
                  *         n   o     t permit       acc  ess    to  the given re   mote          address
         *
        * @since       1  .4
     */
      p      ubli    c void connect(Sock  e       tAddres     s ad      dr) throws S  ock  etExc        e   pt    ion {
               if      (addr == nul    l)
            t        hrow new Il    le     galArgumentException("A      ddr  ess   can't be n   ull"); 
          if (!(addr   in  stanceo f In     e          tSocke   tAddr    e     ss))
            throw new IllegalArgu   m              ent    Excep   tion    ("Unsuppor    ted addr   es        s typ     e");
        InetSocketAddress epoint   = (  InetSocketAd                    dress ) ad dr         ;
                if     (epo   int.   isUnr        eso   lved())
                  throw new     S  ocket  Exce      p   tio    n("Unresol ved          addre  ss");
        connectInternal(epoint.g     et    Addre  ss(), ep    oint.getPo     rt( ))    ;
    }

        /**
       * Disconnects the socket. If the socket         is clo             sed            or   no  t     conn   ected            ,
     * the   n        this method has no e   f  fe  ct.
     *
             * @see   #co               nnect
       */
    pub    li  c void dis      c         o   nnect(      )   {
         synchron    ized (this) {
                    if (isClosed      (      ))
                                  return;
                  if (c     o    nnectState == ST_CONNECTED) {
                      impl.dis     co nn  e   ct (        )        ;
                  }
              connectedAd  dress = null;
              connec tedPort = -1;    
                 connec  t    State = ST   _NOT_C  O     NN ECTED;  
            expl  icit   F   ilter = false;
          }
            }
   
    /**
          * Retu  rns                   the    bin       ding state o     f t   he    sock   et.
     *        <        p              >
     * If the s      ocket was bound       prior to bei  ng {@link   #clo      se    clo    sed},
     * then t     his    method will      cont     inue to return {@cod     e true}
        * afte              r the        sock      et is closed.
     *
     * @re      tu    rn true if the soc   k   et succes    sfu   lly bound    to  an    addre    ss
            * @since 1.  4 
            */
    pub    lic b ool  e     an      is   Bound()     {
              ret   urn bound;
           }

      /**
     * Re        turn          s th   e connection state of th        e soc   k    et.
       * <p>
     * If the socke    t wa        s conne   c       te   d pri  or         to being     {   @lin     k #c   l   ose closed}       ,
     * then this meth  od will continue to return     {    @code tru    e}
         * after t   h   e         socket i   s clos  ed.
            *
     *       @return   tr  ue      if    th   e    s o  cket su  ccessfully connected t     o a   se     rver
            * @ sin    ce 1.    4
     */
        pub        lic boolean isConne ct    ed() {
                           r      eturn   connectState != ST_NOT_CONNEC    TED;
      }

        /**
          * Retur  ns the      address   to    wh            ic h this            socket is connect   ed. R      eturns
          * {@co  de  null           } if the socket is no     t  connected.
         * <p>
     *   If the socket   was   connected             pr                 i      or to being    {@link #close clo  sed},
            * then    this me       th         od will continu   e                     to return th           e  con  nected ad    dr     ess
        * after t        he socket is closed.
      *
       * @retu     rn the       address to wh ic h this socket is connected    .
                   */
               p     ub  lic InetAddre  ss getIn      etAddres      s() {       
           re  tur  n co        nnect     edA  ddress;
    }

      /**
                *            Retu rns     the port               nu       mbe r to which this socket i     s connected.
            * Retur   n s {@co     de -1} i  f the socket  is not     connec  ted .
     * <p>
           * If          th   e s    ocket            w  as c    onnected     p         rior to      bei                 n     g                          {@link #close    cl osed},
                      * then this method will c   onti      nu             e to r     eturn the c  o     nnect  ed port number
            *       a     fter   the socket is closed.
     *
                   * @              retur    n     t           he port number t   o     which this soc   ket   is   connected.
         */
      publ  ic     i      n    t get     Port            () {   
                    return     connect    ed       Port;
        }

    /**       
          * Re       tu     rns th                      e ad    dress   of th e       endpoin   t t             his socke   t                       is connected to,     or    
       *    {@code          null   }    if    it     is unconnect ed.
     *   <p    >
     * If    th  e socket was connected    pr i   or to  bein    g {@lin k #cl     ose    cl    osed},
           * then t  his   method   wi       ll      continu   e    to retur n the                     connect    ed address
         * after             the socket is    closed.
     *
          * @return  a {@    code    S   ocketAddress} repre    se  nting the remot e
     *                                endpoi  nt o   f this s   ocket     , or {@co  de nu         ll}      if it is 
     *                 not con           nec  ted            y et.
      *                             @           s    ee #getInetA         dd         ress()
     * @   see    #getPor   t()
      * @            s      ee #connec  t(Sock   etAddres  s)
          *    @s          ince 1.4     
            *   /
    p   ublic Soc ke    tA          ddre             ss            g  etRe   mot     eS ocketAddress()     {
                       i      f (!i  sConn  ected())
                                    re      tur   n null; 
          return new I   netSocke           t    Ad dr   ess   (get    InetA ddress       (), g       et    Port   ());
    }
   
    /*  *
     * Returns      the     add         ress of    the endpoint   this so               c   k    e t    i    s    bound t o.  
     *
            * @r    eturn a {@     code S        oc  ketA  ddre ss} r  e prese  nting t   he            l             ocal e     ndp oint of                 this
            *             socket, or {   @   c    ode null}     i     f it i     s                  clo   s    ed or n ot    bo  u  nd yet.
     * @see #g       etLocalAddres   s(         )
                   * @see #getLocal       Port()
             * @se    e #bin d(Socket     Addr  e     ss)   
     *           @sinc e 1.4
           */

    pub       lic       S    ocketAd    d        ress getLocalSocketAdd        ress() {
                if (isClosed ())  
                      re turn null   ;
                         if (!isB                         ound())
             return    nul    l;    
                               retu            rn  new   Ine    tS   o   cketAddress(getLo  calA          ddre       s    s     (),         get  Loc     alPor             t());
    }

                /**
        * Sends                a dat agram pac     ket     f   r  om this socket     .         The
        * {         @c    ode Datagr  am           Packet}          inc ludes informa tion i    n       dicating t he
     * data   to be sent, its length, the IP             add   ress of         t he remote host,
         *    and the por     t   numb   er      on the      remo  te      host.
           *
       *    <p>If there is a sec   urity         ma         nage r, and   t he so                  cket i s    not cu                 rrently
         * connected to    a r e  m          ote   address, this me       thod    first       performs some
       * sec   urity chec       k         s.  First, if {@cod e                   p .getAddr     ess().isMulti    c     astAdd        re  ss ()}
             * is t     rue,    this     me thod ca ll       s   the   
         * security m     anager's       {@co               de   c hec   kMulticast} metho   d
     * wi   th {   @code p.getAddr                ess()}     as it       s  argum      ent      .
     *  If    the       eva      lu    at  ion      of th      a   t         e   xpress    io    n i   s false,
               * th  is meth   od     i     nste        ad calls  the secu  rity manag        er's
     * {@code checkCo     nnect} m   ethod with a       r gu m   e  nts 
         * {@cod     e   p.g     et  Address ()    .getH  os  tAddre     ss()}       an      d
                   * {@code p. ge   t     P  o  r  t()}. E   ach   call to a         s  ecu     r    ity manage     r method  
         * could res        ult       in     a       Se     curity  Exce   ption       if th       e operation is      not allow   ed.
                *
     * @p  aram         p   the {@c     o               de D    atagra       mPacket} to     be   s          en  t.
     *    
     *       @exception  IOE xception  i          f a     n   I/O error      o  ccurs.
                         * @exception  SecurityExcep   tion  if a   s     e          curi    ty ma  nager   exi  sts an     d     i    ts
             *                {@code ch  eckM  ul  ticast}        or {@code check   Connec  t }
     *                           meth              od doe   sn' t allow               the se  n  d.
     * @exception              Port  U n      reachableEx    c     ept    ion m        a            y be thro   wn if   the    soc     ket is connected
             *             to a currently u  n   r    ea      chable dest inat  ion          .     Note    , ther  e  is no
     *                                                 guarantee that   the excepti     on   will be thrown   .  
        * @   ex    ce p  t  i     on  java  .       nio.channel      s.IllegalB lockin   g    Mo  deEx       ception
          *               if this socket has an  as    s        oci       ated chan                          nel      ,
      *                  a          nd     t he channel is in            n    on -b   lo    cking mode.
              * @    excep   tio  n           IllegalArg ument  Exception if the so           cket is  conn   ected,
     *                                    and con  nected a     ddress and   packe   t a ddress    differ.
                           *
     * @see            j      ava.net.Da      tagramPacket
     * @see        Security      M      a     n        ag  er#ch     e    c  kMulticast(I netAddress)
       * @         see              Security  Ma     nage    r#c        h             e      c kConn   ect
     *      @ re  vised 1.4
              *     @s pec JS  R-     5                1
         */
           pub               lic       void    send(DatagramPa     c     ket      p) throws IOEx  cep    t      i     on       {
             InetAd     dress     pa                        cketAddres          s = null   ;
         sy    nchronize                d (p) {
             if (isCl o    sed(   ))
                          throw       new So  ck    etExcep         tio     n("Socket is          closed" );
            checkAddre   ss     (p .getAddre       ss(), "se           nd");
                                 if (c   onnectState == ST_NOT_CONNECTED) {
                  // c    heck t        he addr   es   s is    ok wiht         the secu   r ity manager on e       very send.
                               SecurityManager     se        curity = Syst      em. ge          tSe              curityMa      nag  er();

                                     //  T       h   e rea           son you   wan                      t to syn   chr          onize on  d          ata    g  ram packet
                             //  is becau     s       e y  ou    d   o         n't wa   n  t an applet to         change th    e add  ress
                                    // w          h il     e yo     u are t     rying to send t  he pac  ket       for exa         mple 
                      // aft  er the security check but bef  ore    the     send.
                                                         if (sec       urit  y != null     ) {
                         if ( p.         getAdd    res         s(     ).i       sMult icastA      dd   ress())      {
                                                     se  curity.che      ckMulti cast(p.getAddress())   ;   
                                                 } else      {
                                                                   s    e curi      ty.checkC         onnect(p   .ge         tAddr ess().getHo   stA           ddres  s(),
                                                           p.g    etPo  r          t   ()   );
                                                           }     
                                  }
                            } el      se {       
                 /    / we're con nected
                               packetAddress = p   .getAddres       s();
                    if (pa   ck     etA      ddress == nu             ll) {
                                                       p.setAddress   (conne   ctedA   dd  ress     );
                                          p.setPort(   conn            ecte  dPor   t);
                       }     el     se if ((!pa    c   ket      Address.equal  s(connectedAddress  )) ||
                                               p.g     et        Por         t() !=     connecte     dPort) {
                         thro   w ne    w I  ll e   g    alArgumen     tException("c      o  nnecte   d addre  ss     " +
                                                                                        "  and packet address " +
                                                                                                                " dif  f  er"     );
                      }
               }
                                 //       Che  c k whether the sock  et is bound
                           if (!isBou         nd(  ))
                               bi  n       d(new InetSocke               tA         dd ress(0)  );
                       // c  all th   e    met      hod       to send   
                 getImpl  ().   send(p)  ;
        }
    }   

             /          **
     * Receive   s a da     t agram packet fr  om thi    s      socket. When     this method       
     * retur n    s, t        h       e {@         c ode Data         gr amPacket}'s buffe          r        is filled w        ith
               *   th      e data    receiv  e              d.   The datagram packet als  o co     ntains the s ender'       s
           * I   P address , and the port numbe     r  on the se  nder's mach   ine.      
               * <p> 
     *      Th i         s  met hod blocks unt il a datagram               i       s rece ived.          T     he
     *           {@c       ode leng    t         h}    field o  f the datag         ram packet o        b     je      ct conta      ins
                 * the length of      the r eceived mess    ag   e. If    th  e message is                  longer tha      n
        *    the     p    acket            's le  n  gt h, th   e message is truncated.
     * <p>
        * If there is      a security m an     ag        er,    a pa    cket cannot  b     e   rece  iv   e        d   if  the
      * security        man  ager's {@code chec   kA ccept}      me        t      ho  d
        * does no t allow it  .
     *
     * @par   am         p   th e {@code DatagramPac      ket}                                 into whic h to place
      *                           the inc        om           ing data.
      * @ex     ce  ption  IOE      x  ception         if an I/O e     rror occurs.
           *    @exception  SocketTi   meoutExce   pt           ion  i f se    tSoTimeou       t wa       s previous  ly called
     *                    and t    he timeout    has   expired.
       * @exception  Po   rtUnr  eachableException          may be thr    own   if the socket      is  conne cted
               *              to a curre n        tly u       nreac hable d      estinati        on.  N   ot    e, there is   n o   gu               aran  tee that the
     *             except io    n will be th  row   n.
     *  @exc ep  tion  java  .ni    o.channels        .Ill    egal Bl   ockingModeExce   ption
     *                if  th       is soc      ke  t   has          an associated ch       annel,     
             *                                  and t     he chann        el     i s in non-block   in g mode.
            * @   see        ja         va.net     .DatagramPac    ket
       * @see                     jav a.net.DatagramSocke     t
     * @revised         1.4
     * @s pec J S       R-51
       */
     publi c s     yn             chronized void     receive(DatagramP         acket      p) throws IOException {
               syn       chroniz    ed       (p)     {
                                  i   f     (!isB      ound())
                              bind(new I          net   Socket          A  ddress(0   ));
                               if (c   o  n      n      ec     tState == ST     _         NOT_CONNECTE D  )  {
                            // c  heck the addre  ss    i    s ok w    ith the security manager be   fore every  re         c  v .
                                          SecurityMana  ger sec       urity = System.getSe  curi  ty  M   an ag  e       r();               
                i    f   (s   ecurity           != null) {
                         while(     true) {
                                     S      trin g p  eekA       d = null;
                                    i      nt pe      ekPort =    0;
                                       // peek at the packet to see wh    o it i     s f        r       o   m.
                                  if (!ol   dImp      l ) {
                                   // We c    an u         se  t   he  ne    w peekData() A  PI     
                                                            Da      tagramP       ac ket peekPacket = new      Datagram        Packet(new                b yte    [1]       ,     1)   ;
                                   peekPort       = get Impl().peekD  a   t  a(peekPacke t   )  ;
                                   peekAd = pe     ekPacket.getAd     dress().get HostAdd  ress(  );
                                      } else          {     
                                            InetA           ddres   s adr =    new In   etAddress();    
                                              pe        ekPo        rt         = getImp  l(     ).pee     k(adr);
                                               peekAd =      adr.ge tHostAddress();
                                    }
                                                         try       {
                                        security.check   A ccept(peekAd,       peekPort);
                                        //  security check         succeede  d   - so now break
                                               // and recv the  packet.
                                break;  
                                     } catch (SecurityEx c eption              se) {
                                           /                     / Th   row  a way  the   offendi        ng packet by       consumi ng
                               /  / it in     a tm   p bu   f    fer.
                                  Datag ramPac       ket t   mp      = new Da        tagramPacket     (new     b    yte[1] , 1)   ;
                                                   getImpl().receive      (tmp )        ;

                                          // s   ilently d  isca   rd the of  fen   d     i        ng packe     t
                                                          //     and continue: u   nknown/m  alicious
                                          // entities  on     nets sh              ould               not            make
                                                     // runtime thro                    w sec      urity exception and
                                        // disrupt th  e a  pple   t by sen      ding random
                                      /    /    datagram packets.
                              conti nu  e;
                                              }
                                      } // end of wh ile
                      }
                                       }
            DatagramPacke          t tmp = null  ;
            if     ((c       onnectS  tate  =   =       ST_C ONNECTED_NO_IM   PL) || exp     l    icitFi   lter) {
                      // We have to do the       filtering the old f ashioned way sin           ce
                   // t he na     tive      impl does n't support connect  or       th               e c     o    nnect
                          //     v ia the    impl    fail e  d, or .. "e       xpl  icitFilter" may   be  set       when
                     // a soc         ket is con     nected   via the impl, for a p               eriod of ti             m     e
                                      // w     hen p     ackets from     othe  r        sou   rces m    ig  ht be     queu     ed on      socket.
                               boolean sto            p = fals    e;
                       while      (!stop)          {
                                  I        netAddre   ss pee                 kAd        dre    ss = null;
                                       int peekPort    =   -1      ;
                                    // peek at the pa  cket      to see who it   is from   .
                                      if (    !old I  m   pl   ) {
                                // We c  a    n   us   e the new       peekData   (  ) AP   I
                              Datag             ramPacket pee   kP acket   = new Dat   a gramPacket(new    byte    [1],       1);
                                              pe   ek   Port =      getImpl(    ).peekData(peekPacket);
                                 peekAddress =         pe      ekPacket.getAdd    ress    ();
                              } else {     
                                         // t     his api only work  s for IPv4
                                            p   eekAddress =                 n  ew  InetAddress();
                                   peekPor         t = getIm p  l    ().peek(   peek     Address  );
                      }
                    if    ((!connec      tedAddres   s.equals(peekA  ddress)) ||
                                  (connectedPort !=    peekPor t)) {
                                    //   thro   w th e   pack   et away and sil  e  ntly       conti   nue
                                 t   mp    = ne     w DatagramP  a            cket(
                                                               new byte[1024], 1024);
                          getImpl().rec  eive(tmp);
                                 if    (explici  tF    i   lter) {
                                     if (checkFiltering(tmp)) {
                                                stop = true;
                                        }
                                                                  }
                           } else        {
                                          stop = tru  e;
                                }
                  }
            }
             //     If the securi ty c  heck su  cceed  s, or        the datagram is
                    // co   nnected t    hen rece    ive     th  e p   a c           ket
                           getImpl           ().  receiv  e  (p);
                 if (explicitFilter && tmp ==    nul       l) {
                   //           packet             wa        s not fi       ltered,  acc o  unt for it here
                  che    c      kFiltering(p);
                 }
        }
         }

    priv     ate boolean ch     eckFiltering(D     atagramP         acket p) th    rows Soc           ketExce  pti   on {     
        b  y tes  LeftToFilter -=   p.get    Lengt          h();
              i          f        (bytes   LeftToFilter <= 0 |       | getImpl    ().dataAv   ai  labl    e() <= 0)            {
               explicitFilter =    false;
                             return true;
        }
            return false  ;
    }

    /**
     * Gets the local         address to whi   ch the soc     ket is  boun   d.
     *
     * <p>If t    here is        a secur    ity    manager, i   ts
     * {  @code checkCo    nnect} met    hod is firs     t c    alled
          *    with the    host add  r es   s   and      {@co  de -     1}
        * as    its arguments   to se  e    if the operation is allowed.
         *
     * @s         e   e SecurityManager#che            ckC onnect  
        * @return     the loca l add                  ress    t   o    which the     so  c ket is bo     und         ,
     *             {@code n   u        ll} if the socket is c  losed, or
     *          an {@code Inet  Addr    e ss} represen  ting
      *             {@link I n  etAd       dress#isAnyLoc       alAddress w          ildcard}
     *              address i  f  eithe   r        the         socket   is  not         boun    d, or
     *          the         security manager {@code c heckConnect}      
     *           method does not   allow the    operation
        *  @s          ince       1.1
          */
    p ubli c In   et Address      getLocalAd        d     ress   () {
        if   (isClosed() )
               return nul      l;
           In     etAddress in =    nul  l;
        try {
                        in  = (InetAddress) getIm  pl(   ).getO   pt      ion(SocketOptions.SO_BINDADD R);
               if (   in.isAn   y    Local   Add  ress()) {
                    in = InetAddress.anyLoc       alAddress();
                }
            SecurityManager s = Syst     em .getSecurit yManage   r();    
                  if (s != n   ull) {
                      s.checkConnect  (in.getHostA    ddress(  )   , -1);
               } 
                 }  catch (Exception e) {
                    in = InetAddres  s.anyLoc   alAddress(); // "0. 0.0. 0"
              }
           retur  n i   n;
          }  

        /**
     *      R        eturns the  p         ort number  on       the lo   cal host to which   this socket
     * is boun  d.
     *
           * @return  t     he por        t nu mb e   r on the loca     l host    to wh    ich this socket is bound,
                {@code -1}  if the     so      cket i  s closed    , or
                  {@code 0}     if i    t is not bo      und yet.
              */  
        publi   c int getL             ocalPort()     {
        if    (isClosed     ())
            return -1;  
                 try {
              return getImpl().ge tLocalPort();
           }   catch (E      xception e)    {
               r  eturn 0;
         }
       }

    /**              Enable/di      sa   ble SO _T  IMEOUT with the specified       timeout   , in   
       *  milliseconds. W ith this optio     n set to a non-zero timeout,
     *       a call to    receive() for this    Data  gramS     ocket
     *  will bl  ock                    for o      nly                      this am     ount of time.  I f the timeo   ut expires,
     *  a <B>java.    net.S        ocke  tTimeoutExc   eption</    B> is ra      ised, th  ough the
         *         DatagramSocket       is stil    l valid.  The op    tion       <B>must</B> be enabled
                *  prior       to  entering the blocking operation to have    effect.  The
           *  timeout     must be {@code > 0}.
            *  A ti   meout        of z ero is interpreted a       s an infinite timeou        t.  
     *
     * @par    am timeout    the spe  cified      t   imeout    in milliseconds.
     * @throws      Soc k       etE   xceptio      n if there is an      err   or in the underly              ing     pr    otocol, such as an UDP error.
       * @s   inc   e   JDK    1.1
     * @se    e    #getSoTimeout()
     */
    publ      ic synchro   nized v   oid setSo   Timeout(int timeo    ut) throws Sock   etException {
            if (isCl   o   s     ed(      ))
            thro      w ne   w SocketEx   ception("So        cket i     s close    d");
           getIm     pl().setOption(SocketOptions  .SO_TIME      OUT, n  ew Integ       er(timeout));
      }

        /**
           * Retr      ieve setting   for SO_TIMEOUT.  0 r   eturns implies that the
     *          opti   on is disabled (i.e., timeout      of infini       ty)  .
     *
     * @return   the setting for                SO_TIME   OUT
     * @throws SocketException if there      is an     err        or in the underlying protocol, such as an UDP erro     r. 
           * @since          JDK1.1
       * @see      #set             S oTimeout(int  )
        */
    public sync   hronized i        n  t getSoTimeout() th r  ows So   cketException {
        i  f (isCl     osed())
             throw n  ew Socke  t  Exce  pti          on   ("S       ocket is closed         ");
        if (getImpl()   == nul                l)
                   return 0;
                Object o   = getI   mpl().getOption(Socke     tO ptions  .SO_T      IMEOUT)         ;
        /* extra type safety */
             if (o ins  tanceof Integer) {
            ret  urn ((Integer)    o).intVal ue();
        } else {
                     return 0;   
        }
    }

    /*    *
     * Sets the SO_SND  BUF option t   o the   specified value for th   is
       * {@ code DatagramSocket}. T     he SO_   SNDBUF option    is u  sed by th    e
         * netw  ork implem  entation as a hint t   o size  the un   derlyi ng
        * network I/O buffe     rs.   Th  e SO_SNDBUF setti   ng      may also     be used
     * by the        network implementation    t  o determine the maximum size
      * of the pa  cket that   can be sent on this socket.
     * <p>
     * As S   O_SN    DBUF is   a hin  t, applicati    ons that  want to verify
     * wha t size the buff   er is should call      {@link #getS      endBu     fferSi ze   ()}.
           * <p>
                * Incre  asi    ng th   e    buf  fer size may allow        multiple outg         oing packets
     *         to be queued by the n etwork implementation when the send rate
     * is h            igh.
         * <p>   
      *     Note: If {@li    nk #send( DatagramPacket)} is used to   send a
     * {@code     Datagr    amPacket} that is large r t       han the setting   
     * of  SO_SNDBUF the     n    i  t is implem entation   specifi     c if the
     *    packet is sent or discard     ed.
     *
           * @param size the   s    ize to which to    set the se       nd buffer
     * size. This value must  be greater than      0.
     *
     * @       exc epti    on S    ocket    Exce             ption if th ere is an error
          * in t      he underlying    proto  c   ol,    such as an UDP   error.
     * @ excepti     on IllegalArgumentExce    ption if the val  ue is 0 or is
          *      neg  a   tiv      e.
          * @see #getS    endB    ufferSize()  
     */
          public sync           hronized void setS  e   ndBu fferS  ize(int size)
      throws SocketException{
        if     (!(size >     0))   {
               throw    new Ille        ga           lArgumentEx   ception("negative    send        size");
        }
        if      (isClosed())
                 throw new SocketException("Socket  is          close     d");
           g    etImp    l().setOption(Sock etOptions.SO_SNDBU           F, n     ew I  nteger(size))      ;
    }

    /**
     * Get value  of the SO_S    NDBUF option for this {@c ode Data     g  ramSocke     t}, that is the  
     * buffer size    used by  th e platform  for output on   this {@code DatagramSocket}.
     *
     * @ret  urn the value of the SO_SNDBUF option for this {@code    DatagramSocket}
            * @excep    tion SocketExc  eption if ther  e is an error in
     * the u     n     derlyi  ng p  rotocol, s    uch as an UDP error.
        * @ s   ee #setSendBufferSize
            */
    public synchronized   int getSendBuffer   S   ize() thro    ws SocketException {
             if (isC                   losed   ())
              throw new SocketException("Socket is closed");
        int result = 0;
               Objec    t o = getImpl().getOption(Soc  ketOpti       ons.SO_SNDBUF);
        if (o inst anceof Integer) {
                       resu         lt = (   (Integer)o).intValue();
        }
                return result;
              }

    /**
     * Sets the SO_RCVBUF optio  n    to the specified value for this
           * {@   co d     e DatagramSocket}. The SO_R      CVBUF option is used      by         the
     * the   net  w o    rk implementation as a hint to size the underlying
       * network I/  O b    uffers. The SO_RCVBUF setting may also be us ed
     * b   y t   he   n    etwork implementati   on to det      ermine the  maximum     size
     * of the packet that can    be    received on this sock   et          .
     * <p>
     * Because SO_R  CVB  UF is a hint, applications that wa nt  to
     * verify          what size   the buffers were set  to should call
      * {@link   #getReceiv  eBuffe rSize()}.
     * <p>
     * Increasing SO_RCVBUF may a llow th       e n    etwork implementation
     * to buffer multiple packets whe   n packets     arrive faster than
     * are being received using {@link #  rec eive(Data             gramPack et)}.
            * <p>  
     * Note: I       t     is imp   lementation specific if a packet   larger
     * than    SO_RCVBUF            can be received.
         *
     *   @param size the size to which to set the receive b    uffe r
            *        size. This va lue must be greater than 0.
     *
        * @exception SocketExcept   ion if there is an e    rror in
          * th   e underlyi     ng protocol  , such as an UDP error.
     * @exception IllegalArgumentExce   pti  on if the value is 0 or is
        * negativ     e.
     * @se  e #getRe  ceiveBufferSize(   )
     */
    p       u   b lic synchronized void setRe    ceiv eBufferSize(int size)   
    throws SocketExcep    tion{
                   if (size <= 0) {
            throw new Illeg    alArgumentExc     eption            ("invalid receive size");
        }
        if (is Cl    osed())
            t   hrow new SocketExc   eption("Socket is closed");
        getImpl().setOption(SocketOptions.SO   _RCVBUF, new Integer(size));
    }

    /  **
     *    Get value of the SO_RCVBUF option for this {@code Datagra    mSocket}, that  is the
     *  buffer size used by the platform for input on this   {@code Da  tagramSocket}.
     *
     * @ret    urn th     e value of the SO_RCVBUF        option  for   this {@    code    Datag  ramS ocke    t}
     * @exception Sock    etException if th      ere is an error in the     underlying protocol, such as an UDP erro  r.    
     * @   see #setRe c   eiveBufferSi   ze( int)
     */
          public synchronized int getReceiveBu    fferSize()
    throws SocketExc     epti         on{
             if         (is  Closed())
                          throw new SocketExcepti     on("Socke  t is closed");
          int result = 0;
        O     bj       ect o = getImpl().getOption(    SocketOptions.SO_RCVBUF);
        if (o instan   ceof Integer) {
            r     esult = ((Integer)o).intVa    lue();
            }
        return result;
    }

      /**
     * Enable/d  is  able the SO_REUSEADDR socket option.
     * <p>
       * For UDP socket       s  it ma     y be nec    essary to b          ind   more than one
     * socket to the same socket addre ss. This is ty pically f or the
        * purpose of re ceiving multicast packets
      * (See {@link java.net.MulticastSo cket}). The
     * {@code SO_REUSEADDR} socket option a       llows multiple
     * sockets to be bound to the same socket addres  s if      the
     *      {@code SO_REUSEADDR} socket option is enabled prior
     * to b    ind     ing t he s   ocket using {@link #bind(So   cketAddress)}.
     *    <p>
        * Note: This functionality       is not   supported by all existing    platforms,
       * so it is implementation specific whether this option will be ig  nored
     * or not. Howe    ver, if it is        no      t supported then
       * {@link #getReu   seAddress()} will al ways return {@code f alse}.
     * <p>
     * Whe            n a { @code DatagramS  ocket} is crea  ted the        i     ni    tial se  tting
     * of {@code SO_REUSEADDR} is disabled.
       * <p>
      *      The     behaviour when   {@code     SO_REUSE  A   DDR} is      enabled or
     * disabled after a      socket i     s      bound (See {@link #isBound()})
     * is no    t defined.
            *
     * @param on  whether to   enable o    r di   sabl e the
     * @exception SocketException if an error occurs enablin  g or
     *            disabling   the {@code SO_RESUEADDR} sock      et option,
           *             or t      he    socket is    closed.   
     * @since 1.4
       * @see #getReuseAddress()
          * @see   #bind(   Socket Address)
       * @see   #isBound()
      * @     see #isClosed(     )
       */
    public synchronized void setReuseAddress(b   o    olean on) throw   s Socket      Exception {
        if (isClosed())
            throw new SocketEx     c   eption("Socket is closed");
        // Integer instead o      f Boolean for compatibility with    older DatagramS      ocketImpl
         if (oldImpl)
                       ge tImpl().setOption  (Socket     Options  .    SO_REUSEADDR, new Integer(on?-1:0)     );
        else
               getImpl().setOpt ion(SocketOptions.  S   O_  REUS   EADDR, Boolean.valueOf(on))  ;
    }
    
    /**
      *      Tests if SO_REUSEAD     DR is enabled.
     *
         * @return a {@code boolean } indicat      ing whether     or not SO_REUSE ADDR is enabled.
     * @exception Socke  t      Ex      ception if there is an error
     * in the underlyin  g protocol, such as an UDP error.
     * @si   nc    e      1.4
     *       @see #setReuseAddress(boolean)
     */
          public synchronized       boolean ge    tReu  seAddress() throw  s SocketException    {
        if (i    sClosed())
              th row new SocketExceptio   n("Socket is close  d");
           Object o = g     etIm         pl().getOption(SocketOptions.SO_REUSEADDR);
          return ((Boo     lean)   o).booleanValue();
       }

    /**
     * En   able/disable SO   _BROADCAST.
     *
     * <p> Some operating syst   ems  ma    y require that the Jav a v    irtual machine be     
     * started with  implem entation specifi c privileges to enable this option or
        * sen d br    oadcast datag rams.
     *
     * @param  on
     *         whether or not to have broadcast turned on.
     *
      * @throws  Soc  ketException
     *          if  ther e is an error in the underlying protocol, such   as an UDP
     *            error.
     *
        * @since 1.4
     * @see #getBroadcast()
     */
    public synchronized void setBroadcast(boolean on) throws SocketException {
        if (isClosed())
               throw new  Sock         etException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_BROADCAST, Bool  ean.  valueOf(on));
    }

             /**
     * Tests if SO_BROADCAST   is enabled.
     * @return a {@code boolean} indicating wheth    er or not SO_B      ROADCAST is enabled.
     * @e  xception S   ocketException if there  is an error
     * in the underlying p     rotocol    , such as an UDP err     or.
     * @sin    ce 1.4
     * @see #setBroadcast(boolean)  
     */
    public synchronized       boolean getBroadcast() throws SocketException {
        if (isClosed())
            throw new   SocketException("Socket is closed");
        ret  urn ((Bool  ean)(get    Impl().get  Option(SocketOp  tions.SO_BROADCAST))). booleanValue();
    } 
      
    /**
         * Sets traffic class or typ  e- of-service octet in the IP
     * datagram header for datagrams sent from this DatagramSocket.
     * As t   he underlyin    g network implementation may ignore this
     * valu e appli  cations should consider it a hint.
     *
     * <P> The t c <B>    must</B> be in t   he range {@code    0 <= tc <=
     *                255} or an Illega  l     ArgumentException will be thro wn.
     * <p>Notes:
     * <p>For Internet Protocol v4 the       value consists o      f an
        * {@code integer}, the least     significant 8   b    its of which
     * represent the value of    the TOS octet in IP p  ackets sent by
     * the  socket.
      * RFC  1   349 defin  es the TOS values as follow           s:
     *
           * <UL>
     * <LI><CODE>IPTOS_LOWCOST (0x02)</CODE></LI>
     *    <LI><CODE>IPTOS_RELI  ABILITY (0x04)</CODE></LI>
     * <LI><CODE>IPTOS_THROUGHPUT (0    x08)</CODE></LI>
     * <LI><COD   E>IPTOS_LOWDELAY (0x10)</CODE></LI>
     * <   /UL>
     * The last low order bit is always ignored as this
     * corresponds to th  e MBZ (must be zero) bit.
     * <p>
     * Setting bits in the precedence field may result in a
     * SocketException indicating tha t the operation is not
     * permitted.
     * <p>
     * for Internet     P   roto  col v6 {@code tc} is th e va  lue that
     * would be placed into the sin6_flowinfo field of the IP header.
     *
     * @param tc        an {@code   int} value for the bitset.
     * @throws SocketException if there is an error setting the
     * traffic class or type-of-     ser     vice
     * @since 1.4  
     * @see #getTrafficClass
     */
    public    synchronized void setTrafficClass(int tc) throws SocketException {
        if (tc < 0 || tc > 255)
            throw new Illega  lArgumentException("tc is not in range 0 -- 255");

            if (isClosed())
            throw new So    cketException("Socket is close     d");
                 t  ry {
            getImpl().se tOption  (SocketOptions.IP_TOS, tc);
            } catch (SocketE    xception se) {      
            // not supported if socket already connected
            // Solaris returns error  i   n such cases
            if(!     isCo     nnected())   
                      throw se;
        }
    }

    /      **
     * Gets traffic     class or type-of      -service in the IP datagram
     * hea    d er for pa    ckets sent fro    m this DatagramSocket.
     * <p>
     * As the underlying network implementation    may ignore the
     *    traffic cla ss or type-of-service set using {@lin     k #setTrafficC    lass(int)}
     * this method may         return a different value than was previously
     * set using the {@link #setTrafficClass(int)} method on this 
     * DatagramSocket.
     *
     * @return the traffic class or type-of-service already set
     * @throws SocketE xcep tion if there is an error obtaining the
     * traffic class or ty   pe-of-service v alue.
     * @since 1.4
     * @see #setTrafficClass(int)
      */
    public synchronized int getTrafficClas   s() throws SocketExce       ption {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return ((Integer)(getImpl().getOption    (SocketOpt ions.IP_TOS))).intValue();
    }

    /**
     * C     loses this datagram socket.
     * <p>
     * Any thread currently blo  ck     ed in {@link #receive} upon this socket
     * will throw a {@link SocketException}.
     *
     * <p> If this s      ocket has an asso  ciated channel then the channel is closed
       * as well.
        *
     * @revised 1.4
     * @spec JSR-51
      */
      public void close() {
        synchronized(closeLock) {
            if (isClosed())
                       return; 
            impl.close();
               close  d     = true;
        }
    }

    /**
     * Retur  ns whether the socket is clos   ed or not.  
     *
                 * @return true if the socket has been closed
     * @since 1.4
     */
    public boolean isClo   sed() {
        synchronized(closeLock) {
                return closed;
        }
    }

    /**
     * Returns the unique {@link java.nio.channels.DatagramChannel} object
     * associated with this datagram   socket, if any.
     *
     * <p> A datagram socket will have a channel if, and only if, the channel
     * itself was created via the {@link java.nio.channels.DatagramChan   nel#open
     * DatagramChannel.open} method.
     *
     * @return  the datagram channel associated with this datagram socket,
     *          or {@code null} if this socket was not created for a channel
     *
     * @since 1.4
     * @spec JSR-51
     */
    public DatagramChannel getChannel() {
        return null;
    }

    /**
        * User defined factory for all datagram sockets.
     */
    static DatagramSocketImplFactory factory;

    /**
     * Sets the datagram socket implementation     factory for the
     * application. The factory can be specified only once.
     * <p>
     * When an application create     s a new datagram socket, the socket
     * implementation factory's {@code create     DatagramSocketImpl} method is
     * called to create the actual datagram socket implementation.
     * <p>
     * Passing {@code null} to the method is a no-op unless the factory
     * was already set.
     *
           * <p>If there is a security manager, this method first calls
     * the      security manager's {@code checkSetFactory} method
     * to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * @param      fac   the desired factory.
     * @exception  IOException  if an I/O error occurs when setting the
     *              datagram socket factory.
     * @exception   SocketException  if the factory is already defined.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkSetFactory} method doesn't allow the
     operation.
     * @see
     java.net.    DatagramSocketImplFactory#createDatagramSocketImpl()
     * @see       SecurityManager#checkSetFactory
     * @since 1.3
     */
    public static synchronized void
    setDatagramSocketImplFactory(DatagramSocketImplFactory fac)
       throws IOException
    {
        if (factory != null) {
            throw new SocketException("factory  already defined");
        }
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSetFactory();
        }
        factory = fac;
    }
}
