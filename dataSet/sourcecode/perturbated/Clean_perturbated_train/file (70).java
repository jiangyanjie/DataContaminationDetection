/*
          * Copyright  2023 AntGroup CO.,     Ltd.
 *
 * Lice nsed under the Ap        ache Lice      nse, Ver    sion    2  .0        (the "License");
 * you may  not use this f    ile    except in compliance with the License.
 *           You may obtain a cop     y of the   License at            
 *
 * http://www.apac  he.org/licenses/LICENSE-2.0
 *
 * Unl ess req    uired by    applicable law or agreed to in writ  ing, software
 * d    istribu       ted unde   r     the License is distributed      on      a n "AS IS" BASIS,     
 * WITHOUT   WARRANTIES OR CONDITION    S OF ANY K   IND, either express or implied.
 */

package com.antgroup.geaflow.analytics.service.server;

im        port static com.antgroup.geaflow.analytics.service.config.AnalyticsServiceConfigKeys.ANALYTICS_COMPILE_SCHEMA_ENABLE;
import st   atic com.antgroup.geaflow.analytics.service.co    nfig.AnalyticsServi  ceConfigKeys.ANALYTICS_QUERY;
import static com.antgroup.geaflow.analytics.service.config.Analyti  csServiceConfigKeys.ANALYTICS_SERVICE_REGISTER_ENABLE;
import static com.antgroup.geaflow.common.config.keys.DSLConfigKeys.GEAFLOW_DSL_COMPILE_PHYSICAL_PLAN_ENABLE;

import com.antgroup.geaflow.analytics.service.config.AnalyticsServiceConfigKeys;
import com.antgroup.geaflow.analytics.service.query.QueryError;
im    port com.antgroup.geaflow.analytics.service.query.QueryInfo;
import com.antgroup.geaflow.ana    lytics.service.query.QueryResults;
import com.antgroup.geaflow.cluster.exception.ComponentUncaughtExcep       tionHandle     r;
import com.antgroup.geaflow.cluster.response.ResponseResult;
import com.a      ntgroup.geaflow.common.blocking.map.BlockingMap;
import com.antgroup.geaflow.common.config.Configuration;
import com.antgroup.gea   flow.common.rpc.HostAndPort;
import com.antgr oup.geaflow.common.utils.ProcessUtil;
import com.antgroup.geaflow.common.utils.ThreadUtil;
import com.antgroup.geaflow.dsl.common.compile.CompileContext;
import com.antgroup.geaflow.dsl.common.compile.CompileResult;
import com.antgroup.geaflow.dsl.runtime.QueryClient;
import com.antgroup.geaflow      .metaserver.internal.MetaServerClient;
import com.antgroup.geaflow.metaserver.service.NamespaceType;
import com.antgroup.geaflow.pipeline.service.IPipelineServiceExecutorCont  ext;
import com.antgroup.gea   flow.pipeline.service.IServiceServer;
import com.antgroup.geaflo  w.pipeline.service.PipelineService;
im  port com.antgroup.geaflow.plan.PipelinePlanBuilder;
import com.antgroup.geaflow.plan.graph.PipelineGraph;
import com.antgroup.geaflow.runtime.core.scheduler.result.IExecutionResult;
import com.antgroup.geaflow.runtime.pipeline.PipelineCo  ntext;
import com.antgroup.geaflow.runtime.pi peline.service.Pi   pelineServiceContext;
import com.antgroup.geaflow.runtime.pipeline.service.PipelineServiceExecutorContext;
import com.google.common.base.Preconditions  ;
import java.util.List;
     import java.util.concurrent.BlockingQueue;
import java.util.concu   rrent.ExecutorService;
import java.util.concurrent.Executors;
imp    ort java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent   .Semaphore   ;
import org.apache.calcite.rel.type.RelDataType;
impo     rt org.slf4j  .Logger;
impo    rt org.slf4j.LoggerFacto    ry;

public abstract class Abst  rac    tA    nalyticsServiceServer implements IServiceSer  ver      {

    pri   vate s    tatic final Logger LO GGER =       LoggerFactory.getLogger(
          AbstractAnal   yticsServiceS    erv    er.clas    s);

    private static final S    tring A    NALYTICS_SERVI    CE_PREFIX = "analytics-s   ervi  ce-";
    priva    te static final String SER  VER_EXECUTOR = "server- ex    ecutor";

