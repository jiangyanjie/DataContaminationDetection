
package com.transportation.SIRI_IL.SOAP;

import  javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import      javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

   
/**
 * Abs  tract typ    e ffor capability a     ccess control 
 *    
     * <p>Java class for ConnectionCapabilityAccessControlStructure complex type.
 * 
 * <p>T  he followi   ng schema fragmen    t specifies the expec     ted content contained   within      this class.
    * 
 * <pre>
 * &lt;complexType name="ConnectionCapabilityAccessControl    St     ructure">
 *   &lt ;complexContent>
 *     &lt;extension base="{http://    www     .siri.o    rg.uk  /siri}CapabilityAccessC       o  ntrolStructure">
 *           &lt;seque  nce>
 *                &lt;elemen   t ref="{http://www  .siri.org.uk/siri}CheckOperatorR      ef" minO   c  c    urs="0"/>
   *              &lt;element r e  f="{http://www.siri.org.uk/siri}Che   ckLineRe        f" mi   nOccurs="0"/>
 *                 &lt;element ref="{http: //www.siri.org.uk/siri}C    heckConnect ionLinkRef" minOcc  urs     ="0"/>
 *       &lt;/seq     uence>
 *         &lt;/extens    ion>
 *   &lt;/com plexCon     tent>
 * &l    t;/complexType>
 * </ pre>
 * 
   * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionCapabilityAccessCo  ntrolStruct       ure", p               ropOrder =      {
    "chec  kOperatorRef",
    "checkLineRef",
    "c    heckConnectionLi    nkRef"
})
pub      lic class     Conn   ectio   nCap  abilit    yAccessCo      ntrolStructure
        extends CapabilityAccessControlStr ucture   
{  

    @Xml           Element(name      = " CheckO    peratorRef", defaultValue = "true")      
    protected Boolean che ckOperato    r      Re f;
    @XmlElement(name = "CheckLineRef", defaultVal    ue =      "tr   ue")
          protected Boolean c heck     LineRef;
    @  XmlEle ment (na me   = "CheckConnecti    onLinkRe      f", defa      ultVa                lue    = "true")
      p    rotected B   oolean checkConnectionL  inkRef;   

                /**
                                *      Gets the value of  the checkOperatorRef    p    rope rty.
              * 
         * @  return
             *            possible obje   ct             is
          *         {@link Boo   le   an }
       *      
            */
         pub       lic     Boole     an          i       s   Ch         eckOperato      rRef() {
          return ch            eckO     pe     rat  orRef;
         }

             /**
     *   Sets     th     e va    lue of the c   heckOperat     orRef pro    perty.
           *   
     * @p   aram value
      *           allo        we    d object is
                        *                   {@link   B  oole     an }
      *         
              */
     publi c void s  et         Check    Opera   to         rRef(Boolean v       a        lu   e) {
            this.ch     eckOper atorRef =   value;
    }
   
     /**          
     * G ets   the value   of the checkLineRef     propert         y.
      * 
          * @return
        *      poss    ible object is
     *     {@link Bo    o      lean    }
     *               
         */   
                   public Boole a n isCheckLineRef() {  
          return       c            h          eckLin eRef;
        }

         /  **
     *  Sets the value of the chec  kLi neRef     proper  ty.
     * 
            *  @         param    valu       e
           *     allow  ed    object is
        *     {@link Boolea       n }
     *             
     */
    public void setC     h eck   LineRef(B    oolea    n value) {
        thi       s.ch   e       c kLineRef = value    ;
    }

    /**
     * I     f access contr   ol is sup       ported, whether access       con   trol  b  y connecti   on link is supported. Defa     ult is      true.
        * 
       * @retur   n
      *     possible o    bj   ect is
     *     {@       link Boolean }
             *     
       */
    public Boolean isCheckConnec   t   ionLinkRef() {
        retur  n checkConnectionLinkRef;
    }

      /**
     * Sets t he value of the checkC  onnec   tionLin  kRef property.
     * 
     * @param value
        *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCheckConnectionLinkRef(Boolean value) {
        this.checkConnectionLinkRef = value;
    }

}
