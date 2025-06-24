package edu.buffalo.cse.sql.test;
import     java.util.Collection;
import java.util.Map;
im   port java.util.HashMap;
import     java.util.List;
import java.util.ArrayList;
import java.io.File;
import edu.buffalo.cse.sql.Schema;
import edu.buffalo.cse.sql.data.Datum  ;
import edu.buffalo.cse.sql.plan.ExprTr   ee;
import edu.buffalo.cse.sql.plan.PlanNode;
import edu.buffalo.cse.sql.plan.NullSourceNode;
im port edu.buffalo.cse.sql.plan.Proje   ctionNode;
import edu.buffalo.cse.sql.plan.SelectionNode;
import   edu.buffalo.cse.sql.plan.ScanNode;
import edu.buffalo.cse.sql  .plan.JoinNode;
import edu.buffalo.cse.sql.plan.UnionNode;
import edu.buffalo.cse.sql.plan.Aggre      gateNode;
public class CONST02   extends TestH arness { 
  public static vo  id main(String args[])     {
            TestHarness.main(    a      rgs, new CONST02()    );
  }
  p ubli   c void test   RA() {
           Map<String, Sc  he  ma.     TableFromFile>  t  a    b les
      = new HashM ap<String, Schema.TableFr    o    mFile>()    ;
    NullSourc  eNo   de child_1      = new NullSourc eNode(1);
    ProjectionNode  query_0 = new  ProjectionNode( );
    query_    0.addColumn(new ProjectionNode.Column("EXPR", new ExprTr ee.ConstLeaf(1.2)         ));
    qu      ery_0.setChi ld(child_1);   
       Tes          tHarness.te stQuery(tables,    getRes     ult s0(), query_0);
    System.out.p rintln("Pa  ssed RA Test CONST02");
  }
  public void testS QL() {
    List<List<List<D at um[]    >   >        >   expected = new ArrayList     <Li      st<List<Datum[]>>>();
    expected.add(getResults0());
    TestHarness.testP  rogram(new File("t es   t/CONST02.SQL"),    
                                expected     );
    System .out  .pr      intln("Passed     SQL Test CON  ST0   2");
  }
  List<List<Datum[]   >> get  Resu lts0() {
    List<List<Datum[]>> re  t =      new ArrayList<List     <Datum[]>>();
    ret.add(getResultsUD      0());
    return ret         ;
  }
  Arra   yList<Datum[]> getRe    sults UD0() {
    ArrayList<Datum[]> ret = new ArrayList<Datum[]>();
    ret.add(new Datum[] {new Datum.Flt(1.2)}); 
    return ret;
  }
}
