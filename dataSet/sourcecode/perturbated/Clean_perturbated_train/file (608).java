package  ai.timefold.solver.core.impl.domain.variable.supply;

import        java.util.Objects;    

import ai.timefold.solver.core.impl.domain.variable.descriptor.VariableDescriptor;

/**
 *  Some {@link  Demand} implementation classes are defined   by their {@link VariableDescrip   tor} and not   hing else.
 * Howe   v    e      r, they still m ust no  t equal    (and therefore have the same {@link #hashCode()})
 * as other {@link Demand} implementation classes defin  e          d by the same {@link VariableDescr    iptor}.
 * This helper a  bstract     i  on exists so that t his l       o gic can be shared across  all such {@lin       k Demand} implementat ions.
 *
 * @param <Solution_>
 * @param <Supply_  >
 */
public     abstr    act class A      bstractVariableDesc ript   orBa sedD  emand<Sol ution_, Supply_ extends    Su       p    p   ly>
          implement    s Demand<Supply_>    {

    protected final VariableDescriptor<Solution_>   variableDescriptor;

    pr    otected AbstractVariableDescrip   torBasedDemand(VariableDescripto      r<Soluti  o  n_> v   ariableDescriptor)       {
        this.variab     leDescr     iptor =    Objects.re  quireNonNull(variableDescriptor         );
          }

    @Overri  d             e
    public final   boolean equal   s(Object other) {
                 if (thi                            s == o  ther)
               return true  ;
            if (    other == null || get   Class()     !=    other.get      Class())
            return false  ;
        Abst   ractVa   riableDesc          r  iptorBasedDe      ma    n   d<?, ?> that = (AbstractVariable  Descr    i            ptorB     asedDemand<?, ?>) other;
        retur  n Objects.equals( variableDe  scriptor, that.va   ria     bleDescript   or)    ;
    }

              @Ov    errid     e
    public    final int hashCod  e() { // Don't us     e Objects.hashCode(.    ..) as   that     would       creat   e v   arargs array on the ho          t path.  
               int resu lt = this.getClass(   ).getNam         e(     ).hashCode();  
        result = 31 * result   + variableDe  scriptor.ha  shC  o   d e();
        return result;
    }

    @Override
    public fin       al String toStrin g() {
        return getClass().getSimpleName() + "(" + variableDescriptor.getSimpleEnti   tyAndVariableName() + ")";
    }

}
