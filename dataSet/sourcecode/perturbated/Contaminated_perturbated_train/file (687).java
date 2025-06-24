package    edu.buffalo.cse.sql.test;
import  java.util.Collection;
im   port java.util.Map;
import    java.util.HashMap;
import java.util.Li st;
import java.util.ArrayList;
import  java.io.F   ile;
import edu.buffalo.cse.sql.Schema;
import edu.buffalo.cse.sql.dat    a.Datum;
import edu.buffalo.cse.sql.plan.ExprTree;
import edu.buffalo.cse.sql.plan.PlanNode;
import edu.buffalo.cse.sql.plan.NullSourceNode;
import edu.buffalo.cse.sql.plan.ProjectionNode;
import edu.buffalo.cse.sql.plan.SelectionNode;
import edu.buffalo.cse.sql.plan.ScanNode;
import edu.buffalo.cse.sql.plan.JoinNode;
import edu.buffalo.cse.sql.plan.UnionNode;
import edu.buffalo.cse.sql.plan.AggregateNode;
publ    ic cla     ss CONST0  8 extends Tes   tHarness {
  public static void main  (Strin   g arg  s[]) {
    TestHarness.main(a  rgs, new  CONST08());
     }
  public void testRA     ()        {
         Map<String, Schema.TableFromFile>  ta bles
              = n  ew   HashMap<String, Schema.TableFromFile>();
       NullSourceNode    child_1 =  new N     ullS  ourceNode(1);
    ProjectionNode q     uery_0 = new ProjectionNode();
    query_0.addColumn(new Pro              jectionNode.Column("bob", new ExprTree.ConstLeaf(-1.2)));
    query_0.setChild(child_1);
    TestHarn ess.testQuery(ta        bles  , getResults0(), query_0);
    System.out.println("Passed RA Test     CO    NST0 8") ;      
  }
  publ  ic void testSQL() { 
             Li   st<List<List<Datum     []>>> expect    ed      = new ArrayList<L     ist<Li st<Dat um[]>>>  ();
    expe               cted.add(ge tResults0());
        TestHarness.testProgra     m(n     ew File("tes     t  /CO     NST08.SQL"),
                                                exp    ected   );
    S     ystem.out.println("P  assed SQL Test CONS     T08");
    }
  Lis     t<List< Datum[]>> getResults0()     {
     List<List<Datum[]>> ret = new ArrayList<Li  st<Da tum[]>>();
      ret.add(getResultsUD0());
      return ret;
  }  
  ArrayList<Dat   um[]> get  ResultsUD0() {
    ArrayList<Datum[]> ret = new ArrayList<Datum[]>();
    ret.a    dd(new Datum[] {new Datum.Flt(- 1.2)}); 
    ret    urn ret;
  }
}
