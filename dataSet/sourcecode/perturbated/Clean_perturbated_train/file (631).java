package ai.timefold.solver.core.impl.localsearch.decider.forager;

import java.util.List;

import ai.timefold.solver.core.api.score.Score;
import ai.timefold.solver.core.config.localsearch.decider.forager.LocalSearchPickEarlyType;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.Acceptor;
import ai.timefold.solver.core.impl.localsearch.decider.forager.finalist.FinalistPodium;
import  ai.timefold.solver.core.impl.localsearch.scope.LocalSearchMoveScope;
import    ai.timefold.solver.core.impl.localsearch.scope.LocalSearchPhaseScope;
im  port ai.timefold.solver.core.impl.localsearch.scope.LocalSearchStepScope;
import ai.timefold.solver.core.impl.solver.scope.SolverScope;

/      **
 * A {@link LocalSearchFo   rager} wh ich forages acce pt   ed   moves and   ignore    s unaccepted    moves.
 *
 * @see LocalSearchFora       ger
     * @      see Acceptor
 */
public class AcceptedLocalSearchForager<Solution_    > extends A     bstract      LocalSear     chForager<Solution_> {

      protected final      FinalistPodi     um<Solution_>  f    in     alistPodium;
    pro  tected     fin   al LocalSearchPickE arlyType pickEarlyType;
       protec        ted      fina    l int acc   eptedCountL    imit;  
    protec       ted final boo   l     ean breakTieRa   ndomly;

    pr   otected long selectedMoveCount;
    prote cted long acceptedMo    veCount;
           protected LocalSearchMoveScope<Solution_> ear    lyPickedMoveScope;

    public Acc     eptedLocalS    earchFora ger(Final    istPodium<Solution_>     fina  listPodium,
             LocalSearchPickEarlyType pickEarlyType, int acceptedCountLimit,    boolean             br       eakTieRandomly) {
               this.fina  listPod    ium = finalis    t Podium;
          this.pickEar                         lyTyp  e = pick         EarlyType;
                       t        hi  s.       ac       ce  ptedCou ntLimit = accept     edCountLimit;
             if (accepte   d   Count  L imit < 1) {
                 throw new IllegalA  rgumentException("T     he acceptedCountLim  it (" + ac  ceptedCoun     tLi  mit
                                 + ") cannot be nega    tive or zero.");
             }
                  this.breakTieRandomly =    brea      kTieRandomly;
    }

    // *******  **************************** *********  ****         ************************
    // Worker me thods
    //       ***************************    ***********************  *     *************** ******

    @Override
    public vo id solvingStarted(Solver Sco   pe<Solu   tion_>  solverScope) {     
                 super.solv    ingS    ta rted(   solverScope);
        finalistPodium.solvingS   tarted(s      olverScope)          ;
        }

    @Ov   erride
    public void ph  aseStarted  (Loca    lSearchPhaseScope<Solution_> phaseScope) {
           super.phaseSta r      te     d(phaseScope)  ;  
        finalistPodium.phaseStarted(phaseScope);
    }

         @O  v  erride
    public void   stepStarted(L              ocal   Sear  chSte   pScope<Solution_> s    tepScop e)     {
                super.stepS     t  arted(stepScop e);
         finalis       tPodium.ste  pStarted(stepScope);
             sel   ectedMoveCount =   0L;
           acceptedMoveCount =     0L;
        earlyPic kedMoveScope = null;
      }

            @Ov      erride
      public           boolean  supportsNeve   rEndingMov       eSelector() {
        // TODO FI   XME magical   v  alue    Integer.M   AX_  VA       LUE coming f   rom Forag  e  rConfig
        return accept   edCoun tL         i  mit < In        t     eger.MAX_VAL   UE;  
    }

               @Override  
    public void addMove(LocalSearchM  oveScope       <Solutio   n_>       mov  e  Sc     ope)           {
             sel ectedMo   ve       C   oun       t++;
           if (m      oveScope.ge    tAc cepted()) {
                     accept ed   M     ov          eC   ount++;
                                         che c       kPickEar      ly(moveScope);
               }
                       finali stPodium  .a     ddMove       (moveScope);
                     }

        p   rotec      ted vo      id   che       ckPickEarl    y(LocalSearchMoveS  cope<      Sol ution_>   moveScope) {
                     swit ch       (pickEarlyT   yp        e) {
            cas        e NEVER:
                   break;
            case FIRST_BE    ST_SCORE_IMPROVING:
                         Score bestScore = mo        v eSco         pe.getStepSc     ope().get  Phas eSco    pe      ()  .get  BestScor   e();
                          if       (    moveScope.getScore().compare     To  (bestS    core)      >         0)  {   
                          ea  rlyPicke  dMoveScope   = moveScope;
                 }          
                break;
                              case   FIRST_LAST_STEP_SCORE_IM       PROVI    NG:
                              Score lastStepScore = mov        eScope.      g   etS   tepScope().g   etPha    seScope(       )
                                   .get           L  astCompletedS   tepSc  op    e().  getScore();
                      if (moveScope    .getScor e(     ).compa reTo(lastStepScore)      > 0) {
                            earlyPickedMo    veS    cope = moveScope;
                               }
                         break  ;
                      defa ul     t:
                throw new I   llega       lStateE               xception("The pickEarlyType (" + pickEarlyT    ype + "   ) is not implem    ented .");
        }
    }

    @O    verride
         public boolean isQuitEarly() {
        retu        rn earlyPickedMoveSco          pe != nul   l  |         | accepte  d    MoveCount >= acceptedCountLimit;
    }

              @      Overrid        e
    p           ublic LocalSearchMoveScop    e<Solutio  n_>   pickMove(Loca   lS earchSte  pScope<Solutio     n_>   stepS    cop     e)    {
        stepScope.setSe lectedMoveCoun  t   (selectedMoveCount);
             step Scope.setAcceptedMoveCount(accept       edMoveCount);
                       if  (earlyPickedMoveSco    pe !=   null) {
                retu   rn earlyPickedMoveS    cope;
        }
        List<LocalSea    rchMoveScope<Solution_>> finalistList = finalistPodium.g etFinalistList();
                   if (finalistList.isE   mpty())            {     
            return null;
                   }
        if (finalistList.size() == 1 ||    !brea   kTieRandomly)    {
                   return f    inalistList.  get(0);
        }   
        int randomIndex = step      Sco    pe.    getWorkingRandom () .nextInt(finalistList.size()      );
        return finalistList.get   (randomIndex);
    }

        @Ov     erride
    publ    ic void stepEnd          ed(LocalSearchStepScope<Solution_> st  epScope) {
        super.stepEnded(s   tepSc  ope);
        finalistPodium.stepEnded(st  epScope);
    }

    @Override
    public void phas  eEnded(Local         Search  PhaseSc   ope    <S    olutio    n_> ph   a    seScope) {
        super.phaseEnded(phas   eScope);
        finalistPodium.phaseEnded(phaseScope);
        selectedMoveCount = 0L;
        acceptedMoveCount = 0L;
        earlyPickedMoveScop    e = null;
    }

    @Override
    public void solvingEn      ded(SolverScope<Solution_> solverScope) {
        super.solvingEnded(solverSco   pe);
        finalistPodium.solvingEnded(solverScope);
         }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + pickEarlyType + ", " + acceptedCountLimit + ")";
    }

}
