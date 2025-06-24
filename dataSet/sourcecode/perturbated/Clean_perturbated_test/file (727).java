
package    DBFullReportsClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
imp ort javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**    
 * <p>Java class for CourtInformationType complex     ty   pe.  
 * 
 * <p>    Th     e following schem    a fragment speci  fi    es the expected c  ontent   cont    ained wit hin this class.
 * 
 * <pre>
        * &lt;c   omplexType name="CourtInformationType">
 *   &lt;complexContent>
 *     &lt;rest     riction base=   "{http    ://www.w3.org/   2001/XMLSchema}a   nyType">
 *       &lt;se q  uen   ce>
 *         &lt;element name="CourtName" typ              e   ="{     http://www.w3.or g/2001/XMLSchema}string" mi  n      Occurs="0"/>
 *         &lt   ;element name="CourtAddress" type="{http://gateway.dnb.com/getProduct}  AddressType" minOccurs="0"/>
 *                   &lt;element name="CourtType" type="{http://www.w3.org/2001/ XMLSchema}string" minO ccur s="0"/>
 *               &lt;e  lement name="Attor neyName"        type="{http://www.w3.org     /2001/XML         Schema}   string" minOccurs="0"/>
 *       &lt;/se      qu    ence>
 *     &lt;/restriction>  
 *             &lt;/complexConten           t>
 * &l    t;/complexT    yp   e>
 * </pre>
 * 
 *      
 */
    @XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name    = "CourtInformationType"       ,  prop    Order   = {
    "courtName",
    "courtAddress",
      "court   Type",
    "attorneyName"
     })   
pu    bl   ic class Court    Info         rmationType   {

     @XmlElement(name     = "    Cou  rtNa  me")
       pr     ote cted String courtName;
    @X  mlElement(name = "C   our     tAddress")
    protected AddressType court  Address;
    @XmlElemen t(nam   e = "Cou rtTy pe "  )
    pro     tected   St   r   ing cour   tType;   
           @XmlEl ement(na      me   = "At torneyName"    )    
      prote     cted String att   orneyName;

    /*   * 
           * Ge  ts t  he value of       the cour     tName pr                o       perty.
        *    
     * @return     
                      *           pos               sible ob           ject    is
     *                {@li  n  k      St    ring }   
           *                      
          */
       public  St  rin   g get   CourtName     (  )       {
              re      tur   n cour    tName;
    }

      /**
              * Sets the      v       alue of the courtNa    me prop  er     t  y      .
        * 
     * @pa    ram value
          *             allowed ob     ject is
                   *     {       @li  nk String }
     *     
     */
                    public v           oid s    etCourtName  (            S     tr i    ng   value) {
            this.courtN   ame =       v al  ue;
    }

       /**
     * Gets the        value of       the  courtAddres     s         pr  operty        .
             *             
         *  @   return
     *     p  ossible object is
         *           {   @l   ink           AddressType }
               *     
                               */
    public Addr   essType   g etCourtAddress() {
           return                c  ourtAddre  ss;
          }

          /   **
     * Set     s t      he valu     e of the          courtA                     ddress  p   roperty.
         * 
             * @   p aram      valu                 e
                      *                            al lowed ob  je  ct i   s
           *     {@li     nk          Ad         dre   ssType       }
                  *     
            */
    pu       blic void           setCo     urtA                    ddre        ss(A   ddress    Ty  pe value) {
                  thi        s.courtAd   dress = value;
    }

        /     **
              * G   et      s the value o       f           the  court     Type property.
            *        
     *     @return
     *            possibl    e obje   ct is
     *        {@link     S  tring }
                  *     
     */
    p     ublic Str ing     ge  tCou    r   tType() {
        ret    ur      n courtType;
               }

    /*     *
     * Sets t   he val ue     of the cou     r  tType property     .
                     * 
     * @param value
     *     allowed object is
            *         {@link String }
       *     
     */       
    public v   oid setCourtType(String valu      e      ) {
                      th      is       .courtType = value;
    }

    /**
        * Gets th  e v  alue o     f the attorneyName    pr      operty.
     * 
     * @return
      *     possible object is
        *     {@link      String }
     *     
     */
    public String getAttorneyName() {
               return attorneyName     ;
      }

       /**
     * Sets the value of th     e attorneyName pr   o   perty.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public   void setAttorneyName(String value) {
        this.attorneyName = value;
    }

}
