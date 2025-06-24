/*
   * ORACLE PROPRIETARY/CONFIDENTIA    L. Use    is subject to license term   s .
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
     *     Wr    itten by Doug Lea with ass    i   s    tance from members of       JC  P JSR-166
 *      Expert G   roup and released       to the public domain,  as explained at
 * htt   p://creativ   ecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent.locks;
import java.util.c  oncurrent.TimeUnit;
i  mport java.util.ArrayLi st;
import java.util.Collection;
import java.util.Date;
import s        un.misc.Unsafe;

/**
 * Provid es a framework for implemen     ting blockin  g lock   s and related
 * synchroniz  ers (semap  hores      , events, etc) that re   ly on
 *   first-in-first-out      (FIFO) wait queues  .  This cl   ass is designed to
 * be     a useful basis fo  r  most kinds of   synchroni   zers that rely on a
 * sin   gle atomic {@cod    e        in  t} value to represent state. Subclasses
 *     m    ust define the pr otected me      thods that change this    state, and       whi       ch
     * d    efi   ne what th  at  state       means in terms of this o       b  ject being         a  cquired
 * or release     d.  Gi ven  these, the other metho     ds in this cl a             s  s c arry
 * out all que         uing an   d blocking mechanics. Subclasses can maint       a  i  n
 * other state fi  elds, but only the atomically updated    {@     code int}
 *     value manipulated u  sing method   s {@link #     getSta   te}, {@l  ink
 * #s  etState} and {@li      nk #compareAndSetState} is tracked with respe     ct
 * to synchronization.
 *
 *     <p>Subcla       sses sh  ould be defined as non-public internal helper
 * cla      sses     that are us    ed to    implement   the        synch ronization proper     ties
 * of their e    nclosin  g class.  Class
 * {  @code Abs  tractQueuedSynchronizer} does not       implement any  
    * syn   chro  n   ization int erf      ace.       Instead it defi nes m  ethods      such as
 * {@link #acq  uireInter ruptibly} that     can  b   e invoked as
 * appropriate by    concrete loc  ks and related synchroniz ers to
 * implement their pub   lic methods.
 *    
 *    <p>     Thi        s class supp  orts either or both a default <em>exclusi  ve</em>
      * mode     an  d a     <em>sh  ared</em> mo     de. When acquired   in e        xclusive mode   ,
 * attempted acquires by other threads     cannot succeed.      Shared mode
  * acq  uire      s by multiple     th   re  ads may (but need not) succeed. This class
 * does not &quot;understand&q   uot; th     ese differences ex    cept in         the
 * me chanical sense tha    t whe     n a shared   mode ac    quire succeeds,      th    e next
 * waiting       thread (if    one exi s       ts) mus   t also     determine whether it can
 * acquire       as we  ll. Threads waiting in the different mod       es shar    e the
 * same FIFO queue. Usually, implem   entation   su   bclass es support on ly
 * one of these mo  d     es, but bo       th can come in to play for exa    mple   in a
 * {@link ReadWrit     eLock}. Subclasses that support only e xclusive or
 * o    nly shared mode s need      no               t define the methods supporti    ng      the unu    sed mode.
 *
 * <p>This class d   efines    a nested {@link  ConditionObject} class  that
 * can  be used as a {@link Condition } implementatio       n by subclasses
 * supporting exclu   sive    mode for   w    hi     ch method {@link
 *     #isHeldExclusively} reports whether synchr    o   nizatio     n is exclus   ively
 * hel    d with res   pect to the            c    urre  nt t   hread, met  hod {@lin  k #rel     ease}
 * invoked with the c        urrent {@l      ink #ge         t   S    tate} val  ue fully release      s
 * this  o   bject, and {@link #acquire}, given this sa ved state value,   
 * e     v    en   tually restores this objec    t  to its previous acquired state.  No
      * {@code AbstractQ    ueuedSynchronizer}    me  thod otherwise creates such a
 * conditio        n, so if th i  s constraint cannot be met,            do not use it.  The
     * be          havior of {@lin   k ConditionObjec t} depends of course on the
 * sema          ntics     of     its s            ynchronizer implementation.
 *
 * <p>This class provides inspection    ,   instrumentation, and monitori      ng
 *    methods f    o   r the    inter      nal queue, as   wel     l as similar met     hods for
 * c     ondition objects. These can be ex     ported as desired into classes
 * using an {@code Abstract QueuedSynchronize           r} for their
 * s   ynchronizati     on mechanics.
 *
 * <p>Ser    ialization of             this class stores     only the underlying atomic
 * integer    maintaining state, so deseria     lized ob  jects have empty
 * t   hread queues. Typical subclass      es requiring seriali   zab    il   ity will
 * defi   ne a {@code  read   Object} method that res     tores this to a known
 * init i   al st      ate upo    n deserialization.
 *
 * <h3>Usage</h3>
 *
 * <p>To use this class as the    basis of a synchroni     zer, red efine the
 * foll     owing methods,    a     s applicable, by inspecting and/or modifying
 * the s ynchronization state using {@link     #getSta   te}, {@l     ink
 * #setState} and/or {@link #compareA  ndSetState}:
 *  
 * <ul>
 * <li> {@lin  k #tryAcquire}
 * <  li>    {@  link        #tryReleas   e}
    * <li> {    @link #try     Acqu   ireShared}
  * <li>     {@l      ink #tr   yRel   easeShared}
 * <li> {  @link #isHeldExclusively}
 * </ul>
 *
   * Each of the se me  thods by default throws  {@link
 * UnsupportedOperationException}.   Impleme         ntations o     f the     se methods 
 * must be internally threa      d-sa    fe, and should in general be short     and
 * not block. Defi       ni   ng these methods is the <em>only</   em>  s    upported
 *     means o    f       using this cla  s     s. Al     l other methods are dec     lared
 * {       @ code final} because th   e   y cannot be independently varie   d.
  *
 * <p          >You m       a    y also find  t he inhe    rited methods from     {@link
 *   AbstractOwnab   leSync           hro    nizer} use   ful to keep track  of the thr      ead
  * ownin     g an exclusive synch  ron     izer.                   Yo   u are enc   ouraged to use        them
 * --    this enables   monitoring and diagnostic tools      to         as       sist u   se    rs in
 * de   termi        ning whic   h threads hold locks.
 *
 * <p>Even tho  ug      h this class     i   s based on  an internal FIFO queue, it
 * does not automati  c ally enforce FIFO acquisition poli    cies.                  T     he core
 * of exclusive synchron ization takes the      form:
 *
 * <pre>
 * Acquire:
 *     while (!tryAcquire(arg)) {
 *        <  em>enqueue thre      ad i f it    is not alr    ead y queued</em>;
 *        <em>possibly block current threa         d</em>;
 *     }
 * 
 * Release   :
 *      if (tryRelease(arg))
 *        <         em>unbloc k   the first queued thread</  em>;
 * </pre>
 *
 * (Shared m     od e is similar but may involv     e casc             ading signals.)
 *
        * <p id="bargi ng">Because checks in a  cquire        are    inv    ok    e  d before
 * enqueuing, a newly acquiring thread may <e     m>barge</em> ahead of
 * ot hers that are blocked and queued.  How     ever, yo   u can, if d    esired,    
 * define {@code tryAcquir    e     } a   nd/or {@code tryAcquireShared} t   o
 * d     isable barging by internally invoking o    ne or more of th    e in  spect ion
 * methods, thereby   pr    oviding a <em>fair</em> F   IFO acq     uisition or   der.   
 * In particular, most fair synchr     onizers can define {@c   od     e tryAcq uire}
 *  to re     tur       n {@code fa  lse} if       {@link #hasQueuedPredecessors} (a method   
    * specific    al ly designed to be used by fair synchronizers) ret  urns
 * {@code true}.          Other varia  tions are possible.
 *
 * <p>Throughput an  d scalability are gener ally highest for    the
 * def    ault barging (also kn   own as <em>g  re edy   </em>,
 * <em>ren      ouncement</em>,   and <e     m>convoy-avo   idance</e    m>) strategy.
 * While this is not guaranteed to be   fa    ir  or st   arvation-free, earlie  r
 * queue d   t   hreads are allowed t    o recontend befo      re later queued
 * t    hreads, and each        r  econtention has an u  n  biased     chance to su    cceed
  * ag  ain  s      t incom       ing threads.   Also, while acquires do no    t
 * &quot;spin&quot;        in the usu  al sen     se, they m      ay perform multiple
 * invocations   of {@code tryAcquire} int    erspersed with ot  her
 * c    o       mpu    tations      before b  locking.  This     give    s most of the ben   efits  of
 * spins when exc  lus ive synchron   ization is o      nly bri   efl    y held, without
 *  most of the liabilities when it   isn't. If so desired, you c    an
 * augment this by preceding c  al   ls t   o acquire methods with
 *   "fast-path" che     cks, possi  bly    prechecki   ng {@li nk #h  asConten    ded}
 * a nd/or {@link #hasQueuedThreads} to          only do so i f the synchronizer
    *   is     likely not to be    con   t         ended.
 *
 * <p>This  class provides an e fficient and scala ble ba  sis for
 * synchronizatio  n in part b    y specializing its range of use to
 * synchronizers that   can rely on {@code int} state,      a     cquire, and
 * release parameters, an        d an inte  rnal    FIFO w  ait queue. When this   does
 * not suffice,         you can build synchro nizers from a lower level usi     ng
   *     {@link    java   .uti  l.conc   urrent.    atomic atomic   } classes, yo   ur own c   u         stom
 *      {@link java   .util.Qu eue} classes, and          {@link LockSuppor  t}  blocki  ng
 *         support    .
 *      
 * <h3>U    sa  ge Exa     mples </h  3>        
 *
 * <p>He     re is a non-re   entr    ant        mutual exclusion lock cla ss that use       s
 * the value zero to represent the unlocked st   ate     , and one to   
 * repr  esent th e lock ed sta  te. While a non-reentrant    lock
           *    do    e  s not strictly   require recor         ding of        the current owner
     * thread, this class doe s so any way            to make usa       ge easi  er to     m     onitor.
    * It als   o su   ppor    ts c  on    di   tions   a   nd expo    s  es
 * on  e of the instr   umentati  on methods:
 *
 *  <pre>  {@co de
    * class   Mutex imp       leme        nts   Lock,   ja       va.io.Seri  alizable {
 *   
 *      // Our i    nternal helpe   r class
 *             pr     ivate stat       ic cla    ss Sync exten    ds A    bstractQ    u   euedSynchronizer {
 *     // Reports whether in locked state
 *     pr     otecte   d boolean isHeldExclusively() {
 *       retu  rn g     etState(   ) =   = 1;
 *     }
 *
 *     // Acquires the l      oc    k if s  tate i  s      zero
 *             public bool    ean          tryAcqu   ire(int acquires)  {
   *              assert acquires           == 1; // Otherwis   e unu   sed  
 *       if (compa reAndSe      tState(0, 1)  ) {
 *                  setExclusive     OwnerThread(Thread.curren            t Th   read())       ;
 *            re     tu     rn tr  ue;
 *                    } 
 *       retur   n fa  ls   e;
 *            }
        *
 *                // Releases t he lock by setting state to zero   
 *           protected     boolean    tryRelease    (int    releases) {
    *       assert re  leases =     = 1;    // Otherwise u         n     use  d
 *               if (getState() == 0) thro     w new IllegalMon itorStat      eException();
 *               se      tExclusiveOwnerT    h read(null);
     *              setState     (0);
     *              return true ;
 *     }
 *
 *        // Provides        a Condition
 *     Condition newCondition       () { return         new Co  nditi    onObject() ; }
 *
 *     /    / Deser   ializes      properly
 *          priv ate void readObje  ct(ObjectInputStream s)
 *             thr  ows IOExc     epti on  , ClassNotFoundExce     ption {
   *       s  .def aultRe      adObje         ct();
 *       setSt     ate(0); //    reset to u    nl    ocked state
 *     }
    *      }
 *
 *   // The  sync o    bject does all    the hard work. We j          us  t forward    to it.
  *     private final Sy  nc sync = new Sync();
 *
 *   public voi      d lock()                      {       sync.a   cquir  e(1); }
        *   publi    c boolean tryLock()          { r      eturn sync.tryA   cquire(1);           }
 *    public void unloc   k( )                  {    sync.rele      ase(1);  }
 *     public Condition newC      ond        ition()           { return        sync.n  ewCondition(); }
  *   public boolea    n isLocked()         { retu    rn sync.isHeldExclusivel   y(); }
 *    pub   l ic boolean hasQueu  edThreads() { return        sync.h asQueuedThreads    (); }
 *       public       vo   id lockInterrupti bly(            ) throws Interrupte               dExcep               tion {
    *     sync.acquire I   n   terrup        tibly(1);  
 *   }
 *         pu blic       bo  ol     ean tryLock(lo   ng timeout, T   imeUn    it       unit)
 *            th  rows In   te   rruptedException {  
 *          return sync.tryAcquireNanos(1        , uni   t      .toNano  s(timeout)    );
 *   }     
 * }}</pre>
 *
 * <p>Her             e   i   s a latch class  th    at is like a
   * {@li   nk   java    .ut         il.concurrent.CountDownLatch CountD ownLatch}
 * except t        h         at it    only requires a  single {@cod         e signal} to
 * fire. B e  cause a lat   ch is    non-exclusive, it uses th  e    {@cod e s   har    ed}
 * acquire and releas e methods.
 *
 *  <pre>   {@co            de
     * class Bool      eanLatch {
 *
 *   pr      ivat e static cl   ass    Sy  nc extend   s Ab       stractQueuedS         y        nchr  oniz   er {
 *         boolean isSignalled() { return getState() != 0 ;     }
 *
         *     protected int tryA   cquireShared(int ignore)  {
    *       retur   n isS  ignalled         () ? 1 : -1;
 *        }
 *
 *     p   rotecte     d  b   oolean tr      yReleaseSha  red(int ignor e     ) {
 *       setState(1); 
 *          retu         rn   true;
 *            }
 *    }   
    *
 *   private fi  nal S  ync s   ync = ne w      Syn      c();
 *   pub     lic boolean isSignalled() { return sync.isS      ignal    led()  ; }  
 *     pu  b lic void     sig   nal()               { sync  .releaseShar ed(     1   ); }
 *      public void await() t   hr     ows In        t          errupte          dExc eption      {
 *     s    ync     .      acquireShar     edIn  terruptibly(1    );
 *         }
 *    }}</pre>
   *
 *     @since 1.5
 * @autho  r Doug Lea
 */
