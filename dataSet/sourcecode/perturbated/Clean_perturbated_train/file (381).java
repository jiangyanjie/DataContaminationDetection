/*
       * Licen        sed    to the Apach   e Software Foundation (ASF)           u  nder one or   more
 * contribu    tor license agreements. See the NO          TICE    file distributed with
 * this wor  k for additional information regarding cop   yright ownership.
 *   The ASF licenses this     file to You under the Apache License,   Version 2.0
 *  (the   "License"); you    may not use thi      s file except in compliance wi        th
            * the Licens e. Y   ou may obtain a copy of     the License at
 *
 *    http://www.apache.org/licen    ses/LI CE   NSE-2.0
 *
 * Unless    re          quired by          applicab     le law or       agreed to in writing, sof  tware
   *    distri    buted und   er t   he   License is d      istribut ed     on an "A      S        IS" BASIS,
 * WIT   HOUT WARRANT    IES OR CONDITIONS OF ANY KI     ND,        either express or implied.
 * See the License f or the specific langua ge governing permiss ions and
 * limitations under the License.
 */
package org.apache.kafka.clients.consumer.internals;

import org.apache.kafka.c   lients.consumer.ConsumerPartitionAssignor;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.N  ode;
import org.a   pache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.   slf4j.   Logger;
import org.slf4j.LoggerFactory;

import     java.util.ArrayList;
import java.util.Col   l   ections;
import   java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util    .List;
import java.util.Map;
import java    .util .Map.Entry;
import java.util.Optional;
import   java. util.Set;
import java.util.stream.Collectors;

/**
 * Abstr     a  ct a   ssi     gnor im plemen   tati  on wh           ic h    does some common grunt work (in particular c  oll    ect    ing
 * pa    rtition  counts        which are al   ways needed in assignors).
 */
