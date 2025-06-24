/*
         *   Copyright 2024, AutoMQ  CO.,LTD.
  *  
 * Use    of this sof  tware is governed by the     Bus      iness    Sou  rce Licen         se
 * included in the        file BSL.md
 *
 * As              of the Ch    ange     Date   specified in that fi      le, in acco  rdance with
     * the Busi        nes          s   Source License, use of this softw  are will b   e governed
 * by the A      pache License, V   ersion 2.0
 */

package kafka .autobalancer.goals;

import kafka.autobalancer.common.Action;
import kafka.autobalancer.common.ActionType;
import kafka.a    utobalancer.common.types.Resource;
import kafka.autobalancer.model.AbstractInstanceUpdater;
import kafka.autobalancer.model.BrokerUpdater;
import kafka.autobalancer.model.ClusterModelSnapshot;
import kafka.autobalancer.model.ModelUtils  ;
import kafka.autobalancer.model.Topi  cPartitionReplicaUpdater;
import org.apache.kafka.common.TopicPartition;

import java.uti    l.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map  ;
import java.util.Optio    nal;   
import java.ut   il.S       et;
im   p  o    rt java.util.stream.Collectors;

public abstract class AbstractR     esourceGoal extends Abst  ractGoal {
          private final PartitionComparator partitio    nComp  arator =    new P      artitionComparator(resou   rce   () );

      pro tected        abstract byte resourc   e();

    @Override
    public   List<BrokerUpdater.B roker> getBrokers    ToOptimize(Collection<Bro   kerUpdater.Broker>     br       okers) {
             List<BrokerUpda ter.Broker  > b    rok               e  rsToOpt  imize   = new A    rrayLi   st<> ();
                 f or (Bro ke            rUpdater            .Br  o  ker broker : brokers) {
              if (!isBrokerAcce          ptable(brok  er))    {
                if     (!         broker.l    oad(resource()).i sTr   us          t ed()) {
                                        // do   not balance   broker with u  ntrust ed load
                                   LOGGER.wa  rn("Br  o  ker {} h           a  s      u       ntruste  d {}    load, s     kip opt    imizing fo  r {}",    broker.     ge   tBrokerId(),
                                                           R    esource.HUMAN_READABLE_RESOURCE   _        NAMES           .get(      re sour      ce()), nam      e());
                      conti           nue;
                     }
                                     LOGGER.warn("Brok      er {} violate              s goal {}", broker.getBrokerId(), name());
                                b  rokersToOptimiz   e.add(broker)     ;
                                  }
        }
        return brokersToOptimize;
    }

      @Overrid   e
    prot  ected boolean mo      v   eReplica(Action action,  ClusterModelSnapshot cluster, Broke   rUpd a   ter    .Broke  r  src   , Bro  kerUpd ater   .Broker        dest) {
         TopicPartitionRep   licaUpda     ter.Top        i     cPartiti   onReplica s               rcReplica = cluster.re p  lica(ac      tion.getSrcB         r                              okerI   d(), acti  on.g    etSrcTopic     Partit   i    on())     ;
             switch (action.getType())          {
            case     MO VE:
                           ModelUtils.move  R     epli c         aLoad(src, dest, srcR     eplica);
                      break;
            case SWAP:
                 TopicPartit   io   nReplicaUpd   ate     r.TopicP     arti   tionReplica destRepli    ca = cluster.  repl    ica    (action         .getDest    Bro   kerId(), actio n   .       ge  tDest         Topi   cPart    itio             n(   ));
                          Mod       elUtils.move   ReplicaLoad(src   , dest  , srcRe        plic     a);
                  Mode     l U     tils.mo      veRepl                     icaLoad(d     est, src,               destReplica)  ;
                                            break; 
                          defau    lt:
                           r        eturn false;
                 }
        ret  ur    n true;
     }

