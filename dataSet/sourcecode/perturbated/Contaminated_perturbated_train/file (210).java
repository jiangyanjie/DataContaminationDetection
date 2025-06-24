
package  com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import     javax.xml.bind.annotation.XmlAccessorType;
im   port javax.xml.bind.annotation.XmlElement;
import      javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSee    Also;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalend ar;


/**
 * Type for abstract EntryAbstract type.  
     * 
  * <p>Java class for AbstractSituationElementStructu         r e complex typ      e.
 * 
 *  <p>The     following schema fragme  nt specifies the exp      ect   ed content  c ontained w    ithin this class.
 * 
 * <pre>
 * &lt;co      mplexType nam   e ="AbstractSituationElementStructure">
 *   &lt;complexContent>
 *          &lt  ;restriction b    ase     ="     {htt   p://www.w3.org/2001/XMLSchema}anyTy    pe">
 *       &lt;seque     nce>
 *         &lt     ;element nam  e  ="CreationTime" type="{  h   ttp://www.w3.org/2001   /XMLS    ch    e  ma}dateTi   me   "/>
 *         &l  t;group ref="{http:/ /www.siri.or g.uk/siri   }SituationSharedIdentityGroup"  />           
 *          &lt;/sequence>
 *     &lt;/  restric   tion>
 *   &lt;/complexCo  ntent>
 * &lt;/c   omplexTy     pe>
 * </pr    e>
 * 
 * 
 */
@XmlAccessorType        (XmlAccessType.FIELD)
@XmlType(name = "Ab  stractSi           tuationElementStructure", pr   opOrder = {
    "cr  eationTime",
    "countryRef",
    "p artici        pantR     ef",
         "si  tuationNum   b    e  r",
    "updateCountryRef",
    "updateParticipantRef",
    "    version"
})
@XmlSeeAlso({
    Situati   o    n      Element      Structure.c   lass
})
public class AbstractSituationE  lementStructure {  

    @XmlE  le    ment(name = "Creatio  nTime", required = true   )
    @XmlS c hem        aType(name = "dateTime")  
    protect ed XML   GregorianCalenda    r crea tionTime;
    @XmlElement(name =     "   Coun tryRef")
    protected CountryRef Struct    ure c      ou  ntryRef;
    @XmlElement(nam      e = "ParticipantRef")  
    protected ParticipantRefStruct     ure participantRef;
    @Xm  lElem  ent(na   me = "Situati           onNumber", required = true)
      protected EntryQualifierStructure si tuationNumber; 
    @XmlElement(nam e           =              "U         pdat   eCountryRef")
        protected C   o   un  try    RefStru ctu    re    updateCountryRef             ;
                 @XmlElemen   t       (nam  e = "Updat    eParticipantRef")
      protected Part icipant  RefSt  ructure u  pdateParti            cip         antR ef;
        @XmlEle me       nt(na    me =    "Ver      sion" )             
    protected Situ       ati  onVe    rsion ve  r    sion;

    /**
     * Gets the value of   th     e cre  ationTime property.
      * 
          * @ret    u rn
     *       possible ob                  ject is
     *                        {@l  ink XMLGrego         ri   a          nCalendar }      
      *             
     */
    public  X  MLGrego    rianCalendar   get    C reatio    nTime() {
           retu  rn    cre     ationT    ime;
    }

      /   **     
         * Set  s  the value o  f t                        he cre    ati onTime   p             roper        ty.
     * 
                 * @param value
       *     allowe     d ob ject i  s
                       *              {@lin     k XMLGregorianCal endar }
     *     
          */
       public void               setCre      ati   onT    i        m        e(XMLGre gorianCalend   ar value) {
        thi    s.creatio  nTime          =    value;
    }

        /**
     * Gets the value of the countryRef      property.
     * 
     *       @                re turn
         *      possi        ble obj   ec t is
     *         {@link Cou   ntry    Re               fStr  ucture }
     *            
           */
    public CountryRefStructure       get     C    ou n    tryR  e   f(  ) {
        retu      rn c    ountryRef;               
      }
  
