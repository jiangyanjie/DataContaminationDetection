/*
    * Copyright (c)     1 997,  200  8, Oracle    and/or   its affi     liates. All rights reserved.
          * ORACLE PROPRIETARY/CONF       I  DEN    T           IAL. Use is subject to license terms.
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
 *******    ************************************************    ***********
 *** COPY     RIGHT (c) Eastman Kodak Compa  ny, 199  7
       *** As  an unpublished  work     pursuant  to Title 17 of     the  United
 *** States Code.  All rights reserved.
 ****************************************** ************************
 ******************************************************************
 **********************   ********************************************/

p      ackage java.awt.image;

     import sta  tic sun.java2   d.StateTrack     able     .State.*;

/**
 * This cla  ss extends <CODE>DataBuffer</CODE> and stores data inte       rn    ally as bytes.
 *   Values  sto    red in the byte array(s) of this             <CODE>DataBuffer</CODE> are treated as
 *     unsigned  va    lues    .
 * <p>
 * <a name="opti   mization s">
 *      Note tha t some implementatio    ns may func t   ion more efficiently
 * if they can   maintain cont    rol    over how the    da      ta for an  image is   
 * stored.
 * For      example, optimizations      such as           caching an im       age in video
 * memory require       that the     implementat ion      track all modifications
 *    to that data.
 * Other imple  men  tations may operate better if they can s   to  re the
 * data in locati      ons other than a Java ar     ray.
 * To maintain optimum compati     bili     ty with var  ious optimiza tions
 *          it is     best    to avoid const  ruct    ors an  d         met  hods which expose the
              * underlying storag      e as     a Jav      a array, as noted below in the
   * document    ation for those methods.
 * </a>
   *   /
public f      ina  l    c  lass D    ataBuf    fer  Byte e   xte    nd  s       Dat   aBuffer
{
    /*  * The def          ault data bank. */
           byte   data[];

       /** A    ll data b    anks        */
           byte ban kdata[][]  ;

         /**
     * Const   ructs a byte-   based <     CODE>Dat    a Buff     er</CODE>     with a sin    gle bank and the
     *  specif     ied size.      
       *
      * @pa             r am si     ze The size    of the <CODE>Dat      aBuf     fe  r</CODE>.
     */
    public     DataBufferBy  te(int  size) {
         super(STABLE, TY PE_BYTE, siz  e);
        data =   new byte[size];
      bank    d  at    a = new byte     [1][     ];
                   bankdata[0] = data  ;
      }

    /**
      * Con    structs  a byte based <CO    DE>DataB uffer</COD             E> w ith  the specified      numbe r of
       * ba nks a     ll of which        are the specified size.
            *
     *   @param   size   The size of the bank  s   i n the <     CODE>DataB  uff   er</CODE>.
     * @param num      Ban        ks T   he numbe     r of banks in the  a<C   OD      E> DataBuffer</CODE   >.
           */
     public D    ata   Buffe      rByte(int size, i    n        t numBanks) {    
          s  u                    p er(STA       BLE   , TYPE_BY  TE, si   ze,     numBanks);
                 bankdata  = new byte[n     umBanks]  [];   
           f   or (int i= 0; i < num      Ba      n   ks  ; i++   )     {
               bankdata   [i] = n     ew byte[size];
             }
        data = bankd        ata[0];
    }

    /**
     * C o  ns         tr  ucts a byte-b    ased <CODE>DataBuffer</CO  DE> with    a single bank      using t   he   
     * s       peci   fie    d array.
                 * Only the first <CODE>siz  e    <     /CODE>     elements should be  used   by a  c             ce  ssors            of
        * th     is       <   CODE   >DataBuffer </CODE>.  <CODE>dataArray<    /C ODE> must be large  enough to
         * hold    <CODE>size</CODE> elements.
                * <p>
     * Note   that     {@c     ode DataB  uff         er} objects created by thi  s constructor   
     * may    be     inc    ompatib              l    e with <a h  ref="#o   ptimizations">pe     rformanc   e
     *     optimizatio     ns</a> used b        y so     me implementations (such a    s caching
     * an associa     ted            image in vi deo memory).
      *
     * @pa  ram d        ataArra  y The    byte array for the <CODE>DataBuff er</CODE>.
        * @param size The    siz   e of the <CODE>DataBuffer</CO       D  E> bank.
     */
       public Dat    aBu ffe     rByte(b  yte dataArray[], int s ize) {      
                      super(UNTRA  CK  ABLE,   TYPE_BYTE, si    ze);
          data = d a  taArr      ay;
                  bankdata = new byt      e[1][];
        bankda   ta[0] = da  ta;
         }

    /**
     * Constructs a byt   e-based <C    ODE>   DataBuff er  </CODE> with a single bank using the
      * s  pecif ied array,         size  , and offset.  <CODE>data  Arra     y</CODE> m   ust have at    least        
              * <CODE>offset</CODE> + <CODE>s  ize<   /CODE> elements.  Only ele       ments <CODE>offset</CODE> 
     * thr     ough <CODE>       o ffset</CODE> + <CODE>size</CODE> - 1
     * should be use    d by accessors of th    is <CODE>DataBuffer</CODE    >.
         * <p>
     * Note tha    t   {@code Da      taBuffer} obj    ects     created   by   th is co      n s     truc   to       r     
      * may be       incom  p   at ible   with   <a h  ref="#op  timizations">performance
     *   o  ptimizations</a> used by some impleme   ntations (such          as ca       ching
         * an a    sso  c         iated image in video memory).
         *
       * @para     m d     ataArr      ay The byte    array  for the  <CO   DE>DataB uffer</CO  DE>.
     * @   param s ize T  he size    of t   he <CODE>D  ataB      uffer</CODE> bank.
      *      @param offs            et The offset i   nto the    <CODE>da      taArra      y</CODE>. <   CODE>dataArray</CODE>
     *  mus   t have at lea st    <CODE    >o ffset</C OD E> + <   CODE>siz   e</COD E> el ements.
       */
    pu     bl  ic  DataBufferByte(byte dataArray[], int s  i       ze  , int offs  et){
        super(UNTRA   CKABLE, TYP      E_     BYTE, size, 1, offset);
            data     = dataArray;
        bankda   ta =   new by   te[    1 ][     ];
        bankdata[        0] =      data;
    }

    /**
        * Constructs a byte-based     <C     ODE>DataBuffer</CODE>    with the            specified a    rrays.
          * The     num          ber of ba n  ks is equal to <CODE>dataArray.length    </C  ODE>.
     *  Only the      f irst <CODE      >   size</CODE> eleme    nts            of eac h array       should                  be     used by
     * accessors of th  is <CODE>DataBuffer</CODE>.
     * <p> 
     * Note   that {@c     ode DataBuffer}   objects created by this      con   str      uctor
      * may be   inc   ompatible w  ith <a href="#optimization              s  ">  per    formance
     * opti       miz   ations</a> used b   y some implementations  (such as caching
     * an associated image in vi        deo memory).
     *
     * @param dataArray Th   e b   yt e a    rr     ays for      the <CO  DE>Da   taBu     ffer</CODE>.
       * @pa ram size The size of     the banks i     n the <CODE>Da         taBuf   fer</CODE>.
     */       
        pub    lic     DataB ufferByte(byte dataArray[][ ],        in    t s iz e) {
           super(UNTRACKABLE, TYP   E_BYTE, s            iz     e, dataA  rray.length);
               bankdata = (       byte[][ ]) data    Array.clo   ne();
        d       ata = bankdata[0];
         }

    /      **
     * Co   nstruct    s a byte   - based <CODE>DataBuff    er</CODE> with the specified     arrays       , size,
     * an    d offsets.
     * The number of         banks i         s       equa     l to <   CODE>dataArray.length<   /CODE>.  E  ach a   rray  must
         * be at least as large as  <CODE> size</CO  DE> +   the correspond    ing <CODE> offset</C    ODE>.
     * There mu   st b    e an       e    ntry in the <CODE>offset</CODE>   array for each <  CODE>dataArray    </CO     DE>
         *    entry.  For      each ban  k, only elements <CODE>    offset</CODE>  through
     * <CODE  >o      ffset</CODE>     +     <C                  ODE>     size</CODE> -     1 sh         ould be used by accesso rs of this
      * <COD  E>D  ataB  uf            fer</CODE>.
          * <     p >
     * Not    e that {@code D    ataBuf    fe r} objec    ts cre    at     ed    b             y this construc   to  r
           * may be incompatible with <a href  ="#optimiz  ati ons">performance
     *            optimiza  tions</a> used  by some impl     ementation s (such as caching
     * an a ssociated image in v    ideo mem  o  r y  )             .
       *
     * @pa       ra   m d       ataArray The     by  te ar                 rays for the <C           ODE>DataBuffer</CODE>.
     *    @param    size Th                   e s        ize o     f the banks in the <C       ODE>DataBuffer</CODE>.
     * @param  offsets             The offsets      into  eac      h    array.
     */  
    pu  blic D     ata    BufferB      yte(byte da   taA      r    ray[  ][]  , int size,   int     offsets[]) {    
                   sup  er(UNTRA   CKABLE, TYPE_      BY         TE, s  ize, d  ataAr ray.leng  th     , of   fset s);
                    bankdata =   (by   t    e[][])   dataArray  .c   lone();    
            d    ata = b   ankdata[0];
    }

    /**
            * Returns               the         def    ault (fir  st) b        yte  d      ata arr   ay.
            * <p>
     * Note that calli  ng this metho  d    ma   y cause this {@code DataBu       ffer}
          *   obje    ct to be           i   ncompatible wit    h     <      a href="    #opt imizat ions  ">pe   rf   ormance
         * optimiza   tions</a> u     sed b      y some implement     ations      (such as ca   ching
              *    an associated image in      video memory).
     *
     * @retu   r   n The    first b              yte data array.
     */
    public byte  [] getData   () {
               t  heTrackable.setUntrac       kable();
        return da   ta;
    }

                       /**
     * Re    t  urn  s the da  t          a array for the specified bank.
     * <p>
         * Note that     calling th  is method may    cause this {@code DataB uffer}
       * obj  ect to be incompat      ible with <a href=  "#optimizations">performa      nce
       * op         ti  mizations</a>  used  by            some i  mplement     ations    (     s   uch as caching
     * an    asso  ciated image       in video memor  y).
     *
     * @param bank The bank whose dat              a array you want to get.
     * @return The     data array  for the     sp   ecif     ied ba     nk.
     *     /
        pu         b         lic byte[] getData(int bank)       {
                theTrackable.s         etUntrackable();
              return bank       data[bank];
    }

    /**
     * Returns     the data arr         ays for all ba  nks.
     *    <p>
     * Note that callin          g this metho d may cause this {@c          ode      Data Buff   er  }
     * object to be incompatible   wi th <   a href="#optimizat ions">performance
     *  optimization s</a> used by some i    mplementations (such    as cachin g   
     * an associ ated image in    vide o memor   y).
     *
     * @return All of th    e data arrays.
     */
      public b    yte    [][]    ge   tBankData() {
         t   heTra     ckable.   setUnt  rack     able();    
                 retu     rn (byte[][]) ba        nkdata.clone   ();
         }
        
    /**
     * Retu  rns the re   que  sted data         arr    ay    element from the first (de  fault) ba    nk.
     *
     * @param i   The data   array element you want       to get.
     * @return The requested data array element     as      an integer.
     * @see #setElem(int, int  )
     * @see #se  tElem(int, int,       int)
     */
     p    ublic int getE  lem(int i  ) {
          return (int)(data[i+offset]) &    0xff;
    }

    /**
        * Returns th  e requested data       array element from t   he specified bank   .
     * 
     * @param bank The bank    from which you want to get a   data array element.
        *           @param i      The dat      a  array element you want to get.
     * @return The re    quested data array element as an int   eger. 
     * @see #setElem(int, int)
        * @see #setElem(int,    in    t,  int)
     */
    public int getElem(i      nt bank, int i) {
        return (int)(bankd  ata[bank      ][i+offsets[bank]]) & 0xff;
     }

    /**
     * Sets the re     quested data array element in the first (default) bank
     * to the   specified value.
     *  
               * @param i The data array element you want to set.  
        * @param v   al The integer value      to which you want to   set the data array el  ement.
     * @see #getElem(int)
     * @see #getElem(int, int)
     */
    public v   oid setElem(int i, int           val) {
        data[i+offset] = (byte)val;
        theTrackable.markDirty();
    }

    /**
     * Sets the requested data array element in the specified bank
     * from    the given integer.
     * @param bank The bank in which you want to set the data array element.
            * @param i The data array element you want to set.
     * @param val The integer value  to which you want to set the specified data array element.
     * @see #getElem(int)
     * @see #getElem(int, int)
     */
     public void setElem(int bank, int i, int val) {
        bankdata[bank][i+offsets[bank]] = (byte)val;
        theTrackable.markDirty();
    }
}
