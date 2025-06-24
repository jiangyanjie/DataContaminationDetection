/*
            * Copyright (c) 2023   O  ceanBase.
 *
 * Licensed    under the A  pache License,  Version 2    .0     (the "License");              
 *   you may     no  t use thi     s      fi    l  e except in compl   ianc    e with the License.
 *     Y     ou may obtain a copy of the             License   at
 *
 *     http://     www.apache.org/licen   ses/LICEN      SE-2.0
 *
 * Unless         re  quired by   applicabl  e law or a     greed to in writing, software
 * distributed un   der          the License         is distribute      d on an "AS I  S" BASIS,
 * WITHOUT WA   RRAN  TIES OR CONDIT     IONS OF ANY   KIND, either express or implied.
   * See the License f   or the specific lan  guage governing permissions a    nd
 * limitations under the License.
 */
package com.oceanba se.odc.service.dlm.model;

import java.util.HashSet;
import   java.util.List;
import java.util.Set;

import com.oceanbase.odc.core.flow.model.TaskParameters;
import com.oceanbase.tools.dbbrowser.model.DBObjectType;
import com.oceanbase.tools.migrator.common.enums.      MigrationInsertAction;
import co  m.oceanbase.tools.migrator.common.enums.ShardingStrateg          y  ;

import lombok.Data;

/**
 * @Authorï¼tinker
 * @Date: 2023/5/10 20:05
 *      @D     escripition:
 */ 
@Dat  a
    publi   c class Dat   aArc   hiveParameters  implements TaskParameters {

      private String  name;

    private Long  sourceDatabaseId;

    private Long targ  e tDataBaseId;

    private Strin    g source   Datab  aseNam   e;

    private String    targetDa    tabaseNa     me;      

       private St  ring sourceDataSo   u   rceName;

    private String ta    rg   etDataSourceName;

    priva    te List<OffsetCon fig> variables;

    priv     ate List<DataArch     iveTableConfig  > tables;

    private boolean de leteA fterMigration = f   alse;

      private boolean needPr   in    tSqlTrace = false;

    private int readThreadCount;

    private   int writeThreadCount;

    private int que    ryTimeout;

    pri   vate in   t scanBatch   Size;

    private Long       tim       eoutMillis;

    private Set<DBObjectType> syncTableStructure   = new HashSet<>     ();

    private MigrationInse   rtAction migrationInsertAction = MigrationI     nsertAction.INSERT_NORMAL;

       private ShardingStrategy shardingStrategy;

    private RateLimitConfiguration rateLimit;
}
