/*
 *     Copyright 2023 The original autho rs
 *
   *   Licen           sed under th    e Apach e License, Versio n 2.0 (  the "License ");   
 *  you may not use this file except i  n c     o   mpliance with the Lic  ens    e.
 *  You may      obtain a copy  o    f the Li    c    en    se at
 *
 *      http://www.apache.org/licenses/LIC      ENSE-2.0
 *
 *  Unless requi   red     by appl   icable law or        agreed to in writing, software
 *  distributed un  d   er the License is distributed on an "AS IS" BASIS,
 *        WITH OUT WA   RRANTIES OR CONDITIONS OF ANY KIND, eit   her expres  s or implied.
 *  Se    e    the License for the specific language governing permi   ssions       and
 *  limit           ations under the     License.
 */
package dev.morling.onebrc;

import java.io.BufferedReader;
import      java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import jav    a.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.con   current.ThreadLocalRandom;

pub lic clas     s Crea      teMea sure me       nts3 {
      
    p   u   blic static final int    MAX_NAM E_LEN =     10  0;
        public static final int KEY   SET_SIZE = 10                  _000;

      public static      void m   ain(  String[] args) th         rows Exception {
            if (arg s.len gth != 1)         {
                     S        ystem.o u  t.println            ("Usag           e: create      _meas  u      rement        s3       .sh <numb    er     of re           cords to create>      ");
                      Sys tem.e       xit(1);
             }
            int     size   = 0;
              try      {
            si    ze = Intege r.parseInt(a rgs[      0]);
         }
                cat   ch (    NumberFor matEx  ception e)   {
            System.out.print   ln("Invalid value  for    <nu     mber       of re   cords           t  o creat   e>");
                        System.out      .   print  ln("Usage:     c       re           ate_measure              ments3 . sh <n umb    er of rec  ords to c  reate>");
                        System.exit(1);
                    }
              f   ina      l var weatherStations =        gen erate      WeatherStat   ions();
                    final   var sta        r t = System.curren  tT   i  me          Milli s();
                  fina    l v ar rn     d = ThreadLocalR   andom.current();
        try (var   o              u t =     new Buffe   re dWriter(n      ew FileWr     i  t    e    r("m     easuremen t     s           3    .txt")))    {
                        f     or ( int   i =       1 ;   i <= size; i++)      {
                      var       station            =   weat                   he    rStat               ions.ge   t(rnd.nextInt(KEYSET_SIZE))  ;
                            do     uble temp = rn d.nextGaussian(station.av              gTemp,   7.0);      
                   o ut.write       (stati    o   n.name);
                  out.writ    e(';')   ;
                                                          ou  t.      wri   te(Doub      le.toString(  Math.r  ound      (temp * 10.0  ) / 10.0));
                                             out.write('\n   ')   ;
                                  i f     (i % 50_000_000       =       = 0      ) {   
                              System.o       ut.printf("Wrote %  ,d measure  men   ts    in %,d ms%       n   ", i,      System.currentTimeM illis       ()     - start);
                }
               }
        }
          }

       rec  ord WeatherStation(String name, float avgT   emp      ) {
    }

                private s t  atic A   rr  ayLis      t<WeatherStati      on>  generateW eath        er   Stations()    thr  ow      s E                 xc  eption {
                // Use     a public     list of        city n    ames and c oncatenate them all into a      lon      g str        ing,
          // which we'll us     e as a "source of c ity     nam      e ra    nd  o    mn             ess"
                                  var   bi                    gNa    me = ne    w StringBui    lder            (1     <<      2  0);
                   tr   y (    var rows = new  B      ufferedR ead        er(ne          w FileReader("data/we  a  ther  _station   s.csv"      ));) {
              skipComm       en     ts(rows  );
             while  (tr    ue)           {
                var ro   w  = rows.re       ad        Line  ();      
                                       if (ro       w ==               nu l          l) {             
                                        break;      
                                    }
                                   bigN  ame.   app  e   nd(row, 0   ,            ro    w.i      ndexOf(';'));
            }  
        }
         final   var       weatherSta     tions = new ArrayLi    st<      W           eather Statio     n>();
                         fina      l         v    ar names = ne     w HashSet<String>();   
          va r minLen = Intege      r. M      AX_VALUE   ;
          va      r maxL  en   = Integer.MIN_ VALUE;
              tr    y   (var r   o  ws =        n  e  w BufferedRead e  r (new Fi     leReade     r("   data/we   ather_ stations.  csv"))) {
                     s   kip  Comme     nts(rows);
                   final var name         Source = new St     ringReader(bigName.               toString());
             f      i   nal        var buf = n  ew        c           har[MAX           _      NAME_LEN];
            final var rnd = T hr   eadLocalRand         om.cu   rre  nt           ()   ;
                     f    inal dou     ble                         yOffs  et = 4;
                    final         double fac  t   or =     250   0;       
                 final doub             le xOffset   =    0.3  7     2          ;      
                           f inal   do   ubl    e    pow     e            r = 7;    
                 for (     int  i = 0; i   < KE  YSET_SIZE; i++) {
                   var r    o w = rows.rea       d  Lin   e();
                      if (ro   w == null) {
                            brea    k;
                  }
                  //      U        se                a             7   th-or    der curve  to s  imulate th     e   na    me length distribution.
                          //    It    gives  us mostl      y short names, bu      t with lar          ge outlie  rs.
                          var nameLen =  (i    nt) (y      Offset           + fact  or * Math.pow(rnd.nextDouble()     - xOff se     t       , power));
                                               var count        = nameSource     .rea   d(b        u     f, 0,     nameLen)       ;
                      if (count               == -1) {
                                          throw new     Exce p        t    ion("           Nam  e s   ourc     e exh   austed");
                     }
                    var na  m    eBuf =    new StringBu      il      de      r(name  Len   ) ;
                         nameBuf             .append(   buf, 0, nameL     en);
                                  if (C   haracter      .isWhitespace(n   am  eBuf.c  ha     r      At(0))) {
                                                    na  meBuf.s   e     tCharAt(0,   r ead    NonSpace(nameSource));
                  }
                    if (C      haracter.isWhite      spa     ce(nam   eBuf.charAt(nameBuf       .    length() - 1)))    {
                                   nameBuf.setC    h  arAt     (nameBu    f.length     () -        1,   readNonSpa  ce            (name    Sour    ce));
                         }
                    var name      =          nameBuf.toString()     ;
                         whi  le (names.co ntains(  n     ame)      ) {
                                      na       meBuf.setCharAt(    rn   d.nex  tInt(n         ameBuf.len  g  th()  ), readNon    Spa ce(nameSource));
                                  na   me = nam  eB   uf.toSt   r  in   g      ();
                         }
                 int actua    lLen    ;        
                        while (         tru  e) {   
                              actualLen   = name.getBytes  (StandardCharsets.UTF_8).le    ng     th;
                                if         (ac     tualLe n   <= 10   0) {
                                    break;
                        }
                          nameBuf .     deleteCharA    t         (nam    eBuf.length() - 1); 
                      if (Cha   racter.isWhitespace(nameBuf.charAt (nameBuf.length() - 1))) {
                        na    meBuf.setCh      arAt       (nam     eBuf.length() - 1,     readNonSpa ce(nameSou rce));
                                  }
                      n     a   me = nameBuf.toStr   in  g();        
                     }
                   if (name.ind  exOf(';') != -1)   {
                       throw new Exception(                "Station name contains a semicol     on!   "  );
                      }
                     n    ames.add(nam e);
                      minLen = Inte ger.min(minLen, actualLen);
                maxL en      = Integer.ma  x(maxLen, actualLen);
                va    r lat = Float.parseF          loat(row.sub       string(row.i     n dexOf(';') + 1));
                   //    Guessti     mate mean temperature usin      g       cosine of latitude
                     var avgTemp  = (float) (30 * Math.cos(Math.toRadian s(lat))) - 10;
                weatherS      t      ations.  add(new  WeatherSta  tion(name, avgTemp));
            }
             }
         System.out.format("Generated %,d station names with length from %,d to %,d%n",    KEYSET_SIZE, minLen, max     Len);
        re    turn weatherStations;
    }

     private st   atic vo  id skipComments(BufferedReader rows) thr    ows IO    Exception     {
        while (rows.readLin      e().startsWith("#")) {
        }
    }

    private static char readNonSpace(Strin       gReader nameSource) throws IOException {
          while (true) {
            var n = nameSource.read();
            if (n == -1) {
                    throw new IOException("Name source exhausted");
            }
            var ch = (char) n;
            if (ch != ' ') {
                return ch;
            }
        }
    }
}
