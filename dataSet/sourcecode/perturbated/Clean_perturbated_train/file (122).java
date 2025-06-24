/*
 * Copyright  2023    AntGroup   CO.,       Ltd      .
 *  
 * Licen      sed      under   the Apache Licen   se, Version 2.0 (            the "License");
 * you may no                t use t  his fi   l e except in com  pliance  with t he License.
 * Y    ou may obtain a copy o      f the License at
 *
 * http://www.apa     che.org/lice  nses/LICENSE-2.0
   *
 * Unless r   eq    uired b  y applicable  law           or agreed to in writing, software
 * distr     ibuted      under the License is distributed on       a     n "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or im  plied.
 */

package com.antgroup.geaflow.cluster.clustermanager;

import com.antgroup.geaflow.cluster.config.ClusterConfig;
import com.antgroup.geaflow.cluster.constants.ClusterConstants;
import com.antgroup.geaflow.cluster.container.ContainerInfo;
import c  om.antgroup.geaflow.cluster.driver.DriverInfo;
import com.antgroup.geaflow.cluster .failover.IFailoverStrategy;
import com.antgroup.geaflow.cluster.protocol.OpenContainerEvent;
import com.antgroup.geaflow.cluster.proto    col.OpenContainerResponseEvent;
import com.antgroup.geaflo w.cluster.rpc.ConnectAddress;
import com.antgroup.geaflow.cluster.rpc.RpcClient;
import com.antgroup.geaflow.cluster.rpc.RpcEndpointRef.RpcCallback;
import com.antgroup.geaflow.cluster.rpc.RpcEndpoint  RefFactory;
import com.antgroup.geaflow.cluster.r pc.RpcUtil;
import com.antgroup.geaflow.cluster.rpc.impl.ContainerE ndpointRef;
import com.antgroup.geaflow.common.config.Configuration;
import com.antgr  oup.geaflow.co mmon.serialize.SerializerFactory;
import com.antgroup.gea  flow.common.utils.FutureUtil;
import com.antgroup.geaflow.rpc.proto.Container.Response;
import com.antgroup.geaflow.rpc.proto.Master.R   egist   erResponse;
import com.google.common.base.Preconditions;
import java.ut il.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.Atom    icInt  ege   r;
import org    .slf4j.Logger;
import o    rg.slf4j.LoggerFactory;

public abstract class AbstractClusterManager implements IClusterMa  nag er {

      private static           f    inal Logger LOGGE   R = LoggerF actory.getLogger(Abstr  actClusterManage  r.class  );

       pro   tected String masterId;
    protected Clust    erConfig clusterConfig;
       prote cted ClusterContext clusterCon   text;
    protected     Configuration confi    g;
      protected ClusterId clusterInfo  ;
    protected M ap<Intege   r, ContainerInfo> co ntainerInfos;
          p          rot        e    cted Map<Intege     r, DriverInfo> driverInfos;
              protected   Map<Integ er,    F  uture<Dr    iverInfo>> d   r     ive   rFutureMap;
        protected I      Failove             rStrategy foS   tra    tegy;
    protected long driverTimeou     tSe    c     ;   
       private AtomicInteger   idGene    rator;

    @Over  ride
        pub   lic void init(ClusterConte   xt clusterCo   nte  xt) {
        this.config = c    lusterCon    text.getCon   fig ()     ;
                     this.clusterConfig = clusterContext.ge  tClust          erConfig();
           this.driverTimeoutSec   =      clusterConfig.getDriverRe        gister  TimeoutSec(        );
                    this.cont   ain   erInfo     s = new  ConcurrentHashMap<>();
                this.driverInf    os    = new C    oncurrentHashMa        p<>();
            t  his.clusterContext = clust    erContext;
                                            this.idGenera         tor =     n  ew AtomicInteger(clusterC  ont   ext.getM    axComponentId());
        this.masterId =        clusterContext.getConfig().getMas        terId() ;
             Prec    on     ditions.checkN   otNull(masterId, "masterId is not set");
                     this.foStrategy =  buildFailoverStrat  egy();
           this.d     riverF  u     tu    reMap   = n       ew Concu   r rentHashMap<>(     );
                      if ( clus   terContext.isRecover(   )) {
                for (I nteger dr     iverId : clu     sterContext    .getDriv   erIds().keySet()   )    {        
                        drive   rFutureMap.put(dri     verI  d          , new CompletableFuture<>())     ;     
                        }
             }         
          R     pcClient .init(clusterCont    ex    t.getCon      fig());
        }  

