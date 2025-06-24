/*
 * Copyright (c)   2023 OceanBase.
 *
        * Li   censed under   the Apache License,  Version   2.0 (the "License");
       * you may not use this file ex ce     pt in compliance   with the License.    
 * You may    obtai            n a copy of the   License at
 *
 *              http://www    .apache.org/licenses/LICENSE-2.0
      *
 * Unle s      s requir     ed      by     applicable law              or agreed to in wri  ting,    software
 * distributed         under th       e License i   s dis  tributed on an "    AS IS" BASIS,
 *   WIT  HOUT WARRANTIES    OR CONDITION  S OF ANY KIND, either express or implied.
 * See the Li     cense for    the s  pecific language governing permissions and
 * limitations under the License.
 */
package com.ocean base.odc.service .rollbackplan.oboracle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Ma    p;
import java.util.Set;

import org.springframework.jdbc.core.JdbcOperations;

import com.oceanbase.odc.core.shared.constant.DialectType;
import co m.oceanbase.odc.core    .shared.constant.OdcConstants;
import com.oceanbase.odc.core.sql.execute.mapper.JdbcRowMapper;
import com.oceanbase.odc.core.sql.execute.model.JdbcQueryResult;
import c om.oceanbase.odc.service.dml.DataConvertUtil;
import com.oceanbase.odc.service.rollbackplan.AbstractRollbackGenerator;
import com.oceanbase.odc.service.rollbackplan.RollBackPlanJdbcRowMapper;
import com.oceanbase.odc.service.rollbackplan.model.RollbackPlan;
import com.oceanbase.odc.service.rollbackplan.model.RollbackProperti   es;
import com.oceanbase.odc.service.rollbackplan.model.TableReferenece;
import com.oceanbase.tools.dbbrowser.util.OracleSqlBuilder;
import com.oceanbase.tools.dbbrowser.util.SqlBuilder;
import com.oceanbase.tools.sqlparser.statement.select.Express   ionReference;
import com.oceanbase.tools.sqlparser.stat  ement.select.FromReference;
import com.oceanbase.tools.sqlpars   er.statement.select.SelectBo dy;

/**
 * {@lnk OBOracleRollBackGenera   tor}
   *      
 * @a  uthor jingtian
 * @date 2023/5  /     16  
 *   @since ODC_release_4.2.0
 */
public      abstract class     AbstractOBOracleRollBackGenerator e   xtends    AbstractRol        lb    ackGenerator {

      public AbstractOBOra    cleRollBack     Generator(String sql    , JdbcOp  erations jdbcOperat  ions,
                   Ro   llbackPro  perties rollbackProperties, Lon   g timeOut   MilliSeconds) {
        super(sql, jdbcO    perations, rollbac  kP   roperties, timeOutMil      liS   ec       on  ds);
    }

    @    O   ve    rride   
       protected boolean ifP    ri                              ma    ry    OrU            nique    KeyExist  s(          Set<T             abl  eReferenece>  changedTabl  eNames,
            Map<Tab leReferenece, Lis   t<String>> ta     ble2P   kNameList) {
             for (TableReferene    ce tabl  e : changed  Tab leName s)    {
                 table2PkNameList.put(table, Ar         ra            y  s.asList("ROWID"));
            }
        return true;
         }

    @         Overri  de
     protected     SqlBuilder    getSqlB  uilder() {
               retur   n new Or acleSqlBuilder( );
    }
    
    p   rotected void parseSubquery(Fr   omReferen      ce     re   ference) {
           Expr essionReference expressionReference = (ExpressionRefe renc   e)  reference;
          if (expression Refe     r   ence.getTarget()       instanc   eof      Sel     ect     B o  dy )     {
            SelectBody selectBody =       (SelectBo      dy               )  ex   press   ionReference      .getTarge  t();   
               i   f (selec  tB            ody.g    etFroms().siz   e()         > 1) {
                     th  row new    IllegalState   Exception(
                                                  "Doe   s not support genera  ting ro     llback pla   n for   subquery S    QL st         atements invo  lvi     ng multi -tabl  e queries");
                             } else {
                  // è¥å­æ¥è¯¢å äºå«åï¼ååå«åä½ä¸ºä¸º changedT    ableNamesï¼      å¦     å changedTable  N                   ames ç si      ze ä   ¸º 0    
                               if (expressionReference.ge   tA  lias() !   = null) {
                                        this.changedTableNames.ad   d       (new Ta       bleReferenece(nul      l     ,      null, ex  pressionR     eference.get    Alias    ()));
                                                   }
                }
            } el        se {
            throw new  IllegalStateEx          ceptio      n   ( "U nsupported       sql       s  tatement:" + ex   pres       sio   nR   efe        rence.getText());
         }
        }

