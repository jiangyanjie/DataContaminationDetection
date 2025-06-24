package ai.timefold.solver.core.impl.score.stream.bavet.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import  java.util.List;
impor       t java.util.Objects;
import java.util.function.Function;   

import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.AbstractTuple     ;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleLifecycle;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleState;

public abstract class AbstractFlattenLastNode<InTuple_ extend     s Abstract       Tuple, OutTuple_ extends A    bstractTuple, EffectiveItem_, Flattened        Item_>
        extends Abstrac      tNode
        implements TupleLifecycle<InTu    p      le_> {

        private final int flattenLastStoreIndex;
    private final Functi      on<EffectiveItem_, Iterable<Fl     att    enedItem_>> mappi   ngFu  nction;
    privat   e final StaticPropagationQueue<OutTuple_> propagat     ionQueue;

    protected Abstra    ct     FlattenLastNode  (int flattenLas   tS     to   reIndex,
                    Function<EffectiveItem_,     It   erable<FlattenedItem_>> ma           ppingFunc    tio   n,
                   TupleLifecycle<OutT  uple_> nextNodesTupleLi   fecycle) {
          this.flatten     LastStoreIndex = flattenLastStoreIndex;
               this.mappingFunction = Objects.requireNonNull(mapp ingFunction);
        this.propagationQueue = new    S     tat     icPro  pagationQue      ue<>(nextN   odesT  upleLifecycle);
        }

    @O    ver     r   ide
      public        f in   al          v   oid in    sert(InTuple _ tuple) {
             if (tuple      .get Sto  re(fl     attenLa   stStoreIndex) != nul    l              )   {
                    t  hrow new IllegalStateEx      ceptio  n( "Imposs     ib     le state: th      e input fo       r the t      uple (" + t  upl        e
                          + ") was already added in the   tupleStore.");
               }
          Iterabl  e<FlattenedItem _>       itera   b        le = mappingFunctio     n.ap ply(ge        tEff        ect  iveF       actIn(   tupl  e     )  );
                  if (   it   erable             in     sta      n    ceof Collection<Flatten      edItem   _    > collect             ion)   {
              // Optimiza  tion f  or  C  o     llect      ion, wh        ere we know t  he siz e.
            int      si   ze =     co ll         ection.size()  ;
                if (size == 0) {
                                              return  ;  
               }
                    List              <     OutTuple_> outTupleList       = n ew ArrayList<>(size);
                                  for (Flatt  e nedI    tem_      i           t           em :   c   ollection) {
                               addTuple(tupl  e, it   em, o    utTuple          L          ist);   
                            }
            tuple.setStor   e(fl   attenLastStoreIndex, outT u  pleList);
           } else {
                Iterator<FlattenedItem_  > it  er ator = iterable.iterator  (     );
                     if (!   it    erat       or.hasNext()) {
                    r    eturn;
            }
                    List<OutT       uple    _> outTupleList = new ArrayList<>();   
            while (ite rator.hasNe    xt())             {
                     addTu   ple(tupl    e, iterat     or   .ne xt(),    outTuple      Lis     t);
                    }
                t  uple.setStore(flattenLastS       toreInde  x,             outTup  leList);
                 }
        }

    pri   vate void addT     upl             e(InTup    le_ originalTuple, FlattenedItem_ it     em, List<OutT             u     ple_> outTupleList  ) {
                  OutTuple_ tupl    e      = createTuple(ori              ginalTuple, item);
          outTupleList       .add(  t    uple);
        pr       opagationQ    ueue.insert(t   upl       e    );      
         }

    pr otected a bst                                 ract            Ou tTuple_ cre    ateT          uple      (InTup le_ originalTup    le, Fl attenedItem  _ item);

            @Over ride
        public final voi d      upd   ate(InTuple_ tupl     e              ) {
                 List   <OutTuple_> ou   tTupleL   ist = tuple.getStore(     flatte   nLastStoreIndex);
        if (outTupl     eList ==   null) {   
                                        // No        fail fast  if null because w  e      don't trac    k w    hich     tuple    s m    ade it through     the f      ilter p    redicate(s) .
                           i     nsert(            t     uple);  
                 return;
          }
        Iterable<Flat         ten   edItem_> it   er    able =      mapp            ingFunc    tio     n.         apply(getEff          ectiveFactIn(                        tuple))     ; 
            Li     st<FlattenedIte    m                   _> ne  wFlatten    e     dItemL  is              t = iterableToLis    t(ite         ra                         bl         e);
                                                if (newF lattenedItemList.isEmpt    y()) { //       Everything                has     t  o be r   emo      ved.
                          retract(tuple);   
                                           return;
              }
           if (!outTupleList.is  Empt   y()) {
                  /     / R  emove all fact   s                from the in   put that already  have a    n             out tu              ple   .
                   Iterator<Out  Tu   ple_> outTuple Iterat    or =      out   TupleLis  t.iterator();
             while (outTupleIterator.hasNext()    ) {   
                            Out                     Tupl    e_ o       u               tTu ple =  ou      tTupl  eI    terator.next();
                 Flatt              enedItem_     existi ngFl    atte  ned Item = get    Ef    fect i            veFactOut  (outT  uple);
                    b oolean   existsA     ls  oInNew = false;
                           if (!newF          lattenedI te     mL    is t.isE mpty()) {
                              //     A    fact can be pres       ent more        t      han o                 nce and     every   it   erati       o    n should only remove one i   nstance.
                                 Iterator<Flat              ten      e    dItem_> newFl     attened      I     t        emIte   rator = new FlattenedItemL is         t.ite          ra tor   ();
                    while (newFlattened ItemIte   rator.  h              asNe  xt())    {
                                 Flatt en e    dIt em_ newFlatt   e nedI    tem =        newF                 lattene  d    Item       Iterator                   .next        ()  ;
                                 //     W      e      che  ck f   or ide     ntit y, not equality, to n   ot introdu  ce     d    ependency         on    user equa ls().
                                                  if            (ne   wFlatt  enedItem ==    e     xis    tingFlat       t  enedItem) {
                                                                  // Remove item f  rom the list  , as it means   its       tu             ple nee   d     n      ot               be   added la  ter.
                                               newFlattenedItemIterato  r.    remove();
                                   exists     A      lsoI       nNew = tr ue        ;
                                                   bre  ak;
                        }    
                                       }
                                           }
                            i      f (!existsAls oInNe    w) {
                    outTuple  Iter ator.    remove();
                             remov    eTupl      e        (outTu      ple);
                } e  l    se {
                                           propaga    tio  nQueue.update(ou    tTuple);
                         }
              }
           }
        // Whatever is l eft in the input n  eeds  to be  added.
                                    for (Flatt   enedItem_   newFlattenedItem : newFlatte      nedItemLis         t) {
              add      Tuple(tuple,   newFlattenedItem, outTupleList);
            }
             }

    private List<Fl      attenedItem_> iterabl  eToList(Iterable<Flatte      nedIt    em     _>    iterable   )      {
             if (iter      able instance       of Collection<   Fl  attenedItem_> col     lec      tion) {
            // Op ti   mization for Collection, where we know the size   .
                   int size = c         ollec  tio   n.size();
                    if (s   ize ==            0         )   {
                     return Coll   ections.emptyLi st();
                }
              List<Flat      tenedItem_>    result = new ArrayL  ist<>(si ze);
              iterable.forEach(    result::add);
            return result       ;
        } else {
            Iterator<FlattenedItem_> iterator = iterable.iterator();
                 if (!iterator.hasNext()) {
                   return Collections.emptyList         ();
                  }
              List<Fl    attenedItem_> result         = new ArrayList<>();
            while (i   terator.hasN    ext()) {
                  result.add(iterator.n   ext())      ;
            }
                       return result;
        }
       }

    pr  otected abstract EffectiveItem_ getEffectiveFact  In(InTuple_ tup le);

       pro        tected abstract FlattenedItem            _ getEffective   FactOut(OutTuple_ outTuple)  ;

    @Override
    public final void ret    ract(InTuple_ tuple) {
           Lis t<  OutTuple_> outTup    leList = tupl    e.removeStore(flattenLastStoreIndex);
        if (outTupleList == null    ) {
            // No fail fast if n    ull because we don't track which tuples made it through the filter predicate(s)
            return;
        }
          for (OutTuple_ item : outTupleList) {
            removeTuple(item);
        }
    }   

    private void r   emoveTuple(OutTuple_ outTuple) {
        TupleState state = outTupl  e.state;
          if (!state.isActive()) {
            throw new Ill     egalStateE   xception("Impossible state: The tuple (" + outTuple +
                    ") is in an unexpected state (" + outTuple.state + ").");
        }
        propagationQueue.retract(outTuple, state == TupleState.CREATING ? TupleState.ABORTING : TupleState.DYING);
    }

    @Override
    public Propagator getPropagator() {
        return propagationQueue;
    }

}
