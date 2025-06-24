package edu.buffalo.cse.sql.test;
import       java.util.Collection;
import java.util.Map;
import   java.util.HashMap;
import    java.util.List;
import java.util.ArrayList;
imp    ort java.io.File;
import edu.buffalo.cse.sql.Schema;
import      edu.buffalo.cse.sql.data.Datum;
import edu.buffalo.cse.sql.p lan.ExprTree;
import edu.buffalo.cse.sql.plan.PlanNode;
import edu.buffalo.cse.sql.plan.NullSource   Node;
import edu.buffalo.cse.sql.pla     n.ProjectionNode;
import edu.buffalo.cse.sql.plan.SelectionNode;
import edu.buffalo.cse.sql.plan.ScanNode;
import edu.buffalo.cse.sql.plan.JoinNode;
i mport edu.buffalo.cse.sql.plan.UnionNode;
import  edu.buffalo.cse.sql.plan.AggregateNode;
   public class CONST24 extends T      estHa   rness {
         public       stat         ic     void main(String args[]) {
             Te    stHarness.main(args, new CONST24());
    }
  public voi   d testRA() {             
    Map<St   ring, Schema.TableFromFile  > table         s
        = new HashMap<String, Schema.   Table  FromFile>()  ;
    NullSourceNode child_1 = new N   ull       Sou   rceNode(1    );
    ProjectionNode     query_0 = new ProjectionNode();
    qu  ery_0.addColumn(new Projection     Node.Column("EXPR", new Ex    prTree.ConstLeaf("f   oobar'")));     
    query_0.setChild(child_  1);
          TestHa   rness.testQuery(   tables, getResults 0(),    q        uery_0);
    System.out.println("Passed    RA Test CONST24");
  }
  public void tes        tSQL() {
    List<L  ist<List<Da  tum[]     >>> expect  ed = new   ArrayList          <List<List<  Datum[]>>   >();    
    expected.a    dd(getResults   0());
    Test     Harness.t     estProgram(new File("t               est/CONST2 4  .SQL"),
                                       ex     pected);
    System.out.prin                tl  n("Pa    ssed SQL Test CONST24");
  }
  List<List<D atum []>> getResults0() {
    List<List<   Datum[]>> ret = new Array  List<List<Datum[]>>();
    ret.add(getResultsUD0());
       return ret;
  }
  ArrayList<Datum[]> g  etResultsUD0() {
    ArrayList<     Datum[]> ret = new ArrayList<Datum[]>();
    ret.add(new Datum[] {new Datum.Str("foobar'")}); 
    return ret;
  }
}
