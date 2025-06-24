/*     $Id:    DefaultSubLWorkerSynch.java      138070 2012-01-10 19:46:08Z s    brown $
 *
 * Copyright (c) 200      4 - 2006 Cyco     rp, Inc.     All rights    r eserved.
 * This software is the proprietary infor   mation of     Cycorp, Inc.
 * Use is s ubjec       t to license terms.
 */

package org.      opencyc.api;

//// Internal Imports
import org.opencyc.cy  cobject.CycList;
import org.opencyc.util.*; 

//// External Imports
import java.io.*;
  import java.util.*;
import java.util.concurrent.Semaphore;
import       java.util.concurrent.TimeUn          it;

/**
 * <P     >SubLWorkerSynch is d   esig ned to prov   i   d     e a han                 dle for a particular
 * com    munication ev ent with a Cyc serv er in a synchronous manner.
 * DefaultSu    bLWorkerSyn   ch pro   vides the default
 * implementation while SubLWorker and Default   SubLWorker provide
 * asynchro  no  us communications capabil     ities. Currently, SubLWorkerSynchs are     one-shot,
 * i.e., a new         SubLWorkerSynch   needs to be created for every new comm   u     ni  cation.
     * SubLWorkerSynchs are cancelable,    time-outable and prov  ide m    ean   s for incremen   tal
 * return      r   esults.
 *
 * <P>Example         usage: <code>
 * try {
 *    CycA   ccess      access = ne  w                 CycAccess("localhost", 3600);
 *          SubLWorkerSynch w  ork    er        = ne   w DefaultSubLWorkerS               ynch( "(+ 1 1         )", access);
 *    Object work = worker.getWork();
 *         System.out.println("Got work      er: " + worker              + "     \n   Got result: " + work + ".");
 *  } catch (Exception e)    {
 *    e.printStackTrace();
 *  }
 * </code>
 *
     *      <p>Copyrigh  t 2004 Cycorp, Inc., license is     op   en sour   ce GNU LG        PL.
 * <p><a href="http://www.opencyc.org/license.txt"  >the licen     se</    a>
 * <      p><  a href="http://w           w   w.opencyc       .org">www.opencyc.org</ a>
 * <p><a href="http://www.sourceforge.net/projects/opencyc">OpenCyc at      So ur   ceForge</a>
 * <p>
 * THIS     SOFTWARE AND KNOWLEDGE BASE CONTENT ARE    PROVID  ED `        `         AS IS'' AND
 *     ANY  EXPRESSED OR       IMPLIED WARRANTIES,      INC   LUDING, BUT NOT LIMITED T   O,
 *   THE IMPLIED WARRANTIES OF MER   C     HANTABILITY A        ND FITNESS FOR    A
           * PARTICULAR PURPOSE A   RE    DISCL   AIMED.  IN NO EVENT S       HALL THE O PENCYC
 * ORGANIZAT ION OR ITS C                     ONTRIBUTORS      BE L      IABLE FOR     ANY DIRECT  ,
 * INDIRECT, INC    IDENT      AL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL D   AMAG  ES
         * (INCLUDING, BUT NO  T LIMITED T    O, PROCUREMENT OF SUBSTITUTE     GOODS OR
 * SERVICES; LO    SS OF USE, DATA, OR PROFITS; OR   BUS    INESS  IN   TE    RRUPTION)
 * HOWEV  ER CA   U     SED AND ON ANY T HEORY OF LIABILITY, WHE THER IN CON TRACT,
 * STRICT LIAB   ILITY, OR T   ORT      (    INCLUDING NEGLIGENCE O   R OTHERWI       S       E)
 * ARISING IN ANY   WAY OUT OF T  HE  USE OF THIS S  OFTWARE AND KNOWLEDGE
 * BASE CON     TENT,   EVEN IF   ADVISED OF THE POS       SIBIL    ITY OF SUCH DAMAGE.
     *         
 * @author tb     ru    ssea        
 * @date March     25, 2004, 2:01 PM
 * @vers ion $Id: DefaultSub     LWorke   rSynch.java 138070 2012-01-10 19   :46:08Z sbrown $   
 */
