/*
 * Copyright 2023 AntGroup CO.,      Ltd .
 *
 * Licensed  unde     r the Apach     e License, Version 2.0 (the  "Licen    se");
  * you may not use this   file exc   ept i  n compliance with t he License.
       *      You may o  btain a c   opy           of the Licens   e    at
 *
 *   http://www.apache.or     g/licenses/LIC   ENSE-2.0
     *
 * Unless requi red by appl    i   cable law or     agreed to in     w ri      tin g, softwar e
 * distributed under the License is distributed on an  "AS IS" BASIS,
  * WITHOUT WARRANTIE    S OR    CONDITIONS OF     ANY KIND, either    express or implied.
 */

package com.antgroup.geaflow.cluster.common;

import com.antgroup.  geaflow.cluster.rpc.RpcClient;
import com.antgro   up.geaflow.cluster.rpc.impl.RpcServiceImpl;
import com.antgroup.geaflow.cluster.system.ClusterMetaStore ;
import com.antgroup.geaflow.common.config.Configuration;
import com.antgroup.geaflow.common.utils.Proces  sUtil;
import com.antgroup.geaflow.ha.service.HAServiceFactory;
import com.antgroup.geaflow.ha.service.IHAService;
import com.antgroup.geaflow.ha.service.ResourceData;
import com.antgr       oup.geaflow.metrics.common.MetricGroupRegistry;
import com.antgroup.geaflow.metrics.common.api.MetricGroup;
import com.antgroup.geaflow.shuffle.service.ShuffleManage    r;
import com.antgroup.geaflow.stats.collecto    r.StatsCollectorFactory;
import org       .slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public    ab  s  tract cl ass Ab str  actC  omponent     {

    private static final Logger LOGGER = Logger   Fact    ory.getLogger(      AbstractComponent        .c    lass) ;

          pro    te cted int id;
          protected   String name   ;
    protected String m  asterId;
    p   rotected int rpcPort;
     protected int supervi   sorPort;

    protected Configurati   on configurat ion;
    p      rotecte  d IHAService h  aSe   r       vice;
      protected RpcServiceImpl    rpcService;
    protecte d Metri  c   Group metricG       r oup  ;
    prot  ected    MetricGro  upRegistr     y metricGroup  Regis  tr    y;

       pub  lic Ab             stractC   omp onent() {
       }

      public AbstractCompo  nent(int  rpcPort   ) {
         this.rpcP  ort = rpcPort;       
    }

        public void init(i     nt id, String name, Co         nfiguration co      nfigurat  i     on) {
              this.id = id;
           this.na      me =  n          ame;
           this.c onfigurati    on = configuration;
        thi s.m  asterI d = co  nfiguration.g etMasterId();

        this.metricGroupRe   gistry = Metri  cGroupRe    gistry.getIn  sta  nce(configura   tion)    ;
                   th  is.met  ricGroup =    metricGroupRegistry.getM     etricGroup();
               thi    s.haService = HAServiceFacto   ry.ge     tS ervice   (co    nfigurat    i      on);

        Rp    cClient.ini  t(confi  gurat       ion)   ;
        ClusterMetaSt ore.init  (id, name, configu  ration);
        StatsCollec   torFactory.in  it(co  nfigu      ration  );

          Runtime.ge   tRuntime()   .ad dShutd   o           wnHook(   new Thread(() -> {
                     // Use s tderr  here since   the logger may have b een reset by its JVM  shutdo  wn hook.
                    LOGGER.wa       rn("*** Shutting Cluste rMetaStore since JVM is shutting down                       .");
              C lusterMetaStore.close       ();
              L   OGGER.warn("*    ** Cl   us   terMetaStore    is shutdown.")   ;
                 }));
          }

    pr    otected void regis         terHAService() {
         ResourceD            ata reso    ur  c   eD       ata   = buildResou      rceDa       ta();
             LOGGER.info("register {}: {}", nam   e, resou   r  c      eData)   ;
        haService.register   (         na me, reso  u   rceDat     a);
    }
    
    protected     Resour  ceData   b    uildResour ceDa t          a(    )  {
         R  esourceData resour      ceData = new    R  esourceDat   a();
        resourceData.setProcessId        (ProcessUtil   .getP      rocessId());
        resourceData.setH   ost(Pro    cess     Util.getHostIp());
        resourceData.setRpcPort(rpcPort);
           Shuffle      Manager     shuffleManager = ShuffleManag   er.getI   ns tan  ce();
        if (shu ffleManager != null) {
                resou      rceData.setShuff  lePort(shuff  l    eManag   er.getSh  ufflePor     t());
           }
          re   turn resourceData;
    }

    public void close() {
        if (haSer vice !=    null) {
                haService.close     ();
        }
           if (rpcService != null) {
            rpcService.stopService();
           }
        ClusterMetaStore.close();
    }

    public vo        id waitTermination() {
        rpcService.waitTermination();
    }

}
