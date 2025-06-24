/*
  *   Copyright (   c) 1995,  2013, Oracle and/or   its affiliates. All righ  ts r   eserved.
   * ORACLE PROPRIETARY      /CONFIDENTIAL. Use is s    ubject to license terms.
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

package java.io;

/**
 * The         {@code DataInput} interface   provides
 * for re    ading      bytes from a      binary stream and
 * rec  onstructing from them data in      any of
 * the Java primitive types. Th   e      re     is also
 *          a
 * facility    fo   r reconstructing a {@code Str ing}
 * from data in
 * <a href="#modif ied-utf-8">modified   UTF-8</a>
 *    format.
 * <p>
 * It is generally true of all       the rea ding
 *      routines in t h     is i    nte   rface t  hat if end of
 * file is   reached before the    desired num  ber
 *     o     f bytes has been read, an {@code EOFExc   eption}
 *       (which   is a kind of     {@code IOException})
 * is  thrown  .      If any   byte cannot be read for
   *   any re    ason other t           han end of fil    e,    an {@code IOException}
 * other than {@code EOFException} is
 * thrown. In particular   , an {@code       IO    Except       ion}
 * may be    th    rown if the input stream has b     een
 * cl      osed.
 *
 * <h3><a name="modified-utf         -8">Modified UTF-8</a>    </  h3>
 * <p>
 * Impl    emen    tati    ons of the DataInput an     d Da        taOutput in terfa ces represent
 * Unicode str   ings   in a     format tha     t is             a slight m           odification of U      TF-8.
 * (For information regarding the standard UTF    -8 fo      rmat, s     ee  section
     * <i>3.9 Unicode Encoding Forms</i> of    <i>   The Unicode S   tan   dard, Versi     on
   *    4.0</i>).
 * Note t  ha        t in the following table, the     most significant bit appears in the
 * far left-hand     column.  
 *
     * <b   l ockquote>
 *       <t  able border="1"                cells         pacin     g="0   " cellpadding="8"
 *          summary ="Bit val            u  es   an     d             bytes"> 
 *     <t   r >
 *       <th   co   lspan="9"><sp       an styl  e=     "font-weight:n    ormal">
 *             All     cha  racters in     the   ra    nge {@co    de '\u005Cu0001'} to
 *                       { @code '\u005Cu007F'} are represen  te  d   by a single    byte:</spa   n></th  >
 *        </    tr>
       *       <tr  > 
 *       <td></td  > 
   *        <th   colspan="  8" id="bit_a">    B  it Va lues</     th >
 *        </tr>   
    *       <tr>
   *       <th i    d="byte1_        a">B   yte 1    </th>
 *                <td><cente   r>0</center>
 *       <td colspan="7 "><  ce         nter>b   its    6-0<      /center>
 *     </    tr    >
      *        <tr>
 *       <       th colspan="9"><span s t     y   le    =    "font-wei     ght:normal">
 *                                The null character {@code '\u005C           u0000'}   and ch  a                             racters
 *            in    the range {@cod   e '\u005C  u0080'} to {@code '\u005Cu07F    F'} are    
    *                re  pr     e     sented by a p    air    of by        tes: </span></th  >
 *       </tr>
 *                  <tr>
 *       <td></td>
     *       <th     c olspan="8"         id="bit        _b">Bit Values</th>
     *         </tr>
 *        <  tr>
 *           <th i d="b  yte1_    b">     Byte 1</th>   
 *               < td><center>   1</cen      ter>
      *       <td><ce        nter    >1</    cente   r>
 *                         <td>  <center>0</         center>
 *        <td colspan="5"><cent  er>bit    s 10-6</c      enter>
 *     </tr>
 *     <tr>
 *               <th          id="by   te2_a">Byte 2</th>
 *          <t      d><center>        1</center      >
 *          <td>   < cente     r>0</center>
   *          <td colspan="6"><  center>b   its 5   - 0</center>
 *            </tr>  
 *            <tr>
 *         <th co    ls    pa    n ="9"><span  style= "font -we     ight:normal">
      *         {@c  ode    char} values in th                e   range {@code    '\u005C  u0    800'}
 *         to {@code '    \u005CuFFFF'}          are repre    sen        ted by three by    tes:</span></th>
       *     </tr>
 *     <tr>
  *               <td></   td    >
 *        <t    h colspan="         8"id="bit_c"   >Bi    t Values    <   /    th>
 *     </  tr >
 *          <tr>
 *       <th id="byte1_c ">Byt    e 1</th>
 *       <       td >     <cente      r>1</c    e n     ter>
 *       <td><ce   nt    er>1</ center>
     *       <td><cente          r>1</center>
 *                   <td><c                   ente        r>0</center>
 *       <t    d            colspan="4"    ><cen    te     r>bits 1     5-12</center>
 *         </tr> 
 *     <t r>
 *       <            th id    =      "b       yte2_b">Byte 2</th>
 *                <td >     <    cen  ter>1<     /center>
 *                     <td     ><center>0</center>
    *                  <t         d col    span       ="6"><center>bits 11-6        </center      >
 *         </tr>
 *     <  t  r>   
 *             <th id=               "    byte3">Byte 3</th>
 *       <td><center>1</center>
 *           <td><center>0</center>   
 *            <td colspan="6   "><  center>b    its 5-0</center>   
 *     </t   r>
 *   </table>
 * </b lockq u        ote>
 * <p  >
 *  The differ ences      between this format and the
  * standard UTF-8 format are the follo    wing:
 * <ul>  
 * <li>The null      b   yte  {@code '  \   u 005Cu0000'} is encoded in 2-byte form   at
 *       rather t  han     1-byte, so that the encod   ed strings never have
      *       embedde         d nulls.
 * <l   i>   Only the 1-byte,      2-byte,    a    nd 3-by         te formats    are used.
      * <l  i       >   <a href="..    /lang/Char  acter.html#unicode">Su  pplementary c   haracters</a>
   *      are repre  sent ed       in  the fo   rm of    s urrogate pairs.   
 * </ul>
 * @author  F     rank          Ye  ll    in
 *                  @  see     java.io.DataInputS   tream 
 *   @see        java.   io.D   ataOut  put
 *       @since        JDK1  .0
 */
       public    
