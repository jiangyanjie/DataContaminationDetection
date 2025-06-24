/*
 * Copyright        (c) 2023 OceanBase.
 *
    * Licensed   under      the Apache License, Version 2.0 (the  "License");   
 * you may not  use this file    except   in compliance            with the L icen  se.
 * You may     obt    ai     n a co       py    of the License a     t
 *
 *     http://www.apache  .org/l ic       enses/L ICENSE-2.0
 *
     * Unless r  equ   ired by a    pplicable    law or    agreed to in writing, software
 * distri     b  uted under the License is distribut    ed   on     an "AS I       S" BASI S,
 * WITHOUT WARRANTIES OR CON  DITIONS O       F A       NY KIND, either express or implied.
 * See the Lice    nse fo   r the specific language govern    in g permissions and
 * limitations un   der the License.
 */
package com.oceanbase.odc.service.flow.task;

impor       t java.io.File;
import java.io.InputStream;
import j   ava.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.comm    ons.collections4.CollectionU  tils;
import       org.fl   owable.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;

im  port com.oceanbas  e.odc.common.json.JsonUtils;
import com.oceanbase.odc.core.flow.exception.  BaseFlowException;
import com.oceanbase.odc.core.shared.Verify;
import com.oceanbase.odc.core.shared.constant.Dial  ectType;
import com.oceanbase.odc.core.shared.constant.FlowStatus;
import com.oceanbase.odc.core. shared.constant.TaskType;
import    com.oceanbase.odc.core.sql.split.OffsetString;
import com.oceanbase.odc.core.sql.split.SqlStatementIterator;
import com.oceanbase.odc.metadb.task.JobEntity;
import com.oceanbase.odc.metadb.task.TaskEntity;
import com.oceanbase.odc.service.connection.model.ConnectProperties;
import com.oceanbase.odc.service.connection.model.ConnectionConfig;
import com.oceanbase.odc.service.datasecurity.DataMaskingService;
   import com.oceanbase.odc.service.flow.exception.ServiceTaskError;
import com.oceanbase.odc.service.flow.task.   model.DatabaseChangeParameters;
import com.oceanbase.odc.service.flow.task.model.DatabaseChangeResult;
import com.oceanbase.odc.service.flow.task.model.DatabaseChangeSqlContent;
import com.oceanbase.odc.service.flow.task.model.FlowTaskProperties;
import com.oceanbase.odc.service.flow.task.model.RollbackPlanTaskResult;
i      mport com.oceanbase.odc.service.flow.task.util.DatabaseChangeFileReader;
import com.oceanbase.odc.service.flow.util.FlowTaskUtil;
import com.oceanbase.odc.service.objectstorage.ObjectStorageFacade;
import com.oceanbase.odc.service.objectstorage.model.ObjectMetadata;
import com.oceanbase.odc.service.sqlcheck.SqlCheckUtil;
import com.oceanbase.odc.service.task.TaskService;
import com.oceanbase.odc.service.task.config.TaskFrameworkProperties;
import com.oceanbase.odc.service.task.constants.JobParametersKeyConstants;
import com.oceanbase.odc.service.task.enums.JobStatus;
import com.oceanbase.odc.service.task.exception.JobException;
import com.oceanbase.odc.service.task.runtime.DatabaseChangeTask;
import com.oceanbase.odc.s   ervice.task.runtime.DatabaseChangeTaskParameters;
import com.oceanbase.odc.service.task.schedule.DefaultJobDefinition;
impo   rt com.oceanbase.odc.service.task.schedule.JobDefi        nition;
import com.oceanbase.odc.service.task.schedule.JobScheduler;
import com.oceanbase.odc.service.task.service.TaskFrameworkService;
import com.oceanbase.odc.service.task.util.JobUtils;
import com.oceanbase.tools.sqlparser.statement.Statement;
import com.oceanbase.tools.sqlparser.statement.alter.table.AlterTable;
import com.oceanbase.tools.sqlparser.statement.createindex.Cr eateIndex;
import    com.oceanbase.tools.sqlparser.statement.createtable.OutOfLineConstraint;

import        lombok.extern.slf4j.Sl  f4j;

@Slf4j
public class Data baseChangeRuntimeFlowableTa        skCopied extends    BaseODCFlowTask   Delegat  e<Da  t   abase ChangeResult> {

