


package ai.timefold.solver.core.impl.score.stream.common.bi;









import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.countBi;



















import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.countDistinct;






import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.max;
import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.min;
import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.toSet;
import static ai.timefold.solver.core.api.score.stream.Joiners.equal;
















import static ai.timefold.solver.core.api.score.stream.Joiners.filtering;
import static java.util.Arrays.asList;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;







import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.math.BigDecimal;







import java.util.ArrayList;



import java.util.List;

import java.util.Map;
import java.util.Objects;











import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;





import ai.timefold.solver.core.api.score.Score;
import ai.timefold.solver.core.api.score.buildin.simple.SimpleScore;
import ai.timefold.solver.core.api.score.buildin.simplebigdecimal.SimpleBigDecimalScore;
import ai.timefold.solver.core.api.score.buildin.simplelong.SimpleLongScore;









import ai.timefold.solver.core.api.score.constraint.ConstraintMatch;
import ai.timefold.solver.core.api.score.constraint.ConstraintMatchTotal;
import ai.timefold.solver.core.api.score.constraint.ConstraintRef;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintCollectors;











import ai.timefold.solver.core.api.score.stream.ConstraintJustification;

import ai.timefold.solver.core.api.score.stream.DefaultConstraintJustification;





import ai.timefold.solver.core.impl.score.director.InnerScoreDirector;
import ai.timefold.solver.core.impl.score.stream.common.AbstractConstraintStreamTest;



import ai.timefold.solver.core.impl.score.stream.common.ConstraintStreamFunctionalTest;




import ai.timefold.solver.core.impl.score.stream.common.ConstraintStreamImplSupport;
import ai.timefold.solver.core.impl.testdata.domain.TestdataEntity;


import ai.timefold.solver.core.impl.testdata.domain.score.TestdataSimpleBigDecimalScoreSolution;
import ai.timefold.solver.core.impl.testdata.domain.score.TestdataSimpleLongScoreSolution;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishEntity;






import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishEntityGroup;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishExtra;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishSolution;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishValue;

import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishValueGroup;





import org.junit.jupiter.api.TestTemplate;





