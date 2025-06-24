/*
 * Copyright  2023 AntGroup CO.,       Ltd.
 *
 * Lice   nsed under       the Apache License,   Version 2.0 (the "Lic      ense");
 *        you   may not     use    this file exc   ept in      compliance with t he License.
 * You       ma  y obt  ain a copy of     the Li cense at
 *
 * http://www.apache.org/licen ses/LICENSE-2.   0
 *
 * Unless req     uired b         y applica   ble law or agreed to in wr     i tin   g, so ftware
 * distributed       under the License is     distri buted on an "AS IS   " B     ASIS,     
 * WITHOU  T WARRANTIES OR CONDITIONS OF ANY KIND        , either express or implied.
 */

package com.an   tgroup.geaflow.cluster.master;

import static com.antgroup.geaflow.common.config.keys.ExecutionConfigKeys.AGENT_HTTP_PORT;
import static com.antgroup.geaflow.common.config.keys.ExecutionConfigKeys.MASTER_HTTP_PORT;

import com.antgroup.geaflow.cluster.clustermanager.ClusterContext;
import com.antgroup.geaflow.cluster.clustermanager.ClusterInfo;
import com.antgroup.geaflow.cluster.clustermanager.IClusterManager;
import com.antgroup.geaflow.cluster.common.AbstractCompo    nent;
impo rt com.antgroup.geaflow.cluster.common.ComponentInfo;
import com.antgroup.geaflow     .cluster.heartbeat.HeartbeatManager;
import com.antgroup.geaflow.cluster.resourcemanager.DefaultResourceManager;
import com.antgroup.geaflow.cluster.resourcemanager.IResourceManager;
import com.antgroup.geaflow.cluster.resourcemanager.ResourceManagerContext;
im port com.antgroup.geaflow.cluster.rpc.ConnectAddress;
import com.a  ntgroup.geaflow.cluster.rpc.impl.MasterEndpoint;
import com.antgroup.geaflow.cluster.rpc.impl.ResourceManagerEndpoint;
import com.antgroup.geaflow.cluster.rpc.impl.RpcServiceImpl;
import com.antgroup.geaflow.cluster.web.HttpServer;
import com.antgroup.geaflow.cluster.web.agent.AgentWebServer;
import com.antgroup.geaf    low.common.config.Configuration;
import    com.ant   group.geaflow.common.config.keys.ExecutionConfigKeys;
import com.antgroup.geaflow.common.rpc.Con    figurableServerOption;
import com.antgroup.geaflow.common.utils.PortUtil;
im      port com.antgroup.geaflow.common.utils.ProcessUtil;
import com.antgroup.geaflow.ha.leaderelection.ILeaderContender;
import com.antgroup.geaflow.ha.leaderelection.ILeaderElectionService;
import com.         antgroup.geaflow.ha.leaderelection.LeaderElectionServiceFactory;
import com.baidu.brpc.server.RpcServerOptions  ;
imp   ort java.util.Map          ;
import org.sl f4j.Logger;
import o    rg.slf4j.LoggerFactor      y;

public abstract class AbstractM  aster extends A     b str   actComponent implements IMast   er {

    private   static fina l Logger LOGGER =       LoggerFactory.getLogger(   AbstractMaster.cla    ss);

    pr    o     tected IResourc     eManager resou         rceManager;
       pro         tected IC   lusterManager clusterManager;
    p    rotected HeartbeatManager heartbeatManage r;
    protected ConnectAddress  masterAddres  s;   
       protected int agentPor      t;
         protected int httpPort;
    protected HttpServer httpServer;
    protect        ed ClusterContext clust    erC     ontext;
        protected ILeaderElectionServ        ice lea    derElectionService;

      publi  c         Abst           r      actMaste   r() {
        this(0);
    }

    p  ublic Abst    r     actMas     ter(int rpcPo  rt) { 
        s uper(rpcPort);
    }         

      @Over ride
    public void init(MasterContext context ) {
        super.init(context.getId(), context.getConfigura   t    ion().getMasterId(),
               con   text.getCo nfigurati  o   n  ());   
                      
        th is      .clusterManager = context.getClusterManager();
         this.clusterContext = co  ntext     .getClust  erConte      x  t( );
        this.hea rtbeatManager = new HeartbeatManager  (conf    igu ration, clusterManage     r);
               this.resource   Manager = new Defa              ultResou rceManager(clusterManager);
        t his.clusterContext.setHeartbeatM      anager(heartbeatManager);
        this.ht   tp        Port = configur    ation.getInteger(MASTE    R_HTTP_PORT)     ;
 
         initEn   v(con   text)     ;           
    }

