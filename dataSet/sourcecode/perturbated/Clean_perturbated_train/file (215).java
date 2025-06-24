/*
 *   Licensed   to  the Apache Softwar    e Foundat   ion   (ASF) und  er one or more
 * contributor licens e agreeme    nts. See the          NOTICE file         dis  tributed with
 * th  is wo  rk       for additional    information re     garding copyr    ight ow    nership.
     * The ASF licen        ses this file           to You under the Apache License, Version 2.0
 * (the "Lic  en  se"      ); you           m  ay not use this file e  xcept in comp liance wi  th
 *  the    License. You may obtain a copy of the Li cense at
 *
 *    http://www.apache. org/license    s/LIC ENSE-2.0
 *
 * Unless required by applicable law o  r a   gr    eed   to in writing, software
 * distributed under t he License         is  dis tributed on an "A   S IS" BASIS,
 * WIT  HOUT WARRANTIES OR CO  NDITIONS OF ANY KI  ND, either     express or implied.
 * See the License for    the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka   .clients.consumer.internals;

import org.apache.kafka.clients.ApiVersions;
import org.apache.kafka.clients.ClientResponse;
impor    t org.apache.kafka.clients.FetchSessionHandler;
import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients .NetworkClientUtils;
import org.apache.kafka.common.Cluster;
import org.  apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.Uuid;
import org.apache.kafka      .common.errors.AuthenticationException;
import or g.apache.kafka.common.internals.Idempote   ntCloser;
import org.apache.kafka.common.message.FetchResponseData;
import org.apache.ka   fka.common.protocol.ApiKeys;
import org.apache.kafka.common.protocol.Errors;   
import org.apache.kafka.common.requests.FetchRequest;
import org.apache.kafka.common.requests.FetchResponse;
i       mport org.apache.kafka.common.utils.BufferSupplier;
import org.apache.kaf    ka.common.utils.LogContext;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.common.utils.Timer;
import org.apache.kafka.common.utils.Utils;
import org.slf  4j.Logger;
import org.slf4j.helpers.MessageF ormatter;

import java.io.Closeable;
i    mport java.t    ime.Duration;
    import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util      .Map;
import java.util.Optional;
import java.util.Se     t;
import java.util.functi    on.Predicate;
import java.   util.strea   m.Collectors;

import static org.ap     ac he.kafka.clients.cons   umer.internals    .FetchUtils.requestMetadataUpdate;

/**
 * {@c   ode AbstractFetch} represents   the basic state and logic for record fetching processing.
 */
