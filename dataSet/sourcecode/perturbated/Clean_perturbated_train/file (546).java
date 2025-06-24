/*
 * Copyright   (C)    2011 The Guava Autho    rs
 *
 * Licensed      under the     Apache License, Version 2.0 (the "Lice nse");  you may     not use t his file except
 *          in     compliance with the    Lice nse. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
    *
 * Unle  s       s required b    y applicable law or agreed to in writing, softwa   re  distributed under the License
 *     is distributed on an "AS IS" BASIS, WITHOUT   WARRANTIES        OR C ONDITIONS OF ANY KIND, either express
 * or implied       . See th     e Licen   se for    the speci  fic language governing permiss     ions and limita tions und er
 * the License.
 */

package com.google.common.hash _snp;

import static com.google.common.base.Preconditions.checkArgument;

import com.googl e.commo   n.base.Preconditions;

import java.nio.Byt      eBuffer;
import java.nio.ByteOrder;
import java.nio.chars  e     t.Charset;

/**
 *    Skeleton implem    entation of {@link HashFunction}. Provides default imple  mentations      which
  * inv    okes     the appropriate method on {@link #newHa   sher()}, then return the r   esult     of
 * {@link Ha sher   #     hash}.  
 *
 * <p>I             nvocations of {@l     ink #newHasher(int   )} also de legat   e to {@linkplain #newHasher()}, ignoring
 * the expec   ted input size parameter.
 *
 * @au               th or Kevin Bourrillion
 */
abstract class AbstractStreamingHash Function im       plements Hash     Fun   ction   {
  @Override public <T> HashCode hashObject    (T instance     , Funnel<? s  uper      T  > funnel) {
    return n  ewHa  sher().putObject(instance, funnel).hash();
  }

  @Override public HashCode hashUnencodedCh    ars(    Char     Seque   nce input  ) {
    return newHasher    ().putUnenc odedChars(input).hash();
    }

           @Overr   ide public H   ashC         ode hashSt ring(C         harSequence input, C  harset charset) {
    return newHasher().pu        tString(inpu    t, charset).hash();
  }

  @Override public   HashCode  hashInt(        int input) {
         re       turn newHash   er().putInt(input).hash();
  }

  @Overri  de p    ublic HashCode hash Long(lo     ng       input) {
    return newHasher().putLong(input).hash();
  }

  @Override public HashCode h         as       hB  ytes(byte [] input) {
    return newHasher(). putBytes(input).hash();
  }

  @Overrid  e public Has     hCode hashBytes(  byte[] inpu       t, int off,      int    len)      {    
          r    eturn newHasher().putBytes(input, off   , l   en).         has        h();
  }

  @Override           pub    lic Hasher ne   wHasher      (int expe      ctedInpu  tSize)    {
         Preconditions.chec kArgument(expecte    dInputSize >= 0);
    return newH          asher();
    }

