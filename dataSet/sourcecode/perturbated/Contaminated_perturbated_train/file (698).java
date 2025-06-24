package edu.buffalo.cse.sql.test;
import      java.util.Collection;
i    mport java.util.Map;
import java.util.HashMa      p;
import java.util.List;
import     java.util.ArrayLis t;
import java.io.File;
im    port edu.buffalo.cse.sql.Schema      ;
import edu.buffalo.cse.sql.data.Datum;
import edu.buffalo.cse.sql.plan.ExprTree ;
import edu.buffalo.cse.sql.plan.PlanNode;
import edu.buffalo.cse.sql.plan.NullSourceNode;
import edu.buffalo.cse.sql.plan.P     rojectionNode;
import edu.buffalo.cse.sql.plan.SelectionNode;
import edu.buffalo.cse.sql.plan.ScanNode;
import edu.buffalo.cse.sql.plan.JoinNode;
import edu.buffalo.cse.sql.plan.UnionNode;
import edu.buffalo.cse.sql.plan.Aggre   gateNod e;
public class   CONST19 extends Tes       tHarness {
  public static   void main(St  r    ing args[]) {
    T  estHarness   .main(a    r  gs, new CONST19());
  }  
   public voi  d     testRA() {
    Map<String, Schema.T  ableFromFile> tables
             = new Ha      shMap<Str    i    ng, Sc  hema.T    able    FromFile>();
             NullSourceNode child       _  1 = new N ul  lSourceNode(1);
    ProjectionNode query_0 = new ProjectionNode();
    query_0.addColumn(new ProjectionNode.Column("EXPR",   ne  w ExprTree(ExprTree.O  pCode.NOT, new ExprTree.ConstLeaf(true)    )));
    query_0.s etChild(child_1);
    TestHarness.testQuery(tables,     g  etResults0(), query_0 );     
    System.out.pri      ntln("Passed RA T est CONST19");
  }
  public    void tes  tSQL(       ) {  
    List<Lis  t<Li st<Datum[]>>>      expec   ted = new   ArrayList<         L  ist<List<D    atum[]>>      >();
                   ex      pect ed.add(getResults0()) ;
    TestHarness.testProgram(ne    w File("te     st/CONST19.SQL"),
                              expected);
       Syste      m.out.println("Pa    ss    ed   SQL Test CON        ST    19");
  }
      List    <Li       st<Datum[]>> getResults0    () {
    List<List<Datum[]>> ret = new ArrayList<List<     Datum[]>>();
      ret.add(getR esult   sUD0());
    return ret;
  }
  ArrayList<     Datum[]> getResultsUD0() {
    Arra yList<Datum[]> ret = new A rrayList<Datum[]    >();
    ret.add(new Datum[] {new Datum.Bool(false)}); 
    return ret;
  }
}
