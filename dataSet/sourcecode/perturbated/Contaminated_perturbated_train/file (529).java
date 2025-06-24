
package   ExperianAlertsAddRemoveWS;

impo     rt javax.xml.bind.annotation.XmlAccessType;     
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java     class       for Con     firmation complex typ       e.
 *   
 * <p>The following schema fra   gment spec ifies th    e expected content cont     ai   ned withi      n this class.
 * 
 * <  pre>
 * &lt;complexType name="Confirma   tion          ">
 *   &lt;complexContent>
 *     &lt;restriction base=      " {http://w    ww.w3.or   g/2001/ XMLSchema   }a  nyType">
    *          &lt;s   equence>
 *         &lt;elem     ent name="  BusinessRef" type="{       http://   www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;element name="LegalStatu      s" type  ="{http://www.w3.o rg/2001/XMLSchema}str ing"  minOccurs="0"/>
 *         &lt;  eleme     nt name="ClientReference" ty pe="{http://www.w3.org/2001/XML  Sc     hema}string" minO ccurs=    "0"/    >
 *         &lt;element name="SubjectScheme" type="{   http://www.w3.or g  /2001       /XMLSchema}string" minOccurs="0"  />
 *         &l     t;element na     me="ActionConfirmation"    type="{http:/   /www.w3.org/2001  /XMLSchem a}s     tring" minOccurs="0"/>
 *         &lt;element name="AlertsErrorCode" type="{http://www.w3.  org/   2001/X   MLSchema}string" minOccurs="0"/      >
    *         &              lt;element nam  e=" Co     mmercialName  " type="{htt   p    ://www.w3   .org/2001/XMLSchema}string"   minOccurs="0"/   >
 *       &lt;/sequence>
 *     &lt;/restr      iction>
 *         &l t;/complexContent>
   * &lt;/complexType>
 * </pre>
 * 
 * 
        */
@XmlAccessorType(XmlAc  cessType.FIELD)
@XmlType(name    = "Confirmat  ion", namespace = "http:/ /schema.    uk.experian   .com/e  xperian/bi/generic/  alerts/bsns/v100/basetypes", prop   Order = {
    "bus      inessR     ef",
    "legalStatus",
    "client        R  eference",
     "subjectScheme           ",       
    "action  Conf       irmation",
    "a   lert sErrorCode",
    "commercialNa  me"
})
public class Confirmation {

    @XmlEleme  nt(name = "BusinessRef" )
    protected Str    ing business     Ref;
       @XmlElement(na      me     = "LegalStatus")
    protected String legalSt      atus;
           @XmlElement(         nam   e      = "   ClientReference")
    p  rotected S   tring cl  ientReference;
         @XmlElement(name = "Subjec        tScheme         ")
    protected String    subjectSc   heme;
         @XmlElement(n  am  e = "Ac   tionConfirmat    ion  ")
    protecte       d String actionCo  nfirmatio  n;
       @XmlE lement (name = "Ale  rtsEr    rorCo     de")
          prot       e  c  t    e   d String   a    le      rtsEr    rorC ode      ;
        @XmlEle men  t(na       m    e     = " Co  mmercialN    ame"                )     
      p    rote   ct    ed Stri        ng commercialName;

    /   **
           * Gets the value  of             th       e busines    sRef            property.
     * 
     * @return
     *     po   ssibl      e object is
     *        {@link St        ring }
     *       
         */
    public Stri            n   g g    etBusin essR  e    f() {
                  return busine   ssR  ef;
          }

                    /**
     * S    ets t   he v  alu     e of   th    e businessR    ef pro           perty.
           * 
                   * @param va  lue
                  *       allowed o   b jec       t                is
     *            {@link St   r  i    ng }
               *         
          *      /
       pub lic void setBu sinessRef(String va lue) {    
                t  h  is.busine    s        sRef = val   ue;           
                       }  
 
    /**
     *   G ets the value  of    the   legal Status property             .
          *   
                      *     @return       
          *          po   ssible object is
            *     {@link String }
                      *           
       */
        p   ublic Stri       ng ge tLegalSta  tus() {
               return   l   egalS  tatu  s;
    }   

             /**
     * Sets th           e value of      the leg  alStatus p     roperty.
     *   
        *    @param v      alue
        *       all   owed object      is
     *     {@l   ink St ri  ng    }
     *         
                     */
    publ    ic v oid     set   LegalStatus(   String value)    {
          this.legalSt  atus =    value; 
     }

           /**
     *             Gets th   e value of the cli               entRef     e  renc  e pr   operty.
     *    
     * @re     t     ur       n
     *     possib  le object     is
              *                    {@  link Strin g  }
             *           
     */
             pub  lic  String g          e  tClientReference() {  
        ret       ur    n clie   nt   Ref  eren   c         e;
    }

    /**
          * Sets th    e valu   e of t      he         clien t    Re      ference property.
         *          
     *    @para  m value
         *                      a               llowed        obj   ect     i   s 
        *     {@link     String }  
           *     
                    *   /
    publ      ic vo id setCl  ientRefe ren    ce(St      r  i           ng value) {
           thi    s.c  lient   Reference = value;
          }  

          /*          *
             * Get    s the value of the subje   ctS         chem      e pro   pe rty.
     *       
       *        @ return
       *      possible obj        ect i   s
     *            {     @ link String }
     *     
     */
    publ ic        String g      etSubjec  t     Scheme( ) {
           return subject     Scheme;   
       }
    
    /**
           * Set s the va  lue of the    s    ubjectSch      eme pro                 per     ty  .
       *                 
     *          @param value      
         *     allowe    d object is
     *     {@l     ink St     ring }
           *         
     */
            public void setSubj   ectScheme(St  rin  g value) {
                      thi s.subjectScheme = value ;
         }

         /**
                *     Gets the valu      e of the actio     nConfirmation p    ro      perty.
     * 
        * @return
        *                  possible object is
            *     {@link    Strin g }       
     *     
                  */
    public String getActionConfi                rmation()           {
        return actionConfi  rmat   ion;
    }

    /**
          * Sets the value of the actionConf irmation pro  perty.
     * 
        *  @param value
        *                    al lowed obj      ec          t is
         *        {@link String }   
        *     
                */
          public        voi  d setAction   Confirmati   on(St   ring v   alue) {
        th    is.actio   nConfirmation   = valu     e;
    }

    /**
     * Gets      the value of the alertsError  Cod e pro   perty.
     * 
     * @re   turn
     *        possible o            bject is
     *     {    @li nk S       tring }
     *     
     */
    public String getAlertsErrorCode() {
        ret     urn alertsErrorC  ode;  
      }

    /**
     * Sets the  value of the alertsEr            rorCode prop  erty.
     * 
       * @p    aram value
       *       al  l   owed object is
     *         {@link String }
     *     
     */
                 public voi    d setAlertsErrorCode(String value) {
        this.al     ertsErrorCode = value;
           }

            /**
     * Gets the value of the commercialName property.
     * 
     * @return
     *     poss   ible object is
     *     {@link String }
      *     
     */
    public  String getCommercialName() {
        return commercialName;
    }
   
    /**
     * Sets the value of the commercialName property.
         * 
     *    @param va   lue
     *     allowe  d object is
     *     {@link String }
     *     
     */
    public void setCommercialName(String value) {
        this.commercialName = value;
    }

}
