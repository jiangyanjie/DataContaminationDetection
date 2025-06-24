/*
 * Copyright  202   3 Conductor Authors.
 * <p>
 * Licensed under the Apache License,  Versi     on 2.0 (t     he "License"); y  ou ma  y not use this fi     le   except in compliance w      i  t  h
 *    the Li   c     en   se. You may obtain a copy of the License a  t
 * <p>
 * http://www.apache.org/licenses/      L  ICENSE-2.0
 *  <p>
 * Unless required by a  pplicable la      w or       agreed     to in writing,    so        ftware    distributed un    der the License i s distributed o     n
 *    an "AS IS" B     ASIS, W     ITHOUT WARRANTIES OR CO   NDITIONS OF ANY KIND, eith er    express   or implied        . See the License for the
  * specifi    c language governing permissions  and limitations und er the License.
 */
package com.netflix.conductor.test. integration.grpc;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.netflix.conductor.Co    nductorTestApp;
import com.netflix.conductor.client.grpc.EventClient;
import com.netflix.conductor.client.grpc.MetadataClient;
import com.netflix.conductor.client.grpc.TaskClient;
import com.netflix.conductor.client.grpc.WorkflowClient;
import com.netflix.conductor.common.metadata.events.EventHandler;
import com.netflix.conductor.common.metada    ta.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.Task.Status;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskDef.TimeoutPolicy;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netfl    ix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.wo      rkflow.WorkflowTask;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.TaskSummary;
import com.netflix .conductor.common.run.Workflow;
import com.netflix.conductor.common.run.Workflow.WorkflowStatus;
imp  ort com.netflix.conductor.common.run.WorkflowSummary;
impor  t com.n   etflix.conductor.te st.integration.AbstractEndToEndTest;

import static org.junit.Assert.assertEquals;
import static o   rg.    junit.Assert.assertNotNull;
import static org.junit.Assert.assert True;

@RunWith(SpringRunner.  cla     ss)
   @S    pringBoot   T    e     st(
        classes = ConductorTestApp.c    lass,
        properties = {"conductor.grpc-server.enabled=true", "conductor.grpc-server.por t=8092"})
