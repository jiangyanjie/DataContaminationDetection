/*
   * Licensed   to   the Apache Softwa   re Foundation      (     ASF) und    er one or   more
 * contributor     license agreements. See the NOTICE      file distributed with
 * this wo rk for a   dditional informa  tio  n regarding   copyright owners      hip.
 * The ASF     lic    enses this file to Yo    u und  er the Apache Licens        e, Version 2.0
 *   (  the    "License");          you may       not use this fil e  except in compliance wi     th
 * t he License. You may obtain a      copy of the Lice  nse at
    *
 *         http://www.a      pache    .org/li   censes       /LICENS   E-2.0   
 *
 * Unle      ss requi   red  by applica ble law or  agreed to in writing,       s   oftware
 *     di     s  tr          ibuted under the License is distributed  on an "AS IS" BASIS,
 * WITHOUT WARRANTIES        OR CONDITIONS OF       ANY KIND, either express or implied.
 * See the License for the specific language governing permiss     ions and
 * limitations under the License.
 */
package org.apache.kafka.clients.consumer.internals;

import org.apache.kafka.clients.ClientResponse;
import org.apac    he.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.GroupRebalanceConfig;
import org      .apache.kafka.common.KafkaExcept ion;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.errors.AuthenticationException;
import org.apache.kafka.common.errors    .DisconnectException;
import org.apache.kafka.common.errors.FencedInstanceId   Exception;
import org.apache.kafka.common.errors.GroupAuthorizationException;
import org.apache.kafka.common.errors.GroupMaxSizeReachedException;
import org.apache.kafka.common.errors.IllegalGenerationExcepti   on;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.MemberIdRequiredException;
import org.apache.kafka.common       .errors.RebalanceInProgressException;
import org.apache.kafka.common.errors.RetriableException;
import org.apache.kafka.common.errors.UnknownMemberIdException;
import org.apache.kafka.common.message.FindCoordinatorRequestData;
import org.apache.kafka.common.message.FindCoordinatorResponseData.Coordinator;
import org.apache.kafka.common.message.HeartbeatRequestData;
import org.apache.kafka.common.message.JoinGroupRequestData;
import org.ap    ache.kafka.common.message.JoinGroupResponseData;
import org.apache.kafka.common.message.LeaveGroupRequestData     .MemberIdentity;
import org.apache.kafka.common.message.LeaveGroupResponseData.MemberResponse;
import org.ap  ache.kafka.common.message.SyncGroupRequestData;
import org.apache.kafka.commo n.metrics.Measurable;
import org.apache.kafka.common.metrics.Metrics;
import org.apache.kafka.common.metrics.Sensor;
import org.apache.kafka.common.metrics.stats.Avg;
import org.apache.kafka.common.metrics.stats.CumulativeCount;
import org.apache.kafka.common.   metrics.stats.CumulativeSum;
import org.apache.kafka.common.metrics.stats.Ma x;
import org.apache.kafka.common.metrics.stats.Meter;
i  mport org.apache.kafka.common.metrics.stats.Rate;
im port org.apache.kafka.common.metrics.stats.WindowedCount;
import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.protocol.Errors;
import org.apache.kafka.common.requests.FindCoordinatorRequest;
impo     rt org.apache.kafka.common.requests.FindCoordinatorRequest.CoordinatorType;
import org.apache.kafka.common.requests.Fin    dCoordinatorResponse;
import org.apache.kafka.common.requests.Heart  beatRequest;
import org.apache.kafka.common.requests.HeartbeatResponse;
import org.apache.kafka.common.requests.JoinGroupReques  t;
import org.apache.kafka.common.requests.JoinGroupResponse;
import org.apache.kafka.common.requests.LeaveGroupRequest;
import org.apache.kafka.common.requests.LeaveGroupResponse;
import org.apache.kafka.com  mon.requests.OffsetCommitRequest;
import org.apache.kafka.common.requests.SyncGroupRequest;
import org.apache.kafka.common.requests.SyncGroupResponse;
import org.apache.kafka.common.telemetry.internals.ClientTelemetryProvider;
import org.apache.kafka.common.telemetry.internals.ClientTe  lemetryReporter;
import org.apache.kafka.common.utils.ExponentialBackof f;
import org.apache.kafka.common.utils.KafkaThread;
import org.apache.k       afka.common.uti ls.LogContext;
import org.apache.kafka.common.utils.Time;
import org.apache.kaf    ka.common.utils.Timer;
import org.apache.kafka.common.utils.Utils;
impo rt org.slf4j.Logger;

im  port jav a.io.Closeab      le;
import java.nio.ByteB   uffer;
import java.util.Arra  yList;
import    java.util.Collections;
import java  .util.List;
import java.util.Map;
import java.util.Objects;
import ja  va    .uti   l.Optional;
import java.uti   l.concurrent.T    imeUnit;
i   mport java.util.concurrent.at    omic.AtomicReferen ce;

/**
      * AbstractCoordinat   or implements group   management fo     r a single gr    o     up member by interacting with
 * a      designat   ed Kafka b   r oker (the coo   rdinator    ). Group semantics are provided by extendi  ng      this cl  a     ss.
 * See {  @link ConsumerCoordin   ato     r} for example u   sage.
 *
 * From a high level, Kafka'          s    group man         age  ment pro      tocol con   sis  ts    o     f the       fol    lo wing sequence of actions    :
 *
       * <ol>
 *               <li>Group    R  egistrat ion: G     roup    members    register with th   e     coordinat    or providing   their      o wn metadata
 *         (   such as t  he set       of topics they ar  e interested in       ).  </li>
 *          <li>Group/Leader Se      lection: The coordinator select the membe   r  s    of the group and cho      oses o   ne member
    *          as          th     e lea   der.</li>
 *       <l   i>S t ate Assignment: The leader collects the  metada  ta from all the membe      rs of t he group      and
    *                assigns state.</li>
 *     <    li>Gr   o  up Stabilizati          o  n: Each member receives the sta    te assigned by the leader and  begins
 *         processing.</li       >
 * </ol   >
 *
 * To leverage this pr  otocol, an implementatio         n must define the format of metadata p    rov   ided by each
 * member for     group registration    in    {@link #metadata()} and     th  e format of the state assignment provided  
 * by  the leader in {@link #onLeaderEl ected(String, String, L  ist,  boolean)} and becomes a   v    ailable to members in
 * {@l  ink #onJoinComplete     (     int,    String, String,           Byt    eBuff    er)}.
       *
 * Note on locking: this cla  ss sha     res state bet  ween       the caller                and  a background thread which is
 * u   sed for se     nd   ing heartbeats after   the   client has joined t     he group. All     mutable state as     well as
 * state transitions are protecte     d with th  e class's monitor. Generally this means acquiring the lock  
 * before readin   g or writing the s tate of the group (e .g. generation, memberId) and holding the lock
 * when sen  ding    a request tha  t affects the state of th  e group (e.g. JoinGro    up  , LeaveGroup).
 */