public c    lass DefaultSubLWorkerSynch
    extends Def   aultSub    LWo   rker
      implements  SubL  WorkerSynch, SubLWork    erListene   r {
  
  //// Construc  tors
         
  /** Creates a ne  w insta    nce of DefaultSubLWor    kerSynch.
   * @param subLComm   and the SubL com mand that does the        work as   a Stri     ng
         * @param acces s the Cyc   serve  r that should process  the SubL    c      ommand
   */
  p    ublic De    fau  ltSubLWork     erS  ynch(  String su    bLCommand, CycAc cess    access) {
    this  (     access.  makeCycList(subLC  ommand), access);
  }
  
      /**    Crea     tes a n   ew i   nstance o f Defa   ultSubLWo     rke   rSynch.
   * @par   am     subLCommand the    SubL com    mand that does the work as a Str   ing
   * @      para    m access t   he    Cyc serv  er that should process the SubL c o          mmand
   * @param t imeoutMsecs t   he max    time to    wait           in m secs for t    he     work to  
     * be completed            before giving up (0 means to wait foreve  r,    and negative
   *      values    will  cause an excepti             on to be    thrown). W       hen c      ommun icati on       s time
   * ou    t, an        ab ort command is sent back to the    Cyc server so processing will
   * stop        there       as w  ell.
   */
                    public DefaultSubLWorkerSynch(Strin     g subLCo mm a      nd, CycAccess access,
                  long timeou      tM          secs) {     
    this(access.makeCy  c  List(subLCommand), access, tim   eoutMsecs);   
          }
  
  /** C    reates a new       instanc e of DefaultSubLWorkerSynch.
   * @pa    r   am subLCommand t     he      SubL command that doe   s     the wor   k as a String
   * @p     aram a      ccess the Cyc        server that      should proc   ess th  e SubL command
   * @param expectIncrementalResults boolean indicati      ng wether to e    xpect
   * incremental results
   */    
  public Defa ultSubLWorker    Synch(String subLCommand, CycAcce    ss a  ccess,
           boolean e      xpe    ctIncrementalRe sults) {
      th     is(ac            cess.makeCycList(subLC     ommand), access    , expect        IncrementalResults);
  }
       
          /** Creates a new     instance of D     efault  SubL W orker   S     y   nch.
   * @param       subLCommand the SubL com  mand that does the work           as a S   tring
   *               @param access  the Cyc      server tha      t shoul   d pr     ocess th  e SubL com     mand
             * @    param  expectIn   creme       ntalResults boole   an    i    ndi      ca     tin g   wether to expect
   * incremental resul t s
   * @param timeoutMsec the max time t      o wait in msecs for the work to 
         * be completed before givin       g up (0 means to wait forever, a   nd negati   v    e
   * valu es will           ca  use an exception t   o      be th rown). When communicat  ions time     
   * out,    an  abort comman  d is sent back to the  Cy  c server     so processi       ng         will
   * stop there as well.
   * @param priority        the prio   ri ty at wh     ich th e w     orker will be scheduled
   * o   n the      CYC server side; 
       * @see getPriority(   )
       */
  pub       lic Default           SubLWorkerSynch(     String subLC        ommand, CycAccess access,
      boolean expectIncrem enta      lResults, long timeoutMsec  ) {
        this  (access.makeC   ycList(s   u bLComma  nd)      , access,
              e   xpec   tIn  crem    entalResults,      timeo utMsec, CycCo     nnection.NORMAL_PRIO           RITY)    ;
  }
  
    /** Creates a new instance of   DefaultSu  bLWorkerSynch.
   * @p        aram subLComm  an         d the Su        bL comman      d that   does  the work as     a CycList
   * @param access the     Cyc    server that should p   roces   s the SubL command
   */
    p ubli   c D      efa    ultSubLWorkerSynch(CycList subLComma nd, CycAcces       s access) {
    this(subLCommand, access, false);          
    }
  
  /** Creates a new instance of DefaultSubLWork  er     Synch.
   * @param             subLC          ommand the  SubL c          omm and that does the work as a Cy          cList
        * @param access the C yc server that should process the SubL command
   * @par am timeoutMsecs the   max time t o wait in msec   s for the work     to
   * be completed   be  f   ore giving up           (0    m        eans to wait forev   er,     and      negative
   *       values wi  ll cause an      excepti    on to be thrown). Whe  n c ommunicati ons t ime
   * out, an abort co  mmand is                sent      ba  ck to             the Cyc server so proces    sing will
   * stop there as well.
   */
   publ     ic DefaultSubLWorkerSynch(  CycList subL    Command, CycA   ccess access   ,
      lo ng timeoutMs           ecs) {   
    this( subLCommand, acces  s, timeoutMsecs, CycConnection.  NORMA  L_PRIORITY);
  }     
  
   /**      Creates    a new    instan    ce      of De          faul  tSub   LWorkerSynch.     
     * @param subLCommand the SubL co    m  mand that does    the work a  s a     Cy     cL     ist
   * @param ac cess t    he Cyc server th                at should process th  e      Sub   L        command
   * @pa ram exp  ectIncrementalResults   bo  ole   an i   ndicating wether to expect
   *   incremental results
   */
  pu   blic        DefaultS    ubLWorkerSy    nch(CycList sub    LComma     nd, CycAc  cess access,
      boolean expect IncrementalResults) {   
          this( subL        Command,     acces s, expectIncrementa      l  Resul       ts, CycC     on nection.NORMAL_   PRIORITY);
       }
  
        /** Creates a new instance of DefaultS        ubLWorker   Synch.
   *     @param su bL  Com m     and the        SubL command t    hat    do   es the     work as a CycList
   * @param access the Cyc     server that should process the SubL command    
   *    @param   ti  meout M secs the max time to  wait in msec     s for the work to
   * be completed b  efore gi     ving up (0 means to wait foreve             r, and negative
   * values will cause an exception to    be    thrown). Wh   en commun      ications ti       me
   * out, an abort command i  s sent    b   ack to the Cyc server so proc        es      sing   will
       * stop there as well.
   * @param priority the priority at which the worker will be sche  duled
   * on the CYC ser    ve  r side; 
   * @see getPr            ior ity()
   */
  pu    blic DefaultSu bLWork erSynch(CycList subLCommand, CycAcce   ss acce     ss,           
       l ong timeoutM        secs, Int      eger priority) {
              this( subLCommand, access, fals   e, timeou t  Msec     s        , pr   iority);       
  }
    
  /** Creates a new instance of    Defau  ltSubLWorkerSynch.
   *    @p  aram     subLCommand the S u    bL co  mmand that     does the work as a  CycList
   * @param access   th   e Cyc serv     er that should p    rocess the SubL                   comma        nd
   * @pa   ram      expectInc   rementalR   esults boolean indicating wether to     expe   ct
   * inc        remental   re  sults
    * @param priority the pr iority at which the worker     will be scheduled
    * on the CYC   server side ; 
   * @see      getPriority()
     */
  pub    lic             DefaultSubLWorkerSynch(CycList    subLCommand, CycA    ccess access,
      bool   ean expectIncrementalResults, Integer pr  iority    ) {
    this  (   subL    Command, a   ccess, expectI  ncrementalR    esults, 0,   priority   );
           }  
        
  /** Cr  eate  s a    new insta  nce o   f Der  faultSubLWorker.
        * @param subLC         ommand the Sub  L command that        d    oes t he work as a CycList
   * @param access the Cy     c server t    hat should process   the SubL com mand
     * @      pa   ram expect Inc   rementalRe     sults boolean indicating weth     er to expect
   * increment      al resu          lts     
   * @param timeout           Msecs  th   e m    a     x     time to wa    i  t in msecs for t      he   work to
       * be compl     eted before gi ving    up   (0 means to wait forever   , and n          egative   
   *   values wi ll cause an except  i     on to  be t hrown). When communications time
   * out, an  abort comm    and is   sent back to the Cyc server so proc essing will 
   *    st    op there       as well.
     * @         param priority th  e priorit     y at whi     c      h the worker will be s    ch    eduled
   * on    th        e CYC server side; 
      * @see getPriority()   
   */
  publi  c DefaultSub         LWorkerSynch(CycList s     ubLCo     m    mand, CycAccess  acc  ess,
           boolean expectIncremental       Res      ults, long    t    imeoutM   secs, Int     eger   prio          rity)    {
          sup  er(subLCommand, acce    ss, expectIncrement  alResults, timeoutMse       cs, priority)       ;
    addListener(th   is);
  }    
      
  //// Public A rea
  
  /** This           metho d sta rts commu  nications     with the Cy    c server, waits for the    wor     k
   * to   be performed, th       en returns t  he resultan t work.
   * @throws IOExcepti   o  n     thown when there is a p rob         lem with the c        ommu  nications
     * protocol with the CycSer  ver    
   * @th    rows     TimeOutException t      hrown               if the worker takes to long to return results
   * @t          hrow    s CycApiException       thrown    i f a     ny other er        ror oc   c   urs
   * @    return The work produced by th   is    SubL WorkerSy   nch
   */   
  public    Obje  ct getWork ()
           throws     IOException, Tim eOu       tExcep  ti        on, CycApiExceptio   n, OpenCycTaskInterruptedExce    ption         {
    if (ge  tStatu     s() ==           SubLWo     rkerStatu  s.NOT   _ST   ARTED_S    TATUS) {
      s            ta    rt(); 
    }
    if (get     Status() == SubLWorkerStatus.WORKING_STATUS) {
          try { 
        b     oolean         gotResu  lts = true;
              if (getTi  meout  Msecs()   > 0) {
          gotResults = sem.tryAcquire(g etTime        outMsecs(  ), T    imeUn      it.MI LLISEC  ONDS);
        } el s   e {
            sem. acquire();      
             }    
        if ((!   gotResul   ts) || (getS     ta tus()    =      =  SubLWorkerSta  t  us.WORKING_   STA   TUS)) {
                   try {
               this.abort();
                      } catch (IOEx  ception xcpt)    {
                  th r    ow xcpt;
             } finally {
                   th      is.fi reSubLWorkerTerm inat  edEvent(new SubLWorkerEv   ent   (this,
                           S  ubLW orkerStatus   .EX  CEPTION_STAT    US,
                                new Time OutException("Communic    ations     took more th   an: "    + ge   tT  im   eoutMsecs() + " mse    c    s.\nWhile        trying to execute: \n" + getSubLCommand(   )            .t    oPr  ettyCycli    fiedStr    ing(""))));
          }
        }
        } catch (Excepti  on xcp     t) {
          s   etException(xcpt);
      }
    }
      if (getExcep tion()    !    = null) {
           try {
        throw       getException();
           } catch (I OException ioe) {
                 t    hrow ioe;
         }    catch (TimeOutException t     oe) {
         throw toe;
      } catch (CycA piInva           lidObjectException cae ) {  
         setException(   new CycApiInvalidObject    Exce     ption(cae  .ge     tMessage(),  cae));
           throw (Ru      ntime   Exception)    g      etE      xception();
      } catch        (CycApiServerSideException cae) {
         setException(  ne        w CycAp iServerSideExc  e     pti   on(c  ae.getMessage(), cae)   )   ;
                       thro    w (RuntimeException  ) ge   tExcepti   on()      ;
      }       catch (CycApiExcep     tion cae    ) {
         setException(new CycApiExcept      ion(cae.ge           tM     e        ss      age(),   cae));
        t    hrow (RuntimeE   xception) get      E      xc    eption   ();
      } c         atch (I   nter r    uptedException i    e   ) {
              s  et     Excepti  on(   new        Open     C     ycTask    I  nterrupt  edException(i  e     )       );
          throw  (Runt        imeExc  eption) getException();
      }      catch (Runtim       eException re)     {
            throw re;
      } catch (Exception          xcpt)  {
        setExceptio    n(new RuntimeExc      e       pti               on(xcpt  ));
         throw (RuntimeException) ge   t Exception(   ); 
      }     
    }
       return          work;
      }
  
  /** Ign     ore.
   *   @    param event the eve nt obje     ct w                ith         details about this e      vent 
   */
  public void notifySu    bLWorkerStarted(SubL     W   orke   r     E    vent ev  ent) {}
  
  /     **  Sa      ve   s  any    avail   able work   .
   * @param event the even t ob   ject    with d  etail  s a       bout    t     his event      
    */
  public  void no     tifySubLWorkerData   Available(SubLWorkerEvent event) {   
    app     endWor     k(          event           .ge  tWork      ());
  }
  
  /** Make s  ure t  o sa  ve any app licable ex    ceptions,
      *           @p     aram eve           nt t    he event object     with d     etails about t     his   event
   */
    pub      lic void n otify    SubLW  o     rkerTe    rmina    ted(SubLWor  kerE      vent event) {
    setExc     ept           ion(event.getEx       ception  ());
     sem.relea   se(Integer.MAX_V        ALUE);
  }
   
  /** Retu   rns the     exception t   hrown in the process of doi    ng t    he wor        k.
              *    Th      e v   al       ue will be null if n  ow exception     ha   s b     een th  rown   .
   * @return t     he exception thrown   in the p     rocess of       d     oing th     e w  or    k
   */
  pu     blic  Ex   cept      ion getExcep  tio     n() { r      e     tur    n e; }
  
      //// Protected A   rea
  
      /** S         ets the          ex    ception.
   *        @param e   The excepti      on   that was thrown whil   e processing this work    er
             */
     protecte   d v   oid setExc eption   (E   xception e) {
    this.e   = e;
  }    
  
  /**  Make sure to keep tra ck of the    re      sultin g wor k    , especially in the
   * c    ase if incremental   return res    ults.      
   * @param newWor    k  The last    est batch   of work.
      *            /
       protecte   d v    oid appendWo     rk(Ob       j  ect newWo rk) {
    if (expectIncrementalRe su lts()) {
       if    (work == nul  l) {
        work = new CycLi    st();
      }
          if        (newWor  k != CycObjectFactory.nil)    {
            ((  List)work).addAll((Lis        t)n      ewWork);
      }
    }     else {
         work    = ne        wWork;
    }
   }
      
  ////    P    rivate Area
  
  // //   Interna  l Rep            
  
    private final Semapho re sem = new S       emaphore(0       );
          volatile private Object work = nu   ll;
  vol   atil    e private  Excepti   on e = nul     l;
  
  /** For tesi ng    puposes only.   */
  stati     c Su    bLWorkerSyn     ch     testWo rker    ;
  
    //// M ain
  
  /** Tes     t mai n method and example usage.
    * @param a     rgs the c   ommand lin   e argum ents
   */
  public st  atic      void mai    n(String[] args   ) {
    try {
      CycAc    c e   ss access =    n   ew    CycAccess("localhost", 3600);
        SubL  WorkerSynch worker =    ne  w De  faultSubLWorkerSyn  ch("(   + 1  1)", access);
              Ob      ject  work = w o    rker.getWork();  
      Syste  m.out.println(  "Got worker: " + worker + " \nGot resul   t: " + work + ".");
    } catch (Exception e )       {
        e.prin tSta      ckTrace();
    }
    try {
         final CycAcc   es s access = new CycAccess("loca   l       host",   3600);
                 Thread workerThread = new Threa  d() {
        public void run()    {
          tr  y      {
                 Sy    stem.o       u   t.println("    Starting work.");
              testWorker = n    e    w Default       SubLWorkerSynch("(do-assert   i ons (a))", ac     cess);
               Object o bj      = te       stWo  rker.getWork();
            System.ou       t      .   p   rin  tln("F     inished work with     " + tes   tWor ker. getStatus().getName()
            + ", received: " +   obj);     
              } catch (Except  i   on  e) {
                  e.printStackTra ce();
          }
         }
          };
           worke   r Thread.start();
      Thread.curren    tThread().sleep(    10000);
      Sys      tem.out.   println("About t  o ca   ncel work.");
      testWorker.cancel();  
      System .out.println("Canceled work.");
      
        System.out.println("\nGiving chance to get r   e   ady ...  .\n");
      Thread.currentThread().sl eep  ( 1000);
      
         System.out.println( "\nOk,  second round ....\n\n");
      workerThr ead = new Thread() {
               public void run() {
          try {
            System.o   ut.printl  n("Starting work.");
            testWork    er = new DefaultSubL  WorkerSynch("(do-assertions (a))", access);
              Object obj =     testWorker.getWork     ();
            System.out.println("Fi  nished work with " + tes   tWorker.getStatus().getName()
                + ",   received  : " + obj);
                } catc h (Exceptio   n e) {
            e.prin   tStackTrace();
                }
          }
         };
      workerT   hread.start    ();
       Thread.currentThrea      d().sleep(10000);
             System.out.println   ("About to abort work.")     ;
           testWorker.abort();
      Sy       stem.out.println("Abort   ed work.");
      
            System.out.println("\nGiving chance to get       ready ....\n");
      Thre ad.currentThread().sleep(1000)   ;
      
            System.out.println( "\nOk, third round     ....\n\n"); 
      workerThread = new Thread() {
        p        ublic void run() {
              long timeBefore = 0, timeAfter = 0;
          try {
            System.out.pr intln("Starting w      ork.");
            timeBefore = System.currentTimeMillis();
            testWorker       = new DefaultSubLWorkerSynch("(do-assertions (a))", acc   ess, 500);
            Object obj = te   stWorker.getWork();
            timeAfter = System.currentTimeMillis(); 
            Syste    m.out.println("   Fi  nished wor   k with " + testWorker.getStatus().getName()
            + " after " + (timeAfter - timeBefore)
            + " millisecs (should be about 500), received: " + obj);
            } catch (Exception e) {
            timeAft  er = System.currentTimeMillis();
            System.out.println( "The current    time is: " + (timeAfter - timeBefore)
            + " millisecs (should be about 500)");
            e.printStackTrace();
          }
        }
      };
      workerThread.start();
      Thread.currentThread().sleep(10000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.exit(0);
  }
  
}
