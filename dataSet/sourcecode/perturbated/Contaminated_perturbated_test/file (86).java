
package      DBFullReportsClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
i      mport javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class     for CustomerInformationDocOrderType compl   ex type.
   *  
 * <p>The following sch    ema fragment specifies the    expe    cted cont  ent   contai    ned within this clas     s.
 * 
 * <pr  e>
 * &lt;complexType name="CustomerInformat   ionDocOrde  rType">
 *   &lt;complex   Conte  nt>
 *     &    lt;restriction base="{http://ww    w   .w3     .org/2001     /XMLSchema}a         nyType">
 *          &lt;se    quence>
 *         &lt;element n ame="SignonCredentials" type="{http://gatew ay.dnb.  com/ge       t       Product   }SignonCredentialsDocO    rderType"/>
 *         &l  t;element na      me="EndUserR  ef" type="{http   ://gateway.dnb.c  om /getProduct}EndUserRefT         ype" minOc   curs="0   "  /  >
 *       &lt;/se quence>
 *     &lt;/restriction>  
 *       &lt;/complexContent>
 *     &lt;/complexType>
 * </pre>
 * 
  * 
 */
@XmlAcc  essorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerInfor    mationDocOrderType", propOrder = {
    "signonCredentials",
    "endUserRef"     
})
publ   i    c class CustomerInformationDocOrderType {

    @XmlElement(name = "Sign      onCredentials",         required   = true)
    protecte   d SignonCredentialsDocOr  derT   ype sig    nonCredentials;
    @X      m   lElement(n  ame = "En     dUserRef")
    protect  e       d Strin     g endUserRef;

       /**      
     * G  ets the           value of the signonC    redentials pr   operty.
     * 
        * @              retur         n
     *     p     ossible object    is
     *     {@link Sign   on     Cr ed  e   nti        als   D      oc   O        r           derT     y    pe }
     *         
        */
      pu  blic Signo   nCredentia  lsDo    cO    rderType getSignonCredentials()     {
           re           tu rn   s    ignonCredentials;
    }
   
             /  **
           *      Sets the val  ue  of the signonCrede  nti  als pro    perty.
          * 
     *      @param val    ue      
     *     allowed object i s
     *          { @link  S       ignonCr  ed         entials  DocOrderTyp  e    }
       *         
               */
        pub lic vo   id se  t SignonCre    d   entia   ls(SignonCre     denti  alsDo                    cO    rde                    rT ype value) {
           thi       s   .signo      nCrede ntials            = val ue;
    }
    
        /* *
     * Ge     t              s the     val    ue of   t  he endUserR    ef property.
       * 
     * @return
     *     possible ob  ject is
     *     {@link String }
     *          
     */
    public      String getEn    dUserRef() {
            return endUserRef      ;
         }

    /**
     * Sets the value of the endUs  e  rRef property.
     * 
     * @param value
     *                allowed object is
     *     {@link String }
     *     
     */
    pu blic void setEndUserRef(String value) {
        this.endUserRef = value;
    }

}