interface Data   Inpu   t {
           /**
     * Reads some bytes fro        m an input
     * stream and stor    es          t         hem into the  buffer
     * array {@code        b}  . The n   umber of b     ytes
             * re   ad is equal
     *         to th     e length of   {@code b}     .
         * <p >
                  * This metho   d bloc    ks until     one of    the
     * fol  lo     wing condition s occurs:
     *   <ul>
        * <l i>   {@cod  e b     .l     en                           gth}
     * byt    es of input data   ar   e    av           ailabl   e  ,    i     n which
     * case a      normal  r    eturn is    made     .
             *    
     * <li>End of
       * f ile is de te cted, in w h   ic         h case            an    {@c   ode EOFEx        c          eption}
             * i     s  thrown.
     *
         * <li>      An I/O error occur      s,    in
     *          wh      ich    case an {@code IOExcepti   on  } ot    her
     * than {@code EOFException} is thro  wn.  
     * </ul     >   
     *  <p>
     * If {@code   b} is {@code null},
             * a                   {@code            Null   PointerEx   ception} is thr   own.
       * If     {@code      b.le  ngth}    is     zer  o, th   en      
        * no bytes are read. O  therwise, the   first
          * byte read is store                   d        in  to   el    ement  {@    cod   e        b[0]},
     * the next  one into {@code b[1]},   and
           *   so on         .
     * If an       e   xception i   s thrown from
             * t    h       is      me   th od,       then it ma y be   tha    t some bu t
        * not all   bytes o  f {@co    de b} have be     en
     * updated with  data from t he              inp     ut stream.
            *
     * @param       b   the buffer into wh  ich the data is rea    d.
                * @excep      t   i      o n  EOFExc   ep     tion   if this stream r     eaches the end      before reading
          *                            all the     b    yte   s.   
              * @exceptio       n       IOException   if      an I/O error occur    s.
                */  
     void   re   adFull   y(byte b[]) thro ws         IOException;  

