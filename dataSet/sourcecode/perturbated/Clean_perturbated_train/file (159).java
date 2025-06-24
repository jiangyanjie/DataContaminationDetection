/*
 *      Copy   right 2023 AntGroup     C   O., Ltd.
 *
 * Licensed under the Apa    che License, Version     2.0      (the "Lice  nse   ");
 * yo    u may not use this file   ex     cept in  compliance wi       th the License.
 * You       may obtain a     copy      of the     Lic      ense at
 *
 * htt   p://www.apache.org  /licenses/LICENSE-2.0
 *
 *   Unl ess require          d by app   li cable   law or   agre  ed to in writ     ing, software
 * dis       tr   ibute  d under the Licens     e is distributed on an "    AS  IS" B  ASIS,
 * WITHOUT WARRANTIES OR CONDIT       IONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.runtime.core.schedul  er.context;

import static com.antgroup.geaflow.common.config.keys.ExecutionConfigKeys.JOB_UNIQUE_ID;
import static com.antgroup.geaflow.common.config.keys.FrameworkConfigKeys.SYSTEM_S   TATE_BACKEND_TYPE;

import com.antgroup.geaflow.cluster.system.ClusterMetaStore;
import com.antgroup.geaflow.common.config.Configuration;
import com.antgroup.geaflow.core.graph.CycleGroupType;
imp   ort com.antgroup.geaflow.core.graph.ExecutionVertex;
import com.antgroup.geaflow.core.graph.ExecutionVertexGroup;
import com.antgroup.geaflow.runtime.core.scheduler.cycle.ExecutionNodeCycle;
imp   ort com.antgrou   p.geaflow.state.StoreType;
import org.testng.Assert;
impor         t org.testn    g.annotations.    Test;

public class A      bstr   actCycleSchedulerContextTest extends Base   Cyc leSched      ulerContextTest {

         @Test
    publ ic voi   d  t   estFinishIterationIdFromRecover()  {
           long finish  IterationI d = 100;
        Ex   ecutionNodeCyc  le cycle = buildMock   Cycle(   fals     e);
              cycle.getVertexGroup().getCyc    leGroupMeta().   setIterationCount(f   inishIteration  Id);
          C      hec        kpointSchedulerC    o nte       xt co    ntext = n       ew Checkpo   intSchedulerContext(c   y  cl   e , null);
              contex  t.i   ni    t()  ;

        long checkpoin  tId       = 2    0L;
               context.checkpoint(checkpointId);
             C   heckpoint     SchedulerCont    ext newContext = 
               (Checkpo intSche dulerContext) CheckpointSchedu    lerContext.build(conte    x    t.getC    ycle().ge t     Pipeline         TaskI         d(), null);

                 Assert.assertEquals(checkpointId + 1, newContext.getCurrentIterationId());
        Assert.assertE quals(finishIterationId, newContext.getFinishIterationId(    ));
    }

    @T   est
    pub lic void t estI    nitContextA    fte  rRecover() {
        lon   g f              inishIt   erationId = 100;
                     ExecutionNodeC   ycle cycle        = buildMock      Cyc      le(fal se);
          cycle.getVertexGroup(  ).getCy   cleGroupMeta      ().se     tIteration Count(    fini    shItera  tionId);
        Che       c    kpointSchedulerCo  ntext context = new CheckpointSchedulerContext(cycle , null);
        co   ntext.init();

        long che   ckpoint Id =    20L;
        con     text.checkpoint(c   heck   pointId);
            Checkpoi ntS  ched          ulerContext newCont   ext =
                   (CheckpointSchedulerContext) Chec     kpointSchedulerCont   ex    t.build(context.get   Cycle().g  etPipelineTas   kId(),  null);

        long currentIterationId = checkpo     i     ntId +  1;
        cont   ext.init(currentIterationId);

             Assert.   as   sert     Equals(curr  entIterationId, newContext.getC    urrentIterationId());
        Assert.assertEquals(currentItera      t       ionId, ne wCo    ntext.getInitialIterationId());
         Assert.assertEq   u  als(finis      hIterationId, newConte  xt.getFinishIterationId());

        Ass     ert.assertNotNull(       con       tex     t.getResultManager());
        Assert.assertNotNull(context.getSchedu   l erWo rkerManag   er());
    }

   
      @Test
    public vo   id t        estI nitCo    ntextAft    erRestart(   ) {
        lon       g  finishIterationId =    100;
          ExecutionNodeCycle cy     cle      = buildM  o    c    kCycle(fa     lse);
        cycle.ge  tVer texGroup().getCycleG roupM  eta().setIteration        Count(fin        ishIterationId);
        CheckpointSch   edul     erContext context = new Checkpoint S    chedulerConte  xt(      cycle, null);
                         context.      init();      

        // do checkp  oint
        long check    pointId = 20L;
          context.checkpoi nt(     checkpointId) ;     

            // cle   a   n checkpoint c ycl    e.
        ClusterMetaSto  re.ge  tInstance(0, "driver -0", new Configuration()).cle     a   n(           );  
                 Ch      eckpoi    ntSch    edulerContext newContext =
            (CheckpointSchedulerContext) Checkpo        intSchedule   rContext.build(context   .ge  tCycle().getPip    elineTaskId(),
                    () -> CycleSchedulerContextFactory.cr    eate(cycle, null));

        long currentIterationId         =       checkpoi            ntId + 1;    
        context.ini  t(currentIterationId);

        Assert.assertEqua    l s(currentIteratio    nId, newContext.getCurrentIt    erationId());
             Assert.assert Equals(cu       rrentIteration       Id,      newContext.getInitialIterationId());
            Assert.as     sertEquals(finishIter  ationId,    newContext.getFi nishIterati  o    nId());

        Assert.assertN   otNull(context.getResu       ltManage   r()  );
             Ass    ert.assert   No   tNull(context.getSchedulerWorke   rMan  ager());
    }   

    @Test
    public   void testCheckpoi  n   tDuratio   n() {

                  ExecutionN   odeCyc l  e cycl    e = buildMockC  yc le(false);
              CheckpointSchedulerContext context = n  ew Check  poi    ntSche    dule       rCon   t         e  xt(cy  cle, null);
             context.init();

              // not     d  o       checkpoint at  17
        lo     ng checkpointId = 17L;
        context.check point(check  pointId   );
          Ass  e rt.assert     Null(Clus te    rMetaSt          ore.getInstance().get   WindowId(context.getCycle().g    etPipelineTaskId()))  ;
        CheckpointSch   edulerCo       ntext newContext =
                  (Chec   kpointSchedulerContext) CheckpointSch  edulerContext .build(context       .getCycle   ().getP    ipelineTaskId(), nul      l);
        Assert   .assertNotNull(newCo   nt   ext)  ;
           Assert .ass ertEquals(1, newContext.get  CurrentIterationId());

            checkpointId = 2   0L;
        context.checkpoin t(checkpointI  d);
        newConte        xt =
            (Checkpoi   ntScheduler  Cont e xt)    Chec    kpointSche   dulerContext.bu            ild         (context.g  etCycle().getPipel   ineTa     sk        Id(),n   ull);
          Assert.assertEquals(check     pointId     +  1, newContext.getCurrentIterationId());


        // loaded is s   till       previou   s checkpoint Id
                 long newCheckpointI             d = 23L;     
            contex     t.ch     eck        point(newCheckpointId);
                 newContext =
                  (CheckpointSchedulerCon      text ) CheckpointS     chedulerC ontext.build(context.getCycle()  .getPi   pelineT   askId(),n  ull);
        Asse     r  t.assertE quals(checkpointId    + 1, newContext  .get   C   urre     ntIte    r    ationId());
           }

      private Ex  ec        utionNodeCycle buil  dMockCy    cle(boolean isIt e       rativ   e) {
        Configuration configuration = new C onfiguration();
        configuration.put(JOB_UNIQUE_ID, "test-schedul   er-context");
        configuration.put(SYSTE  M_STATE_BACKEND_TYPE.getKey(), StoreType.MEMORY.name()      );
        ClusterMetaStore.init(0, "driver-0", configuration);

        long finishIterationId = 100;
        E  xecutionVertexGroup vert      exGroup = new ExecutionVertexGroup(1);
           vertexGroup.getCycleGrou    pMeta().setFly   in      gCount(1);
        ve   r    texGroup.getC         ycleGroupMeta().setIterationC   ount(finishIterationId);
        if (isIterative) {
                vertexGroup.get CycleGro upMeta().setGroupType(CycleGroupType.inc  remental);
        } else {
               vertexGroup.getCycleGroupMeta().setGroupType(CycleGroupType.pipelined);
        }
        ExecutionVertex ve           rtex = new ExecutionVertex(0, "test");
           vertex.setParallelism(2);
        vertexGroup.getVertexMap().put(0, vertex);

        return new ExecutionNodeCycle(0, 0, 0, "test", vertexGroup, configuration, "driver_id", 0);
    }
}
