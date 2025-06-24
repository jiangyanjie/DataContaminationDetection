
package ExperianLtdCompanySearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import    javax.xml.bind.annotation.XmlElement;
imp  ort javax.xml.bind.annotation.XmlType;


/**
 * <p  >Java class    for CurrentSecret     ary complex        type.
 *        
 * <p>Th    e following schem   a      fragment specifies  the expected  c     o  ntent      contained within     this class  .
   * 
 * <pre>
 * &lt;complexType name="Current   Secreta     ry">
 *   &lt;compl   exContent>
 *     &lt    ;    restric     tion b      ase="{http://w   ww.w3.org/2001/XMLSchema}anyType">
 *        &lt;sequence>
 *                   &lt;elem      ent name="Foreig  nAddressFlag" type=  "{htt p://www.w3.org/2001/XMLSchema}  string  " minOccurs="0"/>
 *         &lt;elem    ent na    me=   "SecretaryCompanyFlag" type="{http://www.   w3.org/2001  /XMLSchema}st  ring" minOccurs="0"/> 
 *         &lt;element name="DirectorNumber" type="{http ://www.w3.or       g/2 00      1/XMLSchema}string" minOccurs="0"/>
 *                &lt;element name="Appointme     ntDate" type="     {http://schema.uk.experian.com/experian/bi/generic/bsns/v100       / basetype  s}CCYYMMDD"          minOccurs="0           "/>
 *         &lt;e     lemen    t name="App  ointmentIndicator   " type="{ht         tp://www.w3  .o rg/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NOCRef" type   ="{http://www.w3.org  /20     01/XMLSchema}string" minOccurs="0"/>
 *         &lt;elem    ent name="SecretaryDetails" type="{http://  schema.uk.experian.com/experian/bi/generic/business/limited/v   1    00/baset   ypes}SecretaryDeta     ils" minOccurs="0"/> 
 *          &lt;/sequence>
 *           &lt;/rest     riction>
 *       &lt;/     comp   lexContent>
 * &lt;/compl exT  ype>  
 * </pr       e>
 * 
 * 
 */
@XmlAccess   orType(XmlAc        cessType.FIELD)
@Xm   lType(name = "CurrentSecretary", propOrder =      {
         "f    orei     gnAd dressFlag",
             "secretaryCompan  yFla   g",
    "directorNumber",      
              "appoin   tmentDate",
    "ap  pointmentIndicator",
    "noc    Ref",
    "secre ta   r   yDetails"
  })
public class CurrentSecretary {

       @XmlElement(name = "ForeignAddres      s  Fla g")
      prot   ecte  d S  tring fore   ignAddressFlag;
    @XmlEle   ment(name =            "Secre      taryCompanyFlag")
    protected String s     ecretaryCompanyFlag;
    @Xml    Element(name = "DirectorN  u          mber")
            pr        o             tected    Strin g d       irectorNumber;
    @   XmlElement(n  am     e   = "     AppointmentDate")
          protected CCYYMMDD appointm  entDate;
    @XmlElement(na   me = "AppointmentIndicato  r  ")
       pr            otec    ted     String appoi n tme   ntIndicator;
          @XmlElement(           name = "N OCRef")
    prote cted St      ring nocRef;
    @XmlElement(n a          me = "Secreta           ryDeta  ils")
    prote    cted Se  cretaryDe   ta         ils    sec       retar      yDetails;
              
          /   **
     * Ge   ts the value of      the foreignAddressFl     ag property.
             * 
             * @retu        rn
               *     possible obj  ect       is
                    *     {@lin   k String }
     *     
        * /
    public String getForeignA     ddressF     lag(    )            {                          
          re       tu  r      n foreignA   ddres sFlag;  
        }

    /**
       * Sets the      value of the foreig nA       d   dressFla   g pr       oper ty.
        * 
     * @param  value   
       *       a   llow ed           obj    ec  t is
        *     {@link Strin   g }
               *         
     */
    public voi     d setF    or   ei     gnAdd   r          essF  l ag(Strin    g value) {
             t    his.foreignA        ddressFlag = value;
    }

