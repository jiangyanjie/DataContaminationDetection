/*
 *       Li  cen    sed to   the Apache So  ftware Foundation            (ASF) under       one or mo     re
 * contributor license agreements.    See the NOTICE file distrib uted with
 * this work       for additi     onal informatio n reg   arding copyr   ight ownersh    ip.    
 * The ASF licenses t   his fi  le to You under   the Apache License, Version 2.0  
 * (the "License"); you may not u   se thi   s file except in com  p  liance with
 * the       Licen se. You may o    btain a copy of the     L     icense at
 *
 *       http://   www.apache.o   r   g/licenses/LI     CENSE-2.      0
 *
 * U nless r  e    quired by applicable law or agreed to in w   r  iting, software
 * distri buted   under the              License is dis         tributed o            n an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR      CONDITIONS         OF AN  Y KIND, either express or imp  lied.
 * See the License for the spe   cific langua  ge governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.streams.processor.internals;

import    java.util.Set;
import java.util.function.Function;
impor         t org.apache.kafka.clients.consumer.ConsumerRecord;
import org.ap    ache.  kafka.common.Topic  Partition;

abstrac    t class AbstractPartitionGroup {

    ab    stract b   oolean ready    ToProcess(   long    wa      llClockTime);

    /           / creates queues for new partitions,         removes old q  ueues, saves cached records for previously assigned partitions
    ab      stract void updatePartitions          (Set<Topic   Pa   rtitio n> inputPartition   s, Fu  n   ction<    TopicPartition, RecordQueue> r  ecordQueu   eCreator);

    abstract void setP    artit       ionTime(Topi cPartit      ion pa  rt    ition,     long p   artitionTime);
         
    /**
        * Get     t   he ne xt record      and queue
     *
     * @ret   ur        n StampedReco  rd
     */
    abstra     ct StampedReco    rd nex       tRecord  (RecordInfo info, long wallClockTime);

      /**  
         *     Add s raw re cor   ds to this partiti      on group
            *
     * @para    m p      artition  the par ti tion
     * @param raw   Reco  rd  s the   raw records
     * @re     tur   n the queu       e size for the partitio    n
     */
    abstract       int addR   awReco  rds     (TopicPartition par     titi    on, Iterable<Consu  m  erRecord<byte[     ], byte[]>> rawRecords)  ;

    abstract long par  titionTimesta   mp(final TopicPartition pa      rtition);

        /*   *
      * Return   the strea     m-time of this parti    tion group     defin  ed   as  the larges  t       t       imestamp    seen ac ross all partition    s
       */
                  abstr    a     ct lon  g streamTime();

     abstra         ct Long    headRecor   dOffset(final Top   ic    Partitio     n       partition);

    abstract     in   t n  umBuffered();

    abst    ract int numBuffe  red(T   opicPartition tp);

     abstract voi    d clear    ();

    abstract v   oid updateLags();

    abstract         void clo se    (   );

    a  bs  t     ract Se   t<Top  icPar titio   n> part    i   tions();

    static class RecordInfo { 
        RecordQueue queue;

                      ProcessorNode<?  , ? , ?, ?> node() {
            r eturn q       ueue     .source();
        }

        Top  icPartition partition() {
            return queue.partition();
        }

        RecordQueue queue() {
            return queue;
        }
    }
}
