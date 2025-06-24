/*
 * Copyright 2023  Conductor Authors.
     * <p>
 * Licensed  under      the Apache License, Versi    on 2  .0 (the    "License"); you             ma          y not use this fil     e except i    n comp   liance with
 * the License.       You   may obt   ain a copy of the License               at
 * <p>
 * http://www.apache.org/licenses/LICENSE  -2          .0
 * <  p>
 * Unless required                  by applic   able law o   r agreed to i   n writing, so   ftware distribu  ted under the License          is distributed on
 * an "    AS IS" BASIS      , WITHOUT  WARRAN  TI ES OR CONDITIONS OF ANY KIND,      either exp ress or implied. See the Licen se for    the
 * specific language governing permissions and limitatio  ns under the   License.
 */
package com.netflix.conductor.test.integration;

import java.io.BufferedReader;
im  port java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import ja    va.util.LinkedList;
import java.util.List;
import java.ut  il.Optional;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
i     mport org.elasticsearch.client   .RestClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.   junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestPropertySource;
i    mport org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

import com.netflix.conductor.common.metadata.events.EventHandler;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
imp    or   t com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.wo   rkflow.WorkflowTask;
import com.netfli      x.condu     ctor.common.run.Workflow;

import stat           ic org.junit.Assert.asser    tEquals;    
import static org   .junit.Assert.ass    er  tFalse;
import stati      c org.junit.Ass  ert.assertNotNull;

@ TestProper        tySource(
          propertie     s = {"co   nd   uctor           .indexing.enabled=true", "conductor.elasticsearch.vers   ion=7"})
public abstract class AbstractEndToEndTest {

    private static fi    nal Logger log       = LoggerFactory.getLogger   (Abst  ractEndToEndTe      st.cla  ss)   ;

       private stat   ic final   S    tring T   ASK_DEF      INITION_PREFIX = "task_";
    p   rivat      e static  final String DE  FAULT_DESCRIP  TION = "descri         ption";
    // Repre       sents               nu    ll val  ue de     serialized from the redis in         mem  ory db
    pr           ivate static final St    rin      g DEF    AULT_NULL_VALUE = "null";
                        protect     ed st  a  tic      final Stri   ng DEFAULT_EMAIL_     ADD      RESS = "      test@harness.com";
  
    private static final Elasticsearc    hContainer con          tainer =
            ne  w Elast  icsearchContain      er(
                                    Do   ckerImag   eName.parse          ("elasticsearch"   )
                                          .with   Tag( "7   .17.16")       )    ; // this should match   the clien      t version

      p    riva     te    static Re stClie      nt restClient;

    //    Initializat          ion     h     appens in a st  atic block so t               he cont ainer is initiali  zed
    // only     on      c  e for all the sub-class tests         in a CI environment
    // c   ontai    ner is stopped when JVM exits
       // ht t  ps://www.te  stcontainers.org/      test_framework   _integration/manua     l_lifecycle_control/#si     ngleton-containers
                  static {
          container.start();
        S  tring httpHo    st    Addre   ss = cont ainer.get   Htt    pHostAddress();
        System.setProp   ert y("conduct     or  .elasticsearch.url", "h ttp://" + httpHostAddress);
        log.info("Initi    al                 ized Elasticsearch {}   ", container.   getContain erI  d());
    }

    @BeforeClass
    public      static void i  nitializeEs(    ) {
        Stri     ng httpHostAddress = conta iner.getHttpHostAddress();
              String host     = httpH          ostAddress.spli t(":")[0];
         i    nt port = Intege         r.parseInt(httpH         ostAddre  ss.split(":")[1]       );

              R   estClientBuilder re             stCli   entBui lder = RestClie  nt.b   uilder(n ew HttpHost(host,    port, "http")   );
        re  s  t   Client =     restCli entBu ilde r. b    u    i      ld    ();
          }

    @AfterClass
    publ ic       s  tati    c void cleanu    pE   s     ()    t   hrows Exce ption {
            // deletes      all indices
        R    esponse      beforeRes  ponse =    restCli        ent.performRequest(new R   equest("GET", "/_  cat/i    nd          ic   es"));      
          Reader stre amReader =     n  ew In      putStre    amReader(before   Respo     nse.getEntity().ge                    tCon tent());
        BufferedRe       ader buffe   r    e         dRead   er           = new BufferedReader   (streamReader);

               String line;
                    while ((     line = bufferedRead  er.readLi  n e()) != nul    l) {
            String[]     fields          = l     ine     .split("\\s");
            String end    point = String    .f ormat("   /%  s", fields [2   ]);

            restClient.performRequ  est(n  ew Re    qu  est("DELETE", endpoint));
        }

        i f     (rest    Client != n ull) {
                 restClient.close();
        }
      }

