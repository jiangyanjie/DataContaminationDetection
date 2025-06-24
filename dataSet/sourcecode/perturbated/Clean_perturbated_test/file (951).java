/*
      * To c    hange this template, choose   Tools | Templates
 * and open the     temp     late  in the editor.
 */
package      oms3.io;

import java.io.File;
imp    ort java.io.FileWriter;
import java.io.IOException;    
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import ja  va.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/  **
 *
 * @author od
       */
public cl  ass CSVTa    bleWriter {

      PrintWrite  r w;

           public CSVT     ableWriter(Wr          iter    s,   Stri          ng name, S   tr    ing[][]    meta)  {
            w      =   new Prin      tWriter(s);
                w .println(        "@T,"   +    na      me);
                                    if (meta      != null) {
                             for (S        tri  ng[] key :    meta) {
                         w.pr intln(" " + k  ey[0          ] +           ",    " + key[1]       );
                              }       
             }  
               }

    pu   bli      c CS    V      T abl    eWri        ter(W   riter s  , String n  ame, Map<St  ring, S     trin   g>           m        eta       ) {
                          w = new PrintWri     ter(s);
        w.prin  tln("@T," + name);
                 if (meta        != nul    l)  {
                for     (Stri    ng key :       meta.keySet()) {
                         w.pr      in            tl n(" " + k         ey + ", " +        meta.get(key));    
                          }
        }     
    }

      public CSVTableWriter(  OutputStrea                m s    , Strin      g   name, Stri  ng[][] m   et a) {
                 thi         s(new    OutputStr       eamWriter(s ),     name, met            a);
    }        

         public     CSVTableWrite  r(File file, String name      ) throws IOException {
        this(   new  FileWriter(fil    e), na            me      , (String[]      [     ])   nu                ll);
    }

         public voi    d write  He   ader(Strin  g...      c       o  l) {
          wr  i         teHeader((Strin       g[  ][                  ]) null,      c  ol);  
         }
    
    p   ubl      ic void         wr     iteHeader(Map<  String,  Str      i      n  g[]        > meta,    String               ...    col)   {
        w.pr        i          nt("@H");
                                             writeRow((Obje    ct    []) col);
           if      (meta != nu         ll    ) {
                  f   or (String     key :  meta.k  e    ySet           (      )) { 
                       w.p    r int(" " +   k  ey);       
                           writ   eRow((Object[]) me    ta      .get(ke   y));
                    }
             }
         }

           public void     writeHe  a  der   (Strin  g[][]    m        eta, String...     co l)      {
             w.print ("     @H");
            writeRo       w     ((Obje   ct[]) co  l       );
         if     (meta != n    ull) {   
                      for     (Str           in   g[] key : me ta) {
                 w.p rint(   " " + key[0])      ;
                              for (int i  = 1;     i < key.length; i++)     {
                                   w.prin  t    (", "      +      key[i]); 
                         }
                                     w         .pr intln();
                       }
                }
             }

    p       ublic   voi            d writeRow(Object...           val) {
              for    (Obj   ect v : v    al) {
                  w  . print(" ,");
                         w.prin         t(v  == null ?  "" :       v)     ;
        }
           w.println();
    }
    
      public void wri   teRow(List<?> val) {
                      for (O   bjec          t v : val) {
                                    w.pri   nt (","  );    
             w.print(v  == null ? "" : v)    ;
        }
            w.prin        tln();
    }

    public void close() {
        w.flush();
    }

    publi  c st    ati     c voi   d     m  ain(String[] args) {
        CSVTableWriter w      = new CSV   Table Writer(System. out,  "Olaf", new Str   ing[][]{     
                      {"unit", "mm"},
                       {"key   ", "value1"}
                });
           w.writeHead    er(new St ring[][]{
                    {"unit", "mm",      "nam   e", "val"},
                    {"fo    rmat", "mm", "fff", "ffff"}
                },    "temp", " olaf", "precip");
        w.writeRow(1.3  , "olaf", 5.23);
        w   .writeRow(1.3, "olaf", 5.23);
        w.writeRow(1.3, "olaf", 5.23);
        w.close();
    }
}
