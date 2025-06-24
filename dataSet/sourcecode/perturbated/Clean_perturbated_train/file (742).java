/*
 *   Copyright (c) 20 23 OceanBase.
 *
        * License  d  under the Apache   License   ,          Version 2.0 (the "  Lice          nse");
 * you may not use this file except in compliance     with t   he     Lice   nse.    
      * You may obtain a    copy     of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2. 0
 *
 *     Unle   ss required by     applicable     law or agreed to      in wr    iting, softw    are
      * d        istributed   under  the Licens    e   is distributed on an "AS IS"        BASIS,
 * WITHOUT       WARRANTIES OR        CONDITIONS   OF    ANY KIND, either express or implied.
 *             See    the License for the specific language governing   permissions and
 * limitations under the License    .
 */
package com    .oceanbase.odc.service.flow.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonSubTypes  ;
import com.fasterxml.jackson.annotation.JsonTyp      eInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jacks   on.annotation.JsonTypeInfo.Id;
import com.oceanbase.odc.core    .flow.model.TaskParameters;
import com.oceanbase.odc.core.shared.PreConditions;
import   com.oceanbase.odc.core.shared.constant.TaskType;
import com.oceanbase.odc.plugin.task.api.datatransfer.model.DataTransferConfig;
import com.oceanbase.odc.service.flow.processor.CreateFlowInstanceProcessAspect;
import co    m.oceanbase.odc.service.flow.task.mod   el.DBStructureComparisonParameter;
    import com.oceanbase.odc.service.flow.task.model.DatabaseChangeParam    eters;
import com.oceanbase.odc.service.flow.task.model.Mu   ltipleDatabaseChangeParameters;
import com.oceanbase.odc.service.flow.ta  sk.model.OdcMockTaskConfig;
import com.oceanbase.odc.service.flow.task.model.ShadowTableSyncTaskParameter;
import com.oceanbase.odc.service.onlineschemachange.model.OnlineSchemaChangeParameters;
import com.oceanbase.odc.service.partitionplan.mo  del.PartitionPlanConfig;
import com.oceanbase.odc.service.permissi  on.database.model.ApplyDatabaseParameter;
import com.oceanbase.odc.service.permission.project.ApplyProjectParameter;
import com.oceanbase.odc.service.resultset.ResultSetExport    TaskParameter;
import com.oceanbase  .odc.service.sc     hed    ule.flowtask.AlterSchedulePara m     eters; 

import lombok.Da ta;

/**
 * @   author wenniu.ly
    * @date 2022/2   /9
           */

@Data
pu   blic class C      reateFlow In      stanc      eReq {
    /        *    *
     * FlowI nstance    Id
               *  /
    @JsonPropert  y(acce  ss   =      Acc      ess.REA      D_O   NLY)
    priva   te Long id;
  
    /**
                 * Database id
              */
        private Long datab  aseI   d;
        /**
          * Task type
        */
    @Not     Null
    private       Ta    skTy      pe taskTy    pe;
    /**
     * Execution strategy defaul t    auto.
     */
    @N    ot   Null
        pr   ivate FlowTaskExecutionStr        ategy execut     i  onStrategy = Fl owTa skExecutionStr ategy.AUTO;     
    /**
          * E            xecution       ti    me, v    alid o  nl y when executionStrate    gy      is TIME    R  
       */
                  priva     te Date ex   ecuti onTime;
    /**
     * Par  ent instance id    , valid only   when genera   ting rollback pl   an
        */
        private Long paren   tFl      owInst  an     ceId;
    /*    *
             * Task descriptio  n    
     */
      priv    ate     String description;
      /**
           * Tas   k parameters
               */
                   @  NotNull
    @Jso    nTypeInfo(   use = Id.NAME, include = As.EXTERNAL_PRO   P      ERTY, property = "t    askType")
     @J  sonSubTypes(value = {
                     @JsonSubTypes.Type(value = OdcMockTaskConfi      g.class, name    = "MOCKDA T    A") ,
                      @J     sonSubTypes.Type(value = DataTransferConfig.clas   s, names = {"EXPORT", "IMPORT    "}),
                           @JsonSubTypes.Type(value = MultipleData   bas     eCha   ngeParamet      ers.class,      na m    es =      {"MULTIPLE_ASYNC"}),
                    @Jso nSubTypes.Type(value   = Dat  a     baseChangeP    ara     m eters.class, names = {"ASYNC"}),
            @Jso    nSubT         ypes.Type(      value = Partitio   nPlanConf  ig.  class, n  ame = "PARTITI  ON_PLAN"),
            @  JsonSubTypes.Type(value      = ShadowTableSyncTaskParameter.class, nam    e = "SHADO WTABLE_SYNC"),
                   @JsonSubTypes    .Type(value = AlterSc  heduleParame     ters    .cl   ass, name    = "ALTER_SCHED  ULE"),
                @JsonSubTy    pes.T        ype(value = OnlineSchemaCh   angeParame              ters   .cla  ss, name = "ONLINE_SCHEMA_CHANGE"),
            @Jso   nSubTy   pes.Type(va   lue = ResultSetExportTas kPar  ameter.class,        name = "EX   P ORT_     RESULT  _SET"),
               @JsonSubTypes.Type(value = ApplyProject   Parameter.class, name = "APP    LY_PROJECT_PER  MI      S    SION"),
                     @JsonSubType      s.Type(va          lue      = A   pp         lyDatabase     Parameter.class, name =    "AP        PLY   _DATAB     A  SE_PE       RMI    SSION"),    
            @Js     onSubTypes.Type(val    ue =    DBStr       uctureComparis  onParameter.class, name = "STRUCTURE_COMPARISON")
       })
    private    TaskParam      et    ers parameters;

    /**
     * Followings are filled by aspect {@link      C   reateFlowInsta    nceP   rocessAspect}
     */
    @J   so  nProperty(access = A   ccess.READ_ONLY)
    private Long projectId;
    @JsonProperty(access = Access.READ_ONLY)
    private String projectName;    
    @  JsonProperty(access = Access.READ_ON    LY)
    pr   i vate St   ring databaseName;
    @Js    onProperty(access = Access.R EAD_ONLY)
     private Long connec tion  Id;
    @JsonProperty(access = Access.R      EAD_ONLY)
    private String   connectionName;
    @Json    Property(access = Acces      s.READ_ONLY)
    private Long environmentId;
    @JsonProperty(access = Access.READ_ONLY)
    private String environmentName;

    public void validate() {
        if (executionSt  rategy == FlowTaskExecutionStrategy.TIMER) {
            PreConditions.notNull(execu  tionTime, "executionTime");
        }
    }

}
