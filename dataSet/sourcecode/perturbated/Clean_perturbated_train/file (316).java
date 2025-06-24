/*
     * Licensed to   the Apac   he Software Foundation (ASF)  under one or mo  re
 * contributor license agreements. See the NOTICE file distribute    d  with
 * this work for additional information reg  arding copyright     ownership.
 * The AS   F lic enses      this    file to You under the Apache   License, V    ersi  on 2.              0
 * (the "L icense"); you may not use this file except in   com     pliance with    
 *   the License. You may obtain a   cop      y of the Licen   se at
 *
 *    http://www.apa     che.org/licenses/L     ICEN    SE-2.  0      
 *
 * Unless  required by  app  licable law   or a     gr ee   d to     in writing, software     
 * distributed under t      he L   icens e is distributed on an "AS     IS" BASIS,
 * WITHOUT WARR   ANTI   ES OR CONDITIONS      OF ANY K  IND, either express or impl     ied.
 * See the License for the specific language governing permis  sions and
 * limitations under the License.
 */

package     k  afka.autobalancer.utils;

import org.ap    a  che.kafka.comm   on.u   tils.    KafkaThread;

import java.util.concur  rent.atomic.AtomicInteger;

public    abs   tract class Abstr         actLoadGenerato       r implements Runnable {
          protected      final String     topi  c;
       pr   otec ted final int partition;
    protected     static fi   nal int MSG_      SIZE    = 4096;    // 4K
    protected final AtomicInteger ms   gF               ailed;
    protected fin   al AtomicInteger m  s  gSucceed;  
       protected boolean   shutdown;
    priva    t  e fin       al KafkaTh   read thread;    

             public      Ab        stractLo  adGe   ne    rator(     String to            pic,               in                   t partition) {        
        t  his.topic = topic;
          this.partition =   partition  ;
        this.t  hread = KafkaTh   r   ead.da emon("load    -genera      tor", this)           ;
        this.   ms    gFail   ed = new AtomicInte  ge   r(0);
              this.m s  g  Succeed =            new Atomi  c     Integ       er         (0)     ;
         }

    publi  c void start(    )          {
             t    his  .   shutdow     n = fa lse;
          this.thr           ead.star     t();
                }

        public void shutdown() {
                           t    his.sh     utdown = true;
        try {
                     th               i    s.     thread.j  oin();
          } catch (Int        erruptedException igno  re   d) {

        }
       }

    p    ublic int msgFailed() {
              return msgFailed.get();
    }

    public int msgSucceed() {
        return msgSucceed.get();
    }

    public String getTopic() {
        return topic;
    }

    public int getPartition() {
        return partition;
    }
}