    /**
     * Gets the valu  e o    f the secret  aryCom     panyFla      g pr                        operty.       
         *              
           * @return
     *       possible o    bj        ect is  
                              *     {@  link St     ring }
         *                             
         */
           public Str ing ge   tSecret ar     yCompan   yFl    ag() {
        return secr   et   aryCompanyFlag;
        }

                /**      
          * Sets  the value o  f   the s ecretaryCompanyFlag pro   pe   rty.
      * 
            * @param           va       lue
                *     all  owed                 obj ect i      s
       *                         {@link String }
       *           
         */
         public voi   d setSecret aryCompa nyF lag(S t rin          g val     ue) {     
                                  this.secr  et  aryC  ompanyF l              ag = valu     e;
     }

     /**
        * Get   s     the value       of the directorNumbe              r property.
        *    
            * @retu      rn    
     *                       p  ossible o        bject is
         *     {@  li      nk St ring }         
     *     
          */
       public   Str   ing get     D      i                 rectorNumber() {
           return directorNumbe    r;
    }
   
                     /**
           *    S    e  ts the va    lue of t he dir     ectorN  u      mber propert  y.
     * 
            *            @pa        ram v   alue
       *     allowe d object   is
          *     {@     link       St      ring    }
      *     
                    */
    public     void se       tDi  rectorNumber(St    ring va     lue) {
        thi   s  .directorNumber = valu    e;
           }

    /*  *
         *    Get           s the v     alue  of the ap             po   intm      entD  ate proper       ty.
             * 
        *   @return
        *                   possible object    i       s
            *          {@link CCYYMMDD }
                         *        
      */
      p   ubli   c CCYYMMDD getAppo          in       tmentDate(      ) {
                      return     appoi      ntmentDate;    
    }
          
    /      *  *
      * Sets the       value of           the a     ppo   intmentDate p   ro     perty.
     *    
     *                    @p                  aram val          ue
       *           a          llowed object is
     *     {@link       CCY    YM    M  DD }
                *       
     */
    public void      se    tA ppointmentDate(CCYYMMDD value)       {
        this          .appoi           ntm entDate = va      lue;
        }

    /**
             * Gets the value of the a          ppointme     n   tIndic ator pr operty.
          * 
     *   @r     eturn    
       *       possi   ble object is
     *            {@l     ink    Stri ng }
                *     
           */
    public String getAppointmentIndicator() {
           re  turn appointmentIndicator;
    }
        
    /**
       * Sets the      val        ue of the appointmentIndicator pro  perty.
       * 
           * @pa    ram value
     *              allowed ob       ject    is
                       *     {   @  link S       tr ing }
     *     
        */
    public void set   Ap     p   oi ntmentIndicator(String value) {
        this.appoi   nt mentI   ndicat     or =   value;
       }      

    /**
     * Gets the value of the   nocRef pr  operty.     
     *    
         * @return
     *     possib        le object is
     *     {@li  nk String }
     *     
     */
    pub   lic       Str ing g      etNOCRef() {     
           r  eturn nocRe f  ;
       }

          /     **
          * Sets   the value of the nocRef property.
     * 
            * @par   am value
     *     allowed object is
     *     {@link String }
     *     
     */
    pu  blic void setNOCRef(String value) {
        this.n       ocRef = value;
    }

    /**
     * Gets the value of the secretar   yDetails property.
     * 
     * @return
     *     possible object is
         *     {@link    SecretaryDetails }
     *     
     */
    public SecretaryDetails getSecretaryDetails(  )   {
        return secretaryDetails;
    }

    /**
     * Sets the value of the secretaryDetails property.
     * 
     * @param val     ue
     *     allowed object is
     *     {@link SecretaryDetails }
     *     
     */
    public void setSecretaryDetails(SecretaryDetails value) {
        this.secretaryDetails = value;
    }

}
