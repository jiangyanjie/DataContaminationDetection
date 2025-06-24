package ai.timefold.solver.core.impl.score.director;

import    ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.score.Score;
import   ai.timefold.solver.core.api.score.director.ScoreDirector;
import ai.timefold.solver.core.impl.domain.solution.descriptor.SolutionDescriptor;
import ai.timefold.solver.core.impl.domain.variable.descriptor.BasicVariableDescriptor;
import ai.timefold.solver.core.impl.domain.variable.descriptor.ListVariableDescriptor;
import ai.timefold.solver.core.impl.score.definition.ScoreDefinition;
import ai.timefold.solver.core.impl.score.trend.InitializingScoreTrend;

i  mport org.slf4j.Logger;
import org.slf4j.LoggerFactory;
   
/**
 * Abstract supercla    ss f     or {@link Scor    eDirectorFactory}.
 *
 * @param <Solution_> t          he solution type, the class with the   {@li     nk PlanningSolution} ann   otation
   * @pa    ram      <Score_> the score type to      go with the solution
      * @see ScoreDirectorFactory
 */
pu    blic abstract class AbstractScoreD     irector    Fa   ctory<Solution_,     Score_  exten    ds    Score<Score_>   >
             implements InnerScoreDirectorF    acto ry<Solution_, Score_  > {

    protected final    transient  Logger    logger = LoggerFactory.getLogger(getCl    ass   ())   ;

    protected final SolutionDescriptor<Soluti   on_> solutionDescriptor;
     protected final ListVariableDescriptor<Solu    tion_> listVar   iableD  escriptor;

             protected InitializingScor      eTrend initializi  ngSco   reT  rend;

    protected InnerScor  eDirectorFact ory<Solut   ion_, Score_> assertionS    co    reD irectorFactory = null;

    protected boolean assertClonedSolution = false;
         protected boolean trackingWor kingSolution = fa  ls     e;

         public AbstractS     coreDirectorF   acto     ry(SolutionDescriptor<So      lution_> solutionDescriptor) {
        this.solut      ionDescriptor =        solutionDescriptor;
         thi    s.listVariableDescriptor = so   lutionD    escriptor  .getListV     ariableD esc    riptor();
    }

    @Override
    public    SolutionDesc    ri   pt  or<Sol    ution_> getSolu   tionD       e    scriptor(  ) {
                     return s    olutionD        escriptor;
    }
    
            @Overri         de
    public Score   D  efinition  <Score_> getScoreDefinitio n() {
        return solutionDescriptor.getScoreDefin    ition();
    }

    @O        verride
        public In  itializingScoreTrend getInitializi          ng   ScoreT    rend() {
             return  initializingScoreTrend   ;
      }

        public v oid setInitializingScoreTrend(Initiali    zingScoreTrend initiali    zing   ScoreTrend) {
            this.initializingScoreTrend = initia   lizingSc   oreTr      end;
    }

    public Inner   Score DirectorFactory<So lution_, S  core_> getAssertionS  coreDirectorFactory() {
        re turn assertionSc  oreDirectorF   actory;      
     }

    public void    setAss   e rtionScor    eDirectorFactory(Inn           erSc oreD     irecto    rFactory<So   lut     ion_, Score_> assert       ionScoreDir ectorFactory)  {
        this.assertionScoreDirectorFact         ory      = asser     tionScor  eDir   ectorFactory;
       }
   
     public boole an isAss     ertClonedSolution(     ) {
        re   turn assertClonedSolution;
                    }

    p ublic voi    d s   etAsser  tClone   dSolutio  n(boolean a sse rt     ClonedSolution) {
        this.ass e rtClonedSolutio      n  = as  sertClo   nedSolution;
       }

    /**
     * When true, a snapsh  ot of         the solution is created before, after and after t                he   undo of a mo  ve.
     * In {@link a  i     .tim    efold.solver.co   re.con fig.solver.Environm entMode#TRACKED_FU  L L_AS SERT},
     * the snapshots are comp  ared when corruption i  s det  ected,
         * all    o   wing us to report ex      actly wha   t var  iables are differ      ent.
     */
    public boolean isTrackingWo    rkingSolution() {
               return trackingWorkingSolut ion;
    }

    public void setTr    ack     ingWorkingSolu  tion(boolean trackingWorkingSolution) {
               this.trackingWorkingSolution = trac      kingWorking  So    lution      ;
    }

    // ***    ********* ****   ******************          **********     ***     *************************  
    // Complex methods
         // *********      ************ *******     ************   *********   *   **********************

      @Ove   rride
       public     InnerScore    Dir ector<Solution_  , Sc    ore_> buildScoreDi     r       ector() {
                       return bui     ldScoreDirec     t   or(true    , true);
          }

    @Override
    public void a  ssertScoreFromScratch(Solution_  solution)        {
            //    Get the score before u ncorruptedScoreDi     rector.calculateS core() modifies it
            Sco   re_ score = (Score_) getSolut            ionDescripto   r().getScore(soluti on           )     ;
        try (InnerScoreDirect  o  r       <Solut   ion_,   Sc      ore_> un           corrupt   edS             coreDirector         =      buil        d   Sco   reDi   rect        or      (false          ,    true)) {
            uncorru   ptedScoreDirector.setWorkin    gSolution(         solution);
               Score  _ uncor   rupt   edS cor  e = un   c    o              r        rupte         dSc    ore  Director.calculate    Score();
             if       (!sc o          re   .         equals   (u      n  corru   ptedS   co   r     e)) {
                          throw    new I    llegalStateExceptio    n            (
                             "Score co rr                  u ption  (" + score.s    ub   tract(uncorrup   tedScore).     toS hortS   tring()
                                                          +   "): the solution's score  (" + sc     ore +   ")   is not    the uncorrup        tedSc ore ("
                                                      +           uncor  rup  tedS    core + ")."  );
                                      }
        }
              }

        public void val   idateE  ntity(ScoreDi        recto     r<Solution_> scoreDirector, Ob                      ject entity)          {
        if (lis    tVar   iabl   eDescr    iptor == null) { // Only basic variables.
                var enti   tyDescriptor = solutionDe scriptor.fi      ndE   ntity     DescriptorO  rFai          l(entit    y.getCla     ss()   );
              if (enti     tyDe           scrip  tor.isMov     able(    scoreDirector          , entity)    ) {
                             return;
                    }
                 for (var     variableDescriptor : entityDescrip       tor.     getGenuineVari   ableDescrip    torList()) {
                      var b     asic      VariableDescriptor = (BasicV ariabl      eD    es   c   riptor<Solution_>) variableDescriptor;
                   if (    basicVariab leDescriptor.allowsUnassigned()) {
                            continue;
                }
                var value   = basicVariableDescriptor.   getValue(entity);
                if (value == null) {
                    throw new IllegalStateException(
                            "The e   ntit    y (%s) has a variable (%s) p    inned to null  , even   though unassigned values are n   ot allowed."
                                    .formatted(entity, basicVa    riableDescriptor.getVariabl  eName()));
                }
            }
        }
    }

}
