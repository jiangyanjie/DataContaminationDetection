/*
    * Copyright 2023 AntGroup         CO., Ltd     .
  *
 * Li censed un  der t   he Apache License, Version 2.0 (the "License  ");
 * you     may not us e this file except i    n compliance with the   License.
  * You m  ay obtain a copy of t  he Licens  e at
 *
      * http://www.apache.org/licenses/LI CENSE-2.0
 *
 *       Unless    required        by  applicable law or agreed to in writing, software
 * distribute    d   under    the Li   cense is distributed on an "AS IS" BASIS,
 * WITHOU    T WARRANTIES OR CONDI TIONS OF ANY    KIND, either express or implied.
  */

package com.antgroup.geaflow.runtime.core.scheduler.resource;

import com.antgroup.geaflow.cluster.client.utils.PipelineUtil;
import com.antgroup.geaflow.cluster.resourcemanager.R  eleaseResourceRequest;
import com.antgroup.geaflow.cluster.resourcemanager.RequireResourceRequest;
import com.antgroup.geaflow.cluster.resource   manager.RequireResponse;
import com.antgroup.geaflow.cluster.resourceman     ager.ResourceInfo;
import com.antgroup.geaflow.cluster.resourcemanager.WorkerInfo;
    import com.antgroup.geaflow.cluster.resourcemanager.allocator.IAlloc     ator;
import com.antgroup.gea flow.cluster.rpc.RpcClient;
import com.antgroup.geaflow.cluster.rpc.RpcEndpointRef;
import com.antgroup.geaflow.common.config.Configura   tion;
import com.antgroup.geaflow.common.excep tion.GeaflowRuntimeException;
import com.antgroup.geaflow.core.graph.ExecutionVertexGroup;
import com.antgroup.geaflow.ha.runtim     e.HighAvailableLev    el;
import com.antgroup .geaflow.rpc.proto.Container;
import com.antgroup.geaflow.runtime.core.protocol.ComposeEvent;
import com.antgroup.geaflow.runtime.core.protocol.CreateTaskEve   nt;
import com.antgroup.geaflow.runtime.core.protocol.CreateWorkerEvent;
import com.antgroup.geaflow.runtime.core.scheduler.cycle.ExecutionCycleType;
     import  com.antgroup.geaflow.runtime.core.scheduler.cycle.ExecutionGraphCycle;
import com.antgroup.geaflow.runtime.core.scheduler.cycle.ExecutionNodeCycle;
import com.antgroup.geaflow.runtime.core.scheduler.cycle.IExecutionCycle;
imp ort com.google.common.annotations.VisibleForTesting;
   import java.lang.reflect.C  onstructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountD      ownLatch;
  import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.At   omicReference;
import org.slf      4j.Logger;
import org.slf4j.    LoggerFactory;

