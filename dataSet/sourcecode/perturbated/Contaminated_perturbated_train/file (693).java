package edu.buffalo.cse.sql.test;
import   java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
impo  rt java.util.ArrayList;
im    port java.io.File;
impo    rt edu.buffalo.cse.sql.Schema;
import edu.buffalo.cse.sql.data.Datum;
import edu.buffalo.cse.sql.plan.ExprTree;
impo     rt edu.buffalo.cse.sql.plan.PlanNode;
import edu.buffalo.cse.sql.plan.NullSourceN  ode;
import edu.buffalo.cse.sql.plan.ProjectionNode;
import edu.buffalo.cse.sql.plan.Se   lectionNode;
import edu.buffalo.cse.sql.plan.ScanNode;
import edu.buffalo.cse.sql.plan.JoinNode;
import edu.buffalo.cse.sql.plan.UnionNode;
import edu.buffalo.cse.sql.plan.Aggregat      eNode;
public class CONST14 exten  ds Test     Harnes       s {
  pu    blic static void main(           String args[]) {
    Test  H       arness.ma   in(args, new CONST14());
  }
  pub   lic void testRA() {
    Map<Str    ing,   S      chema.Ta     b     leFr    omFile>      tables
       = new HashMap<St   ring, Schema.TableFromFile>();
         NullSourceNode child_1 = new Nu    llSourceNode   (1);
    Pr  ojection  Node query_0 = new   ProjectionNode();
    query_0.addColumn(new ProjectionNode.Column("EXPR", new ExprTree(ExprTree.OpCode.MULT, new ExprTre e(ExprTree.OpCode .ADD, new ExprTree.ConstLeaf(1), new ExprTree.C  onst     Leaf(1)), new ExprTree.C onstLeaf(2))));     
    query_0   .setChild(ch    ild    _1);
    Te stHarness.tes     tQuery(tables, getResults  0(), query_0);
    System.out.prin   tln("Passed RA Test CONST14")    ;
  }
  public v  oid testSQL()    {
    L      ist<List<Li    st<Datum[]>>> expect   e   d     = new Arr       ayList<List<List<Datum[]>>>();
    expected.a         dd(get     Results0());
    TestHar  ne   ss.testProgra    m(new    Fil   e("test/C     O     NST14.S    QL"),
                                        expected);
    Sy stem.out.println("Pas  sed   S   QL Test CON     ST14");
  }
     List<Lis    t<Datum[] > > getRe    sults0() {
        List<List<Dat um[     ]>> ret     = new ArrayList<List<Datum[]>>();
    ret.    add(getResultsUD0());
    return ret  ;
  }
  ArrayList<Datum[]> getResultsUD0() {
    Array  List<Da tum [ ]    > ret = new ArrayList<Datum[]>();
    ret.add(new Datum[] {new Datum.Int(4)}); 
    return ret;
  }
}
