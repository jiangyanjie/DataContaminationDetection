/*
   * Copyright (c) 199    7, 2008, Oracle      and/or        its affiliates.    All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTI       AL. Use is subject to lic     e           nse ter   ms.     
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

/* ***********************************************       *****************
 **********************************   ********************************
 *************     *********************   ********************************
  *** COPYR   IGHT (c) Eas      tman Koda    k Company    , 1997
 *** A         s  an unpublished  work pursuant to Tit   le 17 of   the United
 *** States Code.  All r    ights reserved.
 ******************************************************************
 ****************       **************************************************
 **************************************   ********************    ********/

package j  ava.awt.image;

import sta      tic sun.java2d.   StateTrackable.State.*;

/*    *
 * T   his cla  ss extends <CODE>Dat    aBuffer</CODE> and    stores data internally as shorts   .
           * <p    >
 * <a name="optimizations">
 * Note that    some implementa        tion      s may function more efficiently
 * if they can maintain control over how   the d ata              for an image is
 * sto     red.         
 * For example, optimizat  ions  such as caching an image in v    i  deo
 * memory require th   at the implemen  ta tion track a  ll mo     dificat   ions
 * to that data.
 * Other implementation  s may operate b etter if they ca n sto        re    the
 * data in locati     ons    other     than a     Java arra  y.
 * T     o ma  intain optimum compatibility with various opti   mizations
 * it is best to avoid const     ruct     ors and methods which expose the
 * un       derlyi    ng storage as        a    Java array a s n                   oted below  in the
    * documentation for tho    se m      ethods.
 * </a>
 */
public final class DataB   ufferShor          t        exte   nds   Da taBuffer
{ 
            /  ** The d      e  fault data bank. */      
             s    hort data[];

    /** A ll data banks   */
    sh    or   t bankdata[][      ];

    /**
       * Co  nstructs a sh      ort-base         d <CODE>DataBuffer        </    CODE> with a     sin      gle bank a nd the   
     * s pecified size.
     *
     *     @par    am size The       siz         e of th  e <CODE      >DataBuff   er</CO        DE>    .
               */
                 publ  ic DataBufferSho           rt(int size) {
                   su  per(S   TABLE, TYPE_SHO    RT  ,si   ze);
            data =  new sh    ort[size];
          bankd          ata =    new short[1][];
                bankdata[0    ] =                d  at             a;
            }

      /**
     * Co    nstructs a shor      t  -based    <CODE>Data     Buffer</CODE    > with the   specified number of
       * bank       s    all   o         f w h   ich ar      e           the    specified size. 
       * 
     * @         param siz  e The size of the banks in the <COD   E>    DataB         uffer</CODE>.
     *    @param num   Banks           The numbe      r of      bank    s in     the a<COD     E>DataBuffer</CODE>.
           */  
    public DataBuffe rSh          ort(int size, int    numBanks) {
          su  per(STABLE       , TYPE_SHORT       ,size,numBanks);
          bankdata = new short    [numBanks][];
        for         (        i  nt    i=      0; i         < n  umB   anks; i++) {
                  bankd ata[i       ] = new short[size];
                     }
        data =   bankdata[0];
    }

                          /  **
     * Constructs a    sho  rt-based <CODE>DataBuffer</ CODE      > with a single bank using the
                         * sp ec   if      ied array   .
     * O  nly the first <COD  E       >s      ize</CODE> elements should be used by accessors of
          * this <CODE>Data Buffer</COD    E>.  <CODE>dataA rray    </    CODE> must be l   arge     enough    to         
     * hold <  CODE>    size<   /CODE> el    ements.
     * <p>
     * No t  e that {@cod  e    DataBuffer} objects created           b  y this constructor
     * may be incompat                 i   ble wi   th <a            href="#optimizations">pe   rf    ormance
      * optimizati ons</       a>       u       sed b        y    some implementatio   ns (such as    caching
     *     a n assoc           iated image in video memory).
     *
     * @   param dataArray T   he         short array for the <CODE>DataBuffer< /CODE  >.
               * @para           m siz     e T   he size of   th  e   <CODE>DataBuf  fe      r</COD  E>   bank.
     */
    pub     lic DataBufferShort(short dataArray[      ]     , int s  ize) {
        supe r(UNT    RACKABLE, T  YPE_SHORT,       size);
                 data = dataArray;
          bank    data =      new short[ 1][];
        bankdata[0] = data;
    }

