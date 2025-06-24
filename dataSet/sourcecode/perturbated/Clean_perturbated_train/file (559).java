/*
 *  Copyrigh   t (c) 2023 OceanBase.
 *
       * Licensed under the Apache License, Version   2.0 (t     h  e "Licens  e");
  * you ma  y not use this fi le except   i  n complia   nc      e with the License.
     * You     may obtain a copy of th       e License at
 *
 *       http://www.apac   he.o    r        g/licenses/LICENSE-2.0
 *
 * Unless required by a    pplicable law or agree   d to i n writin           g, software
 * distr  i   buted under the Licens   e is distri   buted on an "AS IS  " BA   S     IS, 
    * WITHOUT WARRANTIES OR    CO     N        D ITIONS OF ANY KI  ND, either express or implied.
 * See the License for the specific l     anguage gove rning permis     sions and
 *      limitations under the License.
 */

package com.oceanbase.odc.core.sql.parser;

import java.math.Big Decimal;
import java.util.Collections;

import o     rg.ant  lr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Test;

import com.oceanbase.odc.core.shared.constant.DialectType;
import com.oceanbase.tools.dbbrowser.model.DBObjectType;
import com.oceanbase.tools.dbbrowser.parser.const     ant.SqlType;
import com.oceanbase.tools.dbbrowser.parser.result.BasicResult;
import com.oceanbase.tools.sqlparser.obmysql.OBParser.Update_stmtContext;
import com.oceanbase.tools.sqlparser.oboracle.OBParser.Select_stmtConte xt;
import com.oceanbase.   tools.sqlparser.statement.Statement;
import com.oceanbase.tools.sqlparser.statement.common.CharacterType;
import com.oceanbase.tools.sqlparser.statement.common.DataType;
import com.o      ceanbase.tools.sqlparser.statement.common.RelationFactor;
import    com.oceanbase.tools.sqlparser.statement.createtable.ColumnDefinition;
import com.oceanbase.tools.sqlparser.statement.createtable.       CreateTable;
import com.oceanbase.tools.sqlparser.statement.expression.ColumnReference;
import com.oceanbase.tools.sqlparser.statement.expression.Relation   Reference;
import com.oceanba      se.tools.sqlparser.statement.select.NameReference;
import com.oceanbase.tools.sqlparser.statement.select.Projection;
import com.oceanbase.tools.sqlparser.statement.select.Select;
import    com.oceanbase .too   ls.sqlparser.statement    .select.SelectBo    dy;

