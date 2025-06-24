
package org.kuali.rice.kew.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import    javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java cl   ass for ActionS  etType complex type .
 *      
 *  <p>The fo      llowing schema fragmen    t      specifies the expec ted content c  ontained     within this class.
 * 
 * <pre>
 * &lt;complex  Type nam        e=    "Actio  nSetType">
 *       &lt;complexCo  ntent>
 *     &lt;restric  tion base= "{htt      p://ww   w.w3. org/20   01/XMLSche        m  a} anyT  ype">
 *       &lt;sequence>
 *         &lt;element name="actionSe   tList" t ype="{http://www.w3.org/2001/XMLS chema     }string" maxOccurs="unbounded" min   Oc    c           urs="0"/>
 *         &lt;any p     rocessConten   ts='skip' name    sp        ace          ='##other'    maxOccu    rs="unbounded" mi  nOccurs="0"/>
 *          &lt;/sequence>
 *         &lt;/restri   c    tion>
 *    &lt;/co    mplexContent>
 * &lt;/c omple    xType>
 * </pr   e>
    *   
 * 
 */
@XmlAccessorTyp      e(XmlAccessType.FI   ELD)
@XmlType(name = "ActionS e tType", propOrder    = {
    "actionSet   List      ",
        "any"
})
pu   blic     class ActionSe    tT              ype {

      protected List<String> acti onSetList               ; 
       @Xm lAnyElem  ent
         protect     ed List<      Elem     en   t>    an     y;

    /**
              * Gets the va lue of   the action   SetL       i st prope rty.          
     * 
         * <p>
     *  This           access    or m   ethod        return s a reference to the live        list,
                  *        not     a snapshot.      Theref      ore a   ny m       odifi  cation  you make    to the    
     * retur         ned list w      ill     be present i     nsi       de the JAXB        o   bject.
      *    This is w hy there is not a <  CODE>       set<   /CODE>    metho d for         the ac   ti         onSet  List property.
     * 
                    *          < p>            
           *        Fo     r examp   le, to add a new item, do as   foll ows:
         * <p      re>
       *     get   ActionSetList    ()           .add(newItem);
           * </pre>                   
     *     
     * 
     * <p>
       * Objects  of the f            ollowing type(s) are allowed in t   he    list
     *              {@link S   tring }
     * 
     * 
                */   
        public List<St  ring>    ge         tActionSetList() {
            if (actionS     etList ==   null)       {
                  actionSetLis  t = new Ar  rayL     ist<String>    ();
        }       
                             retu      rn t   hi      s.action     SetLi     st;
       }

    /**
          * Gets     the value of th e  a  ny property.
     *   
            * <p>
     * Th     is  ac    ce ssor meth   od returns    a refe   re   nce to th      e live lis      t   ,
       *     n    ot  a     snapsh   ot. Th  erefore any modifi cation        y   o  u m      ake to the
     * returned list w  i ll be pres      ent  inside the JAXB object.
     * This is why th  ere         is not a <CODE>se      t</CODE>      method for      the any prope    r    ty.
     *    
               * <p>
     * For      exampl   e, to      add a new i  t  em, do as      follows:
     * <pre>
     *    getAny().   add(newItem);
     * </p re>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowe    d in the lis    t
     * {@link Eleme   nt }
     * 
     * 
     */
    public List<Element> getAny() {
        if (any == null) {
            any = new ArrayList<Element>();
        }
        return this.any;
    }

}
