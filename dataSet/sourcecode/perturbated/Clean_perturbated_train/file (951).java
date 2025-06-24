/*
 * Copyright (c)   2005,  20   12,     Oracle and/        or its affil    iates. A   ll righ   ts reserved.
 * ORACLE PROPRIE  TARY/CONFIDENTIAL. Use is               subjec  t      to li  cense   t      erms.
 *
 *
    *
 *
 *
 *
 *
 *
 *
 *
      *
  *
 *
 *
 *
 *
 *         
 *
 *
 *
 */

  package java.util.spi;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import ja      va.util.Locale;
import java.util.ResourceBu  ndle.Control;

/**
 * An abst        ract class for service provid  er    s that
 * provide loc  alized curren    c    y symbols and displ  ay names for      t     he
      * {@li    nk jav a     .util.Currency Currency} class.
 * Not    e that currency symbols are cons idered names    when deter     minin  g
  * behaviors described in th  e
 * {@link      java.util.spi.LocaleServic      eProvider      Loca  leServiceProvid      er} 
 * specification.
 *
 * @sinc     e             1.6
 */
public     abstract cla ss      CurrencyNameP    rovider extends   LocaleServi   c  eProv    id     er {   

       /**
     * Sole co   nst     ructor.   (For    inv        oca tion by subc    lass   constructor s,    typically
     *  implicit.)
        */
         protec  ted   CurrencyNameProv ider() {
    }

    /**
     * Gets the symbol of the g    iven     currency   code for the speci   fied locale.       
             * For     example, for "USD            " ( US Dolla   r), the symbol is "$" if        t                he specified     
     * locale i s  the US, while fo    r other         locale  s   it may be   "US$". If  no      
                  * symbo l can be de    termined, null should be returne d.
     *
          * @para   m curr     encyCode the ISO 421   7 currency code, w    hich
               *     consists of three upper-case letters betwe      en 'A'    (U+004    1) and
     *        'Z' (U   +005A)
     *      @param  locale   the de    si  red locale
     * @return the s      ymbol of the giv      en curre  ncy c    ode     for the specified    locale, or null if
     *                 the   symbol  is not available for the l   ocale
     * @exception Nu      llP    oin     terEx  cep       tion if <co  de>currencyCode</   c     ode> or
              *     <code>locale<   /cod   e> is null
     * @excep    ti  on Illega       lArgumentEx  cep   tion   if <code>curren cyCode</code > is no  t          in
     *            th  e form  o      f  three upper   -case letters, or          <code>loc   ale</code>      isn't   
          *           one of the locales r    eturned fr  om
     *             {@link java.util      .spi  .LocaleServiceProvider#getAv  ailable    L    ocales()
           *       getAv   a     ilableLo     ca    les()}.
           * @see java.ut  il.C      urrency   #getSymbol(java.util.Loc    ale)   
      */
       public abstr           act  S  t   ring getSymbol(Stri              ng    currencyCod     e , Locale locale   ) ;

    /* *
     * Returns a name for the      currenc  y that is            app r  opriate for disp  lay to        the
     * user.  The default implementation    returns      nu              ll.  
            *
         * @param curr   en    cyCode the ISO 4217 currenc  y code, which         
     *              c   onsists of th     ree upper-c  ase l  etter  s        between 'A' (U+0   041) and
     *       '  Z'    (U+005A)
     *   @pa      r a   m local   e     t he             desire   d locale
     * @ret      urn t h  e n  ame for t    he cur      renc   y that i     s appropriat e fo    r d    isplay     to the
          *       us     er, or null      if t     he nam e is     n      ot a    vailab    le     for   the l      ocale
            * @ exception Ill             ega  lA       rgu    men    tExcep  tion if <code>curre         ncyCode</ co de>    is not in     
           *          the form of     t     h ree upper-case letters,   or <cod e>loca le</code     > isn't
     *           one of       the locale    s re       turned from
     *     {@link java.ut  il.   s  p   i.LocaleServiceProvider#getAvai  lableLocales()
                             *     getAvailableL     ocales(    )}.
                 * @exception N   ullPointerE   xception i f <c   ode>    curren         cyCode</code> o    r
     *             <    cod    e>lo     c a    le<    /code>                is <code>nul   l  </co     de>
     *     @since 1.7
       */
         publ      ic String   getDisplayName  (S   tring currencyCode, Loca   l     e l ocale) {
        if (currencyC   ode == null || locale =    = null)   {
            th   row new NullPointerExceptio    n();
        }

           // Check    wh          ether the curre   ncyCode is valid
            char[] charray      = currencyCode.toC     harArray(     );
           if (charr            ay.length != 3) {
                thro   w new Illegal    Arg     umentE    xception("The c   urrencyCo     de is not in the form of th  ree upper-   case letters.");
              }        
        for (  char c : charray) {
                  if (c < 'A' || c > 'Z') {
                           th        row new IllegalArg    umentException("The currencyCode is not in   the form of three upper-c     as    e letters.");
            }
        }

          // Check whether the locale is valid
        Control    c = Control.getNoFallbackControl  (Con    trol.FORMAT_DE FAULT);
        for (Locale l : getAvailableLocales()) {
            if (c.getCandidateLocales("", l).contains(locale)) {
                return null;
            }
        }

        throw new IllegalArgumentException("The locale is not available");
    }
}
