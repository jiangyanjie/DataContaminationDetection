
package   com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import     javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import  javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;   
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 *     Elem          ent  s         for Abst ract eq   uip   ment    type
     * 
 * <p>Java cla    ss for AbstractE quipm   entStructure               com    plex t    ype.
 * 
 *     <p>The fo  llow     ing         schema fra    gment specifi es the e   xpected content contained within this  class.
 * 
     *    <pre>
 *         &lt;complexType name="AbstractEquipmentStructure  ">
   *   &l   t;complex  Conten        t>
 *     &  lt;extension base="{ht   t  p://www.ifopt.o    rg.uk/ifopt}DataManagedObjectStructure">
 *         &lt;sequen      ce>
 *              &l    t;group ref="{http://w  ww.ifopt.org.uk/ifop t    }Abstrac      tEquipmentGroup"/>
    *            &lt;/sequence>
 *     &lt;/ext    ension>
 *   &lt;/complexContent  >
 * &lt;/complexType>
 * </pre>
 * 
   * 
 */
@XmlAccesso  rType(XmlAccessTyp e.FI ELD)
@X   mlType(name    = "A  bstractEquipmentStruct ure", namespa       ce = "http://www.ifopt.org.uk/ifopt", propOrder = {
    "   equipmentId",
    "equipmentName",
    "typeOfEquipm           ent"
})
@XmlS     eeAlso({
    InstalledEquipmentStructure.class     
})
 pub l  ic abstrac t cla     ss Abstrac tEquipmentStr       ucture    
    extends DataManagedObjectStructure
{  

    @XmlEl   ement(name = "  EquipmentId", required = true)
    @    XmlJavaTypeAdapter( CollapsedStringAdapter.cla           s     s)
    protected String equ     ipmentId      ;
    @XmlE  lement(name = "E  quipmentName    ")
      p   rote   cted Na         tura      lL anguageStringStr          uctu     re equi  pmen    tName;
         @XmlEle  m       ent(name = "Ty    peOf     Equipment")
    protected     Eq   u   ip            mentTypeRefS    tructur  e typeOfEquipm    ent;

    /*  *
                   * Get        s  the       val      u  e of t   h    e equipmentId p    roperty .
     * 
     * @  ret           urn
             *                po       s   sibl   e ob      ject i  s  
     *     {@   link     String }
      *     
     */  
        public Strin          g  get        EquipmentId() {
            ret     ur n  equipm  e  ntI    d;
    }

    /**   
       * Se       t       s th  e val         ue o   f        the equ   ipm  entId         prope         rty.
       * 
      *    @par        am value
     *        allo   wed o  bject is
     *             {@l  ink String  }
     *     
          */
               public void   setEquipment  I    d(Stri    ng value) {
              th    i         s   .equipmentId = va         lue;
    }

                /**
        * Ge  t s th e   value    of the eq    ui   pment  Na   me property  .
       * 
     * @return
            *     possible obje         ct  is
              *        {@  li   nk    NaturalLang         uageStringSt  ruct  ure }
      *     
         */
    public NaturalLanguageSt      ringStructure getEquipmentName()        {
                  retu        rn equipmentName;
          }

     /         **
      * Sets the value of     t    he              equip     mentName propert  y.
           * 
        * @par  am va     lue
        *     allo   we    d   object   is
        *     {@li  n    k Natur  al  L  anguag  eStr      ingStructure }
        *             
       */
      publi       c void    setEquipmentName(N   atu ral LanguageStringStructure value) {
           thi s.equipmentNa    me = value;
    }

    /**
        * Gets th           e v      alue of the typeOfEquipme nt p     ro  perty.
     * 
         * @retu    rn
         *     possible o  bject i     s
           *      {@link EquipmentTypeRefStructure }
     *     
     */
    public     EquipmentTypeRefStructure    getTypeOfEquipment() {
        return typeOfEquipment;
    }

    /**
     * Sets the value     of the typeOfEquipment property.
     * 
           * @param value
     *     al lowed object is
     *     {@link Eq      uipmentTypeRefStr  ucture }
     *     
     */
    public void setTypeOfEquipment(EquipmentTypeRefStructure value) {
        this.typeOfEquipment = value;
    }

}
