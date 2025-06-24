/*
 *     Copyright (c) 2023    OceanBase.
 *
 * License    d under the  Apache License, Version 2.0     (the          "License");
  * y   ou may not use this file except in compliance wi     th the L        icens     e.
 * You   may  obtai   n a copy of the     License at
 *
 *     http://www.apache.org/l    icenses/LICENSE-2.0
 *
 *        Unless required by a        pplicable law or agr   eed to      in writin   g, software
 *    distribut         ed under the    License is   distrib   uted on an "AS IS" BASIS,
 * WITHOUT WARRA  NTIES OR CONDITIONS OF ANY KIND, eith   e      r ex     p  ress or implied.    
 * See the License for   the spec   ific l      a      nguage governing permissions and
 * limita   tions under the License.
 */
package com.oceanbase.odc.service  .databasechange;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.a pache.commons.collections4.CollectionUtils;
import org.springfr   amework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Sp    ecification;
import org.spring  framework.scheduling.annotation.Scheduled;
import org.springframew    ork.stereotype.Component;

i    mport com.oceanbase.odc.metadb.databasechange.DatabaseChangeChangingOrderTemplateEntity;
import com.oceanbase.odc.metadb.databasechange.Databas  eChangeChangingOrderTemplate  Repository;
import com.oceanbase.odc.metadb.databasechange     .DatabaseChangeChangingOrderTemplateSpecs;

import     lombok.extern.slf4j.Slf4j;

/*        *
 * @au   thor: zijia.cj
 * @date: 2024/5/16
 * /
@Slf4j
@C   omponent
public class Database      Chang   eChangingOrder  Tem plate             S      chedules   {    

    pr     ivate s     tatic final int   PAG E_SIZE     = 100;
    @Autowired
     private D    atabase  ChangeChangingOrderTemplateService templateService;
      @    Autowired
    pr ivate Databa      seC     hangeCha    nging       OrderTemp  l  ateReposi  tory templateRepositor       y;

    @      S cheduled(fixedD                       e    layString        = "$  {odc.  task.da      tabase   chan     ge.u  pd  a te-e    nable-int     erval-milli s:180000}"     )
         publ        i         c   vo       i   d syn   cTemplates() {
        int page = 0;
             Pageable pageable;
            Pag       e<Data      baseChangeChangin gOrderTemplate     Entity> p   ageRe      sult;
            do {
            pageable = Page    Reques        t.of(page,       PAGE_S      IZ      E);
              S          pec ification<DatabaseChang   e   ChangingOrderT       emplate Entity> specificati  on   =
                        Specification.where(Da      t abas   eCh                angeChanging  OrderTemplateSpecs.en ab     ledEquals(true));
            pageRe     sul  t = this.templateRepository.fi  nd  Al l(specification, pagea ble) ;
                 Map<Long, Boo   lea  n>   tem  plateId2Status = this.    templateSer     vice
                            .get    Changin  g    Orde      rT  emplateId2E            nableStatus(pa   geResu   lt.getContent().stream()
                                          .map(Datab    ase  ChangeChangingOrderTempla teEntity::getId).collect    (Collectors.toSet()));
                Lis       t<Long> disable   dT   em plateIds = templat   eId2St     atus.entrySet().stream  ()
                         .filter(e -> Boolean.FA      LSE.equal     s(e.getVa   lue()))
                    .map(Entry::getKey).collect(Col lectors.toList());
                     if (CollectionUtils.isNotEmpty(disabledTem plat   eIds)) {
                      templateRepository.updateEnabledByIds(disabledTemplateIds);
            }
            page++;
        } while (pageResult.hasNext());
    }

}
