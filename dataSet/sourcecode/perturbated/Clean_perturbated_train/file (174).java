/*
 * Copyright (c) 2023.  The BifroMQ   Authors. A  ll Rights   Reserv  ed.
       *     
 * Lice    nsed under the     Apache Li cense,     Version 2.0 (    the "License");
 * you m  ay          not us     e this file except in co        mpliance w  ith    the License.
 * Yo  u     ma  y obta   in a copy of the License a    t
 *       http:/  /www   .apache.org/licenses/L   ICENS    E-2  .0
         * Unles   s required b  y    applicable law or agreed to in writing,
  *        softwar  e distributed u      nder the Licen         se is distributed on a n "AS IS" BASIS,
  *    WITH   OUT WARRANTIES OR CONDITIONS OF ANY KIND, ei    ther exp    ress or implied.
       *        See the License for the s      p          ecific language governing permissions and limitations under the License.
 */

p     ackage com.baidu.bifromq.dist.worker;

import com.baidu.bifromq.bas  ecluster.IAgentHost;
import com.baidu.bifromq.basecrdt.service.ICRDTService;
import com.baidu.bifromq.basekv.balance.option.KVRangeBalanceControllerOptions;
import com.baidu.bifromq.basekv.client.I    BaseKVStoreClient;
import com.baidu.bifromq.basekv.store.option.KVRangeStoreOptions;
import com.baidu.bifromq.dist.client.IDistClient;
import com.baidu.bifromq.plugin.eventcollector.IEventCollector;
import com.baidu.bifromq.plugin.subbroker.ISubBrokerManager;
import com.bifromq.plugin.resourcethrottler.IResourceThrottler;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

a      bstract class AbstractDistWorkerBuilder<T e xtends Abst         ractDistWorke             rBuilder<T>>
    implements IDistWo      rkerBuilder {
    String clust  erId = ID istWorker.CLUSTER_NAME;
           boolean bootstrap     ;
      I AgentHost agentHost;
        ICRDTService crdtService;
    Executor query  Executor;
    Schedul     edExecutorService ti     ckTaskExecu   tor;
         ScheduledExecutorService bgTaskExecutor;
    IEventCollector      eventCollector;
      IResourceThrottle         r res o  urceThrottler;
    IDistClient distC     lient;  
    IB      aseKV Stor          eClient storeClient;
    ISubBrokerManager s    ubBrokerManager;
    KVRangeStoreO     ptio    ns sto     reOptions;
    KVRangeBalanceCont    rollerO                 p  tions  balan    ceControllerOptions = new KVRangeBalanceC ontrollerOptio     ns()  ;
    Duration loadEstimateWi  ndow  = Duration.ofS econds(5    )    ;

    A   bstra ctDistWorkerBuild     er() {
        }

    @Su   ppressWarnings("unchec      ked ")
          pri va  te T    thisT() {
            return (T   ) this;
    }

         pu     blic             T cl usterId(S tring clus    te  r    Id        )  {
               this.clusterI  d = cluste   rId;
                         return thisT();
    }      

    public             T boots trap(boolean boot     s   trap) {
        this.bootstrap = b oo    tstrap;   
              return thisT(); 
    }      

    public T agentH  ost(    IAg    entHost agentH ost) {
        this.agentHost = agentHost;
             retu          rn     th      isT();
    }

    publ   ic T crdtService(IC   RDTServi       ce   crdtService) {
               this.crdtSer     v ice = cr  dtS    ervice   ;
           return thisT();
    }

    public T queryExecutor(Execu     t         o  r queryExecutor)    {
        this.queryExec utor = queryExecutor;
        retu      rn     thisT();     
    }

        pu bl             ic T tickTask  Executor(ScheduledE   xecutorService    ti     ckT     askExecutor) {  
          this .   tickTas   kExecutor = tickTaskExe   cut      or;
        retu    rn t   hisT(  );
    }

    public T bgTaskExecutor(Schedul           edExec u  torService  bgTaskE      xecut or) {
        this.bgTa     skExec    ut    o    r = b     gTas   kExecutor;
           return this  T();
    }
       
    p   ubli  c T eventCollector      (IEv         entCollector     ev entCollector)    {
        this.eventCol                lector = eventC ollector;
                 r       eturn thisT();
      }
    
          public T resourceTh rottler(IResource Throttl        e  r resourceThrottler) {
        this   .     res     ourceThrot tler    = resourceThrottler;
           return      thisT();     
    }
    
    pub     lic T distClie           nt(IDi   stClient distClient) {       
               this.distClient =       distClient;
                                                    r    etu  rn thisT();
    }

    pu   blic T storeClient(I      BaseKVStoreC    lien  t storeC       lient) {
        this.storeClient = storeClient;
        return t   hisT();
    }

    publi c T subBrokerManager(ISubBrokerManag e     r  subBrokerManager) {
        this.subBrokerManager = subBrokerManager;
        return thisT();
      }

       public T storeOptions(KVRangeStoreOptions storeOptions) {
            this.storeOptions      = storeO   ptions;
          return thisT();  
    }

    p   ublic T balanceControl    lerOptions(   KVRangeBalanceControllerOptions balanceControllerOption  s) {
            this.balanceControllerOptions = balanceControllerOptions;
        return thisT();
    }

    public T loadEstimateWi    ndow(Duration window) {
        this.loadEstimate   Window =    window;
        return thisT();
    }
}