public abstract clas   s AbstractQueue  dSynchroni   zer
    extends AbstractOwnableSynchronizer
    implements java.io.Ser ial   izable {

        private st  atic final l         ong serialVersi      on UID     =    73739849725  72414691L;

    /*  *
     * Cre      a tes a new {@code Abst          ractQueuedSynchronizer  } instance
     * with   ini    t    ial  synchronization state of zero   .  
     */
        prote    cted AbstractQueuedSy             n               chroniz     er   (    ) { }

              /*  *
     * Wait queue node       c   lass.
     *
                * <p>The wait queue is  a variant of a "CLH" (Cr       aig, Landin, and
       *  Hagersten   ) lock q     ue  ue. CLH lo        ck   s are normally used  for
     * sp i     nlocks.  We  instead use  them fo      r  blocking sync    hronizers, but
        *            use the sa      me b     asic    tact     ic   o    f holding some        of the co   ntrol
       * in  formati   on    a       bo            ut a thread in th e prede  cessor of i   ts   node .  A
       * "statu    s    " field in    e   ach node keep    s   track of whether a thr ead
     *       sho uld b  lock.  A node is si   gnall   e   d when i     ts predecesso    r
     * releases     .    Eac        h node     of the queue o  therw   is  e   serve    s as a
          * specific    -   notific                    a     tion      -st        yle moni t o     r hold     ing a     singl      e waiting
       * thread. The st   atus field does NOT   control wh   ether t   hreads   are
     * g     r  anted locks etc       though.  A thread may try to acquire if it      is
                   * first                        in t     he queue. But being first d  oes not guar    antee succes            s;
     * i  t only gives th    e righ    t to     contend.  So the currentl y   r         el  eas   ed
     *   contender th  read m  ay n  ee     d      to r   ewai   t.
            *
           * <p>To enq   u eue into a   CLH lock, you atomically   splic     e it in as ne w
     * tail. To dequeue, you just set the  head fi    eld.
               * <pre>   
                *        +          ------+  prev +  -----+       +-----+
       *     head |      | <---- |        | <---- |        |  tail
       *                          +---  ---+               +-----+                  +-----+
                     * </pre>
     *
         * <p>Inser    ti  on i   nto a                    CLH queu  e   re   q   uires only a s  ing  l         e atomic
     * op er    at ion    on "tail", s o ther    e i  s a sim    ple atomic   poin     t        o                f
     *    demarcation  from unqueued to queued.    Simi lar   ly, dequeu ing
               * involve         s only u  pdating the "head". Howe ver, it takes a bit
       * mo  re     work for node  s to determine w             ho the   ir suc    cessors are,
       *        in part to deal with pos        sible ca ncell    ation due    to     timeouts
     * and interr  upts        .
        *           
     * <p>The "p    rev"   links (     not used in       origi   nal C LH   locks),    are mai      nly
       * ne ede  d      to handle     cancellati      on. If a node is cancelled, its    
     *  successor is         (normally    ) relink ed to       a n   on-c          ancelled
                 * predecessor. Fo   r e xp      la  nat  ion o   f                  similar mech         anics in the cas     e
               * of sp in loc   ks, see the papers by Scott and Scher        e           r a    t
      * htt p: /  /www.cs.rochest           er.edu/u/scott/synchro      nization/                  
           *  
     * <p>We also use "ne    x      t         "      link  s to              implement blocki   n     g mechanics    .
     *   The thread      i d for ea  c    h node is kept in it   s own n   od        e,   so a
                      * pred   ecessor signals   th     e           nex       t node to wake up by    t      ra       versing
            * next link to dete    r       m    i           ne which t h       read it is.  Determin       a   tion o  f
              *  su       ccessor         m    ust avoid          races with newly   queue     d nodes to se   t
     * t  he "   next" fiel ds of         their pre   decessor                 s.  This is sol  ve    d
     *      wh      en n   ecessa ry by       checki ng backwar    ds from the atomica      lly
     * updat  ed "tail"     wh   en    a node  '       s successor ap    pears   t      o be null.
        * (O   r, s  aid differently, the next-lin    ks are an optimization
            *            s        o th      at we don't usually nee         d      a back  ward  scan.)
     *
     * <p>Cancellatio   n int   r        odu c es some co  nservatism to th   e   basic
            *       alg          orithms       .  Sin    c e we m   u   st p  oll for cancell                         ation of     other
        * nod   es, we c an mi   ss notici ng whet   her a cancelle d  node is
         * a    head or behind u   s. This    i       s   dealt            with by al   way s unparking
        * succ     ess  ors     upo     n cancellation, allowing      t hem   to                      stab     i    lize on
     *    a new p  redecess                   o               r     , unless       we can         identify an u    nca   n    cell   ed
         *   predecess                  or who w  ill     carry  this    responsibil   ity.
                      *        
        * <p>CL   H queu    es need          a dummy hea   der node         to get started. But
     *         we          don'   t        creat    e t  he m    on  const             r    uct ion, becaus   e it    would be wasted
       * eff          ort if  there is  never con  tention. Instead, the nod   e
       *      is constructed    and      head and tail pointers        ar   e set upon f  irst
     * contention.
                          *       
     *    <p>Thr                eads  waiting on Conditions use the s      ame   no   d  es, but
     * use     an a    d      diti        onal lin   k.  Co    nditions only need to li        nk nodes
       *  in si   mple (      no n-co    ncurrent) linked queues b      ec     a use they are
               * only a         cc      es   sed when exc   lu       sively     held.          Up     o    n   await   ,           a n     ode is
                            * i   nserted into a co nditio n q    ueue     .  Upon    s  ig      nal     ,          the             node is
     *  transferred to the m  ain         queue.  A       special   va    lue of status
     * field is used to   mar  k which q ue     ue   a node is on.
         *
     * <p>Th anks go     to     Dave  Dice, Mark Moir, Victor Luc       hangco, Bi l l 
     *     Schere     r and Mi   chael    Scott    , a  long wi   th  members   of       JSR-16    6                   
           * expert gro     up, for h  elpful          ideas,     disc           uss  io   ns,           and critiqu   es
       * on   the  d   esign of th   is                 class.
           */
     static    fi nal class Node {
                      /** Ma   rker        to                             in  dic         ate a nod   e is    w  aiting in    shared mode      */   
                   s tat       ic         final Node S HARED =   ne       w Node( );
                  /** Marker to  in  dicate a  node is w     a  iting i   n exclus   ive mod e * /
               static f    inal Node EXCLUSIVE  = null;

                         /  ** wa  itS   tatus   va lu e to i    ndicate thread h  a                       s c           ancelle        d */
           stati   c final        int C    A  NCEL LED =       1           ;
        /** wait         Status value to indicate suc    cessor's  t     h read      needs unparki    ng    */   
                           stat   ic final int SIGNAL    = - 1;
            /** waitSt   at     us val      ue to i  ndicate thread is wai      ting      on  condi      t     ion     *    /
              stat ic       final int CO     ND ITION = -2;
        /*   *
                    *      waitSta    tus value t     o  indicate               the nex  t acquir     eS         hared should
         * unc onditionally propagate      
                                */
          static fina   l int P       RO   PAGATE =          -3 ;

                              /**
               * St            at  us fi  eld, taking on                    onl y    the va      lu    es:
                    *         S    IGNAL:     The succes   s           or of              this node  is            (or w       il        l soon be       )
                         *                                            blocke   d (via     pa        rk       ), so the c         urren t node mu          st
               *                     unp    ark i ts successor when it re   leases o    r
            *                   cancels. To avoid races, acquire m   e            thods must       
         *               first indica         te th ey nee      d a sig          nal,  
           *                     t  hen ret  ry the a         t omic a   cquire, and then,
          *                           on failure, block.
                  *         CANCELLED:       T      his node           is c          ancelled      due t    o timeout or interrupt .
               *                              Nodes nev           er leav   e this     sta     te.     In  particula      r,
            *                  a thread wit h canc elled nod     e    never again blocks.
         *             C  ONDI TION      :  This node is curre  ntly     on a condition    queue.
                  *                     It will not       be     use    d as a sync queue no         de
             *                             until t ra            nsferre      d,   at w   hich tim   e t  he stat     u    s
                   *                      will    be     set to       0. (Us     e  of     thi    s    val  ue here has
         *               nothing t    o do with the   o      ther  uses      of the
                   *                         field, but simplifies mechanics.  )
                    *          PR         OP  AGATE:  A re  l   e  aseSh    ared s       hould be propa     g ated to     ot   h        er
          *                        no   des. This is set (  for         head node on     ly   ) i n
                             *                    d                oR    ele aseSh  ared to ens   ure propagat    io     n
                         *                                               continues   , even if other       op  erati   ons hav     e
               *                s     i    nce int    ervened.
            *   0:              None of t            h  e above   
                   *
                                   * T  h    e value     s are arr       anged   n        umer    ica   lly t      o      sim pl    ify use.
                                  * Non-n     egative va     lues mean th    a  t a no de does   n't  need to
              * s    ign   al. So, most code doesn't n      eed t  o check fo    r particular       
                  *      values, just for   sign.
          *
            *   T   he field is initialized to 0 for       normal sync no           des, and
           * CON    D    IT  ION f  or condi  tion   nodes.  It is modified us   i     ng C     AS
                       * (or      when poss ibl      e, unc    onditional vol    atile w rit  es).
                  */
             vo      latile int waitSta    tus;

             /**
                  *  Lin         k to predecessor node that c  urrent      node/t          hre  ad              re       lies            on
             * for checking waitSt     atus.  Ass    igned during enqueuing, and nulled
             * out   (for sa  ke of GC) onl                 y upon               d equ    euing.  Also , upon
              *     cancellat  io      n      of a predecessor, we  shor     t-circuit         while
                 * findin   g a non-     cancelled one   ,      which w    ill a  lways exis    t
                     * bec     a     use the he       ad nod e i        s never cancelled: A n    ode be     comes
                       *    head only as a result of succ       essful a            cquire. A
                  *    cancel              led thread n eve    r su  ccee      ds in a  cquirin    g     ,  and a th  read onl   y  
         * cancels i  tself,  n    ot         any oth    er node.
               */
             volatile Node prev;

        /**
         * Link to     th   e   successo      r no de that           the cu   rrent node/thre      ad
               * unp  ar     ks upon release.   Ass   ig  n       ed d     ur  ing enqueuing, adjusted
          *    when     bypas           sing     cancelled prede     cess   ors      , a nd nulled out ( for
            * s    ak  e of     G C)                 when dequeue    d.  The enq operation does            no t
         * as    si     gn ne  xt fi    eld o     f     a predecessor until after atta       chment,    
                          * so seeing a n   ull next field does not necess    arily      mean that
                 * node is a                                t    end of queue.  H  owever, if  a next fi    eld  appears
          * t o be n   ull   , we ca       n sca  n pre  v's from the tail to
          * do     uble-check.  The next fiel     d of ca          ncelled nod es     is set     to    
                * point      to the       node itsel  f i         nstead of null, to make l   ife
            * e   a  sie      r fo  r isOnSyncQueu  e.
                 */
                volatile   N     ode next  ;

             /**   
                      * T        h      e          thre            ad     that enqu    eued this     node.  Initialized          on
                 * co    ns truc        ti  on   an    d null ed out         a     ft   er use.    
              */ 
           volatile  Thread thread;   

                 /**
         *    Li    nk           t    o n e    x t node waiti    ng             on     condi tio  n, o  r the    spe     c  ia      l
            * value SHARED         .  Because c  ond     it        ion q    ueues       are accessed o      nl     y
             * w hen ho    ldi  n  g in  exclusiv   e m   o  de,    we j      ust                need a simple
                * linked queue    to h         old nodes while they are  waiting on
              * conditions.   The    y     a re      then transferre                 d      t o the   que     ue       to
                   * re    -acqu ire. And beca          use con      ditions can only be         exclusi   ve,
         *      we   save a field by using special valu    e to indica    te   sh                   ared
                * mode.
            */
          Node  nex           t   Waiter;

        /**
                  * Returns true       if    no   de is wai  ting in s   hared        mo      de.
          */
            final      bool       ean   isShared()           {          
                     return     n  extW    aiter ==    SHA   RED;
        }

                        /  *   * 
                     * Returns previ      o   us node, or th        rows NullPointe rExceptio    n if nul  l.
                *       Use       when    prede  ce sso  r cannot be null.      The null      check   could
                       * b      e elid  e   d           ,  but is pre       sent to help the VM  .
         *
         * @retur         n the predecess or of this node               
                */
                 fin            al Node predeces sor() throws Nu    llP oi   nterExce  pti  o     n        {
                          Node p =  prev;
                               i   f    (p == nu ll  ) 
                          throw new NullP ointerException();
                                           else
                     re  turn p     ;
        }

         Node () {    //   Used to e  sta        blish initial head or  SHARED ma    rker
                   }

                 No  de(Th read      t   hr     ead, Node  mode) {        //      Used by addWaite    r
               th  is.nextWaiter = mode;
                           this.thread = thread     ;
                       }

             Node(Thread thread, int        waitStatus       )          { // Used   by Con          ditio         n
               this.waitStat        us = wai   tStat     us;
                     thi  s.thread    = t hrea   d;  
          }
               }

          /*   *
              * Head of the w         ait q ueue    ,   lazily   i       n           i     tialized.     Exc     ept for
     * ini    tial   ization,       it is             modifie  d o    nly via met  hod set Head.  Note:   
       * If he    ad e     xis     t           s, i    ts waitStatus i s guaranteed not to b   e  
                    *  CA   NCELLE    D.
     */
    pri v  ate transient  vo         la    ti l         e  Node           h      ead    ;

       /**         
      *     Tail of the    wait queue, lazily     initialized.      Modified only via
     *        method enq t        o a dd new wait node.
                                 *     /
    private transient vo       l            a  tile Node  t   a  il;     

      /**             
            *    The synchronization       state.
     *  /     
    p     ri    v     ate      v  o      l     atile      in     t s     tate;  

              /**
                 *   Returns the cur     rent               v alu    e     of  s   ynchro n      i   zation sta  t       e.
     * T   his operation has  memory semantics of a {@code   vo              l  atile} read.
     * @   retur      n       cu r re nt         st  a     t  e value
         */   
        pro        tec     ted final int    getS     tat e() {
                retu rn s     tate;
    }

    /  **
           *     Se  ts    the value    of    synchro n     ization state.
        *  This    op    eration  ha    s m    emory  sema  n    tic   s      of a {@code volatil     e    } write.     
     * @param           newState the new st a    t      e va    lue
     */           
       p  rotected final void set  Sta  te(       i  nt     newS  tate)           {
           sta  te = new       Stat  e        ;
    }        

    /**
     * Atomic  a   ll   y sets synch ronization                state to           the given up   dated
     * value if t     he current    sta    t   e v   alue e  qu  al  s the ex   pec   ted  value.
       * This        operat      ion has mem          ory   se  mantics   o   f a {@code volati      le} r      ead
              *   and write     .
                  *
     * @      para    m ex   pect      the expected  va   lue    
     * @pa   r a   m     update the new value
     *    @ret   urn {@c ode t rue} if succes      sful. False return indica         tes that      the    ac   tual
      *                value was n  ot equal to   t he ex    pected value.     
        *     /
    protected fi   nal  boole   a   n comp     are   AndS   etSt     ate(in  t expect, in     t upd    a   te) {
        // See    bel o w for intrinsics    se t   up   t   o       s   u   pport this
                                     return unsafe   .compareAn    dSwapI       n    t(t      h  is, stateOffset,           expect, upd   ate);
    }         

    //     Queuing utiliti e     s        

    /**
                * T   h e n   umber    of n   anoseco   nds     for        which it is faster to spin
         * r   ather than                to use ti  m     ed p     ark.    A     rough    estimat e s         uffices    
                              * to imp     rove    responsiveness     wit  h very s hort timeouts.
            */
    s        tatic final long       sp  in       ForTimeoutT  h       re    sho  ld =        1      000L;

    /**
     *       Inserts n ode i  nto    queue  , ini       tializing if n      eces    s      ar     y.   Se                         e     picture      above.
     * @     param no     d        e the node to   insert
     * @retur  n         node's pr    edeces   sor
       *   /
                  priv ate Node enq (final Node node)    {
                 for (;;)     {
                        Node t = ta  il;
                 if    (  t        == null)  { //         Must initialize
                    if (comp     areA ndSetHead(new    N  ode()  ))
                       tail = head;
                                         } else   {
                       n      ode.p re   v =    t;
                                     if (co     mpareA    nd   SetT ail(t, node)) {
                               t.next   = node;
                             return t;
                                                    }
                 }
                 }    
             }  

    /**
      * Creates a  nd e nque  ues node for c  urrent   thre    ad    a    nd giv               en mod    e.
      *
     * @param mode        N ode.EXCLUSIVE fo   r ex clusi  ve, N      ode.SHA          RED fo  r shared   
        * @return th   e new nod  e
     */
        p  rivate Node           addWaiter(N         ode mode)   {
        No    de node = ne     w Node(T        hr      e   a   d.curr       entThread(), mode);
           // Try t     he fas        t path o   f   enq;     backu  p   to full   enq on f      ailure
                     Node p       r  ed =          tail ;   
            if ( p   red != null) {  
                                      node.prev = pred;    
                            if (com  pareAn   dSetTa  il(pred,   node))         {          
                        pred.next =       n   ode   ;
                retu   rn n     ode;
                                        }
                                  }
         enq(node);
                           return node;
        }

              /  *   *
       * Sets he   ad of queue to be   node, thus   dequeu             ing.    Cal led    only by
            * acq   uire met               hods.  Also    nulls       ou   t unused          fiel d  s for s      ake o f GC
         * an  d to    suppr   ess unnecessar  y   si gna      ls and tr                   aversal        s         .
                  *
     * @p            a      ram no      de the  node
         *      /
    pri   vate void      se    tHead(Node     node) {      
                         head = n  od  e;    
            no  de.thread =       null    ;
           node.  prev =     nu   ll;
       }

    /  *        *
        * Wakes u  p node's su       c   cessor,   if one exi      s      ts.
     *
       * @param no    de the node
     */
    privat            e      void     unparkSuccessor    (Node                      n   o       d      e) {
                 /*
            *   If     status is negative (i.e., p  os   sibly n     eeding signal) try
                 * to c  l    ear          in anticipation   of signa       lling.   I         t is      OK     if    this
                *     fa       ils or       if sta        tus       i s            c      hanged        by wai    ting   thread.
                           */
        int ws = n      od   e.wait           Status  ;
                                    if (ws < 0     )
              compa   r eAn      dSet  WaitStatu    s(node,   ws, 0);

           /*
            * Th       read to     unpark is  h       eld  in succes  sor,      wh    ich is n     o  rma  lly
         * just the n      e  xt node.        B    u          t     if            can   ce      lled or                  apparently  null    ,    
         *                                trave   rse backwards    from     tail t    o find the a  ctual
                            * non-cancelled suc  c                    essor.
                 *   /
             Node s    = n  ode.next;
        if          (s =   = nu ll  |  | s   .wai   tStatus >    0) {
                    s       = null;
                       for (Node  t = ta     il   ;       t           != null && t !=    node; t        = t.prev)
                             if (t.wait        S   tat    us <     = 0)
                                                 s = t;
                  }
        if (       s != null   ) 
                    Loc   kS  upport.unpark(s.thre           a             d);
    }

    /    **
     * Release ac     tio  n   for shared     m             o                      de -- sig       nal   s     s          uccess          or and e    nsures
     * p   ropagation. (N   ot            e: For exclu        siv     e     mo      de   ,    releas   e   just am  ounts
     * to calling un     p   a       r     k    Successo r of head if    it nee            ds signa     l.        )
             */
       privat   e voi d  d  oR     elea     seSh ar    ed()    {
                        / *
             * E nsure t      hat a  release propagate   s, even if there are other
          *  in-progress    a         c   qu  ires/rel   eases.  This   proc         eeds in t   he usual
                *           way     o  f              trying to    u nparkSuccess  or   of hea  d if it needs
           * sig   nal     . But if           it d   oes    not,        statu             s         is set t       o PROP             AGATE to
                     * ensure         that upon       r   elease,     pro             pagat ion continu es .
          * A d   di   tionall  y, we       mu          st   loo  p in c  ase a new              node   is adde    d
                       * w  hile we    a  re d                              oing thi s. A             l        so, un        like o        th  er use        s             of
              * unparkSucc        essor     , w     e need   to kn    ow if   C   AS to re set status     
              * f         ails, if       so rechecking.   
         */
             for (;;)        {
                Node  h =  head   ;  
            i f    (h !=              null && h     != ta       il) { 
                       in  t ws = h.wai     tStatus;
                                if     (   ws   == Node    .SIG     NAL) {
                                if      (!compareAnd   SetWaitSt            a    tus(h,  Node.SIGNAL           ,  0))
                                                               contin              ue  ;                                 // loop       to recheck case s
                                 unpar          kSuc     cessor(h);
                         }
                            else    if (         w   s    == 0 &&
                                           !co    mpar   e    AndSetWaitSt atus(h, 0,     Node. PROPA  GATE))
                      conti       nue;                                     // loop           on   fai    led CAS
                             }
                             if (h ==          head)                                  / / loop if   he  a     d changed
                            break;
        }
       }

    /    **
            *       S       ets  h      ea d of queu      e    , a    nd checks if   successor ma       y     b    e wai ting         
        * in share    d mode, if                 so propagating if ei   th   e       r propagate > 0 or
     * PROPAG   ATE st   atus was set.
     *  
     *       @   p ara             m nod   e the nod  e
           *     @     p      aram p          ropagate the retu       rn     v         a     lue from a tr   yAcq   uireSh   ared
                  *   /
    pr   ivate v   oid   setH  eadAndPropag       ate(             No     de nod       e, int p  r        opagate)    {
        Nod     e h     = he a   d; //  Recor  d old head for    check bel  ow
                               setH   ead(no    de);
                     /*
              * Try to si  gna    l  next queu    ed             no  de if    :
         *      Propagat     ion w   a            s indica   t ed by caller,
           *        or was re     corded (as h.wai               tSt            atus     either     bef      ore
                  *      o     r af ter setH     ead           )  b       y a pr   evious            opera          tion
             *         (no    te: this use     s    s  i          gn-ch               eck of waitS   tatus      beca use
         *      PROPAGATE  status                m     ay tran     sitio          n to SIGN     AL.      ) 
          *    and
         *   Th       e n  ext node is waiti    ng     in  s    hared mo  d  e,
                        *                o r     we   do   n        't know,      be  cause it appe               ar   s   null
                 *
                 * The conserv   atism in both of t    he se  ch     ecks    ma  y cause
         * unnecessar     y     wa     ke-ups,      but     only  when t         her    e ar       e     mult                    iple
         * raci              ng acq     u  ir    es/releases,      s o most   need    sign          al       s now    o r soon
            * anyway.
            */
         i   f   (p     ropagat     e > 0 || h ==      null ||    h       .wai tStatus    < 0        ||
                              (h = he   a       d)       =     =   null || h.waitStatus     < 0) {       
             Node s                         = n    o    de.next;
                    if         (s ==          null ||        s.is   Share   d(  ))
                                     d  oR   eleaseShared();
        }
    }

    // Uti lities  fo   r        various       versio  ns     of acqui      re

    /*   *
     *    Cancel   s an    o   ngoi ng a   t     tem   pt     to acq    uire.
             *
     * @param     node the node
            *       /
    private vo    id can   cel        Acquire(Node node) {
        // Ignore      if node do esn'    t       exist
             if (nod     e ==     nul  l)
                                return;
   
          n   ode.thread = n               ull;
   
                       /    /      Ski p cancel    led predece   ssors 
          Node pred  =      n  od              e.prev;
                 wh    ile (    pre      d.waitStat       us > 0   )
               node.pr      e               v    = pre      d = pred.prev     ;

         //     predNex t is the      apparent no de to unsplice. CASes     be  l          ow will
           // f   ail if  no t  , in whic   h case, we los       t race    vs anot       her can    cel
                       // or signal, so no fu         rt     her a         c     tio   n is necessary .
                           Node    predN       ext  = pred.ne        xt;

                //      C             an        use un    conditional writ e inst              ead       of CAS         here.
                  //     After        this         atomic      step, oth  er Nodes can skip past u s.
         // Before, w  e ar  e free of interference   from o    ther threads.
            node.waitStatu   s =   Node.CANC ELLE  D;

          // If w     e a  re t     he       tail , rem   o                 v   e o     ursel  ves.
                     if  (     node   ==         t     ail && co   mpareAnd      SetT      ail(no         de   , pr  e  d))              {     
                co  mpar     e                A      ndSetNext(pr   ed, pr             edNext, nul      l           );
                    } els      e     {
                 // If succ  ess   or     need     s   signal, try to  set pred's next-link
                    //  so         it    will get o    ne. Oth             erwise wake  it u      p t             o propa    gate.
                    int ws;
                  if (pr   ed         !      = h ead     &&
                          (   (ws      = pred.waitStatus ) ==      Node.S          IGNAL ||
                 (ws <= 0 && compareAndSe  tW   aitStatus(pr      e   d,  ws  ,            Node   .SIGNAL))) &&
                    pred.thread !=         null) {
                          Node nex     t =   node.next;
                              if (nex    t != null && next.waitStat      us <=   0)       
                                                                      c   ompareAnd  Se   tNe xt(pred, pre    dN                  e x   t, next);
                  }            else {
                         unparkSucces sor(node);
            }

                   node.      next = node; //       help G  C
                }  
        }

       /**
     * Checks and updat    es    stat    us for a n    ode     that failed            to ac   quire.
          * Returns tr      ue        if    thread s    hould   b   lock.        This is the m        ai    n    signal
          *                   control in all acquire loops.  Requires        that pred == no  de.prev.
     *
     * @p        a ram           pred node's predecess              or holdin   g status
     * @para  m     node the node
          * @return {@co      de t    rue} i    f       t      hrea         d                     sho  uld   block   
               *  /
        pri            vate        static b               oo   l    ean  shouldParkAfterFai  l       edAc    quir   e(Node pred, Node nod   e)     {
          int ws = pred.wait Stat u s            ;
             if (        ws = = Node.      SI        GNAL )     
                                /*
                 * Th i  s node     h as already s   et status ask     in                g a release
                         *         to sig  na  l it, so i  t   can safely park.
                         */
                 return true;
             if (ws >  0) {
                /*
                          * Pred  ecessor     was can  cel    led. Skip over  predecessors and
                              * in    d icat       e retry         .
             *    /    
                   d    o {  
                      n ode.pr   ev = p      r  ed = pre   d.prev;   
                  } while  (pred  .wa it     Status    > 0);
                 pred.next = no  de;
        } else {
                   /*
             * wa  i tStatus must be 0 or PROPA       GATE.  Indicate tha          t we
             *        ne ed a     si        gnal    , but                    don          't park  yet.  Calle r         wi   ll need t  o
                                * retry to make sure it cannot acqu    ire befo        re park     i    ng.
                                        *     /
                  compareAn  d   SetWaitStatus    (pred, ws, Nod           e.SIGNAL);  
          }
        re  turn false;
        }

            /**
              * Convenience method      to i    nt       errupt      cur       rent thr   ead.    
     *                /
      static void selfInterrupt(   ) {
             Thread.cu  rr     entThre  ad(   )                     .interrupt(   );
          }

         /**
     * C         o       nven   ien        ce      method to pa           rk     and               t   hen  chec   k if i            nterrupted
                     *
      * @return {            @  cod        e true}    if i    nte        rr           upt    ed
     */
                                         p      riv   a  te final bool        ean p            arkAnd   C     hec     k  I   nterrupt()      {       
            Loc     kSu                 ppor       t.   park(this);
        re  turn  Thread.int     errupted ();
                                    }
   
    /*
         * Various fla vors of              acq  uir      e    , varying in exclusive   /sh    ared and        
           * contro    l mode    s.  Each is mos   tly the same, b    ut a      nnoyingly
           *       diffe  rent.       O  nly a little bit of factoring     is poss  ibl  e due to
          * intera    ctions of exception mechanics         (including ensuring t     h    at we
                * cancel if tryAcqu   i   re thr   ows exception)     a  nd other   control, at
        * lea st not wi  tho            ut hurting perf     orm anc     e    too much.
          */

     /**
                *       A c       q   uire   s   in      exclu  si  v   e un                inte   rrup tible mode fo  r t   h    read already in   
          * queue.            Used by condi              ti   on wait metho         ds as well as acquire .
       *
     * @param node the node
     *     @param arg   the     ac                 qu  ire argument
     * @return {@        co   de true  }    i  f interrupted while    wai   tin   g
     */
    fina   l         b   oolean   acquireQueued(fin     al   No           de       node, i nt arg) {         
             boolean     f   ailed =    t     r     ue;
                try {
               bool   ea        n in   terrupted =     fals        e ;    
                       fo   r (;  ;)       {    
                             fi nal    Node    p   = node.   p   redecessor();
                        if (p ==                  head && tryAcq  uire  (arg)   ) {
                            setHead(node);
                             p .    next = null ; // h  elp GC
                              fai         led =       f    als  e;
                               ret   ur n         interru   pted;
                    }
                                if (shouldPark          AfterFailedAcquire(p,    node) &&
                                              parkAndC          heckInter      rupt()            )
                                      interr    upted =    tr   ue;
                 }
                     }  finally       {
            if (f   aile  d)
                                     cancelAc    quire(node);
             }
         }

    /**
       * Acq   uires in exclu  sive     interruptible mode.
     * @p   aram arg the ac      quire argume   nt
              */
    pri      vate void do          Acqu    ireI   nterruptibly(int arg)
            thro     ws In  te  r ruptedE    xcepti     on      {
                    fin  al Node node     = ad    dWaiter(No    d  e      .EX  CLUSIVE);     
        boolean f   a   ile       d =   true;
              try  {
                 for (;;  )     {
                          final Node p = n    ode.   predecesso         r();
                 if (p ==     head      &&       tr      yA         c    qui     re(arg)) {   
                                    setH  ead(node       );   
                             p.next         = null;    //             hel            p G      C
                         fa  il           ed = false;
                                       return  ;
                          }
                     i      f (shou   ldPa        rkAfter     FailedAcq       ui    re(p, no  de) &&
                                       parkAndCheckInterrupt(   ))
                          thr          ow new InterruptedException       ();
                          }
          } finally    {   
                     if (failed)
                                 ca    n   celAcquire(n  od  e);       
            }
        }

       /**
               * Acq    u        ir   es in ex  clusive         t             im   ed mode.
     *
     * @pa    ram arg    the a   cqu   ire argumen   t
           *   @p    aram    nan       osTi  meout   m   ax wait time
      * @return {@   code   tr     ue} if acquired
          *           /
            private   boolean doAcquireNanos(i     nt arg, long na   nosTimeout)
             th rows Int  errup   te  dExce   pt  io    n {
         if     (nano   sTimeout <=      0L    )
               r  eturn fa lse;
               f    inal long d  eadline = Sys  tem.n   anoTime() + nanosTimeout ;
        f   inal N ode       nod  e =     addWa  iter(Node.EXCLUSIVE );
        boolean failed = true;
        try     {
             for   (;       ;) {
                      fin  al Node p = node.pre   dece   ssor();
                        if (p ==   head && tryA                       cquire(ar     g)) {
                        setHe   ad    (n          ode)  ;
                          p       .next = null; // h    elp GC
                           failed = false;
                       r     eturn true;
                                }
                  nanosTimeout = deadline - S         ystem.nanoTim  e();   
                                  if (      na nosTi  meout <=       0L)  
                                         retu        rn false         ;    
                      if     (s       houldParkAfterFailedA           cquire(p, node) &       &
                                      na   nosTi     m  eout  > spi    n F           orTimeoutThreshold)
                                                  Lo   c   kSuppo      rt.park   Na      n     os(th        is, na    nosTim      eo    u    t);
                    if (Thread.interr   upted())
                           throw new Inter           rupte  dExcept    ion();
            }
              }        finally {    
                     if (failed)
                                                               cancelA   cquire(node   );
               }
         }

     /**
     *    Ac q  uires in sha red uninterruptible m     ode.    
     * @pa            ram a   rg the acquire      a  rgument
         */
          p  riva te vo  id     doAcquir   eShared    (int arg)             {
        final Node node = addWaiter( N  ode    .       SHAR       ED);
           boolean failed       = true;
            tr  y {
                        boo    lean interrupted    = false;    
               for     (;     ;) {
                            f   inal Node p      = node.predecessor    ();
                 if    (p == head) {
                            i    nt r  = tr    yA  cqu       ir    eSh    ar    ed(arg);
                                                          if (      r >= 0) {
                                             s     etHeadAndPro      p   agate(node, r);
                                                 p.next     = null; //     he l    p    GC
                                  if (interrupt   ed)
                                         selfInterrupt();      
                                        fa   il   ed = f   alse;
                                   re  t  u   rn;
                                }
                     }
                          if (shouldPar  kAf     terFail  edAcqui   re (p, n ode   )     &&
                                                      parkAndCh  e    ckIn  terrupt(    ))
                           inter  rupted = true      ;
                      }
            }     fi      n   al  ly {
            if  (failed     )
                            cancelAcquire(n  ode);
        }
    }   

       /**
     * Acqu ires in sha     r            ed    int   errup   ti      ble mode  .
     * @param arg the a     c     quire  argument
     */
    priv   a   te void d     o     AcquireSha       redInter   ru  ptibly(int arg) 
                            throws Interr    u          ptedExc    epti   on {
                final Node   node     =  addWaiter(Node.SHARE              D              );
            boolean f        ailed  =    true;
              try {
            for (;;) {
                         f   inal Node p =     no       de.pred  e   ce sso  r()      ;
                            if (             p == head  ) {
                                        int r = tryAcquireShared(arg);
                                     if (r >=     0) {
                                               s       etHe   adAndPr   opag   ate(nod    e, r)  ;
                                     p.next = nu          ll ; // help GC
                                                fai    led      = false     ;
                                     return;
                                    }
                  }
                             i      f (    shou   ldParkAfterFai ledAcqui   re(p, nod    e        ) &&
                                parkAn     dC  heckInterrupt())  
                       thr         ow ne    w      InterruptedEx  ception();
                }
               } fin ally {
               if (fai      led)
                         cance        lAcquir   e(node);
               }
    }

       /   **
     *      Acquires           in shar    ed t  imed mod  e.
       *
     *    @par  am arg           the acquire ar  gument
     * @pa      ram nanosTim  eout max wait time
               *    @r  eturn    {@code tr      ue}     if  acquired
       */
    private    boolean   doAcquireSharedNa   nos(i nt     a rg, long nanosTime   out)
            t     hrows In       ter   rupt       edException {
        if (nanosTimeo    ut <=  0L)  
                     re   t  urn false;
                final       long deadli  ne    =    System.nanoTi me() + nan  os          Timeout;
                           final Node       no    de =   addWaiter(Node.SHARE   D);
        boolean  failed =    true ;
        t ry        {
            for (;;) {
                          fi    nal N  ode p = node.predecessor()  ;
                          if (p =     = head) {
                           int   r      = try    A   cqu  ireShared(arg    );
                    i  f (  r      >= 0) {
                                            se        tHeadAndPropagate(node, r);    
                                             p.next = null; //     hel     p     GC 
                                            fa  iled = fal   se;
                                                    return t       rue;
                               }
                    }
                na nosTimeo   ut = deadl  ine -   System.nanoTime(   );
                           if (        nanos     Timeout    <           = 0L)
                              return      false;    
                   if (shouldPar         k   AfterFailedAcquire(p, node) &&   
                                 nano  sTimeout > spinForTimeoutThreshold)
                             L   ock       Suppo     rt.p ark   Na  nos(this,   na  nos Timeout)   ;
                  if   (Thread.    inter  rupted())
                                      throw new Interrupt     edExcept  ion();   
             }
        } finally            {
                       if (failed)
                                    cance    lAc      quire(       node);
            }
           }

    // Main exported meth    ods

    /**
                 *         A  t   tempts      t  o     acquire in excl   usive            mode.       This method s h    ould query
       * if the st  ate o           f the object permits it to be acq          u    ir   ed in the
     * e   x  c  lus  iv   e mode, and i  f so to ac       quire it.
          *    
     * <p>T his meth     o    d is always invoked              b    y the thr ead performing 
          * acquir    e.  If this   method reports               failure,   the  ac      qui      re   method
       * may queue the thre   ad,     if it is not alread y queu    ed, until it is
                   * sig    nal    led     b      y         a           release       from s    ome       other thre     ad. This can b  e used
        *          to  implem  ent method {@link Lock   #          tryL     ock()}.
     *
             * <p>The         d  efault
           * imp lementation thro w  s {@lin    k U      n   suppo     rtedOperationException         }     .
           *
      * @ p aram arg       th e  acqui  re    argum  ent         . This value is            always  the  one
     *             pas    se             d t     o    an      acquire           method, or is  the valu   e sav  ed on entry
     *        to     a condition w   ait.  The valu         e               is ot  herw    ise   uninterpre            ted
      *        and can rep  res  ent          an    ything you like.
                  * @return {@ code true} if    succ   es             sful. Upon s    uccess, thi        s object has
     *                been    acquir ed     .
       *                @t   h   rows     IllegalMoni   to        rStat  e    Exception    if   acquiring would plac   e    this
       *          sy        nchron     izer in an ill e       gal state. This exceptio  n must be
          *          th r own     i     n a consiste   nt fash  ion          f   or syn    chroniza     tion to    work
            *         c orrectl  y.
     *     @t      hro    ws UnsupportedOpe    rationEx   ception     if exclusive mode    i  s n ot supp         orted
     */
    protected boolean t    ry        Acqu    i re(         int arg) {
             t  hro    w new U     ns  upportedOper   ationExceptio    n();
    }
    
    /**
              *     Attem         pts t             o set the state to refl ect a release in excl       usive
     *           m         ode.
      *
      *    <p>This met     hod i    s   always invoked by the   thread pe   r   formin      g     rel   ease.
     *
             *     <p>     The def   ault    i  mplemen      tation th   rows
     * {@link Unsupporte    dOpe       rationExc      epti     on}.
           *
      * @param ar  g the rele              ase argume        nt .           This value   is always the one
      *           passed to a releas e met     hod, or the cu      rrent state      va    lue u    pon
           *              entry t   o a condition wait.  The      value i   s otherwi  se
             *              unint     erp  reted and can            repres ent     anythi    n                         g you like.
            *                  @return {@code        tr    u  e}     if this obj    ect is   now      in a fully released
        *                 s  ta   te     , s         o    th     at an  y wa  i    ti ng thread s may    attempt to   a       cqu ire;
       *         and {@c              od      e false} oth erwise.
            * @throws Ille     galMonito rState    E    xception        if r eleasing would pla       c         e   t     his
     *              synch    ronizer in an   illegal state. Th   is except  ion must b         e
     *            thrown in a  co     ns  istent    fashion     for synchronizat    ion to        work
      *           cor      re     ctly        .
          * @throws Unsuppo      r     te dOperationE   xception      if exclusive mode is not supported
     */
       protected               bool   ean t ryR     elease(int arg)     {
                      t  hrow new UnsupportedOper   ation      Excep tio   n()     ;   
     }
     
    /        **
     * Attempts   to acquire in shared      mode. This   met      hod sho   u  ld query if
     * the state of the   obj ect perm    its it to   be acqui  red     i n the shared
     * mode, and       if so to   acqu   ire     it.
      *
          * <  p>    Th  i     s  method  is always invoked b    y          the thread pe        rfor    ming
     * acquire.  If th     is m    ethod reports f  ailure, the acq     u  ire     method
        *       may q     ueue th     e thread, if it is not already queued, until it is
     * signalled by a    relea  se             fro    m so    me         ot  h   er thread.
       *
     * <p>T          he default implementation   thr     ows {@link
     * Unsupported             O  per                ationExcep      t ion}   .
     *
     * @param arg     the ac       quire argumen  t. This value is always     th  e one
     *        passed to an acquire m   ethod,    o   r     is t   he value      saved on entry
     *                to a         condition wa    it.  The valu    e is otherwise uninte   rpreted
     *          an    d   ca  n rep    r es   ent       anything you li  ke.   
                 *      @re  tu          rn    a n          e         gative value on failure; zero i   f acqui sition i            n shared
     *                      mode      succeeded but no subs equent sh       ared-mode      acquire can
     *         succeed; and a  posit           ive va          lue if acquis  ition in shared
     *                   mode suc  ce   eded and subsequent shared-mode    acqu   ires  might
       *           als o succee d, in whic     h       case a s ubsequ  ent   waiting thr      ead
       *                      mu        st check      availabil    ity         . (Sup   port for three different    
        *         r  eturn values enables t h  is method to be use   d in  contex ts
      *                             whe   re ac    quires    only   sometimes   act excl  usive     ly.    )  Upon
     *                    success, thi     s obje  ct   ha s be en   acquir  ed.
       * @   throws     Ill    egalMonito rStateException if acquiri  ng    would place this     
           *                     sy   nc  hronizer        in an illeg    al state. This e     xcep        tion must be
     *             th     rown in a       consistent fashion for s    ynchronizat   io   n to work 
     *                           c  orrec    tly   .
            * @throws   UnsupportedOperationException     if    shar     ed mode is not    supporte   d
                */
         prote          c  ted in  t try  Acq      ui r     eSh          ared(           int a     rg) {
                throw new Uns     upported  Operati onE        xc        eption();   
    }    

           /**
       * Attempts                 t  o set the state to   reflect a r      elease in shared   mod  e.
     *
     * <       p>Thi      s me    thod is always in   vok   ed by the thread performin     g release. 
     *
          *    <p>The  default imp   lementati   on throws
              *             {@link UnsupportedOperationException}.
     *
     * @par   a   m   arg the release argu           ment.       This v        alue is  always the one
     *           passed to a re            lease me   th od, or  the current s      tate val       ue u   po      n
            *                 entry to a condi  ti       on wait.  The value is otherwis      e
        *        uni  nterpreted an  d can represent anything      you like.
         * @           r        eturn {@code tr    ue} if    this relea  se of shared        mode ma       y permit a
         *              wai  t            ing acquire (shared or exclusive) to succeed; and
      *               {@c ode false} other    wis    e
            * @thr        ows I    l legalM onit    or    St      ateException if releasing would place    th is
     *         synchroni   zer in an    illegal state. This exce             ption mu         st be  
     *                         thro wn in a    consistent fashion       for synchroni  zation to work
     *         correc   tly.
          *          @thr     ows U   ns   upportedOperationEx  cepti    on if shared    mode is not s     uppo     rt     ed
                 *       /
      protected            boo   lean tryRe l  easeSha red(int ar   g   ) {
        t        hrow new Unsuppo    rtedOperatio   nExcepti  on();
    }

         /**
           * Returns {@code true} if synchron   ization is held exclusively  wi         th
     * respect to the curre   nt (c     alling) th   rea   d.  Th  is  met         hod is invoked     
            *  u  pon       each ca     ll    to    a no     n-w     aiting       {@lin  k Con     d  iti     onObje       c    t} me     thod.
     * (Wai      ting metho       ds in  stead      invoke {@ li nk #re   lease}.)
     *
           *      <p>The    default imp   leme  ntation throws      {@link
         * Unsuppor tedO p   e   rationException}. Thi       s          method is       invoked
                      *  internally only w        ithin {   @link C  ondition        Objec     t} meth    o   ds, s    o nee d
     *   no      t be defined if cond  ition            s are no t u     sed.
        *
     *     @retur n {@code  t    rue} if synchronizati  on is h   eld exclusivel      y;      
              *                       {@c  ode   false} ot   herwise
     * @thro ws Unsupp  or              te  dOpera tionE                  x             ception if   condit    ion      s        are   not support      e   d
     *   /
    protected boolean isHeldExclusively() {
            throw n    ew         Uns                upported                   Op   erationExc ept  ion(    );
             }

    /  **
     * Acquires in e   xclusive m  ode, ignor    ing interrupt   s.  Implemented
     * by invoking at lea    st           o  nce {@link #tr   yAcqu           ire},
      * r     eturning on success   .  Other       wise       the thread is queu  ed, p   ossibly
       * repe   atedl    y         blockin             g and             unblock    ing,      invok   ing {@l  ink
     *  #tryAc quir     e} until s  uccess.       This m    ethod can   be   used
         * to i     mpl  ement   method {@li    nk    Lock#lock}. 
     *
     * @   param arg the a c   qu   ire        argume  nt         .     Th        is v  alue     is conveyed to
     *              {@link #tryAcquire} bu  t is otherwise u  ninterpreted and
     *                  can repr     esent anythin  g you li      ke.    
     */
          pu   blic fin  al void a           c  quire              (int     arg) {   
             i     f (!   tryAc  quir      e (arg) &&
              ac    quireQ  ueued(addWa    i   ter(Node.EXCLUSIVE), arg))
              selfInterrupt();
      }   

    /**
         *    A  cq  uires     i  n exclusive mod         e   , abor     ting i f interrupted.
     * Implemented by first  check    ing interr     upt status       ,       t     hen inv   oking
     * a  t    l     ea    st     onc        e     {@link #tryAcquire}, returning on
       * success.  O  the       rwise t     he thread is que     ued, possibly repeatedly
               * b     locki      n   g and unblocking, inv   oking {@link #try   Ac  quire}
     * until s        uccess o r the threa d is   in   terrupted.  This method can b  e    
     * u  sed to imp  lement   method      {@link Lock    #lo  ckI   nte  rr  upti   bly}.
           *
     * @param       ar            g the acquire argument.  This val     ue is conv  ey   ed to
      *        {   @link #tryAc    q uire}   bu        t is        oth  er   wise uninterpr   eted and
     *                       can represen   t anything you li    ke.
       * @th  rows   Interrupted Exception if     the c    urr       en   t t       hr     ead     is interrupted  
             */
                public final   void acquireInterruptibly(     int       arg)
            t      hrows InterruptedEx    ce       ption {
           if    (Th read.int errupted()    )
              th     row new Inte     rrupt   edExc   ep tion();
                         if (!tryAcqui   re(arg))
                                doAcq      u   ir    eInte rruptibly(   a       rg  );
        }

    /**
            *         Attemp   ts to       acq              uir  e in exclusive   mode, aborting    i        f inter  r   upte d,
     * and fa  ili  n      g        i  f the given timeout elapses.           Implemented by    f  irst
     *   checking in  terru  pt statu s, t   hen invoking a   t least once {@link
                 * #tr yAcquire}, retur    n  ing on s          u cc   ess.     O    therw      ise, t  he thread is
     * que    ued, pos  s  ibly repeatedly blocking and un     blocking, invokin  g
     *    {@li nk  #t    ryAcquir    e} until suc cess o    r the  thread   is inte rrupte d
     * or the timeout elapses.  Th    is   method can b  e us    ed t o implement
     *  method  {@link    Lock#t  ry    L  ock(lo  ng, TimeUnit)}.
     *
       *    @param arg    the acq    uire argu        m ent.  This value is    co    nveyed to
                 *        {      @lin k #tryAcquire} but is o        therwise unint    e    rpreted an     d
      *                 can represen              t anyth  in    g       you  like.
                * @param nanosTim  e out the maximum number of nanoseconds to wait
     *     @      return {@code true}        if ac qu     ired; {@code f  alse} if    timed o ut
         * @throws Interrupted          E      xception if the           current thr   ead is i     nter  rupted
     */
     publ   ic fina l boolean tryA  cqui  reNa nos(in t arg, long nanosTime  out    )
            thr            ows      I          nterrupte          dE         xception {
          if (    Thread.interrupted())
                    thro              w new Interrup  ted E xception(   );
           r     eturn tryA       cquir              e            (arg)     ||
                doAcquireNanos(   arg,    nanosT   imeout);
    }

           /**
        * Releases in    exc    lusive mo  de.  Implemented by  u  nblo    cking           one or
      *  more threads if {@li    nk #t   ryRelease} returns true.
                    * This    method can be         used to implement met    hod {  @lin       k Lo    ck#unlock}.
     *
     *   @param arg the release arg  umen  t.   Th is val   ue        is con veyed to
     *                                {@l         ink #tryRelease} but is otherwis    e       uninterpreted and
             *        can repr      esent anything      you like.   
     * @r  e   turn  the value returned from           {@li  nk #tryRel   ease}
          */    
    publi   c   final boolean release(in   t ar   g      ) {
        if (        tryR   elease(     arg)) { 
                          Node h =   hea  d;
                 i               f (h != null & & h.waitStatus != 0     )
                                    unparkSuccessor(h);
                               r           e      turn tru   e;
        }
         return f        alse   ;
     }

     /**
     *     Acquir    es in  shared mode,    ignoring     i n           t  e rrupts.  I   mple            ment   ed                by
     *             first invoking at l    east once     {@  link #    t      ryAcqu   ireShare       d  },
     * returning                   on succes  s.  Othe    rw ise the      thr     e ad   is q ueued, p    o    ssibly     
       *              repeatedly bl   o     ck       ing an       d      unbloc   kin   g, invoking {@link
     * #tr  yAcquire  Sha   red}      until  succ      ess.
     *
       * @param a           rg the acq    ui re argument.   T       h  is value i  s conve    ye   d to
     *           {@link #tryAcq uireShared}       b    ut   i  s otherwi    se   uninterpre   ted
     *            and can re        present          a  nything      you     like.            
         */
       public final void acquireShared(int     arg) {
        i  f    (tryAcquire       Shared(arg)   < 0     )
                              doAcquireSh      a    red(arg      )    ;
    }

    /**
     *  Acquires in  shared mod   e,       abo     rt     ing if           interrupted.        Implem   en      ted
                   * by f     irs t   che  cking in  terrupt stat     us, then invoking          at least     on   ce
       * {     @link #tryAcqu     ireShared}, returning on success.  Othe  r    wise the
          * thread is que          ue   d  , po ssibly   repea     te  dl      y block   i       ng and unblock   ing,
           * invoking {@link #t  r  y   Ac  qui  reSha  red} until suc     cess or t   he thread
     *    is   interrup      t  ed  .
     * @par am ar     g     the acqu    ire argumen      t.
     * This va   lue is co   nveyed to {@link #tr      yA            cquire   S  h      are    d} but is
     * otherwise u      nint       er     p   re   te  d an   d can rep       resent anyt             hing
     *  y  o  u like.
                   * @throws In           terruptedEx c e     ption if the curren    t thread is     interru   pte d
     */
    pub     lic final v oid acq uireSharedInt  erruptibly(in   t     arg)
               throws I   nterruptedException {
            if (    Thread.i     nterr  upted   ())
             throw ne     w            Inte               r            r   uptedExcepti        on();       
               if                 (tryAcqu   ireSh     ared(a   rg) < 0         )
            doA  cquir  eSh aredInter         rupt ib     ly(arg);
    }

    /*             *
        * Attempts to ac q    uire   i   n s hared mod  e,       a  borting if  interru   pted, and
         *  fail     ing     if the   give  n tim     eout       elapses.  Implemented by fi    rst
     * c     hecki    ng interrup   t status          , th en invoking       at   l      east once {@link
     * #tryAcqu  i     r eShared   }, r    eturn      ing             on    succ   ess.  Otherwise, the       
      * thread is  queued, possibly r   ep               eatedl   y blocking and   unblocking,
     *   invok ing {@link #tryAcq  uireS    hare     d} until succes         s or      th   e t     hr                           ead
            * is i       nterru          pted or     the timeout    elapses.
      *
        * @param       arg the ac  quire argumen      t.  This v   alue  i  s c      onveyed t              o
       *               {@link    #tr             y     A    cquireShared} but is other    wise uninterpret  ed
     *        and            can represent anything you      like.
                        * @  param nanosTimeout the m      ax     imum numb   er of nan   oseconds to wai    t
               * @r    eturn {@code t   ru     e}     if  acq uired;     {@co        de        f   alse} if tim            ed out
         *   @t   hr  ows          InterruptedExce   ption if the     current     threa       d is inte  rru     pted
                                    */
    public final boolean tr     y   Acqui     re Sh    are    dN anos(int arg,    long          nanosTimeout)
                                         thro   ws Interru p      tedException  {
              if (Threa  d.interrup      t  ed())
                throw    n    ew Int     erruptedException();
             re    turn tr   yAc         quireShared(arg)  >= 0 ||   
                   do Acqu    i    reShar      edNanos(arg, nano     sTimeo      ut  );
      }

    /**
           *   Rele  ase  s         in shared m     ode        .    I      mplemen te           d by unblocking one   o  r more
     * thread      s if {@link  #tryRele   aseSha    red} ret  urns tr  ue  .
             *
         * @pa   ram    a    rg t         he r  e       l       ease argument.  Thi   s value is         co      nveyed to
           *                {@l  ink # tryReleaseShared       } b    ut is o  therwise uni  nterpreted
                *               an   d can  represent any   t    hing you      like.
        * @return th       e      val ue retur ned from {@link #tryRelease Shared   }
     */
                 pub lic fina l boole  an       r  eleaseShared(int     arg  )  {
        if    (tryR     eleas        eShared(arg)) {
              doRele  aseSha        red     ();
                return true;   
          }
        return false;       
     }

    // Q  ueue in        spect       ion m     ethods

    /**
     * Queri es whethe  r an  y thre   ads a  re waiti       ng to acq         uire. No           te that
     * because cancel   lation    s d ue to in      ter       rupts a      nd timeouts      ma    y  oc      cu     r
     * at any      ti     me, a   {@  code true } re    tu       rn d           oes not   guarantee      that a ny   
               * other threa      d   w    il   l ever acquire.
         *   
     * <p>In thi     s implementatio   n, this                ope    r     ati on ret   urns in
     *   constant time.  
     *
      * @return {@c    ode    true} i    f   the  re ma              y be ot  her thread  s waitin    g      to acq uire
     *     /
    publ   ic final bo      olea n hasQu         euedThreads() {
                      return   hea d !=        tail;
         }   

         /*   *
         * Queries w  hether a   ny    thre  ads have ev er   c    onte  nded t    o acq    ui    re t   his
       * synchro      nizer    ;    that i  s if a  n acq      uire  method      has         ever blo         cked.
     *
                        * <p>In this implem    ent      ation, t               his operation re      turns in 
       *                  consta  nt t ime .
                *
              *       @return {          @code tr  ue} if     there         ha s       ever be      en    c    ont         ention
       */
        p ub    lic fi          nal boolean hasConten ded() {
             return head !     =      null;    
      }

    /** 
     * Re   turns the        first    (longes        t-wa                             iting   ) th   read in the queue, o   r
        *    {@co   d e    null   } i                   f no thread      s are cu  rrently    queued   .    
                       * 
      *     <p>I n   th    is imp    lement atio           n, t           h   is operatio n    normally returns        in
         * consta       nt ti  me, but m ay iterate upon   contention if other threads are
     * con   currentl              y modifyi   ng  t  he queue.    
            *
     * @return the fir       st (lo                      nge    st-wai  ting)   thread in the queue        , or
         *           {@code    null} if no  thre        a  ds are currently queued     
     *          /  
    pu           blic f  inal Thread getFi    rstQu  e   u    edThread() {
              /         / ha   ndl   e only     fast path,    else re   lay
           return (head    =    = tail) ?   nu      ll       : fullGetFirstQueuedThread();
    }

    /**
            *   Ve   rsion   o    f ge   tFi        rstQueuedThread called when fast     path fails
      */
     pri  vate Thre  a    d fullGetFir    stQu        e        ue    d Thr      ead() {
                       /*
                   * The fir   st node i    s n ormally head.     nex   t. Try to get its
                 * thread    field, ensuri     ng         con siste  nt read   s:     If thread
                              * f   i  eld is nulled out or s.   p rev is no longer head,    then
                                           * s   ome other   threa   d(s) concurrentl y performed setH  e     a d in
             * betwee     n  s ome of o  ur    re  ads. We try     t     his twice   b   efore
            *                  resorting to traver                sal.
                      */    
                     No     de h ,   s;
        Thread st        ;
        if (((h = head) != null && (s     = h.next) != null &&
                s.prev =  = he  a    d &         & (st = s.t  hread) != null) ||     
                         ((h = head) != null && (s = h.next) != n          ull     &&
                    s.prev ==   he   ad && (st = s.  th         read       )    !    = null))
             re  turn st;

           /    *       
                * Hea       d's    next field mig      ht   no         t     have been set  yet, or may     have  
          * been unse   t aft      er           se tHead. So we must check t   o see i  f tail
          * is        ac     tually first     no   de. If   no    t  , we contin     ue on, s  afely 
         * tra    v  ersing from  tail back     to head     to f       ind first,
         * gua          ra     nteeing termin                ation.
         *      /

                     Node t    =    tail;
            Thre   ad f   irst   Th  read = null;
          w     h ile (t !           =   nu      ll && t         != head   )        {
              Th  rea        d tt   = t   .t          hr     ead;
                if (tt !  = nul    l)
                               fir st  Th          read = tt;
                     t = t.prev;
           }
        r    et    ur   n firstThre  ad;
               }

               /**
     * Re turns tr          ue if the give     n thread is      curr   ently   queued.
     *       
       * <p >T  hi   s implementation       traverses    t he queue to determine
     *     presen    ce of the given thread.
     *
     * @par  am thread the thread
       * @return { @cod e     tru e} if           the given thread is      o  n t         he         queue
       * @thro         w  s    NullPoin    terExcept  ion if the thre    ad     is null
           */
           public f       inal boolea   n isQueue  d(Thread threa     d)    {
        if    (thread == null)   
                           th        row new NullP  ointerExcept    ion(  );  
                   for (Node p = tail; p != null; p = p.prev)
                  if (p.thre     ad ==     thread        )
                retu rn true;
                  return f    als        e;
          }

    /     **
     * R  eturns           {@c           o       de        true}         if       th e apparent first que   ued t        hr    ead       , i    f o          ne     
     * e xists, is waitin   g in     ex  clusive m  ode.  If this method returns
        * {@  code true},   and th    e curr   e    nt      thread is attemptin           g         to acquire  in
     * shared m   ode   (th      at is,   this m ethod is invoked from          {@link
         * #t    ryAc     quir    eSh  ared}) then it               is guarantee d       that      the           current   thre     ad
                        * is not the first queued thre   ad   .  Used o   n             ly as    a he  uristic in
             * Reen  trant   R           eadWr     iteLock.
      */
    final      bool  ean apparentlyFirstQueuedIsExclusive() {
        N ode h, s;
          return (  h = head) != null &&
                           (s =        h.next)                      !   = null      &&
                     !s     .isShare     d()         &&
              s .thre   a           d != null;
    }

    /**
     * Que               ries whether   an  y threads         have           been wa     iting     to acquire   longer
     * than the cu   rre        nt        thread.
         *            
        * <p>An inv   oc     ation of this method     is equivalent    t   o   (but m        ay       be
      *  mor       e efficient t han):
           *  <pre> {@c   ode
      * g     etF irstQ       u  euedThread() != Th  read.c      urrentThread() &&
     *   ha  sQueu ed      Thr eads    ()}</pre>
     *
     * <p>Note that be     cause    cancellat ion  s due   to interrupts a         nd
       * ti    m  eo         uts m      ay o       ccur at any  time,     a {@cod         e true}  return does not
     * guar a    n   t            ee that some ot           her    thread will acquire bef      ore the current
     *   th  read.   Lik       ewi     se, it   i  s                                  po    ssible f or another threa d to win      a
     * ra      ce      to   enqueue    af  ter this    method has    ret   urned {@code fals    e}     ,
     *   due      to     th e       queue being e  mpty.
       *
         * <p> This     method is d   esigned to  be       u     sed by a fair  synch ronize  r to
     * avoi        d <a     href="AbstractQu         e    uedSynchronizer#barging   "    >barging </a>.
           * Such a synchr    oni   zer's {  @link      #tryAcquire} met      h     od should                   return   
     *        {@code false},       and         i       ts   {@link #tryAcquireShare d} method should
             *   return a nega     ti    ve value, if  this           method     re    turns {@     cod       e true}
       * (unless      this   is a re entrant acquire  ).  F     or examp    l    e,     the {@   code
             * tr        yAcq    uire } meth   od      for a fai  r, reentr            ant, exclusive mode     
     * synch    r    onizer m  ig    ht look like this    :
        *
     *  <   pre> {@code
     * protec    ted bo      olean t ryAcquire        (int arg)    {
              *   if (i   sHe    ldExclusi     vely()) {
       *           //        A reentrant acquire  ; increment h           old count
                     *         r                  e   turn true   ;
     *   } el     se if (hasQu    eued Predecess     ors()) {
      *     retu  rn f  alse;
         *       } else {
        *     // try to a             cqu    ire normally
          *   }
       * }}    </pre>
     * 
               * @r e               tur   n     {@c ode tru   e} if t  here is a    qu     eued t hr    e                ad      pre       ce   ding the
     *                curren t   t  hrea  d, and           {@    code false} if the    cur    r    ent thread
      *           is     at the head of the       q      ueue  or     the que       ue is e                   mpty        
     * @since 1.7       
         */
    pu      blic fi   na    l b     oolean hasQ  ueuedPredece  sso  r s()  {    
        // T  he c orrectnes  s     of thi   s depends on head  b     eing i   nit  ia li   zed
            // befor     e tail and on   hea    d.next being ac     cura       te if             t   he   curr    ent
        // thr     ead is firs   t in queu  e.
             N ode t =     tail; // Read fiel   ds in re     ver       se init ialization order
        N    ode h = head;
          N   o de  s;
              return h !=         t     &&
                   ((s     = h.nex                  t  ) == nul   l ||    s.thread != Thread.c      urrentT         hre    a            d());
               }


    // Instrum  entation      and     m   onitori      ng         methods

    /**
            * Returns     an e       stimat  e of t     he n  umber of thread           s waiting     to
       * a       cquire   .  The value i          s only an  estimat    e beca  use the numb     er o    f
              * threads may change     dynamically wh    ile this metho    d        t   r       a  verses
            * i   nt       e  rn     al dat  a        struc       tures      .      This method is d  esign ed for use in     
                * m    onitoring system state, not fo  r  syn   chro nizati  on
          * control.
         *
        * @retu rn the estimat    e d n   umber   of threads waiti  ng to a   cquir      e
     * /
           public final in     t      ge      tQue       ueL            ength(    ) {
                    i               nt n = 0;
        for (Node p      = tail;                     p !   = n      ull; p =          p.prev)   {
            if (p.t   h rea    d !=       null)
                      ++n;
              }
                   ret urn n;
    }

    /**
      *    Returns a collecti    o   n c  ontaining thre       ad s  t   hat may        be w    aiting to
     * acquire.  Beca use th     e actual set o  f thread   s ma   y change      
     *  dynamically w                    hi          le constructing thi      s re   sult       , t  he returned 
        * co     llection is only a         best                -effo       rt estima                    te.  T  h  e          elem    e nts of the
       * r  eturned    co      llec t  io  n a re    in no part                 i   cular order.      Th  is      metho      d is
               *      d    esi         gne   d to   facilitate       con    stru   c   ti   o   n of    su  bclasses    that     provide
     * more   extensive       m onitor    ing facilities.
           *
       * @retu      rn the collection   of threads   
        */          
    public final Collection<    Thread> getQueued    T   hreads() {
            Arra   yList<T    hread> lis     t  = new ArrayLi st<Thread>();
               for (No   de    p = tail; p                       != null;    p    = p.pr  ev) {
             Thre ad t = p.thr       ead;
                      if      (t !=  nul  l)  
                list.   add(t);
        }
        retur     n l  ist;
    }

    /**
          * Ret urns      a collection contai       ning t hreads t  hat  ma          y       b    e wait  ing   to
       *    acqu ire   in exc       lusive              m    ode. This has           t  h  e same propert  i      es   
            * as {@link #get   Queu     edThreads} e              xce   pt that it only returns
     *     those threa    ds    waiting due to   an ex       clusive acqu      i   re  .
         *
     * @return t    he co  llec tion o     f thre ads
             *   /
       public final Collectio  n<Thread>   g   etExclusiveQu    eu  edThr      eads() {
                     ArrayList<   Th            read               > lis t = new A              rr   ayList<Thr   ead>();
                       for (Node       p = tai    l; p != null; p =         p.prev) {
                       if (!p.i  sShared(   ))  {
                               Threa           d t        = p  .thread;  
                           if (t != null)
                            l      ist.add(t)    ;
            }
        }       
        ret      urn l  ist;
      }

    /* *
             * Ret   urns a collection c      ontaining threads that may be       waiti              ng         t  o
     * acquire in shared mode.    This      has t    h  e       same properties
        * as     {@lin   k       #get Q   ueued  Threads} except th    at it only returns
     * those     threads waiting due    to a share  d   acquire.
                    *           
               * @  return the collection   of threads
     */
    public final Collect    ion<Thread> getSh     ar   edQueu edThreads()   {
                        Array   Lis   t<Thread> li   s  t   =  new ArrayList<Thre              ad>(   );
               for (Node p =     tail; p != null; p =         p    .pr ev) {
                i      f (p.i  s Shared()) {
                    Thread  t = p.thread    ;
                if    (t      != nul l)  
                                                     lis      t.a         dd(   t);
                    }
        }
        r  etur n li st;
    }

        /**
                        * Return  s a s  t       ring identi fying this synchronize  r, as well as     its state     . 
     * T    he               state,     in   brac          kets, include     s the String {@cod     e   "St   at  e ="}
     *             foll               owed by   t he c  ur   ren     t v      a     lue of {@l   ink #getState}, and eit    her
     * {@code "no  ne mpty"}    or {@code "empty               "} depending on whe the     r the
        *    qu    eue is empty.
          *
              * @ret  urn a string identifying th    is synchroniz           er,   as well as its   stat        e
          */
    publi    c Stri ng toStr i   ng() {
        int s    =           getS        tat e(   );
              String q  =   h  asQueuedTh      reads() ? "non" : "";
                return super.toString() +
                      "[State = " +      s + ", "   + q + "    empty que      ue]";
         }

   
    //     Int       erna  l suppo        r  t me          t     hods for Condi   tion      s 

       /**
       * Re  t     urn         s true if a      node,     alwa     ys on  e that w     as initially place      d on
         * a cond  ition queue, is now     wai     t   ing to re  acquire on sync        qu       eue.
        * @param nod  e t   he node
     * @retur  n   t  rue            if is         reacquirin   g
     */
                      f  i   nal boo      lean isOnSyncQueue(Node        node)  {
             if (no   de.waitStatus   == Node.CONDITION         || node.prev ==      nul        l)
            re   t urn f     alse;
                if (     node   .  next   != nu           ll) // If ha    s          successo   r, it must be on queue
            return true;
        /*
                  * n           ode  .prev can         be non-n  ull, but not yet on queue because
             *  the     CAS to place it on queue         c  an fail. So w   e have    to
         * traverse f  ro      m tail to mak   e sure it actually m ade              it.        It
          * wi       ll a    lwa    ys be ne                     ar the tail in cal  l     s to this m  ethod, a  nd
           * unles  s the      C  AS failed (  which is unlike    ly), it will be
               * there, s     o we   h  ardly ever        travers                   e much.
          */
                return findNodeFro    m     T   a     il          (n         ode);
    }

        /**   
     * Returns true if no    de is o   n sync queue by searching backw     ards from tail    .
     * Called only whe n needed  by isOnSyncQ      ue ue.
     * @r    e  turn tr     ue if present
     */
    pri vate b        oole  an findNodeF romTail(No   de n  ode) {
               No de  t        = t     ail   ;
        for (;;)  {
                  if (t ==         no    de)
                     return true;
                 if (t == null)
                      return false  ;
                         t = t.prev;
         }
    }

    /**       
          * Transf         ers a         node        from a c    ondi   tion queu   e on   to sync qu           eue.
     *     Ret urns true if   succ  essful   .
     * @param node the   node
             * @return true if      succe  ssfully tr                      an      sferred (else the node was             
      * cancelled before signal)
     */
    final boolean   transferFo rS  ig        nal(Node node) {  
           /*  
         * If cannot c    han   ge waitStatus, the node has been can       celled.
               */
                if (!com    pareAnd    SetWa    itS   tatu    s(nod   e, Node.CO       N      D     ITION,   0))
            return false;

        /*
          * Splice     on   to   queu        e and try to   set  w       aitS tatus of p   redecessor to    
                 * in     dic  ate t  hat th     read      is (p  robably) waiting.   If cancel            led     o  r
         * attempt       to set wa    it    Status            fa     ils, wa ke up to        resync (in     whi  ch
         * ca   se the w  aitSt    atus can be       tra    nsiently     a   n    d  harmless ly w    rong)      .
         */
           N     ode p = enq(      nod  e);
        int ws = p.waitStatus;
        if    (ws > 0  || !co     mpareAndSe   tWai  tStatus(p, w    s, N  ode.       S      IGNAL))
               LockSupp      ort.unpark(node.   t      hread);
                     return true;
        }

    /**
     * Tra nsfers no    de  ,   if    ne cessary,  to  sync qu   eu      e      after a cancelle d w  ait.
     * Ret         urn     s true if   thread      w         as cancelled   before being signalle      d.
     *
               * @param no   de the no     de
     * @r etu   rn t   rue if cance   lle       d before     the node was signalled
        */
    final boolean        transfer     Aft     erCancelledWai  t(N ode no    de       ) {
               if (compare    And        Se  tWaitS         tatus(node, No   de.COND           ITIO         N, 0)) {
                 enq              ( nod        e);
                        retur   n true;
        }
          /        *    
         * If    we l   ost      out to   a signa  l(), then we   can't proceed
             * until    i    t finishes its enq().  C        ancelling d     uring    an   
             * inco    mpl       ete transfer is bo   th rare       and tr          ansient, so just
                * s      pin.
                */
        while (!isOnSyncQueue(node))
               Th         read.yi     el  d();
        return f    alse;
          }

    /**
         * Invok    es r     elea   se with cu  r         ren    t state value; retur  ns         saved st  ate.
     * Cancels node and   t                         hrows exception on failure.
     * @p  aram node the c     ondition node for thi  s w    ait
     * @ret   urn previous sync            state
     */
    final int     f   ull     yRelease(Node node) {
                 bo   olean failed      = true;
        try {
                               int savedState = getState( );
                 if   (   rele    ase  (sav      edState)) {
                         failed         =     false               ;
                               ret ur n savedState;
                               } else {
                              thro    w new  IllegalMonitorStateExcep  tion();
                    }
             } finally {
                if (  failed)
                        node.waitStatus = Node.C   AN   C      ELLE      D;
             }
    }

    // Instrumenta   tion methods fo   r con         ditions

      /** 
                  * Quer    ies  whe  ther the given ConditionObject
     * uses this synch     ronize r as its    lock.
     *
       * @param condition t     he conditio    n
     *         @    retu    rn   {@  cod     e true} if owned
     * @throws NullPointerExc     eption if the condition is nu        ll
     */
    public final boolean ow ns   (Conditio   nObje ct  condit  ion) {
        retu    rn conditio    n.isOwnedBy(t       his);
       }

            /**
         * Queries w hether any   threa    ds are waiting o     n the given c   ondi   tion
     *      associated with                     th        is synchron izer. Note that because time outs
                 * a   n d interrupts m  ay occur at an y time, a {@co   de true} return
     * does not guarantee that         a future {@cod   e s      ign   al}    will      a  waken
     * any thr     eads.    T hi   s me   tho     d is   d e signed primarily f   or use in
     * monitorin       g of th  e sy  st    em      s   tate.
           *
     * @par  am condition the condition
        * @return {@code tru     e} if there are any waiting t  hreads
        * @throws  Il    legalM    onitorStat       eException if exclusive synchronization
      *          is n    ot held
         * @throws  IllegalArgumentException if the given                     c       ondi    tion is
     *                         not      ass           ociated      with      this sy  nchro  ni     z  er   
     *      @throws Nul   lPointerE               xc  eption if    the       con   dition     is      null
              */
    public final     boolean hasWaiters(Cond       itionObject condition) {
         if (!owns(c     ondit ion))
               throw new Ille  galArgumentExcep   tion( "Not owner  ");
                 return           condition.has  Wai    ter s();
    }

               /**
      * Returns an estim     ate of th    e number of th      reads waiti ng on the
      *              given     condition asso    ciated with this       sy nchronizer. Note that
     * because t   imeouts and interrupts ma    y occur at any t   i   m    e,   the
             * estimate serves   o    nly  as an upper     bound on the actual    number of
     * waiter    s.    Thi    s me   thod is   designed for us    e in mon    it    ori               ng of the
       * syst               em      state,    not for synchroniz  ation control.
          *
     * @    param condition the condi   tion
     * @   return the estimated number       of waiting thre  ads
     * @throw     s IllegalMoni     torSta          teException if exclusive synch    ron ization
     *         i  s not h     eld
     * @throws IllegalArgumentExcepti o n if th  e giv                      en  c o      ndition is
      *               not associated w   ith t    his synchronizer
          * @throws NullPointerExc           eption        if the condition i s null
       */   
    public final int getWai    tQ     ueu  eLength(Co nd     itionObject c       ondit   ion)  {
              if (!own       s(condition))
            thr   ow new IllegalArg   umentExcep  tion("Not owner  ");
                 return    condition.getWaitQu  e   ueL ength();
    }  

    /**
         * Returns a co llec   tion contai      ning tho    se th reads that     may be
     * waiti  ng on the given condition associat      ed     with th         is
     *    synchronizer.  Bec       ause the actual set     of threads may chan     ge         
     *     dynamically                  while c   onstructi    n  g this result, the returned
          * collection is only   a best-effort estim  at     e. The elements of the
                * r eturne d col lect   ion are i     n no part   icular order.
      * 
     * @  param condition        the       condition    
          * @r  eturn t  he collection of thre    ad      s
     * @throws   Illegal         M    onitorStateException if exclu   s     ive sy     nchr on    ization
     *           is not held
        * @t   hrows IllegalArgumentException        if the g     i    ve  n        condit    ion is
     *                not ass  oc     iated with t    his synchronizer
     * @th rows N     ullP    ointerEx         ce  ption if the conditio     n is       null   
          */
    public final Collection<T hread>   getWaitingThreads(Con        d         iti o   nObject condi       tion) {
        if (!owns(condition))
              throw n         e   w  I  llega    lArgumentEx   ceptio  n("Not owner");
          r     et    urn c   ondition.get   WaitingThread     s();
    }

    /    **
      * Condition    imple  mentation for        a {@li    nk
     * Abstrac  tQu euedSynch        ronizer} serv       ing    as the b asis of      a    {@l   ink
     * Lock} imple   mentatio n.
     *
       * <p>     Method documentation f      or this cl    ass descr    ibes mec hanics,
     * n ot beh  avio ral speci  fication    s fr    om the point of view o     f Lo      ck
     *           and     Condit   ion users. Exp      orted v  ersio ns of this class will in
            * general nee     d      to be acco mpanied by docu       mentation descri bing
     * con      dition  semantics t        ha    t rely on tho           se of the as    soc iated
           * {@code A            bstractQueuedSynchron        ize   r     }.
     *
     * <p>    This class is Serial   izable,   but all field  s are transient,
     *  so deser  ial   ized conditions have  no waiters.
              */
        public class         ConditionObjec t imp     lem   ents Condition, java.io    .Serializable {
                   p      rivate static final long serialVersionUID    = 117398487257     2414699           L;
        /  ** First node o   f condit     ion queue. *     /
          priv      ate transien    t  Node firstWai      ter;
            /** Last node of conditi   on queue. */
        private tr   ans  ient Node lastW   ai    ter;

        /**  
                * Cre  ates     a new   { @   code Conditio   nObject}    instance.
              */
              public ConditionObject() { }

           // Inte rnal m  ethod s

                 /**
         *   Adds a new wai  ter to wait queue.
                  * @return its new wait no   de
                  */
            priva   te Node addConditionWaiter() {
            N   ode t = lastWaiter;
            //          If lastWa    iter is cancelled   , clean out.
                     i   f (   t != null &    &   t.waitStatus != Node.CONDITION)    {
                   unlinkCancell edWaiters(   );
                t = last   W   aiter;
               }
                  Nod   e node = n ew Node(Thread.curr     entThread(), Node.CONDIT        ION);
                    if (     t ==               null)
                    f irstW   aiter = node;     
                            else      
                   t  .nextWaiter = node;
               lastWaiter =   node;
               return    node;
          }

        /**
           * Removes and transfers nodes    until     hit non -ca     nce  ll ed one or
           * nu   ll. Split  out from signal in part t   o e     ncourage co     mpilers
            * to inline the ca    se of no waiters.             
         * @param first (non-null) the first node    on condition queue
         */
           private void doSignal(No  de fir       st) {  
                 do {
                if    ( (firstWaite       r = first.nextWaiter) == nul    l)
                        la           stWaiter = null      ;
                       first     .nextWaiter    = null;
                       } while (!tr   a   nsferForSignal(first) &&
                                (first       = firstWaiter)   != null);
        }

        /**  
                  * Removes and transfers all nod                es.
         * @param first (non-null) the first node on condition q   ueue
         */
        private void d    oSignalAll   (Node first)  {
            lastWaiter   = firstWaiter =      null;
             do {    
                Node next = fir   st    .next   Waiter;
                    first.nextWaiter = null;
                     transferForSignal(     fir  s    t);
                f         irst      = next;     
               } while (first !=   null);
            }

          /**
         * Unl     in ks         cancelle  d waiter    nodes from condition queue.
         * Called only while holding        lock. Th is  is called wh en
          * cance   llatio  n o ccurre  d   during    condition wait, and upon
         * insertion of a new w        aiter when lastW  ait   er is seen to have   
         * b       een cancelled. This method is needed   t    o      avoid          garbage
             * re t    e  ntion i  n t     he absence of signals. So even though it   may
         * require         a full traver   sa    l, it comes into play o         nly when
            *  timeouts or cancellations occur in  t     he absence o   f
         * signals. It traverses all nodes rather than    stopp  in    g at a
         * particu   lar target to unlink all pointers     to garbage no des
                * without        re    quiring many re-traversals during ca   ncella  tion
         * storms.
         */
        privat e void unli      nkCan    celledW     aiters() {
            Node t = fir     s   tWaiter;
            Node tr       ail       = null;
            while    (t != null ) {
                Node next = t.nextWaiter;
                       if (t.waitStatus !=    Nod     e.  CONDITION) {
                          t.ne      xtWaiter = null;
                        if (t    rail == n    ull)
                              firstWaiter = next;
                       else
                            trail.nextWaiter    = next;
                        if (next == null)
                        lastWaiter = trail;
                                        }
                         else
                        tr   ail = t;
                t = next;
                }
        }

           //       p    ublic meth   ods

            /**
         * Mov    es the   lo ngest-waiting thread, if one  ex   i  sts, from the
         * wait queue for thi s   condition to      the wait queue f     or the
         *   ow   ning   lock.     
              *
         *   @throws IllegalMonitorStateException if {@link #isHeldExclusiv  e   ly}
              *          r   eturns {@code false}
         */
          p     ub lic final void signal() {
                 if   (!isHeldExclusiv ely())
                      thr   ow ne w Il     legalM   oni      torStateExce  p tion();
             Node first =            first   Waiter;
            if (f irst !=   null)
                doSignal(first);
        }

        /   **
                * Moves all threads fr   om the wait queu    e fo r th       is condition to
           * the wait        queue        for the owni   ng lock.
               *
         * @throws  Il   legalMonitorSta   teException if {@link #isHeldExclusively}
         *                    returns {@cod   e fa     lse}
             *     /
        public       final void si   gnalAll() {
            if   (   !is   H    eldExclusively())
                   throw ne w IllegalMonitorStateExc eption();
                 Node first = first  Waiter;
                   if ( first != null       )
                  d   oSignalAll(    first);
        }

          /**
           * Implements uninterrupt ible condition       w   a       i  t.
          * <ol>
              * <li> Save lock state returned b   y {@link #getStat    e}.
           * <li> Invoke {@link #release} with saved state as  argument,
         *      throwing IllegalMonitorSta   teExcepti   on     if it fa     ils.
         * <li> Block     unt    il signal   led.
           *   <li> Reacquire by in       vok     ing  specialized version of
                *      {@link #      ac qui  re} with  saved state as argument.
                * </ol>
            */
        publ  ic    final void      awaitUninterrup tibly() {
            Node node     = addConditionW   aiter();
            in  t savedState = f   ullyRelease(node);
            boolean in        terrupted = false;
                              while (!is   O   nSy     ncQueue  (node)) {
                LockSupport.par    k(this);
                    i   f (Thread    .interrupted())
                    interrupted = true;
            }
            if (acquireQueue     d(node, savedStat   e) || interrupted)
                   selfInterrup    t();
        }
    
        /*
              * For interruptible waits, we n eed to track whether  to thr    ow
          * InterruptedException, if interru pted while blocked on
         * condition, versus reinterrupt  curre   nt thread  , if
         * interrupted while blocked w    aiting to  re-acquire.
         */

        /** Mode meaning to reinterrupt on exit from wait */
        private stati  c final int REINTERRU     PT =  1;
        /** M   ode meaning to throw Int   erru     ptedException on exit from       wait */
        private static final int     THR   OW_IE    = -1   ;

        /**
               * Checks for inte  rrupt, returning TH  ROW_I       E if interrupted
         * before signa   lled, REINTERRUPT if after signalled, or
         * 0 if not inter   rupted.
             */
        private i  nt checkInterrup     tWhile       Waiting  (Node node) {
            return Threa   d.interrupte   d() ?      
                      (transferAfte    rCancelledWait(node) ?     THROW_IE : RE   INTERRUPT) :
                0;
        }

        /**
         * Thro     ws InterruptedEx     ception  , reinterrupts curren   t  thread, or
             * does nothing, depend ing o       n mode      .
         */
        priv  ate void reportInterruptAfterW   a  it(int     interru    ptMode)
            throws Interru ptedExce  pti    on {
            if (interruptMode == THROW_IE)
                   throw new I   nterruptedE  xcept   ion();
                            else if (interruptMode ==       REINTER RUPT)
                se    lfInter   rupt();
           }

        /**
         *   Impl  ements interruptible condition wait.
         *    <o        l>   
         * <  li> If current thread is interrupte       d, throw  Interrupt   edExce ptio        n.
         * <li> Save lo   ck state          retu  rned by {@lin   k #getState}       .
         * <    li> Invoke {@link #release}    wi       th          sa    ved s  tate as argument,
         *            throwing Ille  galMonitorStateException if it fai     ls.
                   * <li> Bloc k until signalled o  r in   terrupt ed.
         * <li> Re acquire by inv  oking speciali     zed version of
             *      {@         link #acquire} with saved     state as argument.
                    * <li> If i        nt        errupted while blocked in step 4, throw      InterruptedEx  ception.
               * </ol>
         *  /
        public final void await(    ) throws Inte  rruptedException {
              if (Thread.interrupted())
                    throw new InterruptedException();
              Node node          = addCond   ition   Waiter();
            int savedState = fu  llyRelease(node);    
            int interruptMode = 0;
                    while (!isOnSyncQ  ueue(node)) {
                  LockSupport.park(this);
                if ((inte rruptMode = checkInterruptWhileW  aiting(n           ode)) != 0)
                    b     reak;
            }
            if (acqu     ireQueued(node,    savedState) && inter ruptMode != THROW_IE)
                     interruptMode  = RE   INTERRUPT;
            if (node.nextW  aiter != null) // clean up     if ca    ncelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAf  terWait(interruptMode);    
        }

        /**
          * Implements timed condition wait.
              * <ol>
         * <li> If current threa  d is interrupted, throw InterruptedE xception.
              * <li> Save lock state returned by      {@link    #getState}.
         * <li> Invoke {@link #release} with     saved state as argum en      t,
         *      throwing Ille  galMo   nitorStateException if it fails.
         * <li> B    lock until  signalled, interrupted, or timed out.
         * <li> Reacquire by invoking specialized versio   n     of
            *      {@link #acqui    re} with saved    state as argument.
             * <li> If i  nterrupted while blocked i     n step 4,   throw InterruptedEx  ception.
         * </ol>
              */
         public final l      ong awaitNanos(long nanosTi  meout)
                    throws Interrupted  Exception {
            if (Thread.interrupted())
                          throw new InterruptedExc eption();
             Node node = a   ddCondi tionWaiter();
            int savedState = fullyRelease       (node);
            final long de  adline = System.na  noTime()     + nanosTimeout;
            int interruptMode = 0;
            while (!i    sOnSyncQueue(node)) {
                   if (nan osTimeout <= 0L) {    
                    transferAfterCancelledWait(node);
                       break;
                }
                     if     (nanosTimeout > = spinForTimeoutThreshold)
                      LockSupport.parkN  anos(this, nanosTimeout);
                if ((interruptMode = che    ckInte   rrup  tWhileW   aiting(node))       != 0)
                        break;
                nanosTimeout = deadline - System.nanoTime();
            }
             if (acquireQueued(nod     e    , savedState) && interruptM    ode    != THROW_IE)
                          interruptM ode = REINTERRUPT;
            if (node.nextWaiter != nul  l)
                  unlinkCanc   ell   edWa   iters();
            if (interruptMode != 0)
                    reportInterruptAfterWait(interru   ptMode);
                 return deadline - Syste m.nanoTime();
        }

        /**
           * Implements absolute timed con   dition wait.
         * <ol>
         * <li> If current thread  is interrupted, throw InterruptedException.
         * <li> Save lock state re turned by {@link #getState}.
                 * <li> Invoke {@link #releas  e} with saved state as argumen  t,
         *      throwing IllegalMonitorSta    teException if it fails.
             * <li> Block until signalled, interru              pted, or timed out.
              * <li> Reacquir    e by in   voking     specialized v    ersion of
         *        {  @link #acqu   ire} with saved state as argument.
         * <li> If interrupted wh       ile block       ed in step 4, throw Interru  ptedException.
         *  <li> If ti med out while bl  ocked in ste  p 4,  return false, else true.
         * <    /ol>
         *  /
        public final boolean      awaitUntil(    Date deadline)  
                throws Interrupt  edException {
            long abstime = deadline.getTime();
            if (Thread.interrupted())
                      t    hrow new InterruptedException();
             Node node = addConditionWaiter();
            int savedState = fullyRel    ease(node);
                   boolean timedout = false;
            int interruptMode = 0;
               while (!isOnSyncQueue(node)) {
                  if (System.currentTimeMillis() > abstime) {
                           timedout = transferAfterCancelledWait(node);
                    break;    
                }
                LockSupport.parkUntil(this   ,   abstime);
                if ((interruptMode = c     heckI      nterruptWhileWaiting  (node)) != 0)
                    break;
            }
            if (acquireQ ueued(node, s    avedState) && interruptMode != THROW_IE)
                interruptMode     = REINTERRUPT;
            if (node.nextWaiter != null)
                  unlinkCancelledWaiters();
                 if (  int  errupt           Mode ! = 0)
                  reportInterruptAfterWait(inter r  uptMode);
               return !t     imedout;
        }

        /**
         * Implements timed conditi   on wait.
         * <ol>
               * <li> If current thread is interrupted, t   hrow In    terruptedException.
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@      link #release} with saved state as argument,
         *      throwin        g IllegalMonitorStateException if i     t fails  .
         * <li> Block un    til      signalled, interrupte   d, or timed out.
         * <li> Reacquire by invoking specialized version of
              *      {@link #ac   quire}     w   ith saved state as      argument.
         * <li> If interrupted while blocked i  n step 4, thr  ow     InterruptedException.
            * <li> If timed out whi     le blocked in step 4, return false, else tru    e.
         * </ol>
         */
        publ   ic final    boolean await(long time, TimeUnit unit)
                throws InterruptedException {
               long nanosTimeout = uni t.toNanos(time);
            if (Thread.interrupted())
                throw new I   nterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelea   se(node);
            final long deadline = System.nanoTime() + nanosTimeout;
               boole   an timedout = false;
                int interr     uptMode = 0;
               while (!isOnSy             ncQueue(nod    e)) {
                if (nanosTimeout <= 0L) {
                    timedout = transferAfterCancelledWait(node);
                       break;
                }
                if (nanosTimeout >= spinForTi meoutThreshold)
                        LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkIn     terruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = dea    dline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                i nterruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
                if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
              return !timedo    ut;
        }

        //  support for instrumentation

        /**
             * Re     turns true if this condition was created by the given
         * synchro  nization object.
         *
         * @return {@code true} if owned
            */
        final boolean isOwnedBy(AbstractQueuedSynchronizer sync) {
            return sync == AbstractQueuedSynchron  izer.this;
        }

        /**
         * Queries w   hether any threads are waiting on t    his condition.
         * I    mplements {@link AbstractQueuedSynchronizer#hasWaiters(C   onditionObject)}.
         *
         * @return {@code true} if there are any       waiting threads
         * @throws IllegalMonitorStateExceptio         n if {@link #isHeldExclusively}
            *         returns {@code false}
             */
        protected final boo  lean hasWaite rs() {
            if (!isHeldExclusively())
                throw new IllegalMoni    torStateException( );
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus    == Node.CONDITION)
                    return true;
            }
            return fal    se;
        }

        /**
         * Returns        an estimate of t       he number of threads waiting on
         * this c   ondition.
         * I    mplements {@link AbstractQueuedSynchronizer#getWaitQueueLength(ConditionObject) }.
         *
         * @return the estimated    number of waiting thr    eads
         * @throws Illega lMonitorStateException if {@link #isHeldExclusively}
         *           returns {@code false}
         */
        protected final i   nt getWaitQueueLength() {
            if (!isHeldExclusively())
                throw new IllegalMonito  rStateExcepti     on();
            int n = 0;
                  for (Node w = firstWaiter; w != null; w = w.nextW   aiter) {
                if (w.waitStatus == Node.CONDITION)
                    ++n;
                }
            re   turn n;
        }

          /**
         * Returns a collection containing         those threads that may be
         * waiting on this Condition.
         * Implements {@link AbstractQueu  edSynchroni zer#getWaitingThreads(ConditionObject)}.
         *
         * @return the collection of threads
         * @throws Ille         galMonitorStateException if {@link #isHeldExclusively}
         *         returns {@code false}
         */
        protected final Collection<Thread> getWaiti ngTh reads() {
               if (!isHeldExclusively())
                throw new IllegalMonitor  StateException();
            ArrayList<Thread> list      = new ArrayList<Thread>();
               fo r (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus ==   Node.CONDITION) {
                    Thread t = w.thread;
                         if (t != null)
                        list.add(t);
                }
            }
            return l ist;
        }
    }

    /**
     * Setup to support compareAndSet. We need to natively implement
     * this here: For the sake of permitting future enh  ancements, we
     * cannot explicitly subclass AtomicInteger, which would b  e
     * efficient and useful otherwise. So, as the less    er of evils, we
     * natively implement using hotspot intrinsics API. And while we
     * are at it, we do the same for other CASable fields (whi   ch could
     * otherwise be done with     atomic field updaters     ).
     */
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private stat  ic final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                (AbstractQueuedSynchronizer.class.getDeclaredField(    "state"));
            headOffset = unsafe.ob    jectFieldOffset
                (AbstractQueuedSynchronizer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                (AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                (Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                (Node.class.getDeclaredField("next"));

        } catch (Exception ex) { throw new Error(ex); }
    }

    /**
     * CAS head field. Used only by enq.
     */
    private final boolean compareAndSetHead(Node update) {
           retur     n unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    /**
     * CAS tail field. Used only by enq.
     */
    private final boolean compareAn   dSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
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
