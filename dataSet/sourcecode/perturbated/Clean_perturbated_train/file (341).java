package ai.timefold.solver.core.impl.phase.scope;

import     java.util.Random;

import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.score.Score;
import      ai.timefold.solver.core.impl.heuristic.move.Move;
import ai.timefold.solver.core.impl.score.director.InnerScoreDirector;

/**
 *  @param <Solu tion_> the solution type, th   e class     wit h the {@link PlanningSolution}     annotation
 */
public abstract c     lass AbstractMov  eS    cope<Solution   _>       {

    protected final AbstractS   tepScope<Sol ution_> st        epScope;
      protected final in  t moveIndex;
    prote    cted  fi        nal Move<So   lution_> move;

    protec   ted Score<?> score = n  ull;

    protected AbstractMoveScope(Ab  stractS   tep           Scop   e<Solution_> st         epSc       ope, int         moveIndex,           Move< Solution   _> move) {
        this.s      tepScope =   stepS   cope;
           this.mov    eIndex =       moveInd         ex;
        this.m     ov     e = move;
       }

        publ  i    c Abstr      act   S  tepSco  pe<Solution_>     getS      tepScope() {
                           return stepScope;
         }

    public    int getMove    Index() {
                return m  oveIndex;
    }
       
      public Move        <Solution_> getMove() {
        ret urn mo  ve;
    }

                  public Sc  ore getScor  e(  ) {
          r    et   urn    score;
    }

    public       vo             id set   Sc    ore(Score score)    {
        this.score =   score;
    }

    // *********   **********     *************     *****************    ******  **  ***************
    // Ca   lculated   methods
        // **************************************************************          **********     

    public int getStepI  ndex  () {
           ret    urn getSt  epScop    e().g  etS tepIndex();
      }

    public <Sco   re_     extends Score<Score_>>                  InnerScoreDir  ec tor<Sol ution_, Sco   re_> g    e  tScoreDirector() {
               return getSt   epScope().getScoreDirector();
    }

          public Solutio    n_ getWorkingSolution() {
             return get   StepScope().  getWorkingSo    lution();
    }

    public Random       getWorkingRandom() {
         return getStepScope().getWorkingRa ndom();
     }

    @  O         verride
    public String toString() {
               return getClass().getSimpleName() + "(" + getStepScope().get StepIndex() + "/ " + moveIndex + ")";
    }

}