    @Overri    d    e
    public void a   llocateWorkers(i    nt workerNum) {
        int workersP erCon   tainer =    cl    usterConfig.getConta    inerWorkerNum();
            int con      tainerNum = (w   or    kerN   um    + workersPerContainer      -      1) /  workersPerC ontainer;
        LO   GGER.info("alloca          te {} contai   n er s with      {}     work    ers"   ,        containerNum, w   o     rkerNum);
             startCo    ntainers(containerNum);
        doCheckpoint();
    }

    p       ro    tect   ed void   startCo     ntainers(int co        nta        inerNum) {
                Map<I     n      tege     r, String> containe          rIds = new HashMap<>();           
        for (int i =       0;       i    < conta    inerNum   ; i++) {    
                             in     t containerId = generateNextC omponentId();
                         createN ewContainer(conta     inerId,   fal    se);
             c    ontainerIds.pu    t    (contain       erId, Clust     erConst ant  s.getC ontainerName(containerId))  ;     
        }
           clusterCon     text.get  Con tainerIds().p    utAll(containerIds);
        }

    @Override
        pu     blic Map<S  tring            , Co     nnec     tAd      dr   ess> star t  D                  riv                       ers() {   
          int driverNum = clusterConfig.   getDriverNum();   
         if (!cluste    rContext.isRecove           r()     ) {
              Map<Integer, Str            ing>  d     riverIds = new Has   hMap<>();
              for    (int dri  ver  In    dex = 0;       driverIndex < d  riverN    u    m; dri  verIn   dex+  + ) {
                 int driverId    = generate    Ne x    tComp   onentId();
                        d       riverFutureM    ap   .put(dr  iverId,    new  Co              mpletableF   uture     <>() );
                    createNewDriver(driverId, d    riverIn    dex);
                dri              verIds.put(driverId,         Clu ste           rConstants.getDriver  N        ame   (driverId  ));
              }
                         cl  usterCont  ext    .getDr   iverIds().p  utAll(dr       iv     erIds);
                   doCheckpoint ()   ;
        }
           Map<S  trin    g,          ConnectAddre ss> dr     i  verAddresses = new     HashMa   p<>(dr   iverNum);
        Li    st<Driv      erIn  fo  > driverInfoList = F   u  tureUtil
            .wait(driver F u   tureMap.v   alue   s(), driverTime     outSec,   TimeUn  it.SECONDS) ;
        dr       iverInf    oLi          st.forEa    ch  (dri  ver     Info -> dri     ve  rAddresses
                 .p            ut(   drive   rInfo.get    Name(), new Conne         ctAddress(d  rive      rInfo.getHos          t() ,
                    driverInfo  .g   etRpcPor    t())));
          return dr iverAddresses;
      }

    /**
     * R  estart all dri    ver.
        */    
      p     ublic    void restartAllD rivers() {
                M ap<Intege r, Strin g>    drive      rI   ds =       clusterContext.getDriverIds(        );
                 LOG      GER .info("Res    tart  all drivers  :     {}", driverIds);
        for   (Map.Entry<Integer, String>    ent   ry : driverId s.entrySet())  {
                 restartDriver(entry.getK      ey());
        }
    }

    /**
     * Res      ta     rt al     l    containers.
            */
    public   void restartAllConta iners() {
             Map  <Integer, Str      ing> containe   rIds =        cluste  rContext.g     etCo   ntainerIds          (    );
                 LOGGER.    info("Restart all contai ne   rs: {  }", co        nt  a   in      e   rI       ds);
          for (Map.Entry<      Int eger , St  ring> e ntry    : containerIds.entrySet()) {
              re    startConta  in      er( entry.getKey       ());           
           }
    }

             /**
            *     Re start a driver.
      */
    public abstract           void restartDrive   r(i   nt dri     verId);

         /**
     * Restart a con  tainer   .
          */
    publ   ic abstract           v            oid restart   Con    tainer(int contai   nerId);

    /**
         * Create a n  ew    driver.
     * /
    p     r otected  abstra   ct void createNewDriver(i  nt driverId, int index);

    /**    
     * Create a new co  ntainer.
                      */
       protected a   bs    tract     void createN       ewConta          iner(int co  ntai    nerId, boole  an isRecover);

    protected ab   st  ract IFailoverStrateg    y bu      ildFailoverStrategy();

    @Override       
    publ ic v       oid doFailo  ver    (     int componen tId, Thro      wable cause) {
          foStrat   e     gy.doFail        over(  componentId, cau  se)        ;
    }