    /**                
     *     
     *       Reads {@ c ode len}
       * byte          s  from
     * an input   stream.    
          *  <          p>
        * This method
             *           blocks until     o         ne  of the fo      llowing   c         onditions
     * oc  curs  :
      *        <ul>
     * <l   i>    {@code          len} byte s
     * of in                           put dat   a are a        vailable, in which ca    se   
     * a          norma    l return is made    .
      *
        * <li>E   nd of file
           * is detected , i  n whic   h case a   n      {@code      EOFException}
             * i   s thrown.
            *    
     * <   li>An I/O e   rror occurs, in
     * which   c   ase an     {@c  o     de             IOExce ption} other
          *        than {@code EOFExcep     ti o   n} i s thrown.
                 * </ul>
     * <p>  
       * I f {@code b }          is {@code null},
     *  a    {@co   de      Nu  llPointerException} is thrown.
                       * If    {  @code  off} is negat     ive, or {@co         de len      }   
     * is negati ve,   o r {@code off+len}    is
      *    g  r   eater      than the length of   the    array   {@   code b  },
     * t  hen            an {@co de IndexOutOfB   oundsException   }
          *   is                  thrown                .
     * If {@code le    n} is zero,
                        *        th     en no bytes are read. Otherwise,      the f  ir st
        * byte     read     is  store  d    into element {@c           o   de     b[off]},
     *               the   next o                ne in     to   {@code b[off+1]},
          * an  d    so    o     n. The number of       b    ytes       read is         ,
     * at  most     ,   eq ual to {@      code le  n}.
     *
      * @  para     m     b      the          buffer      into which    the data is read.
     * @ param    off  an          int sp   ecifying th e offset into     the data.
      * @param len  an int sp  e cifyi          ng th  e n umbe     r of b  ytes to read   .
     * @exception  E                 O    FExcepti  on  if t   his       stream reach   es the end before re           ad  i           ng
        *               all        th     e bytes.
        * @ex  ception  IO        Ex   c      ept ion   if an I/O erro      r occurs.
                      */ 
    void readFully(byt  e b[], i nt o                    ff, int len)      throws IOE    xcept    ion;

    /**
      * Makes an     attempt to     skip over
            * {@co   de n} byte   s
        * of dat    a from th  e     inpu t
     * stre      am, dis      car    ding the skipped bytes. However,
                  * i t may s kip
      * ov        e         r       some sma     ller numbe    r o      f
     * b  ytes, pos        sibly zero. This    m   a     y re  sult from
     * a  ny of a
     * number of             conditions; r          e  achin  g
           * end of f    il e be   fore {@cod  e         n}        b ytes
                  * have been s   kipped is   
       * o    nly o   ne possibili     ty.
          * This      method          ne     ver throws an {@co   de EOFException}.
      * Th   e     act ual
     * nu   mber of    bytes skipped i s returned.
     *
              * @param      n    the   n      umber of bytes to be    s     kippe     d.
     * @return          th    e numb            er of bytes actually  sk    i  pped.
     * @exception       IO       Exception   if    an  I     /O erro  r     occ urs.
          */ 
         int skipBytes(int n) throws I     OExce      pt     ion;

         /*         *
            * Reads one in    put        byte and retu      rns
       * {     @code true}   if that byte is   nonz   e    ro    ,
        *      {@code false}              if that byt     e i     s zero.
     * This    m     et    hod is suitable for  reading
        * the byte    written by the   {@code writeB          oolean}
          *       meth    od   o     f int   erface {@code         DataOutput}.
     *
     * @re tu rn           the {       @co       de boolean}  valu e rea        d.
               * @exception     EOFE       xception  if t     his stream reaches           th e en    d b  efore read      ing
        *                          all the bytes.
         * @e   xc  eption  IOExcepti                   on   if a  n      I/ O erro       r occurs.
                          */
              boolean re adB   oolean() throws IOException;

                /**
       * Rea  ds and r  eturns one           inpu    t byte  .
     *   T    he byt   e is tr        eated        as a signed valu  e  in
     *       the range {@cod  e        -128}    through {@code 127},
     * inclusive.
         *              This method is   suitable for
     * reading     the byte         written    by the {@cod  e writeByte}   
         * method of interface {@c  ode Da   t             aO      utput  }.
          *
     * @return     the 8-    bit v        alue read.
        *  @exce        ption  EOFExcepti  o                 n  if this   stre  am reaches th       e end    befor       e    reading
     *                          a      ll the bytes.
      * @except  ion  IOEx  ce     ption     if an I/O error occ     urs.
            */          
    b     yte readByte() throws IOExceptio  n;

