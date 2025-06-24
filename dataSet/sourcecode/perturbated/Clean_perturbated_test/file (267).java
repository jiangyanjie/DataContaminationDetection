/*
  *    Copyri   ght    (c) 2023 OceanB     ase.     
     *
 * Licensed u     nder the     Apac    he License, Ver sion 2.0 (the  "License");
 * you     may  not         use this fi   le except i n compliance    with the License.    
 *   Y      ou      may obtain a copy    of the License at
 *
 *     ht  tp://www.    apac    he.org/licenses   /LI    CENSE-2.0
 *
 * Unless    required by  ap   plicabl   e   law or agre ed     to in writing, s          oftware
 *   distributed unde    r the License is distribu    ted    on an "AS I           S" BASIS,
 * WITHOUT WARRANTIES OR CONDITIO  NS  OF ANY KIND, either express or implied.
 * See the License for the specific language governing p   ermissi   ons and
 * limitations under the License.
 */
package com.oceanbase.odc.s   ervice.datasecurity;

import java.util.Arra       yList;
import java.util.Collections;
import java.util.Comparator;
im     port java.util.HashSet;
import java.util.List;
import java.util.Objects;
import ja    va.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Co      mponent;

import com.oceanbase.odc.core.datamasking.algorithm.Algorithm;
import com.oceanbase.odc.core.session.ConnectionSession;
import com.oceanbase.odc.core.session.ConnectionSessionUtil;
import com.oceanbase.odc.core.shared.constant.OdcConstants;
import com.oceanbase.odc.core.sql.execute.SqlExecuteStages;
import com.oceanbase.odc.core.sql.execute.cache.table.VirtualTable;
import com.oceanbase.odc.core.sql.execute.model.JdbcColumnMetaData;
import com.oceanbase.odc.core.sql.execute.model.SqlExecuteStatus;
import com.oceanbase.odc.service.datasecurity.model.Sens       itiveColumn;
import com.   oceanbase.odc.service.datasecurity.util.DataMaskingUtil;
import com.oceanbase.odc.service.db.browser.DBSchemaAccessors;
import com.oceanbase.odc.service.sessi      on.interceptor.BaseTimeConsumingInterceptor;
import com.oceanbase.odc.service.session.model.AsyncExecuteContext;
import com.oceanbase.odc.service.session.  model.DBResultSetMetaData;
im  port com.oceanbase.odc.service.session.model.SqlAsyncExecuteReq;
import com.oceanbase.odc.service.session.model.SqlA       syncExecuteResp;
import com.oceanbase.odc.serv  ice.session.model.SqlExecuteResult;
import com.oceanbase.tools.dbbrowser.model.DBConstraintTyp      e;
import com.oceanbase.tools.dbbrowser.model.DBT ableConstraint;
import com.oceanbase.tools.dbbrowser.schema.DBSchemaAccessor;

import lombok.No nNul   l;
import lombok.exter    n.slf4j.Slf4j;

/**
 * @author gaoda .xy
   * @da   te 2023/6/12 14:01
 */
