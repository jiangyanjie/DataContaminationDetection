/*
     * ORACLE      PROPRIETARY/CONFIDENTIAL. U   se is su   bject to license           ter ms.
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

/*
 *
 *
 *
    *
 *
 * Wr    itten by Doug     Lea with    assistance from       membe       rs of JCP JS  R-166
 * Expert Group and  releas   e  d to the public domain, as exp     lained at
 * h   ttp://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent.locks;
import java.util.concurrent     .TimeUnit;
import java.u til.ArrayList;  
impo    rt java.util.Collection        ;
import     jav   a.util.Date;
import sun.misc.Uns     afe;

/**
 * A version of {@link AbstractQueuedSynch  r  onizer} in
 * which      synch  ronization stat   e i   s ma    i nta  ined as a {@code lon g}.
 * This class has exac tly the  same stru     cture, propert ies, and methods
 * as   {@code     Abst  ractQueuedSynchronizer}     wit h  t he exception
 * tha    t all st  ate-rela                   ted parameters and results a       re defin  ed
 * as    {@code long} rather    than {@code int    }. T    his  clas      s
 * may       be useful     when creating sy      nch  ronizers su          ch as
 * multilevel locks and barrie   r   s     that requir e
         * 64 bits o     f state.
    *
 * <p>See {@l  in       k AbstractQueuedSynchroni  zer} for usage
 *       note    s    and ex      amples.    
 *
 *  @since 1.  6
 * @author Dou g Lea
 */
