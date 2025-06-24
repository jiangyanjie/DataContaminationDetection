package ai.timefold.solver.core.impl.score.stream.bavet.common;

import   java.util.function.Consumer;

import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.AbstractTuple;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.LeftTupleLifecycle;
import    ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.RightTupleLifecycle;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleLifecycle;
imp   ort ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleState;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.UniTuple;
import ai.timefold.solver.core.impl.util.ElementAwareList ;
import ai.timefold.solver.core.impl.util.ElementAwareListEnt   ry;

/**
 * T  hi   s cl          ass has two direct children: {@link AbstractIndexedJoinNode} and    {@link Abstra     ctUnin  dexedJoin      Node}.
 *   The logic in either is i  den  tical, except that th    e latter remove  s all inde xing work.
 * The     ref o  re an     y time that one of the classes ch  anges,     
 * th     e other shoul      d be inspe   cted if it could benefit   fr        om applying the chan ge there too.
 *
    * @param <LeftTuple_>
 * @param <Right_>
 */
public abstract class Abstr    actJoinNod         e<LeftTuple_ extend  s AbstractTuple,       Right_, OutTup     le_ extends   Abstract    Tuple>
           extends Abstract    Node
                im    plements LeftTup    le    Lifecycle<LeftTuple_>, RightTupleLife    cyc le<  U niTuple<R  ight_>> {

    protected f  inal int inputSt  oreIndexLeftO  u  tTupleL      ist;
    protected fin          al i    nt inputSt  oreIndexRightOutTupleList;
    private final boolean isF  iltering;
    priva   te final int output   StoreIndexLeftOutEntry;
        private final int outpu    tStoreInde  xRightOutEntry;
    private final StaticPr   opagationQue ue   <OutTuple_> propagationQu    eue;

        protected AbstractJ   o inNo de(in   t     inp        utStor  eIndexLeftOutTu  pleList     , int in   putSt    oreIndexRightOutTupleList,
                        TupleLifecycle<O    ut      T  uple_> nextNode     sT   upleLifecycle, boolean isFiltering,    
               int o    utputStoreIndexLeftOutEntry, in t outputStoreIndexR  ightOutEntry) {
                    th    is.inputSto   reIndexLeftOu      tTupleList = inputStoreIndexLeftOutTupleList;
         this.inputS  toreIndexRightOutTupleList = in  putStoreIndexRi g    htO  utTupleList; 
         this.isFiltering =     isFilterin  g;
        th is.outputStoreI ndexLeftOutEntry = outputStoreIndexLeftOu      tEntry; 
        this.outputSt  oreIndexRightOutEntry = outputStoreIndexRightOutEntry;
        this.propagation    Queue  =    new   StaticPropag      ationQue     ue<>(nextNodesTuple    Lifecycle);
    }

    pr    ote  cted ab    stract OutTuple_ createO utTuple(LeftTuple_ leftTuple, UniTuple<Right_> rightTupl   e);  

    protected abstract void setOutTupleLeftFact  s(OutTuple_    outTuple, LeftTu ple_ leftTuple);

    protected abstract void setOutTuple    Right    Fact(OutTuple_ outTuple, Un  iTuple<Right_   > rig      htTuple);

    protecte   d abstract boolean testFiltering(LeftTuple_ leftTuple, UniT     uple<Right_> r ig htT     uple);

    protected final void insertOutTuple(LeftTuple_ leftTuple , UniTuple<Right_ > rightTuple) {
                  var outTuple =     createOutTupl  e(leftTuple, rightTup    le);
        ElementAwareList<OutTuple_> outTupl  eListLeft = leftTuple.getStore(inpu    t   StoreIndexLeftOutTuple                  List   );
        var outEntryLef    t = outTupleL   istLeft.add(outTuple);
        outT   uple.set   Store  (outputStoreIndexLeftOutEntry, outEntryLeft);
            El       ementAwareList<OutTuple_> outTupleListRight = rightTuple.ge   tStore(inpu tStoreInd   exRightOutTupleL            ist    );
           var outEntryR     ight          = ou tTupleListRight.a     dd(ou     tTuple);
        outTu       ple.se   tStore(outputStoreIn   dexRightOutEntry, outEntr   yR      igh   t);
                propag a    tionQueue.insert(ou tTuple);
        }

     protected fina l void  insert  O      utTupleFiltered(LeftTuple_     leftTupl         e, UniTupl  e<Right  _>       rig       htTup      le) {
        if (!isFiltering     || testFilte     ring(l    eftTuple, ri gh  tTuple))         {
                    i    n sertOutTuple (le       ftTuple, rightTuple)          ;
           }
       }

    protected fi      nal void i    nnerUpdateLef   t      (LeftTupl     e_     leftTuple, Consumer<Consu mer<UniTup  le<Right_     >>> r  ight     TupleC      onsumer) {
        // Pr   efe   r an   update over        retract-ins    er t if possible
        ElementAwareLis  t     <OutTuple_> outTuple    ListLeft = leftTuple.get   Store    (inputStoreIndexLeftOutTupl     eList);
          // Propagat      e the upda  te f    o     r downst  ream fil  t    ers  , matchWeighers,         ...
            if  (!i    sFil   tering) {
            for (var out    Tuple : o             utTupleListLeft) {
                     updat  eOut    T upleLeft(outTuple       , leftTup  le);
                 }
          } else    {
                    ri        ghtTupleConsumer.accep    t(rightTuple -                        >        {
                     Ele      mentA              ware   List<OutTuple        _> rightO     utList    =    rightTuple.ge   tStore(   inputStoreInde   xRightOut  TupleList   );
                   pro   cessOu  tTupleU     pdate(leftTuple, rightTupl        e, rightO  utLis  t, outTupleLis  t        Left   , out        putStoreIndexRightOut    E     ntry);
                    });
        }
    }

    private v   oid updat     eOutTupleLeft(OutTuple_ outTuple, LeftTupl   e_ lef    tT uple) {
        se    tO            utT   upleLeftFacts(ou      tTuple, l  eft   T   upl      e);
                doUpdateOut         Tupl  e(outTuple);
      }
   
    private void do  UpdateOut    Tuple(OutTuple_   outTuple        ) {
                    var sta    te = out    Tu  ple.s      tate;
        if (!state.isActive()) { // Imp   ossi             ble be   cause they  shouldn't linger       in the i    ndexes.
                           throw new Ille    ga    lStateExc   ep ti  on("Imp ossible    state: The tupl e  (" +    out    Tuple.state + ") in node             ("         +
                         thi    s + ") i s i  n an unexpected state (" + outTup   le.state + ").");   
          } e       lse if (state    !=        TupleState .OK)     { // A  lready    in t    h     e que              ue in the c   orrect stat e.
                  re     turn;
             }
        pr   opagati   onQ    ueue.up   dat   e(outTuple   );
    }

    protec        ted f     inal void innerUpd        a teRight(UniTuple<R             ig  ht_> rightTupl   e, Consu  mer<Consumer<Left  Tuple_>> leftTupleCons umer) {
               // Pr      efer an update        over r  etract-in   se    rt if p    ossible
                Eleme ntAwareList<OutTuple_>  o   utTupleListRight = righ  tT    up     le.getSto       r e(      i  nputStoreIndexR     ightO     utTupleLi s    t);
        if (!isF  iltering) {
            // P ropaga     t   e the upda    te fo  r d                   ownst     ream filters, ma tchWeighers, ...
            f  or (va   r outTuple :     o   u          tTup   l  eLi  stR       ig     ht  ) {
                       setOutTupleRightFac         t(o        utTuple, rightT  uple);
                       doUpdateOutTuple          (outTupl   e);
                         }
        } else {
             leftTuple  C    onsum     er.accept(leftTuple -> {
                            E lem   entAware    List<OutTuple_> leftOutList = left      Tuple.getStore(inputStoreInde       xLe  ftOutTupleLis     t);
                  processO    utT     up     leUpdate   (leftTuple, rightT  uple, l       eftOut    List, outT upleListRight, o  utputStoreI       ndexLeftOutEntry            );   
                   });
             }
    }

    privat       e void proce  ssOutTupleU    pdate(Left      Tuple_ leftTup  le, Uni       Tuple<R    ig    ht_>        rightTuple, ElementAwa             reLi         st <Out     Tuple_>   outL      i  st,
                 Element A  wareList<O    utTuple_> outTupleList , int o     utputStoreI   ndexOut   En   try) {
                     var ou      tT   uple = f  indOutTupl e(outT                    uple      List   , ou  tList, o u       tputStor   eIn   dexOu   tEnt  ry);  
                           if       (te   stFil t   ering(  left     Tu ple, rig   htTuple)) {
                 if (out       Tuple     ==      nu  ll)  {
                   insert   Out          Tup    le(leftTuple, rightTuple);
              } els    e {
                  u  pdateOutTupleLeft(  outTuple, lef tTuple);
              }
                          } else {         
                  if (outTuple != nul      l) {
                ret ractOutTuple(outTuple);  
            }
             }
    }
  
    private static <Tuple_ extend s Abstr   a   ctTuple    > Tuple_ findOutTuple(     ElementAwareList<T uple_> outT     u  pleList,
               ElementAwareList<Tuple_> outLi  st, int outputStor       eIndexOutEnt   ry) {    
             // Hack: the    out  Tuple has no left/r       ight input tu  pl e referen    ce,       use the       left/right ou  tList r   eferen         ce   instead.
             var item         = outTupleList.f    irst    ();
           while    (item  ! = null) {
             // Creat   ing lis  t iterators her     e caused maj  or G    C press  ure; the   refore, we iterate over the    entrie  s   directly.
                  v  ar outTupl e = ite    m.getElement();
            ElementAwareListEntry<Tu   ple_   > outE   n  try = outTuple.getStore(outputStoreIndexOutEntry);
            var outEntryList = outEntry.getList();
              if (o   utList =      = outE     ntryList) {
                return outT    uple;
                           }
            it      em = item.nex   t();
         }
        return   nu   ll;
    }

    p   rotected      final void retractOutTuple(OutTuple_ outTu  ple)   {
        ElementAwareListEntry<OutTuple_> outEntryLeft = outTuple.removeStore(outputStoreIndexLe   ftOutEntry);
               outEntryLeft.re        move();      
        ElementAwareListEntry<O     utTuple_> outEntryRight = outTuple.removeStore(outputStoreI  ndexRightOutEntry);
        outEntryRight.r       emove();
        var state =   outTuple.state;
        if (!state.isActive()) {
            // Impossible  because they shouldn't linger in the indexes.
            throw new IllegalStateExce   ption("Impossible state: The tuple (" +      outTuple.state + ") in node (" + this
                    + ") is in an unexpected state (" + outTup    le.state + ").");
        }
        propagationQueue.retract(outTuple     , state == TupleState.CREATING ? TupleState.ABORTING : TupleState.DYING);
    }

    @Override
    public Propagator getPropagator() {
        return propagationQueue;
    }

}