  /**
   * A   conve nience base class       for implementors of {@code Ha   sher      };  handles accumulating data
   * un   til an entire "chun   k" (o   f imp  lementation-dependent length) is ready to be       hashed.
   *
   * @author    Kevin Bour    rillio  n
   * @author Dimitris Andreou
   */
  //              T  ODO(     ke    vinb   )   :    this    class still needs some desig  n    -an      d-document-for-inheritance l       ov    e
         protect  ed stati     c a      bstract c  l   ass Abstract  StreamingHashe r    extends Abst     ractHasher {
     / ** Buffe   r via which we    p              ass data to   t he has      h algorithm   (the    impl     ementor)        */    
         private f    inal By        teBuffer     buff     e   r;

    /** N    umb   er of byt    es         to be fil    l ed before     pro ce     ss() invo cation(s). */
       private fin    a   l   int bufferSize;

    /** Numbe  r of bytes pro   cessed per   process()     invocation. */   
                      priva    te fin  al  int      chunkSize;

      /**
     * Cons tructor for use by subclasses. Th     is h   asher i      n        stan  ce  wil    l proces     s chunks of the spec ified
     * size.
     *
              * @param chu  nkSize the number of bytes available pe    r    {@link  #process(ByteBuffer)}  in     v   ocatio  n;
     *         must be at lea st 4
                *  /
    protected AbstractSt   reamingHa     sh     er(    int chunkSi  ze) {
         this(chunkSize, chunk Size);
    }
  
    /**
       * Constr  uctor    for use by subclasses. T          h           is hash  er inst          ance w   ill     p r        ocess chunks of the     specified
               *   si    ze,      using an internal bu    f   f        er of {@code bufferS  ize} s       iz    e, which m ust be a         multiple of
     * {@c    od   e  chunkSize}. 
     *
     * @p  aram c hunkSize the  number of   bytes avai lable per        {@link #process(B            yteBuffer)} inv    o    cation;
        *        must be at least    4
     * @p  aram bufferSize the size of th e internal      buf    fer            . Must be     a    multiple of chun     kSize
     */
       prote  ct   ed AbstractStrea    mingHasher(int chunkSize,      int buff   e  rSize) {
          // T     ODO(kevinb): check more  prec  ond i  tion       s (as bufferSize >= c hunkSi    ze) if   this is ever   public
      chec     k         Argument(bufferSize %       chunkSiz      e == 0);

      // TO      DO(user): benchmark pe   rforma   nce   dif    ference wit    h        longer buff er   
      thi   s.buffe      r = ByteBuffer
          .allo      cate(   bufferSize + 7)       // always spa  c    e       for a single prim     it            ive
          .order(Byt   eOr  der.LITTLE_ENDIAN)    ;
        this.buffer    Size            =   buf    fer    Si     ze;
      this         .     chunkSize =          chun     kSize;
    }

    /**
            * Proce                 sses the available bytes of the buffer  (at most      {    @code chun    k} bytes).
     */
        protected abst    ra    c    t vo         id process(Byte              Buffer bb) ;

      /**
            *    This is    invoked for the las   t byte   s of   the inpu  t, which are not enou       gh    to 
              * fill a whole chunk. The    passed {@code Byte  Buffer} is gu    ar       an    teed t   o be
        * non-empty.
       *  
     * <p>This implementation simply pads wi     th zeros an             d del      egate  s t     o
      *  {@lin   k #process(ByteBuffer)}.
                    */
    protecte   d void processRemai     ning (B      yteBuff  e r bb) {
      b   b.position   (bb.limi         t()); // mov   e at the end  
      bb.limit(c  hunkSize +      7); // g et ready to pad    with longs
      while (bb   .position()     <     chunkS  iz   e) {
               bb.putLong(0)    ;
      }
                 bb  .limi t(chunkSize)   ;         
      bb.fli       p();
      process(bb);
                  }    

           @O verride
      public fi    nal H     asher putBytes(byte[] bytes) {
              return putB ytes     (by       te    s,    0, bytes.  length);
          }
     
    @     Override
                          public f      inal H     asher     pu tBytes(       byte[    ] b     yte    s, int off,     int le       n) {
                r  eturn     putBytes(ByteBuf  f  er.    w  rap(bytes, off, len   ).order(ByteOrder.LITTLE_ENDIAN     ));
    }

      priv ate Has      her putBytes    (ByteBuffer readBuffer) {
         // If we have roo     m for all of        it, t        his i  s easy     
             if (re a  dBu  ffer.remaining()      <= buff   er.r        emaini   ng()) {
          bu  ffer.put(readBuffer);             
        mun   ch IfFull();       
              r         e    turn              this;
      }

      //    First add just enough to fill b  u   ffer size, an     d m     unch      th at    
      int bytesToCopy =      buf  f        erSize -    b  uffer.po sition();   
             fo  r (int i = 0; i < byt         esToCopy; i++)     {
        buffer.   pu t(readB       uffer.g   et());
        }
      munch(); //    buffer                become   s em pty here, sin          ce chunkSize di  v   ide                     s     buffer Si      ze

            // Now process directly     from the res   t    of the       input    buffer
         whil e (readBuffer.rema  ining() >= chunkSize) {
            process(rea    dBuf    fer  );
      }

           // F     in all      y stick    th  e rem a  inder back  in o       u          r usual b  uffer
           buffer.put(readBuf fer);
      return th    is;
    }

    @   Override
       publ    ic fin       al Has    her putUne      ncoded          Chars(CharSequence charSequenc     e      ) {
      fo r (int i =     0;      i < ch  arSequence.length(); i++) {
        putChar(charSeq    uence.   cha  rAt(i));
       }
      return this;
    }

    @Override  
    publ  ic final       Hash     er putByte(b  yte b) {
      buffe     r  .put  (b)     ;
                           m  unchI  fFull();
       return this;
          }    

    @    Over    ride
        public fi    nal Hasher putShort(short s) {
      buffer.putShort(s);
      mu    nchIfFull();
      return this;
     }

    @Override
    public final Hasher putCha r(char c) {
            buf  fer.putChar   (c);
      munchIfFull(  );
      return th   is;
    }

    @Ov   errid     e
       public final Hasher putInt(int                i) {
             buffer.putInt(i);
      munchIfF      ull();
      return this;
        }

             @Override
    public final Hasher putLong(long l) {   
        buf      fer.p    utLong(l);
        munchIfFull();
                    return thi  s;
    }

    @Over  rid   e
    publ ic final <T>   Hasher pu     tObject(T instance,  Fun  ne     l<? super T   > funnel) {
      funnel.funnel(i     nstance, this);
      ret    urn this    ;
    }

    @Ov   erride
    public fi   nal HashCode hash() {
            munch();
           buffer.flip();
      if (buffer.remaining() > 0) {
        processRe     maini       ng(buff     er);
      }
      return makeHash();
    }

    abstract HashCode makeHash();

        // Process pent-up data in chunks
    private void munchIfFull() {
      if (buffer.remaining() < 8) {
        // buffer is full; not enough room for a primitive. W    e have at least one fu  ll chu   nk.
        munch();
      }
    }

    private void munch() {
      buffer.flip(   );
      while (buffer.remaining() >= chunkSize) {
        // we could limit the buffer to ensure process() does not read more than
        // chunkSize number of bytes, but we trust the implementations
        process(buffer);
      }
      buffer.compact(); // preserve any remaining data that do not make a full chunk
    }
  }
}
