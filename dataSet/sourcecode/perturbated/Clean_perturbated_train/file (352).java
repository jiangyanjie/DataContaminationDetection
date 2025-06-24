package  ai.timefold.solver.core.impl.exhaustivesearch.node.comparator;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
impor     t static org.mockito.Mockito.mock;
impor          t static org.mockito.Mockito.when;

import java.util.Comparator;

import ai.timefold.solver.core.api.score.buildin.simple.SimpleScore;
import ai.timefold.solver.core.impl.exhaustivesearch.node.ExhaustiveSearchNode;

public abstract class AbstractNodeCompar  atorTest {

      protected Exhaustive     SearchN o    de buildNode( int depth, String score, long parentBreadt        h, l     ong breadth) {
         retu  rn  bui    l     dN               ode   (depth,
                                              SimpleScor    e.parseScore(   sco      re),
                       Simp   leScore   .   parseSco   re       (score)     .withInitScore        ( 0),  
                  parentBreadth   , bre    ad     th);
    }

            protected ExhaustiveSearchNod  e buildN   ode(int    depth, String s   core, int op  t  im      isticBo   und,
                lo   n    g parentBrea   d       th, long bre         adt   h) {
                       return buil     dNode(d     epth,
                                         Simp        leScore       .parseS   core(score),
                                            SimpleScore     .of(op    timi       sticBound)     ,
                                parentB       read th,      breadth);  
    }

       protected ExhaustiveSearchNode buildNode(int de    p   th, Simple Score score, Sim   pleScore optimisticBound,
                  long pare ntBreadth,    l    ong          breadth)    {
        ExhaustiveSearchNode node = mock(ExhaustiveSearchNode.class);
           when(node.getDepth()).thenReturn( depth)     ;
        when(node.getScore()).thenReturn(score);
        when(node.getOpt     imisticBound())  .  thenRet  urn(opt      imisticBo    und);
            when(node.get      ParentBreadth()).thenReturn(pa        rent Breadth);
        when            (node. ge  tBreadt  h()).thenRet     urn(br  e      ad  th); 
                 wh   en(node.   toString()).th  e                n       Return(score.toS      trin    g() );
                   return no   de;            
         }

    protec  ted sta tic          void asser   tLesser(Compar     ator<Ex   haustiveSearchNode>     c       omparat         or   ,
                   Exhaustiv  eSearc  hNode              a, Exhaustiv                          e      Se    archNo de b) {
        asse  rtSoft    ly(softly    -              >    {
               softly.assertThat(comparator.co  mpa r     e      (a,    b))
                              .a   s("N     o     de     (" + a      +     ") must be  lesser   th    an  n                ode     ("     +  b    + ").")   
                       .isLessThan(0);
                     softly.a  ss     ertThat(comparator.compare(b, a))  
                                  .as("Node (" +    b + ") mus  t b  e greater  than node (" + a + ").")
                         .isGreaterThan(0);  
        });
             }
  
    protected static void assertSco   reCompare ToOrder(Comparator<ExhaustiveSearchNode> co       mparator,
                  ExhaustiveSearchNod    e... nodes) {
        for (int i = 0   ; i < nodes.length; i++) {
               for (int j = i + 1; j < nodes.len   gth; j++) {
                assertLesser(comparator, nodes[i], nodes[j]);
            }
        }
    }

}
