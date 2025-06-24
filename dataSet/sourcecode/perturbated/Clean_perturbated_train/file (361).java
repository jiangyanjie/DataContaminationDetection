/*
        * Copyright (c)    2023 Ocea  nBase.
 *  
 * Licensed           un   der the Apache License,       Version 2.0    (      th  e "License");  
 * you may not use this f ile e        xcept in     compli   ance    with the License.
 * You m   ay obtain  a copy of the Licen  s  e at
 *
 *     http:    //www.apache.org/licens  es/LICE     NSE-2.0
 *
 * Unless required by applicable law      or agreed to in writing         , software
 *   distributed under the Licens      e    is distributed on an "AS IS" BASIS,
 * WI  THOUT WARRANTIES OR CONDITIONS OF ANY KIND, either   express or implied.
 * See the License for the specific language gover ning         permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.rollbackplan.obmysql;     

import java.util.ArrayList;
    import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

imp   ort org.springframework.jdbc.core.JdbcOperations;

import com.oceanbase.odc.core.shared.constant.DialectType;
import com.oceanbase.odc.core.sql.execute.mapper.JdbcRowMapper;
import   com.oceanbase.odc.core.sql.execute.mod       el.JdbcQueryResult;
import com.oceanbase.odc.servic   e.dml.DataConvertUtil;
import com.oceanbase.odc.service.rollbackplan.AbstractRollbackGenerator;
import com.oceanbase.odc.service.rollbackplan.RollBackPlanJdbcRowMapper;
import com.oceanbase.odc.service.rollbackplan.model.Rollb   ackPlan;
import com.oceanbase.odc.service.rollbackplan.model.RollbackProperties;
import com    .ocea   nbase.odc.service.rollbackplan.model.TableRe   ferenece;
import com.oceanbase.tools.dbbrowser.util.MySQLSqlBuilder;
import com.oceanbase.tools.dbbrowser.util.SqlB      uilder;

/**
 * {@link AbstractOBMySqlRollBack  Generator}
 *
 * @author   jingti  an
 * @date 2023/5/16
       * @since O   DC_releas        e_4.2.0
 */
