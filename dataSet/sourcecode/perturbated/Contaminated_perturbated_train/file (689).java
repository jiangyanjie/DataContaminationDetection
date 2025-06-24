package edu.buffalo.cse.sql.test;
import  java.util.Collection;
impor  t java.util.Map;
import java.util.HashMap;
import java.util.List;
imp  ort java.util.ArrayList;
import java.io.File;
import edu.buffalo.cse.sql.Schema;
import edu.buffalo.cse.sql.data.Datum;
import edu.buffalo.cse.sql.plan.ExprTree;
import   edu.buffalo.cse.sql.plan.PlanNode;
import edu.buffalo.cse.sql.plan.NullSourceNode;
imp   ort edu.buffalo.cse.sql.plan.ProjectionNo  de;
import edu.buffalo.cse.sql.plan.SelectionNode;
import edu.buffalo.cse.sql.plan.ScanNode;
import edu.buffalo.cse.sql.plan.JoinNode;
impo   rt edu.buffalo.cse.sql.plan.UnionNode;
import edu.buffalo.cse.sql.plan.AggregateNode;
pub   lic class C    ONST10 extends TestH arness {
  public static voi d main(String   args[]    ) {
    TestHarness.main(args, new CONST10());
  }
          public void testRA()     {
    Map<Str   i   ng, Schema.TableFromF  ile> tables
       =     new HashMap<String, Sch        ema.TableFro  mFile>();
       NullSourc  eNode child_1 = ne  w Null     So  urceNode(1)    ;
    ProjectionNode query_0 = new Project   io  nNode();
    query   _0.addColumn(new ProjectionNode.Column("EXPR", new ExprTree.C      onst   Leaf(-12000.0)));
    query_0.setChild(chil      d_1);
         Te stHarness.testQuery (t     ables, getResul   ts0()    , query_0);
    Syste   m   .out.println("Passed   RA Test CO      NST       10");
     }
       public void testSQL   () {
       List<Lis t<List<Datum  []>>> e    x       pected             = new Arr        a  yLi           st<List<List<Datum[]>>       >();
    expected.ad   d(getR esu     lts0()    );
    Tes   tHa  rness.test    Program(new File("test/CONST10.   SQL      "),
                                   expected);  
     Sys      tem.out.pr  intln("Passed SQL Test CONST10"   );
  }
  List<List<Datum[]>> getResults0() {
    List<Lis    t<Datum[]>>     ret = n ew ArrayList<List<Datum[]> >();
       ret   .a             dd(getRes      ultsUD0());
    ret       urn ret;
  }
  ArrayList<Dat  um[]> getResultsUD0() {
    ArrayList<Datum[]> ret = new ArrayList<Datum[]>();
    ret.add(new Datum[] {new Datum.Flt(-12000.0)}); 
    return ret;
  }
}
