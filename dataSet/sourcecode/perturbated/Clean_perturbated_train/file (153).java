/*
 *       Licensed    to the Apache     Soft        ware Foundation (ASF)   under   one or more
 * contributor license agreements.   See the NOTICE file  dis    tr   ibuted wit     h
 * this w   ork for ad    ditio   nal information regarding copyright ownersh ip.
       * Th    e     ASF lic   ense  s this file to Y  ou unde     r the Apac he License, Version 2.0
 * (the "License"); you        may not      use t  h   is file     except in compliance w   ith
 *     the    L           icense. You may obtain a copy of the Li       ce   nse   at
 *
   *    http://www.apac    he.org/li  censes/LI C    ENSE   -2.0       
 *
 * Unless r  equired b   y applica  ble law or agre    ed to in     w    riting, software
 * dis       tributed under the License is distributed on      an "AS I S" BASIS,
 * WITHOUT WARRANTIES OR CONDIT  ION     S OF ANY KIND, eith er express or implied.
 *      See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.clients.   consumer.internals;

import org.apache.kafka.clients.CommonClien     tConfigs;
import org.apache.k    afka.clients.GroupRebalanceConfig;
import org.apache.kafka.clients.MockClient;
import org.ap   ache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.co     mmon.Node;
import org.apache.kafka.common.err  ors.AuthenticationException;
import org.apache.kafka.common.errors.DisconnectException;
import or    g.apache.kafka.common.errors.FencedInstanceIdException;
im     port org.apache.kafka.common.errors.GroupMaxSizeReachedExcep   tion;
import org.apache.kafka.common.errors.InconsistentGroupProtocolException;
import org.apache.kafka.common.errors.UnknownMemberIdExc  eption;
i mport org.apache.kafka.co  mmon.errors.WakeupException;
import org.apache.k     afka.common.in  ternals.ClusterResourceListeners;
import org.apache.kafka.common.message.HeartbeatResponseData;
import org.apache.kafka.common.message.JoinGroupRequestData;
import org.apache.kafka.common.message.JoinGroupResponseData;
import org.apache.kafka.common.message.LeaveGroupRequestData;
import org.apache.kafka.common.message.LeaveGroupResponseData;
import org.apache.kafka.common.message.Leav eGroupResponseData.MemberResponse;
import org.apache.kafka.common.message.SyncGroupRequestData;
import org.apache.kafka.common.message.SyncGroupResponseData;
import org.apache.kafka.common.metrics.KafkaMetric;
import org.apache.kafka.commo         n.metrics.Metrics;
import org.apache.kafka.common.protocol.ApiKeys;
import    org.apache.kafka.common.protocol.Errors;
import org.apache.kafka.common.requests.AbstractRequest;
import org.apache.kafka.common.requests.FindCoordinatorResponse;
import org.apache.kafka.common.requests.HeartbeatRequest;
import org.apache.kafka.common.requests.HeartbeatResponse;
import org.apache.kafka.common.requests.JoinGroupRequest;
import org.apache.kaf ka.common.requests.JoinGroupResponse;
import o  rg.apache.kafka.common.requests.LeaveGroupRequest;
import org.apache.kafka.common.requests.Leav  eGroupResponse;
import org.apache.kafka.common.requests.RequestTestUtils;
import org.apache.kafka.common.requests.SyncGroupRequest;  
impo   rt org.apache.kafka.common.re quests.SyncGroupResponse;
import org.apache.kafka.common.utils.LogContext;
import    org.apache.kafka.common.utils.MockTime;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.commo  n.utils.Timer;
import org.apache.kafka.common.utils.Utils;
import org.apache.kafka.te      st.TestUtils;
import org.junit.jupi     ter.api.AfterEach;
import org.junit.ju   piter.api.Test;

import java.nio.ByteBuff    er;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
imp   ort java.util.Map;
import java.util.Optional;
i     mpo     rt java.util.concurrent.  CountDownLatch;
import java.util.concurrent.ExecutorSer  vice;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Ti   meUnit;
import java.util.concurrent.atomic.Atom  icBoolean;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api .Assertions.assertEquals;
import static org.junit.   jupiter.api.Assertions.assertFalse;
i  mport static org.junit.jupiter.api.A       ssertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import stati   c org.junit    .jupiter.api.Assertion    s.assertNotSame;
import static org.junit .jupiter.api.Assertions.assertSa  me;
import  static org.ju  nit.jupiter.ap  i.Assertions.assertThrow   s;
import static org.junit.jupiter.api.Asse     rt ion   s.assertTrue;
import s    t  atic  org.ju nit.ju     piter.api.Assertions.fail;

public class Abstr     actCoordinatorTest {
       private static final Byte      Buffer EMPT   Y_DATA =  By    teBuffer.wrap(new by  te[0]);
    private static final int REBA     LANCE_TIMEOU  T_MS = 6 00    00;
    private st      atic final int SESSION_TIMEOUT   _MS  = 10000;   
    pr  ivate static final int HEARTBEAT_INTERVAL_MS = 30    00;
    private static fina   l int RET         RY_BACKOFF_MS =   100  ;
          priva     te stati     c final int RETRY   _BACKOFF      _MAX_MS =      1000;
    private static final int REQUEST_TIMEOUT_MS =     40000;
    priva  te sta  tic final Stri            ng GROUP_ID = "d   ummy-group";
    p      rivate static fina  l String ME   TRI     C_GROUP_PREFIX = "consumer";
    priv       ate static final   S tring        PROT OCOL _TYPE = "dummy";
    private static final String PROTOCOL_NAME = "d ummy  -subproto   c   ol";

        pr  i    vat    e Node nod    e;
    pr   ivate Metrics metric  s      ;
      private Mock Time mockTime;
     p       rivate     Node     co        ord    inatorNode;
    pr       ivate MockCli  ent mockClient;
         private DummyCoordi nator coordinator;
    private Consum  erNet  work     Cl       ie  nt consumerCl     i    ent    ;

    private final  String mem   b      erId = "memberId"; 
    pri       vate final String leade  rId =     "leaderI   d";
    private f i    nal int defaul    tGeneration = -1;

    @A fterE ach
    public void closeCoordinator(  ) {
              Utils.closeQuietly(coordinator, "  close coordinat  or");
        Utils.closeQuietly(consum    erCli     e   nt, "close cons   umer client");
    }
  
               private  void setu   pC    oordinator() {
        setupCoordin       ato  r(R    ETRY_BACK  OFF_MS, RETRY_BACKOFF_MA    X_MS, REBAL    ANCE_TIMEOU    T_MS,
            Optional.empty());
     }

    pr    ivate void    s    etu    pCoordinato  r(int retr  yBackoffMs, int retryBackoffMaxM    s) {
        setupCoo rdin  ator(retryBacko   ffMs, re  tryBackoffMaxMs, R  EBALANCE_TIM           EOU  T_M    S,
               Opt   iona     l .emp   ty());
             }

           pr          ivate void setupC   oord          inator(int    r        etryBackoffMs, in           t re    tryBac            koffMaxMs    , int rebalance        Tim  eou  tMs, O   ptio    nal<   Str      in       g>   grou     pInst  an   ceId) {
               Lo  gCo    ntext log C           on     text = new Log   C     ont   ext     ();
                         t        his.mockT   i    m e       =      new MockTi  me(   );
        C  onsum   erMetadata metadata              = new C                onsum  erMetadata(r   e  tr   yBack    offMs,    re  tryBac    koffMa  xMs,          60     *   60      * 1000L,
                                                               false, false, new  SubscriptionState(lo  gCon tex     t, O   ffsetResetS       tr ategy.EARLIEST),  
                                             log  Co       n     te  xt ,    new     Cl   us   t   erResou r     ce        Lis     te       ner s());

        this.mockC   li   en  t             =  new MockCl         ie nt(     moc   kTim    e,   metadata);
                   th is.co     nsumer    Client         = new    C    onsumer Networ k   Clien  t(lo  g          C  ontex    t,
                                                                                              moc  kClie       nt,
                                                                                              meta      data,
                                                                                                                   m  o         ck        Time,
                                                                                                                                               retr    yBack  of    fMs,
                                                                                                                                RE  QUEST_T      IMEOUT_MS,
                                                                                                                                                       HE  A     R  TBEAT_INTE      RVAL         _M S)    ;
              met  r       ics = new  M  e      trics(moc                k     Time);

              mockClient.       u   pdate   Meta    data(R   eque stTes     tU         til   s.m       e          t   adataU     p                 da     t    eWith  (1   ,   e     m     pt        yMap(        )));
                 this    .nod       e = me         tadata.    fet ch()  .n      ode  s()  .g    et     (      0);
          thi   s.coordin     at         orNode =          new  Node(In            tege  r.MAX_VAL     UE - nod e.id(), node.hos     t(),          node.por           t());

        G           r   oupRebalan ceConfig reba      l  an    ceConfig = n e  w GroupRebala     nceConf      i        g(SE SSION_TIMEOUT_M      S ,        
                                                                                                                                  rebalanc  eTime  o    ut  Ms,
                                                                                                                                       HE        A   RTBEAT_I              NT    ERVAL_M      S,
                                                                                                                          GR   OUP  _ID, 
                                                                                                                            groupI nstanceId  ,
                                                                                                                       retr yBa c koffM  s,
                                                                                                       retr       yBackof fMaxMs,
                                                                                                       !gro   upInstanceId.is    Present());
                  this.c   oordinator = new     DummyCoordinator(rebalanceConfig,
                                                       consumerCli        ent,
                                                             metrics,
                                                mockTime);
    }

    private void joinGroup()           {
               mockClient.prepareR        esponse(group   CoordinatorResponse(node, Errors.NONE));

         fi     nal int generation = 1;

        mockClient.pr epareResponse(joi     nGrou  pFollowe  rResponse(ge   neration, memb    er    Id, Join  GroupRequ   est.UNKNOWN_MEMBER_ID, Errors.NONE))   ;
                 m ockClient.prepareResponse(syncGroupRe  spons    e(Errors.NONE ));

        coordinator.ensureActiv   eGroup   ();
      }

     @Test
    public     void testMetrics     () {
           setupCoordinator(     );

          ass   er  tNotNull(getMetric("heartbeat-resp onse-time-max"));
          assertNo   tNull(getMe  t    ric("heartbeat-rate"));
                     ass    ertNotNull(getM  etric("heartbeat-tot al"))          ;
           assertNotNull(ge  tMetric("las    t-heartbeat-seconds-ago"))   ;
             assertNotNull(getMetric("join-time-avg"));
         assertNotNull(getMetric("join-time-max"));
        assertNot  Null(getMe     tric("join-rate"));
             ass    ertNotNull(getMetric("join-total"));
        asser          tNot   Null(    getMetric("s   ync-time-avg"));
            assert   NotNull(getMetric  ("s   ync-time-max"));
          assertNotNull(getMetric("sync-rate"));
        assertNotNull(g    e   tMetric("sync-total"));
          assertNotNull(getM          etri      c("rebalance-latency-avg"));
        assertNotNull(getMet     ric("rebalance-la tency-max")   );
        assertNotNul   l(getMetric("rebalance-late   ncy-total")  );
                asser    tNotNull(getMetric("  re balance-rate-per-hour"));
            asse  rtNotNull(ge    tMetri c("rebal   ance-tot  al"));
          asse   rtNotNull(getMet       r      ic("    la     st-rebalance-second s-ago"      ));
            asse  rt   NotNull(get     Metric("failed-    rebalance-r     ate-   pe           r-hour"));
              assertNotNull(getM    etric("failed-r   ebalance-total"));

            metrics.sensor("heartbeat-latency").record(1.0d);
        metrics.sensor("heartbeat-lat    ency").record(6.0d);
        met    rics.sens    or("heartbeat-latency").record(2.0d);

        assertEquals(6.0d, get  Metric("heartbeat     -re   sponse-ti  me-max       ").metri  cV     alue());
           asser    tEquals(0.1d, g     etMe  t ri  c("heartbeat-rate") .met      ri  cValue());
         assertEquals(3.0d, get     Met   ric("heart  beat-total").met         ricValue());

        assertEquals(-1.  0d, g   etMetric("la  st-heartbeat-seconds-a  g  o").metri  cVal   ue());
              c         oordinator.heartbeat   () .se ntHeartbeat(moc     kTime.milliseconds());
        assertEquals(0.0d, g etMetric("last    -heartbeat-s     econd   s-ago").metricValue());  
        moc   kTime.    sleep     (10 * 1000L);
        assertEqua       ls(10.0d, getMetric("last-hea rtbeat -seconds-a g   o").metricValue());

             metrics.sensor   ("j       oin-latency").record(1.0d);
        metri           cs.sensor("join-latency").re  cord(6.0d);
        metr   ic  s.se  nsor("join-latency").reco  rd(2.0d);

            asser  tEquals(3.0d, getMetric("join-time-av     g").metricV   alue());
        asse   rtEq     u     als(6.0d, getMetric("    join-time  -max").metricValue());
           assertEquals(0.1d, getMetric("join-r  ate").metricValue());
           as    sertEquals(3.0d,      getMetric("   join-total").metricValue());

              me    t   rics.sensor("sync-laten cy").record(1.0d);
          metrics. sensor("sync-laten   cy").record(6.0d);
        me   trics.sensor("sync-latenc    y").record(2  .0d);

            assertEquals(3.0     d, getMetric(     "sync-  time-avg").me      tri  cValue ());   
        a          sse rtEquals(6.0d,     getMetric("sync  -ti   me    -max").metricValue   ()  )       ;
           asse  rtEquals(0.1d, get         Metric("sync-rate").met   ricVa lu  e());
        assertEquals(3.0   d, getM e     tric(  "sync-total").me tricValue());
     
           m e tric   s.sensor("r   ebalance-latency").record(1.0   d);
          metric   s.s  ensor("   rebal ance   -latency").recor      d(               6.0d);
        metrics.sensor( "rebalance-lat   ency" ).record(2.0       d);    

        assertEquals(3.         0d,          getMetric("rebalan     ce-latency-avg").      metricValue()       );
        assertEquals(6.0d, getMetric ("rebalance-latency-max").metri  cValue());
           asse    rtEquals(9.0d, getMetric("rebalanc e-la    tency-total").met   ricValue    ());
         as        sertEquals(3  6   0.0d,      getMet ric("rebalance-rat  e-per-hour").metricValue());
            assertEquals(3         .0   d, getMetric("rebala nce-total"         )           .metricValue());
 
        metrics.sen     sor("failed-rebalance").r ecord(1.0     d);
        m         etrics.sensor("fa   iled-rebalance")      .reco   rd(6        .0d);
        metrics.sensor("failed-rebalance").reco   rd(2.0d);

        assertE    qu  al   s(360.0d,           getMetric("failed-reb alance-rate-p  er-h      our").metr   icV             alue(    ));
          asser tEquals(3.0d, getMetric("failed-rebal  ance-t ot al").m       etricValue(  ));

            ass  ertEquals(-1.0   d,      getM             etri  c("last-reba     lance-    se    conds-a  go").metricValue());
                coordinator.setL  astR  e        balance    Tim     e     (mockTime.        mi   llise      co     nds());
        assertEquals(0     .0d, getMetric   ("las   t-rebala    nce-secon   ds-ago").metricValue    ());
            mockTime.slee     p(10 *  1000L);
                 as    sertEquals(  10.0d, g      etMetri   c("last-reba     lance -se   con   ds-ago").metricVa   lue())        ;     
          }
  
    private KafkaMetric ge   tMetric(fi   nal String   name)        {
                re   t urn metri  cs.metrics().get(metri   cs.metric          Name        (n am      e     ,      "consu   mer-c      oordina   tor-metrics"));
             }

       @Test
    public void tes     tCo     ordinatorDiscoveryExponentialBackoff() {
        // With exponential  ba  ckoff,     we wil       l get retries at   10,    20, 40, 80, 100 ms   (    with           jitter)
        int  shortR      etr yBackoffMs = 10;
        int s      hortRetryBackoffMaxMs = 1 00;
        setupCo           or     dina   tor(shortR  etr        y    BackoffMs, shortRetryBackoffMa  xM     s);

           for (    int i = 0; i   < 5; i++) {
                mock Client.prepar     eResponse       (groupCoo rdin  atorResponse(node, Errors    .NONE));
        }

            //         cut out th e coord     i    n    ato   r  for 100 milli  seconds t     o simulate a disconne  ct      .
        // af  ter back      ing off, we     should be abl     e to conn       ect.
        mockCli     ent.b ackoff(coordinatorNode, 10 0L);         
    
           long initia  lTime = mockTime.millisec         onds();
          coord   inat     or.ensureCoordinatorRead y(moc         kTime.timer(Long.MA   X_VALUE));
        lon g endTime = mock Ti  me   .milliseconds   (    );

        long     lowerBoundBack   off     Ms = 0;
        l  ong u     pperBoundBackoffMs = 0    ;
           for (int i = 0; i <      4; i++) {
            lowerBoundBa     ckoffMs += (long) (sho   rtRetryBackoffMs *      Math.pow(Com  monClientConfigs.RETRY_B   ACKOFF_EXP_BASE, i) * (1 - CommonClientConfig s.RET        RY_BACKOFF_JITTER   )       );
            u   pperBoundBa   cko    f   fMs += (lon g          ) (shortRetryBackoffMs * Math.po     w(CommonClient          Configs.RETRY_BACKOFF _EXP_BASE, i)     * (1 + CommonClientConfigs.R     ETRY  _BACKOF  F_JITTER));
        }

            lo ng timeEl    apsed = endTime - in   itialTime;
        assertTrue(timeElapsed >= l  ower    BoundBackoffMs);
                     assertTrue(timeElapsed <=    upperBou ndBackoffM  s + shortRetryBackoffM     s);
        }

    @Te      st
    publi  c voi   d testWakeupFromE     n  sureC    oordinatorRea      dy  () {
        setupCoordinato           r()     ;   
 
         consumerCl     ient.wakeup();
    
        // No wakeup should occur f      rom the asyn  c variation    .
        coo   rdinator.           en  sureCoor  dinatorReadyAsync();

        //     But     should   wakeup in      sync vari   ation ev    en if timer is 0.
        assertThrows(WakeupExcep tio      n.class, () -> {
            coordinator  .ensureCoordinator Ready(mockTime.tim    er   (0))        ;
                });
          }

    @Test
    public void testTimeoutAndRetryJoinGrou    pIfNeeded() throws Exce     ption {       
                setupC   oordinat      or();
            mo ckClient.prepareResponse(groupCoordin  atorResponse(n       ode, Errors.NONE));
        coordinator.ensureCoordinatorReady(mockTime.time    r           (0));
  
        Execut     o   rService ex ecutor = Executors.newFixedThr    eadPool(1);
              tr        y {
            Timer f     irstAttemptTimer = mockTime.ti  mer (REQUEST_TIMEOUT_MS);
                Futur           e<Boolea  n> firstAtt  empt = executor.submit((   ) -> coordinator.joinGroupIfNeeded       (fir  stAttempt        Timer));

            mockT   ime.sleep(REQUEST_TIMEOUT_MS);
               assertFa  lse(firs   tAtt    empt.g     et());
                   as    se    rtTrue(consumerClient.hasPendi  ngReque st      s(coord  inatorNode)    );

                 mockClient.respond(   joinGroupFollower  Res   po   nse(             1,   me  mber   I d, leaderId,   Errors.NONE));   
                 mockClien t.prepar  eR    espons  e(syncGroupResponse(  Errors.NONE));

               Timer secondAtte              mp    tT    imer = mockTime.  t   imer(REQUES    T_TIMEOU      T_MS); 
            Future<Boolean> secondAt tempt = ex  e   cutor.su  bmit(() -> coordi   nator.joinGro     upIfNe  eded(secondA   ttemptTimer));

                   assertTrue(secondAtt  empt.get());
             } finally {
            execut   or   .shutdownNow()    ;
               executor.a waitT      ermination(1000   , TimeUnit.         MILLISE  COND   S);    
           }
    }

       @Test
      public void testGrou              pMaxSizeExceptionIsFat     al() {
        se  tupCoordinator() ;
        mo     ckClient.p   repareResponse(gro  upCoordinatorResponse(node   ,          Errors.    NONE));
        c oordinat  or.ensur    eCoordinator  Ready(moc         kTime.timer(0));   

        m  ockClient    .pr    e     par           eResp     onse(joinGroupFo   llowerRes     ponse(de    faultGeneratio    n, m         emberId, Joi   nGro upRequest.UNKNOWN_MEMBER_ID, Errors.GROUP_M    AX_SIZE_REACHE    D    ));

        RequestFut  ure<ByteBuff er> future   = coord         inator.sen     dJ     oinGroupReque   st(   );
        a      ssertTru     e(consumerClient.poll(fut     ure, moc     kTime  .timer(REQUEST_TIMEOUT_MS)));
        asse   rt   InstanceOf(future.exception().getClass(), Errors.GRO   UP_M    AX_SIZE_REACHED.excepti    on());
        ass   ertFalse(future.isRetri       able());   
       }

           @Test
    public void testJoinGroupRe    questTimeout() {
        setupCoordinato    r(RETRY_BACKOFF_MS, RETRY_    BACKOFF_MA   X_MS, RE     BALANCE_TIMEOUT_  MS,
                Opt     ional.empty());
              mo   ckClient.prepareResp      on         s  e(groupCoordinat   orResponse(node, Errors.N       ONE     ));     
        coordinator.ensure   Coo   rdinatorReady(mockTim  e.timer(0))   ;

        RequestFuture<ByteBuffer> future = coordina       tor.sendJ   o     i  nGrou    pRe   quest();

        moc      kTime.sleep(R  EQUEST_TI MEOUT_MS    + 1);
        assert     False(consumerCli ent.      poll(future, mockTime.time  r(0)));

          mockTime.sleep(REBALANCE_TIMEOU     T_MS       - REQUEST_TI      MEOUT_MS + AbstractCoordinator.JOIN_GROUP_TIMEOU T_LAPSE    );
         assertTrue(cons    umerClient.poll(future,  mockTime.timer(0)));
        assertInstan ceOf(DisconnectExceptio n .class    ,  future.exception   ());
       }

    @Test
    public void testJo    inGroupRequestTimeoutLowerBoundedB     yDefaultRequestTimeout(  ) {
        int rebalanceTi   meou      tMs =         REQ UEST_ TIMEOUT_MS - 1000 0;
          setupCoordinator(R       ET RY_BACKOFF_MS, RETRY_BACKOFF     _MAX_MS, rebala nc eTimeoutMs, Opti    onal.empty()    );
                       mockClient.prepa  reRespon    se(groupCoordinatorResp onse(node, Errors.    NONE));
           coor   dinator.ensu   reCoor   dinatorReady(mockTime.timer(0));

               RequestFuture<Byt     e   Buffer> f    uture = co      ordinator.sendJoinGroup     Request();

        l o ng   expectedRequestDeadline = mockTime.millise con ds       () + REQUEST_TIMEOUT_M  S;     
        m    ockTime.sl     eep(rebalanceTimeoutMs + AbstractCoordinator.JOIN_GROUP _TIMEO   UT_LAPSE + 1);
          as        sertFa          lse(con   sumerClien   t.poll(future,     mockTime.timer(0)));

             mockTime.sleep(expectedRequestDe     a    dli   ne - mockTime.milliseconds() + 1);
            assertTrue(consumer     Client.poll(future, mockTim    e.tim   e     r(0)));
           assertInstanceOf(DisconnectE   xceptio  n     .class, future.exception());
    }
  
    @Test
    public void te    stJoinGroupRequestMaxTimeout()  {
          // Ensure we can hand le       the ma    xi     mum allowed rebalance timeou      t

           setup  Coordinat    or (RE  TRY_BACKOFF_MS, RETRY_BACK OF   F_MAX_   M S, Integer.MAX_VAL   UE,
            Optional.empty());
        mockClient.prepar   e    R   es     ponse(groupCoordinatorResponse(node, Err   ors.   NONE));
        coordinato r.ensureCoord   inato     rR  eady(mockTime.timer(    0));

           Request      F    uture<ByteBuffer> future = coordin  ator.sendJoin         Grou   pRequ est();
          asser    tFalse(consum  erC  lient.      poll(future, m ockTime.timer(0) ));   

        mockTime.sleep(Int      e ger.MAX_VALUE +      1L);
                a    sser    tTrue(consumerClient.poll(fut  ure, moc  kTim              e.   timer      (0)));
    }

    @Test
    public v   oid testJ oinGroupRequestWi  thMemberIdRequired() {
          setu    p        C o   ord   inator();
        m   o  ck    Cli  ent.prepareResponse(g   roupCoordinatorResponse(node, Errors.NONE));
          coordin ator.ensur      eCoordinatorRea dy(mo     ck    T   ime.time r(0));

        mockCli              ent.prepa reRes         ponse(joinGroupFo    llowerRes   po  nse(defaultGe   nerati     on, member    Id,     Jo inGroupRequest.UN        KNOWN_MEMBER_ID, Erro   rs.MEM BE      R_ID_R  EQU  IRED));

        m      ockClient   .prepar   eR    esponse(     body -> {
            if  (!(body instanceof   JoinGroupR     e      quest)) {
                return false;     
                     }  
                 Joi nGroupRequest joinGroupRequest = (Jo    inGro upRequest) body;
                  retur   n joinGroupRequest.data().memberId()  .equals(memb      erId);
        }, joinGroupRes  ponse(E     rrors.UNKNOWN_        ME    MBER_   ID))  ;

        RequestFu   tur    e     <Byt     e   Buffer> future =    coordinator.sendJoinGroupRequ    est();
           assertT      r     ue(consumerClient.poll(future, moc    kTi     me.timer(REQ  UES   T_TIMEO U    T_MS)));
        as     sertEquals(Errors.MEMBER_ID_REQUIRED.me    ss age()   , futur      e.exception().getMes  sage());
            assertTrue(coordinator.rejoinNee       dedOrPending()  );
        assert    True    (coordin          ato  r.hasVal i   dMemberId   ())         ;
         ass   ert  True(   coordinator.hasM atchingGeneratio      nI d(defaultGe    neration));
        future =  coor  di nator.sendJoinGroupR   equest();
        assertTrue(consum  erCli  ent.     poll(future, mockTime.t   imer(REBALANCE_TIMEO    UT_MS)));
              }

      @Test
           publ         ic void  testJoinGroupRequestW   ithFencedInstanceIdExceptio   n() {
        setupCoo   rd     inator();
            mockC  lie   nt.prepareResponse   (group   Coordinato  rResponse(node,     Error       s.NONE));
                 coordina   to  r.ensureCoor dina torReady(moc    kTi me.timer(0));

        mock   Clien  t.p    repa   reResponse(joinGroupFol  lowerResponse(defa  ult    Generation, memberId, JoinGroupR    equest.UNKNOWN_ MEMBER_ID, Errors.FENCED_INSTANCE_ID     ));

              RequestFuture<B  yteBuffer>     future = coordinato   r.sendJ   oinGroup    Request();
        assertTrue(consumerC    lient.poll(futur      e   , mockTime.t       imer(REQUES  T_TIMEOUT_        M           S     )))    ;
        assertEquals(Errors.FENCED_      INSTANCE_ID.message(),  future.e  xcep  tion().getMessage(   ));
                    // Make sure t      he excepti on is fatal.
                assertFals       e(  future.isRetriable(  ))    ;
                }

    @Test
    public v  o    id testJoinGroupP    ro    toc    olTypeAndName() {
            f  inal String wrongProtocolType =    "wrong-type";
        final        String wrongProtocolName = "wrong-n ame";
  
        // No Protocol Type in      both JoinG   roup an    d SyncGroup responses
         a      ssertTrue(joi           nGr       ou    pWithPr otocol    Ty  peAndName(nul l, n   ul    l      , null));  
  
        //      Proto         co     l Type in both JoinG     roup and Sy   ncGroup   resp  onses
              assert True(joinGroup WithProtocolTypeAndName       (PRO   TO           COL_TYPE,    PR       O    TOCOL_TY     PE, PR  OTOCOL_NAME  ));

            // Wrong protocol   t   y pe    in    the   JoinGroup     Response
                 ass  ertThr ows(Inconsist   en tGr     oupP   rotocolE  xception.cla     ss,
               ()                -> joi    nGroupWithPro                toc     olTypeAndNam    e("wrong",     null, null));

        // C      orrect   protocol type i   n the Jo     inGroupRespo   nse
        // Wro n     g proto  col type in th   e    Syn         cGroupResponse
        // Correct       protocol na  me          in t  he SyncGroupResp   o nse
        as     sertTh ro   ws(In  cons  istentGro upProto   c     olE    xcep     tion.class,
             () -> join  GroupWithProtocolTypeAndName(P           ROTOCOL   _TYPE, w     ron    gProtocolType, PROTOCOL_NAME) );

           // Cor     r   ect protocol typ  e   in the Jo i   n  G   roupResponse
        // Correct protocol type  in the SyncGroup Response
        // Wrong p rot      ocol    name in the SyncGroup       Response
            ass    ert          Throws    (InconsistentGroupProtocol    Excepti on.class,
                     ()    -  > joinGroupWithProtocolTypeAn  dName(PR O    TOCOL_TYP     E     , PR  OTOCOL_TYPE, w     ron      gProtocolN   ame));
    }

    @Test
           public void testReta  inMembe   rI   dAfterJoinG roupDisconnect() {
            setupCoordinator();

        String membe    r         Id = "mem       berId";
        int generation = 5;

             // Reb     alance once to initialize the gene ratio     n      and memberId
        mockClient.prepa reResponse(gr   oupCoordinatorResponse(node, Errors.NONE));
        exp  ectJoinGroup("  "          , generation,     memb erI        d);
        expectSync    Gro   up(gen   er  ation, member     Id);   
        ensureActiveGroup(generat  ion,  m    em     ber Id);  

                    //    Force a      r    ebalanc    e
                  c     oordinator.requestRejoin("Manua   l  te     st tri   gger");
          as      sertTrue      (coordinator.rejoinNeeded      OrPending());

                 // Disc onnec      t during the     JoinGro up and ens     ure tha         t t  he retry pr     eserves the m   ember     Id
              int rejoined    G  en     erati     on = 1     0;
              expectDisconnectIn  JoinGroup(m e    mberId)        ;
           mockClient.prepareResponse(g   roupCoordi  n  atorResponse(node, Error    s.NONE));   
        expectJoinGr    oup     (membe rId, rejoinedGe      neration, memberId);
           exp       ectS  yncG      roup(rejoi       n          edGeneration, me             mb   erId);
        ensu          reActiveGroup(rejoi  nedGeneration, mem    berId);
                      }

    @Test
    public    void t     estRetain Mem    berIdAfterSyncGroupDi s    connect() {
              setupCoo  rdinator();

            String mem           be  rI            d    = "memberId";
        int         genera t    ion = 5;

        // Re   balanc e once to initialize the gene   rati  on and me             mberId
                     mo  ckClient.  prepare Response(groupC   oordinatorR        esponse(no   de          , Err           ors.         N      ONE));
        expectJoi   nGroup( "", generation, memberId);
           expectSyn         cGrou  p(gene        ration, memb    erId);
                            ensureActiveG   roup(   generati on, memberId);

                     // F orce a rebal  ance
        coordinator.reque   stRejoin           ("Manual test trigger    ");
                  ass ertTr  ue(coordinato    r.rejoinNeededOrPending());

        // Disco  nnect during the SyncGroup and ensure     that the ret ry preserve   s     the me   mbe     r I   d
             in  t r    e   joine        dGeneration =     10;
        expect    JoinGrou     p(me  mberId, r  ejoined  Ge   neration,          memberId);
           expectDisconnec  tInSyncG    roup(rej  oinedGene          ratio n    ,    me   mberId);
        mock          Client.prepareResponse(  groupCoordinatorRe spo          nse(n     ode, E  rrors.    NONE));
   
            // Note   that the       con      sumer always sta   rts fro   m Joi  n  Group a     ft  er a failed rebalance
          ex     pectJoinGroup(       memb erId, rejoinedGene     ra    tion    , membe         rId)   ;
        e    xpe ct     SyncGroup(rej  oinedGeneration, memberId);
        ensu    r   eAct iveGrou        p(rejoin   edGenera     ti      on, memberId                   );
    }

    @Test
       public    void testRejoinReason() {
        set   upCoordi  nator();
    
           String me    mberId = "     membe    rId";
             int generation = 5;

          // test i    nitial re   ason 
                   moc   kClient.p    repareRes    pon   se(groupCoo rdinatorRespon       s e(node, E     rrors.NONE));
        ex    pectJoinGroup("",        "", generation, me   mberId);

        // successful sync gr  oup re     sponse s   hould     re  set reason
             expectSyncGroup     (g  eneration, memberId)      ;
        ensu    reActiveG ro  up(generation,   memberId);
        ass      ertEqu    als (    "",   coo  rdinator.re      joi   n       Reason());    

        // force a rebalance
                       ex   pectJoinGroup(membe   rId, "Manua   l      test trigger",    generation, mem berId);
        e    xpectSy   ncGroup(generat  ion, memberId);
             coordinator.request    Re  join("  Manu       al t est trigg     er    ");
           en   s    u  reActive       Group(ge   neration, member Id);    
        assertEquals("", coordina  tor.r  ejoin  Rea   s    on());

        // max group   size reached
            mockClient.pr  e  pareResp  onse(joi    nGroupFollowerResponse(defaultGen           eration, memberI  d, Joi  nGroupR  equest.UNKNOWN_MEMBER     _ID, Errors.GROUP_MA   X_SI  Z  E_RE   ACHED));
               coordinato r.requestRejo    in(      "Manua  l tes    t trigger 2");
        Throwable e = assertTh             rows(GroupMaxSi  ze  ReachedException.class,
                               () -> coordinato r.joinGr   oupIfNeeded(mockTime.timer(10     0L))  );

        // ne          xt joi  n    group re    quest sh  ould con  tain                exception message
        ex      pectJoinGroup(memberId,          Strin       g.format(    "rebalance faile   d  d   ue to %s", e.getC                 lass().get   SimpleNa me()), generation, membe rId);
        expe ctSyncG  roup(generatio n, memberId)    ;
        ensur    eActiveG  roup (ge   nera     tion, memberId      );
             assert         Equals("", coordinator.rejoinReason());

        // check limit length     of     reason field
                fin  al String reas          on        = "Very looooooooooo     ooo      oooooooooo   ooooooooooooooooooooo o   oo     ooooo oooooooooooooooooo   ooo   ooooooooooooooooo      ooooo   oooooooooooooo    ooo       ooooooooooo    oooooo        oooooooooooooooo               o       oooo    oooooooooooong reason       that is 27      1 chara     cters long to make s       ure    t   hat l    en gth limit logi                c handles the sce nario n  icely";
        final String t         ru   ncatedReason = reas  on.substring(0, 255); 
           expec         tJ   oinG     roup(memb    erId,  truncatedRe       ason, generation, mem  berId);
        expectSyn cGrou   p(g   eneration, me     m    berId);
           co   ord  inat   or.req   uestRejoi n(reason);
        ensureActiveGroup(generation, memberId)   ;   
           asse   rtEqu    als("",   co  ordinato          r.rejoinRe ason());
      }
  
    private void     e            nsu  reActiveGroup (
        int      gen  er       ation,
                     String memberId
      ) {  
          coordinator.en   sureA    ctiveGroup()    ;
         assertE qu   als(generation         ,      c      oor         dinator.generation().generationId)   ;
         assertEquals(membe    rId, coordinator.generation().mem    be         r I  d);
        as     sertFalse(coordinat   or.rejoinNe  ededOrPending   ());
           }

                           priva   t        e void expectSync       Group( 
         in     t expected    Gene    rat   ion,
        Str    in g expectedMemberId
    ) { 
                 mockClient.pr  epare     Re     spon se(body ->     {
                   if (!   (bod  y instance   of S   y   nc Gro  upReque   st)) {
                  r   eturn fal    se;
            }
                                        Sync    Gro   upRequ  estDa ta syncGroupRequest = ((Sync     GroupR      equest) body).da      ta();
            return syncGroupRequest.generat ionId() == expectedGe    n   eration
                                       && syncGro u  pReque  st     .memberI    d( ).e           quals(expectedMe mberId)
                     && syncGroupRequest.protocolType().equals(PROTOC OL_TYPE)
                            && s   y    n     cGr      oupRequest.p     r   otocolName().equals(PRO  TOCOL_NAME);
            }  , syn cGroupRes    ponse                           (Errors. N  ONE, PROTOCOL_T    YP     E, PROTOCOL_NAME   ));
          }

    p          rivate void exp   ectD     iscon  nectInSyncG   roup  (
                     int expectedGen  e rati  on,
                         String expectedMember    Id
    ) {
        mockC        lient.prepareR    espon     se (    body -> {
                   if (!(body instanceof SyncGroupRequest)) {
                   return       f alse;
               }
                SyncGro    upRequestData sy  ncGroupRequest          = (       (SyncGrou     pR    eques  t       ) body).data();
                   return syncGrou   pRequest.           generationId() =   =  expec     tedGeneration
                &&  syncG    roupRe qu      e     s    t.memberId().equals(expectedMemberId)
                             && s    yncGroupRequest.    pr  oto  colType(    ).equals(PROTOC    OL_     TYPE)
                 && syncGr   oupRequest.prot     ocol  Name().equals    (PROTOCOL_N      AME);
               }    , null, true);
    }

     pri vate      vo      id e     xpectDisconnectInJo   inGroup(
        St    ring ex    pec   tedMemberId
         ) {   
          mockClie         nt.prepa    reRespons   e(b   ody   -> {
            if (!(body i nstanceof JoinGrou pRequest)) {
                               r        eturn false;     
              }
               J  oinGroupRequestData jo    i  nGrou  pRequest =  ((JoinG     ro       upRe   ques t)    bod                   y).data(  );             
                 return joinGrou   pRequest.memberId().    equals(expectedMemberId)
                               && joinGr  ou    pRequ    est.protocolTy  p  e().equals(PROTOCOL_TYPE);
        }, nu      ll, true);
          }          

    private vo    id expectJoinGroup(
               String     expectedMemberId,
            int respo        nseGenera    tion,    
                       Str   ing responseMemberId
       ) {  
         expect JoinGroup(expectedMember  Id, null,      respon   seGeneration,    response    MemberId);    
      }

       private void expect   J oinGro         up(
        String e   xpectedMemberId,
        Strin    g expec  tedReason ,
          int responseGenerati      o  n   ,
           String    r    e  sponseMemb      erId
    ) {
        Joi  n     Grou  pResp   onse   respon       se   = joinGr  oupFo  llowerRespons e(
                    responseGeneration,
                  res      pons    eMemberId,
            "leaderId",
               Errors.NONE,
             PROTOCOL    _     TYPE
        );

          mockClient.prepare     Respon  se(body       -> {
                if       (!(body instanc            eof Jo  i  nGroupRe  quest)) {
                        return  fa   l se;
              }
                    Jo    inGro  upRequestDa  ta      joinGroupR   equest = ((JoinGroupRequest) body).  data();
                   // abstract coordinat o   r  never sets reason t   o null
            String ac              tualReason = joi  nGroupRequest.   re ason     ();
            boolean isReasonMatchin   g   = ex   pectedReason      =      =          null   || expect e  dR   eason.equals(actualReaso     n);
                 ret       u rn joinG  roupReque s   t.memberId().e     qu    als(  expectedMemberId)
                         && jo    inGr   oupRequest.protocolType (  ).equals(PROTOCOL_TYPE)
                      && is         Re  asonMatching;
        }, response);
      }

    @Test
    public voi   d   testNoGenerationWillNotTriggerProtocolNameCheck() {
               final String      wrongProtocolName    = "wrong-nam  e";

             setupCo  ordinato   r();
             mockClient.      reset();
        mockC     lient.p     re  par    eResponse(  groupCoordinatorResponse(node, Erro          rs.NONE) )     ;
        coo   rdin  ator.ensureCoordinato  rReady(mo  ckT   ime.   timer(0));

             m  ock      Client.p     repar   eRe     sponse(body -> {
                   if   (!(body in      stanceof JoinGroupReques   t)) {
                return   false;       
                }
            JoinGroupRequ            est joinGroupRequ         est  = (Join      Grou  pReques   t) b   ody;        
                           return j          oin    GroupRequest.d  ata().pro    tocol Type   ()  .equals(PR       O TOC  OL_TYPE);
           },  joinGroupFollower      Resp  on  se(defa ultGeneration, memberId     ,
                "m     emberi       d"  ,     Errors.NONE,      PROTO  COL_TYPE)  );

             mo  ckClient.prepa reRespon se(body ->   {
             if (!(bod  y ins  tanceof SyncGroupRe    quest)) {
                    return false ;
                     }
            coordinator.resetGeneration   O  nLeaveGroup();

               S    yncGroupRequest syncGroupReque      st = (SyncGr              oupReq uest) body;
            ret          urn        syncGroupRequest.dat  a().protocolType().e          quals(PROTOCO  L_TYPE)
                                && sy    ncGroupRe               quest.data().protoco         lName().equa         ls(PROT   OCOL_      NAME);
          }    , syncGroupResponse    (Erro rs.           NONE,  PROTOCOL   _TYPE, wro            ngProto  colName));

        // let t    he re           try    to complete succ    essfully to bre  ak out          of     the w  hile loop     
        moc  k Cli  ent.prepare    Resp  onse     (body      -> {
                 i     f (!(body instanceof JoinGroupReq      uest)) {
                         r  eturn false;       
                }
            JoinGroup  Request   j  o   inGroup   Request =  (JoinGr  oupR e        q     u        est) body;
            r     etu      rn join  GroupRequest.data().protocolType(    ).equ    als(PROTOCOL_TYPE)     ;
            },     j    oinGroupFo      llowerResponse(1, mem berId,
                       "memberid"    , Errors.NONE, P ROTOCOL_      TYPE))  ;

             mo              ck  Cl    ient.prepare    Respon     se(body -> {
                     if (      !(b  ody instanceof   SyncGroupRequest)) {
                return false;
                 }

                   Syn    cGroupRequest syncGroupRequest =   (Sy  ncGroupReq uest) body;
            retu  rn syncGrou  pReque     st.da  ta()   .    prot    ocolType().e   qual  s(PRO TO    COL_TYPE)    
                     && syncGroupReque          st.data(). protocolN   ame   ().equals(PROTOCOL_NAME);
               }, s ync  GroupResp   onse(E   rrors     .NONE, P RO      T   O    COL_TYPE, PR            OTOCOL_NAM    E));

        // No    excep     t    ion shall be t    hrow    n as the generation is reset.
        coordin ator.joinGroupI        fNeeded(moc         kTime.t ime   r   (100L));
     }

         private boolean joinG       ro   upWithProtocolTypeAndName(String joinGrou  p  R   esponsePr                 otocolT ype,
                                                                   String syncGrou  pRes    ponseProtocolType,
                                                                            String s   yn   cGroupResp onseProto c     o   lN   a me) {
         setupCoordin               ator();
                 mockClient.res     et()    ;
        mockClient.prepareResponse(groupCoordinat    orResponse(node, Errors.NO     NE));
        coordinator.ensur eCoordinat    orReady(mock   Time.t     imer(0)  )  ;
 
           mockClient.prepareRespon      se(body -> {
                   if (!(bod    y instanceof JoinGroupRequest)) {
                         return false;
                                     }
            JoinGroupRequ   est joinGroupRequest = (  JoinGroupRe quest) b      od y;
                    re    turn jo      inGroupR         eq   u       est    .   data().protocolTyp   e()   .equals    (PROTOCOL_TYPE      );
                      }, jo inGr oupFo  llow  erResponse(def  aultG          eneration, memberId,
                         "member   id", Errors.NONE, joinGroupResponseProtocolTy pe));

              moc     kClient.prepareRe  sponse(body -> {
                if (!(bod   y inst    ance     of Sync       Grou  pRequest)  )     {
                        retu  rn fals      e;
                        }
                     Syn   cGroupRequest syncG     roupR  e      quest = (SyncGr      oupR    equest) body;
                return syncGroupReq   uest.d             at    a().p   rotocolType().eq  uals(PROTOCOL_TYPE)
                   &     & syncGroupRequest.    data().protocolName().   eq   ual    s(PROTOCOL  _NAME   );
        }, syncGro     upResponse(Erro  rs.NO   NE, syncGroupR esponseProto       c  ol  Type  , syncGroupResponse ProtocolName));

        return coo    rdi na   tor.joinGroupIfNeeded(mockTime.timer(5000  L)  );
    }

    @Test        
    pub          lic          void test     SyncGr   oup      RequestWithFencedInstanceIdExcept    ion()    {
        se  tupCoordinator();
           mo          ckClient.pre     pareResponse(group     CoordinatorResponse(node   , Errors.NONE));

           final int generation = -1;

        moc kClient.prepareResponse(join GroupFollowerRes  ponse(generation, memberId, JoinGroupRequest.UNK         NOWN_MEMBER_ID, Errors.NONE));
                moc  kClient.prepareRe     sponse(s    yncGroup Response(Errors.FENCED_INSTANCE_ID));

        assertThrows(      FencedInstanceIdE  x   ception.cla  ss, () -> co   ordinator.ensureAc       tiv   eGrou    p());
       }

    @Test
    pub  lic vo    id testJoinGroupU nkn    ownMemberRes ponseWithO    ldGen er    ation() throws Interrupt  edEx    ception {
        setupCoord    inator();
          joinGroup();

            final AbstractCoordi   nato    r.Generation currGen   = coordinator.generation();

               Reques tFuture<ByteBuffer> futur    e =    coo  rdinator.sendJ   oi      nGroupRequest(  );

            TestUti  ls.wa   i       tFor   Condition(() -> !  mockClient.requ ests().isEmp  ty(), 2000,
                  "T  h             e join-g rou      p request     w   as not    sent   ");

        // change the generation a   fter the join-   group request
        final Abst   ract Coor   dinator.Generation newGen =       new AbstractCoordinator.Gener    ation(
                    currGe   n.gen   erationId,
                    currGen.mem  berId + "-new",
                currGen.pro   tocolN    ame    );
        coord     inator.s             etNewGenera     tion(newGen)  ;

            mockClient.r   espond(joinG roupFoll  owerRes  ponse(     cur rG en.generationId + 1, memberId, JoinGroupRequest      .UNKNOW     N_M  EMB  ER_ID, E rrors.UNK  NOWN_MEMBER_ID));

        ass ertTrue(consumer        Cl    ient.    poll(   future, mockTime.     timer(REQUEST   _TIMEO   UT  _MS)));
         asser    tInstanceOf(future.e   xception().getC  lass(),      Err     ors.UNKNOWN_MEMBER_ID.ex     ception());

        // the ge    neratio   n should not be        r        eset
        asser tE   quals(newGen, coord  inator.generation(  ));
    }

       @T  est
        p             ublic vo id testSyncGroupUnknownMemberR     esponseWithOldGene  ration() throws                   Interrupte       dExc    ept   i    on {
        set      upCoordinator();
        joinGroup();

        f   inal Abst      ract  Coordinator.Generation   currG      en = coordi      nator.gen    eration();

                coordinator.setNewS ta   te(A bst          ractCoordinator.Me     mbe rS tate.PREPARING _R        EBALANCE  );
        Re    ques     tFu   ture<B   yte  Buffer> futur e    = coor   dinator.s     endJoinGro     upReques    t  ();

               TestUtils.waitForCond ition(()    -> { 
               consumerClien    t  .poll(mockTime.timer(RE     QUES        T_TIM   EOUT_       MS  ));
               ret    urn !mockClient.requests   ().isEmp  ty();
        }, 2000,
                 "The join-gro  u  p request was not sent");       

            mockClient.respond(joi   nGroupFollowerResponse      (currGen.generatio  nId,     memberId, JoinGroupReques t.UN  KNOW    N_MEMBER_ID   , Errors.NONE));
        assertTrue(mockClient  .   re     ques     ts().isEmpty());

                  TestUtil    s.waitForCondition(() -> { 
                                   consumerClient.poll(mockTime        .timer(REQUEST_TIMEOUT_MS)    );
                     return !mockClient.requests().isE     mpty();
              }, 2000,
            "The s  ync-group request was not  sent");

            // c    hange the   generation after the sync-group request
        fina   l Abst             ractCoordina      tor.Genera    tion ne     wG    en = new AbstractCoord          inator.Generat    i    on  (     
                                currGen.generationId,
                    currGen.memberI d + "-new",
                   currG      en.protocolNa  me);
        coordinator.setNewGeneration     (newGen);

        m      ockClient. respond(sy   ncGro  upResponse(Errors.U       NKNOWN_MEMBER_ID));
             assertT     rue(consu    merClient.p  oll(fu     t     ure, mockTime.tim   er(REQUEST_TIM   EOUT_MS)));
        ass        ertI   nsta  nceOf(fu     ture.excep    tion().ge      tC lass(), Errors.UNKNOWN_MEMBER_ID.exception());

         // the            ge   neration    should      n ot be  re set  
           assertEq       u       als (newGe   n       , coordinator.generatio  n());   
    }

    @Test
         public void testSync   GroupI  lle   galGenerationR  espon        s    e              W ithOldGeneration() throws Interrupt            ed        Exc   epti on {
           set   upCoordinator();
          joinGroup();

        final AbstractC    oordinator.Generati     on curr   Gen = coordinator.        generation();

        coordinat   or.setNewState(AbstractCoordinator.MemberState.PREPARING_    REBALANCE);
        Re   q        uestFutur    e<B   yteBuffer> futur    e      = coordinator.sendJoinGro     upRequest();

                   TestUti ls.waitForCondition(() -> {
            consumerCli  e              nt.poll(mockTi  me.timer(REQUE         ST_TIME      OUT_MS))  ;
            return !mockClient.reque  sts().i sEm    pty    ();       
         },         200  0,
                                        "The join-group   req       uest was not s        e     nt");

          mockClient.respond(joinGro    upFollower    Respon   se(  c u rrGen.gene         rationId,       memb  erId, JoinGroupReque      st.UNKNOWN_MEMBER_ID, Errors.NONE       ));
         asse   rtT  rue(mockClient.req   uests().isEmpty());

        TestUt               il     s.waitForCon   dition(() -> {
            con sume    rClient.p          oll(moc  kT  ime.timer(REQUEST_TIMEOUT_MS));
              return      !mockClie     nt   .r  equ ests().isE   mpty();
              }   , 2000,
               "The  sync-group request      was not sent");

        // c  hange the g   eneration after the sync-group requ    est
        final Abstr   actCoordinator.Generat    ion newGen = new AbstractC oordinator.   G eneration(
                  currGen.generati              o  nId,
                    currGen.memberI    d + "-new",    
                  currGe  n.protocolNam e);
        coordinator.setNewGeneration(newGen);

        mo    ckCl ient .r espond(  sync              GroupResponse(Errors.ILLEGAL_GEN    ERATION));
        assertTrue(consumerCli    ent.poll(fut  ure, mockTime.tim    e   r(REQUEST_TIM     EOUT_    MS)));
        asser  tInst       an  ceOf(fu   ture.exception   ().getClass(), Errors.ILLEGAL_GENERATION.exception());

          //   the g  eneration should no     t be reset
            asse    r    t      Equals(    newGen, coord         inator.ge     neration());
    }

    @Test
    public v    o  id testHeartbeatSentWhenCompletingR   ebalan       ce() thr      o                ws Ex     ception {
          se      tupCoordinator();
         joi   nGroup();

        f       inal AbstractCoord   i nator.Gener  a  tio  n cur    rGen = co  ord  ina     tor.generation();

           coor       d   inator .   setNewSt         ate(    Abst           ractCoord   inator.Me  mbe rState .COMPLETING_REBA  L ANCE   );    

               // th e he   artbe      at shoul d b  e sent out    during      a reb     al     ance
                         moc  k    Time.sle    ep(HEART    BEAT_INTERVAL_M  S);
                       TestUt            i  ls.waitForCondi     tion(() -> !mockClient .requests().isEmpty(), 2000,
                   "The heartbeat request was not sent")        ;  
            asser    tTru  e(coordi   nato  r.hea  rtbe   at(  ).hasI  nflight());
        
          mockCli   ent.res  p   ond(h  eartbeatResponse(Erro   rs.REBALANCE_   IN_PROGR     ESS));
        assertEquals(currGen, coordi  nat or.generation());
     }

    @Te   st
      public void testHeartbeatIllegalGen  erationRes    p    onseW        i    thOldG  en   eration( ) thr      ows Interrupte   dException {
        setupCoordinator();
          joinGroup();   

                 fina  l AbstractCoordinator.Generation currGen = coordinator.generatio n();

         //     let the heartbeat thread sen d out a req        uest
         mockTime.sleep(HE      ARTBEA  T_INT     ERVAL_MS);

        TestUtils        .wa        itForCo     n  dition(()      -> !mo     ckClient.r        e  quests().isEmpty(), 200 0,
                     "The hea   rtbeat reque  s t     was not sent");
              assertTrue(coordi   n ator.heartbeat   ().ha   sInflight());

         // cha  ng      e the generat    ion
         final       A  b   stractCoordinator.Gene  rat          ion newGen    = new     Abstract    Coordinato       r.G    eneration(
                        currGen .generat  ionId + 1,
                                        currGen.memberId,
             currGen.protocolName);
        coordina tor.setNe    wG   en   eration    (newGen);

                mockClient.respond(hea    rtbeat       Response(Errors. ILLEGA     L_G    ENERATION));

          // the heart b  eat error     co  de should b    e ignored
            TestUti  ls  .waitForCondition(() -> {
            coordinator.pollHe     artbeat(  mo   ckTime   .    milli         seconds());
              return !coordi  nator.heartbeat().ha   sI        nfli  g     h    t();
        }, 2        000,
            "The heart  beat respons        e was not received");
        
         // the     generation should    not   be  reset   
        asser     tEquals(ne    wGen,        coordinator.     gene     ration(      )   );
                }

      @Test
     publi    c void testHeartbeatUn  knownMemb    erRespo    nseWithOldGeneration() throws Interr    upt edException {
            setu  pCoordi     nator();
            joi nGroup();
  
        final Abstract   Coordinato   r  .Ge n erati on c      urrGen =   co  ordinator.g  ener a    t      ion();

                         // let      th e heartbeat request  to send out a    request       
        mockTime. sleep(HEA  RTBEAT_IN    T    ERVAL     _MS);

           TestUt    ils.waitForCondition(()      -> !mockClient.requests().isE      mpty(), 2000,
                    "The hea   rtbeat req    uest was not se   nt");
        assertTrue(coordinator     .h      eartbeat().hasIn   f  light());

             //   ch  ange   the generat  ion
        f      inal AbstractCoo         rdinator.Ge                nerat        ion        newGen = new Ab  stractCoordinator.Generation(
                curr     Gen.generati      on Id,
                   currGen.              memberId + "-ne    w",
                currGen.  p rotocolName);
        coor       d     inator.       setNewGeneration(newGen);      

              mockC         li       ent.respo    nd(heartbeatResponse(Er    rors.U   NKNOWN_MEMBER_    ID));

         // t   he hear tbeat er ro    r code shou     ld be ign    o      re d
           TestUtils    .wa    itForCo      ndition(() -> {
                       coor     dinator.pollHeartbeat(mo  ckTime.mil  liseconds())        ;
                       return !coordinator.heartb         eat().has  In   fl  ight();
           }, 2      000,
                "The heartbeat r     esponse  was no                t received ")  ;

        // the       generation shou    ld not     be reset
        assertE   quals(newGen, coordinato    r.     generation())  ;  
    }

        @Test
    public void testHeartbea           tRebalan   ceInProgressResponseDur  ingRebala          ncing() throw  s Interrupt   edExcepti   on {
        setupCoo   rdi         nator();
         joinGroup();

        final AbstractCoordinator.Generation currGen = coordinato r.generation(    );

        // let       the heartbeat              request to send out a reques   t
          mo   ckTime.sleep(HEAR   TBEAT_INTERVAL_MS);

        TestUt  ils.waitForConditio n      (   ()          -> !mockCl       ient.re            ques ts().isEmpty(), 2000,
                        "The heartbea      t   request was     not sent");

              ass   ertTrue(c    oordinator.hea   rtbeat().hasInflight());    
  
                     mockClient   .respond(heartbeatRespon        se(Errors.REBALANCE_IN_PROGRESS));

        c    oordinator.requestR           ejoin("test");

            TestUtils.w    ait         ForCon   diti    on(()    -> {
                     coordinator.ens     ure      ActiveG    r      oup( new Mo           ckTime(1L).tim    er(100L));
            r   eturn !coordinator.heartbeat( ).    h  asInflight    ();
        },
                            2000,
                "T    he   heart      b           eat response was        n    ot received" );

        // th    e generation would not be rese t whil    e the rebalanc    e is in progress
        asser       tEquals(currGen, coordinat        or.g  en  eration  ());

        mo  ckClient.respond(jo       inGroupFo      llow   erResp       o   nse(currGen.gen  era            tion  Id   , memberId,   JoinGr oupRe       q  uest.       UNKN     OWN_MEMBER  _ID, E   r     rors.NONE));
        mockCli  ent                .prepa reResponse(syncGroupRe    sponse(Errors.NONE));

                    coordi nator.ensureActiveGroup();
            as         sertEq  uals(currGen, coordinator     .generation());
    }

            @Test
    public      void testHeartbeatInstanceFence  dResponseWithOldG    enerat ion() throws Interru   pt     edExc       eptio      n {
              setupCoordinator();
              j  o  inGrou    p();

        final AbstractCoordinator.Generati    on currGe n = coordinator.generation();

          // le     t the heartbeat request to send  out a request
         mock   Time.sleep(HEARTBEAT_INTE    RVAL_MS);

            TestUtils.waitForCondi     tio  n(   ()      -> !      mockClient.requests().isEmp   ty(), 2000,
               "The heartbeat request w  as not se     nt")    ;
           assertTrue(coordinator.heartbe     at().hasInflight    ());  

        // chang e   t  he genera     tion
            final      A    bstractCoordinator.Generation newGen = new Abstrac   tCoo  rdi na tor.Gene  rati      on(
               currGen.g e nerationId,
             cu     rrGen.memberId + "   -      ne   w",
                    cu    rrGen.protocolName);
         coordi     nator.setNewG   ene          ration(newGen);

        mo    ckCl   ie          nt.respond(     heartbeatRespons  e(Errors  .FE                   NCED_    INSTANCE_ID));     

        // the heartb     eat     e   rror code should be           ignored     
                    TestUti ls.wait    Fo rCo        ndition(() -  >  {  
                    coor   dina    tor.    p     ollHeartbeat  (mockTime.m    illis   econds())   ;
                             return !coordinator  .heartb  eat(  ).hasInflight();
            }  , 2000,  
              "The h        eartbeat    response was not received"); 

            // t   he generation sho u ld not be reset  
        assertEquals(newGen, coo     rdinator .generation())       ;
                }

    @Test
         p  ublic voi               d tes     tHeartbea  tReq  uestWithFencedIns   tanceIdException() {
           setu  pCo  ordinator();
        mockClient.prepareResponse (groupCoordi natorResponse(node, Err      ors.N   ONE));

                                 fin   al  i      nt generatio   n   = -1;

            mockClient.prep    areRespon   se(   jo  inGroupFollower     Response(generation, memberId, Jo     inGr     ou   pR  equest.UNKNOWN    _ MEMBER_ID, Er   rors.   NONE));
         mockClie     nt.pre   pareResponse(syncGroupRe  sponse(E    rr   ors.NONE));
        mockClient.pre    pareResponse(hea     rt   bea    tResponse   (Erro              rs.FENCED_I            NSTANCE  _ID));    

                              assertThr  ows(Fenc  edInstanceId             Excep  tion.class,
                   ( ) -> {
                                    co    o    rdinator.   ens         ureActiveGroup();
                                   mockTime  .     s  leep (HEA  RTBEAT_I    NTERVAL_MS  );
                   long startMs = System.c   urrentTime    Millis (  ); 
                   w          hile            (System.currentTime  Millis( ) - st    ar     tMs < 1000) {   
                                    Thread.sl   eep(10);
                    c  oordin  ato    r.po    llHeartbeat(mockTime.mil      liseconds());
                    }
               },
            "Exp      ected pollHeartbe  at to raise fenc    ed instance id          e      xception in 1      second")   ;
    }

    @Test
    public     v    oid tes   tJoi    nGroupRequ     estWit  h          Grou         p    Instan     ceIdN    otFound() {
        setupCoor    d i  n     ator();
           mockClient.prepareRespo   nse(groupCoordinatorResp   onse(node, Errors.NON      E));  
               coordinator.ensu     reCoordinatorReady(mockTime.timer(0));

        mockClient.prepareResponse(join       Gro   upFollowerRespon    se(de faultGen   eration, mem  be          r    Id, Join     Grou  pR     equest  .     UNKNOW   N_MEMBER_    ID, Errors.  UNKNOWN_ MEMBER_ID));

        RequestFuture<ByteBuffer> futu    re = coordinator.sendJ   oi  nGr      oupRequ      est();

                a     sse   rtTrue (c  onsu          merClient.poll(  future,   mockTime.timer(REQUEST  _TIMEOUT_MS)));
                assertEquals (   Er    ror      s.UNK NOWN_MEMBER_ID.   message(), future.e     xception().getMessage());
             as   sertTrue(coordinator.re    join  Need       edOrPending());   
           a       s  sertTrue(coordinator.hasUnknown Generatio   n    ()) ;
        }

    @Tes          t
    pub  lic v  oid testJ o    i      nGroupRequestWithRebalanc  eIn      Progress()  {
         setup      Coordinator ();
        mockCli     e nt    .p   re  pareResponse(groupCoordinator    Response(node, Errors       .NONE))   ;
        coordinator  .ensureCo or   dinatorReady      (mo  ckTime.timer(0)  )   ; 

        mockClie    nt.pre    pareRespon   s e(
                      joinGroup  FollowerRe sponse(defaultG  en  eration    , memberId,   JoinGrou    pRe quest.     UN KNOWN_ME  MBER_ID, Error  s  .RE    B   ALANCE_IN_PROGRESS));

          Req   uestFu     ture<    ByteBuffer> future = coordinator.s endJoinGroupRequest();

        assertT     rue(consume  rClient.poll(fu  ture, mockTi     m       e.        timer(REQUES    T_TIMEOUT_MS)));
        assert   I  nstanceOf(future.exception().getClass(), Errors.REBALANC    E_I        N_PRO   G        RESS.except  ion());
         a     ssertEquals(Errors.REBAL          ANCE_IN_PR      OGRESS.mes  s  age  (), fu tur  e.exceptio  n().ge   tMessage());
               assertTrue(coordinator.          rejoinNeede    dOrPe ndin   g());

           // mak   e sure we'll retry on ne   xt poll
        assertEquals(0, coordinator.onJoin   PrepareInvokes);     
                 assertEqual       s(0, coordinator   .onJo     inComplete        In      vokes);

        mockClient.prepareR    espo nse(joinGroupFollowerResponse(de     faultGeneratio n      , member  Id, JoinGrou     pRequest.UNKNOWN_MEMBER_ID, Err  o  rs.NONE));
        mockClient.        prepare  Respons   e(syncGroupResponse(Errors.   N    ONE));

           co   ordinator.en   sur    eAct  iveGroup   ();
                      // make    su  re both onJoinPrepare an d onJoinC     o  m      plete got c   alled   
          as      sertE    quals(1,   coor       dinat   or            .onJo  in    PrepareInvo   kes);
        asse  rtEquals  (1,   c  o  or  dinator.onJ    oinC    ompl    eteInvokes)   ;
      } 

        @Test
             public void t    estLeaveGroupSen   tWit   hGroupInstanceIdUnSet() {
        ch     eckLea        veGro   up     R  equestSent(     Optio   nal.empty());
        checkLeaveGroupRequ       estSent    ( Optional.of("groupIn    stanceId      "));
    }

           private vo  id  checkLeaveGroupRequestSent(Optio  nal<S   tr      in    g>      g     roupInstanceId) {
             se      tupCo      or   din   ator(R ETRY_BACKOFF_MS,        RETRY_BACKOFF_MAX_MS, I      ntege       r.MAX_VALUE, groupInstan   ceId)   ;

        mockClient.prepareResponse    (gr        oupCoordinato    rRes pons    e(    node, Erro rs.NONE));
        mo   ckClient.prep areResponse(joinGrou pFollowerRespo              nse(1, memberI  d, lead erId      , E  rrors.NONE));   
           mockC        l    ient.prepar       eRe   sponse  (     syncGroupRespons   e(Errors.NON     E));

          final     RuntimeE xcep     ti    on e = new RuntimeException  ();

            /  /   raise the                    error when the co   ordinator tries to send  lea   v        e group req      uest.
        mockCl   ient.prepareRe       spon  se(body -> {
             if (body instanceof Le    ave     GroupRequest)
                          throw e;
              re    tu  rn false;
         }, heartb  eat  R     es ponse(Errors.UN KNOWN_SER       VER_ERROR)) ;

        t  ry {
                         coordinator.e     nsureActiv    eGroup();
            coordinator   .close    ();
                                    if (coordinato r.isDynamicMember())     {
                   fa    il( "Expected leav    egroup    t     o rais       e an erro r.");
               }
        } ca  tch (RuntimeExce   pti   on   exception) {
                         if (coordinator.isDynamic Member()) {
                     a    ssertEqual     s   (exception, e);
                  } else    {
                f ail("Coordin ato      r with gr oup.instance.id set shouldn't send     leave      gro    up     request.");
                    }
         }
    }

    @   Test
    pub           li   c void  testHan dl  eNormalLeaveG   roupResp       onse() {
        Member          Response memberRespo  nse = new     MemberRe sponse()
                                                                     .s      et        MemberId(membe    rId)
                                              .setErrorCode( Er      rors.NONE.code())  ;
          LeaveGroupResponse respons   e =     
                 l     eaveGroupR  e    spon       se(  Collections.si ngleto nLi   st(mem b         erR  esponse))   ;
          RequestFuture<Void>  leav   eGroupFuture = setupLeave  Group(     response);
        assertNot  Null(leaveGro u   pFuture )       ;
        a  sser t True(le  aveGroupFutu        re.succeeded());
           }

    @Test
    public      void te stHandle      NormalLeav  eGroupR esponseAnd    TruncatedLeaveR     eas    on() {
        MemberRes  ponse memberR esp   onse = ne       w M   emberResponse()
                         .set   MemberId(m  em    berId)
                        .   setErrorCode(Errors    .  NONE.code());
           Le aveGroupR  esponse res ponse        =
                  leaveGroupResp     onse(C         o   llections.si              ngletonList(memberResponse));
             String leaveReason =        "Ve   ry         loooooooo  o       ooooooooooooo ooooooooo oo      o  o ooo  oooo    o  ooooooooooooooooooooooooooooooooooo     ooooooooooooooooo  ooo oooooooooooooooooooo  oooooooo    oooo     o      ooooooooooooooo    oooooooooooo        o  oooon                 g le   aveReas     on that is        271 characters long to make       sure that l  ength limit lo     gic handles the scen     ari   o ni  ce  ly";
        Reque     stFuture<Void> le   aveGro  upFuture = se        tupLea   veGrou p(res  po    nse, le   aveReason, leave Reason.substring(0, 255));
        asse rt    NotNull(leaveGroup    Future   );
          as sertTrue(leaveGr oupFuture.     succ  eeded());
            }

       @Test
    public void testHandle     Multi   pleMembersLeaveGroupResponse() {         
              Member         Respo  ns   e memberR   esponse =  ne            w       MemberRespon   se(  )
                                                               .setM   em      be    rId(me  mberId)
                                                           .setErrorCode(Errors.NONE.code());
              L  eaveGroupResponse re   sponse =
                           leaveGro  upResponse(Arrays.asList(memberResp   onse, memberResponse));
        Re   q  uest    Fu   t  ure<Void>  leaveG        ro upFuture = setupLeaveGroup(respo nse       );
            as    s  ertN otNul           l(leaveGroupF   uture);
            assertInstanceOf(IllegalStateExcepti     on.cl   ass, leaveGro  upFuture.  except   ion   (  )  );
    } 

    @Tes t    
         public    void testHandleLeave    Gro     upRe   sponseWithEmpt y MemberResponse() {
          Lea  veGroupResponse response =
            leaveGroupResponse(Colle  ctions   .empt  yList());
        RequestFuture<Void>    leaveGro    u  pFuture = setupLeaveGro   u     p(response);
        a      ssert   NotN        ull(leav    eGroupFu      ture);
        a  ssertTrue   (leave   GroupFutur  e.succeed  ed()     );
           }

       @Test
    pub    lic vo    i  d test   Handle  LeaveGr    oupRes      p onseWithException        ()     {
        MemberRespon   se mem  berResponse = new      MemberResponse()
                                                    .setMemberId(memberI   d)
                                                    .setErrorC      ode  (Err   ors.UNKNOWN_ME  MBER_ID.code()) ;
        LeaveGroupResponse respo  nse =
            leaveGro        upResponse(Co l  lections  .singletonList(memberResponse    )    );
        Reque  stFuture<Void> leaveGroupFuture =       se  tupLea  veGrou   p(r      esponse);
        assertN      otNull(le  aveGroupFuture);
                         assertI    n    stanceOf(UnknownMemberIdEx    ce      ption .cl   ass, leaveG   roupFuture.    exce pti     on(  ));
    }

    p    rivate Request  Future<Voi       d> setupLea   veGroup(LeaveGroupResponse leaveGroupResponse     ) {
        r  eturn setupL  e  ave     Gro    up(leave     GroupResponse,  "test maybe  leave group   ", "test may        be leave gro      up"  );
    }

             private R  equestFuture<Void> setupLeav     eGroup(LeaveGrou  pResponse leaveGr      oupResponse,
                                                           St ring  lea        veR   eason,        
                                                      Strin   g expectedLeaveReason) {
        setupCoo   rdinator(RETRY_BACKOFF_MS, RE      TRY_BACKOFF_M AX_M  S, Integer.M AX_VALUE, Opti onal.e   mpty());

        mockCl ient.prepareR    espo  nse(gro    upCoordinato  rResponse(   node, E      rrors.NONE));
        mockCli      ent.prepareResp   onse(joinGroupFollowerResponse(     1, memb       erId, leaderId, Errors.NONE))            ;
            mockClie nt   .prepareRe sponse(syn     cGr    oupResponse(Errors.NONE))    ;
            mo  ck  Client.     prepare   Respo     nse(bod       y -> {          
            if (!    (bod  y i nst anceof Leav eGroup     Request))      {
                  retu      rn    fal         se;
              }
               LeaveGroupRequestData leaveGro     upRequest = ((LeaveGr   o  u     pRequest)   body).      d   ata();
                     r    et   urn leaveGroupReque st.members       ().get(0)   .memberId().equals(m     emberId) &&
                         le   aveGro   u  p   Request.mem     b   ers   ().get(0).   reason().equal s(     expe  cted  LeaveReason);
        }, leaveGr    oupRespo   nse);

                  coordinator.ensureActiveGroup    ();
        return coordinator.maybeLeaveGro      u         p(leaveReason);  
    }

    @Test
           public voi     d testUncaugh t   ExceptionInHeart     beatThrea  d() throws      Ex  ceptio          n {
        setupC   oordinator()  ;

        mockC   l ient.prepareRespon  se(        gr    oupCoordinatorResponse(n  ode,  Er rors.NONE)           );
              mock   Cli   ent.   prepare     Respons          e(joinGro    upFollower         Response(1, memb  erId               , leaderId, Errors.NONE));
           mockClie   nt.prepareResponse(syncGroupResponse(Err ors.NON E)    );

              final      R un         ti            meException e = new RuntimeExce     p tio     n();

          //   raise the     error when th   e background thread    tri   e  s to s    end a heartbea t
        mockClient.p        repareResponse(body -> {
            if (body inst            a  nceof       Heart   beatReques   t)
                   th row e;
            ret        urn false;
         },   he       ar   tbeatR   e     sponse    (Errors.UNKNOW      N_SERVER_      ERRO    R));
        c     oordinator.ensur     eActiv   eG    r oup()   ;
         mo    ckTime.sleep(HEAR      TB    EAT     _INTERVAL_MS);

        t     ry {      
                    lon    g start   M     s   =    System.curr           entTimeM illis();
                  while  (System.currentTimeMillis(  ) - startMs   < 1000) {
                Thr ead.sle ep(10);
                       coord      inator.timeToNextHea      rtbe         at(0      );   
                        }
            fail("Expected timeToNextHear tbeat to raise an error in 1 se    con  d");
        } catch (R   untimeException exce       p  tion) {
                       assertEquals(exception,     e);
            }

                   try {
                 long sta    rtMs = Syste  m.curr entTimeMillis();
             while (System.c      urrentTimeMillis(           ) - startMs < 1000 ) {   
                    Thread.sleep(10)     ;
                   co  ordinator.pollHeartbeat(mock   Time.millis econds(  ));
            }
                         fail("Expected pollHeartbe      at to          rai   s       e a n      error            in 1 second");
                  } catch (RuntimeExcep   t    ion e  xcept io n)  {    
                  assertEquals(exce  ption, e   );
        }
    }

         @Tes t
    public void       testPollHeartbeatAwak  esHeartb  eatThr ead()    throws Exc          ept   ion {  
            final int     longRetryBackoff       Ms =     1    0000;
        f    ina l    int    longRet         ryBackoffMaxMs =    10000;
         setupC               oordi   nator(long     RetryBackoffMs, long  Ret            ryBackof  fMaxMs   );   

              mockC lient.prepareResp onse(groupCoordinatorResponse(n    ode, E   rr ors.NONE));
           mockClie nt . prepareRe sponse(joinGr      oupFollowerResponse(1, memb   erId, leade     rId, Errors. NONE));
              mockCli ent.  prepare        Response(syncGro  upRespo   nse(E r         rors   .NONE));   

         coordinator.ensure       ActiveGroup();          

          fina l C  ountDownLat c   h heart     beatD  on         e = new CountDownLatc     h(  1)  ;   
            m    ockClient.p r         epareResponse(bo  dy -> {
              heartbeatDone.co  unt Down();
                r  eturn    body instanceof Heartb    eatRequest  ;
        },    heartbeatRespo   n se  (Errors.NONE)   );

        mockTi  me .sleep(HEARTBEAT_INTER    VAL_MS);
            coo rdinator.pollHeartbeat(mockTime             .mil   li second s()        );

                 if (!     heartbeatDone.await(             1, Ti  m    eUn      it.SECOND      S))   {
                           f a        il ("Should        have received a heartbe a     t r equest    after ca       lling pollHea   rtbeat");
              }
     }
  
           @Test
    public void testLooku    pCo   or     dinator()   {
                                  setupCoordinator();    

        mockClient.backoff(node,       50);
        R  eq          u estFuture<Void> noBro    kersA v  ailableFuture = coordi nator .lookupCoo    rdinator();
            as  s    ertTrue(noBro kersAvaila     bleFut  ure.failed     (),       "Failed future ex   pected");
        moc    kTime.sleep(50);

              R    equ   estF uture    <Void   > future = coordinator .looku     pCoordinator()   ; 
             as  s  e      rtFalse(future.isD   one     (   )  , "      Request  not    sent"               );
        ass   ertSame(future  , co ordinator.l   ookupCoor dinator(), "Ne  w r   equ  est sent while one is in progress");

        mockClient.p  repareRespons  e(groupCoordina    torRe s pon se(node, Err ors.   NONE));
          coordina   t    or   .ensure  C     oordinatorRe     ady(mo      ckTime.t      imer(Long.M   AX_VAL    UE));
                     asse  rtNotSa    me(fu      tur              e, coordinator.lookupCoordinator(), "New reque    st not sent afte   r prev   i  ou   s co        mp         le t ed");
    }
   
    @Test
    public void testWakeupAfterJoinGrou   pSe      nt() throws Exceptio     n {
         setu     pCoordinato    r();
  
        mockClient.pr  ep areResponse(g   roupCoordinatorResponse(node   , Errors.   NONE));   
        mockCl    ient.prepa     r         eRespon   se(new MockCl ient   .RequestM   atcher() {
            priva  te       int    in         vocations = 0;
                          @Override
            public b   oolean matc   hes(   AbstractRequest body)          {
                                   inv    ocatio   n  s      ++;
                boolean isJoinGroupRequest = body instanceof JoinGrou       pRequest;
                     if     (isJo    inGroupReques    t &&    invocation  s == 1)
                                           /    /        s          imulate wakeup before the request returns    
                                               throw new       WakeupException();
                     ret    urn isJoinGroupR       e     qu est;
                      }
        }, joi   n        GroupFollowerResponse(1, memberId,               leade          rId,    Errors.NON   E));
                  m     ockClient.prepareResponse(syncGro  upResponse(E      rro    rs.NONE))  ;
        AtomicBoolean heartbea    tRecei   ved = prepareFirstHeartbeat();

        t      ry {
                        coordina   to   r.ensu    reActiveGr        oup();
                  fail("Sh   ould have w          oken   up fro     m ens          u        reAc    t iveGroup ()");
            } cat   ch (Wake   u   pException ignored) {
        }

        assertEquals(1,  coordinator.onJoinPr    epareInvokes);
            assertEquals(0, coordin        ator.onJ            oinComplet   eInvokes);
          assertF   al s  e     (hea  rtbeatReceiv     ed.ge      t());

              coordinator.ens ureActiv   eGroup();

        assertE  quals(1, coordinator.onJoinPrepareInvokes);  
              as     sertEqu      als    (1, coordinator.o     nJoinCompleteInv             okes)         ;

              awaitFir s   t    Heartbeat(hea  rtbeatRece   ived);
    }

    @Test
          publi      c void testWakeupA        fterJoi     nGro   upSent    ExternalCom   plet       ion() throws Exception {  
             setupCoordinator();

                  moc  kClient.p  r      e   pareResponse(   groupCoord inatorRespon se(           n  ode, Errors.NONE)  );
        mock    Cli   ent.   pre     pareResponse   (  new M   o       ckClient.RequestMatche     r() {
                   priv       ate int invocations =      0;
               @Overrid e
            public   boolean matches(Abstract  Reques     t     body) {
                in         voc       at   ions++;
                 boolean is  JoinGroupRequ    est       = b       ody instance    of J       o        in  G     roupR equest;
                           if ( is JoinGroupRe   quest && invocations    == 1)
                             // si  mulate wakeup befor  e the reques   t return   s
                          th row n   ew WakeupException(   );
                    r    eturn      isJoinGroupRequest;
            }
                }, joinGroup    Follo werResp   onse     (1, m   emberId, le  aderId , Errors.NONE));   
           mockClient.    p   rep     areResponse(syncGroupRespo          ns  e(Errors.NONE));
               Atomi  cBoolean heartbeatR  eceived = p    repareFirstH   eartbeat();
   
        try {
                  coor    dinator.ensureActiveGrou     p();
            fail("Should h    ave         woken up from  ensureAct  iv eGroup()");
        }   catch (Wa   k  eupException igno red) {
        }

             assertEquals(1, coord      inator.onJoinPrepareInvokes);
        assertEquals(0, coo  rdinator.onJo  inCompleteInvo   kes);
          assertFalse(heartbeatRe   ceived.get());

        // the join group comp   letes         in this poll()
        consumerClient.pol    l(m  ockTime.                timer(0)  );     
                   coordinator.ensureActiveGro  up()  ;

           assertEquals(1, coordinator.onJoinPrepareInvokes);
             assertEquals(1, coordinator      .onJoinCompleteInvokes);
  
         awa  itFirstHeartbeat(he    artbeatReceived);
    }

    @Test
                 public void te  st  Wakeup AfterJoi      nGroupRe     ceived() throws     Exception {
             set    upCoordinator()  ;

          mockClient.prepareResponse(groupCoordinatorResp   on  se(    node, E    rro  rs  .NONE))    ;     
        mockClient.p  repareResponse(body -> {
                       boolean is JoinGroupRe   quest = body inst   anceof J  oinGroupR    equest;
                 if (isJo    inG   roupRequest)
                       // wakeup after the      request returns
                    consu              merClient.wa     keup();
            return      isJo  inGroup  R     e    quest       ;
        }, joinGroupFollo   werRespon     se(1, member      Id, leaderId, Err  ors.NONE));
                  mockClient.prepareResponse(syncGroupResponse(Er rors.NO NE));
               AtomicBoolean heartbeatReceived           = pre   pare       F          irst    Heartbeat();

        try  {
            coor   dinator.ensureActiveGroup();
                 fail   ("S  hould hav   e woken up from ensureActiveGr       oup (    )");
        } catch (WakeupException ign or ed) {
                 }
    
        assertEquals(1, coordinator.onJo     inPrepar       eInvokes)    ;
        assert     Equals(0, c  oordinator.onJo   i     nCompleteInvokes);
               asse     rtF       alse(  hea rtbeatR eceived.get());
   
         coordinat    or.e        n   s    u   reAc  tiveG roup();

        a  ssertEquals(1, coordinator.onJoinPre      pareInvokes);
        asser    tEquals  (1, coordinator.onJoinCompleteInvo    kes);

        awaitFirstHeartbeat(h  eartbea   tReceived);
    }

    @T   est
             public voi     d testWak    eupAfterJoinGroupReceivedExter   nalCompletion() throw     s Exce  ptio    n {
        s      etupCoordinator();

        mockClient.prepare     Response(groupCoo      rdinato         rRe  sponse(node, Errors.NONE));
             mockClient.pr   epareResponse(body     -> {
                 boo lea   n is       JoinGroupRequest =       body instanceof JoinGr    oupReq      uest;
            if (isJoinGroupR     equest)
                   // wakeup after the r  equest returns
                   cons  u  merClient.wakeu     p();
            return isJoinGroupRequest;
          }, joi   nG  roupFol  lowerResp     onse(1, memberI      d, leade   rId, Errors.N   ONE));
           mockClient.prepar e    Response(sy  ncGroupResponse(Erro  rs .     NO   NE));
        At    omic   Bo       olean heart beatReceived = pr    epa    reF        ir      stHe     art   beat(); 

        assertT hrows(Wakeup Excep   tion.class, () ->  coordinator.ensu    reActiv  eGroup       (),            "Sho     uld have woke  n     up from ensur  eActiveGroup()");

         asse rtE   quals(1, c  oordinator.  onJoin PrepareInvokes);
        assertEquals(0, coordin     at  or.onJoinCompleteI     nvokes);
            as  sertFalse(heartbeatReceived.get())     ;

        // the join group completes in this poll()
        consumerClient.poll(mockTime    .timer(0));
        coor     dinator.e   nsureActiveGroup();

              a     ssertEqual  s (1, coo  r    di  nator.onJoinPrepare  Invokes);
        assertEquals(1   , coordinator.onJoinCompleteInvok  es);

         awaitFirstHeartbeat(heartbeatReceived) ;
    }

          @Test   
    public void testWakeupAft     erSyncGr         oupSentExternalComp   l     etion()      thro  ws Excepti  on {
            set   upCoordinator();

                mockCli      ent.prepareResp   onse(grou   pCoordinatorResponse(node     , Errors.NONE));
              moc  kClient.pr    epareResponse(joinGroupFollowerResponse(1, me    mbe rId, leaderI d, Errors.NONE));
                moc   kClient     .prepareRes         ponse(new MockClien t.     RequestMatcher()     {
               private int invocations = 0;
            @O   verride
            public boolean matches(AbstractRequest body) {
                     invocations++;
                      boolean is   SyncGroupRequest = bod    y instanceof SyncGro upRequest;
                  if (isSync     GroupRequ       est && invocations == 1     )
                    // wakeu     p after the request returns
                                       consu           merClient.wakeup();   
                     r   eturn isSyncGroupRequest;   
            }
           }, s    yncGro    up     Re  s      ponse(Errors.NONE));
        Atomi     cBoolean heartbeat   Received = prepa   reFirstH  eartbeat();

        assertT  hr   ows(Wa    keu  pExceptio    n.class,       () ->  coordinator.ensureActiveGroup (), "Sh  ou  ld h  ave woken up from    ensureAct   i   veGrou   p()");

           as   sertEquals(1, coordin   ator.onJoinPrepareInvokes);
           ass     ertEqua     ls(0, coordinator.onJoinComple     teInvokes);
        assertF   alse(       heartbeat   Re   ceived.get());

             // the join    group completes   in this poll()
           consumerClient.pol   l(mo    ckTime.timer(0));
        coord   in   at    o  r    .   ensur        eAc   t  iveGroup();
   
        assertEq        ua ls(1, coordinator.onJoinP  repareIn  vokes);
        assertEquals(     1    , coordinator.onJoi    nCompleteInvokes);

        awaitFirstHear  tbeat(heartbeatReceive    d);
       }

    @Test
    publ    ic  void testWakeupAfterS yncGroupReceived() throws Exception {
               setupC    oordinator();

        mockClient.prepareResponse(groupCoordinator   Response(node     , Errors.NONE));
        mockClient.prepareResponse(joi    nGroupFollowerResponse(1, memberId, leaderId, Errors.NONE)); 
        mo  ckClient.   pr   epareRes   ponse(b   ody -> {
            boo    lean isSyncGroupReques    t = body instanceof Sync   GroupRequest;
                  if (isSyncG  roupRequest)
                     // wakeu    p after           the reque   st returns
                consume    rClient.wakeup();
            retur   n isS   ync  GroupRequest;
         }, syncGroupResponse(Err       ors.NONE   ));
        AtomicBoolean he  art    beatReceived = prepareFirstHeartbeat();

                    try    {
            coor     d      inator.ensureActiveG    roup();
             fail("S     hould  have woken up from  ensureActiveGroup()");
        }     catch (WakeupException ignored) {
        }

        assert   Equals(1, coordinator.on      JoinPrepareInvokes);
        a s    sertEquals(0, coord  inator.onJoi   nCompleteInvokes    );
          assert     False(hea   rtbeatRece ived.get()   );

        coo   rdinator.ensureA      ctiveGroup();

        a  ssertEqua      l s(1, coordinator    .onJoin   PrepareInvokes);
        assertEquals    (1, coor di    nator.onJoinCompleteInvokes);

        awaitFirstHeartbeat(heartbeatReceived);
    }

    @Test
    public void testWa    keupAf     te   rSyncGro   upReceivedExternalCompletion()     t hrows Exception {
                setupCoordi    nator();

        mockClient   .prepareResponse(group   Coordinato    rResponse(node, Errors.NONE));
           mockClient.prepar  eRes ponse(joinGroupFo    llowerResponse(1   , memberId   , leader Id, Errors.   NONE));
        mockClient.prepareResponse(bod  y  -> {
                boolean i  sSyncG   roupReques  t = bo   dy insta    nceof SyncGroupReque  st;
            if (isSyncGroupRequest)
                // wakeup after th       e request returns
                    consumerClient.wakeup();
               return    isSyncGroupRequest;
         }, syncGroupResponse(Errors.NONE));
        AtomicBoolean heartb   eatReceived = prepareFirs   tHeart      beat();

        a  ssertThrows(WakeupException.class, () ->   coordinator.e         nsureActive Group(), "Sho uld have woken up from ensureAct     i    veGroup()");

           assertEquals(1, coordinator.onJoinPrepareInvokes)   ;
        assertEquals(0, coordinator.onJoi    nCompleteInvokes);
        assertFalse(heartbeatR    eceived.    get());

                 coordin    ator.ensureActiveG    roup();

        assertEquals(1, coor    din    ator.onJo    inPrepare   Invokes);
        assertEquals(1, coordinator.onJoinCompleteInvo   k       es    );

            awaitFirstHeartb     eat(    heartbeatRec    eived);
      }

       @T     est
    public void     testWakeupInOnJoinCo   mplete() throw    s      Exception {
          setupCoordinator();

        coordinator.wakeup  OnJoinComplete = true;
            mockClient.prepareResponse(groupCoordinatorRe   sponse(node, Errors.NONE))    ;
           moc    kClient.prepareResponse  (joinGroupFollowerResponse(1, memberId, leaderId, Errors.NONE));
             m ock    Cli   ent.prepare   Response(syncGroupRespon   se(Error      s.NONE   )) ;
        AtomicBoolean heartbeatReceive d = prepareFirs   t    Hear tbeat(   );

                   try {
                     coordinator       .ensureAc tive            G  roup();
            fail("Should have woken up from ensureA   ctiveGroup()");
           } catch (WakeupE xception ign ored) {
            }

          a      sse   rtEquals(1, co  ordinator.   onJoinPrep    areInvokes);
        assertEquals(0,    co  ordinat        or.onJoinCompleteInvokes);
                  assertFalse(  heartbe   atReceived.get());

            // the join    group completes in this poll()
        coordinator.wakeupOnJoinComplete = fals  e;
        co    n  sumerClient.poll(mockTime  .timer(0));
              coor     dinator.ensureAc   tiveGro   up();

        assertEquals(1  , c  oordinator.onJoinPrepareInvokes);
        asse   r tEquals(1, coordinator.onJoinCom    pl   eteInvokes);

        awaitFirstHeartbeat(   h  eartbeatRec  eived   );
    }

       @Test
        public void testAuthenticationErrorInEnsureC    oo   rdinatorReady() {
        setupCoordinator();

        mockClient.cre    ateP  endingAuthenticationError(node, 300);
   
           t   ry {
            coord    inator.ensureCoordinatorRe  ady(mockTime.timer(Long.MAX_VALUE));
              fa il("Expect   ed     an authen   ticat ion err    or.");
            } catch (AuthenticationException e) {
                      // OK
        }
    }

    @Te          st
    pu    blic void testBacko       ffAndRetryUponRetriableError() {
        this.mock     Time = new MockTi   m   e();
        long currentT   imeMs = Syst       em.currentTime      Mill   is();
        this.mockTi me.setCurrentTime      Ms(System. currentT    imeMillis());

        setupCoordinator();
          mockClient.prepareResponse(groupCoordinatorResponse(node, Error   s.N   ONE));
        coordin        ator   .ensureCoordinatorReady(mockTime  .timer(0));

           // Retriabl e Exception
        mockClient.prepareResp   on            se(joinGroupResponse(Errors.C          OORDINATOR_LOAD_I    N_PROGRESS));
        mockClient.prepareResponse(jo inGroupResponse(Errors.NONE)); // Retry w/o error
             mockClient.prepareResponse(syncGroupResponse(E r  rors.NONE));
        coordinator.joinGroupIfNeeded(  mockTime.timer(REQUEST_T IMEOUT_MS));

        assertEquals(RETRY_B    ACKOFF_MS, mockTi   me.m  il   lisecond s() - cur  rentTimeMs,
                ( int) (RETRY_BACKOFF_MS     * CommonC   li  entConfigs.RETRY_BACKOFF_JITTER) + 1);    
    }

    @Test
    public voi    d testReturnUponRetriableErrorAndExpiredTimer() throws InterruptedException {
        setupCoordinator();
        mockClie  nt.prepareResponse(groupCoordinatorResp      onse(node, Errors.NONE));
        coordina tor.ensureCoordinatorReady(mockTime.timer(0));
        Exec    utorService executor = Executo    rs.newFixedThreadPool(1);
               Timer t = m   ockTime.timer(500);
        try {
            Futu     re<Boo lean>  attempt = executor.submit(() -> coordinator.jo    inGroupIfNeeded(t));
            mo ckT    ime.sleep(500);
            mockClient.prepareResponse(j    oinGroupResponse (Errors.COORDI   NATOR_        LOAD_IN_PROGRESS));
               assertF  alse(attempt  .get());
          } catch (Exception              e) {
            fail  ()       ;
        } finally {
            ex  ecutor.shutdownNow();
            executor .awaitTerminati on(1000, TimeUnit.MILLISECONDS);
          }
    }

    private AtomicBoolean prepareFirstHear  tbeat() {
         fin al A    tomicBoolean heartbeatReceived = new AtomicBoolean(false)  ;
        mockClient.pr  ep    areRespons   e(body -> {
               boolean isHeart   beat  Request = body instan     ceof HeartbeatRequest;
             if (isHe artbeatRequest)
                hear     tbeatReceived.set(true);
            return isH  ea  rtbeatReq u  est;
        }, hea       rtbeatResp onse(Errors.UN  KNOWN_SERVER_ERROR));
           re   turn heartbeatRecei ved;
    }    

    private void awaitFirstHea  rtbeat(f i  nal AtomicBoolean heartbea    tReceived) throws Excep  tion {
        mockTime.sleep(HEARTBEAT_INTERVAL_MS);
        TestUtils.waitForCondi  tion(heartbeatReceived::get,
            3000, "Should have   received a he  artbeat request after joining the group");
      }

    private    FindCo     ordinatorResponse  gr     o upCoordi     nator  Response(Node node, Erro rs error) {
        return FindCoordinatorResponse.prepareResponse(error, GROU    P_ID, nod        e);
    }

    privat  e Heartbea    tResponse heartbeatResponse(Errors error) {     
        re  turn new HeartbeatResponse(new HeartbeatResponseData().setError    Code(error.code        ()));
    }

    pr ivate JoinGroupResponse joinGr  o  upFollowerRes      ponse(int generationId,
                                                                String memberId,
                                                              String leaderId,
                                                            Errors error)     {
        return joinGroupFollowerResponse(gener    ationId, memberId,    leaderId, error, nu    ll);
    }

    private Jo inGroupResponse joinGro   upFo   llowerResponse(int gener a    tionId,
                                                           String memberId,  
                                                             String leaderId,
                                                                   E     rrors error,
                                                               String protocolType) {
        return new JoinGroupResponse(
                   new JoinGrou  pRespon seData()
                              .setError Code(error.c   ode()     )
                          .setGenerationId(generati onId)
                        .setProtocolType(pro        t    ocolType  )
                                 .setProtocolName(PROTO  CO L_NAME)
                                    .setMemberId(memberId)
                        .setLeader(leaderId)
                             .setMembers(Collections.emptyList(  ))  ,
                ApiKeys.JOIN_GROUP.latestVersion()
        );
    }

    private JoinG    roupResponse joinGroupResponse(Errors    erro    r) {
        return joinGroupFollowerResponse(JoinGroupRequest.UNKNOWN_GENERATION_ID,
                  JoinGroupRequest.UNKNOWN_MEMBER_ID, JoinGro  upRequest.UNKNOWN_MEMBER_ID  , error);
         }

    private SyncGroupResponse syncGroupResponse(Err ors error) {
        return syncGroupResp     onse(error, null, null);
    }

    private SyncGroupRespo    nse sy      ncGroupResponse(Errors error    ,
                                                String protocolType,
                                                  String protocolName) {
           return new SyncGroupResponse(
                 new       SyncGr ou      pResponseData()
                         .setErrorCode(error .code())
                        .setProtocolType(pro   tocolType)
                          .setProtocolName(protocolName)
                        .setAssignment(new byte[0])
        );
    }

    private LeaveGroupResponse leaveGroupR    espo     nse(List<MemberResponse> members) {
        return   new LeaveGroupResponse(new LeaveGroupResponseData()
                  .setErrorCode(Errors.NONE.code())
                .setMembers(members));
    }

    public static class DummyCoordinator extends   Abst ractCoordinator {

        private int onJoinPrepareInvokes = 0;
          private int onJoinCompleteInvokes = 0;
        private boolean wakeupOnJoinComplete = false  ;

        DummyCoordi  nator(GroupReba      lanceConfig rebalanceConfig,
                         ConsumerNetworkClient client,
                           Metrics metrics,
                           Time time) {
            super(rebalanceConfig, new LogContext(), client, metrics, METRIC_GROUP_PREFIX, time);
        }

        @Override
        protected String protocolType()    {
            return PROTOCOL_TYPE;
        }

        @Override
        protected JoinGroupRequestData.Join      GroupRequestProtocolCollection met      adata() {
            return new JoinGroupRequestData.JoinGroupRequestProtocolCollection(
                    Collections.singleton(new JoinGroupRequestData.   JoinGroupRequestProtocol()
                                .setName (PROTOCOL_NAME)
                               .setMetadata(EMPTY_DATA.array())).iterator()
                 );
        }

        @Override
        protected Map<String, ByteBuffer> onLeaderElected(String leaderId,
                                                              String protocol,
                                                          List<JoinGroupResponseData    .JoinGroupResponseMember> allMemberMetadata,
                                                          boolean skipAs      signment) {
            Map<String, ByteBuffer>     assignment = new HashMap<>();
            for (JoinGroupResponseData.   JoinGroupResponseMember member : allMemberMetadata)       {
                a       ssignment.put(member.memberId(), EMPTY_DATA);
            }
            return assignment;
        }

        @Override
        protected boolean onJoinPrepare(Timer timer, int generation, String memberId) {
            onJoinPrepareInvokes++;
            return true;
        }

        @Override
        protected void onJoinComplete(int generation, String memberId, String protocol, ByteBuffer memberAssignment) {
            if (wakeupOnJoinComplete)
                throw new WakeupException();
            onJoinCompleteInvokes++;
        }
    }

}
