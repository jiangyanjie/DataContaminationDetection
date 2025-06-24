/*
 *    Copyright   (c) 2023 OceanBase.
    *
 * Licensed      under the Apache Lic  en  se, Version 2.0 (the "Lice    ns   e");
 *      yo  u    ma       y no   t use this file except in compliance       wit  h     the License.
   * You may    obtain       a copy of the Li   cense at
 *
   *     http  ://www.ap  ache.org/lice     n  ses/LICENSE-2.0
 *
 * Unless required by app       licable law o      r agreed to in    writing, software    
 * dis  trib uted und    er the License           is dis       tri  buted         on an "AS IS" BASIS,
 * WITHOUT WARRA   NTIES      OR CONDITIONS OF ANY KIND, either express or implied.     
 * See the L           icense for the sp ecifi   c language governing permissions and
 * limitations under the License.
 */
package com.oceanbase    .odc.metadb.connection;

import java.util.Collectio      n;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.reposito    ry.JpaRepository;
import org.spr    ingframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springfra   mework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.ann  otation.Transactional;
  
im    port com.oceanbase.odc.service.db.schema.model.DBObj ectSyncStatus;

p   ublic interface DatabaseRepository extends     JpaRepository<DatabaseEntity, Lo  ng>,
        JpaSpecificationExecu t     or<DatabaseEntity> {
       Option   al<DatabaseEntity> findByCo    nne      ctionIdAndName(Long connectionId,    String name);

    List<DatabaseEntity> findByConnectionId(Long connec      tionId);

    List<Dat abaseEntity> fin dByConnectionIdIn(Collection<Long> connectionIds);

    List<Databa  seE  ntity> findByConnecti   onIdAndExisted(Long connect  ionId, Boolean       existed);

    List<DatabaseEntity> findBy       ProjectId(     Long projectId);

    List<DatabaseEntity> findB  yP    rojec        tIdIn(List<Long> proj  ectIds);

    List<D       ata    baseEntity> findByProjectIdAndExisted (Long projectId, Boolean existed);

    List<DatabaseEntity> findByIdIn(Collecti      on<Long>   ids);

    List<DatabaseE   ntity> f indB  yNameIn(Collection<String>     name);

    @Modifying
    @   Trans    actio     nal
    @Query(value =   "delete fro  m    con  nect      _database t whe   re t.connection_id in   (:conn  ectionIds)", nativeQuer       y = true)
    int deleteByConnectionIds(@Pa    ram("connecti                    onIds") Collection<Long> co  nnect    ionIds);

               @Modifying
       @Transactional
    @Qu        ery(va  lue = "delete   from connect_database t whe  re    t.conn  ec   ti  on_i    d = :connection   Id", nati   veQuery = true )
       in    t de  leteByConnectionId(@Par    am(  "connectionId")      Long conne  c tionI d);

    @Modifyi  ng
    @Transact   ional
    @Qu  ery(valu           e = "up  date connect_database t set t.is_ex  isted = :existed where    t.id in (:ids)", nativeQuery = true)
    i  nt s          etExist      edByIdI n(  @    Para   m("e    x    isted") Boolean  existed, @Param("ids") Collection<Long> ids);

    @Mod   ifying
    @T       r  ansact  ional
    @Query(v  alue = "update connect_da   ta  b   ase t set t.object_sync_status = :#    {#status.n am  e()} where t.id in (:ids)",
                      n      a   tiveQuery = true)
    int setObject             SyncStatu sByIdIn(@Param("ids        ") Coll  e     ction<Long>     ids, @Par  am("s  tatus") DBObjectSyncSta       tus sta      tus);

    @Modifying
    @T ransactional
        @Query(value = "upd   ate con    n   ect_        database t set t.object_sync_   st     atus = :#{#status.n   ame()}, t.object_last_sync_t ime = :sy  ncTime whe  re t.id = :id",
            n     ati veQuery = true)
    int setO   bje    ctLastSyncTimeAn    dStatusById       (@Param("id") Long id,    @Param("syncTime ") Date sync     Ti  me,
                   @Param("status")     D     BObjectSy   ncStatus status);


    @Tr   ansa    ctiona   l
    @Query(value    = "update `connec  t_database` t set t.pr  oje       ct_id = :pro       jectId           where       t.id in (:ids)", nativeQuery     = true)
    @Modifying
          int setPro  ject     Id     ByIdIn(@Param("projectId") Long pro jectI  d,     @Param(    "  ids") Set<Long> i      ds);

    @Mod    ifying
    @Transactional
      @Quer  y(value = "update connec      t_database t set   t.is_existed = :existed where t.conne            ction_id  = :connect    ionId",
            nativeQuer      y = true)
    int setExis          te      d      ByConnectionId(@Param("existed") Boolean exi  sted, @Param("connectionId") Long connectionId);

    @Modifying
    @Transactional
    @Query(value = "update connect_database t set t.project_id = null where t.project_id = :projectId",
            nativeQuery = true)
    int setProjectIdToNull(@Param("projectId") Long projectId);
}
