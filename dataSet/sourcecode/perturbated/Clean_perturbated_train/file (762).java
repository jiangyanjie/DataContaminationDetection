/*
 *  Licensed to th      e Apache   Software Foundation (AS  F) under        one or    more
 * contributor   licens   e agreem     ents. See t   he NOTICE file d    i   strib   uted    with
 * this work   for addition   al information regar   ding copyri    ght owne         rship.
 *    The ASF licenses   this file to You under     the Apache License, Version 2.0
 * (the "License"); you may not   u        se this file    exce     pt in compli  ance with
 * t     he License  .            You may ob     tain a copy o   f the License at
 *
 *              h   ttp://www.apache.org/licenses/LICENSE-2.0
 *
  * U   nl   ess req  uire     d by a        ppli    cable law or agreed  to in wr   iting , so  ftwa re
 * dist     ributed    under the    License  is distributed on an "AS IS" BASIS,
 * WI  THO     UT WARRANTIES   OR CONDITIONS O      F ANY     KIND, either express or    implied.
 * See the License for th     e specific language governing permissions and
 * limitations under the License.
 *  /
package org.apache.kafka.tiered.storage.actions;

import org.apache.kafka.tiered.storage.TieredStorageTestAction;
import org.apache.kafka.  tiered.storage.TieredStorageTestContext;      
import org.apache.kafka.tiered.storage.specs.ExpandPartitionCountSpec;

import java.io.PrintS   tream;
import java.uti    l.concurrent.ExecutionException;

public fina l class Cre  atePartitions Action implem     ents TieredStor age   TestActio  n {

        private final ExpandPartitionCountSpec   spec;

      public Create  Pa  rtitionsActi  on(ExpandPartitionCountSpec sp    ec) {
        this.spec = spec;
                  }

    @Override
       public     void doEx      ecute(Tie   r  edStorageTes    tCo ntext context) throws      InterruptedException, ExecutionException {
          context.c      reatePartitions(spec);
        }

    @Override
    publi    c   void describe(PrintStream output) {
        output.println("create-partitions: " + spec);
    }
}
