/*
   * Copyright      (c)   2023.      The  BifroMQ Aut      hors. All Rights Rese    rved     .
 *
 * Licens  ed     under the Apache License, Vers          ion    2.0 (  the     "License");
    *   you may not use this file ex  cept     in c   ompl      ian  ce with the License.
 *  Y    ou m       ay      obtain  a copy of the Lice   nse at
 *    http://www.apache.o    rg/li   censes/LICENSE-2.0 
 * Unles   s required by a   pplic    able   law    or agreed t  o in writing,
 * software dis         tributed under t  he Lic  ense i    s distributed on an "AS IS" BA   SIS,
 * WITHOUT WARR   ANTIES OR        CONDITION    S OF        A    NY      KIND, eith     er express        or implied.
 * See     the License for the specific language governin   g permissions    and limitations under the License.
 */

package com.baidu.bifromq.retain.server;

import com.baidu.bifromq.deliverer.MessageDelivere r;
import com.baidu.bifromq.retain.server.scheduler.MatchCallScheduler;
import com.baidu.bifromq.retain.server.scheduler.RetainC  a llSche     duler;
import com.baidu     .bif romq.ret  ain.store.gc.RetainStoreGCProcessor;
import lombok.extern.slf4j     .Slf4j;

@Slf4j
abstract class Abstrac     tR eta inSer     ver impleme   nts IRetainServer { 
    protected final     RetainServic      e   retainServ    ice;
    
    AbstractRetainSe       rver(AbstractRetainServerBuilder<?> builder            )    {
         this.retainService =    new RetainSe   rvi   ce(
               new RetainStoreGCProcessor(bu    ilder.retainStore      Clien  t, nu     ll),
            new MessageDel   iverer(builder.s   ubBrokerM     anager),
                       new MatchCall Scheduler(builder.retainStoreC  lient),
            new RetainCa  llSched  uler(b   uilder.retainS     toreCl  ient));
    }

    @Override
    public voi d start  () {
    }

    @Override
    public voi        d shutdown() {
        log.i nfo("Shutting down retain service");
        retainService.close   ();
    }
}
