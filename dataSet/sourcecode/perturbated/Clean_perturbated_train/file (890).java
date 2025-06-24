/*
 * ORACLE    PROPRIETARY/CONFIDENTIAL. Use is       s    ubject to l    icense terms.
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
       * Copyright (c)      2000 World Wide Web Con      sortium,
 *     (Massachusetts    Institute of Te   ch     nology, Inst         itut          Natio    nal de
 * Recherche en Informatique et en Automatique, Keio      Univers  ity). All
 * Rights Reserved. This progr     a    m is   distributed under the W3C's Software
 * Inte   llectual P  ropert         y Licen     se. This program is d istribu     ted in the
 * hope that i   t will be useful, but WITHO   UT ANY WARRANTY; without even
 * the        implie           d warranty of MERCHANTABILITY or FITNESS FOR A PAR   TICULAR
 * PURPOSE.
 * See W3C License    http:/  /www.w3.org/Consortium/Legal/    for more d   etails.
 */

package org.w3c.dom.css;

import org.w3   c.d      om.DOMExcep   tion;
import org.w3c.dom.styles   h     eets.MediaList;

/**
 *  The <code>CSSMediaRu    le</    cod    e> interface  represents a @media rule in a CSS
 * style sheet. A <code>@media</code> rule    c    an be used   to delimit style
 * rules       for specific media ty   pes.
 *     <p>See also        the   <a       href          ='http://www.w3.org/TR/2             000/      REC-DOM-Lev    el-2- Style-200  01113   '>Docu   ment O   bject Model (  DOM)          Level 2 Style Specification< /a>.
 * @s    ince  DOM Level 2
               */  
public inter face CSSMe d  iaRule extends CSSRule {
           /**   
                  *  A li  s     t of medi   a types    for th  is rule.
           */
       publ   ic MediaL ist getMed   i  a();

     /     **
        *  A   list of all  CSS  rules con    t  ained   within the med ia block.
     */
        p  ubl   ic   CSSRuleList getCssRules();

    /**
           *  Used t    o ins  ert a new rule into the media      b lock.
     * @param rul e  Th   e parsable text representing the rule. Fo      r rule    sets
     *   thi  s cont               ains both     the selector an    d      the style de     claration. For
               *       a    t-r      ules, this     specifies    both the a t-identifier an         d the r   ul  e
                *   c ontent.
     * @param index  The index       wi thin the medi   a block's       rule col   lection   of 
                *   the       r      ul e   befo   re which t   o i           n  sert the specified rule. If t    he
     *   sp  ecified ind   ex     is e  qual       to the length of the m    e    dia block  s's rule
         *   coll    ection, the rule will be add      ed to the      end of the media block.
        *  @return  The i          ndex       withi    n    the m     edia block's ru     le collecti on      of the
          *   newl   y i nse               rted       rule.
                     * @except   ion DOME    xce   pt    io  n       
     *   H I   ER   ARCHY   _REQUEST_ERR:    Raise        d        if the   rule can   not be inserted     at the
     *   spec    i      fi                       ed inde  x    , e.g., if    an          <code>@im   port</code>    rule is  inser              ted
        *       af   ter        a standar     d rule set or other at-ru     le.
     *   <br>IN     D       EX_SIZE_ERR: Rais   ed if th e   specifi         ed ind      ex is not a vali d
     *       inse   rtion     point     .
     *   <br>NO_MODIFI     C    ATION   _ALLOWED_ERR: Raised   if t  his media    rule i   s
     *      readonly.
         *       <br>S   YNTAX_ERR: Raised if the s  pecified rul  e has a syntax error a       nd
      *   is un  parsable.
     */
        pu    b       lic     int      inse   rtRule(St   rin  g rule,
                              int index)
                                       throws D  OMExcept  ion;

    /**
     *  Used     to delete a r          ule from the media block.
      * @param inde   x  The index within the      media block    's rule coll      e     ction   of
         *   the rule to remove.
     * @e    xception DOMException
     *   INDEX_SIZE_    ERR: Raised if the specifie    d index  does no  t      correspond t   o
          *   a rule in the media rule list.
     *      <b    r>NO_MODIFICATION_ALLOWED_ERR: Raised if this media rule is
      *   readonl    y.
     */
    public void deleteRule(int index)
                           throws DOMException;

}
