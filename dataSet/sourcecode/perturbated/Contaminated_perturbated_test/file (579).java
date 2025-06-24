//
//      This file    was g       enerated  by the JavaTM Architec  ture for      XML Binding(JAXB) Re ference Implementation, v2.2.4-2 
/   / See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/     j   axb</a> 
// Any modification  s        to this file will be lost   upon recompilation of the source schema. 
/   / Generated on: 2014.10.30 at 12:18:26   PM EDT 
//


package com.rsa.transarmor.dto;

import javax.xml.bind.annotation.XmlA   ccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import j    avax.xml.bind.annotation.XmlElement;
impor     t javax   .xml.bind.anno    tation.Xm   lType;


/**
 * <p>Java c     las  s for    Debit  Grp   complex t ype.
 * 
 * <p>The following schem  a   fragment spec    ifies the expected      content c     ontained within this class.
     * 
 * <pre        >
 *   &lt;complex  Type name="DebitG     rp">
 *       &   lt;complexContent>    
 *        &lt;restr ic  tio     n b    ase="{h tt   p://www.w3.org/2001/XMLSchema  }anyType">
 *       &lt;sequence>
 *         &lt;element ref="{com/firstdata/Merchant/gmfV3.10}     PayeeP     honeNu    m" minOccur   s          =       "    0"/>
           *              &lt;element ref="{com/firstdat   a/Merchant/gmfV3.10}Pa    yeeAcctNum"        minOc  curs="0"/>
   *          &lt;element ref="{com/firstdata/       Merchant/gmf      V3.10     }Pay    eeID" minOccurs="    0"/>
 *         &lt;e    lement ref="{com/firstd   ata/Merchant/gmfV3.1    0}B  illingInd" minOccurs="0"/>
 *         &lt;element ref="{               com/firstdata/Merchant/gmfV3.10   }FPI"      minOccurs="0"/>  
     *       &lt;   /       sequence>
 *     &lt ;/restriction>
 *   &lt;/complex  C       ontent>
 *    &lt;/com  plexType>
 *     </pre      >
 * 
 * 
 */
  @XmlAccessorType   (XmlAc     cessType.FIELD)
@XmlType(name = "DebitGrp",   propOrde  r = {
    "payee  PhoneNum",               
    "    payeeAcctNu     m",
    "payeeID",
          "billing I      nd",
      "fpi"
})
public cl     as       s   DebitGrp   {

    @XmlEleme      nt(name = "PayeePhoneNum")
        protected Str   ing     payeePhoneNum;    
    @  Xml Element(name = "Pay   eeAc   ctN        u  m")
    protec    ted       String   pa     yeeAcctNum;  
    @X     mlElement(name = "P ayeeID")    
    pro   tected Str ing payeeI    D;
    @XmlEleme nt(name =    "BillingInd"         )
    prote     ct    ed String   billingInd;
    @XmlElement(na  me = "FPI" )   
                      p      rotec    ted Strin g fpi;

    /**
                        * Gets the value of            the payeePhoneNu      m pr           operty   .
             * 
             * @    re turn
                    *       po  ssib    l   e        object is
            *      {@link Stri ng }
          *          
          */
     public String ge    tPa   ye  e Pho    neNu    m()  {
          return     payeePhoneNum;
    }    

     /**
     *    Sets the value of t he paye e Phon   eNum p      rope         rty.
           * 
           *         @par am    value
     *      al lowed ob ject    is  
       *     {@li nk            St       ring }    
     *     
         */
    p ublic  void s    etPayeePh                on  eNu     m(Strin g   value)    {
                                 th  i   s.  p   ayeeP    honeNum       = val  ue;
            }

                 /**
                   *   Gets the va  lu e of the payeeAcctNum property.
      * 
     * @return
         *            possibl   e o     bject is
         *          {@l i     nk S     tring }
     *     
     */
    p   ublic String  getPayee    AcctNum() {
               ret   urn      p    ayeeAcctNum ;            
      }

       /**    
           * Sets the val     ue        of the pa    y      e   eAc  ctN       um              property   .
        *  
     * @  param     value
             *     a      llowed    ob   ject      is
          *           {@link String    }
     *     
        *      /
                         publ   ic voi     d    s     etPay e   eAcctNu    m     (Str        i  ng      value) {
             this.payeeAcctNum       = value;
             }

    /**
         * G ets the         value of the p       ayeeID         property.
                        * 
     * @retu    rn
         *          pos     sible object is  
            *           {@link Str   i        n    g    }   
             *                  
         */
            publi c St ring ge       tPayeeID(    ) {
           retu  rn payeeID;
           }

       /* *
              * Sets the             valu    e of      the   payeeI              D pr   operty.
     *      
         *    @param   va  lue     
       *           allowed object is
     *      {@link Stri        ng    }
     *                      
       */
    pu    blic voi    d set   PayeeID(Strin     g    value) {
                 this.p     ayeeID = v  alue;
    }

    /**
                    * Gets the  value of the     bill ingInd    property      .  
            * 
          * @return
     *     possible      object i s
       *     {@link String }
        *         
     */
         public       Str       ing g      etBillin    gInd() {
           return bill          ingInd;
    }

    /**
     * Sets the      value of the billingInd prop    erty.
     *        
                 * @pa   ram value
     *     allowed object is
     *     {@link    String }
     *     
                *     /
    public              void setBillingInd(String value) {
        this.billingInd = value;
    }

    /**
     * Gets the valu    e   of the    fpi property.
     * 
     * @return
          *     possible object is
     *     {@link String }
     *     
     */    
    public String getFPI()   {   
          return fpi;
    }

    /**
     * Sets the      value of the fpi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFPI(String value) {
        this.fpi = value;
    }

}