          @Autowired      
    private ObjectStora    g  eFacade ob  jectStorageFacade;
    @    Autowire  d  
    priva  te ConnectProperties c   onnectPro  perties;
    @Autowi   red
    private JobSc            hedule    r     jobScheduler;
    @Autowire   d
    private Ta     s     kFr    ameworkService tas kFrameworkService;       
    @Autowi red
                  priv  ate TaskFrameworkProperties taskFrameworkProp  ertie s;
       @Autowired
    private DataMas  kingServic  e dataMaskingS  ervice;
    @ Autowired
        p          rivat         e Tas kS      ervice taskService;
    @Auto   wired
        private FlowTa   skProperties flow        TaskProperties;
    @Autowire  d
    pr  iv   ate Ob jectStorageFacade storage  Facade;

                 priva te volatile L    ong jobId;
    priva   t   e volatile b    oolean isSuccessful = false;
      priva   te volat    i      le    boolean isFailure = fal      se;
    pr    ivate volatile boolean i s  Canceled = false;

    @Override
       pub     lic b     oolea        n cance  l(boole         an mayInterru ptI  fR     unn        i  ng, Long taskId, Ta  skSe        rvice     taskS               ervice)    {
                try {
                        job   S                              ch     edu   l er. cance l        Job(this.       job Id);
                     try {  
                            jobScheduler.awai             t    (thi         s.jobId, tas     kFram   eworkPro   pe   rties.getJobCanc  elTi   meout     Seconds(),        T imeUnit.SECON     DS)           ;
                                } catch (E xception    e)   {
                                 log   .warn("Ex    c   eption oc    curs   whi  le waiting job cancelled.", e);   
                }  
                            JobEnt   it  y r  ecent           J ob =   taskFramewo    rk  Service.f  ind      (thi      s  .  jo       bId);
                          if    (re    cen        tJob.g    etS    tatus              () =  = J   o   b S   tatus.CANCE  LED) {
                   task              Service.ca     nce       l(taskId);
                            isCance     l ed = tru                 e;
                       return true;
                     } e     lse {
                                      return   false;
            }
          } catch (J    obEx      cep      tion e) {
                        log.wa  rn("Cancel job f  ai  led.",       e)            ;
                 re           turn fals    e;
            }
      }
    
    @Overrid         e
     pu  blic boolean    isCancelled()            {
        return isCanceled     ;
           } 

         @Overr ide
     protected DatabaseChangeResult start(Lo    ng ta    s    kId,   TaskService task    S  ervice, D         elegat        eE       xecuti   on          executi      on)
               throws Excep      tion {
             DatabaseChangeRe  s   ult      result;
          try {
            log   .info("Databas   e change tas         k starts, taskI d=   {}, act    ivityId  ={}", taskId, execution.getCurre  ntActivityId( ));
            taskService.start(taskId);   
                       TaskEntity taskEntit  y =     ta   sk     Se     rvice.detail(taskId);
                                   Verify  .  notNull(taskEntity     , "taskEntit   y");
                      Ro   llbac       kPla            nTas kResult rollbackPlanTaskResul  t =   null;                    
               res  ult =       Jso    n   Ut  ils.fromJson(taskEntity.getR     esultJ    son(),             D    atabase     ChangeRes    u  l   t          .c    la  ss);
                i  f (result != null)          {
                         rollb   a       ckPlanTaskResult = result.getR      ollb  a                    ckPlanRes    ul    t();
            }  

            J     o          bDe    finition jo    bDefinition = buildJobDefini      tio   n(execution, taskEnti   ty);
                thi  s.jobId = jobSc  h     edule   r.sche      dule   Jo  bNow      (jobDefinition)   ;
               taskService.upda      te  JobI   d(taskId,   jobId)     ;      
            log.info("Database change t     ask is sche       duled,    taskId={},   jobId={              }", taskI  d, this.jo            bId);       
                         jobScheduler.     await( this.jobId, FlowTaskUt il          .getExecution  E  xpirationInte     rvalM   il   lis(exe c ution).intVa               lue(),
                                              Ti         meU    nit.MIL    L      ISEC  ONDS);

             JobEntity              jobE     ntity = taskFrameworkS    ervice.find(this.jobId     );
                    resu   lt = J                 sonU     tils.fro   mJson(j  obEntity.g      etRes   u   ltJ   son(     ), Da   tabaseCh  angeR   esul    t.class);
             result.s      etRollbac   kPlanResu  lt(r     oll           backPla   nTaskResult);
               if (jo           bEntit    y.ge   tStatus(        ) == Job Statu   s.D O       NE) {
                              isSuccessful =     true;
                      taskSe   rvice .succeed(taskId, res   ult);
                } else {
                                     i   f (j  obE     ntity.getStatus() == JobStatus.    CANCELED) {
                                      i   sCanceled    =   true;
                        } else {
                              isFail       ure = true;
                  }
                  taskServ  ice               .fail( taskId   , jobEntity.getProgre  ssP    ercentage(), result);
             }
                log           .info("Da          tabase change t   ask e  nds   ,     taskId={}, jobId={}, jobStatus=  {}", tas kId     ,      this.j   obId,  
                    jobEntity.getStatus());
           } catch (Exception      e) {
                log.error("Err   or occurs   while          dat     abase change task executing", e) ;
            if (e instanceof Bas       eFlowException) {
                        throw e;
              } else {   
                  throw new   Se         rvice       TaskErr    o                         r(e);   
            }
        }    
        return res   ult;
              }

