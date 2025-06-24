
package     com.transportation.SIRI_IL.SOAP;

im   port javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import  javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
i    mport javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

  
/**
 *    Comm    on parameters for all situations     .  
 * 
 * <p>J ava class for Context Stru     ct     ure compl   ex type    .
 * 
 * <p>The following schema fragment     specifies t       he expe ct   ed content contained      withi  n this class.
 * 
 * <     pre>
 * &   lt;complexType n    am    e="ContextStru     ct  ure">
 *   &lt;complexContent>
    *      &lt;restric    ti   on base="{http://www.      w3.org/2001/XMLSchem             a}anyType">
 *          &lt;sequ  enc     e>
 *         &lt;element name="CountryRef" type="{http://www.ifopt.or    g.uk/ifopt}Coun   tryRefStru cture        " minOccur   s="0"/>
 *                 &lt;element name="Partic  ipantRe     f" type="{http://www.siri.org.uk/siri}ParticipantRefStruc    ture    "/>  
  *         &lt;element name="TopographicPlaceRef" type="{http://www.w  3.org/2001/XMLSchema}NMTOKEN" minOccurs  ="0"/>     
 *             &lt;element name="TopographicPlaceNam e" type="{http://www.siri.org.u   k/siri}NaturalLanguageStrin      gSt       ruc   ture" minOccurs=  "0"/>
  *         &lt;element name="DefaultLanguage" type="{http://www.w3.org/2001/  XMLSchema}langu                age" minOccurs="0"/>
 *         &lt;element name="NetworkContext" type="{http://www.siri.or    g.uk/sir    i}N           etworkCo ntextStructure  " minOccurs=   "0"/  >
 *         &lt;element name= "Action  s"   type=     "{http://www.siri.org.uk/siri}ActionsStructure" minOccurs="0    "/>         
 *          &lt;el   e   ment ref="{http://www.siri.org.uk/sir     i}Ext      ensions" minOccurs="0"/>  
 *        &lt;/sequence>
  *     &lt;/restriction>     
 *   &lt;/complexCo     ntent>
 * &lt;/co   mplexType>
 *         </pre>
 * 
 *    
 */
@XmlAcce   ssorType(XmlAccessType.FI  ELD)
@XmlType(n ame =        "Cont          extStructure", prop      Order           = {
     "countryRef",
    "part      ici     pantRef    ",
    "topographic  PlaceRef"  ,
    "topographicPlaceName",  
    "defaultLanguage",
          "networkContext",
    "actions",
    "extensions     "
})
public class ContextSt     ructure {

    @Xml     Element(name = "Country    Ref")
    protected CountryRefStructure countryRef;
    @XmlElement(     name = "ParticipantR ef", required      =   true)
      protected P          articipantRefStructure participantRef;
        @XmlEle     ment(name =  "  T       opographicPlaceRef")
    @XmlJavaTypeAdapter(Colla p  sedS   tringAdap    ter.   class)  
    @XmlSchemaType(nam e = "NMTOKEN"  )
    protected Strin  g topogra    phicPla   ceRef;
    @XmlElement(name =  "TopographicPlaceN   ame  ")
    protected NaturalLanguageString  Structure t opographicPlaceName  ;
              @XmlElement(name = "    DefaultLangua     ge")
    @XmlJav    aTypeAdapter(Co    llapse   dStringAdapter.class)
          @Xm           lSchemaTy   pe(n   am      e = "language                    "          )
    protected      Str     ing      defaultLa   ngu      a        g   e;
          @Xml        Element(name =            "NetworkC          onte             xt")
    protec  t ed Net  workCo nt    extStructure networkC     ontext;
      @XmlElement(na     me = "   Actions")
         protected ActionsSt                        ructure actions;
    @XmlEl   em          ent(name    = "Extens  ions         ")
             protecte    d E  xt     ensionsStr                u   cture extensions;
   
    /**
          * Gets the va            lue      of the countryRe   f p    rope   rty.
          * 
     *    @ ret   urn
     *          possible ob      ject is
               *     {@link     Co       u ntryRefS   tructure }
          *        
     * /
      public Cou  ntryRefStru    cture getCoun     tryRef() {
                   retu   rn c ou    ntryRef;   
    }     

    /**
                * Sets the                v alue      of t    he co                untryRef property.
       *         
     * @      pa  ram valu       e
      *           allow  ed ob   ject       i    s 
              *     {@lin   k Co         untryRefS    tructure                  }
       *       
     */
          pu         b    lic void    se  tC ountryRef(Countr   yR          efStruct     ure valu      e     ) {   
            this      .coun   try   R   ef      = value;
    }
  