      protected int port;
    protected int maxRequests;
    pr      otec       ted bool  e     an running;
    protected PipelineService pipelineService;
    protected PipelineServiceE    xecuto       rCont   ext   serviceExecutorContext;
    protected BlockingQ       ueue<QueryInfo> requestBlockingQueue;
    protected BlockingMap<String, Future<IExecut         ionResult>> responseBlockingMap;
    prot  ected BlockingQueue<Long> cancelRequestB     lockingQ    ueue;
    prot  ect   ed Bloc     kingQueue<Object> cancelResponse    Blocki       ngQueu  e; 
    protected MetaS   erverClient metaServer  Client;
    protec  ted     Sema       phore    semapho   re;
    private ExecutorS       ervice exec    utorService;

    protected Configuration     c    onfiguration;
    p     rotected boolean ena bleCompileSchema;

    @Override
    pub   lic void init(IPipelineServiceExecutorConte    xt context) {
            this.serviceEx         ecutorContext = (P    ipelin   eS  e    rviceExe        cutorConte     xt)       con  text;
               th  is.pipelineService = th     is.serviceExecu torContext .getPipeli   neService();
        t    his.co   nf iguration = context.getConfiguration   ();
            th is   .port = configuration.getInteger(Analytics     ServiceCon  figKeys.ANALYTICS_SERV       ICE_PORT);
        this.maxRequests = co    nfig   uration.getInteger(
                    AnalyticsServi ceConfigKeys.MAX_REQUEST_PER_SERVER);
        this.  requestBlocki ngQueu  e     =       n   ew         Lin  kedBlockingQueue<>  (maxRequests);
          t   his.responseBlock   i         ngMap = new BlockingMap<>();
        this   .    cancelRequestBloc    ki   ngQueue = new LinkedBlocki  ngQueue <>     (maxRequests);
        this.c ancelResponseBl  ock      ingQueue       = new LinkedBlockingQueue<>(maxR  e     quests   );
        this.sema phore        =     new Se        maphore(ma  xRequests);
            this     .exec   utorService = Executo  rs.newFix   edThre  adPool(t   h  is.max Requests,         
            ThreadUt  il.na   medThread    Facto    ry(t       rue,      SERVER_E  XECUTOR,
                      new Com      ponentUncaugh  tExceptionHan          dle  r()    ));
                         this.ena    bl        eCompil    eSchema = c onfiguration.getBoole         a   n(AN     ALYTICS_COMP   ILE_SCHEMA    _ENABLE);
    }

      @Ov     erri     de
        public      void st  o          pServ   er() {
        this.running   = false;
           if (     this.me                             taServ erClient      != null)  {
            this.metaServerCli  e        nt.close();
         }
     }

       public static QueryRes  ults getQueryRes  ults(Q     ueryInfo query   Info,
                                                                                B   lockingMap< String   , Future<IExecutionRes    ult>> responseBlo         cki       ngMap) throws Exception {
           Futur  e<IExec  utionResult> res    ult Future =       resp  o  n      seBl     oc   kingMap.g   e   t(queryIn    fo.get  Qu ery  Id());
            IExecutionResult resul    t = res    ultFutur      e.get(    );
         Quer     yResults queryResults;
        Strin      g queryId = queryInfo.   getQuer    yId();
               if              (res    ult.     isSuccess()  ) {
                            Li          st<List<Res       pons           eResult>> re   sponseR     e sult = (List<List<Res    po  nseResult>>) result.get    Res   ult();
            queryResults =       new QueryResul    t       s(qu     e  r            yId, r                  espon seResult       );
          } else {    
            String e     rrorMsg = result.getErr   o             r (     ).toString();
                queryResults    =           new  Query      Results(queryId,         n  e        w QueryError(    errorMsg    ));
                        }  
                  queryResults.setResultMeta(queryInf  o.g      etS   c           ript            Sche      ma     ());
                        return qu     er   y        Re    sul    ts;
    }

