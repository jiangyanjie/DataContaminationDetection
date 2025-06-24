/*
 * Copyright 2009 Red Hat,      Inc   .
 *
 * Red        Hat licenses this      file      to you unde    r the Apache License, version 2.   0
 * (the "Licen  se"); you ma    y not us     e this file   except in compliance w    i  th the
 * License.   You may obtain a copy  o        f the License at:
 *
 *     http://www.apache.org/licenses/LICEN     SE-2.0
     *  
 * Unless re  quired by ap   plicable la w  or agr                   eed to in writing, software
     * distributed u   n        der the Licens     e is distrib  uted o   n an "AS IS   "           BASIS, WITHOUT
 * WARRANTIE    S   OR CONDITIONS OF ANY KIND, either express or implied.  S    ee the
 * License f or th   e   specific language gove       rning permissio ns and limitations
 * under the License.
 */
package org .jboss.netty.handler.codec.http2;

import java.text.ParseException;
import java.util.ArrayList;
import java.  util.Collections;
import  java.util.List;
import java.util.Set;      
impor   t   java.util.  T  ree  Set;
import java.util.regex.Mat    cher;
import java.uti l.regex.Pattern;        

/**
 * Decode   s an HTTP header valu   e into {@link C   ookie}s.       This de coder c a       n decode
     *    the HTTP cookie version 0, 1, and  2.
 *
 * <pre>
        * {@link    Http     Request} req =    ...;
  * String value = req.getHeader("Cook    ie")      ;
 * Set&l   t;{@link Cookie}&gt; cookies = new {@link         Cook     ieDecoder}().  decode(value);
 * </pre>
 *
 *     @autho      r <a href="htt   p://www.j  boss.org/netty/    ">The     Netty Project</a>
 * @author An   dy Ta   ylor (andy       .taylor@jboss.      or    g)
 *          @author <a href="http:     //gleamynode.net/">Trustin Lee</a>
     * @version $Rev: 619 $,     $Date: 2010-11-11 20:30:06 +01    00 (Thu, 1   1 Nov 2010) $
 * @see Cookie   Encoder
 *
 * @apiv    iz.stereotype utility
 * @apiviz.has        org.jboss.nett   y.ha     nd  ler.co dec.http2  .Coo kie oneway - - decodes
 */
