
package   com.transportation.SIRI_IL.SOAP;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import        javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/*        *
 * T      ype for Del  iveries for Connection       Monitoring Service. Used in WSDL.
 *         
 * <p>Java    class for ConnectionMonitor  ingDeliv  eriesSt     ructure complex type.      
    * 
 * <  p>The following schema fragm   ent specifie   s the expected content   c       ontained within this   class.
 * 
 * <pre  >
 * &lt;comp   lexType name="ConnectionMonit   oringDeliveriesStruc           ture">
 *   &lt;complexContent>
 *     &lt ;restriction    base="{http://ww   w.w3.or       g/2001/XML Schema}  any  Type"  >
 *       &l     t;sequ  enc   e>
      *         &lt;choic e> 
 *           &   lt;element      ref="{http:/    /www.siri.o   rg.uk/siri}Conn     ectionMonitoringFeederDelivery"    ma   xOc    curs="u  nboun  ded"/>
 *           &lt;elem    ent ref="{http://w  ww.siri.org.uk/siri}Conn   ect ionMonitoringDistrib        utor    Delivery" m          a     xOccurs="unb  ounded"/>
 *            &lt;/choice>
      *       &lt;/sequence>
     *     &lt;/restric       tion>
 *   &lt;/     com    plexContent>
 * &lt;/complexType>
 * </pre>
  * 
 * 
 */
@XmlA  ccessorType(XmlAccessType.FIELD)
@XmlType     (name = "ConnectionMonitoringDeliveriesStructure", prop      Order    =  {
     "connectionMonitoringFeederDelivery"    ,
    "connectionMonito  ringDistributorDelivery"
} )
public clas    s ConnectionMo  nitoringDeliveriesStructure {

    @XmlElement(name = "ConnectionMoni   toringFeederDelivery")
    protected List<ConnectionMonitor     ingFeede   rDel  ive    ryStructure> c         onnectionMonitoringFeederDelivery;
      @XmlElement(name = "Co    nnect     ionMonitoringDistributorDeliver   y")
     protected       List<ConnectionMonitoringDistributorDelive ryStructure> connectionMo   nitor    ingDi      s      t rib utorDelivery;

    /*    *
     * Delive   ry           for Con      nection Prot       ection         Fetche  r Servic    e.G   et        s the value o f the    conne ctionMonitoringFeederDeliv  ery     property.  
           *     
     * <    p>
         * This accessor method returns a reference to            the l    ive list,
     *  no   t a snapshot.   Therefore any modif    icat      ion you ma   ke     to t     he
     * returned list will    be present    inside the JAXB  objec   t.
     * This is       why there is     not       a <CODE   >set</C     ODE> method for th    e connecti onMonito    ringFeederD elivery pro  pe    r  ty.
       * 
     * <p>
     * For exampl      e    , to add a new item, do as follows:
              * <       pre>
              *             getConne  ct       ionMonit  oringFeed      e    rDeliv  ery().ad    d(newI tem);
           * </pr       e    >
        * 
               * 
     * <p>    
           * Objects o    f th     e follo     wing type(s) are allowed     in the list
        * {@   link Connecti onMoni  toringFeederDeliveryStructure }
       * 
     * 
     */
    public    List<Connectio  nMonitoringFe    e   derDe   liveryStructure> getCon n       ectionMo       n    itori    ngFe  ederDel     ivery() {
        if (connectionMonitoringFee  derDelivery == null)    {
             connectionM       onitoringFeederDelivery   =      ne   w Ar rayList<ConnectionMo  nito ri ngFeederDeliveryStru        ct ure>()    ;
         }
        return this.connectionMonitoringF       eederDelivery;
    }

             /  **
     *  Deli     very for     Connection Pro   tecti    on Fetche         r          S e    rvice.Gets the value of the connection     Monitoring       Dis    tributo        rDelivery      property.    
                 * 
     *    <p>
          * This accessor     meth      o d returns a refer    en     ce to the live   l  ist,
        * not    a      sna  pshot. T  he     refore any m   odific ation you make to  the
     * ret  urned list will be presen   t inside the JAXB object.
     * This     is why there is not a <CO     D       E>s  et</CODE> m   ethod   for the connectionM   onitoringDistrib    utorD    elivery         property.
           * 
     * <    p>
                 * For exampl   e, to add a    n  ew item, do as follows:
     * <pre>
      *    getConnectionMonito    ringDis      tributorDelivery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects      of the following type(s) are allow      ed in the list
     * {@link ConnectionMonitoringDistributorDeliveryStructure }
       * 
     * 
     */
    public List<ConnectionMonitor      ing  DistributorDeliveryStructure> getConnectionMonitor   ingDistributorDelivery() {
        if (connec   tionMonitoringDistributorDelivery == null) {
            connectionMonitoringDistrib  utorDelivery = new ArrayList<ConnectionMonitoringDistributorDeliveryStructure>();
        }
        return this.connectionMonitoringDistributorDelivery;
    }

}
