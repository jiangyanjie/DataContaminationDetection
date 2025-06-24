
package ExperianLtdCompanySearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import   javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
       *   <p>Ja   va clas    s for ConvictionDetail    s complex t    yp         e.
 * 
    * <p   >T   he following schema f   ragment         spe    cifies   the   expected conten t contained within this c  la  ss.
 * 
 * <pre>
 * &lt;complex    Type name="ConvictionD   etails">
 *   &lt;complexCont    ent> 
 *       &l t;restriction base="{http://www.w3.org/20  01/XMLSchem      a}anyType">
 *          &lt;sequence>    
 *         &lt;element name="Date" type="{http://sc hema.uk.experian.com/experian/  bi/generic  /b    sns/v100/basetypes}   CCYY   MMDD" min   Occurs="0"/>
  *         &l    t;element name="Reason" type="{http: //www.w3.o  rg/2001/XMLSchema}stri   ng" mi     nOccurs="0"/>
 *         &lt;element n   ame="Fine" type="{http://      www.w3.org/2001/XMLSchema}strin       g" minOccurs="0"/>
 *              &lt;element        name="Costs" type="   {http://www.w     3.         or           g/2001/XMLSchema}string" minOccu     rs="0"/>
 *       &lt;/sequence>
 *        &lt;/restri    ct      ion>
 *   &lt;/comple   xConten           t>
    * &lt;/complex   T ype>
 * </pre>
 * 
 * 
 */
@XmlAccess      orType(XmlAccessT  ype.FIELD)
@XmlTyp e(na me = "Con   victionDetails     ",     propOrder = {
    "d  ate    ",
    "reason",
    "fine",
    "  cost    s"   
})   
public class Convic  tionD    etails     {

    @XmlElement(          name =     "Date")  
              prot     ec       ted CCYY      MMDD dat    e;
              @    XmlElement(name =          "Reason")         
    pro     tected Stri ng       reason;
        @XmlElement(name   =         "Fine")
    prot     ected Stri      n         g fine;
    @XmlEl  eme  n  t(name = "       Costs")   
    prote  cted String co    sts;
  
             /    **     
                    * Gets    the value of the date property.
     *      
     *           @retu   r    n
     *                 poss ibl     e        o      bjec        t is
     *      {@link CCYYMMDD }
      *      
         *     /  
             pu            b     lic C      CY   Y      MMD  D                       ge     tDate()   {   
                  r  eturn da        te;
    }     

            /* *
     *  Sets th    e value of the dat  e        proper  t     y.
        * 
         * @param              value
              *     allowed obje ct is
               *           {@l    ink       CCYYMM  DD         }
       *        
     */  
               public voi   d se        tDat e   (C     CYYMMDD va  lue) {
            th  is.      date = valu    e;
    }

                 /       **
                             * Gets the v   a   l     ue     of the re   ason proper ty.
                  * 
                           *   @retur                n 
           *     possible object is
     *     {@link String }
     *          
     */     
    publ   ic St r   in g      g   etR    e                as   o        n() {
        retu       rn    r e   ason;
    }

    /      **  
        * Sets the value of t  he    reason    property.
           * 
     *       @pa  ra        m v alue
     *     allo     w           e    d o         bject        is
     *     {@l       i  nk S  tri  ng }
     *        
                */ 
             public voi  d     setReas           o  n(String val  u      e) {
        t  hi            s.re  a  son          = val   ue;
        }  

     /**
           * Gets the value of the fine property.
     * 
          * @    r   etur n
         *          possible obje c        t is
     *       {         @lin          k String }
                *            
          */
    public String getFin   e() {
            retu  rn fine;  
    }
 
      /**  
       * Sets the value of the f ine    property.
     * 
               * @param value
             *     allowed       obj    ect      is
               *     {@lin     k String }  
       *     
     */
      p   ublic void setFine(String value) {
             this    .fin      e = value;
     }

    /**
     * Gets    the value of the costs pro   per     ty.
     * 
     * @ret  urn
     *     possible object is
     *     {@link Str  ing }
     *     
       */
    public Strin  g getCost  s() {
        return costs;
    }

    /**
     * Sets the value of the costs prop     erty.
       * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCosts(String value) {
        this.costs = value;
    }

}