    /**
     *      Sets       the valu    e of t   he cou  n   tryRef proper    ty.     
          *     
     *    @param value
     *     a    llowed object  i    s
             *         {@           link CountryRefStr      ucture }  
         *           
        */
          public   void set           CountryRef(CountryRef  Structur e  value) {
        this.countryRef = va   lu       e;
     }
  
       /**
      * Get     s t   he            valu     e    of the participa   nt   Ref property.
     * 
      * @return
         *        p  ossibl     e   obje    ct is
        *     {@li   n  k     P  ar     ticipa         n tRefS   tr  ucture  }
     *        
        */
    public Partic                ipantRef       Structu    re getPartici pan t    Ref(   ) {
         retu    rn p    a     rticip    antRef;
                }

      /**
      *     Sets the value of the particip antRef        pro   perty.
     * 
               * @  pa    r   am v        alue
        *       allowed         o   bje    c     t is 
     *     {@link Particip              antR efStru   ctu  r           e   }
      *     
                      */
    pub lic void setParticipan     t   Ref(Parti     cipant     Re  fStruc      ture   value) {
                 this.pa    rticipantRef = val   ue;
    }
   
    /**
        * Unique ide  nt  if  ier of  Situat     ion within   a Par ticipant. Exclud es any v           e     rsion nu mber.
     * 
     * @    return
     *         possible object   is
            *        {@       link          Entr     yQua   lifierStru   ctur           e }
     *         
     */
    publ ic   E   ntryQua    l   ifierStr ucture ge tSitu     at  ionNumb       er() {
            ret ur   n sit        uatio   n    Num    ber;
    }

    /**
        * Set    s th       e    value       of the sit   uationNum     ber   pr op  erty.
                 * 
       *    @    param value
     *         allow ed obj      ect is
            *           {  @link E   ntryQualifierStructu       re }
     *     
       */
    p u      b    lic voi           d setSit    uationNumber (  Ent      ryQualifie r       Stru           ctu    r  e va               lue) {
            t   hi    s .sit      uationNumber =     value;
    } 

    /**
     * Gets the value of t     he upda    teC                o     u   n      try   Ref property.
            *   
               *           @return
     *         possible    obj         ec  t i      s
     *      {@link Cou  ntryRef   Struc     ture }
        *     
         */
               pu            blic C   ountryRefStr uc     ture get   UpdateCou       ntryRef()    {
                    r    eturn   updateCo    untryRef;
    }

    /**   
     * Se   ts the value of the updateCo   untryRef property.
          * 
            * @param value
        *     allo      wed  object is      
     *         {@link C ountryRefStructure }
     *           
     */
    public void setUpd           ateCount      ryRef(Count    ryRefStru  cture value)      {      
           this.updateCo     untryRef = value;
    }

    /**
     * Gets        the value of the updat eParticipantRef property.
      * 
       * @return
     *     possible object is
     *        {@link ParticipantRefS  tructure }
     *     
     */
        p   ublic ParticipantRefStructure getUpdateParticipantRef() {
                 return updateParticipantRef;
    }

      /**
     * Sets the value o     f   the updatePa           rti cipantR  ef property.
     * 
      *       @para  m value
     *     allowed object is
     *     {@link Particip  a n       tRefStructure }
     *     
           */
    publi c void setUpdateParticipantRef(Part      icipantRefStructure    value) {
        this.       update     P    articipantRef = value;
    }

    / **
     * Gets the val  u   e of the version prop erty.
     * 
     * @  return
     *     possible object is
     *          {@link S        ituationVersion }
     *     
        */
    public Situation    Version getVersion() {
        retur  n version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link SituationVersion }
     *     
     */
    public void setVersion(SituationVersion value)    {
        this.version = value;
    }

}
