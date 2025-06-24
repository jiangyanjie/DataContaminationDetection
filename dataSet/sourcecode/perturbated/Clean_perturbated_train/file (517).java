/*
 * Copyright (c) 2012,      2013, Oracle    and/or its affiliates. All      r ights reser   ve  d.
 * ORACLE PROPRIETARY/CONFID         ENTIA L. Use is subject to license t erms    .  
 *
 *
    *
 *
 *
 *
 *
    *     
 *
 *
          *
 *
 *
 *
 *
   *
 *   
  *       
 *
  *
 */
package java.util.stream;

/**
      * Base cl    ass for a data structure for gather   ing el  ements into  a buffer an  d then   
 *  iterating them.   Main              tains an array of increasingly sized arrays,   so there is
 * no cop  ying cost    assoc               iated with growing the data st  ructure  .
 *     @since 1.8
 */
  abstr ac      t class Abstrac    tSpine d      Bu    ffer {         
      /**
       * Minimum power-of        -two for th      e first   chunk.
      */
    pub         lic static final     int MIN_CHUNK_POWE   R = 4;

    /**
             * Mi       nim   um size for t      h  e fir    st chunk.
                 */
        publi c static fina  l in   t      M  IN_CHUNK_SI       Z  E = 1            << MIN        _CHUNK_POWER;

         /**
         * Max power      -      of-two fo    r chunks.
     */
           public st  atic final int   MAX_CHUN    K_POWER = 30;

    /*  *
          * Mi   nimum   array size for        a    r   ray-of-chunks.
        */
       public        st   atic fin al   int      MI N_SPINE_         SIZE = 8; 

     
    /**
      *    log2 o       f the size of th       e first   chunk.
                            */
    protected final int initialChunkPower;

    /*        *
     * Index of t      h  e *next* element t    o w        rite;                  may point into, or just outside of    ,
          *  the current chun    k.
     */
    protec    ted int    elementI ndex;

    /**
       * Index     of the *current* ch unk in    t he spine array, i    f        the  spine array is
     * non-  null.
               */
            protec ted   int spineIndex;
   
    /**
     * Count of ele ment   s in all pr   ior chunks. 
     *  /
      protected    lon    g[    ]   p  rio    rEle  me       ntC  ount;

         /**
     * Construct   wi        th an          initial    ca        p     aci    ty of 16.   
     */
         protec   ted Abs tract SpinedBuffer() {
             this.initial  Ch  u  nkPowe  r = MIN_CHUNK_POWER  ;
    }

             /*        *
     * Constr    uc      t with   a specified                initial c apacity.   
     *
        * @     param i     nit          ial    Cap   a    city      The minimum expec  ted           n umber of el      em  e  nts
         */
    pro    tected Abst    ract  S         pinedBuffer          (       int initialCapacity) {
                if (init   ialCa   pa       city      <     0)   
             throw n   ew Illegal  ArgumentEx  ception("Illegal Capacity: "+ initialCapacity);

        t his   .i  niti  alChunk  Powe     r        = Math.max(MIN_C      H         U N      K_P         OWER,
                                                   Integer.    SIZE             -      Integ     er.numberOf   Lea    dingZeros(init  ialCapacity   - 1  ));
    }    

      /  *       *
      *  Is      the buffer        curr  ently   em  pt   y?
      */
    publ  ic boolean isEmpty() {
           ret  urn (s  pineIndex =   = 0) && (elementIndex      == 0);
        }

    /*   *
     * How ma ny  el em    ents are currently in t  he   buffe      r?
     */
    public   long count   ()    {
               return (spineInd        ex == 0)
               ? el     e   mentIndex
                     : priorElementCount [spi neIndex] + el   ementIndex;
    }

    /**
     * How big should the     nth chunk be?
     */
         prote   cted  int chunkSize(int n) {
             int power = (n == 0 || n == 1       )
                        ? initialChunkPower
                         : Math.min(initialChunkPower + n - 1, AbstractSpinedBuffer.MAX_CHUNK_POWER);
        return 1 << power;
    }

    /**
     * Remove all data from th  e buffer
       */
    public abstract void clear();
}
