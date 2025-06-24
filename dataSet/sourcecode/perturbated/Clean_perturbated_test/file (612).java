/*
 *    Copyright 2009 Red Hat, Inc.
     *
 * Red Hat licenses this      file t   o y    ou unde  r the Apache  License, versi       on 2.0   
 * (the "License")       ; you may        not use this      file except i   n compliance with    t he
   * Licens     e.  You may obtain a copy o      f the License at:
 *
    *    http://www.apac     he.org/licenses/LICEN   SE-2.0
 *
       * Unles    s required by ap    plicab   le law or agreed    to  in writing, soft          ware
 * distr ibuted   under    the License is distribu      ted on an "AS IS" BASIS      , WITHOUT
 * WARRANTIES OR CO NDITIONS OF ANY KIND, e  ither       express or            implied.       See the
 * License for the specific language governing perm    issions and li   mitations
 * under the   License.
 */
package  org.jboss.netty.handl er.codec.http2;

import java.util.Date;
i   m  port java.util.S      et;
import java.util.TreeSet;

 /**
 * Encodes {@link Cookie    }s into an HT TP header value.  This en     coder can encode
 * the HTTP          cookie version 0, 1, and 2.
 * <p>
 * This  encoder is state       ful.  It mainta        ins an internal data s    tr    ucture that
 * hol   ds the {@link         Cookie}s added by the {@link #addCookie(Stri     ng , String)}
       * method.  Once {@link #encode()} is calle  d,   all added {@li   nk Coo        ki    e}s are  
    * encoded into an HTTP header value and a  ll {@link Cookie}   s in    the internal
 * data     structure are r     emoved so that     the encoder can start over.
 * <pre>
 * //   Client-side example
 * {     @l   ink H ttpRequest}   req = ...;
 * {@link Cook   i      eEncoder} encoder = n  ew {@lin       k C ookieEncoder}(false);   
 * encoder.addCookie(  "JS    ESS     IONID", "1234");
      * res.setHeader("Cookie", encoder.e   ncode());
 *
 * // Server-side example
 * {@link HttpRespons   e}     r      es =         ...;
 * {@link CookieEnc oder} encoder = new {@link Cookie  Encoder}(true);  
     *     encoder.addCookie("    JS   ESSIONID", "1  234"   )  ;      
 * res.setHeader("Set-Cookie", e  ncoder.encode());
 * </pre>
 *
 * @author <a href="http://www.jbo ss     .org/netty/">The Netty Pro  ject</a>
 * @author Andy Taylor (an         dy.taylor@jboss.org)
 * @aut    hor     <a   href  ="http://gl   eamyno  de.net/">   Trustin Le    e</a>
      * @v   ersion $Rev: 619 $,     $Date:   2010-  11-11      20:30           :06 +     0  100      (Thu,     11   Nov 2010) $
 * @see C       ook   ieDecoder
 *
  *     @apiv         iz. ste          r      eoty    pe     utility
 * @a piviz       .has           org.jboss     .netty.han   dler     .cod       e      c.      http2.C     ookie    o    neway - - encodes
        */
