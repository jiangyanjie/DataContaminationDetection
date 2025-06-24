
package com.transportation.SIRI_IL.SOAP;

import     javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
im      port javax.xml.bind.annotation.XmlElement;
import    javax.xml.bind.annotation.XmlType;


/**
 *  Typ     e for Co    nnection Monito        ring Capabilities.
 * 
    * <p>Ja  va class for ConnectionMonito     ringServiceCapabilitiesStructure co  mplex      type.   
 * 
   * <p>The following   schema fragment specifies th     e expected content        contain    ed within this class.
 * 
 * <pre>
 * &lt;complexType name="ConnectionMonitoringServiceCa          pabilitiesStr    u   cture">
 *   &lt;complexCon  tent>
 *       &lt;ex  tension base="{http://www.siri.o      rg.uk/siri}A      bstractCa   pabiliti      esStructure">
   *          &lt;sequence>
 *                  &      lt;element name="T     o   p      icF        ilt ering    " m                   inO  ccurs="0" >
 *                        &lt;comple   xType>
 *                     &lt;   complex        Con   tent>   
 *                                   &l t;rest ric    tion base="{http    ://www.w3.org/20      01/XMLSche     m  a}any    Type">
 *                          &  lt;sequen   ce>
 *                        &lt   ;elemen      t name="  Fi lterByJ  ourney" type="{http://www.w   3.org/2001/XMLSch    ema}boolean"/     >
 *                             &lt;eleme      nt name="Fi        lterByTime" type   ="{http://www.w3.or      g/2001 /XMLSch     e      ma}boolean"/>
  *                     &lt;/s           e       quenc     e>
 *                   &lt;/r  est    riction>
        *                &lt;/comp        lexCon     t   e   nt>
        *                &l    t;/compl   exType>
    *                            &lt;/e      lement>
    *                 &lt       ;el   ement n   ame="RequestPolicy"     mi      nOccurs="0">
 *              &lt;      complexType>
      *                         &lt;complexContent>
    *                          &lt;extensio   n base="{h  ttp:  //ww     w.siri .org     .uk/ siri    }Capabil     ityRequestPoli    c  yStructure">
 *                             &lt   ;   sequ     en    ce>
 *                        &lt;     element name=    "F  or   e   i    gnJo    urneysOnl  y" typ    e ="{http://w    ww.w3.         org  /2001/XMLSchema}b  oo    l          ean" minO  ccurs="0  "/    >
 *                   &lt;/sequence>
 *               &lt;/extension>
 *               &lt;/complexContent>    
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Subsc     riptionPolicy" type="{http://www.siri.org. uk/siri}CapabilitySubscriptionPolicyStructure" minOccurs="0"/>
 *                &lt;element name="AccessCont  rol" type="{htt  p://w   ww.siri.org.  uk/siri}ConnectionCapabil     ityAccessContr  olStructure" minOccurs=   "0"/   >
  *         &lt;elem   ent ref="{http://  www.siri.org.uk     /sir    i}Ex   tensions    " minOccurs=" 0"/>
 *       &lt;/sequence>
 *     &lt; /ext   ension>
 *   &lt;/c       omplex  Content>
 * &   lt;/complex   Type>
 * </pre>
       * 
 * 
 */
