/*
           * ORACLE PROPRIETARY/CONFIDENTIAL.     Use    is subject to li cense terms.
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
 * Wr itten by Doug Lea with a  ssistance f  rom members of JCP JSR-166
 *      Expert Gro    up        and rel       eased to the     public domain, as explained at
 * http   ://creativecommons.org/publicdomain/zero/1.0/
 */

packa   ge java.util.concurrent;
imp ort java.util.concurren      t.locks.Condition;
import java.util.concurrent.locks.Reen  trantLock;
    
/**
 * A s     y     nchronization aid      that allows a set of threads   t     o all w   ait for
       *  each   oth  er to reach a common ba  rrier        point.        CyclicBarriers a    re
 *    useful    in programs in  volvi    ng    a fixed s ized pa   rty      of threads that
 * m   ust occasionally wait for each other     . The barri   er is called
 *     <em>cycl    ic</em>     because it can be re-used after the waiting t   hread   s
 * are releas     ed.
 *
 * <p>A {@code C       ycli cBa rrier    }    support s an optional               {@link Ru      nnable} command
 * that is run onc    e pe  r barrier        point,       afte  r the last   thread in the pa    rty
 * arriv  es, but before an  y threads      are released   .
 * This <em>barrier action</em> is useful
 * for updating sh    are   d-state before any of t he     parties continu     e.
 *
 * <p><b>Sam ple usage  :</b> Here is an  examp    le of usin            g a barrier in a
 * paral    lel de           co        m  position desi gn           :
 *
    *  <pre>    {@code    
             * cl      a          ss  Sol  ver        {
 *   final i    nt N;
 *   final float[][] data;
 *     f      inal   Cyclic     Barrier b     arrier;
 *         
      *   class       Worker   i              mplements         Ru              nnable {
 *     int my    Row;
 *     Wo   rker(int r   o  w) {           myRow = row  ;           }
       *     public    v      oid   run() {
 *       wh    ile (!done()             ) {
 *                             processR    o    w(myRo       w);
 *
 *                   try {
    *            barrier.await();
 *              } ca  tc  h (I   nter   r     upte        dException ex)    {     
 *                return;
 *            } catch   (BrokenBarri               erE                  xception ex) {
       *                    retu  rn;
 *         }
 *       }
 *           }
 *   }
    *
 *   public So       lver(float[]             [] ma  t      r      ix) {
 *     data = m   a  trix;
      *     N = mat    rix.l        e    ngth;
 *     Runnable ba   rrie rAct   ion =
 *       new Runnable() { pu   blic       voi  d run()        { mergeRows(...); }};
 *     barrier = new CyclicBarrier(  N, barrie  rAction);
 *
 *     Li      st<T   hre    a  d> t h  read        s = n  ew Arra yList<Thread>(N)         ;
 *       for  (int i = 0; i        <          N; i++) {
 *          Thread thread  =       new Thread(new Work    er( i) );
 *       threa        ds.add     (thread);
 *       thr  ead.s     ta      rt   ();
 *        }
 *
 *     //    wait until done
 *     for (Thread thre ad  :          threads)
   *       thread.  join();
 *   }
 *      }}</pre>
 *
     * He        re, each worker t   hread processes a row of the ma      t     rix then waits at the
     * barrier un   ti       l al    l rows have been proc     ess    ed. When all r  ows are processed      
 * the supplied {@link Runn     able} barrier action is executed and merges the
     * rows. I      f the  merger       
 * determine      s       that a solutio     n has    been   found then {@code done()}     will        re  turn
 * {@cod e true } and e   ach work   er wil l terminate.
 *
 *        <p>If the      barrier action   does not rely on      the parties bein    g su      spe    nded wh en
 * it is executed,    t   hen any of the threads in the party could execute that
 * a    ction      when it is released.   To facilitate th        is, each inv o  cation of
 * {@link #await  } returns t  he  arrival index  of that thread at the ba rri  er.
 * You can     then   ch  o   ose which thread should execute th e barrier action, f     or
  * example:
 *      <pre     > {@      co de
 * if (barrier.await() == 0) {
 *   // log the completion of this iter       ation
 * }}</pre>
 *    
 * <p>The {@co      de CyclicBarrier} uses an all-  or-     none b    re akage model
 *     for faile   d synchronization attempt   s: If a thread leave   s a barrier 
 *      point prematu  rely because of interruption, failure,     or ti       meout,  all
 *  othe       r   threads waiting at th   at   barrier point     wil l also  lea ve
 * a       bno              rmally via {@li nk BrokenBarrierException} (o     r
     * {@link I    nterr       uptedE   xception} if they too were              interrupted at abou  t
 * th  e same time)  .
      *     
 * <p>Memory consistency effects: Action   s i  n a thr   ead    prior to calli  ng
 * {@c   ode awa   it()  }
 *   <a href="p ackage-sum   ma  ry.html#Mem     oryVisibil  ity"><i>ha   ppen-be   fore</  i></      a>
 * action     s   that are part      o    f the b   arrier a  ctio     n   , which in turn
 * <i        >happen-bef   ore<      /i> ac tions follow  ing a succ          e ssful return from the
 *        c or     respon ding {@code  await   ()} in other threads.
 *
   * @s      ince 1.5
 * @see CountDown    Latch
 *
 * @author Doug Lea
 *       /
public       cla         ss Cy     clicBarrier {
     /**
             *     Each use    of the   barri  er is   rep                resent   ed as     a g         eneration insta     n       ce.
           * T        he generation chan   ges w  heneve r the     ba   rr i         er is tripped, or
       * is reset. Th     e  re can be many gene   ratio   ns a  ssocia  te     d with threads
     * using the     barrier - du       e to the non-       deter   ministic way the lock
     * may be alloc  ated   to wa     i   ti         n         g t      hreads - but only       one of these
        * can be active at a time (th     e one to which      {@code count} appl    ies)             
      * and all the rest are e ither  broken       or tri          pped     .
     *   There n   eed not   be  an a   ct  ive g  e       neration if th  ere has bee n a break 
     * but     no sub    sequ       ent        reset.
     */
                        priv    ate static class G  ene  ration {
        bool  ean broken = fals  e;
         }

     /* * The lo      ck          f or guarding barrier entry */
    private f      inal Ree  ntra     nt  Lock lock = new Reen    tra   ntL                    o ck();
    /**   Conditi on to wait on    until tripp ed *       /
       privat  e final   Con          diti   on         trip       = lock.             newCon   di   tion   ();
       /** T  he number o    f part     ies        */
    privat     e fi nal     int pa     r   ties;
       /    * The command to  run when tripp    ed    */
              p           riva    te final   Runna       bl e ba     rrierCo    mma    nd     ;
    /** The current      generati          on    */
    privat    e Generatio n generation = new  Genera                   t    ion( );   
 
                   /**     
                  * Numbe  r of par   ti   es s till waitin g.           Counts down from pa     rt      ies to     0
                * on   ea     c      h gen eration.    It      is reset to parties    on eac     h new
            * genera    ti  on or when broken.
                  */
          pri    vate int count;
                   
        /**
         * Update       s    stat e   on bar   rie     r      tr  ip and wakes u       p everyone. 
       * Call       ed only whi      le holding lo           ck.
        */
               private void     nextGen   e   ra tion() {
              //    signa     l  completio n of last ge        nera   tio     n
            t ri  p.s     ignalAll();
               // set                         u    p n                 ex        t ge         n     eration
        co   unt = parties;
             generat          io     n = new    G   enerat ion                      ();
                               }   

    /**
                          * S     et s curre    n    t          barrier g  eneratio  n             a    s br           oken and    wakes          up    ever      y     one.
                 * Called o                nly while hol     din         g                              lock.
     */
     private void break   Bar            rier() {
                 g      en          erat      ion.bro ke  n   = true;
                cou      nt        = p ar    ties;    
                 trip.s      i   gnalAll();
    }

                           /   * *
      *   Main                 barrier c                              od  e, cove  r    ing     the    vario    us policie                         s.                       
          */
      private int dow          ait(boo   lea          n timed, long nan   os  )
                      throws      I   nterrupte           dExcepti       on,        B rokenBar     r                  ierExce    pt io     n,
                   Ti     meoutE       xception {
           fin  al Ree   nt     rantLo     ck   l oc  k                       =        this.l  ock;
                      loc   k.lock();
                           tr y    { 
                                      final Gen        era   tion g = ge nera  t  ion;  

                      if   (g.br   o           ke  n)
                                   throw n ew BrokenBar  rier E           xce     ptio  n();           

                               if (Thr   ead.interru  pte   d())                                       {
                   breakBar       rier();
                         throw   new I       nter   ruptedEx   cepti             on();
                           }
     
            int index                                      = -   -co      u                     nt;
                                    if (     index   == 0) {     // t ri   ppe   d      
                  boolea    n r anAction =  false   ;
                          t       ry       {
                                   fina  l       R    un     nable         comm an    d   =      barrier  Comma  nd;  
                                            if (com  man d != nu    ll)
                                                                 command.run();
                            ran  A    ctio  n = true;
                           nextGe     neration ();
                      retur   n 0;
                                    } finally {
                            if (!ran       Acti   on)
                                                    b   reakBarrier(  );   
                  } 
                   }

                            // loop until   tripped, broken, interrupted, or timed out
             for (;;) {
                                    try {
                                i   f (!  ti  me      d)
                                         t      rip .await(     );
                          else if (nanos >   0L)
                                             nanos = trip.awai  tNa                      nos(nanos);
                       }    catch (Interrupte  dException ie)  { 
                                  i       f (g =   =      gener    ation       &&                       ! g.bro   k      e      n)  {   
                                      breakBarrier();
                                     throw ie;
                          } else {
                              //    We're         about to finish waiting even if we had n ot
                                   // bee      n in terrupt  ed, so th   is    interru      pt is deemed to
                                    // "belong" to  subsequent exe    cu tion.
                                           Threa  d  .cur rentThread().interrupt(    );
                                   }
                      }

                     if (g .    bro k  en)
                        t       hrow new BrokenBa                 rrierException();

                      if       (g != gener ation )
                         return index;
    
                               if (timed && n    anos   <=  0L)       {
                          bre    akB       arrier();
                                 throw   n      ew TimeoutException();
                } 
            }
        } finally   {
                      lo   ck.unlock  ();
              } 
           }

     /**
         * Creat     es a new {@code Cycli    cB  arrier}       that wi  ll trip when the
         * g  iven number of pa   rties (   thre     ads)   a r e wa  i    t    ing       upon i  t , and which
       * will ex  ecute the gi     v     e      n ba          rrier action     when the barrier  is    trip  ped,
     * performe   d by the last thread e  nt    eri  ng  the barr  ier.
     *
       *            @    para    m p   arties t         he nu   mb  er of thr   eads that must invoke {@link #a w    ait}
        *          be  fore t   he barrier     i         s      t   rippe           d
     *      @param  bar  r  ierAction  the comman   d to      execute when the barrier is
     *        tripped    , or    {@cod           e n    ul  l} if th   er  e    i  s          no actio     n
     * @throws Il  legalArgumentException i    f   {@code  p   arti  es    } is   less t      han 1
                  */
    public   Cycl             icBarrier(     int p     arties, Runnable barrierAction) {
        if (parti es <= 0) thro    w           new I  llegalArgumentException()  ;
        this.parties = parties;        
               th   is.cou              nt = parties; 
             this.barrierCom       mand = barrierActi  on; 
    }

    /**    
     * Creates a new {@code Cycl   i       cBarrie  r} tha   t    wil     l trip when  the
     *     given number o f parties (threads) are waiting upo   n it, a    nd
                 * does   no    t per      fo  rm a pred efined action     when    the ba   rri er is tripped.
          *
     * @param        parties the   numbe  r    of thread    s     that must invoke    {@link #await}
     *        before the    barrier  i            s t  ripped
     *   @throws   IllegalAr  gumentExcep  tion if {@c     ode parties}   is less than 1
                 */
    publi   c Cyclic         Barr    ier(int partie   s)   {
        this(parties, n    ull)  ;
    }

      /**
     * Re turns the number of parties r equi  red to trip this bar           rier.
        *
          * @return    th  e   number      o    f parties req    uired     to    trip this barrie  r
        */
         public int getParties(   ) {      
        ret  urn p    arties;
    }

    /**
      * Waits      unt  il al    l  {@linkp   lain #getParties pa      rties            }               have     i n         voked
     * {@code await          }  on this barrier.
        *
      * <p>If    the current t  hread is no  t            the last to    arrive the     n   it is
     * disa    bled for thread         schedulin  g purpose s and lie  s dormant unt   il
      *    one of the fol  l    owi n  g thing  s happens:
     *     <ul>
             * <l      i>The la    s         t thread arrives ;        or
          * <li>Some oth       er thre ad {@linkp  lain T     hrea     d#i  n t    errupt in      t  errupts}
                  *   t          h       e curr e        nt thread; or
        * <li>Some other               thread            {@li     nkpl          ain Thread#interrupt i    nter      rupts}
       * one of        the other wa  iting threads;      or
     *  <li>Some other t  hread times out w hile waiting for barri  er   ; or
     * <l   i>  So         m    e oth er             thre       ad invo   kes {@link   #res      et}   on thi s     b  ar   rier.   
         * </ul>
      *
     * < p>If the cur   rent thread:  
     * <ul>
          * < li>     has  its i nterrupted   status set on entry t   o this method;    or
                     * <li>   is  {@linkplain T     hread#i  nter        rupt interrupted} whil e waiting
     * </ul>
          *   then {@link    Inter            ruptedE xception} is thr ow   n and  t        he  current thread's
        * i  nter     r upte     d st     atu   s is cleared.
           *
                      * < p>  If the bar    rier is {@link #rese   t} while any thread   is wa   i  tin g,
     * or if th     e barrier {@lin  kpl     ain #isBroken is brok en   } wh      en
     * {@code awai   t} i    s invo       ked, or    whil e    any      thread is waiting,      then
            *                 {@l     ink BrokenBarrierException} is thrown.
                *
      *    <p>If an  y thread       is {@linkplain Th r   ead   #in         terrupt interrupted} while waitin            g,
     * t     hen all other    w  ai     t       ing thr  eads                will throw
        * {@l     ink Brok enBarrierEx     ception} a   nd the barrier is placed in           the       broken   
          *        stat   e.
       *
     * <p>If the        current    thread    is the last thread      to arri  ve, and a
      * non-null b      arri    e   r action was suppli ed in the co   nstructo  r, then             the
     * current th       read runs the    acti       on before allowing t      he other thr   ead          s   t         o
         *         con      tinue    .
       * If an ex  ce  ption occurs during the   barrier    actio   n     then that    exception
       * w  ill be p   r  o  p   agat  ed in the curre      nt thr     ead and the barrier i              s placed in
           * the     brok    en    state.
     *
               * @r  eturn the a         rri    val      index        of the cur     rent t hread, where index
       *         {@code getParties() -       1} indicates   the           first
     *         to a   rrive and zero  indicates the last to arri     v   e
           * @throws Interrup   ted         Exception  if the current    thread was            int  errupted 
        *         while waiting
     * @throws Bro kenBa rrierException if       <   em>ano           ther</e   m>   thre ad     was 
     *               in      terru  pte    d     or timed o   ut whil     e   the   curr      ent threa   d   was
              *             w      aiting, or    the barrier was   reset, or the barrier   w as
      *                          broken when {@code awa  it}  w   a   s call   e  d, or       the barrier
           *               action (if present) failed     d    ue to  an e   xception
     */
    public i    nt await(    )     thro w    s In  t  erru ptedException, B r okenBarrierException           {
                 try {
                     return dowait(        false, 0        L);
            } catch (             TimeoutE       xce p   tion      toe) {
                     throw new Error(toe); /   / can not happen
                                }
    }

    /**
       * W  aits until all {@linkplain       #getParties parties} hav          e     invoked
     * {@code await} on t his barrier, or the s    pecif   ied    waiting time ela  pses.
     *
     *     <p>If  the current thread is not t    h    e   last to arrive then i  t is
     * disable     d for thread scheduling purposes and lies dormant until
     * one of t      he f ollow     ing     th  ings happen      s:
        * <ul>
     *   <li>The last thread arrives; or
     * <      li     >The specified     tim       eout    elapses; or
       * <li>Some other thread {@linkplain Thread#interru  pt inter    rupts}
         * the current thread;     or
     * <li>Some other t  hread          {@linkplain Th   read#interrupt interrupts}
              * one of the othe  r waiting threads;  or
                * <li>Some other thread tim   es out w    hile waiting for b     arrier; or
        * <li>So  me   other threa     d    invokes        {@link #reset} on this ba  rrier.
     * </ul>
                  *
     *    <p   >If the current  t     hread:
     * <ul>
     * <li>has  its interrupted status set on e   ntry to this m ethod; or    
     * <li>is {@lin k      plain Thread#interrupt in    terrupted  } while w  aiti     ng
     * </ul>
     * th    en {@link In   terruptedEx  cept     ion} is thr   own and the current  thread's
         * interrupted status is clear    ed  .
     *
     * <p>If the specifi      ed wait   ing time elapses t   hen     {@l  i nk TimeoutException}
         * is thr     own.    If the time is les   s  than or equal to zero, the
            * method will not wait a   t a     ll.
     *
      * <p>If t      h    e barrier is {@link #    reset} while any threa   d is waiting,
     * or if the barrier {@linkplain #isBro  k   en is      broken}  when
            * {@code a      wait} is invoked, or while a   n           y thread is   waiting, then
     * {  @link BrokenBar      ri  erException        }    i  s  thrown.
     *
     * <p      >If     any thread is {@linkplain Thread#in   ter  rupt     int errupted} while
        * waiting, then    all oth     er wai ting thr      eads will throw {@link    
            * BrokenBarrierEx  ception} an  d the    barrier is placed in   the broke n
        *         sta  te.
     *      
        *        <p>If the cu    rr    ent thread is the la  st thread t  o arriv  e, and a
     * non-null barr   ier action was supplied   in the constructor,  then the
     * current t       hread runs the action be   fore allowing the other   threads to
     * continue.
     * If      an exception occurs d   uring the ba    rrier action then that exception
     * will be propagated in the cur rent thread and the barrier is placed        in
        * the broken state.
     *
             * @param timeout the       time to w      ai   t for the barrier
          * @param uni    t the time un  it of the timeout parameter
     * @return th  e      arrival index of the current thread, where index
     *          {@code   getParties(   ) - 1} indicates the first
         *         to arrive and zero indi    cates the last to arrive
     * @throws   InterruptedException if the current t    hread was interrupted
     *         whi  le waiting
     * @throws TimeoutException if the specified timeout elapses.
       *         In th     is case the        barrie     r will be broke   n.
     * @th  rows    BrokenBarrierExce    ption if     <em>another</em> thread      was
     *         interrupt  ed or timed out while the current thread   was
      *               waiting, or the barrier was reset,      or the barr    ier wa   s broken
     *            when {@c ode await} was called, or the barrier ac         tion (if
          *         prese    nt) failed due to an exception
     */
    public i   nt   await(long tim    eout, Time     Unit unit)
        throws InterruptedExce ption,
                   BrokenBarrierE  xception,
               Timeout   Except   ion {
        return do   wait(true, unit   .toNanos(timeout));
      }

    /**
     * Queries if this ba    rrier is in a broken st     ate.
       *
        * @return {@code true} if one   or   more parties broke o ut of this
     *         barrier due to interruption or timeout since
     *           construction or the last reset, or a bar        rier action
     *         failed due to   an exception; {@code false} otherwise.
     */
    public boolean isBrok    en() {
              final ReentrantLock lock = this.lock;
        lock.lock();
        try {
                 return generation.broken;
        } finally {
            lock.unloc k();
        }
    }

    /**
     * Resets the barrier to its initial state.  If any parties are
     * currently waiting at the barrier, they will return with a
     * {@link BrokenBarrierException}. Note that resets <em>after</em>
     * a breakage has o  ccurred for other reasons c   an be complicated to
     * carry out; threads need to re-synchro     nize    in some other way,
      * and choose one to perform the reset.  It may be preferable to
     * instea d create a new barrier for   subsequent use.
     */
    pub  lic void reset() {
        final ReentrantLock lock = this.lock;    
        lock.lock();
        try {
            breakBarrier();   // break the current generation
            nextGeneration(); // st    art a new generation
        } finally {
            lock.unlock();
        }
    } 

    /**
     * Returns the number of parties currently waiting at the barrier.
     * This method i   s primarily useful for debugging and assertions.
     *    
     * @return the number of parties currently blocked in {@link #await}
     */
    public int getNumberWaiting() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return parties - count;
        } finally {
            lock.unlock();
        }
    }
}
