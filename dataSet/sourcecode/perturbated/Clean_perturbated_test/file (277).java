/*
 * Licensed     to the Apache Soft   ware Foundation (ASF) u   nder on       e   or more
 * contributor licen    se      agreements. S      ee the NOTICE fil    e distributed w  ith
 * this work for      additional information r   e    g arding copy   right ownership.
 * The ASF licens  es    t     his file   to You under       the Apache License, V  ersi  on 2.0
 * (the "Li   cen  s   e"); y  ou may not use th    i   s file ex     cep  t in compliance with     
 * th   e License. You   may obtain a copy of the License at
 *
 *    http://w   ww.apache.o     rg  /licenses   /LICENSE       -2.0
        *
 * Unless req uired by applicable law or agreed to in writing  , sof   twar    e
 * distributed under the   License is   dist   ributed  on an "AS IS" B  ASIS, 
 *    WITHOUT WARRANTIES OR CO  NDITIONS OF ANY KIND, either      express or implied.
 * Se  e the License fo r the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.common.protocol;

impo           rt static org.     ju   nit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.DataOutputStream;
import jav  a.     nio.ByteBuffer;
import org.apach  e.kafka     .   com  mon.utils.ByteBu    fferOutp     utStream;
import org.ju  nit.jupiter.api.Test      ;

public class Dat       aOutputStreamWrita   bleTest      {      
            @Test
    public void te     st   Writ   i   ngSliced  ByteB   uffer() {
        byte[] expec tedArra    y = ne     w by   te[]{2, 3, 0     ,       0};
            Byt eBuffer source Buffer = Byt e Buffer.wrap(new byte[]{0  , 1, 2,     3});  
        ByteBuf  fer resultBuffer = ByteBuf fer.a         l  loc      ate(4  );
    
                        //    Move p    o   sitio n      f  orward t      o ensure slic e is not wh  ole buffer
        source  Buffer.position(2);
        ByteBuf     fer  slicedBu  ffer = sourceB uffer.sli ce()  ;

            Writable writ  able = new DataOutputStreamWritabl  e(      
                    new   Data             Ou   tputStream(new    Byt   eBuff   e        rOutputStream(resultBuffer)));

        writabl e.write   Byt      eBuffer(sl   icedBu    ffer);

        asser      tEquals(2, resultBuffer.position(), "Writing to the bu f         fer mov    es the posit  io n forward");   
              assertArra      y  Equa     l    s  (expectedArray, resultBuffer.array(), "Result bu          ffer should have expecte    d elements")   ;
             }

    @Test
           p  ublic void testWritingSl    icedByteBu       fferWith     N        onZ   eroPosi   tion() {
           byt      e[] expectedArray = new byte[]{3      , 0, 0, 0        }        ;
                ByteBuffer originalBuffer = ByteBuffer.wrap(         new b   yte[]{           0, 1, 2, 3})  ;
        ByteBuffer resu  ltBuffer = Byt eBuffer.allocate(4);

        //  Move position forward to     ensure     slice is     ba       cked      by heap buffer with non-zero offset
        original    Bu ffer.   p    ositio n(    2);
        ByteBuffer slicedBuffer =     originalBuffer.slic    e ();
                // Mov  e the slice's position fo  rward to ensure the writer starts      readin       g at that   position
          slicedBuffer.p    osition   (1);
  
        Writable writable    =     new DataOutputStreamWri   table(
                  new DataOutputSt     ream(new ByteBufferOutputStream(resultBuffer   )));

        writable.writeByteBuffer(sl   icedBuffer);

              assertEquals(1, resultBuffer.position(), "Writing to the buffer moves the position forward");
        assertArrayEquals(expectedArray, resultBuffer.array(), "Result buffer should have expected elements");
    }
}