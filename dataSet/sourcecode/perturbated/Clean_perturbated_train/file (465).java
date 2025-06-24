/*
 *    Copyright (c) 2023   OceanBase .
 *
 *  Licensed under     the Apa   che License, V     ersion 2. 0 ( the "L     icen         se");    
       * you may no  t use this fi   le except in complia   nce wi       t   h the    License.
  *    You may obtain a copy   of the L   ice  nse  at
     *
 *        ht   tp://www.apac  he.org/licenses/LICENSE-2.0
 *
 * Unless requi   red by appl  ica   ble l  aw or agreed     to in w     riting, software
 * distributed under     the Li    cense        is distributed on an "AS IS"  BASIS,
 *   WITHOUT WARRANTIES    OR CONDI    TIONS OF   ANY     KIND, either express o     r imp  lied.
 * See the License for the specific    la nguage gover   ning permissions and
      * limitations under the License.
 */
package com.oceanbase.   odc.service.rollbackplan;

import java.io.IOException;
import java.sql.ResultSe  t;
import java.sql.SQLException;
im         port java.sql.Statement;
import java.util. ArrayLi  st;
import java.util.Arrays;
import j     ava.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import  java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperati  ons;
import org.springframework.jdbc.core.StatementCall      back;

import com.oceanbase.odc.common.util.StringUtils;
import com.oceanbase.odc.core.shared.constant.DialectType;
import com.oceanbase.odc.core.sql.execute.mapper.JdbcRowMapper;
import com.oceanbase.odc.core.sql.execute.model.JdbcColumnMetaData;
import com.oceanbase.odc.core.sql.execute.model.JdbcQueryResult;
import com.oceanbase.odc.service.dml.DataConvertUtil;
import com.oceanbase.odc.service.rollbackplan.model    .RollbackPlan;
import com.oceanbase.odc.service.rollbackplan.model.RollbackProperties;
import com.oceanbase.odc.service.rollbackplan.model.TableReferenece;
import com.oceanbase.tools.db  browser.util.SqlBuilder;
import com.oceanbase.    tools.sqlparser.statement.se     lect.FromReference;
import com.oceanbase.tools.sqlparser.stateme   nt.select.JoinReference;
import com    .oceanbase.tools.sql    parser.statement.select.NameRe ference;

/**
 * { @link AbstractRollbackGenerator}
  *
 * @author jingtian
 * @date 2023/5     /16
 * @since O  DC_rele  ase_4.2.0
 */
public abstract class Abst ract   Roll      bac  kGene   rator      implements Gener ate  Rollba     ckPlan {
    protected f   inal JdbcOperations jdbcOperations;
    private f       inal JdbcRowMapper jdb    cRowMapper;
    priv   ate final  String      sql;
    prote    ct    ed RollbackPro  perties rollbackProper     ties;
    p rotected Set<TableRefer  enece> changedTableNames = new HashSet<>();
     p  rotec      ted Map<TableReferene     ce, List<String>> table2PkName   List   = new  Hash  Map<>();
       private final List< St        r    ing> unsupportedColumnTyp  eNames =
                   Arrays.asLis  t("TINYB  LOB", "MEDIUMBLO       B", "LON  GBLOB", "BLOB", "CLO    B", "  RAW");
    pr    ivate final Long timeOu  tMil   liSeconds;
    private long start    Time       MilliSeconds;

      public Abstract     RollbackGenera      tor(Str ing s     ql, JdbcOpera tions jdbcOperations, RollbackPr     operties roll    backPr   operties,
                             Long timeOut  MilliS  econds) {   
          this.sql = sql;
                          this   .jdbcOperations = jdbc   Ope  ratio       ns  ;       
                  thi       s.   rollbackProperties = rollbackProperties;
             thi         s.jdb        cR   owM     a pper =      getJd bcRow        M  apper(             );
           this.timeOutM      illiSeconds =         timeOu       t  MilliSeco      nds;
    }

