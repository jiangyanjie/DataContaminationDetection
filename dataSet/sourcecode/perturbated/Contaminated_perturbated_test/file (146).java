package org.opencyc.api;

//// External          Imports
import java.io.IOException;
import java.net.ServerSocke     t;
import java.net.Socke        t;
import java.net.UnknownHostException;
import java.text.DateForma      t;
import java.util.Collections;
import java.util.Date;
impo     rt java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.R  ejectedExecutionException;
import jav   a.util.concurrent.Semaphore;
import ja   va.util.concurrent.TimeUnit;
import java.util.logging.Level;
import ja va.util.logging.Logger;

//// Internal Imports
impo   rt static org.opencyc.api.SubLWorkerStatus.*;
import static org.opencyc.ap    i.SubLAPIHelper.makeSubLStmt;
import    org.opencyc.cycobject.CycObj  ect;
import org.opencyc.cycobject.CycList;
import org.opencyc.cycobject.CycSymbol;
import org.op   encyc.cycobject.DefaultCycObject;
impo  rt org.opencyc   .util.Log;
import org.opencyc.util.StringUtils;
import org.opencyc.util.TimeOutException;
import or g.opencyc.util.Timer;
import org.opencyc.uti  l        .UUID;

/**
 *  Provide    s a binary connection and an     ascii co    nnecti   on to   the OpenCyc server. The ascii   conne   ction
 * is l   egacy and its use is deprecate             d.
 *
 * <p>
 *     Collaborates with     the <tt>CycAccess<  /  tt> class which wraps the a     pi functions.  CycAc    cess ma y be
    * specified as null in the CycCon  ne     ct  ion constructors           when the bina ry api is used. Concurrent api
     *   reques ts are suppor   ted for binary (cfasl) mode. This    is imple men          ted by two socket connec    tions,    
 * the first being for asynchronous api reque     sts sent to Cy     c, and t he second for the  asychr o   nous
 * api responses received from Cyc.
 * </p>
 *
 * @version $Id: CycConnection.java 140169 2012-05-24 2      0 :58:4       8Z daves $
 *  @auth      or S   tep hen L. Reed    <p><p><p><p><p>
 */   
