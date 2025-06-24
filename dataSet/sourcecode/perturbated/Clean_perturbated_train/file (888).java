/*
        *    ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject     to l  icense     terms.
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

/     *
 *   
 *
                 *
 *
 *
 * Cop      y   right   (c) 2000 World Wide Web Consorti   um,
      * (Massach     usetts Ins      titute of Technology           , Institut Natio   nal de
 * Recherche en Inf   ormatique et en         Aut      omatique, Ke   io University). All
 * Rights Reserved. This pro   gram i    s dist ributed    under the  W3    C's Software
    * Int      ellectual Property License. This program is di  stributed in the
 * h    ope t                  h at it will be useful, but        WITHOUT ANY WA RRA  NTY; without even
   * the implied warranty of MERCH    ANTABILITY or FITNESS FOR A PARTICULAR
   * PURPOSE.
     * See W3C License http:/    /www.w3   .org/Consortium/Leg   al/        for more details.
 */

pa ckage org.w3c.dom.css;

import org.w3c.dom.DO    MException;

/**
 *  The      <code>CSSCharsetRule</code> inte rface repr      esents a @charset rule in   a
 * CSS style sheet. The value of the <code>encod    ing</       code> attribute does
 * not af   f ect   the en     coding of text data     in        the DOM o    bjects; this en     coding is
 * always UTF-16. After a style    sheet    is loaded, th   e value of the
 * <code>encodi     ng</code> attribute i     s the   value found    in the
      * <cod      e>      @c      harset</code>   rule. If there was no <code>@charset</code   > in the
 * origi     nal do       cument, then    no <code>CSSC     harse   tRule</code>  is created. The
 * value of the <code>encodin g</c     ode>        a  ttribute may also be use     d as a h    i         n    t
 * for t      he encoding used on serializat   ion of the style sheet.
 *      <   p> The         value of the @charset rule (and     therefore of the
 * <code>CSSC     harsetRule</code >) may not    corres     pond to the encod    ing the   
 * document actually came in; character encod ing in     for mation e.          g. in an HTTP
 * header, ha    s prio  rity     (see CSS  document represent    ation) b ut t   his i     s            not
 * r eflected                in th  e          <code>CSSCharsetRule<  /code>.
 * <p>See als  o the <a href='http://www.w3  .org/TR/  2000/R EC-DO M-Level-2-   Style-2000111    3'>Document Object Model (DOM) Level 2 S   tyle Specificatio    n</a>.
 * @since DOM Leve   l 2
 */
public interf   ace CS                   SCharsetRu   le extends CSSRule {
    /  **
          *       The encod   ing information used in this <code>@c   harset</co      de> rule.
        */
    public S  tri    ng    getE  ncoding();
    /**
       *       The encoding information used in   thi     s   <code>@charset  </co    de> rule.
     * @except      i   on DOMException
     *   SYNTA  X_ERR: Raised       if the specif      ied e   ncoding value has   a syntax   error
     *   and is unparsable.
       *   <br>NO_MODIFICATION_ALLOW    ED_ERR: Raised if     this encoding rule is
     *      readonly.
     */
    public        void set   Encoding(String enc  oding)
                              throws DOMException;

}
