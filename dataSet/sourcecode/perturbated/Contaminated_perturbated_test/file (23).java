package    org.shawnewald.javatools;

imp     ort java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;

/**
 *    Cookie Tools - HTTP Cookie manipulation metho      d    s.
 * @author  Shawn Ew  ald <shawn.ewald@gmail. com>
  * C      opyright (C) 2009,2010,20    11,20     12 Shawn         Ew   ald
 *
 * This pro      gram is free       software; you can redistribute it and/or
     * modify it under th          e     terms of the GNU G  eneral Public Li   c    ense 
 *    as pub        lished by the Free Software Fo     undation;      eithe   r vers  ion 2
 * of the License, or (at your         optio n)  any later   version.
 *
 * This program is    d  istributed in the hope th   at i    t will be useful,   
 * but WITHOUT ANY     WARRANTY; without even the implied warranty o        f
 * MERC   HANTABILITY or FITNESS FOR A    PARTICULAR PUR   P       OSE.  See the
 *         GNU General   Pub lic License for mor    e details.
 *
 * You shou    ld have        received   a copy of the GNU            Ge  n eral Public License
 * alon    g with this prog         ram; if not,  wri te to the Free  Software
 * Foundation, In      c., 51     Franklin Street, Fifth Fl       oo      r, Bo       ston, M     A     02110-1301, USA.
 */