public ab stra  ct class Abstr      actQueuedLo ngSynchronizer
    ext      ends Abstr  act  Ownabl  eSynchr  onizer
              implements ja  va.         i o   .Serializable {

    private          stati        c final long serialVersio    nUID = 737398 49725 72 4  14692L;

       /*
                 To keep sources in sync   , the r    emainder of this     sou       r  ce fil     e   is
      exact   ly     clon  ed from Abstra   ctQu  euedSynchro        n   iz      er,             re     placing class
       name and chang    ing      int s related with sync     state to longs. Pl e  a   se   
         keep i   t that way.
        */    
      
          /**
     * Cre     ates a   new {@c  o  de AbstractQueued      LongSyn           chron   izer} instance
       *    with initi   al  syn  chroniz   a      ti    o  n       state of zero  .
       */
    protected AbstractQueuedLongSyn       chronizer    () { }

         /**   
       *    Wa     it        queue node cl   ass      .
     *
           * <p>The wa   i        t queue is    a variant of a "CLH" (Cra   ig, La    n din,      and
     * Hagersten      ) lock queue. C  LH    locks       a  re  norm  ally use     d for 
      * spinlocks.  W              e instea                d use them for bl  ocking s    ynchr      oniz            ers, but
     * use th    e sam  e    basic      tac    t  i         c of holdi  ng  some of the      c   ont   rol
       *    information about a thread in t     he  pr edeces   sor    o    f it    s node.          A
      * "st   atus" field in e     ach   nod  e keeps tra       ck   of whether a thread
     * should block    .  A node is    sign   alled when  its predec e ssor
       * releases.        Each node of the queue otherwi   se s     erve       s as     a
           * specific-  notification-style m  onitor ho    lding a           single waiti   ng
     * threa   d. The st                atus    fie    ld   does NOT co ntro l wheth  er t hrea  ds ar  e
     * granted lo         cks etc though.   A thread     may try to   acquire if it is
        * first i  n the       queue. But       being first does not guarante    e succes      s;
     *      i   t only    gives the ri   ght to   conten d.  So                th       e currently released
               * contender      thread            may need to rewai    t.
     *
     *    <p>To  e   nqu    eu   e i  nto a CLH lock, yo    u atomically   splice it in as new
        * tail. To dequeue, you j    us     t se  t      t    h     e head   field.
                * <     pre>
               *        +----        --+     prev +-----+           +-----+
                 * h     ead    |      | <--- - |                  | <-   --- |       |  tail
     *        +------+         +-----+         +-- ---+
       * </pre>
        *
     * <p>Inser tion into a CLH queu      e r   equi res only   a             singl    e atomic
                   *  ope   ration on "     tail", so  there is a s    imple atomic point of    
     * demarcat        ion   fro  m unqueued to     queued. S       imil  arly, dequeuing
       * involves o     nly           u   p    dating      the "head". However, it    tak   es   a bi t
     * more wo    rk      fo   r nodes to determ     ine     w      ho their successor          s ar      e,
     * in part to deal with pos sible cancellation due t o timeouts
     * and                 interrupts.
             *
     * <p>The     "prev" links (n    ot used in      original CLH      locks  ), are mai    nly
     * needed to handle cancellat   ion. If a   node is cancelled, its
     *     succes    so     r is  (normally) relink  ed to a    non-  ca               nce   l  led
        * predece       ssor.    For explana              tion of   similar mechan  ics in the case
     * o   f    spin lock      s, see the papers by Sc ott an       d Scher er     at
         * http://www.cs.rochest er.edu/u/scott/s        y n       chronizatio   n/
        *
          *  <p>We   also use "next" links   to impl  ement blocking mec      ha nic       s.
                    * The thread id      for    each n      ode    is kept in its own node, so      a 
     * pre  decessor signal        s the next    node to wake up by     tr    aver   si   ng
        * next link to d    etermine w   hich     thre  ad it is .  Determinat ion of
      * successor mus  t           a v        oid ra       ces wi        th ne wl   y q       ue       ue      d nodes to set
     * th         e "next   " fi e  lds of   their pred  eces            s     ors.  This      is solve d
            * when necessary b    y     check     ing backwards from the atomica  ll   y
     * updated "tail"        whe  n a node's su   ccessor a      p    pears t    o be null.       
     * (Or, said different ly, the next-links                  are an optimization
     * so          th at we don't usu   ally  need a backwa     rd sc  an.)
     *
     * <p>Cancellation introduces    some conservatis   m to the basic
     * a    lgorithms.  Since we must p oll for          cancellation of othe     r   
     * nodes  ,         w        e         can m      iss noticing whet   her  a can   cell   ed node    is
     *   ahead or behind u s. Th     is is dealt   with by a   lways unparking
      * successors up   on    cance        l     lation,        allowing       them to stabi                lize        on
        * a new predece   ssor, unl    ess we can id      enti    fy       an  uncan       celled
     * predecessor who will carry this res  pon s       ibility.
       *
          * <p    >CLH q    ueues need a   dummy header node to ge    t st arted. But 
        * w    e do  n't create them on cons    tructi   on,      beca        us  e it      would b e wast     e d
       * e       ffort if t     here is n   ev  er content   ion. Instead, the  node     
        * is constructed and head and tail poin     ters   are set upon first
           * contention.
     *
           * <p>Threads waiting on Condit     ions use       the same   nodes, but
     * use an         a        d    dit       io   na         l  link.   Conditions only need t o link node   s
              * in simple  (non-concurrent     ) linked que    ues  because t     h       ey     are
       * only ac      c   ess      e        d    when ex   clusively  h    e  ld.       Up   on awa       it  , a n  o de  is
             *    ins    erted into     a             conditi   on queu     e.  Upon signal, the node i s
         * transferred     to    th        e      main  q ueue.  A sp   ec       ial v  alue of            status
     * fiel      d is      use       d          to mark wh ic                  h que ue a no   de is on.
          *
                           * <  p>Th   anks go t   o Dave Dice          , Ma  rk Mo  ir, V      ict           or       Luc      hangco, B      ill
            * Schere     r    and M  ic h    ael Scot        t,         along with members o      f    JSR-1  66
       * e     xpert        gr     oup, for   helpful ide      as   ,    d  iscussions       ,    and critique   s
             * on t        h   e        de   sign of t    his cla       ss .
         *         /
    sta       tic f   inal cl                 as          s Nod    e {
        /** Mark er    to ind    icat e a     node is waiting in shar    e  d mode */
                 static final    Node               SHA     RED             =     ne        w N o        de();
                          /**  Mar              k  er to ind     icat  e a node            is wa  iti     ng in exclusive m ode    */
           s    ta      tic final Nod        e     EXCL       USIV  E = nul        l    ;

               /** waitStatus va  lue t  o      indicate  thread has cancelled */
                       static             final int CANCELLED     =  1;
                     /**   wai    tStatus va    l   ue to indicate suc  cessor's      thread needs un pa      rking */
        static final        int SIGNAL                = -1    ;
                                /** w  aitStatus value          to     indicat e    th       read     i        s wait    ing on condition */
            s    tatic  final int     CONDITION  = -2;
          /**
             *            waitStatu    s           value   to indicate the     n   ext acqu     ireShar ed                sh    o  uld    
                  * unco        ndi   t  ionally pr  opa   ga   te
         */
                     stat  ic      final in   t PROPAG   ATE = -3;

                              /**
                  *       Stat  us f  i eld, tak i  ng on on  ly the  values:
           *   SIGNAL:       The success       o    r of         th   is n   o de is              (or will   s    o on be)  
            *                          bl    ocked (via          pa     rk ), so           the curre    nt node must
             *                                  unpark its    successor   whe        n it    releases            o       r
          *                            cancels. To avoid races,    acquire methods must
            *                                                first indicate they need  a            si   gnal,
            *                         then r        etry th        e a   tomic acquire                , an   d the                n,
         *                  on failure, block.
            *       CANCELLED         :                   T  his  node         is cancelled   du    e to timeo   ut  or             in      terr   upt.
         *                     No             des never lea     ve this state. In  particul    ar,
          *                          a thread wi th             ca n  celled    n   ode never again block   s.   
           *      CO   ND  ITION:  This node is currently   on a     co    ndi  ti         on     queue.
              *                                  I t will n   ot be used   as a sync queue n  ode
         *                            un    til transfer      red, at  w hich time the st    a   tus
                        *                    wi  ll be set to 0.       (Use     of t                  his valu            e here h as
         *                       nothing to do with t   he other uses of               the
         *                        field, but                          si            mplifies     mechanics.)
            *   PROPAG ATE:      A          re      lease  Shared should be propagated to ot    her
            *                          no   des. This  is  set         (f     or head node onl  y)                in
            *                          doReleaseSh            ar   ed to ensure   propagation
            *                      c    onti  nues, eve     n if other operati    on  s  have
            *                  si      nce          i  ntervened.
                    *     0:                None of     the above
           *
            *           The values are    arranged             nu   merically to si    m     plify use            .
            *    No    n-negative v             alues me an th  at a node doesn't ne ed    to
         * signal. So, m o    st cod   e does  n't need     to che         ck for parti cular
              *   v       alues, j     ust f  or s     i   gn.
         *
         * T   h    e field is initi        al ized to 0   f    or normal sync       nodes,     an      d
               *  CO NDI         TION fo      r  c   ondition no   des.     It is modi  fied using CA   S
           *   (or w           hen p     ossible, unconditio       nal volati         le     w  r      i          t  es).
             */
               volati   l   e int waitStatus;    

        /**
         * Link           to p          r       edec           essor   n      o            de that curr    ent node/th  read rel ies    on       
            * for che  cking wa     i tStatus. Assigned du   ring enqu  eu  ing,       a   nd          nul led
                * o  ut (for sake of GC) o    nly     upon    de  queuing.  Also    , up on
                       * c    ancel  lation o           f a        predecessor, we short- circuit while
                         * f           inding    a non-c anc  elled o     n      e, wh  ich will always        exist
                         * be            c ause t      he       head node is   n ev er ca    ncelled   :    A        node becomes
                      *    head only    as a   result o   f s  uccessful    ac quire    .               A    
                       *    cancelled thr   ead  never  succeeds in acqui     ring, and a thread only
               *   canc  els   itse   lf, n  ot any ot      h    er node.
                           */
                      volatile Node prev;

        /**
             * L       ink to the     successor node that the    c          urr       ent no de/thread    
                   * un        park   s    upon rel    e    ase.     As  signed     d   uring enqueuing, adj   us  ted
           *  when                bypa      ssing ca   nce     lle      d p       r      edecessors,    and n  ulled        out (for
            * sake o     f    G              C  )             wh       en dequeued.  The enq operati    o  n does not        
         * assig     n nex  t fi           e  l         d of a   pre decessor     unti            l af    ter a    tta  chmen    t        ,
              *  so seei    ng a n   u  ll           next fiel   d does    not    nec        essarily me      an t    hat
         * nod e      is at e  nd o  f queue.   Ho        we         ver,               if               a nex  t     f  ield appe    ar    s
                       * to   b  e null, we can scan pre   v's  from the tail      to
                * d    oub     l  e     -   check.  Th          e nex  t field o       f can    c el     led  nod   es is s     et       to
           *          point to the      node  i   tself instead of null, to     make life
                                        *     easie    r for is   OnSyncQueu      e.
                                */
                        volatile Node          next;

                              /*     *   
             *     The thread    that enqueued t     h          is     no      d  e.  I    nit   ialized on
             * constructi   on and    null     ed out afte   r use       .
         */
            volatile              Thr         ead thread        ;

                /**
         *                    Link to     next no     de wait  ing on c       ondition, o   r t     he    s     pecia   l          
                      *     value S     HARED.  B       ecause condition queues are accessed only
           * when   holding in exclus  ive  mode,   we         just need a                 simple
          * linked   queue    to            hold no des whil  e th ey are wai ting         on 
                  * conditions. The   y are then       tran      sfe   rred t                o t  he qu       eue to
                * re-acquire. And        because conditions ca  n only b     e    exclusive,
             * w      e sav   e a field by us        ing special v   al    ue to        indica       t  e sh  a     red      
                     * mode.       
         */   
                  No de nextWaiter;  
           
        /**   
           * Returns     true if node    is waiting in     sh     ared mode.
         */
               final boo  lean isSh              ar      e   d()   {
                                 ret   u        rn nextWai     t    er     == SHARED;     
                }

        /**
              * Returns   previo                  us nod  e,     or throws   Nul lPoin   ter Exce       pt  io   n  if null.
                  * Use when predec            essor   cannot  be         nu     ll.  The        null c   hec     k co          uld
                                                     * be elided, bu   t        is present     to help the V    M.
                  *
             * @ re   turn the predecesso     r of this node
              *           /        
            fin            al Node pre   dece      ssor()    throw    s Nul       l  PointerException {
                          Node     p = p     rev;
             if     (p   =  = null)
                              throw new   NullPoi            nterEx    cep        tio  n();
                 else
                   return p;
           }
       
        Nod   e()   {                     //    Used to    establish      in    itial      h ead o  r SHARED marker
        }

                Node(Thread   thr  ead, Node mode)         {        // U sed   by addWaiter
                          th      is.nextWa    iter = mo  de;        
               t   his.thre    ad = thread ;
        }

                Node(Thread   thread , int w aitStatus) { /      /  Used  by Cond   ition   
                               this.wai   tStatus    =   waitS        tatus ;
                               this.thre   ad =      th  read;
          }
    }

        /*     *  
       * Head     of the wait que    ue, l      azily initia      l    ized.  Except      for     
       *                in    itialization,       it is modified    only via method s    etHe   a   d.  Note         :
                   *  If head exists, its wait Status is guaranteed no  t to b  e
            * CA    NCE   LLED.
      *   /
                   priva  te  tr                  ansi    ent volatile Node head;
 
    /*      *
     * Tail of th      e     wa    it que          ue,      lazily initia  li  zed.  Modified o            nly via
                        * method enq to add new wait node.
        *  /
       pr   ivate tran     sien  t volatile No    d   e            tail    ;      

    /**   
     *         The synchr    o   nizat  ion state.
          */
    priv ate           vola      tile long state;

           /**               
     * Ret urns the curren                t v alue of synchronizati             on state .
          * This   oper   ation      has mem ory        semantics of    a {@code vo      latile} read.
         * @return current    state v  alue
               */  
    p    rotected f      ina l      lon         g g  etS t  at  e() {
                return state;
          }   
 
          /**
     *  Se     ts   the   va   l  u      e of s       ynchronizati          on sta     te.
                 * This operation ha  s memor   y semantics of   a {@co     de vo  lati   l    e}  write.
        *    @pa            r        am newState  the new sta       t e val   ue
     */
    p ro     tected final void setSt  at                  e(long      newS      t      ate) {
            state   = newSt   a te;    
    }

                /**    
     * A  tomically sets    synch   roniza      tion state to the given u      pd        a  ted
       * val   ue if the current    s       t      ate value equa      ls   the    e  xpected val    ue.
       * This oper at         ion has memory    sema   n     ti  cs of a {@code volatile} r      e  a     d
               * and write  .
     *
     *     @param expect          t  he expect  e            d       value
                       * @    param up     dat  e    the new va             lue
             * @r   e      turn   {    @code true} if        suc   c    essfu          l. F    alse return indicates that             the actual
        *            value was    not equal to the expected   va   l     ue.
          */
     pr  o    tec  te   d fina     l boolean compareAndSetState     (lo  ng expect,   long up      da  te) {
                      // See bel ow for int      r      i  nsic s s etu    p       t   o support this
        return unsafe.com  pareAndSwapLo     ng(thi   s, stateOff  set,       exp ect,   upda      te);    
      }

    //                 Que   uing uti   lit   ies

                  /**     
                       *     The number of nanosecon        ds for which it    i  s           faster to   spi n
         * rather th      an       to use timed park. A rough e            stimate suffices
            * to imp  rove         re  spo nsiv   en  ess wit h ver        y   sh  o    r     t        timeouts.
       *   /
         stat   ic final l  o  ng   spinF    orTime out         T  hreshol d      = 1000L;

           /**
     * Inserts node     into     qu  eue,                  i            nitializing   if     necessary   .  See  p   ictu           re abov      e.
     * @param node  the    node to insert
     *    @r       et   urn node'     s      pred  e   cesso   r   
            */
      private Node enq(  fina       l Nod    e   n            ode) {  
                f         o  r    (;;   ) {
                                   N  od               e t   = tail;
               if (t == nul      l) {         // Must initi   a    liz   e
                           i f (comp      areAndSetHead(n    ew Node())     )
                           ta    i    l     =        head;
                                 } e lse { 
                 node.prev     =  t;         
                                 if     (compa reA      ndS  etTa  il(t       , n  ode)) {    
                                             t  .nex  t  = node;
                                      return   t;
                           } 
            }
         }
      }

    /   **
     *   Create      s a    nd enqu     eues n    ode for curren       t thread a     nd given   mode.
          *     
       * @para              m     mo  de   Node.EXC  LUSIVE   fo  r    exclusive, No         de.SHAR  ED fo r shared
             * @ret u  rn the ne w node
     *  /
    priva te  Node         addWai       ter(N     ode mod   e) {
        No                de   no       de = n ew Node(Thread.         cu                   rrentThread         (   ), mode);
             //              Try t  he fast        path   of enq; backup to fu ll enq on failure    
           Node            pred = tail;
               if (pred    != null) {
                          nod          e.        prev = pred;
                           if         (c   o mpar   e  And SetTail(pred    , node       )) {
                             p red.next = n          o d  e;
                             retur           n       node;
                    }
            }
                            enq      (       node);
           return node;
          }
   
    /**
     *    Se t    s head of queue     to b       e no   d e, thus dequeuing. Called only    by
             * acqui              re methods.  Also       nu   lls out unus        ed  fields         for      sake o     f GC
     * and      to suppre    ss unne             ce  ssa      ry       signals a    nd traversal         s.
       *
     *     @param node    the     node
            */
    private      v           oid s   e    tHead(Node             node   ) {
        h     ead = node;
                      n           ode.thread = null;          
                        n     ode.pre   v =     n      ull;
    }

      /**
     * Wa     kes up node    '   s  success       or, if one exists.   
     *
     * @p aram no de the     node
      * /
                              private void          unparkSuccessor(No       de node)             {
          /*
                 *    If     status is    neg  ative (i.e.    ,     possibly       ne           eding signal)     try
                * to clear in  antici     pati on of    sig  nalling.  It         is OK                   if this
         * fails      or if               statu  s   i    s      chang       ed by waiting thread.
             */
                  int w           s =   node.waitSt    at    us                             ;
         if    (w        s <        0 )
              compareAndSetWaitStatus(node  , ws, 0);

                      /*
           * T hr   ead to unpa   rk is hel  d  i n su  c                           cessor,  which is              normally
         * just the next   node.       But if cancelled or    apparen   tly n    u ll,
         * trav     e  rse         b     ackwards from     tai l to f   ind the actual
                                      * non-cancelle   d   suc     c                    essor.
         */
        Node   s = node.ne     x   t;
          if (s ==   n                  ull ||    s .wa     i   tSta        tus >          0) {
                s = null;
                         fo   r     (Node t = tail; t        != null &&              t      != node; t = t.prev)
                        if (t.waitSta      tu               s      <= 0)
                                        s =    t;
                     }                 
        if     (    s != null  )  
                   LockSupp ort.unpark      (s.thread   );
           }

             /**
              * R                  elease action for shared mode -   - signals su   ccessor and       ens    ure     s
      * propagation. (N     ot   e: Fo      r exclus   ive m    ode   , re lea      se      ju  st                    amounts   
     * to   call   ing unparkSucc         essor of he                          ad if         it needs  signal.)
     */
    privat                     e   void doReleaseS  hared()   {
           /*
           * Ensu              re that   a release pro     pa    g      ates, e     ven if th      e     re are  other
            * in-prog ress a               cqu   ires/re  lease        s.  Thi s pr          oceeds           in the usual
                      * way     of try      in         g    t  o unparkSuccessor of head if it needs
         * signal. But if it do           e   s not    , s    t atus i  s se t to PROPA     GATE to
         * ensur e that   u   p  o     n         rele     ase,      propaga  t    ion c  ontin  ue s .   
                                         * Additionally, w    e    must    lo    op in    c  ase       a new no de is added
                        * while w         e a                              re doin       g      this. Also    , u         n   li     ke other us    es of
                             * unp   arkSuccessor,         we need to kn ow i         f                   CAS to    reset           s  tatu   s          
                      * fails,   if so r  echecking.      
                                         */     
                         for (;;) {      
              N          od e h = head;
                             i   f (h != nu  ll &     & h != tai l) {
                             i        nt ws = h.waitStat   us       ;
                                        if      (w    s  == Node.SIGNA L) {
                                                     if (   !      compar eAndSetWait  S  tatu    s(h,  No        de.SI      GNA  L,   0))
                                 continue;               // loop to reche     ck case     s  
                                      unparkSuccess   o       r            (h);
                        }
                 else if (   ws ==          0 &&
                                                    !compare  AndS     etWa   i   tSta    tus(h, 0    , Node.  PROPAG     ATE        ))
                                                        c    ontinue     ;                  //       l    oop on      failed C          AS
                       }    
                 if      (h == h  e ad)                          // lo   op if          head              changed
                   bre  ak;
             }
                  }

    /*     *
     * Set s head  of queue     , a    nd checks if successor        may   be wa   it ing
     * i   n sh     a red  m     ode,         if  so pr    o   pagating if  ei  ther             propagate > 0      or
           *             PR      OPAGATE st       atu       s          was                set.
     *
     * @param   nod e th e node  
                 *        @pa    ram propagate the return value f rom   a tryAcquire      Sha red
           */
      priv  a   te   vo    id setHeadA   ndPro    pag     ate     (Node node, long pr               opa   g ate) {
                  Node h =    head; // Re    cord old  he ad for check      bel               o  w
            setHead      (          no de);
            /*
         * T    ry t       o signal next qu   eued n o       de if:
             *      Propagation wa    s ind      icated by cal   l er    ,
            *          o  r   wa  s recorded (as    h  .    waitStatus e    i      ther bef     ore
                             *     or after          setHea d             )      b  y a prev    ious ope                   ratio    n
          *          (    note: th   is     use  s  sign-check of waitS        tatus because
           *        PROPAGA      T  E s tatu  s may           tran      sition to      S   IG   NAL.)   
             *        and    
                                   *     Th e    next nod                   e   is     waiting in shared m      ode,
                 *     o r      we don   't k   n  ow,    because it appears null
             *
                              * The con     servatism i  n both   of th    ese checks may cause           
           * unn   ece   s sary wake-ups, but only w      h  en  th    ere ar      e mul   tiple
                  *      racing acquires       /releases,        so     mo     st    nee       d signa          ls  now or soon
              *      anyway.
                                  */
        if (propag    ate > 0 || h == null   |  |   h     .wa     itSta  tu   s <  0  |                       |   
                 (       h = hea  d) == null ||     h.        wait   Status        < 0) {
                             Node s        = nod     e.next;
              if (        s        == null       || s.isSh  ared())
                                         d    oR         elea seS ha red();
                }
              }

         // Utilit   i                es        for    various versions    of acquire

    /** 
       * Cancels an     o  ngoing attem       pt   to ac       quire.
     * 
          * @param node   the         node
                             * /
       priva    te      void can   celAc              quire(No           de n  ode)  {
        // Ign    ore if    nod       e doesn't    exis t
          if (node          == null)
                        retur  n;

            node.thread = null;

        // Skip ca   ncelle      d   predec         essors
              Nod     e pred = n           ode.  pre     v     ;
        whil    e (pre  d.w  aitStatus >              0)   
                     n  ode.prev   = pr ed   = pred.prev;

                  //   pre  dNe xt is the            apparen      t node       to      un   splice. C  ASe     s      belo  w w ill  
                      // fail   if not, i   n which case, we lost    rac  e vs anoth  er          cancel
                // or signal, s   o no f  urt  her action        is ne  ces    sary. 
             Node predNext = pre     d.ne  x t;

               // Ca    n use unconditiona          l wr ite ins   te  ad of     CAS   here.
                  //   Afte     r this          atomi     c s    tep   , o      ther       N  odes c   an skip past us.
        // B    ef     o re, we are     fr     ee of interfe  re        nce from ot     her threads.         
          node.waitSta  tu  s  =  No   de.CANCELL         ED;

        // If we     are    the tail, remove ourselv     es.
                          i    f (node          ==     tail &&          compareAn  dSetTai    l(node,         pred))   {
            compareAndSet       Ne xt(pred, pred         N  ext, null);     
               } else {
                   // I               f suc cessor      n     eeds       s    ig      nal    , try to set pre d's next-link             
                      // so it    wi       ll                        get one.    Othe       r    wise wake it up to propagate.
                   in    t ws           ;
                             if    (pred !=     head     &&
                          ((ws = p         red.wai                   tStatus) == Node  .SIGN    AL  ||
                 (  ws <=  0      &&   c   omp     areAndSe     t    W    aitStatus(p red  , ws,       N       ode.SIGNAL))) &&
                      pr   ed.thread !=     nu     ll        )  {
                                 No              de next = node         .nex   t;
                                       i     f   (next           != null && next    .waitStatus <= 0)
                       compare       A  ndSetNe  xt          (           pred,   pred  Next      ,    next);                  
                 } else     {
                            unparkSu ccessor(   node);  
                    }

              node.n ext  = no  d  e; // he lp GC
            }
         }

        /**
     * Chec        k   s and u       pdates        sta    tus fo  r       a node    that failed to       acquire.
     * Ret   urn s tr   ue if     thread should bl       ock   . This is                th  e      main signal
        *   cont  rol     i        n     al         l acquir   e loops.  Requ         ires that pre d ==       no de.pre         v.
      *
          * @param p     re    d  node's p   redec          essor hol  ding    statu   s
       *    @pa   ram node the   node
          * @re  t    urn {@co de          true} if   th   read       shoul  d block
                         */
    priv   at  e sta   tic            bo   ol   ea  n                     shouldPar     kAfterFailedAc   qu   ire(Nod e   pred ,         Node no            d   e) {
                   int ws             = pred.        waitStatus; 
          if    (w         s   ==    Node.SI      G       NAL)
            /*
              *    This  node has already set        status asking a release
                            * to s ignal it,    so it       ca  n safel y      park.  
                           */
                  return tr           ue;        
        if (ws       > 0) {
                /*
             *        Pred       ecessor wa          s canc     elled. Skip ove     r pr      e     de      ce   ssors and
                  * indic    a te r   etr   y.    
                                              *             /
                         do {
                   n          ode.prev =   pr  ed = pred    .prev;  
            }             w  hile (pred.   waitStat    us > 0);
                    pred.n                    ext = no  de;
           } else   {
                     /*
                             *              waitStatu           s m   ust    be 0 or    P    ROPA  GATE.  Indica      te that   w e
                    * need a sig      nal,  but    don't    park    yet.  Caller will    need to
                   *       r  etry to  ma     ke sure         it ca   nnot acq              ui re befo    re parking  .
                                        */ 
            compare       A         n    dSetW  aitStat   us(pred, ws,     Node.SIGNA     L);
                     }
              ret     urn false;
    }
   
    /**
     * Con            venience method  to interrupt   cu  rr   ent thre ad.
     */
     static void selfInt      e     rrupt   () {
         Thr    ead.current     Thread()               .i    nterr  up           t      ();
    }

                /      *   *
          * Conven      ience metho            d to park and then    check if inte         rrupted  
     *
     *      @ret    u   rn {@   cod       e tr      u         e} if in    te      r    ru   p       ted
     */
    priva  te final boo         lea      n parkA   ndC   heckInter  rupt() {
              LockSuppor  t.p        ark(      th    i   s     )     ;
             retu   rn Thread.inter  rupted();
    }          
       
       /*
             * V            ari   ou    s     flavors of acquire, v     aryi ng in e         xclusive/sh          ared and
     *  control modes.  Ea            ch is mostly the s                                 a  m   e    , b ut annoy     ingl     y 
     * different.  Only a lit   tle bit of facto    r  ing is  possible due   to
           * intera        ctions of e         xce   ption mechanics (   inclu    ding e                  nsuring      that we
     *      cance   l  if t    ry Ac    quire       throws         exceptio  n)    and other    control, at
           * least n    ot wi     t        h out   hur tin    g perform                a       nce   too much     .
     */
   
    /**
                 * Acquires in exclusive uni nterr    uptibl   e    mod e f  or threa    d alrea   dy      i     n
            * queue.   Used by cond itio   n wait methods as wel   l       as acqu         ire.
     *      
      * @   param node                    the node
     * @param arg the     acqu   ire ar    gume     nt
     *                     @return        {    @code      true} if int             erru   pted     whil         e waiting  
          */
    fina  l bo     olea     n acquireQueued(final Node node, long arg) {
                          bool      ea    n f              aile    d          = t   r      ue   ; 
              try {   
                      boolean interrupted = false;
             for (;;) {
                        final   Node p = node.predecessor( );     
                              if (p   ==     head    && tr         yAcquire(arg)) {
                                                            setH  ead(node);    
                                 p.next = null; // help GC
                    failed = false;
                           re   turn    in       ter       rupted;   
                                            }     
                            if (            shouldParkAfterF   ailedAcqu     ire(p, node) &&
                                parkAndChe    ckI nt  erru    pt       ())
                                      interrupted = true;     
                   }
            }   finally {  
                        if (failed    )
                         cancelAcqui   r        e(no de)   ;       
                  }
              }
    
            /**
          * Acqui   res    in e xc  lusive    in      terruptib  le mode.
                    * @                 par   am arg the acqu        ire a rgument
         */   
      priv  at  e void do      AcquireInterruptibly(lon g a rg)
         throws Inter          r   uptedE                   xcepti  on {
                 final   Node node = addWaiter(Nod         e.EXCLUSIVE);   
        boolean         fa iled =          true;  
                 try {
                   for (;;) {
                       final Node p = n                         ode       . predecess   o r();        
                                                          if (p ==     he ad      && tryAc  quire(arg))            {
                                          setHead( n       ode);    
                      p            . ne      xt = null; //      he  lp GC
                           fai                   le    d = fals    e                      ;
                                     r        eturn;
                            }
                        if (s        houldPark  AfterFa  iledAcquire(p, node) &&
                                         parkAndCheckInterrupt())      
                                  th     row new   Interrup        tedException      ();
                        }
              }    fina   lly   {
                    i   f  (failed)    
                                cancelAcquir       e( no  d      e);
           }
         }

    /    **
     * Acquires in exclus   ive timed    mod      e.
      *
      *   @pa    ram       arg      the       ac  qu    i         re ar gume    nt
             * @para     m na  nosTimeou  t max wait time
     * @retur   n {@cod    e tr      ue} if          acqu ired
     */
    private b oolean     doAcquireNanos(lo  n    g a   rg, lon          g nanosTime   out     )      
               thr      ows Interrupte   d     Ex     ception {   
        if (n    anosTimeou   t <=          0L)
            return false;
          final long dead  l       ine      = Syst em.nano    Ti     me(     )   + nano   sT  imeout;
              final Node node = addW          aiter   (Node  .EXC  L  USIVE);
          b   ool   ea           n failed    = true;
               try       {
             for ( ;;)    {
                      fina           l Node p =          nod     e .p    re  d     ecessor();
                          i  f (p ==     he       ad && tr     yA  c      quire(arg))    {
                                      setHe ad(n      od    e);   
                         p     .next =      null; /    /      help GC
                           fai       led = false;
                             retu  rn true;
                                   }
                    nanosTimeout  = d  eadline - System.n   anoTime();
                           if (nan osTimeout <= 0L)
                                 re turn fa     lse;
                 if (shoul dPar      kAfterFail  ed     Acquir     e(p    , nod          e) &&
                          nanosT        imeout      > spinForTimeoutThreshold)
                         Loc   kSup     port            .parkNanos(this,        nan  osTi   meo    ut);
                 if (Thread.interrupted())
                     thro       w new     InterruptedEx              ception ();    
                }
         }    fina   lly {
                         if (  fai    l  ed)
                     cancelAcqu   ire(node)  ;
                             }
    }

    /**
          * Acquires in shared uninterru ptib  le mode.
     * @para                  m arg the acqu ire argumen t
     */
            p        rivat    e voi    d      d  oAcqu     ireShared(long ar      g) {
         final       Node no    d   e = ad dWait  er(Node  .SHARED);
        boolean fai le d = true;   
        tr    y {
                                             boo      lean inter           rupted      = fa  lse;
                f or (;;) {
                       fi    nal Node   p = n             ode.p  redeces      sor()          ;
                      if (p == h         ea     d)    {
                                                               long r = tr     yAcquireSha     red(arg);
                     if (r >    = 0) {
                        se         t            HeadAndPropagate(n    o           d e, r);
                              p.next   = null; // help    GC
                                   if  ( in   t  errupted)
                                                        se   lfInterr upt();
                                 failed  = f   alse;
                              re     turn          ;
                      }
                      }
                   if (shouldPa   rkAfte        rFailedAcqu  ire(       p    ,     nod  e) &&
                                  parkAndCheckI   nte       rrupt())
                         inte                rrup     ted = true;
                 }
        } fin    ally {
                                 if (failed)
                cancelAcquir       e(nod             e   );
               }
    }

    /**
     *    A c     quir    e   s in s    h              ared in   terruptibl        e mode .
                      * @pa   r     am a   rg th          e        acq    uire   argum   ent
     */
    pr            ivate voi     d doA  cquir   eSharedInterru    ptibly(l     ong     ar   g)
          throws Interru        pte   dException {
           final Node node = addWaiter(   Node.   SHARED);
                  boolean        failed = true;   
           try {
            for (;;   ) {
                      final             Node p =    node     .predecessor();
                if (p == hea     d)         {
                             long r    = try     AcquireSh      are      d(  a   rg);
                            if    (r       >     = 0) {
                                set    HeadA      ndProp  agate(node   ,                r);
                             p.ne         xt = n    ull    ; // help GC
                              failed  = false;
                                return;
                    }
                }     
                        if (shouldPark     After   FailedAcquire  (p, n       ode) &&
                               parkAndCheckInterr        upt())
                                      throw new  InterruptedEx ception();
                          }
              } fi        nally {
                         if (failed)
                              cancelAcquire(    node);
           }
    }
   
    /**
     * Acqui  res in sh   a    red timed  mode.
                  *
       * @pa   ra  m       arg the    acquire argument
     * @ param nan      osTim  eou   t m  ax wait t  ime
     * @return {@   c  od          e        t         rue}     if acquired
      */
    private boolea    n doA   cqui      reSharedNanos(long ar g, lo       n g nanosTimeout)
            throws Interrup         te  dE   xce pt  ion {
             if (na      n       o      s  Timeout <= 0L)
                         return fa   l   se;
        fin   a          l    l ong d e      adline   =  System.n   ano     Time()   +       n    an       osTimeo   u  t;
           final Node   node   = ad    d    Waite       r (Nod  e.S  HARED);
               boolean faile     d =    true;
           try {   
            for (;     ;)    {
                           f inal No         de p = node.predecessor();
                    if  (p == h      ead   )        { 
                              long r = tryAcquireShared (arg     );
                               if   (r >=     0) {     
                        setHeadAnd      Propagate(no   de, r);
                                         p.next = null; /    / help GC
                                                   failed     = false;
                              retu       rn true;
                                        }    
                      }
                       nanosTimeout      =  dead                 line - System.nanoTime()  ;
                               if     (          nanosTimeout   <= 0L)
                                                        return f   als         e;
                       if    (s    houldPar kAft   e rFai  ledAcquire(p  , no d               e) &&
                    na      nosTimeout >    sp inForT     imeoutThre    s h     old)
                          Lo   ckSupport  .parkNan  os(t     h               is, nan os       Tim e   o ut);
                  if (Thread.interrupted())
                                  throw new         Inter   rupt     edExcept    ion( );
                                           }
              }    final      ly {
                   if (   failed)
                     cancelAcq ui         re(node);
               }
    }
       
     //    Main exported  methods

         /**
      * A   tte               mpts to acquire             in e       x    cl   u  sive m ode. This m      ethod s     h oul    d query
      * if the stat  e o  f the objec t permit   s it to be ac        quired in the
     *   ex   clusi    ve mod      e, an   d if so to a    cqui        re it.
           *
         * <p  >This method is  always inv oked by           the thread      perfo            rm           in           g
         * acqu ire.      If     this method r   e  ports                       fai lure, the a       cq   uire metho       d
     *    may    q   ueue the t          hread        ,        if i       t is  not alrea   dy qu  eue   d, until it is
           *   signalled by a release from some other    thread. This can be used
                * to implement me      thod {@link Lock   #tr   yLock(               )}.
          *
       * <p>Th    e    defa ult
           * imp  leme     ntation  thro       ws {@lin k Unsupport  edOperationException        }.
     *
          *     @param a   rg        the ac   quire argum   ent. Th     is value is        alway     s the o ne
                 *        passed to an acqui     re    method,    or is the v al u     e saved on entry
             *                              to a        co   ndition wait.  The   val  u       e i  s otherw         ise u ninter           preted  
     *             a nd can  repres    ent anythi       ng you l  ike.
     * @ret   urn {@code true} if successful. Upon success, this object has
          *         been acquire  d.
          * @throws Il   le   ga   lMonitorStateE   xcepti on        if      acqui    ri      ng w ould place thi  s
     *         synch   ronizer in an ille  gal sta  te    .   This excepti             on must      be    
       *         thrown in a consistent f         ashion  for synchroniza         tion to  wo       rk
                  *                      correctly.
                  * @t   hr   ows Un    sup        portedOperationExcept               ion i  f exclus      ive mode i   s no    t supp    orted   
     */
    protected boolean          tryA   cquire(   long arg) {
          throw  n   ew Unsuppor   tedOperationExce       pti        on     ();      
    }

             /**
     * Attem pts to set t  he  state    to reflect a     rele ase in ex  clusi        ve
     * mode.
        *
     * <p>This m   ethod is al  way  s invoked by th    e    thread performing   release.
     *
     * <p>T  he default   implementatio     n throws
     * {@ lin      k UnsupportedOperationExcep   tio  n}             .
        *
            * @par      am a   rg th e release argument. This value is always the one
           *            passe   d to  a release method, or t   he cu    rrent st  ate    v        a lue upo  n
     *           entry      to a c ond     itio   n wait.   The           value is ot  herwise
        *          uninterpr eted an  d can re present anything you li   ke.
       * @r    etu    rn {@cod   e true        } i       f   this object is now in a fully re   leased
                           *               s    t   ate, so that an y waiting th            reads may              attempt    to      ac        q     uire;
      *         and {@c    ode false} othe r   w            ise.
     *     @th    rows     I   lle  g alMonit           orSta   teE     xception if    releasing wo     ul     d pla   ce t  his
        *             synchroni        zer in an illegal state.      T   his excepti   on must b e
        *         thrown in a c     onsis  tent fas  hion for synchronizat  ion to w   or    k
     *                       c  or   rect      ly.
       *    @thro ws Uns    u  p portedOperationException if exclu   s  iv  e mo    de is    not  supported
     */
           protec t  ed b  oolean tryRele      as e(long arg   ) {   
                throw new UnsupportedOperationE      xcepti      o    n();
    }

    /**
       * Attempts to acqu         ire i       n     shared mo  de     .   This        method          should query if
       * th    e   st    a     te of t he o    bjec     t permits i     t to be acquired in the shared
     *              mode, and if     so     to acqui   re it   .
     *
          * <  p   >This me    thod is always invo ked by      the thr       ead per   forming
     * acquire.  If this method repor t   s failure, the acqui        re method
           * ma y    queue    the thread, if i       t is not     already queu    e         d    , until it is
          *      si gnalled      by    a release from some othe          r           thread.
     *
                      * <p>The d efault implement    ation throws {@link
     * UnsupportedOperationException}.
       *
     * @ param arg   the acqui  re     argum ent.      This value    i      s  a    lway    s    the one
      *        p     assed to a  n acqui  re method, or is  the        value save d   on entry
         *        to a condition wait  .  T   he v alue is oth     erwise uninter  preted
     *               and can r  ep  resent anyth ing you      like.
               * @return a    nega     tiv   e value on failure; zer   o    if acqui      sit     ion in s    hared
       *                 mode succeeded but no subsequent shared-mode acquire ca     n    
                  *                  s   ucceed;           and a p    ositiv e value if acquisitio   n i n sha    r   ed    
            *           m o      de succeeded a     nd       subse      que nt    share         d-mode    acqui res might
      *         als      o    succe ed, in        which cas   e a subseq   uent   wai ting thread
     *            must   chec   k a  vai  lability. (Support for three di    fferent
     *             retur    n values enab  les th   i   s me         thod     to    be used in contexts
     *             where acquires only sometimes act exc lusivel             y  .)     Upon
     *                       succes s         , thi      s object has been   acquired.
             * @thro ws IllegalMonitorState Exc eption  if ac     quiring    w      ould place this
       *           synchr    onizer in a  n illegal  st ate. Th is excep       tio      n    must be
     *              th   r  own in a c          onsist    ent   fashion for synchroniza      tio n to work
                      *               cor  rectl    y.
        * @      throws U       nsup   por         t   edOpe             rationException     if shar   ed mode is not s  upporte     d
         */
      protected        long tryAc     quireShared(long     arg)      {
        throw n  ew Un  support  edO    perationException();
    }

      /  **
     * Attempts   to set         the st     ate  to reflect a re le   ase in   shared mode.
     *
               * <p>This method is always    inv      o           ked by the    thread performing     release   .
              *
      *   <    p>The default   implementation thr    ows
     * {        @link Unsuppor  t  edOper      a   tionExcepti         o   n}.
         *
     * @pa     ram a    rg  the re      lease argument. This va    lue is al ways the one   
           *        p     assed to    a release metho    d,     or the current state     value upon
     *             en try     to a co   ndition    wait.  The   value        is ot he    rwise
         *                     unint     e  rprete          d and    can represe nt    anything you li ke.
     *  @return  {@code true} if this release  of       sha  red m    ode may p ermit          a   
          *           waitin  g acquire (        sh       ared     or exc              lusive) to suc      ceed; and
     *                  {@code false}        otherwise
      * @throws Illeg     alMonitorState Except     ion if                releasing woul  d place          th       is
           *            syn   chr  on   i               zer  in an illegal st      a  te . This exception must be
         *         thro  wn in a co          nsist   ent f        a    shion       for s       ynchro  n      ization to w   ork   
        *            corr ec     tly.
     *       @throws Unsup  portedOp  erationException if        shared m   ode is not support  ed
         */
        protected      b       oole     an tr   yReleas    eShared(lon   g arg) { 
                throw n       ew       Unsup portedO    perati onEx         cepti on();
       }

          /  **
        *   Returns        {@code true}           if s       ynchroni    zati    on   is   held exclusively with
     *     respect to  the curre                 n t (calling) th  rea       d.  Th      is    m  et  hod is in  voked
         * upon e             ach ca    ll to a         non-           waiting   {@link ConditionOb         j ect        } method.
     * (Wai              ting m    ethods instead invoke {@link #r  elease}.     )
     *
        * <p>The default impleme    ntati     on      t  hrows {@link
       * U    nsupportedOp              erationException}.           This   method is i    nvoked
            * internally only   within     {@li  n    k      Con    ditionObject} me  thods,          so need
        * not               be defin   ed         i  f conditions are not u se  d     .
     *
                    * @ret    urn {@cod    e tru     e}   if        synchronization is he    l d e     x     clus     iv   ely;
          *          {@c                  o  de false}            otherwise
     * @throws UnsupportedO   perationException if condit    ions are not su        pported
     */
               protect   ed boolea            n isHe l dExcl     usively() {
        th row     new Unsuppor    tedOperati        onException         ();
    }

        /**
      * Acqu   ires   i    n exclus    ive mode, ign    or  ing interru     pts      .  Imp   lemented
          * by invoking at       least once {@link #tryAcqui         r                 e},
     * ret urni    ng on success.    Otherw   ise     the    thread is queued, possib    ly
     *    re    p        eatedly   b    lo       cking and unbl  ocking, i    nvoking {@link                           
     *  #t    ryAcqu        ire} un    til success.            This m   eth    o    d     c   an be u      se    d
          * to i   mple    m ent method {@link Lock  #lock}.
              *
             * @p            aram arg    the acquire ar    gument.          This   value is conv    eye   d to
        *        {@li nk #tryA     cquire}       but i         s other         wise   uninterprete  d an  d      
                 *        c          an    represent a nything   you like     .
     */
        public final voi   d acquire(long           a       rg) {    
                  if (    !tryA             cquire(arg) &&
                acquireQu      eued(addWaiter(Nod e.E    XCLUSIV        E), arg))
                     selfInterr    upt();
    }

    /**
     * Acq uires in exclusive mode, ab          orting if inter    rupted.
     * Impleme  n   te   d b  y firs  t   checki  ng interrupt status, then invoking
     *         at le   ast onc   e {@link #tryAcquire},    returni   n    g on
         * succ     ess.  Otherw    ise    the   thread    is   queued, pos   sib        ly repeatedly
     * blocki  n   g and unblocking,    invoking {@link #tryAcqui     re}
                 * until success o    r the thr    ead    is       interrup   ted  .  T    his method c   an  be
           * used to im   pl  e  ment m        ethod              {@link Lock#lo     ckI nterr   uptibly}.
            *
        * @param   arg the acqu    ir e argument.     This value is conveyed to
     *        {@link #tryAcquire}    but is othe    rwise u         ninterpr   e   ted and
     *        can represe           nt any   thing you like  .
           * @th    rows Int            errupte   dExc  ept   ion if the c u    rrent thread is interrupted
     */
        pub           li     c fin  al          void acqui     reInterr   uptibly     (long       arg)
            throws Int     e         rr     uptedException {
        if (Th  r   ead.in     terrup  ted())
                                  thr   ow new Interrupte    dExcept   i      on();
        if (!  try    A c   quire(arg))
                do Acq  uireInte     r    rupti   bly(ar           g);
    }

           /**   
     * At   t   empts to acquire in e  xclusive    mode, abor     ting if int  erru    pted,
            * and failing if   the given timeout  elapses          .    I mplemented by    first
     * che    cking in      terrupt s      tatu        s ,   then i    nvokin g at least once {             @l        ink
     * #tryAcquire}, ret                urning on succes  s.  Otherw   ise,  the      thread is
       * que    ued, possib   ly repeat   edly blocki         ng and unblo     cking, invoking
             * {@  lin      k           #tryAcquire} un    t  il success or t         he    thre      a d is            int er     rupted
     *   or the  time  ou  t el   aps  es.  This me thod can be used to impleme  nt       
                  * meth od    {@l    in   k Lock#   tryLock(long,    TimeUnit   )}.
          * 
     * @param        ar   g the   acquire a  rgum          ent.    This    value   i  s conveyed to
         *              {@lin   k     #tr yAcqu          ire} but is ot       he         r    wise uninterp  reted  and
     *        can repr    esent    anything     y  ou like.
         *       @param nano   sTim    eout the maximum  numbe   r of  nano seco  n          ds to wai    t
         * @ret    urn {@code true   } if ac     quired;   {@code  false} if timed out  
     * @       th       r  ows In   terr               uptedExcept             ion if   the current    thre       ad is i    nterrupted
     */  
       public final bo olean tryAcquire Nanos(lo  n   g arg,  long nanosTimeout)
                  throws Inte       rr  upt  edE    xceptio  n {
                         if (Thread.         i    nterrupted())
                    t     hrow new      Interrupte              dExceptio       n();
        return tryAcquire(arg  ) ||
                   doAcqu    ireNanos(arg,    na         n   o  sTimeo    ut);
    }   

    /**      
                   * Releases in ex     cl         us  i  ve    mod e.  Imp     lemen     te  d by unblocking on   e  or
     *     more threads if {   @link #tr  yR  elease}   returns true.
       *     This met           hod can b     e used to implement       me   thod {@      link Lo          ck#un     lock}.
     *
                         * @par  am arg the release        argumen  t.         This value i s c   onveyed     to
              *        {@link #t   ryRelease   } but is otherwise uninterpreted and
     *               c   an re  pres     en   t anything      you l     ike.
     * @retu rn the va             lue returned from {@    lin   k #tryRelease}  
     */
    p   ubli     c    final boolean release(long arg)  {
                   if (tryR    eleas     e(arg)) {
                  Node h = head;
            if (h !=     n   u ll &&      h        .wa      itStatus !=        0)
                     unpa  rkSu    ccesso  r(h);
              return             true;     
           }
            re        tur           n fals e;
      }

           /**     
      *       Acqu   ire     s in       sh    ared mod e  ,   ign   orin       g interr     upt s.  Imple     men    ted   by
     * first in   voking at         least once {@link # tryAcquireSh   ared},
     *      re  tur   ning o  n        success.  Otherw        ise the threa  d is    queued, possibly
     *    repeatedly   b    locking and unblockin  g, i  nv  oking {@link    
       * #t ryAcquireShared   } until    success.
           *
     * @pa      ram a   rg   th  e acquire argument.  This value is convey  ed   t                          o
     *        {@link #tryAcq  uir eS        hared } but is     otherw  i se uninterpreted
     *           and  ca  n repres      ent     anythin    g you like  . 
     */
    public final   void acq   ui      reShared(long arg) {
           if (tryA  cquireShared(ar g) < 0)
                              doAcquireSh  ared(arg);
    }

    /     **
           * Ac       qui  res i     n share      d             mode, abortin     g if in    ter   ru    pted.  I   mplemented   
     *     by       first check  ing interr    upt st      a      tu  s, t      hen invok   ing at least o nce
              * {@link #tryAcquir  eShared}, returnin g on su        ccess.  O   therwi    se the
     * thre   a    d is queued, p    os   sibly r  epeatedly    blo cking an    d unblock ing,
        *      invokin       g {@link #tryAcquireSh ared} until success or the th read
     *      i   s interrupt     ed.
            * @param      arg the acquire ar   gument.
        * This value is conveyed     to    {@link #tryAcqu     ireShared}    b  ut is
     * otherwise u   nin   terpreted and can re present           anyth      ing
     * y    ou like    .
     * @throws   Interrup   te      dEx   ception if the  curr    ent thr   ead  is interrupted
          */
    publi   c final void acquireSha  re   dInterruptibly(long arg)
                th                       rows    Interr   upted  Excepti   on {      
              if (Thre ad.inte   rrupted())
               throw             new Int    errupted E xc       ep      tion();
        if (        try  AcquireSh  ared(a       rg ) < 0)
            doAcquireSharedInte   rruptibly (ar g         );
    }

                               /* *
                  * Attempts  to ac  quire in share  d mode,          a     borting if interrupted, and
     *         f  ailing     if the given tim eout elapses.  I  mp    lem    ented   by f          irst
     * chec     king      in       terr   upt stat  us,   the    n invoking at        lea st    once {@  link
            * #tryAc     quireShared},            r  etu   rni          ng on  suc cess.  Otherwi        se,      t   h  e
     * thread is que  ued, poss  ibly repeat      edly blocking and unblocking,                                  
     * invokin         g {@link #tryAcquireShared} until s    uccess or the thread
               *      is      interrupted or the time out elapses    .
       *
     * @      par           am arg    the acqu ire argument.      This value is conveyed to
              *          {@link    #tryAcqui    r eShared} but is otherwise uninterp     ret    ed
            *        an d        can r   ep  resent an y  thing               you like. 
       *     @p   a ra   m nanosTimeout   the m       axim   um num be    r       of nanoseconds   to wa      it
                  * @re       tu    rn    {@   code true     } if acqu     ired; {@code f    alse} if  timed     o   ut
                 * @t  hro w  s Interru                  pte     dEx cep  tion if t     he current thre      ad   i     s int        errupte    d
     */
            public final b   oolean         tryAc      quireS    hared      Nanos(l  ong ar  g,    long nano    sTimeo            ut)
            throws Inter       ruptedExcepti    o   n {    
        if       (Thread.in t    errupte   d())
               throw new Int     er rupt      e       dE      x cept    ion()      ;
                 return tr       yAcq   uireShared  (a      r   g) >=     0 ||
              doAcquireSharedN        anos(arg, nanosTim  e       out);
     }

    /**
         *     Re   lease                 s in shared mode.  Imp       leme   nte      d by unbloc  k  ing on  e or more
     * th      reads if {@  li    nk #tryRel  ease   Shared} r              etur           ns  tr ue.
              *
         * @param ar   g     the release ar   gumen           t.  This va   lue is  co       nveye d t    o
            *                    {@li      nk #     tryRele   aseSh   are    d} but is otherwi se uninterpreted
       *        and c        an r epr            esent anyt hing  you lik  e.
           * @ret        urn t   h  e value re  t  ur   ned from {@link #tryReleaseS   ha      red         }
     */
        pu   blic fina l boo     le     an rel       eas    eShared(long   ar            g) {
            if (tryRele              ase  S   hared(arg    )) {
               doReleaseShared();
               return true;
        }  
        return false;
    }
 
    //     Queue ins    pe   ction met       h    ods

    /**   
     * Queries         whe     ther   a         ny threads are wa iting to acquire. Note that
         * because ca   ncellations du    e        to                   in  te           rrupts a    nd timeouts         may o           cc     ur
     *      a   t  an  y    time, a {@code t     r  ue}     return do es       not g     uaran     tee that       any
     *     other thre   ad will      e         ver       acquire. 
     *
           *    <p        >In this implem     en             tation  , this operation       returns i n
       *       consta        nt time.
                 *
           *     @return {@           code true} if there may be   other th       reads wa iting to acquire
     *    /
     public     final boolean hasQueued   T  hreads() {
         return head != tail;
     }

          /**
                   *   Queries w      he   ther     a  n      y t hreads ha                       ve     ever contended to acq     u ire this
     * synch     ronizer;    that   is if an    acquire meth  od has eve         r blocke  d  .
       *
            * <p>In           th      is im   plem   entati  on,            this oper  ation         ret  urns in
     * constant      time.
     *
     * @  r    etur  n               {@code true}            if th   ere has ever been cont      ention
       */     
     pub  lic final       boolea n hasContended()       {
             re  turn head != null;
      }

       /**
        * Retu     rns the    fir       st (longest-wai    ting) th          rea   d in the queue, or        
     * {      @code nu  ll} i f no   thre  ads are currently     que         ued.
       *
     *  <p    >In thi       s i        mplem    entatio n,     thi    s op      era tion  normally r      et       urns in
     *    constant time,       bu     t m  ay  iter      ate     upo    n contenti    on if other     threads are
     * co   ncu   rren  t      l  y m    odifying the queue.
         * 
      *  @ret    urn    the  first (     longest-wait  ing    ) thread in the que   u  e       ,  or
                           *          {@code   null} i    f   no           th  r eads are currently queued
      */
           pub   lic final Thread  getF    irs  tQue       uedThrea      d() {    
          // handle    only        fa   st     path, e lse re la    y
          re      turn (head ==       ta    il) ? null :  f ullGetF ir  stQ     ueuedT        hrea d();
           }
    
       /*  *
     * Version of  getFirstQueued  Thr   ead c a   lled  wh  en fastpath fa     ils
         */   
      priva    t     e T hread   fullGetFirstQueuedThrea  d() {     
                       /     *
                *  The       fi         rst node    is normal   ly      h      ead.next. Try t  o get its
                * thread fi     eld ,    ensurin    g co     nsistent re            a          d       s: If thread
             * field is    nulled out or s.pr ev is n     o        l   onger he     ad, then
              * some oth   er t      hr    ead(s) concur   ren   tly per   f  orme  d setHead in
                  * b        etwee      n s      ome of our read  s. We      try this twice before
             * resorti  n  g    to traversa  l.
              */
              Node h,    s;
                T         hread     st;
              if (((h = head)  != null       && (s = h.next) != null &&
                     s.pre       v ==  h  ea   d         && (st = s.t  hread) != nul   l) | |
                ((     h = head) !=  null && (    s        = h.next)     !=           null &&
             s.prev ==   head    &&   (st = s   .thre    ad) != nu  ll))
                   retu rn st       ;

        /*
            * Head's next           fiel  d mig    ht     not have  been       set yet,    or may       h       ave
              * been unset after s        etHead. So we must      check to see if tail
                     * is actu all y f    ir     st node  . I f not       , w        e continue on   ,            saf  ely   
            *    traver  sing fr      om   tail back to head to   fin d first,
               * guaranteein g termination.
         */

                       N          ode t =  ta il;
             T   hre    ad firstThread = null;
        w    hile (t    !=     null && t     != hea     d) {
                              Thr      ead tt = t.thread;
                                if (tt != null)
                                    fir             s         t         Thre ad       = tt;
                t = t      . prev;
           }
          return     firstT           hread;
    }

    /**
     * Returns true if the given thread is   currently qu    eued.
               *      
     * <p>This implement  ation     tra    ver ses the que    ue to determine
     *            pres        ence of the giv    en thread.
                 *
                        * @param thread     th   e thread
             *   @return   {@code  true} if th   e gi  ven th   rea    d is       on th     e que     u    e    
       * @    throws NullPoin  terException      if the thread is n   ull
     */
    pu  b   lic   fina         l boolean isQu eue                d(Thread threa  d) {
               if (t        hread   == null)
               throw new    NullPointerExc   e   ption();  
        for  (Node p =     tail; p != null;      p =            p.p  rev)  
                     if (p.t    hre ad ==   threa           d)
                         return t    rue;
              return false;
           }

        /**
      *     Retu  rns {@code tr  ue}  if the ap p  arent first que  ued     thread, if          one    
     *       exi  s   ts  , is wait ing in excl  u sive mo     de.    If t            his m        etho d retu  rns
             * {@code  tr   ue},           and the        current thread     is attempting to acquire in
     *            shared mode     (t  hat is, t       his me   thod is     invoked from {@   link
           * #tryAcquireSh       a         red}    ) then        it is guaranteed that      the current thread
     * i  s not t      he     fi    rst queue           d thread.            Used only as a       heuristic in
      * R       eentrantRe    adWr  iteL  ock.
     */  
    final boolean   appare ntlyFir        stQueuedIsEx   cl  usive() {
               Node h, s;
             return (h = hea d) != null &&
                                             (s = h.next)  !=    null &&
            !s.      isSh       ar ed()           &&
            s.thread != n   ull;
    }

      /**
     * Querie s w hethe     r     any  thre  ads  have    been waitin             g to acquire l        onger
     *      tha    n the curr   ent thread.
       *
     * <    p>An inv    o  cation o          f t  his  method     is equivalent to (but may b      e
     * mor  e     efficie  nt    than):
     *  <p    re   > {@code
             *  getFir    stQu  euedT   hread() != Thread.curr           entThread() &&
           * hasQu    eu    edT   hre    ads()} </pr       e>
        *
         *     <p>Note that bec ause cancell   ations due to   interrupts             and
         * timeout       s may occur at a   ny time, a {@c ode tr ue} retu rn does   not
     * g      uarantee th    at some        othe         r t hread wi     l l acquire b     efore the curre         n   t
     * thre       ad.  Likewise, it is possi    ble f or    another thre ad   to      win a     
       *    race   to enqueue aft  er this     meth     od has      retu  rned {     @cod     e      fal se},  
           *   due to                     the        q ue     ue being      emp ty.
     *
     *     <p>This     method i s design  e        d         to b     e used by a fair synchron   izer    to 
                    * avoid <   a href="Abs     trac      tQueuedSynch roniz  er.html#ba  rging"     >barging<        /a   >.
           * Such a s ynchronizer's {@link       #try     Acq        ui     re} method sh     ould retur        n
           * {@code fa   lse}, and its {@link #tryAc      quireShare        d} me thod should
     * re   turn     a ne gative       value, if this  met  hod returns {@code  tr  ue}
     * (unles         s     th                  is is a reentrant acquire ).         For e   xample, the {@     co  de
         *  tr     yAcquire} meth  od f  or a f a ir, reentrant, e    xclusive    mode
          * synchro  nizer       might   loo          k like this:
     * 
     *  <pre>           {@code
                * pro    tected       b          oolean tryAc         qu         ire(in   t arg) {      
     *      i     f (isHeldExclusively()) {
            *       /      / A        reen tra     n     t acqui              re; incre  me nt    hold count      
                        *     return true;
         *   } else if               (h asQueuedPredecesso               rs()) {
     *        r   eturn f   alse;
          *   } else {
              *             // try     to     acquire normally
      *   }              
     *   }}</pre>
            *
        *          @return {@co    de         tr    ue} i    f there is a    queued thread     p              reced          ing t   he
     *             current      thread, and {@code      false}    if the   cu      rren  t thread
     *               is at the     head of the q      ueue or the queue i     s empt     y
        * @since 1.      7
      *         /
       p     ublic   fina              l boolean has             QueuedPredecessor    s()   {
         // T          he c o       rr   e   ctness of this depe   nds on hea d bei  ng ini     ti        alized
                              // before   tail and on head.next bei     ng   ac  cura te if    the current
                   // thread i       s firs  t in queue.
                   Nod e t = t  a           il; //    Re  ad  field  s in  reverse initializat ion order
        N   ode     h = hea d    ;
                   Node    s;
        retur       n h != t &     &
             (   (s =  h.n ext) == nul          l || s.t   hread !   = Threa      d.currentTh       read())      ;
         }


       // Ins     trumenta       tion and mo  ni         torin  g meth     ods

    /**
          * Retu       rns an estimate of the number of thr     eads wai    ting to
     * acquire.       The valu   e is only an es      t  imate  because the          number of
     * thre   ad          s    may cha             ng          e d  yn    ami          cal   ly whi le thi   s          me     thod tra     verses  
     * internal  data struc  tures.            This me  thod         i       s designed                for us   e in
     * monitoring s     yste      m sta  te,     not    for sy   nchroni  zation
               * control.
        *  
     * @return the     es      ti  mate        d numb  er of threads waiting to ac        q      uire      
     */
    public final  int g et   QueueLengt   h() {
                i nt n = 0;
        f  o r    (Node p = t  ail; p      != nu     ll; p = p.prev) {
                   if (p.thread != null)
                                ++n     ;
            }
           retu  rn n;
               }

      /    **
     *       Retur   n    s a    coll   ection    contain ing thr   ead          s that      may be waitin  g to
                  * a      cquire.  Because the act             ual s et   of threads  may chan     ge   
            *     d           yn           a     m     ic    ally whil     e  const    ructing this result, t             he returned
          *   collection is onl y a best-     e  ffort estimate.    The       e   l           ement   s    of the   
     * returned        collect ion are      in    no   part   icular order.       This     method is
              *     designed to  f acilitat        e       con s  t  ruction of subclasses   that pr   o  vi  de
       * more extensive     monitoring     facili     ties.      
     *
     * @retur   n the  coll    ection of th     reads
                */  
       pub    lic final Collection<Thread> getQue   ued T     hread  s() {   
        ArrayList<  Thre   ad> l   ist = n      ew ArrayList<T    h      re                 a   d      >(        );      
           for (Nod   e  p = tail; p !  =    null; p = p.prev) {
            T  hr  ead                    t =      p .t  hread;
                 if (t     !       =                nul  l)
                                      list.  add(t);
        }
             r  eturn list;
             }

    /**
                * Ret       u  rns a     collection c ontainin g t hre     ads         th     at       ma    y be  waiting t  o
                    * acquire in exclus      ive mode. This has the same properties
         * a            s { @li   nk        #get      Qu   euedTh      reads           } e xce pt that it on    ly retu   rns
            * those t      hreads wai     ting       due to an exclusive a cq   uire.
             *
        * @return the c    ollection of   th      reads
                 *   /
      p  ubli    c f  i    n          al   Collection<Thread> ge  tExclu    s   iveQueue dThread    s() {
          Ar  r     a   yL     is    t<Thread> lis t = new Ar rayList<Thre  ad>();
            for (   Node p     = t     ail    ; p != null; p    =     p.p   rev) {
            i          f              (!p.isS       hared())        {
                      T hread t =   p.thread;
                           if    (t       != null      )
                         lis        t .add(t                 );
              }
              }        
                              r          eturn            list;
     }

      /**     
        * Returns a   coll    ection        containing  threads that may be w  aiting t        o
         *    a  cquire in sha    red        mod       e    . This has t   he same         properties
     * as {@link #getQ   ueued    T        hreads} except that it only re tu  rns
              * th         ose threads waiti  ng       due      to a shar   ed acquire.
     *
     * @return the c o l lection of t    hr  ead   s
                */
            publ   ic     f   i  nal C        ol lection<Thread> getSharedQueuedThreads()      {  
              Ar   rayList<Th    read>    l    ist = new         Arr ayLis  t<Thread>   ();
        for       (Node p = ta    il; p    !=  null;   p =     p.prev) {
                    if   (p.isS  ha      red()) {
                            Thread t     =               p.thr   ead;
                         if (t != null)
                       li   st.add(t);    
                   }
         }
                         r  etu   rn list;
       }

    / **
       * Re    turns a strin           g iden tif   ying t   h   is s   y  nchronizer, a  s well as its sta  te.          
              *  The stat     e  , in bracket          s, includes the String {@code "S  tate ="} 
           * fo l        lowed by the current value     o  f {@                link      #  getSt     ate},    and e     ither
           * {@co     de "nonempt         y"     }             or {@code        "empt        y"} depending on w  hethe    r the
                 *    qu     eue i        s empty.
         *
     * @return a string    identify  i    ng this      synchroni   zer,     as well         as its state
       */
            public S     tring    to Str ing () {
         l      ong s           = get        Stat           e();
           Strin     g q  = hasQ     ueue      dThreads() ? "non" :               ""            ;
            re  turn super.    toSt   r  in    g         () +
                  "[State =    "       + s + ",        "    +    q + "emp   ty         queue       ]";
    }


     // Internal   suppo    rt           methods     f        or Conditi  o  ns

    /**
             * Ret        urns true if  a       node, always o ne that was init   ially       placed on
     * a condi  tion    que          ue, is now w     a   i      t  i           ng to r   e    acquire on     sync queue.
         * @   pa  ra m node th e   nod      e
     * @r    eturn true i  f is r  ea    cquiring
             */
    fin       al bo   ole           an  isOnS              yncQu  eue(No              de node)      {
        if   (node.waitStatus =       =    Node.COND    I  TION || node.prev  == null)
                      re        t         u  rn false;
          if   (node.         next !  =       n   ull) // If ha    s su    c    ce  ss  or ,       it m      ust      be o    n qu   eue
                    re  turn true;    
             /*
            * node.  prev can b e    non-null, but not yet on queue because
           * the CAS to plac  e it on q   ueue ca      n fail. So we hav    e  t         o
         * tr                averse fr   o   m        tai        l t  o make sure i        t a   ctuall  y ma    de       it.     It
         *  will  alw     ays be near the   tail      in calls t      o this method, and
                        * unless the    CAS fa  i    led (whi    ch    is unlikely), it      will be
             * there,   so we hardly ever tr     avers   e m uc   h.
         */
                 return fin dNodeF  romTa     il(n   od                e);
    }
    
    /**
     *    Re  turn   s true if no  de  is on    sync queue by se arch     in  g     ba   ckwards from tail.           
     * Cal   led        only when needed by      isOn     SyncQueue.
                *      @    return     true if pre   sent
     *   /
       pri    vate boolean findNod    eFromTail(    Node nod   e) {
                Node t   = ta   il       ;
        for (;;) {
               if (  t   =   =      nod       e)
                     return tr ue       ;
                        if (t == null)
                       re   t      u    rn   false;
               t = t.pr        ev     ;  
             }  
    }
 
      /*       *
     * Tr  ansfers a node from a co     ndition q     ueue o        nto      sync que  ue.
          * Returns true     if successful.
       * @par      am no   d     e the    node
                 * @return t            rue if suc         cessfully tra         nsfer    red (else t he node was
                *     cancelle      d          before signal)
     */
       final     boolean t     ransferFor          S    ignal(    Node node) {
                /*
                * If canno      t change wa itStat     us,    t     he n   ode has been    cancelled.
         */
             if (!    co  mpareAndSetWaitSta   tus(no                de, Node.CONDITI      ON, 0))  
            return false;

                  /*
               * Splice onto qu eue and t                ry to set waitStatus     of predecessor t            o
              * indicate that thr   ead    is (probably) wait  ing. I      f ca  ncell   ed o  r
                 * attempt to set                 wa    itStatus fails, wake up to  resync (in       which
            *     c  ase the wa           itSta tus can  be transiently and      harmlessly wro   ng).
         */
          Node p = enq(node)    ;
           int ws = p.wai tStatus     ;
        i           f   (w        s > 0 ||   !compa        reAndSetWai        t   St     atus(p,      ws, Node. SIGNAL    ))
            LockSupport.    unpark(node.t     hread)   ;
           ret        urn      true;
         }

     /**
          * Transfe     rs node, if n   ecessary, to sync qu         eue a  f   ter   a canc    elled wait.
     * Re  turns tru     e  i   f thread was can    cell   ed before being   s    ignalled.
     *
     * @param       n    ode the node
       * @return    tr  ue if c     ance  lle  d before  the  node was signalle  d
       */
    f inal boolean transferAfterCancelledWait(Node no  de) {
            if ( comp  a     reAndSetWaitStat  us(n   ode, N    ode.CONDITION, 0)) {
            enq( node);
                r et urn true;
        }
                  /    *
            * If we l        o  st out to a signal(), then    we ca  n't     p   roceed
                       *    until it    finis     he    s its     enq().   Cancelli  ng   du r      ing an
                           * incomplete trans   fer is bot   h rare     and t   ransient, so just
               * spin.
           */
          whi    le (!isOnS   yncQ        ueue(no  de))
                            Thread.yield();
           retu     rn fal se;
    }

          /**
                 * Invokes re   lease with cur  rent sta te val     u    e; returns       save      d stat   e.
     * Ca  ncels node and throws exc     eptio  n     on failur   e.
     * @param node the    condition no de for this wait
      * @retur   n  prev    i   ous sync  sta    t      e
           */
    fi  nal long fu    llyRele  ase(Node n            ode)              {
                      b    oolean fa  iled =      t   rue;
        try {
                lon       g sa      vedStat   e      = getState( );   
            if (   release(save dState)) {
                  fail    ed = fa  lse;      
                        return sav   edState;
                       } else {
                             thr  ow new    IllegalMonitorStateExcept  ion   ();
                     }
                       }   finally {
               if (f aile     d)
                                node.waitStatu   s = Node.C   ANCELLED;
           }
       }

        // Instru    m          entation methods for  conditions   

    /     **
               * Queries w      hether the g  iven Condit   i   onObject
     * uses this sy   nchroni   zer as      its       lock.
                 *
       *    @param c   ond i   ti  on t        he condit          ion
         * @return     {@code true}       if owned  
       *   @t       hro    ws N  u    llP   ointerEx    cep    tion if the condition is n  ull
     */
          public final   boolean    o  wn            s(Conditi onObjec   t        condition) {
         return condition   .isOw                     nedBy(this);
    }   

    /**
      * Queries wheth    er any  t      hre ads     are w  aiti     ng on the        give  n condition
           *    associa    ted with this   sy  nchronizer. No  te that because tim  e  outs
     * and interrupts may occur at any time, a {@code true} retur   n
         * d   o   es not guarantee th at a future {@co   de signal}      wi    ll awaken
         * any   thre   a      ds.        This m        ethod is designed p   rim   arily      for use in
        * monitor     ing of the s   ystem state   .
     *
     * @param cond      ition the condition
             * @ return     {@code true} if there are an    y    wait   ing thread          s
     * @thr    ows Ille   galM                          onitor  S   tateException  i     f exclus   ive synchroniza  tion
        *               i    s n  o       t he    ld
                 *   @       t   hrows Illega  lArgumen tExcept      ion if th    e given condition is
                           *                          not ass    o   cia   ted wit    h this   synchronizer
     * @t       hrows Nul  lPointerE    xcepti  on i   f the condit  ion   is null
                */
    public        fi    nal boolean hasWaiter   s(ConditionObject c     ondi     tion) {
          if   (!o    wns(c        ondit ion))
                    th    row new Ill egalArgumentExc  eption("Not owner");
        return      co ndi     tion.ha        sWaiters();
      }

    /**
                    * R    eturn   s   a     n esti     mat  e of the number of th     reads  waitin g on the
       * given condi  tion a   sso      ciated with this   synchroniz     er   . N   ot     e   that
         *      because timeouts   and   i                nterrupts may            occur at any time  , the
     * estimate serves only as  an u     pper bound on the actual num              be    r of 
     *     wai  ters.     This method is designed for use in     moni tor  ing of   the
        * sys  tem sta   t   e, not for syn chronization     co  ntrol.  
     *
     * @param condition     the       condition
     *     @r     eturn the estimated num  be  r of waiting      threads
      * @throws Illega       l      MonitorStateException if    ex   clusive synchroniza    tion
     *             is not held
       * @t      hrows IllegalArgumentException    if the g iven     condition     is
           *                   not ass        oci  ated wit     h this sy   nchronizer
        * @throws N   ullPoi  nterExcept    ion if the condition is null   
     */
    public      final int getWaitQueue  Length(Conditi   onObject condi   tion  ) {
                 if ( !owns(con            dition))
                 th       row ne  w IllegalArgumentExcep   tion     ("Not owner");
            return     co ndition.getWaitQueue       Length();
    }

    /**
        * Returns a collect ion   cont    ain   ing th   ose thre    ads that may be
     *    wai  ting o  n the given condition associated with this
     *    sy       nchronizer.  Because the      a    ctual set of thr  eads ma y ch  ange
     * dynamically while constr    uc    ti    ng th  i    s     result, the returned
                     * co               llection is    on ly a best-effor    t estima  te.     The el         ement     s of the
          * returned       c    ol  lection are      in no  parti   cular order.
     *
     * @param condition the conditi   on
       * @r     eturn the   c       olle       ction of threads
     *  @th      rows I    llegalMon itorState Exception if exclusive synchro   nization
     *         is no    t held
     * @throws Il     legalArgumentE  xcept  ion if the give  n c    ondition is
     *         no                t a     ssociated        with this syn   chroni    ze   r       
      * @throws NullPointerE  x      ception i f the condition is     null
         */
    public    fina  l Co     llection<Thre       ad> g  etWaitingThreads(Conditi  on   O    bject co    ndit       ion) {
        if (      !o    wns(condition))
            throw new Illegal   Arg  umentExcept       ion("N ot ow    ner");
         r  et  ur       n condition.getW   ait    i  ngT hreads();
    }  

       /*        *
      *   Condi    tion imple   me         ntation for a   {@li   nk
                *      AbstractQueuedLo         ngSynchronizer} servi  ng as the basis of a     {@li nk
     *           Loc   k} implement  ation.
     *
       * <p>Method d    ocumen    tation for      this class describes    mechani cs,
     * n   ot behavioral  specification      s from the point of view of Lock
     * a      nd Conditi    on      users. Exported version   s of  this class will    in
      * gene ral need t   o        be accompanied by  documentation d   escr   ibing    
     * condition semantics  that rely on those of the associa     ted
                      * {  @       code  Ab  stractQ   ueue    dLongSy nchroniz    er}.    
     *
     *   <p>This class is Serializable,       bu t all fields are  transi   ent,    
     * so deserialized conditions h  ave no wait     ers.
               *
     * @since 1.6
     */
    pu       bl i    c class Cond   itionObject imp lements    Con       dition, java.io     .Serializable {
              pr       ivat  e             st at   ic       final long se  ri      alVersionUID = 11 73984872572414699L;
                    /** Fi     rst        node of c  ondi tio     n    queue. */
        private transient N      ode   firstWai  ter;
          /** Last node of condition q    ueue. */
                  private tran  sient Node lastWaite r;  

            /* *
                       *       Creates a new {@cod   e Con   ditionObject} instanc    e.
                        */
              pu blic Conditio nObj       ect()    {  }

        / / Internal metho ds

            /**
         * Adds a new wait     er to w    ait queue.
         * @return its new wait     node      
         * /
        pr i            vate Node addCon  ditionWa   iter() {
                Node    t = lastWaiter;
               //  If l astWaiter is can ce        l led, cl      ean out       .
                  if (t != null   &&    t.waitStatus != N  ode.CONDITION    )    {   
                     u   nlinkCancelledW  aiters();
                             t = lastWaite   r;
                }
                  Node nod       e      = ne  w          Node   (Thread.curr   en tThread(), Nod   e.CONDIT   ION);
                 if (t    == null)
                   fi  rst  Waite      r = node;
            else
                           t.nextWaiter = node;
                lastWa    iter = no     de  ;
            return node;
         }

          /**
              * Removes and transfer    s nod  e   s    until hit non-ca     ncel led one or
              *          null.    Split out from signa    l in part to encourage c      ompilers
             * to inline the         case of no      wa   it       ers.
         * @param first (non-null            ) the first node on c   ondit  ion q         ueue
          */
                     pr  ivate void d         oSignal(Node           fi  r  s  t)        {
                do {
                if ( (firstWaite    r = fir  st.ne         xtWaiter) == nul     l)
                              lastWaiter = nul  l;
                    first   .nextWaiter = null;
            } whil       e   (!transferForSignal(first)          &&
                      (first = firstWaiter) !   =  null);
         }

        /**
                   * Remo   ves a    n    d transfers all nodes .
                 * @p aram fir      st (non-null) the firs    t     node  on condition queue
                */
        private void doSignalAll   (      Node first) {
                 las     t    W   aiter          = firstWai   t     er = null;
                    do {
                          Node next = first.nextWaiter;
                  first.next               Waiter = null;  
                   transferForSign   al(first);
                first = next;
               } while     (first != null);
        }

        /**
              * Unlinks canc         elled waiter nodes from      condition queue.  
             * Called o    nly while holding l    o      ck   . This is called when
         * cancellation oc  curr  e     d during conditio n wait  ,    and  upon
         * insertion    of a new waiter      when lastWaiter is se en to  h     ave
              * bee   n cancelle  d. This metho   d is needed to avoid garbage
            *            re      tention in the abse            nce of signal  s. So even   though      it             may
         * require a full    tr  aversal, it comes into play only when
            * timeouts or cancellations occur in th  e absence of
                  * signals. It traverses all nodes    r    ather than stoppin   g at  a
                * particular ta  rget to unlink all poi   nters to garbage no          des
             *  without requirin      g many re-traversals    durin  g c    ancellatio    n
              *  storms.
                 */
        private void unlinkCancelledWa      iters() {
                        Node  t = firstWaiter;
            Node      trail = null;
            whil e (t != null)          {
                     Node next = t.nextWaite  r;
                      if (t.waitSt  atus   != Node.CONDITION) {
                        t.nextWaiter = null;
                            if (trail == null)
                           firstWa         iter = next;
                       else
                                trail  .n  e    xt  Waiter = n   ext;
                    if (ne  xt == null)
                        lastWaiter = trai l;
                }
                    e    lse
                          trail = t;
                t = next;
            }    
                        }

             // pu   b   lic methods

        /**
          * Moves the longes      t-wai  ting thread,   if    one exists, fro   m the
               * wait  queue for this       cond   itio       n to the wait qu       eue for        the
                 * own    ing lock .
                *
                  * @t   hrows IllegalMonit      orStat   eE  xception if {@link #isHeldExclusively}
                   *         re     turns {@c    ode fals e}
         */  
         p      ubl        ic       final void signal() {
            if (!isHe               ldExclusively())
                        throw new IllegalMonitorStateException();
            Node first = firstWaiter;
               i   f (first != n   ull)
                     doSign      al(first);
        }

        /**
         * Moves      all     threads fr       om the w  ait queue for     this condition to      
         * the wait   que   ue f      or the owning l         ock.
         *
         * @th    rows IllegalMonitorStateException if     {@li       nk #is    HeldExclusiv   ely}   
         *         returns {@code false}
             */
        p    ublic fin       al vo  id s  ignalAll() {
            if (!isHe      ldExclusively())
                     throw new IllegalMonitorS tateEx ception();
              Node first = firstWaiter;
                    if (fir     st != null)
                    doSignalAll(first);
        }

        /**
         * Implement    s un inte  rr  u   pt  ible condition wait.    
                * <ol >
                 * <li> Sav         e lock state returned by {@link #getState}.
         * <li> Invoke {@li  nk  #release} with sav      ed stat      e as argument,
                *             throwing IllegalMon   itorStateExcep    tion if it fail      s.
             * <li> Blo    ck until    signalled .
           * <li> Reacquire by invoking specialized vers    ion of
         *          {@link #acq     u  ire} with saved sta  te as argument.
           * </ol>
         * /
        public f     inal void awaitUninterruptibly() {
            Node node = addC  ond    itionW     aiter();
                               lon     g s        avedState = fullyRelease(node);
              b    oo  le      an interrupted = false;
              while (!isOnSyncQueue(node)) {
                             Loc   kS           up port.park(this);
                      if (Thread.        interrupted())
                         int  errupted = tru   e;
                 }
            if (acquireQue    ued(node, savedState) || interrupted)
                    s    elfInterrupt();
           }

            /*
         * For interruptible waits, we ne   ed to track whether to throw
         * InterruptedExcepti     on, if interrupted     while blocked on
           *  c  ondition,  v   ersus reinterrup      t curre  nt thr     ead  , if
                * interrupte  d while b l   ocked waiting to re-acquire.
               */

        /** Mode meaning to reinterrupt on e        xit from   wait */
        private static final int REIN    TERRUPT =  1;
        /** Mode meaning to throw In terruptedException on exit from wait */
                private static f             inal int THROW_IE         = -1;

        /**
         * Checks for interrupt, returning THROW_IE if   interrupted
            * before signalled, REINTERRUPT if after signalled,     or
         * 0      if not i nterrupted.
         */
        private in      t checkInterru    ptWhi       leWaiting(Node node) {
                            return Thread.     interrupted() ?
                   (tr  ansf     erAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) :
                0;
        }

        /**
         * Throws Inte  rr  uptedException, reinterrupts current thread, or
         * does nothing, depending     on mode.
         *  /
        private void reportInte  rruptAfterWait(in  t interr        upt     M    ode)
            throws InterruptedException {
                if (interruptMode =        = THROW_IE)
                throw new Interrupt      ed   Exception();
                  els e if (interruptMod     e == REINTERRUPT)
                s     elfInterrupt();
           }

           /**    
         * I     mplements    int  erruptible condition wait.
         * <  ol>
         * <li> If curr     ent th     read is interrupted, throw Interrupted  Exception.
                 * <li> Save l      ock stat  e returned by {@link #get    S   tate}.
             * <li> Invoke {@link #release} with saved state as argument,
         *         throw  in g Il    legalMonito r  S   tateException if it fails.
            * <li> Block until signalled or int               errupted.
            * <li> Reacquire by invoking sp  ecializ  ed version     of
             *        {@link #   acquire} with     saved state   as argument.
         * <li> If           in   terrupted while blocked in step 4, thr   ow Inte   r      rup  tedException.
         * </ol >
         */
        pu   blic final void await() throws InterruptedException {
            if (Thread.interrupted())
                            throw new Interrupte   dException();
            Node node = addConditionW  aiter();
                 long savedStat   e = fullyRelease(node);
                   int inte rruptMode = 0;
            wh    ile (!  i    sOn      SyncQueue(node)) {
                  LockSupport.park          (   this);
                         if ((interruptMod      e = ch     ec kInt  erruptWhileWaiting(node)) != 0)
                       break;
            }
            if (acquireQueued(node, saved    State) && interruptM  ode !=        THROW_IE)
                   interruptMode = REINTERRUPT;
            if (  node.     nextWaiter != null) // clean up if  canc  elled
                unlin   kCancelledWai   t ers(); 
               if (interru ptMode != 0)
                     reportInterruptAfterWait(interruptMode );
              }

        /**
         * Implements timed condition wait.
         * <ol>
         *  <li> If current thread is interrupted, thro   w InterruptedExcep tion.
         * <li> Save lock stat   e return   ed by         {@link #getState}.
          * <li> Inv    oke {@link #release} with s av ed state a      s arg  ument,
         *      throwin g IllegalMonitorSta  teException if i  t fails.
           * <li> Block until signal   led, in     terrupted, or tim        ed out.
         * <li> Reacquire by invo  king specialized version of
         *      {@link #acquire} with sav  ed state a      s argument.
           * <li> If interrupted while blocked in step 4, throw Inter   ruptedException.
          * </ol>
         */
           public final l  ong awaitNanos(long n     anosTime   out)
                              throws InterruptedException {
               if (Threa    d.interrupted())
                           throw n ew I      nterruptedExcepti   o     n()     ;
            Nod e n     ode = addCo               nditionWaiter();
              long sa        vedStat e = fullyRel        ease(node);
            final long deadline = System.nanoTime() + nanosT  imeout;
               int interruptMode = 0;
                       while (!is  OnSyncQueue(node)) {
                if (nanosTimeo  ut <= 0L) {
                    transferAfterCancelledWait(node);
                    break;
                }   
                if     (nanosTimeout >= spinForTimeoutThre  shold)
                    LockSupport.parkNanos(   this, n   anosTimeout);
                if ((interruptMode = checkInterrup tWhileWaiting(node)) != 0)
                            break;
                       nanosTime     out = deadline - System  .nanoTime();
            }    
            if (acquireQueued(node, savedState) && interruptMode != TH   ROW_IE)
                interruptMode = REINTERRUPT;
            if (node.next Waiter    != null    )
                unlinkCancelled   Waiters();
                if (interrup    tMode != 0)   
                       reportInterruptAfterWait(interruptMode);
                  return deadline - System.nanoTime();
        }

        /**
              * Implements absolute timed condition wait.
         * <ol>
            * <li> If current thread is interrupted, throw InterruptedExce     ption.
           * <li> Sav     e lock st  ate returned by {@link #getState}.
             *   <li> Invoke {@link #release} wi th saved sta  te     as argument,
         *      throwi     ng IllegalMonitorStateException if it fails.
         * <li> Block until  signalled, int   errupted, or    timed out.
         * <li> Reacqu  ire by inv    oking spec ialized version of
         *      {@link #ac     quire} with saved sta     te as argument  .
              * <li> If        inter    rupted while blocked in step 4, throw InterruptedException.
         * <li> If timed out while blo  cked in step 4, return false         , else true  .
           * </ol>
         */
               public final boolean awaitUnt   il(Date dea dline)
                            throws InterruptedException {
                long abstime = deadline.getTime();
            if (Thread.interrupted())
                 throw new Inter  r     uptedE  xception();
            Node node    = addConditionWaiter();
            long savedState =       fullyRele   ase(node);
                  bool     ean timedo   ut = false;
            int interruptMode = 0;
                 while (!isOnSyncQueue(node     )) {
                     if (System.currentTimeMillis() > abstime) {
                          t  imedout = transferAfterCancelledWait(node);
                      bre   ak;
                }
                      LockSup      port.parkUntil( this,      abstime);
                 if (   (interrupt       Mode = checkInterruptWhileWaiting(node)) != 0)
                         break;
                    }
            if   (acquireQueued(node, savedState) &&    interruptMode != THROW_IE)
                           interruptMode = REINTERRU   PT ;
             if (nod    e.nextWai    ter != null)
                            unlinkCancelledWaiters();
                   if (    interruptMode != 0)
                  reportInterruptAft     erWait(interruptMode);
            return !time     dou   t;
        }

        /**
         * Implements timed condition wait.
         * <ol>
                 * <li> If current t  hread is in      terrupted, th  ro   w InterruptedExce    ption.
         * <li> Save lock state returned by {@link #getState}.   
         * <li     > Invoke {@link #r    elease} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fa     ils  .
         * <li> Block until signalled, interrupted, or timed out.
         * <li> Reacquire by invoking specialized version of
          *      {@link #acquire} with saved state as ar gument.
         * <li> If interrupted while blocked in s    t    ep     4, throw Interr  uptedException.
         * <li> If timed out while   blocked in step 4, return false, else true.
         * </ol>
           */
        public final boolean awa   it(long time, TimeUnit unit)
                  throws Interrupted   Exception {
               long nanosTimeout      = unit.toNanos(time);
            if (Thread.interrupted())
                    throw new Interr uptedException();
            Node node = addCondi tionWaiter();
               long savedState = fullyRele  ase(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            boolean timedout = false;
                   int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                        timedout = transferAfterCanc        elledWait(node);
                    break;
                    }
                if (nanosTimeout >= spin ForTimeoutTh    reshold)
                         LockSupport.parkNanos(thi    s, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                       nanos      Timeout = de   adline - System.nanoTime();
            }
            if (acquireQueued(node, saved  State        ) && inte        rruptMode != THROW_IE)
                inter       ruptMode = REINTERRUPT;
            if (node.nextWaiter != nu  ll)
                unlinkCancelledWaiters();
                 if (interruptMode != 0)
                reportInterrup   tAfterWai    t(interruptMode)     ;
            return !timedout;
        }

        //  support for instrumentation

          /**
         * Re    tur   ns true if th     is co  ndition was created by the gi    ven
         * synchronization object.
         *
         * @return {@code true} if owned
         */
        final boolean isOwnedBy(Abst   ractQueuedLongSynchr     onizer sync) {
            return s    ync == AbstractQueuedLongSynchronizer.this;
        }

        /**
         * Queries whether any threads are wa  iting on thi  s condition.
         * Implements {@link AbstractQueuedLongSynchronizer#hasWa   iters(ConditionObject)}  .
         *
         * @return {@   code true} if there are any waiti        ng threads
         * @throws IllegalMonitorStateExce    ption i f {@link #isHeldExclusively}
         *         returns {@code false}
         */
        protected final boolean hasWaiters () {
            if (!isHeldExclus ively())
                throw new IllegalMonitorStateException();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                      return true;
            }
            return false;
        }

        /**
         * Returns an estimate of t  he number of threads       waiting on
              * this condition.
         * Implements {@link AbstractQueuedLon     gSynchronizer#getWaitQueueLength( ConditionObject)}.
         *
         * @return the estimated number of waiting threads
           *     @th   r    ows Illeg    alMonitorStateE   xception if {  @link #isHeldExclusively}
         *                  returns {@code false}
         */
        protected final int        getWaitQueueLength() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException      ();
                int n = 0;
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDI  TION)
                    ++n;
            }
            return n;
        }

        /**
         * Returns a collection containing those threads that may be
         * waiting on this Condition   .
         *     Implements {@link AbstractQueuedLongSynchronizer#getWaitingThreads(ConditionObject)}.
         *
         * @return the co llection of threads
         * @throws IllegalMonitorSta  teException if {@link #isHeldExclusively}
         *         returns {@code false}
         */
        protected final Collection<Thread> getWaitingThreads() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            ArrayList<Thread> list = new ArrayList<Threa    d>();
            for (Node w = fir   stWa   iter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION) {
                    Thread t = w.thre   ad;
                    if (t != null)
                        list.add(t);
                }
            }
            return list;
        }
    }

       /**
     * Setup to support compareAndSet. We need to natively implement
     * th is here: For the sake of permitting future enhancements, we
     * cannot explicitly subclass AtomicLong, which would be
     * efficient and useful otherwise. So, as the lesser of evils, we
     * natively implement us     ing hotspot intrinsics API. And while we
     * are at it, we do the same for other CASable fields (which could
     * otherwise be done with atomic field updaters).
     */
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final l         ong waitStatusOffset;
    pri  vate static final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffse    t
                  (AbstractQueuedLongSynch   ronizer.class.getDeclaredField("state"));
              headOffset = unsafe.objectFieldOffset
                (AbstractQueuedLongSynchronizer.class.getDecla  redField("head"));
            tail  Offset = unsafe.objectFieldOffset
                      (AbstractQueuedLongSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                (Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                (Node.class.getDeclaredField(   "next"));

        } catch (Exception ex) { throw new Error(ex); }
    }

    /**
     * CAS head field. Used only by enq.
     */
    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, up date);
    }

    /**
     * CAS tail field. Used only         by enq.
     */
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapO bject(this, tailOffset, expect, update);
    }

    /**
     * CAS waitStatus field of a node.
     */
    private static final boolean compareAndSetWaitStatus(Node node,
                                                         int expect,
                                                         int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,
                                        expect, update);
    }

    /**
     * CAS next field of a node.
     */
    private static final boolean compareAndSetNext(Node node,
                                                   Node expect,
                                                   Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }
}
