
package     ExperianLtdCompanySearchWS;

im   port javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;    

   
/**
 *     <p>Java class for ConvictionDetails com    plex ty     pe   .
 * 
 * <p>The following schema  fragmen  t specifies the   expected content con   tained within this class.
  * 
 * <pre>
 * &lt;complexType    name=" Conviction  Details"  >
      *     &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLS   chem   a}an   yType">
 *       &lt;sequence>
 *                  &lt;element name="Date" type="{http://schema.uk.experian.   com/experian       /   bi/generic/bsns/v100/basetype  s}    CCY   YMMDD" minOccurs=" 0"/>   
 *          &lt;element nam   e="R      e  ason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs=   "0"/>
 *         &l   t;element name="Fine" ty   pe="{http://www.w3.org   /2001/X        M      LSchema}string" minOc   curs=   "0"/>
     *         &lt;eleme   nt     name="Cost   s" type="{http://www.w3.org/2  001/XMLSch  ema}stri             ng" minOccurs="0"/>     
 *         &lt;/sequence>
    *       &lt;/restriction>
 *   &lt;  /complexC           ontent>
  * &lt;/c  omplexType>
 * </pre>
 * 
 * 
 */
@  XmlAccessorType(XmlAccessType.FIELD )
@XmlType(name =        "Convi            ctionDetails"   , propOrde   r = {
    "              date",
       "reason",
    "fine" ,
    "costs"
})
public class Convi   ctionDetails {

      @   XmlE  lement(name = "Date")  
         pro tected CCY    YM   MDD date    ;
           @   XmlElement(na  me = "Reason")
        protected Strin  g reason;
    @Xml Eleme                  nt(nam     e        = "F        i   n  e")
                protected St ring fine;
    @Xml         E     lem   ent(name   = "Cost    s")
        protected St       ring c   os           ts;

             /   *         * 
        * Gets           th        e   val        u  e o      f the date prope      rty.
               * 
     * @re   turn
     *       possible obj ect         is
          *     {@lin               k CCYY                   MMD             D }
     *        
              *   /
    public CCY     YMMDD ge  tD  ate() {   
            return date;
       }
        
              /**
     *      S ets the va   lue o     f the dat         e   propert          y.
     *         
          * @par       am v     alue
     *          allowed object is
     *     {@link CCYYM    MDD          }
     *     
         */
     public v    oid set     Date(C   C     YYM  MDD          value) {
        t   his.date = v   alue   ;
    } 

                    /     **
      *    Gets    the valu    e of the rea   son                        propert    y.  
      *    
     * @ret u   r             n
        *                             pos  sible o    b       ject is
              *       {@l   ink S tri    ng }    
              *               
     *   /
        public      S     t   ri    ng ge     tRe     ason()               {
         re  turn re   ason;
    }

    /*  *
        *     Se  ts the value         of the re   ason pr      ope     r   ty.
            * 
      *        @param  value
            *           all           ow ed objec   t is
         *        {@link     Strin            g }
               *           
           */
     pu      blic void          se   tRe as  on(String val       ue          ) {
                 this.reason = v     alu   e;
      }   

    /**
         *  Gets the    value of the fine     property.
     * 
     *  @r     eturn
     *         pos sible object is
            *                 {@link             St   rin     g       }
          *        
     */
    public   String getFine() {
              retur n fine;
      }

        /**
     * S      ets the value       of    the fine prope rty.
     * 
     * @param val  ue
     *          allowed object is
     *               {@link    String }
     *     
     */
         public v    oid setFine(String value) {  
        this.fine = val  ue;
    }

           /**
      * Gets the value of the c  osts prop   erty.
        * 
       * @return
     *     p   ossible object is
     *     {@link    Str     i    ng }
     *     
     *    /
    public Str  ing getCost     s() {
        r eturn costs;
    }

     /**
     * Sets th       e value of the costs property.
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
