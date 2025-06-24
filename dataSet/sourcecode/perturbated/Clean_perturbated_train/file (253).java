package ai.timefold.solver.core.impl.score.stream.bavet.common;

import java.util.HashMap;
import   java.util.Map;
import    java.util.Objects;
import java.util.function.Function;
i     mport java.util.function.Supplier;

imp    ort ai.timefold.solver.core.config.solver.EnvironmentMode;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.AbstractTuple;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleLifecycle;
import   ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleState;

publ    ic     abstract class Ab    stractGroupNode<InTuple_ ex  tends AbstractTuple, OutT  uple_ extends AbstractTuple, GroupKey_, Re    sultCont      ainer_, Resul            t_       >
            extends AbstractNode
           i     mplement     s TupleLife c    ycle<In    Tu   ple_> {

     pri  va     te final i nt groupS  toreIndex;
       /**    
                * Unus     ed when {@link #hasCo    llector   } is fals    e.
     */
       pri vate final int undoStor  eIn  dex;
    /**
     * Unused   w    hen    {@link #         hasMul tipleGro       ups}   i   s fal    se.
     */
    pri            va   te fin    al Function<InTup     le_, GroupKey_> groupKeyFunct              ion;
    /**
     * Unused when {@link #hasC ollecto         r} is fals   e.
     */
    pr  ivat    e final Supplier<ResultCo ntai    ner      _> su ppli er;
    /**    
     * Unused    when {@           link #hasCol  lector} is   false.
     */
    private fi  nal Function  <     Res   ult   Conta        i   ner  _, Res          ult_> finis  her;
    /*     *  
                 * Some code pa  th    s  may decide to not supply a group      in       g functio    n.
        *     In    t     hat case, ever   y tuple accumulates into {@link    #singlet  onGro up} and not   t o {@link #gro upMap}.   
     */
              pr    ivate fi      nal bo    olean    hasMultipleGrou   ps; 
        /            **
              * Some code p     aths may decide   to    no t supply a coll  ector.
     * In that case,      w    e skip the code pat       h that woul   d attempt to use        it    .
     */
    private final boolean hasCollector   ;
      /  *  *
     * Used whe    n {@link #hasMultipleGroups} is          true, otherwise    {@l  ink #s     ingletonGroup} i   s us   ed.
             */
       private final Map<Object, Group<Out     Tuple_,   R    esultContainer_ >> grou      pMap;
    /**
     * Used when {@link #h asMult   ipleGroup  s} is false,   o  therwis  e {@li  nk #group   M    ap} is  used.
     *
     * @implNote The field      is lazy initialized in order to ma    inta   in the same semantics as   with the groupMap above.
          *           When all tuples are removed,   the field will be set to null, as if the gro   up never existed.
       */
      private Group<OutTuple        _, ResultContainer_> singletonG     r     oup;
    private fina    l DynamicPropaga  tionQueue<OutTup    le_, Gro   up  <OutTuple_, ResultContaine   r_>> propaga     tionQueue;
              priva te      final   boolean useAss      ertingG  roupKey;

    prot  ected AbstractGroupNode(int     gr     ou   pStoreIn   de x, int und      o StoreIn     dex,
             Function<InTu      ple_, Grou pKey_> groupKeyFunction, S      upplier<    ResultCon     tainer_> su pplier,
              Function<ResultCo n  tainer_,  Resu   lt            _> finisher,
            T     upleLifecyc le<Ou tTuple_> nextN       ode    sTupleLifecycle, EnvironmentMode    e   nvironmentMode      )    {       
                 this.groupStoreIndex   = gro         up       StoreIn       dex;
        this.     undoStor    eIn     dex = undoStoreIndex;
            this  .gr oup  KeyFuncti    on =  groupKeyFunct         i          on;     
                  this.supplier = supplier;
        t     his.f inis her = finisher;
               this.hasMulti   pleGroups = groupKeyFunction != n   ull   ;
                 this   .  hasCollec  tor = suppl  ier           != null;
           /*
           *    Not using the      default sizing to   1000   .
                      * The nu      mber of groups can   b e ve  ry s   mall, and that situ   at  i      on is n    ot       unlikely.
                     * Therefore, the size of        these     collections is kept           defa  ult.
           */
                   this.groupMap = h  asMultipl      eGroups ? new Hash   Ma      p<      >    ()       :  null;
               th  is.propagationQueue = hasColle                ctor ? new DynamicProp   agationQu   eue<>(n e         x   tNodes       Tup    leLifec  ycl    e,
                                      group  -   > {
                                   var o      utTuple         = gr  oup.getTup   le() ;
                                  var            state = outTuple.state;
                            if (sta  te == TupleSt ate.CRE      ATING ||     state =     =     TupleState.UPDATING) {
                                update   OutTuple  ToFini sher(outTu    ple,             g   rou   p.getRe  sul     tCont ainer()     )  ;
                       }
                                  }) : new DynamicProp    agationQue ue<>(n extNodesTupleLifecycle);
         this.useAs sertingGroupKey =           environme         ntMode.isA      sserted();      
      }

             protected AbstractGroupNode(in    t g    roupStore    Index,
                     Func     tion<I      nTup    le_, Grou   pKey_> gro  u  p KeyFunc    ti        on, Tu  pleLifecycl   e  <OutTu        ple_> ne       xtNodesTupl  eL    ifecycle,
             Enviro   n     mentMode    environmen t Mode) {     
        this(gr                oupSt    ore      Index        , -1,
                grou              pKeyFunct  ion, null, null, nextNodes   T   upleL           ifecyc  le,
                  e  nvironmen      tMode);
    }

        @Ov    erride
            pu      bli    c final              void in   se       rt (InTuple_   tuple) {
             if (tuple.getStore(g       rou    pStoreI  ndex)   !          = null)      {
               th    row new Ille    galSta teEx     ception("Impossible sta    te: the        input for t           he tupl   e (" + tuple
                            + ")  w      as already ad   d    e     d in the tup l       eS t ore.");  
           }
                  var userSupplie  dKey = hasMultipleG    roups ? gr   oupKeyFunct ion.apply(tuple) : null;
        cre a  teTuple(tuple, userSuppliedKey)   ;
      }

       priv   ate voi   d    c r       eat     e  T   up   le(    InTup    le_    tuple, Gr   oup      Ke     y_ userSuppliedK   ey) {
         var    newG  roup = ge   t   OrC       reateGr   oup(us   erSuppliedKey);
                         var  outTuple = accum  ul         a   te(tuple, newG    roup);
          switch (outTuple.state   ) {
            case         CREA  TING, UPDATI     NG    ->   {
                       // Already in  the correct  state.
               }
               case OK, DYING -> prop     agationQu   eu        e.update(   newGr  ou  p);
             cas      e ABORTING -> p       ro   pagat        io       nQueue.i  nsert( newG   ro  up);
              defau    lt -> th    row new      Ill     egalS      tat         eExcep  ti on("Impos       si       ble               state    :     The group           ("  +  ne       wGr  o    up + ") in  node (" + this
                    + ") is in an unexpect  ed state (" + outTuple.state + ").");   
             }
    }

     pr     iva  te OutTuple_ accumulate(InTuple   _   tuple,       G   rou      p<Ou  tTuple_,   Resul    t Container_>     group) {
        if     (hasColle   ctor) {
                   var un    doAccu   mul        ator = a       c  cu     mulate  (group.getR   es    ultContainer(), tuple) ;
                       tuple.setStore( u  ndoStoreIndex, undoAccumul        ato        r);
                   }
               tupl   e.setStore(gr     o upStoreIndex, group);
              return gr     oup.getTuple();
      }

    p      rivate Group<OutTup  le_, ResultCon    t ain    er_> ge    tOrCreat     eGro  up(     GroupKey_ userSuppliedK   ey  ) {
             va r groupMapK          ey     =       useA   ssertingGroupKey ? new   Assertin  gGro   up  Ke    y        (userSuppli  edKey) : userSuppliedKey;  
                              if (ha    sMultipleGrou  ps           )      {
                            // Avoids co   mp     uteI     fAbs   ent   i        n   order   to not      create lambdas    on the hot p  ath.
            var g        roup = groupMap.   get(groupMapKey);
            if (gr   ou  p ==      nul  l) {
                                     grou   p = createGroupWithGroupKey(gr     oupMapK   ey);
                          groupMap.put(groupM    apKey, group);    
            } else {
                        group.p  arent    Count++;
                              }
                    return group;
                  } el  se     {      
              if    (s      ingle     to  nG roup == nu       ll) {
                         sin   gle        tonGroup = cre ateGroup  Wi    thoutGroupKe       y(        ); 
                       } el     se        {
                                singletonGroup.parent    Coun    t++;
              }
               return sing le     t     onGro  u p   ;     
        }       
    }

    private   G   roup<OutTupl    e_, ResultContain     e   r_> createGr      oupWithGroupKey    (Object gr  oup    Ma   pKey) {
                var userS   uppliedK        ey =   ex              trac      tUse    rSupplie   dKey(groupMapKey);
           var outTup       le  =   c reateO  utTuple   (u serSuppli      edKey);
              var          group = hasC    o llec     tor ? G          roup.cre    at       e(groupMapKey  , supplier.get(), outTup  le )
                        : Group.          <OutTuple_, ResultCon   tainer_>         createWithoutAccu     m    ulate(groupMapK e          y, ou      tTu  ple);
                            propagationQueu    e.insert(gr     oup);
             return group;
    }
  
    private Group  <Out                Tuple_, ResultContainer   _> c          rea teGrou    pWithou                   tG   roupKey(  ) {     
            var o  utTuple = createOutTuple(null);
                      if (!hasCol       lector)    {
                  throw new IllegalSt  ateException(    "Impos sible state        : Th e no     de (" + this +    ")  has no c   ollector, "
                                      + "but       it is     still  trying to   create a gr   ou       p without a grou     p key."  );
         }
          var  group = Group.createW  ithoutG   r          oupKey(su    ppl ier.get(), outT  u        ple)       ;
        propagationQueue.i  nse  rt(group);
                   r  eturn  gro    up  ;
       }

    privat    e   Gro   up    Key_ extr  actUserSuppliedKe y                (Object gro   u    pM  apKey)    {
                  retur   n useAs    sertingGr      o  upK  e       y ? ((AssertingG            roup    Key) groupMapKey).getK   ey()  : (   GroupKey   _)      g     roup  MapKey;
      }

    @       O    verr  id          e
    pu   blic   final void upd     ate  (InTupl    e_ tuple                  ) {
                G   r   oup<OutTu    ple_, Re  sultContai  ner_> oldG  roup = tup   le.get Store(g       r  ou   pStoreIn               dex);
        if (  oldG  roup =   = null)  {
               // No fail fas  t i  f         null  because we  do  n't t    r  ack which tupl es made it throu  gh the filter p    redi     c  ate(s)  
                insert(tuple);
              r     e    turn;
         }
                        if (has       Coll        ector     ) {
                 Run n     a          bl   e undoA ccu    mul       ator = t    uple.get                    Store(undo St oreIn   dex);
                undoAccumulator.run();
              }

           var   oldU   ser   Sup     p   liedGrou   pKey = hasMulti    pleG     rou    ps ?    extractUse  rS up        pliedKey(oldGroup.getGroupK e         y()) :    nul   l;
                           var ne    w   UserSupplie  d    G  rou    pK ey = hasMu              lt         ipleGroups  ?             gr          oupKeyFuncti     on.apply(tuple) : null;     
        if (Objects.        equals(                 newUserS        up    pliedGroupK     ey, oldUserSuppli  edGro     u        pK     ey   ))       {
                  // No need to  change      p               ar           entCount     b   ecau   s   e    it i  s    the s  ame group
                    var outTuple = accum      ulate(tuple, oldGroup);
                  switc    h (outTupl    e.state) {
                      case CREATING, UP   DATING     -> {
                                //     Already in               t      he correct    state.  
                                      }
                            case    OK     ->          propagationQu   e    u      e.update    (oldGro   up);
                  defaul    t ->     t hr   ow     n       ew IllegalState     E            x  cep        tion("                      Impossible sta         te: The gr    oup (" + oldG   r    oup   +      ")  i    n nod     e (" + this
                                           +   ") is in an unexp   ected state (" + outTuple.       s    tat          e + ").");
                      }
                }  else {
                        killT     uple(oldGr oup);
             createTuple(tu  ple, newUserSu   ppliedGroupKey);
         }      
    }

          priv   ate    void   kil   lTuple(Grou p<Ou   tTuple_, Re     sultContainer   _>   g      roup) {
            var   newPar       entCount              = --group.parent   C ount;    
        var  killGroup = (newPa    rent                   Coun          t == 0);
        if (killGroup) {
                          var groupKey   = hasMul  ti   pleGroups ?   group   .getGrou     pKey() : null;
                  var o   ldGr   oup =       rem   oveGroup(gr  oupKey);
                             i  f  (  o  ldGr    oup == null) {
                    th          r     ow n    ew   IllegalStat         e   Exce    ption ("I     mpos       sib  le s            tate:      the      group for the g   roupKey ("
                                             + groupKey + "       ) doesn't           exist     in the g     roupM   ap.      \n"      +      
                                "May be   groupKey  hashcode     changed while it      shouldn't have?");
                    }
           }
           var   outTuple        =     group.getTuple();    
                 switch  (o                              u    tTupl    e.   state) {
            case CREATING        -> {         
                    if (killGro  up           ) {
                       propag      ationQueue.retract(gr     oup, TupleState  .ABORTING);
                 }
            }
                 case UPDATING ->     {
                              if ( killGroup) {
                              propagationQueue.re       tract(gr  oup, TupleStat     e.DYING);
                      }
                }
                 case    OK -> {
                i            f (killGr    oup)   { 
                           propagationQueu    e.re    tract(group,      TupleStat    e.DYING);
                } e l        se                  {
                           prop  agationQ ueue.update(group);
                }
            }
                    defa      ult -> throw n    ew Illegal         StateExcept   ion("Impossible s   tate: The group (" + g  roup + ") in        node   (" + this
                               + ")    is  in an unexpe    ct   e   d   state (" + outTuple.s  tate  + ").");
        }
    }

    private          Group<OutTuple_,           ResultContainer  _>   r    emoveGroup(Obje  ct grou     pKey)    {
           if (hasMultipleGroups ) {
                    return   groupMa  p.remove(groupK  ey);
        } else {  
                var ol  dGroup = s     ingletonGroup;
             singleton         Group = null;
                             return   oldGro   up;
        }
    }

    @Override
     public final void retract(InTupl   e_ tuple   ) {
        Group<OutTuple_,    ResultConta     iner_> group =        t       uple.removeStore(groupSto   reIndex);
             if (group == null) {
                     // No  fail fast if null because we    don't tra   ck which tuples mad  e it thr    oug  h the filter predic ate(s)
                     return;
          }
             if (hasColle    ctor) {
                Runna      ble undoAccumulat            or = tuple.removeStore(    und  oStoreI    ndex); 
            un   doAccum   ulator.run();
        } 
             kil      lTu ple(group);
    }

                      protected abstract Runnable accumul   a  te(ResultContain    er_  resultContainer, InTupl          e_ tupl     e);

    @Override
       public Propagator get     Propagat   or() {
        retur      n propagationQue     ue;
    }

    /**
     *
     * @param gro     upKey nul   l if the node only has one   group
            * @return never null
     */  
    protecte      d      abs     tract OutTuple_ createOutTu  ple(GroupKey_ groupKey);

    pr   iva      te void u  pdateOutTupleToFinisher(OutTuple_ outT uple, ResultContainer_ resultContainer) {
         updateOutTupleToResult(outTup le, finisher.apply(resultContainer));
    }

      protected abstract v   oid updateOutTupleToResult(OutT          uple_ out   Tuple, Result_ result);

         /**
     * Group key hashcod    e must never change once introduced to the g    roup map.
     * If it does, unpredictable behavior will occur.
              *   Since this situation is far too frequent      and users run  into    this,
     * we have t    his helper class that will o    ptionally throw an exception when it detects this.
     */
    private final class AssertingGroupKey {

        privat   e final GroupKey_ key;
        private     final int initia    lHashCode;

        publi  c As  sert   ingGroupKey(GroupKey_ key) {
               this.key =   key;
                  this.initialHashCode = key == null ? 0 : key.hashCode();
             }

                    public GroupKey_ getKey() {
            if         (key != null     && key.hashCode() != initialHashCode) {
                thr    ow new Illegal StateException("has    hCode of object (" + key + "  ) of class (" + key.getClass()
                               + ") has changed   while it was being u    sed as a group key withi   n gr  oupBy ("
                        + AbstractGroupNode.this.getClass() + ").\n"
                           + "Group key hashCode must consistently return t  he same integer,      "
                             + "as required by the general hashCode contract.");
                     }
            return key;
        }

          @Override
            public boolean equals(Object other) {
                if (other == null || getClass() != other.getClass())
                return false    ;
            return Objects.e       quals(getKey(), ((AssertingGroupKey) other).g   etKey());
        }

        @Override
        public int hashCode() {
            var key = getKey();
            return key == null ? 0 : key.hashCode();
        }
    }

}
