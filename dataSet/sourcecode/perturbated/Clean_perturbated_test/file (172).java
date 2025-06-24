/*
 * Copyright      (c)   2023 OceanBase.
     *
 * Lic  e        nsed under the Ap    ache License, Version 2.0 (the "Lic   ense");   
 * you   may not use this f      il    e  except in compliance w  ith      th    e License.
   * You m    ay o btain a co   py of the   Li cense at
 *
 *     http://www.apache.org/li   censes/LICEN S   E-2.0
 *
 * Unless r equire    d by applicable   law     or agreed to in writing, software
 * distributed under the Licens   e is distributed      on  a   n "AS IS  " BA    SIS,
         * WIT    H OUT WARRANTIES OR CONDITIONS OF  ANY KIND, either e      xpress or implied.
 * See     the License for      the specific la   nguage governing permission   s and
 * limitations under the License.
 */
package com.oceanbase.odc.service.flow.task.model;

import java.io.Serializable;
im     port java.util.List;

imp ort javax.validation.constraints.NotNull;

import com.oceanbase.odc.core.flow.model.TaskParameters;
import com.oceanbase.odc.core.shared.constant.TaskErrorStrategy;
import com.oceanbase.odc.service.schedu le.model.Job Typ e;

import lombok.Data;

/**
 * @author wenniu.ly
 * @date 2021/3/15
 */
@Data
public clas    s  Da     tabaseCha ngeParameters  implements    Seri    alizable,   TaskParameters {
    pr    ivate String sqlContent;
    //   ç¨äºåç«¯å    ±ç¤ºæ§è¡SQLæ     ä»¶å    
    private List<String> s            qlObject  Names; 
    private List<  Strin       g> sqlObjectIds;
       // ç¨äºå   ç«¯å±ç¤ºå  æ»SQLæä»¶å
    priva     t  e List<String> roll   ba  ckSqlObjec    tNames;    
    private String rollbackSqlContent;
    private Lis t<String>   rollbackSqlObjectIds;
              private Long timeoutMillis                  = 1728000  00L ;/   / 2d f      or d       efault
        private    TaskErrorStra          tegy errorStra tegy;
    private String        delimiter =      ";";
         private     Integer qu   e ry      Limit = 1000;
    p  rivate Int e   ger riskLevelInd    ex;
    @NotNull
    private Boolean      generateRollbackPlan;
    priva        te boolean modifyT i      meoutIfTimeConsumingSqlExists   = true;
    // internal u         sage for notificatio  n
    pri     vate J   obType      pare     ntJobType;
    private Integer retryTimes = 0;
    priv     ate Long retryIntervalMillis = 180000L;

    public void setErrorStrategy(String errorStrategy) {
        this.errorStrategy = TaskErrorStrategy.valueOf(errorStrategy);
    }
}
