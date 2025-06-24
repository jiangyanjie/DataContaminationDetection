
package ExperianLtdCompanySearchWS;

import     javax.xml.bind.annotation.XmlAccessType;
im   port javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

 
   /**
 * <p>Java class for C  RO     complex   typ    e.
      * 
 * <p>The following schema frag ment    specifies the expe  cted content contained within thi    s      class.
 * 
 * <pre>
 * &lt;complexTy       pe n      am  e="CRO">
 *   &lt;complexContent>
    *     &lt;restriction base="{http://www.w3.org/200       1/X    MLSchema}anyType">           
 *             &lt;sequ  ence>
 *         &lt;element name="DocumentType" type=    "{http:         //www.w3. org/    2001/XMLSchema}st   ring" mi  nOccurs="0"/>
 *         &lt;element name="DocumentDate" type="{http://sche  ma.uk.expe   rian.com/experian   /bi/generic/b   sns/v100/basetypes} CCYYMMDD" minOccur s="0"/>
 *       &lt;/sequence>
 *     &lt;/restrictio n>
   *   &lt;/comp    lexContent>
 * &lt;/complexType>
 * </pre   >
 * 
 * 
 */
@XmlAccessorType(XmlA           ccessType  .FIELD)
@    X   mlType(name  = "CRO", propOrder =  {
      "documentT   ype",
    "docum     e     ntDate"
})
publi     c class CRO {

    @XmlEle     ment(nam    e      = "DocumentType"  )
      protect  ed   String doc    umen  tType;
    @XmlElem  ent(name = "D  oc    umentD  ate"         )
       p   rotected CCYYMMDD d      oc  u  mentDate      ;              

    /**
         * Gets t    he value of the d  oc       um e    ntT  ype     prop   erty.
              * 
      * @r   e     tur   n
            *                         possi   ble ob   je ct is
       *          {@lin    k     Strin g   }
       *                  
     */
       publi        c String ge    tDocu               mentTy    pe(  ) {
                   r     e   t  urn        documentTyp          e;
        }  

    /**
     * Sets the value       of     the documentT    ype        p   roperty.
     * 
        *      @pa  ram va lue  
                           *     allo       wed    obj     ect is   
              *          {@link         String    }
     *     
              */
          p    u       bli   c      void  setDocumentT   y    pe     (String        value) {
                this.docume    ntType = value;
    }

           /**
     * Gets the v  alue of    t      he documen      tDate pro    perty.
       * 
          *         @re     turn
     *     possible ob   ject is
     *     {@ link C   CYYMM  D D }
     *     
     */
    p     ublic CCYYMMDD getDocumentD    ate()        {
        return docum  entDate;
    }

       /**
     * S   ets the value of the doc    umentDate proper     ty.
     * 
     * @param value
      *     allowed object is
     *           {@link CCYYMMDD   }
     *     
     */
    public void setDocumentDate    (CCYYMMDD value) {
        this.documentDate = value;
    }

}
