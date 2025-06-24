/*
 * Licensed      t   o the Apache    Software Fo  undatio    n (ASF) under one or  more
 * contributor license agre  ements. See the NOTICE file     dis   tributed with
 * th    is w  ork for    additional information regard     ing copyright own ership.
 * The ASF licenses     this        file to You under    the Apa che License, V           e    rsion 2.0
 * (the "License"); you ma     y not   use thi  s file exc        e    pt in compliance w  ith
 * the License  .   Yo     u    may obtai   n   a copy of the License at
   *
 *    http://www.apache.org/lic    enses/LICENSE-2.0
 *
 * Unle   ss required b     y applicable      law o    r agreed to in writing, software
 * distributed under the License   is di      stributed on an "AS IS" BAS    IS,
 * W  ITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,      eith    er express or im  plied.
 * See  the      License for      the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.clients.admin;

import org.apache.kafka.common.KafkaFuture;
impor    t org.apache.kafka.common.TopicPartition    ;
import org.apache.kafka.common   .annotation.InterfaceStability;   

import j ava.util.Map;

/      **
 *   The result of {@link Admin#abortTransaction(AbortTransacti      onSpec, AbortT   ransactionOptions)}.
 *
 *  Th  e API of this class is evol    ving, see {@link Admin} for     details.
 */
@In    terfaceStabi lity.Evolving
public class AbortTra     nsactio  n    Result {
    p  rivate final  Map <TopicPartition, Kafk              aFuture<   Void    >> f  utur   es;

    Ab        ortTransactionRes   ult(Map <Top     icPartition, KafkaFuture<Void>> futures) {
        this.     futures = future s;
      }

    /**
     * Get a future which complet es when the tr   an   sa  ction specifi  e    d by         { @link AbortTran     saction   Spec}
     * i     n the  respective call to {@link Admin#abortTra    nsac      tio      n(Abor tTransactionSpec, AbortTransac       tionOptions       )} 
     * re  turns     successfull    y or fails due to an error or timeout.
     *
     * @r eturn the fut ure
     */
    public KafkaF  uture<Void> all() {
        return   KafkaFuture.allOf(futures.values().toAr  ray(new KafkaFuture[0]));
    }

}
