package  com.scottbyrns.ml.neural;

import com.scottbyrns.ml.Mathematics;
import com.scottbyrns.ml.datasets.Pattern;
import com.scottbyrns.ml.neural.Activation.ActivationFunction;
import   com.scottbyrns.ml.neural.Activation.ActivationFunctionSigmoid;

import java.util.ArrayList;
import   java.util.Iterator;
import java.util.List;

/**
   *   D   efault im     plementati   on of the FeedForwardNeuralNetwork interface.    
 *
 *     @author Sco       tt Byrns
 * Date:            Nov 11, 20     10
 * Time: 2:1  0:48 PM
 *
      * @version 1.0
 */
public cla ss DefaultFeedForwardNeuralNetwork impl     eme  nts Fee  dForwa   rdN  eu    ralNetwork  {

    publi    c   s   tatic fina l int DEFAU  LT_NUMBER_BIAS_NEURONS = 1;

    private Lis   t<NeuronLa     yer> neuronLayers;
    private Li  st<SynapseLayer> synapseL    ayers;

    /**
                  * Create an instance of a DefaultFeedForwardNeuralNetw   ork from an   existing FeedForwardNeuralNetw   o   rk
     *
              * @param      feedForwardNeuralNetwor k    neur        al netwo  rk to cop  y.
     */
    public DefaultFeedForwardNeuralNetwork    (FeedForwardNeuralNetw o  rk   f   eedForwa  rdNeur    alNetwork) {
                /* Sorry        fo   r     the formatting, this method call was way too   long visually otherwise   */
         th  i     s(
                feedForwar   dNeu ralNetwork.getNumberN   euronsInput(NeuronTy    pe.Normal),
                      f  ee         d  ForwardNeuralNetwork.getNumber    Neuron          sHidden(Neur    onType.   Normal),
                  feed     ForwardNeuralN    etwork.getNu    mberNeurons       Ou  tp  ut(NeuronType.Nor       mal    )   
                            );
     }

                          /**
     * Creat e a new DefaultFeedForwardNe  uralNetwork
     *
     * @param    inputSize num     ber of inputs
     * @p     aram hiddenSizes array    of  hidden      laye    r neur   on count s
     * @param o        u  tputSize number  of outp   uts
     */
    public DefaultFeedForwardNeu ralNetwor   k(int  inputSize, int[]     hiddenS    izes, int outputS     ize)   {
                 th  is(inputS      ize,   h    iddenSizes, ou tputSiz  e         , new Activatio      nF            unctionS  ig moid()  );      
         }

    /**
             * Cr     eate a    new DefaultFeed ForwardNeuralNetwork
     *
             * @param inputSize            numb     er of inputs
     * @p a    ram hiddenS     iz      es ar         ray of hidden layer neuron counts
         *        @param           o     utputSize number           of outpu   ts
     * @param activa  tionFunction neuron   activation function.      
     */
    public D  efaultFeedForwar   d   NeuralNetwork(int inputSize, int    [] hiddenSizes, int outputSize, Act    ivationFunction activa    tionFun    ction) {     
        t  ry {

            // Cr  eate the layers
			in  t bias_nodes_per_layer = DefaultF    eedForwar   dNeuralNetwork.DEFAULT_NUM      BER_BIAS_NEURONS;
		   	setNeu    ronL  aye  rs(new   ArrayL  ist<Ne       uro    nLayer>(   ))   ;
			getNeuronLayers().              add(ne    w DefaultNeuron   La            yer(inp utSize,   activationFunction    , bias_nodes_pe   r_ layer));

                for (int x = 0; x < hidd enSize    s.leng   t               h; x++) {
                 getN    euronLa   yer         s().add(     new Defaul    tNe  uronL        ayer(hiddenSizes[x       ],     activ   ationF     unctio  n,     bias_n    odes_pe    r_lay    er));
             }

			getNeur   onLayers().add(new DefaultNeuronLayer        (outputS   ize, activa ti    onFun     ction))        ;   
        			//   Con  n   e  ct the layer   s        
			se  t    Synap    seLay  ers(new Ar   rayList<Synapse Layer>());
			for (int x = 0; x < getNeuronLaye     r  s().s   ize(         )   -   1; x+ +) {
                             conn  ectLayers(x, x   + 1);
               }

                            }
        catch     (Runtime                        E      xception e     ) {
               e. printS       tack    Trace();
               }
     }

            /**
     *        Connect t       wo   neuro      ns    together a     nd re    turn t   he conn    e cting synapse.  
                *
     * @param source neuron
        * @param            dest   in ation     neuron
                    * @  return    The c             on necting ne        ur on or null if         some    thing wen     t wrong.
             *    /
       pu  blic Synap          se conn       ectNe             uro           ns(Neuron sour       ce, N        e      uron d estinat    io   n)  {

                         try {
            do  u    ble weight =                           Mathematics.rand();
                 Synapse s   yn a       p   se = new Def  aultS    ynapse(source,    des      tination,                 we        ight  );

                        source.a    ddOutgoingSynapse(synaps  e);
                      destinati    on   .addIn     coming    Synap    se(      synapse);

                    re   turn s  yn   apse;                
                 }
                          catch (Runti       me   E   xcept         ion e) {
                         re    turn null   ;
           }

       }

       /**
     * Calcula    tes the netwo  rk's outp    ut by fe        edin    g    the i    nput all the way to the
           * outp  ut   la   ye  r
            *
           *  @p aram      i    nput The netwo    rk's input
     *         @return Returns the network's outpu   t                          , o r null      in case of error
                 */
       pu b   li         c List<Double> feedForw  ard(L      ist<D    ouble>    i   npu  t) {
             try {
                 res etValues(  )   ;
                       fe    edForwardInpu tL        ayer(input );

                         Iterator<N      euronL     aye       r> ne   uronIterator = getNeuronLa      yer     sIterator ();
                                           NeuronLayer n     eu   ro nLa         y  e   r;

                   n  e uro    nI t    e rator.next().fe edForward();

             wh  ile     (ne   uro      n  I    terator    .ha  sNext ()) {
                                     neuronLayer  = neu   ronIter      ator.next() ;

                   if (neuronIterator.  hasN ext()) {
                          neu   ronLayer.cal      cula    teOutput(     )  ;    
                                                 ne        uronLayer.feedForward();
                              }
                              else {
                        neuronLayer.calcu l        ateO    utpu    t();
                                        return neu ro   nLayer.g etOutpu t();
                }
             }

                        re    turn null;
        }
        catch (Runtim       eException e) {
                    return        n  ull;
            }
       }

    /**  
      * Return  s     t  he number of   neurons in th  e hidden layers 
       *
              * @pa    ram neuronT ype of neuron (us            e                 constant s in class De       faul          tNeuron)
        *     @return Number   of   neurons i  n th e hidden layers
     */
    p  ublic int[  ] getNumberNeuron   sHidden(N      euronType        ne      uronTyp   e) {

                        int[] hidden = new int[     getNe  uronLayers()    .size() - 2]   ;
                    fo r    (int x = 1; x <   getNeuronLa    y    e    rs    (). siz     e() - 1;    x++)     {
               hidden[x - 1    ]       = getNeuronLa yers().get(x).getNum  berOfNeuronsOfType(ne   uro       nType);
           }

           retur n     hidd en;
    }

	  /**
	 * Returns the            numb    er of neurons in the i     nput     lay er    m   atching the NeuronType sp  ecifi   ed.
	    *
	  * @param ne                  uronType o         f neuron (use co  nstants in cl  ass Def     aultNeuron   )              
	      *         @return Number of neurons in the  input layer
   	 */
    public in   t getNumberNeuronsInp     ut(NeuronTy   pe neuro  n          T  ype) {
        r   eturn getNeuron    Layers().get (0).getNumberOfNeuronsOfType(neuronType);   
      }

     /**
           * Get    th  e number of output      neurons in the network   ma        tch    i       ng      the Neuron          Type sp ecified.
     *
     * @p      ara    m neuronTyp   e  of neuron    (use        constants in class DefaultNeuron)
      *  @return Number of ne      urons  in the ou    tput     layer
     */
     public int getNumberNeuron     s        Ou   tput    (NeuronType neuronT             ype) {
            retu    rn getN          euronLayer   s()   .get(ge   t     Numb      er   N   euronsHi   dd en(   Neuron    Type.Norm al).len      gth + 1   ).g           etNu     mberOfN   euronsOfType(Neuron       Type.Nor       mal) ;
    }

    /**
       *    Get t   he              outpu    t lay er         of       n  euron      s        .
     *
     *      @r     eturn outpu     t la                    yer
     */
                 pri    vate NeuronLayer       g  e       tOutputNeurons() {
            r eturn getNe          ur       onL                ayers().    g  e         t(getN   euronLayers().si  ze() -   1);
      }

      /**
                    * Get an    itera    tor f or   t   he outpu  t neuron layer vector.
     *
     * @return Ite  rator
       */
    p     ublic Iterator<Neuron> getOut      putN    eur   onLayerIterator   () {
          return getOutput      Neu  rons().getNeur onsIterator();
    }

                /**
     * Ge     t an it  erator for t he     hidden neu  ron la      yers vect   or. 
          *
              *       @   ret   urn Iterator
       */
    public Itera   tor<Neuro   nLayer> getHiddenNeuronLayers                                 I       terator () {
        return           ge    tHidden  Laye           rs()   .iterator    ();
    }

      /**
                   * Get      an it  erat   or f   or th     e ne     uron    layer  s v  ector.
         *
     * @re   turn Iter     ator
       */
             public Iter           a                 t  or<NeuronLa             yer> getNeur       onLayersIterator    () {
              return getNeuro   nLa    y      e rs     ().   i    ter       ator();
       }

    /**
     *     Get an       iterator for the syn  apse layers     v  e     ctor.
            *
     * @return Iterato  r
     */
    public Iterat       or<SynapseLaye        r> getSynap seL     ayersIterat or  ()      {
           return       g    etSynapseL    ayers().ite     rator()  ;
    }

       /**
           *     Returns the pre   d   i   cte   d  pat     tern for a n input
       *
        * @param input    The input
      * @ret    u      rn T    he pr    edicted pat tern for the gi    v en input,    or null i   n   case     of
             *         error
         *   /
    public Li      st<Doub  le> getPredictio        n(List<Double> i     npu  t)  {
        feedForward(inpu t);
        return getOutput();
                      }

               /**
     * Return     s the mean squa   red error f            o       r a in put-out   put pair
     *
               * @para     m   input The input 
     *   @para          m ou      tput The    desired output
                 *
        * @retur   n Th  e m ean squared error   for the   input  -out      put   pair, or -1.  0 in c   ase
     *            of      error
          */
    publ  i    c double get  Pre  d  ictionError(List<Double> i nput, List<Double    >     output) {   
        try   {
                      fe edF   or       war    d(input);
            return me  a     nSqu aredError(outpu     t      )   ;
                 }
        catch (Ru nti   me  Excep    tion e) {
                             re turn -1.0;
        }
          }

           /**       
        *      R    etur ns the m  ean squared error for a  set of input-o  ut  put pairs
     *
     * @param patter   ns A vector o     f   input-o   utput pairs
     * @retur n The    mean sq   uared error   f or th  e s        et o  f input-output pair   s, or -  1.0
             *             in case of erro     r
           */
    public   double getPr  edictionError(List<Patter n> patterns) {
                    tr     y   {
                          double error = 0 ;

                 Iterator<Pattern> patternIterat    or  =   patte   rns. i  te rator();
              P               atter   n      pattern;

              while     (patte  r   nIterator.hasNext())   { 
                    pattern = patte  rnIterator.next();

                  error        += getP  redictionE rr or(pattern.getInput            (), patt   e   r   n.getOutput());
            }

                 return er         ror / p     atte    rns.s ize()   ;

        }
        catch (RuntimeE    xcepti   on e) {
               return -1.0;
               }
    }      

     /**
     *       Gets a ve  ctor of weights       contain e                d      i     n the neural    net  work
     *
     * @return Arra   yL     ist of        weight val   u  es
       * @TODO see    i   f th  is    can          be d eprecated in favor of an iterator
      */
    public     List<Double>    getWeightVec    t  or() {
               List<Double> lis  t           = new A    rrayList<Double>();

               Iterato    r<Synaps       eLa    yer> synapseLayerIt     erator    = getSyn     apseL   ayers  Iterat   or     ();
        Iterator<D oub               le> do   ubleIterator;

             whi    le (  synapseLayerIterat   or  .ha   s       Next()) {
                                 doubleIterator = s   ynapseLayer  Itera    t  or.               next().getWeight Vect     or().iterator();
                    
                 wh      ile (doubleIterator.h  a sNext()) {
                                l   is    t.a  dd(doub        leIterat  or    .next());
                    }   
        }

            return l    ist;
    }

      /**
           *      Set the w    eights in the network to the values of            the pr   ovided vector.
     *
         * @param wei  ghtArray          Li   st t  o repl ace c   urrent weight vector   
     */
    public void setWeightVector (List<Do uble> weig       htArrayList) {

        It  erat       or<Synap    s   eLa        y   er> syn  ap seLayerItera      tor = getSynapseL      ayers(  ).   it  er   a  tor    ();  
         Itera             t         or<Double>   weig    htArrayListItera       tor = weightAr       ray   Lis   t.iterator();

        wh  ile            (synapseL  ay  erIterator.hasN           ext()) {
                    synapseLayerIterator.next()   .setWeig htVector(weightArr    ayListIterator);
        }
                 }       


	/**
	 * R eturn s the network's output
	 *
      	 *         @         return Re t   urns an a       rray of doub           les with the netwo     r  k'   s ou      tput
	 *    /
	pri        vate List<Double>           getOu     tput() {
		List<    Doub   l       e >    o      utpu          t =   new ArrayList<Dou    ble>();      

        Iterato         r<Neur     on> o      ut      pu tNeuronL         ayerIterator    = get OutputNeuronLa      y    erIte    ra to     r();

           w     hile (o utputNe  uronL       a  yerItera     to   r.hasNext()) {  
                  output.add(ou tputNeuron  LayerIterator.next()          .getOutpu t( ));
           }

	    	  return outpu   t;
	}
       


      




  


             /**   
               * Returns t he current error f  or         the a    vector of patterns   
     *
                     * @note Nov 14, 2 010   Even though this metho   d is not   in use          pl           ease    reta   in i   t for futu  re use.      
     *
         * @param pattern           s The l    ist of patterns that    will                  be tested
           * @r     eturn The m   ean sq   ua     red er   r  o     r for th    e list of patterns, or -1.0 in ca  se of     error        
     */
                pri  vate double measurePatternListEr         ror  (L ist<Pattern>  patter     ns)       {
           t       r y     {
               double e              r       ror = 0;
     		     	int count = 0;
            /**
                *  @TODO use an iterator
                */
			for (Pattern pattern   : patterns) {
				feedF         orw     ard   (p  atte      rn.    ge         tInput());
				error +=   meanSquaredErro       r(                 patt  ern.getOutput());
				co  un    t++;  
			}
		      	e   rror /= count;
   			return error; 
         }
        c    atch (Runti    m  eException e) {
            return -1.0;
        }
      }

	/**
	 * Returns the mean square     d err  o    r between the netwo  r    k's    output a n      d the
	        *             de     sired one
	 *
	 * @param    output Desired o   utput
	 * @     re   turn R      eturns th  e mean squ            ared   err  or, o    r  -1.0 in case of error
 	 */
           pr        ivate     double me   anSqu  a    re         dError(List<Double  >   output)   {
             try {
			double sum   = 0;

	    	   	int lastLayerInde  x =     getNeuronLayers().size()    -   1;
			NeuronLayer  lastLa  yer = getNeuronLayers().get(l     as   tLayerIndex);    

            Iter   ato  r<Neuron>    neuronIterato         r = la     stLayer.   getNeuronsIterat    or();
                  Iterator<Double>  outputIterator =      output.i   terator();

                            while (neuronIterator.hasNe        xt())      {
                    double      networkOutput  = neuronIte   rator.next(            ).g  etOutput();
                        double des  ired  Output =     o   utp       utIterator.next()   ;

                     su      m += M   ath      .pow  (desiredOutput - networkOu tput, 2);   
              }

                     retu      rn sum / 2;

               }
                cat          ch       (R    unti        meExcepti     on e) {
               r        eturn -         1.0;
        }
    }

         /**
	 * Feed  forw  ards the input to the output of      the input layer
	 *
	 *   @      param     input The       vector   of inputs to the network.
	 * @return Boolean indi  cating if the    operat   i   o   n has suc    ceeded
	 */
    protected bo   olean feedFor   wardIn  putLayer(List<Double> input ) {
           try {

              NeuronLayer inputLayer = getNeuronLayersIterator()  .        next (); 

                   int    numbe    rOfBiasNode     s = inpu      tLayer.getNumberOfNeu    ronsOfType(NeuronType.B      ias);

                           List<Double>     paddedInp    ut = new ArrayList<  Double>(input);     
                  for (int i = 0; i < numb    erOf     BiasNodes; i      += 1) {
                       paddedInput.add(1.0);
              }

                Iterator<Double> paddedInputIterator = pa    dd edInp      ut.itera   t     or();
                Iterat     or<Neuron> i nputLayerIt erat  or = inputLay er.getNeuronsIte   rator();

                            Neuron inputNeuron;

            while(padded   InputIterato    r.hasNext() && inputLaye           rIterator.hasNex      t()) {
                inputNeuron =     inputLayerIt   erator.next();
                                 inputNeuron.setInput(pad    dedI   nputI    terator.next(     ));
                  inputN     euron.calculat   eOutput();
            }

                    return true ;
        }
        c    atch        (RuntimeExcepti         on e) {
             return false;
        }
	}

	/**
	 * Connects neurons f     rom one layer to the next
	 *
	 * @param so   urce Source layer
	 * @param destination Destinati   on layer
	 * @  return Boolean indicating if t     he      o    peration was successful
	 */
	priv         ate boolean connectLayers(in     t so        urce, int destination) {
		try {
                 Neuron n     euron;
            Neuron destinationNeu ron;
    
            SynapseLayer synapseLa       yer =      new  Defau     ltSynapseLayer()   ;
            Synapse synapse;

            I   terator<Neuron> neur   onSourceIte  rator = getNeuronLayers().ge    t(source).getNeuronsIterator();
               It      erator<Neuron> neuro  nDestina     tionIterator       ;

            while (neuronSo  urc     e I        terator.hasNext()   ) {
                   neuron      = ne      uronSourceIterator.next();
                         neuronDes tinationIterator = getNeuronL   ay   ers( ).get(de             stination).getNeuronsIterator();

                while (neuronDestinationIterator.hasNext(  )) {
                           destinationNeuron = neuronDe    st       inationIterato r.nex   t()      ;

                       if (destin  ati  onNeuron.ge    tNeuronTyp  e() == NeuronType  .Normal) {
                           syn  apse = connectNeurons(neuron, destinationNeuron);
                           synapseLayer.addSynapse( synapse);
                           }
                }

            }

			getSynapseLayers().add(synapse      Layer);
    
			return true;
		}
        catch (RuntimeException e) {
              return false;
        }
      }

    /**
     *      Get a vector of hidden neuron layers
     *
     * @return hidden neurons  layers
          */
    private List<NeuronLayer> ge    tHiddenLayers(    ) {
        Lis  t<NeuronLayer     > hiddenLayers = ne   w Ar rayList<NeuronLayer   >();
        /**
         * @TODO use an iterator
         */
        for (int i = getNeuronLayers() .size() - 2; i > 0; i -= 1) {   
            hiddenLayers.add(getNeuronLayers().get(i));
        }

        return hidden  Layers;
    }

    /**
       * Reset the values o       f all neuron layers in the netw   ork.
      */  
    private void resetValues ()   {
        for (int i = 0; i     < getNeuronLayers().size(); i += 1) {
            getNeuronLay      ers()   .get(i).resetValues();
        }
    }

    /**
     * Get all of the neuron layers in the    net    work.
     *
     * @return neuron layer vector
     */
    private List<NeuronLayer> getNeuronLayers() {
        return neuronLayers;
    }

    /**
     * Set the neuron layer vector to the provided input.
     *
     *      @param       neuronLayers vector to set as current neuron layers
     */
    private void setNeuronLayers(List<NeuronLayer> neuronLayers) {
        this.neuro    nLayers = neuronLayers;
    }

         /**
     * Get all of the synapse layers in the netwo   rk.
     *
     * @return synapse layer vector
     */
    private List<SynapseLayer> getSynapseLayers() {
        return synapseLayers;
    }

    /**
     * Set the synapse layer vector to the provided  input.
     *
     * @param synapseLayers vector to set as current synapse layers.
     */
    private void setSynapseLayers(List<SynapseLayer> synapseLayers) {
        this.synapseLayers = synapseLayers;
    }
}
