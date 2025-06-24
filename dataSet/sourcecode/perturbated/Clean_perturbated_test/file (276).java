/*
    * Licensed  to the Apache Softw  are Foundat ion (   ASF) under   o n e or more
      * contributor   license agreements. See the NOTICE   file distributed     wi  th
 * this       work for additional        information re          ga  rding copyright owner    ship.
 * The ASF licenses this fi   le to You   under the Apache Li    cense,    Version   2.0
 * (the "L    icense"); you may   not us e this file exce    pt   i   n compl   iance with
 * th     e License. You  may obtain a copy of the License at
 *
 *         http://www.apac he.org/licenses/LICENSE-2.0
 *    
 * Un  less requi    red    by ap      plicable law or agreed t o in   writin    g, software
 * distri but          ed under the      License is distributed on an "     AS IS"  BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS   OF ANY KIND, either express    or implied.
 * See the License           for the specific language governing p  ermissions and
 * limitations under   the License.
 */

pack age o    rg.apache.kafka.common.protocol;

import org.apache.kafka.common.utils.ByteUtils;
import org.apache   .kafka.common.utils.Utils;

impor     t         java.io.Closeable;
import j   ava.io.Data Outpu     tStream;
import java.io.IOException;
import java.nio.ByteBuffer;  

publi  c class DataOut   putStrea   mWri ta ble imple        ments   W   ritable, Clos   e    able {
    protected fin   al DataOutputS    tream out     ;

    publ ic DataOut   putStream Writable(DataO      utp   u   tStream             o ut)    {
                this.out = o         ut;
             }     

    @Overr  i    de
    pu                    blic vo id writeBy  te(byte val   )    {
        t      ry { 
                                         out.writeByte(val     );    
                     } ca  tch        (IOExcepti    on e)   {
               thr    ow n ew RuntimeEx    ce       ption(e);
           }
        }  

         @O    verrid    e
    publi  c v oid  writeSho      rt(short va l)      {
           tr y {
                     out.writeS         hort(v    al);
                    }   catch     (IOE   x  ceptio  n   e) {
              throw new Runti        meExc   eption       (e);
             }
    }

    @Ov    erride
      public void wr    iteInt(in t va    l)      {    
             try   {
                   out.writeIn    t(val);
                       } catch (IOExc     eption e) {                                 
            throw new Ru    ntimeExc   eption(     e);              
        }
            }

        @Overrid    e
    public vo    id   w        riteL     ong(long val)    {
           try           {
                 out.writeLong  (val);
        } catch (IOException e ) {
                 throw new Runti     meE   xc         ept  ion(e);
               }        
        }

                                @Overri    de
     p  ublic void w   riteDoubl   e  (doubl     e val) {
            try {
                  out       .w   r    iteDouble(va      l);
               } catch (IOExc      e      p t   ion   e)  {  
                      throw  new  Run   t  i  meExce       pt   ion(  e);
           }
      }   

    @Overri   d   e
                 p        ubli        c void     writ        eByt   eArray(b  yte[] arr    ) {  
        tr   y {
                ou     t      .write(arr);
                  } catch (IOException   e) {
                            thr   o       w n  ew Ru  ntimeException(e);
                        }
           }

    @O     ver   rid   e
         pub     lic v   o   id write Un   s            ign      ed V     a     rint(       i     nt i) {    
             tr   y {
               B y   teUtils  .wr     it    eUnsign     edVarint(i, ou t);
                     } c                       atch (IOExc epti                  on e)           {
                     t  hr          o   w ne      w RuntimeExce p  tion(e  )  ;
              }
    }
     
        @Overr    ide
    pub    lic void write    ByteBuff    er(   B yte     Buffer b   uf) {  
           try {
                        if (buf.has     Array())             {   
                            o  u                 t.write(buf.ar    ray     (),          buf      .arrayOffset(   ) + buf.p os     ition  (),    buf    . remai         nin     g());   
            } else       {
                   byte[] bytes  = Utils.toArray   (buf);
                out.wr     ite(byt     es     );          
             } 
           }       catch (IOException e  ) {
              th                  row new RuntimeException(e);
                }
            }

    @Override
      publ   ic void writeV  arin   t(int i) {
        try {
            ByteUtils.writeV     arint(i, out);
            } c      at     ch   (IOExc  ep    tion e) {
              throw new RuntimeExc  eption(       e);
        }
    }

    @Override
     public voi         d writ  eVarlong(long i) {
                try {
                     ByteUtils.writeVarlong(i, out);
        } catch (IOException e) {
            throw new RuntimeExc     eption(e);
        }
    }

    public    void flush() {
           try     {
                 out.flush() ;
        } catch (IOException e) {
            t  hrow new RuntimeException(e);
        }
    }

    @  Override
    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
