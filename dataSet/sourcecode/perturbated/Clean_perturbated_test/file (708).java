
package org.kuali.rice.location.v2_0;

import java.util.ArrayList;
import   java.util.List;
impo    rt javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
impo   rt javax.xml.bind.annotation.XmlType;
impo   rt org.w3c.dom.Ele   ment;


/**
 * <p>Java      class for  CountryType comple  x type.      
 * 
 * <p>The fo        llowing schema fr   agmen  t specifies the expected      content co ntaine  d within this cla  ss.
 * 
 * <pre>
    * &lt;complexTyp    e    n  ame="CountryType">     
 *      &lt;complexContent>
          *     &lt;restriction    base=    "{http://www.w3.org/2001/XMLSc   hema }anyType">
 *           &l   t        ;seq   uence>
 *          &lt;e  l      ement name="code"   type="{h  t    tp://w   ww        .w3.org/2001/XMLSchema}string"/>
 *         &lt;element   name="alternateCode" type="{http://w        ww.w3.org/20    01/XMLS    chema}string" min    Occurs="0"/>
 *            &lt;element name="name" type="{http://www.w3.org/2001/X     M     LSchema}string" minOccurs="0"/>
 *           &lt;   element name="restricte  d" type="{http://     www.w3.org/2001/XMLSchema         }b  oolean"/>
 *            &lt;ele  ment name="active" type="{http://www     .w3.org/2001/XMLSchema}boolean"/>
    *         &    lt;element name="versio   nNumber" typ   e="{http://www.w3.or         g/2001/XMLSchema}long  " minOccurs="0"/>  
 *         &lt;any p       r     oces    sCont   ents=   'skip' namespace='#          #other' maxOccurs="unbounded" minOccur     s="0"/>
            *         &lt;/sequence>      
 *      &l t;       /restriction>
 *          &lt;/complexContent>
               * &lt;/comple  xTy         pe>
 * </pre>
 *    
 * 
 */
@Xm     l Ac  cess  orType(XmlAcces sType.FIELD)
@XmlType(name = "CountryType",  p  ropOrder =    {
    "     code",
    "al  ternateCode",
      "name",
        "restricted"    ,
    "active",
            "vers  ionNumber",
    "any"
})
     public    class CountryType {
  
    @XmlEl emen t(requi red    =  true)
       p  rotecte   d  String code                  ;
    protec  te     d Str  ing alterna            te   Code;
        pro   tected     String    na    me;
    prote       cte  d bool ean rest r       icted;
                      prote        cted boolean act  ive;
       prot    ected Long vers                ionNumb    er;
      @XmlAnyEl         em    ent  
       prote     cted    List<   E lement> any;    
             
       /       **
           * Gets    the       value of   the      c      ode       prope  rty.
              *     
     * @re        t urn
     *     possible object is
     *      {@link S tri     ng                }
     *     
      */
              publ    i   c String             getCode(    )    {
              return c   ode;
    }

    /**
                       * Sets    t     he valu   e of             the co       de property.
         * 
        * @par  am           value
     *                                           allowed o     bject i s
        *       {       @ link Stri ng     }
        *       
     */       
    public void setCode  (String value) {
              t  his .    code    = value    ;
     }

       /**   
     * Gets the value of    the alt ernate      C   ode property.
     * 
     * @r     eturn
        *       po    ss      ible objec t i s
        *        {      @link      Str     ing }
                              *          
           */
    public       S  tr  ing     getAl                     te rnateCode() {
              retu   r      n a    lterna      teCode;
    }

    /**
       *        Sets the va   lue       of the alte        rna teCode proper   ty.
     * 
       *    @para  m   value
     *       allowed objec      t     is
     *          {@link   String  }
     *           
          */
       public          v oi    d setAlternate  Code  (Str    ing value        ) {
                    this            .alternateCo  de = va  lue;
       }

    /**
             * Ge          t  s             the valu    e  of    th    e name proper   ty             .
                * 
                          * @return
     *                        poss ib  le obj    ect is
     *         {@   lin            k String }              
         *     
                               */
              p      ubl  ic St      ring  get         Name() {
            r  et       ur    n name;
        }

            /**
          * Sets the val    ue of th           e  name property.
             * 
         * @param va  l   u       e
              *                 allowed   objec              t    is 
                *          {@li     n k String }
                   *                
                             */
              publi    c             void setNa  me(S   tr i       n            g   v  alue  )     {
        this.name =            value;
    }

       /*    *
         * Gets       the v       alu        e   of the restricted pro   perty.
          * 
     */
       publi  c b    oolean    isRe      st   ricted() {
              return restricted;
      }

    /*    *
      * Sets   the va            lue of the restric    ted p  roperty.
       * 
                   *        /
    p   ublic void set    Restricte      d(boolean  val  ue) {    
           this.rest       ricte    d =   valu        e ;
      }

    /**
     * Gets     the value of the act   ive property.
     * 
               * /
                  public boolea                 n isActive() {      
        return a cti   ve;
    }

                     /**
         *     Set s the value of the a  ctive propert     y.
     * 
     */
          publi c voi   d setActiv     e(boolean value  ) {
        this.ac  tive =     value;
     }  

    /   **
     *   G    ets the va    lue of   the v       er          sionNumb    er proper     ty.
     * 
       * @return
     *     p  ossible object is
     *     {@link Long }
       *         
     */
          public           Long getVersionNum    ber() {
        return versionNumber;
           }

    /*    *
     * Se   ts the value of the versi   onNumber   property.
     *   
     *                  @param value
          *         allowed object is
        *         {@lin  k Long }
     *     
     *    /
              public void setVersio     nNumber  (Long va    lue) {
        this.versionNumber = value    ;
    }
      
         /**
     * Gets the value of the an  y proper  ty.
     * 
     * <p>
     * This accessor meth      o  d returns a reference to the live list,
     * not   a snapshot. Therefor  e any modification you make to the
          * returned list will be present inside the JAXB object.
     * This is why th  ere is not a <CODE>set</CODE> method for the any proper       ty.
     * 
     * <p>
           * For    example, to add a new    item, d  o as f   ollows:
     * <pre>
        *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in th  e list
         * {@link  Element }
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
