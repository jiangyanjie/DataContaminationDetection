
package com.transportation.SIRI_IL.SOAP;

import    javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
imp ort javax.xml.bind.annotation.XmlElement;
imp   ort javax.xml.bind.annotation.XmlSchemaType;
imp     ort javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import   javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCa    lendar;


/**
 *   Type for Common eleme   ntd for a            SIRI serv   ice delivery of the              Form xxxDelivery.
 *   
 * <p>Java class for AbstractSe   rvice    Deli    veryStructure complex      type.
      * 
 * < p>The fol  lowing s che       ma fragment specifies the expected content  contained within t  his class    .
 * 
 *       <pre>
 * &lt;complexType name="A bstrac  tS ervi ceDeliveryS      tructure">
 *   &lt;compl     e      xContent>
 *               &l       t  ;  extension base="{http        ://www.     siri.  org.uk/sir     i}Re sponseS     tructure">
 *       &lt;s       equence>
 *             &lt;choice>
 *                &l t;group   ref="   {http://www.siri.org.uk/siri}Co    ntextualisedRespon  seEndpointGroup"/>
 *                          &lt; group ref="{http://www.sir   i.org.uk/siri}SubscriptionIdentif    ierResourcePropertyGroup"/>  
 *                 &lt;/ch      oice>
 *                         &lt;g  ro        up ref="{http://www.siri.org.uk/siri}DeliveryStatusGroup"/>
 *         &lt;group ref="{http://www.sir     i.org.uk/siri   }Del     iveryDefaultGro        up    "/>
 *       &lt;      /sequence>
 *     &lt;     /extension>
 *   &lt;/complexContent>
 * &lt   ;/com   p   lexTy    pe>
 * </pre>
 * 
 *   
 */
