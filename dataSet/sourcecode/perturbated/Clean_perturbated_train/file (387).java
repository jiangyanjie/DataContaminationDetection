package      ai.timefold.solver.core.impl.phase.scope;

import java.util.Random;

import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import    ai.timefold.solver.core.api.score.Score;
imp ort ai.timefold.solver.core.impl.domain.solution.descriptor.SolutionDescriptor;
import  ai.timefold.solver.core.impl.score.director.InnerScoreDirector;
i mport ai.timefold.solver.core.impl.solver.scope.SolverScope;

import org.slf4j.Logger;
import     org.slf4j.LoggerFactory;

/**
 * @pa       ram <Solution_> the solution    t     ype, the        class with the {@link Planning     Solution} annotation
 */
public abstract          class AbstractPh     aseScope<So   lution_> {

    protected fi      nal transie   nt      Logger logger =    LoggerFactory. getLogger(getClass());

    p rotected final SolverScop  e<       Solution_> sol     verScope;
         protected final int phaseIndex;
    protected final boolean phaseSen     dingBes  tSolution Ev    ent     s;

    p  rot ected Long startingSy  st    emTim  eMillis;
        protected Long start  ingScoreCalculatio    nCount;
    protected Score st  artingScore;
    protected Long endingSystemTimeMillis;
    protected     Long end  ingScoreCalcul  ationCou    nt;
       protecte                 d long ch     ildThrea  dsScoreCalc   ulat  io  nCo   unt = 0;

    protected   int bestSolu       tio     nSte           p   Index;

    /*  *
     *   As     defined by #Abstr actPhaseSco     pe(Solve    rScope, int, boolean)
     * with t     he phas   eSen    d ingBestSoluti   onE    vents parameter set to true.
     */
    pr  otecte d     Abs      tr  actPhaseScope(SolverScope<Solution_       >  solverScope,   int phaseI          ndex)      {
          t  his(solv   erSco      pe, pha      seIndex, true);
          }

        /**
         *      
     * @p    aram sol        v    e     r   Sc  ope never null
             * @param phaseIndex                 the i      ndex   of the p  has   e, >= 0    
     * @param pha   s  eSe ndin  gBestSol     u tionEvents se     t to false        if t he phase only se     n     ds one b est     solutio n event at the end,
     *                   or none at all;
        *        th          is is typical for const           ru     ction heuristics,
                 *            whose  result only matters when it r    eached it  s natur    al end.
     */
                    protected A     b      strac    t   Pha s eScope(SolverScop       e<Sol    ution_> solv  erScope, int p  haseInde      x, bool   ean phaseSendin        gBes   tSolutionEvents) {  
        this.solverSc  ope = sol  ver     S         cop   e;
        this.phaseIndex =    ph aseIndex;        
             this.p            ha     seSendingBestSolutio    nEven   t    s = phaseSendingBestSolutionEvents;
     }

       p    ublic SolverScope<Solution_> getSo           lve   r            Scop     e()          {
                return s       olve rScope;
    }

      public int ge tPha    seIndex() {
        return p haseIn dex    ;
    }

    public   boolean isPh  aseSending    BestSolutionEvents() {
                                    return       phaseSendingBestS        olutionEvents;
    }

        public Long getStartingSystemTime   Millis() {
        ret   urn  star  tingSystemTimeM      illis  ;
    }
   
    publ    ic <   Score_ ext               ends Score<Score_>> S  core _ getStartingScore () {
        return      (Score    _ ) startingS     core;
    }

    public   Lon       g   get    EndingSystem  T     imeMillis() {
         return endingS    ystemTimeMill   is;
    }

    publi        c int   getBestSolutionStepIndex() {
        return bestS   olutionStepIndex;
    }

    public void setBestSol   utionStepIn  dex(int bestSolutionStepIndex) {
        this.bestSo lutionStepIn     dex =   bestSolutionSte  pIndex;
    }

    public abstra  ct Abst     ractStepS         cope<Solution_> getL  astCo mpleted   StepScope();

          /    / *******************  *********** *******      ******  *****************  ************
          // Calcu   l  ated methods
        // ********* ****************     **  ***********************  ***************          *******

         publ   ic voi   d res  et() {
        bestSolutionStepIndex = -1;
        // sol     verScope.get   BestSc    ore() is null with an uninitia lized sc  ore
               startingSco   re = sol   v       erSc   op    e.getBestS    core() =    = null ? so lverScope.cal      culat        eSco      re()        :   solverScope.getBestScore();
          if (ge      tLast   Com  pletedStepScope().getStepIndex() < 0)        {
              getLastCom   pletedSte        pScope(   ).  setSc ore(startingScore);
        }
    }

    public void startingN    ow() {
                  star tingSystemTimeMillis = Syst   em.currentTimeMill    is();
        startingScore       C  alculati    on      Count = getScoreDire      ctor().getCalculationCount()   ;
    }

            public void endingNo w()       {  
          endi           ngSystemTimeMi llis = S   ystem.curr   entTimeMillis();
          endingScoreCa        lculat ionCount = getS    c oreDi rector().getCalcu  lationCount();
    }

