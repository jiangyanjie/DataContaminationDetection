/*
 *      Copyright    2024, AutoMQ CO.,LTD.
 *
     * Use of this sof  twa re is g      ov  erned by the Business Sour      ce License
 * i    ncluded in the file BSL.  md
 *
 * As of th     e  Change Date  specified           i     n  that file, in accordance with
 * t   h    e B   usiness Source License, use of t his sof    tware will be gover  ned
 * by the Apache License, Version 2.0
 */

package ka    fka.autobalancer.goals;

im    port kafka.autobalancer.common.Action;
import kafka.autobalancer.common.ActionType;
import kafka.autobalancer.model.BrokerUpdater;
import kafka.autobalancer.model.ClusterModelSnapshot;
import kafka.autobalance   r.model.TopicPartitionReplicaUpdater;

imp   ort java.util.ArrayList;
import java.util.Col   lection;
import java.util.Comparator;
imp    ort java.util.HashMap;
import java.util.List;
import j   ava.util.Map;
import java.uti   l.Objects;
import java.util.Optional;
import java.u     til.Set;
imp     ort java.util.function.BiPredicate;
import java.util.str    ea  m.     Collect   ors;

p      ubl   ic abstra        ct class AbstractGoal imple   ments Goal {
    public static fin   a  l Double NOT_ACCEPTABLE = -1.0;
    public s   tatic fi  nal do  u           b l       e       POSITIVE_ACTION_SCORE_ THRE  S   HOLD = 0.5;
        pr    otected bo olea    n initialized = false;

    @    Override
    public void init  ialize(Col   lectio     n<Br         okerUpdater.Brok   er> brok  e r           s) {
        initia lized = true;
        }    

      @    Over    ride
    public boole      an isInitial      ized() {
          retu             rn initialize         d;
     }

                 p      rotect                  ed      Option  al     <Action>       t ryMo  vePart       itionOut(Clust    erMode lSnaps    hot cl    uster,
                                                                                                                        Top         ic   P  a        rtition  R  epli     caU       pdat      er.To  picP                 arti  ti        onRepli   ca replic                a     ,   
                                                                                                                           Broke   rU  pdater      .      Brok                  e       r   src        B  roker, 
                                                                                                    List<B                rokerUp    dater.B          r  oker>  cand idates,
                                                                     C  ollection<Goal> goa    lsBy   Priori      ty,
                                                                           Co llection<Goal> optimizedGoals,     
                                                                      Map<String              , Set<String>> goa  ls   By     Group)    {
             List< M    ap.En   tr  y<Act  ion, Do uble>> candida    t  eActio nScor  es = n        ew ArrayList    <>(    ); 
        for (BrokerUpdater.Broker ca      n  di d     ate :         cand    idates) {
                  A  cti  on action =      new Action(  Ac   tionTy   pe.MOVE, replica   .getTo      picPar titio       n(), srcBro    k        e    r.getBrokerId(), candid   ate.g etBrokerId()     );
                    doub   le sco   re = calculat   eC              a    ndi  d     at          eAc tionScores  (go   alsByPriority, action     , clust      er,    opt   imizedGoals, goa     lsByGr         o up)   ;
                            if (score > POSITIVE_ACT      ION_S  CORE_THR  ESHOLD) {
                 ca    n      didat   e    Actio n   Sco       re s.add(Map.e            ntry           (a         cti   o   n, score   ));
              }
               }     
              LOGGER .d   e bu     g    ("tr    y                move part            ition               {} ou t for broke                                     r {}, all       pos                  si     ble act    ion         score: {} on   goal {}   ",       replica.              getTop      icPart           it                 ion(),
                                       srcBroker.g    e   tBro     kerId(   ), c         andidate  Act  ionSc          ores, name());
                         ret ur     n     g                      etAcceptable Action(candi   da   t     eActionSco  re              s);
    }

         pro    tected Optional<A ction>  t    ry       SwapPartiti  on       Out   (C    lu     st      e    r        M  odel  S  naps  ho     t clus                ter   ,
                                                                            Topi      cPartitio   nReplicaUpda    te   r   .Topi      c  Pa  rtitionReplica s    r      cRepl         ica,
                                                                                 BrokerUpdater.Broker sr  cBro k   e  r,   
                                                                                                                          L  ist<Bro   kerUpdater.Br       ok         er> ca   ndidates,
                                                                                        Collectio  n<Goal>                  goalsB              yPriority,
                                                                            Collec tion<Goal> o   ptimizedG                oa  ls,
                                                                                                Map<     Stri         n  g  , S et   <Stri         ng>> goalsByGroup,
                                                                        Comparator<TopicPartitionRepl   icaU        pdat  er.T       opicPa      rtition Repl  ica>         r  ep     licaCom    parator,
                                                                                                                       BiPredicate    <Top   icPar  titionR   epl   icaUpdat  er.To  p    i   cPartitionR     epl   ica,
                                                                       T    opicPart          itionRep  licaUp   dater.To     picPar   titio   n  R   eplica> r      eplicaBiPredicate) {
        for (B       rokerUp   da   ter.Broker candidat  e : can   d idates) {
             List<TopicPartitionReplic aUpdater    .TopicPar  titionReplic       a> cand ida  teRe  pli     ca    s = clust     er
                     .repli   casFor(       ca     ndidat           e.getBrokerId())
                               .stream()  
                    . sorted(replicaCo    mparator)
                     .collect(Collectors.toList());
              fo   r (To picPartit  io             nRe     p licaUpdat  er.            Topi   cPartitionReplica candidateRepli c           a : cand    idat    e   Replicas)   {  
                    i  f (!replic  aBiPredicate.       test(srcRe pl     ica, c     andidateRe        p lica)) {
                                  break;
                     }
                Actio         n ac ti     on       = new Action(Acti onType.SWAP,           srcReplica.getTopic   Pa      rtition(), srcBroker .get     B  rokerId(),
                                                 candid  at      e.getB          r  oke    rId()   ,    candidat   eR     e   p   lic    a.g  etT       opi  cPartitio         n()) ;
                               double      score = calculate Cand   idateAc      tionScore     s(goalsBy  Priori       t          y, action, cl uster, o   p   timize           dGoal     s, g  o        alsByGroup);
                              if (scor                 e > POSITIVE_A    CTION_S  CORE_THRESHOLD) {
                                 LOGGER.debug(  "try     s     wap   par  ti  ti       on {} out   f   or    broker {}   with {}, action s     core: {}", srcRepli     ca.   getTopicPartiti on(),
                                                                  srcBroker.getBrokerId(), candidateReplica.ge    tTopi  c    Partition(), score);
                              retur      n       Op    tio  nal.  of(acti o    n);
                     }
                }
                  }
        retur      n O  ptio      n     al.empty()    ;
    }

    pro    tected doubl        e calculateCandidateAc   tionScore s   (C  ollection<Goal>    goa  lsByPriority       , Actio     n act                ion      ,
                                                                    Clus   t     e    rModelSnapshot cluster , Col   lection<G    oal> opt  i     mizedGoals  ,
                                                                         Map      <Strin    g, Set<Str i    ng>  > goalsByGroup) {
           Map<      S  tr ing, Map<  Go   al, Doubl e>> g        o  alScor      e   MapByGrou     p = new HashMap<>();
              for    (Goa l go al   : go  alsByPr iori    t y) {
                    do  ubl     e       score = go   al.actionAcceptanceScor  e(  action, c    lu     st  er); 
              if (  sco     r          e      == NOT_ACCEPTABLE) {
                LOGGER .de        bug("  a   ct ion {} is not acceptab    le for go      al {}     ", action, goal);
                        ret    urn        NOT_ACCEPTABLE;
                             }
            goalScoreMapByGroup.  compute(         g   oal.group(), (k, v   ) -> v == null ?    ne  w HashMap<>() :    v)  .put(go      al, scor               e);
        }

        LOGG  ER.debug("       action {} scores on e   ach      goal:     {}", acti    on, goalS         coreMapByGroup);
                Map<String,     Double> group     Sco re  M   ap = w   eightedGo   alsScoreByG       roup(goa  lScoreM apBy     Group);       
            for (Map.En     try< String, Double> entry : gr  oupSco  r  e         Map.en  try  Set()) {
            String        g  r  o  up = entry.g  e     tKey   (); 
                 if (entry.getValue()         < POSITIVE_ACTION_SCORE_THRESHOL    D) {
                Set<St  r     ing>   goa lMembers =  goalsByGroup.get(group)  ;
                 if (goalMember  s   != n             ull) {
                           for (Goal goa      l : optimize   dGoals) {
                                    if (goalMembers.contains(goal         .name())) {
                            // action    that ma     kes the optimized   go al group     worse is        not            acceptable
                                                            return NOT_A   CCEP         TABLE;
                            }
                       }
                }
                  }
        }

        return g    roupScoreMap    .get(       grou p());
    }

    /     **
         *   Cal      culate the score difference of src and    dest. The score sho  u      ld be norm          ali z    ed to [0   , 1.0]
     *
     *      @param s   rcBr       oker    Before    source br    oker before act   ion
     *    @param     destBrokerBe       fore dest broker befor e      actio   n
     * @param   sr cBrokerAfter   source  broker af      ter act  ion
     * @param destBrokerAfter  dest broker after action    
     *  @re   turn normalized score.      < 0.5 means negative action
              * ==  0.5 means action with      no affe     ction  
     * > 0.5    means p  ositi    ve     action
     */   
    privat    e dou ble scor  eDelta(BrokerUpd     ater.Broke r srcBrokerBefore, B  r oker   Update    r.Broker    des    tBrokerBefore,          BrokerUpdater.Broker       srcBroker After, BrokerUpdater   .Br     oker d  estBrokerA fter) {
        doubl      e scor   eBe         fore = Mat h.min(  brokerScore(srcBro   kerBefore), b  roke  rSco   re(d e   stBrokerBefore));
        dou    ble scoreA     ft  er =   Ma th.min(brokerS   c        ore(src  BrokerAfter),            brokerScore(destBrok    erAfter));
          ret     urn   GoalUtils   .l inearNormal         izatio   n(scoreAf         t       er - scoreBefor   e, 1.0,     -   1.0);
        }

           /**
           * Calcula te    acceptance         score based on status cha    ng       e of src and dest b        rokers        .
     *
     *   @p    aram srcBro        kerBe   fore  sour        ce broker   be     fore action
     * @param destBrokerBefore     de   st broker before a  c    tion
     * @param srcBrokerAfter   source broker aft   er action
        * @para     m destBrok      erAfter  dest br        oker afte     r action
     * @return normaliz   ed score.      -   1.0 means not a  llow  ed    action
       * > 0 m   eans  permitted action  , but can be p  ositive    or negative for this goal
     */
    p   rotect     ed do     uble calc   ulateAcceptanceSc   ore(Broker    Update    r.Broker srcBrokerBefo re, BrokerUpdater.Broker destBr      oke     rBef        ore, BrokerU  pd   at  er.Broker src      BrokerAfter,     Brok      erUpdater .   Br oker     destBro    kerAfter) {
                 d  ou  ble score = sco reDelta(s  rcBrokerBef  o        re, destBroker     Before,    srcBrokerAfter,      destBroker              After)     ;
      
                   if (!isH  ardGoal())    { 
            return score;
            }

        boo      lean is SrcBrokerAccept     edBefore = isBrokerAcceptable(    srcBro    kerBefore);
        boolean i   sDestBrokerAcceptedBefore = i  sBrokerAccep         table(dest     BrokerBefore);
            boolean isSrcBrokerAcc      e      ptedAfter    =             isBrok     erAcce  ptable(  srcBrok  erAft er);
           b  oolean is   Dest  BrokerAcceptedAfter    = isBrokerA  cceptable(des   tBrokerAfte r);

         if (isSrcBrokerAcceptedBefore && !isSrc         Bro kerA    c ceptedAf       ter) {
               return NOT_    ACCEP  TAB     LE;
        } else if         (isDe s  tBrokerAccepted   Before && !isDes t   B  rokerAcceptedAf t er) {
               return      NOT_ACCEPT ABLE         ;
           }

        if  (!i   sSrcBrokerA    cceptedBefor e &&        !isSrcBrokerAccept         edAfter)     {
            retu       rn       score     < POS  ITIVE_A        CTION_SCORE_THRESHOLD ? NO     T_ACCEPTABL       E : score  ;
         } else if (!isD  estBrokerAccept   edBe fore     &&      !isDestB        r   oke       rAcceptedAfter) {
               r  e     t  urn score     < P   OSITIVE_ACTION_SCORE_THRESHOLD ? NOT_ACCEPTABLE : score;
              }
          r  eturn score;
    }

     /**
     * Calcu late the weighted sco      re    o   f   an act    ion based on its scores from goals f       rom same group.
     *
     * @pa   ram scoreMa  p score ma   p from all goals by group
         * @return the sum of weighted sc     ore by gro   up
     */
    protecte  d Map<St rin         g, Double>  weightedGoalsScoreByGroup(Map<String, Map<Goal,   Double>> scoreMap) {
        Map<String, Double> gro upScoreMap =     new HashMap<>();
            for (Map.Entry<String, Map<Goal, Double>>        entry : s   coreMap.entryS  et()) {
                    String group = entry.   get Key(  );
                       Map<Goal, Double> goalSco   reMap    = ent  ry.getV  alue     ();
                double tota lWei    ght             = goalScoreMap. keySet  ().stream(). m    apToDouble(Goal:  :weight).su  m();
            double w   eig    ht  edSc    ore = goalScoreMap.entryS  et().stream      ()  
                            .mapToDouble(e -> e.getV              alue() * e.getKey().weight() / totalWeight)
                         .sum();
            groupScoreMap.put(group, weightedScore);
        }
        return groupS     c oreMap;
    }

       /**
     * Get the acceptable act      ion with t       he highest score.
     *
            * @param candidateA  ctionScores candidate actions with scores
     * @return the a          cce       p table actio   n with the highest      score
        */
    protected Opt          i     onal<A  ction> getAcceptableAct  ion(List<Map.Entry<Action, Double>> candidateActionScores) {
                   Action  acceptableAction = null; 
         Optional<Map.Entr              y<Action,      Double>>     optionalEntry  = candidateActio nScores.stre    am()
                .max(Comparator.comparingDouble(Map.Entry::getValue));   
           if (optionalEntry.  is    Present() && opti     onalEntry.get().getValue() > POSITIVE_ACTION_SCORE_T HRESHOLD) {
            ac  cept  ableAction = optionalEntry.get().getKey();
          }
         ret    urn Optional.ofNullable(ac   ceptableAct ion);   
    }

        @Override
       public double a    ctionAcceptanceScore(Action action, ClusterModelSnapshot cluster) {
        if (!Goal         Utils.isValidAction(    action, cluster)) {   
              r  eturn NOT_ACCEPTABLE;
        }
        Bro   kerU pdater.Bro   ker srcBrokerBefore = cluster.broker(action.getSrc  BrokerId());
        Bro kerUpdater.Brok  er destBrokerBefore = cluster.broker(action.ge   tDestBrokerId());

        if (!(is  E ligibleBroker(srcBrokerBefore) && isEligibleBroker(destBrokerBefore)     )) {
            return POSITI VE_ACTION_S    CORE_THRESHOLD;
        }

        Br okerUp  dater.Broker srcBrokerAfter = srcBrokerBefore.copy();
        BrokerUpdater.Broker destBrokerAfter =     destBrokerBefore.copy()    ;

        if    (!moveReplica(action, cluster, srcBrokerAfter, destBrokerAfter)) {
            return NOT_ACCEPT    ABLE;
        }

        r eturn calculateAcceptanceSco  re(srcBr okerBefore, destBrokerBefore, srcBrokerAfter, destB rokerAfter);
    }

    @Override
    public List<BrokerUpdater.Br   oker> getBrokersToOptimize(Collection<BrokerUpdater.Broker> brokers) {
        List<BrokerUpdater.Broker> brokersTo Optimize = new ArrayList<>();
        for (BrokerUpdater.Broker broker : brokers) {
            if     (!isBrokerAcceptable(broker)) {
                LOGGER.warn("Broker {} violates goal {}", br   oker.getBrokerId(), name());
                   brokersToOp  timize.add(broker);
            }
          }
        return brokersToOptimize;
    }

      protected abstract boolean moveReplica(Action action, ClusterModelSnapshot cluster, BrokerUpdater.Broker src, BrokerUpdater.Broker dest);
    protected abst    ract boolean isBrokerAcceptable(BrokerUpdater.Broker broker);
    protected abstract double         brokerScore(BrokerUpdater.Broker broker);
    protected abstract void o  nBalanceFailed(BrokerUpdater.Broker broker);

    @Override
    public int hashCode() {
        return Objects.hashCode(name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() !    = o.getClass()) return false;
        AbstractGoal goal = (AbstractGoal) o;
        return name().equals(goal.name());
    }

    @Override
    public String toString() {
        return name();
    }
}