@Xml      AccessorType(XmlAccess        Type.FIELD)
@XmlType(name = "Connecti   onMonitoringServiceCapabilitiesStructure", propOrder = {
               "topicFiltering"        ,
       "requestPolicy",
    "subscriptionPolicy",
    "accessControl"   ,
      "e     xtensions"
})
public cl   ass      Con     necti    onMo  nitoringServiceCapabilitiesStructure
    extends AbstractCapabi  liti    esS  tructure
{

    @XmlElement(name = "TopicFiltering")
    pro tected     C     onne     ctionMonitoringServiceCapabilitiesSt   ructu  re.TopicF       iltering topicF   iltering;
    @XmlEl         em ent(na       m e =      "RequestP      olicy")
         pro    tected ConnectionMo nitoringS  erviceCapa  biliti    esStruct        ure.Requ          estPol   icy requestPolicy;      
           @Xml  El         ement(name    = "    Su    bscri    p  ti    onPolicy")
    pr    ote  cted Capa           bilitySubs        criptionPoli    c     ySt   ructu      re subscriptionPolic        y;
             @XmlEleme       nt(name = "AccessControl")
    pr       otected ConnectionC   apa       bilityAccessControlStru   cture accessCon  trol;
    @XmlE            lement(nam   e = "Ex   tensi    ons")
          protec     ted ExtensionsStructure exten  sions;

            /**
     * Ge        ts the v    alue      of the      top      ic  Fil   terin            g    property.
     * 
     * @r  e   turn    
     *     possib  le  objec      t    is
          *      {@link Connecti  on MonitoringServi ceCapabilitiesStruc   tur   e.Topi    cFi    lt  ering }
     *              
     *  /
        pub  lic Conn   ec       tionMonitorin   gServiceCa            pa bilit   ie      s Structure    .Top      icFi  ltering getTopicF  ilte   ring(  ) {
                 re      t    urn   t  opicFilt             ering;
     }

       /**
     * Sets the value of the topicFiltering propert      y.
         * 
     * @param value
     *     all     owed ob   ject i  s
     *             {@lin  k Connec tionMonitoringServiceCapa  biliti  esS   t   ructure.Topi    cFilteri      ng }
       *       
      */
      public vo id setTopicFiltering(ConnectionMo     nitoringServ iceCa pabilit   iesStruc  ture.TopicF     il     te   ring     value) {
        this.topicFilter         ing = value;
    }

    /**
     *  Gets the value of t he         requestPolicy p r   operty.
              * 
      * @return     
     *        possi    ble ob   ject  is
        *        {@lin  k Co    nnec      ti     onMo    nitoringServiceCapa        bilitiesStruc       tur               e.RequestPolicy }
                    *       
      */
         p  ublic Connection   MonitoringServiceCapabilitiesStructur     e.Requ   estPolicy get        Requ              estPolicy()    {
               return      re    qu               e      st     Policy;
           }

    /**
     *          Sets the valu  e of t    he     re   que  st             Polic    y property.
      * 
     * @   param value
        *        allo    wed object   is
          *       {@link Connect   ion  Monitoring       Servi ceCapab    ilit   iesStructur             e.RequestPolic  y }
     *             
      * /
    public void         se    tRequestPo       li   cy(Conne    ctionMonitoringServiceCapab    ili tiesStructu    re.Requ   estPolicy va   lue) {   
              this.   requestPolicy = valu   e;
         }

    /**
             * Ge            ts the v  a  lue o                  f th   e subscriptionPoli     cy proper   ty.  
       * 
     * @re  turn
     *     possible    object    is
     *     {@link Capabil  itySubscri       ptio      nPolicy   Structur e     }
     *     
     */  
    public  Capa        bilitySubs criptionP olicyStructure getS    u        bscriptionPol    ic   y() {
        return subscriptionPolicy;
     }

    /**
       * Sets the        value of the su   b           s  criptionPo   licy p      rop   e               rty.
     * 
      * @param va    lue
         *     a   llowed    obje  ct   is
       *     {@link C             apab  i    litySubscri  ptionPolicyS       tr      ucture              }  
        *     
               */
      public void     setSu   bscriptionPol    icy(Capabilit ySubscri        pt        ionPo     lic       yStructur  e value) {
        this.subscriptionPolic      y =         value;
        }     
    
        /**
      *     G       ets the value of the acc  essCon    trol property.
                * 
                  * @r   et   ur     n
           *     possi   ble object is
     *      {@link ConnectionC   apabilityAc  ces sControl   Struct   ure      }
          *         
     */    
    publ   ic      Connecti   onCapabili       tyAccessControlSt        ructu     re getAccessCo       ntr     ol(   ) {
         return accessControl;
    }

    /**
           * Sets the val  ue     o f t  he acce      ss     Co  ntrol pr      operty.
        * 
     * @param      value
     *     allowed object is                 
          *        {@li  nk     ConnectionCapabilityAc cessContr   o  lS   tructure       }
        *            
           */
          pu  blic    voi       d setAccessControl(ConnectionCapability   Acc      es   sCo       ntrolSt    ructu     re valu   e) {
             t   his.accessCo   ntrol = value      ;
    }     

       /* *
     *           Gets       the    valu    e o      f the                  extensions       pr       op       erty.
                 *  
        * @return
     *               pos    sible obj          ect is
     *            {@l      ink E  xtens  i             onsS        tructure     }
     *     
           *         /
      publi  c Extension    sS    tructure   getE       x   tensi             ons() {
                      return             e   xtensi  ons;       
      }

    /**
            *  Sets the value    of the extensio       ns    property   .
           *  
           * @param     value
     *                 allowed object  is
        *             {@lin  k Extension   sStructure }
     *       
     */
    publi     c    v  oid set  Ext   e   nsions(Exte   nsionsS      tructure value  ) {
              this.extensions      =    value;
    }


             /**
     *  <p      >Java cl    as  s for    anon y          mous comp       lex ty       pe.
       * 
               * <p> The followi ng schema fragment specifies the exp          ecte    d cont   en   t     contained withi         n this    class.
         * 
     * <pre>
     * &lt;complexType>
     *   &lt; com  plexC    onte   nt>
      *      &     lt;exte   nsion bas   e="{h           tt   p:/   /w     ww.siri.org.uk/   sir  i}  C  apabil       i  tyRequestPolicyStructu        re">
                 *       &  lt;s   equ     enc           e>
     *              &lt;element   name="ForeignJourney     sOnl   y"   type="{h   ttp://ww  w.w3.org/2001/X    MLSchema               }boole an"  mi   nOccurs="0"/>
      *                        &lt;/se quence         >            
     *     &l  t;    / extension>
     *    &lt;/complexCon     tent>
       *    &    lt;/comp lexType>
              *     <  /pr  e>
       * 
     * 
     */
    @XmlAccessorType  (XmlAccessType.         FIELD)
    @X     mlType(name = "", propOrd     er             = {
                     "foreig     n      JourneysOn           ly"
         })
     public st       ati             c   class Requ es  tPolicy
               extends CapabilityRequ  estPolicyStruc     ture
    {

               @XmlEl   eme  n  t(name = "Fore                  ignJour     neysO  nly", default    Valu e = "   false")    
             pr otected   B    ool   ean foreig  nJour   neysOnly     ;

        /**     
         * G     et      s t         he value of the fo   reig nJourn      eysOnly property.
              * 
                  * @return
                 *            possib   le object is 
         *              {@l     ink       Boolean }
         *        
                       */
            publ  ic Boolean isFore   ignJourneysOnly() {
                      re turn foreignJourne    ysOnly;
        }   
    
                  /* *
            * Se   ts the value   of the f  oreignJourneysOnly property        .
         * 
         * @param value
                 *                  allowed    object is
         *         {@li  nk Boolean }
          *     
         */
        public void set   F              oreignJourn eysOnly   (B     oolean value) {
               this.fore      ignJourneysO   nly = value;
        }

    }


    /**
     * <p>Jav      a class for anonym    ous comp lex type.
      * 
       * <p>The following schema fra         gment specifies the    expected   content contained within   this clas s.
     * 
     * <pre>
     * & lt;complexTy   pe>
     *        &lt;complexContent>
     *     &lt;re   str  iction base="{http://w  ww.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="FilterB   yJourney" type="{http  ://www. w3.org/2001/XML     Schema}boolean"/>
     *            &lt;e leme        nt name="FilterByTime"   type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *       &lt;/sequence>
             *     &lt;/restriction>
     *   &lt;   /complexConten t>
     * &lt;/complexType>
     * </pre>  
       *   
     *     
              */
     @XmlAccessorType(XmlAccessType.FIELD)
        @XmlTyp   e(name = "", propOrder = {
             "filterByJourney",
        "f      ilterByTime"
    })
    public static class TopicFiltering {
      
          @XmlElemen      t(name      = "FilterByJourney")
        protecte   d bo  o  lean filterByJour   ney;
        @XmlElement(name = "F ilterByTime", defaultValue = "true")
          protected boole an filterByTime;
   
        /**
         * Ge  ts the value o  f    the filterByJourney property.
                        * 
              */
        public boolean isFilterByJourney()     {
             return filterByJourney;
        }

        /  **
          * Sets the value of the filterByJ    ourney property.
         * 
         */
        public void setFilterByJourney(boolean value) {
            this.filterByJourney =         value;
        }

        /**
             * Gets the value of the filterByTime propert  y.
         * 
                */
        public boolean isFilterByTime() {
            return filterByTime;
        }

        /**
              * Sets the value of the filterByTime property.
         * 
         */
        public void setFilterByTime(boolean value) {
            this.filterByTime = value;
        }

    }

}
