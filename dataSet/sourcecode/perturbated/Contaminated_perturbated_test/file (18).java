/*
 *   Copyrig   ht 2009   R  ed Hat, Inc.
 *
 * Red Hat licens es this f  ile t o     you under the Apache Li        cense   , ver    sion 2.0
 *   (the "Licen           se"); you m  ay not use this   file excep   t in  com pliance with the
 * Lice   nse.         Y      ou may obta         in a    copy of the License at:
 *
 *    http://www.apache.o rg  /licenses/LICENSE-2.0
 *
 * Unless required by app  licable     law or agreed          to in writing, software
 * distributed under         the License   is distributed on an "     AS      I  S" BASIS, WITHOUT
   *  WARRANTI  ES O      R CONDITIONS    OF ANY KIND, either exp ress or implied.  See the
 * License for the specific language governing permi   ssions and limi    tations
 * under the License.
 */
package org.jbo  ss.netty.handler.codec.http2;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Li    s   t;
import java.util.Set;
import java.util.TreeSet;
import java.u      til.regex     .M  atc    her;
import java.util.regex.Patt  ern;
   
/**
 * Decodes an HTTP header value into {@    li    nk  Cooki           e}s  .  Th is decoder can decode     
 * t he HTTP cookie version 0, 1,   a   nd 2.
 *
 * <pre>
 * {@lin  k HttpRequest} req = ...;
 *        String value     = r    eq.getHeader   ("Cookie    ");
 * Set&lt;{@  link Cookie}&gt;        coo   kies = new    {@link CookieDec     oder}().dec   od    e(value);
 * </pre>
 *
 * @author < a href="http://www.jboss.org/netty/">The Netty Project</a    >
 * @author Andy Taylor (andy.taylor@jboss.org    )
 *      @author  <a hr    ef="http://gleam  y    node.net/">Trustin      Lee</a>
 * @version $Rev: 61    9 $, $D    ate: 2010-11-11 20:30:0    6 +0100 (Thu, 1   1 Nov 20  1    0) $
 * @see  C  ookieEncoder
     *
 * @apiviz.stereotype utility
 * @apiviz.has           org.jboss.netty.ha  ndler.codec.     htt     p2.Cook      ie oneway    -       - decodes
 */