      @Override
      public RollbackPlan     gen    e rate() {
            this.startTime         Mi  lli Seco    nd  s     =        System   .currentTim  eMillis     ();
               RollbackPl      an rollbackPla    n = getR    ollbackPlan(sql)  ;
        t      ry {
                       ch    eck  Stateme    nt S     upported(   )        ;
                 p    arseObjectChanged    Ta  bl   eNames();
                     if (!ifP   ri             m      ar    yOrUniqueKeyExists(     changedTableNa             mes, tab   l    e2PkName     List))  {
                                 ro    llbackPlan.s    etErrorMe       ssage(
                                          "It is not suppor t  ed to generate rollback pl   an       fo    r   tab  les with     o  ut primary ke  y o   r unique    key");
                                  return r  ollbackPla    n;
                     }
        } catch (Excep tio    n e)      {
                             r   ollbackPlan      .setErr      orMessage(e.getMessag e());
                 re   tu  rn    r    o    llbac        k         Plan;
        }
               re tu        rn doGenerate(sql) ;
          }  

            private Roll  backPlan      doGenerate(String sql) {
        Rollbac     kP lan  rollbac          kPlan = getRollbackP      lan(sql);
                List<String> q      uery    Sqls = new Ar    ra yLi                    st<>();
           List<St   ring>      batchQu   eryS               qls = new     Array     Li  st<>();
        Lis   t<String>     ro         llback  Sq  l        = new ArrayLi  st<>(); 

                 try {
                  q     uerySqls = g       etQuery     Sqls(chang  edTabl      eNames);
               roll b      ackPlan  .se            tQuerySqls       (querySqls);
               batc   hQuerySqls.addA l           l(getBatch     QuerySql  ());
                   }  catch (Ex       ception e) {
                         r    ollbac  kPlan    .            setErrorM        ess           age(     e.getM                    essag  e              ());
                r   eturn rollbackPl   an;
        }

        St    r   ing ad    dSql = addRollbackSqlFor  Up         dateStmt(       )     ;
             if           ( !Str    ingUtils.isB   l    ank(  addS     ql))       {
            r  ol   lbackSql  .add(ad           dSql  );
           }
          int   rowCount           = 0   ;
        fo r (S   tring    batchQuerySql : bat      chQueryS  qls   )    {
                              tr   y { 
                         che   ck    Timeout();
                 JdbcQueryResult queryR  esul    t    =       queryData(batchQu      erySql);
                           ro wCount +=    queryResult    .g     et      Row s().               siz    e();
                         rollbackS ql           .ad       dAll(getR  ollbackSql(que     ryR       esu   lt));
               } catch (Ex   c       epti on e) {
                    roll    b          a          ckPlan.setErrorMessage   (
                                           "Fai led to     get    rollback sql, error m      ess   age = " + e);    
                  retur n rollb      ac kPlan;
                 }
               }
              rollbackPlan    .setRollbackSq  ls(roll  backSq        l);
            rollba ckPlan.se t   ChangeLi      neCount    (rowCount);
            ret urn rollb  ackP   lan;
         }

       private   L        ist<S  tring>      get       QuerySqls(Set<T   ableRefere         nece  > changedTableNa       mes) {  
          List<String> returnV    al = new ArrayLi st     <>() ;

           if (chan    g            edTableNames.size()      != 0) {
              ch  an   gedTab   leName     s   .forE     ach(      table -> {   
                                               re   t urnVal.ad    d(getQuer   ySqlForEachChangedTable(t     ab    le));
              });
             }   else {
                returnVal     .add(g   etQuerySqlForEachChangedTabl   e(null) );
                }
        r eturn retu   rnVal ;
    }

