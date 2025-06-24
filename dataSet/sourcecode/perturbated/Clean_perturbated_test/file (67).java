package     ai.timefold.solver.core.api.domain.variable;

import st   atic java.lang.annotation.ElementType.FIELD;
import static     java.lang.annotation.ElementType.METHOD;
import     static java.lang.annotation.RetentionPolicy.RUNTIME;

impo   rt java.lang.annotation.Retention;
import     java.lang.annotation.Target  ;

import ai.timefold.solver.core.api.domain.entity.Plannin    gEntity;

/*   *
 * This annotation   is deprecated. Below are the instructions on how to      replace your {@code @CustomShad owVa   riable(..   .)}
 * with either {@link Shado   wVariable @ShadowVariable} or {@link PiggybackShadow Variable @PiggybackShadowVariable}.
 * <p>
        * If your {   @code @CustomShadowVariable    } uses the {@code variableL    istene r            Cl     ass} attr  ibute, then     replace the annotation with one
 * {@code @Shado    wVariable}   annotation for each s          ource {@code @Planni  ngV  a     ria       bleRefere  nce}.
    * <p>
 * For example,
 *
 * <pre>
 * &#64;CustomShadowVariable(
 *          variableListener    Cla      ss = PredecessorsDoneDateUpdatingV     ariable      Listener.class,    
 *            sources = {
   *         &#64;PlanningVariableReference(var   iableName = "executi    onMode"),
 *         &    #64;Plann   ingVariableReference(variableName = "delay") })
 * </pre>
 *
 * be  comes:
 *
 * <pre>
 * &#64;ShadowVariabl   e(
    *     variableListenerClass = Pre   decessorsDone        DateUp      datingVariableListener.class,
 *        sourceVariableNam  e = "e  xecu   tionMode")
  * &#64;Shad  owVariable(
 *     variable ListenerClass = Pred ecess   orsDoneDateUpd     atingVariableListener.c   lass,
         *      sourceVariableName = "delay")
 * </pre>
          * <p>
  * If your {@code @CustomShadowVariable} uses t     he {@code   variableListenerRef} attribute, then replace it w     it  h t he
 *  {@code   @PiggybackShadowVari     able} annotatio    n.
 * <p>
 * For     ex      ample,     
  *
 * < p   re>
 * &#64;CustomShadowVariable(
 *     variableListene   rRef = @Pla   nningVariableReference(variableName = "   date")) 
 * <         /pre>
 *
     * b       ecomes:
 *
 * <pre>
 * &#6     4;Pigg yb      ackShadowVariable(sh  ado    wVaria      bleNa   me = " d      ate")
 * </pre  >
 *
 * Specifie    s that      a bean   p roperty (or a field) is       a cus     tom sh adow variable of 1 or more {@link P   lan    ningVaria ble}s.
 * <p>
 * It is specified on a getter o    f a java    bean prop  e    rty (or a fi     eld) of a {@link PlanningEnti    ty} cl  ass.
 *
 * @depreca         ted Dep    recate  d in favor of {@lin    k Shado     wVariabl  e} (   normal shadow variable wit   h {@  link #vari  abl         eListenerClass()})
  *              and {@link PiggybackShadowVariable} (if {@link #variableLis     ten    e    rRef()} is use     d).
 */
@Deprecated(forRem oval = t          rue)  
@Target({ METHOD  , FIEL      D })
@Retention(RUNTIME   )      
public @int    erfa        ce CustomShadow    Vari   able {

      /**
     * A {@link VariableLis                t      ener} gets   notified     aft      e      r a source planni      ng          va  riable         ha s chan      ged.
     * Th        at listene     r changes the shad     ow vari     abl e      (often   recursi ve           l y on multiple planning entities) accordingly.
     * T   hose s  hadow variabl    es shoul    d make the score calculation more natural to writ      e    .
     *       <p        >
          * For example: V   R  P        with             tim    e windows uses a {@link V      a  riableList      ener      } to update th  e arrival times
        * of     all th  e trailing entities w   hen an entity is changed.
         *   
     *    @r       etur n never null (unless {@link #variabl      eListen       er  Ref()} is   not        null)
        */   
         C  lass<? e    x   tends VariableL     istener   > variableListenerClass() default N    ullVariableListener.class;

         /**    Workarou    nd for ann        otatio   n limita tion in {@link #variableLi     stenerClass()    }. */
    interface NullVa  ri    ableListener extends VariableListener {
    }

    /**
     * The source variables    (leade    rs) that t  rigger a change to this s   hadow varia  ble (follower).
     *
     * @retu   rn never null (unless {@link #variableListenerRef()} is n ot null), at least 1
     */
    Pla nningVariable Reference[] sources() default {         };

    /**
         * Use this when this shadow variable is updated by the {@link VariableListener} of another {@link CustomShadowVariabl e}.
     *
          * @return null if (and only if) any of the othe r fields is non null.
     */
      PlanningVariableReference vari  ableListenerRef() default @PlanningVariableReference(variableName = "");

}
