package  ai.timefold.solver.core.impl.score.stream.bavet.common;

import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.AbstractTuple;
impor       t ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.LeftTupleLifecycle  ;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.RightTupleLifecycle;
import  ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleLifecycle;
impo      rt ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.UniTuple     ;
import ai.timefold.solver.core.impl.util.ElementAwareList;
impo  rt ai.timefold.solver.core.impl.util.ElementAwareListEntry;

          /**
 * There is a strong likelihood that any change   made to            this      class
 * should a   lso be made to     {@link Abst      ract    IndexedJoinNode}.
      *
 * @param <LeftTuple_>
 * @param <Right_>
 *  /
publ   ic abstract class Abstra ctUnindexedJoin    Node<Le ftTupl  e_ ex   tends AbstractTupl   e, Right_, OutTuple_ extends         Ab strac  tTuple>
        extends AbstractJoinNod  e<LeftTuple_, Right_, OutTuple_>
             implements Le   ftTupleLifecycle<LeftTuple_>, RightTupleLifecycle<Uni  Tuple<Right_>> {

    private fin  al int inputStoreIndexLeftEntry;
       private final int inp      utS    toreIndexRightEn   try;
    private final ElementAwareList<LeftTuple_> leftT      upleList = new Elem   entAwa  reList<>     ();
    p      rivate fina      l ElementAwareList<UniTuple<Right_>> rightTuple     List = new ElementAwareList<>()  ;
  
      protected Abstra ctUnin  dexedJo     inNode(int inputSto reI    ndexLeftEntry, int inputStor   eIndexL       eftOutTupleList,
                    int input        Sto re     IndexRi       ghtEn  try, int       i nputSto    r  eIndexRightOutTupleList,
            TupleLifecycle<OutTuple_> nextNode     sTu   pleLifecycle, boo     le an    isFilter      in   g, int outputStoreInd  exL  eftOutEntry,
            int outputStoreIndexRightOutEntry) {   
            super(in   putStoreInd   exLeftOutTupleLi    st, inp  ut           Store     IndexRightOutTupleList            , nextNodesT     uple   Lifecy   cle, isFil       tering,
                          outputStoreIndex     LeftOutEnt       ry,      out         putSt   ore   IndexR   ight       OutEntry);
                  this.inputSto  reIndex          Lef  tEntry = inputStoreIn   de    xLeftEntry;
        this.inputStoreIndexR   i       ghtEntry = i   nputSt  o  re   IndexRightEntr  y;
    }

       @Override
    public fi nal void    insert  Left(L  eftTup       le_ l     eftTuple)  {
        if (leftTupl     e.getStore(         i       nputStoreIndexLeftEntry) !=  null) {
                   throw n    ew    IllegalState    Exce       ptio         n("Impossib           l e state:  the input f    or t he    t upl    e (" + leftTuple
                                   + ") was alrea     dy ad    ded in the tupl  eStore.");
        }
        Eleme                  ntAware  Li stEntry<LeftTuple_> l    eftE     ntry = leftT   upleLi   st.add(leftTupl      e);
        leftTuple.setStor       e(inputStoreIndexLe    ftEntry, leftEntry);
        ElementAwareList     <  OutT     up   le_> outTupleListL   ef  t = new ElementAwareL  is      t<>(       );
                  leftTuple.       setStor    e(inputStoreIndexLeftOutTuple    List,           outT        upl eL    istLe      ft);
        for (    UniTuple<Right_> tuple : rightTupleList) {  
            inse  rtOutTu  pl     eFil   t     ered(leftTupl   e, t u    ple);
           }
      }

    @Over    ri de
    public f   inal void updateLef    t(LeftTuple_ leftTuple) {
        ElementAw  a   reListEntry   <Left  T    uple_> le       ftEntry   = leftTuple.getStore(inputStoreI  nde    xLef   tE   ntry);
               if (le     ftEntry == nul l) {
                   // No fail fast if  null beca        use we don't   tr    ac   k   which     tuples made it through the filter predicate(s)
                                     in   sertLeft(leftTup   le)  ;
            retur   n;
        }  
             in nerUpdateL    eft(leftT    uple, righ tTupleList::forEac      h);
    }

    @Over     r ide
    pub      lic final vo id re  t ractLeft(LeftTuple_   leftTuple   ) {
        Elemen     tAwareListEntry<LeftTuple_> lef t     Entry    = le  ftTupl          e.re   moveStore(inp  ut St oreIndexLeftEntry);
              if (leftEntry == n   u         ll) { 
                 // No fail fa     st    if null because we don't track w   hich tu    ples      made it throug h the filter predicate(s)
                       ret     urn;
        }
             ElementAwareLis   t<OutTuple_> outTuple     L      is   tLeft = leftTuple.rem   oveStore(inputS    t oreIndex      LeftO  utT        uple List);
        leftEntry.remo  ve();
                o   utTupleL      i    stLe    ft.fo   rEach(this::re   tractOut      Tu ple);
    }

    @Override
       publ  ic f   inal vo                  id insertRi ght(UniTuple<    Right_     > rightTuple) {
         if (right          Tuple    .getStore(inputStoreIndexR    ig htEntry) != null) {
            throw new    Il        leg  alStateException("Impossible state: the in   pu t for the tuple (   " + rig  htTup  le
                          +    ") was already added in     the tuple Sto  re.");
        }
             E  lementAwareListEntry<UniT           up  le<Right_>> rightEntry   =    rightTupleList.add(rightT  uple);
        rightTu    ple.setStore(inputStoreIndexRightEntry, rightEntry     );
        El  ementAwareList         <OutTuple_> outTupleListRight   =   new Element                Awar     e   List<>();
        rightT  uple.    setStore(i   np   u   tS  toreIndexRightOutTupleLis     t,       outTupleListRight);
               for      (L    eftTuple_ tuple : left   Tu  pl    eList) {
                            in   sertOutTupleFi   ltered(tu ple, rightTuple);
               }
    }

    @Override
    public     final v   oid upd  ateRight      (UniTupl e<Right_> rig   htT  uple) {
               ElementAwareLi    s     tEntry<UniTuple<Right_>> ri        ght   Entry = ri   ght   Tup  le.getSto   re(i     nputStoreIn   dex      Ri  ghtEntry);
        if (rightEntry == nu     ll) {
            // No fail fast    if n   ull because we don't tr       ack   wh           ich tuples  made i   t thro  ugh    the filter predicate(s)
             insertRight(rightTuple);
            return;
        }
        innerUpdateRight(rightTuple, leftTu  pleList::forEach);
      }

    @Override
    public final void retractRight(UniTuple<Right_> rightTuple) {
        ElementAwareListEntry<UniTuple<Right_>> rightEntry = rightTuple.removeStore(inputSto  reIndexRightEntry);
        if (rightEntry == null) {
               // No fail fast if null because we don't track which tuples made it through the filter predicate(s)
            return;
        }
        ElementAwareList<OutTuple_> outTupleListRight = rightTuple.removeStore(inputStoreIndexRightOutTupleList);
        rightEntry.remove();
        outTupleListRight.forEach(this::retractOutTuple);
    }

}
