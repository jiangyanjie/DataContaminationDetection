/*
   * Licens  e d to the Apache   Software Founda   tion (       ASF) under one or more
   * contr   ibutor license agreements. See the NOTICE f   ile        d      istributed    with
   * this work for additional informatio   n regarding copyright ow    n  ership.
 * The ASF licenses this       file to You             under the Apache    License,       Ve   rsion 2.0
      * (the "Licens e"); you may not   use th    is file   except in comp   li       ance with
 * the License. You may obta in a co    py of  the License at
 *
 *     http://www.apache.org/lic enses/   LICENSE-2.0
 *
 * U      nless req uired by applicable       la        w or agreed to in      writ   i     ng, so ftware
   * distr    ibuted under the Li   c     ense is dist     ributed    on an "AS IS" BASIS,   
 *    WITHO     UT WARRA   NTIES OR CONDITIO   NS OF       ANY KIND, e  ither express or imp lied.
 * See the Lice    nse for the speci    fic language governing  permission     s and
 * limitations under the License.
 */
package or    g.apache.kafka.clients.consumer.internals;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java      .util.HashMap;
import ja   va.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerPartitionAssignor.Subscription;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.StickyAssignor;
import     org.apache.kafka.clients.consumer.internals.Abstrac   tPartitionAssignorTest.RackConfig;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.c       ommon. TopicPartition;
import org.apache.kafka.common.utils.CollectionUtils;
import org.apache.kafka.common.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
impor   t org.junit.jupiter.api.Timeout;
import org.junit.jupit  er.params.ParameterizedTest;
import org.junit.jupit   er.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

i   mport static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org .apache.kafka.clients.consumer.internals.AbstractPartitionAssignorTest.TEST_NAME_WITH_RACK_CONFIG;
import  static org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignorTest.TEST_NAME_WITH_CONSUMER_RACK;
import static org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignorTest.nullRacks;
import static org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignorTest.racks;
import static org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignorTest.verifyRackAssignmen     t;
import static o      rg.apach   e.kafka.clients.consumer.internals.AbstractStickyAssignor.DE  FAULT_GENERATION;
impo   rt static or      g.apache.kafka    .common.utils.U   tils.mkEntry;
import s   tatic org.apache.kafka.common.utils.Utils.m     kMap;
import static org.junit.jupiter.api.Ass    ertio      ns.assertEquals;
import static org.  junit.jupiter.api.Assertions.assertFalse;
import static org.junit.  jupiter  .a      pi.Assertions.assertNull;
i mport static org.junit.jupiter.api.Assertions.assertTrue;