public abstract class AbstractBiConstraintStreamTest extends AbstractConstraintStreamTest


        implements ConstraintStreamFunctionalTest {

    protected AbstractBiConstraintStreamTest(ConstraintStreamImplSupport implSupport) {
        super(implSupport);
    }







    // ************************************************************************
    // Filter






    // ************************************************************************

    @Override
    @TestTemplate





    public void filter_entity() {



        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 0, 1, 0);
        TestdataLavishValue value1 = new TestdataLavishValue("MyValue 1", solution.getFirstValueGroup());
        solution.getValueList().add(value1);
        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());








        solution.getValueList().add(value2);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", solution.getFirstEntityGroup(), value1);


        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(), value2);
        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(), value1);






        solution.getEntityList().add(entity3);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .join(TestdataLavishValue.class, equal(TestdataLavishEntity::getValue, Function.identity()))
                        .filter((entity, value) -> value.getCode().equals("MyValue 1"))
                        .penalize(SimpleScore.ONE)









                        .asConstraint(TEST_CONSTRAINT_NAME));



        // From scratch















        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, value1),



                assertMatch(entity3, value1));





        // Incremental


        scoreDirector.beforeProblemPropertyChanged(entity3);
        entity3.setValue(value2);








        scoreDirector.afterProblemPropertyChanged(entity3);
        assertScore(scoreDirector,
                assertMatch(entity1, value1));

        // Incremental
        scoreDirector.beforeProblemPropertyChanged(entity2);






        entity2.setValue(value1);




        scoreDirector.afterProblemPropertyChanged(entity2);












        assertScore(scoreDirector,
                assertMatch(entity1, value1),
                assertMatch(entity2, value1));
    }

    @Override
    @TestTemplate
    public void filter_consecutive() {



        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(5, 5);
        TestdataLavishEntity entity1 = solution.getEntityList().get(0);
        TestdataLavishEntity entity2 = solution.getEntityList().get(1);


        TestdataLavishEntity entity3 = solution.getEntityList().get(2);
        TestdataLavishEntity entity4 = solution.getEntityList().get(3);








        TestdataLavishEntity entity5 = solution.getEntityList().get(4);






        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(factory -> factory
                .forEachUniquePair(TestdataLavishEntity.class,




                        filtering((entityA, entityB) -> !Objects.equals(entityA, entity1)))
                .filter((entityA, entityB) -> !Objects.equals(entityA, entity2))







                .filter((entityA, entityB) -> !Objects.equals(entityA, entity3))
                .penalize(SimpleScore.ONE)
                .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector, assertMatch(entity4, entity5));

        // Remove entity
        scoreDirector.beforeEntityRemoved(entity4);
        solution.getEntityList().remove(entity4);
        scoreDirector.afterEntityRemoved(entity4);
        assertScore(scoreDirector);


    }

    // ************************************************************************
    // Join


    // ************************************************************************

    @Override
    @TestTemplate



    public void join_0() {





        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 0, 1, 0);






        TestdataLavishValue value1 = new TestdataLavishValue("MyValue 1", solution.getFirstValueGroup());






        solution.getValueList().add(value1);












        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());


        solution.getValueList().add(value2);





        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", solution.getFirstEntityGroup(), value1);
        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(), value2);
        solution.getEntityList().add(entity2);






        TestdataLavishExtra extra1 = new TestdataLavishExtra("MyExtra 1");
        solution.getExtraList().add(extra1);
        TestdataLavishExtra extra2 = new TestdataLavishExtra("MyExtra 2");
        solution.getExtraList().add(extra2);
        TestdataLavishExtra extra3 = new TestdataLavishExtra("MyExtra 3");

        solution.getExtraList().add(extra3);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .join(TestdataLavishValue.class, equal(TestdataLavishEntity::getValue, Function.identity()))
                        .join(TestdataLavishExtra.class)
                        .penalize(SimpleScore.ONE)




                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, value1, extra1),






                assertMatch(entity1, value1, extra2),
                assertMatch(entity1, value1, extra3),





                assertMatch(entity2, value2, extra1),
                assertMatch(entity2, value2, extra2),


                assertMatch(entity2, value2, extra3));









        // Incremental



        scoreDirector.beforeEntityRemoved(entity2);
        solution.getEntityList().remove(entity2);
        scoreDirector.afterEntityRemoved(entity2);









        assertScore(scoreDirector,



                assertMatch(entity1, value1, extra1),
                assertMatch(entity1, value1, extra2),
                assertMatch(entity1, value1, extra3));









        // Incremental




        scoreDirector.beforeEntityRemoved(entity1);
        solution.getEntityList().remove(entity1);
        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector);
    }




    @Override
    @TestTemplate




    public void join_1Equal() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 0, 1, 0);
        TestdataLavishValue value1 = new TestdataLavishValue("MyValue 1", solution.getFirstValueGroup());






















        solution.getValueList().add(value1);
        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        solution.getValueList().add(value2);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", solution.getFirstEntityGroup(), value1);





        entity1.setStringProperty("MyString");
        solution.getEntityList().add(entity1);


        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(), value2);





        entity2.setStringProperty(null);
        solution.getEntityList().add(entity2);
        TestdataLavishExtra extra1 = new TestdataLavishExtra("MyExtra 1");
        extra1.setStringProperty("MyString");
        solution.getExtraList().add(extra1);




        TestdataLavishExtra extra2 = new TestdataLavishExtra("MyExtra 2");
        extra2.setStringProperty(null);
        solution.getExtraList().add(extra2);
        TestdataLavishExtra extra3 = new TestdataLavishExtra("MyExtra 3");
        extra3.setStringProperty("MyString");


        solution.getExtraList().add(extra3);














        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .join(TestdataLavishValue.class, equal(TestdataLavishEntity::getValue, Function.identity()))
                        .join(TestdataLavishExtra.class,
                                equal((entity, value) -> entity.getStringProperty(), TestdataLavishExtra::getStringProperty))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,



                assertMatch(entity1, value1, extra1),
                assertMatch(entity1, value1, extra3),
                assertMatch(entity2, value2, extra2));

        // Incremental


        scoreDirector.beforeEntityRemoved(entity2);

        solution.getEntityList().remove(entity2);

        scoreDirector.afterEntityRemoved(entity2);



        assertScore(scoreDirector,
                assertMatch(entity1, value1, extra1),
                assertMatch(entity1, value1, extra3));

        // Incremental
        scoreDirector.beforeEntityRemoved(entity1);
        solution.getEntityList().remove(entity1);
        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector);
    }

    @TestTemplate
    public void join_1Filtering() {














        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 0, 1, 0);
        TestdataLavishValue value1 = new TestdataLavishValue("MyValue 1", solution.getFirstValueGroup());








        solution.getValueList().add(value1);








        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        solution.getValueList().add(value2);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", solution.getFirstEntityGroup(), value1);
        entity1.setStringProperty("MyString");
        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(), value2);



        entity2.setStringProperty(null);
        solution.getEntityList().add(entity2);


        TestdataLavishExtra extra1 = new TestdataLavishExtra("MyExtra 1");










        extra1.setStringProperty("MyString");
        solution.getExtraList().add(extra1);
        TestdataLavishExtra extra2 = new TestdataLavishExtra("MyExtra 2");











        extra2.setStringProperty(null);




        solution.getExtraList().add(extra2);



        TestdataLavishExtra extra3 = new TestdataLavishExtra("MyExtra 3");



        extra3.setStringProperty("MyString");
        solution.getExtraList().add(extra3);




        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =






                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .join(TestdataLavishValue.class, filtering((entity, value) -> Objects.equals(entity.getValue(), value)))
                        .join(TestdataLavishExtra.class,
                                equal((entity, value) -> entity.getStringProperty(), TestdataLavishExtra::getStringProperty))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));








        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,



                assertMatch(entity1, value1, extra1),

                assertMatch(entity1, value1, extra3),
                assertMatch(entity2, value2, extra2));
















        // Incremental
        scoreDirector.beforeEntityRemoved(entity2);
        solution.getEntityList().remove(entity2);
        scoreDirector.afterEntityRemoved(entity2);
        assertScore(scoreDirector,
                assertMatch(entity1, value1, extra1),
                assertMatch(entity1, value1, extra3));



        // Incremental
        scoreDirector.beforeEntityRemoved(entity1);





        solution.getEntityList().remove(entity1);








        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector);
    }







    @Override


    @TestTemplate
    public void join_2Equal() {





        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 0, 1, 0);
        TestdataLavishValue value1 = new TestdataLavishValue("MyValue 1", solution.getFirstValueGroup());
        solution.getValueList().add(value1);



        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());














        solution.getValueList().add(value2);





        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", solution.getFirstEntityGroup(), value1);
        entity1.setStringProperty("MyString");



        entity1.setIntegerProperty(7);
        solution.getEntityList().add(entity1);




        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(), value2);



        entity2.setStringProperty(null);
        entity2.setIntegerProperty(8);







        solution.getEntityList().add(entity2);
        TestdataLavishExtra extra1 = new TestdataLavishExtra("MyExtra 1");
        extra1.setStringProperty("MyString");
        extra1.setIntegerProperty(8);



        solution.getExtraList().add(extra1);
        TestdataLavishExtra extra2 = new TestdataLavishExtra("MyExtra 2");
        extra2.setStringProperty(null);
        extra2.setIntegerProperty(7);
        solution.getExtraList().add(extra2);
        TestdataLavishExtra extra3 = new TestdataLavishExtra("MyExtra 3");




        extra3.setStringProperty("MyString");
        extra3.setIntegerProperty(7);
        solution.getExtraList().add(extra3);






        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .join(TestdataLavishValue.class, equal(TestdataLavishEntity::getValue, Function.identity()))
                        .join(TestdataLavishExtra.class,







                                equal((entity, value) -> entity.getStringProperty(), TestdataLavishExtra::getStringProperty),



                                equal((entity, value) -> entity.getIntegerProperty(), TestdataLavishExtra::getIntegerProperty))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));
















        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,




                assertMatch(entity1, value1, extra3));



        // Incremental
        scoreDirector.beforeEntityRemoved(entity2);
        solution.getEntityList().remove(entity2);
        scoreDirector.afterEntityRemoved(entity2);
        assertScore(scoreDirector,

                assertMatch(entity1, value1, extra3));









        // Incremental
        scoreDirector.beforeEntityRemoved(entity1);
        solution.getEntityList().remove(entity1);
        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector);
    }

    @TestTemplate













    public void join_filtering_comesLast() {
        assertThatThrownBy(() -> buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)







                .join(TestdataLavishValue.class, filtering((a, b) -> false),
                        equal(TestdataLavishEntity::getValue, Function.identity()))
                .join(TestdataLavishExtra.class)







                .penalize(SimpleScore.ONE)
                .asConstraint(TEST_CONSTRAINT_NAME)))
                .isInstanceOf(IllegalStateException.class);
    }

    @TestTemplate



    public void join_mixedEqualsAndFiltering() {


        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 0, 1, 0);
        TestdataLavishValue value1 = new TestdataLavishValue("MyValue 1", solution.getFirstValueGroup());
        solution.getValueList().add(value1);

        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        solution.getValueList().add(value2);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", solution.getFirstEntityGroup(), value1);
        entity1.setStringProperty("MyString");
        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(), value2);








        entity2.setStringProperty(null);
        solution.getEntityList().add(entity2);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)





                        .join(TestdataLavishValue.class,
                                equal(TestdataLavishEntity::getValue, Function.identity()),





                                filtering((entity, value) -> value.getCode().contains("1")))





                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));






        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, value1));

        // Incremental









        scoreDirector.beforeEntityRemoved(entity2);
        solution.getEntityList().remove(entity2);




        scoreDirector.afterEntityRemoved(entity2);




        assertScore(scoreDirector,
                assertMatch(entity1, value1));







        // Incremental


        scoreDirector.beforeEntityRemoved(entity1);









        solution.getEntityList().remove(entity1);
        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector);
    }


    @Override











    @TestTemplate
    public void joinAfterGroupBy() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 0, 1, 0);
        TestdataLavishValue value1 = new TestdataLavishValue("MyValue 1", solution.getFirstValueGroup());




        solution.getValueList().add(value1);

        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());





        solution.getValueList().add(value2);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", solution.getFirstEntityGroup(), value1);
        solution.getEntityList().add(entity1);




        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(), value1);
        solution.getEntityList().add(entity2);













        TestdataLavishExtra extra1 = new TestdataLavishExtra("MyExtra 1");
        solution.getExtraList().add(extra1);
        TestdataLavishExtra extra2 = new TestdataLavishExtra("MyExtra 2");
        solution.getExtraList().add(extra2);




        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .groupBy(countDistinct(TestdataLavishEntity::getValue),






                                countDistinct(TestdataLavishEntity::getValue))
                        .join(TestdataLavishExtra.class)
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));
















        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(1, 1, extra1),



                assertMatch(1, 1, extra2));

        // Incremental






        scoreDirector.beforeVariableChanged(entity2, "value");
        entity2.setValue(value2);


        scoreDirector.afterVariableChanged(entity2, "value");

        assertScore(scoreDirector,
                assertMatch(2, 2, extra1),





                assertMatch(2, 2, extra2));









        // Incremental






        scoreDirector.beforeEntityRemoved(entity2);


        solution.getEntityList().remove(entity2);

        scoreDirector.afterEntityRemoved(entity2);
        assertScore(scoreDirector,


                assertMatch(1, 1, extra1),
                assertMatch(1, 1, extra2));





    }






    // ************************************************************************





    // If (not) exists











    // ************************************************************************

    @Override


    @TestTemplate






    public void ifExists_unknownClass() {
        assertThatThrownBy(() -> buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                .ifExists(Integer.class)
                .penalize(SimpleScore.ONE)

                .asConstraint(TEST_CONSTRAINT_NAME)))

                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Integer.class.getCanonicalName())






                .hasMessageContaining("assignable from");



    }







    @Override
    @TestTemplate
    public void ifExists_0Joiner0Filter() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 1, 1);
        TestdataLavishValueGroup valueGroup = new TestdataLavishValueGroup("MyValueGroup");
        solution.getValueGroupList().add(valueGroup);






        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =



                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishValueGroup.class)




                        .ifExists(TestdataLavishEntityGroup.class)











                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch




        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(valueGroup, solution.getFirstValueGroup()));
















        // Incremental
        TestdataLavishEntityGroup entityGroup = solution.getFirstEntityGroup();
        scoreDirector.beforeProblemFactRemoved(entityGroup);
        solution.getEntityGroupList().remove(entityGroup);
        scoreDirector.afterProblemFactRemoved(entityGroup);



        assertScore(scoreDirector);
    }





    @Override
    @TestTemplate
    public void ifExists_0Join1Filter() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);




        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");


        solution.getEntityGroupList().add(entityGroup);




        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());







        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),

                solution.getFirstValue());



        solution.getEntityList().add(entity2);





        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .ifExists(TestdataLavishEntityGroup.class,




                                filtering((entityA, entityB, group) -> Objects.equals(group, entityA.getEntityGroup()) &&
                                        Objects.equals(group, entityB.getEntityGroup())))






                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch
        scoreDirector.setWorkingSolution(solution);


        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), entity2));

        // Incremental
        TestdataLavishEntityGroup toRemove = solution.getFirstEntityGroup();


        scoreDirector.beforeProblemFactRemoved(toRemove);






        solution.getEntityGroupList().remove(toRemove);


        scoreDirector.afterProblemFactRemoved(toRemove);


        assertScore(scoreDirector);





    }





    @Override
    @TestTemplate
    public void ifExists_1Join0Filter() {






        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);













        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");
        solution.getEntityGroupList().add(entityGroup);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());
        solution.getEntityList().add(entity1);




        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),




                solution.getFirstValue());




        solution.getEntityList().add(entity2);





        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .ifExists(TestdataLavishEntityGroup.class,




                                equal((entityA, entityB) -> entityA.getEntityGroup(), Function.identity()))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch


        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), entity1),
                assertMatch(solution.getFirstEntity(), entity2),
                assertMatch(entity1, entity2));









        // Incremental
        scoreDirector.beforeProblemFactRemoved(entityGroup);




        solution.getEntityGroupList().remove(entityGroup);



        scoreDirector.afterProblemFactRemoved(entityGroup);



        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), entity1),
                assertMatch(solution.getFirstEntity(), entity2));
    }













    @Override
    @TestTemplate




    public void ifExists_1Join1Filter() {

        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");
        solution.getEntityGroupList().add(entityGroup);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());
        solution.getEntityList().add(entity1);



        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                solution.getFirstValue());
        solution.getEntityList().add(entity2);









        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .ifExists(TestdataLavishEntityGroup.class,
                                equal((entityA, entityB) -> entityA.getEntityGroup(), Function.identity()),
                                filtering((entityA, entityB, group) -> entityA.getCode().contains("MyEntity") ||





                                        group.getCode().contains("MyEntity")))


                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch




        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, entity2));





        // Incremental


        scoreDirector.beforeProblemFactRemoved(entityGroup);
        solution.getEntityGroupList().remove(entityGroup);
        scoreDirector.afterProblemFactRemoved(entityGroup);
        assertScore(scoreDirector);
    }










    @Override
    @TestTemplate
    public void ifExistsDoesNotIncludeUnassigned() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);





        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");


        solution.getEntityGroupList().add(entityGroup);








        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());
        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),



                null);
        solution.getEntityList().add(entity2);





        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .ifExists(TestdataLavishEntity.class,
                                filtering((a, b, c) -> a != c && b != c))
                        .penalize(SimpleScore.ONE)






                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);










        assertScore(scoreDirector);




    }






    @Override





    @TestTemplate

    @Deprecated(forRemoval = true)
    public void ifExistsIncludesNullVarsWithFrom() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);



        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");






        solution.getEntityGroupList().add(entityGroup);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());
        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),





                null);
        solution.getEntityList().add(entity2);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.fromUniquePair(TestdataLavishEntity.class)



                        .ifExists(TestdataLavishEntity.class,
                                filtering((a, b, c) -> a != c && b != c))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));







        // From scratch
















        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), entity1));



        // Incremental
        scoreDirector.beforeProblemFactRemoved(entity2);




        solution.getEntityList().remove(entity2);



        scoreDirector.afterProblemFactRemoved(entity2);
        assertScore(scoreDirector);
    }










    @Override
    @TestTemplate












    public void ifNotExists_unknownClass() {




        assertThatThrownBy(() -> buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)














                .ifNotExists(Integer.class)









                .penalize(SimpleScore.ONE)
                .asConstraint(TEST_CONSTRAINT_NAME)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Integer.class.getCanonicalName())
                .hasMessageContaining("assignable from");
    }




    @Override
    @TestTemplate
    public void ifNotExists_0Joiner0Filter() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 1, 1);
        TestdataLavishValueGroup valueGroup = new TestdataLavishValueGroup("MyValueGroup");


        solution.getValueGroupList().add(valueGroup);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =





                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishValueGroup.class)






                        .ifNotExists(TestdataLavishEntityGroup.class)
                        .penalize(SimpleScore.ONE)


                        .asConstraint(TEST_CONSTRAINT_NAME));






        // From scratch














        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector);

        // Incremental



        TestdataLavishEntityGroup entityGroup = solution.getFirstEntityGroup();




        scoreDirector.beforeProblemFactRemoved(entityGroup);
        solution.getEntityGroupList().remove(entityGroup);
        scoreDirector.afterProblemFactRemoved(entityGroup);


        assertScore(scoreDirector,







                assertMatch(valueGroup, solution.getFirstValueGroup()));



    }




    @Override
    @TestTemplate




    public void ifNotExists_0Join1Filter() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);


        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");
        solution.getEntityGroupList().add(entityGroup);





        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());

        solution.getEntityList().add(entity1);



        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                solution.getFirstValue());



        solution.getEntityList().add(entity2);







        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .ifNotExists(TestdataLavishEntityGroup.class,
                                filtering((entityA, entityB, group) -> Objects.equals(group, entityA.getEntityGroup()) &&
                                        Objects.equals(group, entityB.getEntityGroup())))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));


















        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,




                assertMatch(solution.getFirstEntity(), entity1),










                assertMatch(entity1, entity2));
















        // Incremental
        TestdataLavishEntityGroup toRemove = solution.getFirstEntityGroup();

        scoreDirector.beforeProblemFactRemoved(toRemove);
        solution.getEntityGroupList().remove(toRemove);








        scoreDirector.afterProblemFactRemoved(toRemove);
        assertScore(scoreDirector,

                assertMatch(solution.getFirstEntity(), entity1),







                assertMatch(solution.getFirstEntity(), entity2),


                assertMatch(entity1, entity2));


    }


    @Override



    @TestTemplate
    public void ifNotExists_1Join0Filter() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");




        solution.getEntityGroupList().add(entityGroup);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());
        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                solution.getFirstValue());
        solution.getEntityList().add(entity2);




        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .ifNotExists(TestdataLavishEntityGroup.class,




                                equal((entityA, entityB) -> entityA.getEntityGroup(), Function.identity()))





                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector);




        // Incremental



        scoreDirector.beforeProblemFactRemoved(entityGroup);
        solution.getEntityGroupList().remove(entityGroup);
        scoreDirector.afterProblemFactRemoved(entityGroup);
        assertScore(scoreDirector,





                assertMatch(entity1, entity2));










    }



    @Override
    @TestTemplate
    public void ifNotExists_1Join1Filter() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");






        solution.getEntityGroupList().add(entityGroup);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());



        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                solution.getFirstValue());
        solution.getEntityList().add(entity2);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .ifNotExists(TestdataLavishEntityGroup.class,














                                equal((entityA, entityB) -> entityA.getEntityGroup(), Function.identity()),
                                filtering((entityA, entityB, group) -> entityA.getCode().contains("MyEntity") ||
                                        group.getCode().contains("MyEntity")))
                        .penalize(SimpleScore.ONE)


                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch









        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), entity1),
                assertMatch(solution.getFirstEntity(), entity2));







        // Incremental






        scoreDirector.beforeProblemFactRemoved(entityGroup);
        solution.getEntityGroupList().remove(entityGroup);
















        scoreDirector.afterProblemFactRemoved(entityGroup);


        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), entity1),
                assertMatch(solution.getFirstEntity(), entity2),
                assertMatch(entity1, entity2));
    }

    @Override
    @TestTemplate
    public void ifNotExistsDoesNotIncludeUnassigned() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");
        solution.getEntityGroupList().add(entityGroup);






        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());
        solution.getEntityList().add(entity1);







        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),








                null);


        solution.getEntityList().add(entity2);




        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .ifNotExists(TestdataLavishEntity.class,
                                filtering((a, b, c) -> a != c && b != c))





                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));










        // From scratch






        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), entity1));

















        // Incremental




        scoreDirector.beforeProblemFactRemoved(entity2);
        solution.getEntityList().remove(entity2);



        scoreDirector.afterProblemFactRemoved(entity2);

        assertScore(scoreDirector,



                assertMatch(solution.getFirstEntity(), entity1));
    }




















    @Override
    @TestTemplate
    @Deprecated(forRemoval = true)
    public void ifNotExistsIncludesNullVarsWithFrom() {



        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);













        TestdataLavishEntityGroup entityGroup = new TestdataLavishEntityGroup("MyEntityGroup");




        solution.getEntityGroupList().add(entityGroup);
        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup, solution.getFirstValue());


        solution.getEntityList().add(entity1);
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),



                null);



        solution.getEntityList().add(entity2);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.fromUniquePair(TestdataLavishEntity.class)
                        .ifNotExists(TestdataLavishEntity.class,
                                filtering((a, b, c) -> a != c && b != c))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));





        // From scratch











        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector);




        // Incremental
        scoreDirector.beforeProblemFactRemoved(entity2);
        solution.getEntityList().remove(entity2);
        scoreDirector.afterProblemFactRemoved(entity2);
        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), entity1));







    }



    @Override
    @TestTemplate




    public void ifExistsAfterGroupBy() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 0, 1, 0);







        TestdataLavishValue value1 = new TestdataLavishValue("MyValue 1", solution.getFirstValueGroup());
        solution.getValueList().add(value1);










        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        solution.getValueList().add(value2);



        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", solution.getFirstEntityGroup(), value1);


        solution.getEntityList().add(entity1);



        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(), value1);





        solution.getEntityList().add(entity2);
        TestdataLavishExtra extra1 = new TestdataLavishExtra("MyExtra 1");
        solution.getExtraList().add(extra1);
        TestdataLavishExtra extra2 = new TestdataLavishExtra("MyExtra 2");
        solution.getExtraList().add(extra2);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .groupBy(countDistinct(TestdataLavishEntity::getValue),
















                                countDistinct(TestdataLavishEntity::getValue))




                        .ifExists(TestdataLavishExtra.class)




                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch



        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(1, 1));





        // Incremental
        scoreDirector.beforeVariableChanged(entity2, "value");

        entity2.setValue(value2);
        scoreDirector.afterVariableChanged(entity2, "value");
        assertScore(scoreDirector,













                assertMatch(2, 2));

        // Incremental
        scoreDirector.beforeEntityRemoved(entity2);

        solution.getEntityList().remove(entity2);
        scoreDirector.afterEntityRemoved(entity2);
        assertScore(scoreDirector,
                assertMatch(1, 1));
    }

    // ************************************************************************


    // Group by
    // ************************************************************************



    @Override



    @TestTemplate






    public void groupBy_1Mapping0Collector() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 7);
        TestdataLavishEntityGroup entityGroup1 = new TestdataLavishEntityGroup("MyEntityGroup");


        solution.getEntityGroupList().add(entityGroup1);


        TestdataLavishEntity entity1 = new TestdataLavishEntity("MyEntity 1", entityGroup1, solution.getFirstValue());
        solution.getEntityList().add(entity1);






        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", entityGroup1, solution.getFirstValue());


        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),




                solution.getFirstValue());
        solution.getEntityList().add(entity3);









































        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class, equal(TestdataLavishEntity::getEntityGroup))



                        .groupBy((entityA, entityB) -> entityA.getEntityGroup())






                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch






        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, solution.getFirstEntityGroup()),
                assertMatchWithScore(-1, entityGroup1));

        // Incremental
        Stream.of(entity1, entity2).forEach(entity -> {






















            scoreDirector.beforeEntityRemoved(entity);
            solution.getEntityList().remove(entity);


            scoreDirector.afterEntityRemoved(entity);
        });











        assertScore(scoreDirector, assertMatchWithScore(-1, solution.getFirstEntityGroup()));
    }

    @Override


    @TestTemplate
    public void groupBy_1Mapping1Collector() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 3, 7);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)















                        .groupBy((entityA, entityB) -> entityA.toString(), countBi())
                        .filter((entity, count) -> count > 4)
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));







        // From scratch

        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, TEST_CONSTRAINT_NAME, solution.getFirstEntity().toString(), 6),






                assertMatchWithScore(-1, TEST_CONSTRAINT_NAME, solution.getEntityList().get(1).toString(), 5));













        // Incremental; we have a new first entity, and less entities in total.
        TestdataLavishEntity entity = solution.getFirstEntity();
        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);
        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, TEST_CONSTRAINT_NAME, solution.getFirstEntity().toString(), 5));
    }

    @Override
    @TestTemplate
    public void groupBy_1Mapping2Collector() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =





                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .groupBy((entityA, entityB) -> entityA.toString(),
                                countBi(),
                                toSet((entityA, entityB) -> entityA))
                        .penalize(SimpleScore.ONE)


                        .asConstraint(TEST_CONSTRAINT_NAME));


        TestdataLavishEntity entity1 = solution.getFirstEntity();









        TestdataLavishEntity entity2 = solution.getEntityList().get(1);




        // From scratch
        scoreDirector.setWorkingSolution(solution);


        assertScore(scoreDirector,




                assertMatchWithScore(-1, TEST_CONSTRAINT_NAME, entity1.toString(), 2, singleton(entity1)),




                assertMatchWithScore(-1, TEST_CONSTRAINT_NAME, entity2.toString(), 1, singleton(entity2)));



        // Incremental
        TestdataLavishEntity entity = solution.getFirstEntity();
        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);


        scoreDirector.afterEntityRemoved(entity);










        assertScore(scoreDirector,



                assertMatchWithScore(-1, TEST_CONSTRAINT_NAME, entity2.toString(), 1, singleton(entity2)));
    }


    @Override
    @TestTemplate
    public void groupBy_1Mapping3Collector() {










        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);







        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .groupBy((entityA, entityB) -> entityA.toString(),
                                min((TestdataLavishEntity entityA, TestdataLavishEntity entityB) -> entityA.getLongProperty()),





                                max((TestdataLavishEntity entityA, TestdataLavishEntity entityB) -> entityA.getLongProperty()),
                                toSet((entityA, entityB) -> entityA))








                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));




        TestdataLavishEntity entity1 = solution.getFirstEntity();
        entity1.setLongProperty(Long.MAX_VALUE);













        TestdataLavishEntity entity2 = solution.getEntityList().get(1);

        entity2.setLongProperty(Long.MIN_VALUE);



        // From scratch

        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, TEST_CONSTRAINT_NAME, entity1.toString(), Long.MAX_VALUE, Long.MAX_VALUE,







                        singleton(entity1)),
                assertMatchWithScore(-1, TEST_CONSTRAINT_NAME, entity2.toString(), Long.MIN_VALUE, Long.MIN_VALUE,
                        singleton(entity2)));

        // Incremental

        TestdataLavishEntity entity = solution.getFirstEntity();





        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);


        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, TEST_CONSTRAINT_NAME, entity2.toString(), Long.MIN_VALUE, Long.MIN_VALUE,






                        singleton(entity2)));
    }



    @Override
    @TestTemplate
    public void groupBy_0Mapping1Collector() {
















        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 2, 3);














        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =



                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)



                        .groupBy(countBi())





                        .penalize(SimpleScore.ONE, (count) -> count)
                        .asConstraint(TEST_CONSTRAINT_NAME));






        // From scratch










        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector, assertMatchWithScore(-3, 3));



        // Incremental
        TestdataLavishEntity entity = solution.getFirstEntity();
        scoreDirector.beforeEntityRemoved(entity);









        solution.getEntityList().remove(entity);












        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector, assertMatchWithScore(-1, 1));



    }





    @Override


    @TestTemplate















    public void groupBy_0Mapping2Collector() {


        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);



        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .groupBy(countBi(),
                                countDistinct((e, e2) -> e))
                        .penalize(SimpleScore.ONE)



                        .asConstraint(TEST_CONSTRAINT_NAME));








        TestdataLavishEntity entity1 = solution.getFirstEntity();










        // From scratch




        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector, assertMatchWithScore(-1, 3, 2));

        // Incremental
        scoreDirector.beforeEntityRemoved(entity1);
        solution.getEntityList().remove(entity1);
        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector, assertMatchWithScore(-1, 1, 1));
    }

    @Override
    @TestTemplate



    public void groupBy_0Mapping3Collector() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =



                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)

                        .groupBy(countBi(),





                                min((TestdataLavishEntity e, TestdataLavishEntity e2) -> e.getLongProperty()),



                                max((TestdataLavishEntity e, TestdataLavishEntity e2) -> e.getLongProperty()))




                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));












        TestdataLavishEntity entity1 = solution.getFirstEntity();
        entity1.setLongProperty(0L);
        TestdataLavishEntity entity2 = solution.getEntityList().get(1);



        entity2.setLongProperty(1L);








        TestdataLavishEntity entity3 = solution.getEntityList().get(2);
        entity3.setLongProperty(2L);










        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, 3, 0L, 1L));

        // Incremental
        scoreDirector.beforeEntityRemoved(entity1);




        solution.getEntityList().remove(entity1);
        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector,






                assertMatchWithScore(-1, 1, 1L, 1L));











    }











    @Override
    @TestTemplate
    public void groupBy_0Mapping4Collector() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =





                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .groupBy(countBi(),
                                min((TestdataLavishEntity e, TestdataLavishEntity e2) -> e.getLongProperty()),
                                max((TestdataLavishEntity e, TestdataLavishEntity e2) -> e.getLongProperty()),




                                toSet((e, e2) -> e))





                        .penalize(SimpleScore.ONE)





                        .asConstraint(TEST_CONSTRAINT_NAME));

        TestdataLavishEntity entity1 = solution.getFirstEntity();
        entity1.setLongProperty(0L);



        TestdataLavishEntity entity2 = solution.getEntityList().get(1);
        entity2.setLongProperty(1L);
        TestdataLavishEntity entity3 = solution.getEntityList().get(2);
        entity3.setLongProperty(2L);
















        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, 3, 0L, 1L, asSet(entity1, entity2)));

        // Incremental
        scoreDirector.beforeEntityRemoved(entity1);
        solution.getEntityList().remove(entity1);
        scoreDirector.afterEntityRemoved(entity1);



        assertScore(scoreDirector,
                assertMatchWithScore(-1, 1, 1L, 1L, asSet(entity2)));



    }




    @Override




    @TestTemplate





    public void groupBy_2Mapping0Collector() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 3, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =



                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .groupBy((a, b) -> a.getEntityGroup(), (a, b) -> b.getEntityGroup())
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        TestdataLavishEntityGroup group1 = solution.getEntityGroupList().get(0);
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);
        TestdataLavishEntityGroup group3 = solution.getEntityGroupList().get(2);

        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, group1, group2),





                assertMatchWithScore(-1, group1, group3),
                assertMatchWithScore(-1, group2, group3));







        // Incremental
        TestdataLavishEntity entity = solution.getFirstEntity();
        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);






        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, group2, group3));
    }







    @Override
    @TestTemplate




    public void groupBy_2Mapping1Collector() {










        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 4);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =



                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)







                        .groupBy((a, b) -> a.getEntityGroup(), (a, b) -> b.getEntityGroup(), countBi())
                        .penalize(SimpleScore.ONE, (entityGroup1, entityGroup2, count) -> count)









                        .asConstraint(TEST_CONSTRAINT_NAME));







        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);

        // From scratch
        scoreDirector.setWorkingSolution(solution);




        assertScore(scoreDirector,
                assertMatchWithScore(-1, group1, group1, 1),
                assertMatchWithScore(-3, group1, group2, 3),










                assertMatchWithScore(-1, group2, group1, 1),







                assertMatchWithScore(-1, group2, group2, 1));










        // Incremental
        TestdataLavishEntity entity = solution.getFirstEntity();
        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);


        scoreDirector.afterEntityRemoved(entity);





        assertScore(scoreDirector,
                assertMatchWithScore(-1, group2, group2, 1),


                assertMatchWithScore(-1, group1, group2, 1),
                assertMatchWithScore(-1, group2, group1, 1));
    }

    @Override
    @TestTemplate




    public void groupBy_2Mapping2Collector() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 4);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)






                        .groupBy((a, b) -> a.getEntityGroup(), (a, b) -> b.getEntityGroup(), countBi(), countBi())
                        .penalize(SimpleScore.ONE,
                                (entityGroup1, entityGroup2, count, sameCount) -> count + sameCount)








                        .asConstraint(TEST_CONSTRAINT_NAME));






        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();



        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);

        // From scratch











        scoreDirector.setWorkingSolution(solution);


        assertScore(scoreDirector,
                assertMatchWithScore(-2, group1, group1, 1, 1),





                assertMatchWithScore(-6, group1, group2, 3, 3),


                assertMatchWithScore(-2, group2, group1, 1, 1),
                assertMatchWithScore(-2, group2, group2, 1, 1));












        // Incremental



        TestdataLavishEntity entity = solution.getFirstEntity();
        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);
        scoreDirector.afterEntityRemoved(entity);





        assertScore(scoreDirector,
                assertMatchWithScore(-2, group2, group2, 1, 1),
                assertMatchWithScore(-2, group1, group2, 1, 1),
                assertMatchWithScore(-2, group2, group1, 1, 1));








    }




    @Override





    @TestTemplate
    public void groupBy_3Mapping0Collector() {











        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 2, 3, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .groupBy((a, b) -> a.getEntityGroup(), (a, b) -> b.getEntityGroup(), (a, b) -> a.getValue())





                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));




        TestdataLavishEntityGroup group1 = solution.getEntityGroupList().get(0);
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);
        TestdataLavishEntityGroup group3 = solution.getEntityGroupList().get(2);
        TestdataLavishValue value1 = solution.getValueList().get(0);
        TestdataLavishValue value2 = solution.getValueList().get(1);

        // From scratch





        scoreDirector.setWorkingSolution(solution);






        assertScore(scoreDirector,
                assertMatchWithScore(-1, group1, group2, value1),





                assertMatchWithScore(-1, group1, group3, value1),
                assertMatchWithScore(-1, group2, group3, value2));

        // Incremental
        TestdataLavishEntity entity = solution.getFirstEntity();



        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);





















        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector,
                assertMatchWithScore(-1, group2, group3, value2));



    }




    @Override
    @TestTemplate











    public void groupBy_3Mapping1Collector() {






        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 2, 3, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =










                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)




                        .groupBy((a, b) -> a.getEntityGroup(), (a, b) -> b.getEntityGroup(), (a, b) -> a.getValue(),






                                ConstraintCollectors.countBi())



                        .penalize(SimpleScore.ONE)









                        .asConstraint(TEST_CONSTRAINT_NAME));









        TestdataLavishEntityGroup group1 = solution.getEntityGroupList().get(0);
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);





        TestdataLavishEntityGroup group3 = solution.getEntityGroupList().get(2);



        TestdataLavishValue value1 = solution.getValueList().get(0);
        TestdataLavishValue value2 = solution.getValueList().get(1);

        // From scratch

        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,


                assertMatchWithScore(-1, group1, group2, value1, 1),








                assertMatchWithScore(-1, group1, group3, value1, 1),
                assertMatchWithScore(-1, group2, group3, value2, 1));

        // Incremental

        TestdataLavishEntity entity = solution.getFirstEntity();


        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);
        scoreDirector.afterEntityRemoved(entity);









        assertScore(scoreDirector,











                assertMatchWithScore(-1, group2, group3, value2, 1));
    }

    @Override


    @TestTemplate
    public void groupBy_4Mapping0Collector() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 2, 3, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)





                        .groupBy((a, b) -> a.getEntityGroup(), (a, b) -> b.getEntityGroup(), (a, b) -> a.getValue(),
                                (a, b) -> b.getValue())


                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));




        TestdataLavishEntityGroup group1 = solution.getEntityGroupList().get(0);


        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);


        TestdataLavishEntityGroup group3 = solution.getEntityGroupList().get(2);
        TestdataLavishValue value1 = solution.getValueList().get(0);
        TestdataLavishValue value2 = solution.getValueList().get(1);






        // From scratch


        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,





                assertMatchWithScore(-1, group1, group2, value1, value2),
                assertMatchWithScore(-1, group1, group3, value1, value1),








                assertMatchWithScore(-1, group2, group3, value2, value1));

        // Incremental
        TestdataLavishEntity entity = solution.getFirstEntity();
        scoreDirector.beforeEntityRemoved(entity);




        solution.getEntityList().remove(entity);
        scoreDirector.afterEntityRemoved(entity);



        assertScore(scoreDirector,
                assertMatchWithScore(-1, group2, group3, value2, value1));
    }

    // ************************************************************************
    // Map/flatten/distinct/concat













    // ************************************************************************



    @Override
    @TestTemplate
    public void distinct() { // On a distinct stream, this is a no-op.






        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 2, 2, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)


                        .distinct()









                        .penalize(SimpleScore.ONE)

                        .asConstraint(TEST_CONSTRAINT_NAME));




        TestdataLavishEntity entity1 = solution.getFirstEntity();






        TestdataLavishEntity entity2 = solution.getEntityList().get(1);
        TestdataLavishEntity entity3 = solution.getEntityList().get(2);










        // From scratch
        scoreDirector.setWorkingSolution(solution);








        assertScore(scoreDirector,
                assertMatch(entity1, entity2),
                assertMatch(entity1, entity3),
                assertMatch(entity2, entity3));
    }

    @Override
    @TestTemplate


    public void mapToUniWithDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);



        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .map((a, b) -> asSet(a.getEntityGroup(), b.getEntityGroup())) // 3 entities, 2 groups => duplicates.





                        .penalize(SimpleScore.ONE)





                        .asConstraint(TEST_CONSTRAINT_NAME));




















        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();



        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);








        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,






                assertMatch(asSet(group1, group2)),



                assertMatch(asSet(group1, group2)),












                assertMatch(asSet(group1)));


        TestdataLavishEntity entity = solution.getFirstEntity();







        // Incremental
        scoreDirector.beforeEntityRemoved(entity);



        solution.getEntityList().remove(entity);
        scoreDirector.afterEntityRemoved(entity);





        assertScore(scoreDirector,
                assertMatch(asSet(group1, group2)));








    }



    @Override


    @TestTemplate



    public void mapToUniWithoutDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 3, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .map((a, b) -> asSet(a.getEntityGroup(), b.getEntityGroup())) // 3 entities, 3 groups => no duplicates.
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);
        TestdataLavishEntityGroup group3 = solution.getEntityGroupList().get(2);











        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(asSet(group1, group2)),
                assertMatch(asSet(group1, group3)),
                assertMatch(asSet(group2, group3)));

































        TestdataLavishEntity entity = solution.getFirstEntity();

        // Incremental




        scoreDirector.beforeEntityRemoved(entity);

        solution.getEntityList().remove(entity);





        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector,
                assertMatch(asSet(group2, group3)));







    }
















    @Override


    @TestTemplate
    public void mapToUniAndDistinctWithDuplicates() {



        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =



                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)






                        .map((a, b) -> asSet(a.getEntityGroup(), b.getEntityGroup())) // 3 entities, 2 groups => duplicates.





                        .distinct() // Duplicate copies removed here.



                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));



        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);

        // From scratch





        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(asSet(group1, group2)),












                assertMatch(asSet(group1)));




        TestdataLavishEntity entity = solution.getFirstEntity();






        // Incremental
        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);







        scoreDirector.afterEntityRemoved(entity);




        assertScore(scoreDirector,


                assertMatch(asSet(group1, group2)));






    }




    @Override
    @TestTemplate
    public void mapToUniAndDistinctWithoutDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 3, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .map((a, b) -> asSet(a.getEntityGroup(), b.getEntityGroup())) // 3 entities, 3 groups => no duplicates.





                        .distinct()



                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();


        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);
        TestdataLavishEntityGroup group3 = solution.getEntityGroupList().get(2);






        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(asSet(group1, group2)),
                assertMatch(asSet(group1, group3)),
                assertMatch(asSet(group2, group3)));



        TestdataLavishEntity entity = solution.getFirstEntity();





        // Incremental
        scoreDirector.beforeEntityRemoved(entity);




        solution.getEntityList().remove(entity);

        scoreDirector.afterEntityRemoved(entity);



        assertScore(scoreDirector,
                assertMatch(asSet(group2, group3)));












    }









    @Override


    @TestTemplate
    public void mapToBi() {


        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 2, 2, 3);






        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)


                        .map((a, b) -> a.getEntityGroup(),


























                                (a, b) -> b.getEntityGroup())
                        .penalize(SimpleScore.ONE)












                        .asConstraint(TEST_CONSTRAINT_NAME));





        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();






        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);








        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(group1, group2),
                assertMatch(group2, group1),
                assertMatch(group1, group1));

        TestdataLavishEntity entity = solution.getFirstEntity();

        // Incremental



        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);




        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector,





                assertMatch(group2, group1));



    }

    @Override
    @TestTemplate
    public void mapToTri() {




        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 2, 2, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)







                        .map((a, b) -> a.getEntityGroup(),
                                (a, b) -> b.getEntityGroup(),








                                (a, b) -> a.getLongProperty() + b.getLongProperty())

















                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));







        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();




        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);




        long sum01 = solution.getFirstEntity().getLongProperty() + solution.getEntityList().get(1).getLongProperty();
        long sum02 = solution.getFirstEntity().getLongProperty() + solution.getEntityList().get(2).getLongProperty();


        long sum12 = solution.getEntityList().get(1).getLongProperty() + solution.getEntityList().get(2).getLongProperty();

        // From scratch
        scoreDirector.setWorkingSolution(solution);




        assertScore(scoreDirector,
                assertMatch(group1, group2, sum01),
                assertMatch(group2, group1, sum12),



                assertMatch(group1, group1, sum02));

        TestdataLavishEntity entity = solution.getFirstEntity();










        // Incremental
        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);
        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector,



                assertMatch(group2, group1, sum12));
    }

    @Override


    @TestTemplate





    public void mapToQuad() {



        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 2, 2, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =




                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)



                        .map((a, b) -> a.getEntityGroup(),





                                (a, b) -> b.getEntityGroup(),
                                (a, b) -> a.getValue(),

                                (a, b) -> b.getValue())
                        .penalize(SimpleScore.ONE)




                        .asConstraint(TEST_CONSTRAINT_NAME));






        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();












        TestdataLavishValue value1 = solution.getFirstValue();
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);




        TestdataLavishValue value2 = solution.getValueList().get(1);


        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(group1, group2, value1, value2),
                assertMatch(group2, group1, value2, value1),
                assertMatch(group1, group1, value1, value1));

        TestdataLavishEntity entity = solution.getFirstEntity();

        // Incremental
        scoreDirector.beforeEntityRemoved(entity);






        solution.getEntityList().remove(entity);
        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector,
                assertMatch(group2, group1, value2, value1));




    }






    @Override
    @TestTemplate
    public void expandToTri() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 2, 2, 3);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .expand((a, b) -> a.getLongProperty() + b.getLongProperty())









                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));


















        long sum01 = solution.getFirstEntity().getLongProperty() + solution.getEntityList().get(1).getLongProperty();














        long sum02 = solution.getFirstEntity().getLongProperty() + solution.getEntityList().get(2).getLongProperty();
        long sum12 = solution.getEntityList().get(1).getLongProperty() + solution.getEntityList().get(2).getLongProperty();





        // From scratch



        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), solution.getEntityList().get(1), sum01),
                assertMatch(solution.getEntityList().get(1), solution.getEntityList().get(2), sum12),
                assertMatch(solution.getFirstEntity(), solution.getEntityList().get(2), sum02));





        TestdataLavishEntity entity = solution.getFirstEntity();

        // Incremental



        scoreDirector.beforeEntityRemoved(entity);
        solution.getEntityList().remove(entity);
        scoreDirector.afterEntityRemoved(entity);



        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), solution.getEntityList().get(1), sum12));
    }















    @Override
    @TestTemplate
    public void expandToQuad() {

        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 2, 2, 3);














        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =


                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .expand((a, b) -> a.getLongProperty() + b.getLongProperty(),
                                (a, b) -> a.getEntityGroup().getCode() + b.getEntityGroup().getCode())





                        .penalize(SimpleScore.ONE)




                        .asConstraint(TEST_CONSTRAINT_NAME));







        long sum01 = solution.getFirstEntity().getLongProperty() + solution.getEntityList().get(1).getLongProperty();
        long sum02 = solution.getFirstEntity().getLongProperty() + solution.getEntityList().get(2).getLongProperty();
        long sum12 = solution.getEntityList().get(1).getLongProperty() + solution.getEntityList().get(2).getLongProperty();
        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);



        String concat01 = group1.getCode() + group2.getCode();



        String concat02 = group1.getCode() + group1.getCode();




        String concat12 = group2.getCode() + group1.getCode();


















        // From scratch
        scoreDirector.setWorkingSolution(solution);








        assertScore(scoreDirector,











                assertMatch(solution.getFirstEntity(), solution.getEntityList().get(1), sum01, concat01),
                assertMatch(solution.getEntityList().get(1), solution.getEntityList().get(2), sum12, concat12),
                assertMatch(solution.getFirstEntity(), solution.getEntityList().get(2), sum02, concat02));

        TestdataLavishEntity entity = solution.getFirstEntity();




        // Incremental
        scoreDirector.beforeEntityRemoved(entity);













        solution.getEntityList().remove(entity);
        scoreDirector.afterEntityRemoved(entity);
        assertScore(scoreDirector,
                assertMatch(solution.getFirstEntity(), solution.getEntityList().get(1), sum12, concat12));
    }








    @Override
    @TestTemplate


    public void flattenLastWithDuplicates() {






        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);



        TestdataLavishEntity entity1 = solution.getFirstEntity();








        TestdataLavishEntity entity2 = solution.getEntityList().get(1);








        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);





        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .flattenLast(b -> asList(b.getEntityGroup(), group1, group2))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));





        // From scratch





        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, group1),
                assertMatch(entity1, group1),





                assertMatch(entity1, group2),
                assertMatch(entity2, group2),





                assertMatch(entity2, group1),


                assertMatch(entity2, group2),
                assertMatch(entity1, group1),
                assertMatch(entity1, group1),









                assertMatch(entity1, group2));






        // Incremental








        scoreDirector.beforeEntityRemoved(entity1);



        solution.getEntityList().remove(entity1);
        scoreDirector.afterEntityRemoved(entity1);






        assertScore(scoreDirector,
                assertMatch(entity2, group2),
                assertMatch(entity2, group1),
                assertMatch(entity2, group2));
    }






    @Override

    @TestTemplate


    public void flattenLastWithoutDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);
        TestdataLavishEntity entity1 = solution.getFirstEntity();
        TestdataLavishEntity entity2 = solution.getEntityList().get(1);
        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .flattenLast(b -> singleton(b.getEntityGroup()))




                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));



        // From scratch
        scoreDirector.setWorkingSolution(solution);










        assertScore(scoreDirector,
                assertMatch(entity2, group1),
                assertMatch(entity1, group2),
                assertMatch(entity1, group1));










        // Incremental
        scoreDirector.beforeEntityRemoved(entity1);

        solution.getEntityList().remove(entity1);









        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector,



                assertMatch(entity2, group1));
    }

    @Override



    @TestTemplate










    public void flattenLastAndDistinctWithDuplicates() {




        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);
        TestdataLavishEntity entity1 = solution.getFirstEntity();














        TestdataLavishEntity entity2 = solution.getEntityList().get(1);
        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);



        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)




                        .flattenLast(b -> asList(b.getEntityGroup(), group1, group2))
                        .distinct()



                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));




        // From scratch


        scoreDirector.setWorkingSolution(solution);




        assertScore(scoreDirector,
                assertMatch(entity1, group1),
                assertMatch(entity1, group2),



                assertMatch(entity2, group2),

                assertMatch(entity2, group1));

        // Incremental
        scoreDirector.beforeEntityRemoved(entity1);








        solution.getEntityList().remove(entity1);
        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector,
                assertMatch(entity2, group1),






                assertMatch(entity2, group2));

    }



    @Override








    @TestTemplate
    public void flattenLastAndDistinctWithoutDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 1, 2, 3);

        TestdataLavishEntity entity1 = solution.getFirstEntity();
        TestdataLavishEntity entity2 = solution.getEntityList().get(1);
        TestdataLavishEntityGroup group1 = solution.getFirstEntityGroup();
        TestdataLavishEntityGroup group2 = solution.getEntityGroupList().get(1);




        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =



                buildScoreDirector(factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .flattenLast(b -> singleton(b.getEntityGroup()))



                        .distinct()
                        .penalize(SimpleScore.ONE)









                        .asConstraint(TEST_CONSTRAINT_NAME));





        // From scratch






        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,








                assertMatch(entity2, group1),
                assertMatch(entity1, group2),





                assertMatch(entity1, group1));






        // Incremental
        scoreDirector.beforeEntityRemoved(entity1);
        solution.getEntityList().remove(entity1);









        scoreDirector.afterEntityRemoved(entity1);
        assertScore(scoreDirector,









                assertMatch(entity2, group1));




    }




    @Override






    @TestTemplate
    public void concatUniWithoutValueDuplicates() {








        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);





        TestdataLavishValue value1 = solution.getFirstValue();



        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());












        TestdataLavishEntity entity1 = solution.getFirstEntity();







        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                value2);
        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),







                value3);







        solution.getEntityList().add(entity3);







        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .filter(entity -> entity.getValue() == value1)
                        .join(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value2))













                        .concat(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value2))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));






        // From scratch


        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,









                assertMatch(entity1, entity2),
                assertMatch(entity2, null));

        // Incremental
        scoreDirector.beforeVariableChanged(entity3, "value");







        entity3.setValue(value2);
        scoreDirector.afterVariableChanged(entity3, "value");

        scoreDirector.beforeVariableChanged(entity2, "value");
        entity2.setValue(value3);

        scoreDirector.afterVariableChanged(entity2, "value");
        assertScore(scoreDirector,





                assertMatch(entity1, entity3),


                assertMatch(entity3, null));

    }

    @Override
    @TestTemplate
    public void concatAndDistinctUniWithoutValueDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishValue value1 = solution.getFirstValue();




        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());
        TestdataLavishEntity entity1 = solution.getFirstEntity();
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                value2);





        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),









                value3);
        solution.getEntityList().add(entity3);



        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .filter(entity -> entity.getValue() == value1)
                        .join(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value2))
                        .concat(factory.forEach(TestdataLavishEntity.class)



                                .filter(entity -> entity.getValue() == value2))
                        .distinct()



                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));



        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,




                assertMatch(entity1, entity2),


                assertMatch(entity2, null));





        // Incremental
        scoreDirector.beforeVariableChanged(entity3, "value");
        entity3.setValue(value2);



        scoreDirector.afterVariableChanged(entity3, "value");

        scoreDirector.beforeVariableChanged(entity2, "value");
        entity2.setValue(value3);
        scoreDirector.afterVariableChanged(entity2, "value");







        assertScore(scoreDirector,

                assertMatch(entity1, entity3),









                assertMatch(entity3, null));





    }

    @Override
    @TestTemplate


    public void concatBiWithoutValueDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishValue value1 = solution.getFirstValue();




        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());
        TestdataLavishEntity entity1 = solution.getFirstEntity();
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                value2);
        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),
                value3);
        solution.getEntityList().add(entity3);








        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =

                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .filter(entity -> entity.getValue() == value1)





                        .join(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value2))
                        .concat(factory.forEach(TestdataLavishEntity.class)


                                .filter(entity -> entity.getValue() == value2)
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value3)))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));






        // From scratch
        scoreDirector.setWorkingSolution(solution);


        assertScore(scoreDirector,
                assertMatch(entity1, entity2),




                assertMatch(entity2, entity3));





        // Incremental
        scoreDirector.beforeVariableChanged(entity3, "value");





        entity3.setValue(value2);
        scoreDirector.afterVariableChanged(entity3, "value");






        scoreDirector.beforeVariableChanged(entity2, "value");
        entity2.setValue(value3);


        scoreDirector.afterVariableChanged(entity2, "value");
        assertScore(scoreDirector,
                assertMatch(entity1, entity3),
                assertMatch(entity3, entity2));
    }

    @Override
    @TestTemplate


    public void concatBiWithValueDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);



        TestdataLavishValue value1 = solution.getFirstValue();

        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());
        TestdataLavishEntity entity1 = solution.getFirstEntity();





        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                value2);







        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),





                value3);



        solution.getEntityList().add(entity3);






        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =












                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .filter(entity -> entity.getValue() == value1)
                        .join(factory.forEach(TestdataLavishEntity.class)



                                .filter(entity -> entity.getValue() == value2))
                        .concat(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value1)
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value2)))




                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch






        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,


                assertMatch(entity1, entity2),





                assertMatch(entity1, entity2));

        // Incremental
        scoreDirector.beforeVariableChanged(entity3, "value");
        entity3.setValue(value2);
        scoreDirector.afterVariableChanged(entity3, "value");

        scoreDirector.beforeVariableChanged(entity2, "value");
        entity2.setValue(value3);
        scoreDirector.afterVariableChanged(entity2, "value");

        assertScore(scoreDirector,



                assertMatch(entity1, entity3),
                assertMatch(entity1, entity3));
    }

    @Override
    @TestTemplate
    public void concatAndDistinctBiWithoutValueDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishValue value1 = solution.getFirstValue();
        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());
        TestdataLavishEntity entity1 = solution.getFirstEntity();
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),



                value2);
        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),
                value3);
        solution.getEntityList().add(entity3);









        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .filter(entity -> entity.getValue() == value1)
                        .join(factory.forEach(TestdataLavishEntity.class)







                                .filter(entity -> entity.getValue() == value2))
                        .concat(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value2)
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value3)))
                        .distinct()
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, entity2),
                assertMatch(entity2, entity3));

        // Incremental
        scoreDirector.beforeVariableChanged(entity3, "value");





        entity3.setValue(value2);
        scoreDirector.afterVariableChanged(entity3, "value");







        scoreDirector.beforeVariableChanged(entity2, "value");





        entity2.setValue(value3);
        scoreDirector.afterVariableChanged(entity2, "value");

















        assertScore(scoreDirector,
                assertMatch(entity1, entity3),
                assertMatch(entity3, entity2));
    }

    @Override
    @TestTemplate
    public void concatAndDistinctBiWithValueDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishValue value1 = solution.getFirstValue();

        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());
        TestdataLavishEntity entity1 = solution.getFirstEntity();

        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                value2);
        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),
                value3);




        solution.getEntityList().add(entity3);














        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)




                        .filter(entity -> entity.getValue() == value1)



                        .join(factory.forEach(TestdataLavishEntity.class)






                                .filter(entity -> entity.getValue() == value2))
                        .concat(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value1)
                                .join(factory.forEach(TestdataLavishEntity.class)




                                        .filter(entity -> entity.getValue() == value2)))
                        .distinct()
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));





        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, entity2));





        // Incremental
        scoreDirector.beforeVariableChanged(entity3, "value");

        entity3.setValue(value2);




        scoreDirector.afterVariableChanged(entity3, "value");







        scoreDirector.beforeVariableChanged(entity2, "value");
        entity2.setValue(value3);
        scoreDirector.afterVariableChanged(entity2, "value");
        assertScore(scoreDirector,



                assertMatch(entity1, entity3));
    }

    @Override


    @TestTemplate

    public void concatTriWithoutValueDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishValue value1 = solution.getFirstValue();
        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());
        TestdataLavishEntity entity1 = solution.getFirstEntity();
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),






                value2);
        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),
                value3);





        solution.getEntityList().add(entity3);



        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)

                        .filter(entity -> entity.getValue() == value1)
                        .join(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value2))








                        .concat(factory.forEach(TestdataLavishEntity.class)



                                .filter(entity -> entity.getValue() == value2)
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value3))
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value1)))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch


        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, entity2, null),




                assertMatch(entity2, entity3, entity1));

        // Incremental
        scoreDirector.beforeVariableChanged(entity3, "value");
        entity3.setValue(value2);
        scoreDirector.afterVariableChanged(entity3, "value");



        scoreDirector.beforeVariableChanged(entity2, "value");


        entity2.setValue(value3);
        scoreDirector.afterVariableChanged(entity2, "value");
        assertScore(scoreDirector,
                assertMatch(entity1, entity3, null),












                assertMatch(entity3, entity2, entity1));
    }





    @Override



    @TestTemplate
    public void concatAndDistinctTriWithoutValueDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishValue value1 = solution.getFirstValue();
        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());




        TestdataLavishEntity entity1 = solution.getFirstEntity();
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                value2);
        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),
                value3);







        solution.getEntityList().add(entity3);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =




                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)

                        .filter(entity -> entity.getValue() == value1)



                        .join(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value2))

                        .concat(factory.forEach(TestdataLavishEntity.class)


                                .filter(entity -> entity.getValue() == value2)
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value3))
                                .join(factory.forEach(TestdataLavishEntity.class)







                                        .filter(entity -> entity.getValue() == value1)))
                        .distinct()
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch




        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, entity2, null),
                assertMatch(entity2, entity3, entity1));

        // Incremental
        scoreDirector.beforeVariableChanged(entity3, "value");
        entity3.setValue(value2);
        scoreDirector.afterVariableChanged(entity3, "value");



        scoreDirector.beforeVariableChanged(entity2, "value");

        entity2.setValue(value3);
        scoreDirector.afterVariableChanged(entity2, "value");







        assertScore(scoreDirector,
                assertMatch(entity1, entity3, null),
                assertMatch(entity3, entity2, entity1));

    }

    @Override
    @TestTemplate
    public void concatQuadWithoutValueDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishValue value1 = solution.getFirstValue();
        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());
        TestdataLavishEntity entity1 = solution.getFirstEntity();











        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                value2);
        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),
                value3);




        solution.getEntityList().add(entity3);








        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .filter(entity -> entity.getValue() == value1)
                        .join(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value2))
                        .concat(factory.forEach(TestdataLavishEntity.class)



                                .filter(entity -> entity.getValue() == value2)
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value3))
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value1))
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value2)))
                        .penalize(SimpleScore.ONE)


                        .asConstraint(TEST_CONSTRAINT_NAME));




        // From scratch
        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,

                assertMatch(entity1, entity2, null, null),

                assertMatch(entity2, entity3, entity1, entity2));



        // Incremental



        scoreDirector.beforeVariableChanged(entity3, "value");
        entity3.setValue(value2);
        scoreDirector.afterVariableChanged(entity3, "value");

        scoreDirector.beforeVariableChanged(entity2, "value");
        entity2.setValue(value3);
        scoreDirector.afterVariableChanged(entity2, "value");
        assertScore(scoreDirector,
                assertMatch(entity1, entity3, null, null),





                assertMatch(entity3, entity2, entity1, entity3));
    }






    @Override
    @TestTemplate
    public void concatAndDistinctQuadWithoutValueDuplicates() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);










        TestdataLavishValue value1 = solution.getFirstValue();
        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());


        TestdataLavishEntity entity1 = solution.getFirstEntity();
        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),



                value2);
        solution.getEntityList().add(entity2);





        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),
                value3);
        solution.getEntityList().add(entity3);



        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)




                        .filter(entity -> entity.getValue() == value1)








                        .join(factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> entity.getValue() == value2))
                        .concat(factory.forEach(TestdataLavishEntity.class)



                                .filter(entity -> entity.getValue() == value2)








                                .join(factory.forEach(TestdataLavishEntity.class)

                                        .filter(entity -> entity.getValue() == value3))
                                .join(factory.forEach(TestdataLavishEntity.class)
                                        .filter(entity -> entity.getValue() == value1))
                                .join(factory.forEach(TestdataLavishEntity.class)



                                        .filter(entity -> entity.getValue() == value2)))
                        .distinct()




                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));











        // From scratch



        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entity1, entity2, null, null),
                assertMatch(entity2, entity3, entity1, entity2));




        // Incremental
        scoreDirector.beforeVariableChanged(entity3, "value");



        entity3.setValue(value2);
        scoreDirector.afterVariableChanged(entity3, "value");

        scoreDirector.beforeVariableChanged(entity2, "value");
        entity2.setValue(value3);
        scoreDirector.afterVariableChanged(entity2, "value");
        assertScore(scoreDirector,
                assertMatch(entity1, entity3, null, null),
                assertMatch(entity3, entity2, entity1, entity3));




    }

    @Override

    @TestTemplate
    public void concatAfterGroupBy() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(2, 5, 1, 1);
        TestdataLavishValue value1 = solution.getFirstValue();
        TestdataLavishValue value2 = new TestdataLavishValue("MyValue 2", solution.getFirstValueGroup());
        TestdataLavishValue value3 = new TestdataLavishValue("MyValue 3", solution.getFirstValueGroup());
        TestdataLavishEntity entity1 = solution.getFirstEntity();



        TestdataLavishEntity entity2 = new TestdataLavishEntity("MyEntity 2", solution.getFirstEntityGroup(),
                value2);
        solution.getEntityList().add(entity2);
        TestdataLavishEntity entity3 = new TestdataLavishEntity("MyEntity 3", solution.getFirstEntityGroup(),
                value3);


        solution.getEntityList().add(entity3);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .join(TestdataLavishEntity.class)





                        .filter((e1, e2) -> e1.getValue() == value1 && e2.getValue() == value2)
                        .groupBy((e1, e2) -> e1.getValue(),
                                (e1, e2) -> e2.getValue(),
                                ConstraintCollectors.countBi())
                        .concat(factory.forEach(TestdataLavishEntity.class)
                                .join(TestdataLavishEntity.class)
                                .filter((e1, e2) -> e1.getValue() == value2 && e2.getValue() == value3)
                                .groupBy((e1, e2) -> e1.getValue(),
                                        (e1, e2) -> e2.getValue(),

                                        ConstraintCollectors.countBi()))



                        .penalize(SimpleScore.ONE, (v1, v2, count) -> count)





                        .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch
        scoreDirector.setWorkingSolution(solution);


        assertScore(scoreDirector,
                assertMatchWithScore(-1, value1, value2, 1),
                assertMatchWithScore(-1, value2, value3, 1));




        // Incremental



        scoreDirector.beforeVariableChanged(entity3, "value");
        entity3.setValue(value2);
        scoreDirector.afterVariableChanged(entity3, "value");
        assertScore(scoreDirector,
                assertMatchWithScore(-2, value1, value2, 2));

        // Incremental for which the first change matches a join that doesn't survive the second change
        scoreDirector.beforeVariableChanged(entity1, "value");

        entity1.setValue(value3);



        scoreDirector.afterVariableChanged(entity1, "value");
        scoreDirector.beforeVariableChanged(entity3, "value");
        entity3.setValue(value1);
        scoreDirector.afterVariableChanged(entity3, "value");
        assertScore(scoreDirector,




                assertMatchWithScore(-1, value1, value2, 1),
                assertMatchWithScore(-1, value2, value3, 1));

    }

    // ************************************************************************
    // Penalize/reward






    // ************************************************************************

    @Override
    @TestTemplate
    public void penalizeUnweighted() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(-21));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void penalizeUnweightedLong() {


        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {
                        factory.forEachUniquePair(TestdataEntity.class)
                                .penalizeLong(SimpleLongScore.ONE)




                                .asConstraint(TEST_CONSTRAINT_NAME)
                });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();


        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(-21));
    }

    @Override
    @TestTemplate
    public void penalizeUnweightedBigDecimal() {
        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),






                        factory -> new Constraint[] {
                                factory.forEachUniquePair(TestdataEntity.class)
                                        .penalizeBigDecimal(SimpleBigDecimalScore.ONE)
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                        });

        scoreDirector.setWorkingSolution(solution);

        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(-21)));
    }

    private <Score_ extends Score<Score_>, Solution_, Entity_> void assertDefaultJustifications(
            InnerScoreDirector<Solution_, Score_> scoreDirector, List<Entity_> entityList) {
        if (!implSupport.isConstreamMatchEnabled())
            return;

        assertThat(scoreDirector.getIndictmentMap())
                .containsOnlyKeys(entityList);




        String constraintFqn =
                ConstraintRef.composeConstraintId(scoreDirector.getSolutionDescriptor()
                        .getSolutionClass().getPackageName(), TEST_CONSTRAINT_NAME);



        Map<String, ConstraintMatchTotal<Score_>> constraintMatchTotalMap = scoreDirector.getConstraintMatchTotalMap();
        assertThat(constraintMatchTotalMap)
                .containsOnlyKeys(constraintFqn);
        ConstraintMatchTotal<Score_> constraintMatchTotal = constraintMatchTotalMap.get(constraintFqn);
        assertThat(constraintMatchTotal.getConstraintMatchSet())
                .hasSize(entityList.size() * 3);

        List<ConstraintMatch<Score_>> constraintMatchList = new ArrayList<>(constraintMatchTotal.getConstraintMatchSet());
        for (int i = 0; i < entityList.size(); i++) {









            ConstraintMatch<Score_> constraintMatch = constraintMatchList.get(i);
            assertSoftly(softly -> {
                ConstraintJustification justification = constraintMatch.getJustification();




                softly.assertThat(justification)
                        .isInstanceOf(DefaultConstraintJustification.class);
                DefaultConstraintJustification castJustification =
                        (DefaultConstraintJustification) justification;
                softly.assertThat(castJustification.getFacts())
                        .hasSize(2);
                softly.assertThat(constraintMatch.getIndictedObjectList())





                        .hasSize(2);






            });
        }
    }

    @Override
    @TestTemplate
    public void penalize() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(



                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .penalize(SimpleScore.ONE, (entity, entity2) -> 2)
                        .asConstraint(TEST_CONSTRAINT_NAME));






        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(-42));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void penalizeLong() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {
                        factory.forEachUniquePair(TestdataEntity.class)
                                .penalizeLong(SimpleLongScore.ONE, (entity, entity2) -> 2L)
                                .asConstraint(TEST_CONSTRAINT_NAME)
                });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(-42));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate


    public void penalizeBigDecimal() {
        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),






                        factory -> new Constraint[] {













                                factory.forEachUniquePair(TestdataEntity.class)






                                        .penalizeBigDecimal(SimpleBigDecimalScore.ONE,
                                                (entity, entity2) -> BigDecimal.valueOf(2))
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                        });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(-42)));



        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void rewardUnweighted() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .reward(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));




        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(21));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void reward() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();






        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(







                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .reward(SimpleScore.ONE, (entity, entity2) -> 2)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();





        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(42));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate

    public void rewardLong() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();




        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {



                        factory.forEachUniquePair(TestdataEntity.class)
                                .rewardLong(SimpleLongScore.ONE, (entity, entity2) -> 2L)
                                .asConstraint(TEST_CONSTRAINT_NAME)



                });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(42));



        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void rewardBigDecimal() {


        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();





        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),





                        factory -> new Constraint[] {
                                factory.forEachUniquePair(TestdataEntity.class)
                                        .rewardBigDecimal(SimpleBigDecimalScore.ONE,
                                                (entity, entity2) -> BigDecimal.valueOf(2))
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                        });

        scoreDirector.setWorkingSolution(solution);



        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(42)));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());


    }







    @Override
    @TestTemplate
    public void impactPositiveUnweighted() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .impact(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();


        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(21));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void impactPositive() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .impact(SimpleScore.ONE, (entity, entity2) -> 2)


                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(42));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void impactPositiveLong() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(


                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {
                        factory.forEachUniquePair(TestdataEntity.class)
                                .impactLong(SimpleLongScore.ONE, (entity, entity2) -> 2L)
                                .asConstraint(TEST_CONSTRAINT_NAME)
                });

        scoreDirector.setWorkingSolution(solution);

        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(42));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }



    @Override
    @TestTemplate
    public void impactPositiveBigDecimal() {
        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                        factory -> new Constraint[] {
                                factory.forEachUniquePair(TestdataEntity.class)


                                        .impactBigDecimal(SimpleBigDecimalScore.ONE,
                                                (entity, entity2) -> BigDecimal.valueOf(2))
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                        });



        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(42)));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }



    @Override
    @TestTemplate





    public void impactNegative() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .impact(SimpleScore.ONE, (entity, entity2) -> -2)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(-42));







        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate





    public void impactNegativeLong() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {
                        factory.forEachUniquePair(TestdataEntity.class)
                                .impactLong(SimpleLongScore.ONE, (entity, entity2) -> -2L)





                                .asConstraint(TEST_CONSTRAINT_NAME)
                });





        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(-42));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate



    public void impactNegativeBigDecimal() {
        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),



                        factory -> new Constraint[] {
                                factory.forEachUniquePair(TestdataEntity.class)
                                        .impactBigDecimal(SimpleBigDecimalScore.ONE,
                                                (entity, entity2) -> BigDecimal.valueOf(-2))
                                        .asConstraint(TEST_CONSTRAINT_NAME)


                        });










        scoreDirector.setWorkingSolution(solution);









        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(-42)));
        assertDefaultJustifications(scoreDirector, solution.getEntityList());
    }




    @Override
    @TestTemplate
    public void penalizeUnweightedCustomJustifications() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .penalize(SimpleScore.ONE)



                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(-21));





        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    private <Score_ extends Score<Score_>, Solution_, Entity_> void assertCustomJustifications(
            InnerScoreDirector<Solution_, Score_> scoreDirector, List<Entity_> entityList) {


        if (!implSupport.isConstreamMatchEnabled())
            return;

        assertThat(scoreDirector.getIndictmentMap())




                .containsOnlyKeys(entityList);

        String constraintFqn =
                ConstraintRef.composeConstraintId(scoreDirector.getSolutionDescriptor()
                        .getSolutionClass().getPackageName(), TEST_CONSTRAINT_NAME);
        Map<String, ConstraintMatchTotal<Score_>> constraintMatchTotalMap = scoreDirector.getConstraintMatchTotalMap();
        assertThat(constraintMatchTotalMap)
                .containsOnlyKeys(constraintFqn);




        ConstraintMatchTotal<Score_> constraintMatchTotal = constraintMatchTotalMap.get(constraintFqn);
        assertThat(constraintMatchTotal.getConstraintMatchSet())
                .hasSize(entityList.size() * 3);
        List<ConstraintMatch<Score_>> constraintMatchList = new ArrayList<>(constraintMatchTotal.getConstraintMatchSet());
        for (int i = 0; i < entityList.size(); i++) {
            ConstraintMatch<Score_> constraintMatch = constraintMatchList.get(i);
            assertSoftly(softly -> {
                ConstraintJustification justification = constraintMatch.getJustification();
                softly.assertThat(justification)
                        .isInstanceOf(TestConstraintJustification.class);
                TestConstraintJustification<Score_> castJustification =
                        (TestConstraintJustification<Score_>) justification;


                softly.assertThat(castJustification.getFacts())
                        .hasSize(2);
                softly.assertThat(constraintMatch.getIndictedObjectList())
                        .hasSize(2);
            });
        }
    }

    @Override
    @TestTemplate
    public void penalizeCustomJustifications() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .penalize(SimpleScore.ONE, (entity, entity2) -> 2)
                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(-42));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void penalizeLongCustomJustifications() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {
                        factory.forEachUniquePair(TestdataEntity.class)
                                .penalizeLong(SimpleLongScore.ONE, (entity, entity2) -> 2L)
                                .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                                .indictWith(Set::of)
                                .asConstraint(TEST_CONSTRAINT_NAME)
                });





        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(-42));



        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void penalizeBigDecimalCustomJustifications() {
        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                        factory -> new Constraint[] {
                                factory.forEachUniquePair(TestdataEntity.class)



                                        .penalizeBigDecimal(SimpleBigDecimalScore.ONE,
                                                (entity, entity2) -> BigDecimal.valueOf(2))




                                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                                        .indictWith(Set::of)
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                        });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(-42)));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void rewardUnweightedCustomJustifications() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .reward(SimpleScore.ONE)
                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);


        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(21));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void rewardCustomJustifications() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(






                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .reward(SimpleScore.ONE, (entity, entity2) -> 2)
                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))





                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();




        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(42));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void rewardLongCustomJustifications() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {
                        factory.forEachUniquePair(TestdataEntity.class)
                                .rewardLong(SimpleLongScore.ONE, (entity, entity2) -> 2L)
                                .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                                .indictWith(Set::of)
                                .asConstraint(TEST_CONSTRAINT_NAME)
                });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(42));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void rewardBigDecimalCustomJustifications() {



        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                        factory -> new Constraint[] {
                                factory.forEachUniquePair(TestdataEntity.class)
                                        .rewardBigDecimal(SimpleBigDecimalScore.ONE,
                                                (entity, entity2) -> BigDecimal.valueOf(2))
                                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                                        .indictWith(Set::of)
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                        });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(42)));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void impactPositiveUnweightedCustomJustifications() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .impact(SimpleScore.ONE)
                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(21));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate

    public void impactPositiveCustomJustifications() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .impact(SimpleScore.ONE, (entity, entity2) -> 2)
                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(42));
        assertCustomJustifications(scoreDirector, solution.getEntityList());


    }

    @Override
    @TestTemplate
    public void impactPositiveLongCustomJustifications() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {
                        factory.forEachUniquePair(TestdataEntity.class)
                                .impactLong(SimpleLongScore.ONE, (entity, entity2) -> 2L)
                                .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                                .indictWith(Set::of)
                                .asConstraint(TEST_CONSTRAINT_NAME)
                });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(42));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void impactPositiveBigDecimalCustomJustifications() {
        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                        factory -> new Constraint[] {
                                factory.forEachUniquePair(TestdataEntity.class)
                                        .impactBigDecimal(SimpleBigDecimalScore.ONE,
                                                (entity, entity2) -> BigDecimal.valueOf(2))
                                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                                        .indictWith(Set::of)
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                        });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(42)));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void impactNegativeCustomJustifications() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .impact(SimpleScore.ONE, (entity, entity2) -> -2)
                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(-42));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void impactNegativeLongCustomJustifications() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {
                        factory.forEachUniquePair(TestdataEntity.class)
                                .impactLong(SimpleLongScore.ONE, (entity, entity2) -> -2L)
                                .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                                .indictWith(Set::of)
                                .asConstraint(TEST_CONSTRAINT_NAME)
                });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(-42));
        assertCustomJustifications(scoreDirector, solution.getEntityList());

    }

    @Override
    @TestTemplate
    public void impactNegativeBigDecimalCustomJustifications() {
        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =






                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                        factory -> new Constraint[] {
                                factory.forEachUniquePair(TestdataEntity.class)
                                        .impactBigDecimal(SimpleBigDecimalScore.ONE,
                                                (entity, entity2) -> BigDecimal.valueOf(-2))
                                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                                        .indictWith(Set::of)
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                        });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(-42)));
        assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void failWithMultipleJustifications() {
        assertThatCode(() -> buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .penalize(SimpleScore.ONE, (entity, entity2) -> 2)
                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                        .indictWith(Set::of)



                        .asConstraint(TEST_CONSTRAINT_NAME)))
                .hasMessageContaining("Maybe the constraint calls justifyWith() twice?");
    }

    @Override
    @TestTemplate


    public void failWithMultipleIndictments() {
        assertThatCode(() -> buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class)
                        .penalize(SimpleScore.ONE, (entity, entity2) -> 2)
                        .justifyWith((a, b, score) -> new TestConstraintJustification<>(score, a, b))
                        .indictWith(Set::of)
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME)))
                .hasMessageContaining("Maybe the constraint calls indictWith() twice?");
    }

    // ************************************************************************
    // Combinations
    // ************************************************************************

    @TestTemplate
    public void joinerEqualsAndSameness() {


        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 2, 1, 2);
        // The two bigDecimals are not the same, but they equals()
        String decimal = "0.01";
        BigDecimal bigDecimal1 = new BigDecimal(decimal);
        BigDecimal bigDecimal2 = new BigDecimal(decimal);
        TestdataLavishEntity entity1 = solution.getEntityList().get(0);
        entity1.setBigDecimalProperty(bigDecimal1);
        TestdataLavishEntity entity2 = solution.getEntityList().get(1);
        entity2.setBigDecimalProperty(bigDecimal2);
        // Entity 3's BigDecimal property is the same as Entity 1's and equals() Entity 2's.
        TestdataLavishEntity entity3 = new TestdataLavishEntity("My Entity 0", solution.getFirstEntityGroup(),
                entity1.getValue());
        entity3.setBigDecimalProperty(bigDecimal1);
        solution.getEntityList().add(entity3);

        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector =
                buildScoreDirector(factory -> factory.forEach(TestdataLavishEntity.class)
                        .join(TestdataLavishEntity.class, equal(TestdataLavishEntity::getBigDecimalProperty))
                        .penalize(SimpleScore.ONE)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                // Each entity's BigDecimal property is joined to itself.
                assertMatch(entity1, entity1),
                assertMatch(entity2, entity2),
                assertMatch(entity3, entity3),
                // Each entity's BigDecimal property is joined to each other entity's.
                assertMatch(entity1, entity2),
                assertMatch(entity1, entity3),
                assertMatch(entity2, entity1),
                assertMatch(entity2, entity3),
                assertMatch(entity3, entity1),
                assertMatch(entity3, entity2));
    }

}
