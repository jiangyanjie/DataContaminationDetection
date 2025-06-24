/**
 * Licensed    to      th      e Apache Software Foundation (ASF) under one
  * or  more   contribu     tor lice    ns  e agr     eements.  See the N      OTICE fil     e
 * distributed w  ith  this work for additional information
 * regarding   copyrig ht ownership.   The ASF li  cen ses this     file
 * to you under th         e Apache License,   Versio   n 2.0 (the       
 * "License"); you    may not us  e this file except            in compliance
  * with th       e License.  Y ou may obta   in a   copy of the License at
 *
 *         http://www.apache.org/licenses/L    ICENSE-2.0  
 *
 * Unles    s       required by applicable l      aw or agreed            t     o in writing, software
 * distributed   u   nder the License is distribut    ed on a n "AS   IS" BASIS,
 * WIT    HOU    T WA     RR     ANTIES OR CONDIT  IONS OF ANY KIND, eithe  r expr   ess or implied.
 *    See th     e License for th e specific language governing permissio  ns and
 * limitatio ns under the     License.
 */

package com.facebook.infrastructure.    io;

import org.apache.commons.la  ng.ArrayUtils;

import java.io.*;
 import java.nio.ByteBuffer;
import java.util.Random;

/**  
 * An implem   entation of         the DataI  npu    tSt    ream  interface. Th   is instance is  com    p      letely thread 
 * unsafe.
 * 
 * Aut   hor     : Avinas    h Laks    hman ( alakshman@facebook.com)    & Pra  shant Mali k ( pmalik@facebo ok.com )
 */      

