
package       com.transportation.SIRI_IL.SOAP;

import java.util.ArrayList;
import     java.util.List;
import    javax.xml.bind.annotation.XmlAcces    sType;
import javax.xml.bind.annotation.XmlAccesso        rType;
import javax.xml.bind.annotation.XmlAttr  ibute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.X        mlJavaTypeAda  pter;
   

/**
 * Type for Distr ibutor Delivery for Connect     ion        Mo  nitoring Service.   
   * 
 * <p>J  ava class for Conne    ction    MonitoringDis    tributorDeliv   eryStructure co  mplex type.
 *    
 * <p>The following schema fragment specifies   the expected content   contained    within this class.    
 * 
 * <pre>
 * &lt;complexType na   me="ConnectionMonitoringDistributorDeliveryStructure">         
 *   &lt; complexCo nte nt>
 *        &lt;extension  ba    se="{http:/    /www.siri.org.uk/siri}AbstractServiceDeliveryStructure">
 *       &lt;sequence>
 *         &lt;group ref="{http://www  .siri.org.uk/siri}ConnectionMon       ito  ringDistributorPayloadGroup"     />
  *            &lt;el ement ref="{http:/   /w ww.siri.org.uk/siri}Extensions" m   inOccu  rs= "0"/>
 *                 &lt;/sequence>
 *       &lt;attri bute name="version" use="requir  ed " type="{http://www.siri.org.uk/siri}VersionStri n      g" fixed="1.         3" / >
 *     &l     t;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 *    </pre       >
 * 
 * 
 */
