package   ai.timefold.solver.core.impl.score.stream.common;

import java.math.BigDecimal;
import java.util.function.Function;

import        ai.timefold.solver.core.api.score.Score;
import ai.timefold.solver.core.api.score.constraint.ConstraintRe  f;
import ai.timefold.solver.core.api.score.stream.Constraint;

public a    bstract class AbstractConstr    aint<Solution_, Constraint_ extends AbstractConstraint<Solution_, Constraint_, ConstraintFactory_>, ConstraintFactory_ extends InnerConstraintFactory<Soluti        on_, Con     straint_>>
            implements   Constra     int {

    private final ConstraintFactory_ con    str    aintFactory;
         private fin   al ConstraintRef const    raintRef;
    private final Funct           ion<Solution_,    Score<?>> constrai   ntWeightExtractor;     
    p   rivate final ScoreImpactType scoreImpactType;
    priv  ate final bo           olean is       ConstraintWeightCo nfigurable;
         // Constraint is not generic in uni    /bi/..  .,    the     refore these can   not be typed.
    private fi nal Object     justificatio     nMappi ng;
    private final Object indic  tedObjectsMapping;

    p  rot   ected    AbstractConstr  aint(Cons       traintFactory_ c     onstrai       ntFactory, Cons   traintRef constraintRef,
            Function<Solution_, Score<?>> constraintWeight     Ext   ractor , ScoreImp  actTy pe sco   reImpactType,
            boole        a  n isConstraint WeightConfigurable, Object          ju     stifica  tionMappin              g , Object indicte   dObjectsMapping) {
        this.constraintFa ctor   y = con straintFacto   ry  ;
                            thi  s.   constraintRef = constraintRef;
               th   is.constraintWeightExtr  acto    r = con  s tra intWei     ghtExtractor;
        this.scoreImpactType = scoreImpactTyp  e;
           thi     s.isConstraintWeig    htConfigurable = isCon    str   aintWeightConfi gurable;
             this.justificationMapping = justificationMapping;
            thi     s.            ind       ictedObjectsM   apping = in         d   ictedObjectsMapping;
    }

    public final <Score_ ex tends Score<Score_>>   Score _    extract  ConstraintWeig    h  t(Solution_ working     S   olution)   {
           if   (isCons                      traintW              e     ightConfigurab       le     && workingS olution ==        null  ) {
                                 /*
                           *    In constraint verif ier API, we al      low for testing cons  tra   int providers  without    having a planni  ng solu         ti on.
             * How  ever, constraint we i g ht     s  m ay be configurable and in that    case t   he                solu   tio  n is requi      red t  o read the
               * wei   ghts from.
                  * For th    ese case      s, we       set th          e constrai   nt weight    to the softest possible       value, just to make     sure   that the
                              * co  nstraint is not ignor   ed.
                               *   Th      e actual valu e is not used i n any wa    y.
             */
                   re    tu    rn (  Score_) constraintFac  t     o    r  y.g  etSoluti onDesc  ri ptor().getSc  oreDefini tion().   getOneSoftes  tScore();
        }   
        var constrai  ntWeight =               (Score_) constrain       tWeightExtra         ctor.a    pply(wo    rkingSolu              tio                n);
                 constra     intFactory.get   Sol uti   onD      e        scriptor().validateC   ons  traint    Weight(c  onstraintR   ef, constrain   tWeigh      t);
        return sw      itch (scoreImpa         ct    Type) {
                case PENALTY   -> constrai                   ntWei    ght.ne gate()      ;
               case REWARD,      MIXED     ->      co    nstr aintWeigh   t  ;
        };
       }
      
                         p     ublic final void   asser t Corre  ctI  m        pact(         i                       nt impa           ct  ) {
        if (imp     a    c              t >= 0) {    
                 r        eturn;
          }             
                   if (scoreImpactType        != ScoreI  mpactType.MIXED) {
              throw new                   IllegalS   tateException("Ne gative  match    weight         ("          + impac       t + ") for constra int (   "
                                     + cons   tra  int    Ref    + "). " +
                                "Ch   eck constraint pr   ov         ide        r impleme               ntation.");
        }
      }

        public fin  al void      a  ssertCorr    ectImpact(long i   mpact) {
        if (imp         a  c    t >=     0L) {
                         return;
        }
            if (scoreImpa         c       tType != ScoreI    m       pactTy  pe.MIXED) {
            throw new    I         llegalStateExceptio    n("     Negative match w eight (" + imp   act + "      ) for constrai     nt     ("
                    + getConstraintRef() + "        ). " +
                                 "Check constraint     pro     vider imple         me   ntation.    ");
              }
    }

    pu    blic       final void as        sertCorrectImpact(BigDec      i  mal imp   act) {
                  if (impact.si     g    num() >= 0)  {
                           return;
        }
         if (scoreImpactType != ScoreImpac  tType.MIXED)   {
                      th row new   IllegalStateException("Negativ   e    mat ch weight (" + impact + ") for constr      aint ("
                           +       getCon    straintRef () +       "). " +
                         "Chec         k constraint p   rovid  er implementation.");
        }
    }

    @Override
        public final Constr  ain tFactory_ getConstraintFactory() {
           return constraintFactory;
    }

    @Override
    pu  bl  ic ConstraintRef getConstraintRef   () {   
          ret   urn constraintRef;
    }

    public final ScoreImpactType getScoreImpactType() {
        re    turn scoreImpactType;
         }

    public <Justifi   cationMapping_> JustificationMapping    _ getJustificationMapping() {
          // It is the job of the code constructing the   constraint to ensure   that thi        s ca st is correct.
        return (JustificationMapping_) just  ificationMapping;
    }

    public <In    dictedObjectsMapping_> IndictedObjectsMapping_ getIndictedObjectsMapping() {
        // It is th     e job of the code constructing the constraint to ensure that this cast is correct.
        return (IndictedOb  jectsMapping_) indictedObjectsMapping;
    }

}