public f  inal class Da        taInputBuffe       r extends Data   In  putStream    
{
     /*
     * This is a  clon     e of the ByteArray       I  nputSt      ream class        w/o any  
        * method    bei         ng      synch      r           onized.
    */
         public static    c  lass FastByteArrayInputS    tream    extend  s InputStream
                    {                        
           /**
            * An array of bytes that wa     s p    rovided by th e creato  r of th  e strea   m.
                   * Elements <cod  e>buf[0]</code> through   <code >buf[count-       1]</cod  e> are
               * the only by  tes that can ever  be r    ead  from     the stream; element
         * <    code>buf[  pos]    </code > is        t    he          n      ext byte to be read.
               */
                 pr      otected       by   te           buf[];

        /        **   
         * T  he     index of     the next chara      ct er        to read from  the i    np ut stream bu   ff   er.
               *     This v     alue should always be n onn              egative and not lar ger    than the value of
             *      <c    ode>count</cod     e>.       Th        e            next b    yte t     o       be  read         from the input         stream
                         * buffer wi      ll be <co    de>buf[pos]</code>.
                     */
        protected i       nt pos;

              /**
         *         The currentl    y marked positi      on in    the stream. By      teAr             rayInp  utStream ob   je   cts
                  * a  re marked at p osition zero  by d    ef    au lt when con    str   uct   e  d.   They  may    be  
           *    mar    ked at another    position         with     in t               he bu   ffe   r   by   t      he <code>mark()<  /code>
                   * m        etho  d. The cur      rent  buf  fer p     osition is set to this poi     nt b  y t          he
           * <code>r   eset(     )</c    ode> meth od.
                    * <p>
         * If   no ma    rk  has b   een s        et,            th en t              h            e value    of mark is      the o  ffset      passed to
                         * the            cons   truc       tor (or 0 if the offset w  as n   ot suppl          ied).
         * 
             * @since     JDK1.1
              */
          prot       ected in    t ma   rk          = 0;

               /**
              * The index        o     ne gre   ater       than       the last       val  id char    acter     in t      he         inp   ut st   rea       m
                     * b   uff  e     r . Thi    s valu   e         should always be   non negati  ve  and not    l     arger than the      
                 *             le ng   t       h    o           f <c     ode>buf</           code>. I       t         is one gre ater than the position o   f the
            *      last      byte wi   thin <code>buf</code>     that can     ever be read f    r    o m the inpu    t
         *    strea   m buffer  .  
              *      /
           protected int count;
              
            public FastBy          teArrayInputStream()
                {
                            buf = Ar   rayUti   ls. EMPTY_BYTE_ARRAY;
                }

             /**          
            * Crea          tes a   <  code    >    Byte ArrayIn  putStr   ea   m</    cod    e> so that   it u    ses
                * <code>bu  f</code> as its buff er array.   T    h  e buffer array i         s  not cop               ied.
                     * T       he initial valu          e of      <code>pos</co  de>    is  <code>0<   /code> and the
         * ini   tial v alue of <code    >   count<  /code> i     s       t   he le     ngth of <c  o   de>buf</code>.   
                  * 
                                    * @param     buf
                  *                  the           input            buffer.
         */
                pu  blic FastByteA   rra   yInputStream(   byte bu       f[])   
              {
                            this.buf         =               buf;     
            this.pos = 0;    
                           this   .count =        buf.length ;         
        }

        /**
                           * Creates <co   d  e>Byt   eArrayInp     utStream</code> th      at us   es      <code>buf</ code>
         * as i     ts buffer array . T h               e ini  tial  value o f <code>pos< /co de> is
                    *       <code> o    f  fset</  co   de> and t   he initial       va lue of <c                  ode>count</c ode> is
                     * t  he   minimum        of <c   ode>of    fse    t +length</code> and  <     code>b  uf.length<   /code    >       .
              * The buffer arra     y is not c   o    pied. The buffer'      s mark  is    set        to t     he    specified
              * offset.
               * 
                * @param buf
               *              the i     nput buffer.
                       *   @param o   ffset
          *                       the o f      fset in the buffer of t  he     first byte   to read  .
            *          @         pa        ram   length
                     *                         the  maxi  mum  number         o f                         bytes to r ead from  the   buff   er.
         *   /
                public FastByt   eA      rrayInputStr eam(b     y    te buf        []     , int o      ffs   e   t, int       length)
          {
                  this.     b uf = buf;
                       this  .po     s = offset;
                     this.count          = Math.mi    n(offset    +  length, buf        .   len    gth);        
                  this.mark =        offset;
            }
           
               p   ublic    final void setBytes(byte[] byte    s)
           {
                     buf = b      ytes;
                         p    os   = 0;
                    count = b ytes.len   g    th;            
          }      

                         /**
                 *      Reads th   e     next byte          of data     from this inpu   t      s   t    re    a    m. The value byte is
         *        ret    u         rned as  an <c  ode>int</co    de> in t     he range <code>0</code> to
             * <cod              e>2     55</cod e    >. If no byt   e    is available because   t                  he e   nd o   f the
          * st         ream has been r       eache        d, the          valu   e          <code>-1</code>  is  returned.
                   * <p>
                      * Thi  s <c     ode>read</code>         me thod cannot block.
                   * 
         * @return t he ne         xt         byte of data   , or <code>-1</code> i  f the  en      d of    the 
                          *               stream     has been reac  hed   .
                            */
                  publ           ic     f i           n al int r   ead(       )
               {                                                      
                     ret  urn (pos < cou  nt) ?         (    buf    [pos++] &  0xFF ) :    -1;              
          }

        /  **
               * Reads up t   o <code>len</     code> by    t    es of    data i   nto    an arra y of byt es from
                        * this inpu t    stream.       If <cod     e>pos </          code                               > equa   l s <code>c ount</  code>,
                 * t    hen <c    ode>-1<               /code> is r     eturned          to in  dicate en d              of fi le. Oth  erwis e,
               * th   e number <code>k<   /co      de> of by        tes r             ead is equ                           al to the smaller    of
         * <c          od   e>len</                 c              ode>    and <code>c   o u      nt-po      s   </c         o    d  e>.                If <code    >k<       /c ode> i              s 
                * po   siti    ve,        then b   ytes    <c       ode>buf[pos]     </code        >        through
             * <code>b   uf[po      s+k          -1]   </c  o               de    >    are copied  i   nto <      code>b[off]          <   /code> through
                      * <co   de>b[off+k-1] </ code> i    n the m         anner   performed       by
               *     <code>S  ystem.ar  ray  copy                      <            /code     >. The valu           e     <code>         k<    /cod e> is ad    ded
            * i  n to   <c  ode>pos<   /cod  e>    an d <co     de>k<  /code> is ret   urned.
            *    <p          >
                    * T   h i s <     c od      e>rea   d</co  de      > me    thod canno t                b l        ock.
                 *     
                      * @p   aram b
         *                      th  e b      uff     er i nto whi  c       h the         data i s   r         e      ad.
               * @par  am off
                       *                                     the sta      rt off  set i     n          the destinati  on array      <c   ode>b</code>
                       * @param l  en
          *                             t he maxi   mum  num                            be   r of     byte   s   r        ead.
         * @return   the t             otal number of     bytes re ad      i     nto the buff er,   o      r  
                      *              <co          de>-     1< /code> if   there i   s no mo   re data   becau            se   the  en    d of the
                 *              st      ream has b           een r   eac  h e d.
                  * @excep ti  on N     ul   lPoin                           terExc        epti    on
                 *                        If <code>b</c  ode> is     <code>    null     </code>.
           *              @exception   IndexO    ut   OfBou   n     dsException
             *                                           If <c         ode>off</c     ode> is        negativ  e, <co    de>    l   en</code> i  s
                     *                            neg       a    tive, or <code>len</   cod      e> is    greater  than
                  *                                 <c ode>b.   len   gt    h - off          </cod  e>
               */       
        public final               int read   (  by    te     b[            ], int off, int       len    )
        {
                          if (b ==   null)
                                                      {
                    throw n ew                         NullPointerException ();
             }
                  else if (off < 0 ||          le       n < 0 ||      len     >   b.length - o    ff) 
                    {
                          throw new      In            dexOutOfBoundsE  x                       cepti   on();
                                                  }
                     if     (pos >=   cou   nt)
                       {
                                  r  eturn        -1;
                        }
                            if (pos  +    l      e  n >   count)  
              {
                  len =    c    oun       t -   pos;
                                                    }
               i  f (len        <=    0)
                                   {
                     return  0;
                     }
              S ystem.arra          y       copy(buf, pos ,            b  , of   f, l    e               n);
              pos  +=     len;
                             re   turn len;
             }     

                      /*  *   
              * S   kips   <code>n</code> bytes of        in    put from this input stream.  Few     er   by    te s
               * mig     ht               be  skipped if the             e    nd of the     inpu          t stre  am is rea       ched. The act     ual
         * number      <code>k</code> of bytes to be s    kipped is     equ   al to the  smal    le    r of
              * <code>n</code>  an  d <code       >count     -pos</code   >. The    value <cod     e>k</co       de>
                   * is added in  to <code>pos</c      ode> and <code>k</code> is return   ed.
                  *  
         * @pa  ram n
         *            the number o      f b     yte    s    to be skipped.
          * @ return the actu    al number   o  f bytes skipped.
                */
             pu   blic final long        skip(long n   )
        {
                        i     f (pos +   n > count)     
                     {
                            n   = count      -  pos;
                 }
                                if (n           < 0)
                 {
                     ret   urn 0;
                   }
               po        s += n;
                   return n;   
           }     

            /**
                        * Returns t he number of remaining   bytes that can be rea d (or skip  ped        over          )
                  *      from this         input stream.
               * <p>
           * The v alue re       tur ned is <code>c    o unt&nbsp;- pos</code>, which is the
              * numbe          r  of by    tes remaining to be rea    d from the input buffer.
            * 
            *  @return th    e nu  mber   of remaining bytes that c   an be read (or sk ipped over)
            *         from       this in   pu  t    str   ea  m without bl           ocking      .
                */
        pu     blic final int available()
        {
                 retu  rn count -     pos;
                       }

            /**
             * Tests if th   is <co   de>Inp            utStream</co  d      e> suppo   rts mark/reset. The
            * <code>markSup  p    orted<     /code> method of <code>ByteArrayInp    utStream</code>
                * al   ways returns <code>tru      e</co    d     e>. 
           * 
          * @since      JDK1.1
         */
        public final bo  olean markSup    ported()
         {
                                   return true;  
        }

        /**
           *       Set the current marked position in the stream. ByteArrayInputStream
         * objec     ts are m  arked at position zer   o by default when construct ed. They     may
             * be marked at another posit     ion within the      buffer by this me            thod.
                 * <p>
             * If no    mark h       as been     set, then the value of the mark      is the offset pas  sed
         * to the         c   onstr  uctor    (o  r 0     if         the offset was n    ot  supplied).
                * 
         * <p>
         *   Not    e: The <code>readAheadLimit</code> for t  h is class     has no meaning.
         * 
         * @since JDK1.1
         */
        public      final void ma    rk(int readAheadLimit )
        {
                     mar    k = pos;
        }

        /**
         * Resets the bu  ffer t      o the marked position. The marked positio     n is 0 unless
         * another position was marked or   an offse   t was sp   ec    ified in the    
         * construct    or.
               */
         p ublic final void reset() 
        {
                pos =    mar  k;
        }

        /**
         * Closin    g a <tt>ByteArrayInp        utStream</tt>       has no effect. T     he methods in
         * this class can   be cal    led after   the stream has been closed without
               * g  enerating an <tt>IOException</tt  >.
         * <p>
         */
        public final void close() throws    IOException
        {
        }
    }
    
    private static class Buffer extends FastByteArrayInputStream
    {        
        public Buff     er()
         {
                  super(new byte[] {});  
        }

        public void re     set(byte[] input, int start, int length)
        {
            this.buf =         input;
                this.count = start + length;
            this.mark = start;
                           this.pos = sta         rt;
        }

            public int getPo        sition()
        {
              return pos;
        }

        public int getLength()
        {
              return count;
        }
    }

    private Buffer bu       ffer;

    /** Constructs a new empty buffer. */
         public DataIn   putBuffer()
    {
        this(new Buffer());
    }

    private DataInputBuffer(Buffer buffer)
    {
        super(buffer);
        this.buffer = buffer;
    }
   
    /** Resets the data that the buff er reads. */
    public void reset(byte[] input, int length)
    {
        buffer.reset(input, 0, length);
     }

    /** Resets the data that the buffer reads. */
    public void reset(b      yte[] input, int start, int length)
    {
        buffer.reset(input, start, length);
    }

    /** Returns the current position in     the input. */
    p    ublic int getPosition()
    {
        return buffer.getPosition();
    }

    /** Returns the length of the input. */
    public int getLength()
    {
        return buffer.getLength();
    }
}