    @  Overr  ide
    protecte           d b  ool     ean isSuccessful() {
          retur            n isSucc essful;
         }

    @Override
       protected boolean isFail   ure() {
        return isFailure;
     }

    @Override
    protected void onFailure(Long taskId, Task   Service taskServic e) {
           log.warn("Database change        task f          ailed, task  Id={}", taskId);
        super.onFailure(tas kId, taskService);
    }

    @Ove        rride
    prot ected void   onSuccessf    ul(Long   ta  skId, TaskSer vic   e taskService) {
        log.info("Database change task succeed, tas    kId={}", taskId);
        updateFlo   wIn   stanceSt    atus(Flow  St  atus.EXEC   UTION_SUCCEEDED);
      }

    @Overrid     e
    protected void onTi  meout(Long taskId, TaskService task   Service) {
        log.warn("Database change t  ask timeou       t, taskId={}", taskId      );
         }

       @Ov erride
    protected void onProgress      Up  date(Long tas      kId, TaskService taskService) {

    }

    pr      ivate JobDef inition buildJobDefinitio        n(De        le   gateExecution execution, TaskEntit  y  taskEntity          ) {
        Ma  p<St  ring, Strin      g> jobParameters = new H      ashMap<>();
           DatabaseChangeTa    skParameters taskP       aramet    ers    = new DatabaseChangeTaskPa rameters();
          DatabaseC   hangePar          ameters p =    FlowT   as         kUtil.getA        syncParameter(execution    )    ;  
           tas kParameters.s   etPara  met    erJ son(Js    on       Utils.toJson(p));
                     Conne  c      tionCon  fig config =      FlowTaskUtil.get   ConnectionConfig(execution  );
        config.setDefaultSchema(FlowTaskUtil.getS    chemaName(execution));
        taskParamet ers.se   tC onnection   Co   nfi    g    (config);
          taskPara            met           ers.setFlowInst   anceId(Flow   Ta   skUtil.g               etFlowInstanceId(ex  e  cut  ion      ));
        tas   kPar      ameters.setSes  sionT           imeZone(   conne    ctProperties.    getDefaultTimeZone()  );
          if (Collectio  nUtils.isNotE  mpty  (p.getSqlObjectIds()     )) {
            List<Ob   jectMetadata> objectMetadatas =  new ArrayList<    >();
               Long crea    torId = FlowTaskU  til.get  TaskCreator(executi   on).getId();
                                      f or (String o  bje       ctId  : p.      getSq  lObject   Ids(   )) {
                     objec    tMetadatas.add(o  bjectStorageFac  ade
                                   .loadMetaData("a  s ync".concat     (File.separato     r)   .concat(String.value    O  f(cr   ea  torId)), objectId));
                              }
                 tas      k Parameters.    setSqlFileOb   j    ectMetadata s(          obj     ec     tMeta  datas)    ; 
        }
               t a   skParamete    rs.setNeed       DataMasking(dataMaskingServi           ce.isMaskingE  nable  d());
        mod   ifyTimeoutIf     T               imeConsu  mingSqlExists(p, task Para      meters,    taskEntity, c    onfig.getDialectType(  ),
                    FlowTaskUtil.ge    tTaskC   reator(execu   tion).getI     d());
                    jobParameters.put(JobPar         ametersKeyConstan               ts.FLOW_INSTANC   E_ID, getFlo      wInstanc   e  Id().t oString ());  
        jobParameters.put(JobParametersKeyCo       nstants.TAS         K_PARAMETER    _J    SON_KEY, JobUt    i                ls.toJson(tas    kPar  ameters))          ;
                  jo     bParameters.put(  Job       Paramet     ersKeyCon     stants.T       AS         K  _EXECUTION_TIMEOUT_MILLIS,   p    .ge  t           TimeoutMilli  s   () + "");
                      r       eturn DefaultJ     obDefinit           ion    .builder  (  ).job   Class(   D   a   tabas    eCha    n    ge     T   ask.class)
                          .jobType     (TaskType  .  A SYNC.name())
                                      .jobParamet   e rs(jobP    a ra   meters      )
                     .b  u  ild     ();
    }

