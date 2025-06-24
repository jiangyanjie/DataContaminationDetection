
package    org.kuali.rice.kew.v2_0;

import    java.util.ArrayList;
import     java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import       javax.xml.bind.annotation.XmlAccessorTy   pe;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlEl    ement;
import javax.xml.bind.annotation.XmlType;
import    org.w3c.dom.Eleme   nt;


/**
 * <p>Java class for ActionI      tem   Custo m  ization    Type complex      type.
 * 
 * <p>The f ollow    ing schema fragment specifies t   he expect        ed content      contained w       ithin this class.   
 * 
 * <pre>
 * &lt;complexT      yp e name="Action  ItemCustomizationType">    
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2      0  01/XMLS  chema       }a  nyType    ">
 *         &lt;sequence>
 *         &lt;element nam   e="actionIt   emId"  typ   e="{htt      p://www.w3.org/20  01/XMLSc       hem    a}string" minOccurs="0"/>
 *         &lt;element ref="{ht    tp://rice.kuali.org/kew/v2_0        }a ctio    nSet"/>
 *         &lt;e  lement ref="{http://rice.  kuali.org/kew/     v2_0}display Parameters"/>     
 *         &lt    ;any proces   sContents='skip' namespace='##ot  her   ' maxOccu    rs="unbounded" m inOccurs="0"/>
     *       &lt;/sequence>
 *        &lt;/restric tion>
 *     &lt;/complexConten    t>
 * &lt;/c             omplexType>
 * </pr     e>
 * 
 * 
 */
@XmlAccessorType(    XmlAccessType.FIELD)
@Xm lType(name  = "ActionIte   mCu        stomizationType",  propOrder = {
    "actionItemId",
        "act     ionSet",
    "displayParameters",
       "any"
})
public       clas  s Actio      nItemCusto  mizationType {

    protected String action  I      temId;
    @XmlE       lement(required = true)
    p rotected Act  i    o   nSetTyp e     actio  nSet;
    @XmlElem     ent(required = true)
               p rotected Displ  ayParamete   rsType   displ      ayParameters;
    @XmlAnyElemen        t
       protecte     d      List<Eleme    nt> any;

            /*   *
            *       G    ets the v al         ue of the   actio    nIt       e  mId prope      rt    y.
     * 
     * @return
      *          po          ssible object is     
       *     {@link String } 
     *         
                  *  /
                        public String getActionIte mId() {
              return                a ct              i   onItemI  d  ;      
      }

    /**
     * Sets the      value of     the actionItemId   p    roper     ty.
     *       
      *        @ param value
                *            allowed            o     bject   i         s
     *               {@lin  k S   tri  n  g }    
     *          
       *   /
      public void setAct        i  onItemId(Stri  ng v       alue) {
                             thi     s   .actionItemId = val   ue       ;
    }

        /**
               * Gets   the va  lue of the ac       tionSe         t proper  ty.
      * 
     *       @ret     urn
         *                possi  ble      obj            ec  t is
     *           {@link   ActionSetType }
     *         
      */
                   p   ublic ActionSetT  ype ge   tA    ctionSet()  {   
                    ret ur     n actionSet;
         }      

       /**
     * Sets the va     lue of the     actio  nSet property.
     * 
            *          @param value
          *         allowed o      bj      ec  t    is
           *          {@link     A     cti  onSetType }
     *          
       * /  
    public  void   se      tAc        t     ionSet(Actio   n     Se  tTyp   e value) {
                         th  is.ac   tionS   et = value;
       }

    /**      
            *           G    e  ts the va    lue    of the    di  s   pla  yPar    a  mete        rs       property.
            * 
        * @retur n
     *      p     oss       ible   object is
     *       {@l  ink Di  spla   yParametersT   ype }
             *     
                   */      
        public Disp   l   ayPa   ramet      ersTyp     e getDis        playParameters() {
               return displayP             arameters;
    }

    /**
     * Se    t s the va  lue of the displayP    ara   met    ers  p  roper   ty.
     *            
     * @para  m value
                       *         all  o   wed object is
          *         {@link     Di  spl ayParame   t           e  rsType }
     *     
     */
        public v        oid setDis     pl     ayParameters(    DisplayParametersType  value) {
        this.displayParamet ers = value;
    }  

      /**    
         * Gets the va    lue of the any property.
     * 
        * <p>
     *    This acc  essor   m   ethod returns a reference to the live list  ,
     * not a snapshot. Theref     ore any modifi  cation you make   to     the
     * r    eturned       l  ist will be present inside t   he   JAXB object.
        * This     is    why t   here is      not a <CODE>set</CODE> method for the any   property.
     * 
     * <p>
     * For  example, to add a new item, do   as   follows:
         * <pre>
     *    getAny().add(newItem);
     * </pre >
     * 
     * 
           * <p>
     * Objects of the f   ol    lowing type(s) are allowed in the list
     * {@link Element }
     * 
     * 
     */
    public Li   st<Element> getAny() {
        if (any == null) {
            any = new ArrayList<Element>();
        }
        return this.any;
    }

}
