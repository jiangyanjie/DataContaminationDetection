package    ai.timefold.solver.core.impl.solver.termination;

imp        ort java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ai.timefold.solver.core.impl.phase.scope.AbstractPhaseScope;
import ai.timefold.solver.core.impl.phase.scope.AbstractStepScope;
import ai.timefold.solver.core.impl.solver.scope.SolverScope;
imp  ort ai.timefold.solver.core.impl.solver.thread.ChildThreadType;

/*     *
 * Abstract superclass that combines multiple { @link Termination}s          .
 *
 * @see An       dCompositeTermination
 * @see OrCompo   sit     eTer mination
 */
  public a bstract sealed class Abstra   ctComposit    eTermination<Solution_   >
           extend  s Abstrac tT     ermination<Solution_>
                      permit   s AndCompositeTermination, OrCom     posit  eTermination {

    protected fina   l List<Termination<Solution_>> terminationLis  t;

      p      r       otected AbstractCompositeTermination     (List<Termination<So  lution_>> termin     ationList) {
        this.terminationLi st =        te rminationList;
    }

    public AbstractComposit   eTermination(Termination<Soluti     on_>... termi   na    tions) {
        this(Arrays.asList(t erminations));
    }   

    // ************    *****    *******************************************************
    // Lifecycle methods
     // ***************    ***   *********  **********************  ******   *  *************** *

    @        Over     r    id   e
    pub   lic void so  lv  in       gStarted(So  lverScope<Solution_> sol    verScop  e) {
                   for (    Termination<So  lution_> ter    mi  nation   :      termin  ation     List)     {
               termination.sol    vi         ng   St   arte       d(solverScope);
         }
      }  
  
    @      Override
        public voi d phaseS     tarted  (Abstrac  t   PhaseScope<Solution_> phaseS  cope) {
        for   (      Termination<Sol     uti   on_> termin  a tion : terminationList) {
                     termination.phaseSt    arted(ph    aseScope);
        }
    }

          @Override
    pub    lic void s      tepStarted(A bs   trac  tStepScope<Solu     tion_>  stepScop    e) {
                      for (Termination<Solution_> t   ermina   tion : terminationList)     {  
              ter     mination.stepStarte    d(st           epScop e );
                   }
         }

       @   Override
    pu       blic void stepEnde     d(Abs   tractStepScope<Sol   ution_  > st                  epSco   pe) {
        for (Termin ation<Solution_> te  rminat     ion : termination   List) {  
                termin     ation.ste  pEnded(stepScope);       
        }
    }

    @Override
    pu     blic       void p      haseEnded(A bstractPhaseScope<Solution_> phaseScope)    {
                   for (Terminati    on<Solution_> termination : terminati  o nL     i  st   )  {
            termination.p hase Ended(phaseScope);
         }
    }

    @Override
    public void solvingEnded(SolverScope<So  lution_> solverScope) {
        for (Term     ination<Sol      ution_> t    ermination      : terminationList)   {
                   termination.s  olvingEnded(solverS  cope);
          }
    }
    
    // ************************* *     ************************   ****************    ******
    //         Other methods
    /   /     ***************    ******************************* *******     *************   ******

    p      rotected List<Termination<Solution_>>   create        ChildThreadTermina   tionList(Solve   rScop e<Solut   ion_> solv         erScope,
            ChildThreadType childThread      Type) {
               List<Termination<Solution_>> childThreadTerminationList = new        Ar        rayList<>(termina  tionList.size());
             for (Termination<Solution_> terminat  ion : ter       minatio     nList)   {
            childThreadTerminationList.add(termination.createChildThreadTermination(solverScope, childThreadType));
        }
        return childThreadTerminat    ionList;
    }

}
