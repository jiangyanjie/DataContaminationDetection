/*
 *    Copyright (c) 1997,       2008,      Oracle  and/or its affil         iates. All rights reserved.
 *     ORAC   LE PROPRIETARY/CONFIDENTIAL. Use is subject   to license terms.
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
 **************************************    ****************************
 ************************************************************** ****
   *** COPYRIGHT       (c)      Eastman Kodak Company, 19    97
 ***  As  an         unp   ubl         ished  work pursuant to Title 17   of the United
 *** States Code.  All rights reserved.
 ************************************************* **   ***************
 ******************************************************************
 ***********     *******************************************************/

pack    age java.awt.image;

import st    atic su  n.j   ava2d.StateTrackable.State.*;
 
/*       *
 * This class ext         ends         <CODE>DataBuffer</CODE>     and stores data    internal      ly as
 * shorts         .  Values s          tored in the shor    t arra      y(s) of    this <CODE>Dat     aBuf    fer</CODE>
 *    are treated as  unsigned     va     lues.
 * <p>
 * <a   name=   "op   timizatio  ns">
 * N ote that some im  plement  ations may fu     nction more efficiently
 * if      they can maintain control    ove   r  how the dat    a for a         n image is
 * store  d.
 * For examp   le, optim   izat         ions s uch as ca    ching an image in video
 * memory require   that the implementa  tion  track all modific    ations
 * to th            at         data.
    * Other implementations may operate bett   er if the y can store the
      *     dat  a in locations ot    her than a      Jav  a array .
    * To maintain      optimum compatibil  i ty with various opti         m       izations
 * it is best to    avoid c     onstructors and met  hods wh     ich e                          xpose the
 * under lying storage as a Java a   rra    y as noted below in th  e
   * docu mentati      on f     or those methods.
 * </a>
 */
