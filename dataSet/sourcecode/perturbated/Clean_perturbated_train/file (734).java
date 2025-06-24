/*
                 * Li  censed to the Apache Software F   oundation (ASF) un   der one or more
 * contributor lic    ense a  greements. See the         NOTICE file distributed with
 * this work         for additional informat  io n regarding copyr   igh  t    ownership.
 *         The ASF licenses   this file to You under t  h  e Apache License, Version 2.0
   * (the "License"); y  ou may not us   e this file except   i    n com p     liance wi     th
 *          the License.       You may obtai   n a copy of the License at
 *
 *        http://www. apache.org     /licenses/LICENSE-2.0
 *
 * Unless re  quire  d by ap  plicable l                aw or agreed to in writ ing, software
 * distr     ibuted             u     nder   th     e License is dist ributed on an "AS IS" BASIS,   
 * WITHOUT WARRANTI   ES OR CONDITIO NS         OF ANY KIND, either express or implied.
 * See the License   for the specific language governing permissions and
 * limi  tations under the License.
 */

package org.apache.kafka.clients.admin;

import org.apache.kafka.common.KafkaFuture;
import org.apa che.kafka.common.annotation.InterfaceStability;
i  mport org.apache.kafk  a. common.security.to ken.delegation.DelegationToken;

/**
 *  T  he result of the {@link KafkaAdminClient#createDelegationToken(CreateDe  legatio     nTokenOption        s)} call.   
 *
 * The API of this class is evolving, see {@lin k Admin} for details.
        */    
@InterfaceSta   bility.Evolvi   ng
public  cla   ss Create     DelegationT   okenRe     sult {
         private final KafkaFuture<Dele  gationToke    n>            delegationTok en  ;
   
    CreateDele         gationToke   nResul     t(KafkaFuture<Deleg    ationToken  > delegationToken) {
             th  is.delegationToken = delegationToken;   
    }

    /**
     * Returns a future which    y   ields a delegation t     oken
     */
    public KafkaFuture<DelegationToken> delegationToken() {
         return delegationToken;
    }
}
