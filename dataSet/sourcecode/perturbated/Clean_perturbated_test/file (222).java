/*
 * Copyright (c) 1997,     2008, Oracle      a  nd/or      i   ts affiliates.           All   rights reserved.
 * ORACLE PROPRIETARY /CON     FIDENTIAL. Use      i    s su     bject to li    cense terms    .
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

/* ****************************************************************
 ******************************************************************
 *********************************    ****  ***********************  ******
 *** CO      PYRIGHT (c) Eastman Kod       ak Comp     any,   19  97
 *** As  an unpubl   ished  work pursuant to Tit  le 17 of the United
 *** States    Code.  All rights reserved.
 ******************************************************     ************
 ******************************************************************
 ****************     **************************************************/

   package java.       awt.image;

import     stat  ic sun.java2d.StateTrackab    le.State.*;

/     **
 * This class ext ends <CODE>DataBuffer</CODE> and  stores data  internally
 * as integers.
 * <p>
 * <a     name="opti   mizatio   ns">
 * Not   e that some im     plementation    s may function mo     re efficiently
 *      if they ca   n mai   ntain control ov        er how the data f or    an image is
        * stored.
     *    Fo    r example, optimizat   ions such as ca     ching an image in video
 * memory require that the impleme        ntation tra   ck all mod ifications
 * to that data.
 *    Other implementations       may operate better if  t        hey c    a  n      store the
 * data in l    ocations other than a J   ava a    rray.
 * To maintain o pt   imum     compatibility w  ith va   rious        optimizatio    ns
 * it is best to a     void cons   truc     tors  and meth   ods      which   expose the
        * underlying   storage as a Java array a        s noted              below in the
 * do    cumentation for those methods.
 * </a>
 */
