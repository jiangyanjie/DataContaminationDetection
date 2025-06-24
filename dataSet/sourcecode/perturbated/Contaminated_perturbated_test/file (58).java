//
// This    file    was gen   erated   by the Jav    aTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.       4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/j   axb</a> 
/    / Any modi fications  to this fil   e     will be lost upon recompila  ti     on of the source     schema. 
// Gen      erated on: 2014  .10.30 at 12:18:26 PM     EDT 
//


package com.rsa.transarmor.dto;

import ja   vax.xml.bind.annotation.XmlAccessType;
i  mport javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annot      ation.XmlType;


/**
 * <p        >    Java class for CustIn foGrp comp     lex      type.
 *    
 *    <p>The f  ollowing schema fragment specifies the expected con          tent contained within this class.
 * 
 * <pre>
 * &lt;co   mplexTyp     e name="CustInfoGrp">
 *   &lt;complexCon           ten     t>
 *      &lt;re     striction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;seq    ue nce>
 *          &lt;element ref="{com/firstdata/Merchant/   g  mfV3  .10}AVSBillingAddr" minOccurs="0"/>
 *            &lt;e  lemen     t ref="{co   m/firstdata/Merchant/gmfV3.10}AVSBillingPostalC   ode" minOccurs="0"/>
 *         &lt   ;ele    ment ref     ="{com/first   data/Mer chant/gmfV3.10}CHFirstNm"   minOccurs="        0"/>
 *                &lt;elemen t r  ef="{c           om/ f   irstdata/Merchant/g    mfV3.10}C  HLastNm" minO c      curs="0"/>
 *         &lt;   element ref="{com/fir  stdata/Mer  chant/g    mfV3.10}   CHFu    l lNmRes " minOccurs="0"/   >
    *       &lt;/s     equence>
 *     &lt;/restri        ctio     n>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *        
 * 
 */
@Xml       AccessorType(Xml      AccessType.FIE    LD)
@Xml   T  ype(name   = "CustInfoGrp",     propOrde      r = {
           "avsBillingAd dr",
    "avsBillingPostalCode",     
          "chFirs  tNm",
    "chLastNm",
     "chFullNmRes"
})
pub lic class CustInfoGrp {       

    @XmlElement(name =  "AVSBillingAddr")
    protected          String avsBilli ngAddr;
    @XmlElement  (name = "AVSBillingPo   st    alCode")
    protected String avsBillingPost  alCod e;
                 @X  mlEleme      nt(name = "CHF    irstNm")
        prot ected String chFirstNm;
    @XmlElement(  name = "CHLastNm")
    protected String chLastNm;
        @XmlElemen   t   (n   am  e     = "CH   Full    Nm             R      es") 
    pr  otected CHFullNmResType chFul       lNm  R   es;

             /**
     * Gets the va       lu   e of the    avsBilling            A       dd      r property.   
     * 
                        * @    return   
            *            po   ssible object               is
     *     {@   lin  k  String }
       *     
     */
    publi     c String getAVSB    ill  in         gAddr() {
        return                 av      sBilli                 ngAdd     r;
    }
   
     /                  **
     *     Sets    the value          of    the          a   vsBilli      ng    A      ddr pr    op e   rty.
     * 
      *  @param value
        *     a llow       ed objec    t is
            *      {@   li  nk    String }
     *     
        */
           public v    oid    set A    VSBil               l  in gAddr(String va lue) {
           t      his.avsBillingAddr = value       ;
       }    
   
    /*   *
       *          Get             s the value of the av                       sBillingPostalCode prope    rty.
          * 
     *    @ret urn
     *     pos   s  ible     obje c      t is
            *     {@link String }
     *           
                   */
    pu blic Str   ing        getAVS    BillingPo     stalCo       d         e() {
              ret   urn avsBill  ingPos        t alCo    d  e;   
      }

      /**
       * S         ets the value of the avsB  illing   PostalCode property.
      * 
       *           @param v   a lue
        *     al    lo   wed       object is
       *                 {@link S  tring }
                 *              
           */
    public        vo  id setAVSBilling           Po  stal   Code(String valu e) {
                this   .avs   Bil lingPo   stalCode    = v alue;
                  }

             /   **
          * Ge   ts th    e                  value        o  f the ch    First   Nm property.  
           * 
                          *         @r     etu  rn        
       *                             possib   le        ob    ject           is
     *      {@link St     r    in     g     }
          *           
           */
          publi   c Strin  g   getC  HFir   stNm    () {
        retur   n c    hF irstNm;
         }

    /**
        *      Sets   th             e val         ue of the chFir     stNm      pr    op    ert y.
        * 
     *  @  param valu   e
     *          allo    wed      o   bject           is
           *     {@link String       }
     *          
                  */
            pu   blic void setCH     F      irstNm(Strin  g valu e) {
         th  is.chFir   stNm =   value;
    }

    /     **
             *  G  et s the value of t  he  chLastNm    property.
     * 
     * @retur    n
     *       p ossible obj       ect is  
         *     {@link String }
       *     
       */
     public String getCHL   as     tNm() {
        retur    n ch    La   stNm;
      }
    
    /* *
     * Sets the value of the chLa stNm property  .
        * 
     * @param value
     *     allowe          d object is
     *     {@link String }
       *     
     */
    public vo    id set   CH     LastNm(String va       lue) {
        this.chLastNm    = value;
    }

    /**
     * Gets the value of the c hFul     lNmRes property.
                * 
     * @return
         *            possible object is
     *     {@link CHFullNmResType }
     *     
     */
    public     CHFull    NmResType getC    HFullNmRes() {
        return ch      FullNmRes;
    }

    /**
     * Sets the value of the chFullNmRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link CHFullNmResType }
     *     
     */
    public void setCHFullNmRes(CHFullNmResType value) {
            this.chFullNmRes = value;
    }

}
