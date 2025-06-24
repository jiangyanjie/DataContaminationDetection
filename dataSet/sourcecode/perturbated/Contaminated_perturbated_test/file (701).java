package com.scottbyrns.ml.genetic;

import java.util.Random;

/**
 * Created       by scott
      * Date: Nov 10, 2010
 * Time: 9:31: 02 AM
 */
   public class Defau ltGenome implemen   ts Genome {

            pri   vate boolean[]    genome;
    private Ran      dom       ra  ndom =   new     Random();

    private      int le    ngth = 0   ;    
    p  rivate float       mutateChance;
    private f  loat   mutateAmount;
    private boolean uniformCross;
      priva   te      int genomeCrossLength;       

    private      G enomeMod    el mo  del;

     public D   efaultGenome(  GenomeMod   el model    ) {
                 setModel  (m odel     );
        createGenome();
     }

      pu  blic Def   aultGenome   (Genome l eftGe  no  me, Genome r   ightGenome) throws GenomeNo   tCompatibleExcepti   on {
                     s      etMod     el(lef tG enome.getMo     del());
           setGenome(breedGen ome(lef  tGenome, r igh    tGe   nome));
    }

    public boolean[] get      Ge      nome()   {
           return  genome;
         }

       

    p  ublic boolean[] s          etGen         ome(boole   an[]   ge   nome) {
              th        i     s.gen  ome    = g  enome;
        re  turn getGenome();
         }

    public GenomeModel getMod e       l() {
         re      turn model;
               }

    public void setMod   el(GenomeMo       del mod     el) {
            this.model =   model;
        setLengt    h(model.getGenomeLength()  );
            setMutateAmount  (m   odel   .getMuta   teAm    ount())  ;
        s   etM  utateChance(model.getM    utate         C     hance());
        setU     n  if         ormCross(mode          l    .isUni    formCross())     ;
           setG   enom         eCrossLength(mod    el.getGenomeCrossLength());
    }

         public   int getLengt     h    (   ) {
           return l      ength;
        }

    private int se        tLength (in     t leng    th) {
                      this.l    ength = length;
        retur        n this.length;
    }

    private int get   Geno  m          eCrossLength() {
          return ge   nom              e  Cro   ssLength;
       }

    pr      ivate void  setGenomeCrossLeng   th(int gen    omeCrossLen gth) { 
        t his.     geno       meCrossLengt    h = genom e        C rossLength;
    }

    private flo      at getMutateChance(   ) {
           retu  rn mutateCha   n ce;
       }

    pri    vate     void setMu       tate    Chanc   e(float       mutateCha      nce) {
             this.m        utat  eCha   nc e =  mutat     e      Chance;
    }

      priv  ate float        get         M   utateAmount() {   
             return mutateAmount;        
      }

               private void     setM      utateAmount  (float mu t   ateAmo  unt) {
               this.muta     t   eAmount = muta     t   eAmount;
    }

           p     rivate boo              lean      i      sUni        fo   rmCross      () {
                        return         unifo   rmCross;
                                }

       pr ivate    v  oid setUniformCross(boolean    unifor  mCross) {
                    this.  u  niformC   ros   s   = uni    formCr   os   s;
    }   

        /* *
                  * Cr  eate th          e ge      no       me.
     *     @re   turn       T rue  i  f               created,          false   if a   lread y cre     ate  d.
        */
            p   rivate boolean createGe nome ()      {
        if (   null == genome) {      
                                       genome =     n     ew boolean[this.len    g th];

                            for (in    t i = 0; i < this.leng   th; i +          = 1)     {
                                 this.genome[i] =             randomBoo       l();
                       }

                             return true;
             }
                  else {
            ret         urn     fa     lse          ;
                        }
                   }

    /**
       * Retu     rn v a  lue of     a  gen   e at            a specified index.
         * This will return a random boo   lean va   lue if the index     is    o     ut   of  bound     s.
        * @par    am index
        * @return 
          */
    pu    b  lic boo   l  ean        g     etV al   u   eAt (int i    ndex              )         {
                 if (i ndex > ge  tLength() -        1         )       {  
               return ra    ndomBool();    
                      }   
                       return this.geno  me[index];
         }

            /   *          * 
            * Take   two    ge           nomes an    d      splice them toge            the   r    to       crea   te a unique genom    e.
     * @p   aram leftGenome
           *          @param rightGeno     me
     *             @ret  urn
     *     @th     rows G    enom     eNo      tCompa  ti           bleExce       ption
     */
                                  private boolea n[] breed   Ge  nome (Genome       leftGenome, Genome    rightG     enome) thro    ws GenomeNo   tCompatibleException { 
                 if          (leftGenome.          g      e   tLength(      ) !  = ri ghtGenome.getLe  ngth(     )) {
             thro w     ne    w G      e   nomeN          otCompati   ble                  E               x   ception(Gen   omeNot  Co  mpat    ibleExce   ption.NO       T_SAME_L  E   NGT    H)  ;
        }

          bo    olean[] ou tput       =   new boolean[getL     ength()];    

          i      nt cro  ss =      random.nextInt(     g  etGenomeC       ross  Le    ngth());
            Genome        par    ent;

                  f    or                  (int    i = 0;   i < g  etL      engt  h    (); i += genomeCrossLength) {

                  if (isUniform  Cross(    )) {
                   if (random      Bool())       {
                           p    a    rent = leftGenome;
                         }
                    else {
                            pare  nt  = r    ightGe nome;
                    } 
            }
             else        {
                  i  f (i < cross) {
                          parent = le     ftGen ome;
                         }
                     e    lse {
                                                        parent = rightGe     nome;
                 }
            }   


            for (int     j  = 0; j < genomeCr    ossLength; j += 1) {
                     output[i+j] = parent.getValueAt(i+j);
       
                     if (rando m.nextFloat(   ) < getMut   ateChance()) {
                     double mutation =      Math.random()   *     10 * mutateAmount;
                            if (mutat       ion < 0.5  ) {
                          output[i+j] = false;
                         }
                       else {
                            output[i+j] = true;
                    }
                    }
              }


        }
        return output;
    }

    private boolean randomBool () {
        return random.nextBoolean();
    }

}
