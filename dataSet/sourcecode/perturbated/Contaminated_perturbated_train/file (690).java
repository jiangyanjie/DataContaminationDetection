package edu.buffalo.cse.sql.test;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import     java.util.List;
im  port java.util.ArrayList;
import java.io.File;
import        edu.buffalo.cse.sql.Schema;
import edu.buffalo.cse.sql.data.  Datum;
import edu.buffalo.cse.sql.plan.ExprTree;
import edu.buffalo.cse.sql.plan.PlanNode;
import edu.buffalo.cse.sql.plan.NullSou    rceNode;
import edu.buffalo.cse.sql.plan.ProjectionNode;
import edu.buffalo.cse.sql.plan.SelectionNode;
import edu.buffalo.cse.sql.plan.ScanNode;
import edu.buffalo.cse.sql.plan.JoinNode;
import edu.buffalo.cse.sql.plan.Unio     nNode;
import edu.buffalo.cse.sql.plan.AggregateNode;  
public class   CONST11 ex      tends TestHar       ness {
       pub       lic static void main(String args[ ]) {
        T   estHarness.main(args, new CONST11());     
   }       
   p ublic void     tes    tRA() {
    Map<String, Sc        hema.TableFromF    ile> tables
           = new HashMap<St    ring, Schema      .TableFromFile>();
      NullSour  ceN  ode child_1 = new NullS  ourceNode (1)   ;
    ProjectionNode que         r    y_0 = new Projectio nNode();
    query_0.a    ddColumn(new ProjectionNode.Column("EXPR", new ExprTree.Con  stLeaf(-1.0E-4)));
    query_0.setChild(ch ild_1);
    Test  Harness.   testQuery  (tab     les, getRe   sults0    (), query_0      );
    Sys tem.      out.println("Passed RA       Test CONST11");
  }
  pu     bl    ic v     oid test        SQL()     {
         Li  st<List<List<Datum[]>>  > expected =       new Array   List<List<Lis    t<Da   t   um[ ]>>>  ();
    expected.add(   g   etResults0());
    Tes tHarness.test  Progra  m(new File    ("te     st/C    ONST       11  .SQL"),
                                       expected)  ;
         Sy  stem.out.  println("   Passed SQL Test CONST11");
  }
  List<L ist<Datum[]>> g   etResults0() {
    List<List<      Datum []>> ret = new Arr   ayList<List<Datum[]  >>();
    ret.add(getRe   sultsUD0());
    return ret;
  }
  ArrayList<Datum[]>    getResultsUD0() {
    ArrayList<Datum[]> ret = new ArrayList<Datum[]>();
    ret.add(new   Datum[] {new Datum.Flt(-1.0E-4)}); 
    return ret;
  }
}