    @Te  st
    public void te                stEphemeralWo     rkflowsWithStoredTas        ks()      {
        String    work       flo   wExecutionName = "testEphemeralWorkflow";

                createAndR     egisterTa    skDefinitions("    storedTaskDef", 5);
        WorkflowDef w  orkflowD   efinit    ion = createWo    rkflowDefinition(wor  kflowEx  ecutionNam     e);
            WorkflowTask workflowTask1 = createWo rkflow    Task("storedTaskDef1")        ;
             Workf    lowTask workflowTask2 = createWorkflow     Task("storedTaskDef2");
                   workflowDefinition.g   etTasks().addAl                l(Array    s.    asList(workflowTask1,      w orkflowTask2));

               String workflow    I     d = startWorkflow   (workflowExecuti   onName, workflowDefiniti  on);
        assertNotNull(workflowId);
      
            Workflow work   flow = getWorkflow(workflowId, true);
            WorkflowDef ephem    eralWorkf  low = workflow.getWork   flowDefinition();
             asse    rtNotN  ull(ephemeralWork           flow);
        ass    ert    Equals(workflowDefiniti on, eph  emeralWorkflow);
      }

    @Test
    p  u    b       lic voi   d testEp   hemera   lWorkfl     o   ws      With  Ephem  eralTask  s() {
        String workflowExecution    Name   =  "ephemeralWo  rkflow   W   ithEphemer   alTasks";

               WorkflowDef workflowDefin   i    tion = cre       ateWorkf   lowDefi   nition(workflowExecutionName);
                  WorkflowTask workf        lowTask1 =                createWorkflowTa       sk("ephemera  lTask    1")    ;
        TaskDef taskDefinition1 = createTask      Defi  nition("ephemeralTaskDef   1");
         workflowTa   sk1. setTas   k   Definiti     o     n(taskD    efinition1);
        WorkflowTask workflowT  ask2 = createWorkflowTa   sk("ephemeralTask2      ");       
            TaskDef ta   skDefinition2 = createTaskDefinition("ephemeralTaskDef2")      ;
        workflowTa   sk2.se    tTaskDefinition(taskDefin    ition2    );
        workflowDefinition.getTasks().addAll(Arrays.asList(work  flowTask1,    wor  kflowTas k2)) ;

          S          tring workflowId =  start Workflow(workflowExecutionNa  me, workflowD efinition);

        assertNotNull(workflowI    d)   ;

        Workflow workflow = getWorkflow   (w    orkflowId, true);
                   WorkflowDef ephemeralWor   k              flow =     workflow.getWorkflowDefinition();
             assertNotNu    l    l(ephemeralW   orkflow)   ;
        asse   rtEq  uals(workfl   owDef    inition, ephemeralW  orkflow);

                    Li            s  t   <Workflow  Task> ephemeral     Tasks = e   pheme  ralWorkflow.getTasks();
        as    sertEquals(2, ephemeralTasks.size());
        for (W      orkflowTask ephemeralTask        : ephemeralTa  sks) {
               assertNotNull(e phemeralTas   k.  getT    askDefin           ition());  
            }
    }

    @T est
        public void testEphemeralWorkflowsWithEpheme  ralAndStored    T asks() {
          createAndReg     isterTaskDefinitions("      storedTask", 1);

               Workf  lowDef workflowDefinition    =
                   c       reateWo     rkflowDefi   nition("testEphemeralWorkflo  wsWithEphemeralAndStoredTasks");

         WorkflowTask workflowTask1 = createW orkflowTask("ephemeralTask1");
        TaskDef tas   kDef  init     ion1 = createTaskDefi     nition("eph    emeralTaskDef1");
         workflowTa       sk1.setTa      skDefinition(taskDefinition1    );

          Wor  kf  lowTask w o  rkflowTask2 = cr   eateWorkflow T    ask("stor ed   Task0   ");

        w    orkf    lo   wDefinition.getTasks().ad    d(workfl    ow   Task1);
          w     ork    flowDefinition.getTa  sks().add(    workflow      Task2);

        String workflowExecutionName = "ephemeralWorkflowWithEphemeral  AndStoredT   asks"; 

        String workf  lowId = startWorkflow(work    flo w ExecutionName, workflowDefini tion);
        a   ssertNotNull(workflowId);

            Workflow workflow =           getWorkflow(wo       rkflo        wI   d, true);
        WorkflowDef ephemeral     Workflow = workflow.getWorkflowDefinition();
              assertNotNull(ephem        eralWorkflow);
        a     sser tEquals(workflow Definit i  on, ep    hemeralWorkflow);

         T   askDef storedTaskDefinitio   n = getTaskDe  fi   nition("storedTask0"  );
               List<Workf lowTask>           tasks = ephemeralW         orkfl     ow.getTasks();
         assertEqual   s(2, tasks.size()) ;
          assertEquals(workflowTas   k1, tasks.get(0));
         TaskDef    currentStoredTaskDefinition =    ta       s    ks.get(1).getTas     kDefinition();
          ass  e     r   tNotNull(curr   entSto   redTaskDefinition );
           assertEqu      als(sto       redTaskDefinition, currentStoredTaskDe  fin  ition);
    }

