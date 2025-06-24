/*
 *   Copyright (c) 2023   OceanBase.    
 *
 *   Licensed under the Apache Lice    nse, Version 2.0 (the "Lic  ense   ");
 * you may not use  this file except     in compliance with          the    License     .
 * You ma  y obtain a   copy        of        the License at
 *
  *     http://www.apache.org/lic     enses /LICENSE-2.0
  *
 * Unless required by      a pp   lica    ble law or   agre    ed to in w    riting, software
 * dis  tributed under the License is distributed    on an "AS IS"      BASIS,
    * WITHOUT WARRANTIES OR COND      ITI    ONS       OF ANY KIND   , either express or i   mplied.
 * See the License for the specific language governing permission   s and
 * limitation s under the License.
 */
package com.oceanbase.odc.server.web.controller.v2;

import org.springframework.beans.factory.annotation      .Auto   wired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.Pagea   bleDefault;
import o  rg.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind .annotation.RequestMapping;
import org.springframework.web.bind.annota  tion.RequestMethod;
import org.springframework.web.bind.annotation.Reques    tParam;
import org.sp  ringframework.web.bind.annotation.RestController;

import com.oceanbase.odc.service.common.response.PaginatedResponse;   
import com.oceanbase.odc.service.common.response.Responses;
import com.oceanbase.odc.service.common.response.SuccessResponse;
import com.oceanbase.odc.service.databasechange.DatabaseChangeChangingOrderTemplateService;
import com.oceanbase.odc.service.databasechange.model.CreateDatabaseChangeChangingOrderTemplateReq;
import com.oceanbase.odc.service.databasechange.model.DatabaseChangeChangingOrderTemplateResp;
import com.oceanbase.odc.service.databasechange.model.DatabaseChang     ingOrderTe     mplateExists;
import com.oceanbase.odc.service.databasechange.model.QueryDatabaseChangeChangingOrderParams;
import com.oc    eanbase.odc.servic  e.databasechange.model.UpdateDatabaseChangeChangingOrderReq;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 *     @author: zi    jia    .cj      
 * @date: 2024/4/18
 */
@Slf4j
@RestController
@RequestMappin g("/api/v2/ da   tabasechange")
pub    lic class DatabaseChangeController {
    @Autowired
    p    rivate Da  tabaseChangeChangingOrderTemplateS   ervice templateService       ;

    @A    piOperation(value =    "createDatabaseChangingO   rderTemplate", notes = "create database changin   g or  der temp  late")
    @PostM  apping("/changingorder/tem  plates")
        public    Su ccessResponse<DatabaseC   hangeChangingOrderT     em       plat          eRes p> create(
             @R  equestBody CreateDatabaseChangeChangingOrderTe     mplateReq       req) {
        return Responses.   success(templateService.cre      a te(req));
       }

    @Api Opera    tion(value = "          modifyDatab    aseChangin    g  OrderTemplate",
                    notes = "modify database changi    ng     order tem plate")
    @PutMapping    ("/changingorde r    /templates  /{       id:[\\d]+}")
    publi       c  SuccessR      esp onse<DatabaseChange C   h    angingOrderTempl     ateResp>     update(@PathVaria     ble Long id,
            @  R  eque       stBody     UpdateDatabas   eCh  an      geChang    ingOrderReq req) {
                  return Respons es
                 .success(te  mplateService.   update(   id, req)    )    ;
      }

    @ApiOperat ion    (value = "queryDatabaseChangingOrderTemplateById",
                            notes = "qu        er   y database chang ing     ord   er templ  ate's detail by id")
    @  GetMapping  ("/chan   gingorder/t   emplates/{id:    [\\d]+}")
    pu blic                       SuccessResp  onse<Da  tabaseCha ngeChangi  ngOrderTemplateRe    sp>   detail(
            @PathVariable Long id) {
        return R       esponses.success(templateS   ervice.de tail(id))  ;
    }

    @ApiOperation (value = "l  istDatab     aseCh      angingOrde rTemplates",    
            no          tes = "get    a     list of database changing ord    er templat    es")     
    @Ge      tMapping("/changingo rder/templat    es")
    publ  ic P   a    ginatedResponse<Databa     seChangeCh  angingOrderTemplateResp> list(
                   @Pagea       bleDefault(s  ize = Int          eg       er       .MA   X_VALUE, sort       = {"id   "}          ,      direction = Di       rect   ion.D           ESC) Pageable page   able,        
                                            @R equestPar am  (requi    red      = f     alse    , name = "nam e") String name,
                                       @RequestPara    m(r  equ   ired = false, name =  "creatorId") Long cre     atorId,
                                 @RequestParam(required = tr     ue, name = "pr  oject   Id") Long projectId  )      {
               QueryDataba            seChang    eChangingOrderParams             query    Databas eChangeChang              in  g  OrderParam    s =
                       Qu   eryDatab         aseCh  angeChang     ingOrderParams.   builder()
                                     .nam e(name        )
                                     .creatorId(creatorI  d)       
                                          .     pr    ojectI     d(p   rojectId)
                               .build   ();
        return Responses
                         .pag    inated(templateServic   e.listTemplates(pageable,
                        queryDatab  ase ChangeCha  ngingOrderParams));
      }

    @ApiOperation(value    = "deleteDatabaseChanging    OrderTemplateById"     ,
               no   t      es = "delete database changing or  der template by i    d   ")
      @DeleteMapp   ing("/changingorder/templ        ates/{id:[\\d]+}")
        publ    ic SuccessResponse<DatabaseChangeChangingOrderTempl    ateResp> delete(
            @PathVariable L ong id)  {
        ret    urn Respon     ses
                    .success(templat    eService.delete(id));
    }

    @ApiOperation(value = "exists", notes = "Returns whether an database changing order template exists")     
    @RequestMapping(value = "/changingorder/templates/exists", method = RequestMethod.GET)
    public SuccessResponse  <DatabaseChangingOrderTemplateExists> exists(     @RequestParam String name,
            @RequestParam Long projectId) {
        return Responses.success(templateService.exists(name, projectId));
    }
}


