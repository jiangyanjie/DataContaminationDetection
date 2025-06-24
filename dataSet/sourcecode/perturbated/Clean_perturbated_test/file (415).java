
package     DBFullReportsClient;

im port javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import    javax.xml.datatype.XMLGregorianCalendar;


/**  
 * <p   >Java class for ApplicationAre aType com    pl    ex type.
   * 
 * <p>The following sch   ema f        rag   men    t specifies the     expected conte   nt conta    ined within this class.
 * 
 * <p    re>
 * &lt;complexType name="Appli   catio nAreaTyp     e">
 *   &lt;co        mplexContent>
 *       &lt;re  striction base="{http:/    /www.w3.org/2      001/XMLSchema}anyType">
 *                 &lt;s   equence>
 *         &lt    ;element name="Applic     ationIdentifier   " type="{http://gateway.dnb   .com/getProduct}Applica   tionIdent   ifierType     "/>
 *           & lt;element name="Re   questCreationDateTime" type="{http://ga    teway.dnb.com/getProduct}RequestCreationDateTimeType"/>
 *         &lt;element name="UniqueReferenceIdentifier" type="{http://gateway.d   nb.com/get   Produ        ct}UniqueR      efer    enceIdentifie    rType"/>
 *       &lt;/sequenc    e>
     *     &  lt;/re striction>
 *   &lt;/complexCont  en     t>
 * &lt;/complexT  ype>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccess         T   ype.   FIELD)
@   X   mlType(name = "App  licationAreaTy    pe", propOrder = {
      "applicationIdentifier",
    "requestCreationDateTime",
    "uniqueReferenceIdenti   fier"
})
public class A   p      plicationArea Type         {

    @XmlElem   ent(name = "Applicatio    nIdentifier", requi   red = tru      e)
    pro tected String applicationIdentifier;
     @XmlEleme    nt     (name =       "RequestCreationDateTime", required = true)
       protected XMLGrego    rianCa       lendar reques   tCrea  tionDateTi  me;
    @XmlEl     eme     nt(nam             e =   "Unique  Reference        Identifier",    r  equired = t     rue)
    prote cted   String uniqueRe   fer        enc eI   dentifier;

        /**
           * Gets the   valu     e  of the applicationI    dentif     ier   proper    t   y.
          *  
          *  @retur   n
         *     p    o        ssib       le object        i             s
           *             {@l         ink String }     
      *      
           */
      public String g  etApplicatio              nI d     enti      f   ier() {
             re turn           application      Id  enti fier;
       }

    /**
     *  Se   ts the    val ue of the app   lication   I    dentifie      r  property.      
     * 
           * @param value
               *       allowed objec       t i     s
     *             {@li  nk Stri   ng }
            *                   
     */
         public vo  id         setApplicationIde ntif  ier(String value) {            
           thi s.applicatio nIdenti     fier = value;
       }

      /**
       * G   et      s the va           lue of        t   he requestCre     ationDateTime    propert   y.
     * 
      * @return
        *              possible object i s               
          *           {@link           XMLGregori         anC    alend    ar }
     *     
     */
                      pu blic XMLGre gor i   anCalendar getR  e que stCr  eationDateTime     () {    
        return requestCreat  ionDat       eTime; 
      }
      
      /**
     * Se    ts the valu  e of the reques             tCreationDateTime property .
                     * 
        *  @par  am  val                     ue
     *     all owed   object is
     *     {@link XMLGregorianC   a       lendar }
              *        
     */
         public   void se   t     Re     que       stCreationDate   T   ime(XMLGregorianCalen   dar value) {
                t     his.requestCre  ationDateTim    e = value;
    }

    /**   
     *          Gets      the value o       f the uniqueReferenc   eIdentifier property.     
     * 
     * @ret    urn
     *     possible object is
     *     {@link String }
     *     
     */
    pub   lic String getUniqueReferenceIdentifier() {
                      return uniqueRefere nceIdentifier;
     }

    /**
     * Sets the value of the uniqueReferenceIdentifier property.
     * 
     * @param value
     *     allowed objec    t is
        *     {@link String }
     *     
     */
    public void setUniqueReferenceIdentifier(String value) {
        this.uniqueReferenceIdentif  ier = value;
    }

}