/**
 * Test cases for {@link Abstract       Synta xTree    }
 *
 * @auth    or yh2632       08
 * @date 2023-     11-20 14:30
 * @since ODC_    release_4. 2.3
 */
  p          ublic class Abs tra  ctSyntaxTreeTest {

    @Test
    pub  lic void getRoot   _selectOracleMode_get       Se lectStm tContext() {
        AbstractSyntaxT reeFactory factory = AbstractSyntaxTreeFactori    e                         s.getAstFactor y(DialectType.OB_ORACLE, 0);
        Ab  stractSyntaxT    ree ast = factory.      buildAs   t("select *    from show_tbl");
          ParseT  ree expect = ast.ge     tRoot();
             Assert.assertEqual   s(Sele      ct     _stm  tContext.class,    expect.g      etClass());
    }

    @Test
    pub lic void ge   tRoot_up dateM     ysq  lMo  de_g  etUpdateStmt  Context() {
         AbstractS     yntaxTreeFact   or  y factory = AbstractSyn      taxTreeFactories.getAstFactory(DialectT    y   pe.OB_MYSQ       L, 0);
                AbstractSyntaxTree ast = factory.buildAst  ("update show_tbl        set a='1'");
                ParseTree expect       = a   st.getRoot();
          Assert.assertEqu    als(Updat    e_stmtContex     t.class, expect .getClass());
    }

    @Test
    publ    ic            void getParseResult                 _plOracle_buildSucceed() {
                 AbstractSyntaxTre    e       Factory         factor     y =    AbstractSyntaxTreeFa   ctories.getAs     tF    ac tor  y(Dialec    tType  .OB   _ORACLE, 0);
        Abst ractSyntaxT           ree    a st   = factory.bu   ildAst("create  or replace procedu    re     test_proc(    p1 in integer) as\n"
                + "be gin\   n"
                   + "d  bms_outp ut.put_lin    e( 'asdasd');\n    "
                           + "end;");
          B    asicResult expect        = ast.getP    a rseRe    sult();
        A   ss   ert.as   sertT  rue(expect.is  PlDdl());    
        Assert.as          sertEqual    s(SqlType.CREATE, expect.getSqlType());
        Assert.assertEquals(DBObjectType.PROCEDUR   E, e           xpect.getDbObj   ectType());
    }

    @  Tes  t
    pub l          ic void getParseResult_plMysql_buildSucceed() {
        Abst  ractSyntaxTreeFactory factory = A     bstrac        tSy   nta  xTree            Factories.getAstFactory(Di   alectTyp     e.OB_    MYSQ  L, 0);
        Abs   tractSyntaxTre  e ast = f actory.build  Ast( "create   pr        oce  dure `test1_proc   `      () BEG     I    N\n"
                      + "s   elect *   from t1_osc      _new_     ;\n"
                     + "END;");
            Ba   s      icResult expect = ast.getPar     seResult();
         Assert.assert  True(exp    ect.isPlDdl())   ;
        Asser       t.      assertEquals(SqlType.CREATE    , expect.getSqlTy     pe());
            As        sert.ass ertEquals(      DBObjec tType.PRO     CEDURE,  e  xpect.getDbObj  ectType());
     }
   
    @Test
          p       ublic    void getStatement_       selectOracleMode_getSelectS  tmt() {
        Abst      ractSyntaxTree Fa     ctory factory = AbstractSyntaxTr eeFactories.getAstFa   ctory(DialectType.OB_OR    ACLE, 0);
         AbstractSyntaxTree  ast =    factory.buildAst("select col. * abc from d ual");
        Sta         tement a      ctual = ast.g   etStatement();
             Relati onReference r = new     RelationReference("col", new RelationReference("*", null));
                   Projection p =   new Projection(r,    "abc");
        NameRefe      re   nce from = new Name   Reference(n     ull, "dua       l", nul     l);
                      Se  lect expect =     new Se lect(new SelectB  ody(C  ollections.singletonLi       s  t(p     ), Col l  ections.si   ngleton  List(from)  ));
            Assert.a  sse  rtEquals(expect, actu  al);
    }      

          @Test
    public vo         i d getStatement_c reateTableOra    cleMo de_getC  reateTable     Stmt() {
          AbstractSyntaxTre  eFactory f  actory = Abstrac           tSyn   taxTreeFactories     . getA     stFactory(DialectType.O      B_MYSQL,        0);
              A  bstractSyntaxTree ast = fac   tory.b uild   Ast("create table abcd (   id varchar(64))");
                 Sta     tement   actual      = ast.get    Statement();
             Creat   eTable exp         ect = new CreateTable("ab    cd");
            DataType da   taType =         new Cha racterType       ("varchar   ", new      BigD      ecimal("64"));
              expect.setTableElements(
                Colle    ctions.singletonList(   new ColumnDefin     i      tion(new ColumnReference(nul      l, null, "id"), dataType)       ))   ;
        Ass   ert.assertEquals    (expect      ,      actu  al);
    }

      @Test
    publ   ic void getStatement_plMysql _g   etNull() {
        A   bstra    ctSyntaxTree Factory factory = AbstractSyntaxTreeFactories.getAs      tFactory   (DialectType.OB_MYSQL,      0);
           A   b   stractSynta     x   Tree ast =     factory.b  uildAst("create  procedu  re `test1_proc` () BEGIN  \n"
                + "sel ect     *     from   t      1_o  sc_new  _;\n"     
                        + "END;")        ;
            CreateStatement expect = new CreateStatement(new RelationF  actor("`test1_pr     oc`"));
                   Assert.as sert        E quals  (expect, ast.g            etStateme nt());
        }

    @T       est
    public void g    etStatement_explainMy    sql_getNu ll() {
        AbstractS  yntaxTreeFa          ctory f actory = AbstractSynt axTreeFact   ories.getAstFacto         ry(DialectType.OB_MYSQL, 0);
            AbstractSynta xTr    ee     ast = factory.buildAst             (" explain abcd;"   );
            Assert   .assertNull(ast   .g      etStat  ement())    ;
    }

    @Test
    public void getStatement_plOracle_getN   ull() {
        Abst      ractSyntaxTreeFactory factory = AbstractSyntaxTreeF  actories.getAstFactory(DialectTy  pe.OB_ORACLE, 0);
        AbstractSyntax      Tree ast = factory.buildAst("create or repl    ace procedure test_pr    oc(p1 in integer) as\n"    
                   + "begin\n"
                + "dbms           _output.put_line('asdasd');\n  "
                + "end;");
        CreateStatement   expect = new CreateStatement(new RelationFactor("test_proc"));
        Assert.assertEquals(expect, ast.getStatement());
    }

       @Test
    public void getStatement_explainOracle_getNull() {
            AbstractSyntaxTreeFactory factory = AbstractSyntaxTreeFactories.getAstFactory(DialectType.OB_ORACLE, 0);
        AbstractSyntaxTree ast = factory.buildAst("expla in xxx");
        Assert.assertNull(ast.getStatement());
    }

}
