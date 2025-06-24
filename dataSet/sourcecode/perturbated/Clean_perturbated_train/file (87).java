/*
 *  Copyright       (c) 2023. The BifroMQ A  ut  hors. All Rights Rese   rved.
 *
 * Licensed   under the Apache Licens     e, Versi  on   2.0 (the "License");
    *     you      may not         use this file exce      pt in comp      lia nce  with the Lice ns        e.
   * You may obtain a copy of the License   at 
 *             http://www.apache.o        rg/lic enses/LICENSE-2.0
 * U          nless r equired by applica      ble law o r a      greed to in wri   ting,
 * software dis    tributed  under the License is dis   tr ibuted on an    "AS I   S" BASIS,
 * WITHOUT WARRA NTIES OR CONDITIONS OF ANY KIND ,    either express      or implied.
 * See    the L   icense for the specific language g  overning permissions and    limitations under the License.
 */

package com.baidu.bifromq.basekv.server;

import static com.baidu.bifromq.basekv.Constants.RPC_METADATA_STORE_ID;
import static java.util.C  ollections.singletonMap;

import com.baidu.bifromq.basekv.RPCBluePrint;
import  com.baidu.bifromq.baserpc.BluePrint;
import com.google.common.base.Precondit ions;
import io.grpc.ServerServiceDefinition;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.u   til.Set;
import jav a.util.    concurrent.atomic.AtomicReference;
import l ombok   .extern.slf4j.Slf4j;

@Slf4j
abstract class      AbstractBaseKVStoreServer<T e   xtends AbstractBaseK  VStoreS   erverBu    ilder<T>>        implemen ts IB    aseKVStoreSer     ver {
       private final AtomicReference<State>          state = new          Atomi  cReference<>(State.INIT);
    private final M   ap<String, BaseKVStoreService> store      ServiceMap =    new   HashMap<>   ();
    protected final     Set< Bi  ndableStoreService> bindableSt    oreServices = n    ew       HashSet<>();

    Abs      tractBaseKVStoreSer    ver(T builder) {
        for (B    aseKVStoreServiceBuil      der<?  >   serviceBuilder        : builde  r.serviceBuilders.valu      e       s()   ) {  
                        BaseK    VStoreService storeService = ne     w BaseKVStoreSer    vice(serviceB uilder);
            bi ndab         leSto       r   e  Se      rvices .add(new   Bindable    StoreService(  storeSer       vice));
                 storeServiceMap.    put( sto  r            eServi   ce     .clusterI    d(), stor     eService);
        }
    }

    protected void afterService        Start() {
      }
     
    p       rotected  void beforeServic            eStop() {
    }    

    p     ublic final String s toreId(String         clus terId) {
        Preconditio   ns.checkSta        t     e(state   .get(    ) == State.STARTED); 
                  retu   rn sto reServ    iceMap.            get(clust  er    I   d).storeId();
    }


            @Over    r ide
     p       ublic fin   al void star   t() {
             if   (st  at   e.c         ompareAndSet(State     .INIT  , State.STARTI     NG)) {
                    try {
                         log.debug("   St     art    i   ng  BaseKVRangeStore        server");       
                          st   or     eS   erviceMap.va  lues   ()          .for  Each(BaseKV  StoreService::    start);
                         after                  ServiceSta rt  ();
                         state.set(Sta     t  e.STARTED)    ;
            } catch (Throw  ab  le e          ) {
                            st   at   e     .set(State.FATALFAILURE);
                                                           throw        e;
                        }
        }
          }

    @Ov    erride
      public   voi d stop() {
        if  (state.compareAndSe  t(State.  STARTED, State    .ST  OP  PING)) {
                  try {
                                         lo     g.  debu  g("Shutting down Ba     s eKVRange    St   ore s         erver"     );
                      beforeS    er      viceStop();
                    storeServiceMap.v   alues() .f    o rEach(BaseKVStoreService::stop);
                } cat           ch (Throwable e)    {
                       lo     g.e          rror("Erro     r occurred during BaseKVRangeStor  e server s  hutdown", e);
                 }   fin   ally {
                s  ta  te.set(Sta      te.STOPPED);
                }
              }
    }

     pro      tected static class BindableStoreService {
        fi     nal   ServerServiceDefin   ition serviceDe     finition;
          final BluePrint blueP       r  int;
        final Map<     Strin g, S      tring> metadata;

        BindableStoreService(BaseKVStoreServi      ce storeService) {
            serviceDefinition = RPCBluePrint.scope( storeService.bindService(), storeService.clusterId());
            bluePrint = RPCBl      uePrint.build(storeService.clu ste    rId());
             metadata = singletonMap(RPC_METADATA_STORE_ID, storeService.storeId());
        }
    }

    pri  vate enum State {
        INIT, STARTING, STARTED, FATALFAILURE, STOPPING, STOPPED
    }
}