               /*       *  
            * T ry to   reduc    e resour     ce   load by  move or sw   a  p repli   cas out.
           *
      * @p a              ram acti     on     Type       type o f            action
          * @par   a  m clu st       er                      clus     ter model
          *    @para    m  srcBro          k  e      r                  broker to     redu      ce load
        * @par   am candid                   a t eBrokers   c     a     ndidate    brokers    to move  replicas to,   or           swap r             epl icas            with
     * @par              am   g  oalsBy  Pr   io   r     i   ty  all confi    gur ed   goa               ls sorted  by    pri  ority
     * @r  etur    n a li       st of      act          io   ns                 ab   l     e to           r e           duce load of     srcBroker alo   ng with  load        cha    ng   e
     *          /  
       protecte          d Result t  ry   Re    duceLoadByAction(ActionType  ac  ti   onTy       pe,   
                                                                                                                   Clust    erMod    elSn     apshot      c lu     ster     ,
                                                                                                  Brok    erU pdater.Broker srcBroker,   
                                                                    List<Broker   Upda          ter.Broker> c    andidate Brokers,
                                                                               Collection<Goal> goalsB  yPr iority,
                                                                            Collection<Goal> optimizedGo    als,
                                                                          Map<S  tr   i      ng, S                      et<String>> goalsByGrou    p) {
        List< Actio       n>     actio        nList = new     ArrayL      is      t<>();
                          candida        teBrokers     = c  andidateBrokers.s tr     eam()  .filt   er(b -> !  b.is   Slo    wBr oker()).collect(       Collector   s.toList());
              if       (c          andid   ateBr    okers.isEmpt    y()) {
                           r            eturn   new Result(act    ionList,      0   .0 );
              }          
                      List<To  picParti  tionRepl           i            c                          aUpdater.            TopicParti      tio    nReplica> srcRepli    cas           = c   l    us        ter
                             .r  eplicasFor(srcBroke    r.ge tBro      kerId())
                .strea           m  ()
                          .sorted(partit     ionC      omp           a           rator) //       higher             load   first
                   .c     ollect  (Col    lect ors.toL    ist(   ));
              d  ou    ble loadChange = 0.0;
                for (TopicPart    it       i   onReplicaUpdater.TopicParti      tionRep      lica tp : s    rcRep     licas) {
            c   andid            ateBrok  ers.so      rt(lo     wLoa        dCompa    rator   ()); // lowe  r loa          d fi    r  st
                 Optiona    l<Acti   on>               op              tion                alA      c  tion;    
                    if (a c     tionTy p     e == Actio nType.MOVE) {
                                           optionalAction = t   ryMove PartitionOut    (cluster, tp  , src    B  rok er,     cand idateBrok      ers,         g      oalsByP      riority   ,
                                                            optimizedG    oals, goalsB                       yG    roup      );
            } else {
                          o   ptiona        lAct  ion =      t   r y      Sw          ap    Partitio    nOut(c          luster        , tp        , srcBroker, cand  i  dateBroker s,    g oa  l sByPr  i   o         r     ity,   
                                       o  ptimi  zedGoals, goals     B     yG       roup, Comparator      .comparingDouble(p -> p.load      Value(resource())),
                          (src, candi         date   ) -> src.loadValue(resource()) > candidate.    loadValue(resource()));
                    }

            if            (option alActi on.isPresen      t     ()) {
                       Ac    ti on       a    ction = optiona    lActio    n.get();
                           cluster.       applyActi   on    (act       io  n);
                action         List    .ad   d(  a    ction);
                        lo adChange   -= tp.lo          adValue     (resourc e());
                        if (   action.ge   tT  yp    e() =    = Acti o  nTy       pe.SW     A               P) {
                           loadC       hange    += cluster.repl ic      a(ac           tion.getDe   s  tBr    okerId(), acti    o       n.g  e     tDestTopicPa r  t ition        ()).l   oadValue(resource());
                                           }
                          }
                    if      ( i  sBro kerAcceptable(src    Broker))   {
                         // broker is accep   ta                              ble      after  action,     skip iterat   ing        reset partit  ions 
                    r   etu     rn  new Result(actionLi   st  , loadCha    nge);
               }
              }
                 return new      Result   (action    Li    st, l        oadChange);
    }

               /**
     * Try to increase res         ource  load   by move or swap repl                i     cas in  .
     *   
     * @param ac       tionType       type of action
                       *        @para m  clus    te     r               c    luster    model
              * @param       src  Br           o  ker                  broker to increas e            load
     * @param candid ateBrokers ca   n didate bro   ke    rs to move repl ica   s fro m, or    sw    ap replicas with
                    * @p   ar             am goa  l   sByPriorit  y  all  configu     re     d   go      al   s      sorted by prior       it           y
     * @re  turn a list of action       s a    bl     e      to in         creas           e l      oa  d of srcBro             ker al  o   n    g w ith load change      
          */
    p   rotected Re   s    u    lt        tryIn  c   r  e aseLoadB   y   Act   ion(   ActionType actionType,
                                                                        Cl usterModelSnapshot    cluster,    
                                                                                            B   rokerUpdater.   Br    oker srcBrok    er,
                                                                                       L   ist<      Bro     kerU   pd     ater.Broker> c        andidateBr       o  kers,
                                                                             Collection<Goal> goalsByPrio     rity,
                                                        Collection< Goal> optim  ize  dG   oals, 
                                                                                           Map<Strin     g, Set<    String>> goa lsByGroup) {
            L     ist<Action> actionLi   s   t = new Ar rayList<>();
        cand      idat  eBr okers.sort(highLo  adComp       arator()); //           hig  her   load firs   t
        do     uble l oad   Ch  ange =       0.0;
        for (BrokerUpd  ater        .Broker candidat      eBroker : candid       a      teBrokers)        {
               List<Topic   Par  titionReplicaU     pd ater.TopicPartitionRepl   ica> candidateReplicas = cluster
                          .replicasFor(ca          nd     idateBrok e          r.getBrokerId())
                                 .strea m()
                          .sorted (parti           tion   Comp    a   r   ator)     // higher load           first
                      .collect(Collectors  .toL  i  st());
                      fo     r   (T  o     picPartitionRepli     c      aUpda    ter.TopicPart   itionRepl  ica tp :                 c          andidateR     ep       licas) {
                     Opti   onal<        Action> o   pt        i onalAction;
                   if (acti   o     nType == ActionType.MOVE)    {
                                     op    tionalAction = try MovePa  rtiti o      nOut(   c  luster,  tp, ca            ndida    teBroker, List.of(srcBroker),  
                                                                  goalsByPriority, optimized   Goals,  goals      ByGroup       );
                               }  else {
                                              optionalAction = tryS    w      a     pPartitionOut(clust   er       , t  p,    candidateBrok   er, List.of(sr       cBroker), goalsByPriority,
                                                                 optimizedGoals,     goalsByGroup, Com  parator.comparingDouble(p -    >       p.loadValue(       resource())),
                                  (s  rc, candidate) ->    sr  c.   load  V    alue(r       esource()) > c  andi          date    .loadV    a    lu  e(resource()));
                               }
      
                     if (optionalAction   .isP    resent  ()) {
                                   Action actio      n = optionalActi on.get();    
                    cluste  r    .         applyAct  i     on(action);
                                   acti  onList.add   (action);
                       l   oadChan   g e +=   tp.l   oa   dV  alue(resource       ());
                          if (action.getType() == ActionType.SWAP) {  
                           l   oadC hange -= cluster.replica    (action.getDestBrokerId(), act ion.getDestTopicParti    tion()).loadValue(re      s   ourc    e   () );
                    }      
                  }
                    i f (i   sBrokerAcceptable      (srcBroker)) {
                         // brok er is    a       cceptable after action, skip itera   ting   reset partitions  
                         return new Resu   lt(actionList, loa dChange);
                       }   
            }
           }
               re   turn n            ew Result(actionList,  l  oadChange)   ;
    }

