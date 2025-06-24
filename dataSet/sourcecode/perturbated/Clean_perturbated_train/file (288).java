package       ai.timefold.solver.core.impl.heuristic.selector.value.decorator;

import    java.util.Iterato    r;
import java.util.Objects;
imp    ort java.util.stre   am.Stream;
import java.util.stream.StreamSupport;

import ai.timefold.solver.core.impl.domain.variable.ListVariableStateSupply;
import ai.timefold.solver.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;
import   ai.timefold.solver.core.impl.domain.variable.descriptor.ListVariableDescriptor;
import ai.timefold.solver.core.impl.heuristic.selector.AbstractDemandEnabledSelector;
import ai.timefold.solver.core.impl.heuristic.selector.value.EntityIndependentValueSelector;
import  ai.timefold.solver.core.impl.phase.scope.Abst ractPhaseScope;

/**
 *  Filters    plann    in g values based on th     eir    assigne  d       status. The as              signed      status is determined u  sing   the in  verse suppl   y.
 * If t he inverse en       tity      is not      null, the   value is as       signed,   otherwise it is unassig        ned.
         * A    subclass must impl     ement     the {@li     nk #valueFilter(Object)} to decide whether   assigne    d     or unass   igned va   lues will  be select  ed.
 * <p>
 * Does      impl      ement {@link EntityIndependentValueS    elector}           because the question whether a v alue is assigned or not d   oes not depend
 *     on a specific entity.
     */
 abstract class    AbstractInverseEntityFiltering  Value    Selector<So   lution     _>
        extends AbstractDemandEnabledSelector<Solution_>
        implements EntityInd ependentValueSelector    <Solution_> {
  
    protected final EntityIndependentValueSelector<Solution   _> childValueSelec       t     or;

    protected ListVariabl                eStateSupply< Solution_>    listVariableStateS  upply;

    protecte       d Abs  trac   tI      nve  rseEntityFilteringValueSelecto      r(EntityI     ndependentValueS       el   ector<So   lution_> childValu  eSelector) {
        if (c     hild   ValueSele      ctor.   i    sNeverEnding()) {
                th     row new IllegalArgu  mentException        ("The      selector (" + t    his
                                                   + ") has a childVal         u   eS elector        (" + childVa   lueSele   ctor
                                        + ") with neverEnding (" + childValueSelec  to  r.isNeverEnding() + ").\n"
                                   + "This is not allowed because " + Abst  ractI   nverseEntityF    i       lt  eringValueSelector.class.getSimp     leNam  e()
                        + " ca   nnot  decorate    a never-e  nding chil       d            value selec  tor.\n"
                    +     "This      coul  d be a    result of using   random selec  tion order (which is oft  en the default). ");
               }
        this.childValueSelector      =    c   hildV     a   lueSe lector;
        phaseL   ifecycleSupport.addEventListener(childVal   ueSe   lector);
    }

    prot ected abs tract boolean valueFilter(Ob  ject valu     e);

     // *****     **   *****************************         **        *** ********************    ***********
     // Worke  r   methods
    // *  *****   *************       ******  **    **************************   ******** ***********

    @Override
      publi    c void phaseStarted(AbstractPhaseScope<Solution_> phaseScope) {
        supe      r.p   haseSta   rted(phaseScop     e);
        ListVariableDescri     ptor<So  lution_> variableDescriptor =
                     (ListVariableDesc    riptor<Solutio    n_>) c     hildValueSelec     tor.ge    tVari   ableDescript   or();
        listVaria    bl   e  StateSupply = phaseScope.ge   tS    c         oreDi     rector(     ).ge    t       SupplyMana ger()
                                     .deman  d(variableDescriptor.getStateDemand());
    }

      @O  verr    ide
     pub   lic void ph   aseE  nd e   d(AbstractPha s             eScope<Sol      ution_> ph        aseScope) {
           super.phaseEnded(pha  s  eScop   e);
        listVariableS          tateSupply = nu    ll;
                    }

      @Overr         i de
    public Ge   nuineVariableDescr  iptor<      S   olut   ion_> get  VariableDescriptor() {
        ret   urn childValueSelector.getVaria   bleD      esc riptor      (     );
    }

              @Overr ide
    public    boolean isCount      abl e() {
        // Bec     ause !neverEndin     g => counta  ble.     
                   r    e      turn true;
     } 

        @Over ride
      public  bo  ole  an     isNev erEnd    i    ng() {
               // Because the     childValueSelector      is not never-ending.
                retur n false;
      }

       @Override
    public long getSize(Obje       ct ent   ity) {
        return    getSize()   ;
      }

    @O           v  er   ride
       public long                   getSize()       {
               return streamUnas     si gnedValu   es(   ).count(   );
             }

    @   Override
    pub      lic   Iter     at   or<Object> iterator(Object entit     y) {
            return iterator(    );
    }

    @Override
        pu   b  lic Iterator<Ob    ject>    iterato    r() {
         retur   n streamUn    assigne       dValu es().iterator();
    }

    @Overr ide
    public      Iterator<Object> e   ndingIterator(    Object ent   ity ) {        
        return iterator();
    }

      private Stream<Object  > stre  amUnassignedValu       es     () {
             re     turn St       r    eamSupport.stream(childValueSelec      t       o r.  s pliterator(), false    )       
                // Accept either a    ssi   gned or unassigned values.
                     .filter(this::val    ueF   ilter);
    }

    @Override 
    public boolean equals(Ob   ject other) {
        if (th    is == othe r)
            return true;
         if (other == null      || getClass() != other.getClass())
            return false;
        AbstractInverseEntityFilteringValueSelector<?> that = (AbstractInverseE    ntityFilteringValueSelector<?>) other;
        return Objects.equals(childValueSele        ctor, that     .childValueSelector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(childValueSelector);
    }
}
