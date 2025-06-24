
package ExperianLtdCompanySearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import    javax.xml.bind.annotation.XmlAccessorType;
imp    ort javax.xml.bind.annotation.XmlElement;
import  javax.xml.bind.annotation.XmlType;


/**
      * <p>Java cl ass      for Corporat   eStructure complex type.
   * 
 * <p>The fol lowing schema fra     gmen      t spe cifies     the expected content contained     w        ith   in this class.
    * 
 * <pre>
 * &lt;complexType     name="C  orporateStru ctu      re">
 *           &lt;c    omplexConten     t>
 *     &lt;restriction bas    e="{http://ww   w.w3.org/2001/XM   LS    chema}a     nyType">
 *            &lt;sequence>
 *         &lt;element name="CompanyOwnership" type="{http://s     chema.uk.experian.com/experian            /bi/generic     /business/limited/v100/basetypes}CompanyOwnershi    p" minOccurs= "0"/>
 *         &lt;element name="Subsidiar   ies" type="{http:/ /schema.uk.exper   ian.com/e  xperian/bi/gene   ric/busi      ness/limited/v100/basetypes   }Subsidiaries" minOccurs  ="0"/           >
 *         &lt;/seq     uence>
 *      &l    t;/rest    r   i   ction>
   *   &lt;/complexContent>
 * &lt;/complex  Type>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlTy pe(   name = "C  orporateStructure", p ropOr  de  r =      {    
    "companyOwnership",
    "subsidiaries"
})
publi      c class C   orporateStructu   re {     

    @XmlEl     ement(name = "CompanyOwners              hip")
     pro   tected CompanyOwnership co      mpan  yOwnership;
             @      XmlElement(   name = "S ubsi    diarie           s")
        protec    t      ed Subsidiar  ie       s subsidiar ies;

    /**
                  *   Gets the va   lu   e    of the companyOwnership pro    perty.
                 *    
        *       @re turn
      *              possible ob      ject i   s
     *           {@link   CompanyOwn     ership }
     *     
     */
    public   Com       panyOwn e   rship g  etCom panyO   wnership  () {
              re      tu  rn  compan       yOwnership;
        }

    /**
     *          S ets t  h   e v alu      e o     f the     com  pany     Own  er             ship    prop      erty.  
       *   
                    * @     param    v    alue
     *      a   l            lowed o  bject is
           *       {@link CompanyOw    ner   ship }
     *     
                    */
    public    voi     d s    e   tCompanyOwn           ership(Compa  nyOw        ners   hip value) {           
           this.    company  Ow n ership = va       lu         e;
     }

    /**    
     * Ge      ts the value of the     sub      sidiaries property.
        * 
     * @return
         *     po  s     sible object is
     *      {@link Subsidiaries }     
     *     
         */ 
    pub    lic Subsidiaries ge  tS   ubsidiaries() {
            r eturn subsidiaries;
      }

    /**
                * Sets the value of the subsidiaries property. 
        * 
     * @param v  alue    
     *     allowed object is
     *     {@link Subsidiaries }     
     *     
     */
    public void se     tSubsidiaries(Subsidiaries value) {
        this.subsidiaries = value;
    }

}