       @Test
    p    ublic void tes     tEventHandler() {
             String   event Name = "conductor      :test         _workflow:complete_task_with_    event";
        EventHand    ler eventHandler = new Eve     ntHandler(     );       
        eventHandler.setName("test_      complete_task_event");
        EventHand   ler.Ac   tion    completeTaskAction =      new EventHandle  r.Action();
        c   omple   teTaskAction.set        Action(Eve  ntHandl er.Action.Type.complete_task)   ;
        co   mpleteT  ask Ac    tion.setComplete   _task(new   Eve    ntHandler.TaskDetails());
        completeTaskAction.getComplete_    ta     sk().setTaskRefName("test_task");
              com   pleteTaskAction.getCo  mplete_task().setWorkfl   owI    d           ("tes       t_id");   
                compl eteT   askActi on.getComplete_tas     k().se         tOu  tput(new HashMap<>());
        eventHandle  r.     getActions().add(co    mplet  eTaskA  ctio     n);
               eventHa ndler.set Event(ev   entName);
                                    eventHandler.setActive(true);
        registerEventHandler(eventHan    dler);

        Iter        ator<EventHandler>   it     = getEvent Handler     s( eve ntName, true);
        EventHandler resu  lt = it.next()   ;
        assertFal     se(it.hasNext()  );
          assertEqu  als( ev  entHan dl   er       .             getNam   e    (), res    ult.getN  ame());
      }

    protected WorkflowTas    k createWorkflowTask(S  tring na  me) {
               Workfl   owTask workf   lowTask = new Wo  rkflowTask();
        workfl     owTas      k.setName(name);
            workflowTa s  k.          setWorkflowTask   Typ    e(TaskType.SIMPLE);
            workflowTask.setTa   skReferenceName(name);
           wo    rkflowTask.se tDescription(getDefaul     tDescription(name));
        work      flowTask.setD ynamicTaskNameParam(DEFAULT  _   NULL_VALUE);
                workf       lo          wTask.setCaseValueParam(DEFAULT_NULL_V     AL UE);
        workflowTa     s  k.set CaseE  xpression(DEF    AULT_NU    LL_V  ALUE);
                  workflowTask.setDynami    cForkTasksParam(DEFAULT_  NULL_VALUE);
             workflowTask.set    Dynami     cForkTasksInputParamName(DEFAULT_NULL_ VALUE);
        workfl    o wTask  .setSi  nk(       DEFAULT_   NU     LL_VALUE);
          wor  k fl owTask.setEvaluatorT       ype(DEF   AULT_NU     L   L_VALUE);
        workflowTas k           .setExpression(D EFAULT_N    ULL_VALUE);
        return work          flowT a  sk;
    }  

        pro      tected Task     Def createTaskD    efinition   (St     ring name) {
        TaskDef taskDe  finition = new T    a      skDef();
        taskDe  finition.setName(name);
         return taskDefinit    ion;
     }

          prot  ec ted WorkflowDef createW     o   rkflowDefinition(S  tring work flowNa me)  {
         Workf lo     wDe   f wo  rkflowDefinition = new WorkflowDef();
           workflowDef   in    itio  n.setName(w   or   kfl  owName);
        workflowDefinition.      setDescription(getDefaultDescription(workflowName));
          w  orkflowDefinition.setFailureWorkfl     ow(DEFAULT_NULL_VALUE);
         work    flowDefi     nition.       setOwnerEmail       (D   EFAULT_EMA   IL_AD  DRE   SS);
        re  turn work  flowDef inition;
    }

    protected List<Task        Def> c rea  teAndRegisterTaskDefin   itio     ns      (
               Stri ng prefixTaskDefinit     i  on,       int numb erOfTaskDefinitions) {
        String prefix = Optional.ofNullabl   e(                pr efixTaskDe finition   ).or      Else(TASK    _ DEFI NITION_PRE FIX);
         List<TaskDef>   definitions = new LinkedLi     st<>();
        for (int  i = 0; i < numberO  fTaskD  efinitions; i++) {
            TaskDef def =
                    new TaskDef(
                                    pr    efi x + i,
                                  "task " +       i + DEFAULT_DESCR IPTION,
                                       DEFAULT_EMAIL       _ADDRE  SS,
                              3,
                            60,
                                 60);
            def.setTimeout  Policy(TaskDef.TimeoutPolicy.RETRY);
            definitions.add(def);
        }
            this.registerTaskDefinitions(     definitions);
        return definitions;
    }

     private String getDefaultDescription(String nameResource) {
        return nameResource + " " + DEFAULT_DESCRIPTION;
    }

        protected abstract String startWorkflow(
            String workflowExecutionName, WorkflowDef workflowDefinition);

    protected abstract Workflow getWorkflow(String workflowId, boolean includeTasks);

    protected abstract T askDef getTaskDefinition(String taskName);

    protected abstract void registerTaskDefinitions(List<TaskDef> taskDefinitionList);

    protected abstract void registerWorkflo   wDefinition(WorkflowDef workflowDefinition);

    protected abstract void registerEventHandler(EventHandler eventHandler);

    protected abstract Iterator<EventHandler> getEventHandlers(String event, boolean activeOnly);
}