public clas      s Cy     cConnection implements CycCon   nec  tio          nInterf  ace {

     /** Default hos    t name for the OpenCyc   server. */
  public static Strin g DE   FAULT_HOSTNAME = "loc     alhost";
  
  /** Default base tcp port f   or the O    penCyc server.      */
  public     static fina         l in  t DEFAUL  T_BASE_PORT = 3600;
  /** HTT P port off    set f   or the     OpenCyc se    rver. */     
  public stat  ic  final int HTTP_PORT_OF       FSET = 2       ;     
    /** CFASL (binary) port offset f        or the OpenCyc server.    */
      pu blic stat  ic final int CF   ASL_P    ORT_OFFSET = 14   ;
        /** No api     trace. */
  public static final   int API_TRACE_NONE = 0     ;
  /** Messa      ge-le           v el api trace. */
  p  ubl            ic static final int API     _TRACE_MESSAGE    S = 1;
  /** Detail  ed api trace. */
  pu blic static final int AP      I_TRACE_DETAILED = 2;
    /** Parame  ter that, when true, causes a trace of  the messages to and from the server. */
      prote cted         int trace = API_TRACE_NONE;
//  protected int t   r  ace = API_TRACE    _MESSAGES;
//  prot  ected int trace =     API_TRA        CE_DETA    IL ED;    
     /*         * CFASL (binary)     mo   de c    on   nnection to the Cyc server (preferred). */
  public static f   inal int BINARY_MODE = 2;
  /** The binary interfac  e    inp      ut     s    tream. */
  protected Cfas   lIn pu tStream cfasl   InputStream;    
  /**            The     bina   ry interface output    stre     am    . */
  protected Cfa    slOutputStream cfa slOutpu     tSt    ream;
  /** The na    me    of the computer hosti          ng t    he OpenCyc se    rver. */
  protected String hostName;
  /** The tcp port fro  m w         hich the asciiPort and cf      aslPorts are derived. */
  protected    i   nt basePort;
  /**    The t    cp     po  rt assigned    to the binary connec tion to the    OpenCyc se rver. */  
   protected      int cfaslPort     ;
  /** The tc   p socket assigned to the b  inary connec      tio  n t   o the Ope   nCyc serv    er. */
  protected Socket cfaslSo   cket;
     /** The tim  er which optionally      monitors the duration      of requests to the OpenCyc serv      er. */
  prot     ected stat i  c final T  imer notime       out = new Timer();
         /**
   * In     dicates if the response from the Ope   nCyc server is a  symbolic expression (enclosed in
     * pa   re     nth      eses).
            */
        protected boolean isS    ymbolicExpression = fa       l se;
     /**
       *   A    reference to the    parent C   ycAccess object for derefere   ncing c   onstants in asci          i symbolic
   * expressions  .
   */
    p   rot   ected CycAccess cycAccess;
  /         ** outboun   d r      equest serial id *    /
    static private int   apiRequestId = 0;
  priva  t    e volati    le boolean isClosed = false;
  /** The prio     rities for the task processors. These   cor   respo    nd to  the
     * SubL prior ities  for SL:SET-PROCESS-PRIORITY */
  public static final Integer MA         X_     PRIORI    T Y = n e       w Integ    er(10)    ;
  publi c static final Integer CR  ITICAL_  PRI  ORITY = new Integer(7);
  public static       final Integer NORMAL_PRIORITY = new Integer(5);
  public stat ic   fin                al Intege     r BACKGR  OUND_PRIO  RITY = new Integer(3);
  public s      tatic fi     nal Integer     MIN_PRIORITY = new Integ          er(1);
  // @l   e      gacy
                p ublic st       ati          c final int DEFAULT_PRIORITY     = NO   RMAL_PRIOR     ITY     .intValue();
  /** name of my api client */
  protected String    my     ClientName = "ap  i client";
  /**
    * Implem       en     t   s an ass   ociation:  apiReque      stId    -->  waiting thread info, where waiting thre    ad info is
   * an array of tw      o objects                 : 1.  the latch waiti ng for the respons         e from the Cyc ser    ver
   * (n umber 1 is   no lon   g        er valid @todo fix    this description)    2.    the
   * api -req  uest in CycList form         Used when submitting  concurrent reque     sts to the t    ask-proce   ss    or.
   */
  protec    ted Map waitingReplyThread       s     = C   o ll   ections.s  yn    chronize  dMap(new H    ashMap());
  /** hand    les re    sponses from task-proces  sor re     quests in binary communica    tion mode   . *   /
  pro    tec  ted     TaskProcessorBinar    yResponseHandle  r taskProcesso     rBinaryResponseHa  ndler;
  /**      Indicates to       th  e taskProcessor response handlers that     the ser   ver connection  is closed. */
  protected boolean taskProcessingEnd   ed = false;
  /** Indicates that the    task processing thread    is dead */
  protected     vola   tile bo olea  n taskProcessi ngThreadDead   = fa    lse;
  /**
   * Universa lly Uni    que ID that i     dentifies this CycCon     nectio  n to the C    yc ser    ver. It is used  when
   * establis   hing the (seco  nd) asychronous socket connection.
   */
     prote ct  ed U   UID u     uid;
  /** t    he logger */
  protected fin          al Lo gger logger;

  /**
   * Const   ructs a new C       ycConnection u  sing the             given socket obtained    fro    m the pare   nt  Age   ntMa      nager
   * l  is   tener.
   *
   * @pa  ram cfaslSoc    ket tcp socket which   forms the binary c   onnection to the Ope   nCyc serve    r
   *
   * @throws IOException    whe  n communication error occurs
   */
  public CycConn   ec     tion(Soc     ket cfa   slS    ocket) throws    IOException {
    if (Log.current == null)   {
       Lo      g.makeLog("cyc-api  .l   og");
        }
    logger = Logger.getLogger("org.opencyc.CycConn    ection"    );
    this.cfaslSocket = cfaslS   ocket;
    hostName = cfaslSo     cket. getInetAdd  ress().getHostName();
         basePort = c  faslSocke  t.getPor     t() - CFASL_POR       T_OFFSET;
    cycAccess = null;
        cfaslInputStream = new Cfas   lInputSt     ream(cfaslSocket.getInp     utStream());
    c    fa         slInputS     tream.tr   ace = tr  ace;
         cfaslOutput        Stream = new CfaslOutput    Stre   am(cfasl   Socket.getOutp   utStream());
            cfaslOutp     utStream.trace = trace;
  }

  /**
   * Constructs a  new CycConnectio     n     object  using the default host na   me and default base port numbe.
       * When CycAccess is null as in this case, d   iagnostic output    i   s       red      uced.
      *
      * @throws         UnknownHostException     whe   n    the           cyc server c   an  not be found
    *    @throws IOExcep tion when communications error occurs
      * @th  rows C            ycA  piException when an api error           occurs
    *    /
     public CycConnecti   on( ) throws IOExcepti    on, Unk       no        w    n    Hos tException, CycApiException {
    this(  DEFAULT_HOSTN    AME, DEFAULT_BASE_P ORT, null);
  }

   /**
   * Constructs a n    ew Cyc    Connect    ion object u  sin   g the    default host name, def   ault     b   ase       port nu   mber
   * and the given CycAccess object.    
         *
      * @param c  ycAccess the given    Cy      cAc    cess object which provides api s   erv     ices over this
   *         CycCon     nection object
   *
              * @throws CycAp iExcepti    on when a  Cyc api excep  tion occurs
   * @throws     IOException when communicati    o  n error occurs
   * @throws U nknownHo    stExcepti   o  n when the cyc s        erver cannot be foun         d
      */
      public CycConnection(CycAc       cess cycAccess) throws IO    Exception, Un  known HostExce    ption,     CycApiException {     
    this(DEFAU      LT_     HOSTNAME, DEFAULT_BASE_PORT, cycAcce  ss);
  }  

  /**
    * Con structs a n  e  w CycConnectio  n object u    sing a given host name, the given base port number, the
   * gi  ven comm unicati    on mode, and the given Cyc   Access object
   *
   * @param hostName the cyc ser   ver host name
        * @param basePor     t the base tcp po  rt on whi   ch  t  he OpenCyc server is listening fo  r connection    s.
     * @param     cycA   ccess the given Cyc  Access o      bject which provides api services ov   er this
           *           Cyc  Con   nection   object
           *
       * @throws IO   Exception when a communicat   ions     erro  r occurs
   * @throws Unkno    wnHostExcepti      on when t  he cyc se    rver ca nnot be foun      d
   * @throws       CycApiException when a Cy   c API error oc    curs   
   */
  public CycConnection(String h   ostName, int baseP  o   rt, CycAccess cycAcc   ess) throws IOEx  ceptio      n, UnknownHostE   xception, CycApi    Excepti  on {   
         if (Log.  curre   nt == null) {
      Log.ma  keL      og("   cyc-api   .log");          
    }
    logg    er = Logger.ge    tLogger("org.opencyc.CycCo   nnec     tion");
    t  hi  s.hostName =   hostName;
    t   his   .basePort = basePor   t;
     cfaslPort =  ba     sePort + CFAS               L_    PORT_OFFSET;
    final Conn        ectionTimer connect   ionTimer = n ew C onnectionTimer();
    connectio  nT   imer.st      art();
    this.cycAcce    s s = cy     cAccess;
    in   itial       izeApiConnections();
    if  (trace > API_T RACE_NONE) {
      Log.current.print     ln("CFASL connect     ion " + cf  aslSo    cket)     ;
    }    
                 uuid = UUID.rand omUUID();
    initia    lize  Concur r   entP   rocessing();
         connectionTimer.isCycConnectionEstab      li      shed = true  ;
  }

  publ            ic int getConnec      tionType() {
          re   turn CycAcce        ss.PERSIS  TENT_C   ONNECTION  ;
  }

  /**
       * Initializes the    OpenCyc CFAS  L   socket connections. 
   *     
   * @throws I    OException when a com     munications e rr   or occurs
   * @throw    s Unknow   nHostException      when the cyc se    rver   cannot be found
   *  /
  pr ivate   void initializ    eApiConnection s() 
          throws     IOExc     eption,          UnknownHostE       x     ce   ption       {
       cfaslSocket = new    So        c      ket(hos    tName         , cfaslPo       rt);
                 int val = cfa slSocket.getReceiv     eBuffer    Size();
       c                faslSocket.setR   eceiveBufferSize(val *   2); 
    cfaslSocket.setTcpNoDelay(true);
         cfas   lSocket.setK     eepAlive       (true);
    cfasl      InputStream = new Cfas   lInputStream(cfa     slSo  cket.getInputS    t    ream());
      cfaslI     nputStream.trace = trace;
    cfa        sl     Out  putStrea     m = new CfaslOutputStr e  a         m(cfaslSocket.getOut    putStream());      
    cfaslOutputSt   re  am.trace   = trace;
  }    

  /*  *
   * Initializes the concur    re   nt pr  o          ce         ssing mo   de.  U    s    e serial mess  aging mo                 de to e nsur       e the   Cyc t      ask
     * proce       sso  rs are initialize  d, then start this conn ection's taskPr        oc  essor response   ha    nd ler      
   * thre   ad.
   *
   * @throws     IOException when a communicat  ions er       ror occurs                  
      * @throw            s U  nkn    own    Hos tE    xceptio           n        when the cyc server cannot be found
       * @th  rows CycApiE    xception when a Cyc     AP I     error  oc curs
    */
  prote     cted void initializeCo ncu rrentProcess    ing()
                     throws IOExc     eption, UnknownHos         tException, CycAp      iE  xception {
        task   Pro    cessorBi   naryRe sponse   Ha    ndler =  
                     new Ta    sk    Processo rBina      ryResponseHand ler(Thread.cur       rentThread(), this);   

      /   / the    st  art    me  thod will     not        return until   t  he inbound socket
      /   / has had time to initialize
    taskProces   sorBinaryRespon    seHandle        r.start()    ;
  }

  /**
   * Ensures that t   he api socke t    con    nections are c  l    osed when this     object  is garbage collect   ed.
   */
          pro     te      cted v o   id f  inalize()   {
        close();
  }

  /             **
   * Cl   os  e  t  he    api     s      ockets and s     treams.
               */
     publi      c               synchronized void close    () {
    if (  isClosed) {
       return;
    }
           isClosed   = tr      ue;
        task   Pro   ces      sorBinaryRe  spons   e Handler.isClosing = true;
             try {
           if (i  sValidBinaryConnection(tr  ue)) {
                  if (cfaslOutpu   tStream   != null) {
            CycLis     t c   ommand;  
                     if (trace > API_TRACE_  N            ONE) {
                                     Log.current  .print    ln("Clo   sing server '    s api r    esponse so    ck et associated with uuid:           " + uuid);
           }
           comm   and = new CycList();
               command.a  dd(Cyc      O    bjectFac   tory.m  akeC        ycSymbol("  RELEASE-R      ESOURCES-   FOR-JAVA-API-CLI    ENT"));
          comman             d.   a dd(uuid);
          try     {
                      cfaslOutputStream.writeObject(comman       d);
            cfaslOutputStrea   m.flush()     ;
              } catch (Exception     e) {
               Log.curr  en       t.print  S ta   ck  Trace(e);
                           Lo    g   .c  u      rrent.printl  n("Err     or closing server's a    pi response socket "    + e.g         e     tMessage());    
             }
                i   f (tr      a           ce >   API_    T   RACE_NONE)     {
                Log .cur  re nt.     pr     intln("S   ending API     -    QUI       T to serve       r t hat wi     ll cl      ose its ap   i request socket and its      handli  ng thr   ea   d");
                  }       
          command = n  ew CycList();
              co        m  mand.add(CycObject      Factory.makeCycSymbol("API-QUIT") );

               try {
            cfa   slOu       tputStr   eam.writeObj      ect(command);
            cfas     lOutputStrea  m.flu    sh();
                }       catch (Exception     e) {
                    Lo  g.curr         ent.printStackTrace(e);
             Log.c     urrent.pr     in    tln("Error              quitting the api c            onnection " + e.getMessage    ());
                       }
                  }
             }
        if  (cf     as   lIn      putStream != null) {
        if (trace > API_TRACE_     NONE)         {
          Log.current.println("Closing    cfas  lIn           putS    tr eam");
         }

        try {
                cfaslInputS   tream.clos  e()    ;
        } c                 atch (Exception e) {
          Log.curren        t.pr  intSt        ac  k    Trace(e );
                   Log.current.println("Er    r   or fina     lizin   g the api connect            ion        " + e.getM  essage  (  ));
               }
              }

      if            (cf aslSocket ! = null) {
                 i   f     (trace >       API_  TRAC  E_N    ONE) {
          Log.current.prin    tln("Cl           osing cfaslSoc  ket");   
        } 

            try  {
              cfaslSocket.close       ();
              }  catch (E        xception e) {
          Log.c     urre    n  t.printStackTrace(e);
             Log.current.println("Error c losing the api c  onnec     tion "          + e.    getMessage());
        }
        }

      taskProcessingEnded = true;      

                  if (trace > AP  I_TRACE_NONE) {
        Log.cu rrent.println("Interr     uptin          g any threads awa   iting    replies");      
                }

      inter          ruptAllWaitingReplyTh      reads(    );
     
      try {
         taskPro ce ssorBinaryResp  on  seHandler.interrupt ();
            taskProc    essor    Binar   yRe             spo   nseHandler.close();
           if (trace > A   PI_T  R   ACE_NON  E) {
          Log.cu rrent.pri     ntln("Wait            ing   at most 500 millisecond  s for the taskProcess     orB          inar   yResponseHandler         th read to   die");
                   }

        taskPro   c     essorBin  a  ryRes    ponseHa   ndler.       join(500);     
   
        if (!tas kProcessingThread D ead) {
                  if (trace > API_   TRACE_NONE) {
             Log.cur      re    nt   .p     rin tln("The task  P   rocessorB   inaryR       espon     seHandler       thread has not died,    so   continuin g");
           }
          }
          } catc   h      (Ex     ce ption      e) {
      }

      i    f (trace > API_TR  ACE_NONE)     {
             L      og.cu   rren      t.pr   i    ntln("Co      nnection      c   lo    sed for      " + c  onnectionInfo() );
        }     
     } finally {
      isCl osed =   tr     ue;
    }

  }

  /**
   * Return th     e n         ame of the host   to              which the CycCon  ne  ction is established.          
   *
     * @re     turn the name of    th     e Host to   whi       ch this <tt>C   ycCo  nnection    </tt> is connected.
   */
  @Overri      de
  publi c String get   HostName() {
    return this.hostName;
  }

  /   *    *
   * Return    the b a      se por  t to    whi      ch t   he CycConnection     is establ    ished.
   *
   * @ return the port to which this <tt>CycConnection</tt> is connected.   
   */    
            @Overrid     e
  public int getBasePort()   {  
               return this.b  asePort;
  }

    /**
   * Return the http port  of the Cyc ser  ver to which th e CycConne     c    tion    is est      ab   lished.
   *
   * @return the h    ttp port of the      server to which    this    <tt>CycConnection  </tt> is con   necte   d.
   */
  @Ove   rrid     e
  public int getHttpPort() {
    r      eturn this.basePort + HTTP_  PORT_    OF    FSET;
       }
  
  /**
   *       Return       the CFASL port to    which     the CycCon       nection is established.
   *
       * @return the C   F    ASL    port to whic     h  thi s <  tt>CycConn   ection</t  t>   is connec  ted.
   */
           public int ge     tCfaslPort() {
        retu  rn this.cfaslPort;
  }

  /** 
         * Send a me  ss age   to C   yc and return the <tt>Boolean</tt> t rue as t    he first    el  ement of an object
   * arr  ay, and                the cyc res   ponse Symboli     c Expressi         on   as the second e          lement.  If an      erro   r occurs
   *    the          first element is <tt>Bool  ean</t         t> false    and the  second element is the error message
   * strin     g.
   *
   * @param message the api command
   *
       * @        return an array of two objects, the first i  s an re sp        ons        e st  atu   s objec    t either a B    oolean
   *            (binary mode) or In  teger (asc ii mode), and the second i   s the response object or error
   *             string.
   *
   * @throw  s IOEx     ce    pt    ion when a co     mmuications error occurs
     *  @throws   Cy cApi  Exception when a Cyc AP           I  error occurs
     */
  public Object      [] conv erse(Obj  ect message  )
          throws I   OException, CycApiE       xception        {
       ret    urn con    verse( message,
                  notimeout)           ;
  }

  /**
   * S   end     a message    to     Cyc an      d return the response code     as th   e      f     i     rst       e   le       ment of an object array,
      * an d the c   y c response Symbolic Exp res  sion as the second element, spending no less time   than
         * the speci       fied timer al lows     but throwing a  <code>TimeOutException</code> at the first
   * o            pportunity wher   e t    hat time limit is     exce      eded. If      an er   ror o   ccurs the   second element is the
   * error message s  tring.
            *    
    * @param messag     e   the api comman d which    must be a St        rin g or   a C         ycList
   * @  par     a  m          timeout a <t    t>Timer</tt > ob     ject  gi  ving the ti   me limi t for t he api call
   *
   * @return  an array of two objects, the first is a Bool   ean response stat       us object, and the se cond
      *         is the response object or error string.
     *
   *    @throws IO  Excepti       on when a communication    s           error occurs
   * @t        hrows    TimeOutExceptio   n       when the time limit is exceeded
     * @thro        ws Cy  cAp     iExcepti  on   when a Cyc a pi error occur   s
   * @throws RuntimeExceptio     n if CycAccess is not present
   */
  publi  c Object[] co        nverse (Object mes      sage,
          Timer ti          m   eout)
          thr   ows IOException, Tim   e      OutExcept     ion, CycApiE  x              c eption {
                   CycList      messageCycL      ist;
    if (message instanceof            CycList) {
              messageCycList = (CycList) message;
    }        e    l    se if (message i  ns   tanceof String) {
          if   (cycAccess == null) {
        throw n e    w RuntimeException("CycA   ccess            is      required to process comma   nds in string    form")       ;
      }
               me    ssageCycList  =     c   ycAcce  ss          .     makeCycList((String         ) m   essage);
      } else {
         throw new CycApiException("Inva       lid           cl    ass        for m    e        ssage " + message);
    }
    messageCycList     = substitu           teForBackquote(mes    sageCyc   Li    st   ,
                 ti        me   o      ut) ;
    re turn     converseBinary    (messageCycList, ti meout);
  }

  /**       
   * Su bstit   ut  e a    R    E  AD-FROM-STRING ex   pre ssion for      expressions d  irectly        contain    i       ng a b a  ckquote
       * symbol.          Th is               transfor m  a tion is only required for    the binary api, which does not parse th  e
    * backquo       ted expressi      on .
          *
   * @p  aram messageExpressio        n t  he    given expressi    on
     * @param timeout a  <tt>Timer</tt> object  gi  ving the time       limit f      or the api cal l
     *
     * @return t  he              expression with a READ-  FROM-STRING expression substi   tuted fo     r expressions directly
   *                          containing a backquote   symb    ol
   *
        * @throws  IOException when a commun  ica    tion error occurs
        *    @throws C   ycApiExceptio   n when a C    yc a  pi error occurs
   */
  protected Cy   cList substituteF   orBackquote(CycList messageCycL      ist,
          Timer     timeout)
                 throws IOE     xception    ,   C y   cApiException      {
          if     (messageCycList.treeContains(CycObjectF       actory.ba  ckq   uote)) {
          Cyc List substit    uteCycList = new CycList  ()       ;
      substituteCycList.add(CycObjectF        actor   y.m      ake CycSymbol("read-from-st  r        ing"));
      String tempStr  ing        = m    es  s       ageCycList.cyclify();
      tempString = te mpSt rin   g.r   eplaceA  ll("\\   |\\,\\|"      , "   ,    ");
      s    ubstituteCy  cList.add(tempString);
      Object[]    response        =       co    nverseBinary  (substitu     teCy       cList,
                    timeout); 
      if ((r      esponse[0  ].   equals(Boolean.TRUE)) && (respons        e[ 1]       instanceof  CycList)) {
          CycList bac   kquoteExpression      = (CycLis         t) re     s    p    ons  e[1];
        return backq     uo       teExpression.subst(CycOb jectFactor  y.makeC          ycS ymbol("api-bq   -list"   ),
                CycO     bjec    t  Factory.makeC  ycSymbol("bq-l ist       "   ));
             }
      throw new CycApiExcept     ion  ("Inva     lid     backq         u     ote substitution in " + messageCycList +
               "\nstatus"    +      re  sp    onse[0] + "     \nmessage " + response[1        ]);        
    }
     r et  urn message   Cy   cList;
  }
     
  private     class WaitingWorkerInfo {

    final Su  bLWorker worke   r;
    final boolean       isReturnW      holeTaskProcessorResponse;
        final Cy   cList       taskP   r  o  cessorRe    quest;

    WaitingWorkerInfo(f in     al       S ubLWork  er wor    ker, final CycList                tas   kPro cessorRequest  , final boolea    n isReturnWhole     TaskProcessorRe     sponse) {
      this.  wor   ker = work   er;
      this.tas kProce     ssorR       equest = t    askPro   cess   orRequ   est;
             t his.isReturnWholeTaskProcessorRes        po  nse = isRet  urn   WholeTaskProcess       orR    esponse;   
       }
 
    Su      bLWorker getWorker() {
          return worker;  
    }
      
        CycObject g  etMessage() {
      retur    n (CycObject) taskProcessorReque    st.g  et(1);
    }
                }

  /*          *
   * Send a message   to      Cyc  and re  turn the resp    onse code as the first ele    ment of an object array        ,
   * and t   he cyc response Sym  bol   ic E  xpression as the second element, spendi ng no less time  tha      n
   * t           he specified timer allows but throwing a <code>TimeOutException</code    > at  the fir         st
         * opportunity        where that time l   imit is exceeded    . I  f an       e    rror occ    urs the seco   n   d element is the
      *    error me      ssage string. The c  oncurrent mode          of Cyc serv er  comm     unication       is      supported by C  yc's
   *     po           ol    of tran   sac tion process     or th    r    ea ds, eac       h of  which can concurrently pr ocess an api request.
   * 
   *      @pa      ram messag   e the api command
   * @param timeo   ut a <tt>Timer</tt> object giving    the time limit f   or the api call
   *
   * @return    an a r   ray      of t       wo        o   bjects, the first is an Boolean   respons    e co      de   , and the second is th     e
   *              res  ponse o       bjec   t or error str        in g.
   *
   * @t      hrows     IOException   when a        c  o m    mun   ication err    or occurs
        *  @throws TimeOutException when the tim       e    lim    it    is  exceeded
   *   @throws Cyc  ApiE         xception when a Cyc api error occurs
   */
  @     Override
  publi      c Object  []     converseBinar    y(C  ycList me ssage,     Tim      er timeou   t) th  rows I   OExce ption, TimeOutException, CycApiException {
              DefaultSubLWorkerSynch worker = new DefaultSub    LWor ke   rS     ynch(message, cycAccess,
               timeout.get  All otedMSecs());
    Object[] result = ne w     Object[  2];
    tr     y {
      result[1] = worker.       getWork();
        } catc      h (IOException  xcpt) {
      throw xcpt   ;
       }      catch (TimeOutException xcpt) {
      throw xcpt;
    } catch       (      CycApiServerSideExcept    ion        x   c pt) {
      // @note: this implem  ents a    legacy AP     I of co   nverseBinary()
      result[0] =     Boolean.FALSE;
      result[1] = xcpt;
      retu  rn result;
    }       catch (Cyc ApiException xcpt) {
      throw xcp  t;
       }      catch (RuntimeExc   eptio  n r     e) {
         thro         w re;
    }     catch    (Exception xcpt)   {
      throw n ew RuntimeExc    eption(xc  pt)   ;
    }  
    re    sult[0]       = worker.getStatus() == FINISHE  D_STATUS ? B   oole   an.  TRUE           : Boolean.FALSE;
    return result   ;
       }

      pub             lic  voi    d cancelCommunication(SubLWork  er    worker ) t    hrows java.io.       IOException {
      Integer id = worker.ge       tId();
    if (id.intValue() < 0) {
          //@note seria             l communicatio ns ca   n        not be   ca nceled right now
       retu rn;
    }
    String command = "(fif (" +     "terminate-acti     ve-task-process" +   " " + worker.ge tId()    + "        \"" + uuid + "\" "     + ":cancel" +
               ") '      (ign  o    re) '(ignore))";     
    sendBin             ary(  cycAcce  ss .makeCy   cList(command));
    // th  e SubL implement  ation of      CANCEL   will s   end a CANCEL e     vent      b ack,
    //        which          will   cleanup the waiting thread      info and sign    a    l th    e termination
    //    event, so no need to perfor m event signaling and cleanup
       }

             p  ublic void ab      or tCommunicatio  n(   SubLW     orker worker) throws java.io.IOException {
      Inte          ger id =    worker.getId();
    if (i d.intV   al  ue() < 0 ) {
      //@note serial   communications cannot be cancel   ed     right      n      ow
          r eturn;
    }
    try {
      String command = "(fif (   " +    "terminate-active-task-process" +    " " + worker.getI  d() + " \""         +    uuid + "\" "   + ":abort" +
                ") '(igno   re) '(   ignore))";
         s    endBi   nary  (cy cAccess.makeCycLi s t   (command));
    } fina   lly {
      // the  SubL implementat  ion of ABOR  T will not s   en   d anything back,
        // so we do need     to perform event signaling and cle  anup   
         worker.  fireSubLWo    r      kerTerminatedEvent(new SubLWorkerEvent(worke       r,
                              ABORTED_ST      ATUS, null));
                waitingRe      plyT           hreads.remov    e(id );
      }
  }
   
  pub    lic sta   tic          boolean in    AWTEventThread(    )   {
    t  ry {
      return javax.swing.S  wingUtilities.is      E  ventDispatchThread   ();
    } catch (Throwable    e) {    
      return false;
    }
  }

  /**
   * Se  nd a mess          age to Cyc sp  ending no less time than t    he specified     timer allows but   throwing a <code>TimeOutE   xcept          ion</code>
   *   at the first      opportunity     wher    e that time limit is excee       ded.    The con   current mode of C  yc server   communicat  i  on
      * is su  pporte       d by Cyc's poo l of transaction processor t  hread  s    , each of    which can                concurre   ntly proc     ess   an api request.  The  
   * SubLWorker o  bject notifies the caller when the api re  sponse is as chronously received.      
   *  
   * @pa   ram worker a <tt >      SubLW   or     k   er</tt> object that notifi es the caller when work is done
      *
   * @throws IOException when    a commu  nicatio     n error occur    s
   * @      t    hrows      TimeOu tExcepti on w   hen the time li   mit is exceeded     
   * @thr   ows Cy    cApi   Exception when a Cyc api er      ror o ccurs
   */
     public   void conver seBinary(final SubLWorker worker)
            throws IOEx  ception,   T   imeOutEx    ception, CycApiException {   
      logger.fine     st        ("API     reque     st: "    + worker.toSt          ri ng());
           if (   cycAcce    s       s.isClosed() ||     taskProcessingT  hreadDead) {
      thr    ow new Cyc       ApiClosedConnec    tionExcept    ion("Att    empt to com municate to Cyc u       sing a closed connection (" + c       ycAcc   ess.getHostNa  me() + ":" + cycAccess.getBa sePort() + ")");
    }    
       /*if ((!work    er.s  h  ouldIgnoreInv    alidLeases()) && (!cycAc   cess.has  ValidLease())) {
    th      row       new CycApiE     x     ception("Att  e                 mpt to c  ommunicate to Cyc using a conne  ctio  n with an invalid le    a     se." +
    "\  nSu    bLCommand: " + worker   .ge   tSubLCommandCycList().toPrettyCyclifiedStr  ing(" "))  ;
    }*/
    //System   .out       .println("worker: "    + worker);
    i        f   ((worker instanceo         f S    ubL  WorkerS   ynch) && inAWTEventThread()) {
      throw new CycAp  iExcepti   on("Inv       alid      atte       mpt to synchronously communi        cate with Cyc "
              + "fr  o  m the AWT      event thread.\n\n" + worker        );
    }
    Cyc  Symbol    taskPro    cessorReque stSymbol = Cyc    Obj       ectF   actory .makeCyc  Symbol("task-processo       r-request"    );  
      Integer id = null;
              CycList taskProcessorRequest = null;
    boolean isReturn WholeTaskProc  essor Response = fals      e;
    CycList sub LCommand = worker.getSubLCommand();
          final Integer priority = work     er.g        etPriority();
    if (subLCommand.first(    ).equals(CycObjectFactory.    makeCycSym  bol("retu   rn-whole -task-processor-re spon         se"    ))) {  
      isRetur   nWholeTaskProcess  orRes      ponse = true;
      subLComman  d =    (CycList) subLCommand.secon     d();
    }
    i  f (subLCom     mand.first  ().equals(taskProcessorRequ          estSymbol))     {
      // cl          ien   t has supplied the   task-pro     cessor-r        e   quest fo   r            m
      taskP   rocessorRequest        = subLCommand;
      id = (I     nteger) subLCommand.t hird();
      task Processor Req  ue   st.set(6,        uuid.t      oString());     // ove         rride the uuid to identif   y thi   s client
    }   els e {   
      id = nextAp     i        RequestId();
      taskPr   ocessorR           equest = new CycList();  
      taskProcessorRequest.add(taskProce   ssorReque        stSymbol); /      / function
         t  askProcessorRequest.add(subLC  ommand); // requ    e    st             
        ta     skProcesso rRe  q     uest.add(id); // id
      t askPro  ce  ssorRequest.  add(c     lampPriority(prio    rity)); // priority
           t   askProcessorRequest. ad d(myClientName);         // requestor
       tas    kProcesso  rRequest.  add    (CycOb   jectFactor  y             .n il); // cl i     ent-  bindings
        taskProc       essorRequ  est          .add(uu i   d.toString()); // uuid to    identi  f  y this client
     }
      final CycLis t actualRequest           = (CycList) taskP   roce       ssorRequest.       get(   1);
      if (a  ctualRequest.toString().startsW    ith("(F       IF (TERMINATE-ACTIVE-T    A    SK-PROCESS ")) {
          // o     verride the uuid used t  o ide  ntify                       this client
      // (fi f (terminate-active-     task-process i   d uu  id :cancel) (qu    ote (i    gno      re)) (quote (ignore)))
       final       CycList         temp = (Cyc    List) act  ualRequest.second();
      temp.se   t(2, uuid.t oString());
    }
    logger  .fin  est("tas     kProcessorReq  uest  :   "     + ta    skProces        sorRe    q uest.toPretty    CyclifiedStrin g   ("")   );
      WaitingWorkerInfo wa      itingWorkerInfo = new WaitingWorkerInfo(    worker,         t    as       kProcess   orRequ    es  t, isRet  ur    nWholeTaskProcessorR       espo    nse);
      //      tell e   veryone this i  s getting started
     waitingReplyThreads.put(      id, waitingWorkerInfo  );
    SubLWorkerEv    ent e         vent     = new SubLWorkerEvent(worker,   id);
    worker.fi reSubLWorke  rStartedEvent(event);
      //start communication
       sendBinary(taskP  rocessorRequest);    
    }

  stat ic public Intege     r clampPriority(Integer prior   ity) {
    if (priori  ty        .i    ntValue()    > MAX_PRIORITY.intValue()  ) {
      priority = MA  X_PRIORITY;
      } else if (priorit    y.intV   a l ue() < MIN_PRIO   RITY.intValue()) {
      priority = MIN_      PRIORITY; 
    }
       return pri  ority;
  }

  public b o  olean isClo   sed    (  )          {
    return is     Closed;
  }
    
        /**
   * Ret   urns the next             apiRequestId. 
       *
   * @return the next     apiRequ    est    Id
    *  /
    static public synchronized In teger nex      tApiRequestId() {
          return new Inte    g    er(++   apiRequestId);
           }

  /**
   *  Send      s an object to th  e CY      C server.  If the connectio   n is not already   open, it is open   ed.  T he
   *      object             m       u  st be a valid   CFASL-translatable: an Integer, Float,    Double, Boolean, Stri   ng,   or cyc
   *     objec t.
   *
          * @param message the api comman  d
   *
   * @throws IOEx        c   eption when a communi   cation error     occ    urs
   */
  public synchron     i zed void sendBinary(Object message)     
          throws IOException {
    if (   trace >= A PI_TRACE_MESS    A             GES) { 
        L     og.current.println(df.fo  rmat(new Da  te()) + "\n    S   ending r  equest: " +    m    essage + " to connec     tion: " + this);
     }
    if          (logg   er.isLogga  ble(Level.FINE)) {
        log      ger.fine("sendBinary: "   +  DefaultCycObject.stringApiValu      e(messa        g    e));
    }
    cfa    slOutputStream.writeObj ect(m essag        e);
                cfasl    OutputS  tream.flush           ();
        }

  /**
   * Receive  s an object fro m the    CY      C server.
   *
   * @return an array of three    objects, the fi   rst is a Boolean response,   the second is the
   *                     re  sponse o  bject or err or string,      and t  h    e t    hird is a    n indication     that     the otherwise
   *            good response c  ontains an invalid object.
   *
   * @th            rows    IOException when a     communication    s error occurs
   * @throws CycAp   iExc      eption when a  Cyc API      err       or occurs
   */
  pu   blic sy   nchronized Object  [   ] r    eceiveBi nary()
                th    r    ows I OException, CycApiExcept  ion {
    cfas  lInputStrea    m.rese tI  sInvalidO    bject();
         final Object status = cfa   s   lInputStream.read      Object( );
        cfaslInpu    tStream.resetIsInvalidObject();
    f           inal Object   resp     onse = cf  asl      InputStr eam.readObje        ct();
       final Object []   answer =  {n          ull, null,     null} ;
    answer[1] = response;
    // TODO h    an        d    le the in    valid ob      j e  ct in th e callers of th  is se ldom-used method.
    ans      w  er[2] = new Boolea    n(cfaslInpu   tStr          eam.isInvalidObject());
    if (  (status  == null     )        || status.equals(CycObject Fact         ory.nil)) {
      answer[0] = Boole     an.FALSE;

           i     f (trace > API_TRACE_    NONE) {
               fi         nal String res   ponseString = response.toSt   r   ing( );    
          Log.current     .printl       n("received error = (" + status + ") " + responseString);
      }
          return an   swer;              
    }
    answer[0] = Boolean.    TRUE;
    retu    rn answer;
  }

  /**
   *     Re        ceives a binary (cfasl) api request from a cyc s     erver.         Unlike the api response     handled by
         * the receive  Binary method, this m    et      hod              does not expect an input  st   atus object.
   *
   * @return th e       api request expression. 
   *
     * @t   hrow s IOExcep  tion when a communication error occ u      rs  
   * @t     hrow s CycApiExc   eption       wh    en a Cyc API    exception occur  s
       */
  public   synchronized CycList rece    ive  Bina  ryA   piRequest()
           throws IOExcep           tion, CycApiExcepti on {
           c faslInputSt          ream.resetIsInvalidObject();
          CycList apiR       equest = (CycL   is                 t) cfaslInputStream   .re   adObject();
    return apiR  e   quest;
  }

   /**
     * Send  s a binar  y (c   fasl)        a    pi response to a cyc server.  This      method       prep   e       nds a s     tatus object   
    *    (th  e symbol T) to the messa     ge.
   *
     *          @p   ar   am       m    essage the give      n binary api re    sponse
      *
       * @throw   s I    OException when a     communic            ation e   rror occu   rs
          * @throws Cyc ApiException wh   en          a Cyc API error occurs
   */
    public sy  nchronized void  sendBinaryApiRespon  se(Object message)
              thr   ows IOException, C   yc      ApiExc    eption {
    Cyc     List api Resp  onse =      new C    ycList();
       apiRes          ponse.add(CycObjectFactory.t);
           apiResponse.       add(message)   ;    
    c    faslOutpu tStream.writ    eObject    (apiResponse);
    cfaslOutputStream.flush();
         }

  /**
                  * Turn s on the diagnost   ic trace  of socket messages.
      */
  public v    oid  traceO  n()   {
    tr       ace = API_TRACE    _MES SA  GES;
    c     f  aslInpu  tStr     e      am.trace = trace;
    cfaslOu     t        putStream.trace = trace;
  }

   /**
   *   Turns on     th e  detailed diagnostic trace of socket me  ssages.
   */
        public void         traceO    nDetailed() {
        setT   race (API_TR   ACE_DETA   ILED);
    }

  /**
      *   Turns off the diagnostic  trace              of socket message   s.
   */
  public vo    id traceOff() {
       setTrace(API_TRACE_NONE);
      }

  /**   
   * Returns the     trace value.
   *
   * @r  eturn   the trac   e va     lue
    */
        pu     blic int getTrace(    ) {
    return t   race;
  }

  /   **
   *  Sets the soc   ket me  ssages dia     g  nostic trace va   lu   e.
   *
   * @p  a    ram trace the   new socket messa    ges diagn     osti   c trac  e value
          */
  pu       blic void set  Trace    (i   nt   trace)    {
        this.trace = tra   ce;
    cfas  lInputStream  .trace = trace;
    cfaslOutp      utStream.trac       e = trace;
    if (ta    sk     ProcessorBinary       Respon  seHandle   r != null   ) {
      t  askProcessorBinaryResponseHan     d  ler    .inbound       Strea m.tr         ace = tr  ace;
         }
  }  

  /*  * Answers true iff this is a valid         binary       (cfasl) c   onn          ection t    o  Cyc.
   *
   * @return           tr     ue iff this is a va   lid binary      (   cfasl) connect   ion t     o Cyc
       */
  publi    c boolea   n isVal  idBinaryConnection() {
    return              isVa    li   dBinaryConnection   (false);
   }

  /**    Answers true iff            th    is   is a val   id binary (cfasl) co        nnection   to Cyc.
   *
   * @para   m isQuiet the indica   tor for no inf ormational logging
   *    @return true if     f t   his is    a val  id bi   nary (cfasl) connection to Cyc
                         */
  pub    lic boolean isValidBinary    Connection(fi nal bool       ean isQuiet) {
    if (cfaslS ocket == null) {       
         if  (!is Quiet) {
        Log.c  urrent  .  p    rin   tln("Invalid binary c      onnection       b  ecause cfas        lSocket is nul  l");    
               }
      return false;
       }

    if (!cfaslSocket.isConnected()) {
               if (!isQui   et   ) {
        L   og.current.p          rintln("Invalid binary con nection b    ecause cfaslSocket is  not  connected");
            }   
      return fals   e;
    }
         if ((taskProcessorBin  a      ryResponseHandler == null)
               | | (taskProce  ssor         Bi naryResponse Handl     er.inboundS  ocket == nu   ll)) {
          if    (!isQui           et) {
        Log.curr   ent.pri   ntln("Inval   id binary c       onnection   becaus      e       t  askProcessorBin  aryR  esponseHandler.       i     nboundSocket is null");
            }
        retu  rn false;
            }
      if   (!task ProcessorBin    ary      Resp  onseH   andler.inboun        dSocket.isConnecte       d()) {
           if  (!isQuiet) {
        Log.curren  t.println("In  valid b             inary con  nectio   n because tas     kProcessorBinary      Res  p         o  nseHandler.inbou         ndSocket is not conne     ct   ed");
                 }    
      re     tu  rn false;        
    }
    return true;
    }

  /**
   * Returns conn    ec   tion in   form   ation,       s  uitable for diagnostics.
   *
   * @r   et             urn conne      ction information, suitable f       o r diagn     ostics
   */          
  public String con   nectio    nInfo() {
        return "  host "   + hostName + ", cfaslP      ort "     + cfa     slPort;
  }

       /            *    *
   * Gets the UUID that ide         ntifies   this java  api cli    ent conn  ection.
   *    
   * @retur  n the    UUID that ident     ifies this     j         ava api clie   nt c onnec        tion
      */
  public    UUID        ge       tUui    d() {
    re   t              urn uu   id;        
  }  

  /**
        * Sets th   e client name of this api connec      tion.
   *
   *    @p aram myClie  nt     Name th   e client           na  me of this ap            i        connect     ion   
   */
   publi c void setMyCli  ent                     Name(S  tring myClie    ntNa   me) {
    this.myClientN   ame      = myClient    Name;
     }

  /**
   * G   ets    t      he  client name       of this api    c  onnection.
   *
           * @re    turn th  e         client n   a    me of this      api     co    nnect       ion
   */
  p  ublic String getM  yClien      tname(   ) {  
    re      turn myClient     Name;
  }

  /*  *
   * Recove     rs fro    m a sock         et error by interrupting a       ll the waiting reply th         reads.    Each     awakened
         * thread will             de  tect t    he error condition and thro  w an IOExe cp   tion.
   *   /
  protected vo     id interruptA  ll    W   aitin               gReplyThreads() {
    Iterator   iter = w  aiti          n   gReplyThreads.values(  ).itera tor();

         whil      e    (iter.has   Next()   ) {
      Wait   ingWorkerInfo    waitingWorkerInfo   = (Waitin    g WorkerInfo) i   ter.n ext(     );
          i     f  (trace > A      PI_T               RACE_NONE    ) {
        Lo    g.current.prin    tln   ("I   nterrupting re  ply w o     rker " + w    aitingW   o  rkerInfo.getWorker ());
         }        
        try {   
                  w  ait    ingWorkerInfo.worker.can    cel();
                   } catch (java.io.IOException   xcpt) {
        if (      trace > AP  I_TRACE_  NONE) {
             Log.current.println("Co    uld not interru   pt   reply worker "    + w   aitingWorkerInfo.ge   tWorker() + ": exce       ption: " + xcpt);
             }
        }
    }
  }  

  /**
   * Re  cove   rs         fro m a s  o      cket    error by interrupting all the wait     ing reply threads.  Each     awak   ened
   * thr   ead will d   et               ect      the error cond itio     n an    d   thr   ow       an IOExecption .
   */
  p rotected synchron   ize     d vo   id forciblyUnb lockAl   lWaitingWork   e    r       s(Exception e    ) {
     Iterator      it   er = waitingReplyThreads.values().iterator(   )                 ;
    if (e ==     nul   l) {
       e = new CfaslInput      St   r         ea mClosedException("Com  muni cations terminated with Cyc.");
            }      
    w   hile (iter.hasNext    ()) {
      Wa  itingWorke      rI             nfo wa i   ting   Worker Info = (Waiting     Wo  rker    Info) iter. ne   x t();
      i  f (  t          race > A    PI_TRACE_NON  E) {
             Log.cu    rrent.println("I   nterrup   ti   ng reply wor    ker "     + waiting      W   o     rkerInf o.   getWorker());
         }
      SubLWorkerEven   t e   vent          = new S   ubLWorkerEvent(waiting     WorkerInfo.getWorker(),  SubL    Worke    rSta  tus.EXCEPTI ON_STAT    US, e)  ;
      waitingWork  erInfo.wo    rker.fireSu   bLW orkerTerminatedEvent(event);
      iter.re  m  ove    ();
    }
  }

  /**
   * Gets th   e dictionary     o  f waitin    g    reply th  read infor    mation obje  cts.
   *    
       * @return     the dict      io         nary o     f wait  ing reply thread i  nformation   obj e  cts
   */
  p    ublic       Map getWaiti   ngReplyThrea        dInfo   s() {
    retu     r   n waitingReplyThre     ads;
  }
    
            /**      
   *      Resets the Cyc task p  rocessor wh   i  ch is currently processing the a            pi      -req    uest s pecifi       ed by the
     * g   iven id.     If   none     of      the task processors        is currently proc      essessing   t    he spe    cified
   * api-  request, then the reset request is   ign     ored.  When reset,    th         e Cyc task process   or returns  
   *     an error mess   a                       ge to the wa        iting client thread.  Th    e err  or me            ssage     consis          ts of
   * "reset  \nTHE-API-REQ    UEST".
                  *
   * @param    i   d the id of th    e api-reques    t which  is  to be        int      erru   pted an  d canc    elled
              *
   * @throws      CycAp   iEx ception when a   Cy  c API er      ror occu rs
       * @throws IOEx   cepti     on w   hen a       c   ommuni  cation error occurs
   *  /
                  public void resetTaskProcessorByI       d(Integer id)
             th  rows Cyc   A   piException, IOE  xception   {
    resetTas    kProcess  orById(i d.intValue());
         }
   
  /**
        * Reset   s     the Cyc task process   or which is currently processing the api-re     q uest spe   cif     ied b    y  the
   * gi           ven id.   If none of th    e task processors is          curr     ently proce        ssessi   ng the s            pecified
        * api-re          que   st,    then   th e reset request      is ignor  ed  .      W    hen rese t, the Cyc ta        s  k processor r  et            urns
        * an erro          r messag  e to     the waiting cli     ent  thread   .
        *
           * @param    id    the     id      of the api-             re    quest w    h    ich is    to be i    nterrupted  an     d cancelled
   *
   * @throws CycApiException wh     en       a C  yc API error oc      curs
   * @t          hrows IOException when a communic  ations erro      r oc  curs
          */
  public void resetTaskProcessorByI  d(i nt       id)
               thr ows    CycApiExcepti  on, IOException         {
    String c   ommand = makeSubLStmt("reset-api-task-processor    -by-id"  , myC lientN     ame, id       );
            cyc         Access.conve rseCycO   bj  ect   (comm     and     );
  }

         /**
      * Class Ta                    skProce  ssor  Bina      r  yRespon   seHandler h         andles responses from task-pr     ocessor request  s in
         * binary    c  om   mun     ication             mode.
   */
  prote   cte       d class Tas     kP rocesso           rBinaryResponseH   andler extends T   hread {

    /** Maximum num ber   of    local cyc clients su p     ported by th      i     s listener.  */
    pub    lic stati      c    final int MAX_LOCAL_CLIENT_CLIE NTS = 50           ;
                /** The     socket wh  ich listens for ne w connect  ions. *    /
    protecte     d     ServerSocket   lis  tenerSocket = null;
    /** Th     e socket    which receives    as ychronous inb        ound mes     s      ages from th e Cyc se    rver. */       
    pro  tected Socke   t inboundSocket = null;
         /** The binary    inte  rfa       ce inp   ut stream which receives         asychronous m essages from th   e Cyc serve r     */            
                   public Cf  asl   In   p      utStream inboundS      tr         eam;
          /**
          *    The  binary   interface outp ut stream,           w      hich      is    the outp     ut       side of the bidire ctional so   cket,        is
         * used onl   y t   o sta  rt   up and clos  e       down the socket.
               */
      p   rotected C      faslOutputStream inboundOutputStream;
              /** Reference to the parent thread which   will slee   p until this handler is initialized. */  
         prote  cted Thread parentThread;
    /  ** The   (ignore) m  es sage     from the Cy     c server t o test if the  conn e   cti           on is alive. */
    protected    C          ycL i    st i       gnoreMess                 age;          
    /   ** t   he pa            rent CycCo       nn         ec tion */
    pr        otect      ed CycConnection cy    c     Con        nec  t   ion;
     private  volatile boolean isClos  ed = fals         e;
    pri   vate vola  tile boolean isCl      osing = fa lse         ;
    /** t       he s   ynchronization obj    ect to ensure that the stream  s are ready       */
    pri  vate Semapho        r    e      ini         tializedSemaphore;
    pri      vate vola   til      e b     oole               an i   nitial  ized;
      privat    e volat     ile Exceptio n i    nitializationEr         ror = n    ull ;
    /** the indice  s      into    t           he task processor        response object, w   h   i          ch is a li   st   */
    final sta    tic int          TASK_PROCESSOR    _RESPONSE_ID = 2;
      fin  al st    atic int TASK_PROC    ESSOR    _RESPONSE_RESPONSE = 5;
                     final stati  c int TASK_PROCE   SSOR_RE SPONSE_STATUS = 6;
    final static in       t      TAS K_P   ROCESSOR_RE SPONSE_                  FINISHED_        FLAG = 7;

    /**
     * Constructs a Tas      kProcessorBina     ryR   espons   eHandle         r      o  bjec      t.     
     *
         * @p    aram par    entTh    read th    e parent              th    read of this     thread
           * @param cycCon  nection the parent CycC   o   nnectio      n
           */
    pub  lic T         a       skProc   ess       orBin    aryRes      ponseHandler(Thread paren    tThread, Cy    c     Con     nection      c       ycConne        ction ) {
          this.par   entThread = parentThread;
      this  .cycConn  e ction =     cy  c  C onnection;
          ignoreM es sage  = new CycList(       ); 
        ignore   Mes sage.ad d(n        ew CycSymbol("IGNORE")) ;
    }

             publi           c void sta  r     t() {
      ini  tializeSync        h   ronization();
      su      per  .start()        ;
      waitOnSe   tupToComplete();
    }         

    / **
     * Ope   ns     the response so       cket with Cyc , blocks until       the next task-   pr oce  ss or res      pon    se     i     s     a    vailable,      
     *     the    n awakens the client thr ead that    mad   e th         e request.
               */
    publi      c   void r  un() {
          Thread.currentThread().     setName("Tas kP     rocessorBinary                ResponseH     and        ler ");
          Exc    eption clo          si    n                    gExce     ption = nu   ll;
      try {
                     if ((!isC   losed ) && (!isClos     i           n  g)) {
               try    {           
                //   O    pen a s   eco  nd ap     i           socket connect   ion and use it for asych     r   ono    us      api  responses.
                        i     nboundSo    cket =      new Socket(hostName, cfaslPort); 
                             int v       al = inboundS     oc    ket.ge    tR    ece   iveBufferS  iz    e   (   )   ;
                      inboundS     ocket.    setRec                ei       v     e  BufferS    ize(val * 2);
                   i   nboundSoc  ket.setTcpNoDelay(    t rue);  
            inb  oundSoc   k   et.set KeepAlive(true );
                inboun        d      Stream    = new         CfaslIn pu     tStre  a  m(inboundSocket.g   etInputStre      a  m());
                  in            bound   Stream.trace   =      trac      e;
                inbo   u         ndOutputStream =   n  ew   Cfasl   OutputStr   eam(inbou    ndSocket.    getOutputStream         ());   
            //                s  end a one-tim  e request   the to Cyc          server to conf       igure this con     necti on for      subseque    nt         api reponses
                 CycList re      qu   est = n          ew CycLis  t(   );
            re  quest.add(new CycSymbo   l("IN   ITIALIZE-      JAVA-    API-PASSIVE-SOCKET"));
                            r equest.a       dd(cy cConnection.uu  id.toStr ing());
              i nboundOutputS  tr  eam.writeObject(reques  t); 
                 inboundO   utput    Str    eam  .f lu sh(  );
                  // r  ea  d and  ignore the status  
                 inboundS  tream.rese       tIsIn        val  idObject();
                inboundStream.readObject();
                                   // read    and ignore the   response
            inbound Stream.resetIsIn validObject();
            inboun         dStream. readObject();
            inboundS   t              ream.trace =       cycC   o   nnection.getTrace();
                     } catch    (Exception e)  {
                  if ((             !isClo       s     ed)      && (!isC  losing      )) {
                     closingException = e;
                  Log.cur  rent.        pr     i     ntSta    ckTrace(e);
                 Lo  g .current.errorPr   intln("Communication with        Cyc cannot be st  arted: host-" + hostName   +  " port-" + cfaslP     ort);
                  notifySetupComplete d(e);
                }
                     return;
          }
                    }
             / /    signal tha   t we are read y                to go
        notifyS  etupCompl  e  ted(null);
               // Handle me     sssa  ges received on  the   a       sy     chronous inboun d Cyc  connection.
          while ((!isClosed) && (!isClosing)) {
          Ob    j ect s   tatus = nu l  l  ;
                 CycList ta    skProce  ssorResponse = null; 
            bo        olean isInvalidOb je    ct = f     al        se;
        
                 if (isClosed |        | is   Closing) {
                 bre     ak;
          }
                     try {
               /             / r   ead st   atus
            inboundStream.resetIsInvalidObject();
              status     = inboundStream.r   eadObject();
              /    /      re    ad task processor respo    nse
                inboundStr         eam     .reset IsIn   validObject();
                         Object curren   tRespon  s   e = inbo    undStre  am.readObjec t();
               if (!(currentResponse in   stanceof   CycLis  t)) {
              throw new Exception("Invalid task pr ocesso  r respo   nse: " + curren     tRespons  e);    
                        }
                        taskPr  o    cesso      r    Re   sponse = (CycList ) cur      ren tResponse;
               if ( logger.isLoggable      (Level.FI  NE)) {
               logger.fin      e("API response: "    + taskPr       o   cessor    Res ponse.stringApiValue(   ));
            }  
                  isInvali  dObject =                  inboundSt   ream.isInvalidObject();
          }   catch   (E     xception e) {
                        if (taskProcess   i   ngEnded) {
                         if (  tr     ace      > API_TRACE_NON      E) {
                                               Log.current.println("Ending bina  ry mode    tas  k pro   cessor handler.");
              }
            }
                    if ((!is         Closed) && (!isClosing)) {
                  logger.fine("Excepti     on: " + e  .getMessage());

                 if (e ins  tanceof CfaslInputSt    r     e    amClosedExc  eption) {
                     if (trace > API_TRAC E_NONE      )     {
                         Log.current.errorPrin tl n(e.getMes     sage());
                            Log.current.printStackTrac     e(e);
                        }
                } els    e if (e instanceof Run   timeExcep  tion) {
                Log.            current.     errorPrintln(e.getM  essage());
                 Log          .current.printStackTrace(   e);
                          continue    ;   
                 }
                       closingException = e;
                   Log.curren t.pri  ntln("Cyc Server ended binary mode t     a  sk     processor handler.\   n" 
                                                + String    Utils.getStringF   orException(e));
                   }
                     return;
            }

          final   boolean objectIsI   nvalid = isInv alidO        bject;

             logger.finest("AP  I status                    : " + status   );
                    if (trace >= API_TRACE_DETA    ILED) {
               Lo     g.curren    t.println("cyc --> (" + s   tatus + ")   " +    t        askPro   cessorResponse.toString());
                     }

           if (taskProcesso rResponse.equals(ignor  eMessag       e)) {
              continue;
           }

                 try {
            if (trace >=      API_TRACE_MESS      AGES) {
                 Log.    c      urrent.print    ln   (df.   format(n ew Date()) + "\n      Got     r      espo nse: ( " + taskProcesso    rRespo   nse + ")");
                              }
            if (!(taskPro     cessorResponse.get(TASK_PROCESSOR_  RE  SPONSE_ID)   instanceo    f Intege    r)) {
              Log.current.println(df.forma t(new Date   ()) + "\n    Got invalid  respon se id: ("    + tas         kProcessor       Respon    se + ")");
                }

                final Integ    er i          d = (Integer) taskProcessorResponse.get(T  ASK_PR   OCESSO               R_RESPON   SE_ID);
            final Objec      t taskStatus = taskProcessorResponse.get(T   A  SK_PROCESSOR_RESPONSE_STATUS);
             // handle    Cy c images tha    t either support or    do n   ot  support (legacy) the finish   ed    flag
                     fina     l Obj        ect finishedFlag =
                           (ta    skPro cessor   R esponse.siz    e() > TASK_PROCE  SSOR_RESPONSE_FINISHED_FLAG )    ? task   ProcessorResponse.get(   TASK_PROCESSOR_RESPONSE_FINI SHED_FLAG) : CycObjectFact     ory.t;

                     final boolean finished = !(finishe  dFlag ==   CycO    b  jectFact ory.nil);
               final WaitingWork  erInfo w    aitingWorkerIn     fo = (WaitingWorkerInfo) waitingReplyThreads.get(id);
            if  (waitingWorkerInfo == null) {
                 if (trac   e >= API_TRACE_MESSAGES) {
                Log.current.println(df.format(new Date()) + "\n      Got response     with    n    o waiting working: (" + taskP        rocess     orResponse + ")      ");
                  }    
              continu  e;    
              }

            final      SubLWorker  worker = waitingWor  kerInfo.getW   or  ker();
             // used fo    r exampl  e in the XML soap s      er    vic  e where there is an up  stream SOAPBi   naryCycConnection object that
                 // needs the whose task processor response.
             final Object response = wa  iti     ngWorkerInfo .isR   etur   nWholeTaskProcessorRespo  nse ? t    askProcessorRespons  e : task    ProcessorResponse.get(TASK_PROCESSOR_RESPONSE_RESPONSE);

            fin    a l Ru    nnable notificationTas      k = n     ew NotificationTa   sk(taskStatus, o   b   jectIsInva     li         d, worker, response, finished, id);
            t   ry {
                    apiPool.execute(notificationTask);      
                  } catch   (Rej ected  Executi  onExce        pt         ion e) {
              e.printStackTrace();
                   Sys  tem.err   .println("Rejected           notification from  " + wor   ker);
            }
     
                  } catch (Exception xcpt) {
              if ((!isClosed) && (!isCl osing)) {
               Log.current.errorP   rintln(xcpt.getMessage());
              Log.current.printStackTrace       (xcpt);
               }
                      continue;
          }
           } // while  -forever
      } catc h (Exception e) {
            closingException = e;
      }     f   inally {
        if (closingE  xception != null) {
            logge r.l    og(Level.SEVERE, "TaskProcessor terminated    because of excep   tion.", cl      osingExcepti on); 
         }
          t              askProcessingThre    adDead = true;
        logger.finer    ("TaskProc   essor is closi  ng    now.")  ;
        notifySetupCompleted(closingException);
          f     o    rciblyUnblockAllWaitin    gWorkers(closingEx  ception);
              close();
      }
    }

    / ** Clo   ses the passive inbou     nd api re sponse socket.     */
    public synchronized void close() {
      if (      isClosed) {
        return;
      }
      isClosed = true;
      if (apiPool != null) {   
          try {
          ap    i         Pool.shut    downNow();
        } catch (Exception  e)  {
        }
        ;
        try  {
          ap i     Pool.setMaximumPoolSize(0   );
        }    catch (Exception e) {
        }
           ;
              t   ry {  
            apiPool.setKeepAliveTime(0, TimeUnit.MILLISECONDS);
        } catch  (Exc    eption       e) {
            }
        ;
        }
      if (inboundO ut    putStream != null      ) {
        tr     y {
              inboundOutputStre   am.close();
          } catch (Exception e) {
          //ignore
        } finally {
           inboundOutputStr   eam   = null;
          }
      }
       if (inboundS    tream != null) {
                try {
          inboundStream.close();
        } catch (    Exception e) {
          //ignore
        } f inally {
          inboundStream = null;
              }
      }
      if (trace > API_TRACE_NONE) {
        Lo g.current.println("    close    d inbound so   cket associated     with         " + uuid);
      }
    }

    priva   te void waitOnSetupToComplete() {
       // avoid blocking on this ptr, wh        ich would stop     t he
      // notify  SetupCo  mpleted metho    d from working correctly
          try {
          initializ   edSemaphore.ac quire();
      } catch     (InterruptedExcep          tion xcpt) {
        initia   lizationError = new IllegalSt  ateException("Una    ble to init  ial ize Cyc communication.");
         System. err.print      ln("In   terrupted during wait(): " + xcpt);
      }
         if (initia     lizationError != null) {    
        throw n ew CycApiException("  Cannot star   t communications to Cyc     .", initial      izationError);
      }
    }

    priva te void initializeS   ynchronization() {
         synchronized (this) {
        ini     tialized = false;
              initializedSema phore = new Semaphore(0);
      }
        }

    private     voi d notifyS     etupC  ompleted(Exception e) {   
      synchronized (this)       {
               initializationError  = e;
        initialized    = t   rue;
      }
        initializedSemaphore.release();
    }

        public class Notif  icationTas   k implements    Runnable {

      p   rivate fin    al Object tas     kStatus;
      private final boolean objectIsInvalid;
      private final SubLWorker worker;
         private     final Object respons     e;
      private f  inal boolean finished;
       private final Integer id;
         private volatile boolean workOnThisTask = false    ;

      public Notifi cationTask(final  Object       taskStatus, f     inal bool ean objectIsInvalid,
              final SubLWorker work    er, final Object    response, final boolean finished,
              final In   teger id) {
        this.taskStatus = taskStatus;
        this.objectIsInvalid = objectIsInvalid;
        th   is.worker = work er;
        this.response = respo   nse;
        this.finished = finished;
        this.id = id;
        worker.  getNotificationQueue().add(th  is);
      }

      publ  ic v  oid run() {
        w  hile (w           orker.getNotificationQueue().peek() != this) {
          try {
            Thread.sleep(1   );
          } catc  h (InterruptedEx ception ie) {
               Thread.curren tT   hread().interrupt();
            return;
            }
        }
        try {
          if (taskStatus.equals(CycObjectFactory.nil)) {
                if (!objectIsInvalid) {
              // no e     rror occurred, no excepti on
              worker.fireSubL      WorkerDataAv       ailableEven    t(new SubLWorkerEvent(worker, response,   -1.0f));
                if (finished) {
                    worker.fireSubLWorkerTerminatedEvent(new SubLWorkerEv   ent(wo    rker, FINISHED_STATUS, null));
                  }
            } else {
              // no API error sent from the server but th    e response contains an invalid object
              worker.fireSubLWorkerTermin  atedEvent(new SubLWorker      Event(worker, EXCEPTION_STATUS, new CycApiInvalidObjectException("API response contains an invalid object: " + response.toString())));
                  }
          } else {
                  // Error, status cont   ains the error message
            //@ToDo need to diferrentiate between exceptions and c    ancel messages    !!!!!!!!!
             if (taskSt    a    tus instanceof String) {
              worker.fireSubL  WorkerTerminatedEvent(new SubLWorkerEvent(worker, EXCEPT   ION_STATUS, new      CycApiServerSideException(taskStatus.toString())));
            } else if (taskS       tatus  instanceof   CycSymbol) {
               worker.fireSubLWorkerTerminatedEvent(new SubLWorkerEvent(worker, CANCELED_STATUS     , null));
            }
          }
          if (worke  r.isDone()) {
               waitingReplyThreads.remo ve(id);
          }
        } finally {
                try {
              NotificationTask notification = worker.getNotificationQueue().poll(1, TimeUnit.MICROSECONDS);
            if (notification != this) {
              throw new RuntimeException("bad notification");
            }
          }    catch (InterruptedExcep   tion ie) {
            Thread.currentThread().interrupt();
              return;
          }
          }
      }
    }
  }

  /** Pro vides a timer thread for cancelling the connection if it takes too long to establish. */
  private cl   ass ConnectionTimer extends Threa  d {

    /**   Constucts a new ConnectionTimer instance. */
    ConnectionTimer() {
    }

    /** Waits for either  t    he Cyc   Connection constructor thread       to set the done indicator, or ki     lls the
     * connection after the time   out is exceeded. */
    public void run() {
      try {
        while (!isCycConnectionEstablished) {
          T  hread.sleep(WAIT_TIME_INCREMENT);
          timerMillis = timerMillis + WAIT_TIME_INCREMENT;
          if      (timerMillis > TIMEOUT_MILLIS) {
             throw new TimeOutException    ("Timeout exceeded when connecting to Cyc.");
          }
        }
      } catch (InterruptedException e) {
        Log.current.println("Interruption while waiting on Cyc connection establishment, closing sockets");
        // close the so   cket connections to Cyc and kill any awaiting api request threads
        if (trace == CycCo nnection.API_TRACE_NONE) {
          trace = CycConnection.API_TRACE_MESSAGES;
        }
         close();
                  Thread.currentThread().interrupt();
        throw new IllegalStateException("Interrupted while establishing Cyc connection.", e);
      } catch (TimeOutException e) {
            Log.current.println("Timed out while waiting Cyc connection establishment, closing sockets");
        // close the socket connections to Cyc and kill any awaiting api request threads
        if (trace == CycConnection.API_TRACE_NONE) {
          trace = CycConnection.API_TRACE_MESSAGES;
        }
        close();
        throw e;
      }
    }
    /** the timeout duration in milliseconds (one minute) */
    final long TIMEOUT_MILLIS = 60000;
    /** the wait time increment */
    final long WAIT_TIME_INCREMENT = 1000;
    /** the wait time so far in milliseconds */
    long timerMillis = 0;
    /** set by the CycConnection constructor process to indicate that the connection to Cyc is established */
    volatile boolean isCycConnectionEstablished = false;
  }
  public static final DateFormat df = DateFormat.getDateTimeInstance();
  public ApiThreadPool apiPool = new ApiThreadPool();
}
