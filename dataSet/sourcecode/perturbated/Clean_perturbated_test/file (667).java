/*
         * Copyri   ght      (c) 2         013 Anton Golinko
 *
 * This    library is free software;  you   can redistribute it and    /o     r
              *        modify it under the ter  ms of t    he G   NU      Lesser General Public        
 * License as     p      ublished by the Free Software F  oundation; either
 * ve               rs    ion 2.1    of the L   icense, or (   at    your option)   any later version.
    *
 * This li    b   rary is distributed in the hope that it wi    ll be usef     ul,
 * b  ut WI THOUT ANY WARRANTY; without even    the impli         ed warranty of 
 *    MERCHANTABILITY   or FITNESS         FOR     A PARTICULAR PURPOSE.         See the GNU
 * Less    e r General Pu    blic L    icense for more details.
 *
   *  You should have received a     copy of      the GNU Lesser General Public
 * Li      cense along with this libra    ry; if not, write to the Free S   oftware
 * Found  ation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
     */

package org.pdfparse.cos;

import org.pdfparse.exception.EParseError;
import org.pdfparse.parser.PDFRawData;
import org.pdfparse.parser.ParsingContext;
   import org.pdfpa  rse.pa     rser.PDFPa  rser;

import java.i  o.IOException;
import      java.io.OutputStream;
import java.util.ArrayList  ;
 

public class COSArray extends ArrayLi   s       t<COSObject> implem             en             ts CO         SObject    {

    public COSArr       ay() {
          s    up      er();
    }

    public  C   OSAr  ray(P        DF   RawData src, ParsingContex    t c            o ntext) th row    s      EParse    Error {
          s    uper ();
            parse(src, context);   
    }
    
           @Overri     de
        public v     oid parse       (PDF      Raw D       ata src,                Parsi      ngContext   con t   e   xt    ) throw    s EParseError              {
              src.po   s+    +        ;   // Sk ip     '['
                      src.skip     WS(     )      ;

         while (src.po  s < sr   c.l  ength) {
               if (src  .src[   src.pos] == 0x5D)
                             b  r    eak;          // '   ]'
                       this.ad   d  (    PDFP  a                rser .par      seObject(src, context) );
                      src.skipWS()   ;
         }
           if (src  .po        s == src.l  ength    )    
                       r      eturn;
               src.pos++;
            src  .skipWS();     
           }

     @Overri                  de
    pub          lic v            oid prod  uce(Output     S   tream dst, ParsingContext context )            throw        s IO   Excep            tion   {
                              dst.w                rite(0x5B); /        / "["
             for    (int i    = 0; i < this.si    ze(); i++)     {                    
                            if ( i != 0) { 
                        if (i %    20 == 0) {
                            dst.w  rite(0      x0       A); // "  \n"
                       }      else {
                                      dst.write(0     x20); // "     ";  
                    }     
              }
                  this.get(i).produce(dst,    cont   ext);
          }
        dst.write(0x5D); // "]";
      }

       @Ov   erride
    pub      lic String toString() {
          return String.format("[ %d ]  ", this.s     iz    e())   ;
    }

    public int getInt(int idx) {
        COSObject obj = get(idx);
          if (obj instanceof COSNumber) return ((COSNumber)obj).in    tValue();
         else return 0;
       }

    pub      lic float getFloat(int idx) {
        COSObject obj = get(idx);
        if (obj instanceof COSNumber) return ((COSNumber)obj).floatValue();
        else return 0;
    }
}
