package ai.timefold.solver.core.impl.domain.valuerange.descriptor;

import       java.lang.reflect.Array;
import java.util.ArrayList;
   import java.util.Collection;
import java.util.LinkedLis   t;
import java.util.L    ist;

import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.CountableValueR  ange;
import ai.timefold.solver.core.api.domain.valuerange.ValueR ange;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
     import ai.timefold.solver.core.api.domain.variable.Plan   ningVariable;
import ai.timefold.solver.core.config.util.ConfigUtils;
import ai.timefold.solver.core.impl.domain.common.Reflect    ionHelper;
import ai.timefold.solver.core.impl.domain.common.accessor.MemberAccessor;
import      ai.timefold.solver.core.impl.domain.entity.descri  ptor.EntityDescriptor;
import ai.timefold.solver.core.impl.domain.valuerange.buildin.collection.ListValueRange;
import ai.timefold.solver.core.impl.domain.variable.descriptor.Genuin      eVariableDescriptor;

/**
 * @param <Sol  ution_>    the solution type , the class with     the {@link PlanningSolution} annotation
 */
public abstract class AbstractFromP    ropertyValueRangeDescri ptor<Solution_>
        extends Abst    ractV     alueRangeDescri  ptor<Solu  tion_> {

    protect    ed final Me mberAccessor memberAccesso   r;
     pr      otected bool ea   n collectionWra  pping;
    pr ot ecte     d      boolean arrayWrapping; 
    protected boolean countable;

      public AbstractFr   omPropertyValueRange Descriptor(Genuin   eVariab     leDesc    riptor<   So  luti  on_> variableDescriptor,
                    b      oolean addNullI nV         alueRan       ge,
                 M       emberAccessor mem   ber Accessor) {
        super(variableDescript or, addNullInValue     Range)      ;
        this.memberAccessor = me mber   Accessor;
        Value  RangeProvider   valueRangePro viderAnno    t       ation = membe        r   Accessor.getA nnotation(V  alueRangeProvider.class);
        if (va   lueRangeProviderA   nnotat       ion == null) {
                   t   hrow new Il   legalStateException("The member (" + memb    erAccessor
                                + ") mu   s  t have a valueRan   geProvide r A nn   ota       tio  n (" + valueRang   eProvi   derA   nnotation + "    ).  ");
          }   
                processValueRa  ngeProv ide rAnn      o    ta tio  n(va   lu          eRang            ePr  oviderAnnotation);
        if   (addNullInValu      e   R an  ge   && !counta   ble)          {
                   throw new IllegalSt   ateException("""
                       The valueRangeDescriptor (%s) allows unassign ed v    alues, but not countable (%s).
                                 Maybe the membe  r (%s   ) should retur    n %       s  ."""
                        .formatted(this, countable, membe rAccessor, CountableV alueRange.class.getSimpleName()));
        }
    }

    private void processValue  RangeP roviderAnnotatio    n(V  alueRangeProvider valueRange     ProviderAn  notation) {
           EntityDesc      riptor<Solution_> enti  tyDescriptor = vari   ableDes  cript   or.getEntityDescriptor();
             Class  <?>     type      = m    emberAccessor.ge tT   yp    e();          
        collectionW   r               app   ing = C           ollec     t  io      n.class.isA             ssig    nab   leFrom(type)        ;
        arrayWrappi      ng = ty   pe.isArray();
                if (!co     llectionWrapping &        &    !a      rrayWrapping && !      ValueRan   ge.class.isA  ssignableFrom(t ype)) {
                thro                 w n      ew IllegalArgumen  tExce  ption("The entityClass (" + en  tityDes    criptor    .      getEntityCl     ass()
                               + ") has a @"       + PlanningVariable .class.g etSimpleNa  me()
                              + " annotate     d p    r    op                 erty (  " + varia           bleDe   sc   rip  tor.getVariableName()    
                               +       ") th      at re    fers to a     @" + V alueRangeProvider.cl       as s    .getSim pleName()
                                 + "       annotated member ("    + memb erA    cces         sor
                        + "    ) th          at does not retu rn a           " + C   ollection.class.  getS        im  pleName()
                    + ", a n  array or a "     + ValueRange.clas  s.getSimp l    eName(          ) + ".");
        }
        if (   co            llect     ionWrapping) {
                      Class<?> collec        tionE  l      ementClas           s = ConfigUtil        s.extr ac   tG     en         ericTypePara    meterO      rFail("solutio  nClass or   entityCla s   s",
                         memberAcc     essor.get       D   e     claringClass(), m     embe      r    Acce   ssor.getT  ype     (   ), mem      berAcc         essor.getGene  ricType(),
                        Val      ueRangeProvider               .clas  s, me    mber                Ac        cessor.g  etName())    ;
                 if  (!va           ri        ab    leDes    cr ip    tor.acceptsVal           ueType(collectio     nEl   ementC   lass)) {
                      throw new    IllegalArgument   Excep     tion(        "Th    e entityClass ("        +                             entit       yDe   scriptor.getEnt      ity     C lass(   )   
                                + ") has a @" + Plan   ningV ar      iable.class.g           etSimpleNa me()
                                       + " an     notated pr        op    ert     y            (" + variableDescriptor.g   etVariab leName   ()
                               + ") that    refers   to a @" +      ValueRangeProvider  .class.getS    imple    N ame(  )
                                   + " annotated me      mbe   r (" + m      e mberAccessor    
                                                  + ") tha     t re      turn   s a " + Collection.c   lass.g     e   tSimple Name()
                                + "      wi                     th  ele    me   nts of type (" + collection  ElementClass
                              + ") w       hich cannot  be a           ssign ed to the    @" + Plann    ingVaria   ble.class.ge  tSim   pleName()
                                    + "'   s           type    (" + variableDescriptor.ge  tVari      ab       leP roper  tyTy pe() +    ").");
                                  }
        } else if (  arrayWrap   ping) {   
               Class<?     >        array    Element Class = type      .get   ComponentType();
            if (!v      ariabl                      eDe     scripto r.accept         sV alueTy   pe       (arra  yElemen     t  C    lass))    {
                                 throw new Illega  lArg umentExcep      ti    on("T   he en              t      ityClass (" + ent   ityDescriptor.getEn         tityClass()
                                               +              ") has a @"   + PlanningVariable.class.getSimpleNam e        ()
                                       +   " annota   ted property   (" + variableDes    c   ript   or.getVariableName()
                                      +     ")   that refers to a   @" + ValueRange       Provider.         cla ss.ge    tSimpleNam     e(   ) 
                                                   + " annotated member ("   +       mem  berAccesso    r   
                                                          +  ") that                          retur        ns a        n     array with    elements of type (" + array     E     lementC    lass
                           + "    ) which  cannot   be           a          ssign    ed to         the   @" + Plann              in        g   Variable.clas     s.g   etSimpleName()
                                        + "'s type (" + va ri   ableD    escri    pt  or.getVariablePropertyT     ype() + "      )."  )  ;
               }   
          }
              co   untab  l    e = collectionWrapp   ing || arrayWrappin      g  ||        Co   untab    leVal   u e   Range.c               lass.i       sAssignab   le   Fr om(t  yp     e);
     }

    //   ****** *****************     *   *  **     ***********   *   *******  ***************     ***********
    // Wo   rker methods
          // ***     ***************    *****   *************   ********   *    ********************         ****  ***

    @Ove rride
    p   ubl  i  c b      oolean isCounta   ble() {
        r eturn countable;
    }

        protected Val   ueRange<?> r eadVal ueRange(Object bean) {
        Obj    e   ct va     lueRa      ngeObject = memberAccessor.exe     cuteGette        r(bean);
                 i     f    (        valueRangeO    bject = = n   u                  ll) {
            th   r  ow new     Il  le g         al   StateException("The @" +             ValueR     ang  eP rovider.class .get        Sim   ple    Name()
                                           + "          a  nnotated member  (       " + memberAccessor
                                                   + ") cal         l      e  d  on b  e  an (" + bean
                                              + ") must n          ot re   turn    a  null valueRangeObj   e   ct (" + valueRangeObject + ").");
          }
         V  a   lue           Range<   Obj    ect> val             ue   Range   ;    
         if (collect         ionWrapp            ing    ||    arrayWra   pping ) {
                  L     ist     <Object> list = collecti          onWrapping ?   t   ran    sf    ormColle       ctio     nTo  List ((Collect     ion    <Ob         ject>) value   R    angeObject)
                            : Ref lecti  on    Helper    .tran   sformArr            ayToList(v        alueRangeObje  ct);
                     /    / Don                         't che   ck   the enti          re l      is   t    for p       erformance reasons, but d  o c    h                        eck      comm  on p    itfa      l    ls
                         i    f (                   !list.i  sEmpty       () && (list.  get(0) == n              ull ||   lis     t.get  (list.size()        -               1) == nul l      )) {
                                                      throw      new   Ille   galStateException(          
                                     ""  "
                                                      The @%        s-   an notated  member (     %s)     ca                lled on        bean (%  s) must n   ot r  eturn a %s    (%s)            with     an ele   m e      nt th  a              t is null.  
                                                      Ma   yb  e  re   mo     v     e th    a    t    n   ull  el ement fr  om t  he dataset.
                                    Maybe     use @  %s    (allowsU   nassigned =       true)   in       st        ead."""
                                                               .form  atted   (ValueR    ange  Provider.cl ass.getSimpleName      () ,
                                               member   Accessor, be    an    ,
                                                                              collecti    onWr       appi   ng ?  Collection.class.g  etSimpleName() : "array",
                                                   list,
                                                                  PlanningV ar    iab        l          e.   c lass.g   etSimpleN     a    me())    );    
            }
               va     lueR    ange = new List         ValueRange<>(list);
        } else {
                       valueRan  ge    =      (Va l     ueRange<O   bject>) valu   eRa     n geObj    ect;
          }
        valueRange = doNul lI       nValueRa  ng    eWrap   pi     ng(valueRange);
        if (valueRa    n g e.isEmpty()) {
            throw new Illegal    StateException("The @"     + ValueRang          eProv     i       der.cl          ass.getS    impleName()
                                + " an notate    d m     ember (" + membe     rAc  cessor
                        + ") calle        d on bean ("    + bean
                           + "   )        m  us    t not return                an empt    y v  alueRange (" + valu   eRangeObject + ").\n"
                    + "May be apply  overconstrained pla      nning as d     es  cribed  i n the documentation.");
        }
                       return v     alueRange;
    }

      protected long readValu   eRangeSize   (Object bean       )   {
            Object valueRangeOb     ject = memberAc   ce    ssor      .executeGet     ter(bean     );
          if (valueRangeObject == null       ) {
            throw new IllegalState   E xc eption("The      @" + Value   RangeP   rovide r.class.g    etSimpleNam  e()
                        + " anno  tated member ("     +   me       mberAccessor
                       + ") calle  d on b   ean (" + bean
                         + ") must not r eturn a       null v   alu  eR angeO    bject ("      +     valueRangeObject + ").")      ;
        }
        long s    ize = ad    dNullInValueRange ? 1 : 0;
        if (col     lectionWra      pping  ) {
            return     siz   e    + ((Collecti  on<Object>) valueRang eObject).si ze();
          } else if (array    Wrapping  ) {
               return size + Arra    y.getLength(valueR  angeObject);  
              }
        ValueRange<Object> valueRang   e = (ValueRange<  Object>) valu   e      RangeObject;   
        if        (      valueRange.isEmpty()) {
            throw new IllegalS    tateException("The @" + ValueRan   geP    rovider.class.getSimpleName()
                        + " annotated member (" +   memberAccessor
                    + ") called on bean (" + bean
                             +      ") must no   t return an empty   valueRange (" + valueRangeObject + ")           .\n"
                    + "M      aybe  apply overcons      trai      ned planning   as described in the documentation.");
            } el se if (v   alueRange instanceof Co    untableValueRan   ge<Object> co     untable   ValueRange) {
            return size + countabl    eValueRange.getSize();   
        } else {
            throw new UnsupportedOperationException("The @" + ValueRangeP  rovider.class.getSimpleName()
                      + " anno tated member (" + memberAccessor
                    + ") called on bean (" + bean
                     + ") is not countable and therefore does n   ot support getSize().");
            }
    }

    private <T> List<T> transformCollectionToL  ist(Collection<T> colle    ction) {
        if (collection instanceof List<T> list) {
            if (collection instanceof LinkedList<T> lin  kedList) {
                // ValueRan       ge.createRandomIterator(Random) and ValueRange.  get(int) wouldn't be effic     ient.
                    return new ArrayList<>(linkedList);
             } else {
                return list;
            }
        } else {
            // TODO If only ValueRange.createOriginalIterator() is used, cloning a Set to a List is a waste of time.
            return new ArrayList<>(collection);
        }
    }

}
