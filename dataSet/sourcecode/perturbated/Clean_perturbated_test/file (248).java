/*
 *    Copyright  (c) 2  000, 2013, Oracle and/or    its affiliat   es.  All rights reserv    ed.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use    is   subject to licen  s   e t   erms.
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

package java.nio.channels;

import java.io    .IOException;
import java.net.ProtocolFamily;
import java.net.DatagramSocket;
import java.net.SocketOption;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.c  hannels.spi.AbstractSelectableChannel;
i mp  ort java.nio.channels.spi.SelectorProv ider;

/**
 * A selectable  ch annel  f   or datagr     a m-oriented socke   ts.
  *
   * <      p> A d     atagram channel       is c     reated by invoking             one of the {@link #o p     en open} methods
       * of this clas   s. It is not pos       sible    to create a channel for       an      ar  bitrary,
 * pre-exi   sting datagra  m      s     ock    e  t. A  newly-cr ea   ted datagram   chann      e    l is open b    ut not
 * connected. A datagram ch     annel     need not be connecte    d in     order     for the {@link  #   s  end
 * send} and {@link #receive receive} me  thods t          o be    us         ed.  A datagram channel may      b   e
 * c   onnected, by inv oking its {@link #connect connect} method, in order to
 * avoid the overhead  of the sec urity checks ar e otherwise performed    as p   art of
 * every send and receive operation     .  A datagram channel must be   con  nected     in
 * order to use t       he {@link #read(jav        a.nio.ByteBuffer) read}     an   d {@link
 *     #write(java.     nio.ByteBuffer) w   rite} m  ethods, sin ce those   methods do not
  *       accep  t or return socket address es.
      *
 * <p> Once connected, a datagram channel remains connected until it is
 * disconnect            ed or cl  osed.  Whethe        r o r not a datagram cha  nnel is connected may
   * be determined by invoking its    {      @l    ink #i  sConnected   i       sConn   ected} method.     
 *
 * <p  > Socket options are conf  igu     red     using the {@link #setOption(SocketOptio  n,O bject)
 * setOption    } method.  A da      t      agra   m channel to an Internet       Protocol socket supports
 * the following options:
    * <bloc kquote>
 * <tab le bor     der summa  ry=  "Socket op  tions       "     >
 *     <tr>      
        *     <th>Opti on N    a  me<          /th>
 *     <     th>D    escription</th>
 *   </t  r>
 *   <tr>
 *              <t     d>     {@li   nk java  .net.Stand  ardSocketOptions#SO_SNDBUF SO_SNDBUF}     </td>
 *     <td> The size of the sock  et send buffer </td>
   *   </tr>   
 *   <tr>
 *     <td> {@link java.ne     t.StandardSo cketOptions   #SO_RCV     BUF SO_RCVBUF} </t      d>
   *     <td> The   size o f the         socket receive buffer </td>
 *      <         /tr>
 *        <     tr>
 *     <td> {@    li     nk java.ne    t.StandardSocketO  ptions#SO_REUSEADDR SO_REUSEADDR} </td>
 *     <td> Re-use address < /td>
 *   </tr>
   *   <t      r>
  *         <td> {@link java.net.Stan    dardSoc     ketOp t         io  ns#SO_BROADCAST SO_BROADCAS T } </td>
 *     <td> Allow transmiss       ion of b      roadcast datagrams </td>
 *     </tr>
 *   <tr>
    *        <td> {@link ja   va    .net.Stan     dar  dSock   etOptions#IP_TOS       IP_TOS} </td>
 *     <td> T      he      Type   of Se    rvice (ToS    )     octet in the   Internet Prot            ocol (IP) header    </td>
 *   </t r>
 *   <tr>
 *            <td> {@l    ink java.net.      Stand  ar  dSocketOptions#IP_MULTICAST  _IF IP_MULTICAST_IF} < /td>
 *     <td> The network i   nterface  for Inter    n  et Protocol (IP) mul     ticast datagrams </   td>
 *   </tr>
 *   <tr>
 *     <td> {@l ink java.net.Stand   ardSocketOp    tions#IP_MULTICAST_TTL
     *       IP_M   ULTICAS  T_TTL} <     /td    >
 *     <td> The <  em>time-to-live</em> for Internet            Protocol (IP) mu   lticast   
 *       datagram   s </td>
       *     </tr>
 *   <tr>
 *     <td>     {@link java.ne t.Stan        dardSock   etO    ptions#IP_MULTICAS     T    _LOOP
 *        I  P_MULT    ICAST_LOO       P} </td>  
 *     <    td> L   oopback fo    r    Int    ernet    Protocol (IP)        multicast datagrams    </td>
   *     <       /tr>
 * </       table>
 * <    /blockq uote>
 * Additional (implementation specific) o    ptions may also be suppo   rted.
 *
 *          <p      > D   atagram channel      s are safe       for use   by multiple              co  ncurrent thre    ad   s.         T         hey
 *  support concurrent        reading and        writin       g, th    ough at   mo         st      one   thread may    be
     * r    ea ding and at most one thread    may be writin    g at    any given      ti   me.  </p>
 *
    * @author Mark Reinhold
 * @author JSR-51 Exper  t G     roup
 * @since 1.4
 */