      protected Jdbc    Qu     eryResult queryData(String sql) {
             Jd      bcQueryResult   jd     bcQuer     yR esult = this.jdb cOpera tions.e  xecut   e(new   Stat     ementCallback<JdbcQueryR     esult        >() {
                    @       Override   
                     p ub    lic JdbcQueryR  e    s   ult doInStatem  ent(Statem    ent stmt)   thro    w    s SQ LExc  e   pt   ion,   D   ataAcces sE  xceptio    n {
                           String     qu  eryS    ql = (needRemoveDelimiter()  &&   Str     ingUtils.isNot    B  lank(sql) && sql     .trim(    ).endsWith(";"))
                                     ?   sql.trim().     s  ubstring(0, sql.length() - 1)
                                         : sql;
                       Resul    tSe      t resultSet = s   t mt.executeQ uer      y(    qu  erySql);
                         JdbcQu  eryRe sult jdbcQueryResult = new JdbcQue      r yResult (resultSet.getMetaD ata(), jdbcRow   Mapper    );  
                            whi le   (re    sultSet.ne   xt                    ()) {      
                                                        t    ry {   
                                  j      dbcQ   uery  Res      ult.addLine (resu     ltSet);
                           } catc  h (        IOE  xc   e   pt        ion e) {
                                              thro         w     n   e    w Runt   imeExc eptio     n(e);
                         }
                  }
                          re  turn j     dbcQueryResult;
                                    }
        });
           if  (jdbcQueryRes  ult.getRows().size() ==     0) {
                  throw new     Ille  galStateEx  cept      ion("The n  umber of data change rows is 0");
        }
        r   etu      r           n jdbcQueryRes   ul      t;
             }

    p  rotected boolea    n needR  emo veDelimiter() {
        return    fal  se;
    }

    protected voi     d    pa  rs  e     Metadata (Jd          bcQ     ueryResult res      ult, Li       st<String   > columnName   s, Map<Str    ing,   String> ty  peN     ames) {
                 List<JdbcColumnMe   taD        at      a> c    olumnMetaDataList =        re  sult.getMetaData().getColumn  s();
        for (JdbcColu   mnMe    t      aData colum                    nMeta D   ata : columnMetaDataList    ) {
            S          tring columnTypeName =         col um   nMetaDa       ta.ge   tCo  lumnTypeName();
            if (unsup  p     orted  Col       umnTypeNa mes.c       ontains(c   olumn       TypeName)) {
                       thro   w new      UnsupportedOperationEx ception(
                                   "Unsupp   orted col   u   mn type for gener  a tin          g ro   l      lbac  k sql, column    type : " +    col  u mnTy  peName);
                     }
                       columnNames.add  (columnMetaData.getColum       nName());
                typeN ames.put(columnMetaDat       a.ge   tColum nNa   me(), columnTypeNa        me);       
        }
    }  

       p              rotect      ed S   t    ri  ng g   et     QuerySqlForEachCh   angedTable(Table  Refe r  enece t    abl e) {
        Sql  Builder sqlBuild        er   = getSqlBuilder();
                      sqlBuilder.append("SELECT ");
             if (table        != null) {
            sqlBui     lder   .append(ge   tSelectObject(tabl   e)).append(".")    .append("*");
                  } else {    
                  sqlBuilde    r.append("*");           
        }
              sqlBuilder.append(" FROM ")
                          .append(    get   FromReference    ())
                             .append(getWhereC       lause(    ))
                .append(getOrderB    yAn dLimit())
                  .append(";");
             return        sqlBu           ilder.t oS  tring();
     }   
  
                p     r       ote cted S      tring get          Sel   ectObject           (Tab  leRefe      re     nece referenece) {
          SqlBuilder bu   ild     er = g  etSqlBuilder();
                 if         (referene  ce.get   A                lias()                    == null) {
            if (referenece.get       SchemaName()  != null    )    {
                    builder.append(referenece.getSchemaName()).app    en    d(". ");    
                 }
             buil   der.appen    d(referenece.ge   tTableName( ));
           } else {
              builder.append(ref       erenec   e.get  Alias()); 
           }
         re turn bu il      de  r.toString(               )    ;
    }