    /**        
     * R      ead s            one   i nput b  yte    , z   e      r   o -extends
      *   it to t y  pe {@   code i          n      t}  , a     nd ret   urns 
          * t      he result,           wh     i   ch is there f      or   e in the range
     * {@code      0}
     *    through {@code 2                          55}.    
     *         This me       thod is s   uita ble  fo     r reading
                 * the byte written by the {@code writeByte}
          *    method of interface {@code DataOutput}
          * if t     he argument to {@     code writeByt   e}
       *        wa s      i           nten  ded       to be a value in th      e range
     * {@    code 0  } th r    o           ugh {@code 255   }.
     *
        *       @ret       urn     the    u       nsigned 8-bit v alue re  ad.
     * @exception  EO    FExcept ion  if t   his   stream r      eaches the end be   fore readin      g
             *                           all the bytes.              
           * @exc  eption  IOException   if an I/O error o  ccurs.
          */
    int         readUnsign  edByte() throw s       IOException;

    /**
          *   Rea   ds two inp       u          t  b    ytes an    d re   turns
      * a  {@c       ode short} value. Let {@cod   e     a}
     * be the first by        te read and    {  @code b}
                 *   be the second          byte.   The value
     * returned
     * is:
     *     <pre>{@code     (short)(   (a <  < 8) |    (   b &   0xff))
     * }<   /pre>   
     *        This   met hod
                 * i s su      itable for reading the        bytes w rit                     ten
         * by the    {@code writeShort} m      ethod of 
     *  i    nterfac      e {@c  od    e Da       t   aOu    tput}.
     *
        * @return          the 16-b     it v  a            lue      read.
          *  @exc   e    ption      EOFExcept  ion  if this st  ream reaches the end b     efore re ading
      *                       all the    by    tes.
     * @exception  IOExcept  ion       if an I/O er    r or        occurs.   
     */
       s  hort   readShor   t() t   hrows IOEx    c   eption;

    /     **
           *   Reads two input bytes and       ret           urn   s
       * an {  @c   o    de int}      val   ue in th e rang       e {@code 0}
      *    through        {@c ode 65535}. Let  {@code a}
     * be the firs   t byte read and
       * {@code b   }
     * be the secon  d byt                       e. Th    e value     re     tur          ned    i    s:
      * <p re>  {@c     od       e (((   a & 0xff) << 8) | (b & 0xff))
        * }</pre>
             * T  his met hod is suita     ble for r    eadi   ng the byt  es
     * wr    itten               by the {@code writeShort} m         ethod
     * o   f               interface {@cod  e DataOutput}  if
                   *   the argument to {@code writ eShort}
       * was intended to be a value in the ra    ng  e
     * {   @code 0} t  hrough {@code 65535}.
     *
         * @ret    ur       n     the unsigned 16    -bit    va         lu       e re       ad  .   
           *                             @excepti  on  EOFException  i  f    this                            stream   reaches the end bef   or  e reading
     *                            a        ll the bytes       .
     * @exception  IOException        if an I/O error      o     ccurs.
     */   
    int readUnsignedShort()    th         rows IO     E    x   ception;

    /    **
     * Re   ads tw   o input by    tes    and re t urns a       {@code c  har   } value.
     * Let    {@code a}
      * be the first byte read and {@code b    }
     * be             t     he secon       d byte. The val   ue
                     * r        etur         ned is:
     * <pre>{@code (ch  ar)((a << 8) | (b & 0xf  f   ))
     *    }</pre>
                    * T    h  is metho   d
                   *    is  su   itable for      re   ad  i ng b ytes   written by
         * the {@ code writeC    har}    m         ethod of interface
     * {@code Dat a        Output}.
     *
     * @retur        n            the {@code ch             ar} value re     ad.
            * @     exc eption       EOFException  i    f this      st     ream r    eaches the end bef           ore readin         g
      *                  all    t h e bytes .
         * @ex ception     IOException   i  f an I/O     e   rr      or occurs.
                   */
             c h   ar       re      adChar() throws IOEx     ception;

         /**
                  * Reads four         input bytes a  nd r  et   urns an
     * {    @code int} value. Le      t     {@code a       -d }
     * be  th  e first throu         gh   fourth by    te     s rea   d. T     he val   ue       re   tur      ned is:        
     *            <pre>{@code
     * (((a       & 0xff) << 24) | ((b     & 0xff)    << 16)     |
     *  ((c &       0xff) <<  8   ) |           (   d    & 0xf  f))
         * }</p      re >
           * This me   tho d is suitabl           e
     *         fo    r read    ing   bytes written by the {@code wr         iteInt}
     * metho  d of      in   terface {@co    de Data  O    u    tput}  .
     *
     *                @return     the     {@code int}    val  ue read.
     * @exception          EOFE   xception  if thi  s st      ream reaches                  th   e en   d   be fore reading  
         *                    all the    bytes.
     * @e     xce ption  IOExc   eptio        n   i     f an I/ O error o    ccurs.
       */
    int r     eadInt()               throw  s I          OException   ;

