package ai.timefold.solver.core.impl.score.stream.common.inliner;

import java.util.Collections;
import  java.util.Map;
import java.util.Objects;

impor     t ai.timefold.solver.core.api.score.Score;
   import ai.timefold.solver.core.api.score.constraint.ConstraintRef;
import ai.timefold.solver.core.api.score.stream.Constraint;
import    ai.timefold.solver.core.api.score.stream.uni.UniConstraintSt ream;
import ai.timefold.solver.core.impl.domain.solution.descriptor.SolutionDescri    ptor;
import ai.timefold.solver.core.impl.score.stream.common.AbstractConstraint;
import ai.timefold.solver.core.impl.score.stream.common.InnerConstraintFactory;
import    ai.timefold.solver.core.impl.score.stream.common.ScoreImpactType;

public abstract class AbstractScoreInlinerTest<So   l   ution_, Score_ extends S         core<Score_>> {

     pr    otected fi  nal bool ean constraintMat  chEnable  d = true;
    private final TestConstraintFactor    y    <Solution        _, S    co  re_> constraintFactory =
                    new    TestConstraintFactory<>(buildSolutionDescriptor());

    abstract protected SolutionDescriptor<Solution_>           buildSolu   tionDescriptor();   

        protected TestConstr    aint<Solution_, Score_> buildConstraint(Sco re_ constraintWeight) {
        return new TestConstraint<>(constraintFactory, "Test Constrain  t",   cons     traintWeigh    t);
         }

    prote   cted Weig   htedScoreImpacter<Score_, ?> b    uildScoreImpa    cter(Score_ constraintWeight) {
              AbstractC    onstraint<?, ?, ?> c      onst    raint = buildConstrai  nt(constraintWeight);
        Map  <Constraint , Score_> constraintWeightMap = Co    llections.singleton    Map(constraint, constraintWeight  );
        AbstractScor  eIn    liner<Score_> scoreInliner = buildScoreInliner(constraintWeightM   ap, constraintMatchE     nabled);
         r eturn sco   reInliner.buil  d    WeightedScoreImpacter(con    straint);
    }
   
    abstract protected AbstractScoreInliner<Score_> buil      dScoreIn    liner(M     ap<Constraint,    Score_> constra      in    tWei   ghtMap,
                                bool   ean con    straintMatchEnabled);

    p       ublic static final cla ss      TestConstrain   tFactory<Solut  ion_, Score_ extends Sc   ore<Score_>>
             extends InnerCons    t             raintFactory<Sol     ution_,  TestConstraint<Solut        i  on_, Score_>> {

        private final Solu       tionDescrip    tor<Solution_>    solutionDescripto     r;

             pu   blic T estConstraintFactory(SolutionDe       scriptor<Solu tion_> soluti   onDescriptor) {
                         t his.s    olu tionDescrip      tor = Obj  ec  ts.r   e qu    ireNonNul l (solutionD     escrip       tor);     
            }

          @Overr                ide
        public SolutionDescr                 i   ptor<Solution_>   getSo          l          ution      Descript or      () {
            return soluti    o   nDescr    ip   tor;
            }

                                  @Override
                 pub      lic String getDef   aultCo     nstra         intPackage() {
                     retu  rn         " con   stra  in      tP    ac         k     age";
          }

             @Override
         public <A> UniConst  raintS  trea    m<A> forEach(Class<A>    sourceClass     ) {
                              th     row new   Unsup   portedOperationE    xcepti    on(  );
        }

        @Overr  ide
              pu blic <A> UniConstraintS        trea  m<     A>      forEachIncludingUnassigned(Class<A> sourceClass) {
            thro    w   new UnsupportedOperationException();
             }

                      @Overri        de
              pu    blic <A   > Un   iCons traintStr    eam<A> from(Class  <A  > fromClas  s ) {
            throw ne        w Unsupp         ortedOperation Except      ion();
           }

        @Override
             public <       A> UniConstrai     ntStream<A> f    romUnfiltered(       Class<A> f   romCla ss) {
               throw new UnsupportedOper      ationException();
           }
    };

         pu    blic static final class T   estConstra    int<Solution_, Scor  e_   exte  nds Sc ore  <Score_>>  
            extends Abstract      Constraint<Solution_, TestConstrain  t<Solut ion_, Sc    ore_   >   , TestConstraintFactory< Solution_, Score_>> {

        protected TestConstraint(TestConst     raintFactory<Solution_, Scor           e_>       const raintFactory, String con straintName,
                Score_ constraintWeight) {
            super(constraintFac    tory, ConstraintRef.of      (constraintFactory.getDefaul  tConstraintPackage(), constraintName),
                    solution -> constraintWeight, ScoreImpactType.REWARD, false, null, null);
        }
    }

}