publ    ic a  bstract class Da   tagramCha     nnel
    e  xten    ds AbstractSelectabl eChannel
      implements ByteChannel, Scattering ByteChannel, Gatheri     n         gByteChan nel, MulticastCh            annel
{

         /*    *
     * Initializes a new instance of       th   is class.
     *
       * @p      aram  provider
           *         The provi   d    er   that cre ated thi  s     ch an  nel
     */
         protec   ted D    ata      gram   Ch annel(SelectorProvid  er provi   der) { 
        super(provider);
           }
  
        /  **
           * Opens a datagram c hannel.
     *
     * <p> The    n    ew channel   is        created by              i   nvoking t he {@l          ink
      * java.nio.channels.spi.SelectorProvider#o     pen   Data    gram  Cha       nn  el()
      * openDatagramChannel} metho   d of th        e system   -wi          de default   {@link
          * java.    nio.    channels.       sp           i.S  electorProvi  der    } o bject.      The chann  el wi    ll not be   
        * co    nnected.
        *  
         * <p>   The {@link ProtocolFamily Protoco    lFa    m       ily}          of t        he channel 's socke           t
     * is platform (and possibly       con    figurati   on) dep   endent  a     nd there     fore un      s   pecified.
     * The {@l   ink #open(P  r   otocolFamily) open    } a llows the pr      ot   ocol family to b   e
      *    se      lected when ope           ning a d           a     t  agra   m channel,    and should    be used to    open
     * datagram channels that   are    intended for            I   nternet Protocol        mul  ticasting.
     *
        * @re  turn  A new dat     agra                   m channel  
           *
              * @throw  s  IOExc           ep   tion
     *          If       an I/  O error          occurs
     */
       pub         l   ic            static  DatagramCh    annel open() throws  IOExcepti  on {
        retur  n SelectorProvider.pr       ovider().open Da   tagramChannel();
             }

       /    **
     * O   p  ens a datagram chan       nel.
     *
     * <p> The {@code family } parame  ter is used to specify the    { @         lin      k 
     * Pro  toc         olFamily}. If the   dat  ag   ra   m     channel is to be us    ed for IP multicast      ing
     *    then thi  s should correspond     to     the   ad      dress type of   the mul ticas   t groups    
     * that th     is channel w  ill join        .
     *
                        * <p> The new         channel is crea t   ed by invo    king the {@link
     * j    ava.nio.chan   nel  s.spi.SelectorP   ro   vider#openDatagramChanne  l(Pro     to   colFamily)       
     * open   DatagramChan  nel} m     eth  od of the system-     wide defa             ult {@link
       * java.nio   .channels.sp    i     .SelectorProvider} object.  The cha     nnel wil  l no    t be
       * connected.
     *
           * @   param   family     
     *                           The     p  rotoc  ol family
        *
     * @return       A new   datag ram ch    annel
     *
           * @ throws  Unsuppor   tedOperationException
     *               If t   he specified prot      ocol family i  s not s      up  ported. For  example,
             *                          suppose th    e p a     rame  t  er is s   pecifi   ed a      s {@link
                            *                 java    .net.Standa     rdPr     otocolF             amily#I         N  ET6 StandardP   ro         to colFamily.INET6}     
     *          bu       t IPv6 is       not enabl  ed on t  he platform.
     *              @  thro  w s  IOException
       *                   If an I/O er  ror occu rs
                       *
      *  @s    ince              1.7
     */
    publi                         c st      atic DatagramChannel open  (ProtocolFam      il   y  fam      ily) throws IOExcepti    on {
        return Sel   ectorProv   ider.provider().op    enDa   tagramCh  ann  e    l(fami ly   );
    }           

                /**     
           *       Returns an operation        s   et i     dentifyi    ng     this channel's suppo   r    ted
            * ope    ratio   ns     .
     *
      * <p> Dat    agr               am      cha  nnels su  ppor     t reading and wri            t   ing, so th  is        method
     * r    eturns     <          tt>(</tt>{@  l     ink SelectionKey#OP_READ} <tt  >|</tt>&nbsp;{@link
        * Sele        c  tionKey#OP_WRITE}  <tt     >)</tt> .         </p>
     *
       * @re     tu rn  The valid -op      eration set
              */
       publ  ic f        inal int vali  dO  ps()       {
        return (Sele          c  tionKey.OP_READ
                       | Se     le    ctionKey.OP_ WRITE   );
    }


                         // -- Socket-specif    ic operati  ons    --

    /**
     * @throws  Alread  y    B  oundExce    p   tion               {@inheritDoc}
     * @th   rows  Unsup      portedAddres  sTyp     eException            {@inher    itDo  c}
     * @throws  Cl   osedChannelExcept      io     n                          {@    inh                         eritDo         c}
      * @throws  IO E     xce pt   ion                                  {@in heritDoc}
       * @throws  SecurityExcept  ion
     *          If a security mana      ge  r has been installed a              nd i    ts {@   li     nk
           *              Se  curityMana ge   r#checkLi   sten checkLis te            n}                  method denies the       
          *          operation
      *
     * @         since 1.7
     */
    pub         lic abstract D   atagramChanne   l           bind(Soc        ketAddre  ss       lo    cal ) 
        throws IOException;

         /**
     * @throws          U          nsup p  orted   OperationE       xcepti        on                               {@inheritDoc}
        * @throws  Illegal   ArgumentException                                     {@inh     eritDoc}
     * @t      hrows     ClosedChannelExce  ption                   {@inheritDoc} 
     * @    thr  ows  IOE            xce ption                                        {@inheritDoc}
     *
     * @sinc         e 1.7                
     */
    publi    c abstract <T> DatagramCh   ann     el se tOp           tion(S     ock   etOption <T>       name, T value  )
            thro    ws IOExc  eption;

    /**
        *      Retr        ieves a d    ata    gram socket assoc          i   ate   d with this channel.    
           *
         * <p      > The     r eturn  ed ob  ject will   not    decla re any  pu     blic    me thod   s that are  not
         * decl     ared in the {@link ja    va.net.DatagramSocke  t} c  l       a    ss  .  </p>
                   *
      * @retur  n  A datagram socket assoc iated with   this channe   l
     */
       publi c a  bstract Dat      a      gr  a     mSocket socket( );    

         /**
     * T     ell   s whether or   not this channel's socket is con           nect   ed.
          *
     * @return  {@code true} if, and only if,   thi s c     ha       nnel's s             oc  ket
         *           is      { @link #isOpen open} and c    onnecte  d
            */
    publ  ic a     bst  ract     boolean isCo       nnected();

                    /**
     * Connects this      c        h  a    nn el'   s           sock et.
     *
               * <p>   The channe      l'     s socket is configured so        that    it only receives
          * datagra   ms from, and sends datagrams to     ,  the give    n rem ote <i>peer    <      /i>   
     * address.  Once conne    ct     ed, d   atagrams may not be received from     or s en  t    to
           * a      ny other address.  A datagram sock  et r     emai  ns connect    ed un         til it       i    s
     * explicitly d isconnected or until  it i s c             l   ose d.
                *
        * <p> This method performs e   xactly the same security   c  h          ecks as the {@link
     *    java. n  et.DatagramSocke t#connect connect} me          thod o    f   t   he      {@link   
              * java.net.                   Datagra             mS   ocket}    cla ss.         That is,          if a security manager h    as bee     n
     * installed then this m    ethod verifies t  hat it   s    {@link
               * java.lang.Securit yManage   r#c      heckAccept che       ckAcc ep  t} and { @link
         * jav      a .lang.Sec  urityMa     nager#  chec    kC onnect checkCo nnect} m     e           thods    p    ermit        
     *      d           ata grams to     be received from and sent to, respecti  vely,      the given     
     * r   emote ad   dress.  
     *
     * <p> This meth od may be invoked at any    time.                  It will no       t have       any e ffect
     *      on read      o     r write oper          a tions  that  are already in progress at the moment   
      * that it is       invoked. If       this channel'   s socket i         s  not bound then this me      tho   d
       * wi        ll      firs  t ca use the socket to    be bo    und t   o a    n   addres s t   hat is assigne  d
     * automatical   ly, as  if i  nvoking th     e {@li   nk   #bind  bi   nd}      method w   ith a
     * parameter      of {@code  null}. </p>       
        *
     * @param  re   mote
     *         The rem            ot    e     add      ress to wh           ich t   his    channel is        to be connected    
     *
     * @              return  This d    ata gr    a    m       channel
          *
       * @t hrows  ClosedC  hannelExceptio   n
                         *                If   this   chann      e  l     is clos           ed
          *
      * @throws  Asyn                       chronou       sCl    oseExceptio        n
     *             If another th     read clos   es t     his cha    nne    l
        *          wh  ile the connect operatio   n is i      n  progress
        *
            * @throws  ClosedByInter    ruptExc     eption
          *                       If      another threa          d interru    pt     s th     e current thread
          *                            while the       connect operation is in progr   e                ss, thereby
     *              cl      os                  ing the ch    a       nnel and setting the curr ent    thread's
         *                  i  nt      e     rrupt status           
           *
     * @throws  SecurityException
     *                  I    f    a se   curit      y manager          has b     een i    nstalle d
                           *          and it d              o   es no    t permit                 acces          s to the given remot          e      address
            *
          *  @throws   I   O Except   ion
     *                         If      some other   I/O e           rror occurs    
     */
    publi              c abstrac               t       DatagramCha  nnel connec        t(SocketAddress         r   emote)
        thr ows IOExc   eption;

    /**    
     * D  isconnects th   is channel'          s socket.
     *
        * <p> The chan              nel    's       socket is   configured     so   that it can     receive dat       agrams
     * fr   om, and sends  d      a   tagra  ms to,  any remote    ad     dress so lo ng as the       securi ty
     * m   a    nager, if inst    alled, per     mits it.
               *      
               *  <p>      Thi s                  method may be inv oked at any t    ime.  It will n  ot  hav    e    any effect   
                           * on r    ead or w  rite ope     rations     that are     already in progress          at the moment
     * that     it is invoked.
     *  
     * <p> If this channel's socket is not connect ed, or      if the channel is
                * c         losed,                the n invoking this me tho  d   h    a   s n       o e   ffect.  <     /p>
     *
     * @return  This datagra   m cha nnel
     *
         * @throws  IOExce     ption
          *           If som       e other        I/           O error occu  rs
           */
    p         ublic        a  bs   t   ract Datagr amChannel disco    nnect() th       rows   IOException;

    /**
      *      R  eturns th    e remot   e a  ddress      to which this channel's        socket is co  nn  e     ct ed   .
     *          
        * @return  T he remote  addre   ss;       {@code null} if the ch  annel's soc ke t is   not
     *          connected
            *
         *    @throws  ClosedChannelEx ception 
          *          If the c h  anne         l is    cl osed
     * @throw  s  IOException
     *           If an I/O          error occurs
       *
     * @since     1.7
     */
    public a        bstra        ct S   ocketAddress  g   etRemo       teAddress          () throws IOExc   eption;

       /**
     *            Re   ceiv      es a data   gram v   ia this chan     nel     .          
           *
        * <p> If    a  datagram     i           s i  m    m      ediately available,   or    i     f th        is chann   el is in  
      * blocking mode       and   one eventual        ly     become   s a       vaila     ble  , th       en th e dat agram is
     *  copied i  nto the g   iven byte buff   e  r        and i     ts source  ad dress i   s returned.
     * If this      channel i   s in    non-blocking mode and a data     gram is       not
                      * immediately a   vailabl  e then this met        hod immediat  ely ret      urn s
     * <tt>      null          </t      t>.              
          *
     * <p> The datagram is transferred int      o the     given byte buffer sta rt  ing at
         * its curren    t po   si      tion, as if by a regular {@link
         *  ReadableByteChannel#           read(java.ni  o    .ByteBuffer) read} op   erati      on.  If    the  re
     *         are fewer bytes remaini  ng in   the        buf        fer th  an are require       d to hold t    he
        *    datagram then the   remaind     er o  f t         h e datagr        am         is sil   ently     discar             de      d.
       *       
     * <p  >  This method perf      orms exact   ly the same      security ch          ec  ks as th        e {@link
     * java.ne    t.Datagr                 amSock        et#receive    receive   }    met             hod of the      {@li     nk
        * j  ava.net.Da   tagramSocket} cl      ass.  Th  at i   s, if the socket          is not connected
                  *    to a  spe    cific rem   o    te ad    d     res     s and    a           securit   y    m        anager    has b      een   installed
     * th    en        f            or    each d  atagram re  ce    ived this m      ethod ve   rif   ies that the source's
         *    a  ddress and port number a   re  permi      t     ted by         t   he se  cu    rity manager's {@link
          * java.la    ng.S ecur   ityM            anager#chec        kAccept chec  kAccept} method.  The overhead
             * of this security c     heck            can b e a        voi   d     ed b      y f       irs   t connec    t    ing the socket via
           *         the {@link #connect connect} m ethod.
                     *
        *  <p   >     This  method           may      be invoked       at                any time.  If a   no  ther thread             h  as
     * alrea     dy initiat  ed a r          ead operation upon this   ch   a        nnel, h   owe        ver, then an
     * i              nvocatio n of t his method wil  l block u   ntil the  first operat  ion is
                * com   ple    te. If this channel's s ocket is not b   o      un  d th     en  thi  s meth      od will
         * first cau   se         the so cket to       be      bound    to  an address t            hat is ass    ig   ned   
     * automat       ically, as if       invoking the {@l   i     nk        #b  ind bind} metho   d with a
     * parameter o     f {@c   o    de null  }                  .  </p>
         *
     * @p  aram   d  st
     *                The buffer in    to w              hich the dat  agram is to  be transferred
     *
     * @r  eturn        Th  e data   gram's sou       rce address,
          *          or <t       t>n     ull    </tt> if this cha    nnel i s in non-bloc  k in   g   mode
     *              and no datagram was immediately availabl  e
        *
          * @throws       ClosedC hannelExce         ption
       *          If this cha   nnel    is clos     ed
     *
     *      @throws                  A   synchro  nousCloseException
     *          I    f ano        t he   r      thread closes t hi s channel
         *                         while   the read opera   tion is  in progre        ss
       *
                * @throws           ClosedBy     InterruptExcep  tion
          *           If another thread interrupts      the current t h  read
        *          wh   ile the read ope  ration                 is in progress, thereb     y
     *                 closing t    he             ch       annel and setti   ng   the    curre   nt   th   read   's
              *          inter   ru      pt status
       *
        * @        thr   o ws  SecurityE  xception
      *                  If a s     ecurit       y manager ha       s been i   nstalle  d
         *                and it do         e    s n     ot per     mi       t dat    agrams to be accep  ted
              *            from t   he   dat    ag      ram's send     er
     *
     * @throws     IOEx    cepti  on
      *                                If som e o         ther        I/O          error occu     rs
         */
           public a         bstr      ac t SocketAddress receive(    ByteBu      ffe      r                              dst) thro     ws             I  OExcepti    on;

    /**
     * Sen  ds a    datagra      m via     this chan nel.
         *
          * <p> I   f this           channe      l is in non  -b      lo              cking mode and th  ere is suff   icient room
     * in th    e underlyin    g outp  ut  buffer, or if   this channel is      in     blo   ck  i   n  g mode
          * and su    fficient r      oom b    ecomes available, then th    e              remain  ing by   tes in the
     * given buffe       r   are t   ransmitt  ed as a single data    gram             to t        he given target
              *  addres  s.
                   *
        * <   p> The da tagram is transferred     f   ro   m the  b     yte buffer   as i    f b        y       a regular
         * {  @lin      k WritableByteChann el#write(java.nio.Byte Buffer) write}    operation.
       *
          * <p>        This method per   fo rms exac          tly the same security checks as     the {@li  nk
     * java  .net .Data  gram   Socket#send send        } meth   od of the {@lin   k
     * java. net.Dat           agramS  ocket             } class.  That is  , if              the   socket      is     not   connected
     *        to a      specific r   e          mote add  ress    and a  secur   i ty ma      nager    has b     een inst         alled
     * then fo r eac        h    datagram  sent this method verifies th  at  the tar get addre     ss
              * and         port number a r            e permitted          by         the sec  urity        m   anager's {@link
     * java.la    ng     .    SecurityManager#c    heckConnect   checkConne    ct      }    m  ethod.   The
      * ove    r  h    ead of this    secu   rit   y ch   eck can be       avoi     ded by f irst c   on      necting     the
      * so       cket    via the {@l  ink        #connect  connect}      method.
            *
        * <p> This    method may be         i nvoked at any time       .           I  f    another t     hread   h     as
     * alre     a   dy     initiated a write    operation upon      this     channel,  howev  er , the      n an
       * i  nvocati    on of this meth    od     will block until the first operati  on is
          * complete. If thi   s cha   nnel's      socket is not bound t  hen th     is    m   ethod will
     * firs   t cause the socket to be      boun  d to a     n add     re   ss      tha  t i  s assigne    d
                  * a  utomatic    a       lly, as if by i       nvoking the {@link #bin  d bind} method with a
       * para  mete  r of      {@code      nul         l}. </p>
        *
           * @param         src
              *               The buffer c       on     tain   ing t   he    d           atag      ram to        be sent
          *
     * @param  ta   rget
      *         The addr    e     ss to w     hi  ch                       the datagram is t   o be sent
         *    
     * @return   The number of byt  es sent    , which wi    ll be e       ithe     r the number
         *            of bytes that were remain        i ng i    n the so   urce buffer    when    this
           *                method was invo  k   ed or,                       if t  his     channel is non-     blocking, ma     y be
      *                  z ero if ther    e   was insufficient room for the datagram in the
     *              u  nd       e    rlying output b  uffer
       *
      * @thr      ows  ClosedChannelEx          ception
     *          I     f this          channel is closed     
     *
     * @thro        ws  A  synchronou       sClos   eExcept           ion
     *                If      another thread c  lose     s thi     s channel
     *               whil   e the  read    opera         tion is in pro   gr      ess
     *
     * @throws  ClosedByInterruptException
                 *          If anoth er thread interrupts the current        thread
     *               while the read o     perat    ion is in progress, the  reby
     *          clo  sing the channel and setting the                   current   thread's
     *             i     nterrupt status
       *
         * @t hrows  Security Exception
      *                If a s       ecurity manager has been        i nstalled
     *          and it does not per  mi   t datagram          s to    be   se   nt
     *                to the given address
      *
         * @th  r ows  IOExcept    ion
     *                  If so  me other I/O er   ror occurs
     */
    public abstract int send(ByteBuffer  s  rc, SocketAdd   ress target)
        throws IOException;


    // -- ByteChan nel operations --

           /**
       * Rea    ds      a datagram from this  channel.
     *
     * <p> This method may only be    in               vok         ed i    f this cha       nnel's socket is
     *             connected, and it only accepts da  tagrams from the socket's  peer.  If
     * there are m  ore     bytes in the datagram than remain in the given    buffer
     * then the remainde  r of    the    datagram is silently discarded.  Otherwise
     * this method behaves      exactly    as spec       ified in the {@link
          * ReadableByteChann   el} interface.  </p>
           *
     *  @throws        No     tYetConn   ectedException
     *          If this channe    l's socket is not   connected
     */
    public abstract i  nt read(By   t  eBuffer ds     t) throw s I  OException;

    /**
     * Reads        a datagram from this chan nel.
     *
     * <p>        This metho d may only be invoked if this channe  l's socket is
     * c      onnected, a n  d it only accepts  datagrams from the socket's peer.  If
           * there are more bytes in the datagr am than  remain   in the given buff       e      rs
     * then the r  emainder of the dat           agram is silently discard    ed.  Otherwise
     *   this   method behaves exactly as specifie     d in the {@link
     * Scattering    By  teChannel} interface.    </p>
        *
     * @throws  NotYetConnectedException
     *          If this cha nnel's s      ocket is not connected
     */
    public      abstract long re ad(ByteBuffer[] dsts, int offset, int  length)
          throws IOExcepti    on;

    /**
         * Reads a datagram from   this channel.
              *
           * <p    > This method may on    ly be      invoke  d if this channel's socket is
        *     connected, and i   t only acc    ep ts   datagrams from   the socket's      peer.  If
     * there are more bytes in the datagram than remain in the gi   ven buffers
     * then the remainder     of the datagr   am is sile   ntly disc  arded.  Otherwise
          *  this      method behaves exact ly as specified in th   e {@link
     * Sc   atteringByteChannel} interface.  </p>
     *
         * @throws  No tY     etConnectedException
     *          If th    is channel's socket is not connected
     */
    publ   i     c final long read(ByteBuffer[] dsts) throws    IOE    xception {
        return read(dsts, 0, dsts.length);
    }

    /**
     * W   rites a datagram to this    chann  el.
     *
     * <p> Th         is met   hod may  only be invoked if this channel's socket   is
     * co  nnected, in      which case it sends datagrams d  irect   ly to       the socket's
          * peer.  Otherwise it behaves exactly a       s specified in the {@link
       *  WritableByteChannel} interface.  </p>
     *
         * @throws  NotYetConnectedException
     *          If this channel's socket is not connected
     */
    public abstract int write(ByteBuffer src) throws      IOException;

    /**
      * Writes a datagram to this c    ha     nnel.
     *
     * <p> This     meth     od ma   y only b   e invoked if this channel's socket is
     * connected, i  n which case it sends datagrams directly to the socket's
     * peer.  Othe     rwise it behaves exactly as specified in the {@link
     * GatheringByteChannel} interface.  </p>
          *
     * @    return     The number of b ytes sent, wh     ich will be either the nu   mber
     *           of bytes tha       t were re  m aining   in     the source buffer when this
     *                method was invoked or, if this channel is non-b        locking, may be
     *           zero if there was insufficient room for the datagram in the
     *           underlying output buffer
     *
         * @throws  NotYetConnecte dE  xception
     *          If this channel's  socket is not connected
     */
    public abstrac  t long write(B       yteBuffer[] srcs, int offset, int leng  th)
        throws IOException;

    /**
     *    Writes a     da  tagram to this channel.
     *
     * <p> This method m   ay only be invoked if this channel's socket is
      * connected,  in which case it sends datagrams directly to the socket's
     * peer.  Otherwise i  t behaves exactly as specified in the {@link
     * GatheringByteChannel} interface.  </p>
     *
     * @r      eturn   The number of bytes se    nt, which will be either the number
     *           of bytes    that were remaining in the source buffer when this
     *           method was invoked or, if this channel is non-blocking, may be
     *           zero if there   was insufficient room for the datagram i     n the
     *           underlying output buffer
     *
     * @throws  Not YetConnectedException
     *          If this channel's socket is not connected
     */
    public final long write(ByteBuffer[] srcs) throws IOException {
        return write(srcs, 0, srcs.length);
    }

    /**
         * {@inheritDoc}
     * <p>
     * If there is a security manager set, its {@code  checkConnect} method is
     * called with the local address and {@code -1} as its arguments to see
     * if the operation is allowed. If the operation is not allowed,
     * a {@code SocketAddress} representing the
      * {@link java.net.InetAddress#getLoopbackAddress loopback} a  ddress and the
     * local port of the channel's socket is returned.
     *
     * @return  The {@code SocketAddress} that the socket is bound to, or the
     *          {@code SocketAddress} represen       ting the loopback address if
     *          denied by the security manager, or {@code null} if the
     *          channel's socket is not bound
     *
     * @throws  ClosedChannelException     {@inheritDoc}
     * @throws  IOException                {@inheritDoc}
     */
    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;

}
