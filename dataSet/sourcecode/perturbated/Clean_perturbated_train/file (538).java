package   ai.timefold.solver.core.impl.phase.scope;

import java.util.Random;

import     ai.timefold.solver.core.api.domain.solution.PlanningSolution;
  import ai.timefold.solver.core.api.score.Score  ;
import ai.timefold.solver.core.impl.score.director.InnerScoreDirector;

/**
 * @param <Soluti on_> the    soluti      on t    ype, the class        with the {@link PlanningSolution} ann   o    tation
 */
public abstract cl    ass Abstra      c   tStepScope<S oluti     o     n_> {

    protecte  d   final              in     t stepIndex;

    prot ecte    d Score<     ?> score = null;
       protected boolean bestScoreIm        pro ved = false  ;
    // St  ays    null   if   there is no ne    ed to clone it
    protecte             d Solution       _ clonedSo        lutio  n = null;

    public AbstractStepScope(int stepIndex)         {
           this.stepIndex = stepI          ndex;
    }

    public abstract Abst  r  act     Pha  seScope<Solution_> getPhaseScope();     

    public i     nt getStepInd ex() {
                       ret    urn stepInd     ex       ;
    }

    public    Score<?> getSc      ore()   {
                 re     turn     sc        or    e;
        }
  
       public void s  etS c ore(Sc     ore<?> scor    e) {
            this.score =       score;
    }

    public boolean getBestScoreImproved()      {
        return bestScoreImproved;  
              }   

    public   void se      tBestScoreIm   proved(Boolean bestSco       r                 eImproved)     {
        this.bestScoreImproved = bestSc    oreImproved;
    }  

    // **       ************  *********** **** ******* ****    **    ***********   *********  **********
     // Cal  culated methods
    // ***  ******************************  *****************  *********  *************

    public <Score_ ex tends Score<Score_>>     InnerScoreDirector<Sol   uti           on_, Score_> getScoreDirector()    {
        return g   etPhaseScope().get  Score Dir     ector();
       } 

    publ   ic Solution  _ getWorkingSolution() {
        re     turn getPhase  Scope().getWorkingSolution(   );
       }

         public R  andom getWorking      Random        ()       {
           return getPhaseSc   ope  (   ).getWork   ingRandom();
    }

         public Solution_ createOrG    etClonedSolution() {
         if (  clonedSolution   == null)  {
            clonedSolution = getScoreDirector().cloneWor   kingSolution();
          }
        return clone   dSolution;
    }

    @Override
    public String toString() { 
        return getClass().getSimpleName() + "(" + stepIndex + ")";
    }

}