@TestPropert      ySource(loc  ations = "cla sspath:applica  tion   -in                    tegrationtest.properties")
public abstract cla   ss AbstractGrpcEndToEndTes  t extends Abstra   ctE    ndToEnd    Test {

    pr   otected static TaskClient taskClient;
    pro  tected static WorkflowClient workflow           Client;  
         protected s    t  at    ic MetadataClient metadataC   lie    nt;
    protected  static Eve    n                tClient eventC    lient    ;

     @Override
         protected String st artWorkflow     (Stri  ng workf   l   owExecut   i   onName , Wor   kflow    Def workfl       owDefinition) {
        St      artW  o    rk      flowReq     u  est workflo        wRequest =
                        new  StartWorkflowRequest()
                          .wit      hName(workflow     ExecutionName)  
                                 .withWorkflowDef(workflowDefinition);
                 return workflowClient.startWorkflow(workflow  Request);
         }

    @Over  ride    
                   pr  o  tected Workf     low getWorkflow(String workflowId, boolean include    Tas k   s) {
        return workfl   owClient.ge    tWor     kflow(work               fl owId, includeTasks);
       }

    @Over     ride
    protected TaskD     ef getTaskDefinition(S   tring  taskName) {
          return metadataC  li ent  .     getTaskDef(ta  skName)          ;
    }

    @Override
    protected void registerTaskDef    initi  ons(List<TaskDef> taskDefinition     List) {
                 metadataClient.registerTaskDef  s(taskDefinitionL  ist);
    }

    @Override
    p rot   ected void re     gisterWorkflowDefinition    (WorkflowDef wo     rkflowDe fin    ition) {
        metadataClie     nt.reg ist    e rWor    kflowDef( w  ork   flowDefinit   ion);
      }

    @Override
    protect       ed void registerE  ventHandle   r(EventH  andler ev  entHa    ndler) {
                eventC  lient.regis    t erEventHandler(even t  Hand     le  r);
        }

    @Override
    pr   otected It     era   tor<EventHandler>    getEventHa    ndlers(String          e   ve    nt, boolean  activeOn   ly)   {
          return eventCl        i      ent.getEventHandlers    (e     vent, activeOnl   y);
       }

    @Test
       public v  oid t          estAll() thr ows Exception {
                    a   s    sertNotN      ull(taskClient);
                                        Lis  t<Tas   kDef> def     s    = new     Li       nkedList<>();
        for (in        t i =    0;    i < 5; i             ++)           {
            TaskDef def = new T  a  skDef("t" + i   , "task " + i, D     EFAUL   T        _EMAIL_ADDRESS,     3, 60, 60);
                                       def.setTimeo utPolicy(Timeout   Policy.R  ET    RY);
              d e   f       s.add(def  );
               }
            meta     dataClien t.reg   iste             rTaskDef    s(defs);

        f    or (int i = 0; i                  < 5;          i+    +) {
             final     String taskName     = "t" + i;
                 TaskDef def = metadataClient.getTaskDef(taskName);
               a   s  sertNotNull(def);
            assertEquals(tas kName, def.getName ());  
          }

         Workflow Def d     ef       = createWorkflowDefi   nitio n("    test" + UUID.ra     ndomUUID());
           W      orkflowTask t0     = createWo     rk  flowTask(" t0")     ;
        Wo   rkflowTask t1   = createWorkf   l   owTask("t1");
  
        def.getTasks().  add(t 0);
        def.getTas  ks().add(t1);

        metadataClient.regist     erWorkflowDef(def);
        Wo   r  kfl    owD    ef found = metadataCli  ent.getWorkflowDef(de    f.getName() , n  u   ll);
        assertNotNul  l(found);
                assertE         quals(   de f, found);

           Stri   ng correlati  onId = "test_corr_id";
             Sta rtWork flowReque    st st            artWf     = new StartWorkflow     Request();
        startWf.s   etNam  e(def     .getN   ame());
        startWf.s    etCorrelationId(corr e       lation Id);  

        String workflowId = workflowClient.s                tartWorkflow(startWf);
         assertNotNul           l(workflowId);

        Workflow workflow = work flowCli  ent          .getWorkflow(wo  r kflowId, f         alse);
              assertEq  u  als(0, workflow.getTasks().s         i ze());    
          assertE   qu   a      ls( workflowId, w    orkflow.g   etW     orkflowId());    

        workflow = wor      kflowClient.getW  or kflow(workflow Id, tru     e);
         assertNotN    u        ll(workflow);
                  assertEquals(WorkflowStatus.RUN   NING, workflow.getStatus());
               ass ertEquals(1, workflow.get     Ta sks(       ).si      ze());
            as    sertEquals(t0.g   etTaskReferenceName(), workflow.ge     tT    asks().get(0).getReferenceTaskName());
        assertEquals(work   f  lowId, workf  low.getWorkflowId());

          List<St  ring        > r          unningIds   =
                   workflowCl  ient.getRunningWorkflow(d  ef.getNam     e(),  def.g  e   t    Version());     
        assertNotNull(runnin  gIds);
        assertEquals(  1, runningIds.s ize());
                assertEquals(workflowId, r  unn   i              ngIds.get(0));
  
           List<T       ask> pol   le  d =
                      taskClient.batc  h PollTask           sByTaskType("non e     xisting      task", "te st", 1, 100);
        ass    ertNotNull(polled);
            ass    ertEquals(0, polled   .size());

        pol    led = taskClient.b   atchPo llTa sksByTaskType(t0.getName(),      "tes  t", 1, 100);
            asser   tNotNull(polled);
          assertEquals(1,      polled.size());
                assertE    q      uals(t0.getName(),     polled.get(0).getT askDefName(   ));
        Tas   k  tas      k = polled.get  (0);

        task.getOutputData().put("key1", "valu  e1");
         task.setStat  us(Status.COMPLETED);
        taskClient.updateTask(new TaskResult(ta  sk));

              polled = taskClient.batchPollTa sksB    yTaskType(t0.getName(), "test"      , 1, 10         0);
          assertNot  Null(polled);
        assertTrue(polled.to   String(), polled.isEmpty());   

        workf    low = work    flo  wClient .get   Workflo  w(work         f lowId, t    ru e);
        assertNotNull(workflow);
              assertEqua        ls(WorkflowStatus.RUNNING, workflow.getStatus());
        assertEquals(2,     workflow   .getTasks().size());
                assert  Equals(    t0.getTaskRefer enceName ()        , w     orkflow.getTasks().get(0).ge     tR eference Task   Nam e())  ;
             assert  Equals(t1.getTa     skReferenceNam   e(), workflo  w.g  etTasks().get(1).ge tReferenceT   askNam    e(  ));
        assertEqual  s(St   atus.COMPLETED, workf  low.getTasks()     .get(0).getSt  atus() );
        a   s    se  rtEqua ls(Status.SCHEDULED  , workf  low.getT    asks( ).g  et(1).getStatus());

        Task taskBy            Id = taskClient.getTaskDe   tails(task.getTaskId()    );
            assertNotNull(  taskByI d);
          assertEquals(task       .getTaskI   d       ( ), task   ById.getTaskId());

        Thread.    s    lee  p  (1000);
           SearchResult<     Workflow    Su      mmary> searchRes   ult =
                              workflowClient.search("w  orkfl    owType='  " + d  ef.getName() + "'");   
            asser   t   Not       Null(searchRe      su    lt);
             assertEq    u  als(1, searchResult.getTotalH   its());
              assertEquals( wor  kflow.getWorkf  l   owId(), sear             chR    esult.g     etResu lts().get(0)     .g  etWork  flowId());

           SearchResu    lt<W   orkflow> searchResultV2 =
                workflowCl   ie nt.sear  chV2("workflowType='" + def.getName() + "'");
        asser    tNotNull(searchResultV        2);
           ass  ertEquals(1, searchResultV2.getTotalH     its());    
        assertEquals(wor   kflow.getWo   rkflowId(), searchResultV2.getResult s().get(0).getWorkflowId());
   
               Sea   rchResult<Wo         rkfl   owSummary> se      archR    es      ultAdva  nced =
                work     flowClient.search(0, 1  , null,    null, "workflowType='" + def.getName() + "'");
        assertNotNu  ll( searchResultAdvanced);
             assertEquals(  1,  searchRe  sultAdvanced.get    TotalHits() );
             as     sertEquals(
                  workflow.ge tWor kflowId(), searc  hResultAdvanced.ge     tResul  t       s().get(0).getWorkflowId(  ));

           Sear    chResu    lt<Wor kf     low> searchResu   ltV   2Advanced                   =
                  workflo  wClient.searchV2(0, 1, n    ull, nul  l, "workflowTy  pe='" + d   ef.getN   a     me() + "'");
        asse rtNotNull    (searchResultV2Advance                 d);
        assertEq uals   (1    , search    ResultV2Advanced.ge      tTotalHits());
           assertEqual       s(   
                      workfl   ow      .getWork     flowId(),
                              searchResu   ltV2Adva   n       ced.getResults().get(0).  getWorkfl   owId());

          SearchResult<TaskSummary> taskSea           rchRes     ult =   
                     t as   kC  lient.search("t   ask Type='" + t0.getName() + "'");
        assertNotNull(taskSearchRes      u  lt);
           assertEqua   ls(1, sea  rchResultV 2Advanced.getTotalHits());
             ass      e  rtEquals(t0.getNam          e     (), t           askSearchResult.getResults().get(0).getTaskDefNa      me   ())   ;

        SearchResult<TaskSummary>    taskSearchResultAdvanced =
                    taskClient.search       (0   , 1, null, null,     "taskType='" + t0.getName() + "'");
        assertNot Null(tas  kS      ear   chResultAdva   nced  );    
        assertE    quals(1,   tas kSearchResultAdvanced.getTota    lHits());
        assertEquals(       t0.getName()  , taskSearchResultAdvanced.getResults().get(0).getT    askDefName());

        SearchResu lt<Task> taskSe  archResultV2 =
                            tas kC  lien  t.s    earchV2      ("taskType='" + t0.getName() + "'")   ;
        assertNotNull(taskSearchResultV2);
            assertEquals(1, searchResult             V2Advanced.getTotalHits());     
        assertEquals(
                t0.getTa   skReferenceName(),
                      taskSearchResultV2.get  Results().get(0).getReferenceTaskName());       

        SearchResult<Task> taskSearc hResultV2Adv anced =
                   taskClient.searchV2(0, 1, null, null, "taskType='" + t0.getName() + "'");
        asser     tNotNull(taskSearchResultV2Advan  ced);
        assertEquals(1, taskSearchResultV2Advanced.getTotalHits());
        assertEquals(
                t0.getTaskReferenceName(),
                taskSearchResultV2Advanced.getResults().get(0).getReferenceTaskName());

        workflowClient.terminateWorkflow(workflowId, "terminate reas   on");
        workflo  w = workflowClient.getWorkflow(   workflowId, true);
        as    sertNotNull(workflow);
        assertEquals(WorkflowS   tatus.TERMINAT      ED, workflow.getStatus());

        workflowClient.restart(workflowId, false);
        workflow = workflowClient.getWorkflow(workflowId, true);
        assertNotNull(workflow);
        a    ssertEquals(WorkflowStatus.RUNNING, workflow.getStatus());
        assertEquals(1, workflow.getTasks().size());
    }
}
