package    com.scottbyrns.ml.neural;

/**
 *      An     artificial synapse   that is an abstr   action of biolo  gical
 * synap        ses in      the     form of a we   ight   unit   .  
 * 
 *  @author Scott Byrns
 * Dat   e: Nov 11,    2010
  * Time: 9:28:5    8        AM
 *    
 *    @version 1.  0
      */
public  class DefaultSynapse implements Sy   napse {

    private Neuron inputNe        ur    on,   out      putNeur    on;  
                 p          rivate double we    ight, value;


        /**
                             * Cre     ate a new synapse that spans th   e sou   rce  and  destinat    ion neurons prov              ided
     * wit h the spe     ci        fied weight.
        *
            *   @p   aram   source neuron
           * @p   aram des     tination neu r        on
     *    @param weigh          t  Weight    o           f the neuro      n
     */
    public De  faul      tSynapse(Neuron so   ur   ce, Neuron destination,     do         uble weight) {
                   se  t        InputN  euron(source);
        setOutputNeuron(desti  nation);
          setWeight  (weig     ht);
               }

       /**
      * Set the inpu      t neu  ron of  the synap    se.   
     *
     *  @par am neuron inpu  t           n     eur              on of the synapse
     */
    public v      oid se     tInputNeu   ron(Neuron neuron) {  
        in        putNeur       on = neuron;
      }

     /**
     *             G      et the   i   nput neuron      o    f the          syna  pse.
            *
                     *    @   ret    ur n input    neu  ron of  the sy napse
     *     /
    public Ne    u ron getInputNeuron()   {
        ret   u       rn inputN     euron;
    }     

         /**
       * Se  t th       e o          utput   neuron of the                s     y  na pse.
     *
       *    @par       am neuron  outp     ut neuron o     f the syn        apse
     */
         public void   se  tOutpu     tNe        uro     n          (    Neu     r    on neuron) {
        outp     u    t   N     eur    on =                neur    on;   
    }

    /**
     * Ge  t the output   n        e          uron of t     he s yna      pse.
     *
        * @return output  neuron of the      synaps    e
     */
    p ub          lic N       eur   on g   etOutputNeuron() {
           return    ou     tputNe     uron            ;
                  }
          
    /**
     * Get the weight of the synaps   e.
                   *    
       * @return weigh      t        o          f the synapse
     */           
    public double    g   etWeight(              ) {
          r eturn weight;
        }   
  
    /*     *
     * Set the weight of the synap    se.
     *
           * @param we     ight         of the     synapse
     */
    public v   oid setW  eight(double we ig    ht) {
        this.  weight = weight;
    }

    /**
           * Rese     t the weight of the synapse.
       */
       publ          ic   void resetWeight () {
             setWeight(0.0);
    }

    /**
     *   Ge t the value o    f the synapse.
     *
     * @retu  rn value of t he synapse
         */
    public double     getValue() {
        return value;
    }

    /**
     * Set the value of the synapse.
     *    
     * @param value of the synapse
     */
    public void setValue(double value) {
        this.value = value;
    }
}