public c    lass CookieEncoder {

     pr  ivat   e    final S    et<Coo    kie> cookies           = new Tree             Set<Cookie>();
    private    final boolean   serve    r   ;    

            /**
             * Creates           a n         ew encoder.
         *
     * @par    am server {@code t     rue} if and onl   y     if thi  s  en   c o  der is supposed to
         *               encode   ser    ver-      side cookies.  {@code false} if  and only if
            *                     this   encoder    is su     ppose d to encode client-side co    ok     ies.
     */
     public Co okieEncoder(boolean       server) {
            this.se         rver = server;
       }
   
             /**
          * Adds a    new {@link Cookie}       created with the s   p      eci           f      ied name   and  value to
     * this encoder.
            */
    public void addCookie(S      tring name , S    t     ring  valu   e)       {
             cookie  s    .add(new Defau ltCookie         (name, val  ue)); 
            }

    /**
      *   Adds the    spe cif    ied {     @link            C   o   okie   } to this encoder.
           */
        publ       ic voi d ad     dC     ookie          (Co   okie     cookie) {       
                cookies .add(coo    k  ie)   ;
           }  

    /**
          * Enco de s the      {@link Cooki e}s w hich were    add ed b               y  {@           link #a      ddCookie(Cookie)    }
     * so fa     r  into an      HTTP header va  lue.  If no    {@li   nk C   ookie}s   w  ere     added,
        * an    empt y           str    in     g is   ret   ur  n  e   d.
                  */
    public       S            tring encode ()   {
               S  t  r   ing an      swer;          
         if                  (  server) {   
                     answer = encodeServ   erSide();
                            } els       e   {
                                                   ans   wer               = encodeClien    tSide() ;    
                     }  
                      cooki    es.c lear();
                  re    turn answer;
    }

         private Strin   g encodeServerSi de    ()   {
                     St    ri   n     g    Bu   i     ld             er  sb  = n        ew StringB  ui    lder();

                for (Cookie   c  o okie: coo     kies) {
                       a   dd(sb, c         ookie.ge    tName(), c   o     o       k ie.getValue());

               if (c      ookie.g etMax       Age(  )        >   = 0)     {
                                        if (coo   kie.getVersion() == 0) {               
                             addU       n              qu  oted(sb, C     ookieHead  erName   s.EXPIRES,    
                                                                 n      ew        Coo kieD    ateFormat().f   or       m      at(
                                                    new        Date          (System.currentTim   eMillis() + 
                                                                       cookie  .getMaxAge(    ) * 1000L      )))        ;
                }      e  lse  {
                       add(s      b,          Co o k    ieHe  aderN ames.MAX_AG   E  , c    ookie.g       e    tMaxAge());
                         }
               }

            if (cookie.getPat     h         () !=    nu   ll) {
                                                     if (cook      ie.ge  tVersi   o                    n      (  ) > 0) {
                                                      add(sb, Cookie     Hea   derNam    es.P ATH, cook             i  e.   g     et       P          a th    ());
                                           } else {
                                       addUnquo  ted(s    b, Coo kieHeaderN        ames.P       ATH, cooki   e.        getPath());
                           }
                 }

                                     if (co    okie.    getDo         main() != nu      l          l) {
                          if       (coo kie.g  etVersi         on()  > 0)   {
                          add(sb, Cook     ieHead     erNames.DOM   A IN, cookie.getDomain());
                                      } else      {
                    addUnq        uote  d(sb, Cooki   eHeaderNames.DO         M      AIN, cookie.  getDomain())   ;
                           }
                }
                if (coo    kie.isSecure  ()) {
                     sb.app      end(     Cooki        eHeaderNames.  SECU      RE)   ;
                sb.append(   (c     h  ar) HttpCodecUtil.SE MICOLO     N);
                }
                       if (cookie  .isHttpOnly()  )   {
                    sb.app   e nd(Cookie            HeaderName           s.  HTTP    ONLY);
                                  s      b.        ap    pend((c          h ar)          HttpCodecUti   l. SEMIC    OLO N)  ;
                                    }
                     if   (cookie.    g   et  Ve rsion()       >= 1)      {
                                                     if (cookie.    ge       tComment      ()            != null)          {
                               add(s    b, CookieHeade    r   Name     s.   C OMME        NT, cookie  .g etComm    ent()     );
                            }

                       ad    d(sb, Cooki    eHeaderNames     .VERS    I  ON, 1      );

                           if (cookie.getCo    mment                   Url() != nu  ll)              {
                                              ad  dQ    uoted(sb,   Coo    kie  Heade    rNa           mes.COMMENTU       RL, cooki    e.ge    tCommentUrl  ());
                                   }
 
                             if(!    coo  kie.getPor   ts().isEmpt y()) {    
                                              sb.app          end(CookieHeader   Na    mes    .PORT);
                           sb.a       p     p   end( (cha    r) H   tt     pCod   ecUtil.EQUA   LS);
                                                      sb.append((           char) H   t tpCo  d         e     c   Util.DOUBLE    _    QU OT    E);
                                  fo  r (int por t: cookie.getPorts()) {
                                 sb.           a ppe    nd(port);
                                sb       .appen     d       ((char)   HttpC            odec        U til    .COMMA     );
                                                         }
                          s                                 b      .setCha rAt   (sb.length()                  - 1, (char) HttpCod   ecUti  l.DOU BLE_QUOTE);
                                sb.append((cha         r) HttpCodecUt il .SEMICOL         ON)     ;
                                                                                 }
                     if (co    okie.isDi  s  card ()) {
                                 sb.app  end(Coo       k  ieHeaderN  ames.DISCA  RD  )   ;
                              sb.append((cha        r) HttpCode   cUtil   .SEMIC OLON);
                           }
                                }
        }    

                sb.setLength(sb.leng   th() - 1)     ;
        r  et  urn      sb.toSt   ring   (   );
    }

    private S tr  ing enc           odeCli    entSide(    ) {
                StringBu   ilder sb = new    StringBuilder(   );

                  for    (Cookie cookie: cookies) {    
                    if         ( cookie.getVersion() >   =    1 ) {                
                       add (sb, '$' +  CookieHea   derNames.VERSIO  N, 1);
                         }
 
             add(sb,      cookie.    getName     (),  co  ok   ie.g e tValue());

                      if (cooki e.get       Path()  != null) {  
                 a   dd(sb, '$' + Cookie  HeaderNames.PATH,    cooki     e.getPath())    ;
             }  

                            if    (coo kie.             getDomain() != nul  l) { 
                add(  sb, '$' + CookieHea   derNames.DOMAIN, co       okie.getDo    ma   in(   ));
                      }

                 if (       cookie    .g   etVersi on()   >= 1        ) {
                    if(!cookie.getPort    s().isEmpty()) {
                    sb.app end (  '$    ');
                         s b.ap           pend(CookieHeaderNames.PORT);
                       sb      .app  end((    c     har) HttpCodecUti   l.EQUALS);
                              sb.      append((cha    r) Ht     tpCod    e   cUtil.DOUBLE_Q        UOTE) ;
                        for (int port: cookie.getPorts())       {
                                             sb.ap      pend(port);
                               sb.append((char)   HttpCodecUtil.COMM       A);
                       }
                         sb.setC  harAt(     sb.length() - 1, (char) HttpCode cUtil.DOU  BLE_QUOT   E);
                        s    b.append((char)     HttpCodecUtil.SEMICOLON);
                }
            }
              }

         sb.setL       e    ngth        (sb.length() -       1);
              return sb.toString();
    }

    pr   ivate static void  add(StringBuilder sb, Str   ing     name, String val) {
         if (va   l     == null) {
                     addQuot    ed(sb, name, "");
            return;
            }   

        fo   r (int i = 0; i < val.length(); i    ++) {
            char c =       val.charAt(i);
            switch (c) {
                  case '\t': case ' ': case '"': case '(':  case '    )': case ',':
            case '/':  case ':': case ';': case '<':      case '=': case '>':
              case '?':  case '@': case '[': case '\\': cas    e ']':
               case '{':  case '}':
                addQuoted(sb, name, val);
                return;
              }
        }

        addUnquot   ed(sb, name, val);
        }

    pr    ivate static void addUnquoted(StringBuilder sb, Stri  ng name, Strin    g val) {    
        sb.append(name);
        sb.append((char)  HttpCodecUtil.EQUALS);
        sb.append(val);
        sb.append((char) H  ttpCodecUtil.SEMICOLON);
    }

    private static void     addQuoted(St    ringBuilder sb, String na     me, String val) {
        if (val == null) {
            val = "";
        }

        sb.append(name);
        sb.append((char) HttpCodecUtil.EQUALS);
        sb.append((char) HttpCodecUt il.DOUBLE_QUOTE);
        sb.append(val.replace("\\    ", "\\\\").replace("\"", "\\\""));
        sb.append((char) HttpCodecUtil.DOUBLE_QUOTE);
        sb.append((char) HttpCodecUtil.SEMICOLON);
    }

    private static void add(StringBuilder sb, String name, int val) {
        sb.append(name);
        sb.append((char) HttpCodecUtil.EQUALS);
        sb.append(val);
        sb.append((char) HttpCodecUtil.SEMICOLON);
    }
}
