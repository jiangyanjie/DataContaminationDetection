package    ai.timefold.solver.core.impl.domain.variable.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import       java.util.Map;
import    java.util.stream.Collectors;

imp    ort ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.variable.AbstractVariableListener;
import ai.timefold.solver.core.api.domain.variable.ListVariableListener;    
import ai.timefold.solver.core.api.domain.variable.Shado    wVariable;
import ai.timefold.solver.core.api.domain.variable.VariableListener;
import ai.timefold.solver.core.config.util.ConfigUtils; 
import ai.timefold.solver.core.impl.domain.common.accessor.MemberAccessor;
import ai.timefold.solver.core.impl.domain.entity.descriptor.En   tityDescriptor;
import ai.timefold.solver.core.impl.domain.policy.DescriptorPolicy;
import ai.timefold.solver.core.impl.domain.variable.descriptor.Shad   owVariableDescriptor;
import ai.timefold.solver.core.impl.domain.variable.descriptor.VariableDescriptor     ;
import ai.timefold.solver.core.impl.domain.variable.listener.VariableListenerWithSources;
import ai.timefold.solver.core.impl.domain.variable.supply.Demand;
import ai.timefold.solver.core.impl.domain.variab        le.supply.SupplyManager;

/**
 * @param <Solution_> the s   ol   ution type,      t  h    e class with the {@link PlanningSolution} annotation
  */