@XmlAccessorTyp               e(XmlA  ccessType.FIELD)
@XmlType(  name = "AbstractSe  rviceDeliveryStr  u    cture", pro  pOrder = {
    "requestMessageRef",
    "subscriberRef",
            "subscrip tionFilterRef ",
    "subscriptionRef",
    "status",
    "error Condition",
    "validUn    til",
        "shortestPossibleCycle    ",
      "defaultLang    uage"
})
@XmlSeeAlso({
    EstimatedTimetableDeliverySt  ructure.cl  ass,
    VehicleMonitoringDeliveryStructure.class,
    ProductionT  imeta  bleDeliveryStructure.class,
          FacilityMonitoringDeliveryStructure.class,
         SituationExchang eDeliveryStructure.class,
         StopTi   metableDeliveryStructure.class,
    StopMonitoringDelive   ryStructure.class,
    ConnectionTimetableDeliver    yStru  cture .cla  ss,
    Con   nec  t        ionMonitoringFeed     erDeliveryStructure.clas  s,    
     ConnectionM     onitoringDistributorDeliveryStructure.class,
    GeneralMessageDeliveryStructur  e.class
})
public class AbstractServiceDeliveryStructure
    extends R       esponseStructure
{

    @XmlElement(name = "RequestMe     ssageR    ef")
    protected   MessageQ       u   alifie   rStructure requestMessageRef;
    @XmlElement(name = "SubscriberRef")
    protected Partici   pantRefStructure subscrib erRef;
          @XmlElement(name = "SubscriptionFilt     erRef")
    prote    cted         Subscrip   tionFilterRe fStructure subs       criptionFil    terRef;
    @XmlEleme     nt(name = "Subs  criptionRef")
    protected Subscri      ptionQualifierStructure su bscriptionRef;
        @XmlElemen         t(name = "Status", defaultValu   e = "true")      
    protected Boolean status;
    @XmlElement(name        =   "ErrorCondi    tion")    
    protect     ed Servi     ceDeli     veryErrorConditionStructu   r   e            errorCon   di      tion;
    @XmlElement(name     = "ValidUntil")
    @X    ml            SchemaTyp      e(name =   "dateTime           ")
      protected XMLGreg    o  ria nCalendar      validUntil;
      @XmlElement(name =       "ShortestPossibleCycle")    
      prot ected Du  ration    short  estPo    ssibl          eCycle;
    @XmlEl          ement(name = "D  efaultLang   uage")
        @Xm    lJavaTypeAdapter(C    ollapsedS  tringAd   apt            er.clas s)
    @ XmlSchemaType(n          ame = "lang uage"    )
                  pro                 tected String defaultLa  nguage     ;

             /*  *
        * Gets the   value of the requestMessa   geRef p    r  operty.
             * 
          * @return
     *     possib le object         is
       *       {@link MessageQualifierSt         ructu    re }
               *          
      *              /
                   p  u     blic Messa   geQualifi   er      Struct     ure get  RequestM   essageRef( )    {
                 return req    uestMessageR       ef;
         }

    /**
     * Set      s t he   value   of the r equestMes       sageRef p  ro      perty.
     * 
        *       @param val   ue
     *           allowe  d object i        s
         *     {@    link Mess   ageQualifier    Structure }
                *     
     */   
              public void set        RequestMessageRef(MessageQ  ualif  ierStructure va              lue       ) {
                     this.req        uestMessageRef = valu    e;
    }
           
                     /**
     * Gets the v           al    ue of t   he sub         scriberR ef prop er      ty.
       * 
        * @return
       *      possibl    e object is
              *     { @  link     P      articip   a     ntRefStruc     tur  e }
     *     
     */
      public ParticipantRe fS   tructure get SubscriberRef() {
                return subscriberR ef              ;
       }

     / **    
      * S       e  ts the value of the su   b   s  criberRef proper   ty.
           * 
            * @pa    r am    value
     *              al  lowed   object        is
     *            {@link Participan  tRefStru     cture    }
     *     
     */
            pub   lic void          setSu   b   scriberRe   f(Participant    Ref   Structure val ue)        {
        this.subscriberRe f = v  alu e;
    }

      /**  
     * Gets the   val  ue   of  th         e       subsc      r iptionFi     lterR    ef pr    opert    y.
     * 
         * @r     etu   rn
     *         possi  bl             e object is
                      *     {@link Sub    script           ionF  i   lterRefStruct ure }
           *       
     */
    public SubscriptionF    i            lterR     efStructure get   Su   bsc       r  iptionFilterRef()   {
                  return subscri  ptionFilt er    Ref  ;
             }
         
                  /**
     *        S   ets  the val   ue      o       f the      subscriptionFilterRef p   roperty.
        * 
     *    @par    am        value
     *        allowed object    is
           *               {@link Su   bscriptionFilte    rRefSt   ruct    ure }           
     *     
     */
    publ  ic void set        SubscriptionFil terRe f(Subscript   ion  FilterR     efS  tructure    value)  {
                  thi  s.s  ubscriptio  nFilte     rRef      =   value;
    }

    /**
     * Gets the v  alue of the subscr      iptionRef property  .
     * 
     * @return
                   *                    possible ob  ject is
        *          {@    l     in k Subscript  ionQualif      ierStr    uctu  re }
     *     
     *       /    
                  p      ublic Su    bscript ionQua       lifi       erSt      ruc  ture      ge tSub s          criptionR   ef() {
           retur   n    subscr    ipt io  nRef;
      }
     
     /   *     *
     *       Sets t he value      of      th    e subscriptionRe   f property.
      *    
                    * @param value
     *     allo                w   ed object    is
       *          {@l  ink S         u     bs criptionQuali fierStructu re }
             *           
      */
      p    ublic       voi d se    tS   u                       b scrip       t   i on   Ref(Su    bs   criptionQualifie  rStr   uct   ure valu e)     {
           this              .subscription     Ref = value   ;          
    }

               /       **
          *    Gets the value      of the     status pro     pert   y.
          * 
      * @            re      turn
                *     possible obje                             ct               is
       *     {@link Boolean  }
     *               
     */
            p    ublic Boolean isStatus   () {
                   retu   rn   status;
           }   

             /**
     * Se   t   s the v alue    of the sta        tus property.
           * 
          * @param   value
     *         allowed object is
     *                 { @lin  k Boolean  }
     *     
        */
        publi     c void se       t  Status   (Boolean valu      e) {
        this.stat     us = value;
    }

      /**
         * Gets   the    val        ue of t  he er rorCondition property.
     * 
        * @retur     n
     *       p  ossi       ble obj      ect is         
         *        {        @   link ServiceDelive  ryErr o  rCo nd    itionStructure           }
      *     
           *  /
    public  Ser    vi  ceDelivery   ErrorConditionStructure getError    C  on d itio     n() {
         retu             rn errorC       o    nditi      on  ;
     }

    /**
     * Set s  the value of   t  he  errorCon   dition property.
     * 
         * @pa   r    am   v  alue   
     *        allowed obj       ec    t        is
     *         {@l    ink Se   rviceDe    l   ive     ryErrorConditionS   tructure }
         *     
          */
    public void setErrorCondition(   Ser   viceDelive  ryErrorCondi  tionS       tructure value) {
           this.err   o   rCondit ion = value;
    }

    /**
              * Gets the value of the v  alidUntil proper    ty.   
     * 
     * @   ret   urn
       *                possib    l  e object i    s
     *          {@link XMLGregorianCalendar }
         *     
        */
    public XMLGregoria nCalendar get  ValidUntil() {
        return va lidUntil;
    }

       /       **
     *     S ets     the value o f th      e valid   Unt      il p   roperty.
     * 
     *    @param value
     *     allowed object is
            *     {@link XM    LGregorianCalen                  dar    }
         *     
     */
    pub      lic void setVal   idUntil(XMLGregorianCalendar value) {
          this.v      ali      dUntil = value;
          }   

    /**
        * Gets t he val ue of the shortestPossibleCycle proper   ty.
            * 
     * @re  turn 
       *     possible object is
     *     {@link Duration }
          *     
     */
    pu   blic Duration getShorte      stPossibleCycle() {
        r etu   rn shortestPossibleCyc   l e;
        }

    /**
     * Sets the value of the shortestPossibleCycle property.
     * 
     * @para m value
     *     allowe d object is
        *          {@link Durat    io   n }
     *     
           */
    public void setShortestPos  sibleCycle(Duration value) {
        this.shortestPossibleCycle = value;      
    }

    /**
       * Gets the value of the defaultLanguage property.
     * 
     * @re turn
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultLanguage() {
        re   turn defaultLanguage;
    }

    /**
     * Sets the value of the defaultLangu   age property.
        * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultLanguage(String value) {
        this.defaultLanguage = value;
    }

}
