
package    org.kuali.rice.kew.v2_0;

import    java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
imp ort javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
imp  ort org.w3c.dom.Element;


/**
 * <p>Java class     for ActionInvocationType com     plex     type.
   *   
 * <p>The following schema  frag  ment speci  fies the expected   conten   t containe         d      within this class.
 * 
 * <pre>
 * &lt;          co   mplexType name="Action Invoc   ationT   ype">
 *   &lt;compl    exC    ontent>
 *         &l t;res    triction base ="{    http://w      ww.w3.org      /2      001/XMLSche    ma}anyTy pe">
   *       &lt;se qu     ence    >
 *         &  lt;element name="    action" type="{http://rice.kuali.org/     kew/v   2_0}ActionTy   peTyp    e"/>
 *           &lt   ;element name="actionItemId" type=" {http://www.w3.org/2001/XMLSchema}strin  g"/        >
 *         &lt;a ny processContent     s='skip' name  space='##other' maxOccurs="unbounded" min  Occurs="0"/>
 *       &lt  ;/seq  uence>
 *     &lt;/restriction>
 *   &     lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlA    cces s  orType(XmlAccessType.F IELD)
@XmlTyp   e(name = "Ac    tio   nInvoca    tionType", prop   Order = {
    "action",
    "actio    nItem  Id",
    "a  n     y"
})
      public c                lass ActionI  nvocati           on     Type {

    @XmlElement(r  equi   red = t    ru  e    )
    protecte   d  S       tri    ng act     ion;
                  @XmlElement     (required = tr        u        e)
    pr  ote               cted St r       ing    act    ion  ItemId  ;
            @XmlAnyE                       lement
    pro   t        ected    Lis    t<Element>   a    ny; 
                        
    /**
           * Gets the   va lu   e of t   he  action pr    opert   y  .
        *          
     *       @ret   u    rn
       *        pos         sibl   e      obje     ct is
     *     {@   link String }   
     *     
               */
         public          Str     ing getAct            ion() {         
                    ret    urn actio                                 n;
            }

        /**
          * Sets       the     va          lue of the act   io       n prop     erty.
       * 
                     *     @param value
        *                 allowed   object  is
            *     {@l  ink   String }    
           *     
      */
         public void s  etA ction(String v     alu        e   )   {
                this.act     ion  =   value;
       }  

             /**
                 * Gets the value           o f t   he actio       nItemId pr   operty.
              * 
          * @return
        *         possible o                 bje     ct is        
     *     {         @link S      t  ring }
           *         
       */
      public S    tring     ge    tAc   ti  onItemId() {
                return actionItemId;
     }

       /*     *
      * Sets      the v   alue o  f the ac  tionItem  Id pr  opert   y .
            * 
       *    @param va    lue
     *     allowed object i   s
     *     {   @  l  ink String }
      *                  
         */
    public     void setActionItemId(S  tring    value  ) {
        this.ac    tionItemId = value; 
     }

          /**
     * G     ets the value of   the any prop     erty.
     * 
         *    <p>
     *            This ac   cessor method returns a refe  re        nce t   o       the  live list,
     * not a snapshot. Theref           ore   an   y modification y   ou ma     ke to the
     *  returned list w        ill be present inside     the    JAXB object.
     * T      his i   s why ther      e is no  t a <CO    DE>set</CO  DE> method for the any property.
     * 
          *         <p>
     * For example, to add a new item, do as f    ollows:
     *   <pre>
     *    getAny().      add(newItem);
          * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are all owed in the list
         *   {@link Element }
     * 
     * 
     */
    public List<Element> getAny() {
        if (any == nu  ll) {
            any = new ArrayList<Element>();
        }
        return this.any;
    }

}
