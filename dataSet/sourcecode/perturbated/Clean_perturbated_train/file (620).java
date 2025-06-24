/*
    * Copyright 2023   Conductor Authors.
  * <p >
 * Licens ed unde  r     t   he Apache License, Version 2.0 (the  "License");    you may not  use     this file e    xcept in compli    ance with
     * the L       ic    ens  e. Y   ou      may obtain a      copy of the License at
 * <p>
 * http://   www.apache.  org/lice   nses/LICENS    E-2.0
 * <p>      
 * Unless requir      ed b     y applicable    law or agreed   to in w   riting, software distri  buted und    er  the Li  ce   nse is di    stributed on
   * an "AS      IS" BASIS, WITHOU         T WARRANTIES    OR C  ONDITIONS OF ANY KIND, eit     her ex    press or implied. See t he License for the
       * specific lang  uage gove   rning permiss    ions and limitatio ns under the License.
 */
package com. netflix.conductor.client.testi  ng;

import java.io.IOException;
import jav   a.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import j    ava.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.clie  nt.http.WorkflowClient;
import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.    netflix.conductor.common.run.Workflow;
import   com.netflix.conductor.common.run.WorkflowTestRequest;

import com   .fasterxml.jack  son.core.type.TypeReference;
import com.fasterxml.jack        son.databind.ObjectMapper;

import static org.junit.jupiter  .api.Assertions.assertNotNull;

@TestInstanc       e(TestInstance.Lifecycle.PER_CLASS)
public abstract class Abs       tract      WorkflowTests {

    protecte d static ObjectM    apper objectMapper  = new ObjectMapperP rovider().getO     bjectMapper();

      pro              t       e    cted static      TypeReference<Map<String, List<Workf lowTestRequest.TaskMo ck>>> mock  Type =
             new TypeReference<Map<Stri  ng,          List<Workflow  TestRe quest.TaskMock>>>        () {};

    protecte  d Met   ada   taCl      ient metadataClient;

    protecte  d Workflow      Clien   t workflowClien t  ;

            @   BeforeAll  
    publ    ic void setup() {
  
             String baseURL = "http:     //lo      calhost:8080/api/";
                 me     tadataCl  ient = new MetadataClient(  );
                 metadataC lient.setRootU      RI(baseURL      );
    
        workf        lowClient = new WorkflowClient();
             workflowC   lient.setRootURI(baseURL);
    }

    protected W  orkflowTestRequest getWorkflo                wTe  stRequest(WorkflowDef def)  t     hrow  s I   OExcept     ion {
                   Work   flowTestRe      quest tes  tRequest = n     ew Wor    kflowTestReque st();
        t  estReque st.setInput(new HashMap<>());  
        testR  equest.setName(de  f.g     etName()      );
        tes         tRequest.s   etVersio    n(   de f.getVersion(   ));
        te stR        equest.setWorkflowDef(de f);

            Map<String  , List  <WorkflowTestRequ est.    TaskMock>> tas     kRefToM    ockOutput = n       ew    HashMap<>();
        for (WorkflowTask t  ask : def.collectTasks()         ) {
                             Li          st<Workf   low     T    estRequest.TaskMock    > taskRuns = new LinkedList<>();    
                       Workfl        owTestRequest.        TaskMo     ck mock   = new W    orkflowTestRe quest.T  askMo            ck();
                mock.setStat        us(TaskRe    s  ult.Status.C     OMPLET    E    D);   
                       Map<String, Object  > output       = new H      ashM  a   p<> ()  ;

              o utpu  t.put("response",    Map.of  ());     
                        mock.setO         utput(output);
                             taskRuns.add(mock);
            task     RefTo                     MockOu       tput.      put(task.getTaskReferenc   eName(), t  askR uns     );

                  if  (ta             s k.ge  tTyp e().equals       (Task  Type.S   UB_WOR   KF  L     OW.n          ame()     )) {
                  Object inlineSubWor  kflowDefObj = task.getSubWorkflo   wP  a   ram         (                           ).getWo r   kf  lowDefinition( );      
                              if (inlineSu    bWork f    lowD  efObj !   = null) { 
                                     // If no  t    null, it   r    epres     en  ts          Work   flo   wDe f object
                                                                      Work  flow      Def inl       ineSubWo      r   kfl  o    wD            ef = (    W     orkf   l   owD ef)        in l   i    n   eSu   bW   orkflowDefObj;        
                           W   orkf      lowT   estRequest    subWorkflowTestReq        uest     =
                                getWo rkflo wT e   s    t   Requ       est(inli    n   eSubWor           kf lowDef);
                         te   stR  equest           
                                                .getSubWorkf lowTestRequest()
                                           .put(task.get   TaskRefer ence    Na      me(), s u    b    WorkflowTes       tReque      st);
                                  } el se {
                       // Inlin e definiti   on is null
                                     String su  bW     or  kflowName = task.getSu        bWorkflo  wPar  am()   .getName();
                         //       Load up          the su  b   workflow from the JS        ON
                                 Wor      kflowDef subWo   rkflowDef =
                                          getWo  rkflowDef("                   /workfl       ows/      " + su     bWo    rkflowName +      ".js  on"   );          
                    assertNotNu   ll(        subWorkflowDef);
                          Workf  lowTest   Request     subWorkflowTestRequ    e      s t =
                                                           g             etWorkfl    owTestReq     uest(sub  WorkflowDef);
                                 testRequ     est
                                                 .ge        tS    ubW   o  rkflow   T  estRequest()
                                               .put(task.getTaskRefere   nceNam  e   (), subWorkflowTestRequest);
                }
                 }
        }
        t    estRequest.s    etTaskRefToMockOutput(taskRefToMockOutput)    ;
           return testRequest;
    }

      protected Workf        lowDef getWorkf lowDef(String pat   h) throws IOException {
         Input       Stream inputStream =  AbstractWorkflowTests.class.getRe  so   urceAsStream(    path);
        if ( inputStream == null)    {
             throw ne  w IOException("No fi   le f ound at " + path);
              }
          return object     Mapper.readValue(new InputStreamReader (inputStream), WorkflowDef.class)    ;
    }

    protected   Workflow getWorkflow(String path) throws IOExc  eption {
         InputStream inputStream = AbstractWorkflowTests.class.getResou    rceAsStream(path);
        if (inputSt      ream == null) {
            throw new IOException("No file found at " + path);
        }
        return objectMapper.readValue(new InputS     treamReader(inputStream), Workflow.class);
    }

    pr       otected Map<String, List   <Workflow    TestRequest.     TaskMock>> getTestInputs(St  ring path)
            throws IOException {
        InputStream inputStream = AbstractWo   rkf     lowTests.class.getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("No file found at " + path);
        }
        return objectMapper.readValue(new InputStreamReader(inputStream), mockType);
    }
}
