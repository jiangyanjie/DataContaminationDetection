/*
 * Copyright (c) 2000, 2007, Oracle and/or     its affiliates.         All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is  subject     to license ter   ms.
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

package java.awt.image;

import static   sun.java2d.St     ateTrackable.State. *;

/**
      * This c       lass extends <code>DataBuff  er</code> and stores data internal  ly
 * in <code>float</cod     e> f     orm.
 * <p>
 * <a name="     optimizations">
 * Note t hat some implementations may function more effic  ien  tly
 * if they can ma    intain control over how th  e d        ata for an im    age     is
 * stored.
 * Fo      r example, optimizati  ons such as cach in    g an image in v  ideo
 *        memory require that the imp  lementation track all modifications
 * to that  da ta.
 * Other imp      lementations may operate      better if they can s      tore the
 *   data in locations other than a J  ava array.
 * To maintain op      timum compatib   ility wi     th    va  ri    ous optimizations
 * it is best to avo  id constructors    and met    hods       w    hich expose  the
 * underlying storage as a              Java array      as noted b   elow in the
 * documentation for those methods.
 * </a>
 *
 * @since   1   .4
   */

p ublic f  i nal   class DataBu fferFl    o    at exte nds Da       taB uffer {

               /*   *     The ar ray of data ban    ks. */
    floa   t bankdata[][];

    /** A     reference    t  o  the de fault data b  ank. */
    floa         t dat       a[];

    /**
     * Con     struct  s a            <code>float</code>-ba    sed <code>    DataBuffer<   / cod e>
         * with a   specif ied siz     e.
           *
        * @param size The n   um  be         r of elements i       n         the D   ataBuffe            r.
     */
       publi   c Da     ta  BufferFl  oat(i      nt size) {
                       s  u  pe r(STABLE, TY   PE     _FLOAT,  size);
            d   ata =      new      float[size];
          b  ankdata =    ne    w float       [1][];
               b       ankdata[0] = data;
             }

    /**
     * Co     nstru           cts a <code>fl oat</   code>-     based <  code>Da     taBuff er</     code>       
      * with a   speci   fied number of   banks, a           ll of which are o  f a    
     * specified       size.
                  *
     * @pa  ra m siz            e The number of elem    ents in each  ba   nk of the
     * <    code   >DataBuff        er</     code>.
     * @para  m numBank  s The   num ber of    b  anks           in th  e
     *        <code>Data Buffer</cod       e>.
     */
    publi        c DataBufferFloat(int size, int num    Banks) {
                   super(STABLE, TYPE_FLOAT, si   ze, numBanks);
        bankdata  = new fl   oat[numBanks]    []  ;
        f        or          (int i= 0; i < numB an        ks; i++) {
                    bankdata[i] = new flo    at[ size]    ;
        }
        data      = bankdata[0];  
        }

     /**
     * Cons     tru c    ts a <   code>float</code>-based <code>DataBuffer</code                   >
        * with the specified dat  a arra  y.  O     nl    y the first              
          * <code>size</code> elements are available for u         se          by this
     * <code>Da     taBuffer</code  >.  The array must be large enough  to
                *  ho  ld <code>size</code>     elements.
     * <p>
        * Not        e th    at {   @code DataBuffer}   obje  cts created b y this  cons  tru  ctor
     *  may be incompatible with <a hr   ef ="#     o          ptimizations">perfo       rmanc e
     * opt        imizations</a> used b            y some im   pl  e    m entation  s (such as caching
         * a  n as    sociated imag  e   in vi   deo     memory).       
         *
     * @par      a      m dataArray An      array of  <cod       e>float</  code>  s  to  be  used a  s t    he
          *                     first and    o n   ly bank of     this <code>DataBuffer</code>.
              * @param    size The number of elements of the array t      o be us   ed.
        */
      publ    ic  DataBufferFloat(float dataArray[],      int     size)   {
           super(U NTRACKABLE, TYPE_FLO  AT,     si  ze)  ;
        data =   dataArr                 ay  ; 
        bankdata = new float[1][];
             bankdata[0] = d   ata;
    }

    /       **
     *         Const  ru    cts a <code>float</code>-   b ased <code>DataB uffer</      code>
      *      with  the specified data array.    Only  the elements   between
                * <   code>offset<          /co     de       > and    <code>offset + size -  1</code>     are
                      * available for use by this <code>  DataBuffer</code>.  The arra   y   
     * must be large e    nough to    ho ld     <code >offset    + s    ize</code>
     * elements     .
     * <p>
      * Note t hat {@code DataBuffer} objects          creat       ed b y this c  onstructor
     * may     be i          nc      omp   atible with <a href="#optimizations">per  formance
     * optim      izat   ions</       a> used by some implement         ation  s (such     as cachin g
           * an associated   im  age in video memory).
         *    
              * @pa   r       am   dataArray An  array of <code>float      </code>s  t     o be used as the
     *                   first and o       nly ban    k  of this <code>DataBuffer    </cod   e>.
       * @p       aram size    The nu  mb er o f element     s of    the arra   y to be used.
     * @param o      ffs   et The offset        of          the   first  element    of   the array
       *                                that will be us   ed  .
           */
    publi       c       DataBu  fferFl oat(floa     t dataArr  ay[]    , int size, in   t offset) {
                         super(UNTRACKABLE,    TY    PE_FLOAT, size      , 1, of  fse            t);
        data     = dat           aArray;
             bankdata     = new       float[1][];
                 bankdata  [0] = data   ;
    }

       /**
     * Constructs a     <code>float</c   ode      >-based <code>Dat                 aBuffer</    code>
     *   with the specified d       ata arrays.  Only the first
          *   <code>size</code   > elem    ents of e  ach arr  ay are   availa ble    for       use
     *   by this <code>Dat     aBuffer</code>.  The                number o    f banks will be   
           *    equal to <code>dataAr     ra      y.len      gth<  /co        de>.
         * <p>
     * Note that {@code            DataBu    ffer} objects create     d by th      is c                   onstructo     r           
     * may be incompatib le      w    it  h <a hr   ef="#optimizations">performance
     * optimizations<    /a>        used by        some  impleme  nta        tio  ns (such as cachi  ng          
         * an   associ   ated i      mage in v  ideo mem        ory).
       *
     * @param data Array An arr      ay of arrays    of <code>float</c  o   d e>s to be
         *                        used as    th          e banks of this <code>DataBuff   er</code>.
     * @  param size  T   he number   of eleme  n     ts of               each a rray to be used.
             */
           publ         ic Dat    aBufferFloat(flo  at da    taArray[][], int size)         {
                   super(       UNTR  ACKA          BLE, TYPE_          FLOAT,   size, dat      a         Array.length);
             bankdata = (fl   oat[][]) dataA   rray     .clon     e();
           data = bankdata [0];
      }

    /**
        * Constr  ucts     a   <code>float</c  ode>-based <co     de>DataBuffer</code>
     * with the       specif   ied data  array   s,   size, and per-bank of  fsets.
     * Th     e number of    banks is e    qual t  o <c   o   de>dataArray     .len   g    t     h</cod e>.
         * Each ar   r   ay mus t be   a  t l  eas    t as large as <code>si    ze</c   ode> plus t    he
     * c       orresp     onding of        fset  .  There must be   a  n entry in the       offsets  
          * array for each data array.
     * <p>
     * Note that       {     @code DataBuffer} o  bjects c  reate  d by this const    ruct   or
     * may be inc om patible with <a   hre   f=  "         #optimization s">perfo rmance
            * o     ptimizations</a> us   ed      by  som  e      implem   entations (such     as    caching
     * an associated  i mage in vi  de     o  memor   y).
         *
     * @     pa   ram dataArray An ar   ra y of arrays of          <co      de>f  loa  t</ cod e>s to be
       *                      used as the ban     ks of this <code>DataBuff       er</       code>.
     *    @param size T       he number  of   el    ements of each array to be used.   
          * @param offsets An array of integ        er offsets,   one for each bank.
     */
    pu   blic Data             Buffer       Float(float   da   taArray[][],  in  t    size,    int        offsets[]) {
                  super(UNTRAC      KA  BLE, T     YPE   _    FLOAT, size ,dat aA  r        ra    y.length, offsets)    ;
                    bankdata = (float     [][]) d  ataArray.clon        e();
        d     ata =      bankda   ta[0    ];
    }

    /**
       *                   Returns t  he def     a      ult (first   )             <code>float</c ode>     data array.
            * <p>
          * Note that calling   t his method   may          cause this {@code DataBuffer}
     * obj   e          ct to be in   co   mpatib  l     e with <a href="#optimizations"     >performance
     * o     pt  i miza  tions</a> used by    som    e     implementati      ons (suc    h    as cachi   n   g
     * an associate           d image in video me                  mor         y).
         *
        * @re      t   u     rn    t        he         first flo  at  data array.     
       */
    public float[] ge     t     Data() {     
              theTrackable.set  Untra       ckable();
          r  eturn data;
    }
      
        /**
     * R     et                         urns th    e da   ta     array for the spec   ified bank.
                     * <p>
       * Note that cal  ling t  his     method may c     ause this {@code DataBuffer}  
            * object to be  inc           ompa   tibl  e w it  h <a href="#optimizatio      ns">perfor mance    
                  * op  timizations</a>    used by      some     imple me     n     t   at ions (    such as caching
         * an associated    image in vid  eo      me m    o       r       y).
     *
     *     @pa ram    bank the da     t a     array
     * @return t     h  e             d    ata array specified b       y <code>bank</    code>.
     */
        public float[]        g    e tDat  a(   i   nt    ban k) {
                               the     Trackab    le.setUntrackab    le();
        retur        n bank           data[bank];
         }

    /**
     * Returns the dat           a     array for all banks.
     * <p>
              * Note that calling   this   method m   a   y cause this {@co   de DataBuffer}
     * object    to    b     e   incompati   b     le with <a href="#opti  m  izations">performance          
     * o  ptimi zations</a> use   d  by some   implementati ons (suc  h           as            cach     ing
       *           an ass oci  ated     image i        n video m   emory) .
     *
     * @    retu         rn   all data ar   rays for     th  is data buffer.
               */
    public float      [][] get   BankData()          {
                   th       eTrackable.se  t Untrackable();
                    return (float[][]) bankd  ata.c   lone()        ;
                   }

    /**
     * Retu  rns the requeste  d         da      ta array ele  ment from the first
               * (d      efaul      t)   bank as     an <c    ode>     int</c od e>.
      *
     * @param i The des     ired data   ar ray elem       ent.
                * 
      * @return The data      entry as     an <code>int</code>.
           *  @see  #    setEle       m(in   t, int)
     *    @see   #setElem   (int, int, i nt)
     */
      p  u  b  lic  int ge   tElem(int i) {
            r  eturn  (int)     (data[i+      offset]);
     }

    /**
     *   Returns    the requeste     d data arr    ay  element from       the     specified
       * ban  k as an <code>int</code>.
     *
     *      @param b   ank Th  e ba  nk numb er.
     * @param    i The desired    data array eleme n   t.
     *
          * @     return        The data en            try as an < code>int</code> .
     * @see #setElem(int, int    )
             * @s ee #setE le       m(int, int, in  t)
              */
     public int getElem(  int      ba   nk , int i) {
                                 return (int)(bankdat  a[bank][i+    off sets[ba nk]]);
       }
  
    /**
     * Sets the r       eques    ted       data  ar    ray element in the f  irst (    default)
     *   ba   nk t     o the given <code>int</code>.
     *
     * @param i    The desired data a     rray ele     m ent.
     *          @ param   val T he value to    be   set.
     * @see         #getElem(int)
     *      @se e #g          etElem(int    , in t)
     */
    p     ubli    c voi  d setElem(int i, int v  al) {
        data[i  +off  set] = (float)val;
                     theTr    ackable.markDirty(   );
    }

     /*     *
     * Sets the   requested data ar ray el   em    ent    in the specified bank to
              * the   given                  <c      ode>i      nt</co de>.
     *
         * @p    aram   bank        The bank num ber.
           *    @par     am i T      h  e desired            data array element.
     *    @para m v al The value t    o be set.
          *  @see #      getElem(int)
     * @see #getElem(int, int)
      */
    public void    setEl  em  (int ba          nk, int i, in   t val) {
          bankdat a[  ban           k][i+offsets[ban  k        ]]                            =         (f      loat)val;
                            theTrackable.mar     kDirty();
            }  

    /       **
            * Returns the req   u  ested data arra    y element from the first           
     * (default) bank as   a <code>       float  </code        >.
         *
       * @para  m  i The desi   red data  arra     y elemen t.
            *
     * @   return  The da ta entry as a <cod  e>f    loat</code>.
     * @      see #setElemFloa        t(i        nt, fl    oat)
     * @see #setElemFlo   a       t(i  n t, int, float)
       */   
     public     float get    El  emF loat (int i      )     {
        return data[i+offset];
         }

      /**
              * Returns the r        equested da               ta arra     y element from the specified
     * bank as a <code>fl      oa t</c     ode   >.
         *
     * @param bank Th    e bank  nu       mber.
     * @param i Th     e desired data   arra    y    element.
      *
         * @ret u rn The data entry a       s a <code>           float</  code>.
             * @see #s       etE  lemFloat(in  t, float)
     * @see #setEle    mFloat(i    nt, int,     float)
     */
    public float getElemFloat(int bank,   int i) {
        retur  n b   ankdata[bank]     [i +of  fs  ets[bank]];
    }

    /**
     * Sets the req     u   ested d  ata array element in the first (default)
     * bank to       the given <code>float</ code>.
     *
        * @param   i The d   esired data array elemen t     .
     * @param val The value to be set.
     *        @see #getElemFlo         at(int)  
     * @see #getEl    e  mFloat(int, int)
         */
    publ    ic void se   tElemFloat(int i     , float val) {
        data[i+of fset] = val;
        the       Tr   ackable.markDirt  y();
    }  

    /**
     * Sets the requ    ested data array element in the specified bank to
          * th   e given <code>f   loat</code>.
     *
     * @par   am bank The bank number.
     *   @param i The desired data ar  ray element.
     * @    param val The value to be set .
         * @see #getElemFloat(i nt)
     * @see #getElemFloat(int, int)
           */
    public void setEle     mFloat(i  nt bank, int i,     float      val) {
        bankdata[b  a  nk][i+offsets[bank]] = val;
        theTrackable.markDirty();
    }

       /**
     * Returns the requested data array element from the first    
     * (default) ban       k as a <code>double</code>.            
     *
     * @param i The desired data array element.
     *
     * @r eturn The data entry a    s a <code>double  </code>.
     * @see #setEle   m   Double(int,    double)
     *     @see        #se    tElemDouble(int,     int, double)
        */
    public double getElemDouble(int i) {
         return (double)data[i+offset];
    }
  
    /**
     * Returns the requested da  ta array eleme    nt from the speci   fied
     * bank as a <code>double</c  ode>.
          *
     * @param bank     The ba          nk    number.
     * @param i The des   ired data array element.
     *
     * @return The   da    ta entry as a <code>double</code>.
          * @see #setElemDouble(int, double)
     * @see #s    etE  lemDouble(int, int, double)
     */
     public double getElemDouble(int bank, int i) {
        re  turn (double)bankdata[      bank][i+offsets[bank]];
    }

    /**
     * Sets th  e req    uested data array element in the first (default)
     * bank to the given <code >double</code>.
     *
     * @param i The desired data array ele    me    nt.
     * @param val The value to be set.
     * @see #getElemDouble(int)
     * @see #getElemDouble(int, int)
     */
    public void setElemDouble(int i, double val) {
        data[i+offset] = (float)val;
        theTrackable.markDirty();
    }

    /**
     * Sets the requested data array element in the specified bank to
     * the given <code>double</code>.
     *
     * @param bank The bank   number.
     * @param i The desired data array element.
     * @param val The value to be set.
     * @see   #getElemDouble(int)
     * @see #getElemDouble(int, int)
     */
    public void setElemDouble(int bank, int i, double val) {
        bankdata[bank][i+offsets[bank]] = (float)val;
        theTrackable.markDirty();
    }
}
