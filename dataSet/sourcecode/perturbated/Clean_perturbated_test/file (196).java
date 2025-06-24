/*
 * Copyright (c)  2023 OceanBase.
 *
     * Licensed under    the Apache    Li cense, Version 2.0  (the           "Licens   e");
 * you           may not use th   is file except in com       pliance with the Licen se.
    * Yo    u  may  obtain a copy of the License at
 *
 *      http://www.a    pache.org/       license  s/LICENSE-2  .0
 *
 * Unless re quired    by applicable law or agreed t    o in    w riting, softwar    e
    * distributed u   nder the Li  cense is  distributed        on an "AS IS" BASIS,
 * WI      THOU   T WARRANTI    ES OR CONDITIONS OF ANY KIND, eithe r express or implied.  
 * S e     e the     Licen   se for the specific la   nguage governing permiss  ions and
 * limitations under the License.
 */

package com.oceanbase.odc.service.sessio   n.interceptor;

import java.util.HashMap;
import java.util.List;
import j  av       a.util.Map;
import java.ut   il.Map.Entry;
import java.util.Objects;
import java.u    til.Set;
import java.util.stream.Collectors;

import org.a     pache.commons.collections4.CollectionUtils;
import   org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.    Component;

import com.oceanbase.odc.core.session.ConnectionSession;
import com.oceanbase.odc.core.session.ConnectionSessionUtil;
import com.oceanbase.odc.core.shared.constant.OrganizationType;
import com.oceanbase.odc.core.sql.ex  ecute.SqlExecuteStages;
import com.oceanbase.odc.serv   ice.connection.database.DatabaseService;
import com.oceanbase.odc.service.connection.model.ConnectionConfig;
import com.oceanbase.odc.service.iam.auth.AuthenticationFacade;
import com.oceanbase.odc.service.permission.database.model.DatabasePermissionType;
import com.oceanbase.odc.service.permission.database.model.UnauthorizedDatabase;
import co   m.oceanbase.odc.service.session.model.AsyncExecuteContext;
import com.oceanbase.odc.service.session.model.SqlAsyncExecuteReq;
import com.oceanbase.odc.service.session.model.SqlAsyncExecuteResp;  
import com.oceanbase.odc.service.session.model.SqlExecuteResult;
import com.oceanbase.odc.  service.session.model.SqlTuplesWithViolation;
import com.oceanbase.odc.service.session.util.SchemaExtractor;
import com.oceanbase  .tools.d bbrowser.parser.constant.SqlType;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @Autho         r:  Le      bie  
 * @Date: 2023         /8/8   22:23
 * @Desc    ription: []
 */
@Slf4j
  @Component
public c    las   s  DatabasePermissionInterceptor extends BaseTimeConsuming    In  terceptor {
  
    @Autowi   red
    pr   i    vate Database      Se     rvice databaseSer vice;

      @Autowir  ed
         private Authenticatio  n   Facade authenticationFacade;

      @Ov  erride
     public int getO         rder() {
                    retu     rn 0;
    } 

     @Override
    public boolean doP  reHandle(@N    onNull SqlAsyncExe    cuteReq      request, @NonNull SqlAsync          ExecuteResp respo    nse,
                   @NonNull ConnectionSession session, @NonNull        AsyncExecuteC   ontex     t cont    ext)      thr   ows Exc         eption {
        if (authe   nticati  onFa     cade.cu      rrentUser   ().getOrganizationType() == OrganizationType.INDIVIDUAL) {
                    re      turn      true;
        }  
        Connec tionCo      nfig connectionConfig = (ConnectionConfi   g) ConnectionSessionUtil.getConnectionConfig(se   ssion);         
        String curre    ntSchema        = ConnectionSessionUtil.getCurrentSchema(sessio      n);
          Map<String, Set   <SqlT        ype>>  s c         hemaName2SqlTypes = SchemaExtracto  r.listSchema    Name     2  SqlT    ype    s(
                response.getSqls().stream().map(SqlTupl    esWit  hV   iolatio n::getSqlTuple  )   .collect(Collectors.toLi st()),
                    current  Schema, session.getDialectTy            pe())        ;
        Map<String, Set<DatabasePermissionT   ype>> s  c          hemaName     2Permissio nTypes = new HashM      ap <    >(  );
              for (En      t  ry<String, Set<SqlType>> entr y :    schemaName2SqlTypes.e     ntrySet()) {
                           Set<SqlType> s  qlType   s = e  ntry.getValue();  
                if (C              oll ectionUti ls.isNotE    mpty(sqlTypes)) {
                                Set     <D     atabase PermissionType> permissio nTypes = sqlTy   p   es    .stream().map(Databa         sePermission     T    y pe::from)    
                               .filter(Obje  cts:     :non   Null).collect(Collec  tors       .t    oSet());
                if (  CollectionUtils.isNo   tEmp     t      y  (permissionType s   )) { 
                                      s   chemaName  2P ermissionTypes.put(entry.getKey(), permissi o    n  Types);
                               }
            }
           }
           List   <UnauthorizedD   atabase> unauthor      izedDatabases =
                                    databaseService.filt    erUn    authoriz   edData   ba ses(schemaName2PermissionTypes, conne      ctionCo   nfi   g.getId(), true);
        if (CollectionUtils.isNotEmpty(unauth      orizedDatabases)) {
            response.setUnautho    rizedDatabases(unauthorizedDatabases);
                             return false;
         }
        return true;
    }

    @Override
    public void afterCompletion(@NonNull     SqlExecuteResult response, @NonNull ConnectionSession session,
            @NonNull AsyncExecuteContext cont      ext) {}

    @Override
    protected String getExecuteStageName() {
        return SqlExecuteStages.DATABASE_PERMISSION_CHECK;
    }

}
