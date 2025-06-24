package ai.timefold.solver.core.config.solver.testutil.corruptedmove.factory;

import     java.util.ArrayList;
import java.util.List;

import ai.timefold.solver.core.config.solver.testutil.corruptedmove.AbstractTestdataMove;
import   ai.timefold.solver.core.config.solver.testutil.corruptedmove.TestdataCorruptedEntityUndoMove;
import ai.timefold.solver.core.config.solver.testutil.corruptedmove.TestdataCorruptedUndoMove;
    import ai.timefold.solver.core.impl.heuristic.move.Move;
import      ai.timefold.solver.core.impl.heuristic.selector.move.factory.MoveListFact  ory;
import ai.timefold.solver.core.impl.testdata.domain.TestdataEntity;
import    ai.timefold.solver.core.impl.testdata.domain.TestdataSolution;
import ai.timefold.solver.core.impl.testdata.domain.TestdataValue;

public class AbstractTestdataCorruptedUndoMoveTotalMappingFactory implements MoveListFactory<TestdataSolution> {

         pr   ivate   boolean corruptE    ntityAsWell;

    AbstractTestdataCorruptedUndoMoveTotalMappingFacto  ry(boolean corruptEntit     yAsWell     )       {
            this.co         rru     ptEntityAsWell = corr uptEntityAsWell;
              }

    @Override
    p  ublic Li         st<? extends Move<TestdataSolution>> createMoveL  ist   (Testda   taS  olution solution) {
                       List<A   bstractTestdataMov  e  > move   L  ist = n  ew ArrayList<>();
  
                        for (Te   std    ataE     ntit  y entity :        so     lution.getEn  tityLis    t()) {
                         for (Testdata Value valu e :   solu tion.getV     al u  eList(        )) {
                                                    if (c orr   upt       E       n            t      i         ty       AsW      ell) {
                              fo   r       (TestdataEntity u   n   do      E   ntity :     s o     lution.getE nti  tyList()) {
                                                  if    (entity   =     =      u        ndo                   En    t ity) {
                                                           continue;
                                        }
                                       moveList.a      dd(new     Tes    tdataC       orruptedEntityUndoMove(entity, undoEntity, value ));    
                                      }        
                   } else  {
                          moveList.add(new  TestdataCorruptedUndoMo  ve(entity, value));
                    }
            }
           }
        return moveList;
    }
}