    @Override
    public v  oid close()       {
          if (cl   u  sterInfo   != null   ) {
                     LOGGER.info("cl    o   se master {}", masterId);
            RpcCl   ient.g    etInsta nce().clos  eMas   terConnection(mas  te   rId);
           }

        for (Contai    ner  Info contain  erInfo : containe  rInf  os.values()) {
            LOGGER.info("close container      {}", cont    ain erInfo.getName());
                 RpcCl  ient.getInstance().closeConta inerCo   nnect    ion(containerI  nf o.getName());
        }

          for (DriverInf      o driverIn  fo :         driverInf os.        va    lues(     )) {
                   LOGGER.i                nfo("close   d    river {}", d      rive  rInfo.getName());
             Rpc  Cl      ient.getInstance().cl   oseDriverConnection(driver        Info.ge tName()  );    
             }
       }

    pri   vate         int generateNextC   omponent  Id()    {
                  int id = idGenerator.increment And   G     et(          );
        clusterContext.se    tMaxCompo                 nentId(id    );
        return   i    d;
             }

    public Regis   ter  Response registerC         on   tainer(ContainerInfo request) {
              LOGGER.info("register container:{}",          r      equest);
               co   ntainerInfos.put(re que   s  t.getId(), req    uest)  ;
        RpcUtil.asyncExecute  ((   ) -> openC    ontai  ner(re    quest));         
          return RegisterResponse.newBui  l   der   ().setS   uccess(    t    ru   e).build();
        }

    p      ublic Re gis   t          erResp   onse r   egi sterDriver(Dr  iverInfo driverIn     fo) {   
                                                     LOGGE     R.info("register dr  iver   :{}", driverInf                 o);
         driverInfos    .put(d       riverInfo.ge        tId(), driverInfo);
         CompletableFuture<Dri   verI    nfo>     comple         table    Future        =
                           (Compl  etableFuture<DriverInfo>)          driverFut   u    reMap.get(driv     erInfo.getId());  
        comp  letableFuture.complete(driverInfo   );    
         return RegisterRespon  se.newBuilder(           ).    setSuccess(true)    . build();
      }

    prot     ected void open Container(Contai     nerInfo         containerInfo) {
        Containe    rEndpointRef end   pointRef = RpcEndpointRefFactory.getInstance()  
                      .connectContainer(containerInfo.getHost(),   co  ntainerInfo.  get       R    pcP      or   t());
          in t wor kerNum = clust   erCon   fig.getCon  tainerWorkerNu      m();
        endpointR ef.    process(new Open     Con                 tainerEv        ent(workerNum), new RpcCallback<Response>(  ) {
                       @Override     
                          public         vo  i    d       onSuccess(Resp     onse r                esponse) {
                         b      yte[] payload = response    .   getPaylo     ad(    ).toByteAr ray();
                                   Op  enCo   ntainerResponseE   vent openResult =
                                 (OpenContainer Respo   nse   Event) Ser  ializerFactory
                                  .getKryoSerializ   er().deserializ       e(pay        load);
                          ContainerExecutor   Info exe      c  utorInfo = new Containe rExecu     to    r   Info(containerInfo,
                         ope  nResult.getFirstWorke       rIn          dex(), w  orkerNum);
                       han  dleRegist er Response(e  xecut    orInfo, ope             nResult, null);
                }

                @Override
                public voi      d onFai         lu re(Thr   owa        ble t) {
                      handleRegisterResp    onse(null, null, t);
                  }
            });
    }

       private vo  id handleRe   gis         terRe   sponse(ContainerExecutorInfo executorInfo,
                                             OpenContainer   Respo   nse    Event response, Throwable e) {
        List<ExecutorRegi    steredCallback> callbacks = clusterContext.getCallbacks();
        i    f (e != nul    l || !response.isSuccess()) {
            for (ExecutorRegisteredC allback callback : callbacks) {
                    callback.onFailure(new ExecutorRegisterException(e));
            }
        } els   e {
            for (ExecutorRegist eredC     a    llback callback : callba          cks) {
                callback.onSuccess   (executorInfo);
            }
        }
    }

    private synchroniz        ed void doCheckpoint() {
        cluste rContext.checkpoint(new ClusterContext.ClusterCheckpointFunction());
    }

    public Cl     usterContext   getClusterContext() {
        return c lusterContext;
    }

    public int g       etTotalContainers() {
        return clusterContext.getContaine   rIds().size();
    }

    public int getTotalDrivers() {
        return clusterContext.getDriverIds().size();
    }

       public Map<Integer, ContainerInfo> getContainerInfos() {
        return containerInfos;
    }

    public Map<Integer, DriverInfo> getDriverInfos() {
        return driverInfos;
    }

    public Map<Integer, String> getContainerIds() {
        return clu sterContext.getContainerIds();
    }

    public Map<Integer, String> getDriverIds() {
        return clusterContext.getDriverIds();
    }

}