    private         void modifyT im       eoutIf        TimeConsumingSqlExists(Datab              a   seC   hangePa rameters param       eters,  
                 DatabaseChangeT       askParameters task       Parameters,   T   as      k En tity task      Entity,          DialectType   diale  ct   Type,
                       Long cr     eatorId         ) {
          long  autoModifiedTimeo ut = flowTaskPro      p erties.getIndexChange MaxTimeoutMillis    econd  ();
                   if (!parame    ters.isModifyTimeoutIf Ti      meConsumingSqlExists()   |  | !      dialectType.isOceanbase()
                  ||    a  utoModifi  edTi  meout <= p arameter            s.get   TimeoutMillis(       )) {
                    taskParam             eters.  se  tAutoModifyT  imeout(false);
                  r    eturn;
            }
                List      <Offset Stri ng>    use    r Input Sqls = null; 
          SqlS  tatementItera               tor uploadFileSqlIte       rator =   null;
               In       putStream upload         F ileInputStream       =  null;
          try {
              taskP  aram             eters.setA utoModifyTimeout(fals   e);     
                                 Data baseChangeSqlContent sql      C   ontent =    
                    Database    ChangeFileReader.getSq   lConten    t(storageFacade   ,      parameters, dialectType,
                                    "asy  nc".con      cat(File.separat or).  con      cat(creat  or     Id.toSt   ring()));
            userInputSqls   = sqlContent    .get   UserInputSqls();
                u  ploadFileSqlI   terator                           = sqlCo     ntent.getUploadFileSqlIterator();
                uploadFileInputStream = sqlCon    tent.getUploadFileInpu  tS  tr    eam ();
             while     (CollectionUtils.    isNotEmpty(use  rIn   putSqls)     
                           || (uploa     dF      ileSqlIter      ator  != null  && uploadFileSqlIterator.has  Nex       t()))   {
                S  tring sql = Col    l      ec tion    Utils.      isNotEm   pty(u      serInpu    tSqls    ) ? use     rI nputSqls.rem  ove(0)       .getS     tr     ( )
                        :    uploadFileSqlIterator.next().getStr();
                             if (check     TimeConsumingSql(S qlCheckUtil.parseS ingleSql(dial     ectType, sq    l    ))) {
                                    parameters.    setTime       outM illis(aut  oModif       iedTimeout);
                     task  Parameters.set AutoModifyTimeout(true);
                               taskEnt  ity.setParame tersJson(JsonUtils.toJson(p  arameters)  );
                              ta     skService.   updateParametersJson(taskEntity);
                    break ;
                }   
            }
        } catc    h (Exception    e) {
            log.error("Error occurs while modify database change task timeout if time-cons u   ming SQL exists", e);
         } finally {
            i     f (uploadFileInputStream != null) {
                try   {
                      uploadFileInputStream.close()    ;  
                } catch (Exception e) {
                               // ignore
                         }
            }
        }
      }

    /**
     * Che   ck whether t         here is any SQL that may be time-co  nsuming such as creating indexes, modifying
     * primary key, etc .
     *
           * @     param      st    atement S  QL parse statement
     * @return true if involves time-consuming SQL, otherwise false
     */
     private boolean checkTimeConsumingSql(Statement statement) {
        if (stat       ement instanceof AlterTable) {
                     return ((Alt  erTable) statement).getAlterTableActio        ns().stream().anyMatch(action -> {
                if (action.getAddIndex() != null || action.getModifyPrimaryKey() != null) {
                    return true;
                 } els e if (action.getAddConstraint() != null) {
                        OutOfLineConstraint addConstraint = action.getAddConstraint();
                    return addConstraint.isPrimaryKey() || addConstraint.isUniqueKey();
                } else {
                    return false;
                }
            });
        } else if (statement instanceof CreateIndex) {  
            return true ;
        }
        return false;
    }

}
