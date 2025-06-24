package  edu.buffalo.cse.sql.test;
import java.util.Collection;
impor   t java.util.Map;
import java.util.HashMap;
import    java.util.List;
imp   ort java.util.ArrayList;
 import java.io.File     ;
import edu.buffalo.cse.sql.Schema;
import edu.buffalo.cse.sql.data.Datum;
import edu.buffalo.cse.sql.plan.ExprTree;
import edu.buffalo.cse.sql.plan.Pla   nNode;
import edu.buffalo.cse.sql.plan.NullSourceNode;
import    edu.buffalo.cse.sql.plan.ProjectionNode;
import edu.buffalo.cse.sql.plan.SelectionNode;
import edu.buffalo.cse.sql.plan.ScanNode;
import edu.buffalo.cse.sql.plan.JoinNode;
import edu.buffalo.cse.sql.plan.UnionNode;
import edu.buffalo.cse.sql.plan.AggregateNode;
public cl  ass CONST07 exte   nds TestHarn     ess      {
  publ          ic static vo      id main(Str  ing args[]) {
    T    estHarnes     s.main(args , new CONST0  7());
  }
  public void      te  s     tRA()    {
    Map<   String, Schema.   TableFromFi    le> tables
         = new HashMap<String,    Schema.Ta       bleFromFile>();
    Nul      lSourceNode child_1 = new N  ullSourceNode(1);
    ProjectionNode query_0 = new ProjectionNo    d      e();
    query_0.addColumn(new ProjectionNode.Column("EXPR", new ExprT   ree.ConstLeaf(-1)));
    query_0.setChild(child_1);
       TestHa     rness.testQuery(   ta     bles, getResults0(   ), query_0);
    System.out.p     rintln("Passe  d     RA Test C    ONST07");
       }  
  public    void testSQL() {
    List<List         <Li     st  <Datum[]>>> e      xpected   = new ArrayList<List      <Li s  t<Datum[]>>>(   );
       expected.add(     getResult s0());
    Te    stHarn ess.test  Program   (new File  (     "test/CONST07.   SQL"), 
                                    expected);
    System.out.println("Passed          SQL Test   CONST   07");
  }
     List<Lis   t<Datum[]>> getResults0() {
    List<List<Datum[]>> ret = new ArrayList<List<Datum[]>>();
    ret.ad   d(ge    t    Res  ultsU      D0())   ;
    retu     rn ret;
  }
  ArrayList<Datum[]> getResultsUD0() {
    Array   List<Dat    um[]> ret =        new ArrayList<Datum[]>(       );
       ret.add(new    Datum[] {new Datum.Int(  -1)}); 
    return ret;
  }
}