public final clas    s Dat  aBufferIn       t e    xten  ds      DataBuffer
{
    /** T    h e default data bank. */
          int d   a  ta[];

    /** A   ll data bank     s */
    int bankdata[][];

    /**
     * Constructs an integer- bas       ed <         CODE>DataBuffer</CODE> wit         h a si     ngle bank
       * and the      s        pecified size.
        *
        *    @para  m siz  e The si   ze of the <CODE>Dat     aBuff      er</CODE>.
            */  
    public DataBuffe rIn t(in    t  s ize) {
        super(STABLE,     TYPE_INT, size);
                      data =      new     int[size];     
                       bankdata = new i    nt[1][];
        bankdata[0] =              data;
      }

    /*       *       
                     * C onstructs an integer-based      <C           ODE     >Dat   a   Buffer</       C  ODE> with    the   s pecified number of   
            * banks, a   ll of which are     the specifie  d size  .
     *
     * @param s  iz    e The size  of the b a nks in the <CODE>D       ataB      uf fer</  COD   E>.
      * @param       numBanks The number of b   a nks in the a<CODE>DataBu     f  fer</CODE>.
           */
             public       D ataBufferInt(   int s     iz           e,    int numBanks)     {
                   super(STABLE, TYP    E_INT, size, numBa  nks);
        bankdata    = ne w int[numBanks][]   ;
        for (i    nt i= 0; i < numBanks; i++)       {
              bankd        ata[i       ] = n    ew  int[siz    e];
                      }
           data =      bankd    ata[0];
    }

    /**
     * Co  nstr     u cts an integer-bas    ed <CODE           >DataBuffer</COD  E> with a s   ingle ban   k using the
     *   specified     array         .
            * O      nl  y the f     irst       <CODE>size</CODE> element  s sh ould be  used by accessors       of    
     * this <CODE>   Da taBuffer</CODE>.  <CODE>dataArray</C    ODE>  must b    e large             e     nough t      o
      * hold <CODE     >size<           /CODE> el     ements.
        * <p>
         *              Not     e that     {@code D  ataBuf   fer}   objects created by this c            ons  tructor
     * may be incompatible with <a href="#opti mizations">perfor  mance
     *   optimizations</a  > u    sed by some impl  ementations (such as c    aching
         * an associated ima   ge in video mem  o      ry).
     *
     * @p         aram dataArray The int ege      r array fo    r   th    e <CODE>        Data  B     uffer</COD             E>.
     * @para  m s   ize     The size of the <CO      DE>DataBuffer</CO  DE> bank.
          */
               public DataBu  fferIn    t(int dataArray[]       , in     t s     ize) {
          super(UNTRA    CKABLE, TYPE_INT, size            );
          data = dataArray;
             bankdata = new int[1][];
        bank  data[0] = data;
     }     

       /*    *
        * Con   str    uct    s an in    te   ger-based <CODE>Da   taB  uf    fer</CODE   > with a     single bank    using the
           * specified array, size, and   offset.  <C     ODE>dataAr ray<     /      CODE>    must have at le   a    s   t
     * <C ODE>offset</CODE> + <CODE>size<    /CODE> eleme  n  ts.  Only       elements <CODE>offset</CODE>
         * through <CODE>offset</CODE> + <CODE>size</CODE> - 1
              *    should be u      sed by accessors of thi      s <CODE>Da     taBu             ffer</CODE>.   
     * <p>  
     * Note   that    {@    code DataBu    ffer}     o      bjects created by this constru  ctor
     *    may be i      ncompatible with <a href=" #op   tim          i    zatio ns">performance
                * optimizations</a> used by             so                me imp     lementations (such as cachi     ng
       * an ass    ociat      e   d image in     video m emory).
     *
     * @   param dataArra     y T  he integer array       for the <COD  E>Da    taBuffer</CODE   >.  
     * @param       size The size of the <CODE>DataBuff   er</CODE>   bank.
       *    @param off  set The offset into     the <C                  ODE>dataArray</CODE>.
     */
        pub        lic D    ataBufferInt (int dataArray[], int     si     z e, int offset) {
          s    uper(UN   TRACKABLE, TYPE_INT, size        , 1, offset);
                 d        ata = data  A          rray;
                   bankdata = new int[1][];
            ba   nkdata[0   ] =     data;
    }

    /**
     * Con    struct s an integer-base         d <CODE>DataBuf      fer</CODE> wit         h the s    peci  fied a  rrays.
         * The number of banks will be equal to <CODE>data        Array.length</CODE>.
       * Only the    first <CODE>size</CODE> eleme      nts of ea   ch    array sh    ould be used by
          * accessors of th is <CODE>Data    Buffer</CODE>.
     *        <p>
     *     Note that {@code DataBuffer} objects cre    ated by th    is co  nstr         uctor
     * may be incomp     ati   bl e w   i    th    < a href="#o pt    i      mizations">  perfor  ma        nce
         * op    timizatio         n  s</a> us      e d by some implementations (such as caching
     * an a   ssociated i     mage   i   n vide   o memory).
        *     
     * @param dataArray The integer arrays fo r   the <CODE>   DataBuf    fer</CODE>.
     * @pa      ram          size T  he size of the banks in the <CODE>DataBuffer   </CODE>.
     */
    public D            ataBuffe          rInt  (int dataArray[][], int s    iz           e) {
                   s   uper(U  N   TR       ACKABLE, TYPE_IN           T, s        ize, data A  rray.leng     th);
                 bankdata = (int       [][]) dataArray.clo     ne  ();
                data = ban  kdata[0]        ;
    }

    /**  
             * C ons        truct   s an integer-ba       sed   <CODE>DataBuffer</CO    DE> w  i    th t  he spec   ified a    rray  s,        s ize,
     * and offsets.
        * The number of b     anks is equal to <C ODE>dataAr     ray.length<   /    CODE>  .  Eac    h   array must
     * be a      t le   ast as large     as       <CO      D   E>size</C       ODE> + the       cor   res  ponding o  ffset.   The  re must
         * be    a n en    t   ry in th    e offset array for each <CODE>dataArray</CODE   > entr      y.  F     or eac       h 
     * b    ank, only elements <C    OD       E>offset    </CODE>   through
     * <CODE>  off  set</CODE> +      <CODE>size</CODE      > - 1 should       be
         * u     sed     by accesso    rs    of this <COD    E  >Da ta      B             uffer</C  OD   E>.
           * <p>
       * Note th  at      {@code DataBu ffer} ob    jects created by this cons  tructor
     * may be     i  ncompatible with <a href="#   optimizations">per   formanc   e    
     * o  pti       miza    tions</a>  used       by some implementations (su              ch as c aching
     * an associated im age in video memory).
     *
                  * @param dataArray The integer arrays for the <CODE>DataBuffer</CODE>.
     * @param size The si   ze o    f the    b a      nks  in the     <  CODE    >D      ataBuffer</CODE>.
       * @param   offs  ets The offse         ts int   o each array.
     */
    publ ic  DataB   ufferInt(int dataArray[][]  , int size, int offset            s[]) {
           super(U    NTRA       CKAB      LE, TYP E_IN      T, s    ize, dataArray     .l       ength    , offs    ets);
            bankdata       = (int [][]) dataA   rra        y.     clone ();
                           dat   a =      bank   d   ata[0];
    }

    /**
       * Retu  rn   s the default (first) int data array in      <C   ODE>DataBuffe   r</CODE>.
     * <p>
     * Note that calling this metho   d may ca      use th       i      s {@code DataBuffer}   
     *        object to        be inc  ompati  ble with <a h                    ref="#optim      iz  ations"      >pe   rformance
     *      o    pti       m    izati    ons</a       > used b   y some implementations (s      uch as cach       ing
     * an associate       d imag   e in    vide  o memory  ).
          *
     * @return The first integer data a             rray.
     *  /
             public int[] g    et Data()        {
                    theTr       ac      ka   ble  .setU  ntr  ackable(  );    
        retur  n data;   
       }

       /**
     * R   eturns the data a   rr   ay for the specified      bank        .
          * <p>
     * N            ote that c a   llin     g this met      hod    may cause this     {@      code  DataBuff   e     r}
     * object  to b    e inc ompa  tibl e w         ith <a href="#optimizati   ons">pe       rformance
            * optimizations<    /a> used     by some   implementations (    suc h     as cac     hing
     * an assoc       iated image in vid    eo       memory).
     *
     *    @param bank The bank    whose data   array you want to get.    
     *     @re     turn The d ata array    for the specifie  d bank. 
      */
        public int[]          ge   tData(in   t      ba      nk) {
        theTr  ackable.setUntrackab             le();
          retur n   b    ankdata[bank];   
    }

    /  **
     * R        et urns th e data      arrays for all bank  s.
        * <p>
       *    Note that calling      thi  s method may caus  e this   {@code            Data   Buffe          r}
        * object to         be incompatible    with <a href="#opti           mizations">perfo            rma nce
      * op  timizati     o   ns</a> used by some imple     mentations (such as caching   
     * an associate d ima  ge in      video   memory   ).
         *
           * @return All of     the data arrays.
               */  
    public  int[][]   getBankData() {
           the      Trackable.setUnt    rac  kable();
        re  turn (int [][]) bankdata.clone();
    }

    /**
        * Returns the requested data array element from  the first (def    ault) bank.
     *
     *      @pa  ram i   The data array el   eme      nt you want to get.
     * @return The reques te     d data arra  y element as an int   eger.
     * @see #setElem(int, int)
          *        @see #setElem(int,    int, int)
     *    /
    public int getE lem(int        i) {
              return data[i+offset];
    }

          /**
     * Returns the re   quest  ed data array element f rom the specified bank.
          *
     * @param bank The bank f      rom      which yo  u want to get a data array element.
     * @param i The data array     eleme        nt y  ou want to get.
     * @re   turn The requested data array element as an integer.
     * @see #setEle      m(int, int)
         * @see #setElem(int, i   nt   , int)
     */
    public int getElem(int bank, int i) {
        retur  n bankdata[bank][i+offsets[bank]];
      }

    /**
            *     Sets th  e requested data array element in the first (default) bank
     * to the specified value.
     *
                    * @param i The data array element you want       to    set.
      * @param val The    integer value to which you wan  t to set     the data array element.
     * @see #getElem(int)
     * @s  ee #getElem(int, int)
     */
    public vo   id setEle m(     int i, int val) {
        data[i+offset] = val;
        theTr  ackable.markDirty(     );
    }

    /**
     * Sets the r     equested data array elem     ent i n the specified bank
        * to the integer value <CODE>i</CODE>.
     * @param bank The bank i   n which you want to set the data array element.
     * @param i The data  array element you want to set.
     * @param val The integer value to which you want to    set the specified data array element.
     * @see #getElem(int)
     * @see #getElem(int, int)
     */
    public void setElem(int bank, int i, int val) {
        bankdata[bank][i+offsets[bank]] = (int)val;
        theTrackable.markDirty();
    }
}
