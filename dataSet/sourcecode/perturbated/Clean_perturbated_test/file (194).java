/*
   * Copyright (c) 2023 OceanBase.
  *
    * Licensed      under the Apa     che License, Version   2.0 (the "Licens     e"          );
 * you         m   ay not use this file      except in compl    iance w  ith    the     Li   cense.
 * You may obtain    a copy   of t    he L icense at
   *
 *     http://www.apache.org    /licenses/LICENSE   -2.0
 *
 * Unless req    uired by applicable law    or agreed to in   writing, softwa      re    
 * di  s  tributed under the License is distributed on an "AS IS"  BASIS,  
 * WITHOUT WARRAN   TIES OR CONDITIONS OF ANY    KIND,            either express or im   plied.
 * See the License for the specific language gov   erning permissions and
 * limitations un  der the License.
 */
package com.oceanbase.odc.server.web.controller.v2;

import java.util.List;

import org.springframework.beans.facto  ry.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import      org.   springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web   .bind.annotation.PathVariable;
import org.springframework.we     b.bind.anno     tation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.Re  questMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oceanbase.odc.core.shared.constant.AuthorizationTy pe;
import com.oceanbase.odc.service.common.response.ListResponse;
import com.oceanbase.odc.service.common.response.PaginatedResponse;
import com.oceanbase.odc.service.common.response.Responses;
import com.oceanbase.odc.service.permission.database.DatabasePermissionService;
import com.oceanbase.odc.service.permission.database.model.CreateDatabasePermissionReq;
import com.    oceanbase.odc.service.pe   rmission.database.model.DatabasePermissionType;
import com.oceanbase.odc.service.permission.database.model.ExpirationStatusFilter;
import com.oceanbase.odc.service.permission.database.model.QueryDatabasePermissionParams;
import   com.oceanbase.odc.service.permission.database.model.UserDatabasePermission;

import io.swagger.ann        otations.ApiOperation;

/**
 * @author g   aoda.xy
 * @dat  e 2024/1/4 10:15
 */
@RestController
@RequestMapping("/api/v2/collaboration/projec     ts/{proje ctId:[\\d]+}/databasePermissions")
public cla      ss Databa       sePermissionController {

    @Autowired
    privat e Database  PermissionService se    rvice;

    @A      piOperation(va  lue   = "listDatabasePermissions", notes = "List database     permissions")
            @RequestM   apping(value = "", method = Re    quest Method.GET)
      pu     blic  PaginatedRe s    ponse        <UserDatabasePermission      > list(@PathVaria bl    e Long pr     ojectId,
                     @Reque   stParam(name = "use rId",  requir        ed = fa     lse) Long userId,
                                     @Requ       es tParam(     name = "ticket  Id", requ   ired =     fal se) Long     ticketId,
                 @RequestP   ara  m(name      =  "databaseName",    requi     red = false) String fuzzyDatabaseName   ,
             @RequestParam(name     = "dataSourceN ame", re  quired       =    false)       String fuz   zyDataSou          rceName,
            @Req  uestParam(name = "ty    pe", required  = false) Li   st<DatabasePermissionType> types,
                                            @Requ  e     stParam(name     = "authorizat     ionType", required = false     ) Author   izationTyp  e au thor    izationT      y    pe,
                 @Req uest  Param    (name = "st   atus", required =       false) List<Ex    p   irationStatusFilter> statu    se  s ,
                     @  Pa   ge ableDefault(  size =   Integ   er.MAX_    V    A     LUE, s       ort  =   {"i       d"      },       dire  ct    ion      = Directi        on.DE    SC) Pageable pageable) {
        QueryDatabas     ePermiss  io  nPa  rams para  ms = Quer       yDa   ta     basePermissio  nPar ams.builder()
                              .userI d  (     userId)
                        .tic  ketId(ticketId)
                     .fuzzyDatabaseName(fu      zzyData      baseName)
                     .fuzz         yDataS    ou     rc eName(f   uzzyD          ataS       ourceName)
                      .types(t      ype  s)
                .autho   ri      z  ationType(authorizationType)
                .statuses(statuses)
                  .build()    ;
        return     Responses.paginated(service.lis  t(projectId     , params    , pageable))  ;
    }

    @ApiOperation(value = "batchCreateData  b  aseP   ermis    sions", notes = "Batch creat    e database permis sions")
    @RequestM   apping(value = "/batchCreate", method = Reque    stMethod.POST)
    public ListResponse<UserD   atabasePermission> batchCreate(@PathVa  riable Long   projectId,
            @RequestBody Create   DatabasePermission      Req re    q) {
          return Respons  es.list(service.batc   hCreat  e(  projec   tId, req  ));
    }

    @ApiOperation(v alue     = "batc    hRevokeDatabasePermission",  notes = "Batch revoke      database perm   ission    s")
    @RequestMapping(valu   e =   "/batchRevoke", method = RequestMethod.DELETE)
    public ListResponse<UserDatabasePe    rmission> batchRevoke(@PathVariable     Long projectId  ,
            @RequestBody List<Long> ids) {
            return Responses.list(service.batchRevoke(projectId, ids));
    }

}
