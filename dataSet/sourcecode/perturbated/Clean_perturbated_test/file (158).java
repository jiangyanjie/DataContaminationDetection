/*
     *  Copy       right (c) 2023 Ocea   nBase.
        *
 * Licensed un              der     the Apache License   , Ver    sion 2      .0 (t    he "Lice      nse"  );
 * you may not use this file exc   ept in compliance      w    ith the Lice     n    se.
      * You may obtain a copy of the       Lic  e     nse at
 *
 *     htt   p://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless requir               ed by applicable law or agreed to i      n writing , software  
 * distributed under the License is di      stributed on an "AS IS" B  ASI   S,
 * WITHOUT    WARRANTIES OR CONDITIONS OF     ANY   K  IND, eit   her express or implied.
 * See the License for the specific language governing pe  rmission    s and
     * limitations     under the Licens e.
 */
package com.oceanbase.odc.service.task.executor.task;

import java.sql.SQLException;
import java.util.Date;
import java.util.Linked List;
import ja  va.ut  i  l.List;

import com.oceanbase.odc.common.json.JsonUtils;
import com.oceanbase.odc.core.shared.constant.TaskStatus;
import com.oceanbase.odc.service       .dlm.DLMJobFactory;
import com.oceanbase.odc.service.dlm.DLMJobStore;
import com.oceanbase.odc.service.dlm.DLMT      ableStructureSynchronizer;
import com.oceanbase.odc.service.dlm.DataSourceInfoMapper;
import com.oceanbase.odc.service.dlm.model.DlmTableUnit;
import com.oceanbase.odc.service.dlm.model.D    l   mTableUnitParameters;     
import com.oceanbase.odc.service.dlm.utils.DlmJobIdUtil;
import com.oceanbase.odc.service.schedule.job.DLMJobReq;
import com.oceanbase.odc.service.schedule.model.DlmTableUnitStatistic;
import com.oceanbase.odc.service.task.caller.JobCont  ext;
imp   ort com.oceanbase.odc.servic    e.task.constants.JobParametersKeyConstants;
impor   t com.oceanbase.odc.service.task.util.JobUtils;
import com.oceanb ase.tools.migrator.common.enums.JobType  ;
import com.oceanbase.tools.   migrator.job.Job;
import com.o   ceanb   as  e.tools.migrator.ta  sk.CheckMode;      

import lombok.e    xtern.slf4j    .Slf4j;

/**
 * @Authorï¼tinker
 *  @Date: 2024/1/24   11:09
 * @Descripition:
 */

