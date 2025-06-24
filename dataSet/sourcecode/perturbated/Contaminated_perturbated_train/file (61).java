
package     com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
im    port javax.xml.datatype.XMLGregorianCalendar;


    /*     *
 * Abstract supertype fro disco      ve   ry responses.
 *      
 * <p>Java class for AbstractDiscoveryDeliver         yStructure comple    x type.
 * 
 * <p>The   foll       owing schema fragment        specif   ies    the expected content        contained within this c     lass.
 * 
 * <pre>
 * &lt;complexType name="Abstract  DiscoveryDeliveryStructure">
 *     &lt;complexContent>
 *            &lt;e  xtensio       n ba    se="      {http:/  /www.siri.org.uk/siri}ResponseStructur  e">
   *       &lt;sequence>     
 *             &lt;grou  p ref="{http:  //www.s        ir     i.org.uk/siri}Delive  ryStatus    Group"/>
 *       &lt;/sequence>
 *      &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@X    mlAccess orType(XmlAc      cessType.FIELD)
@XmlType(name = "AbstractDis   coveryDe  l     iv eryStruct    ur e", propOrde r        = {
        "status",
      "errorCondit        ion",
    "valid    Until",
    "shortestPossibleCycle"
})
@XmlSeeAlso({
    ServiceFeaturesDel    i     veryStructure.class,
    FacilityDeliveryStructure.class, 
    StopPoints        De liveryStructure.cl     ass,
    VehicleFeaturesDeliveryStructure.cla ss,
        ProductCategoriesDeliveryStructur  e.class,
    InfoCha   nnelDeli       v eryStructu   re.c  lass,
        LinesDeliveryStructure.class
})
pu blic cl  ass AbstractDiscover   yDe liveryStruc    t           ure
    extends ResponseStructure
{

    @XmlElement(nam     e = "Status", defaultValu   e      = "true")
    pro   tected Boolean status;
    @XmlElement(name = "Erro  rCondition")
        protected ServiceDeliveryErrorConditionStruc  ture errorCondition;
     @XmlEl  emen    t(name        = "Val idUntil")
    @XmlSchema      Ty pe(name     = "dateTime   ")
       prote  cted XMLGrego              rianCal        e  ndar     validUntil;
     @  XmlElement (name    = "S  hortestPo          ssi          b   leC  ycle")
    p          rotec  ted    Duration s ho  r      test   Poss                            ibl eCycl   e;   

    /**
     * Gets the va  lue      of th  e       s   ta           tu    s property.
                 *   
       * @re     turn
       *        pos        sible object is
     *                    {   @l           ink   Boolea   n    }
     *                           
        */
         p      u       blic Boolean    isS   tat   u  s() {
           return sta   tus;
             }

        /**
     * Sets th e value of the sta   tu   s p ro pert y.
       * 
             * @pa     ram value
     *             allowed object is
            *       {@       l   ink Boolean }
     *        
     */
    public void           setStatus(Bo        olean value)  {
          thi   s.s  tatus = value;
            }

    /         **  
     * Gets the val    ue of   the e   rrorCon    dition p  rop    erty    .
              *    
             * @retur         n
           *     p  o ssible object i       s
     *           {@link Se    rvic  eDelivery     ErrorConditionStructure   }
       *     
     */
    public Ser    viceDeliveryErr         orConditionStructure getE      rrorCond    ition() {
              ret ur    n   errorCondit          ion;
    }

         /**    
           * Sets the           valu  e of the erro    rC    o  n  dition property   .
     * 
     * @param va l   ue
         *       allowed objec   t is
                   *        {    @link  Ser    viceDel   iveryErrorConditionStru      cture        }
          *     
            */
    publ    ic vo     id set          ErrorC ond      ition(  Ser      v    i   ceDeliveryErr    orC                   ondi  tionS         t            ructure value) {
                 this.errorC onditi    on    =  va    lue;
    }
    
    /**
         *  Ge       ts            the value of   the      validUntil property.
              * 
     * @return
     *     possi   ble    object is
     *      {   @link XMLGr      egorianCalendar }
            *     
     */
    pub   lic XMLGreg o ria nCal         endar getV   alidUntil() {
        retu           rn valid  Until;
          }
      
       /**
     * Sets th  e         value of   t    he validUntil propert y.
     * 
       * @para    m val    ue
       *     al    l      owed   object is
          *      {@link XM      LGregorian    Calendar }
         *     
     */
    public  void setValidUntil(XMLGregorianC    alendar value) {
           this. v    alidUntil = value;
    }

    /*    *
        * Get         s   th   e value of the shortes tPoss      ibleCycle property.
     * 
        * @r  eturn
     *     possible object is
     *     {@link D  u ration }
          *     
      */
    pub lic Duration getShortestPossi     bleCycle() {
        return shortestPossibleCycle;
    }

        /**
     * Sets the value of the shortestPossibleCycle pr               oper          ty.
     * 
     * @param value
     *     allowed object is
     *     {@l ink Duration }
     *     
     */
    public void setShortestPossibleCycle(Duration value) {
        this.shortestPossibleCycle = value;
    }

}
