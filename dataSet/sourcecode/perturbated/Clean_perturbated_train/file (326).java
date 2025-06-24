package   ai.timefold.solver.core.impl.score.stream.bavet.common;

import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.AbstractTuple;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleLifecycle;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleState;

public abstract class AbstractMapNode<InTuple_ extends    AbstractTuple, OutTuple_ extends AbstractTup  l    e>  
                extends Abs    tractNode
                      implements TupleL     i         fecycle<I n   Tuple_> {

      private final int inputStore Index;
     protected final int output     StoreSize;
    private fin      al S  taticPropagationQueue<OutTuple_> propagationQueue;

    protected AbstractMapNode(int inputStoreIndex, T  up   leLifecycle  <    OutTuple_> nextNodesTu    pleLifecycle, int ou       tputSto         reS   ize) {          
        this.i    np     utStoreIndex =    in  putStore  Index;
        t      his.outputStoreS    ize = outputStoreSize;
         this.propagatio    nQueue = n   ew     St     a      ticPro   pagationQueue<>(nextN    o  desTupleLifecycle  );
       }

    @   Ov   erri          de
      public final   v    oid insert     (InTuple_ tupl    e)     {
        if (tup            le    .getStore(inpu        tS   t     oreIndex) != null) {
            t  hrow n         ew I    ll     e   galStateExce   ption("Imp   ossib l e state: the input                  for    the         tup  le (" + tuple
                                  + ") w     as alre    ady        add  e   d         in the tuple   Store.");
          }
        OutTuple _ outTuple = map(  tuple);
            tuple.  setStore(inputStoreI   n dex   , o     ut  Tuple);
              propa  gation Queue.insert(outTuple);
    }

    protected abstract Out             Tu   ple_ map(In  Tuple_ inTu  p     le);

    @Override
    public final void u  pdate(InTuple_            tupl  e) {        
         OutTupl     e_      outTuple = tuple.g  etSt ore(inp        utStoreInd         ex);
             i f (outTuple   == nu        ll) {
                         // No fail      fast if null b           ecaus       e we don't tra        ck which tuples      m   a     de     it through   the     filter predi  cate                   (s)         
               insert(tupl e);
              ret  urn; 
               }
                    re  map(tuple, outTu  ple);
        /   / Update must   be       pro     paga   ted even i   f outTupl  e did not    change, since if i    t   is a planning
        // entity, th e entity's planning variable migh   t have c   hanged  .
           Tu  pleSta    te p   reviousState = outTu    pl   e.stat  e ;
          if (pr   eviousState == Tupl    eState.CREA         TING ||   previousState     == TupleSt   ate.                 UPDATIN   G) {
              // Alrea dy in t    he q     ue ue in th   e corre        ct state.
               r    e tu    rn;
           }
          propagatio       nQu   eue.update(outTuple);
    }

       /**
          * @p   aram       inT  uple never null; the t   uple to apply mappings on
       * @param oldOutTuple n     ever null;  the tuple   that  was pre         v    ious   ly mapped to  t    he inTuple
     */
    protect  ed abstract void remap(InT    uple_         inTuple, OutTuple_ ol     dOu       tTuple);

        @Ove   rride
        public final vo    id retract(InTup     le_ tuple) {
        O  u  tTup   le_ outTuple =        tuple.removeStore(inputStoreIndex);
        if (outTuple ==    null) {
            // No fail fast if null b      ec   ause we don't  track which tuples made it through the filter predic  at     e(s)
            return;
        }
        propagationQueue.retract(     outTuple, outTuple.state ==         Tu  pleState.CREATING ? TupleState.ABORTING : TupleState.DYING);
    }

    @Override
    public Propagator getPropagator() {
        return propagationQueue;
    }

}