public abs  tra  ct class Abstrac  tStick yAssignor Te  st {
    protected    AbstractStickyA    ssigno r a      ssignor;
    protec   ted Stri       ng consumer       Id = "consumer";
    protected String consume   r1       = "consumer1";
    protected String cons  umer2 = "consumer2";
    protec        t    ed St    r  ing consumer3         = "consu  me    r3";
     prote cted String consumer    4 = "consumer4";
            p  rotected Map<String, Subscription > sub    s cript ions;
           protected S   tring to       pic =        "topic"         ;
    p      ro       tect       ed String topic1 = "topi c1";
    prot   ected Stri     ng topi  c2      = "to pic2";    
    protected St   ring topic3 =      "topic3";
    protected T    opicPa   rtition tp0 = tp(topic, 0);
    protected To   picPa  rtition tp1 = tp(topic, 1); 
      protected To    picPartition tp2 = tp(topic  , 2);
    prot    ected String       groupI  d = "gro up";
    pr       otected int generati      onId = 1;

    pro   tected int num BrokerRa   cks;
    protected boolean hasConsumerRa  ck;
             prote     cted int rep   licati      onFactor   = 2;
    p rivate     int nextPartitionIndex;

    protected abstract AbstractStickyAssig    nor createAssignor();
    
    // simulate C        o  nsumerProtocolSubscription V0 protocol
          protected abstract Subs  cription buildS ubscrip tionV0(List<Stri   ng> topics, List<TopicPart   iti on> partitions, int generationId, int consumerInd   ex);

    // simulate ConsumerProtocolSubscripti   on V1    pr   otocol
    protected abstract Subscription  buildSubscri  pti    o  nV1(List<S    tring>   topics, List<   TopicPart    ition> partitions, int generationId, int consumerInde x);
 
         //  simulat  e Cons   umerProtocolSubsc    ript  ion V2 or above protocol
    protected abstrac t Subscription buildSubscriptionV2A   bove(List<String> topics, List<TopicPartition>    partitions, int ge        nerati   on, int consumer   Index);  

    protected abstract ByteB  uffer generateUse                  rData(List<String>      topics, List<Top  ic    Pa    rtition> partition           s,    int generat                i   on)      ;

       @B  efo   reEach
         p      ub   lic v  oid       setU     p() {
                 assig     nor   = cr   eat  eAs signor(    );

        if      (su        bscriptio  ns !=      null) {
            subscriptions.clear();  
        } else {  
            su  bsc    ri           ptions = new HashM         ap<>    (   )   ;
        }
    }

    @T    est
    public void testMemberData()         {
             List<St ring>    to  pics = topics(topic);
            List<TopicPartiti      on    > own   edParti tions = parti   tions(tp(topic       1,        0), tp(t  opic2, 1  ));
          List<Subscription>      s ubscriptions = n       ew Array   L ist<>()     ;
        // add subscription in all ConsumerPro    tocolSubs   cript    ion versi ons
        sub  scriptions.add(buildSubscriptionV   0(topics, ownedPartitions, generation       Id, 0)        );
        subscriptions.add(b      u                   ildS  ubscription   V1(topics, owne     dPartitions  , generati  onId, 1));
            subscriptions.add(bui ldSubscriptionV2Above(top  ics,  ownedPartit  io  ns  ,    generationId, 2));
                  for    (Subs   cription subscripti  on : sub             scription     s) {
            if (subscription != null)     {
                     AbstractSt ickyAssignor.Memb  erD  ata memberData = assignor.memberData        (   subscript  ion);     
                    assertEquals(ownedPartition   s, memberData.partitions, "subscri     pt   ion: " + subscr  iption + " doesn't have expected owned   partition");
                assertEquals (gener    ationId, memberD   a   ta.generation.orElse(-1), "su  bscription:   " + subscription     +   " doesn't ha  ve exp      ecte    d ge   nerat    ion id") ;
                   }
           }
    }

    @Paramete      rizedTest(name =     TEST_NAME  _WITH_CONSUMER_RACK)
    @ValueSource(booleans = {false, tru    e})
    public voi  d testOneConsumerNoT   opi   c(boolean hasConsume    rRack) {
        initializeRac  ks(hasConsu    merRack ? RackConfig.BROKER_AN D_CONSUMER_R   ACK : RackConfig.NO_CONSUMER_RACK);
        Map<S    tring, List<PartitionInfo>> partitionsPerTopic = new HashMap<>()  ;
        subscriptions = Collec   tions.si ngletonMap(c     onsumerId, subscription(Collections.emp    tyLi    st  (),   0));

             Map<String, List<TopicPartition>> assignment =   ass     i  gnor.assignPartitions(partitionsPerTo  pic, sub      scriptions);
          assertEqu  als(Col              lections.si  ngleton(consumerId), as   signment.keySet()    );
                asser  tTru   e(assig  nment.get(consumerId).i   sEmpty());
        assertTrue    (assign or.partitionsT ra    nsferringOwne  rship.isEmp  ty());

         verif yValidityAndBalance(su    bscriptio       ns      ,        ass ignment,    partitionsPerTop         ic);
        assertTrue(isFullyBalanced(assignment));
    }

    @ParameterizedTest(nam    e = TEST      _NAME_  WITH_CONSUMER_RACK)
    @ValueSource    ( booleans =        {false,    true})
    public     void testOn        eConsumerNonexistentTopic(boolea       n hasConsumerRack) {  
         initializeRa  cks(hasConsumer   Rack ? RackConfig.BROKER_AND_CONSUMER_RACK : Ra  ckConfig.NO_CONSUMER_RACK     );
               Map<St ring, List<PartitionInfo    >> partitionsPerTopic = new  HashMap<>();
        partitionsPerTopic.pu  t(top     ic, Collections.emptyList());
        subscriptions = Collections.singletonM ap(consume    rId, subscriptio    n(topics(top       ic), 0));

        Map<String, List<Topic  Partition>> assignment = assignor.assignPartitions(p     artitionsPer     Topic, subscriptions    );

        assertEquals(Collectio  ns.singl   eton(consu merId)    , assignment.keySet());
        asser     tTrue(    assignment.get(consumer      Id)    .isEmpty());
        asse  rt   True(assignor.partiti  onsTransferringOwner    s  hip.   isE    mpty());

          veri  fyValidi  tyAn   dBalance(subscript       ions, assi      gnment,   partitionsPerTopic);
                  as  sert       Tru     e(is   Ful lyBalanc    ed   (assignment));
    }

          @Para  me terizedTest(name = TEST_     NAME_WITH_RACK      _CONFIG)
      @EnumSourc    e  (R ackC    o nfi g.c    lass)   
       public void     testOneConsume    rOneT opic(Rack     Con   fig rackC   onfi                g)      {
          initializeRacks(rac  kConfig);
            Map<S  tri       ng,    List<Par titionInfo>> partitionsPerTopic = new HashMap<>();
                  partitio       nsPe   rTopic.put(t opic, partitionInfo     s(  topic, 3)   );
         subscr  iptions      = Co  llectio     ns .singleto   nMap(cons              umerId, subscription(topics   (t       opic), 0));

        Map<Strin  g,  List<Topic      Pa    rt   ition>> assignment = assignor.assignPartitions(part i     ti   onsPerTopic,     su  bscript      ions );
        assertEq     uals(partit     ions        (tp  (topic, 0      )  , tp(topic    ,       1), tp(to     pic, 2)), assignment.ge    t    (c  onsum erId)) ;
        assertTru      e(ass      ignor.partition    sTrans     ferringOwnership.isEmpty()   );

                 verifyValidity    AndBalance(subscriptions, a   s  signm    ent, partitions     PerTopic);
           assertTr       ue(   isFullyBal        anced(assignment));
       }

      @ParameterizedTest(name = TEST_N     AME_WITH_RACK       _CONFIG)
     @EnumSour     ce(R ack     C onfig    .class)
    publ   ic void testOnlyA    ssignsPar  titionsFromSubscribedTopics(RackConfig rackConf      ig) {
        String otherTopic =   "other";

             initializeR      acks(r  ackConfig);
         Map<String    , List<PartitionInfo>>      partitionsPer          To   pic = new    Ha  shM   ap<     >();       
         partitions   P  erTopic.p       ut( to   pic,   partitionInfos  (topic, 2));
        su   bscriptions = mkMap(
                 mkEntry(consumerId, buildSubscriptionV2Ab  ove(
                         topics(t    opic),
                                        Arrays.asList(tp(topic, 0),         tp(topi c, 1), tp(      otherT     op ic   , 0)                ,       tp(otherTopic,    1)),
                                   generationId, 0)
                   )
        );

        Map<String, List<To      pi  cPartition>> assignment = assignor.assignPartitions(partition  sPerT    opic  , subsc  riptions);
        assertEquals(partitions  (tp(top   ic, 0), tp(topic, 1)), assign  ment.get(con  sumerId));
        asser     tTrue(assignor.par     tition   sTransferringOwnership.is      Empty());

              verifyVal idityAndBala  nce(subsc rip    tions, assignment, partiti    onsPerTopic);
             assertTrue(isFu llyBalanced  (assig  nment));
          }

       @P    arame      teri  zedTest(name = TEST_NAME_WITH_RACK_CONFIG)
    @EnumSource(RackConfig.class)
             public void  t    estOn eConsumerMultipleTopi   cs(RackConfig rackConfig) {
        initi   alizeRacks(rackC o  nfig);
            Map<String, List<Par   titionIn     fo>> partitionsPe     rTopic = new  HashMap<>();
        partitionsPerTopic.p    u    t(topic1, partitionInfos(topic1, 1));
        partitionsPerTopic.put(topic2, partition Infos(top   ic2, 2));
        s        ubscript ions    = Col      lections.s  ing   letonMap(co  nsumerId, subscription(topics(topic          1, topic2), 0));

        Map       <Strin   g, List<TopicPartition>> assignmen    t = assignor.assignPa  rtitions(pa  rtitionsPerTopic, subscri ptions);
        assert     Equals(     par    titions(tp(topic1, 0),  tp(topi c2, 0), tp(topic2, 1   )), assignment.get    (cons   umerId));
        assertTrue(assignor.partitionsT   ransferringOwnershi    p.isEmpty()    );
      
           verifyValidityAndBalance(subscri   p   tion  s, assign ment, part  itionsP     erTopic);
        assertTrue(isFull  yBalanced(assignment));   
    }

    @Param   ete     r    izedTest(name = TEST_NAME_WITH_RACK_CONFIG)
    @EnumSourc      e(RackC     onfig.class)
    public      void te      stTwo Consu mersOneTo   picO      n ePartition(RackConfig rackCo n fi   g) {
                     initializeRacks(rackC    onfig);
        Map<String, List<Par              titionInfo>> partitio  nsPerTopic = new H  a    shMap<>();
            partitions   PerTopic    .put(topic, part  itionInfos(topic , 1));

               subscriptions.p      ut(consumer1, subsc  ription(topics(topic), 0));
                subscriptio    ns.put   (cons      umer   2, subscription(  topics(topic),   1));

                     Map<String, List<TopicPartition>> a    ssi               gnment           = as  signor.assignPartitions(partit ionsPer  Topic, subscri  ptions);
            as  sertTrue(assignor.partitionsTransferringOwnership.isEmpty());

        verifyValidityAndBalance(subscriptions, a   ssignme     nt, partiti     on sPer Topic  );
        as   se rtTrue(isFullyBalan       ced(assig     nment        ));
    }

      @ParameterizedTes  t(name =     TEST_NAME_WITH_RACK_CONFIG)
       @EnumSource(RackConfi   g.class)
    public vo    id testTwoConsumersOneTopicTwoPartitions     (RackConfig ra  ckConfig)     {
        initializeRacks(rackConfig);
         Map<String, List<Partiti  onIn       f o>> partitionsPerTopic = new HashMap<>();
           partitionsPerTopic.put(topic, pa   rt      itionIn  fos(to         pic, 2));

              sub      scriptions.put(consumer1, s   ubscr  i    ption(topics(top ic ), 0    ));
           subscriptions.p     ut(consu    mer2, subscription(topics(top  ic), 1));

        Map<Str   in  g, Li    st<TopicParti         tion>> a ss    ignment =  assignor.assignPartitions(partitionsPerT     opic, s   u      bscr     iptions);
        asse     rtEquals(partitions(tp(topic, 0)    ), assignment.get     (consum   er1));
            assertEquals(pa     rtitions(tp(topic, 1)),   assignm    ent.get(consumer2));
               assertTr ue(assignor.partitionsTrans  ferringOwnership.isEmpty());

              verifyValidityAndBala    nce(subscriptions, assign   ment, part      it  i  onsPerTopic);
        assertTrue(isFullyBalanced(assignment));
        }

      @ParameterizedTest(name = TEST_   NAME_WITH_RACK_CONFIG)
    @EnumSource(RackConfig.class)
        public void  testMult     ipleCo   nsumersMixedTopicSubscript      ions(Rack   Conf  ig r    ackConfig) {
        initializeRa cks(ra   ck          Co  nfig) ;
            Map<String, List<Part     itionIn   fo>> partitionsPerTopic = new HashMap<>();
        partit ionsPerTopi         c. pu     t(topic1, p   artitionInfos(topic1, 3));
        partitionsPerT  opic.p     ut(topic2, partitionInfos(topic2, 2));

        su   bscriptions.put(consumer1,  subscription(topics(topic1), 0));
        subscriptions.put  (co      nsumer2, sub    scri  ption(topics(top ic1,   topic     2), 1));
        subscriptions.put(consumer3,   subscription(   t   opics(topic1), 2)  );

                   Map<   String, List<TopicPartition>> ass      ignm   e   nt = assignor.assignPartitions(partitio            nsPerTopic, subscriptio     ns);
        assertEquals(partitio   ns(tp(t  opic1   , 0),      tp(topic1, 2)), assignment.get(consumer1));
         as  sertEquals(partitions(tp(topic2, 0)    , tp(t opic2, 1))   , as  s   ignment.get(consumer2));
             assertEquals(partitions(tp(topic1, 1)), a     ssig    nment.   get(   consumer3));
             assertNull(assignor.partitionsTransferringOwne    rshi          p);

        verifyValidityAndBalance(subs   cripti     ons, assignment, partitionsPerTopic);
        assertTrue(isFullyBalanced(ass    ignment));
    }

           @Parameter iz    e  d   Test(name = TEST_NAME_WI           TH_RACK_ CONF    IG)
    @EnumSourc   e(RackConfig.class)
          public      void testTwoCo        nsume r   sTwoTopicsSix       Partitions(Ra   ckConfig rac    kConfig   ) {
          initi      al i zeRacks(rackConfig);
        Map   <String,                List<PartitionInfo>> partiti     onsPerTo    pic = new HashMap<>();
        partit    ionsPerTopic   .put(topic1, partition Infos(topic1, 3));
        partitionsPerTopi   c.put(topic2, partitionI   nfos(topic   2, 3));

                 sub sc   riptions.put(consumer1, subscription(topics(topic1, topic2), 0));
              subscr  iptions.put(consumer 2, subscription(topics(topi    c1, to  pic2), 1));

            Map<St     ring, List<TopicPartition>> assignment = assignor.assignPart   itions(partition   sPer    Topic, subscript  ion     s);
                     assertEqua              ls(partitions  (tp(topic1, 0), tp(t   opic1, 2  ),   tp(  topic2, 1     )), assignment.ge t(consumer1));
           assertEquals(parti tions(tp(topic1, 1), tp(topic2, 0), tp(topic2, 2)), assignmen  t.get(consumer2));
        a   ssertTrue   (assignor.par titi     o nsTransferringOw   nershi   p     .isE        mp   ty());

                               ve    ri    fyValidityAndBalance(subscriptions, assignment, partitionsPerTopic);
        a    ssertTrue  (isFully   Balanced(assignment));
       }

    /**
     * This unit te   st is testing     con    sumer owned      minQuota parti     t  ions, a   nd expec   ted         to have maxQu    ota       partitions      situation
         */
      @Pa   rameterizedTest(name = T  EST_    NAME_     WITH     _RACK_CONFIG         )
    @Enu  mS    ou   rce(RackConfig.class)
    public void     test Con   sumerOwn  ingMinQuotaEx  pectedMaxQuota(RackConf i    g rackConfig) {
        initializeRacks(rackConfig);
          Map<String, List<Pa rtitionInfo>> partitionsPerTopic = new  HashMap<>();
        partitionsPerT   opic.put(   to       pic1, partitionInfos(topic1, 2)   );
        p  artitionsPerTop   ic.put(  topic2, p  artition    I nfos(topic2, 3));

        List<String> subscribedTopic  s = topic s(t      opic     1, topic2);

          subscr ipt     ions.put   (co   nsumer1,
            buildSubscriptionV2Above(subscr ib  edTopics, partitions(tp    (   topic1, 0), tp(topic2, 1)), ge       nerati      onId    , 0));
             subscription   s.put(consum    er        2,    
            buildSubscriptionV2Above(subscribedTopics, partitions(tp(t   opic1, 1), tp(topic2,     2)), gene   ratio   n  Id, 1))    ;

        Map<String, List<TopicPartition>> assignment = assig    n  or.assignPartitions   (partitionsPerTopic, subscriptions);
            assertEquals(partitions(  tp(topic1, 0), tp(topic    2, 1), tp(topic2, 0)), assignment.get(con  sumer    1));  
        assertEquals(partit     io ns(tp(to   pic1, 1  ), tp(to            pic2, 2)), assignment.get(con   sumer2));
        assertTrue(assignor.p      artitionsTransferringOwnership.i    s   Empty());

        veri  fyValidityAndBalance(subscripti     ons, assignment, partitionsPerTopic);
        asse   rtTrue(isF   ullyBala  nced(as signment)    );
    }

    /**
     * This unit test   is    testin   g consu   mer  s owned maxQu   ota partitions are more than numExp  ecte  dMaxCapacityM  embers s       ituati  on
     */
      @Para  meteri      zedT    e   st(name = TEST_NAME_WITH    _RAC    K_CON FIG)
    @Enu     mSource    (RackConfig.class)
    public void   testMaxQuotaConsumerMoreThanNumExpectedMaxCapacityMembers(RackConfig rackConfig)      {
        initializeRacks(r    ackConfig)   ;
        Map<S  tring, List<Partition      Info     >> partit ions PerTopic = new HashMap<>();
         partitionsPerTopic.put(topic1, partitionInfos(topic1, 2));
                   partitionsPerTopic.p   ut(topic2,  partitionInfos(topic2, 2));

                 List<String>   subsc r   i  bedTo      pics = topics(top  ic1   , topic2);

         subscriptions     .put(consumer1,
               buildS     ubscri   pt     ionV2Above(subscribedTopics, partitio ns(tp(topi  c1, 0) , t       p(topi   c2, 0) ), generat ion         Id,   0));
        su        bscriptions.put(consumer2,
                buildSubscriptionV        2Above(su    b     sc  ribedTopics, partitions(tp(topic1, 1), tp(topic2, 1)  ), generat    ionId, 1));
               subscri     ptions.put(consumer3, buildSubscriptionV2Above(s  ubscribedTopics, Collections.emptyList(), generationId,    2));

                    Map<Stri   ng, List<TopicPa rtition>> assignment =  assignor.assi   gnPartiti    ons(partitionsPerTopic, subscriptions) ;
        assertEquals(Collections.singleto    nMap(tp   (topic2, 0), consumer3), assignor.partitionsTrans     fe rringOwnership);

            verif  yValidityAndBalance(subsc   rip     t       ions, assignment, partit  ionsPerTopic);
        assertEqual   s(partitions(tp(top       ic1, 0))   , assignment.get(cons        um  er1))    ;
              assertE quals(partiti   ons(tp(topic1, 1), tp(topic2, 1)   ), assignment.get(consumer2));      
           ass   ertEquals(partitions(tp(topic2,      0)), assignment.get(co   nsumer3));

        assertTrue(isFullyBalanc    ed(as     sign  m   ent));
    }   

       /**
     * T   his unit test is testing all con      sumers owned        less than minQuota partitions situa   tion
     */
    @Par  amet   erizedTe st(name = TEST       _NA    ME_WITH_    RACK_CONF  IG)
    @EnumSource(RackConfi   g.class)
    public void testAllConsumersAreUnderMinQuota      (Rac kConfig rackCo   nfig) {
        initi  alizeR   acks(rackConfig);
        Map<String, List<Part      itionInfo>>    par titionsPerTopic =  n  ew HashMap<>();
            pa  rtitionsPerTopic.put(t      opic 1, partitionInfos(topic1, 2));
           parti  tionsPerTopic      .put(topic2,  partit   ionIn  fos(topic2, 3));

        List<String>      subscribedTopics =        to   pics(topic1, topic2)  ;

        subscriptions.put(consumer1,
            buildSubscriptionV2Above(subscribedTopics, partitions (tp(topic 1, 0)), generationId, 0));
        subscriptions.put(consume   r2,
               buil      dSubscriptionV2Above(subscribedTopics, partit     ions(tp(topic1, 1)), generationId, 1));   
         sub    scriptions.put  (consumer3, bu   ildSubscript ionV2Above(s ubscribedTopics, Collections.emp  tyLi st(), generationId, 2));

          Map<S     tring, List<TopicPar tition>> assign       ment =     assignor     .assign    Partit  ions(part    i    tionsPerTopic, subs cript   ions);
        assertTrue        (assignor.partition  sTransferringOwners hip    .isEmpty());

          veri  fyValidityAndB    alance(subscriptions, assignment, partitionsPer  Topic);
             assertEquals    (partitions(tp(topic1, 0), tp(topic2, 1)), as signment     .get(consu   mer    1))   ;
        assertEquals(             partiti o       ns(tp(topi    c1, 1), tp (topic2, 2)), assig     nment.get(consumer2)        );
                   asse          r      tEq      uals(partitions(tp(topic2, 0)), assign  ment.get(consumer3));

        assertTrue(i      sFullyBalanced(assignmen    t) );
    }
   
    @ParameterizedTest    (name = TEST_NAME_WITH_R    ACK_CON    FIG)
    @EnumSource      (RackConfig.class)
    public void testAddRe          moveConsumerOneTopic(RackConfig rackConfig)    {
           initializeRacks(rackConfig);       
        Map<String, List<Par titionI       nfo>> partit ionsPerTopic =    n   ew HashMap<>();
                   partitionsPerTo    pic.put(topic, partitionInfos(topic, 3));   
             subsc riptions.pu          t(consumer1,     subs  cri    ption(topics(to       pic), 0));  

         Map<S     tring, Lis  t<TopicPar  tit    ion>> assignment   = assigno         r.assignPartit  ions(pa     rtitionsPerTopic, subscr     i ptions);
        as   sertEquals(part   ition  s( tp(topic, 0), tp(top   ic, 1), tp(  topic, 2)), assignment.get(consumer1));

           verifyValidityA   ndBalance(subscriptions, assignment, partitions   PerTopic);
        assertTrue(isFullyBalanced(assignment ));

           subscriptions.put(consumer1, buildSubscriptionV2Ab  ov  e     (topic      s(topic), as   signment.get(consumer1), gener a      tionId,    0       ));
                 subscriptions.    put(consumer2, buil    dSubscrip t     io     nV2 Above(topics(topic), Collect  i   ons.emptyList(), generationId, 1));
        assignm   ent = assignor.assignPartitions(partitionsPerTopic, subscriptions);
             assertE   quals(Collections.singletonMap(tp(topic, 2), consumer2), assi gno    r.partitionsTransferringOwnership);

        verifyV alidityAnd     Balance(subscriptions, assignment, partitionsPerTopic)  ;
                   assertEquals(partitions(tp(topic, 0), tp(topic, 1 ))             , assignmen    t.ge  t(consum   er1));
         assertEquals(partitions(     t   p(t   opic, 2))   ,  assignment.get(consumer2)); 
        assert     True(isFully Ba lanced (as  sig  nment));

        subscriptions.remove(consumer1        )      ;
              subscriptions.put     (co    nsumer2, bu ildSub   scriptionV2Above(topics(topic), as  signment.get(consum     er2), generationId, 1));
           assignment = assignor.a  ssignPart      itions(pa     rtitionsP    erTopic, subs            criptions);
        assertE   quals    (    new Ha    shSet<>(partiti       ons(tp    (topi     c, 2   ),    tp(topic, 1), tp(topic, 0    ))),
                  new HashSet<>(assig nment    .get(consumer2)));
        assertTrue(assignor.partitionsTransferrin     gOwnership.  isEmp ty());

        verifyVal    idityAndBalance(subscri ptions,   a         ssignment, partitionsPerTopic);
                assertTrue(isFullyBalan   c   ed(assignme      n   t));
    }

    @ParameterizedTest(name    = TEST_NAME_WITH_RACK_CON   F         IG)
    @E   numSource(RackConfig.class)
    public void  testTopicBalanceAfterReassignment(RackConfig rackConfig)         {
        i  nitializeRack   s(rackConfig);
          List<String> allTopics = topics(topic1, topic2);
        Map<String,  List<PartitionI nfo>> part           it    ionsPe    r   T    opic = new HashMap<>();
        partitionsPerTopi c.put(topic1, partitionInfos(  topic1, 12));
        partitionsPerT   opic.put(topic2, parti  tionInfos(to   pi   c    2, 12));
        subsc   ript     ions.put(consum    er1, subscrip  ti  on(allT    opics,     0));
        Map<String, List<TopicPartition>>      assignment = assignor.assignPartitions(partit   ions     PerT   opic, subscrip       tions);

               verifyValidi  t   yAndBalance(subscriptions, as s   ignment, pa    rti    tionsPerTopic);
            assig nment.forEach((consumer,  tp   s) -> a    ssertEquals(12, tps.str      ea  m().filter(tp       -> tp. topic().equa   ls(topic1)).co       unt()));
        assignm  ent.forEach((consumer, tps)            -> ass    e    rtEquals(12, tps.stream().fi    lter(tp -> tp.topic().equals(topic2)).count()));
           assertTrue(assignor.partitionsTransferringOwne   rship.isEmpty()   );
        assertTru   e(isFullyBalanced(assignment));

        // Ad  d another consume      r
        subscriptions.put(con  sum  er1, buildSubscriptionV2Above(allTopics, assignme       nt.get(consumer1), ge nerati  o nId, 0));
        subscriptions.put(consumer2,    buildSubscriptionV2Above(all Topics, Collectio  ns.emptyList(), gene rationId,         1));
          assignment = assignor.assignPartitions(partitionsPerTopi      c, subscriptions);

        verifyVal     idityAndBalance(su     bscriptions, assignment, partitionsPerTopic); 
            assignment.forEac  h((consumer, tps) -> assert       Equals(6, tps.str    ea    m().filter(tp -> tp.topic().eq   uals(topic1)).co     unt()))  ;
        assig   nment.forEach((cons   umer,    tps) -> assertEquals(6,    tps.    stream().          filte    r(tp -> tp.topi     c().equals(topic2)   ).c ount()));
        assertTrue(isFullyBalanc  ed(assignment));     

             // Add two more consumers
            subscriptions.put(consumer1, bu             i  ldSubscriptionV2Ab    ove(al    lTop    ics, assi g  nment .ge     t(     consumer1), generationId,      0));
        subscriptions.put(co   nsumer2, buildSubscriptionV2Above(allTopics, as   sig nment.      get(co   nsumer2), generationId, 1));
        su  bscriptions.put(consumer3, buildSubscriptionV    2Abov    e(allTopics, Collecti      ons.emptyList(), genera     tionId, 2))   ;
            subscriptions.put(consume   r4, buildSubscription     V2Above(allTopics, Colle   ctions.e  mptyList(), generationId, 3));
           assignment = assignor  .assig  nPartitions(pa   rtition sPe  rTopic, subscr  ip    tions);
   
        ver    ifyV  ali  di   tyA  ndBalance(         subscr  iptions, assignment, partitionsPerTopic);
            assignment.forEach   ((consumer, tps) -> asse   rtEquals   (3, tps.stream().filter(t  p -> tp.top    ic().equals(topic1)).count(  )));     
          ass   ignment.forEach  ((consu mer,  tps) -> asse     rtE qua   ls(    3,     tps.stream()    .filter(tp -> tp.topic(  ).equals(topic2)).count()));
        assertTrue(isF        ullyBalanced(assignment));

        /   / r  emove 2 consumers
           sub       scriptions. remove(consumer1);
        s   ubscriptions.remove(consume   r2);
          sub    script  ions.put(con         s    umer3, buildSub   scriptionV2Above   (allTopics, assignmen       t.get(    consumer3  ),       generation     Id, 2));
        subscriptions           .put(consumer4, buildSubsc       ription         V2        Above(allT  opi cs, assi   gnment.get(      consumer4), generationId, 3 ));
        assignment =       as   signor.assignPartition    s   (p   artitions  PerTopic, subscriptions);

        verifyValidityAn           dBalance(subscriptio  ns, a       ssignm     ent, part iti   onsPerTopic);
        assignm  ent    .for   Each((    c      onsu mer,   tps)       -> assertEqu als(6, tps    .stream().filt   er(tp   ->    tp.topic()    .equals(topic1  )).count()));
        assignment     .forEach((   consumer, tps) -> assertEquals(6,     tps.stream( ).filter(tp -> tp.     topic().equals(topic2)   ).co     unt()));
        ass  ertTrue(assignor.partition     sTransferringOwnership.isEm   pty      ());
        asse    rtTrue(isFullyBalanced(assignment));
    }

    @Parameterize    d   Test(name = TEST_NAME_WITH_RA   C    K_CONFIG)
    @En   umSource(R   ackConfig.class)  
    pu blic void tes       tAddRemoveTwoCons      umersTwoTopic   s(RackConfig r a    ckConfig) {
        initializ   e  Racks(rackConf         i   g);
        List<String     > allTopics = topics(topic1, to    p    ic2)     ;  

        Map  <S   tring, List<Pa      rtitionInfo>>   partit  ionsPerTopic =      new Hash    Map<>();
              partitionsPerTop       ic.put(topic  1, p   a   rtitionInfos(topic1, 3)   );
              partit ionsPe  r Topic.put(topic2, partitio  nInfos(topic2, 4)    );
        subscriptions .put(consumer1, subscri  ption(allTopics, 0));
              subscriptions.put(consu   mer2, subscription(allTopic  s, 1));

        Map<String,        List<TopicPart     ition>> assignment = assignor.assignPartitions(partitionsPerTopic, subscri  ptions);
        assertEquals(pa rtitions(tp(topic1,    0), tp(top    ic1, 2), tp(topic2, 1)     , tp(topic2,  3)), assignment.get(con       sumer1));
        assert Equals(pa    r    titions(tp(topic1, 1), tp(top    ic2, 0), tp(topic2, 2  )),    assig nment.get(consumer2));
         assert True(assignor.partitionsTr    ansfer     ringOwnership.isEmpty());

        verifyValidit yAndBalance(subscriptions, ass   ignmen   t,  partit    ionsPer   Topi     c);
        as     sertTrue(isFul    lyBalanced(assignment));
      
        //   add 2 co nsumers
                subscriptions. put(     consumer1, buildSubscripti     onV2Ab    ove(allT     opics, assign   ment.get(co  nsumer1), generati   onId, 0));
         subscription  s    .put(consumer2,   buildSubscriptionV2Above(all   Topics, assignment.get(consume r2), generati      onId, 1) )      ;
        su  bscriptions.put(consume     r3,      buildSubscriptio               nV2Above(allTopics, Collections.emp  tyList(), generationId, 2));
        subscrip  tions.put(cons     u      mer4, buildSubscriptionV   2Above(allTop ics     , Collec   ti ons.emptyList(), gene     rationId,       3));
           assignment =     assignor.a           ssignPartitions(partitionsPerTopic,     subs  criptions);

          Map<TopicPartition, String> expectedPartitionsT   r  ansferringOwnership = new HashMap<       >();
             e    x   pect  edPa    rtitionsTransferrin gOwne   rship.p      ut(tp(topic1, 2), consumer3);
             expect        edPar  titionsTransf     e     rringOwnershi   p.put(tp(topic2, 3), consumer3);
        expectedPartitionsTransferr     ingOwnership.      put(tp(topic2, 2), consumer     4);
               as  sertEquals(   expec  tedPartitionsTransfer       ringOwnership, assignor.parti         tionsTr   an  sfer ringOwnersh     i p);
   
        verifyValid          ityAndBalance(subscr  iptions, assi   gnment, partitionsPerTo     p   ic);
             as      s ert        E    q      uals(partitions(tp (topic   1, 0), tp(topi  c2, 1)), assignmen   t.get(consumer1));
            assertEqua   ls(p       artitions(tp(     topic       2, 0), tp(topic1, 1)),        as signment.get(con    sumer2));
            a      ssert  Equals(part  i       tions(tp(topic1,  2), tp(   topic2,   3)), ass  ign ment.get(consumer3));
                      ass   ertEquals(p   art           itions(   tp(topic2, 2)), assignment.get(consumer4));
        assertT     rue(i    sFullyBalanced(assignment));

        // remove    2 co  nsumers
        subscription   s.remove(consumer1);
          subscriptions.r  emove(consumer2); 
        subscriptions.put( consumer3, buildSubscriptionV    2Above(allTopics, assignme    nt.g et(consum    er3), gen    erationId, 2));
            subscriptions.put(c   onsume         r4, buildSubscriptionV2Above(al    lTopics,      assig nment.get(c    o nsumer4), gen          erationId, 3   ));
        assi     gnment        = assignor.     assignPartitions(p artitionsPe  rT      op i     c,   subscriptions);
          asse   rtEquals(p  art          itions(tp(topic1,     2), tp(topic2, 3), tp(topic1, 0) , tp(top   ic2, 1)), assignm   ent  .get(co   nsumer3));  
        assertEquals(partitions(       tp(top ic2,     2), tp(topic1, 1), tp(top    ic2,     0     )),    assignment.get(consumer4));    

              assertTrue(assignor.par       titionsTransferringOwnership.is        Empty());

              verify     ValidityAndBa  lance(s   ubscript  io     n s, assignment, partition   sP     erTopic);  
        assertTrue(isF         ullyBalanced(assignment));
      }

       /**
     * Th   is     un    i t test performs sticky assign    me    nt for  a s   c    en  ario that round robi  n assignor h  a ndles poorly.
     *    Topics     (partitions pe     r topic): topic1 (   2), topic2 (1), topi    c3 (2), topic4  (1), topic5    (2)
     * Subscriptions      :
     *  - cons    um       er1: topic1, topic2, t    opic3, t opic4, topic5
        *  - consumer2: topic1,             topic3, topi    c5
     *  - consu    mer3: topi   c1, to    pic3, top    ic5
     *  - consumer4: t o      pic1, topic2, topic3,     topic4  ,    t        opic5
      * Roun d Robin Assignment Result:
     *  - consumer1: to    pic1  -0, topic3-0, topic5-0
       *  - consumer2:    topic1-1, topic3-1  , top   ic5-   1
      *       - consumer   3:
     *  - consumer4:         topic2-                0, topic4-0
     * Sticky As  signment Result:
        *    - consumer     1: topic     2-0, topic3-0
     *  -   co     nsu  mer2: topic1-0, topic3-   1
        *  - c     onsumer3: topic1-1, to  pic5-0
          *  - consumer4: t         opic4-0, topic5-1
     */
    @Para    meter        izedTe    st(name = TEST_N    AME_WITH_RACK_CONFIG)
    @E     numSource(RackConfig.class)
    public voi    d testPoor        RoundRobin  As    signmentScenario(Rack             Config  rac        kConfig)  {
          initializeRa    c    ks(rack    Confi    g);
        Map<Str  ing,          Li     st<Partit   ionInfo>> partitionsPerTopic =    new   Ha         shMap<>();
        for (int i = 1; i  <= 5; i++) {
            Strin  g topicName = String.format("topic%d", i);
               p     a rtitionsPer  Topic.put(topicNa    me  , part         ition       Infos(t  opicName   , (i % 2) + 1      ));     
        }

        subscriptions.       pu     t      ("consumer1",
                         subscr   iption(topics("topi       c1", "topic2    ", "topic3", "topic4", "  topic 5"),      0));
           subscript ions.put("consu mer2",           
            sub     scription(topics("topic1              ", "topic3"      , "topic5  "),    1));      
        subscrip tio  ns.put("consumer3",
              subscription(topics  (" topic1", "topic3", "topic5"), 2       ));
              subscriptions.put("  c  onsumer4",
                             subscripti     o  n(topic    s ("topic1", "to  pic   2", "topic3", "topic4", "topic5"), 3));

           Map<String, List<Topic    Par   tition>> assignmen     t = a  ssignor.assignPa rtitions(partitionsPerTopi                c, subscriptio      ns);
        verifyValidityAndBal  ance(subscription      s, assignm  ent, partitio nsPer      To         pic);
    }

       @ParameterizedTest(n      ame = TEST_NAME_WITH_RACK_CON         FIG)
           @En   umSource(RackConfig.class   )
      public void testAddRemov  eTopicTw     oConsumers(RackConfig     rackCon  fig) {
        initializeRac  ks(rac   kCo nfig);
        Map<      St   ring, List<Partit          ionInfo>>   partitionsPerTo   pic = new       Hash    Map<>();
          partitionsPerTopi  c.put(topic, pa     rtitionInf  o   s(topic, 3));
          subscriptions.pu  t(consumer1, sub               scription(top ics(topic), 0));
               subscripti    ons.put  (co    nsumer2, subs    cripti    on(topics(topi  c), 1));

        Map<String,     Li   st<TopicPartition>> a     ssignment =        assignor.ass    i gnPartiti  ons(partit    io     nsPerTopic, subscripti          ons)       ;
        asser         tTrue(assignor.pa         rtition   sTransfe      rringOw   ners    hip.i   sEmpty());
                        // verify balance
        ass  ertTru     e(isFullyBalanced(a  ssignm   ent)) ;
        verifyVa      lidity           AndBalance(subscriptions, assignment, partitions PerTop   i     c);
                   //     verify st   ick iness    
        List<   Topi cPartition> consumer1Assignment1 =   assignment.get(co nsumer1);
        List<TopicPartition> consumer2      Assign ment1 = as   signment.get(consumer2) ;
                           assertTrue((consu  mer1As    si gnment1.size(   ) == 1    &&    consumer2Assignment1.size           () == 2)    ||
            (consumer1Assi    gn    men t1.size() == 2 && consumer2Assignm           ent1.     size() == 1));

           partitio    nsPerTopic.put(t   opic2     , partit   ionInfos(    topic2, 3));          
              subscrip          tions.pu  t(con    sumer1,  buildSub  scriptionV2Above(t    o  pics(t      op  ic, topic2), assi         gnment.get(c         onsu     mer1), generationId, 0));
        sub scriptions.put(cons  umer2, buildSu   bscr           iptionV2Above(topics(topic, topic2), assign     ment.ge          t(consumer2), genera             tionId, 1));
    
        assi  gn    ment =  assignor.as   signPartitions(partitionsPerTopic, subscriptions);  
         assert True(     assignor.partitionsT     ransferringO           wnership.is   Empty())   ;     
        // verify ba      lance
          v   erifyValidityAndBalance(subscriptions,    assignmen   t, partitionsPerTopic);
            asse        rtTrue(isFullyBalance   d(  ass ignmen  t    ));
        // verif   y stickin    ess
          List<T        opic Par        tition>      c onsumer1assig  nment = assignment .get(consumer1);
           List<To  picPar   tit  ion> cons  umer2assignment = assignment.get (consu      mer2);
        a ss   ertTrue(c     onsumer1assignment.size()   ==      3 && consumer2assignment.size(     ) == 3);
        assertT         rue(consumer1assignm     ent.cont ainsAll(cons umer1Assignment1));
              assertTrue(        cons     umer2assignment.cont       ainsAll     (    consumer2Assign     men     t1));

          partitionsPerTopic.remove(t         opic);
            subscriptions.put(consum   er1, bui      ld     SubscriptionV2Above(t    opi    cs(topic2), as  s  ignme  nt   .get ( consumer1), g        en      erat  io    n Id, 0));
        subscriptio ns.put(consumer2, bu  ildSubsc    riptionV2     Abo        ve(topics(topic2),    as   sig    nment.get(consumer2), generationId   , 1));

            assi        gnment = assigno  r.ass     ignPart        it    ions(pa   rtitionsPerTop  i          c, subsc    rip tions);        
        a    ssertTrue(assignor.partiti           onsTransfer    ringOwnership.isEmpty  ());
        //             verify bal ance
           verifyVal    idityAnd   Balance(subscrip tio     ns, assignment, partitionsPerTopic );
        assertTrue  (   isFull    yBalanced(assi      gnment));
             // verify st       ickin ess
        List<TopicP     ar   t    ition> c   onsumer1A   ssignmen  t3 = assignment.    get(consumer1)    ;
          List<TopicPa  rti   tio  n> co       n      sumer2Assignment3     = assignment.get(con    sumer2);
                      assertTr  ue    ((  consu   mer1Assignmen  t3.size(  ) == 1 && consumer2Assignment3.s  i  ze     (      ) == 2) ||
              (consumer1Ass    i   gnm         ent3.s  ize(  ) == 2 && consumer2Assignment3.size() == 1));
            as        sertTrue (c    onsumer1assignment .contain    sAll(consumer1Assignment3));
                  ass    e rtTrue(consumer2assignment.containsAll(consumer2    Assignment3));
    }

    @ParameterizedTest(nam e = TEST_N         AM  E_W         IT    H_RACK_CO    NFIG)
    @EnumSource   (Ra    ckConf   ig          .class)
         p    ublic void     testReassignmentAfterOneCons     umer    Leav  es(     RackCon       fig      rackConfig ) {
                  init ial            izeRacks(rackConf  ig);
          Map<S        tring, List<PartitionInfo>>    partitionsP       e    rTopic = new H ashMap<>();      
        for (int i = 1; i < 20; i  ++) {
                  String topicName = getTopicName(i, 20);
               partit          ionsPerTopic.     put(topicName, partitionI          nfos(topicName, i    ));   
           }

        for (int i = 1; i < 2    0; i++) {
                          List<St   r     ing> topics = ne    w      Array L   i  s t<>();
               f      or (int j = 1; j <= i;    j++)
                topics.ad   d(getTopic Name(j,            20)); 
                          s  ubscriptions.put(getCon sumerName(i, 20), subscripti  on(topics, i));
          }

        Map<  Str   in   g, Li    st<Topic   Par      tition>> assignment = as  signor.assig       nPartit    io      ns(partitio      nsPerTo  pic, subsc        ript ions);      
           verif  yValidityAndBalance(subs   c     riptions    , assignment, part  itionsPerTopic);

        fo    r (int i = 1; i < 20; i++)     {
                  String consumer =     getC  onsumerName(i, 20);
                    subscriptions.put(  consumer,
                     buildSubscriptionV2Above(   su    bscriptions.g   et(consu   mer).topics(), a   ssignmen   t  .g   et(consu            m    er), ge   ne   rationId, i)) ;
        }
        s  ub sc     riptions.remo  ve( "c   onsumer10     "  );     

           as      si     gnment    =  a  ss     ignor.assignPartitions(par              titionsPerTopic, sub   scriptions);
                    ver    ifyVali      dityAndB  alance   (subscriptions, assignment, partitionsPerTop   ic);
        assertTrue(assignor .i    sSti   cky(     ));
    }


    @Parameteriz     edTest(name      =    TES     T_NAME_WI   TH_RACK_CONFIG)
       @E   nu        mSource(RackConfig.class)
                 publ  i     c void t   estR        eassignmentAf  terOneConsumerAdded(R    ack  Config r      ackCon  fig) {
                     initializeRacks(rackConf   ig);
        Ma   p<String   , List<Partit    i    onInfo  >>  partitionsPer   Top       ic =             new       HashMap     <>(  );
            p   artit   ionsPerTopic.put("topic    ",    partitionInfos("topic", 20));

            for     (in           t i = 1; i            < 10; i++)
            subscriptions.put(getConsum  e    rName(i, 10),
                subscri         ption(to p ics(     "topic"), i ));

        Map<Stri  ng, List<TopicPartiti  on>> assignment = as           s ignor.        assignPartitio ns(pa        rti tionsPerTopic, subscrip    tions);
        assertTrue(      assi      gn or.par   t    itionsTransferringOwnership.isEmp  ty());
                 veri fyVa            li     dity   A    ndBalance(subscriptions, assignm        e      nt, p      artition  sPerTop ic)  ;

           // add a n ew consume      r
        subs    criptions.put(g    etCon    sumerName(10,  10), subscription(topi     cs("topic"), 10                 ));

        assi    gnment = ass   ignor   .assignP      artitions(partitionsPerTopi     c        , sub     scriptions);
                   assert     True                      (as      signor.par  titionsTransferringOwnersh    ip.isEmp    ty());
            verifyValidityAndBal ance(subscriptions, assignme  nt, partit     ionsPerTopic);     
       }

        @Param  eteriz  edTe  s                   t(n  ame = TEST_NAME_W    I  TH_RACK_CONFIG)
    @   EnumSo   urce(RackConfig.cl ass)
      pu     blic void te                  s         tSa meSubscriptions(RackConf    ig rac  kConfig)        {
                                                i    nitializeRac    ks(ra  ckConfig);
         Map<String, Li st<PartitionInfo>>  par  titions   PerTopic   = new Hash  M              a  p<>();
         for (int i = 1; i < 15; i++) {
                       String topicNam          e = get  TopicName(i , 15)     ;
                partitionsPerTopic.pu     t(topicNa    me, partit  ionInfo   s(topicName, i));
                       }         

        for (int i = 1; i <   9; i++) {
                List<String> topics = new    ArrayList<>();
                 f or     (int j       = 1; j <= partitionsPerTopi    c.size(  ); j++)
                        topics.a                   dd(getTopicNam      e(j, 15)        );
               subscriptions.put(           getConsumerName(i, 9   ), subscription(t    opics,    i));
             }

                      Map              <Stri    ng, L   ist<T         opicP  art        ition>> assignment = a      ssignor.assignPa   rt  itions(partitionsPerTopic, s                    ub     scriptio     ns)      ;
           as sertTrue(as  signor.partitionsTra  nsferring    Ownership.isEm     pty());    
            verif  yValidity   AndB     ala    nce(s      ubscri  pt    io     ns, assignme     nt,      partitionsPerTopic);

              fo  r     (i  nt i = 1    ;               i < 9; i++) {
                     String consum      er = getConsumerName(i, 9);
                               subscription                           s.      put(consumer,
                build    Subscriptio          nV2A       bove(sub    scriptions.ge             t(consum er).topics(), assig   nment.get(consumer), g  e       neration   Id, i)  );
            }
        subscriptions.r   emove(getCons  u  mer  N      ame(5     , 9        ));

           assignm  ent = a    ssignor.assi  gnPartitions(partitions   PerTopic, su   bscrip tions)    ;
           assert    T     rue(as  signor.     partitions Transf          erringOwn   ership.i        s    Emp    ty());
        ver ifyVa  lidityAndBalan  ce(subsc rip  tio   ns,      assignment, partiti    on           sPe      rTopic   );
    }

    @T     imeout(30)
    @Parameter    izedT   est(na        me = TEST_NA  ME_    WITH_CONSUME       R_RACK)
    @ValueSource(bo  olea   ns = {false, true     }  )
    public void testLa  rgeAssignmentAndGroup   WithUni     fo  rmSubscrip tion(  b  oole    an hasC   on  sumerRac     k)         {
           ini tializeRacks(hasConsumerRa  ck ? Ra     ckCo nfig.B       ROKER_AND_C        ONSUMER         _RACK : Rac kConfig.N    O   _CONS    UMER_RACK);
                // 1 milli      on partitio n    s      for non-rack-aware! For rack-awar   e, use s    maller     n  u  mber of     partitions            to redu       ce    test r    un t    ime.
            i     nt t opi     cCount = has     Co   nsum   erRack ? 50 : 500;
                int partitionC ount = 2_0       00;     
        int co nsum   erCount = 2_ 000;
  
           List<String> t              o    pics = n   ew ArrayList<>()  ;
             Map<String  ,  List<PartitionInf  o>> par  titi  on  sP      erTopic =     new HashMap<>  ();
                for           (in       t i    =     0;   i      < topi  cCount;       i++) {
            String to    picName =        getTopicName(i, t    opicCo     unt);   
                           topics.add(topicName);
                   partitionsPerTopic.put(topicNa        me, partition  Infos(topicName, partitionCount));         
                }

        for (int i = 0; i < co  nsumerCount; i++) {
            sub scriptions.put (getConsumerNa       m        e(i, consumerCount),    subscription(  topics, i));
        }

         Map<Str      ing, Li    st<TopicPartition>> assignment      = a     ssignor.assign                      Partiti   ons(partitionsPerTop    ic,      s  ubscriptions);

               for (int i =            1;          i < consum erCo un   t; i++)  {
                String consu            mer = getConsumer       Name(i, consumerC    ount)   ;
                          subscripti ons   .put(c      o          nsumer,   b   uil    dSubscri       pti     onV2  Above(topics,     assign m  ent   .ge t(     consumer), gen   er   atio   nId,      i));
          }

           a        ss   ign  or.assignP    artitions(partit  ion       s   PerT   opic, subs   criptio  ns);
      }

       @Tim     eout(9   0)
    @Paramete         r    izedTest(name = TEST_NAME  _WITH_C ONSUMER_RACK )
    @ValueSo u     rce(booleans   = {false, true})
    public void testLargeAssignmentAn   d     Gr        oupWithNonEqualSub       scription(boo     lean hasConsumerRack) {
         init       ializeRacks(hasConsumerRack ? RackConfig.B      ROKER_AND_CONSUMER_      RACK : Ra  ckConfig.NO_CONS           UMER_RACK); 
              // 1 million partitions      for non-rack-a            ware! For rack     -aware,          use sm     a   ller     number of partition    s to reduce t    est         run tim    e.
          int  topic     Count = hasC o      nsum  erR ack    ? 50 : 500;
        int parti    ti     onCo  unt = 2_0  00;
                 int consumerCoun   t = 2_0 00;  
                   
        Lis         t<String> topics = n  ew Ar rayList<>();
        Map<Strin  g, Lis     t<PartitionI   nfo>> p arti     tionsPerTopic = new Ha   s    hMap      <>         ();
        for     (int    i     =    0; i < topicCo    unt; i++) {
            St ring topicNam  e = getTopicName(i, topicCount);   
             topics.add(to       pi    c   Na     me);
             par       titionsPer                   Topic.p    ut(topicName, partitionI    nfos(topicName, part  itionCount))    ;
              }
               for (i   nt     i = 0; i    < consum e  rCount; i++)  {
            if (i == consum  e   rCount - 1)       {     
                                     s   ubscriptions.put       (getCons        umer        Name(i , consum    erC   ount),       subs   cription(to  pi   cs.sub       List(0, 1),    i));
                       } el se {
                             subscription    s.put(get  ConsumerName      (i,     con     su  merCount),         subscription(topics,   i));
                           }
                      }
      
             Map<String, Lis       t<TopicPart       ition>> assignment  =        assignor.    assignPartitions(partitionsPerTopic,       subscriptions);

        f    or (in         t i =     1; i < con    sum    erCount    ;   i++)     {
            String consumer = getConsum     erN              ame(i, con   sumerCoun   t);
                i    f   (i == consu       merCount -      1) {
                          subscri ptions.put (co  nsumer, bui    ldSubscriptionV2Abo       ve(topics.s     ubLi  st(0,   1), as  signment .get(consumer), g enerationId, i)    );
                     } else {
                                     subscriptio    ns.put(consume   r,   b   u   ildSu     bscr       iptionV   2Abo     v   e(topics, assign    m               ent.get(co   ns     umer),   generationId, i));         
                     }
        }
          
            assignor.a    ssign  Part  it   ions(partitions      P    erTopic, sub    scriptions);
    }   

    @Timeo  ut(90)
    @ParameterizedTest(    name = TEST_NAME_WITH_   CONSUMER_RACK)
         @Valu     eSourc  e(booleans  = {false, tru       e     })
    public v          oid tes tAssignmentAndGroupWithNonE qual  Sub  sc                 riptionNotTim      eou   t(boolea  n hasC       onsu   merRack)     {    
           initial       izeRack     s(hasCon    sume     rRac      k ?      Ra    ckConfig.BROKER_A   ND_CONSUMER_R     ACK : RackCo       nfig.NO_   CONSUMER_RACK);
               i   nt top        icCount = ha sConsumer  Rack ? 50 : 100;
         int par   titionCount = 2_    00;
               i   nt   c onsumerCount = 5_00;

               Li     st<   S    tring> topics = new           Array  List<>();
          Ma   p<Strin g, List<PartitionInfo>> parti   tionsPerTopic = new HashMa  p<>(       );
        for      (int  i = 0; i < to   pic Co  unt; i++)     {
            Str    ing t  opicName     = getTopic  Name(i, topicCount);
              topics.add(topicName      );
               partition  sPerTop ic.put(topicName, par  titionInfos(topicName, partiti      onCount));
         }  
            f    or (int i = 0; i < consumer  C    ount;       i               ++) {
                if (i %      4 == 0  ) {
                    subsc  rip tions.put(getConsum    erName(i, consumer   Count),
                                              subscripti  on(t  opics.subList(0, topic   C   ount / 2),   i));
                  }     else {
                           s ubscriptions.put(getConsumerName(i, consumerCoun     t),
                              subsc         ription(topics.subList(topi      cC    ou  nt / 2, t   o   pi     cCount), i))     ;
                }
        }
 
           Map<Stri        ng, List<TopicPart    ition >> assi      g  nment    = assignor    .assig nPartitions(par    titions       PerTo      pi    c, subs criptions);

        for (int i =    1; i    < consum erCou nt; i++) {
                       St           ring consumer =     getConsum      erName(i    , consumer C   ount);
            if (i                 % 4 =  = 0)      {
                  subscr     i ptions.  put     (
                        consumer,
                          buildSubscriptionV2Above         (topics.subList(0, topicC              ount / 2),    
                                 assignment.g   et(cons  umer), generati   on  Id, i)
                   );
            } else    {
                                   sub     scr iptions.put(
                                           co       nsumer,
                                      buildSub      s criptionV2Abo   ve(topics.subList(t       opicCount / 2, top icCount),
                                assignment.g    et(         c    onsumer   ),   generat   ionId, i)
                     );
                 }
              }

                      assignor.assign       Part    ition  s(par ti   tionsPerTopic, subs     cription  s)    ;
       }

    @Te  st
    public vo    id testS ubscript      io        n   NotEq  ual  AndAssig    nSam    ePartitio n W  i  th3     Ge   neration() {    
        Map  <Strin    g   , Li        st<P    artitionInfo>> partiti  onsPerTopic =      new HashMap<   >   ();
                       partitionsPerTopic.put(topic, partitionInf      os(topic, 6));
          par    titio   nsPerTopic.put(topi  c1, p  ar     tition        Infos(    to  pic1, 1))           ;       
                int[][] sequence = new      int  [][]{{1         , 2,    3},   {1, 3, 2 }, {2, 1,  3}, {2, 3, 1       }, {3, 1    , 2}, {3, 2,       1}};
           for (int[] ints  : sequence) {
            subscriptions.put   (
                         consu     mer 1,
                    b     uildSubscrip tionV2Above(       top          ics(topic),   
                                   partition    s    (tp(top      i      c      , 0), tp(topic,    2)), ints[0], 0)    
                       );
                 subscription s.put(
                         consumer2,
                    b      uildSubscripti       onV2Above(topics(topic),    
                             partitions(tp(topic, 1), tp(topic, 2), t      p(t     opic, 3  )),  ints[1],       1         )
            );
                        subscriptions.p   ut(
                               c   onsumer3    ,
                        buildSub   sc      riptionV2Above(top   i  cs(topic)  ,
                             part  itio    ns(tp(topic,  2), tp  (  topi       c, 4  ), tp(t           o      p ic, 5   )),  int      s[2],          2)
            );
            s      u        bscriptions.put(
                                 consu mer4,
                       b uildSu    bscriptionV2Ab      ov   e(to  pics(topi       c1),
                               partitions(tp(topic1, 0)), 2, 3)
                       );
      
                       Map   <Str  ing, List<TopicPar    tit   ion>> assign =          assignor.assignPartit  ion   s(    partitionsPerTopic, s     ubscriptions);
            asser    tEqual    s(assign.values()     .str    e      am().mapToInt(List:   :si    ze)   .sum(),
                            ass   ign.  valu       es().stream        ().flatMa p(List::str   eam).collec     t(Collectors.      toSet()).size());
                     for (List<TopicP   art  ition> list: assign.values())        {
                   assertTrue(list.size() >= 1 && list           .siz  e  () <= 2);
                    }
        }
           }

              @Timeout( 60)
       @ParameterizedTest(name = TEST_NAME_ WITH_CONSUMER _    RA    CK)
       @ValueSource(boole      ans = {false, true})
    public void testLargeA    ssi   gnmentWithMul    ti    pleC    onsumersLeavi  ngAndRand omSu    bsc    ri   ption(boolean h  asConsumerRack) {
                        ini      ti     alizeRack    s(hasCons   ume rRack ? RackConfig.BROKE       R_AND_CON      SUMER   _RACK : R    a     ckConfig.NO_CONSUMER_     RACK);
        Random rand =    new     Random();
             int  topicCount = 40;
           int consumerCount = 20   0;

             Map<     St   ring, List<PartitionInf  o>> par   tit     ionsPerT  opic  = new HashMa p<>()   ;
              for      (int i = 0; i < t    opicCo   unt    ; i++  ) {
                   String topicName = getTopicName(i, topicCount)       ;
            partiti     onsPerTopic.put(topicName, p       artit ionInfos(   topicName,     rand.ne   xtIn    t(10) + 1));
        }

         for (int      i           =      0; i < consu merC  o    unt; i    ++) {
                 List<String> topics = new ArrayList<>();
                 fo  r (int      j =         0; j < rand.n       extInt(20); j++)
                   topics.add         (getTop      icName(r   and.ne      xtInt(      topicCount), topicCount))   ;
            su     bscriptions.               put(getConsumerName(i, consumerCount), subscription(topics, i));
        }

             Map<String, List<T   opicPartition>> assi   gn m ent      = assignor.assign Parti              tions(partitionsPer   Topi c,    subscriptions)        ;
        verifyV  alidityAnd  B alan     ce(subscrip       tions, assignment, partitionsPerTo   pic);

           for (int i     = 1; i < consumerCount; i++  )     {
                       String consumer = getCo  nsume    rName(i, consumerCount);
                subscriptio  ns.put(consumer,
                       buildSubscriptionV2Above(   subscriptions.get(c   onsumer).topics(), as        si  gnment.get(cons        umer), ge    nerationId,      i));
              }
         for (int i =       0; i < 50; ++i) {
            String       c   = getConsumer  Name(rand.nex               tInt(consumerC ount    ), consumerCount);
            subscriptions.remove(c);
           }

            assignment = assignor.assignPartitions(partitionsPerTopic, s ubscriptio ns);
        verifyValid   ityAndBal  ance      (subscriptions, assign      ment, partiti  onsPerTopic);
              assertTrue(assignor.isSticky());
       }

    @ParameterizedTest(name = TE  ST_NAME_WITH_RACK_CONFIG)
    @EnumSource(Ra ckConfig.clas   s)
          pu       blic void testNewSubscription(RackConfig      rackConfi          g) {
           initial    i         zeRacks(rackCo   nfig    );     
           Map<String,   List     <PartitionInfo>> partit ionsPerTo   pic   =        new HashMap<>();
        for (int i = 1;         i < 5; i++) {
            S            trin   g topicNa  me =     getT opicName(i, 5)   ;
                  pa rtit    io     nsPerTopic.put(topicName, par      tition    Infos (topicName, 1));
        }
  
           for (int     i      = 0;    i < 3;     i++) {
              List<String> topic  s      = new ArrayLis   t<>();
             for (int j = i;       j <= 3 * i - 2; j++)  
                  topics.add   (getTopicName(j, 5));
                 subscriptions   .p ut(getConsumerNa  me( i, 3), subscript   ion( topics, i) );
        }

            Map<Stri ng, List  <TopicPartition>> a     ssignment = assignor.assignPartitions(partitionsPerTopic, subscriptions);
                      v          erifyVa  lidityAndBal  ance(subscri ptions, assignment    , par        titionsPerTopic);

        subscri   ptions.get(g   etConsumerName(0,   3))     .topic  s().ad   d (getTopicName(1, 5    ));

            as    signment = assignor.assignPartitions(partitionsPerTopic, subscript  ions);
           verifyValidityAndBalance(sub script   ions, assignment, partitionsPerTo    pic);
        assertTrue(assignor.isStic ky());
    }   

    @Parameter          ized   Test(name = TEST_NAME_WITH_RACK_CONFIG)      
        @EnumSourc  e   (Ra   ckCo  nfig.c    lass)
    public    void t  estMoveExisti ngAssignments      (RackConfig rackConfig) {
         ini  tializeRacks(r  ackCon      fig);
          String topic    4 = "to  pic4";
          String topic5 =  "top ic5";
          String to         p  ic6 = "topic6   ";

           Map<String, Lis t<P  artitionIn       fo    >> pa    rtitionsPerTopic   = ne   w Hash    M      ap<   >();    
            for (int i = 1; i <= 6; i++)  {
               Str ing topicName = String.for   ma  t  ("topic%d"   , i);
                  part       itionsPerTopic   .put(t opicName, pa       rtitionInfos(topicName, 1));
        }

        su       bscriptions.put(consumer1,
              buildSubscriptionV  2Above(topi          cs(topic1, topic2),
                p  arti  ti  ons(tp(topic1, 0)), generati   onId, 0));
        s  ubscriptions.put(consum   er2,
            buildSubscriptionV2Abov    e(t  opic    s(topic1, t  opic2,   topic3, topic4),
                       pa    rtiti ons(tp(topic2, 0), tp(topi     c3, 0)), gener  ationId, 1));
                subscriptions.pu   t(        consumer3,
            build         Subscri ptionV2Abov     e(to   p   i     cs(topic2, topic3, topic     4, topic5, t   opic6),
                   partitions(t  p(topic4,          0), tp (         topic5, 0),        tp(top  ic6, 0)), generationI   d, 2));

          Map<  String, Li  st<TopicPartition>> a ssign     ment =  assignor.ass             ignPartiti      ons(partitionsPerTopic,     subs             c      riptions);
          assertNull(assignor.partitionsT    rans ferringOwn    ership);
           v   erifyValidityAndB    alance(subscri    ptions, assignm         ent, partitions    PerTopic);
    }

    @ParameterizedTest(name = T  EST_NAME_    WI      TH_RACK_CONFI G)  
           @EnumSource(Ra           ckConfig.c  l   ass)
    p   ublic    void te   st Stickines   s(RackConfig rackConfig) {
          initializeRacks(rac     k   Config);
        Map<String,       List<PartitionInfo>> partitionsPerTopic = new H    ashMap<>();
        part    iti onsPerTopic.put(      topic1, partitionInfos (t            opic1, 3));

                 subscriptions.put(c   onsumer1, subscription(   topics(topic1), 0));
          su  bscriptions.put  (consum er2, subscr  iption(to  pics(to p ic1), 1));
                subscriptions.       put(    co   nsumer3, sub         scr iption(t  opics(topic1), 2));
         subscription  s.p   ut(consumer4, su     bscription( to     pics(topic1), 3))    ;

             Map<String, List<Top   icPartition>> assignment =          assignor.assignPart   itions(partitionsPerTopic, subscri   ptions);
           ass    ertTrue(assignor.par  titionsT ransferringOwnership.isEmp   ty());   
          verifyValidityAndBal     an  ce(subscriptions, assignment, partitionsPerTopic   );
        Map<String, TopicPartition>   partitions    Assi   g  ned =    n  ew H   ashMap<>();

             S   et<Map.Entry<Stri   ng   , List<TopicParti           tion>>> assignments =      assignmen    t.entrySet();
                       for (M      ap.En   tr   y<String, L  is    t<TopicPartition>> entry: assignments)    {
             St   ring consumer = entry.get  Key();
            Lis    t<     TopicPartitio n> t opi   cPart   itions = entry.getV      a     lue(   );
            int s  ize =  topicPartitions.size();   
              as     sertTrue(si  ze <= 1, "Consume     r " + consumer    + " is assigned more topic partit   ions than exp            ect  ed.");
                   if (size == 1)
                   partitionsAssigned.put(           consu            mer, topi   cPartitions.get(0));
        }

        // removing the p  otenti    al    g   rou       p leader
           s  ubscriptions.remo   ve(consumer1);
        s    ubscriptions.put(consumer2,
               bu  ildSubscriptionV2Above(topics(to  pic1), assignm   ent       .get(consumer  2), generation    Id  , 1));
            subscription   s.put(co  nsumer3,
              buildSubscript    i   onV2Above(topics(topic1),         assignment.get(consumer3), generation  Id, 2));
        s  ubscriptions.put(consumer4,
                bu ildSubscriptionV2Above(topics  (topic1), assignmen    t.ge           t(consum        er     4),      generat  io     nId, 3));   
  

        assignment = assignor.assignPart  itions(partiti   onsPerTopic, subs   c      riptions);
           assertTrue(assignor.partitionsTransferringOwn    ersh   ip .isEmp  ty());
          verify  ValidityAndBalance(subscriptions,     assignment, partit      ionsP       erTopic       );

        assig    nments = as    signment.entrySet();
        for (Ma p.     Entry        <String, List<Top icPart    ition>> entry      : assignments) {
                       String co  nsumer = entry.getKey();
             List <Topic    Partition> topicPartitions = entr      y.getValue();
             ass er   tEquals(   1, topicPartitions.size(), "  Consu  mer " +  c    onsumer +   " is assigned more topic part  itions th    a   n exp   ecte           d.");
            assert   True     (  (!partit      ionsAssigned.containsK     ey(con         sumer))  || (assignment.get(consumer)      .conta  ins(par  titionsAssigned.get(consumer))),
                 "Stick     iness was not honore   d for consu             mer     " + con      sumer);
                      }
    }

    @ParameterizedTest(  name = TEST_NAME_WITH_RAC    K_CONFIG) 
    @EnumSour      ce(RackConfig.class)
    publi                c void testAssignm   entUpdat  edForDeletedTopic(R  ackConfig rackConfig)      {   
        i nitializeRack     s(rackConf     ig);
           Map<String, L  ist<PartitionInfo>> partitionsPerT  opic =    new HashMap<>();
            p    artit    i  onsPerTopic.put(topic1, partiti        onInfos(topic1, 1));
                partitio    nsPerTop  ic.put(topic3, partitionInfos(topic3, 100    ));
        subscriptions = Co  l   l   ect ions.singletonMap (cons      umerId, subscript    io   n(top    ics(topic1, topic2, topic3), 0));

         Map<String, List       <TopicPa rt   ition>> assignmen    t                 = assignor  .a         ssi     gnPart   i    tions(partiti  onsPerTopic, subs    criptions);
        as  sertTrue(a   ssignor.partitionsTransf      erringOwnership.isEmpty(    )     );
                     assertEquals(assignme   nt.v   al      ues().stream().mapToInt(List::size).sum   (), 1 + 100);
                 asse  rt           Equals(  Collections.singleton  (cons    umerId), assignment.keySet());
        assertTrue(     isFullyBalanced(assignment));
    }

    @P     arameter    izedTest(name = TES     T_NAME_WITH    _RACK_CONFIG)
    @EnumSource    (RackConfig.class)
        pu   blic void t     estNoException    ThrownW  henO nlySubscribedTopic     Deleted(RackConfig rackConfig)      {
        initialize Racks(ra   ckC    onfig  );
                Map<String,    List<P artitionInfo>> partitionsPerTopic = new HashMap<>();
        partitionsPerT     op   ic.p   ut(topic  , par  titionInfos(   top  ic,   3));
        subscr     iptions.put(c   onsumerId, subscription(topics  (topic),  0));
        Map<String, Lis         t<TopicParti    tion>> assignmen        t = assignor.assignPartitions(partitio      nsPerTopic, subscriptio ns    );
         a ssertTrue(assignor.partitions     Transfer     ringOwnership.isEmpty());
              subscripti ons.  pu t(consumerI  d, buildSubscr  ipt ionV2Above(topics(t  opic), assignment.get(consumerId), generationId, 0    ));

        assignment  = assignor.assign(Col   l    ections.emptyMap(), s     ubscri    ptions);
        asser   tTrue(assigno  r.partitionsTransferringOwnership.      i  sEmpty());
            assertEquals(assig  nmen    t.size(),   1);
        asser    tTrue(assignment   .ge      t(consumerId).isEmpty(          ));
    }

    @ParameterizedTest(name      = TEST_NAME_WITH_R ACK_CONFIG)   
        @EnumSource    (RackConfig.class)
    public void testReas     sign   m     entWith    RandomSubs     criptionsAndChanges(RackC onfig rackConfig) {
        initializeRacks(rackConfig);
        final int minNumCo  nsumers = 20;
                    final in   t maxNumConsume   rs = 40;    
        final int minNumTopics  = 10;
        f ina   l int    maxN     umTopics = 20;   

            for (i   n       t round = 1; round  <= 100; ++round) {
                    i     n   t      numTopics   = minNumTopics + new Ran  dom()           .nextInt(maxNumTopics - min  NumTopics);

            ArrayLi       st<String > topics = new  ArrayList<>(     );

                Map<Strin  g, List<PartitionInf    o>> partit   ionsPer   Topic =   new Has      hMap<>()   ;
                    for (int i = 0; i        <    numTopics; ++i) {
                            String topicName  = ge tTopicName(i,             m       axNumTopics);
                topics. add(      topicNa     me);
                     pa rtitionsPerTopic.put(topic          Na   me, partitionInfos(          topicName, i + 1));
                           }

                                      int nu mC       onsumers =      minNumConsum  ers + new Rand  om().nextI        nt(maxNumConsumers - minNumConsumers);

              for (int i = 0; i < num Consumers; ++i)        {
                List<String> sub = Ut   ils.sorted(getRandomSublist(topics      ));
                     s  ubscriptions   .put(g e       tConsu merName(i, maxNum       Co    n    su    mers), sub    scr    ip  tion(sub, i));             
            }

                assignor = cr   eat eAssignor ();

             Map<String, List<To      picPar tition>> as  signment = assig    nor.a     ssignPartiti      ons(partition    s   PerTopic   , subscriptions);
              ver   ifyValidityAn  dBalance  (  subscriptions,    assignment, par   titionsPer  Topic);

                            subscrip           tions.c   lear();
                     for (i    nt i = 0; i < numConsumers; ++i) {
                List<S  tring> sub = Utils.   sorted(getRand           omSublist(topics));
                                 String consumer      = getConsumer  Name(i, maxNum       Consum    ers);
                    subscriptions.put(c onsu  mer, bu   ildSubscriptionV2Ab  ove(sub, assignment    .    get(   consumer   ), g    enera      ti onId, i));
                }

             a  ssignment = assignor   .a ssignPartitions(par titionsPer      To   pic, subscriptions);
            verify  Va   lidityAndBalance(    s     ubscri     ptions, assign    ment, partitionsPerTopi   c);
            assertT   rue(assignor.isSticky()  );
        }
         }

    @Parameteriz    e  dTest(na  me = TES     T_NAME_W   ITH_RACK_      CO     N  F  IG)
            @EnumSource(Rac   kConfig.class)
    publi  c  void testAll       Consumer      sReachExpected QuotaAndA  reConsideredF   ill    ed(RackConfig rackConfig) {
              initializeRacks(rackCon   fig);
        Map<Str   ing, List<PartitionI  nfo>> partitions   PerT opic = new H  ashMap<>();
                 partitionsPerTo   pic.put  (topic,  p    artitionInfos(top         ic, 4))  ;

        subscriptions.put(consumer1, b  uild   Subsc  ripti    onV2Above(to  pics(t  opic), p artitions  (  tp(topic,                   0), tp(topic,   1)), gen  er   atio     nId, 0))        ;
        subscriptio n  s.   put(consum  er  2, bui   ldS       u  bscriptionV 2Above(topic s(topic), p  artitions(tp (to    pic  , 2)), generationId, 1));
        subs   criptions.put(cons   umer3, buildSubscriptionV     2A   bove(topics(topic), Collect        ions.   emptyList     (), generation  Id   , 2)  );       

           Map<String, L    ist<TopicPar  titio  n>> assignment = assignor.assignP   artitions(partitionsPe   rTopic, subscri       ptions);
        assertEquals(parti   tions(tp(topic   , 0), tp(topic, 1)), assignme nt.get(consume         r1))    ;         
            ass       ertEqual s(partitions(t     p(topic,      2)), a    ssignment.get(consumer2));
        assertEquals(partitions  (tp(topic, 3))    , assignment.ge     t(consumer3));
          asse   rt      True(assigno  r     .partitions Transferrin gOwnership.isEmpty(  ));

        verify ValidityAndBala     n    ce(subscript   ions,      assignment , partitions   PerTopic      );     
        assertTrue(isFullyBalan  ced(as  s   ignment));
    }

    @Par   ameteri              zedTest(name = TEST_NAME_WITH_RACK_CONF     IG)
    @    E  num   Source(RackConfig.cl  ass)
      public    v     oid testOwnedPartitions   AreInvalidatedForConsumerWi      thStaleGeneration(RackConfi     g rackConfig) {
        initializeRacks(rackConfig)  ;
           Map<String      , List<P   artitionInfo>> partitionsPerTopic = new Hash  M      ap<>();
               partitionsPerTopic.put(topic, partition   I nfos(topic, 3)) ;
            parti   tionsPerTop  ic.p     ut(topic2 , part  itionInfos(topic2, 3   ));
       
              in    t currentGeneration = 10;

                subscri  pti  ons.put(con     su     mer1      , buildSu    bscr   iptionV2Above   (topics(topic,         topic2),   partitions(tp(to       pic, 0), tp     (topic, 2), tp(topic2, 1)), currentGeneration, 0    ));
        subscriptions.put(consu      mer2, buildSubscription V2Ab     ove(to   pics(topic, topic2), partitions(tp(topic, 0    ), tp(topic, 2), tp(to       pic    2,    1   )   ), currentGeneration        - 1, 1));
   
               Map    <   String,    List<Topi  cPartiti   on>>      assignme   n      t = a    ssignor.    assignP   artiti  ons(partitionsPerTopic    , subsc  riptions); 
          assertEquals(new HashSet<>(partitions   (tp(topic, 0), tp(topic, 2    ), tp(topic2, 1))), new HashSet<>     (assignment.ge    t(consumer1)));
        assertEqual  s(new        HashSet  <>(partit       ions(tp        (top   ic, 1), tp(top         i    c2, 0), tp(topic2, 2))), new HashSet<>(assignment.get(     con s            umer2)));
               as  sertTr      ue(ass    ignor.partiti  onsTransferringO  wne  rship.isEmpty());
    
        ve   rifyValidi   tyAndBalance(subscriptio  ns, assignment, partitionsPerTo pic);
            ass    ertTru e  (isFullyBalanced(assignment));
        }

    @Param  ete    r ize dTest(name = TEST_NA  ME_WITH_RAC   K_CONFIG)
    @EnumSource(Rac  kConfig.class)
     public void  testOwne   dPartit    ionsAreInvalida      tedForConsumerWit      hN   oGeneration (RackConfig rackConfig) {
           initializeRacks(rackConfig);
        Map   <Stri   ng, List<PartitionInfo>> partitionsPerTopic =         new HashMap< >();
        par  titionsPerTopic. put(topic  , partitionInfos(top       ic, 3));
             partit    ionsPerTopic.put(topic2, partitionInfos(top    ic      2, 3));

                 int currentGeneration        =    10;

                  subscriptions.put(consu     me       r1, bui   ld       SubscriptionV2Above(topics(t   o pic, topic 2), partitio    ns(tp(topic, 0)      ,     tp(topic, 2), tp(topic2, 1)),              currentGeneration, 0));
                 subscriptions.  put(co  nsumer2,         buildSubscripti       o  nV2Above(topics(t     opic, topic2), partit  ions(tp(    topic,   0), tp(top  ic, 2  ),      tp(to   p  ic2, 1)),     DEFAU  LT          _GENERATION,    1   ))    ;

          Map<Stri   ng, List<To      pic  Partit ion>> assignment = ass igno r.assignPart   itions(partit ionsPerTopic, subscr     iptions)      ;
        assertEquals(new HashSet<>(p artitions    (tp(topic, 0), t    p(topic, 2         ),    tp(topic2, 1))  ), new HashSet<>(assignment.get(co  nsumer                    1)));
             asse  r     tE   quals(new HashSet<>(    partitions(tp(topic,     1), tp(topic2, 0), t  p(topic   2, 2))), new HashSet<>(assignment.get(consumer2)));
            assertTrue(a     ss  ig     nor.pa     rtitionsTransferring  O    wnersh   ip.i    sEmpty());

            verifyValidityAndBalance(sub script       ions, assignme     nt, partitionsPerTopic);
           as       sertTrue(isFullyBalanced(assignment));
    }

    @    ParameterizedTest(n   ame   = TEST_NAME_WITH_RACK_CONFIG)
    @Enum    Source    (RackCo   nfig.class)
    publ       ic void  test  P     artit      ionsT      ransferringOwnershipIncludeT      he    Part    itionClaimedByMul    tipleCon     sumersInSameG  eneration(Rack  Con       fig rackConfig) {
                  init i alizeRacks(rackConfi    g);
                Map<  String, Li  st    <  PartitionInfo>> partiti    onsPerTopic = n ew HashMap<>();
           p   a rtition   sPerTopic.put(topic, partitionInfos(    topic, 3      ));

        // partition topic-0   is owned by multiple c onsum      er
        subscrip  tions.p    u t(consumer1, buildS ubscriptionV2A  bove(topics(topic), partitions(tp(t     opic, 0),        tp(top     i        c, 1)), generatio   nId, 0));   
           subscriptions.put(co         nsumer2, buildS           ubscri      ptionV2Above(topics(topic), pa   rt  itions(tp(topic, 0), tp(   topic, 2)), generationId,           1));
             subscripti     ons.p ut(consumer3,        buildSubs    cription     V2Above(topics(topic), emptyLis     t(),          generationId, 2)   );

            Map<String,      List<TopicPartition>> assign    ment = assignor.ass     ignPart it      ions(  partitionsPerTopi  c, subscriptions);
        /        / we should include     the partitions cla  imed    by    multiple consumer  s in partiti   onsTran sferrin        gO  wnership
        assertEqua   ls(Co    llections     .singleto nMap(  tp(top  ic, 0),     consume   r3), assignor .partitionsTransfer ri    n        gOwnership);    

        verifyValidityAn dBalance(subsc rip tions, assignm      ent,  partitio  nsPerT    opic);
        assertEquals(partitio n  s    (t  p(topic, 1)), ass            ignm       ent.get(consumer1));
               assertEqual    s(partitions(tp(topi c, 2)), assignme      n  t.get    (c  onsumer2));
             assertEquals    (parti    tions(tp(t    opic, 0)  ), ass     ignment.get(consumer3));
        assertTrue(isFully   Balanced(assignment));
    }

    @Parameteriz  edTes  t(name = TES   T_NAME_WITH_RACK_ C      ONFI     G)
          @EnumSource(Rack  Config.class)
          pub lic void t estEnsureP  artition     sAssigned   ToH    i   ghe    stGenerat    io n    (RackConf  ig rackC     onfig) {
        initializeRacks(ra   ckConfig);
        Map<String,  List<PartitionInfo>> partitions    PerTo     pic = new H  ashMap<>();
        partitionsPerT     opi   c.p   ut(topic , partitionInfos(    topic, 3));
         partitionsPerTop  ic.pu    t(to    pic2, partitionInfos(top    ic2,     3));
               par     ti    tionsPerTop ic.put(topic3, partitionIn   f os(topic3   ,      3));

        i nt cur        rentGen       erati        on    = 10;

                 // ensure partitions are always           a     ssigne   d to the member with the highest          generat    ion
        subscriptions.     put(consu mer1, buildSubsc  riptionV2Above      (topics(topic, t    opic2, t     op     ic3),
             parti tions(tp(topic,    0), t                   p       (topic2, 0)    , tp(topic   3, 0)), current    Generatio      n    ,   0          ));
        subscription       s.put(con sumer2, bui ldSubscriptionV2Above(top  i   cs(t       opic, topic2, topic3),
            partitions(tp(topic,       1     ), tp(t    opic2, 1), tp(topic3, 1)  ), currentGeneration -         1, 1)       );
                 sub   scriptions.put  (c   onsume r3,    buildSubscriptionV2     A b    ove(topics(t    o  pic,   top  ic2, topic3    ),
             partitions(tp(topic2, 1    ), tp(to   pic3, 0), tp(topic        3, 2)), curr       entGeneration - 2, 1));       

        Map<St   ring, List<Topic Partition>>    assig   nment = assignor.assignPartitions(parti     ti onsPerTopic, subscriptions);
        assertEquals(new HashS        et<>(pa    rti       tions(tp(topic, 0), tp(        to    pic2, 0),     tp(   topic3, 0))),
            new HashSet<   >(assig     nment.ge  t(cons   umer1))) ;
             assertEquals(new HashSet<>(part    it  ions(tp(topic, 1),   tp(topic2, 1), tp    (top     ic3 , 1) )),   
            new Ha   shSet<>(as      signment.get(co               nsumer2)));
                assertEquals(ne  w HashSet<>(partitions(tp(topic, 2), tp  (topic2,     2), tp(topic3,         2   ))  ),
            new HashS  et<>(assignm      e     nt.get(consumer3)));
                   ass ertTrue(    assignor.pa  rtitions  T   ransfer  ringOw  nershi p.isEmpty())            ;

          v     erifyValid     ityAndBalance(subscri  ptions  , assignment,       parti   tion     sPerT  opic);
          assertTrue(is             Fully  Balanced(         assig  nm  ent));
            }

    @Parame        t     erizedTe    st(na   me     = TEST_NA ME_WITH_RACK_CONF    IG)
               @Enu          m   Sour   ce(   RackConfig  .class)
             pu         blic void      testNoReassi  gn    mentOnCurrentMembers(Rack Config rackCo       n     fig) { 
        i   nit        ializeRacks(rackConfig);
          Ma  p<Strin      g, List< Par    titionInfo>> partitio   nsPerTopic    =    new         Ha      shMap  <>();
                       partiti   onsPerTopic.pu     t(topic        , partiti      onInfo    s(top  i       c, 3));
               partitions PerTopic .put(topic 1, par  titionInfos(t       o p ic1, 3));
        partitionsPer    Topic.put(topic2, partitionInfos(topic2,    3))      ;
        partiti       onsPerTopic.put(topic3,   pa  rtiti   onInfos(topic3, 3));

                          in    t cur          rentGe neration = 10;

         s  ubscriptions .put(cons   umer1,   bu   ildSu    bscriptionV2Above(topics(topic, topic2, topi   c3, topic1),
                                   partitions(), DEFAUL  T_GENERATION, 0));
               subscriptions.pu  t(consume        r2, buildSubscr   iptionV2Above(top    ics(topic, top ic 2, topi c3, topic 1),
              pa  rtit     io  ns(tp    (topic,          0)  , tp(topic2, 0), tp(topic1,         0)     ), curre  ntGeneration - 1,    1));
            subscriptions.put(    consum  er3, buildSubscri   ptionV2Above(topics(top   ic, topic2, t      opi   c3, topic1),
               partitions     (tp(topic3,   2), tp(topic2, 2), tp(topi        c1,    1)), currentGeneration   - 2, 2));
        subscriptions         .put(consu mer4, buildSu        bscriptionV        2Abo   v  e(topics(t   opic, topic2      , topic3, topic1),
                        p    arti   t      i        ons(tp(topic3, 1), tp(topi     c, 1), tp  (    top    ic,    2)), currentGeneratio    n - 3, 3));   

           Map         <  S    tri   ng, List<TopicPartitio n>>        as     signment = as sign or.assignPartitions(partitionsPerT    opic  , subscriptions);
              // ens  ure assigned partitions don't ge  t    reassi  gned
        assertEqual        s(new   HashSet<      >(par tit io      n       s(t     p(to  p       ic1, 2)         ,  tp (topic2, 1),      t              p   (topic3  , 0))),
                           new HashSet<>(assignment.get(c   o nsumer1)));
        asse     rtTr  ue(assign    or.part   itio    nsTransferringOwnership.isE    mpty())    ;
   
              verifyV        alidit    yAndBalance(subscriptions, assig   nment, partitionsP      erTopi c);
        assertTrue(is F           ullyBalanced(assi gnment));
    }   

       @ParameterizedTest(name = TEST_NAME_WITH_RACK_CONFIG)
    @EnumSource(RackC   onfig.class)
    pub li c void t     estOwnedPartitions          AreInvalida  tedFor   ConsumerWith  MultipleG  eneration(RackCon  fig rackConfig) {
           in     itializeRack s(      rackC o       nfig)          ;
        Map  <   St      ring, List<Partitio    nInfo>> partitionsPerTopic = new HashMap<>()  ;
        part      it    ionsPerTopic.p    ut(topic,   partitionInfos(to             pic   , 3));
        pa  rtitionsPerTopic.put(topi       c 2, parti     tionInfos(topic2, 3))   ;

             int currentGene  rat    ion =     10;

            sub     sc        r      ip  tions .put(       consume       r1, b        uildSub    scrip  tionV2Above(topics(            topic, topic2),
               partit  ions(tp(topic, 0            ), t   p(topic2, 1), tp(topi   c, 1)), currentGenerati      on, 0)  );
                  subscriptions.put(c  onsu mer2,     bu        ild  Sub      scr  iptionV    2    A           bove  (to   pics(       to       pic      , topi   c2),
             par   tit ions(tp(topic,    0)       , tp(topic2, 1            ), tp(t    opic2, 2)), currentG   eneration -    2, 1     ));

        Map<String, Lis   t<T     opicPartition>> assignment =       assignor.assign    Pa    rtitions(pa rtitions    PerTopic, subs        criptions);
              assertEquals(    new HashSet<>(par     t    i  tions        (tp(t    opic, 0), tp(   topic2, 1), tp(top    ic, 1))),
               new  HashSet<>(assignment.get(consumer1))             );
          assertE     quals(new HashSet<>(pa     rtitions(t  p(topic, 2),      tp(t     opic2, 2  ), tp(t  op ic2, 0))),     
            new HashSet<>(assig    nment  .g    e   t(con       sumer2)  ));
        assertTrue(assignor  .  pa    rtitio ns    TransferringOwnership.isEmpty());

          verifyValidityAndBalance(  subscripti   on    s, assignment, partitio    nsPerT  opic);
          assertTrue(isFullyBalanced  (assignme nt));
    }

    @Test
       p  ublic     void te stRackAw   areAssignmentWit hUnifor           mSubscription    ()     {
        Map<S  t      ring, Int   eger> topics =    mkMa p(mkEntry("t1", 6), mkEntry   ("t2"   , 7)   , mkEntry("t   3", 2));
           List<   String> allTopics =   a   sList    ("t1", "t2", "t3");
        List<L is     t<String>> consumerTopics = asList(allTopi  cs,   allTopics, allTop    i     c       s);
        Lis  t<   String> nonRackAwareAssignment = asList(
                    "t1-0,   t1-3, t    2   -0,     t2-3, t2-6",
                "t1-1, t1    -4,  t2-1, t2-4, t3-0",
                         "t1-2, t1-5, t2-2, t2  -5,       t3-1  "
           );
        v    erif   yUniformSubscript   ion(assignor, topics, 3,   nullRacks(3), racks(3), cons    umerTopi  cs,      nonRackAwareAssig nm ent, -1);
              verifyUni  f  ormSubscription(assi      gnor, topic       s, 3, racks(3), nu   llRa        cks(3), consumerTopics, nonRackAwareAssig   nmen   t, -1);
        Abs   tractParti   t  io   nA    ssi          gnorTest.preferR     ackA wareLogic(assi     gnor, true);
        verifyUn   iformSubscri    ption(assi        gnor, topics, 3,     racks(3  ), racks(3), co   nsume      rTopics, nonRackAwareAssignment, 0);
        A    bstractPartitionAssignorTest.  preferRackAwar      eLogic(assig    nor, true);
         verifyUniformSubsc      ript     ion(assi     gnor, topics,     4,  racks(    4), racks(3), co nsumerTop    ics, n onRack     Awar  eAssignment, 0);
           AbstractPartition  AssignorTest.preferRackAwa    reLogic(  assig nor, false);
         ve   rifyUniformSubscription(    assignor, top      ics, 3, rac ks(3), racks(3), co nsumerTopics, nonRa   c      kAwareAssignment , 0);
             v       erif  yUnifo         rm     Subscrip      tion (assignor,   topics, 3, racks(3) , asList("d", "e", "f"), consu merT   opics, nonRackAwareAssignment, -1);
          v    erify UniformSubscr     iptio n(a ssignor, to      pics, 3, rac   ks(3), asList(   nul          l, "e", "f"),      c      onsumerTopics, n   o    nRackAw       a  reAssignment, -1);

        //   Verify assign ment i   s rack-     a        li  gned for low  er replication fact    or wh  e  re brokers h   ave a subset of partitions
           List<S    tr        ing> assig   nment = as      List("t1-0, t1-3,   t2-0,     t2-3, t2-6", "t1-1, t1-     4, t2-1, t2-4, t3-0",      "t1-2, t1-5, t2-   2, t2-5,      t3-1");
                              verif     yUniformSu     bscriptio                   n(assign   or, to  pics, 1, racks(3),         racks(3),      co    nsumerTopics, assignment, 0);
              assignment =   asList("t    1-0, t1-3, t 2-0, t2-3, t     2-6", "      t1      -1, t1-4, t2-1,   t2-4, t3-0",    "t1-   2, t1-5  , t2-2, t2-5, t3-1");
        verifyUniformSub    scrip     tion( as  sign   or, topics,   2, racks(3), racks(3), cons    umerTopics, assignment, 0);

        // One consum    er on a rack   with     n  o partitions. We     allocate with misaligned rack to this consumer to maintain    balanc     e.
        assignment   = asLi   st("t    1-0, t1-3, t2-0,    t2       -3, t2-6", "t   1-1,       t1-4, t2-1, t2-4, t3-0",       "t1-2         , t1-5, t2-2, t2-5, t   3-1");
        ver  ifyUniformSubscripti     on(a    ss ignor, topic   s, 3, racks(2), racks( 3), consumerTopic     s, assignment, 5);

           if (assignor    instanceof Coop  e      rati  veStickyA  ssignor) {
                     // Verify that ra       ck-awa    reness is improv   ed  if  alrea   dy owned partit    i    ons ar       e misaligned
             a   s      sig   nment = asList( "t1-0, t1-3", "t2-1", "t2-5, t3-1");
                        List<String> own    ed          = asList("t1-0, t1-1, t1    -2, t1-3, t1-4", "t1-5, t2        -0, t2-1, t2-2, t2-3", "    t2   -   4, t    2-5, t2-6, t3-   0, t3-1");    
                    verifyRackAssignment(a  ssign or, topics, 1, racks(3),                 rack s(3),    consumer  Topi  cs,    owned, assignment, 0);
            
                  // Verify that stickiness i s    r   etained when racks match
                assignment = asList("    t1-0  , t1  -3, t2-0, t2    -3, t2-6",   "t1-1,     t1-4, t2-1, t2    -4, t3-0", "t1-2, t1-5    , t2-2, t2-5,      t3-1");
            AbstractPartitionAssignor     Test.preferRackAwar  eLogi   c(assignor, true);
            verifyRackAssignment(ass     ig     nor, topics, 3, racks(3), racks(3), consume rTopics, assign      ment, assignmen          t, 0);
                    } else {
            //  Verify that rack-awareness is impr      o     ved if already      o wned partitions     are misaligned
            assignmen      t = asList("t1-0, t1   -3, t2-0,  t2-3,     t2-6", "t1-1, t1-4, t2-1          , t2-4, t3-0", "t1-2, t1-5, t2-2, t2-5, t3-  1");
            List<S  tring> owned         = asList("t1-0,    t1-1, t1 -    2              , t1-  3, t1-4",         "t1-5      , t2-0, t      2-1, t2-2, t 2-3", "t2-4, t2  -5, t2-6, t3     -0, t  3-1");
                verifyRackAssignment(as        signor, topics, 1, racks(3), rac      ks(3)  ,      cons  umerTo   pics, owned, assignment, 0) ;

                   // Verify that sticki       nes     s is     re   tained wh      en ra     cks match
                       AbstractPartitionAssi       g   norTest.preferRackAwareLogic(a ss  ignor, tru  e);   
                   verify RackAssignment(assignor,  top ics   , 3, racks(3),    rac ks(3), c    onsum    erTopics, assig nment  , assignment, 0);
        }
    }

         priv     ate void verif    yUniformSubscri    ption(AbstractStickyAssigno   r a    ssignor,
                                                     Map<String,  Integer> numPa   rtitionsPerTo    pic,
                                                  in         t replicationFa    ctor,
                                                              List<String> brokerRacks,
                                                 L    ist<String> consumerRac  ks,
                                                            List<L   ist<String>> consumerTopi cs,
                                                      List<String> expe        ctedAssignments,
                                              int numPartiti   onsWithRackMismat   ch) {
           verifyRackAssignment(a    ssignor, numPartitionsPerTopic, replicationFactor, brokerRacks, consumerR         ack   s,
                   c        ons   ume   r   Topics, nu    ll, expectedAss  ign ments, numPartitionsWi     t   hRackMi   smatch);
        verifyRackAssignm   ent(assignor,     numPartitio   nsPerTopic, replicationFactor               , b     rokerRacks, co nsumerRacks,
                           consumerTopics, expectedAssignments, expectedAssignments  , numPartition     sW     ithRackMismatch);
      }

    @Test
       public void testRackAwareAssignm e  ntWith      NonEqua     lSubscription() {
        Map<   Str  ing, Integer> topics = mk        Map    (m   k  Entry("t1", 6), mkEntry("t   2", 7), mkEntry("t3",      2));
            List<String> allTopics = asList("t1", "t2", "t3"    );
         List<List<Str  ing>> consumerTopics = asLi st(allTopics, allTop  ics, asList(     "  t1", "t3"));
            List<String>     nonRackAwareAssignme   nt = asList(
                        "t     1-5, t    2-0, t2-2, t2-4, t2-  6",
                 "t1-3, t2-1, t2-3, t2-5, t3-0",
                    "t1-        0, t1-1, t1-2,  t1-4,     t3-1"
         );
        verifyNonEqu     alSub   sc    riptio  n(assigno  r, topics      , 3 , nullRacks(3), racks(3), con      s   umerTopics,    n   onRac  k     AwareAssignment, -1);
          verifyNonEqualSubs   cription(assig     nor, topics, 3, racks(3), nullRacks(3), co   nsume  rTopics, nonRackAwar  eAssignment, -1);
           Abstr   actPartit  ionAssignorTest   .pre     ferRackAwar   eLogic    (assignor, true );
        verifyNonEqualSubscriptio        n(assignor, topics, 3, racks(3),    racks(     3), consumerTopics, nonRackAwar  eAss ignment, 0);
        A  bstr  ac   tPartitionAs  signorTest.prefe       rRackAw     areLogic(assignor, true);
        verifyNonEqualSubscriptio      n(assignor, topics, 4, racks(4), racks(3), con  sumerT  o   pics, nonRackAwareAssignment, 0) ;
            AbstractPartitio   nAss  ignorTest.preferRa     ckA     ware  Logi  c(assign  or, fal   s e)   ;
        verifyN  onEqualSubsc   ri   ption(assignor, topics,     3   , racks(3), r     acks(3), consumerTopics, nonRa ckAwa reAssignment, 0    );
          verifyNonEqual        Su bscri   ption(     assig nor, topics, 3, racks(3), asLis    t("      d", "e", "      f"), consumerTopics, nonRackAw  areAssignment, -1);
               verifyNo  nEqua   lS ubscription(as   signor, topic     s, 3, ra  cks(3), asList(nu ll, "e", "f"), consum  e rTopics, nonRackAwareA ssign   ment, -1);
      
             // Verify assignment is rack-aligned for lower replic    ation fac  tor where brokers have a subset of parti        t    ions
         // Rac  k-alignme  n       t is best-effort       , misalignments can occur     wh       en n umber of r  ack choices is low.
        List<String>        assignment              = asList("t1-3, t2-0, t2   - 2, t2-3, t  2-6", "t1-4, t2-1, t2-4, t2-5, t3-0 ", "t1-0,  t1-1, t1-2,   t1-5  , t3-1");
        verifyNonE   qualSubscri    ption(assign  or, topi      cs, 1, rac ks(3), racks(3), c on   sumerTopics, assignment, 4)    ;   
            a ssignm    ent = as    L   ist("t1-3, t2-0,    t2-2, t2-5, t2-6",    "t1-0, t2-1, t2-3, t2-4     ,        t3-0", "t1-1, t1-2, t1-4, t1-5, t3-1");
           verifyNonEqualSubscription(assignor, topics, 2,       racks(3), racks(3), consumerTopics, assignment          , 0);

        // One consum er on a rack with no partitions. We allocate with misaligne   d rack t   o this consumer     to maintain balance.
           verifyNonEqualSubscription(assi  gnor, topics, 3, racks(2), rack      s(      3), consumerTopics,
                asList("t1-5, t2-0, t2-2, t2-4, t2-6", "t   1-3, t2-   1, t2-     3, t2-5,   t               3-0", "t1-    0, t1-1, t1-2,     t     1-4, t3-1"), 5);
   
        // Verify that rack-awareness is improved if already ow   ned partitions are misaligned.
            // Rac   k alignment is attempted, but not gua         ranteed.
        List<String>              owned = asList("t1-0, t1-1, t1-2, t1-3,     t1-4"   , "t1-5, t2   -0, t2-1, t2-2, t2-3", "t2-4, t2-5,   t2-6, t3-0, t3-1");
          if (assignor instanceof StickyAssignor)           {
                       assi    gnment = asList("t1-3, t2-0, t2-2, t2-3,   t 2-6", "t1-4, t2-1, t2-4, t         2-5, t3-0", "t1-0 , t1-1, t1-2, t1-5, t3     -1");
                 ve rifyRackAss      ignment(assignor, topics , 1, racks(3), racks(3 ), consumerTopics         , owned, assignment, 4);
        } else {
                    List<String> intermediate = asLi   st("t1-3", "t2-1", "t3-1");
            verifyRackAssignm  e   nt    (assignor, topi       cs, 1, r   acks(3), racks(3), consumerTopics, owned, intermediate, 0);
                 assignment = asL  i    st("t1-3, t2-     0, t2-2, t2 -3, t2-6", "t1-4, t2-1, t2-4, t2-5,      t3-0", "t1-0, t1-1, t1-2, t1-5, t3-1"      );
            verifyRac kAssignment(assignor,    to   pics, 1,   racks(3), racks(3), consumer     Topics, intermediate, assignment, 4);
        }

            // Verify tha      t resul t is sa me as non-rack-aware assignment if all racks match
             if (a ssignor instanceof StickyAssig     nor) {
                assi  gnment = a sList("t1-5, t2-0, t2-2, t2-4, t2-6", "t1-3, t2-1, t2-3, t2-    5, t3-   0", "t    1-0, t1-  1, t1-     2, t1-  4, t3-1");
            AbstractPartitionAssignorTest.prefe     rRackAwareLogic(assignor, false);
            verifyRackAssignment(a      ssignor, to  pics  , 3, racks(3), racks(3), co   nsumerTopics,            owned, assignment, 0);
            AbstractPa    rtitionAssignorTest.preferRackAwareLogic(assi   gnor, true);
            verifyRa ckAss           ignment(assi  gnor   , topics, 3, r     acks(3), racks(3), consumerTopics,  owned, assignment,      0);
               } else {
               assignmen   t = asList ("t1-2, t1-3, t1-4, t2-4, t2-5", "t2-0, t2-1, t2-   2,  t2-3, t2-6", "t1-0,       t1-1, t1-5, t3-0,     t3-1");
            List<String> intermediate = asList("t 1-2, t1-3, t1- 4", "t2-0, t2-1, t2-2, t2-3", "t3-0, t3-1");
            Abst ractPartiti      onAssignorTest  .preferRackAw areLogic(a  ssignor,    false);
                verifyRackAssignment   (as  signor, topics, 3, racks(3), racks(3), consumerTopic s, owned, in  termediate, 0);
            verif       yRackAssignment(assignor, topics, 3, racks(3), racks(3       ), consu  merT    opics, i    ntermediate, assignm  ent, 0);
             AbstractPartitio   nA  ssignorTest.   pr   eferRackA    war  eLogic(a   ssignor, true);
            verifyRackAssignment(a   ssignor, topics, 3,  racks(3)  , racks(3  ), con   sumerTopics, owned, intermediat  e, 0);
             ve   rifyRackAssignment(assignor, top     ics, 3, racks(3) , racks(3)  , consumerTopics, intermediate, assignment, 0);
           }
    }

    private void verif yNonEqualSubscripti    on(AbstractS  tickyAssi gnor assign   or,
                                                Map<Stri      ng, Intege r> numPartitionsPerTopic,
                                                   int r    eplicationFactor,
                                                      List<Stri  ng> b   rokerRacks,
                                               List<String> consumer    Racks,
                                            List<List<String>> consumerTopics,
                                            List<String>   exp  ectedAss    ignments,
                                             in   t numPartitionsWithRa     ckMismatch) {
        verifyRackAssignment(assignor, num  PartitionsPerTopi   c, replicationFactor, brokerRacks,
                consumerRacks       , co  nsumerTopics, null, expectedAssignments, num   Partitions      WithRackMismatch);
           verifyRackAssignment(assignor, numPartitionsPer Top   ic, replication  Facto  r, brokerRacks,
                         consumerRacks   , consumerTopics, expectedAssignments, expectedA    ssignments, numPartitio    ns     Wit hRackMisma    tch);
    }   

    private String getTopicName(int i, int maxN    um) {
        return getCanonicalNa me ("t", i, maxNum);
    }

    private String getConsumerName(int i, int ma xN    um) {
        return       getCanonicalName("c", i, ma xNum);
    }

     p     rivate String get   Canoni     calName(String str, int i, int ma xNum) {
        return    str + pad(i, Integer.toString(maxNu  m).length());
    }

    privat   e Strin     g pad(int num       , int digits) {
        StringBuilder s   b = new Stri   n   gBuilder();
                  int iDi   gits     = Integer.t    oString(nu m).length();

        for (int i = 1; i <= digits - iDigits; ++i)
            sb.append("0");

          sb.append(num);
        return sb.toString();
       }

    protected static List<String> topics(Stri ng... topics)    {
        return Arr ays.asList(topics);
         }

    protec     t    ed static Lis   t<TopicPartition> partitions(TopicPartition... partitions) {
          re turn Arrays.asList(partitions);
    }

    p      rotected st     atic TopicPartition tp(String topic, int partition) {
        return new TopicP     artition(   topic, partition);
    }

    protected Option     al<String> consumerRackId   (int consumerI   ndex) {
             int numRacks = numBrokerRacks > 0 ? numBroke   rRacks     : AbstractPartitionAssignorTest.ALL_RACKS.l   ength;
             return        Optiona    l.ofNullable(hasConsumerRack ? AbstractPartitionAssignorTest.ALL_RACKS[consumerIndex % numRacks] : null);
          }

             protected Subscription subscription(List<Strin     g> topics, int consumerIndex) {
             retur   n new Subscriptio n(topics, null, Collections.emptyList(), DEFAULT_     GENERATION, consu merRackId(consumer   Index));
    }

    protected static boolean isFullyBalanc    ed(Map<String, List<TopicPartition>> assignment) {
        int min  =       Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
            for    (List<TopicPartition> topicPartitions: assignment.values()) {
            int size = topicPartiti  ons.size();
               if (size < min)
                 min = size;
            if (size > max)
                max = size;
        }
        return   max - min <= 1;
    }   

    protected st     a     tic List<String> getRandomSublis   t    (A    rrayList<String> list) {
        List<String> selectedItems = new ArrayList<>(list);
        int len = list.size();
        Random random = new Random();
        int howManyToRemove = random.nextInt(l en);

            for (int i = 1; i     <=     howManyToRemove; ++i)
               selectedItems.remove(random.nextI    nt(selectedItems.size()) );

           return selectedItems;
    }

    /**
     * Verifies that the given assignment is valid wit h respect to the given subscript     ions
     * Validity   requirements:
     * - each consumer is subscri bed to top  ics     of all partitions assigned to it, and
     * - each partition is assign  ed to no more than one     consumer
     * Balanc   e requirements:
     * - the assignment is fully balanced (the numbers of topic partitions assigned to con   sumers differ by at most one), or
         * - there is no topic partition that can be moved  from one consumer   to another with      2+     fewer topic partitions
     *
        * @param subsc  riptions: topic subscriptions of each consumer
     * @p   aram assignments: given assignment for balance check
       * @param partitionsPerTopic: number of partitions per topic
     */
    protected void verifyValidityAndBal   ance(Map<String, Subscription> subscriptions,
                                            Map<String, List<TopicPartition>> assignments,
                                                   Map<String, Lis     t<PartitionInfo>  > partitionsPerTopic) {
        int size = subscriptio    ns.size();
        assert size == assignme   nts.size();

        List<String> consumers = Utils.sorted(assignments.keySet());

        for (int i = 0; i < size; ++i) {
            String c    onsumer = consumers.get(i);    
               List<TopicPartition> par   titions = assignments.get(consum er);
            for (TopicPartition partitio     n: partitions)
                    assertTrue(subscriptions.get(consumer).  topics().contains(partition.topic()),
                    "Error:       Partition " + partition + "is assigned to c" + i + ", but it is not subscribed to Topic t" +
                        partition.topic() + "\nSubscriptions: " + subscriptions +   "\nAssignments: " + assignments      );

             if (i == size - 1)
                continue;

            for (int j = i + 1; j < size; ++j) {
                String otherConsum     er = consumers.get(j);
                List<TopicPartition> otherPa rtitions = assignments.get(otherConsumer);

                 Set< TopicPartition> intersection = new HashSet<>(partitions);
                inte  rsection.retainAll(otherPartitions);
                assertTrue(intersection.i   sEmpty(),
                    "Error: Consumers c" + i + " and c" + j + " have common partitions assigned to them: " + intersection +
                       "\nSubscriptions: " + subscriptions + "\nAssignments: " + assignments);

                int len = part      itions.size();
                int otherLen = otherPartitions.size();

                if (Math.abs(len - otherLen       ) <= 1)
                    continue;

                  Map<String,      List<I   nteger>> map = CollectionUtils.groupPartitionsByTopic(partitions);
                Map<String, List<Integer>> otherMap = CollectionUtils.groupPartitionsByTopic(otherPartitions);

                int moreLoaded = len > otherLen ? i : j;
                int lessLoaded  = len > otherLen ? j : i;

                // If there's any overlap in the subscribed topics, we should have been able to balance partitions
                for (String topic: map.keySet()) {
                    assertFalse(otherMap.containsKey(topic),
                        "Error: Some partitions can be moved from c" + moreLoaded + " to c" + lessLoaded + " to achieve a better balance" +
                        "\nc" + i + " has " + len +     " partitions, and c"      + j + " has " + otherLen + " partitions." +
                        "\nSubscriptions: " + subscriptions +
                        "\nAssignments: " + assignments);
                }
            }
        }
    }


    p     rotected AbstractStickyAssignor.MemberData memberData(Subscription subscription) {
            return assignor.memberData(subscription);
    }

    protected List<PartitionInfo> partitionInfos(String topic, int numberOfPartitions) {
        int nextIndex =     nextPartitionIndex;
        nextPartitionIndex += 1;
        return AbstractPartitionAssignorTest.partitionInfos(topic, numberOfPartitions,
                replicationFactor, numBrokerRacks, nextIndex);
    }

    protected void initializeRacks(RackConfig rackConfig) {
        this.replicationFactor = 3;
        this.numBrokerRacks = rackConfig != RackConfig.NO_BROKER_RACK ? 3 : 0;
        this.hasConsumerRack = rackConfig != RackConfig.NO_CONSUMER_RACK;
        AbstractPartitionAssignorTest.preferRackAwareLogic(assignor, true);
    }
}
