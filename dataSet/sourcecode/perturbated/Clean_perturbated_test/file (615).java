package org.shawnewald.javatools;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;

/**
   * Cookie   Tools  - HTTP   Co     okie man  ipulation methods     .
 * @auth  or        Shawn Ewald <shawn.ewald@gmail.com>
  * Copyright (C) 2009,2010,20    11,20  12 Shawn    Ewald
 *  
 * This program is free sof        tware;  you can redistribu       te    it and    /or
 * modify it under the terms of   the GNU General Public L     icense
 * as    published        by the     Free Software     Foundation; either version  2
   * of   the L icense, or (at yo   ur option) any later ve   rsion.    
 *
 * This program       is distributed in the      h   op  e     that it will be useful,
 * but WITHOUT ANY WARRANTY; withou    t even th  e  implied war      ran   ty of
 *    MER CHANT   ABILITY or FITN   ESS FOR  A PARTI    CULAR PURPOSE.  S   ee the
  * GNU Ge   neral      Public L      icense for more details.
 *
 * You sh   oul   d ha    ve rece      i    ved    a copy    of the GNU General    Pub    lic License
 * along with th       is program; if not, write to the Free Software
 * Foundation, Inc.        , 51 Franklin      Street,   Fifth Flo          or, Boston, MA  02110-1  301, USA.
      */            