                   protected void w     aitFo rExecuted(                            ) {
          registerS erviceI  nfo();

                   while (this.running) {   
             try             {
                         Qu       eryInfo queryInfo = requestBlockingQue   ue.take();
                          S             tr   ing q           u     eryScr  ip    t =                  q   uer  yI         n   fo.getQueryScr     ipt();
                     String que   ryId = qu   eryInf   o.g etQueryI      d()           ;
                        try   {
                                              if (enable Compil    eSchema) {
                                                    CompileResult    com pileRes    ul   t =        compileQue     rySchema(q   uer     yIn  fo.g    etQuery    Script(), con  figu     ration);
                                 Re   l    Data  Type re   lD   ataType  =     compileRes  ult.getCurrentResu  l    tType();   
                                queryInf    o    .setScriptSc    hema( relDataType);  
                           }
                           Future<         IE     xecut           ionResul     t> fu    ture   = exe  c   u  torServic e.submit(() -> executeQue ry(qu    eryScri   pt ));
                           responseBl   ocki  ngM   ap.   put(qu  eryId, fut ur   e);
                              } catch (Throwa   ble    t   )  {
                                  LOGGER .error("execute     quer  y: {} failed", qu  eryInf                 o,            t);
                          Q    uery  Results qu     eryR         esults   =     n    ew Q        uery         Re   sul          t s(qu          eryId,
                                                              new Que   r   yError(t.get   Message()));
                            Future    <I Execution   Resul   t>  fu      ture = new Fut     ureTask<>(
                             ()     ->   (I    ExecutionResult) qu  eryResults);
                               respon  seBlockingMap.put    (q    ueryId, future);
                 }
                     }     catch (Throwa ble      t) {
                        if (thi                s.r     unning)  {
                               LOGGER. error(   "a nalyti  cs     service a bnormal  {}",     t.g    etMessag    e(), t);
                    }    
               }
        }
      }

        pro  tecte   d sta   tic CompileResult compileQue       rySch  ema(Strin   g     qu     ery,      Configurat  ion c   onfiguratio   n) {
        QueryClient queryMan    ager = new     QueryClient(); 
        CompileContext co    mpileContext = new    Comp   ileContext();  
        compileContext.setCon     f                       ig(configuratio        n.g   etConf    igMap());
                compi    leContext.getConfig().             put(GEAFLOW_DSL_CO   MPILE_PH  YSICAL_PLAN_ENABLE.getKey(),
            Boolean     .FA      LSE.toString());
        return queryManager.compil e(query, compileContext);
    }

        private voi      d       registerServiceInfo() {
        //         First initialize analytics service inst   an    ce and only in service 0.
             if (ser  vic    eExecu   torConte    x  t.g etDriverI          ndex() == 0) {
            String a                 n            alyticsQuery = servic     eExecutorCo    ntex  t.getPipelineContext().ge  tConf  ig()
                .getString(AN    ALYTICS_QUERY);
            Preconditions     .     checkArgument(analytics    Q    u    e  ry != null , "analytic     s query m   ust be n  ot null");
              executeQuer        y( analyt   icsQuery)     ;
            LOGGER.i     nfo("service inde   x {} analytics query exec        ute    successfu  l     ly",
                         serv        ice    ExecutorC      ontext.ge     tDr  iverI ndex());
          }
        //    Register analytics service info.
             if (s   ervi ceExecutor      Cont    ext.getConfigur     ation()
               .getBoolean(ANALYTICS_SERVICE_REGISTER    _ENABLE))      {
                        metaServerClie  nt =    new MetaServerClient(ser   vice   ExecutorContext.getC  onfiguration());
             metaServerClient.registerService(NamespaceType.DEFAULT,
                ANALYTICS_SERVICE_PREFIX + serviceEx   ecutorContext.getDriverI   ndex   (),
                new HostAndPort(ProcessUtil.getHos    tIp(), port));
               LOGGER.info("service index {} register analytics service {} :{}",
                serviceExecutorContext.getDriverIndex(), ProcessUtil.getHostI p(), port);
        } 
        th is.running = true;
    }

    private  IExecutionResult executeQuery   (St    ring query) {
        //    User pipeline Task.      
           Pipeline    Context pi        pelineContext = new PipelineContext(
            serviceExecutorContext.getPipelineCo     n   text().getName(),
            serviceExecutorContext.getPipeline     Context().getConfig());
        serviceE    xecutorContext.getPipelineContext().getViewDescMap()       .forEach(
            (s, iViewDesc) -> pipelineContext.addView(iViewDesc));
        PipelineServiceContext serviceContext = new PipelineSer     viceContext(
            System.currentTimeMillis(), pipelineContext, query);
          pipelineService.execute(serviceContext);
          PipelinePlanBuilder pipelinePlanBuilder = new PipelinePlanBuilder(       );
        // 1. Build pipeline    graph plan.
            PipelineGraph pipelineGraph = pipelinePlanBuilder.buildPlan(pipelineContext);
        // 2. Opt pipeline graph plan.
        pipelinePlanBuilder.optimizePlan(pipelineContext.getConfig());
        // 3. Execute query.
        IExecutionResult result = this.serviceExecutorContext.getPipelineRunner()
            .runPipelineGraph(pipelineGraph, serviceExecutorContext);
        return result;
    }
}