@XmlAcce  ssorType(XmlAccessTyp e.FIELD)
@XmlTy       pe(name = "      Co  nnectionMonitoringDistributorDelivery             Struct ure", prop         Order = {
    "waitProlongedDe   parture",
    "stoppingPositionChangedDeparture",
    "distributorDepart   ureCancellation",
    "ex     tensions"
})
public    class ConnectionMonitoringDistributorDeliveryStructu   re
         extends AbstractServiceDe liveryStructure
{

    @XmlElement(name = "WaitProlongedDeparture")
    protected List<W     aitProlongedDepartureStructure> waitPr      olongedDeparture;   
        @XmlElement(name = "StoppingPositionChangedDeparture")
       protected List<StoppingPositionChangedDepartureStructure> stoppingPositionChangedDeparture;
             @Xm  lElement(   name = "    Distribut    orDepartureCancellation")
      protected Li st<Dis        tr   ibutorDepartureCancellation     Structure>          dist    r   ibutorDe     p   artureCancellation;
    @X   mlElement(nam     e = "Extensions")
       protec ted ExtensionsS   truct    ure extensions;
    @XmlAttr ibute(name = "version", req    uire  d = tru    e)
    @XmlJav   a    Ty   peAdapter(CollapsedStringAd    apter.class)
    protect         ed String      version; 

             /**
     * Gets  the   value of     the waitProlongedDeparture          propert   y.
        * 
         * <p  >
        * Th       is accessor    m   ethod retu r   ns a reference to the live list,    
     * not a snapshot. Therefore an      y modific        atio     n    you ma          ke to the
             * re  turned list will   be pre      sent   inside   the JAXB    objec t   .
        * Th         is is why there is      not a <COD         E  >set</CODE> method for the waitProlongedD              ep     art        ure pr     op e rty.
          *    
     *      <p>
          * For example    , to add a new ite   m,         do      as fo    ll    ow    s:
     * <pre>
                *           getWaitProlongedDeparture()  .a            dd(newItem   );
      * </pre>
            * 
           * 
     *       <p>
     *   Ob  jects of the follow       ing typ  e(s) are allowed in the list
          * {@link      WaitProlongedDepartu  r    eStr    ucture }
               * 
         * 
                *  /
    p   ublic Li  st<  WaitProlongedDe       partur  eStru cture> getWaitProlon  gedDep    arture() {
        if (wait ProlongedDeparture          == null) {
            waitProlongedDepa    rture = new  ArrayList<W  ait    Pro lon gedDepartu  reStructure>();
        }
           re  tu    rn t     his.waitProlongedDeparture ;
    }
      
       /**
          * Gets the value o     f the stoppingPositionChanged De              p        ar         ture property.               
        *        
     * <p>
            * This   accessor m ethod return    s a refere       nce to the live l   ist  ,
     * not a snapsho  t.  T       herefor e any modification you m ake to the
     * returned list will be pre sent insid e the    J      A  X    B obje   ct.
     * Th     is is    why there is n      ot a         <CODE>set        </CO  DE> metho d for the sto  ppingPositionCh  angedDeparture property.
     * 
     * <p>
     * For example, to add a new ite        m      , do as       follows     :
     * <pre      >
        *    getStoppingPositionChan    gedDeparture()  .              add(n       ewIte      m);
     * </pre   >
         *    
     * 
           *     <p>
              * Objects    o f the following ty        pe(s) ar          e    allowed in       th   e list
     * {@lin   k  Stoppi  ngPositionCh   angedDepart    u  reStru cture }
                 * 
     * 
            */
    pu  blic   List<StoppingPositi onChangedDepartureStr  uctur      e> getStoppingPositionChangedDeparture() {
                  if (stoppingPositionChange    dDepart      ure == null) {
               stoppingPositionChanged Departure = new Ar   rayList<Stoppi ngPosit ionChange   dDepa     r   t ureStru  cture>()  ;
        }
             return this.sto    p     pingPositionChang     e  dDep  artu   re;
         }

    /**
               * Gets t h e value of the distr           ibutorDepartureCan      c       ellation    pr   operty    .         
     * 
     * <p>
     *      This        acces    sor m    ethod re tu    rns a refer         ence  to the     li ve list,
     *     not a snapshot   .      Therefo re an   y modifi  c    a   tion yo  u ma    ke to the
     * returned l ist    will be present    insid   e the JAXB      object.
            * Thi      s is     why there i  s not              a     <C    ODE>set</CODE> method   for the     distribu   torDepar  t     ure   Cancell    ation propert  y.
      *    
     * <p>
     *      For example, to a      dd a n  ew it  em,        do as    fo   llows:
          * <        pr   e>
     *       getDi        stributorDep artureCancel          lat      io   n(). ad       d(newItem );
       *   </pre>
          * 
     * 
           *        <p >
          * Objects   of the foll       owing type (s) are  al   lo    wed in t   he         list
     *  {@link Distributo  r      DepartureCa       ncellationStr  ucture }
     * 
        * 
     */
    public     List<Distr  ibutorDe  part                   ureCancel    la  tionStructur     e     > getDistrib            utorDe  par   tureCa nc    ellation() {     
           if (    distribut  orD epart   ureCanc     el  lation == null) {
               distributorDe part   ureCancellation = ne    w ArrayList<Distr   ibutor   DepartureCancellat ionStruct        ure>(     );
            }
              retu   rn thi s.distribu   torDepartur  e     Cancell  ation;   
    }

        /**
                         * Gets    the v    alue of         the ext   e   nsio   ns   property.
            * 
     * @return
      *     possible object      is
        *             {@     link Extens             ionsStru  cture }  
     *     
        */    
    public Ext    ension sStructure getExtension   s    ()           {
        retu   rn extensions;
    }

    /**
     * Sets   the    value o   f the extens  ions property  .
     * 
             * @param valu    e
     *     allow ed ob  j     ect is
       *       {@link Exte   nsionsStructure }
     *     
         *   /
    publi c void    setExte   nsi    ons(Extensions     Structure   value) {
        this.extensions = value;
    }  

    /**
      *     Gets the value of the version property.
     * 
       * @return
        *     possible object is
     *          {@link String }
     *     
     */
       public String getVersio      n() {
        if (v    e    rsion == null) {
            return "1.3";
        } els  e {
            ret    urn version;
              }
       }

    /**
     * S   ets th      e value           of the version property.
     * 
     * @  param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
