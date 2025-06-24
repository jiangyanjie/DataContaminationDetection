
package    ExperianLtdCompanySearchWS;

imp   ort javax.xml.bind.annotation.XmlAccessType;
import    javax.xml.bind.annotation.XmlAccessorType;
imp   ort javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java cla  ss for CorporateSharehol    der    co     mplex typ e.
 * 
 * <p>The following sch    ema fragment specifies the   expected    c    ontent contained within t  his cla     ss.
 * 
  * <pre>
 * &lt;complexTyp e name="Corpor   ateShareholder">
 *      &lt;c   omplexContent>
 *      &lt;restriction b   ase="{http://www.w3.o        rg/2001/    XMLSchema}an    yTyp     e">
 *               &  lt;sequence>
 *         &lt;element name="RegNumbe r" ty  pe="{http://www.w3.org/2001  /XMLSchem  a}string" minOccurs="0"/>
 *           &lt;       element nam   e="  Country"      type="{ht      tp://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                &lt;element name="Name" type="{http://www.w3.org/2001     /X    MLS   chema}str    ing" mi   nOccurs="0"/>
 *              &lt;elem ent name="Holding" type="{http://www.w3.org/2001/XMLSchema}string"       min  Oc    c u        rs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/c     omplex    Cont   ent>
 * &lt;/co  mplexType>
   * </     pre>
 * 
            * 
 */
@XmlAccessorType(X   mlAc cessTyp    e.FI  ELD)
@   XmlType(name    = "CorporateS      hareholder", propOrder = {
    "regN             umber",
    "co  untry",
    "name" ,
    "holding"
     })
public cl  ass    CorporateShareholder {

           @XmlElement(name = "RegNumber")
    prot     ecte       d String     regNumber;
    @X   m   lEle    ment(name = "Country")
             prote     cted  String c          ountry;  
    @X     ml   Element(na   me =      "N         ame")
    pr otected S   t        rin g   name;
           @X   ml    Eleme   nt(name  =   "Ho      lding")
      pr  otec   te d Strin      g  hol  d    ing;         

                      /             **
     * Get   s the         value     o    f the r         egNumber propert   y.
       * 
            *     @retur    n     
      *          possib  le obj     ect     is
     *             {@l    in   k      Strin     g }  
     *     
     */
        public String getRegNumber()    {          
           return         reg Numbe    r                           ;
       }          

    /**
     *    Sets t  he value        o f t    h      e regNumber pr  o    p    ert y.
             *         
             *     @p  ara m     value
     *         allowe     d     object  is
             *     {   @lin     k String      }
                    *     
     */
                pub              lic void setRegNumb er(String  va  l   ue) {
        this.regNumber =      valu    e;
    }

    /**
     * Gets the value of t he cou    ntry propert          y.
       *  
          *    @r     etur   n
              *     po  ssible obj      ec        t is
     *              {   @lin   k Strin   g   }
         *     
        */
      public Strin  g getC    oun      try()   {                            
                    retur     n    country;
         }

    /**
             *    Sets   the           val   ue o          f the cou       ntry prope                r ty. 
     * 
     * @p         aram value
        *           allow    ed    ob        je          ct i     s
          *     {@lin     k Strin                          g }
     *     
                 */
           public void setCo   unt     ry(Strin g     value)   {
        this.country =  v         alue; 
                   }
       
    /       **
     * Gets the valu       e o     f  th  e name   p         roperty.
             * 
                * @return
       *     possible object is
     *           {@li nk S    t ring }
              *        
     *  /
      public St    ring getName() {
        ret        urn n a   me;
    }

        /**
              * Sets the value of      the name property.
         *  
     * @pa   ram val  ue   
     *     allowed object is
     *        {     @   link String }
       *       
     */
    public void setName(String value) {
            this.name = val     ue;
    }

       /**
     * Gets the      value of the holding property.
     * 
     * @return
     *          possible obj    ect is
     *     {@link Stri         ng   }   
     *     
         */
    public     String getHolding() {
        retur n ho   lding;
     }
   
    /**
     * Set   s the value of the          holding property.
              * 
     * @param value
                 *     allowed object is
     *     {@link String }
     *     
     */
    public void setHolding(String value) {
        this.holding = value;
    }

}