publ     ic class CookieDecod         er {
 
    private    final stati       c Pattern PAT  TERN =
            Pattern.compi      le("(?:\    \s|[;  ,])*\\$*([^;=   ]+)(?:=(?:[            \"']((?     :    \\\\.|[^\                                     "])*    )[\"']|([^;, ]     *)))? (\\s*(    ?:[;,]+\\s*|$))")     ;
 
        private fina l st  atic Str    ing COMMA = ",";

     /**
     * Creates a new decode r.
        */
      public Coo   kie    Decoder(    ) {
        su  per();
    }
             
             /**
     * Deco     des the specified HTTP header value into {@link Cookie}s .
     *
     * @r    etu rn t              he decod  ed {@link          Co      okie}s
     */
     public Set<Co  okie> de    code(Strin g          header) {
                L      ist<      String> names =  new ArrayL    is  t<   String>(8)      ;
         Li   st<Strin   g>         values = new ArrayList<Str   ing>(8);
                ex    trac     tKeyVal u ePai rs(header,    names     , v  al              u  es);

        if  (names.    isEmpty(   )) {
                 ret   urn Collect           ions.emptySet();
        }   

              int i;
                  int version = 0             ;

        // $Versi           on is       the only attribut     e        th      at can ap  pear before the actual 
          // coo kie           name-value   pair.
            if                       (names.ge    t(0           ).equals    Ig   n   oreCase (Cookie   HeaderNam es.   VE   R   SION)  ) {
                            t     r y {
                                                     versio    n =      Int          e  ger.parseI  nt(value    s.           get  (0)); 
                     } catch (Numbe rFormatE    xce                  ption      e   )    {
                           //       Igno       re.
                                         }
              i =    1;
              } e                l     se {
                           i =              0;
            }

              if (names.size() <= i) {
                            // There's   a versio        n attribute, but      noth   ing    more.  
             r eturn C     oll        ections.em  p  tySet();
           }

                Set<Cook   ie> cookie      s =             new TreeSe t<C oo        kie>();
        f    or     (; i <     nam  es.si  ze()      ;  i ++ ) {
                        S  tring nam       e = names.get(    i)   ; 
               Strin     g v    alue                  = value     s.get(i);
              if (va lue ==  null   )    {
                                  val     ue =   "       ";
               }
  
                             Cookie        c = new Def   aultCookie(n       a         me, va  lue   );
                  coo     kies.add(c);
      
            boolean dis    card = false;
                    boolean secure = false;
                            boo    l ean h  ttpO   nly = fals  e;
                       String comment = null;  
                              Stri           ng  commentUR  L       = nu                 l       l        ;
                                           Str    ing                 domain         = null;
                 S    tring                path          = null;
                 i    nt max  A      g  e     = -1      ;
                                   List  <Integ        er     >           po     rts = new A     rrayList<Integer  >(2                );        

                     for (in  t j = i +     1   ;     j             < names  .size();       j     ++, i++) {
                         na   me =             name         s.get     (j) ;                          
                              v     al ue = values   .g                        e   t(   j);

                                    if       (Co    okieHeaderNames.DIS        CARD.equalsIg     nore  Case(name)) {
                                         discard =  true;
                                             }     el   se if (     Cooki       eHeaderNam       es. SEC      U  RE.equa          l              sI                gnoreCas         e(  name)) {
                                          secure = true ;      
                              }        el       s                  e       if (C          o   okieHead   erNa  mes.H  TT    PONL   Y                  .equa     lsIgnoreCase(        n am e)) {
                                             h           t      tpOnly        =   true;
                                         } els   e if    (     Coo kie H    eaderNames.COMMENT.equal       s    IgnoreCase   (name)) {
                      c    omment         = value;
                                } else       if (Cooki  eHea     de         rNames.COMMEN  TU   RL.  eq  ual        sIgnoreCase(name))        {
                                     commen  tURL = value;         
                                             }      else          if             (Coo     kie  HeaderNa    mes. D    O     MAIN.equ    al   s Ign    or    e      C             as   e(name)      ) {
                                                      d   omain  =       v                    alu e;
                                      }       else if       (Cook    ie   H  e  a       derN      ames.PAT      H.eq     ua  lsIgnoreCase(na       m  e )) {
                                                                         p  ath = val    ue ;
                                                       }   e   lse if    (C ookieHeaderNam    es.EXPIRE        S.  eq      ualsIg  nor     eCa             se   (name)  ) {
                                t  ry {
                                                                     long maxAg         eMillis  =
                                                                  new CookieDat    eForma t    ().      p   arse(va      lue     ).getTime() -
                                          Syst  em.c    urren   tTi    me       M  i llis();
                                  if   (maxAgeMillis <= 0    ) {
                                                            maxAge =    0;  
                                   }     el  se {  
                                                         maxAge            = (in        t) (maxAgeMill i    s / 1         000     ) +
                                                   (  maxAgeMi llis %      1  000 != 0? 1 : 0);  
                                                }
                                  } catch (ParseException e) {
                                                        //    Ignore.
                                            }
                                   } e  lse  if (CookieHead    erNa     mes.     MAX_A      G E.equ   alsIgn  oreCase(name)) {     
                                    maxAg    e =     Integer.parseInt(value)                ;
                }    e    lse  if (C        ook      ieH     e  aderNa     m      e s.VE   RSION.eq      u    alsIgn       oreCase(    name))   {
                           ve  rsion  = Integer.parseInt(v      alu  e);
                    }       else if (Cooki       e Heade     rNames.POR  T   .equa                 lsIgnoreCas      e  (name)    )        {    
                          S   tring[] portL           ist     = value.       s       pl             it(C  OMMA)     ;  
                                   for (St     ring s1: por  tLi        st) { 
                           try   {
                                     ports.add(I       ntege       r.valueO     f(s1));
                        } catch        (Num    berFormatExcept   ion           e) {
                                                        // Ignore.
                                          }
                             }
                                 } else   {
                                                   b        reak;
                                        }
                  }

            c.setVe   rs    ion(ve rsion);
            c.se  t  MaxAge(maxAg    e);    
                          c.setPat         h(   path);
                  c  .setDomain(domain);
                 c.setSec        ure(secure);
                       c.  setHtt   pOnly(h    ttpOnly);
            i   f (version > 0) {
                   c   .se        t   Com      ment(comment);
            }
            if ( version > 1) {
                           c         .set  CommentUrl(c  o     mm  e   ntURL);
                     c.set    Ports     (ports);
                              c.     setDisca   rd(disc    ard);
            }
        }

                  r    eturn cookies;
    }

    private void extractKeyValuePairs(
            String      hea     der,    List< S      tring> names, List<Stri ng> va  lues) {
          Matcher m = PATTE             RN.matcher(h eader);
        int pos = 0;
        String na    me =  null; 
        String value = null;
          String    separator     =    null;
        while (m.find(pos)) {
                 pos = m.end();

            // Extract name and   value pair from the match.
              String newName = m.group(1);
                S   tring n   ewValue =     m.group(3)      ;
                 if (newValue =    = null) {
                    newValue =   decodeValue(m.group(2));
                 }
            String newSepara to       r = m.group(4);

            if         (name == null) {
                  name = newName;
                   value = newValue     == null? "" : newValue;
                separator = newSeparator;
                continue;
            }

            if (newValue == null &&     
                  !CookieHeade  rNames.DISCARD.equalsIgnoreCase(new   Name) &&
                !Coo   kieH  ea   derN  ames.SECURE.e  qualsIgn     oreCase(newName) &&
                        !CookieHeaderNames.HTTPONLY.equalsIgnoreC     ase(newName  )) {
                      value = value + sep    arator + newName;
                separator = newSeparator;
                continue;
            }

            names.add(name);
            values.add(value);

               name    = newName;
            value =   newValue;    
            s       eparator = newSeparat or;
          }

        // The last entry
        if (name != null) {
            names.add(name);
                 values.add(value);
        }
    }    

    private String decodeValue(String value) {
        if (value == null) {
            return value;
        }
        return value.replace("\\\"", "\"").replace("\\\\", "\\");
    }
}