public final cla       s       s CookieTools    {

    privat    e s       tatic fina     l int cookieAge = 31536000; // one year i     n seconds
    priva  te static final String cookiePath     =  "/";
    pri         vate               stati    c fin  al String empty        = "";

    pri     v ate Cook      ieTools() {}
                         /**
      * Create     a cook ie.
        * @param name < c           ode>String</code>
     * @    param v   alue <co       de>St      rin   g</code>
     * @re     turn cook  ie <code>Cookie</    code>  
     *    /
    public s   tatic Cook      ie cre  ateCookie    (f          inal Str   ing name, final St    ring value    ) {
               final      Cookie  coo   ki     e  = new Cookie(  name,              value   );
                 cookie.  setP  ath(cookiePat          h);
               cookie.setMaxAge(cookieAge) ;
              retur    n c  ookie;   
                }
                  /**
      * Cre     ate a         co   okie.
     * @param na  me <co   d e>String</code >
     * @p      aram value <code>S   tring</code>
                 * @para   m domain <code>String</code>
     * @ret urn    coo             kie <co               de                >   Cookie< /co de>  
         */
     p    ublic st    atic Co      okie     createC  ookie      ( fi   nal String n  am e, final String value,
                  final Str  ing domain) {
             final Cookie cookie = n  ew      Coo      kie    (name,     value);
                                  cookie  .setPa                   th(cookiePath)      ;
               cookie.setMaxAge(cookieAge); 
            cookie  .setDom   ain         (   domain) ;
                         return cookie;
    }      
       /**
     * Cr           e          ate a cookie.
     * @param name  <code  >String</co      de>
            * @param         va     lue <c    ode                >String</code>              
     * @param age <code>int</code>
     *      @param p    ath <co  de>Stri ng</c    ode>
     * @param domai        n <code>Strin g<     /code>
     * @return cookie <code>     Cookie</c     ode>
        */
          public static Co  okie createCookie (final St   r ing n   ame    , fi   nal     Strin g val ue,
                          f  inal int    age, f ina   l  String pat     h       , final S          tr  ing doma in) {
            final       Cook   ie    coo  kie = new Cookie(name, value   );
                cooki    e.    setMaxAg  e(age);
                     cookie.setPath(path  );
                       cookie.setDo  main(      domain);
               return cookie;
    }
    /**
      * Ch    ecks for a cookie by na    me,          if    it exists it r     eturns a U  TF-8 enc od    ed st ring
      *     representatio  n of the cookie  's v    alue.    This method always at  temp        ts  to         UR    L      Decode              
       * the non-n      ull string value b  e     fore r  etu       r     nin         g it    .  
     * @      param cookies <   code>Cookies[]</co     de>, a cook               ie array.
     * @param cook        ieName  <c   o    d  e>Str    ing</code>, cookie nam    e.
                   * @   r       eturn val  ue <code>Str     i   ng</c     ode>, co     okie value.
     */
       public static String getCooki   eValue (final Cookie[]   c    ookies,
             final S   tring cookie   Nam          e)      {
                    f    in al C      oo    kie cookie =    cookieF       i  ndByName(  cookies,        cookieName);
        re    tu    rn (!cookieIsNul lOrEmpty(cookie))
                     ? Txt.     urlD ecod     e(coo    kie.getValue())
                : empty;
                   }
          /**
       * Returns a cookie with the name supplied    in the          argument or   <co    de>null    </c   ode>    
     * if the cookie      doesn't     exist .  
           * @param cookie  s <code>Cookie[]</c  ode   >, a cook   ie               arr     ay.
     *  @param   cookieName <       code>String</c     ode>, co     okie name         . 
         *     @return <   code>Cookie</c ode>
       */
      publ     ic static    Co  okie getCookie (fi   na   l Cookie[] co    okies,
                     f       i   nal Strin    g     cookieName) {
        retu  rn cookie  FindByName   (co   okies, cookieName);
             }
    /**
     * Return <code>C o  okie[]</code> array      as a <cod  e>Map</code>.
        * @param cookies <code>   Cooki   e[]</cod  e>
     * @return map <cod       e>Map</c ode>
     */
    public s      t      atic Map      <Strin g, String> cookieMap  (final  Cookie[] coo kies)  {
                    final Map<       String, String> ma  p = new HashMap<String, S   t   ring>   (cookies.length)  ;
              for ( final Cookie c :       cookies) {
               map.   put(c.getNa       me(), c.getVal         ue());
           }
              return ma    p;
       }
    /**
     * Updates       a cookie's va   lue.
         * @param cookies <code   >C1ookie[]</code   >,       cookie arr ay           .
        * @param c ookieName <code>St     ri    ng</code>     , co  okie name.
         * @par       am co  o     kie    Value <c ode>String</c      o   de>, co        okie    value.
     * @return cookie <code>Cook       ie</code>
         */
    pub      li   c st  atic C   ookie u  pdateCookie (final Cooki   e[] c         ookie    s,
            final String cookieN   a  me, fin   al String cookieValue) {
        f      in         al Cookie cookie = cookieFindByName(coo    kies, c     ookieName);
                   cookie.setPat     h(cookieP  ath);
              cookie.se  tMa xAge(cookieAge)    ;
             co  okie.setValue (c     o okieValue);
        retu  rn co  o       kie;

    }
     /**
        * Upda tes  a cook   ie's value.
            * @param cookies <code>Cookie[]</code>,     cookie array.
     *   @param cookieNam  e <code>Str   ing</  code>  , c             ookie name.
     * @param cookieValue <cod          e    >  String</c    ode     >, co okie value.
                   *      @param co  ok  ieDomain <code  >String</c    ode>       , co   okie domai   n.
                   * @return coo          kie                 <code>Cooki   e</co     de>
     */
    public static Cookie upda     teCookie      (final Cookie[] cookies,
                final       Stri    ng c     ook       ieName,    fi  nal String cookieV  alue,
            fi  nal     St  ri     ng coo        kieD oma  in) {
             final C ookie cookie    = coo  k ieFindByN ame(cookies, cookieName)      ;
        c        ookie.s        etPath(cookieP               ath)  ;
                coo     kie.setMaxA  ge(cooki     e   Age     );
        co  ok    ie.s    etValue(cookieValue);
                  coo  ki  e.setD      omain(cookieDomai       n);
          retu           rn cookie;
       }
      /**
     * Upd     ates a cooki       e's v    alue.
       * @param cooki  es <cod   e>Cookie[     ]</co     de>,   coo  kie   arrray.
     * @param cookieName    <code>String</c         ode >, cookie nam   e.
     * @p ara     m coo kieV   alue <code>String</code>, cookie value.
     * @param cookieAge     <  code>int  </code>, age of cookie      in second   s.
     * @param cookiePath <code>String< /code>, c    ookie url path.
       * @param coo     k  ieDom    ain  <code>String<   /code>, cookie dom   ain.
          * @retur   n c          ooki       e    <co     de>Cookie<  /code>      
     */
         public static Coo    kie updateCookie    (final Coo kie[]  cookies,
            fin   al String cookieName, final String     co   okieValue,  
            f    inal int     c  ookieAge, final Str ing cookiePath,
               f    in        a    l String cookieDomain) {
             final   Cookie cookie  = cookieFindByName(           cooki  es, cookieN   ame)     ;
        c    ook  ie   .setV    alue(coo     kie   Value);
        cookie.set   Ma     xAge(cookie   A     ge);
            coo  kie.  setP  ath(cookieP       ath);
             co               okie.setDomai   n(cookieDomain)        ;
        return cookie   ;
    }
    /**
     * Deletes      a coo kie   by          name.
     * @     par   am cookiename <code>String</code>, cookie name   .
            *  @ return cookie <code>   Cook   ie</code>
      */
       publi   c static Co  okie deleteC    ookie        (final     Strin   g cookiename) {
        fin al Cookie coo   kie     = new Cookie(cookiename, em          pty);
        cooki  e.se  tPath(co  okiePath);
          cooki         e.set  MaxAge(0);
           retur        n cookie;   
    }
    /**
     * Dele tes a  cookie b  y     name.
                           * @par     am        cook iename <c   ode>String</code>, c   ookie na        me.
     * @param cookieDomain <code>Stri    ng</code>, cookie doma   i n.
     * @r                           eturn cookie <code>Cookie</code>
      */
          p      ublic static Cookie deleteCookie (final String cookiename,
                        final String        cookieDomain) {
              final Cookie   cookie =   new Co    okie(cookienam     e,       empty);
            cook  ie.s  etD     omai    n(cookieDomain)   ;
        cookie.setP      ath(cookie     P   at      h);
         cookie.setM axAge(0);
        return cookie;
    }
    /**
     * Deletes a cookie b  y n    ame
     * @param cookiename <code>Stri  ng     <    /code>,         co  okie name.
     * @param cookieDomain <code>String</c          ode>, cookie domain.    
               * @param cpath       <code>Stri   ng</ code     >, cookie u  rl pat  h.
     *      @return cookie <code>Cookie</code>
      */
    public static Cookie deleteCookie (final Strin g cookiename   ,
              final String cookieDomain, final   String cpath) {
           final Cookie      cookie = new Cook        ie(           cookiename, empt     y);
        cookie.setDomain(cookieDomain);
        cookie.s     etPath(cpath);
                  cookie.setMaxAge(0);
        return cookie;
    }
        /**
     *     Tests if a cookie by the given     name exis    ts
     * @param cookie   s <cod     e>     Coo   k     ie[]</cod   e>
     * @param cookieName <     co  de>Strin      g</    code> the name of    the cookie.
     * @return <code>boolean</code>
     */
    public st    atic boolean cookieExists (final Coo    kie[] cookie s,
            final String c      ookieName) {
        return (cookie FindByName(c  ookies, co  okieNam  e)  ! = null);
    }       
    /**
                      * Tests if a <   code>Cookie</c    ode> is <code>null</cod  e> or if it's value is   empty .
              * @par am cookie
     * @retu    rn <code>boolean</code>
     */
    publ  ic static bo olean cookie   IsNullOrEmpty (final Cookie cooki   e)    {
        return (cookie    == null || cookie.getVa  lue().isEmpty()); 
    }
    /**
     *   Returns a <code>Cookie</code> named afte     r      <co   de>String</code> name from
     * <code>Cookie[]</code> array
     * @param cookies <code>Cookie[]<code>, a c   ookie array.
     * @param name <code>String</code>, name of cookie.
     * @r     eturn cookie <code>Cookie</code>
     */
    private static Cookie cookieFindByName (final Cookie[] cookies,
            final String name) {
        if (cookies != null && cookies.length > 0) {
            for (fina    l Cookie c : cookies) {
                if (c.getName().equals(nam    e)) {
                       return c;
                }
            }
        }
        return null;
    }
}