               /**
                      * Gets th  e value     of the participantRef pr         operty.
         * 
     *      @return
        *          possible        obje ct    is
             *       {@link Par ticipan           tRefS tru  ctur    e   }
     *           
         */
       publ    i    c Participant      Re    fStr        ucture getP     articipantRef( ) {  
          ret   urn         participantRe  f;    
      }

          /**
     * Se      ts the val  ue of    the pa  rtic       ipantRef property.
      * 
      *          @param value    
     *     a    llowed object is                  
      *     {@link   ParticipantRe     fStructure }
      *     
     */
      pu     blic   vo     id   setParticipan     tRef    (ParticipantRefSt   ructure val   ue) {
           this.partic   ipantR   ef =          value;
    }

    /   **
        * Get   s th    e value of t  he topographicPl aceRef p r   operty.
     * 
      *       @retur  n
     *       pos     sible      o    bject is
         *                   {@li   n           k String }
     *          
     */         
    public String   getTopographic      Pla   ceRe  f() {     
                              return topographic PlaceRe     f;
    }   

    /**
                *     Se     ts the value of the topogra  p       hicP           laceR ef property.
          *    
             * @param valu   e
      *     allow   ed objec       t is
     *     {  @          link St   ring }
     *     
               *   /
            publ  ic voi    d set Topograp   hic   PlaceRe  f(S  tring    value)         {
        this.topog  raphicPlaceRef = value  ;
    }

     /**
     * Gets        the v            al    ue of the     topographicP      laceName property.   
     *      
       * @  return
     *                  possible     object is
     *              {@l  i  nk Natural   LanguageStringStruc  ture   }
     *       
     */
        public NaturalLang     uag      eS   tri    n   g     Structure   getTopog    r    aphicPlaceName    () {
                        return topogra     p    hic  Pl    ac     eNam e;  
    }

    /**
     *       Sets the    value of the topogra    ph         icPlaceNam   e prop  erty      .
       * 
     * @p    aram value
             *     allowed obj    ect is
            *                {@li             nk Na  turalLanguag eStr    ingStructur   e }
     *     
     */
      pu    blic     vo  id setTopogr  aphi   cPlac     eNam     e     (Nat ur   alL angu   ag   eString  Str        ucture       va   lue) {
                                       th is.       t   op    og   raphicPlaceName = value;
      }

    /**
     * Gets th         e v    alue of the def  aul       tL      anguage property.
              * 
            *   @r  eturn
     *     possib                  le obje     ct is
       *     {@link      String  }
     *            
     */
    pu   blic Stri       ng getDefau lt   La ngu ag               e     () {
        retur         n def  ault    L   anguage; 
    }

    /  **
        * Sets the value o   f t h e defaultL         anguage pr    operty.
         *  
     * @p      aram v    al   u  e
     *        all   owed ob      j    ect is
           *       {@li  nk String }
        *     
        */
    public void        setDefaultLangu               age   (St     ring   valu        e) {
             this.defaultLanguage   = val    ue;    
    }

    /**
           *   Gets the value of the n  etworkContex    t p       roperty.
     * 
     * @retu    rn
       *        possible object    is
     *        {@link NetworkContextS      t  r  ucture   }
     *        
             */
        p    ublic     Networ kCo ntextStructure   getNetworkCo nt  ext(    )  {
                    return networkCont        ext;
    }

        /**     
     * Sets the val            ue of the networkContext property    .
      * 
        * @param value
     *     allowed object is
          *         {@link     NetworkContextStructure }
     *     
             */
    public void setNetworkContext(NetworkC   onte    xtStructure value) {
          th   is.n   etw   o rkContext = val                ue ;
    }

    /**
          * Gets        the value of the actions      prop e  rty. 
        * 
     * @return
            *         possible object is
         *       {@li   nk ActionsStructure   }
     *     
     */
    public A ctionsStruc      ture ge    tActions() {
        return actions   ;
     }

     /**
     * Sets the value of the action    s property.
     * 
     * @param value
     *     allowe   d obje    ct is
     *     {@link ActionsStructure }
     *     
     */
      public void setActions(ActionsStruct        u  re value) {
        this.actions = val          ue;
       }

    /**
     * Gets the value of the extensions property.
     * 
        * @return
     *     possible object is
     *        {@link ExtensionsStructure }
     *     
     */
     public ExtensionsStructure getExtension  s() {
         ret   urn exten sions;
    }

    /**
             * Sets the value of the extensions property.
     * 
     * @param value
     *     al   lowed object is
     *     {@link ExtensionsStructure }
     *     
     */
      public void setExtensions(ExtensionsStructure value) {
        this.extensions = value;
    }

}
