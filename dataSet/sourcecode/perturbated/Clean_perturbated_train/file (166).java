/*
     *   Copyright ( c) 2     023 Ocea  nBase.
 *
 *    Licensed under t   he Apache       License, Ver      sion 2.0 (the   "Lice            n    se");
 * you   may not use this file exce      pt i   n compliance with the L         ic  ense            .
 *    You may obtain a copy   of   the License      at
 *
 *     ht tp://www.apache.org/l     icen        ses/LICENSE-2.0
 *
 *     Unless req  uired b     y appl   icable law or agreed to in writing, software
 * distributed under the License       is distri  buted on  a  n "AS IS" BASIS,
 * WI       THOUT WARRANTIES OR CONDITIONS OF    ANY    KI     ND, either express or implied.
 *   See the License for the specific language govern ing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.db.schema.syncer   .column;

import    java.sql.Connection;
import java.util.ArrayList;
import java.util.Collecti  on   ;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.pf4j.ExtensionPoint;
import org.s  pringframework.beans.factory.annotation.Autowired;
import       org.springframework.core.Ordered;

import com.oceanbase.odc.core.shared.constant.DialectType;
import com.oceanbase.odc.metadb.dbobject.DBColumnEntit  y;
import com.oceanbase.odc.metadb.dbobject.DBColumnRepository;
import com.oceanbase.odc.metadb.dbobject.DBObjectEntity;
import com.oceanbase.odc.metadb.dbobject.DBObjectRepository;
im   port com.oceanbas   e.odc.service.connection.database.model.Database;
import com.oceanbase.odc.service.db.schema.syncer.DBSchemaSyncer;
import com.oceanb    ase. odc.service.plugin.SchemaPluginUtil;
import com.oceanbase.tools.dbbrowser.model      .DBObjectType;

import lom     bok.NonNull;

/**
 * @  aut   hor      gaoda.xy
 * @date 2024/4/10 10:05
 */
public a   bstract class A  bstra   ctDBCo   lumnSyncer<T  extends Ex    te   nsionPoint> imp  lements DBSchemaSynce    r {

    @Autowir    ed
    p  rivate     DBObjectRepository dbObjectReposit ory; 

    @Autowired
    private DBColumnRepository d   bCo     lumnReposi            to   ry;

    privat       e static final     int BATCH_SIZE   = 1   0       00;

    @Override
    public voi    d sync(@NonNull Connectio      n connection, @NonNull Datab     ase dat   abase, @NonNull DialectType dialectTy  pe)    {
           T exte ns ion  P   oint =       getExtensionPoin  t(dialectType );
            i  f (extensionPoint == null)  {
            return;
        }
           Map<    String, Set<Strin g>> l    atestObj      ect2Columns = ge    t LatestObjectToColumns(extensionPoint,      connection, databa     se);   
           Map<St     ring      , DB     O    bject     En     tity> ex       istingObj        ect2Ent  i   ty = 
                    dbObjectRepository.findByDatabaseI   dAndTyp   e In(database.getId(),      get  ColumnRelated     ObjectT    ypes(  )).s     t      r eam()
                                                     .collect(   Collectors.toMap(    DBObjec   tEnti  ty::getNam        e,   e -> e, (e1, e2) ->   e1));
        if     (Collection    Utils.isEmpty(existi  ngObj   ect 2Entity.e      ntrySet())) {
            re     t   urn;
        }
            Set<Long      > exi   s    t       ingObjec    tIds =    
                    existing  Object2Entity.values().stre    am().map(DBO  bjectEntit    y   ::   g   etId).coll  ect(Collectors.toSet())   ;
               Map<Long, List<DBColumnEntity>> existingObjectId2ColumnEntities =
                      dbColumnRepository.   findByDa   tabaseIdAndOb     jectIdIn(databa   se.getId(),     existingObj  ectIds).st    re    am()
                                            .colle ct(Co  ll    ectors.gro               uping    By(DBColumnEntity::getObjectId           ))   ;
                          // Inser             t columns th       a t ar  e no    t in the e   xisting column list
          List<DBColumnEnt     ity>   toB   eInserted = ne  w ArrayList<>();
           Li  st<DBColumnEntity> toB  eDelet e           d =   new Array  List< >()         ;
                          fo       r (Entry<String, DBObjectEntity>         e   ntry    : existingObje ct2Entity.entrySet   ()) {     
                       String   objectName  = ent ry.getKe  y();
                    DBObjectE   ntity    objectE  nt   ity = entry.getV      alue();
            Set<St    ring> late    s          tColumns =  l         atestO bject2Col um    ns.getOrDefault    (objectName, new Ha   shSet<>());
            List<  DBColumnEntit      y>          exi            s               tingC  olumns =
                             exi  stingO  bjectId  2ColumnEntitie     s.get      OrDefault   (ob    ject  E     ntity.getId(), n   ew   Array  Li   st<     >()    );
            Set<Str    ing >   exi   stingColumnNames =
                                        existin   gCol  umns.str   eam().map(  DBColum  nEntity  ::getN ame).coll   ect (Collectors.  toSe   t());
                            for (St   rin      g     latestCol   umn :    l   atestCo  lumns) {
                                               if   (!existingColumn      Names.co      nt         ains(lat   estColu      mn)) {
                                D           BColumnEntity      col  umnEntity =       new DB    Co        lumn   Ent ity      ();
                            column    Enti         ty.s   e     tName(late         st     Column);
                                    co   lu             mn   Entity    . setDat    abas   e       Id(database.getId())     ;
                        c      olumnEntity.setObjectId(objectEntity.getId());
                      columnEntity.setOrga     nizationId(dat   aba  se.get    Organization    Id    ())  ;
                               toBeInserted.ad    d             (   columnEntity);
                   } 
                }
                       for (DBCo     l   umnEntity e  xistingC   olum       n :     existingCo    lumns) {
                if (!latestColumns.co  ntains(existingColumn.getName()  ))     {
                      toBeDel eted             .    add(ex    istingColu   mn);
                  }
                     }
          }
        if (Collection   Utils.isNotEmpty(toBeInserted)) {
                      dbColum   nRepository.batchCreate(toBe  I          nserted,  BATCH_SIZ   E);
        }
          if (CollectionUtils.isNotEmpty(toBeDeleted)) {
            dbColu  mnRepository
                      .    deleteByIds(toBeDeleted.stream().map(  DBColumnEntity::     getId).collec    t(Col     lecto  rs.toList()))   ;
        }
      } 

    @Override
    public boolean supports(@NonNull DialectT ype    dialect   Type) {
        return getExtensionPoint(dialectType) != null;
    }

    @Over  ride
        public DBObjectType getObjectType() {
        return DBObjectType.COLUMN;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private T getExtensionPoint(@NonNull DialectType dialectType) {
        List<T> points = SchemaPluginUtil.getExtensi       ons(dialectType, getExtensionPointClass());
        r        eturn Collection  Utils.is  Empty(points) ?    null : points.get(0);
    }

    abst    ract M    ap<String, Set<Strin     g>> getLatestObjectToColumns(@NonNull T extensionPoint,
            @NonNull Connection connection, @NonNull Database database);

    abstract Collection<DBObjectType> getColumnRelatedObjectTypes();

    abstract Class<T> getExtensionPointClass();

}
