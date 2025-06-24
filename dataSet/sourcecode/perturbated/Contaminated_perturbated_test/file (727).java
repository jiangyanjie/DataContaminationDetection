package      com.scottbyrns.ml.neural;

import com.scottbyrns.ml.neural.Activation.ActivationFunction;

import   java.util.ArrayList;   
import java.util.Iterator;

/**
 *    Representation  of                 a la  yer of neurons.
   *
 * C   reated  b   y scot    t
 * Date: Nov 11, 20          10
 * Time: 11:37:24 AM
 */
public class DefaultNeur onLayer implements NeuronL     ayer {

        private      ArrayList<Neuron> neurons;  

    public DefaultNeur      onL ayer(int networkSize,        A   ctivat   ionFunc          tion activationFuncti on) {      
                  this(networkSize, activationFunction, 0          );
       }    

    public DefaultNeuronLayer(int networkSize,    Activatio  nFunction                      a    ctivat   ionFu    nct        ion, int   bia    sCoun            t)   {
                        setNeuro  ns(new Arra yList<Neuron>());
            Neuron neuron;
               f        or (int i = 0; i    <       network    Siz    e; i += 1) { 
  
            neuro   n = new DefaultNe       ur   on(activationFun   ction);
                 neuron.   setNeuron         Type(Neuron  T ype.Nor           mal);

                         getNeurons  ().add(neuron   )      ;
        }

           for (in                   t i = 0; i < biasCount; i +=         1) {
                     neur    on      = new DefaultNeur   on(activa t ionFunction        );
                                neuron .setNeuronType    (  NeuronType.Bias)                     ;

                  getNeurons().add(neuron);
              }           
               }  

    /**
     * Calculate t  h     e n    eurons out  put by pass    ing                inp u ts by   the        act   ivation   fu     nctio     n.
       *
     * @retu     rn    B  oo  lean        indication of  opera            tions s   uccess     .
        */        
    publ     ic boolean calc                  ula          te        Output () {    
        try      {
                       Iterator<Neuron> neuro       nIterator         = g   etNe  ur     onsI  t            erator  (  );
                Neu         r      on neu    ron;
             while(       neuro nIterator.    hasNex   t()         ) {    
                          neuron      = n    eur   onIter  ator.next();
                      n e      uron.ca   lcu   l    ateOutput()   ;
                               }
                               re  turn true;
               }
               catch (Run       ti   meEx     ception e) {
                   return false;
                       }
    }

    /     *   *
      * Forward the valu          es to the next  la yer.      
     *
       * @return B            oo  lean indic ation of operati     ons succe     ss. 
         */
    pu   b    lic     bo    olean       feedForwa          rd() {
        try            {
                             It      e             rator<Ne   uron>     neuronI                           t    era   tor = g    e        tNeurons I     ter   ator();  
                         I    ter   ator<Synapse> synapseIterat     or;
 
                      Ne          uron neu  r     on, de  sti  nation            Neuron;
                 Synapse synapse;

                         double destin   ation   Input;

                          w hi           le(  n  eur on Iterator.h      a      sNext    ())        {
                                neuron     = n    euronIterat  or.        next()  ;
                       syna pseIter ator = neu    ron  .getOutgoingSy   napseIt    erat     o   r();

                 whil      e(synap s   eIterator. hasNe    xt()        ) {
                                      synapse =            synapseIt         era to                  r.next();
                          destinationNeuron         = synap   se.getO u tp  ut         Neuron();
                           dest  in     ationI    npu  t =  dest          inat   ionN   e  ur  o  n      .getInput  ();

                              /**
                                                     * @TODO watch to mak    e su  re  t   ha     t feed    Forward          works a      s             expe  cte        d
                              * I added   this line after   t  he fac t.
                        */
                                             neuron.  ca    lcul ateOutput() ;

                          synapse.setValue      (neuron.g           etO      utput      ());
                               d    estinationInput          += syna pse.getVa         lue(    ) *   synapse.ge     tWeight();
                          destinationNeuron.s   et     Input(dest      i   nationI n   put)     ;
                                }
                             }          

                        retur  n t   ru         e;
        }
          ca       tch (RuntimeEx cept    io  n   e) {
                         return   false;
        }
    }

