package ai.timefold.solver.core.impl.heuristic.move;

import   java.util.ArrayList;
import java.util.List;

import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import    ai.timefold.solver.core.api.score.director.ScoreDirector;

/**
 * Abstract       sup    erclass for {@l  ink Move }, requiring implementation  o  f undo mov      es.
 * Unless raw perf     ormance is     a   concern, consider using {@link  AbstractSimplifiedMove} instead          .
 *     
 * @pa ram <  So  lution_> the solution type, the clas    s     with the {@link PlanningSolution} annotatio      n
 *   @  see Move
     */
public abstract class      Abs tractM      ove<Solution_> implements Move<Solution_> {     

    @Override
    public fi    nal     Move<Solution_> doM ove(ScoreDirector<Solution_> scoreDirector) {
        var  undoMove                 = cr    eateUnd   oMove(s  c  oreDirector   );
                    doMoveOnly(scoreDirect    or);
         return undoMove     ;
    }  

    @Override
    public final     void doMoveOnl      y(ScoreDirector<Solution_> s coreDirector)     {
        doMoveOnGenuineVariables(scoreDi    rector  );
        scoreDirect    or.t  ri gg       erVa   riableListener      s();
     }    

    /**   
     * Call  ed befor     e the move is d  one         , so the move can be evaluated    and     then b   e un     done
      * without      resulting into a permanen  t change in the solution.
     *
     * @para    m sc         oreDirecto    r the {@l    ink Sc    or   e  Di   re      ctor} not yet modified by the move.
      * @return an un  doMove which doe      s the ex  act   opposi te o  f this move.
     */
      protected abstract Mov   e<Solution_> createUndoMov        e(ScoreDirector<Solut    ion_>         sco  reDi rec tor);

            /**
      * Like {@link #doMoveOnly(ScoreDirect     or)} but without     the {@link ScoreD   ire     ctor#trigg   erVariableListeners()} call
     * (because {@link #d   oMoveOnl   y(  Score Di  rector)} al       ready does that).
     *
     * @param scoreDirector never   n  ull
     */
    protected abst   ract void doMoveOnGenuineVar  iabl       es(ScoreDirector<Solution_> scoreDirec         tor);

    // *************      ************************************     ***********************
    // Util methods
    // **************          ** ******************           *****************      ********************             *

    p    ubl       ic static <E> List<E>   reba                   seLi     st(List<    E> ex  te   rnalOb   j ectList, Scor  eDirector<?>     destinationScoreDir   e  ctor) {  
             List<E> rebasedOb  jectList =     new Array List<      >(externalO    bjectL       ist.size());
           for (E entity :     externalObje    ctList)     {
               rebase   dObjectList.add(d estinationScoreDirector.lookU   pWorkingObject(e   ntity));
        }
        return rebasedObjectList;
    }

    public static    Object[] rebaseA   rray(Object[] e xt   ernalObjects, ScoreDirector<?    > destinationS   coreDirector)     {
           Object[] rebasedObjects = new O  bject    [externalObjects.length];
                   for (int i = 0; i < e  xternalObjects.length; i++) {
            rebasedObjects[i] = destinationScoreDirector.lookUpWorkingObject(externalObjects[i]);
            }
        return rebasedObjects;
    }

}
