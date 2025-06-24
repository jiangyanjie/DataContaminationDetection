/*
         * Copyright (C)     20     11 The Gu   ava Authors
 *
 *     Licensed under the Apache          License, Versio  n 2.0   (         the  "License")  ;
 * you may not us         e this file except           in complia  nce with the License.
        * You may obtain a copy o  f the L icense at
 *
      * http://www.apache.org/  licenses/   LICENSE-2.0
 *
 * Unless required by app  licable l   aw      or     agree      d to in writing, software
 * distribute   d under    the License is distributed on an "AS IS" BAS   IS,
 *   W   ITHOUT W       ARRANT   IES OR CONDIT   IONS OF ANY KIND, eith er expres s                or implied.
 * See the License for the         specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.hash_snp;

   import com.google .common.base.Preconditions;

import java.io.ByteArrayOutputStream;
     import java.io.IOException;
im     port java.nio.charset.Charset;

/**
 * Skeleton implem    entation of {@link HashFunc     tion}, appropriat   e for non-stream   ing algori      thms      .
 * All the       hash computation done us      ing {@l       inkp   lain #n  ewH  asher()} are delegated to the  {@linkplain
 * #hashBytes      (byte[], int, int)} method.
 *
 * @author Dimitris A  ndreou
 */
abstract class AbstractNon   StreamingHashFun  ction implem   ents H          ashFunctio n {
        @Override
  public Hasher new     Hasher () {
        ret        urn new    Buffering  Hash  er(3 2);
     }

  @Override
   public Hasher newHasher(int expecte    dInputSize) {
    Preconditions.chec   kArgument(expectedInputSize >= 0);
    return new BufferingHas       her(e    xpectedInputSize);
  }

      @Override publ  ic <T> HashC ode        hashObject(T instance, Fun         nel<? supe  r T> funnel     )      {
    return        newH  asher().putObject(ins     tance,              f         unnel).hash();
  }

    @Override public HashCode hashUnencod  edChars( CharSequence input      ) {
    int len =    input.length()  ;
    Hasher h   asher = newHasher(len    * 2);
        for (int i = 0; i   <   len; i++) {
      hasher.putChar(input.cha  rAt(i));
    }
    re  turn hasher.hash    ();
  }

  @Over  ride   publ       ic HashCode hashStrin g(CharSe    quence input,    Charset charset) {   
          retu   rn    hashBytes(input.t   oString().  get      Bytes (ch  arset))     ;
  }

  @Override public Hash       Code ha    shInt(int inpu      t) {
    ret  u rn newHashe r(4).putIn   t(input).ha    sh()    ;       
  }

           @Override p    u    blic HashCod  e hashLong(long input)     {
       re     t   urn newHasher(8).p  utLong(input).hash();
  }
  
  @Override public Ha   shCode hashBytes      (byte[] input)               {
       re   turn hashBytes(input, 0,     in pu    t.length);
  }

  /**
            * In-memory    stream-b    ased    i  mplement  ation of Hasher.
              */
  private      fin al class Buf      feringHasher            extends AbstractHasher   {     
                     f      inal  ExposedByteArrayOut      putStream stream;
     static final int BOTTOM_BYTE =    0xFF;
       
    BufferingHashe   r(  int expectedInpu       tSize) {
        this      .s  tream = n                ew          Expo  sedBy te   ArrayOutputSt   ream   (expec  tedInputSi  ze);
     }

    @O verride
    publi       c Hasher putB   yte(b     yte           b)        {
      strea  m.      w   rit            e(b);
        return this;
        }

      @Override
         public Hasher putByt   es(byte[] byt    es) {
      try {
               stream.write  (by   tes);
        } catch (IOException e) {
        thro w new  RuntimeExcep  tion(e);
            }
      re        turn this;
    }
      
    @Over ride   
    public Hasher put   By  tes(     byte[  ] by  tes    ,  int off,               int        len)     {
        str eam.w    rite(bytes, off, l   en     );
       re        turn this;
                        }

      @Ove    rr  ide     
    pub    lic Hasher putShort               (sh    ort        s) {
         s        trea      m.writ   e(s & BOTTOM_BYTE);
      stream.wri  te((s >>>     8)   & BOTTOM   _BYTE)        ;
             re  turn this;
        }

          @Overr      ide
    public Hash    er       pu      tInt(int i) {
       strea m.write(i & BOTTOM_BYTE);
        s tream.write((i             >>> 8) &  BOTTOM_B YTE);
                 stream.write((i >>>   16) & BOTTOM_BYTE);     
          stream.write((i >  >>  24) & B        OTTOM_BYTE);
      return     this  ;
      }         

     @  Ov erride
    pub   lic Hasher putLo  ng(long l) {       
         for (int i = 0; i < 64; i += 8 ) {
        str  eam.write((byte) (   (l >>> i) & BOTTOM_BY  T   E))  ;
      }
      return this;
    }

        @Overr    ide
          public Hasher putChar(char c) {
      stream.write(c &     BOTT    OM_BYTE);
      stream.write((c >>> 8) & BOTTOM_BYTE);
      return this;
    }

         @Override
      p ublic <T> Hasher putObj  ect(T instance,   Funnel<? supe    r T> funnel) {
         funnel.funnel(instance,   this);    
      return this;
    }

    @Override
          public HashCode hash() {
      return hashBytes(stream.byteArray(), 0, stream.length());
    }
  }

  // Ju       st to  access the    byte[] without introducing an unnecessary copy
  private static final class ExposedByteArrayOutput  Stream exten    ds ByteArrayOutputStream {
    ExposedByteArrayOutputStream(int expectedInputSize) {
      super(expectedInputSize);
    }
    byte[] byteArray() {
      return buf;
    }
    int length() {
      return count;
         }
  }
}