       /**
          *  Constructs a sh    ort-based <CODE>  Data   Buffer</CO      DE> w   i th a sin          gle bank using the
     * sp    ecified arr  ay,    si   ze,     and offset.  <C  ODE    >dataArray</      CODE> m    ust have at least
           * <CODE>offset <   /CODE> + <CODE >size</CODE> elements.  Only ele     ments <  CODE>      off       set</   CODE>
       * through <COD  E  >of fset</CODE> + <CO   DE>size</CODE> - 1
       * shou   ld be     used by a ccessors of th   is <  CODE>D  ataB  uffer</CODE>.
      * <p>
        * Not                e th        at {@code     DataBuffer} o                    bj  ects crea          ted by this c   onstr      u  ctor
                 * may       be incom     patible with            <a href= "#optim        izations">perfo  rmance
     * op  timizations</a> used by some implementation      s (such a     s caching
     *          an    associated image in video memory).
     *
            * @p  aram d   ataArray The short       array for the <COD                E>D      a    taBuffe   r</CODE>.
     * @     pa    ram  siz e    The size of the <CODE>DataBu    ffer   </CODE> ban  k.
     *    @param offset T     he offse   t into the <CODE         >dataArray<   /    C   OD          E>.
     */
    public DataBufferS   hort(short      dataArray[], int si    ze, int offset) {
            super(U   N     TRACKABLE, TYPE   _SHO    RT, size, 1, offs      et);
        data   = dataArray;
           ban  kd   ata = new      s     ho  rt[1][];
        bankdata   [  0] = data ;
    }

     /**
     * Co nstr   ucts a short-based <CODE>DataBuffer</CODE> with the specified arrays.
     * The    number of      banks will be eq      ual       to <CODE>dataArray.length</CODE>.
                * Only   the   first <CODE>    size</C    ODE      > elem         e  nts  of each array should     be used         by
     * ac      cessors of    this <CODE>         D    ataBuff    er</CODE>.
        * <p>   
     * Note    that {    @code Da taBuffer} o    b     jects created    by this     constructo    r
                * may    be i            nc        ompati  ble with <a h  r   ef="#optimizat  ions"    >performance
     *  opti  mizatio  n     s</a> used b y some im               plementa tions (such as cac   hing
           * an associated imag  e in video memory).
       *
     *   @param d at   a                Arra  y The short arrays for the <C     ODE>    Data  Buffer</CODE>.   
     * @  param size   The size of t  he banks     in the <      CODE>D     ataBuffer</CODE>.
       * /
    p  ublic DataBufferShort (short dataArray[] [], int size) {
        super( UNTRACKA  BLE, TYPE_S   HORT, size, data     Arr   a             y.lengt     h);
            bankdata = (short[][]) d a  taArray.clone();
            data = bankdat a[0];
    }

    /** 
     * C   onstructs a s   hort-bas       ed <CODE>DataBuffer</CODE> with the specified arrays, size,
     *  and  offsets.
           * The num      ber of banks is    equal to       <CODE>dataArray.length    </CODE>.  Each array must
     *   be      at least as large as    <CODE>si   ze</CODE> +  the corresponding offset   .   There    mus  t
            *    be an entry in th   e offset array for each <C            ODE>dataArray</CODE> entry.  For each
     * bank, on       ly elements      <C    ODE>off  set</CODE> through        
     *  <CODE>off  set</CODE>    + <CODE>  size</CODE> - 1 sh  ou   ld b    e
     * used b   y acces    sors of this <C ODE>DataBu       ffer</CODE>.
     * <p>
                             * Note that {@co  de DataBuf     fe    r} o  bjects cre  at   ed by th      i s            con     structor
             * may be   incompatible with <a           href="    #optimization  s"  >perform       ance
     * optimizatio     n     s</a>    u sed by so  me imp   lementations (such as ca       ching
     * a   n   associ  a     ted imag      e        in vid      eo   memory).
      *
     * @param dataArray The short arr        ays for the <  CODE>DataB       uffer</  CODE> .
                           * @param                 size The size             o   f the banks in th   e <        CODE>DataBu    ffer</CODE>.
     *    @pa ram offsets The     offsets   into each     arra    y.
     */
    public DataBufferShort(   s   h   o rt    d  ata     Ar  ray[][]  , int s     ize,      int offsets[]) {
                                super(UNTRACK  AB  LE, TYPE_S    HORT, siz    e, data Array.    len         gth, offsets);
         bank    data =      (short            [][     ]) dataAr    ray.clo ne();
               data =    ban  kd   at  a[0];
    }

