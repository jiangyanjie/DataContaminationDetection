
package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import    javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
imp ort javax.xml.datatype.Duration;


 /**
 *      Subs  cription Request      for Connecti  on M  onit oring.     
 *  
 * <p>Java  class for ConnectionMonitoringSubscriptionRe           questStructure comple      x t  ype.    
 * 
 * <p>The following sch         ema fragment specif  ies the expected content contain   ed within this class.
 * 
 * <pre>
   * &lt;complexType name="Conne    ctionMonitori    ngSubscri    pt ionRequestStructur    e">
 *   &lt;complexConten       t>
        *     &lt;     extension base="{htt   p://www.sir  i.org.uk/          siri}AbstractSubscriptionStructu    re">
 *       &lt;sequence    >
 *            &lt;element    ref="     {http://www.siri.org.uk/siri}ConnectionMonitoringRequest"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}Co   nnection   Mon         it   o     ringSubscrip t   ionPolicyGroup"/>
   *         &lt;/sequence   >
 *     &lt;/extension>
 *   &lt;/co   mp        lexContent>
  * &lt;/complexType>     
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.     FIELD)
@XmlType(name = "ConnectionMonitoringSubscripti onRe    questStructure", propOrde     r = {
    "connectionMonitoringRequest",
    "changeBeforeUpdates"
})
public class ConnectionMonitor  ingSubsc     riptionReq    uestStructure
    ext    ends Abst   ractSubscripti  o   nStructure
{

    @XmlElement(name =   "Con    nectionMonitoringRequest", require d = true)
    protect   ed Con  nectionMonitoringR      equestSt    ruc    ture connectionMonitori      ngR    equest;
    @XmlElement(name     =    "Ch   angeBe  for      eUpd  ate      s")
                    protected   Dura    ti    on changeBeforeUp  dates;
   
    /*        *
       *         Gets the val    ue of the connectio     nMon   itoringReq  uest proper     ty.
      * 
     * @       re   t urn
               *     possible object is
      *     {@link C   onn      ectionMon     itoringRequestStr              uc ture }
         *         
         */
     p  ublic ConnectionMonitoringReques tStructure ge   t   Con  necti     onMon   itorin   gRe   que       st() {
             re  turn connectionM   onit  o   ringRequest;
    }

    /**
       * Sets the valu  e of the connectionM       on   it   oringRequest property.
        * 
                     *     @param value
     *     all   owed o               bject is
     *          {@link   Connection  Monitoring Reques      tStructu          re     }
     *     
     */
          public vo  id setConnectio    nMo        n   itoringRequest(Co    nn   ectionM        oni    toring               Requ                         estSt   ructure value     )        {
            this.conne  ctionMo                   n    itor ingRequest = value;
    }

               /**
            * Gets the value of the change  B         eforeU    pdates pr     o  pe    rty.
     * 
       *   @re turn
     *     possible object is   
                  *     {                 @link Durati   on }
     *     
                    */
    public        Duration getChangeBeforeUpdates() {
           retu          rn changeBeforeUpdates;
    }

    /**
                * Sets the value of th  e changeBeforeUpd   ates property.
     * 
     * @para  m value
     *     allowed   object is
     *     {@l  ink Duration }
     *     
     */
    public void setChangeBeforeUpdates(Duration value) {
        this.changeBeforeUpdates = value;
    }

}