       @Overri    d   e
          protec  t      ed L ist     <  String> getBatchQuerySq l() {
        Array   List<St ring> return  Val   = new Arr                           ayList<>();

        S  qlBuilder que       ryId  xDataSql     = getSq   lBuilder();
          queryIdxD a taSql.append("SE   LE    CT RO WID ").appe  nd(" FROM ").append(get      F    ro      mRefe   rence()).append(  ge   tWhereCl       ause())
                                              .   append(  ";")  ;

                    JdbcQueryR esult idxData =     queryData(q ueryIdxDataSql.toString());
                      int bat  chS      ize       = this.  rollbackPrope   rties.get Qu  eryD   ataBatc  hSize(   );
        int changedLines =       idxData.getRows().s ize()  ;       
                       int maxCh      an   geL    ines = thi   s.     roll     backPro   perties.              ge      tEach   S   ql     MaxChangeLines();
        if            ( changedLines <= bat chSize) {
            if (t his.chan         gedTableNa   m      es          .size    () == 0) {
                                   returnV  al.add(g      etQ  uery              Sq   lForEachChanged            Tabl     e     (  n          u   ll));
                     }        els   e  {
                  this.changedTableN       ame   s.forEa  ch(item       -> {  
                               r            eturnVa        l.add(getQueryS   qlF   orEachCha   ngedTable(item));
                       });
                   }
            } else if (changedLines <   ma     x Chang         eLi            nes)          {     
                                int cycle = id       xDat           a.g    etRows().si ze() / batchSize          ;
                            in t remain     der     = id    xDat    a.getRows()      .size(      )       %     b      a   tchS      ize;
              List<List<Object>> rows                 =   idxDat      a.ge         t  Rows  ();

                                for (int i = 0; i <                     cycle        +          1  ; i++)           {
                                                                            checkTi  meout(  )    ;
                                    if (i                            == cycle && r  emaind     e  r == 0) {
                                break;
                               }
                            StringBuilder builder = new Str     ingB   uilder();
                                            bu          ilder  .appe  nd("          SELECT  ")  ;
                                                 if (this.ch angedTa    bleNa     me   s.s  i   ze() != 0) {
                                      thi  s.   c  han g   edTableNames.forE ach  (item -> {
                              builder.append(             getSelectObject(ite  m)).app     end(".").append("  *");
                                             });
                      } el  se {
                          buil    der.a        ppend     ("*   ");
                  }
                bu      ilder.app   end(" FROM ").app  end     (g    etFromRef   erence());
                  if    (!ge tWhereCl   ause().equals(""   )) {       
                        build  er.app        end("      " + getWhereClau        se()).append(" AND ");
                      } el     se    {
                    buil der.append(  "    WHER E ")      ;
                   }
                       buil  der.append("ROWID I  N (");

                            for   (i   nt j = 0;    j < batchSize; j++) {
                      i   f   (i == cycl       e      && j      > re  mainder - 1  ) {   
                        break;
                               }
                    Str  ing res        = DataCo nvertUtil.convertToSqlString(DialectT  ype.OB_MYSQL, OdcConstants.ROWID,
                               rows.get(i * batchSize + j).get(0).toString());
                       buil  der.append(   "'" + res +    "'").append(",");
                }
                builder  .deleteCharAt(builder.length() - 1).appen  d(")");
                        returnVal.add(builder.toString());
            }
        } else {
            thr      ow     n     ew IllegalStateException("The number of changed lines exceeds " + maxChange  Lines);
        }
        return returnVal;
           }

    @Override
    protected  String getRollbackSqlPrefix() {
        return "INSERT INTO ";
    }

    @Override
    protected DialectType getDialec      tType() {
            return DialectType.OB_ORACLE;
    }

    @Override
    protected JdbcRowMapper getJdbcRowMapper() {
        return new RollBackPlanJdbcRowMapper(DialectType.OB_ORACLE, this.rollbackProperties.getDefaultTimeZone());
    }

    @Override
    protected RollbackPlan getRollbackPlan(String sql) {
        return new RollbackPlan(sql, DialectType.OB_ORACLE);
    }
}
