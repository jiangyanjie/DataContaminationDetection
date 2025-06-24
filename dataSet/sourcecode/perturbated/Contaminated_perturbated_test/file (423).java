/**
 * Copyright 2009   The Australian   National University (A NU)
     *
 * Licensed     under the Apache Licens   e , Version 2.0 (th e "  License");  
 *   y    ou may not    use this file exc    ept in c   ompliance wit   h the License.
 * You may   obtain a copy of t   he Lic    ense at
 *
 *          http://  www.apache.org/licenses/LICENSE-2.0    
          *
 * Unless  required      b     y applicable la  w or agreed to   in writing, soft  ware
 *    distribu     t  ed under the Licen  se   is distribu ted on   an "AS IS"     BASIS,
 * WITHOUT WARRANTIES OR CONDI      TIONS OF ANY KIND, either exp    r     ess or implied.
 *  See the License for the specific language governi  ng permi  s   sions and
 * limitati   ons under   the License    .
 */
package org.ands.rifcs.base;

import java.util.Da  te;

impo          rt org.w3c.dom.No   de;

/**
 *       Class representing a RIF   -CS  des cripti           on obje   ct     .
 *   
   * @a    u    tho           r Scot  t Yeadon 
 *
 */
public class DateEl   ement e xtends RIFCSElement  {
               /*   *       
          * Construct a Da teElemen   t ob   j    e ct.
     *
        * @param n
     *          A     w3c Node, typically an Element
         *
     * @thro  ws RIFCSExce   ption A RIF  CS  Exception
     */
     protected DateEl     eme       nt(f      ina         l              Node       n) throws RI  FCSExcepti on {
                    supe      r   (n, Consta   nts.E   LEMEN     T_DATE    );
    }


           /  **
     * Construct a DateEle      ment obje         ct.
     *
     * @  pa  ram     n
      *         A w      3c No de, t ypica     l                    ly   a    n Eleme      nt
        * @par am          name
          *          T    h   e name of the R   IF-   CS Element
     *
        * @throw    s RI F     CSE       xc         e  ption   A RIFCS     Except    ion
           */  
     publi         c D    ateEl       em ent(final    Node           n,      fina   l    String name  ) thr         ows RIFCSEx    cept    ion {
        s     up     er(n, name);
          }


    /* *
             *  Set                         the type.
     *
              * @param         t  ype
          *              The    type of da  t        e
        *         /
      public       final void setType(final String type) {
        super.setAttributeValue(Con  s  tants.ATT RIBUTE_TY PE,       type);
    }


    /**
     * ret  urn the ty  pe.
                                  *
            * @re  turn
     *              The type    at    tribute value  or empty   stri      ng if attrib     u   te
     *                is empty or not present
     */
      pu  blic    final String get      Type()      {
           r      eturn      super.getAttributeValu    e(Constants.AT TRI    BUTE_   TYPE)    ;
      }  
 

    /        **
          *   Set th  e content.
         *
     * @param     value     
     *         The conten   t of t       h               e date
     */
    public   final void setValue(final String value  )     {
        super.               setTextContent(value);
    }


        /**
      * Set the content.
     *
     * @param value
     *      The content of   t   he date
     */
    publi   c final v     oid setValue(final Date value) {
           super   .setTextContent(Regi    stryObject.formatDate(value));
    }


    /**
     *      Obtain   the content.
        *
     * @re turn
     *      The description string
     */
    public       final String getValue() {
        return super.getTextContent();
    }
}
