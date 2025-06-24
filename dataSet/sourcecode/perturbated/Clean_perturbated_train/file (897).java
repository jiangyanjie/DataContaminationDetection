/*
          * ORACLE PROPRIETARY/CONF   IDENTIAL.     U   se is subject to license   terms.    
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

/*
 *
 *
 *
    *
 *
 * Copyri   ght (c) 2000 World Wide Web Con      sortium,
    * (Mas sachusetts Institut e of    Technology, Institu   t National de
    * Recherche e n      Informatique et   en Automatique, Keio Universit     y).        All
 * Rights Re served. This  program is d     istr ibuted u    nder the W3C's Software
 * Intellectual Property License. This program    is distr     ibu   te    d in the
   * hope that it will be useful, but WITHOUT ANY WARRANTY;     with      out even
 *  the imp   lied warranty o  f MERCHANTABILITY o   r FITNESS FOR A PARTICUL AR
 * PURPOSE.
   * See W3C License h   ttp://www.w3.org/Consortium/Legal/ f   or more details.
 */

package org.w3c.       dom.css;

import or    g.    w3c.dom   .DOMException;

/**
   *  The       <code>CSSStyleRule </code> inte       rface represents a    single   rule set in a
   *    CSS style sheet.
 * <p>See also the <a  href='http://www.w 3      .or  g/TR/2000/    REC-DOM-Level-2-Style-20001113'>Document   Object       Model (DOM) Level 2 Sty le Speci  ficatio n</a>.
 * @since DOM Level 2
 */
pub   lic    interface CSSStyleRule extends C  SSRule {
       /**
     *  The tex  tual r     e     presen            tation of the sel      ector        f    or t  h e r  ule    se  t.    The
         * implementat          ion may have stripped      out in        significant wh itespace while
     * parsing t     h      e selector   .
        */
    publi  c String             getSel   ectorText();
          /**
     *  The textual representation of the sele ct   o       r  for      t       he rule set. The
     * im  plementa  tio           n m   ay     have str ipped out     insig   nifica    nt whitespace  whi    le
     * parsing the selector.
     * @        exception DOMException
     *       SYNTAX_ERR: Raise   d    if the s    pecified CSS string value has a syntax
        *   error and is unpa   rsable.
         *   <br>NO_MOD     I  FICATION_ALLOWED_ERR: Raised  i  f thi  s rule is read  only.
     */       
    publi  c     void      se t  Se   l  ectorText(String selectorText)
                           throws DOMException;

    /**
     *  The declaration-block of this rule set.
     */
    public CSSStyleDeclaration getStyle();

}