        /**
     * Returns    the default          (first) byte data ar ray.
         * <p>
     * Note      that calling this method ma     y    cause this {@code   DataBuf  f    er}      
     * o   b        jec           t t  o b       e   inco  mpati    bl     e with        <a href="   #opt    imizations">p   erformance
     * optimizatio          ns</a    > used by som   e impl  eme   ntati  on  s (such as cach  ing
      * a   n associated image in video   memo            ry).
               *
      * @     ret    urn The first short data array.
         */
       public short[] getData()           {
                  th    eTrackable.se   tUntrackable();
          return data;
    }

    /**
        * R  eturns     the data array     for the sp ecified bank.
              * <p>
      *               N ote that calling           t  his   meth od   may cause this {  @code DataBu  ff      er}
           * o   bject to be incompatible with <a hr     ef="#optimi   zati ons">p   e rformance
     * optimiza      tion         s</a> used b y some impl       ement     atio   n     s (such       a s caching
                    * an as sociated    image            in video memo         ry)     .
     *
     * @param bank The bank                  whose data array y ou want to get.
     * @return The d  ata array for     the specified bank.
     */
       public      short[] g    etD ata(in  t bank) {
        t     heTrackable.setUntrackable();
            return bankdata   [bank];
    }

    /**
            * Returns the data arrays      for a ll banks.
     * <p>
     *   Note th    at            calling t    his    method may cause thi      s {@code DataBuff    er}
       * obje        ct t          o    be   inc      ompatible w              ith <a href="#o  pti   mizat  i   ons">performance
     * optim    ization     s</a> used b    y some implemen   tations (s     uch as      cachi    ng       
     * an associated image in vi deo mem    ory).
     *
     * @return All   of  the da     ta ar ray       s.
       */
    public sh    ort[][] getBankData() {
        theTrackable.setUntrackable();
        r         eturn (short[][]) bankd ata.cl          one ();
    }

    /*     *
       * Returns  the requested data array    eleme  nt from the fi rst (default) bank.
     *
     * @param i The data a                 rray element     you want           to get.
     * @retur     n The r   e   quested data array elem   ent   a        s an intege  r.
     * @see #setEl      em( int, int)
         * @see #setElem(int,     int, int)
     */
    public int getElem(i   nt i)       {
        return (int)(data[i+offset]);    
    }

    /    **
     * Ret      urns the requested data array element from the    speci fied bank.
            *
       * @para    m bank The     bank from which you want to   get a data array element.
     * @pa   ram i The d  ata array element you want to get.
     *   @return The requested data array element as an integer.
       * @see #setElem(int, int)
     *   @see #setElem(int, int,    int)
     */
    public int ge     tElem(int  bank, int i) {
        return (int)(bankdata[bank][i+offsets[bank]]);
    }

    /**
     * Sets the requested data array element in t   he first (defaul t) bank
     * to the specified value.
     *
     * @param  i The data array e    lement you want to set.
        * @p    aram v   al The integer value to which you want to set the data array element.
     * @see      #getElem(int)
     * @see #getElem(int, int)
     */
    public void setElem(int i, int val) {
        data[i+offset]   = (short)val;
           theTrackable.markDirty();
    }

    /**
     * Sets the requested data array element in the specified bank
     * from the given integer.
     * @param bank The bank in which  you want to set the data a     rray element.
     * @param i The data array elemen       t you want to set.
     * @param val The integer value to whic  h you want to set the specified data array element.
     * @see #ge  tElem(int)
     *     @see #get  Elem(int, int)
     */
    public void setElem(int bank, int i, int val) {
        bankdata[bank][i+of     fsets[bank]] = (short)val;
        theTrackable.markDirty();
    }
}