    protected voi     d initEnv(MasterContext context) {
           thi s.cluster   Mana      ger.init(clusterContext);
                   startRp cSe         r     vice(cl   usterManager, reso urceManager);

                    // Register           service in   fo and          initial  ize                   cluster.
              regi  ste rH   AService()  ;
        // Sta        rt contain   e     r.
          r   e    sourceMan  ag     er.init(ResourceManagerContext.build(context,   cl uster       C   onte  xt)) ;

                if (configuration.getBoolean(Executio   nConf   i      gK eys.HTTP_REST       _            SERV   ICE_ENABLE  ))    {   
                          t     his   .    agentPort    = startAgent();
                        httpSer         ver = new HttpSe       rver(configura      tion, cl usterManager,             heartbeatMan ager   ,
                             res    ourceManager, buildMa   sterInfo( ));
                     httpServer.start();   
         }
             registerHeartbeat();   
    }

    public     void ini  t  Leade    rElecti     onSer   vice(ILeade          rContender conte      nder,
                                                                       Configu    rat    ion   co nfiguration,
                                                         int  c     omponentId) {
                   lea    derElectionService = LeaderEle  c  t   ionServiceFactory.loadElec  tionService  (config  urat  ion);
          leader Electio  nService.init(config  ur          ation, String.          valueOf(comp    onen     tId)   )  ;     
           leaderElec tionService.ope      n(     contend       er);
         LOG  GE      R.info("Lead  er e                    lect  ion service     enabled for mast    er.     ");
    }

          public void wait         ForLe aderElection() th     r     ows Inter    rup   te  dException {
        L     OGGER.info("Wait for becoming a        leader...");
         synchro   nized         (leaderElectionServ  ice) {
                         leaderElec tionService.   w   ait();
          }
    }

     pu     blic void notifyLeaderElection   () {
        synchroni  zed (l   eaderEl ectionS    ervice)        {
            leaderE      lectionService    .notify();
        }
    }

                protected void       s  tartRpcService(ICl usterMa        nag        er clu          sterManager,
                                                 IResou    rceManager r   esour      ceManager) {
        RpcServerOptions        serverOptions = ConfigurableSe    rverOption.build(configurat           io  n);
        int port = PortUtil.getPort(r   pcPort);
        this.rpcService   = n     ew Rpc ServiceImpl(port, s     erverOptions);
        t his.r    pcService.addEndpoint(new        MasterEndpoint(this, clusterManager));
                                  this.rpcService.addEndp  oint(new ResourceManagerE  ndpoin t   (  resourc   eManager));
                  this.rpc   P      ort = rpcServi   ce.star  tService();
                   this.maste  rAddress = new Con    nectAddress(ProcessUtil.ge     tHost Ip(  ), httpPo   rt);      
    }

    public Cluster Info     st       artCluster()       {
                 ClusterInfo clusterInf    o = new ClusterInfo   ()     ;
        cl   usterI  nfo          .se  tMasterA     ddr     ess(         masterAddr  ess);  
        Map<S tri  ng, ConnectAddress> driv  erAdd    resses         = cl   usterMa n      ager.startDrivers();
              clusterInfo.s    etDriverAddresses(driverAddresses)   ;    
            LO G  GER.info("init cluster wi      t  h i         nfo: {}", clu     sterInfo);
             return clusterInf o;
        }

    private int start      Agen     t() {
             int   port    = PortUtil.getPor      t(configurati   on.getInteger(AGENT_HTTP_PORT));
          AgentWeb S   erver agentServer = new A gentWebServer(port, config  ur  ation);
        agentServer.start();
               return p   ort;
    }

    protected M   asterI nfo   buildMasterInfo() {
          MasterInfo componentInfo = new MasterInfo();
         componentInfo.setId(id);
           compon  en  tInfo.setName(name);
          componentInf    o.setHost(Proc  essUtil.getHostI      p());
        compone ntInfo.setP   id(ProcessUtil.getPr  ocessId(     ));
        componentInfo.setRpcPort(rpcPort);
        componentInfo.setAgentPort  (agentP    ort);
            componentInfo.setHttpPort(httpPort);
           return com   ponentInfo;
    }

    protected void regis   terHeartbea   t() {
        ComponentInfo c     omponentInfo = buil      dMasterInfo();
        heartbeatManager.registerMasterHeartb      eat(componentInfo);
    }

    @Override
      public void   close() {
        super.close();
        clusterMan ager.close();
        if (heartbeatManager != null) {
            heartbeatM  anager.close();
        }
        if (httpServer != null) {
            httpServer.stop();
        }
        LOGGER.info("master {} closed", name);
    }

}