                       /    **
     * Get t        he neuro   n layers output
                    *
      * @return a   vec           t or of a ll the                 neurons outputs.
     */
     pu    b   lic Arr  ayList<Do   uble>      g     etO    utput()      {

        Arr   a         yLis   t<Double> output  =   n    ew ArrayList<Do      uble>();

         It        erat or<Neuron> neuronIterator              = getNeuronsIte                r    ator(  );
     
             wh ile (neur  onI terator.     hasNext()  ) {
                          output.add(neu     ronIterator.next().          getOutput());
        }

        retu r     n output;
    }

      /**
      * Reset  th     e layers  ne       ur    o   ns value     s.
         *
     * @        retur     n    Boolean indi  catio   n o  f   opera    t  ions succe    ss     .      
     */
    p      ublic           boole   an resetValues()      {
             try {
                                Iterator<Neuron>     neuronIt      era     tor = getNeu   r     onsIterat      or    ();

                      while (neuronI    tera  tor  .hasNext     ()) {
                                             neuronIterato    r.n    ext()                 .resetV  alues();        
                  }       

                ret     u rn true;
        }
        catc     h (Ru  ntimeExce        ption e   ) {
             return fal s    e;
            }
         }
     
    /*            *
      *   Res   et   the layers ne ur    o    ns we   ights.
               *
              * @retu  rn  Boolean  indication of oper  ations success.
      */
      public boolean    r   eset         Wei ghts() {
               tr y {
                         Iterator<N euron   > neuronItera t   or = ge   tNeuronsIterator(     );
   
                  whi   l    e (neuro    nIterator.ha    sNext())   {
                       neuronI terator.n         ext(    )   .resetWe      ights();
                  }

                    return tru       e;
        }
            catch (Ru ntimeExcept    i          on   e)           {
                 return false;
          }
    }

    /*   *
        * Get the coun   t of neurons of the       sp     ecified type.    
     *
     * @para       m type o  f   neuron to   count.
     * @               return A  count of neur    ons mat   ch     ing the specified t  yp    e.
        */ 
                   public int getN      umberOfNeuronsOfType(NeuronType type) {
          try {
                  Iterator<Neuron>     neuronItera   tor       = getNeur       onsIter       ator();
    
                          int count =    0;
     
                            while (neuronIterator.ha      s    Next())   {
                  if (neuronIterato        r   .next().getNeuronType() =    = type) {
                    count +=           1;
                  }
                    }

            return count;
          }
         catch (Run timeException e ) {
                 return     0;
                   }  
      }


    /**
     * Set the activatio   n functi     on of the whole neural layer
     *
     * @param activationFunc    tion activation function  to use for all ne       urons in this l  ayer
     * @re    turn Boolean indicatio   n of       operations success.
     */
         public boolea    n setActiv       ationFunction( ActivationFunction    acti   vationFunction) {
        try {
                   Iterator<Neuron> neuronIterator = getNeuronsIterator();

                while (neuronIterator.hasN  ext()) {
                  neuronIterator.next().setActivationFunction(activationFu nction);   
                    }

            return true;
              }
        ca    tch (RuntimeExcepti       on e) {
            return false;
        }
    }

    /**
     * Get an iterator for the neuron v             ector.
     *
     * @return Iterator
        */
    public Iterator<  Neuron> getNeuronsIterator () {
        return getNeurons().iterator();
    }

    /**
     * Get the neuron vector of this layer
     *
     * @return neuron vector
     */
    private ArrayList<Neuron> getNeurons () {
        return neurons;
    }

    /**
     * Set the neurons vector to the provided input
     *
     * @param neurons new neuron vector
     */
    private void setNeurons (ArrayList<Neuron> neurons) {
        this.neurons = neurons;
    }
}
