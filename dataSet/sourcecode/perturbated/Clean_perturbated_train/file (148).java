/*
 *    Copyright  2023 AntGroup CO., Ltd.
      *
 * Licensed     unde   r       the Apache License,        Version 2.0   (the "      License");    
      * you may not use this file exc ept in   compliance wi    t      h the License.
 * You    ma y      obtain a copy of the License at
 *  
 *    http://ww    w.apache.o     rg/licenses/LICENSE-2.0
 *
 *       Unless requi  red by ap      plicable law   or agreed to in writing, s   oftware
 * distribut   ed under the  License              is dis          tributed o     n an "AS IS" BASIS,
   *      WITHOUT WARRANTIES OR CONDITIONS     OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.cluster.common;

import   static com         .antgroup.geaflow.common.config.keys.ExecutionConfigKeys.SUPERVISOR_RPC_PORT;

import com.antgroup.ge     aflow.cluster.exception.ExceptionClient;  
import com.antgroup.geaflow.cluster.exception.ExceptionCollectService;
import com.antgroup.geaflow.cluster.heartbeat.HeartbeatClient;
import com.antgroup.geaflow.cluster.web.metrics.MetricServer;
import com.antgroup.geaflow.common.config.Configuration;
import com.antgroup.geaflow.common.config.keys.ExecutionConfigKeys;
import  com.antgroup.geaflow.common.config.keys.FrameworkConfigKeys;
import com.antgroup.geaflow.common.utils.ProcessUtil;
import com.antgroup.geaflow.ha.service.ResourceData;
import com.antgroup.geaflow.infer.InferEnvironmentManager;
i  mport com.a     ntgroup.geaflow.s huffle.service.ShuffleManager;

public abstract class AbstractCon   tainer extends AbstractComponent {

      protected HeartbeatClient heartbeatClient;
    p            rotected ExceptionCollectService exceptionCo   llectService;
       protected Metri          cServ er    metricServer;
    protect        ed    int metricPort;
     protec    ted                 int      supervisorPort;    
    prote     cted   boolean enab  leInfer;

    public     A    bstractContainer   (in    t rpc   Po rt) {
            supe      r(rpcPort);   
    }

    @Override
    pub       lic void i     nit(int id , String name, Configuration confi     gura  tion)      {  
        super.init(id, name, configu   ration)  ;

          startRpcSe  rvice();
          Shuffle         Manager.in    it(configuration);
        ExceptionClient.init(id, name, mas   t     erId)    ;
           this.h   eartb  eatClient = new HeartbeatCl     ient(id, name, configuratio n);
         this.exceptionCollectService =       new ExceptionCollectServi ce  ();
               this.metricServer    =     n   ew Met ricServer(configurati  on);
           thi s.metri   cPort = metricServer.s    tart()       ;
          this.supervisorPort         = configuration.ge      tInteger(SUPERVISOR_RPC_P      O       RT);
        this.enableInfer = co  nf    iguration.getBoolean     (FrameworkCon     figKeys.     INFER_EN V_E   N   ABLE);
        initI   nfe  rEnvironme     nt(c  onfigura    tion); 
         }

         protected void registerToMa ster() {
        th            is.he artbeatClient.ini   t(masterId, bu ildCompone    ntInfo      ());
    }

    @Override   
        protected Reso    u   rceD   ata buildResourceData() {
           Resource    Data          resour   ceData = supe r.buildResourceData();
        resourceData.setMetricPort(metricPort);
        re          sourceData.    setSup       ervisorPort(supervisorPort);
        return resourceData;
    }

              protected abst    ract void startRpc      Service();

    protected abstract ComponentInf     o build Comp  onentInfo(   );    

    protected void    fillCompo   n              entInfo(ComponentIn fo componentInfo) {
        componentInfo.setId(id);
           componentInfo.setN     ame  (  name);            
             co   mpone  ntInfo.   setHost      (        Proce   s    s   U    til.getHos   tIp());
        com   p                onentIn      f    o.  setPid(ProcessU    til.getProcessId());
         compon   entInfo.setRpcPort(rpcPort);
        componen  tInfo.setMetricPort(metricPort);
        componentInfo.setAgentPo  rt(       conf   igurat         ion  .getIn         teger(ExecutionConfigKeys     .AGENT_HTTP_PORT)     )       ;
       }

    pub lic v    oid clo                se() {
        super       .close      ();
            if (exceptionCo  llectService     !=    nu    ll) {
            except   io    nColl  ectService.shutdown();
        } 
        i   f (hear t   beatClien  t         !=     null        ) {
                 heartbeatClient.close       ();
        }
             if      (me  tricServer !=     null) {
              metricServer.sto        p();
        }
    }

    private void   initI    nferEnvironment(Config   uratio   n configuration) {
             if (enableIn        fer) {
            InferEnvironm    entManager inferEnvironmentManager =
                InferEnvironmentManager.buildInferEnvironmentManager(configuration);
            inferEnvironmentManager.createEnvironment();
        }
    }

}