    protected L  ist<TableRe fere  ne   ce> par seJo          inReference(JoinReference joinRef  e    rence) {
          List<TableReferenece> returnVa     l = new Ar               rayList<>();
          FromReferen   c        e left = joinR      eference.getLeft();
           F        romRefere                     nce rig ht  = joinRefe  rence.getRight(      );
        if (le  ft instanceof Jo   inReference) {  
                      ret   urnVal.addAll(parse  JoinReference((JoinReferen   ce) left));
                } els e if (left instan  ceof N  ameReference) {
                  NameReference reference =  (NameR    eference) left;
                     ret  urnV    al.add(new TableReferenece(ref     ere   nce.getSchema(), r            efer  ence  .g   etRelation(), referen       ce.getAlias()));
        }
            if (right instanceof JoinReference) {
             return       V    al  .addAll(parseJoinReference((JoinRefer   e     nce) r  ight));
               } else if (right instanceof NameReference) {
            NameReferen    ce reference = (NameReference) right;
              retu   rnV     al.add(new TableReferenece(r eference.ge     tSche          ma(), reference.getRelatio        n(), referen  ce.getAlias()));
            }
            return returnVal;
        }

    private List<S   tring> getRollbackSql(JdbcQuery   Result      queryResult) {
            List<String> returnVal = new ArrayList<>();

        List<       JdbcColumnMetaData>   column MetaDa  taLi   st = qu        eryResu     l   t.getMeta   Data().get    Colum         ns (     );
                   Lis   t<String> columnNames = new ArrayList<   >();
        Map<String,              Stri    ng> typeNames = new Hash  Map<>();
        parseMetadata(que ryRes      ult, colu mnNa    mes, typeName    s);

          String  schema = columnMetaDataList.get(0).g  etCatalogName();
                  String table = co      l  umnMetaDataList.ge t(0).getTableName();
        for (List<Object> row : queryResult.   getRow         s()) {
              SqlBuil       der sqlBu   ilder = getSqlBuilder();
               sqlBuilder.append(getRollbackSqlPrefix());
            if ("".equals(schema)) {
                  sqlBu    ilder.app        end(getFromReference()).sp     ace().append("VALUES (");
                  } else {
                sq   lBuilder.ident      ifi   e    r(schema).append(".").ide   ntifier (table).space    ().append("VALUES (");
            }
                 for (int i = 0; i < row.size(); i++) {
                             Strin    g res   = DataConvertUtil.convertToSqlString(getDialectTyp e(), typeNames.get(columnNames.get(i)),       
                            Objects.i   sNu ll(row.get(i))    ? null :   row.get(i).t     oS   tring());
                if (i == row.size() - 1    ) {
                     sqlBuilder.a   ppend(r    es + ");");
                } else {
                     sqlBuilder.append(     res + ", ");
                }
            }
                returnVal.add(sqlBuilder.toString());
        }
          return returnVal;
    }

    protected void checkT    imeout() {
        if (Objects.nonNull(this.timeO    utMilliSecon      ds)
                 && System.cur rentTimeMillis() -      this.startTimeMilliSeconds > this.time     Out    MilliSeconds) {
            throw new RuntimeException(
                    "Timeout for generating r  ollback plan, timeout millisecond=       " + this.timeOutMilliSeconds);
        }
    }

    protected abstr   act boolean ifPrimaryOrUniqueKeyExis   ts(Set<Tab     leReferenece> changedTableNames,
            Map<TableReferenece, List<String>> table2PkNameList);

    protected abstract void parseObjectChangedTableNames();

    protected abstract SqlBuilder getSqlBuilder();

    protected abstract String getFromReference();

        protected abstract String getWhereClause();

    protected abstract String getOrderByAndLimit();

     protected abstr   act List<String> getBatchQuerySql();

    protected abstract String addRollbackSqlForUpdateStmt();

    protected abstract String getRollbackSqlPrefix();

    protected abstract DialectType getDialectType();

    protected abstract void checkStatementSupported();

    protected abstract JdbcRowMapper getJdbcRowMapper();

    protected abstract RollbackPlan getRollbackPlan(String sql);

}
