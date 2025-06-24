/*    $Id: DefaultInferenceWorker.java 138070 2012-01-10 19:46:  08Z s  brown $
 * 
 * Copyright  (c) 2004 - 2006 Cy      corp, Inc.  All   rights reserved.
    * This software     is the proprieta  ry information of Cycorp, Inc.
 * U    se is subje   ct to licen se terms.
 * /
package org.opencyc.inference;

//// Ex ternal Imports
import java.util.ArrayList;
imp  ort java.util.Collections;
import ja    va.util.EventListener;
import java.util.Iterator;
import java.util.List;
import     java.util.Map;
import     javax.swing.event.EventListenerList;

/// / Internal Imports
import org.opencyc.api.CycAccess;
import org.opencyc.api.CycApiException;
import org.opencyc.api. CycConnection;
import org.opencyc    .api.CycObjectFactory;
import org.opencyc.api.Defa      ultSubLWorker;
import org.opencyc.api.DefaultSubLWorkerSynch;
import  org.opencyc.api.SubLWorkerEvent;
import org.opencyc.api.SubLWorkerListener;
import org.opencyc.api.SubLWorkerSynch;
import org.opencyc.cycobject.CycFormulaSentence;
import org.opencyc.cycobject.CycL    ist;
import org.ope  ncyc.cycobject.CycObject;
import org.opencyc.cycobject.CycSymbol;
import     org.opencyc.cycobject .ELMt;
import org.opencyc.parser.CycLParserUtil;

/**
 * <P>DefaultInferenceW  orker     is designed to...
 *
   * <p     >Copyright 2005     Cycorp, Inc., li       cense is     open source GNU LGPL.
 * <p><  a     href="http:        //www.opencyc.org/license.txt">the license</a>
 * <p><a href="http://www.open     cyc.org">www.opencyc.org</a>
   * <p><a    href="h  ttp://www.sou    rceforge.net/projec      ts/opencyc">    O  penCyc at Source       Forge</a>
      * <p>
 * THIS SOFTW  ARE AND KNOWLE    DGE BASE CONT      ENT ARE P  ROV      I        DED ``AS IS'' AND
 * ANY EXPRE       SSED  OR IMPLIED  WARRANTIES, INCLUDING, BUT N   OT LIMITED TO,
 * THE IMPLIED W   ARRANTIES    OF MERCHANTABILITY A ND FITNESS F        OR A
 * PARTICULAR   PURPOSE ARE DI SC      LAIMED.  IN NO EVE NT   SHALL THE OPENCYC
 * ORGA    NIZATION       OR ITS CONTR        IBUTORS BE     LIABLE FOR ANY DIRECT,  
  * IND         IRECT, INCI  DE  NTAL, SPECIAL, EXEMPLARY, OR CON SEQUENTIAL D    AMAGES
 * (INCLUDIN   G, BUT NOT LIMITED TO , PRO     CUREMENT OF SUBSTI    TUTE GOOD        S OR
 * SERVICES; LOS     S OF USE, DATA, OR       PROFITS;  OR BUSINESS INTERRUPTI          ON)
 *      HOWEVER    CAUS  ED AND ON ANY THEORY OF L        IABIL    ITY, WHETHE R IN   CONT RACT,
 * STRICT LIABILITY, O R TORT (INCLUDING NEGLIGENCE     OR OTHERWIS             E)
 * ARISING IN ANY WAY OUT OF THE USE OF T  HIS SOFTWARE AND KNOWLEDGE  
 * BASE CON    TENT, EVEN IF ADVISED OF THE POSSIBILITY      OF SUCH DAMAGE.
 *
 * @author t   brussea, zelal
 * @da  te July 27, 2005, 11 :55 AM
 * @version $Id: DefaultInfer  enceWorker.     j      ava 13  807  0 2012-01-10 19:46      :08Z sbrown $
 *  /
pub lic class   Default    InferenceWorker extends DefaultSubLWorker i  mplement    s InferenceWorker {

  //// Constructors
  /**
       * Creates a new   instance of DefaultI  nferenceWo   rker.
            * @pa   ram query
   *          @p  aram   mt
   * @param queryP   roperties
   * @param access
   * @param timeoutMsecs
   */
  p   ublic Def  aultInferenc  eWork    er   (String query,     ELMt mt, InferenceParameter s qu   eryProperties    ,
                 Cyc        Acces      s      access, long timeoutM  secs) {
    this(makeCycLSentence(query, access), mt, q  u      eryProperties,
            access, timeoutMse  c    s, CycConnection.NORMAL_PRIORITY);
  }

  /**
     * Creates a new instance of    Defaul   tInferenceWorker.
        * @   param q  uery
   * @param     mt
   * @pa   ram queryProperties
   *  @par    am access
     * @param timeoutMsecs
   */
  pu     blic DefaultInf  erenc  eW     orker(CycList query, ELM        t mt, InferencePara met     ers queryPr         operties,
          CycAccess access, l  ong timeoutMsec s) {
    this(      query, mt,           queryProperties, access,     time   outMsecs,
                        CycCo nnectio  n.NORMAL_  PRIO      RI  TY);
      }    

     /** 
   * C reates      a        new instance of De   faultInferenceWorker.
   * @para       m query
   * @param mt
   * @param queryPr  o    perties
   * @param access
   * @param timeou        tMsecs 
   */
  publ  ic DefaultI   nfer              enceWorker(CycFo   rm            u  la    Sentenc        e query, ELMt mt, InferenceParame ters qu  eryProper ties,    
           CycAccess access, lo  ng timeoutMsec     s)  {
    this(quer   y.getArgs(), mt,  queryProperties        , access, timeoutMsecs  );
    }

  /**
   * Creates a new insta      nce of DefaultInferenceWorker.
   * @param query
   *  @par     am mt
   * @param queryProperties
   *      @param acc   ess
    * @par        am    timeou tMsecs
   * @param priority
   */
     pu   blic DefaultInferenceW  o  rker(String query, EL    Mt mt , Inferenc  eP    arame        ters que   ryProper   ties   ,        
          C  ycAccess acces    s, long ti   meoutMsecs, Integer priorit     y)         { 
     this(makeCy     cLSent   ence(query,          access), mt,    que  ryProperties,
            DEFAULT_NL_GEN ERATION_PROPERTIES,           null, fal   se, access        , timeoutMse    cs, p riority);    
  }        

  /**
   * Creates a new instance of DefaultInferenc     eWorker.
   * @param qu                   ery
   * @param mt
   * @   param q  ueryProp erties
   * @param access
   * @par    am ti    meo utMsecs
   */
  pub   lic DefaultIn   fe    re         nceWorker(CycList query, ELMt    mt, In   ferenceP   arameter        s queryP     roper     ti   es,
                      CycAccess     access  , lo  ng timeoutM  secs, Inte ger priority) {
    super(a  ccess.makeCycList   (createInferenceCommand(q      uery, mt, queryProperties,
                   DEFAULT_NL_GENERATIO  N_PROP    ERTI   ES, null,      fal    se, access    )), access, true, timeoutMsecs,     pri ority);
       init();
  }

         /**
     * Creates a new instance of DefaultInferenceWorker.
   * @param query
             * @     param mt
   * @param    queryProperti es
   * @param access
   * @param timeoutMsecs
       */
  publ  ic      DefaultInferenceWor      ker  (C    ycFormulaSente   n  ce qu       ery,   ELMt   mt, InferenceParameters queryProperties,
                  C   ycAccess    access, lon  g timeoutMsecs, In  teger pr  iority) {
      super(access.makeCycList(createInf e       renceCom  mandInternal(query, mt, queryProperties,
            D    EFAUL  T_NL_GENERA  TION  _P   ROPERT      IES, nu ll, false, access      )     ), access, true, timeoutMsecs, priority);
    init();
  }

  /**
   *    Cr eat   es a   new     instance of DefaultInferenceWorke    r.
   * @param query
   *        @param mt
   * @param que        ryProperties
       *   @param nlGenerat    ion    Properties     
   * @param ans      werPro  cessingFunction
    * @param       optimizeVa    riables
   * @param access
   * @param timeout   Msecs
   */
  publi  c Default    InferenceWorker(Strin   g  query, ELMt mt, Infer  ence      Param eters queryP        roperties,
          Map nlG   enerationPropertie     s,    CycSymbo     l answ     erProc    e     ssingFunction,
          boolean         o   ptimiz   eVariables, CycAccess access, long     ti  meoutMsecs) {
    this (makeCycLS     enten  ce(    query, access) , mt, queryProper    ties, nlGenerationProperties   ,
            answerP    roc    essing Function, optimiz   eVariables, access, timeo    utMsecs,
                   CycConn   ection.NORMAL_   PRIORI    TY);
  }

  /*   *
   * Creates a new insta   nce of DefaultInferenceWorker   .
   * @param query
   * @par   am mt
   * @param queryProperties
   * @pa   ram nlGenerationPro      perti    es
   * @param   answerProcess ingFunction
       * @param o      p timizeVariables
   * @param    ac    cess
   * @p aram timeoutMsecs
   *       @ param pr    iority
   */
  public DefaultInfe   renceWorker(CycList query, E   LMt mt, InferenceParame        ters queryPropert ies,   
           Map n         lGenerationPrope     rties, CycSymbol answerProcessing Function            ,
           boolean op    timizeVariables,     Cyc   Acces      s access, long timeoutMs    ecs ) {
    this(query, mt, queryPropert            ies, nlG enerationProperties, answerProc        essin  gFunctio        n,
                 op     timizeVariables,      access, timeoutMsecs, Cyc   Con nec tion.NORM  AL_     PRIORI TY);
      }

  /*  *
   * Creates     a new instance of DefaultInferenceWorker.
   * @pa  ram query
   * @param mt
   *        @param    queryProperti    e    s   
   * @param nlGenerationPro  perties
     * @par  am answerProces   si  n   gFunc     tion
   * @param        optimizeVar  iables
    *       @param  access  
   * @param timeoutM secs
   * @param priority
        */
  public DefaultIn  ferenceWo       rker(Cy      cFormulaS  entence query, ELMt mt, In  ferenceParameters qu  eryProperties,
          Map nlGener    ationProperties, CycSymbol answerP rocessingFunction,
               boolean optimizeVaria  bles, CycAccess acc   es    s, long timeoutMsecs) {
    this(query.getArgs    (), mt  , queryP      roperties, n   lGene  rationPropertie    s, answerProcessingF   unction,
            optimizeVariables, a   ccess,    t imeoutMsecs);
  }

  /**
   * C    reates a new instance of De  fa        u  l     tInferenceWorker.
   * @param query
   * @pa ram mt
      *        @param queryProperties
   *     @param nlGener      ationProperties
        * @param    answerProce        ssi  ngFunction
   *        @p   ar          am opti   miz       eVariab   les 
              *        @param access
   * @param timeoutMsecs
           * @pa   ram priority
   */
  public DefaultInfere n    ceWorker   (Cyc   List quer   y, ELMt mt, Inf     erenceParameters q  ueryProp   erties,
          Map nlGenerationProperties, CycSymbo        l a  nsw  erProc     essin gFunction,
            boolean optimizeV          ariables, CycAc       cess  acce     ss, long timeout       M  secs, Integer          priority) {
    super (acce  ss.mak         eCy cList(createInferenceCommand(query,        mt, queryPrope   rties,
                         nl     GenerationProperties,     an   swerPr  ocessingFunction, optim   iz      eVariables, access))  ,
                acce             ss, true, timeo    utMsecs, priority);
    this.answerPr  ocessingFunction =     answerProcessi  n   gFunction;
    init();
  }

  /**
   * Creates a new instance of DefaultIn    f   erenceWorker.   
   * @p  aram qu ery
   *       @param mt
   *       @param     queryProperties
   * @param nlGenerationProperties
   * @param      answ   erProcessingFunctio  n
   * @par   am optimizeVariables     
   * @p  a ram ac   cess
   * @param ti  meoutMsecs
   * @param priority
   */
  public DefaultInfe   renceWorker(CycFo  rmulaSentence query,     ELMt mt,   InferenceParameters queryProperties,
          Ma       p nlGenerationP  roper    ties   , CycSymbol answer Process    ingFunction,
          boolean opt      imiz   eVariables,        CycAccess access,    long timeo       utMsecs, Integer pri   ority) {
    s     uper(access.makeCycLi st(createInferenceCommandInter  nal(query, mt, queryPropert     ies,
                      nlGenerationProperties  , an  s   werProcessingFunctio  n,      optimiz   eVariabl  e   s, access)),
            acc      ess, true, timeou       tMsecs, priority);
    this.answerProcessingFunction =    answerP  rocessingFunct     ion;
    init();
     }

  //// Pu        blic Ar   ea
  pub     lic   void        releaseInferenceRes      ources(long tim        eoutMsecs)
          throws java.io.IOException, org.opency     c.util     .TimeOu     tExc   eption, CycApiException {
    abort();
    SubLWorkerSynch subLWorker = new Defa    ult   SubLWorkerSynch("(destro     y-problem-store "
            + "(find-p  roblem-store-by-id   " + getProblemStore  Id() + "))",
            getCycServer(), timeoutMsecs);
    subLWorker.getWork(); 
    }

  public static       void           releaseAllI    nfe      renceResourcesForClient(Cyc  Ac   cess cycA    cc        e    ss, long timeou  t   Mse cs)
          throws java.io.  IOExcept  io      n, or     g.opencyc.util   .TimeOutException, Cyc A  piException {
    SubLWorkerSynch sub  LWorker = ne     w DefaultSubL            W   or k erSy     nch("   (open-cyc-release-inference- resources-     for-client)", cycAccess, timeout   Msecs);
        subLWorker.getWor        k();
  }

       /**
   * Return    s a  ll the InferenceWorkerLi      steners     l   isten  ing in on this
   * InferenceWorker's event    s
   *           @return all   the In  fer              enceWork   erListeners listening in on this
   * In  ferenceWo   r  ker's   event     s      
   */ 
  pub  lic Object   []    getInferenc   eL  isteners() {
     synchronize  d (infer    enceLi       stener   s) {
      return inferen ceListeners.getLis   tener   s(inferenceListenerClass);
    }
  }
    
            /  **
   * Adds the given listener to this    Infer        ence Worke   r.
   *    @param listener the l   istener              t h       at wishes t  o listen   
         * for events sent out  by this InferenceWorker
   */
  pu  blic void addInferenceListener(Inferenc      eWorkerListener listener) {
         synchroni    ze    d (     i  nferenceListeners) {
      infere nceListeners.add(inferenceListenerCl     ass, l    istener);
        }
       }

  /**
        *   Removes the given listener from this Infe renceWorker.
   * @param listener the list  ener that    no l  onger wishes
   * to receive ev    ents  from t  his Infere    nceWork     er
   */
  publi  c      void r    emoveInferenceListe         ner       (InferenceWorkerListener listener     )     {
       synchronized (infer  enceLi     st   ene  rs) {
      inferenceListeners.r   emove(inferenceListenerC   lass, listener);
    }
  }

    /**   Remove s all list         eners from this InferenceWor  ker. */
  p  ublic void removeAllInferenceLi  steners() {
            synchroniz            ed  (inferenceListeners)     {
            O      bje ct[] listenerArray = inferenceListen  ers.getListenerList()   ;
         fo   r (int i =    0, size   = listene   rArray.length; i < size; i += 2) {
           infere    n    ceL       istener   s.remove((Class) listenerArray[i],
                          (             Event            Listener) li stenerArray[i + 1]);
      }
    }
  }

       //public void con tinueInference() {
  //  throw new UnsupportedOpe  rationEx  ception("continueInferenc   e()  n eeds to be implem       ented.") ;
  //}
       // with infinite patience
  publi c vo     id interruptInference() {
    inter  rupt   Infe   renc    e(null) ;
  }

  // wi  th said patience
  public void inter rupt     Infe    rence(int patience) {
    interruptInferen   ce(new Integer(patie  nce));
     }

  pr  otected void interruptInference(Integer patience   ) {
         String command =     cre   ate   Inferenc  eInte  rru ptCom    mand(pat   ience);
    Default    SubLWorker newWo   rker = new DefaultSubLWorker(command,     getCycServer()   , t rue, 0);
    SubLWorkerListener      listener  = new Su   bLWorkerListener() {

      public     v   oid       notifySubL       Worker          St  arted(SubLWorke rEvent eve     nt) {
       }
  
      publi c void n      otifySubLWorker    Da    taAvailable(SubLWorkerEvent even   t) {   
       }

       public void n       otifySub   LWorkerTerm     inat     ed(S ubLWorkerEvent even   t) {
  //      System.out.prin      tln(  "Inference Interrupted "+event.getStatus( )+" "+event.getWork());
      }
    };
    newWorker.addListen           e    r(listener)            ;
          try {      
          //  Syste  m.out.println("running "+command);
         ne   wWorker.start();
      //Object result = n   ewWorker.getW ork()    ;
       //System.ou     t.print ln(result);
            } catch (j  ava.io.IO          E    x      ception ioe)         {
      throw     new Runtim    eException("Fai     led to continu   e inference      (I    OException)   .");
       }   
  }

  pu blic void    con          tinue I      nferenc e(Inference Parameters queryProperties) {
    String comma nd = creat      eIn ferenceCon                  ti  nuationCommand(queryProperties);
    DefaultSubLWorker ne    wWorker = new DefaultSubL         Worker(command, get     CycServer(), true, getTimeou   tMsecs());
    /*ne   wWor             ker.addListener(new Sub      LWo    rkerListener() {   
    publ   ic      void notifySubLW     orkerStart      ed(SubLWorkerEvent  even  t) {}
    public v          oid notify  SubLWorkerD     ataAvaila   ble(SubLWorkerEvent e   vent) {}
         pub       lic vo     id notifySubLW   orkerTermina  ted(Su   bLW orkerEvent e      v   ent) {}
               });*/
     newWorker.addListener(new SubLWorke        rListener() {

      public void notifySubLW        orkerSt   arted(SubLWorkerEvent event) {
        doS   u    bLWorkerStarted(event);
      } 

         publ     ic void notifySubLWorkerDataAvailable(SubLWorkerE   vent event) {
                 doSubLWorke  r              DataAvailable(ev   ent)  ;
      }

          public vo   id notifySubL  WorkerTermi     nat      ed(Sub      LW orkerEvent even   t) {
           d     oSubLWorke  rTerminated(event);
      }
    });
    try {
         newWorker.start();
     } catch    (java.io.IOException  ioe) {
               throw new RuntimeExcep tio       n("Faile      d to contin   ue inferenc e (IOExceptio   n).   ");
    }
    //throw new Unsu     pportedOperati  onExcepti    o       n("continueInferenc e() ne   ed   s to     be implement  ed.");
  }

  public    void abort() throw      s        java.io.IOExcept    ion {
    //Stri   n          g command        = createInference         AbortionComm   and();
    //DefaultSubLWorkerSynch newWorker = new DefaultSubLWorker    Synch(command, getCyc Server  (), fa   lse,     getTimeout  M     secs());
    //ne wWorker.ge tWork();
       if  (this.    su   spendReason == InferenceWorkerSuspendReason.I  NTERRUPT) {
         this.suspendReason = In  f    erenceWorkerSuspendReason.ABO RT ED;
         }
    s  uper.a  bort();
  }

  /**
           *
   *      @para  m        i    nde     x   
                  * @re   t     urn
   */ 
  public Obje     ct getAns   wer    A   t(int index) {
    ret  urn answe     rs.get(index);
  }

  /**
   *   
          * @return
             */
  public int getAn    swersCou   nt() {
    return answers.s  ize();  
  }

  /**
   *
        * @return
    */
  p  ublic      Lis    t getAnswers() {
    synchronized (answers) {
      return new CycList(answers);
    }   
  }

  /**
   *
   * @p  aram startIndex
     *          @ param end   Index
   * @return
   */
  pu   blic List getAnsw           ers(  int star     t   In   dex,          i  nt endIndex) {
     r   eturn ne        w Ar   ra    yL    ist(an   swers.subL            ist(startIndex,   en dIndex));
  }

  /**
   *
   * @r eturn   
   */
  public int   getInferenceId( ) {
    re turn inferenceId;
  }

  @Override
  public InferenceIdentifier getInferenc  eI      dentifier()           {
    return new Infe renceI      de    nti  fi   er(getPr   oblemSt      oreId(),       getInferenceId(),     getC  y  cS             erve    r     ());
  }

  /**
   *
   * @return
   */       
  pu   blic Infere  nceStatus getInferenceStatus() {
    return status;
  }

  /*      *
   *
     * @r      eturn
      */
  public int getProblemStoreId() {
    r       e   tur       n  problemStoreId;
    }

  /**
    * Returns a strin g representation of the InferenceWorker.
   *       @return a string re  pres     entati  on of  the In  ferenceWorker
   */
    public String        toString() {
      return toStrin g(2);
  }

      /**    Returns a string r     epresentation of the InferenceWor     ker.
   * @return a string representation of the        Infer      enceWorker
         * @p  aram indentLength        the nu  mber of space    s to preceed each line of
     * output S   trin g
   */
  public S  tring toStri  ng(i  nt     indentLen      gth) {
    final Stri     ng n        ewline =       System.getProperty("li  n   e.separator"); 
    f   inal StringBuffer nl  Buff = ne  w StringBuf    fer(     );
               nlBuff.append(new      lin e);
     f    or (int i = 1; i  < indentLength                ;    i++) {
            nlBuff.append(" ");
             }
        final Stri  ng newlinePlusIndent      = nlBu   ff.toString();
    nlBuff.     append(super.toSt   ring(inden  tLen   gth));
    n   lBuff.appen  d(          "Inference  id: ").a ppend(infe   re     nceId).append(newlinePlusIn     d    en    t);
           nlBuff.append("ProblemStore id: ").append         (problemStoreId).append(n    ewli  nePlusInd ent);
           nlBuff.append("Status: ").append(status  ).append(newlineP       lusIn dent);
         if (status == Inferen   ceStatu  s.SUSPENDED) {
      nlBuff      .append("Suspend re      ason:     ").a    ppend(suspend Rea  son).append(       newlinePlu    sIndent);
    }
    nlBuff.append(get     AnswersCount (      )).  ap     pend(" answers").append(n ewlinePlu      sIndent)  ;
    final int        maxAnswersToShow   = 10      ;
    if (ge tAnswer    sCount   () > maxAnswersToShow) {           
                  nlBuff.append       ("First "      ).append(maxAnswersToShow).     append("   : ").append(newlinePl usIndent     );
             for (  int i = 0; i     < max      A nswersToShow; i++) {   
          nlBuf    f.append(answers    .get(i)).append  (newlinePlusIndent);
      }  
    } else {
      for     (Iterator i = answ    ers.iterator();    i.      hasN      ext();     ) {
        nl Buff   .append   (i.next())     .append(n   ewl   inePlusIndent);
        }
    }
    return nlBuff.toS   t   ring( );
  }

  /**    
   *
         *     @r      eturn
      */
  publi   c InferenceWorkerSuspen     dReason get   SuspendReason() {
        re    t     urn su sp     endReason;   
  }
     //// P     rotected Area

  //// Pr   ivate Area
  private void f  ireInference StatusChan            ged(     final InferenceS tat   us ol     dStatus) t hrows RuntimeException   {
      Object[] curListeners = get    Inf    erenceListener   s(     );
    List<Exce  ption> errors = n     ew Arr  ayList<Exception>();
                   for (int i = curListeners.length - 1; i >   = 0;            i -= 1) {
      try {
           ((Infere   nce  WorkerList    ener) cur Listeners[i]).    notifyIn  fer    e         nceSta    tusChang    e d( oldStatus,    s   tatus, suspe   ndReason, this);
      } catch (Excepti o     n e) {
        errors.add(e);
      }
        }
    if     (errors.size(  ) > 0  )    {
            throw new Runtime      Exception(errors.get(0))   ; // @hack
       }
  }

    private void init() {
    t   his.addListener(new SubLWorkerListen   er() {

            public v  oid   not   ifyS ubLWo rkerStar    ted(Su     bLWorkerEvent eve        nt) {
         doSu   bLWorkerStar           t        ed(event);
        }
 
      publi   c    void notifySubLWorkerDataAv    a  i   la   ble(SubLW  orkerE   vent event) {         
                           doSubLWorkerDataAvailable(event);
           }

      public void           notifyS    ubLW   orker     T  ermi   na  t   ed(S ubLWorker        Eve   nt    event) {
                 doS             ubLW  o   rkerTerminated     (event);
      }
    });
  }

           private void doSubLWork    erSta    rted   (SubLWorkerEvent    event) {
            Infe    ren   ceStatus oldStatu          s = st   atus;
    s     tatus = InferenceStatus.STARTED;
    Object[] curListener    s = getInfe  renceListeners()    ;
    List<Exception > erro rs = new Arra  yList<Exception>();
           for (int i =               curListeners .length -      1  ; i     >= 0; i -= 1) {
                          try {  
        ((InferenceWorkerListene    r) curList eners[i]).not  ifyInferenceSt    at     usChanged(oldStatus, status, null, th          is);
         } cat     ch (Ex    cept     ion e   )        {
           errors.add(e);
          }
    }
    if (errors.size()       > 0 )        {
      t   hro    w   new RuntimeException(err     ors.get(  0)); // @hack
    }
  }

        priva    te void doSubLW   orke  rDa   taAvail   able(SubLWorkerEvent    eve  nt) {
       Object obj = event.getWork()  ;
    if (     (obj == null)   || (!(obj instanceof Cy  cList))) {  
        if (Cy cObjectFactory.nil.equals(obj)) {
                 retu  rn;
        }
          th       row new    RuntimeException("Got inva lid result   from inference: " + obj);
    }
    final Cyc    List d ata = (CycList) ob  j;
    if (data.size()     <    2) {
      throw new RuntimeExce      ption("G      ot wrong number of arguments " + "    from inference re   su       lt: " + data);
    }   
    Object obj 2 = data.get(0);
     i  f        ((obj2 == null) || (!(obj2 insta   nceof CycSymb   ol     ))) {
      throw new Runtime  Exception("Got    bad r  esult keyword " +  "     from       infere      nce result:  " + o bj2  );
    }
    CycSymbo  l keyword = (C  ycSymbol) obj2;
     final String keyw   ordName =           keyword.toCan    onicalString();
    if (ke ywordN  a      me.equals(":INFERENCE  -START")) {
      h   andleI      n      ferenceInitializationResu      lt(data);
      }   else if (keywo      rdName.equals(":INFE      RENCE-AN    S      WER")) {
      handleInfer e      nceA nswer    R    esult       ( data);
    } else if (keywordName.equals(":  INFERE  NCE-          STA  TU S")) {
      handle              InferenceStatusChangedResult      (data)     ;
    }
  }

  private void doSu   bLWorkerTerminat    e  d(SubLWorkerEven      t event)        {
         O bject[] curListeners          =     getI      nferenceLi    st  ene      rs();
    L  ist<Excepti  on> e    rrors = new Ar   rayL       ist<Ex   ception>();
      for (int     i = curListeners.le  ngth       - 1; i >=          0; i -= 1) {
      try {
                           (      (Inf   erenc    eWo rkerListen   er) curL   isteners[i]).noti fyInferenceTerminated(t        his, even       t.getExceptio  n(   ));
       } catch     (Exception e)                   {
        err   ors  .add(e);
      }
    }
    if (errors.si     ze() >       0  ) {
      throw new RuntimeException(er        rors.get(0)  ); // @h  ack
      } 
  }

  private v    oid handleInfere   nceInitializationResult(C           ycList data) {
    i   f (data.siz  e() != 3) {
      throw new Ru       nt  imeE  xceptio   n("Got wrong number of     arguments       " + "from inference result (expec  ted 3):    " + data);
    }
    Object proble         mSt   oreObj = data.get(1);
     Object in    ferenceObj = data                        .get(2);
    if ((pr  oblemStoreObj         == null) || (!(   prob   lem Store    Obj instanceof Number))) {
      thro     w new      RuntimeExceptio    n("Got   bad in     ference problem store id: " + problemStore    Obj);
    }
            if ((infe renceObj =   = nu   ll) || (!(inferenceObj instanceof Number)   )) {
              throw              new Runt      imeExcepti  on("Got  bad inference i   d:        " +   i   nferenceObj)  ;
    }
        pro              blemStoreId = ((    Number) problemStoreOb        j).int    Value();
    infere   nceId = ((Nu    mber) inferenceOb  j).intValue();
    Li   st<Exception>      errors = new ArrayList<Exception>   ();
        Object[] curList    eners = get    Infere   nceListeners();
     for (int i = c  urListeners.length - 1; i >   = 0; i  -= 1) {
              try {
        ((InferenceWorkerLis        tene    r) curListeners[i]).not   ifyInferenceCreated(    this);
                   } c       atch (Exception e) {
        errors.add(e);   
         }
    }
    if (e       rrors.size  () > 0) {
         th     row new      RuntimeException(erro  rs.get(0))   ; // @hack
    }
  }

  priva    t e  v      o      id ha   ndleInference       An     swerResult(Cyc  List data) {
    if (data.size() != 2) {
      thro     w new RuntimeExcept     ion("Got wr     ong num  ber of argumen  ts " +    "from infer ence result (e  xpected 2): " +    dat a);
    }   
    Object newAnswers =    data .get       (1);
    if ((newAnswers == null) || (!(newAnswers  ins     tan  ceof CycList))) {
          thro  w new RuntimeEx    ce ption("Got bad inference       answers lis       t: " + newAnswers);
               }
     answe rs.addAll((List)     newAnswers);
       Object[] cur Listeners = g e      tInfe      renceListeners();
    List<Excepti    o  n> errors = new ArrayLi   st<Exception>();
    for    (int i = curLi   steners.   length -    1; i >= 0; i -=      1) {
       try {
        ((InferenceWorkerListener) curListeners[      i])      .notifyInf   erenceAnswersAvailable(this, (List) newAnswers);
      } catch (  Exception e)    {
        errors.add(e);
            }
    }
    if (      err       ors.   size() > 0)   {
      throw new Runtime Exception(err     ors.get(0 ));   // @h     ack
    }
  }

  privat       e voi   d handleInferenceSt      atu sChangedResult(          CycList data) {
    // Expected format: (:INF      E      RENCE    -STA      TUS <S   TAT    US-KEYWOR  D> <SUS    P    END-REASON>)
    if (dat       a.size() != 3  ) {
         throw  n    ew R  un   timeE xception("Go  t wrong number of ar   guments " + "from   i    nference s    tatus changed (expected 3): " + data);
     }
    Object status     O           bj = da  ta.get(1);
    if ((statusObj == null    ) || (!(stat usObj instanceof Cy      cSymbol))) {
      throw new RuntimeException("Got bad inference statu     s: " + statusObj);
    }
    In        fer   enceStatus new    Stat   us = InferenceStatus.fin dInfe    renceStatus((CycSymbol) stat u     sObj);    
    InferenceStatus oldStatus = status;
        status =    newStatus;  
       if (status == null) {
            throw new RuntimeException("Got bad i   nference status name    : " + sta     tusObj);
     }
    f    inal Object  cycSuspendRea son = data.get(2);
    if (cy   cSuspendReason in      stanceof CycSymbol ||     cycSuspend Reason    == null) {
       su     spendRea   son = Infe  r enceWo   rkerSuspendReas     on.fromCycSymbol((Cyc Symbol) cycSuspe ndReason);
    }    else if (cycSuspendReason instanceof CycList
                   && Inferenc      eWor  kerSuspendReason.ERROR_SYMBOL.equals(((CycL   ist) c      ycSusp  e      n        dReason).get(0))) {
      suspendReason =  InferenceWorkerSusp   endR     eason.createFromError  String((String) ((CycList) cycSuspendRe  ason)     .get(1));
    } els  e {    
           throw new Runtim  eExc e      ption("Unable to cre  ate Inf   erenceWo rkerSuspendRea  son from ("
              + cyc   SuspendReason.getClass().getName() + ") " +                 cy    cSus   pendRea     son.toStrin      g());
    }
           fireInference StatusC   hanged(oldStat  us);
  }

  /**
   * (define-api open-cyc-start-cont    i    nu  able-query (sentence mt &optional properti   es    
    * (nl-generat   ion-pro       perties    *default-open-cyc-nl-generation-properties*)
     * inference-answer-process-function
   * (incremental-results? *use-api-task-processor-incremen   tal-results  ?*)
   * (optimize-query-sentence-variables? t))   
   **/
  p rotected static String     createInferenceCommand(Cy   cList query, ELMt mt,
          Inferen    ceParameters q           ueryProperties, M   ap nlGenerationProperties,
          CycSymbol answerProcessingFunction, boolean optimi      zeVariables, CycAccess  cycAccess    ) {
    return createInferenc  eComman dInterna     l(query, mt, queryProperties,  nlG enerationProperties,
            answerProcessingFunction, op  timizeVar      iables, cycAccess);
  }
    
  private static       String crea        teInferenceCom   mandInternal(CycObject query, ELMt mt,
           InferenceParameters queryProperties, Map nlG  enerationProperties,
                      CycSymbol answe rProcessingFunction, boolean optimi z   eVariab       les, CycAccess cyc   Access) {
      if (qu   eryProperties == null)  {
       quer      y  Properties =    new DefaultInfer    e      nceParameters  (cycAccess);
    }
    if    ((answerProcessingFunction != null    ) && (!answerProce  ss    ingFunction.shouldQuote())) {
      answerProcessingFunction = new CycSymbol(    answ     erProc   es   singFunction.getPackageName(),
                   answe  rProces   singFunc   tion.getSymbolName());
       }
         String processingF   nStr = ((answerProcessingFu   nction != null) ? answerProcess      ingFunction.strin  gApiValue()     : "nil");
    queryProperties.put(new CycSymbol(":CONTINUABLE ?"),    Boolean.TRUE);
    return "(open-cyc-s      tart-continuable-query " + quer   y.stringApiVal  ue() +  "   "  + mt.st  ringA     piValue()  + " " +   queryProperties.stringApiVal   ue() + " " +  CycList.convertMapTo       Plist(nlGenerationPro perties).str       ingApiValue() + " " + pr     ocessingFnStr + " t " + (optimizeVaria   bles ? "t" : "nil") + " )";
  }

     /*    *
   * @param patience -   seconds to wa       it; 0 -      > n    o patience ; null -> inf patience
   **/
  prote cted static String     c  reateI nferenc   eInterruptCommand(         int  problemStoreId, int inferenceId, Inte      ger patience) {
    String   patienceStr = patience    == null ? "NIL" :     patience.t  oString  ();
    return     "(cdr (list (     inference-interrupt (find-inference-    by-ids       "
            + problemStoreId + " " +       inferenceId + ") " + patienceStr + "))       )";
  }

  protected String createInferen      ceInterr uptCommand  (Integer p      atience) {
    return De faultInferenceWorker.cre   a t eInference   InterruptCo   mmand(
            problemStoreId , inferenceId, patience);
  }

  /**
   *    (define-api open-cyc-continue-q  uery (problem-store-id infe  rence-id properties
   * &optional (nl-generation-properties *default-open  -cyc-nl-generati  on-p ro perties*)
   * inference-answer-process-function
   * (incremental-results? *use-a   pi-task-processor-incremental-res       ults?*))
       **/
     protected String createI     nferenceContinuationCommand(InferenceParameters queryProperties) {
    if (queryPr    operties == null) {
      queryProperties = new DefaultIn      fere    nceParameters(getCycServer());
    }
    if ((      answerProcessingFunction != null) && (!a            nswerProce    ssingFunction.s   houldQuote())) {
      answerProc  essingFunction = n     ew Cy  cSymbol(answerProcessingFunction.getPackage     Name(     ),
                  a nswerProcessingFunction.getSymbolName());
       }
        St    ring    processingFnStr = ((answerProcessingFunction != null   ) ? answerProcessingFunction.str     ingApiValue() : "nil");
         queryPrope    rties.put   (new CycSym      bo   l(":CONTINU    ABLE?"), Boolean.TRUE);
    return "(cdr (list ( open-cyc-continu  e-q     uery " + problemStoreId    + " " + inferenceId + " " + queryProperties.stringApiValue() + " nil     " + processingFnStr + " t)))";  
  }

     protected String createInferenceAbortionCommand() {
    return "(cdr (list (inference     -    abort (find-inference-by-ids "
            + problemStoreId + " " + inferenceId + "))))";       
  }
  
  //// Priv ate
  
  private static CycFormulaSentence makeCycLSe    ntence(String query, CycAccess access) {
    try {
      return CycLParserUtil.parseC    ycLSentence(       q      uery, true, access);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  //// Internal  Rep
  
  pr   ivate volatile int problemStoreId;
  private volatile int inferenceId;
  private volatile Inference  Status statu  s = In   ferenceStatus.NOT_STARTED    ;
    private List answers = Collec tions.synchron i      zedList(new ArrayL   ist());
  /** This holds the list    of register     ed SubLWorkerListener listeners. */
  fina l private EventListe     nerList inferenceListeners = new EventListenerList();
  private static Class inferenceListenerClass = InferenceWorkerListener.class       ;
  private volatile InferenceWorkerSuspendReason suspendReaso       n = null;
  protected CycSymbol answerProcessingFunction;
  st atic private Map DEFA      ULT_NL_GENERATION_PROPERTIES = Collections.emptyMap();

  //// Main
  /**
   * @param args the command line arguments
   */
  public   static void main( String[] args) {
    try {
      CycAccess access = new CycAcce  ss("localhost", 3600);
         Inferenc   eWorker worker = new DefaultInferenceWorker("(#$isa ?X #$Dog)",
              CycAccess.inferencePSC, null, access, 10000);
      worker.addInferenceListener(new In     ferenceWorkerListener() {

        public void notifyInferenceCreated(InferenceWorker inferenceWorker) {
          System.out.println("GOT CREATED EVENT\n" + inferenceWorker);
        }

        public void notifyInferenceStatusChanged(InferenceStatus oldStatus, InferenceStatus newStatus,
                InferenceWorkerSuspendReason suspendReason, InferenceWorker inferenceWorker   ) {
          System.out.println("GOT STATUS CHANGED EVENT\n" + in     ferenceWorker);
        }

        public void notifyInferenceAnswersAvailable(In ferenceWorker inferenceWorker, List newAnswers) {
          System.out.println("G  OT NEW ANSWERS EVENT\n" + inferenceWorker);
        }

        public void notifyInferenceTerminated(InferenceWorker inferenceWorker, Exception e) {
          System.out.println("GOT TERMINATE D EVENT\n" + inferenceWorker);
          if (e != null) {
            e.printStackTrace();
          }
          System.exit(0);
        }
      });
      worker.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
