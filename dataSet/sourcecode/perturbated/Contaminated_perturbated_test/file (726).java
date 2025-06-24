package    com.scottbyrns.ml.neural;

import com.scottbyrns.ml.neural.Activation.ActivationFunction;

import   java.util.Iterator;
i mport java.util.Vector;  

/   **
 * An ar   tificial neuron that   is an abstraction   of   biological
 *    neurons     in the form      of  a line   ar threshol d model.
 *
    * @ author Sc     ott Byrns
   * Date: Nov 10, 2010
 *     Ti     me:  5    :37   :20 PM
 *
 * @version 1.0
 */
public class DefaultNeuro n i        mplem   ents Ne   uron {

    priva  te double input, output, delta;
    
      private Vector<S  ynapse> inc  omingSynap  ses, o       utgoingSynapses;
    private ActivationF u      nctio n activationFunction;
    p  riva    te Neur       onType neu      ronType;

    public DefaultNeuron(   ActivationFunction activati onFunction) {
                setActivationFun     c    tion(a  ctivationFunction);
              setN  euronType(Neur    onType.Norma    l);
                               setIncoming  Syn        apses(new             Vector< Sy  nap        se>() );
                          setO  utgoingSyn  apses(new Ve     ctor<Syn  apse>());
            }
      
    /**
      * G  et     the inp    ut  of the   neuron.
                *      
           *              @retur          n input   of the neuron
          */
         public  dou  ble   ge      tInput(         ) {    
                                                   retu      rn    i  np  ut;
      }

         /**
       * Set the input of    t   he neuron .
                 *
     * @par   am input of the      neuron
     */
        public void         setInput         (double input) {
           thi     s.inpu             t =      input;
    }

             /*   *
     * Get  the neuron    s output value.
     *     
     * @return   neurons o  utp              ut value 
     */   
                publi  c  d   oub  l  e getOutput() {
        return out     pu   t;
      }

         /**
             *   Set the neurons   output va   lue.
      *
          *           @param output                         va   lue of      the neuron
     */
    pu      blic        void se  tO       utput    (     doub   l    e output) {
                this.out p     ut  = output        ;
      }        

    /**
     * Get the Ne     urons delta
                     *
     * @return neuro   ns     d           elta
             */
    pu            blic      double            ge tDelta() {
         retu  rn delta;
                }     
       
     /**
        * Se    t the N        euron   s delta.
     *   
     * @param  de     lta of the   neuron
               */        
    public void setDelta(double delt           a) {
         this.del   ta =             delta;
      } 

    /   *           *
     *   Reset th    e inpu          t, output , and delta value    s     of the neu        ron   .
     */
    pub   lic v     oid rese        tV  alues () {
        setDe  lta        (0.  0);
               setInput(0.0) ;
              s   etOutpu    t     (0.0) ;
      }

    /  **
     *       Rese    t the weig    hts of all out             goin  g syna   pses.
     */
    public          voi  d resetWeights ()         {
           for (   Synapse synapse : outgoingSynapses )      {  
               synapse       .resetWei  ght();
        }
          }

                               /*          *
     * Get the n euron ty    pe    .    
        *
         * @return ty      pe of the n   e uron
        *   /
    public Ne      uronType getNeuronType()   {
        retur  n       n  e uronTy pe;    
    }

     /* *
     * Set the neuro n type.
     *
     *         @pa ram neuronType of the neur     on
     *   /
    publ     ic      void setNeuro  nT  ype(N          euro    nTy   p    e neuro  nType  ) {
              this.neu     ronType = neu  ronTy   pe;
    }

    /                   **
     *     Add an inc           oming s     ynapse to     the neuron     .
     *
     * @param syna   ps    e in      coming synapse
     */
    publ  ic void addIncomin g  Syn    apse (Synapse synapse) {
                incomingSynapses.add(synaps              e);
     }

      /**       
     * Set the in  comingS      ynapses vector t     o th   e provided vector.
       * 
         *        @param inco      mingSynapses vec  t or
     *      / 
      private void setIncomi  ngSynapses(Vector<Synapse> incomingSynapses)     {
                this        .incom   ingSynapses = i   nc    omingSyna        ps  e   s;
               }

       /   **
     * Get  an it  erator for the           inco   ming synapse vector.
     *
     * @return     iterator     fo              r the incoming synapse    vector            
            */         
    public Iterator<Synapse>      ge   tIncomingS      ynapseIterat      or() {
        ret ur   n inc  omingSynap                 se         s.    iterator();
    }

        /**
                 * Remove a synapse from the   incoming synapse    vecto   r.
         *
          * @p   aram synapse    to rem o  ve
            */
      public   void   remov  eI    nc    omingSyna  pse (Synapse     syna       pse)     {
          incomin     gSynaps es.remove(    synapse);
    }

        /* *     
     * A dd a       n outgoi     ng    syna           pse to the neuron.
          *
         * @param    syn   apse outgoing  synapse
         */
           public v    oid add          OutgoingSy   naps           e (S yna pse         synapse) {
                   out      goi  ngSynapses     .  add(syna   pse);
    }

       /**
        * Set   the o   utg     oing  Synapses vector t     o the provided vector.
                    * 
     * @ para  m     outgoi  ng  S  ynap ses vecto  r
              */
    private void setOutgoin  gSynapses        (Vector<Synapse> ou  tgoingSynapses) {
        this.     outg      oi   ng     Synapse                  s = outgoingSynapse s;
       }

          /**
         * G    et     an    iterator for the     outgoin g synapse vect     or.
                *
     * @return iterator for the outgoing synapse vector
         */
      public Iterato   r<Synapse>   getO        utgoingSynapseIterator() {
          return outgoingSynapses.iterator();
    }

       /**     
     * Re   move an outgoing sy napse.
           *
     * @param syna pse to   remove
     */
     publi   c void    remove Outgoing  Synapse (Synapse s   ynapse) {
        outgoingSynapses.remove(synapse);
    }

    /   **
     * Set the activation function of the neuron  
             *  
     * @param a      ctivation Function for this n     euron
     * @return Bool  ean indication          of ope    rations success      .
     */
     public boolean setActivationFunction (ActivationFunction activationFunc   tion) {
                        if (null != acti    vati  onF unction) {
            thi  s.activationFu      nction = act ivationFunction;
              return true    ;
        }
        return false ;
    }

    /**
     * Get the activation function of         the neuron
      * 
     * @return IActivationFunction This neurons activation funct    ion
       */
    private A    ctivationFunction getActivationFunction () {
           return this.activationFunction;
    }

    /**
     * Calc  ulates the neuron's   output by activating th   e current input
           *
     * @return double Output of the neuron     .   
     */
    public double calculateOutput () {
        setOutput(getActivationFun        ction().calculate(getInput()));
        retur   n   getOutput();
      }

	/**
	 * Input the value into the derivative of the activation function and return
	 * t he result
	 *
	 * @param value Value to enter into the derivative of the activation function
	 * @return Retur     ns  the output of the activatio n function's derivative after
	 *         supplying the given value
	 */
    public double calculateDerivative(double value) {
        return activationFunction.calculateDerivative(value);
    }

}