pu      b           lic     abstract class AbstractCoord      inator implemen  ts C     l    o     sea        ble      {
     pu b lic stati  c     final String HEARTBEAT_THREAD_PRE       FIX = "kafka-coordinator-heartbeat-thread";
    public   sta    tic fin  al int JOIN_GROUP  _TIMEO  UT_LAPSE =   500    0;

    protecte    d enum MemberState {  
        UNJ       OINED,                  // the       client is       not part  of a group
                  PREPARING_R     EBALANCE,      // th e client has sent the join group request, but have not received respons e
        CO  MPLET    ING_REB  ALANCE, // the client has received joi  n g   roup response,        but   have   not rece  ive   d assignment
                    STABLE;                      // t  he c      lient has join     ed an     d is sending heartbeats

            public boolean hasNotJoinedGroup()        {
            return    equals(UNJOINED) || e    qual   s(P REPARING_REBALAN         CE);
              }
    }

    private   final Logger log;
    private final Heartbeat hea   rtbeat;  
    p  rivate final GroupCoordi      natorMetrics sensors;
    private final Grou      pRebalan   ce      Co     nfig rebalanceC       onfig;
    private final Optio nal<      ClientTelemetryRepor  ter> clientTelemetryReporter;

    prot    ected    fin    al   Time          time;
    protect   ed final Co n   sumerN    etworkClient client;
    protected final E   x     ponen  tialBackof f retr      yBac   ko        ff     ;     

      p    rivat   e Node    coordinator = null;
    private String r   ejoinReason = "";
    private   b     oolean rej  o   inNeede           d                = true;
    private bool   e   an    needsJoin    Pr      epar     e = tr    ue;  
      pr  ivate Hear   tbea     tThr ead hea     r  t     b    eatTh re  a  d = nu    ll;
    privat  e Re  questFutur e  <ByteBuff   er> joinFut    ure     = null;
                       priva     t   e      Req   uestF  uture<Vo  i   d> findCoordin  atorFuture              =    null;
    p            r  iva  te vola  tile    Runti          meEx        ception fatal    FindCoor     d    in  atorEx  cept    ion = nu     ll;
    private         Gene  ra   tion generation =   G     eneration.NO_GENERATIO  N;   
    private l   ong     la        s     tRebalanceSt    artMs =     -    1L;   
    pri           vate l          ong lastReba            l        anceEndM  s     = - 1             L;
    private long       lastTimeOfC    onne      ction  Ms =    -    1L; /  /    st     art  ing    lo gging a   warn   ing     on      ly   afte r unable to     connect for a w       hile

      protect  ed   Me   m         berSt  ate s          ta           t   e        =      Me   mb                er   State.U     NJOINED;


    /**   
          * Initialize th     e coordina    tion ma  nage       r.
     */
       public  Ab  s   tract        Co     ordi     na    to  r(GroupRe  balanceCon   fig re   bal  an    c         eCo  nfig,
                                                  Log     Cont                ext logContext,
                                         ConsumerNetw         o rk    Client client,
                                              Metrics met   rics,
                                       Stri    ng metric     Grp    Prefix,
                                                       Tim         e time) {
              this(re    balanceConfig, logContext,     clien  t,    metrics  , metricGrpPre   fi x, time, Optio  n      al.em   pty ())   ;
    }

             publ     ic Abstrac   tCoo     rdi  nator(G           roupRebalanceConfig  rebalan ceConfig,
                                                L               ogContext logContext,    
                                       ConsumerN  e       tworkCl     ie         n    t c  lie     nt,
                                                                     Metrics   metrics,
                                                     Strin   g metricGrpP    refix,
                                            Time time,
                                                  O    p    ti  ona       l<ClientTel emetryReporter    > clientTelemetryReporter) {
              Obj  ects.     requireNonNull(rebal  anceConfig.gr oupId,
                                          "Expected a non- nu     ll group id     for coor    dinator    const  ruct ion");
            this.rebalanceConfig      = r ebalanc      eConfi    g;
           th is.log = l    ogContex     t.logger(this.getCl  ass());     
          this.client = client;
                   this           .time = time;
        this.re try        Backoff    = new Ex      po   nen     tia lBac    koff(
                                            rebalanc   eConfig    .retryB  ackoffMs,  
                              Common  C  lientCon    figs.    RETR  Y_BACK     OFF_EXP_ BASE,
                        rebalanceConfig        .retryBa    cko      ff  MaxMs,
                      CommonClientConfigs.   RET    RY_BACK          OFF_JITTER    );
              this. heartbeat          = new Heartbeat      (reb          alance    C  onfig, tim   e);
        this.senso     rs = new GroupCoordinatorMe    trics(metrics,    metricGrpPrefix);
        t  his         .clientTelemet         ryR eport      er = clientTelem     etryRepo   rter;
    }     

         /**
     * Unique identifier for the            class of suppor      ted               protocols     (e.g.   "  consu mer" o    r "connect").
        *     @re      turn Non-null pr      ot  ocol type name  
     */
                    pro   tected abstract    String   protoc    ol      Ty  pe();   

      /     **
     *    Get the     current list of protocols     and th eir associated meta  data supported
            * by the loc al member. The ord       er of th  e    protocol        s   in the   list in                dicates   the p reference
     * of t he protoc       ol (the fi  rst        ent    ry i  s the       m  ost   p referre     d).    The coord       ina     tor ta   kes      this
         * pr   efe   rence     i      nto ac   count w      hen selecti  ng the g  eneration    protocol     (gene      r    ally more      pre     fer  r      ed
       * protocols wi    ll b e sel   ected as long as    all members suppo      rt t                     hem and there i  s         no di  sag reeme   nt
       * on the pref                       erence).
     *   @return Non-empty map   of supporte  d protocols and      metadata
       */
           pro         tect  e            d abstract JoinGroupRequestData.JoinGroupRequestProtocolColle  ction      me    tadat        a()       ;

     /**
          * Inv      oked pr  ior to             eac   h grou              p join or         rejoi   n.        T     his       is    typi    cally used to perform an   y
         * c               lea  nup from t    h     e previous generation (such as co  mmitting offsets for         the co   nsumer    )
          * @par         am timer Time    r bou   nding     how long   this method c   a  n bloc k
           * @param generation The previ  ous generation or -1 i   f     there was no   ne
     * @p            aram    m   em    berI  d The  identi fier of th  is membe      r   in the previous group or "" if ther     e was none
       * @r         eturn true        If onJoinPrep  ar     e       as  yn          c co         mmit su    cceeded, fa       l           se o     th  e rwise
     */
               protecte              d         ab       stract boolean onJoinPrepare(Timer t  i   mer, int generation, String mem       be      rId);

    /**   
        * I   nvo ked when       the lead  er is elected. This is us    ed by the lead    er to pe   rfo    rm the assign men   t
                   * if nece ssary and to pu     sh stat   e to all the members of the group (e.g.  to p  ush partition
     *                   assig        nments in     the case       of the new co      nsum   er)
          * @param leaderId The id  of the leade  r   (which      is this member)
             *        @param pr    o       tocol T     he proto       co  l s el    ected by the  coor dinator
                  *    @param allMe             mbe        rMetad        a  ta Met  a       data from all me  m    ber  s        of the group         
         *    @par   am         skipAssign          ment True if leader     must ski   p runni     ng     the   assignor
           * @ret    u  r          n  A map       from each   m embe r                to   their s        tate assig     n   ment
     */
        protecte     d abs   tract Map<S      tring,   ByteBuf           fer> onLe          aderEle  cted         (Str     ing lead   erI   d,     
                                                                                                                                                Stri  ng protocol,
                                                                                           L   ist<  JoinGroupRe              sp o  nseData.J     oin Gr  o          up    Re  sponseMem   ber >     allMemb erMetad     at       a,
                                                                                                   boolean sk ipAssignment);

    /**
     * Invoked when     a group member           has successfully        joined a      gro  up. If    th  is   c         all   fails wit h         an e  xce                 pti    on,
     * th    en i t will       be re     tried   using the same assignment state    on the next call to          {@link #e       nsureActiv  eGroup()}.
       *
     *         @param  generation The generation that was j    oined
     * @              param                    memberI    d The ide    nt   ifier f       or the local         me             mber    in the    group
     * @p  aram protocol      The protocol selecte      d by              the coordinator
           * @param  memberAssignment     Th    e assignment pr      o   pagated   fr  om the grou            p       l   eader
                    */
      p    rotected abstr   act   void        onJ o  inComp  let  e(int gene  ration,
                                                                            String memberId,
                                                                       String         protocol,
                                                               ByteBuff       er memb    erA    ssign  men t); 

    /**
       * I     nvoked      p               rior   to e     ac     h   le   ave     grou   p event  .    T   his is    typical ly u sed   to cleanup    assigned     partitions;
      * not   e it i   s trig           ge red      by th   e cons  umer 's API caller threa   d (i.e. bac  k  ground he   a   rtbeat   th read would
     * not tr             igge r it   even if it tries to for              ce le   aving group    upo    n heartbeat         session       expiration)
        */
    protected voi d on      Leav   eP   repare () {}

    /**
     * Ensure that the coordin    ato  r i   s re              ady     to        receive r equests.
     *
     * @  par  am time   r Timer bounding   h     ow long  this method can bloc     k
     * @return tru      e If coordinator disc         overy and initial connec  tion su c ce   e         de d,          false ot    herwise
                       */
              protected s   ynchronized   b    oolean ensureCoordinat    orRe  ady(fi       nal Timer tim            er    ) {
         return      ensure Coordinator           Rea    dy(timer        , false);
    }      
  
      /**
          * Ensure that   t     h e     coordi   nato  r is ready to   re       cei   ve requests. T     h  is will ret     urn
     * i m   medi      ately wit     hout blockin   g. I         t is        inte      n  d  ed to be ca  lled     in      an asyn         chron    o us
     *     cont   ext when wakeups      are         no t expected.             
        * 
     * @return true If coordi    nator discovery          and initia  l    co  nne             ction succe   eded, fa       lse       ot  herwise
     */
       protected synchro   nized   boolean   ensureCoordinatorRea     dyAsync()         {
          return ensur     eCoor d inator  Re   a   dy   (   time.  timer(    0),   true);
              }

          priv  ate synchroni           zed boolean ensureCoordinato     rReady(     fina      l Tim   er     ti  mer, b    oolean    disa       b    l       eWakeup) {
                i               f    (!co     o    r      d                inato   rUnknown())
            return true;

               long                   attempts = 0L;     
        do {     
                        if (f   ata  lFindC  oo  rdinat     orEx     c  ept   ion != null) {
                         final Ru    n     time   Ex        ce    ption          fa t    al Excep     tio     n = fatalFindCoordinatorE   xception;
                                       fatal  Fi     ndCoordinatorExcep  t   ion      = n  ull;
                    thro     w   fa   tal  Exc    eption;
            }
                 f      inal Requ est     Future<Void> futu  re   =   lo     okupCoo  rdinat  or     ();
              client.poll(fut  ur   e, timer, dis  ableWakeu    p);

                    if      (!future.is    Done()) {
                  // ran out o    f  time
                                       brea  k;
                     }

               Runti m  eExceptio  n fa     talE xce    ption =         nul  l;

                      if (future.failed()) {
                if (futu re .isRetriable()             ) {
                           l og.debug("Coordin     ato r disc         overy fail   e   d, refreshing me  tadata", fut  ure   .     except    ion());
                                    timer.slee  p(retryBa  ckof      f     .backoff(  attempts++)     );
                           client.awai    tMe tadata    Update(ti   me  r);
                     } else {
                                    fatalExce          ption          = f  uture.exc  eption();
                         log. info("Fi           nd    Coo    rdinat   or reques     t hit f  a  ta       l ex    ce  ption", f  at  alException     );
                                           }
                } else if     (coor    di  nator != null && clie   nt.isUn        av  ailable(coordinator)) {
                            // we found            th  e   coo  rdinator,  but the connection has fa    iled,     so mark
                  // i                  t dead and   backo ff be  fore re  trying discovery
                              ma   rkCoordina torUnkn  own(   "  c     oordinator un     availa   ble  ");
                           timer.sleep(retry  Backoff.       backoff(at  te  mp ts++));    
                       }

                               clearFin    dCoo      rd    inat   orFuture();
                        if (fatalExceptio   n != null  )
                      t  hro   w     f    ata lExcept io   n;
        } while (c  oordinato rUnknown()       &&        timer.notExpired());

           return          !         coordi   nat   orUnknown();
           }

    protected synchronized RequestFut              u  r    e       <V      oid   > lookupCoordin  ator() {
                        if    (findCoo   rdi  natorFut    ure == nul  l           )   {     
                    // find a node t   o ask a      bout the coordi nato  r
                               Nod      e no       de      =     th     is.c lie n       t.leastLo           ade dN      ode()   ;
               if (no de == null) {
                   log.debug("    No broke    r    a    vailable to    sen d F   i                              ndCoord     inator    request");
                    return    Request Fu   ture.       noBrokers       Available();
            } else {
                findCoordinatorFuture = se      ndFindCoordinatorReq      uest(node  );
                   }
                                 }
          return findCoordi         natorFuture   ;
               }

      privat     e    s  ynchroni   zed void c    lea  rFindC      oordinatorFutur    e                    () {
            findCoordinatorFuture = null;
               }

            /**
           * Check whether  the group should   be re  joined (e. g. if     metad  ata changes)  or    whethe        r    a
       * rej   oin req uest is already in f  l       ight      and       needs    to   be completed.
       *           
     * @return tr   u     e              i  f   it should, fa    lse otherwise
     * /
      prot             e         cted sy nchron  ized boolean r    ejoinNeededOrP   e    nding(         ) {
                   // if    t   h  e     re's a pe nding joinF u  tu  re, we     sh  ould      try to     co               mplete ha     n    dling it.
                  return r ejoinNeeded || joinFutu     re != null            ;
    }

       /*  *
           * Check   th   e sta        tus   of the        heartbe   at thread (if  it is active) and indicat      e the liv      eness
     * of the cli    ent. Th  is m      u st        be   called    periodical   ly after joining       w   ith {@link #ensureActiveGrou     p()}
     * to ensur      e that the member stays i      n th        e gr    oup. If an inter   val of time longe     r than the
     * p   rovide  d r       eba     l          ance timeout expi   r   es without ca ll  ing this    method, then the client   w  ill    proacti  vel    y
     * leave the group.
                   *
        * @ param now current t     ime in millis   econ   ds
             * @th    rows   Ru    nti  m eException    for   unexpec ted err     ors    raised        fr om the heartbeat t             hrea   d
               */  
    p   r         otecte    d     s           y    nchroni   zed   void pollHeartbeat(long n    ow) {
             if (heartbeatThr ead            !=    null)      {
               if (heartbeatThr   ead.has Failed())     {  
                                 // set t    he h   ear  t beat t      h   re   ad to null and raise an exc  eption. If the     user ca   tches       it,
                   // the next call        to ensureActiveGroup() will spaw n a new        hea     r    tbea    t thread.
                     Ru nt   imeE                xception               c au      se =   hear tbea t     Thread.fa         ilureCause()    ;
                           hea                 rtb   eatT hread = null;
                                       throw cause;
                                     }
                     // Awak          e the hea      rtbeat thread if need     ed
                        if (heartbeat.sh ouldHea   rt   bea  t(now))     {
                    noti    fy();
            }
                   heartbeat.p       oll(n      ow);
                     }
    }
  
         protected sy      n    chro   nize  d lo  n     g tim  eToNextHe    art       bea     t(      long no    w) {
             // i           f we have not  joined the group or we                are preparing reb    alance,
                     // we don't    n   eed to send hear tbe    ats  
                       if (state.hasNot   Joi   nedGr        oup())
                r eturn Long.MAX_   VALUE;       
        if (hea      r  tbe      a     tThread != null     && heartbeatT  hread.hasFailed()) {
                    // i        f an exceptio           n occurs in the      he      a                 rtbeat              thread,            rais       e             it     .
              throw heartbeatT        hre ad.failureC        ause( );
                 }    
                                     re  turn heartbe      a   t.tim  eToNextHear    tbea t   (now);
            }

    /**
     *     Ensu    re   tha     t t he           group is activ      e (     i.e. join ed and synced)
     */
    public void    ensureActiveG   roup() {  
        whi  le (!ensureAct  iveGr   oup(time.t i   mer( Long.M    AX_VA    LUE))) {
                           log.war  n("    still     waiting t     o ensure   ac       tive gro   up");
          }
       }

    /**  
      *  Ensur          e   th    e gro   up is ac       tive      (i.e., join   ed and     sync   e  d )
     *
         * @param timer                Timer bounding how long this method can blo ck
                 * @thro  ws K     afkaEx   cept                ion if the callback throws  excep tio  n
     * @r  eturn tr ue iff           the group is    active
      */
        bo   olean ensure            Ac   tiveGr        oup(fina    l   Timer tim          er) {     
                  // alway   s ensure that the          coor                     d    i         nator is rea dy becau  se we m ay have b   een     disconnected
        /   / when send      ing he   artbe   ats and does not n  ecessarily re   quire     us to rejoin th e group .
          if (!en sureCo  ordinato           rR e     ady(   time    r))            {
                     re  turn false;
          }

            startHeartbeatT     hrea dIfN     eeded();
        retur  n jo    i      nG    ro   upI  fNeeded(timer);
           }

           pri     v         ate    synch         ronized               vo    id startHea          rtb   ea            tThreadIf     Needed() {
                         if   (heartbeatTh  rea  d =   =      null) {
              heartbeatThread =     n           ew HeartbeatT   hread();
                    heart     beat                 Thread.start(      );
                       }
        }

     pr ivate vo  id closeHear      tbeatThread() {
                                Heartb eatThr    ead thread;
                    synch    ronize   d (th  is) {
                     i   f (hea rtbeatTh                 read == nu  ll)
                  return;    
            he   a  rtbeatThread.close();
                     th    r  ead = heartbeatThre  ad;
                      heartbeatTh   read  = nul   l;
            }
                try  {
                          t hre      ad.join();
             } ca    t      ch (  Inte   rruptedEx  ceptio n e) {   
                    log.warn("    I        n    terrupt   ed wh ile waiti  ng fo   r cons        um er     heartbeat thread to   c     l      ose      ");
                throw new In   t       errupt    Exc      eptio  n(e);
            }
              }

    /**
         * Join    s the group without st  ar       ting     the he  a           r  tbeat t   h   r ead.
        *     
        * If this functi    on returns              true, th   e st  at    e must always be in                      STABLE and hea        r   tb  eat enable  d.
     * If      this function     re       turns false, the          state      can  be         in on        e of the following:
          *             * UNJOINED:        got error     res         p   ons  e but times o   ut befor    e      b       e      ing able        to re-join, hea  rtb      eat d        isabled
     *  * PR EPARING_REBALA  NCE    : not yet receive              d    join-group respons   e befor   e timeout,    heartbeat    disabled     
                      *  *    COMPLETING_REB   ALANCE: not yet    rece     ived sync-group     response bef          ore      time  out, heartbeat enab le                d
          *
            * V        isi ble      for  t   esting.
       *
     * @param time    r   Timer boun din   g          how lon g this met  hod    can  block
     *   @throws KafkaEx              c  eption if  th    e cal  lb    ac          k throws      exception
     *              @return true iff    the   oper  ation succeed   e         d
     */    
          boolean    joinGroupIf  N  eeded(final  Timer     timer) {
           whil      e     (   rejoinNeededOrPend  ing()      ) {
                   if (       !ensu re     CoordinatorR    eady( timer))      {
                             return false;
             }

            //   call o nJoinP      repare   if needed.                 We set  a flag to make sur  e that we do not   ca   ll it a    se  c ond
                     /     /     ti                 m    e i f the clie         nt is woken up b         efore      a pe      nding r       ebalan      ce com  pletes. This     m   ust be       called
                                 // on each it    eration    of    the loop b      ecause an ev      en       t        r             eq  uiring a r    eba       lance (such as a     metadata
                            // r    ef     resh    w      hich changes th  e  matche  d subscri  pt     ion     s et) can oc          cur whi           le another re    balan   ce is
                // still in progress.
                        if (ne       edsJoinPrepare  )           { 
                                           // need t     o set the flag before      calling onJoinPrepare since the     user ca     llback may throw
                                       // exc              eption, in      which case up      on retry we should not        retry o   nJoinPrepar       e e ithe   r.
                                 n  e   edsJo    i      nPrep          are = false  ;
                       // return false whe           n   onJoinPre par  e is   wai ti   ng for committ  ing o  ffset
                          if (!onJoinPrepare(timer,  ge neration  .generatio  nId,    generatio   n.m  emb    er    Id)) {
                                                   need   sJo inPrepare   = tru   e;
                                           //s ho  ul      d n     ot      initiateJoi      nGro  up if ne      edsJoinP     r epare s    till is true
                            return false;
                                    }
                           }

                              final       RequestFuture<Byt  eBuf        fer> futu       re = initiateJoinG           ro     up()       ;
                      client .p ol   l(future,         tim   er);
                  if (   !future.   isD     one   (     )) {
                  // we  ran o    u t of ti   m  e          
                   r eturn false;
            }

                          if (fu              ture.su    cceeded    ())  {
                                 Ge          nera      ti         o              n gener    ation Sn       apsho   t;
                    MemberState  stat  eSnapshot;

                                    // Generation     data maybe concurrently        cleared by Heartbea t th     read.
                          /      / Ca    n't use sy nchronized    for     {@co   de onJoinC   omplet   e}, beca    use i  t     can be lon      g          enough
                  // and shouldn 't    block he               artbeat        th     rea    d.
                       // S              ee {@link P          lai    ntextC     onsumerTest#t   estMaxPoll      Inter   v    alMsDelayInAssignm          ent}
                              synchr onized (Abstrac   tCo     o  rdinat   or   .this) {
                             generationSnapsho    t = t  his.generation;
                                          sta teSnaps   hot = t  h is  .stat         e;
                           }

                                  if     (!hasGe n     e      rat                ionR    eset(    generatio   nS naps       ho    t) &               & state  Sn  aps  hot ==   Me   mbe    rState.ST  A BLE) {
                                 // Duplicate the buffer in     case `on  JoinComplete`    does   not     complete and ne  eds      to be retried.
                                       Byte  Buffer memberA     ssi gnment = fut  ur      e.value().    duplica  te();

                              onJoinComplete(gene rationSna      pshot.generationId, genera     tionSnapsho   t.memberId , g    ene  ra   t     ionSnapsho    t .prot  ocolN        ame, memberA      ssignmen    t);

                                //  Generall y   speakin    g w    e shou  ld alway   s resetJo          inGro   upFuture once th         e        futu   re is don        e, but here    
                    //         w   e can      only reset the     join group  futu   re aft    er the comp   letion cal          lback     returns. Thi   s en  s                  ures  
                                         // that if the callback               is woke   n up, we   wi  ll retry  it                 o   n the next joinGroup       IfNe     eded.     
                                 // And  because    o  f th   at we should      explici       t  ly trigge      r reset   J       o   in   Gr   oupFut   ure in other conditi ons belo        w.
                                          r   eset          JoinGro           upFuture();    
                       needsJ  o   inPr epare =  tr ue;
                } else {
                                        f  inal Str   in g        rea  s  on = Str    i     ng.format( "rebalanc        e failed since the generation/sta  te was " +
                                                                                               "modif  ied by heartbeat       thread to %s/%s be fore               the reb     alance callback trigger ed"     ,
                              ge ne       r ation      Snapshot, stat      e     Snapshot);

                      resetStateAn  dRejo    in(rea   s     o     n, true);
                                res etJ oinG   roupFu   tur      e();
                                      }
              } else {
                            f   inal                 RuntimeExce   pt   io n exception = f        utur     e.excepti       on();

                                         resetJ     oinGroupFuture(         ) ;
                           sy    nchron         ized (Abst  r     ac  t    Coo     rdinato     r.this)     {
                                    final        Stri     ng     simple Name = e   x  ceptio   n.g   etC la  ss().getSimpleName                        ();
                             fina   l   String   sho     r       tR easo   n = String.f        orm           a        t("rebalance failed due    to   %s           ", simpleName);
                      f inal S     tri  ng fullReason = St  r    i   ng.f orm     at("rebalance fa iled due to '    %s' (%     s  )   ",
                                                          excepti on.ge   tMessa   ge(),
                                   simple    Na  me);
                            //     Don't need     to reques  t rejoin     again f  or MemberId   R        equiredExcept    ion since      we've done       that     in    JoinGr           oupResponseHandle  r 
                                    if        (!(excepti       on instanceof  Memb  erIdRequiredE    xce    ption)) {
                        requestR   ejoin(shortRea son, fullReaso     n    );
                             }
                             }

                               if     (exce pt  ion i n stanceof U   n     known  Mem  b    erIdExcepti   on ||
                             exc   eption ins    tanceof IllegalGe   nerati  on Excepti       on ||
                                        exc    epti on in  stanceof        Re  ba  la   nceInPr ogressEx           c eptio  n ||
                                                         exception inst              a        nceof M   e   mberId   Requir   e  dEx     cept ion)
                          conti     n   ue;
                 el  s  e i    f (!fu   t ur   e.isRet      ri   ab     le())
                           thro                w exce  ption;

                          /  /       We n        ee    d to    retu    rn upo n exp  ired timer, in    cas  e if            the   client.poll returns imm  e     diately and the time
                             // h   a   s elapse            d.           
                                              i  f (tim       er.isExpired()) {  
                                     return fa    lse; 
                    }

                   timer.s          leep(rebalance     Config.r etryBackoffMs);   
                               }
          }
              re   turn true;
           }

            private sy            n  chroni   zed void resetJ   oinG     ro      upFutu     re() {
        this.jo  inFuture     = null;
    }             

          private synchr   o nized    RequestFuture   <ByteBu   ffer> initiateJoinGro     u p()             {
        //        we stor       e    th    e join f        uture in case  we are woken u    p by the user after beginning the           
                                // rebala  n   ce in           the call t    o   poll             below. Th   is en           sures t                   h at we do not m  istakenly    attemp  t
                         // to rejoin before the  pending re     balanc    e has complet      e             d.
               i  f (joi     nF   utu     re == null   ) {
                                      sta te =   Member     State               .PREPARING_REBALANCE;
                                         /     / a rebal   an c    e c a   n be trig   g      ered consecutively   if    the previ  ou      s one failed,
                           // in this cas    e w    e wo uld not update                  the s      t a   rt  ti    me.
                i f      (l      astRebal   anceSta   rtMs ==    -1L   )
                     l    ast     Reb    alan          ceStartMs =      time.     milli   seco    nds();
                     joinFutu   re = sendJoinGro         up    Req    ue      st();
                   jo inFuture.add            Lis  t  ener(new Req   uestFutureListener        <ByteB    uffe            r>()    {
                                                 @ O   verrid           e
                                     public void onSuccess(ByteBuffer val          ue)     {     
                              //     do    n o       thing since all the handler       log  ic are in         SyncGroupResponseHandler alre    ady
                         }

                                       @Overri             de
                                    publ   ic void onFailure(Ru   ntimeEx         ception e) {
                                       // we hand   le      failures below aft      er the    requ est   finishes. if the   join completes
                           // after having been     woke             n up, the exce ption is      ignored a    nd we will          rejoin;
                        // this c    an   be        tr   iggered  when either join o            r     sy      nc re   quest      fa       iled
                                     synch    roniz   ed (   Abs      tractCoordinator.this) {
                                                                     sensors.fai   le         d      Rebala    nceS  en     sor.record(       );
                      }
                                 }
                })   ;
        }
        return joinFu       ture        ;
    }

                /**
     * Join    the group and return the assignment for      the n  e    xt      generat      io    n. This fun   ction han      dles both
     * JoinGroup and Sync     Grou       p, deleg ating to {@link            #onLead    erE lected        (  String        ,   String,      List, bo  olean)} if
       * ele       cted lea   der by the c oor dinator. 
      *
       * N              OTE:         T      his is visible onl y for testin  g
        *
       *       @retur   n A    re      quest    fu   ture    which wraps    the ass   ignm    ent returned     from  t    he group l   eade   r
     */
    Req    ues   tFuture<By   teBuffer      > s  e   n       dJo    inGroupRequest() {
          if (coordi   natorUnknown()     )
                  retur  n Requ      estFuture.coordina                       torNotAvailable() ;

                    /      / send a join gr   oup request to the coordi    nator
               log.   in      fo("(Re        -)jo ining g           roup");
        JoinGroupRequ             est.Bu ilder requ   estBuilder   =  new JoinGroupRequ      es  t  .B   u        ilder(
                new JoinG       roupReque  stD   ata()
                                  .  set  G   roupId(rebal     a   nceConfig.groupId)  
                                      .setSessionTimeoutMs(thi   s.rebalance  Confi     g.sess      ionTimeoutMs)
                                              .setMemberId        (thi                s.gen       erati   on.memberI d)
                                                        .setGrou pInstanc   eI    d(this.reb     alanceConfig.groupI        nstanceId.  or  Else    (null))
                              .setProt       ocolType(pr oto   c o         lType      ())
                              .setProto co  ls(metadata())
                                        .setRebalanceT   imeo     ut    Ms(this.rebalanceCon  fig.                reba  lan  ceTim  eoutMs)
                                          .setR    ea        son(           J   oi   nGroupRe  quest.ma     ybeT runcateReason(t      his.rejoinReaso       n  ))             
                     );
  
        log.  debug("Se   nding   JoinGr    oup ({}) to coo      rdi     na   tor      {}", reque   st   B     u       ilder, this.coordinator);     

        // N   ote t   hat      we override t he request timeout    using     the rebalance timeout since  that is t he
        // m    aximum     time   that       i   t may block on the co  ordin      at   or. We a       dd   an e  xtra 5    seco   nds      for small d    elays.
                  in   t joinG       roupTimeoutMs = Math  .max(
                    client.defaultReq                 uestTimeo  utMs(),
                    Mat   h     .max(
                     reb  alanceConfig.r    eba   lanceTimeoutMs    + JOIN_GROUP _TIMEOUT_   LAP      SE,
                                rebalance Config.   reb  ala nce         Ti    meoutM  s) // guard against over flow        since r  ebalance timeo ut can be M  A  X_VA  L     U     E
                );
                    return client.sen        d(coord   ina  to  r, r     equestBu   ilder, joinG   roupTime         outMs)
                          .comp       os   e(new       JoinGro     upResp        on  seHandler(gener    atio   n));
               }

    private class       J    oinGroupRes pon  seH      a    ndler ext      ends                     CoordinatorRes     ponseHandler<JoinG    rou  pR esponse  , ByteBuff         er> {
             private J  oinGroupRespons  eHand    ler(final Ge    neration generation   ) {   
                    super(generation);
        }

           @Overrid   e
                public void ha ndle(Joi  n    G   roupRespons   e joinRespon   se,    Reque    stFuture<       ByteB  uffer> futur      e) {
                          E rr   ors           error =    joi   nR    esponse.error()    ;
                       if (error  == Error  s.NONE) {          
                            i      f (  isProtoc        ol                    Typ  e    In consiste   nt(                jo   inResponse   .data().protocolTyp    e( ))) {
                      l     og.    er       ror("JoinGroup faile   d: I nco    ns      is    tent P             rotoco   l Ty    pe, rece                   ived           {} but    expected {}",
                                     joinR             e      sponse.data()   .prot           oc     olT  ype(), protoc          olType());
                                   future      .     rai   se(Errors. INCONSISTENT_GROUP_PROTOCOL);
                             }     el      se {
                        lo  g.  de bu       g   ("Receive       d    success        f   u     l JoinGroup                       re sponse: {}",   joinRes ponse    );
                                               sens  ors.joi   nS ensor.record(    response.req  ue   st     L          atencyMs());  

                                                                  synchronized (Abstract  C oo               rdinator.this  )  {
                                                     if (sta  te != Membe  rState.PREPARING_REBALAN   CE)      { 
                                                       // if  t h     e consumer wa     s wo ken up b   efo      re a                r      e  ba lan ce   completes,   we m    ay   have a    lready     lef   t 
                                   // t he     group. In t      his c  ase,      we do    not w  a   n                t     to        c       ontinue      with the    sync group.
                                      fut    u re.raise(new Unjoi   nedG   roupException()        )   ;    
                                } else        {
                                                 state =         M emb      erState. COMPLETING_RE    BALANCE;    

                                                    //    w     e only nee  d    to   enable heartbea    t t     hread     whenever      we transit            to
                                                 // C     O   MP   LETING_         REBALANCE st ate   since we always tra nsit      fro  m    this   stat    e to STAB    LE
                                                                   if    (h eartbe atTh  read != null)
                                                  heartbeat      Thr                    ead.en     able();

                                            Abst r   act        C oord    ina      tor.this.gen    eration = new      Gene                     r       atio      n(
                                                   jo                  inR     esponse.da     ta().generationId(),
                                                                      joinRe  spo  nse.dat    a().memb     er Id(  ),    joinResponse.       d            at   a().protoc   olNam            e());

                                                               log.info("Succ        essfu    ll    y j     oin   ed group wit  h     generation {           }",       Ab   stractC oordina  tor.t   his.ge  neration);
                                                  client   Te     leme   tryReporter.ifPrese  nt(re    porter -> reporter.u                  pd ateMe      tricsLabels(
                                                                       Coll    ections.singlet    onMap(Clie      ntTelemetryProvider. G    R    OUP_MEMBER_I   D  , join        Re  sponse. data().m              ember   Id())   )   );

                                                     if (jo    inRespo     nse.   i      s       Leade   r()) {
                                                                o       nLeaderElected(join       Response).chai    n    (fut       u   re);
                               } e lse {   
                                                 onJoinFollower(). chain    (future          ) ;
                                   }
                                   }
                                         }    
                                  }
                         }     else if (e      rror == Errors .COO  RDI  NATOR_LOAD_IN_PROGRESS) {
                     lo  g.info("J    oinGroup fai  led    : Coordinator {} is loading      the group.", coord    in    ator());
                               /         / backof       f and re   try
                  fu   tur    e.raise                        (erro   r)  ;        
                                      } else if        (error     == Errors  .UNKNOWN_M        EMB ER_ID) {
                    log   .info("J     oinGroup fail          ed: {} Need       to re   -jo     in the gro    up.         Sent   g        eneration wa   s {}"      ,
                                                             erro      r.message(), sent    Gener ati   on) ;
                                  // o    nly need    to re  se     t the member   id  i   f     gene r         at     ion ha    s not been cha      nged,
                   // then retry imme           diat  ely
                         i        f (generatio         nUn    changed(     ))    
                                  rese tStat eOnRe  sponse  Error(ApiKeys.J      OIN_G     R  OUP,      error, tru    e);

                        fu   ture.rais   e(err     or);
                       } else if (error == Errors.C      OORDINATOR_N  OT_AVAILABLE
                              || e   rror  == Errors.NO   T      _C  OORDINATOR) {
                      // re-disc  over the coordinator and ret    r     y with backof     f       
                          mar  k      Coord   inat   o r    Unknown(error);
                                   log     .in           fo("    JoinGroup f  ailed:  {} Mar  king co  ordi      nator unknown. Sent generation was {}",
                                                                                error.message(), sentG    e   ner atio   n);
                        future.raise       (err           or );
             } else i  f       (error   =  = Err      ors.    FENCED_INSTAN   CE_ID ) {
                            /         /   for j    oi      n-group     request,        ev        en            if    the     g  ener      at     ion has ch   an ged we w ou   ld not ex   pe    ct t   he i  nstanc  e id
                                                     / /     gets fen       ce    d, and hence    we  always treat     th   is as a fatal      error
                log.    er ror("Jo inG  rou    p   fai  led   : The gro  up inst   a               nce id { } h          as been fenced by a     no   t   her instance. " +
                                                  "Sent gen     eration was {}",      rebal        anceConfig.grou   pInsta nceId, sent     Gen   eration);
                            fut         ur  e.raise(er    ror)   ;   
                      } els       e     if (     erro     r == E    rrors.INCONSISTENT_GROUP_PROTO  COL
                                   ||   er    ror == Er r    ors.INVALID_SESSI   ON_TIMEOUT
                       || error == Err ors.IN  VALID_G      ROUP_ID
                      || error == Er      ro   rs.G   RO      U     P_        AUTHO                RIZATION    _  FAILED
                     | | e          rror ==               Errors. GR O     UP_MA   X_    SIZ   E_REACHED)  {
                             // log the    er     ror and re-throw the  ex c   eption
                                    log.error("     Joi  nGroup failed due to      fatal    err      or:      {}  "   ,  error.messag     e   ());
                                               if (  error == Erro     rs.     GRO    U      P_MAX_SIZE_REACHE    D) {
                               fut   u re .raise      (new GroupMaxS ize        Reached   Except    ion(                 "C  o          nsumer gr o      u   p  "       +    r   ebalanceConfi        g.gr       oupI         d   +
                                                      " already has the confi    gure       d maxim u           m numbe     r     o  f me       mb   ers."));
                } els      e if (error == Er rors.GROU P_AUT        HOR    IZATIO N_FAILED) {     
                                 futur    e    .raise(GroupAuthoriz          ation      Exceptio  n     .   f    orGr  oupId(rebalanceConfig.groupId)   )   ;
                             } else {
                                                       future.raise(err or);  
                  }
                                 }          else if (error ==   Errors.U    NSUPPORT   E               D_VE    RSI   ON         ) {
                         log.error(      "JoinGroup faile   d due to unsu   pport ed version er   ror. Please unset fi eld   group     .   instance.id "     +
                                  "a     n d ret     ry         t  o se  e if th    e pr   oblem resol      ves"     )    ;
                           fu     tu  re.rai se(        err   or);
               } else   if (er            ro r == Err  ors. MEMBER_ID_REQUIRED) {
                         // B    roker re    qui  res a conc     rete           memb         er id to be       a     llowed to join     t          he g    roup. Upd  ate            membe  r id
                                        /   / and se      nd a   nother jo    in                 g  roup re  q uest        in nex  t cycle.
                        String m   emberId = joinRe   spon   se.data ().m  em        berId()  ;
                  log.debug("     JoinGro up failed due  t   o non-fatal error:   {}. W      ill set t      he mem     ber i   d as {} and then rejoin      . "  +
                                        "Sent generation   was    {}", e   rror, membe r     I          d,   s e         ntGeneration);
                                                synchroniz   e       d (AbstractCoordinator.this)  {
                               Abstra   ctCoordinator.th  is.ge   nera      tion     = new Generation(   OffsetCommitR equ    est.DEFAULT_GENE   RATIO   N_I    D,      memb erId,  n   u ll);
                          }   
                        re  que   s tR          ej  oin("need to r  e-j           oin wit       h the g          iven m   emb       er-id: " +    mem    berId       )       ;    

                 future.r  aise(error);
              } else if (error = = Er rors.R  EBAL       ANCE_IN_PROGR ESS) {
                           log.    info("JoinGroup failed due   t o non-fatal er   ror:    REBALANCE     _IN_PROGRES   S, " +
                              "wh    ich could indicate a replication t          imeout      on th         e broker   .     Will retry.");
                                 f  uture.raise  (er    ror);
                     }        else          {
                        // une              x     pected erro   r,   thro                w t        he exception
                           log.err      or("JoinGroup       failed  due to unexp         ected e    r r      or: {          }", error.message());
                future.       rai  s  e(n         e   w K    afkaException     ("U    nex pected error in j                oin      g   roup                  r      esponse:  " +       err  or.message     () ));
                    }
               }
    }

    private    R   equestFut     ure<ByteBuffer  > onJo  inFollower() {  
        // send    foll ower's syn  c grou p wi       th an emp          ty assignment
          SyncG     r oupR equest.Builder    requestBu         ild  er =            
                          new SyncGroupReq uest.B    uilder(  
                                                ne w    Sy ncGro       upRe q          ues  tData()
                                                      .setGroupId(r        ebalan        c  eConfig.group     Id)
                                                  .    setMemberId(generation  .memberId)
                                              .setProtocolType(protoc    o                l  Typ  e   ())
                                          .setProtocolName(g          ene  rati       on.p    r   otocolName)
                                                 .set   GroupIns            tanceId( this.rebalanceConfig. g   r  oup          Inst ance Id   .    orElse(null))
                                               .setGenera        t ion Id(generation .generationId)
                                                  .setAss     ign   ments(Collections.emptyList())
                      );
        log.debu       g("Sending fo l  lo     wer Syn c     Grou            p to coordin   ator {}: {}", this  .coor d     inator, requestBuil     de        r);
           r   et urn se   ndSyncGroupReq   ues  t(r    eque          stB             uilder);
    }

    pri    vate  Reque    st          Fut   u  re<By  te              B     u ffer> onLeaderEl  ected(J    oinGroupRes          ponse join      Respo   ns     e)   {
        try   {
                // p    erf     or  m      the leade     r synchroni        zation and         send back th e assig     nment for  the group
                      Map<String,    ByteBuffe   r> g   roupAssignmen      t = onLeaderElected(         
                        jo   i  nRespo  nse.data(  ).l         eader(),
                joinResponse. data   ().      prot ocol   Name(),
                           joinRespon  se.          data(). members         (),
                         join    R   espo   nse.  dat a().skipAssi      gnment(   )
                );
   
                                 Li  st<SyncGro      u  pRequestData.Sync  Gr  oupRequestAssignme      nt> groupAssignmentList = new  A    rrayList<>  ();
              f     or (Map.En try<St  ring,     By teBuffer> assign     me     nt  : g       roup  Assi   gnme     nt.entr              yS  e  t   ()) {
                              groupAssignmentList     .add(new SyncGroupR    equestD      ata.SyncGroupRequest    Assig   nm  ent()   
                              .setMemberId(   assignme     nt.getKey())
                                        .set  Assig  nmen    t(Utils.to Array(assignment.   ge  t    Valu     e()))
                         ) ;
              }

                                   Syn  cGroupReq    uest      .Builder reque    st   Buil   d    er =
                                                      new SyncGroupRequest.Build er  (
                                                 new SyncGroupRequestData()
                                                                               .setGrou  p  I   d(rebalanceConfig            .groupId)
                                                            .s      etMemb    erId(generatio n.membe    rI d)       
                                          .set     P rotocolTy       p   e(pro   toco     lType())
                                                       .setProtoc   olNam    e(generati on.pro  tocolNa      me) 
                                                            .setGroupInstanceId(this.r      eb    alanceConf           ig.groupI    nstan    ceId.orElse(null))
                                                   .set  Generat  ion     Id   (    ge     neration.   g   en  erationId)
                                           .setAssignments(gr              oupAssign     ment  L     ist           )
                                     );
             log.debug(   "Sen     ding          leade   r SyncGr  oup to coordinator {}  : {   }"         , this.coor    d  inator                , request     Builder);
                    return        sendSyncG  roupRequest(requestBuilde      r);
              }         cat      ch (RuntimeE  x            cept  io   n e) {
                   return  Reque     s tFuture.fail  ure(e);
             }
              }

        private RequestFut    ur         e<By  t       eBu      f   fer>      sendSyn  cGroupR   equest(Syn   cGr         oupRequest   .Buil                de     r reque s      tBuilder) {  
               if (coordinat   o      rUnknown())
             r     eturn RequestF  uture.coordinatorNotAvai la  b  le();
                         return client .send(coor   dinator, r          equest      B uilder)
                         .compose(ne  w SyncGrou     pRespo         nseHandl e r(g en  eration ))  ;
    }
  
    pri vat    e boolean                  hasGen                               erati            onR  eset(Gen   eration ge       n) {
        //    t        he member  ID might not    be re set for ILLEGA     L   _G      E NERATI       ON er  ror, so on   ly ch   eck genera     t           i     onID and protocol         n ame       he   re    
              r        e      turn    gen.g   enerationId == Ge neratio              n.NO _   GENERATION.g enerat     ion Id &&      gen.prot oco   lNam   e == nul  l;
           }

    p    rivate  clas    s S    ync     Gr     oupResponseHandler extend   s            Coord        inatorResponseHan  dler<SyncGro      upResponse, Byt   eB              uf  fer> {
        private  SyncGr  oupR  espon   s  eH   andler(final   Genera    tion generation) {
                     super(gene ratio n);
                        }

                             @Overr  ide
        pub li  c vo   id handle(    Sync      Gr  oupResponse sy ncR   esponse,
                                         Requ    estFut ure             <By   teBuffer> fu  ture) {
                    Err         ors   error = syncResp    onse.erro r()  ;  
                                if (error ==    Er     rors  .NONE) {
                                if       (isP      rotocolTypeIn       consis     tent(syncRes           pons   e.data().protoc            olT          ype()))          {
                            log.e         rr     or("SyncGroup failed due to inc   on       sistent Prot            oco    l Type, rece       ived {} bu   t         exp    ected {}       "  ,
                                                            syncRespons        e.   data()  .pro  tocolTy    pe(), pro tocolType());
                                      future.ra         ise(E     rror       s        .INCO    NSISTENT_GROUP_PROTOCOL)     ;
                         }     else {
                         log.debu   g("R    ece   i ved successful      S      yncGroup r       esponse: {}"  , sync  Response);
                           s ensors.syncSensor.record(respon           se.  requ     estL  ate     ncyMs(      ));

                                         synch        roniz    ed  (AbstractCoordinator.     this) {  
                                  if  (  !hasGenerationReset   (gene      ration) &        & state =   =   MemberS   tate.C    OMPLETIN G_R EBALANCE   ) {
                                      // check pr   otocol name on    ly if        th   e g          eneration is  not          reset
                                           f  inal  S tring protocolNam  e               = syncResponse.data ().proto    colName  ()       ;
                                           f     i    n  al boole an pr  oto     colNameInc    onsiste        nt =      protocolName    !=           n ull &&
                                             !   pro t   ocolN  ame.equa  l  s(generation.p        r        otocol      Name);

                                    if       (protocolN   ameI     n       consi         stent) {
                                             log.erro     r   ("S    y     ncGroup faile       d due to in   con   s  istent P  rotocol Na  me, rece  ived {} but ex        pect    ed   {}",
                                         protocolName     , gen        eration.protocolName)   ;

                                                fu  ture.raise(Errors.INCONSIS   TE    N T_GROUP          _  P     ROTOCOL);
                                              } el  se {  
                                                      log.in              fo("Succ     essfully s       y  nced group in generation    {}",          ge     ne  r   ation      );
                                                           state = MemberSta     te  .STABLE;
                                            rejoinReason  = "     ";
                                                                 rej            oinNe    ede   d =    false;
                                    / / reco   rd rebalance late     ncy
                                    lastReba     l  anceEndMs = time.mil   lisecond    s();
                                              s enso      rs.success     fu  l   R   e     balanceSensor.reco    rd(l   a   stReba     l  anceEndMs   -     las  tRebalanc              eStar     tMs);       
                                                las       tRebalanceStartMs  = -1      L;

                                                             f  uture   .complete(Byte  Buf      fer.wrap   (syncResponse.data   ()  .assignment ()));
                                         }
                                    }      else {
                                                         l       o g.in  fo("     Generation d     ata was cleared     by heartbeat       thread to {} and state  is n   ow {} before  " +
                                     "receiving SyncG roup respo    nse, ma   rki    ng t hi    s rebala       nce   as fail  ed and ret  r     y",
                                     generation, st     ate);
                                           //     use ILLEGAL_G   ENERATION error            c     od   e to    let it r       etry i   mmed  iately  
                                     futur   e.r            aise(Er    rors.ILLEGA   L   _GENERATION);
                                                      }
                              }
                                                       }
                   } e lse {
                        if (   error == Errors     .GRO  UP _A  UT  H     ORIZATION_FAI    L      ED) {
                               future.rai     se(Gr oupAuthorization           Exception.f orGr     oup     Id(rebalanceC   onf ig.groupI     d));
                                }         e     lse if (         error == Errors.REBALA   NCE_I     N_PROG   RESS) {
                             log. info    ("SyncG     roup fai  led: The group be    gan another      reb al   ance. Need to r    e     -join the g             roup. " +
                                        "   Se     nt gene    rat   ion was {}      ",      s entG    e  ne ra     tion)  ;
                                      future.r   aise(error);
                       }     else i     f (             error    == Err    ors.FENCED_     INS   TA     NCE_ID  ) {
                           // for sy           nc-group request, even if     the genera t   ion     ha  s change  d      we wou     ld no t ex    pe ct the instance id
                                     // gets f  enced, an    d he  nce w e always        treat this a   s a         f       atal er        ror
                                          log.   error("SyncG     roup failed:   The    group      inst  ance i  d {      }   has been fence  d by another ins  tance. " +
                                          "Sent generation was {}", rebala  n  c     eConf  ig.groupIns  ta nceId, sen tGeneration);    
                           future.r      aise(err   or    );
                            }       el    se if (err         or == Er        ro        rs.UNKNO  WN _M  EMBE    R _ID
                                            || er   ror      ==    Errors.ILLEGA   L_GENERATION) {
                          log.info       ("SyncGrou   p fa         iled: {} Need t      o         re- joi   n the g   roup. Se   nt generation was {}     ",
                                     error.me   ssage(), sentGenerati o       n);
                         if (gene  rationUnchange d()  ) {
                                 // don't re   set generation member ID when ILLEGAL_G    ENERATION, since the member ID might still        be va   lid
                                        resetStateOnRe  sponseError(A  p   iKeys.SYNC_   GROUP, error,   erro    r !=    Erro    rs.ILLEGAL  _GEN        ERATION);
                                }

                                  future.   r      aise(err       or);
                                         } els e if (error == Errors        .COORDINA  TOR_NO T_AVAIL           AB  LE
                              || error == Errors.NOT_ COORD IN         A   TOR) {
                                log.info(   "           SyncGroup failed: {} Ma rking            c    oordi     nator u   nknown. S   ent generation         was {}",
                                          error    .message(), s    ent  Gener  ation);
                               markCoor         dinatorUnknown(            error);
                        future  .raise(error);
                                  } else     {  
                         fu  t  ure.ra   ise(new Kafka Exception("Un  exp     ec      ted error from S    yncGro  up: " + error.m essage()));
                          }
                    }
              }
    }    

    /**
     * Discov    er the c    urrent c    oordinator       for the group. Se n  ds a   FindCo ordin   ator req uest to
     * t          he     given brok    er n  ode. The returned      f uture should be p  olled to get      the resul   t of th                    e  requ est.
         *   @return A r     eq  uest fu  ture whic    h indicates the compl       eti    on o  f the m    e      ta   d            ata  request
           */
    priv     at     e Req       ue        stFuture  <Void> sen  dFindCoor    dinatorRe      quest(Node  nod      e   ) {
        log.de    bug("Sendi    ng FindC  oordinator request t    o brok      e  r    {}", no    de);
                    Fin  dCoo      rdinatorR     eq  uestDa  ta     data          = n   ew FindCoordin    atorRe  ques   tData()
                         .se         tKey     Type(Coordinator     Typ   e.GROUP.i  d(    ))
                 .   setKe     y(this.rebalanceConfig  .groupId);   
             Find  Coordinato  r         R           equ est.Builder requestBuilder = new F  in     d  C          oordi na      torReque   st.Buil        der(     da  t   a  );
               retu   rn      client.s en     d(node, r            eques t Bui ld  e      r)
                            .compose(new Find   Coord     inatorResponseHandl      er());
    }

       private                cl    ass FindC  oor    dina  torResponseHandle   r e  xte    nds   Req   uestFut    ureA    dapte  r<Cl  ientR             esponse, Void> {

           @Overrid         e
        public v    oid   o    nSuccess(ClientResp        ons    e resp  , RequestFuture<Vo   id> future) {
              log.debug("R   eceived Fin   dCoordina     tor response {}     ", res     p);
 
                            List<Coordinator>    coo  r  dina tor   s = (    (FindCoordinato        rResponse) resp.resp  onseBody (     )).    coo   r  dina     tors();
               if   (coordinators            .s ize(               ) !   = 1) {
                                      log .erro      r("Group         coordinator lookup failed: I   nvalid resp  ons  e con    taini n     g more than a si ng  le       coo     rd     i n ator");
                                      future.rais      e(new          IllegalStateExceptio  n("Group coordinator lookup failed:     Invalid re   sponse contain ing more         t     han a single coor  din ator    "));
              }
                Coord    inator    coordinato                   rData = coordi    nators.g   et(    0);
                             Errors err   o  r = Errors     .   forC   ode(co      o    rdin              atorData.error Code());
                 if (e                             rror =   = Errors.NONE)  {   
                    synchronized       (Abs                            tract   Coordinator.t      his)       {
                         // use MA   X_V AL   U  E - node.id as the coo     rdina   tor id    to al  low s   eparate    connections   
                                    // for the coo    rdinator          in the underlyin  g network clie nt    lay  er
                                                                       int coordinatorConnectionId = Integer.MAX_VA  LU      E - c   oord   inatorData.nodeId();

                               AbstractCoordi    nator.this.coor     dinato  r = new N     o  de(
                                                              c    o     ord  inat orConnectionI      d       , 
                                   coordi    natorData.host   (),
                                           coordinatorData.port())   ;
                                   log.in        fo("D      isco          vered grou      p c     oordinator {    }" ,  coo              rdinator);
                        cl  ient.tryConnect(    coordinator);
                             heartbeat    .resetS   essi   onTimeout();
                        }
                   fu t   ure.c  o   mpl  ete(null   );
            } el  se    i f   ( er   ror    == Errors.GROUP_   AUTHOR              IZAT      ION_FA      ILED) {
                                 fu    ture.raise(  Gr     oupAuthorizationException.forG        r        oup   Id(               rebalanc     eConfi  g.g   ro upId));
                       } else      {
                               log.debug("Group coordin   ator lookup failed: {}"  , coor      dinatorDa t   a.erro   rMessage())  ;
                      future.raise(error     );
                  }
        }

                             @O     verride
             pub   lic v oid onFailu   re(RuntimeException e, Requ      estF    uture<Voi  d > futu     re) {
                  lo    g    .debug(  "FindCoordinato    r       request fa iled d  ue      t  o   {}", e.to           String());

                             if (!(e     instanceof Retri  ableExce     ptio n)) {
                             // R  eme   m  ber the exception    if fatal so     w   e  can     ensure it ge  ts    t         hrow     n by    the ma   in        thread
                             f           atal  Find          Coor   din   atorEx  ce   pti    o   n = e    ;   
            }

                        super.onFailure(e,    future)   ;
             } 
    }
      
              / **
           *      Check if we know who the coor dinator   is and we have an a  ctiv  e connectio     n
     * @return true if the coordinator     is unkno   w               n       
       *           /
    pu   blic b      oo      l           ean coordinatorU        nkno        wn() {
        return c heckAnd  GetCoo    rdin   ator() == n     ul    l;
    }

    /**
                              * Get t     he          coordinator if              its connectio              n     i    s still active. Othe  r  wise     mark i t u    nknown and     
     * r e     t    urn        null.   
        *
       * @    return the curr       e   nt c    oord inator or nu   ll if i   t      i    s unknown
     */
         protect     ed synchro nized   N       ode         che  ckAndGetCoor      d   inator() {
        if (coordinato r != nul     l  && client.   isUnavailable(c     oordinato     r)) {
                   mark      Coor    dinatorUnkn   own(          t       r  ue, "coordinator      unavailable");
                  return null;
                 }
             return this.  co   o        rdinator;       
            }

             priva  te syn chron     ized         Node c    oordina    t        or() {
                 ret  urn     this.coordi    nator;
    }


    protected s  y    nc      h  ronized void markCoord   inatorUnknown(  Err       ors error) {
        markCoordinatorUnknown(false   , "error resp     onse    " +     e   rro  r.nam      e());
    }

    p    rotected syn    chronized             void markC      oordinat     orUnknown(String    cause      ) {
                       mark    Co    ord i        natorUnknown(fa     ls e, cause);
     }

          p       ro       tect    ed sy nchronized void       markCoor   dinatorUnknown(b         oo  lea n is  Dis  co  nnecte   d, String cause) {
        if   (   this.coordi   nator !=      null)     {
               lo     g.i         n     fo("   G       roup coord  in     ator {}   is un  a    vailable or invalid d    u         e to cause: {}.        "
                                   +             "isDisconn     ected: {}. Redisco     v      ery will b e attempte  d.",     this.co           ord    inator,
                           cau  s          e, isDisconnected)      ;
                     N         ode    oldCoor     dinato           r =  this.c  oordinator;

                        //     Mark t   he coordinator dead       b             efor     e disco          nnecting requests sin ce the                   c  all backs for  any pend   in       g
                      // requests may       attempt to do l  ik     ewise. This al              s  o prevents ne    w   request  s           fr  om       b        eing          sent   t   o    the
                           // coordinator        while the disconnect   is in p                    rogress.  
                           th   is.coordinator     =     n      ull;

                 // Dis  c  onne    ct from the     c   oor    dinator    to ensure    th  at there are  no     in-fli     gh   t requ       ests    remaining.  
                          // Pendin g ca   llb    acks will be invoked with a DisconnectE   xcepti      on on the n  e    xt         call    to    poll.
                     i   f (!isDisco        nn  ecte  d) {
                  log.inf o("R      e   questing  disconnect from   last kn     own c     oordinator {}", o   ldCoordinator);
                           cli  ent.disc onne           ctAsyn      c  (   oldCoordinat                or);
                  }    

                        lastT         imeOfC    o   nnect       io n    Ms = t      im      e.mil    l                 isecon  ds();
         } else {
                long d        uration            OfOngoin   g      Discon   nect = time.   mill  isec                    onds()         - la    s tTimeOfC  onnectionMs;
            if (d ur  ati    onOfOngoingDisconnect        > reba    lanceCo    nfig.re  balanceTim       eoutMs)
                       lo  g.warn("   Con     su  mer    h as bee  n discon                 nec        ted     from the    group   coordinator f  or {      }ms",  duratio     nO   fOn     go    ingDisconne      ct);
         }
    }

    /** 
     * Get the cu   rrent generation state, regar   dl               ess of        whether           it is cu        rr       en   tly stable   .
      * No   te tha      t the ge     neration     info rmation         can be up   dated while w      e a       re stil  l in th e middle
     * of a        rebalance,     after the joi  n-   gro  up response is recei v      ed.
     *
        *            @     return     the    current ge       neration   
     */
    protec          t    ed sync hron               ized Generation generati on    () {    
                        re  turn gener    a   tion;
    }
      
    /*      *
     * Get t he c    urr en  t generation stat   e if the grou       p is    stabl  e  ,    ot                  h  erw                 ise return nul  l
              *
          * @return t    he current    g   eneration   or null
     */
    prot  e       ct    e   d sync      hroni zed Ge    neration  generationIf     Sta   bl               e() {
                               if   (th   is.state != M        emberState.STABLE)
                 retur   n null;
              return generation;
    }

                 prote     cted synchro    nized bo      ol ean reba       la   nc   e     InProgress() {
               return this.state          ==    Membe      rState.PREPARING_REB       AL     ANCE || this.state ==             Me      mberSt a    te.COMPLETING_REBALANCE;
    }

    p      rote       cted sy   nchr  o    ni  ze    d S tring member     Id    ()     {
                return g  enerat  i on.memberI    d;
    }

    private syn        chr    onized vo        i  d res                       et           Sta               teAndGe   ne      ration(fin       al S     t     ring reason,          fin     al boolean s       houldResetM       emberId) {
        log.   info("Resett    ing generation {}d  ue to:   {}",    sho        uldReset                     MemberId ?   "an d m         e       m   b er id " : "", reason);
        
        s tate  =         Membe  rState         .UNJOI    NE  D;
                    if (shouldRes    etM  ember           Id) {
                        g      enera  ti     o   n = Generation.NO_    GE      N     ERA  TI   ON;
              } els           e {
                    //       k  ee    p m     ember id since i t mi       g  ht be         still vali d ,    to avo id to wai      t for the      old       mem        ber id leaving g roup
                  /    / until rebalance timeout   in ne         x       t rebalan     c     e
                   gene  ration            =  new Gen    eration( Generatio    n.N O  _GENERATI  ON.generationId, generation      .memberId, null);    
           }
    }

    private     synchroni     zed void resetStateAndRej  o   in(fi        nal     St  rin  g r    eason, fi     nal         boolean   shouldRes  etMemberId) {
                  res etStateAndG   eneratio  n(reason, sh          ould Re  setM         e mb       e     rId )  ;
        requestR   ejoin(re        ason);  
                     needsJ          o     inPrepare = tr  u          e;
       }

     synchr  onized void re   s    etSt   ateO     nResponseError(ApiKey    s ap   i,  Err     o    rs error, boolean      shouldRe    s       etMemberId) {
                  final S t   ri      ng reason      = String.     format(       "encountered %s from %s resp  onse", erro   r,         ap     i   )  ;
        r   esetSta     teAndRejoin  (rea    son, shouldR  e  setMe         mberI    d)  ;
          } 

    sy nchronized v      o      id   reset   Genera     tionOnLeaveGroup() {
                  resetStateAndRejoin("consumer pro-actively leav    ing t  he group    ", tru  e);
         }

    public       synchronized     void     r     eque   stRejoinI   fNe cessa     ry(  final            String              shortR  ea   s    on,
                                                                                            f inal      String      fu   l    lReaso  n) {
             if (!t    his.rejoinNeede  d) {
                    requestRejoin(shortReason, ful          lReason);
        } 
          }  

    publ    ic  synchronize d void re      qu        estR   ejo  in(final    St   ring s              hort  R    eason) {
             requestRejoin(s    hort    Reason, shortReason           );
    }   
       
    /**
     *  R       e       quest to r          ej        o in the group.
     *
           *          @pa ram shortRe      ason This is the   re      a      s      on  passed up to the   g         ro     up coordinat      or. It   must be
     *                                                    reaso  n      ab  ly small.   
     * @param fullReas   o  n This is the reason logged    loc    ally.
       */     
        public synchronized voi   d requestRejo   in(fin     al String shortR   ea son   ,
                                                      final        St     ri    ng         fullRe             ason) {
            log.info("Request joinin g gro    up du  e to:   {}      ", fu     llRea   son);
        this.r        ejoi            nRe    a son = sh   ort  Rea  s   on    ;
             this.r       ejoi       nNeeded = true;
                 }

       private bo           olean isProto   co     lTypeInconsistent(       String p  ro       tocolType    ) {
        return       protoco   lType != null     && !protoc  o   lType.equals(protocolTy pe());
    } 

                  / **  
     * Close the coord  inato      r,           wai     ti ng     if                need    ed to send L eaveGroup.
        */
         @Overr          ide
             publi   c f  ina   l void        clos        e() {
             close(time.t    imer(0     ));
          }

      /**
          *  @throws  K      afkaExc  eptio  n        if  the reb  al     ance callback             throws exception
     */
      prote  cted vo       id close(Timer timer)     {  
        try               {
                          clo  seHeartbeatTh  read();
           } finally {
                            // Sync  h   r   o            nize          afte    r closing the he     artbeat th   read since                 h   e   artbeat th    read
                   // needs t his lock to com     plete           a        nd termi  nate after close fl ag  is set .
                        synchronize  d (this) {
                i      f (rebalanceConfig.leave GroupOnClos   e) {
                      onLeavePrepar            e();
                           maybeLe          ave         Group("   th         e         co  nsume       r is being c losed");
                    }

                            /    / At this    p      oint, there may    be pe  nding c     ommits (async commits   or sync commits th   a   t were
                   // interrup     te  d using    wak      eu         p ) and      the  leave grou  p        re       quest which     hav    e  been qu    eued   , but not
                    /      / y               e  t sent  to the broker. Wait up to close ti      meout for these pe nding requests to      be process           e    d.
                       // If          coord     inator is n  ot known, request s are ab          orted.
                Node coordinator =      che          c    kAndGetC   oordinator                     ();  
                    if (co          ordinator   != nu ll && !client.aw aitPendingRequests    (coordinator, tim    er))
                           log.   wa    r n("Close timed   out with    {} pend  ing requests   to co     ordinator, terminating  client conne    ct      ions",
                                                      client.pendingReque   s   t    Count  (coordinator));
               }
               }
    }

      protected void h    and l  ePollTimeoutExpi   r     y()   {
        log.war                         n(  "con   sum       er poll time  out h         as expire    d. This means the time between subse      quent calls to   poll()     " +
            "was longer than th   e confi    g   ured max   .p           ol       l.interval.ms, wh  ich typically implies t   hat " +
                        "th   e pol     l loop is spendi     ng to   o     much time pr  ocessing mes   sag es.     You can addre    ss   this " +
                " either b  y in  crea       sing max.pol   l.interva   l.ms  or    by reducing the      m     a  ximum size of batches " +
                "retu         rned in po   ll() with max.poll.r   ecor          ds."   );

             maybeLeaveGroup("consu   m    er p     ol  l timeout h         a   s e    xp ired.")  ;
               }     

         /**   
          *    Sends           Leave  Group   Request and      logs  the {   @cod   e leaveRea    son}, unle  ss this   me  m  ber is using static membership or is alrea     dy   
        * not part of the              group (i      e does not hav         e a valid member id,    is in the UNJO   INE  D  sta   te, or  the coordina   t or is      unknown).      
      *
     * @p    aram leaveR     eason th     e reason    to lea   ve the group fo r loggin  g   
     *      @throws KafkaException i    f the rebalance callbac  k throws exception
     */
    public syn c hronized RequestFutur   e     <V   oid> m   aybeLeaveGroup(String leaveRea  son) {
                RequestFuture  <V    oi  d> f    uture = null;

        // Starting from 2.3, onl    y dyna   mi     c members w  ill send Leav        eG       roupR equest to the broker,
           // co    nsumer with v alid g      roup.instan  ce.id is viewed as sta   t ic memb    er that      n     e ve r   se    nd        s LeaveGroup,
          // and the membership ex    piration is   o   nly     c    ontrol   led    b    y ses       sion t       imeout.
               if   ( isDynamicM  ember(   ) && !coor    din            a    torUnknown() &  &
              st     ate != Mem  be     r  State.UNJOINED        && gener    ation                 .hasMe       mberId()) {      
                // this is a min   imal effort          attempt to l    eave the g   ro   up. we do not
            // attempt any res   ending     if the req  uest fails or t     imes out.
                       log.info("Memb   er {} sending Leav       e      Group r    eq u    e     st to    coordina   tor {} due to    {}"     ,
                                  generation.       memberId,    coord    inator, le       ave     Reason);
                 LeaveGroupRequest.Bu ilder re   quest = new L             eaveGr oup  R        eques  t.Bu  ilder(
                re     balanceConfig   .g      roupId,
                        Collections       .sin       gletonList(n  ew Mem   berIdentity().setMemberId(gen   eration.me  mb  erId)   .setReason(JoinGroupReques   t.maybeTruncateR eason(l       eav     eRe      ason)))
             );

                 future   = client.sen          d(c  o         ordinat   or     ,    request).  compose(new LeaveGroup    ResponseHan  dl     er(genera                   tion));
            client.pollNoWakeup();
            }

        r    esetGenerati         onOnLeav     eGroup();
  
        ret urn   future ;
    }

    protec    te  d boo       l   ea n isDynamicMember(       ) {
                  retu  rn         !rebal     anceCon  fig.grou  pInstanceId.isPresent();
    }

        private class  LeaveGroupResponseH  andl      er ext en   ds Coo      rdinatorRes  p   onseH andler<Lea veGroupResponse,  V      oi     d     >    {
           pr    ivat e LeaveG        roupResponseHan dl  e r(final    Generation gener    ation) {
                                 super(gene     ration)       ;
          }

            @    Override
        pub  lic voi            d h     andle(Leave Gro  u         pResponse leaveResponse, RequestFuture<Void> future          ) {
                     final Lis t<Me       mbe  rRes  ponse>    members = leaveRespo  nse.m     em           berR  espon   ses();
             i   f (members.size() > 1) { 
                           fu               ture.r     aise(new Illeg alStateExceptio      n("The expected leave group respo    n      se " +
                                                                            "sh  ould only contain    no m          ore than one      mem   be r          inf  o, h   owever get " +      members));    
                 }

            final Err  ors       er    ror = leaveR       esponse.e rror();
                    if (e   rror == Er    ro     rs.NONE) {
                   l     og .d   ebug("LeaveGroup response with {} return ed successfu    lly: {}",  sen    tG  en     er  at    ion, response);
                       future  .comp         lete(null);
            }           el se        {
                        log.error("LeaveGroup re            ques     t with        {} fai  l  ed with erro     r: {}", sentGeneration            , error.message());
                     fut      u re.raise(error);
            }
        }
    }
 
     /      / vis      ibl       e for testin g    
    synchronize      d Requ    estFut ure<Void> se        ndHear tbea  tRequest   () {
                         log.debu     g  (       "Send         i  ng H eartbeat request wit h generation {} and member id   {  } to coor  dinator {}",
                    gen    erati    on.gene     ra  tionId, generation.memberId     , coordinator);
                  HeartbeatRequ    est  .Builder   requestBuilder =
                                  new         H   eartbeatRequest.Builder(new HeartbeatRequestData()
                                     .setGroup  Id(     reba  la   nceConfig.grou   pId)
                                .set   MemberId(this.g   ene  ration.me   mberId)
                                         .setG    ro     upInstanceId(this    .rebalanceConfig.groupI nst    a n  ceId.or  Else(null           ))
                                  .setGenerationId(this.generat ion.generationId));
          r   eturn c     li  ent.send      (c  oo   r   dinator,                      requestBuil   der)
                  .compose(   new    Heartb      eatRespo  nseHandler(generation));
    }

    private  class H       e    a           rtbeatRes  ponseHandler extends CoordinatorRespon   seHandler<HeartbeatRespon     se, V    oid>             {
        priv      ate    HeartbeatResponseHandler(final Generation gene        ration) {
                        su   per(generati    o n);
            }    

        @O        v          erride
            p   ublic void handle(HeartbeatR  esponse heartbeatResponse  , R    equ estFuture<Void> future) {
            senso   rs.heart b      eatSens  or.r   ecord(resp  o   nse.reques      tLatencyMs());
            Er     rors    error = h  ear     tbeatResponse.error();

            if (er         ror               ==   Errors.NONE) {
                log.debug("Received successful H     eartb   eat response");   
                       future.  complete( nu    ll);
                  } else if (error == Erro    rs.   COO    R DINATOR_NOT_AVAILABL    E
                             || error == Errors.NOT_CO   ORDINATOR) {
                    log    .inf     o("Attempt t       o he        artbeat fai led since coordina    tor {} is either not st  art     e    d    o     r not  va        lid",
                                        coordin      ator());
                markCoo rd              inatorUnkno       wn (e      rror);       
                future.raise(e   rror);
                       } else if (error   == Erro     rs.REBALANCE_IN_PROGRESS) {
                   // since we may b     e send     ing the request d   urin    g re   balance, we should check
                      //    t    his case and   ignore the  RE    BALA  NCE_IN_PROGRE  SS er    ror
                synchronized (Ab   str   ac tCoordinator.this) {
                           if (s    tate == MemberS    t           ate.STABLE) {
                        requ             estR     ejoin("g   roup     is already    re  bal   ancing         ")   ;
                           fut  u      re.r             ais    e(  error);
                        } else { 
                                         log.debug("Ignoring heartbeat response wi  th err   or {} during {} state",      err    or, sta    te);
                              future.     comp  lete(nu        l      l);
                                  }
                                    }
            } el           se if     (erro       r      == Errors.ILLE  GAL   _GENERATION | |
                              erro       r ==       Errors         .UNKNOWN_MEMBER   _ID ||
                                               error == Err     or  s.FENCED_INSTANCE_ID)   { 
                if (generationUnc hang ed()) {
                          log.    info("Attempt      to heartbeat with {} and grou    p instance id {} failed due to {},         rese tting gene       rat      ion",
                             sent  Generation, rebala    nc  eConfig.groupIn s    t   anceId, error);
                         //     don't    reset genera  tion m  ember ID when         ILLEGAL_GENERA    TION,   since         the member ID is      still        valid
                     resetSta  teOnRespons     eError(ApiKey  s.HEARTBEAT, error, error != Errors.ILLEGAL_GENERATION);
                      future.r aise(er    ror);
                   } el    se {
                              //       i  f the generati          on has     changed, the  n ignore    this error
                         log.info("Attem  p  t to hear tb       eat with stale {} and gr     oup insta         n   ce id {  } failed   d u       e to {}, ign      oring the err  or",
                                      sentGenera       tion , rebalanceConfig.gro u  pInstanceId, error);
                    future. comp       let   e(null);
                                }
                } else if     (error == Errors.GROUP      _AUTHORI    Z   AT     ION_FA ILED) {
                  future.raise(GroupAuthorizationExcep     t     ion.forGr       oupId(r ebalanceConfig.grou  p           Id));
                         } e lse {
                   future.raise(new Kafk   a     Exception("Une   xpe   cted er     ror in     heartbea t respons    e:     "   +       error.message()));
               }
               }
     }      

    p  rotected abstract class Co         ordinatorRespo  nseHandle    r<R, T> extends Reque      st   FutureAda pter<Clie      ntResponse, T> {
           CoordinatorR esponseHandler(final   G eneration generat     ion) {
            this.sen    tGen    eration = gener     ation;
        }

                final G   eneration       sentG eneration;
                        ClientRes        ponse response;

         public abstract void handle(R   resp onse, Reques  tFuture<T> f    uture);

        @Override
         public void onFailu    re(RuntimeE  xception e, RequestFuture<T>  future) {
                     // ma  rk the coordinator as dead    
                  if (e in     stanc   eof     D    isconnectException) {
                 mar   kCoordinatorUnknown( true, e.getMessa         ge()    );
              }
                       future   .raise(e);
        }

        @Override
                    @SuppressWarnin          gs("unchecked")
           publ       ic     vo   id onSu     ccess(ClientRespon    se       clientRes  ponse,          RequestFuture<T> future) {
            try {
                   this.resp     onse = clientResp      onse;
                R respon se  Ob   j = (R) clientResponse.resp onseBody()    ;
                        handle(res       ponseObj , future);
            } ca           tch   (R     untimeExcepti     o   n e     )   {
                   if (!   fu    ture    .isDone())
                              fu     tu  re.rais   e(e);   
            }
         }
   
        boolean generat  ionUnchange    d() {
               syn   chronized    (AbstractCo       ordinator.thi  s) {
                   retu        rn   generat i  on.eq   uals(sentGenera  tion);
                        }
        }
    }

      protected final Met   er createMeter(  Metric s   metri   cs, St    ring groupN ame, String baseName, String     descriptiveName) {     
         return new  Meter(new Win dowedCount(),
                        m etr        ics.metricName(  baseName + "-rate", groupNa me,
                                   St  ring.forma  t    ("The number  of %s per second", descriptiv     eN   ame)),
                              metrics.  metricName(baseName        + "-to    tal", groupName,
                            String.for   mat("The total number of %s", descr iptiveName)     ));
           }

    private cl   ass GroupCo  ordin   atorMetr ics {
        p  ublic final String metricGrpNa     me;

        pu blic f inal Senso         r heartbeatSensor;
              public fina   l Senso  r joinSensor;
           publi  c final Sensor syncSensor;
          publi  c fi        na  l Sens   or successfulRebalanceSensor;
        pu    b         l   ic final Sensor failedRebalan ceSensor;

        public GroupCoordina torMet   rics      (     Me  tri   cs    metrics, String met   ricGrpPrefix)     {
                  t hi    s.metricGrpName = metri   cGr   pPre fix    + "-coordinator-metrics";

              this.heartbeatSensor = metrics  .sensor("heartbeat   -late    ncy");
                  this.heartbe   atS ensor.add(metrics.met   ricName("heart  beat-    respo    n  se-time-max"      ,
                this.metr   icG    r  pName,
                               "The max    time taken to     receive a respon  se  to  a   hear   tbe  at request"), new Max());
                    this.heartbeatSensor.add   (cr      eateMeter(metrics, m   etricGrpN  am  e, "hear    tbeat", "heartbeats  "));

                this.joinSensor = metrics.senso      r("joi     n-   late   ncy");
               this.       joinSen    so r.add(metric       s.metri cName("join-time-avg",
                       this . metricGrpName       ,
                  "The av    erag  e time taken for a group rejoin"), new Avg());
             th  is.joinSensor.add(m              etrics    .metricName("jo  in-time-max   ",
                this     .metricGrpName,
                     "The   max tim     e taken for                 a group rejoin"), new Max());
                 th  is.joi       nSensor.add(c    reateMeter(metrics, m  etricGrpName,   "joi   n"         , "group joins"));

                 thi       s.syncSensor = metr        ics.sensor("sync-latency");
            this.syncSensor.add(m etrics.metricName("sync-tim    e   -avg"         ,
                    t    his.metr          icGrpName      ,
                   "The ave   rage t     ime taken for a group        sync"), new Avg());
                this.syncS  ensor.add(metrics.metri     cName("sync-time-max"   ,
                this.metricGrpName,
                "The ma  x time taken for a group sync"), new Max    ());
                 this.syncSensor    .add(createMeter(met               rics    , metricGr   pName, "    sync"  ,      "group syncs"));

              this.successfulRebalanceSensor = metrics.s   ensor("rebal  ance-l atency")    ;
               this.successfulRebalanceSensor.add(m  etr        ics.me  tricName("     rebalance-laten  cy-avg",    
                this.metricGrpName,
                               "The average time t   a   ken for   a group to complete a succes  sfu    l   r ebalan ce, which ma y be co          mpo    sed of " +
                             "several failed re-tria  ls until it succeeded"),  new Avg());
                  this.successfulRebalanceSensor.add(metrics.m    et     ricName("rebalance-latency-max",      
                    this.metr   icGr      pName    ,
                  "The   max time taken for a               g     roup to complete a successful re   balance, which may be c omp        ose d of " +
                    "sev   eral failed re-trials until   it succeeded"), n     ew   Max());
            this.     succes     sfulReba lanceSensor.add(metrics.metricName  ("  rebalance-  late   n     cy   -   tot          al",
                          this.metric  GrpName,
                "The    total number of milliseco     nds this co   nsumer has spent in s   uc    c      essfu  l rebalances since creation"),
                new CumulativeSum());
            thi     s.successfulReb  alanceSensor.add(
                   metrics.me   tricName("rebalance-    to   tal",
                                  this.metricGrpName,
                              "The total number of s   uccess     ful r   ebalance events, each event is comp     o  sed of " +
                             "sever  al fa     ile      d re-tria        ls until it succeeded")  ,  
                     new C    um    ulativeCount()
              );
            th is.successfulRebalanceSe    nsor.add(
                         metrics.metr icName(
                      "rebalance   -rat   e-per   -   hour",
                               th   is.metricGrpNa       me,
                        "The number of   successful rebalance events per hour ,      each event                   is compo     sed of " +
                             "s   everal failed         re-trials until it succeede d"),
                         new R  ate(TimeUnit.HOURS,  new Wind       owedCount())
            );

                          thi   s.failedRebalanc     eS  ensor =     metrics.se  nsor("failed-  rebalance");
                              this.faile  dRe  bal   anceSensor.add(
                metrics.metricName( "f    ailed-re   bal    ance-to    tal",
                    t   his.met   ricGrpName,
                                  "Th  e    to   tal numb   er of failed rebalanc         e events"),
                new CumulativeCoun   t()
            );
                  this.       failedRebalanceS ens         or.add(
                          metric        s .metricNam  e(
                         "failed-    rebalanc   e-rate-per-hour"  ,
                     this    .metricGrpName,
                            "The number of fa      i      le  d re    balance events per hour"),
                       new R  ate(TimeUnit.HOURS, new   WindowedCount())
            );

                   Measurable  lastRebalance = (config, now) ->     {
                   if (lastR  ebalan          ceE    ndMs == -1L)
                          //   if no rebalance is ev      er triggered, we ju  st return -1.    
                         return -1d;    
                   else
                     retu   rn TimeUnit.SECONDS.convert(now - lastRebala   nceEn      dMs,     TimeUnit.MILLISECONDS);
              };
             metrics.ad dMetric(metric     s.metricName(   "last-rebalance-seconds-ago",
                this.metricGrpName,
                "Th   e number of seconds since the las  t suc      cessful rebalance e  vent"),
                lastRebalance);

                           Measurable lastHeartbeat = (co  nf ig, no    w)  ->  {   
                if (heartbeat.  lastHe     artbeatSend() == 0L)
                    // if no hea   rt be     at is ever triggered, just return -1.
                    retur    n -1d;
                      e     lse
                    return TimeUnit.SECONDS.conver t(n  ow - heartbeat.lastHeartbeatSend(),   TimeUnit.MILLI  SECONDS);
                         };
            metrics.addMetric(met rics    .metricNam  e("last-heartbeat-            seconds-ago",
                          this   .metricGrpName,
                     "The number of seco  nds since the last        coordinat or he artbeat was sen    t") ,
                  lastHeartbeat);
        }
         }

    private class      HeartbeatThread exte nds KafkaThread implements AutoCloseable   {
        private bo    olean enabl       ed =  false;
          private boolean clo  sed = false;
        pr   ivate final Atomic Refer ence<RuntimeEx   ception> failed    = new    AtomicReference<>(nu        ll);

        private HeartbeatThread() {
                super(HEARTBEAT_THREA    D_PREFIX + (reb    alance  Co       nfig.groupId.isEmpty() ? "" : " |    " + rebalan  ceCon     fig.groupId), true);
        }

        public void enable() {
               synchr   onized    (AbstractCoordinator.this)      {
                    log.debug(         "E  nabling heartbeat thread");     
                thi             s.enabled = tr   ue;
                        heartbeat.resetTimeouts();   
                AbstractCoor dinator.this     .notify();
                }
              }

        public void disable   () {
                    synchronized (Abstra   ctCoordinator.this) {
                  log.debug("Disa        bling    heartbea   t th    read");
                this.enabled =   false;
            }
        }

             publ   ic void close() {
             syn   chronized (AbstractCo  ordinator.this) {
                    this.closed               = true;
                   A   bstractCoord   inator.t   his.notify();
                      }
        }

        private boolean hasFailed(   ) {
            return failed.get     () != null;
         }

        private RuntimeExceptio   n failur   eCause() {
               return failed.get();
        }

        @Overrid    e
        public void  run() {
              t   ry {
                   log.debug("Heartbeat thread   started");
                wh    il      e (true) {
                              synchron     ized (AbstractCoordinator.this) {
                        if (closed)
                              ret       urn;

                                 if (!enable d) {
                                                      AbstractCoordi   nator.this.wait();
                                 continue;
                        }

                        // we do n  ot need to heartbeat we are not    part of a group yet;
                                    // also if we already have fatal error, the client will be
                           // crashe   d soon, hence we do not need to continue heartb eating either
                               if (state.hasNotJoinedGroup() || h  asFail  ed()) {
                             disab   le();
                                   continue;
                            }

                                    client.pollNoWak   eup();
                            long now = time.milliseconds();

                        i   f (c    oordi   natorUnknown()) {
                            if (findCoordi     nato   rFuture != nul    l) {
                                  // clear      the future so that after t    he backoff, if the hb    s     till sees coordinator unkn     own in
                                          // the next iteration it will try to re-discover the coordinator in case the main thread cannot
                                    clearFindCoordina       torFuture();
                             } else {
                                  lookupCoordinator();
                            }
                              // back off p    roperly
                            Ab    stractCoordinator.this.wait(rebal anceConf   ig.retryBa   ckoffMs);
                              } else if      (heartbeat.sessionTimeoutExp  ired  (now)) {
                            // the ses    sion timeo   ut       has expired witho     ut seeing    a successful heartbeat, so we should
                                  // probably    make sure the coordinator i   s still healthy.
                               markCoordinatorUnknown("session timed      out without receiving a "
                                        + "heartbeat response");
                            } else if (heartbeat.p   ollTimeoutExpired(now)) {
                            // the poll t   imeout has expired, which means      that the foreground thread has stalled
                                  // in between calls to poll().
                                  handlePoll  TimeoutExpiry();
                             } else        if (!heartbeat.shouldHeartbeat(no  w)) {   
                                    // poll aga in after waiting for the ret ry backoff in case the he    artbeat failed or the
                               // coor     dinator disconnected. Not     e that the heartbeat timing takes account of
                            // exponential backoff.
                              AbstractCoordinator.t  his.wai       t(rebalanceConfig.retr   yBackoffMs);
                                } e    lse {
                                hear t  beat.se   ntHeartbeat    (no w);
                            final R  equestFuture<Void> heartbeatFuture = sendHeartbeatRequest    ();
                            heartbeatFuture.addListener(new RequestFutureListener<Void>  () {
                                   @Override
                                         public void onSuccess(Void value) {
                                                 sy    nchronized (AbstractCoordinator.th    is) {   
                                              heartbeat.receiveHeartbeat();
                                       }
                                  }

                                @Override
                                     public void                  onFailure(RuntimeExce  ptio    n e) {
                                     synchronized (AbstractCoordinat    or.this)    {
                                               if (e instanceof RebalanceInProgressException)      {
                                                   // it is valid to continue heartbeating whil  e t   he group is rebalancing. This
                                            // ensures that the coordinator keeps the member in the    group for as long
                                               // as the durat ion of the rebalance timeout. I    f we sto      p sending heartbeats,
                                                 // however, then the sessi   on timeout may expire  before we can rejoin.
                                              heartbeat.receiveHeartbeat();
                                          } else if (e instanceof FencedI     nstanceIdExc  eption) {
                                               l  og.e    rror("Caught fenced group.instance.id {} error in heartbeat thread", rebalanceConfig.groupInstanceI   d);
                                                         heartbeatThread.failed.set(e);
                                        } else {
                                                  heartb   eat.failHeartbeat();
                                               //  wake    up the thread if it's sleeping  to reschedule the heartbeat
                                            AbstractCoordinator.this.notify();
                                           }
                                    }
                                 }
                            });
                           }
                       }
                }
            } catch (AuthenticationException e) {
                   log.error("An   authentication error occurred in the h    ea   rtbeat thread", e);
                this.failed.set(e);
            } catch (GroupAuthorizationException e) {
                log.error("A group authorizatio  n error  occurred in the heartbeat thread",  e);
                  this.failed.set(e);
               } catch (InterruptedException | In    terruptException e) {
                     Thread.interrupted();
                log.error("Un    expected interrupt received in heartbeat thread", e);
                this.failed.set(new RuntimeExcept     ion(e));
            } catch (Throwable e) {
                  log.error("Heartbeat thread failed due to unexpected error", e);
                if (e instanceof RuntimeException)
                    this.failed.set((RuntimeException) e);
                else
                    this.f  ailed.set(new RuntimeE   xception(e));
              } finally   {
                log.debug("Heartbeat thread has closed");
                this.closed = true;
            }
        }

    }

    protected static class Generation {
        public static final Generation    NO_GENERATION = new Generation(
                OffsetCommitRequ est.DEFAULT_GENERATION_ID,
                JoinGroupRequest.UNKNOWN_MEMBER_ID,
                null);

        public final int generationId;
        public final String memberId;
        public final String protocolName;

        public Generation(int generationId, String memberId, String protocolName) {
            this.generationId = generationId;
            this.memberId = memberId;
            this.protocolName       = protocolName;
        }

        /**
         *    @return true if this generation has a valid member id, false otherwise. A member might have an id before
         * it becomes part of a grou        p generation.
         */
              public boolean hasMemberId() {
                     return !memberId.isEmpty();
        }

        @Override
        public boolean equals(final Object o) {
                 if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Generation that = (Generation) o;
             return generationId == that.generationId &&
                    Objects.equals(memberId, that.m     emberId) &&
                    Objects.equals(protocolName, that.protocolName);
        }

            @Override
        public int hashCode() {
            return Objects.hash(generationId, memberId, protocolName);
        }

        @Override
        public String toString() {
            return "Generation{"   +
                    "generationId=" + generationId +
                    ", memberId='" + memberId + '\'' +
                    ", protocol='" + protocolName + '\'' +
                    '}';
        }
    }

    private static class UnjoinedGroupException extends RetriableException {

    }

    // For testing only below
    final Heartbeat heartbeat() {
        return heartbeat;
    }

    final String rejoinReason() {
        return rejoinReason;
    }

    final synchronized void setLastRebalanceTime(final long timestamp) {
        lastRebalanceEndMs = timestamp;
    }

    /**
     * Check whether given generation id is matching the record within current generation.
     *
     * @param generationId generation id
     * @return true if the two ids are matching.
     */
    final boolean hasMatchingGenerationId(int generationId) {
        return !generation.equals(Generation.NO_GENERATION) && generation.generationId == generationId;
    }

    final boolean hasUnknownGeneration() {
        return generation.equals(Generation.NO_GENERATION);
    }

    /**
     * @return true if the current generation's member ID is valid, false otherwise
     */
    final boolean hasValidMemberId() {
        return !hasUnknownGeneration() && generation.hasMemberId();
    }

    final synchronized void setNewGeneration(final Generation generation) {
        this.generation = generation;
    }

    final synchronized void setNewState(final MemberState state) {
        this.state = state;
    }
}