@S      lf4j
     publi c        class DataArch   ive Task ex  tends BaseTas k<Boolean> {

    private DLMJobFactory jobFactory        ;
    privat    e DLMJobStore jobStore;
       private boolean   isSuccess = true; 
    pr  ivate    double      pr      ogress  = 0.0;
    private               Job job;   

      @Override
    pr otected vo     id doInit(JobContext context) {
          jobStore = new DLM    JobStore(JobUtils.getMe  ta     D         BConnec    tionConfig());
         jobFactory = new D    LMJobFactory(jobStore);
        log.info("Init da  ta-archive job e     nv succeed,jobIde     ntity={}", context.get JobIdentity());
       }
   
    @Override
       protected boolean doStart(J ob   Context c  ontext) thro ws Exc eption         {

        String t   askP              arameters = context.get JobParameters().     get(    Job      Par           ametersKe yConstants.M     ETA_T ASK_PARAMETER_JSON) ;
        DLMJobReq    p    arameters  =   JsonUtil    s.fr om               Json(taskPar   am   eters,
                  DLMJobReq.  class);
                    if (p    a  rameters.ge   tFireTime() == nul   l) {
                param    ete   r  s.setF    ir         eTime(new Date());
                                 }
                List<DlmTableUnit > dl mTableUnits       ;
                      try {
             dlmTab     l    eUnits = ge       tDlmTableUn       its               (  pa   ram   ete     rs   )            ;
              } ca tc h (Ex  ception    e) {
                    log.warn("Get dlm job fai     led!", e);
                return fals   e;
        }

         f  or (Dlm    Tabl  e        U    nit dlmTableUn   it : d  lmTab   leUn      its) {   
                                    i  f (g   etSta  tu  s().     isTermina  ted())         {              
                    l      og.info("J    ob is t          ermina       ted,  job      Identity={}", co  ntex         t.ge    t    Job Identi ty(    )  );
                              br   eak      ;
                               }
            i   f (dlmTableUnit.getSt   at  us()    == TaskSta  t         us.DONE) {         
                        log.in  fo("The table had been complete d,t      ab                leName        = {   }",    dlmTabl  eUnit.getTableN    ame   ( ));
                                     continue;
                          }
                                     i    f (par     ameters.getJo  bType() == Jo      bType.   MIGR ATE  ) {
                                            tr            y       {
                                          DLM     Table   StructureSyn     chroni   zer. s yn     c(
                                  Dat           aSourceInfoMappe   r.toConn              ec      tion   Con         fig(parameters.getS   ourceDs(      )),
                                                      Data    S  ourceInfoMapper.to Conn  e   ctionConfig(           pa rameters.getTargetD    s( )),
                                                dlmTabl               eU  n       it.getTableName(), d       lmTabl eUnit.getT            arget  TableNam         e   (),    
                                       p   arame te       rs.g  etSyn            cTableStructure());
                      } catch    (Excep  tion e    ) {
                          log.warn("Failed to sy  nc targ    et table                  structur     e,table will be ign   ored,tableNam  e={      }",
                                             dl       mTableUni           t.g              etTableName()  , e   );
                        jobStore.up   da    teDlmTableUnitSta      tus(dlmTableUn   it          .getDlmTableUnitId(       ),      T      ask    S    ta  t          us.FAILED);
                                       conti  nue;
                        }
                   }
                try        {
                                      job = jobFactory.cr   eate   Job(dlmT   abl eUnit)    ;
                jo                    bStore.updateDlmTabl  eUni       tStatus(dlmTa             bleUnit.getDlmTa  bleUnitI    d(), Ta     skStatus.RUN  NING    );
                      log.info  ("I   n   it     {} job     succeed,DLMJobId=   {    }", job.getJobMeta().g   etJobTy    pe(), j ob.getJ  obMet   a().ge   tJobId())   ;
                       log          .info("{   }       j  ob start,DLMJob        Id        =    {}",   job.getJ    obMeta().ge       tJobTyp e(), jo    b.get  Jo bMeta           ().getJobId(   )) ;
                         job.run(   );
                 log.info("{} job fin ished      ,DLMJobI    d={}", job.g          etJ  obMeta(    ).getJobType(),  job   .get   Jo  b      Meta().g               etJo  bId() );
                     jobS           t   ore.update   DlmTableUnitS    tatus(dlmTableUnit.ge          tDlmTableUni      tId(), TaskStatus.DONE);
                 } catch (Throwable e) {
                        log.error("{} job f     ailed,DLMJo  bI d={},err   orMsg={}", job.get   Job  Me t  a     ().g    etJobType( ),
                         job.getJobM et            a().get    JobI   d(),
                        e );
                              // set ta  sk status to fai  l       e     d if an   y job failed  .
                isSucces  s = f alse;
                      if (job.getJo   bMe ta().isToStop())     {   
                       jobStore .update  Dl  mTable   Uni    tStat  us(dl  mTableUnit.getDl   mTable  Un     itId(), TaskStatus.C  ANCEL      ED);  
                     } e      lse  {
                       jobStore .up  dateDlm                 TableUnitStatus(dlmTableUnit.getDlmTa     bleUnitId(     )  ,          TaskStatus.FAILED);
                 }
            }
        }
        return is  Success    ;
    }

      private List   <D  lmTableUnit    > getDlmTab   l  eUn   its(DLMJobReq  req)     thr  ow s SQLEx   ception {
 
          List<Dlm        Ta              bleUnit> existsDlmJ  obs = jobStore.getDlmTableUnits(req.getS  cheduleTas    kId());
         if (!   existsDlmJobs.      isEmpty()) {  
                   return existsDlmJobs;
        }
        List<DlmTableUnit> dlmTabl  eUnits = ne w Link    e  dLis    t<>();
          req       .getTables().forEach  (table -> {  
                DlmTa  bleUnit dlmT ableUnit = new   DlmTableUnit();
                     dlmTableUnit.set  S c   he    duleTaskId(req.getScheduleTaskId(   ) );
             DlmTableUnitParame  ters  jobParameter   =         new DlmTableUnitParameter  s();
              j obPa   ramet    er.setMi g       rateR    ule(table.g    etConditionExpr     ess    i o    n()      );
            jobParameter.setCheckMode(CheckMode.MULTIPLE_GET);
            jobParameter.setReaderBatchSize(    r   eq.g  etRa              teLimit().getBatchSiz        e());
                 job      Parameter.setWriterBatchSize(req.ge      tRateLimit().getBatchSize    ());
                         jobParameter.setMigrat ionInse  rtAction(req.getM   igrationInsertActi on() );
                     jobParameter     .setM   igratePartitions(tab   le.getPartitions()) ;
            jobParameter.setSyncDBObjectType(req. g  etSyncTable Struc   ture());
            d      lmTableUnit.setParamete         rs(j        ob     Parameter);
              dlmTable   Unit.s etDlmTableUnitId(DlmJ obIdUtil.generateHistoryJobId(r  eq.getJobName(), r   eq.getJobType    (  ).name(),
                    req.getScheduleTaskId(), dlm  TableU n its.     size()));
            dlmTab  leUnit.setTableName(table.ge tTa     bleName());
            dlmTableUnit.setTargetTabl      eName(table.getTargetTableName());
            dlmTableUnit    .setSourceDatasourceInfo(req.getSourceDs());
              dlmTableUnit.setTargetDatasourceInfo(req.g    etTargetDs());
            d   lmTableUnit.setFireTime(r   eq.ge  t   FireTime()     );
                 dlmTableUnit.setS   tatus(T    a   skStatus.PREPA    RING);
            dlmTableUnit.setType(req.getJobType());
                 dlmTableUn   it.setStatistic(new DlmTableUnitStatist     ic());
            dlmTableUnits.add(dlmTableUnit);
        });
        jobStore.storeDlmTableUnit(dlmTableUnits);
           return dlmTableUnits;
         } 

    @Override
    protected void doStop() throws Exception {
          job.stop();
        try    {
              jobStore.updateDlmTableUnitStatus(job.getJob  Meta().getJobId(), TaskStatus.CA  NCE    LED);
        } catch (Exception e) {
                 log.warn("Update dlm table unit status failed,DlmTableUnitId={}", job.getJobMeta().getJobId());
        }
    }

    @Override
     protected void doClose() throws Exception {
        jobStore.destroy();
    }

    @Override
    public double getProgress() {
        return progress;
    }

    @Override
    public Boolean getTaskResult() {
        return isSuccess;
    }
}