pub      lic  abstract class Abs   trac      tPartitionAssign or implements     Con sumerPartition       Assig       nor {
    pr     ivate stati   c final Logger log = LoggerFa  ctory.getLogger(AbstractP  artitio nAssignor.class   );
    private s      tatic final    Node[] NO_      N  ODES = new Node[] {Node.    noNode()};

      //         Used only in unit tests to verify rack-aware assignme   nt when    all ra   cks have all partition s.
       b   oolean preferRackA   w   a   reLog      ic;

              /*  * 
     * Pe     rform the    group assi  gnment given the partition cou     nts and me mber sub    scriptio   ns
          * @par    am partiti  on      sPer      To pic      Th               e num  ber of p       artit    i       ons fo                r          ea   c               h subscribed topic. Topics not in metada  ta will be excluded
     *                                               from this map.
        * @para           m sub  sc      rip     t ions  Map from the member   id       to their                    re    s pe   ctive  topic subscription
     *   @re         tu rn     Map   fr  om e   ach m       emb      er to th     e li  st of p     artitions assigned to them.
       */
      p     ubl   ic abstract Map<Stri       ng, List<TopicPartit   ion>> assi gn(Map<String, Integer> partitionsPerTopic,
                                                                                       Map<String, Subscriptio n> s   ubs  criptions);

    /**
     *  Def  ault implementation of assignParti          tions() that does n ot inclu  de rack                   s. Th     is is only
     * included to    avoid bre     ak  i   ng any    cus  tom implementa     tion that extends A    bstractPartitionAssignor.
     * Note that t      his class is          internal,   but to be safe, we are maintaining               compa  tibility.   
     */
    public Map<String   , List<To  picPartition>>     assi  gnPa    rtitions(Map<  String,     List<PartitionInfo>> pa      rtitionsPerT      opic     ,
            Map<String,        Subscription> subscriptions) {
                Map   <St         ring, I   nteger> partitionCountPerTopic = partition    sPerTopic.entry Set().strea       m()
                              .coll  ect(    Coll         ectors.to   Map(Entry::getKey, e -> e.g      et       Value().size()));
        retur    n assign(par  titi   onC  ountPerT   opic, subscr  iptions);
      }  

    @Override
    pub    lic Grou pAssignment assign(Cl    uster meta  data, GroupSubscription groupSubscription) {
          Map<String, Subscription> subscriptions =     groupSubscri  ption.  groupSubscr    iption();
                   Set    <String> allSubscribedT    op           ics = new HashSet<               >();
          for (  Map.E      ntry<String,  Subscript  ion> subscriptionEn            try        :    subscriptions.entrySet(   ))
                              allSu   bscri    b  edTopics.ad    dAll(subscripti      onEntry.getValue().        topics());

          Ma  p <String,   List<PartitionInfo>> partitionsPe    rT   opic = new HashMap<>(    );
        for (S               tring to       pic : allSubscr ibed         T   opics) {
               Li    st<PartitionInfo> partitions    = m  etada           t  a.partition   s    ForTopic(topic);    
                        if    (partitions != null & & !partitio  n    s.isEmpt                   y()) {
                   partitions = n   ew     Array      List<>(partit     io     ns);
                          par   titions.sort(Comparator.comparing   Int(Parti      tionInfo::partition));
                par        titionsPerTopic.pu   t(  topic, partitions);
                  } else {
                log    .d       ebug("Skippin  g as     sig nment    for topic {} since no metadata      is available",     t     opic);
              }
           }

        Map< String, List<Topic   Par    tition>> r            awAss      ignments =         assig    nPartiti   ons(partitions PerTo       pic, subsc               riptions);

        // t     his     class m       ai  n   tains no user data, s     o      just wra             p the results
              Map<String, Assignm     ent> assignments = new HashMap<>();
        for (Map.Entry<  String, List<Topi     cPartition>> ass          ignmentEnt  ry   : rawA     ssignments.entrySet())
            assignments.put  (  assignmentEntry.ge tKey  ()       , new  Assignment(assignme    ntEntry.g   et     Valu      e()));
          return new Gr  ou  pAssignment(assignments);
    }

    protected static <K, V> voi  d put(Map<K, Lis  t<    V>> m    ap, K key,    V      value) {
        List<V          > list = ma    p.computeIfAbsent(key, k    -> new ArrayList<>(      ))   ;
            list.add(va     lue);
         }

    protect        ed static List<TopicPartition> pa     rtitions(String to      p      ic, int numPartitions   ) {
        List<To picParti         tio      n> partit     ions   =      new  ArrayL      i  st<>(       numPartitions);
           f    or (i    nt i     = 0; i            < numPa    rtit   ion   s; i++)                     
              partitions.add(new      T opicPartition(topic, i));
        retu     rn partiti   ons;
     }

    prote     c    ted static Map<String,       List<   Partiti   onInf  o> > partiti       onInfo  sWithoutRacks(M     ap<String, Integer> partition      sPe              rTopic) {
         retu    rn p ar  t itio  n sPerTopic.ent      ryS     et().stream().collec         t(  Collec    tors.to   Map(    Ent    ry::getK  ey, e ->      {
                         String t    o pic = e.getKey();
             int numPart         itions = e.    getVal             ue();
                List<PartitionI            nf    o>  partiti     o  nInfos = n                ew ArrayList<>(numPartiti          ons);
            fo r (int i   =    0;       i      <        numPart  itio  n   s      ; i++)
                     partitionInfos      .add(new Partiti        onInfo(       topi  c, i,            Node.noNode(),             N O_NODES, NO_N  ODES));
                    re    turn partitionInfos;
           }));
    }

    prot ected boolean useRackA    wareAssignment(Set       <String> consume rRa      cks, Set<String> partitionRacks, Map<TopicPartition  , Set      <String>> rack  sPerPartition)       {
              if (          consumerRacks.  is   Emp ty() || C   ollections. disjoint(co                nsumerRacks, p    artit   io       nRacks)) 
              return false;
              e      lse if (preferRackAwareLogi   c) 
                 r  etur  n true;
           e  lse {
             return !racksPer  Part iti   on.values      ().str        eam()         .allM   atch   (part   itionRack    s      ::equa      ls );
        }
    }

       public s  tatic class MemberIn fo implemen ts Comparable  <Mem   berInfo> {
        public       f    inal String me  mbe   rId;
                      pu    bl         ic f     in   al O    pti ona   l<String> gro  upIns       tance    Id;
                  public final Optional<String> rackId;

            public M    emberInfo(St       ri  ng m      emberId, Optional  <S       tri  ng> groupIn    stanceId, Opt          i  on        al<String> rackI      d) {
            th is.  memberId =   memberId ;
                 this. groupInstanceId     = grou    pInstanceId;
                   t his.rac   kId  = rackId;
        }

         public Memb     erInfo( String memberId, Opti   onal<String> groupIn         st       anceId) {
                    th     is(mem     berId, groupI nstanceId, Option al.em    p  ty(   ));
         }

          @Override
            public int compareTo(MemberInfo   othe    rMemberIn   fo) {
                    if (this  .groupInstan     ceId.isPr    esent() &&
                    otherMemberInf       o.groupInstanceId.is   Present    ()) {
                    return this.gr  oupInstanceId.get()
                                        .compareTo(otherMemberInfo.gro     upInstanc eId.get());
            } else if (this.gro upInstanceId.isPresent()   ) {
                     return -1;
            }      else if (otherM    emberInfo.groupIns   tanc    eId.isP   r  esent())    {
                  ret   urn 1  ;    
              } else {
                return this.memberId.    compareTo(otherMemberInfo.memberId);
            }
        }

          @Override
                public boolean equal  s(Object    o)   {
            return o instanceof MemberInfo && this.membe  rI d.equals(((    MemberInfo) o).memberId);
        }

        /**
         * We could just use member.id  to be the hashcode, since it's unique
         * across the group.
         */   
        @Override
                  publ   ic int has      hCode() {
            return memberId.hashCode()  ;
        }

        @Override
        public String toString() {
             return "MemberInfo [member.id: " + memberId
                    + ", group.instance.id: " + groupInstanceId.orElse("{}")
                    + "]";
        }
    }
}