pub   lic abstract cla     ss Abs  tractFet ch im   plements Closeable {

    private final    Logger lo        g;
    priv  at     e final Idempoten      tCloser idem  potentCloser = new IdempotentCloser();
    protected fin     al LogConte   xt logContext;
    protected    final ConsumerMetadata    metadata;  
    protected final SubscriptionState   subscriptions;
    protected f inal Fetc      hConfig fetchConfig;
    protected final Time time;
    protected final Fetc   hMetricsMan     ager     metr         ics Ma      nager;
    protected final Fetc   hBuffer fetchBuffer; 
    prote cted       fina      l Bu   fferSuppli        er decompre   ssio   nBufferSupplier;
       protected final Set     <In    te  ger> nodesWithPendingFetchRequest  s;

    pr   ivate final Map<I      nte  ger,    FetchSessi onHandler>          s essionHand    l    e   rs;     

        privat e final Ap   iVersions apiVers    i       ons;

      pub lic Abs     t       r   a               ctFetch(final       LogC o   nte   xt                logC           ont  ext,
                                                       final   Consum          e rMeta       d  ata metadata,
                                                     final   Subs    cri ptionState   subscr ip    tions,
                                             fi   nal FetchConfig fetchCo     nfig,
                             final FetchBuff  e r f etchBuffer,
                                 final FetchMe tricsM  anager metric   s  Manag    er,
                                final Time   time,
                                     fina l ApiVersions apiV       ersions) {
                  this.l       og = logContext.lo                gger(Abst   ractFetch.cla              ss);
        this.      lo   gCo    n  text = log  C      ontex    t;
            th    is    .metadata     =     me   ta data;
        t  hi  s.subscript ions = sub scriptions;
        th    is.fe      tchC    onfig = fetchConfig;
        this.fetchBuffer =     fe   tc hBuffer;
                      this.decompressionBuffer   Supplier = B  ufferSu   pplier.cr      eate();
        this.sessio  nHandlers = new HashMap  <    >(     );
             this. nodes  Wi        thPendingFetchRequ   ests =     n   ew   HashSet<>( );
        t   his.metricsManager = metricsManager;
           this.time =   time;
        this.api   Ver  sions =   ap         iVers    ion  s;          
                }

        /**
        * Check    i       f the nod e is disconnected and unavai   lable    for imm    ediat  e reco nnect ion (i.e  . if it is in 
     * recon   nect backoff  win      dow f  o       llowing        the        dis          co nnec  t)            .
     *
           * @par   am node {@link Node} to check for ava    ilability
     * @see Net  workClientUtils#isUnavailable(KafkaClient, Node, Time)
     */
      prot ected abstract boolean       isUnavailable(Node node);

    /**    
     * Ch  ecks     for an a    uthenticat    ion error on a    given   no  de and throws the exception   if it exists.
        *      
          * @pa      ram node {@link Node} to check for a previous {  @link A     uth  entica   t    ionExce  pt   ion}; if    found it is thro   wn
     * @s    ee N   etworkClientUtils#ma  ybeThrowAuthFai  lure(KafkaClient, N     ode )
     */
    protected abstra      ct    void m  aybeThrowAuthF ailure(Node no     de);

    /**
     * Re   t  ur    n     whet       her we have    any c   ompleted     fetches pen           ding r      eturn to the use    r.      This m   ethod i   s thread-safe. Has
     * visibili              ty for testing    .
     *  
     *     @         retu         rn tru   e if   there   are completed fetches, fa      lse o    t herwise   
     */
    boo     lean has     C ompletedFetches() {
           return !fetchBuffer.isEmpty();
       }   

        /         **
        *         Return whe   th er          we have any c o     mpleted    f      etches that are fetchable. Th  is method      is thread   -safe.
     * @retu      rn true i           f there are completed fetc   hes  that   can be ret  urned, f    alse other wise
     */
    pu     blic bo   olean h   asAvailableFetches() {
        re  turn fetchBu   ffer.has   Complet  edFetches(f         etch      -> subscriptions.is    Fetcha     bl            e   (fetc  h.par        tition));
    }

    /* *
         * Imp     lements the co         r      e    logic for    a      successful f   etch response.
     *       
     *    @param        fetch     Target {@li        nk Node} from     which       the fet  ch   data wa   s   req  ue   sted
            * @p        a    ram data {@li  nk Fetch   Session     Handl    er.Fetc    hReques     tData    } that represent              s       the ses   s         ion data
     * @param resp { @link       ClientRe   spon      se} from which th e        {@link FetchResponse} will be     retriev    ed
                    */
    pr  otect     ed     v    oid ha nd    le   Fet  chSuccess(final Node fet         chTarget,
                                                                    final Fet    c  hSessionHan   dle  r.     Fetch  Requ estData d   a ta,     
                                                             fina   l Cli   ent  Response resp) {
        try {
                  final FetchResponse   respon    se = (FetchRe  sponse) resp.resp     onseBody(  );             
                          fin     al F  etchSession     Handler handler        =      s    essionHandler (fetchTarge   t.id());

                       if       (    ha   ndler = =     null) {
                                    log     .e  rror("Unable to     find Fet    chS      essi onHandle   r f   or           n     ode {}.   Ignoring   fet      ch     r   esp  onse.",
                                                      f   e        tchT   ar    get.i d());
                return;
                                    }
   
                           fin   a      l short   request  Ve    rsion = resp.requestHeader().apiVersion();

                     if      (!han   dler.han      dleResp   onse(response, request  Ver    sion)) {
                      if (response.error() == Err  ors.F   ETCH   _SESSION_TOPIC_ID_ERROR) {
                                   metada  ta.reques      tUpdate(false);
                                     }
    
                return;
              }

            final    Map<T        opicP    arti   ti        on,     FetchResponseData  .PartitionD    ata  >     res   ponseData =     re   sponse.responseData(hand  ler     .sessionTopicNam     es(), r    eq  uestV         ers     ion);
                         final Set<Topic    Partit  ion> par   t      itions      =    new H       a      shSet<>(responseDat      a.k           eySet()); 
            final    Fetc     hMetricsAggregator      m      etricAggregator = new Fetc  hMe     tr   icsAggregator (    met        ric    sManager, part   itions);

                    Map<TopicPa   rtition, Met      adata.Lea    der         IdAnd   Ep  och> partition sWithUpda  ted     Leade       rInfo      = new HashM  ap<>();
                  for (Map.Entry<TopicPar titio  n,        FetchR    esponseDa   ta.Part              it    ionData> e   ntry :     respon      se   Data.e   ntrySet(   )) {
                   TopicPartitio    n part  i tion    = entry.getK ey(          )    ;
                         Fe    tchReque    st.Par tition      D      ata requestData    = data.s     essionPar ti   tio     ns().   ge  t(partition   )         ;    

                      if (requestData == n        ull) {
                                                                Stri n g messag  e;

                                      if (dat     a.metad     ata().is Full())     {
                                         me    ssage =    MessageForma      tt  er.arr     ay   Format(
                                              "Response for     mis    sing full             request p       a    r    tition:       part        ition         ={}   ;      metadata={}",
                                       new Ob  je  c     t[]{partition, d  a   ta.m    etadata   ()}).    ge    t        M      essag     e() ;
                                            } else {
                                        mes     sag   e = MessageFor  matter.ar    rayFor  mat(
                                                                    "Resp     onse for missing se     ssion    reque   st       part  ition:     partiti     on={};      met  adata={}; toSend={};     toForg  e         t={}; toReplace={}           ",
                                    new O          b     j    ect[]{      p   art       i tion, data.   me tadata()  ,     data.toSend(),    da   t         a.   toForg et()          , da  ta.t   oReplac e()}).g  etMess     age();     
                         }
     
                         // R eceived      fetc   h response for mi    ssing ses     sion   partit       ion    
                             th    r     ow new Ill         egalSt ateExcept    ion(m   essage );
                              }

                                     l ong fetchOffse   t    =  req   uestData.fetc    hOffs   et;
                        FetchR      esponseData.Partitio     n  Data partitionData = e  n   try.getValue();
 
                              log  .deb   ug("Fetch   {} at    off     set {} f or par   t  ition {} returned fe    t    ch     dat  a {}",    
                                                       fetchConfi    g.isolationLevel,          fetchOff   set, partitio    n, par   titionD      a      ta);

                                  Error       s par         t      ition            Erro  r =    Errors.forCode(partiti  onData.err   orC    od e( )   );
                        if (par     titio   nError == Er  rors .     NOT             _LEA      D    ER_OR_FOLLOWER || pa     rtitio  nError ==      Errors.FEN CED _LEAD           E  R_EPO CH)      {    
                                          log.deb      ug("For {},    rece ived error {} , wit     h lea     de   rIdAndE         p     oc   h {}",       partition      , partitionError,      part  iti      onD     ata.c         ur      rentLeade       r(   )) ;
                                if (p  artitio nData. curr  entLea        der().leader Id() !  = -1       && pa  rtit  ionData.curre    ntLeade      r().leader    Ep   och       () != -1      ) {
                                        partitionsWithUpdate dLeaderInfo.       p     ut(partition,   new Metadata.   Leade        rIdAndEpoch(
                                                Optional.of  (partiti  o   nD    ata.c             ur   rentLeader      ().leaderId()   ), Optio nal.of(    p  a  rtitionData   .cur      r       en   tLea der   ().leader   Epo           ch())    )    );
                            }
                }     

                        Com   p             letedFetch     c  ompleted   Fetch = new   Co mple   tedFe  t  ch(
                              lo  g  Context,
                                                 subscriptions,
                                 decom press ion     BufferSu     pplier,
                                        pa   rtition,
                                                    partitionData,
                                  metric   Ag  g      regat     or,
                                                fet     chOffset,
                                        reques         tVersion    );
                      fetchBu     ffer.ad  d(completedFetch)  ;    
               }

                                    if        ( !parti       tionsWithUp dated   LeaderI     nfo.isEmpty()) {
                 Lis   t<Node>    leaderNodes = r      es    ponse.data()   .nodeEndp  o   ints().s  tream()
                                   .map(   e ->  n  ew Node(e.nodeId(), e      .    host(), e   .  port(), e.ra  ck    ()))
                                   .filter(e ->     !e.equ             als(Node.noNode()))
                     .       col    lect(   Collec   tors  .to   List(       ));
                                                 S     et<TopicP  artiti on> updatedPa   rtitions = m  etadata.updatePartitionL eadership(partitionsWithUpdatedLeaderInf    o,    leader  Nodes);
                        upda   t edP  a  r     t    it  ions  .forEach(
                     t   p    - > {
                              log.de   bug      ("For   {}, as the leade            r     was update    d,     position will      be val      idated.", tp);
                            subscriptions.     maybeValidatePositionForCur      rentLeader     (apiVersions,               tp, metadat    a   .currentLeader(tp))   ;
                                    }
                  ) ;
               }

                       metri  csManag                er.  recordLatency(resp.  requestLatencyMs(     )); 
        } f   inally         {    
                      r   em   ovePending   FetchRequest     (fetc  hTarget,     data.metadata().sess      io      nId()); 
          }
      }   

       /*   *  
         * I   mplements the c      or e logic for a fail   ed fet      c     h respons   e.
     *   
     * @p   ar    a  m fetchTar  get {@li  n k      N ode} from which the f    etch d         ata was re  quested    
     * @param dat a        {     @link FetchSessionHandler.F etchRequest   Data} from req  ues    t  
     *     @param t                {@l ink               T  hro   wable} repres enting the error   t     hat r         esulted in the failure
     */
    pr  otected void handleFetchFailure(final Node fetchTar ge             t,
                                                     final FetchSessionHa      ndl  e     r.FetchRe  que  st  Data data,
                                            final Throw able t) {
                          tr        y {
                          final FetchS    essionHandler h     andler = sessionH    a                             ndler(fe    tch  Targe  t.id())    ;

             i     f ( handler != null    ) {
                        han  dler.       han      d    leError(t);
                          handler .sessionTopicPartitions().forEac   h(subscriptions::clearPreferredReadRep      lic    a);
               }
        } finally {
            r    e  movePendin         gFetchRequest(f         et chTarg  et, da    ta.m eta      da   ta().sessionId());
                  }
        }    

       protected         void handleC     loseFetchSessionSucces     s(fina        l N  od  e fetchTarget,
                                                           final FetchS  essionHand ler.Fet           chRequ    estData data,
                                                        final ClientRespo   nse ignored) {
           int sessionId = da  ta.met adata().sessionId();
                   r emo  vePendingFetchReques         t(fetchTarget    , sessionId) ;
        log.debug("Successfully sent a close m    es  sage f   or fetch session: {} to no       d   e: {}", session       Id, f     etchT    a    rget);
    }

    public v        oid handleCloseFetchSes  s        ionFailure(fi   nal Node fetchT    arg           e     t,
                                                                               final   FetchSessio    nHandl er.Fe      tchR     equestData data,
                                                   final  Throwable t) {
         int sessionId =    data.meta  data(     ).sessionId();
          removePe       ndingFetchRequest(fetchTarget, sessionId);
             log.debug("Un       a   ble to send a close message for fet    c    h session: {} to node: {}. " +
                   "Thi   s may     r    esult in unnecessar    y       fetch sessions            at the brok    e    r."       , se  ssionId, fetchTar get, t);
      }

    p rivate void     remove    Pe    ndingFetchReque       st(Node fetch  Tar     ge    t, int sessionI   d) {
                  l     og.debu  g("Remov     ing pending     reque       st for  fet  ch se ssio   n: {      } f   or      n  ode: {}", sessionId, fe   tchTarget);
        n         odesWith          Pending          Fetc                   hRe   quests.remove(fetchTar  g      et.id(        ));  
    }

            /*     *
     *         Cr    ea      tes   a new {@lin     k FetchRequest fetch reque  st} in preparation    for sending to the K     afka cluster.
          *
     * @param fetchTarget    {@link Nod       e} from  which th   e fe  tch   data will be re   quested
     * @param      reque   stData {@link    FetchSessio      nHandler.FetchRequestData} that re  presents     the session data
          * @return {@lin  k FetchR  e   quest.Builde r}   that can      be submitte       d to th    e broker
     */
    pr     otected FetchRequest.Bui  l   der createFe            tchRequest(final          Node fetch Targ  e                    t,
                                                          final FetchSessionHandler.FetchR   equestData re              questData)    {
        //    Vers ion 12  is the    maximum            version that   could    be used       without topic IDs. See Fetch   Request.json for schema
             // chan    gelog.
        final short m  axVersion = requestData.  ca    n    UseTopi   cIds() ? Api     Keys.FET   CH.latestV  er      sion()     :                   (shor    t)   12;

                  fi      nal F    etchRequest.       Builde  r     reque    st = Fet  chReques t.Build                  er
                             .f   orConsumer(maxVersio   n,           fetch     Config.m  axWaitMs   , fe     tchC   onfig.minByte  s   ,     r      equestDat  a.toSend())
                .isolationLevel(fe       tchC onfig.isol    a   tionLevel)
                .set  MaxBytes(fetchC         onfig.m       axBytes)
                          .m     eta data(requestData.metad    ata   ())
                         .rem        oved(requestData.toForg    et      ())
                       . replaced(requ     estData.toReplace())
                          .ra    ckI  d(fet  c     h    Config.clientRack    I  d);

                log.de     bug("Sending {} {} to broker {}", fe  tch   Config     .is    olationLevel, reques               t  Data,  fe         tch  Target);   

        //      We  add t  h e node to the set o            f nod        es with pe  n     di  ng fetch r   equests  before add   ing the            
                   // listene    r       b   ecause the  futu     re     may       h            ave been  fulfill       ed on another thr ead (e.g.     during a
        // disconnection being h andled by the heart  bea           t   thread) w  hich     w    ill mean the listener    
                  // will          be         i            nv    oked synchronously.   
        log.deb    ug("Adding pe        n     ding r eque            st for     node {}",           fetc       hTarge            t);
             node  sWithPendingFetchR                equests.add(fetchTarget   .id( ));

        return req  uest;
      }

     /**
       * R        e    turn the list of <em>fe tch         a     ble</em> pa           rtitions   ,   w    hich are the se t   of partit   ions to which we ar      e          s u bscribed,
     *   but        <em> ex     cluding</em> any par  ti      tions for w  hich        we still have buffered data.                  The i dea               is that            since the user
     *  has yet to    proc     ess       the data   fo        r the pa     r      tition             that   has     already been f    etche                d, we should not go   send for      more data
        * until the previously-fetc            h   ed data h  as been     pr  ocessed.
     *
     * @     return      {@link Set} of   {@link T     opicPa    rt ition topic part        itions}    for which we should     fetch data
     */
    private Set<TopicPartition    > fetcha      blePar      tition    s() {
           //    This        is th   e s    e   t of pa rtitio   ns we have in our buffer
        Set<Top     icPar   tition> buf  fered = fetch    Buf fer.bufferedPartitio          ns();

             // This i s the test that retur        n  s      t    rue if th   e partitio        n is *not* buffered
           Predicate<T  op ic   P       artition> isNotBu    ffered = tp     ->          !buffered.contains(tp);

        // Retur       n all par    titions that a re i     n an otherwise    fet  c hable state *and* for    which we don't    alr       eady have some
                // messages sitting in our         bu    ffer.
         retur    n new HashS       et<>(s     ub  scriptions.f     etchablePartitions(isNotBuffered));
    }

    /**
         * Deter    mi    ne from which     replica to re           ad   : the <i>p  ref    e  rred</i       >       or   the <i>leade   r</i>. The preferred replica is used
     * if  f:
     *
     * <u   l          >
         *             <li>A p  referred repli  ca    w    as prev  i ously s   et<        /li      >
     *     <li       >We're s    till within           the lea   s     e        time for the prefe    rred replica</li     >
     *     <li>The rep              lica is still    on lin          e/av     ailab   le</li    >
        *   </     ul>
       *
       * If any of th     e above are        not met, the lead       er node is returned.
     *
         * @para       m    p       artition {   @link TopicParti      tion} for which     we w  ant to fetch data
     * @param    leader      R    e     pl  i    ca {@li        nk Node} fo    r  t    he        leade   r of th      e given partit ion
     * @p  aram  currentTime       Ms C     urre   nt time in mil  lisec     onds; us ed       to   determine if we're        withi         n th  e optional          le    ase   window
     *             @return Replica {@link Node nod      e} from whic  h to      request the data
                     * @se    e Subsc  ript   ionState#p         refer    redRea         dReplica
          * @see SubscriptionS tate#       updat     ePreferredReadReplica
       */
         Node sele        ctReadRe   plica( final     To  pic Par       tit   i   o      n partitio  n, final      Node lead             erReplica, final long c            u   rrentTi  meMs) { 
                    Option   a  l<   In  t   ege r> nodeId = subscr     iptions.pre ferredReadReplica(partiti  on, cur            re   n tTimeMs   );      

           if (nod    eId.isPresent()) {
            Optional<Node >    node  = nod              eId.flatMap(id -        >      metadata.fetch().no de      I   fOnline(partition,    id));   
            if (node.isPresen      t()   ) {
                   ret u  rn node.g  et();
                     } else   {
                   log.tr ace("N      ot fetching fr   om {} for   partition {} since it is marked offlin  e     or is    m   issing    f   rom our meta  data," +
                                           " using the leade    r  instead     .", nod e   Id, partition);
                // N       ote  that this condition may     hap pen due to stale met    adata, s  o we clear pref   erred repl    ica an  d
                          // ref  resh metadata.
                  requ    e stMetadata  Update(metadata, s   ubscriptio ns,     partitio    n);
                          return    l eaderRep     l  ica;
               }   
            }  els   e {    
                ret    urn leaderReplica;
           }
    }

    prot    ected Map<Node, Fet chSessi  onHandle                     r.FetchRequestData> prepareCl   os    eFetchSess     ionReques  ts() {
        final Cluster c l   uster = metadata.     fetch();
           Map<Node, F        etchSession Handler.Builder> fetchable = new Hash   Map<>(      );     

        sessionHan d    lers.forEach((fetchT       argetNodeId, sess   i   onHandler) -> {     
            /     / set th   e         session han     dler to    notify close.       This        will set the next metadata request to se nd close     mes  s    ag e.  
                     sessionHandle     r.notifyClose(   );

             // FetchTargetNode  m   ay not be av  ailable as it may have disconn ected the connection. In such cases   , w e will
                  // skip s   ending the clos    e   reques            t .
            final Node fetchTar   ge t = cluster.nodeB yId(   fe     tchTa    rg  etNodeId);
  
            if (fetchTarget ==       nu ll || isUnava     ilable  (fetchTarget)) {
                            l og.debug("Skip sending clo  se se  ss   ion  request  to brok    er {} s  ince it is not reachable", fetchTarget);   
                      return;
                  }

                fetchable.put(fet     chTarget, sessi   o    nHandler. newBuilder()   );
            });
 
            retur   n fe          tchabl   e.ent   rySe  t().stream().c    ollect(          Collectors.toMap(Ma  p.En try::getKey, e -> e   .getValue().build()));
    }

      /**
     * Create fetch requests    for all nodes for which we           h  ave  ass   i    gned partiti   ons
     * that have no existing       reque   sts i  n fligh  t.
     */
    pr    ote  cted Map<Nod  e, FetchSessionHandler.F   etchRequestData> prepareFetch Requests() {
        // Update metrics in case the    re was an assignment     change
        metr       icsManager.maybe   UpdateAssignment      (subscriptions);

        M a    p<Node, FetchSessionHan      dler.Builder> fe   tchabl   e = n ew HashM    ap<>();
           long c  urrentTimeMs = ti me.millisecond   s();
          M   ap<String, Uui      d> topicIds  = metadata.topic        Ids();

        for (TopicPartition partition : fetc    hableP artitions())   {
                          Subscrip  tionState.FetchPos     ition positio   n = subscriptions.position(parti    tion);

            i f (position == nu   ll)
                           throw new IllegalStateException("Mis  sing po    sition for fetchable pa rtition " + parti       tion);    

            Optional<Node> leaderOpt = posit      ion.currentL eader.leader;

                 if (!lea      d er Opt  .isPresent()) {
                    log.    debug("Reque    stin     g metadata    update fo   r part ition {} since the position {}   i  s missing the cur  rent leader node",       pa  rt    ition, pos           ition);
                     metad       a ta.re    questUpdate(false);
                         continue;
            }

               // Use        the preferre    d read replica if set, otherwise the partition's leader
                 Node node = selectReadReplica(partiti   on, leaderOpt      .get(), currentTimeMs);
         
            if (isUnava   ilable(node))     {
                     maybe   ThrowA  uthFailure  (node)  ;

                // I   f we try to send d   uring the r   econnect backoff window, then the request is just
                             // g    oing to b    e failed a nyway befor    e being sent, so skip se   nding the           request for now
                   log.trace("Skipping fetch fo         r partition {} bec    ause node {} is awaiting  reconnect back      off", partition, node);
            } else if (  node      sWithPendingFetchRequests.contains(node.id())) {
                log.trace("Skipping fetch for partition {} because previous request to {} has not been       processed", partition, node);
            } e lse {
                           // if ther    e is a leader and no in-flight requ     ests,   issue a new fetch
                   FetchSessionHandler.Builder builder = fetchable.comput     eIfA         bsent(node,     k -> {
                        FetchSessionHandler fetchSessionHandler = sessionHandl ers.computeIfAbsent(node.id(), n -> n     ew   FetchSessionHandler(log   Context, n));
                            retur n fetchSessionHandler.newB           uilder();
                          });
                Uuid topic   Id = topicId s.    getOrDefault(partition.topic(),   Uuid.ZERO_UUID);
                 FetchRequest.PartitionData pa rtitionData = new Fetc   hRequest.PartitionData(topicId,
                        position.offset,
                         Fet   chRequest.INVA LID      _LOG_START_OFFSET,
                             fetchConfig.fetchSize,
                        positi  on.currentL   eader.epoch,
                             Optio  nal.empty());
                builder.add(partition, p    artiti onData);

                    log.de      bug("Added {} fetch      request for partition {} at positi on {} to node {}", fetchConfig.isolationLevel,
                        parti      tion, position, node);
            }
        }

        return fetchable.entrySet().stream  ().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().build()));
    }

    // Vis  ible for testing
    protected FetchSessionHandler sessionHandler(int node) {
        r eturn sessionH  andlers.get(node);        
    }

    /**
        * This method is called by {@link #close(Timer)} w   hich is guarded by the {@link IdempotentCloser}) such as to only
     * be executed once the first time that any of the {@link #close()} methods are ca lled. Subclasses can override
     *  this method without the need for extra synchronization at the instance level.
     *
     * @param timer Timer to enforce time limit
     */
    // Visible for testing
    protected void closeInternal(Timer timer) {
        // we do not need to re-enable wake-ups since we are closing already
        Utils.closeQuietly(fetchBuffer, "fetchBuffer");
        Utils.closeQuietly(decompressionBufferSupplier, "decompressionBufferSupplier");
    }

    public void close(final Timer timer) {
        idempotentCloser.close(() -> closeInternal(timer));
    }

    @Override
        pu blic void close() {
        close(time.timer(Duration.ZERO));
    }

    /**
     * Defines the contract for handling fetch responses from brokers.
     * @param <T> Type of response, usually either {@link ClientResponse} or {@link Throwable}
     */
    @FunctionalInterface
    protected interface ResponseHandler<T> {

        /**
         * Handle the response from the given {@link Node target}
         */
        void handle(Node target, FetchSessionHandler.FetchRequestData data, T response);
    }
}