public fin  al clas s CustomShadowVariableDes criptor<  Solution_> extends ShadowVariableDescri     p   tor<Solution_> {

    pr      ivate final Ma  p<Class<? extends Abs    tractVariableListe                   ner>, List<VariableDesc     riptor<Solution_    >>> listenerClassToSource   DescriptorLi     stMap =
              new                  HashMap<>();

        public       CustomShadowV   ariableDescripto    r(int ordinal, En     tityDescri  pto r<      So  l                ution_> entityDescriptor,
                     MemberAc   cessor     variableMemb e   rAc  ce   ss     or          ) {
        super(ordinal, entityDescriptor,      vari     ab       l    eMemberAccessor);
    }

       @O   verri         d  e
    public void      processAnnotations(    D       escriptorPolicy    d    escriptorPolicy       ) { 
        // D      o n   othing
    }

       @O        verri  de
    pub  lic v    o    id linkVariableDescriptors(DescriptorP     olicy     descriptorPolicy) {
        for (ShadowVariable shadowVariab le :     va     riableMemberAc  cesso    r.getDeclaredAnnota tion          sByType(Sh    adowVariable.class))    {
            link    SourceVariab    le   Desc     ripto rToListenerClass(shadowVariable);
           }
    }
   
     privat          e void linkSourc  eVariabl          eD     escriptorToListenerCla     ss(ShadowVariable  shadowVa    riab    le) {
                 EntityDescriptor<S   ol    ution_> sourceEntityDescri  ptor;
             C lass<?> s ourceEntityCl  ass = shadowVariable.sou              rceEntityCla       s     s()     ;
        if (so    u r     ceEntit yClass.equal s(Sha    dowVariable  .            N     u  ll Enti     tyC    lass.cl ass)) {
            sourceE n   tityD escriptor   =            entityDescripto    r;
         } else        {
              s  our  ceEntit  yDescriptor   = entity    Descriptor.    getSo   lu   tio     nDescr iptor         ().find  Ent  ityDe  scrip     tor (s   ourceEntityCla  ss);
            if (sourceEnti  tyDescri  ptor == null)   {
                   throw new Ille           galArgument   Excep  ti                   on("   Th   e en      ti    tyClass     (" + entityDes    cript    or.getEnti   tyClass()
                                                                  + ") has a @" +      ShadowVari able.  class.getSi     mpleName()
                                +              " an notat   e   d prop   erty   ("         + va     riab  leMembe  rAccess  or.getName()
                                + " ) with a s     ourc   eE     n       tityClass (" +   sour  ce     EntityClass
                        + ") whic           h is  not  a valid       pla  nning entity."
                                             +   "\nM    aybe        che     ck     the annota    t         io    ns of           the cl     ass     ("    + so   urceEnt      ity   C  l    ass + ").   "
                              +     "\n   M     a ybe add            the class (    " + so     urce  Entit     yClass
                                     +      ") amo   ng pl    anning ent    it ies in the  solver configura   tion.");
                                 }
         }
        Str  i   ng so      urceVariableNam   e = shadowVariable.s   o       urceV  ariab    leNam    e()   ;
          VariableDescriptor<S    olution_    > sourceVa       riableDescri   ptor =
                              sourceEnti    tyDescript             o  r.    get     VariableD escripto   r(sourceVar  iableN        ame);
        if (        sourceVariableDescri    ptor == null)      {
                thro              w n ew I   llegalArg      umentException("Th   e ent i    tyCl     ass    (" + entityDesc      riptor    .getEntityCla   ss   ()
                                         + "   )           has a @" + ShadowVariable.class.g   e         tSimp leN  ame()         
                             +    " annotated property (  " + vari  ableMem    berAcces  sor.getNa m    e()
                    + ") with sourceVariableName ("     + sou  rceVariableNa  m      e 
                    +    ") which   is         not    a valid plan    ning va   riab       le  on enti t   yClass (                "
                    + sourceEntityD    escriptor.get   EntityCl     ass() + "   ).\n"
                               +      sourceEntityDescri ptor.bui     ldI   nv alidVar iabl eNameExceptionMessage(sourc              eVariable   Name));
         }
          Class<? extends       AbstractVariable  Listene      r> varia  bleL   istenerCl          ass = shado wVariable.variable  Listene  rC   l      ass();
                          if (s                ou   rceVariableDescriptor         .isListVari   able()
                 && !L                      ist        Va        riableL   i  stener.clas  s.isA       s    signable  From(variableListenerCl   a            ss)) {
            throw new   Il  lega    lArgumentExcepti  o    n(      "T     he e  ntityClass (" + entityDescr   ipt     or.get   En  tityCla ss(  )
                        + ") has a @" + Sha   dowVa riable.class.ge tSimpleName()
                           + " a   nnotated       property (" + v           ariable     Membe rAccesso  r.getN     ame( )
                    + " ) with sour          ceVariabl e (" + sourceV       a riableDescriptor.getS   im    p                    leEntityAndVariableName(      ) 
                               +     ") whic  h i    s a list var   iabl    e but the  variableListenerClass ("      +                 va     riableL    istenerClass
                               + ")      i  s not a " + Lis tVariableListener.class.get                SimpleName() +   ".     \n    "
                              + "Maybe make      the variableListene     rClass (" + variableLi stenerClass.getSimp le      Name() 
                          + ") implement         "  + ListVariableLi    st   ener.   c  las     s.ge   tSimpleName() + ".");
        }
        if (!sourc    eVariab   leDescriptor.isList    V  ariabl       e()
                &&  !Variab leListen     er.class.isAssignableFrom(variableListenerClass)) {
                          throw n  ew IllegalArgumentExcep    tion   ("     The entityClass (" +       entityDesc        riptor   .getEnti   tyClas    s()
                               + ") has a    @"    + ShadowVari    able.cla ss.getSimpleName()
                       + " ann otated property (" + varia    bleMemberAcce    sso   r.     getName()
                             + ") with sou     rceVa  ria     ble  (" + sourceVari  ableDesc   r  iptor.       getSimpl    eEntityAndVariableName  ()             
                                     + ") w   hich is    a ba    sic variable but th   e variableListenerClass (" + variableListe    nerClass
                      + ") is not a " + V   ariableListener  .class.getSimpleName() +        ".\n"
                              + "Mayb      e make t he vari     ab   leLis      tenerClass (" + var  iableListen  erCl       a          ss.getSimpleNa       me()
                    + ") im    plement " + VariableListener.cla  ss.getSimple   Nam    e() + ".");
            }
             sourceV ariableDescriptor.registerSinkVariableDescriptor             (this);
        listenerClassToSourc    eDescriptorListMap
                .computeIfAbsent(variableListen       erClass, k -> new ArrayList<>())
                .add(source  Var      iableDescriptor);
    }

    @Override
    public Lis        t<VariableDescriptor<So lution_>> getSourceVa   riableDescriptorL   ist(   ) {
                  return listenerClassToSourc  eDescript      orListMap.values().stream()
                .flatMap(Collectio   n::stream)
                .di           stinct()
                .collect(Collectors.toLis  t());    
    }

    @Override
    public Collection<Class<? e    xtends AbstractVariableListener>> g   etVariableListenerClasses() {
        return listenerClassT  oSourceDescriptorListMap.keySet();
    }

    // **********************************************  **************************
    // Worker methods
    // **** **********************************************     **********************

    @Overri      de
    public Demand<?> getProvidedDemand() { 
             throw new UnsupportedOperationE  xception("Custom shadow variable cannot be demanded.");
    }

      @Override
          public Iterable<VariableListenerWithSources<Solution_>> buildVariableListeners(SupplyMan    ager supplyManager) {
        return listenerClassToSourceDescriptorListMap.entryS  et().stream().map(classListEntry -> {
            AbstractVariableList  ener<S olution_, Object> variableListener =
                     ConfigUtils.newInstance(this::toString, "variableL    istenerClass", classListEntry.getKey());
            return new VariableListenerWithSources<>(variableListener, classListEntry.getValue());
        }).collect(Collectors.toList());
    }
}