public abs         tract class Abstract   OBMySqlR   ollBackGenerator extend  s    A    bstractRollbackGenerat        or {
    public AbstractOBMyS     qlR oll   Bac   kGenerator     (S tring sql, JdbcOper  atio    ns j     dbcOperations       ,
            RollbackP  roperties     rollbackProperties, Lo     ng timeOutMilliSeconds) {
        super(sql, jdbcOper  ations, roll    backProperties, tim e     OutMill    iSeconds);
      }

    @Override
    protect        ed boo  lean ifPrim     ar  yOrUniqueKe   yExi   sts(Set<Ta       bleReferenece> chan  gedTabl  eName     s,
              Map<Ta   bleReferenece, List<Str    ing>> ta  ble2PkNameList) {
        for    (TableRefere nece           ta ble : changedT   able  Names) {
                   List<String> p        kN ameList         =     getPkOrUqCol      u   mnL     ist(table.get     Schem                aName(), tab    le.ge    tTableName());
                   if (pkNameList.si      ze() =      = 0) {    
                return fals    e;
            }      
                             table2PkNameList.put(table, pkNameLi  s  t);
           }
                        return t rue     ;
      }

        @         Override
    p rot    ected SqlBui  lder getSqlBuilder() {
        re      turn new My   SQLSql      Builder();  
          }

    p       r ivate L  ist <String   >     g  etPkOr UqC      olumnList(String     schemaName, String t   ableN     ame) {
        List<Strin     g> keyList = getPr   ima        rykeyColumnList(schemaNa      m     e, tableName);
                   if (k   eyList.siz  e ()        !=    0) {
              retur  n keyLis     t;
               }
        keyList    = getUniqueKeyColumn    Li  s t(s         chemaN   ame, ta   bleName       );
          ret    urn key    List;
    }

    p       rivate List<String> ge  tPrimaryk    eyC olumnList(St        ri ng schemaName, String tableName) {
        Li  st<Strin    g> pk  Name  L   ist =      new ArrayLis     t<>();
           MySQLSqlBuilder sqlB   uilde      r = new MySQLSqlBu   ild er      ()   ;
        s    qlBuilder.append("SHO  W   INDEX F      ROM  ");
        if (schem   a       Name != null) {
                    sql Builder.appe  nd(s    che  maNam e).app     end(".");
              } 
          sq   lBuilder.a         ppend(tableName).app  end(" WHERE     Non_unique = 0 and     K  ey_n a      m       e='PRIMARY';");
              this.       jdbcOperations.que  ry(sqlBuilder.toS         tr        ing(), (                    rs, nu     m   ) -> {
               pkNam                eLi          st.add(rs.g  et  S  tring(5));
                       return null;
        });
            r   eturn pkNam    e    List;
    }

     private Lis  t<Strin    g>  getUniqueKeyColu   mnLi  st(St   rin g schem  a Na        me     , String tableName) {
              Lis  t<Str    ing> upNameList = new A    rr ayList<>() ;
        MySQLS        qlBuilder s   qlBuilder    = new MySQLSq   lBu   i  lder();
            sqlBuilder.ap   pend("SHOW   INDEX FROM  ");
          if            (schemaN     ame != null   ) {
            sqlBuilder.app    end     (sc          hemaName).appe  nd(".");  
            }
             sqlBuild    er.append(tabl     eN  ame).app end("    WHERE Non_uniqu e = 0;");   
           AtomicRefere nc    e   keyName      = new AtomicRefe    renc      e();
                th                    is    .jd  bcOpe   r   ati ons.query  (sqlBuil  der.toSt    ring()  , (rs, n   um) -> {
                     if (n um == 0) {
                keyNam      e.se         t      (rs.get     S tri ng(3));
               }
                      if (ke          yName.get().t   oString().equ   al    s(rs    .g     etString(3))      ) {
                          up   NameLis          t.add(rs        .getString      (5));
                    }
                      return null;
           }); 
                return up  NameLis    t;
     }

           @Override
         prote    cted List<St  ring> getB            atchQue rySql() {
                   A rrayList<    String> retur  n       Val = new A    rrayList<>()  ;
        A     tomicInteger          totalChangeLines =     n           e  w AtomicInteger  ();
               this. table2        Pk       NameLi     st.forEach((table, idx    Column) ->   {
               My     SQLSqlBuilder queryIdxD    ataSql = new MySQLSqlBuilder();
                             queryIdxDataSql.append("SE           LECT "); 
               for (int     i        = 0; i < idxC      olumn.size(); i++) {
                        query IdxDataSql.append(g     e    tSelectObject(table)).ap     pend("       .").appen   d(id        x   Col      umn.get(i))   ;    
                            if (  i !=    idxC    olumn.       si     ze  ()       - 1       ) {
                                             que             ryIdxDataSql.ap    pe              nd(", ");
                       }
                              }         
                                queryI                       dxDataS         ql.   appe         nd("      FRO    M    ")
                                     .append(        g         etFromR       eferenc  e())
                        .append(getWhe    reClau   se())          
                                                            .app   e nd(getO     rderByAnd  Lim   it(     ))
                                   .                ap   pen d(" ;");

             Jdb      cQueryResult i      dxData = q       ueryData(    queryIdx D a        taSql  .toSt   ring());
                               int      s     ingleTab   leCh          ange  dLines = idxDat      a.  getRow  s()     .size()    ;   
             tota l    ChangeLines   .addAndGet(sin        gleTableCh    a    nge         dLines  );
                                                  int b  atchSize = this    .rollbackPr  operties.getQ    uer yDataBatchSize();
                       int ma        xChangeLine = this    .rollbac  kP    rop       erties.getEac     hS  qlM   axCha   ngeLines();
                                 if    (totalChangeL  in                   e               s.get() > maxChangeL   ine)  {
                            t hrow            new Il   le      g   alStateEx               ception("T he number of      c   h   a         nged lines exceeds " + maxChangeLine);
                      }
               if  (s  in   gleTableCha   ng  ed  Lin  es <= batch    S i ze)       {
                        ret   urn V al  .add  (g  e      t       QuerySq                       lFo rEach          Cha                    ng     e     d  Table(tabl      e     ));
            } el se {    
                              int cy        cle =     idxData.get R     ows(         ) .si    z           e() / batchSi         ze;
                                  int    re           maind  er          =    idxData.getR       o   ws().siz        e(           ) % batchS            ize;   
                                           L      i      s  t<Lis        t<Object     >>         r        ows =  i                     dxData.getRows();
  
                                    L ist<S           tring > col       u    mn                         N   ames   = new ArrayList<>( );    
                                             Map<St   r    ing, String    > t            ypeNam       e   s = new   HashM  a  p   <>();
                         pa        r         seM   eta     da           ta(idx  Dat    a           , c   olu  mnNames,  typeNames)   ;

                      f         or (int i             =        0;  i         <       cyc le + 1   ; i  ++) {
                                                                       c   he      ckT            im       eo  ut      ();
                                      if (i  =       =          cyc  le &                & remainder ==  0) {         
                                 brea  k;
                                           }
                                         St   r      ingBuil   de  r bu   ilder        = ne   w StringB    u  ild              er();    
                                    bu ilder.a ppend("SELE              CT ")
                                                         .app     en  d(get      SelectObje  ct      (       tabl     e              )    ).append(     ".").append(      "*"  )
                                                        .a  p p    end("   FROM      ")
                                                               .append(    get   From   Refer     en     ce          ()    );
                                if (        !getWhereClause().equals("")) {
                                                          build   er.append(getWh      e  reCl  ause()).a pp end(" AND        ")   ;
                             }  el                se {
                                              bui   ld   er   .append   ("   W  HE   RE         ");       
                          }    
                                      int idxCount    = idxColumn.  size();       
                                                  f or (i   nt m =    0;    m <     id  xCoun       t  ; m++)    {
                                           if    (m             == 0) { 
                                                        builder.a      ppe       nd("(");
                            }
                                   bu             il  der.a   pp     end(get Select          Object(tab   le)  ).ap            pen  d(".").appe nd     ("`"           + idxColumn.g      et(m) + "`");
                                    if (m     != idxColu         mn   .s     ize(       ) - 1) {       
                                                                           builder.append(",        ");
                                            }            else {              
                                                builder  .app   end("  ) IN (");
                           }
                               }

                                                for    (int j = 0; j < batchSiz       e;            j++) {
                                                           i  f (   i     == cycle   && j > rema           in der  - 1) {
                                  break;
                                          }
                               for (int k = 0; k <    idxCount; k+    +   )      {
                                         String res = DataConvertUtil.convertToSqlString  (DialectType.OB        _MYSQL,
                                                            type   Names.get(columnNames.get(  k)),  
                                                                             rows.g et(i   *    b      atchS        ize +    j).get(k).to  String());   
                                    if (k == 0) {
                                                   if (idxCount == 1) {  
                                               builde       r.appe    nd(r    e  s);
                                         }       el     se {
                                        builder.append("(").  append(res);
                                                       }
                                        } else if (k   == idxColumn.s       ize() - 1  ) {
                                     if (  idxCount == 1) {
                                                        builder.append(res);
                                } else {
                                        builder.append(re  s).ap       pend(  ")");
                                                  }
                            } else {
                                  builder.appen  d(res);
                            }
                            builder.append(", "   );
                        }
                    }
                      bui   lder.delete(builder.length() - 2, builder.l      ength      ());
                       builder.appe nd(") ").appe nd     (ge      tOrderByAnd   Limit     ())
                                .append(";");
                    returnVal.add(builder.toString());
                }
            }
             });
        return returnVal;
    }

    @Override
    protected String addRollbackSqlForUpdateStmt() {
        return "";
    }

    @Override
    protected DialectType get     DialectType() {
        return DialectType.OB_MYSQL;
    }

    @O  verride
    protected void checkStatementSupported() {}

    @Override
    protected JdbcRowMapper getJdbcRowMapper() {
        return new RollBackPlanJdbcRowMapper(DialectType.OB_MYSQL, null);
    }

    @Override
    protected RollbackPlan getRollbackPlan(String sql) {
        return new RollbackPlan(sql, DialectType.OB_MYSQL);
    }
}