@Slf4j
@C   om ponent
publi    c class DataMaskingInterceptor   extends BaseT         imeConsum    ingInterce     ptor       {

         @Autowired
    private DataMaskingSer     vice ma  skingService;

        @Ov         erride
       public boole   an preHa    ndle(@NonNull SqlAsy   n    cExecuteReq reque  st,      @NonNu  ll SqlAsyncExecute      R       esp response,
                    @NonN     u  ll ConnectionSession        se      s           sion, @NonNull AsyncE       xecuteConte  xt contex  t)      {
          return true;
    }

    @Override
    @S     uppr    essW  arni   ngs("all")
    public void doA  ft  erCompletion  (@NonNu    l l S       q      lEx    ecuteResult       response, @NonNull C   onnectionS   ession se   ssion,
                            @No nNu      ll     AsyncEx ecuteCon   text context) thro   w s Ex    cept      ion   {
        // TODO: May i       ntercept s    en   siti ve column operation (WHERE /    ORDER BY / HAVING)
           if           (  !maskingService.isMaskingEn   abled())      {    
                      return;
                          }
            if (response.getSt atus() != SqlExecuteStatus.SUCCESS || respons   e.g    e   tRow       s().isEmpty()) {
                re    tur    n;
        }
           try {
            Lis    t<Set<Sen sitiveColu  mn    >> resultSet   SensitiveColum           ns =
                                 ma         skingService.ge    tResu  ltSetSensit  iveColumns(response.ge          tExecuteSql(), session);
             if (!DataMa           skingUtil.isSensitiveCo   lumnExists(resultSet        Sens   itiveColumns))     {
                    return;
                 }
                  respon  se.setExistSe    nsi tiveDat      a(t rue);
                    List<A     lgorithm> algorit  hms = mask  ingS  erv ice     .getRes     ultS    etMaskingAl     gorit   hmMaskers(r         es       ultSe  tSensit   iveCo    lu mns);
                                    m      askingService.mask      Row   sUsingAlgo  rithms(response, algorithm   s);
                  t       ry   {     
                                            se   tQueryCach  e(response, ses   si on, a  lgor     ith        m   s); 
                      } catch (Ex  cep  tion e)      {
                                        log.warn("Failed to se   t query cache   a     fter d   ata maskin      g, s     ql= {}",             re  sponse.getExecuteSql(), e);
                          }
                   tr   y {
                           setEditable(response   , sessi     on, algorithms);
                 } catch (Exceptio   n e) {
                       log.warn("Failed    to set edi  table after data masking, sql={}     ",  response.getExecu   te    Sql(),       e);
            }
        } catch  (E   xcepti     on e) {
              // Eat e    x      ception and  skip data masking
                         log.warn("F   ailed to  ma   sk q    u ery resu        lt set in SQL cons           o  le, sql={}", res  ponse.getExecuteSql(   ), e);
             }
    }

                @Ov  er  rid  e
    protect  ed String getExecute   S         tageNam       e() {
        return SqlExec   u   t   eStages.DATA_MASKIN        G    ;
       }

    @Over  ride
    p    ub      lic int getOrder()      {
         r   etu      rn 5;
       }

     p  r   iva te  v     oid setEdi   table(SqlEx   ecuteResult response, C  o                nnectionSession session, List<Al    go    rith m> algorithms) {
           if (!  response.getR    esu  ltSetMetaD     ata().isEditable(   )) {
                     return;
         }    
               DBResultSetMetaData   metaData = res    p   onse.g      etR esultSe    t   MetaData();
        String    s  chema               Na           m    e = metaData.getT  ab   le(             ).getD  atabas    eName();
                  Str     ing tabl     eNa     me = metaData.       getTable().getTable       Name ();         
        List<St       ring  > col     umns = res   p     onse.getRe    sultSetMetaDa ta().getField MetaDataList              ().stream()
                              .map(JdbcColu        mnMeta    Data::get ColumnNam      e).     coll    ec t(Collecto            rs.t    oL ist ()     );
        Set<Str     ing> sensitiveC  olumns = n    ew            HashSet<>();
                                 for (int i = 0; i      <   co      lumns.siz    e(); i+      +) {
                      if (Objects.nonNull(alg ori thms.get(i   ))) {
                 se nsitiveColumns    .add(co  l    umns.get(i));
              }
           }
        String    ro    wIdColumn = nu     ll;
                 if       (sessio      n.getDi  alec     tType().isOracle()) {
                fo      r (Str  ing c    o   lumn : col       umns) {
                             i  f (Odc  Con    stants.ODC  _INTERNAL_ROWID.equals       Ignor                    eC   a s     e(column)
                                                         |   |    OdcC on    st           an  t    s.ROWID.equalsIgnore      Case(     column)) {
                                   rowId Column = column;
                                      bre  ak;      
                          }
                           }
           }
                     if (Obj     ects.no nNull(rowIdColumn))  {
                            respo       n      se.se        tWhereColu   mns(   Col   lection         s.       singletonList(row    IdCo  lu  mn));    
                return;
                              }

        DBSchema    Accessor acce    ss      or = DBSc     hemaA  ccessors.create(    ses sion);
            List<DB      Tabl   eC           onstr   aint> constraints = acce     ssor.lis      tTableConstraints(  schemaNa      me,    tableName    ).stream()
                    .filte   r(e -           > e.getType  ()               == DBConstrain  tType.PRIMARY_KEY || e.getType() == DBConstra    intTy pe  .UNIQUE_KEY)
                          .sort    ed(Comparator.comparingI              nt     (o -> o.getType().ordinal(     ) ))
                   .collect(Colle  ctors.toList   ());
            f    or (DBTableConstraint con  strain        t : constraints     ) {
            List<String> constr     aintC  olumns           =       const raint.ge      tColum    nNames();
              boolean conta       i      n   sSensitive = false;
             f or (String constraintColumn : constraintColum ns) {
                        if (sensitiveCo       lumns.contains(cons    traint   Column)) {
                       containsS  e    nsitive = tr    ue;    
                    break;
                   }
            }   
                  if (new Ha     shSe    t<>(columns).contai     ns All(constraintColumns) && !contains  Sens      itive) {
                    response.setWhereColumns(constraintColumns);
                return;
                        }
        }
        metaData.setEditable(false )    ;
         }

       private     void setQ     ueryCache(SqlExecuteResult response, ConnectionSession sessio n, List<Algorit    hm> algorithms) {
           Virtual   Table virtu alTable = ConnectionSessionUtil.getQueryCache(sess  ion, response.getSqlId());
        if (Objec  t   s.isNull(virtualTable)) {
            return;
        }
        List<Integer> maskedColumn    Ids = new ArrayList<>();
        List<Integer> existColumnIds = virtualTable.columnIds();
        for (int i = 0; i < algorithms.size(); i++)  {
            if (Objects.isNull(algorithms.get(i)) && existColumnIds.contains(i)) {
                maskedColumnIds.add(i);
            }
        }
        VirtualTable maskedVirtualTable = virtualTable.project(maskedColumnIds, virtualElements -> virtualElements);
        ConnectionSessionUtil.setQueryCache(session, maskedVirtualTable, response.getSqlId());
    }

}