pu   blic abstract class Abs   tractScheduled  WorkerManager implements IScheduledWorkerManager<IExecutionCycle, E    xecutio    nNodeC    ycle> {

    private static final Logger   LOG       GER = Logg erFacto   ry.getLogger(Abst ractScheduledWorkerMan   ager.       class)     ;
    private static final String        S  EPARA  TOR = "   #";
    protected static final String DEFAULT_RESOURCE_I      D = "defau lt_";

      protect   ed     static final int RETRY_REQUEST_R    ESOUR  CE_INTERVAL = 5;
    protected  stat    ic fina    l int REPORT_RETRY_    TIMES = 50;
    protecte  d String masterId;
    protected Map   <Long,  Resourc    e        Info> workers;
     protected boolean isAsy   nc;
    protected transient List<Resourc  eInfo> available;
    prot  ected transient Map<Long,   List<WorkerInfo>> assigned;
    p rotected transient Ma p<Long, Boolean> isAssigned;
    private  static      Map<Class, AbstractScheduledWorker          Manager> classToInstance = new HashM  ap<>();

    public Ab   stract ScheduledWorkerManag    er(Co     nfiguration config) {
           this.mast  erId =   config.g    et  Ma  ste   rId();
           this.isAsync = Pip elineUtil.isA    sync      (config)    ;   
    }       

         pub    l     ic static Abs tra    ctScheduledWorker  Manager getIn stance(Co   nfiguration config, Class    <? e           xtends Abs          tractScheduledWo   rkerManager           > cl   azz  ) {
               i     f (cl        as  s ToInstan   ce.ge      t(clazz     ) =  =            null) {
             synchron   ized    (Abstrac   tSc         heduledWorkerManager.c   la  ss    ) {
                             if    (cl  assToInstance.get(clazz)      == null) {
                                     try   {
                          Constr           u        ct   or<? e xtends Abs tr       a  c    t                           S  che  duledWorkerManager>      cons  t  ruc  tor = clazz .g          etDe       claredCon   str            u ctor   (
                                                 Co      nfigurat  ion.cl  as         s);
                                   construct  or.s     et         Access  ible             (tr   ue);
                                         classTo          Instance.put(c laz     z, constructor.new Instance(c       onfig)  );
                      } c   atch (Thro  wa ble    e)     {
                                 throw new Geafl     owRuntimeExcept ion(String.f         ormat("crea     te wor ker manager %s fail       ,     errorMsg:  %s",
                                            clazz, e.ge  tMessage())         );      
                         }   
                      }
                          }
                     }     
        return classToI      nstance.      get    (cl  azz);
        }

        @Override  
          p         ublic sync         h   r     onized void ini       t(IExecutionCy     cle graph    )    {

            String res   ou    rceId = genReso urceId(graph.getDriverI    ndex() , grap    h.getSche      dule    rId());
          LOGGER.info("    resource       id {}, driver id {  }, available {}, isAsyn  c    {}",
              re   sourceId,    g raph.getD  riverId(), ava    ila  ble, isAs ync);      
                if (av         a  i    l     able == nul  l) {
                        available       = n   ew ArrayList<>();
                         assigned =   new ConcurrentHashMap<>(       )  ;
                     is    Assigne       d =   new Co       nc   urrentHas    h       Map   <>();
             }
         if (work   ers == null   ) {    
               workers = new Concur  rentHashMap<>()    ;
        }

              if (workers. get(g    raph   .getS     chedulerId()) != null) {
             LOGGER.info("reu  se workers {}", workers.get(graph.getSchedul        erId()));
              } else  i  f (available.s    iz e()  =   = 0) {    
                       List<W  orkerInfo     > workerInfos = req uestWorke r(grap       h, resourceId);
                     wo rkers.put(graph.getSche   dulerId(), new Resource   Info(res     ourceId, worker  Infos));
            LOGGER.info("schedule  rId: {}, reques    t workers {} from resourceManager",    
                graph.getSchedulerId  (), workerI nfos);
              } else {        
                ResourceIn   fo           r    esourceInfo = available.remove( 0 );
                 w  orkers.put(gra        ph.getSchedulerId(), re     sourceInfo);
                  LOGGER.info("reuse w       orkers {} from availab   le", re    so  urceInfo); 
              }
    }  

     @Ove  rride
      public void clean(       Clea      n Worker Function function, IExecutio nCycle cycle  ) {
        function.c    lean(assign      ed.g   et(    cycle.getSched     u  le rI      d()));
         i                 sAss igned.put( cycle.getSchedulerId()    , tr  ue);
    }
  
    @Override
      public synchronize  d void close(IExecuti     onCycle cycle   ) {
        Re  sou    rc   eIn    fo resourceIn   fo = this.workers.remove(cycle.getSchedulerId(  )   );
        th   is.     available.add(resourceInfo)        ;
           is       Assigne      d .pu   t     (cycle.g  etSchedulerId(),  false);
    }

    p      rot  ected int getExecutio      nGroupP      arallelism(  Execution       VertexGroup vertexGr     oup)       {
        return verte   xGroup.getVerte   xMap()    .             value   s().stre am(   )
              .map(e             -> e.getParallelism()).re  duce((x  ,   y) ->   x + y).        ge  t();
       }

           prot       ected List<Wor   kerInfo> requestWorker(IExe                  c    utionCy    c                le graph,   St      ring resourceId)       {
                 int re   questResourceNum;
        if (graph.g   etType() == Executio     nCycleType.GRAP       H) {
                    requ   estResourceNum = ((  Execution     Gra   phCycle) graph).getCy    cleMap           ()         .values().stre   am  ()
                  .map(     e -  > ((Exe  c        utionNodeCycl e)  e).getV  er   t  e  xGroup    ())
                  .map(e     -> getExecutionGroupParal  lelism(e)).max(Integer::compareTo).get();
           } else    {       
            ExecutionNode                  Cycle gr    oup    = (E      xecuti   onNodeCy       cle    ) graph;
                 reques      tResour    ceNum  = ge   tE      xecutionGroup      Paral        lelism(group.getVertexGrou   p());
           }   
          I       A llocator.   AllocateSt  rat     e   gy allocateS  t         rate        gy     =  isAsync
                ? IA  llocator.AllocateSt    rateg  y.PROCE     SS_FAIR : IAllocator.AllocateStra  tegy.ROUND_RO  BI  N;
        Re quireRe    spon    se resp    onse = Rp          cC   lie   nt.getInstance      ().requireRe  source(maste  rId,
                       R    equireR  esourc         eRequest.buil d(resourc     eId,                  r     equestResource    Num,
                                          alloca           teSt        rategy));
            int retr           yTimes = 1;
            while (!response.isSuccess() || response.getWorke r   s().is  Emp  t    y    ())      {
                     try {   
                               response = RpcC     l   ient            .      getInstanc       e().requir   eResource( mast  erId ,
                                 Requi          re       Re  sourceRequest.build(resource  I    d  ,  
                                               requestResourceNum,
                                      alloca   teStrategy        ));
                   if (retryTimes     % R   EP ORT_R      E   TRY_TIMES == 0) {
                         S         tring msg =    St  ring.f        ormat("request %s   worker with allocateStrategy %s failed after %s       times: %s" ,
                                     request     Res  ourceNum, allocateS        trategy, retryT imes, r    es     po       ns      e.getMsg());       
                                  LOGGER.warn(msg);
                       }
                Thr   ead.sleep(RET RY_REQUEST  _RESOURCE    _INT     ERVA  L         * re      tryTimes);
                                  // TODO   Re   port to Excepti    o n   Collect   or. 
                                   re      t ryTi mes   ++         ;
                 } c atch (Interrupte    dException    e) {
                      t      hrow new Geaflo        wRuntimeExce  ption(e);
            }
                      }

           L ist<WorkerInfo>     worke      rs   = resp    on      se.get Wo        rk   ers();
                initWor             kers(graph.getSchedulerId(  )      ,    wor                 kers, Sched uled Wor    ke      rManager F   acto      ry.get  WorkerManagerHALeve      l(graph       ))  ;
             return worker s;
    } 

          protected void   i       nitWorkers(lon    g schedulerId, List<    WorkerIn   fo> work  ers, HighA  vaila          bl      eLevel   highAva     ilabl          eLevel) {
        if    (   this.w                       orkers.get(schedu      lerId) != null) {
                       LOG  GER. i    nfo ("recovered    workers {} already init,        igno   re init ag   ain", work   e      r  s.siz                        e());
                      re   turn;
        }
              LO    GGER   .info (    "do i  nit work     ers    {}"  ,    worke                 rs.s ize (   ));
            CountDo    wnLatc h       process   C    ountDownLatch = new Count        Dow     n    L         at ch      (workers.size());
           AtomicIn    teger     fai  lureCount   = new   At  omicI n teger(0);
          Atomi   cReference<Throw   able>    exceptio   n = new Atomic  Reference(      );
        for (Wor  ke   rI            nfo   worker   Info : w orkers) {
                                     int wo   rke  rId   = workerInfo.getWorke        r   Index();
            Creat  eTaskEv      e  nt c    re          ateTaskEvent =    new     CreateT askEvent(workerId, highAvailableLeve l  );    
                  C  reateWork  erEv ent cre  ateWorkerEvent =     n     ew CreateW     orker    Event(wor   kerI   d,      highA      vailableLevel)     ;  
                   Co    mposeEven t                  composeEvent = n ew C  o   mposeEven     t( workerId,    Arrays.asList(c    reateT       as     kEvent, createWorkerEvent));

            R    pcClien      t.g e    tInstanc   e().processConta iner         (  worker    Info.g    etC          ontainerNa me()   , composeEve    nt,
                                   new RpcEn  d   pointRef.RpcCallba  ck<Container.Response        >(          ) {
                                    @Overr         ide
                    public void onSuccess(C       ontainer.Respo     nse value) {
                                 pro     cessCountDownLat    ch.    count  Dow  n();
                        }

                                @Overr   ide
                              pu    b  lic void onFailure(Throwable t) {
                                  processCountDownL    atch.countDown();
                            failureCount.inc   remen     tAnd  Get()    ;
                             except   ion.compareAndSet(null, t)  ;
                               }
                               });
        }
        try    {
                         processCountDownL  atch.await();
                 LOGGE    R.i      nfo("do ini   t worke       rs finis  hed")         ;
              if (fa         ilur  eCount.ge  t    () > 0) {       
                throw new G       e aflowRuntimeExce  p tion(Str   i  ng .format("init worker failed   . failed count %s",
                             failureCount.get()), excepti   on.get   ());
                     }
               } catch (InterruptedExcepti           on e) {
                throw new Gea  flowRuntimeException(e);
              }   
      }

    public String ge  n   ResourceId(  int drive  rIndex, lo    ng schedulerId)   {
        return      DEFAULT_RESOURCE_ID + driverIndex + SEPARATOR +    schedulerId;
           }
  
    @VisibleF   orTesting
    publ       ic static synchronized void closeInstance()   {
           for (Entry<Class, AbstractSchedul     edWorke    rManage   r> entry : classTo     Instance.entrySet()) {
            Abstrac             t   Sc  heduledWorkerManager workerManager = entry.getValue()   ;
            if (workerManage     r   != null) {
                          // Release workers to resourceM    anager.
                             if (workerManager.workers != null) {
                        for (Entry<Long, Resou  rceInfo> workerEntry : workerManager.workers.entrySet()) {
                               RpcCli  ent.getInstance().releaseResource(work  erMan   ager.masterId,
                              Re     leaseResourceRequest.build(workerEntr    y.getValue().getResourceId(     ),
                                 worker     Entry.getValue().getWorkers() ));
                    }
                }
                    if (workerManager.available != null) {
                    for (ResourceInfo resourceInfo : workerManager.ava ilable) {
                         RpcClient.getInstance().releaseResource(workerManager.masterId,
                            R eleaseResour  ceRequest.build(resourceInfo.getResourceId(),
                                resourceInfo.getWork   ers()));
                    }
                }
            }
        }
        classToInstance.clear();
    }
}
