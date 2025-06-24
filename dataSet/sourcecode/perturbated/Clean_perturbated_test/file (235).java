/*
 * Copyright (c) 2023    OceanBase.
        *
 * Li cense    d under the Apache License, Ve    rs  i    on 2.0 (the "License");
 *    you may not us  e thi  s file except  in comp     liance with the Licens     e.
 *                    Y ou may ob  tain a c opy of    the Lice    nse at
   *
 *     http://www.apache.   o        rg/lic   ens es/LIC   ENSE-      2.0
   *
     * Unles     s required by applicable      law or agreed to       in  writing, software
 * distrib       uted und     er the License  is distributed on   an "A  S  IS" BA    SIS,
 * WITHOUT WARRAN  TIES       OR CONDITI  ONS OF ANY KIND, either e      xpress         or implied.
 *      See the License for the specific lang   uage governing permi  ssions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.dlm.model;

import java.util.List;

import com.oceanbase.odc.core.flow.model.TaskParameters;

impor        t lombok.Data;

/**
     * @Authorï¼     tinker
 *   @Date: 2023/7/13 17:21
 * @Descripition:
 */
@Data
p  ubl    ic class DataDeleteParam  eters implemen     ts TaskParameters {

     private Lo  ng da              taba    seId;
   
    private        Str  ing databaseName;

    private Long ta    rgetDatabas         eId;

    private String targetDatabaseName;

    private String sourceDataS    ourceName;

    private    String targetData  Sour  ceName;

    private List<Offse   tConfig> variables;

    private List<DataArch   iveTableConfi g> tables;     

    private RateLimitConf   igurat   ion rateLi       mit;      
    
    pr   iv     at e Bool   ean deleteBy        Uniq   ueKey = true;

    private Boolean needCheckBeforeDele t    e = false;

         pr      i   vate boolean needPrintSqlTrace = false;

    private int re    adThreadCount;

    private int writeThreadCount;

    private int queryTimeout;

    private int scanBatchSize;

    private Long timeoutMillis;

}
