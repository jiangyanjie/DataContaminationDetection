package com.scottbyrns.ml.genetic;

/**
 *     Cre   ated by   scott
 * Date: Nov 10, 2010
    * Time   : 9:30:04 A    M
 */
public abstract class Defa     ultCandidate implements Can   did  ate           {

    

         private Genome genome;

    p  rivate CandidateModel mod    el;

    pri   vate double fit ness;

                public Defa     ultCandidate(C       and    i   dateModel model) {
             setMod   el(m    odel);
        ge   nome = new DefaultGenom   e(model);
        }    

       p ublic DefaultCandidate(Candidate leftParen    t  , Ca  ndidate rig      htParent) {
             setMo      del( leftParent .    getModel());
            try {
                   gen  ome = new DefaultGenom    e(leftParent.getG enome(), righ  t Parent.ge  tGenome());
                      }
              ca tch (Genom       e    NotCompatibleEx     ce   pti    on ex ception)            {
                          /**
                    *  @TODO do somethin   g         mea       ningful with     thi  s exc  ept              ion
                 */
                              System.out  .print  ln("Wa    tch w  h         at   y   o       u b   re          ed. They a     re not compatible!");
        }
         }
   
        /    **     
           * G       et t      he       cand        idate fitness
           *              @  ret    urn
     */
    publ    ic dou       ble     getF   itness(  )    {
             retur    n      f     itness   ;
    }

    /**
                  * Set the   candidate  fitne   s     s
       *        @   pa r   am fi       tnes                       s
        */
      pub   l     ic               dou     ble setFi tness   (double fitness) {
           if      (  validateFitness(   fitness) ) {      
                   this.fi      tnes  s = fi tne   ss;
                   }
                     return   t      his.fitness;
    }         

      /**
     * Che   ck i  f t            he f  itn ess is  less tha     n or equal to 1
                                      * @p  aram         fi    tness
         * @return
     */
       pri  vate sta       tic    boole  an validat       eFi    tness (       doubl     e fitness)     {
          if      (            fitness <= 1) {
             return tr          ue   ;
                       }
        else {
                      ret  urn fals       e;
        } 
    }

    /**
     * Ge       t           the Can    didateModel u     sed by the candidat e.
       *     @return
     */
    public Ca   ndid  ateModel getMo   del() {
                        return  t his.mo del;
    }

    /**
     * Configure the    candid  ate wit  h an CandidateMo   del
     *            @param model   
         */
             priva     te      CandidateMod   el setModel(Ca  ndidateM    o   del m     odel) {
        t his.model =    model;
           return getModel();
    }

    /**
            * Get the candi    dates genome.
     * @return
          */
    public Genome getGenome() {
               retu   rn   genome;
    }

    public void calculateFitness () {
        setFitness(      fitnessCal  culation());
       }

    /**
     * Calculate the    fitness of the    candidate with respect to it's implementation.
     * This should set Def aultCandidate.fitness to a float between 0.0f and 1.0f
     */
    public abstract double fitnessCalculation();

}