     /**
     * Reads eight input b ytes and return   s
     * a {@c ode long} val       ue.  L et {@code     a-h}
         * be            th  e first  t  hrough eighth by    tes read.
             * The value r   eturn   ed is  :
     * <pre>{@co   de
       *      (((long)(a   &  0xff)  << 56) |     
         *  ((long)(b & 0xff) <<                 48) |
     *         (  (lo   ng)(c & 0xff) << 40)      |
              *  ((lon g)(d & 0xff                  ) << 32  ) |  
     *  ((l   ong) (e &     0xff)     << 24)   |
     *  ((long)(         f & 0xff) << 16) |
      *      ((long)       (g        & 0xff)   <<  8   ) |
     *  ((lo        ng)    (h    & 0xf       f          ) ))
       * }</pre>
            *  <p>
                 * This   method is suit           able
     *  fo         r rea   ding b    ytes written by the {@c  od   e writeLo ng}
     * m et     hod   of inter  f        ace {@co  de Data    Output}.
        * 
        * @re  turn          the   {@co   de long     } value rea d. 
       * @exception  EOFExc            eption  if thi            s stream reaches the en          d   before      reading
        *                                         all the byt   es. 
     *  @ex    ce   ption  IOException   i    f       an I/O   error occu          r  s.
      *  /
    long readLong ()   throws IO       Exception;

        /**
                *  R       eads f     our input bytes and returns
     *   a {@co  de float} v  alu          e. It does this  
       * by firs    t constru   ctin    g        a  n {@    code in   t  }
     * value in exactly  the         manner
     * of the {@code readInt}
            * m     ethod, the    n converting this {@cod    e int}
     * value to a {@code floa t} in
     *  exact    l   y the mann   er o             f the method {@cod  e Float     .intBitsTo Float}.
           *    This meth   od is suit   able for readin     g
        * bytes w     ritten by         the {    @ cod     e w   riteFloat}
            * metho     d    of interface {@code DataOutput}.
     *
          * @return      the {@c    ode float} value read.
     * @exceptio n      EOFException  if this stream reaches the end befo   re reading     
      *                           all the byte    s.         
       * @exc  eption          IOExceptio   n   if an I/           O error    occ urs.
     */
          flo a  t readFloat() throws IOExceptio n;

         /**
     * Reads eight in  put bytes and          re      turns
                      *  a {@co   d  e double} value. It          does this
      * by first c   onstructing     a {@code long}
             *  value in exac  tly the ma    nner
     * of the  {@code readLong}
     *     m ethod, th   en     converting this {@c ode      l    ong}
       * valu  e to     a     {@code     doubl    e} in exact    ly
     * th  e m  a          nner o f the metho            d     {@code D oubl e.longBi      tsToDouble}.
             *    This method is   suit  able for reading
     *     bytes written by the {@code wr  i     teDo  uble}
       * m  ethod of interface {@code DataO    ut  put}.
     *
     * @return     the           {@code do uble} value read.
     * @exception  EOFExcepti     on   if t          his stream rea ches the end before   re    ading
     *                      all t      he    bytes.
                     * @     excep   tio   n  IOExcepti      o    n   if an      I/O error occu    r    s.
       */
    double readDouble()     throws IOExcept      ion;

      /**
     * Reads the next line of tex t f    rom the input stream.
           * It rea  d  s succ   essive   byt  es, conv  erting
       * eac             h byte s    eparate  ly    into a        character,
         * until             it e   ncounters a      line t   e         rminator o      r
     * end of
     *  file     ;  the characters re     ad are th  en
      *       retur    ned   as a {@code   Stri ng}. Note
       * that because this
       * method processes bytes,
     * it does not support input      of    the full Unico      de
     * char      act  er set.   
              *     <p>
                         *         If end of file is enco  untered
      * be   fore   even one by    te can be read, then {@code    n  ull}
     *   is returned. Otherwise, each byte     that     is
     * re  a       d is    converted to type {@code c       har} 
     * by z   ero-extensio   n. If th e   character {@code '\n'}
     * is encountered, it is discarded and reading
     * ceases    . If the char  acter {@code '  \r'}
     * is e   n      cou   ntered, it      is discarded a  nd,     if    
     * the following byte conver           ts &#32;to th e
     * characte  r     {@code '\n'}, then that is
     * discar d      ed a    lso; re    ading then c     eases. If
         * end of file is encounte   red before either
     * of the characte        rs {@c            ode   '\n'} a    nd
     * {  @code '\r'} is enc  ountered, reading
      * ceases . Once reading has ceas    ed, a {@c   ode String }
     *   is returned that contains a  ll    the char  acters
     * r  ead and not discard ed, taken i    n order.
     * Note that every c    haracter in this string
     * will have a v alue less  tha   n {@code \u005Cu010 0},
     * that is, {@code (char)256   }.
     *
         * @r     eturn the next line      of            text fro     m the   input stream,
     *         or         {@code null} if the end of file is
     *         encountered befo          re    a byte can be r  ead.
     * @ex   ception  IOException  if an I/O erro    r     occurs.
        */
        String readLine() throws IOException;

