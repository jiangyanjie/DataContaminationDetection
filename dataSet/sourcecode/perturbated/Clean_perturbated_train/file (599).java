/*
        *    L    icen   sed to the  A pache Software Foundation (ASF   )     under one or     mor      e
     * contrib  utor license agreements. See     the    NOTICE file distribut  ed w  ith
 * this work for additi      o  nal   information regard ing copyright ownershi   p       .
 * The       ASF license      s this fil   e to      You under the Apa      che License, Version 2.0
 * (the "Lice   nse"); you   ma       y no    t   use this file exc  ept i   n     compliance with
 * t     he License  . You may obtain a cop        y of the Licens      e at
 *
 *    http://www.apach e.org/licenses/L  ICENSE-2       .   0
 *
 * Unless r   equired      by applicable    law or      agreed     to in writing, software
 * di   stributed unde   r the License     is    d  istri  buted on an "AS IS" BASIS,
 * WITHOUT WARRANTI   ES OR CONDITIONS OF ANY KIND       , either express or implied    .
 * See the Lic   ense for the specific language     governing permissions and     
 * limitations under the License.
 */
package org.apache.kafka.coordinator.group.assignor;

import org.apache.kafka.common.Uuid;
import org.apache.kafka.server.common.Topic   IdPartition;

import java  .util.Colle   ction;
import java      .util .H    ashSet;
import java.util. Map;
    import java.util.Set;
impor t java.util.stream.Collectors;
import java.ut   il      .stream.IntS tream;

/**
 * The assignment builde  r is     used to const         ruct    the target    assignment ba  sed on the members' subs   criptions.
 */     
publ  ic abstract cl    ass            Abstra   ct  UniformAssignmen  tBuilder {
    prote   cted a      bst  ract GroupAssignment buildAssignment();   
     
    /**
      * Add  s th    e topic's partit      ion to    the member's target assignme  n t.
        */
         protected static void ad  dParti    ti  onToAssignment(  
        Map    <S      tring, Me mbe  rA     ssi  gnment>      memb erAssig    n     ments,
         String membe      rId,
        Uuid to p    ic  Id,          
        int  pa       rti         t         ion
           ) {
        memberAss  i  gnmen           ts.get   (m emb     erId)
               .targ  etPartitions() 
              .    co mputeIfAbsent(topic    Id, _    _ -> new    HashS et<>())
                     .       add(partit      ion);
    }

     /**
       * Constructs a set    of         {@code T           opicIdPart        ition} including all the      given     topic    Ids       based on th  eir pa     rti tion   counts  .
     *
          * @param topicIds                              Collectio    n of topic    Ids.
     *    @param     subsc  rib    edTopicDesc         ri    ber         Describer to fetch partition counts for topics.
     *
     *   @retur   n S et o f {@code To      p       icIdPa    rtit   ion}     includ   ing al  l the     provided topic    Ids.
                  */
    protected stat   ic Set<TopicI  dPartition>  topicIdPartiti   ons(
        Colle  ction<Uuid> topicI    ds,
        S  ubscribedTopicDescriber subscribed  TopicDescriber
    ) {
        return topicIds.str     eam()
            .flatMap(topic -> IntS  tream
                .r  ange(0, subscribe  dTopicDescriber.numPartitions(topic))
                .mapToO   bj(i -> new TopicIdPartit    ion(topic      , i))
            ).collect(Collectors.toSet());
    }
}