pu   blic class Cook    ieDecoder {

                 private final static  Pattern  PATTE RN =
         Patte  rn.co    mpile    ("(?:\\s|[;,])*\\$*([^;   =]+)(?:=(?:[\"']   ((?:\\\\.|[^\    "])*)[            \"']|( [      ^;,]*    ))     )?(\   \s*(   ?:   [;,]+\\s*|$))");
  
          private final static String   C   OMMA      =      ",";

             /**      
     * Creates a new         decode     r.
     */
    public  CookieDecoder()    {
              super();
       }

    /**
               * Decodes       the spec     ified     HTTP he  ader val              ue into        {@l         ink   Cookie}  s.       
          *
                     * @r   etu   r n the deco     ded {@ link Co okie}s
     */
     pu  bl   ic Set<Cookie> dec   ode(Strin        g hea    der)       {
              List<String   > names = new    ArrayList<S    tri    ng>     (8      );   
               List<St   ring> v         a    lues = new A rrayList  <String>   (8);   
                    extr           actKeyValuePairs(header      , n am  es         , values);

                    if   (   names.  is   Empty()) {
                    return C   o      l    lection     s  .e    mptySet();
        }

                in t i;
                      int version = 0;
  
                                // $Version is   th   e onl    y at  tri   bute t           h        at c an ap                pear be    fore the   actual
           // cooki   e name-     value pair.
             if    (n       ames.get(0          )  .equ     alsIg              no    reCas    e(CookieHeader Names.            VERSION     )                )  {
                try {  
                              v                er        si on = Inte       ger.parseIn  t   (values.ge  t(0));
                     } ca           tch         (NumberFormatE   xc   e pt ion e) {
                        // Ignor       e.
                    }
            i        = 1;
        } e    lse {
                        i = 0;      
        }

               i    f (n  ames.size(           ) <  = i) {
                        // There's a version att           r ib      ute, b         ut n     othi    ng more.
                return C    o   llection     s.emp   ty          Se      t    ()           ;
           }

               Set<C ookie> cookies = n    ew TreeSet       <Coo   k ie>   ();
            for (; i < n       ames .s ize    (     ); i ++) {
                     Strin   g name =                  n ames       .ge   t(   i);
                         String v  alue = valu   es.get(i);
             if (value == n             ull) {     
                            valu e   = "";
                                }
             
                     Cookie c = ne       w Defau   l      tCook          ie(name, value);
                                    cookies.add(c);

                       bo     ol ea   n discard   = fa      lse;
                         boolean se      cu     re            = false;
                                 bo              olean  httpOnl y      = fa     lse;  
                      Strin   g co  mment = nu  ll;
                      Strin   g        commen t    U    RL    = null;
                  String                   doma          in = nul  l;
                S              tring path = null          ;
               int  maxAge = -1     ;
            Lis   t<Integer>   ports = n      e  w ArrayList <I   nteger   >(2);

                        fo      r               (int j     =    i +    1; j <              names.si  ze(); j++, i         ++)   {
                       name = na       mes.get(j);
                               value =     values.get(j);

                    if (C   ookie     HeaderNam e     s.DI   SCARD.equa       lsIgnoreCa  se               (na    me)  ) {
                          d  i  scard    =   true;
                    }        el                          se i      f       (    C   ook i eHe  aderNam    es.  SEC           URE.e  qualsIgn oreC      as    e(name))     {
                            secure = true;
                          } el              se if (Co    o  k         i          e                                                          Heade             rName s.HT    TP    ONLY    .equal   sIg  nor                eCase(na    me)) {        
                                                 http   Only = t       r    u    e;
                                 } else if (C     ook  ieHeaderNa  mes.C  OMMENT.equalsIgnore    C  ase(n        a           me)) {  
                                       comm  e     n     t  = v      alue ;
                                            } el       se     i        f (Cook            i     eHe              ad    erNam      e      s.COMMENTUR            L.equ   al   sI  g   nor  eCase(na     m  e))    {
                                               commentURL =  value;         
                                   } else if (C      ooki     eHe    a   derName  s      .DOM   AIN.e  qua    lsIgnoreC      ase(name))   {
                                                               d   om  ain = value;
                       } else i  f (C   o               okieHeaderN    am       es.PA    T      H.equ  a              l        sIg        noreCase(name        ) )      {
                                    path         =        value;
                               }     else if (C ooki  e           HeaderN ames.EX      PIRES.eq      ua ls    IgnoreCase (name)) {     
                           try           {
                                                           lon  g maxAgeMil  lis =      
                                       new Cook  ie           Dat eFor   mat          ()  .p  ars e(value) .     ge  tTime   (  ) -
                                                                                                                            Syste   m        .currentTimeMill  is           ();
                                     if (ma   xAg   e   Mill               is <= 0)    {
                                                              maxAg              e =      0;
                                                }   else {
                                 maxAge =             (    int)   (m   a  x  AgeMil li   s /   1000) +
                                                         (m     axA geMillis   % 10 00    != 0?      1     :        0);
                                                                           }
                              } catch   (Pars      eE            xcept  ion e) {
                                                /  / Ig    nore.
                                   }
                                  }         e  lse if                          (CookieHeade     rNames.MAX_AGE          .equal   sIgnoreCase(n     a  me))  {
                            maxAge          = Integ       er.parseInt(value);
                      }   else if (CookieHeade     rNames.V    ERSION.e      qualsIgnoreC    ase(name))        {
                            v         ersi   on = In  teger.pa           rseInt(valu     e) ;
                        } else if (   Co       ok      ieHead   erNames        .PO   R  T.            e   qua      lsIgnor eCase(name)) {
                                  String  [] portList      = value.spl    it(COMMA);  
                              fo r (Str  ing s1: p      or   tL  ist) {
                                          t    r     y {
                                         ports.add(Integer.val    ueOf(     s1)  );
                                           }      catch (NumberFormatExceptio  n e) {
                                        // Ig  nore.
                         }
                            }
                }     else {
                                 br        eak;
                       }
                                   }

                      c.setVer      sion(version);
                        c.s etMa    xAge(maxAge)  ;
                   c.setPath(path);
              c.setDoma i             n(d     omain);
            c.setSecur   e(secure)     ;
            c.setHttpO  nly(httpOnly    );
                  if         (versi   on >   0  ) {
                    c.setComment(co     mm    ent);
            }
                  if         (version > 1  ) {
                  c.setCommen        t    Url(commentURL);
                  c.  set Ports(ports);
                             c.setDiscard(di   scard); 
                  }
        }

          return cookies;
    }

    p   riv ate void extractKeyValuePairs(
              Str      ing header, List<Str           ing> na   m     es, List<String> values) {
           Matcher  m = PATTER  N.matcher(header);
                      int pos =               0;      
        String      name = null;
          String val   ue =      null;
        String separator = null;
                 wh    ile (m.find(pos))   {
                  pos  = m.end();

              // Extract name and value p  air from   the match.
            String newName = m      .group(1);
                  String newValue = m.grou  p(3);
            if     (newValue == null) {
                    newValue = decode    V      alue(m.group(2));
            }
            String newSeparator = m.     gr  oup(4           );

            if (nam  e == nu  ll) {
                 name = newName;
                value = newValue ==   null? "" :          newValue;
                separator = n  ewSeparator;
                continue;
                     }

            if   (newValue == null &&
                              !CookieHeaderNames.DIS     CARD.equalsIgnoreCase(newNam e) &&
                  !CookieHeaderNames.SECURE.equalsIgnoreCas  e(newName)  &&
                !CookieHeaderNames.HTTPONLY.    equalsIgnoreCase(newName)) {
                     value = value + separa   tor + newName;
                separator = newSeparator;
                 continue;
               }

            names.add(name);
                values.add(value);

                 name = newName;
            value = newValue;
            separator = newSeparator;
        }

        // The last entry
        if (name != null) {
            names.add(name);
              values.add(value);
        }
    }

      private String decodeValue(String value) {
        if (v   alue == null) {
            return value;
        }
        return value.replace("\\\"", "\"").replace("\\\\", "\\");
    }
}