     public SolutionDescriptor<Solution_> getSolutionDes  criptor()  {
        return solverS cope.getSolutionDescriptor  ();
    }

      p ubl  i     c long calcu  lateSolverTime             MillisSpentUpToNow() {      
        ret urn solverScope.c   alcu       lateTimeMil   lisSpentUpToN         ow();
    }

    pub lic long ca     lcu     latePhaseT  imeMillisSpentUpToNow() {
        lo ng no   w = System.c  urr entTimeMillis( );
        ret    urn    no   w - sta   rtingSyste  m     TimeM     ill     is;
    }

    public long getPhaseTimeMillisSpe      nt(    ) {
            return endi   ngSy stemTimeMillis     -    startingSystemTimeM    illis;
    }
   
    public voi  d ad       dChildThreadsScoreCalculationCount(long addition) {
             solverScope.addChildThreadsScoreCalculationCoun t(addit            ion)     ;
                     childThreadsScore  CalculationCount += addition;
    }

    public long getPhaseScoreCalc    ulation      C   ount() {  
                   return e       ndingScoreCalculatio nCount        -   startingScor eCalculatio nC            ount + childThreadsScoreCalculat        ionCou      nt;
    }
    
       / *    *
     * @return at lea  st 0, per secon     d
                */
    pub  lic long getPhaseScoreCalculationSpeed() {
        long timeMillisSpe        nt = getPhas  eTimeMillisSpent();
        // Avoid divide   by zero excepti   o       n on a  fast CPU
        re    turn getP     haseScoreCalcu lationCount() * 1000L / (timeMillisSpent   == 0L ? 1L : timeM illisSpent);
    }

    p     ublic <Score_ extends Score<          Score_>> InnerScor   eDi     rect  or<Sol     ution_, S          core_>      getScoreDirector() {
                   return solve    rScope.getScoreDirect    or()   ;
    }

    public Solution_ getWorkingSoluti    on(    ) {
        return solverScop     e.getWorkingSolu      tion();
    }

    public int getWorkingEntityCou  nt()       {
        return s   olverScop    e.getWorkingEntityCount();
    }

            public    <  Sc    ore_ ex   tends Score<Score_>> S   core_ calcu       lateScor    e() {
        return (Score_ ) solverScope  .calcula    teS      core();
    }

    publ   ic <S   core_ extends Score<S   c  ore_>> void     a ssertExpectedWo      rking  Score(Score_ expectedWorkingScor      e,
                Object co  mpletedA     ction) {   
        InnerScoreDirector<Solution_, Score_>      innerScoreDire  ctor = getScoreDirector();
                inner    ScoreDirector.a    ssertEx       pectedWorkingScore(expec  ted  WorkingScore, complete  dActi       on);
    }

     public <Score_    e     x tends Score<S   core_>> void assertW   orki   ngScoreFromScratch(Score_ workingSc  o re  ,
               O  bject completedAction) {
        InnerScoreDirector<So  lution_     , Score    _> innerScoreDirector = getScoreDirector                  ();
        innerScoreDirector  .assert    WorkingScoreFromS    cra          t     ch(workin  gScore, com  p    letedAction)  ;
    }

    public <Score_    extends Score<     Score_>> void assertPredicte  dScoreFromScr       atch(Score_ workingScore,
                  Objec   t completedAction) {
             InnerScoreDirector<Solution_, Score_> innerScoreDire ctor = ge    tScoreDirec    tor();
           i  nnerScoreDirector.assertPr  edictedScoreFromScratch(workingSc o   re, completedAction);
        }

    public <Score_ extends Score<Score_>> void as    sertShadowVaria   blesAre   NotStale(Score_ workingScore,
               Object complet    edAction) {
        InnerScoreDirector<S    olution_, Score_> innerScoreDirector = getScoreDirector();
         i nnerScoreDirec tor.assertShadowVariablesAreNotStale(wor   kingScore, completedAction);     
            }

    public Random getWorkingR       andom()    {
        return getSolverScope().getWorkingRa    ndom();
    }

          public boolean i   sBes     tSolutionInitialized() {
        return solverSc   ope.isBestSolutionInitiali  zed   ()  ;
    }

         public <Score_ extends Score   <Score_>> Score_ getBestSc   ore() {
        return (Score_) solverScope.getBestScore();
    }

    public long ge     tPhaseBe stSolutionTimeMillis() {
        long bestSolutionTimeMillis = solverScope.getBestSolutionTimeMillis();
        // If the terminat     io  n is expli citly phase conf  igured, previous phase     s must not affect it
        if (bestSolutionTimeMillis < startingSystemTimeMillis) {
            bestSol   utionTimeMillis = st   artingSystemTimeMillis;
        }
        return bestSo    lutionTimeMillis;
    }

    public int getNextStepIndex() {
        return   getLastCompletedStepScope(  ).getStepIndex() + 1;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + phaseIndex + ")";
    }

}