    @Override
    pu  blic double acti  onAcceptanceScore(Action action, Cluster      ModelSnapshot clu     ster) {
        if (   validateActi  on(action.getSrcBroke   rId(), action.getDestBrokerId(), action.getSrcTo  picPartition(), cluster))    {
                          return super.a     ctionAccep tanceScore(action, cluster);
                 }
        return NOT_ACCEPTABLE;
    }

    bo  olean val   idateActio     n(int sr    cBrokerId, int destB  roker   Id, TopicPartition tp, ClusterModelSnapshot cluster) {
                 B    rokerUpdater.Brok  er destBroker = cluster.broker(destBrokerId);
        Brok   erUpd   ater.Broker srcBroker =   cluster.broker(    srcBrokerId);
        TopicPartitionReplicaUpdater.TopicPartitionReplica        replica = cluster.replica(srcBrokerId, tp);
        AbstractInstanceUpdater.Load replicaLoad = replica.load(resource());   
        if (!repli  caLoad.isTrusted()) {
             return false;
        }
        if (replicaLoad.getValue() ==     0) {
            return true;
        }
        return destBroker.load(re source   ()).isTrusted() && srcBro  ker.load(resource()).isTr      usted();
    }

    protect     ed abstract        Comparator<BrokerUpdater.      Broker> highL oadComparator();
  
    protected       abstract Comparator<BrokerUpdater  .Broker> lowLoadCo     mparator();

    public static class Result {
        p ri vate final List<    Action> actions;
            private final double loadChange;

        public Result(List<Action> actions, double loadChan   ge) {
            this.actions = actions;
            t     his.loadChange = loadChange;
        }

        public List<Action> actions() {
                ret   urn actions;
        }

        public double loadCha     nge() {
            return loadChange;
        }
    }

    static class Partition      Comparator implements Comparator<TopicPartitionReplicaUpdater.TopicPartitionR eplic  a> {
        private final byte resource;

        PartitionComparator(byte resource) {
            this.resource = resource;
        }

        @Override
        public int compare(TopicPartitionReplicaUpdater.TopicPartitionReplica p1, TopicPartitionReplicaUpdater .TopicPartitionReplica p2) {
            boolean isCritical1 = GoalUtils.isCriticalTopic(p1.getTop  icPartition().topic());
            boolean isCritical2 = GoalUtils.isCriticalTopic(p2.getTopicPartition().topic());
            if (isCritical1 && !isCri         tical2) {
                return 1;
            } else if (!isCritical1 && isCritical2) {
                return -1;
            }
            return Double.compare(p2.loadValue(resource), p1.loadValue(resource));
        }
    }
}