pu     blic      fin     al          class DataBufferUSho       rt e     xt en    ds     D     ataBuffer
{
    /**    T              he defau    lt data bank. */
    short data[];

                                /** All data b  a  nks */
    sh     ort bankda   ta[][];

       /* *
         * Construct     s a n unsi    gned- s   hort           ba   sed <COD  E>  DataBuffer</CODE  > with a sing   le b    ank and       the
               * specif ied size.
      *
          * @param size The s  ize of  the <CODE>DataBuffer</CODE>.
     */
      public DataBufferUShort(   i   nt size)     {
        super(S   TABLE, TYPE_USHORT, size);
        d       a      ta = new s hort[size   ];
        bankdata =   new   short[1][];    
                 bankdata[0]  =           data;
    }

    /**      
             * Cons  tructs       a     n unsigned-short based <CODE>D ata Bu   ffer</     CODE> wit h th    e      spe   cified numbe  r of
       * banks, all of   which are    the s           pecified size.    
     *
           * @param    siz   e   The size   of the banks in the <C  ODE>DataBuffer</COD     E>.
     * @param numBanks T   h e n um  ber of      banks in t       he a<CODE          >DataBuffe r</CODE>.
             */
              public DataBufferUShort(   int size,  int    numBanks) {
                                  super(STABLE, TYPE_U          SHORT , size  , numBank   s); 
             b      ankdata =     new  sho       rt[numBanks][];
             for (int i= 0; i  < numBanks; i+    +)       {
                bankd   ata[i]  = ne  w short[siz      e];
              }
               data = bankda    ta[0];
     }  

    /**
     * Constr  ucts    a   n unsigned  -short based <CODE>DataBu     ff     er</CODE> with a s i      ngle bank
     * using  the s    p             ecified array.
     * Only the first <CODE>size</CO   DE> elements should be used by accessor     s of
       * this <CODE>Da  taBuffer</CODE>.       <CODE>da    taA  rray</         CO   DE   >      m    ust be large enough         to
           * hold <CO    DE>size</CODE> elements.
     * <p> 
       *         Note that      {@ co  de D     ataBuffer}       object    s   created by   this constr    uc   t    or
           * may be inc    om  patible with <a href=     "#  op  timizations">pe   rfo  rma    nc e
         *         optimizations</a> used by some imp   lementations (suc                h               as     caching   
     *      an assoc i     ated image in video memory).
           *
     * @param dataArray The   unsig         ned-s     hort array for the <CO  DE>DataBuff      er</CO  DE>.
     * @param    s    ize The size of the <C     ODE    >DataBuffer</CO  DE> bank.
        */
          public DataBufferUSh                      ort(sh      or    t   dataArray[], int s   ize) {
                              super(    UNTRACKABLE, TYPE   _USHORT, size);  
            if (d       ataArray == null) {
                  th                    row ne   w NullPointerException("dataArray is null");
              }
        data = dataArray   ;
                  ban   kdat   a = new          short[1][];
        ba   nkdata[0] = d   a   ta;
                  }
     
                      /*        *
     * Constructs an unsigne     d-short bas   ed <CO  DE>D  ataBuffer</CODE> with      a single ba  nk
              * using t he    specifi    ed array, s   ize, and offset.   <CODE>d ataArray</CODE> mu st   have at
     * least <CODE         >offset</CODE> + <CODE>s   ize</CODE>          elements.  Only elements
     *    <CO  DE>offset     </CODE> thr  ough  <CO DE>offset  </ CODE> + <CODE>size</C  O  DE> - 1 shou   ld
     * be    use  d by a  cce       sso  r s  of t                h   i s <CODE>DataBu   ffer</ CODE>.
     * <p>
     * Note th     at {@code DataBuffer} ob      j   ec   ts created   by this constructor    
     * ma   y  be      incompatible   with <a    href="#optimi zation  s">perf    ormance
     * optimiz                       ations</a> used by    some implem entat   ions (such as c  aching
       *          an associated image in v    ideo     memory).
     *
       *   @p     aram d    ataArray The un      s  igned-short ar ray    for the <CODE>Data Buff    er</CODE>.
           * @param size The s         ize of t              h             e <  COD         E>Data    Buffer</CODE> bank.
         * @       param    offset Th  e o ffset      in   to the <CODE>dataArray<    /COD    E>       .
           */
    public DataBuf    f      erUS                 hort(short dataArray[], int   si  ze, int offset ) {  
        su       p       er(UNTRACKABLE, TYPE_USHORT,  size,            1, offset);
        if (   dataArray == null) {
                 t     hrow      n          ew      NullPoin       ter     Excep   tion("da    ta   Arra   y is null");    
        }
              if ((size+o ffset) > dataA    rray       .length)  {
                throw new Il leg            alA     rgumentExcep   tion(       "Length of dataArray is less "+ 
                                                              " than si    ze+offs  et."        );
              }
            data = dataArray;
        bankdat  a       =     ne   w short[1][];
           bankd   ata[    0] = data;
      }

        /**
     *         Cons tr     ucts an unsigned -sho   rt based <CODE>D     ataBuffer  </CODE  > with t  h   e sp ecifi   ed arrays.
     *       The number  of banks will      be equal to <CODE>   da        taArray.leng th</    CODE>.
     * Only the    first <CODE>siz  e</CODE> e  le               me     nts of e      ach array should be used by
       * accessors of this      <CO    DE>DataBuffer</COD  E>.
       *    <p>
        * Note that {@code Data B  uffer} obje     ct s create      d by this cons    tructor
       * ma      y be incompatible wi      th   <a    href=  "#   optimiz  ati    ons">performance
     * optimi   zations</a> use      d      by some impleme  ntations (such as caching
       * an associated  image in vide   o memory).
                *
     * @param dataArray The unsign   ed-short arrays for      t  he <CO     D   E>DataBuffer</CODE        >.
     * @param size Th    e siz                         e of the ban  ks in the <CODE>DataBuffer</COD  E>.     
        */
    publ ic     D ataBuffer     UShort(     short   dat       aArray[   ][], int si         ze) {
            super(UNTRA   C  KABLE  ,   TYP E_U   SHORT,        size  , dataArray.length);
        i f (dataArray == null)  {
            throw new  Null   Po            int   erException(    "dataAr          ray is nu ll");
               }
        for (int i=0; i   <   dataArra     y.length; i++)   {
                    if (dataArra   y [i] == null) {
                th        row new NullPointerEx  c eption("dataArra      y["+i+"]  is null");
                   }          
            }

                 b  ankdata = (short[][]) dataArra        y.c  lon e();
                  data    =     bankdata[0];
    }

    /**  
            * Construc    ts an    uns   igned-shor   t based <CODE>Data  Buffer</CODE> with specified a  rr  ays   ,
     * size, and   offsets.
          * The number of banks is equal to <CODE>dat         aArr  ay   .l   ength</ COD        E>       .  Each ar ray m     ust
     * be at le       as   t as      large                 as   <    CODE    >size</CODE> + the corr     esponding offset.   Th ere must       
     * be an entry i  n  the of      fset a    rr    ay for each      <COD           E   >dataA    rr      ay</CO    DE> ent  ry.                For    each
     * bank, only elements   <C   ODE>offs   et</CO   DE> thro ugh
      * <CODE>offset</CO DE> + <COD       E>     size   <           /CODE> - 1 sho  uld be
                * u s e       d by ac     cessor s of this <CODE>DataBuff   e     r</CODE         >.
     * <p>
     *    No      te that     {@code DataBuffer}      o    bjec  ts cr   eated by this con  struc    tor
        * may b    e incompat    ib  le w  ith <a href="#opti     mizations  ">pe    r   forma   nce
       * opti  mi zations</a> us   ed by some   imp   l    ementat       ions (   suc   h as ca    ching   
     * an associa ted image in vid               eo me                      m    ory).
               * 
     * @pa   ram da     ta Arr                      ay The unsigned-sho  rt array   s for    the <CODE>D    ataB   uffe   r</CODE>.
     *  @para        m s       i  ze T  he size of       the banks in th   e <CODE>Dat    aB   uffer</C   ODE  >.
     * @param offse ts T  he offsets into ea ch     arr    ay.       
          */
    public DataBuf      ferUS   hort(shor     t dataArray[][],    int size,        int          offset s[]) {
         s uper         (UNTRACK                ABLE,      T YPE_U       SHOR       T, siz     e, d   ataArr ay.le                ngt  h, off        sets);    
        if (dat     aArra                y     == null) {
                thr  ow new Nu  l  lP     ointer     Exception("data  Ar        ra y                   is nu  ll");
        }
                 for     (int i=0; i               < dat    a          Array.l   ength; i+ +) {
               if (dataArray [ i] ==    null     ) {
                  thr  ow n               ew N  ullPoi   nte   rExce   p tion("d  ataArray  ["+i+"] is null");    
            }
            i    f ((size  +offs  et     s[i ]) > d       ataArray[i].l e   n      g     t   h   ) {
                 throw new IllegalArgumentE  xceptio                   n("Length of da  taArray  [  "        +i+   
                                                                                            "]    is less than si       z        e+"+
                                                              "of   fs   ets    ["+i+"].")  ;
             }

                }
             ba      nkdata = (      sh   ort[][])     dataArray.clone();
                  data    = bank   d ata  [0];
    }

        /**     
     *     Returns t                   he   default    (first) unsign     ed             -short dat    a array.
     * <p>   
     * Note tha                      t calling   th  is m   ethod   may cause thi   s    {@    code DataBuffer}
                   * object to      be incompatible wit       h <a href="#opt       imiza    tio  ns">perform      ance
     * opti    mizations</a> used by  some        implementations       (such as cachin   g
             * a   n ass  oc    iated image in video memory).
      *
     * @return The     first      unsigned-short data array.
           */
    publi       c short[] getD     ata() {
             theTrack   able  .setUntrackable();
        return data;
    }

     /*  *
     * Retur   ns       the dat    a  array for       the     spe    cified bank.
     * <p>
     * N    ote that calling t  his method ma y cause thi    s {@code    Data   Buffer}
     * obje    c    t to be incompatible with      <a href ="#o  ptimizations">performance
     * optimizations</a> used b     y some   implementation  s (such as caching
     * an associated image  in    video memory).
          *
       * @para m bank The    bank whose data array   you want to get.
     * @retu    rn The da    ta array   for the specified     ban    k.
       */
              public short[] getData(int bank) {
         theTr     ackable.setUntrack      a  ble();
         return bankda      ta[bank];
    }

    /      **
     * Returns the data arrays for  a             ll banks.
     * <p>
     * Note that calling this method may cause this {@co de D    ataBuf   fer}
         * object to be      incomp   atible with <a href="#optimizations">performa     nce
     * optimizations</a> use  d by some         implementations (such as caching
     * an     asso   ci  a  ted image in     video memory)   .
       *
     * @return All of the data arrays.
     */
        public short[][] getBankData() {
        theTrackable  .setUntrackable();
        return (short[][     ]) bankdata.clone ();
    }

    /**
     * Re turns the requested data array element    fr    o  m the first (default) bank.
        *
     * @param i The data array element you want to get.
     * @return The re    q  u ested data arr      ay element as an integer.
         * @see #setElem(int, int    )
     * @see #setElem(int, in    t   , int)
        */ 
    public int getElem(int i) {     
        return (int)(data[i+offset]&0xffff);
       }

    /**
     * Returns the requested  data array eleme   nt from the specified bank.
      *
     *   @param bank The bank from which you w    ant to get     a dat      a   array element.
     * @param i The data array element you want to get.
     * @return The r  eques ted    data array element as an integer.
        * @see #setElem(int, int)
     * @see #setElem(int, int, int)
     */
    public int getElem(int bank, int i) {
        return (int)(bankdata[b   ank][i+offsets    [bank]]&0xffff);
      }

    /**
     * Sets the req ueste    d data array el  ement in the first (default)   bank
     * to the specified val   ue.
     *
     * @p aram i The data array element you want to  set.
     * @param val The integer value to which you     wa   nt to set the data array element.
     *      @see #getElem(int)
     * @see #getElem(int, int)
     */
    public void setElem(   int i, int val) {
        data[i+offset] = (short)(val&0xffff);
        theTrackable.markDirty();
    }

    /**
     * Sets the requested data array element in the specified bank
     * from the given integer.
     * @param bank The bank in which you want to set the data ar    ray element.
     * @param i The data array element y  ou want to set.
     * @param val The integer value to which you want to set the specified data array element.
     * @see #getElem(int)
     * @see #getElem(in    t, int)
     */
    public void setElem(int bank, int i, int val) {
        bankdata[bank][i+offsets[bank]] = (short)(val&0xffff);
        theTrackable.markDirty();
    }
}