public          final    c        l    ass      CookieT   ools {

    p   riv  a   te static fina l int cookieAge =    31536000;   /              / one year   in seco   nds
    private sta  tic fin      al S tring cookiePath = "   /";
       p   rivate s    tatic f    inal String empty = "";

       private Cookie  T     ools()  {}
    /**
               * Create a coo  ki   e.
         * @pa    ram name <code>String</c     od e>
     * @par   am valu    e      <code>String</code> 
         * @return co        okie <code       >C ookie  </code>
       */
    publ   ic stati c Cook    ie   create Cookie   (f inal St      r      ing name, final    String      valu     e) {  
                   fin   a    l       Cookie c   ookie    = new   Coo            kie(name, valu   e);
                  cook ie.set     Path(      cookiePath  );   
                cook ie.setMaxAge(cookieAge);  
                  return cookie;
    }
          /**
     * Create a cooki      e.
     *   @param na me <code>String</code>
     * @param value <code>Strin             g</code>
     * @     para   m domain <code>St       rin        g</code>
         *                 @retur  n cookie <cod    e>Co okie</code>
            */
        pu      bli      c static   Coo       kie createCookie (f  inal St ring name, final Strin   g value ,
            final        Strin g dom   ain) {
              final Cookie cook i        e         = n ew     Cookie   (n     a     m  e, value   );
            cookie.setPath(c ookiePath);
                cook  ie   .    setMaxAge(cookie Age);
                 cooki  e.setDom   ain(d   omain);
                       r eturn cookie;
    }
    /**
             * Create   a cook  ie.
     * @param         n      ame      <          code>Str     ing</cod   e   >
     * @param value <code >String</code>
     * @par am a       g        e <cod          e>i       nt</code>
       * @  param path <code>String</code>
        *    @param domai      n <code>String</code>  
     * @retur    n      cookie <code>Cooki       e</code>
     */
    publi  c  static     Coo kie cr  eateCoo     kie (fina   l   St   r  ing name,   final     Str       ing   value,
                    fina  l int age, fin    al   String              path, final Str       ing d  omain)        {
              final      Cookie coo   kie = ne        w    Cookie(name, value)   ;
                 cookie.se        tMaxAge(age);
            cookie.setPath(     pa     th);
                cookie.    setDo               main(do    main);
                return cookie;
     }
      /**
     * C    hecks  for a c  ookie by      na     me, if it exists i  t ret   u   rns     a UT  F-8 e   nc od  ed    string
     *    re    presentation of   the cookie      '   s valu  e. This method alwa     ys a   ttempts to URLDecode
     * the        non-null str   in    g     val    ue      before returning      it.
     * @param coo    kies <code>Cooki  es[  ]</code>, a cookie array.
     * @param cookieName <code>String< /c  od      e>, cookie name.
     * @return value <cod e>Str     ing</code>, cookie value.
     */
       public          s       tatic Strin      g getC ookieValu   e (final Cookie[    ] cook     ie  s,
              fina                l String cookie  Name) {
         final Cook ie cookie      =        c  ookieFindBy      Name(   c  ookies, cookie   Name);
                  retur  n (!coo   kieIsNullOr   Empty(cook  ie))
                ?         Txt.u   rlDecode(co    ok    ie.  getValue()   )
                              :  empty;
        }    
    /**
     * Return     s a cookie   with th   e name supplied in the arg     um  e      nt or <code>null</   code>
        * if the cookie doesn'    t exist   . 
     *  @p         aram cookies   <cod         e>Cookie[]</code>, a cookie       array.
        * @p    ara   m     cooki    eName <c      ode>String</code>, cookie name.
       * @return    <code>Cookie< /code>
     */
    public static Cookie getCooki        e (final         Coo k    ie[] cookie         s,
                final St   ring c oo    kieNam     e) {
        return cook           ieFindByName(cook        ies,   cookie Name);
        }
    /  **
          * Return <code>Cookie[]</code> array    as      a <code>Map</code>.
     * @param cookies <c     od    e>C  oo    k    ie[]</code>
                 * @return map <c  o      d        e>Map</code> 
         */
      pub lic static Map    <String, Str  ing> cookieMap (fina           l Cookie [] cooki    es   ) {
          fi   nal Map<String, Strin g> m      ap = new   HashM     ap<String, String                 >(co  okies        .lengt h     );
                    for (   f   in   a  l    Cookie c : cook   ies) {
                  map   .put(c.g       etName(), c.g etValue    ());
               }
        return map;
    }       
    /*     *
     * Updates a cooki  e's value.
     * @pa   r   am      cookies  <code >C1ook  ie[]</c   ode>    , cookie ar   r   ay.  
        * @param cookieN   ame <code>String </co   de      >, coo  kie name.
                           * @    param     cookieValue <code>S  t ring</code>,   coo     kie               value       .
       *    @r       etur        n cooki  e <code>     Cookie</c ode>
             *  /         
    public sta tic Cookie updateCookie (final Cookie[] cookies,
                    final  String cookieNa     me, fi      nal Stri  ng c     ooki  eValue) {
        final Cookie cookie = c ookie           FindByName(    cookies, cookie   Name);
        co  okie.s  etPath(cookiePa  th);
        cookie.setM axAge(cookie   Age)  ;
             co   okie.se   tValue(    cookie    Va   lue);    
                  retu         rn          cookie;

    }
    /**
     *   Upd            ates a c       ookie's value    .
     * @param co     okies <code>Cookie[]</    code>, cookie      array.
     * @par     am cookieNam    e <    code>String</co  de> , cookie name .
     * @param cookie Valu      e <c  ode>String</code  >      ,   cookie       val ue.
     * @para    m cooki           eDomain <code>String    </c ode>, coo     ki     e domain.
     * @return co   okie  <code>Cookie</code>
     *     /
      public       stati    c Cookie     upd      ateCookie (f  i    n                al Cookie[]        cookies  ,
                  final String     c    ookieName, fin al Stri     ng cookieValue,
                  final St ring c      o  okieDomain) {
             final C     oo  ki  e cook ie = cookieFi   nd          ByName(co   okies, cookieName);
             cookie    .   se       tPath(coo kiePath);
            cookie.setMaxAge(cookieA  ge)  ;
          cookie.setValue(cook     ieValue)      ;
          cookie.    setDomain(cookieD  omain);
           return cookie;
            }
    /**
     * Upd  ates a     cooki          e's value.
     * @pa      ram co    okies <code>Co  okie[]</  code>, cookie arrray.
      *     @param co  ok        ieNam   e <code>String</code> , cookie name.
     * @p          aram cookieV    alue <cod  e   >String<  /code>, cookie value.
     * @param cookieAge <c      ode>int</co     de>, age of co  okie i    n se            conds.
     * @p       aram cookie Path <code>String</code>, cook         ie url pat     h.
         * @param    cookie   Do  main <cod        e>   Str       in  g</code>,    cookie do   main.
     * @r   et   urn cookie <code>Cookie    </     c   o  de>
     */
    public sta  ti    c Cookie update  Cook      ie ( fina l Coo  k  ie[] cookies,
            fin     al Stri ng c     ookieName,    final String cookieV     a     lue,
                   fi   nal       int cook    ieAge, final String    coo    kie         P      ath,
               final     String cookieDom a  in) {
                final Cookie c    ookie  = cooki  eFin dByNam   e(coo           k       ies,   cookieName);
                          cookie.setValue(cookieValue);
               cook    ie.setMax     Age(cooki  e    Age);
           cookie.se tPath(coo      kiePat   h);
             cook   i  e. setDomain(c   o              o  kieDom    ain);
             r eturn cookie;
          }
    /**
     * Deletes a cookie by      na  me.
     * @param       coo      kiename     <cod  e>String</code>, cookie name     .
     * @return coo         kie <code>Cook   ie</code>
            */
    public stat   ic Cook ie del  eteCoo   kie (final Stri  n   g cookiename) {
        final Cookie cookie = new   Cookie   (cookiename, empty);
        cookie.setPat   h(cookiePath); 
             cookie  .set  MaxAge(0);
          re     turn c ookie     ;
    }
       /**
          *      Deletes a cookie by name.
         *      @par   am cookiename <code>String</code>,        cookie nam     e.
         * @   param cookieDomain <code>String</       code>, c  ookie domain.
       * @r  etu       rn cookie <cod  e>C  o oki e</code>
            */
           public static Cookie delet   eCookie (     final String c   ookie    name,
                  final String cookieDomain) {     
        final   Cook   ie       cookie = new          Cook  ie(cookienam  e, e    mpty       );  
             cookie.setDomai  n(cookie Domain);
        c          ookie.setP    ath(co okiePat       h);
        cookie.set MaxAge(0  );
        return      cookie;
    }
    /**
       *    Deletes   a coo     ki e by name
     * @param cookiename <code>String</     code>, cookie name.
     * @param cookieDomain <c    ode>String</code>   ,   cookie domain.
     * @param cpa    th    <code>String</c        ode>, cookie url path.
       *      @return cookie <code>  Cookie</code>
     */
    public static Cookie deleteCo        okie (final String cookiena  me,
                 final String cookieDomain, final Str       ing cpath)   {
            final Cookie       cookie = new     Cooki           e(cooki   ename, empty);
        cooki   e.setDomain(coo   kieDo    main);
            cookie.setPath(c    path);
        cookie.setMaxAge(0);
                     return cookie;
      }  
    /**
     * Tests      if    a cookie by th  e        given name exists
     * @param cookies <  co       de>Cookie[]</code   >
     * @param cook   ieName <co  de>String</code> t          he na      me  of the cookie.
         * @return <code   >boolean</c  ode>
        */
    public static b  oolean   cookieExists   (fin      al Cookie[] cookies,
            final String     cookieName) {
        return (cookieFindByName(cookies, cookieName) != null);
    }
      /**
     * Tests if a <code>C  ookie</code> is <code>null</ code> or if it's value is empty.
     * @param c   ookie
     * @return <code>boolean</code>
     */
       publi       c static boolean      cook  ieIsNullOrEmpty (final  Cookie cookie) {
        ret        u   rn (cookie == nu    ll || cookie.getValue().isEmpty());
    }    
    /*  *
        *       R   eturns a <code>Cookie</code> named af    ter <code>String</code> name from
     * <co de>Cookie[]</code> array
     * @param cookies <cod     e>Cookie[]<code>, a cookie array.
     * @param name <co    de>String</code>, nam      e of cookie.
     * @return cookie <code>Coo      kie</code>
     */
    private static Cookie cookieFindByName (final Cookie[] cookies,
            final String name) {
        if (c ooki  es != null && cookies.length > 0) {
            for (final Cookie c : cookies) {
                if (c.getName().equals(name)) {
                        return c;
                }
            }
        }
        return null;
    }
}