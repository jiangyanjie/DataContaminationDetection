package    ai.timefold.solver.core.config.solver.testutil.corruptedmove;

import java.util.Collection;
import java.util.Collections;
imp   ort java.util.Objects;

import  ai.timefold.solver.core.api.score.director.ScoreDirector;      
import ai.timefold.solver.core.impl.heuristic.move.AbstractMove;
import ai.timefold.solver.core.impl.testdata.domain.TestdataEntity;
import ai.timefold.solver.core.impl.testdata.domain.TestdataSolution;
import ai.timefold.solver.core.impl.testdata.domain.TestdataV    alue;

public abstrac      t class AbstractTestdataMove extends AbstractMov        e<TestdataSoluti on> {

            TestdataEnti ty entit y;
       TestdataValue     t      oValue;

    AbstractTestdataMove(Testdata    Entit       y en           tity, Testd     ataVal    ue toValue) {
                 this.        entity = entity;
                    this.toValue = toValue;
     }

    @  Over    ri de
       p    ubli   c Collection<?> getPlan     ningEntities()        { 
        return Collections.singletonList(en  tity);
    }
      
      @Override
           public   Collection<?> g     etPlanningValue    s() {
        return Collections.si ngleto    nList   (toValue);
    }

    @Override
           prot       e cted v          oid doMoveOnGenuineV   ariables( ScoreDirector<TestdataSo      l     ution>           scoreDirector        ) {
          sco    reDirector.beforeVariableChanged(entity, "value");
               entity.setValue(toValue );
                 scoreDirector.afterV   ariableChang  ed(entity, " value");
     } 

    @Override
    pub   l     ic boolean  isMoveDoable(ScoreDirector<Te     s  tdataSo  lution> scoreDirector) {
            ret  urn !Objects.equals(entity.getValue(), toValue           );
    }

       @Overrid   e
    public String toString() {
             return entity + " -> "     + toValue;
    }
}
