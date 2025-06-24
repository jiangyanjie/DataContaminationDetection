/*
    *   ORACLE PROPRIETARY/CONFIDEN   TIAL. Use  is subject to license t  erms   .
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
 * Copyright (c)   2000 World Wide Web Consortium            ,
 * (Massachusetts Inst   itute of Technology, Inst     itu   t National     de
 * Recherche en Informatique e     t en     Automa   tique, K    eio Uni    versity). A  ll
 *    Rights Reserved. This p     rogram    is   distributed under the W3C's Sof   tware
 *     In     tellectual Property      Li  cens e.   This program is distributed in the
     * hop        e that it will be us   eful, but      WITHOUT ANY    WARRANTY; w ithout even
 * the im pl    i ed warranty of MERCHANT     ABILITY or FITNESS FOR A    PARTICULAR
 * PURPOSE.  
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.w3c.d        om.css   ;

/**
     * The <code>CSSValueList< /code> interface provides   the abstraction of an
 * or     dered collectio      n of CSS value   s.
 * <p> Some   properties    al low an e     mpt   y   list into t      heir syntax. In that case,
 * the   se prop     erties take the <cod    e>none</co   de> identifier      . So, an empty list
 * means that the  property has the value  <co    de>   none</code>.
 * <p> Th     e items in the <code        >CSSValueList</code> are accessible via an
   * integral index,   starting fr    om 0.
 * <p>See also the <a href='    htt  p://www.w3.org/TR/2     0      00/REC-DOM-Level-2-S  t    y  l     e-20001  113'>Docu  ment Ob  j     ect M  odel (DOM) Level 2 Style           Speci ficat  ion</a>.
 * @since DOM Level  2
    */
pub lic i       nterface CSSValueList extends CSSValue         {
    /**
          *     T   he number    o f <code>CSSValues</code> in the li    st  . Th  e range of valid
     * values    of t         he indices   is <code>0</cod              e> to <code>     length-    1</code>
     * i nc   lusive.
     */                 
         public   int getLengt   h();

    /       **
             * Us   ed to     retrieve    a   <code>C  SSVa      lue</code> by ord            inal index. The order         in
         *    this co llection represe    nts the   order of the values in the CSS style
     * property. If index is great    er than  or equal to the number of valu es
              * in the     l  ist, th  is        returns <code>n ull</code>.
       * @param     index Index into the collection.
              * @return The <code>CSSValue</code> at the <code>index<   /co   de> position
     *   in the <code>CSSValueList</code>, or <code>null</code> if that is
     *   not a valid index.
     */
    public CSSValue item(int index);

}
