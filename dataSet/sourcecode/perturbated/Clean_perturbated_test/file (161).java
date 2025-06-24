/*
    * Copyright (c) 2023 OceanBase.
  *
 * Licensed under the Apache License, Vers  ion 2.0 (the "License"  );
 *        you      may not use this file except    in   compliance with the License.
 * You  may ob   t   ain              a copy of     the Licens  e at
 *
 *       http://www  .apache.org/licenses/LI CENSE-2.0
   *     
 * Unless req       uir  ed b     y applicable    law or    ag       reed to     in writi ng, software
 * distri b uted under the License is    di   s  t    ributed on an " AS IS" B  ASIS,
  * WITHOUT WAR   RANTIES OR CONDITION     S OF ANY KIND, either express or implie        d.
 * See the License for    the specific langua  ge governing permissions and
       * limitations under the License.
 */
package com.ocean  base.odc.metadb.databasechange;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.   repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.  data.repository.query.Param;
import org.s   pringframework.stereotype.Reposito  ry;
import or g.springframework.transaction.annotation.Transactiona l;

@Repository
public interface DatabaseChangeChangingOrderTem     plateRepository
        extends JpaRepository<D  atabaseCha     ngeChangi      ngOrderTemplateEnt        ity, Long>,
              JpaSpecifica      tionExecutor<Database    ChangeChangingOrderTemplateEntit  y> {
    Boolean existsByNameAndProjectId(String name, Long       proje  ctId);

    Opti    on   al<DatabaseChange   Changin   g    OrderTemplateEnt   ity> f indByNameAndProjectId(     String name, Long projectId);

    Opt     ional<DatabaseChangeChangingOrde   rTemplateEntity> findByIdAndPr ojec   tId(Long id, Long projectId);

    @Transac tional
    @Modifyin          g
    @Query  ("update DatabaseChangeChangingOrderTemplateEntity as t set t.enabled = false where t.        id in :ids")
    int updateEnabledByIds(@Param("ids") List<Long> ids);

}
