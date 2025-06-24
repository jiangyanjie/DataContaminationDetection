
package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import   javax.xml.bind.annotation.XmlElement;
import     javax.xml.bind.annotation.XmlType;


/**
 *  Type   f    or B  ody o f Da   ta Supply Request.    Used      in WSDL
 * 
        * <p>Java class for DataSupplyR  eque     stBodyStructure co  mplex type.
 * 
 * <p>The following schema fragment specif ies the expected conten    t co  ntain      ed within this        class.
 * 
 * <pre>
 * &lt;complex   Type name="Data     SupplyRequestB     o  dyStr       ucture"  >
 *   &lt;complexCo   nt     en         t>
 *     &lt;restr   ic    tion      b      ase="{ht tp://www.   w3.org/2001/XMLSchema}an   yType" >
 *        &lt;sequence>
 *                                &lt;gro   up re  f=  "{http://www.siri.org.uk/siri}DataSu    p    plyTopicGroup"/>
 *       &lt;/sequenc    e>
 *           &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexT       ype>
 * </pre>
 *    
 * 
 */
@XmlAccessorType(Xml Access    T        ype.   FIELD)
@Xm lTyp   e(n ame = "DataSupplyRequestBodyStructure", propOrde  r = {
    "notificationRef",
    "allData"
})
public class DataSupplyR   equest Bod  ySt       ructure {

    @XmlElement(n       ame = "     Notificat ionRe          f")
        pro tected MessageRe   fStructure noti   fica t   ionRef;
       @XmlElement(na   me = " AllData  ", defaultValue = "false")
               p      rote  cte    d      B   oolean all           Data;

                 /*         *
     * G          ets the v       alue of  th     e notif    ic  at   ionRef    pr       operty.
            * 
          *   @return
          *           possi  ble objec t is
        *        {@li  n      k Me    ss       ageRe  fStru    cture     }
         *        
       */
        public    Messa ge  RefStructure getNo    t ific    ationRef(   ) {
        return no   tifi  c   a  t    i    onRef;
       }
  
      /**
     * S    e        ts the   val  ue of         the noti       fi   cat   ionRef p     roperty      .
          * 
     * @pa  ram       value
       *     all  o       wed    object is
             *     {@l       ink Messag  e      RefStr uctu      r    e }
          *     
     */
             p     ublic void setN otificationRef(MessageRefSt ru ctur e v  alue) {
                this.notificati  o      nRef =      value;
      } 

    /**
      * G    ets  the value of th      e all     Da    t   a property.
     *               
       * @retu    rn
      *              po   ssib le object is
        *     {@link Boolean }
     *     
     *    /
    public Boolean isAllDat     a() {
            r    e           turn allD   ata;
    }     

       /**
     * Sets    the value of the allDat   a property.
     * 
       * @param value
     *     allowed object is
     *     {@link Boolean   }
     *     
     */
    public    void setAllData(Boolean value) {
            this.allData = value;
    }

}
