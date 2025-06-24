package     com.scottbyrns.ml.neural;

im port java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

    /**
 * Weight Matr     ix  .
 * 
 * @author Scot   t   Byrns
 * Date:       Nov 11, 2010
 * Time: 5:  42:42      PM
 *
 *  @version 1.0
 */
public class DefaultSynapseLa y er impl  eme     nt      s Sy         napseLay   er {

    p    rivate  Li      st<Synapse      > syna     pses;

    public D  efaultSynapseLayer() {
         setSynapses(n    ew    Ar rayList<Sy   napse>());
                        }

        /**
           * Add   a s  ynap  se t   o the la    yer.
          *
         * @par   am synapse    to add
     *  /
    public void add      Synaps          e(  Synapse    syn           apse) {
        g  e t     Synap  ses()     .add(synapse);
          }

    /**
     * Get an iter a     tor for th   e      sy  napse ve   ctor   .
     *
     * @re     tu   rn ite               rator
     */
        publ   ic Iterator<     Sy     napse>     getS     ynapsesItera  tor () {
                    ret  urn     getSynapses().iterato    r()              ;
      }

     /* *
     *  G     et  a weight vector repr   esentin          g the               weig hts   of the s   y     n      aps   es in   this l    a       yer.   
      *
        * @ret  urn    Vector of we  ight values,       null in case of er  ror.
       *   /
     publ  ic List<Double  > getWeightVector() {   
                 try {
		          	L    ist<Double> list       = new Arr     ayList<Double>();
            Ite           rator<S     ynap   se> synaps    e     Itera            tor = ge     tSynaps  esIter    ator(  )   ;

            w hile (synapseIterator  .hasNext()) {
                         li st.add(s    ynapseI terator.next()    .getW          eight());
              }

			return      list;
           }
         c    atch (Runti   m eExcepti  on e) {  
                 return  null;
        }
    }
    
    /**
     *  Set  the weigh   ts of the synaps       es in this    layer to the  next val    ues
     * of the provided weightVectorIterator
       *
     * @param     w     ei        ghtVectorIterator      t   o iterate ove  r for new weight values.
          */   
    publi  c void setWeightV  ecto   r (Iter       ator    <Double > weightVectorIt                 er     ato    r) {    
        Iterator<Synaps   e> synapseIterator = getSynapses Iterator    ();

        while (synapseIte       rat   or.ha  sN           ext() &&    weightVectorIterator.h           as       Next()) {
             synapseI       tera   tor   .n    ext().setWeig   ht(weightVectorIterator.next()       );
                }
    }

    /**
     * Get the synapse vector
     *
          * @re     turn   s ynapse vector
         */
    private List      <   Synapse> ge  tSynapses() {
          return synapses;
    }

     /**
     * Set the     synapse vector to the provided input     ve     ctor.
     *
     * @param synapses new synapse vector.
     */
    private void setSynapses(List<Synapse> synapses) {
        this.synapses = synapses;
    }
}