    /**
     *    Reads in a string that has been enco   de  d using a
            * <a href="#modified-utf-8   ">modified    UTF   -8    </a>
     * format.
     * The general co ntrac t of {@code     rea    dUTF}
     * is that i    t r  eads a represent  a tion of a Unicode
      * c  haracter string en           coded in modified
     * UTF- 8 format; this  string of   char  acters
           * is then retu        rned as a {@code String}.
     * <p>
     * First, two bytes are read       and used t o
            * construct an un signed     16-bit integer in
     * exact       ly the manner of the {@code readUnsignedShort}
     *    method . This integer value is called the
     *    <i>UTF length</i> and specifies  t  he number
     * of additiona    l bytes to    be read.   These bytes
     * ar    e then converted to characters by cons  idering
     * them in groups. The length of eac  h g     roup
     * i   s computed from t he value of the  firs   t
        * byte of the     group. The byte follow     ing a
     * gr oup, if any, is the first b    y    te of the
     * next  group.
     * <p>
     * If the first byte o    f a group
     * match  es the bit    pattern {@code 0xxxxxxx}
     * (where {@co     de x} means "may     be {@code 0}
     * or {@code  1}"), then the group consists
        * of jus       t that byte. The byte is     zero-extended
     * to form a character.
     * <p>
     *      If the first byte
     * of a gr     oup    matches   the bit pattern {@code 110xx    xxx}   ,
     * then the  group consists   of that byte {@cod e a}
     * and a second byte {@code b}. If there
     * is no byte     {@code b} (because byte
     * {@code a} was the last of   the bytes
     * to be read), or if byte {@code b} does
     * not match the bit pattern {   @code 10xxxxxx},
     * t    hen a {@code UTFDataFormatException}
     * is thrown. Otherwise, the group is converte  d
         * to the character:
     * <pre>{@code (char)(((a & 0x1F) << 6) | (b & 0x3F))
        * }</pre>
     * If the first byte of a group
     * matches the bit pattern {@code 1 110xxxx},
     * then the     group consists   of that byte {@c   ode a}
     * and two more bytes {@code b} and {@code c}.
          * If       there is no byte {@code c} (because
          * byte {@code a}     was one of the l   ast
     * t   wo of t   he bytes to be read), or either
     * byte {@code b} or b    yte {@code     c}
     * does not match the bit pattern {@code 10xxxxxx},
     * then    a {@code UTFDataFormatException }
     * is thrown      . Otherwise, the group is converted
     * to the character:
      * <pre>{@code
     * (char)(((a     & 0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F))
     * }</pr   e>
     *      If    the first byte of a g   roup matches the   
     * pattern {@code 1111xxxx} or the pattern
     * {@c   ode 10xxxxxx}, then a {@code UTFDataFormatException}
     * is thrown.
     * <p>
     * If end of file is encountered
     * at any time during this entire process,
     * then an {@code EOFException} is thr    own.
     * <p>
     * After every group has been converted to
     * a character by t  his process, the characters
     * are gathered, in the sam e order in which    
     * their corresponding groups were read from
     * the input stream, to form a {@code String},
     * which is retu rned.
     * <p>
     * The {@code writeUTF}
     * method of interface {@code DataOutput}  
     * may be used to write data that is suitable
     * for reading by this method.
     * @return     a Unicode string.
     * @exception  EOFException            if this stream reaches the end
     *               before reading all the bytes.
     * @exception  IOException             if an I/O error occurs.
     * @exception  UTFDataFormatException  if the bytes do not represent a
     *                 valid modified UTF-8 encoding of a string.
     */
    String readUTF() throws IOException;
}
