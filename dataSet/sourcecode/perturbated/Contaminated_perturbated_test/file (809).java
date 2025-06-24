
package com.smartmuni.services.soap;

import java.util.ArrayList;
import java.util.List;
import     javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>      Java class for deleteResult comple     x ty  pe.
 * 
 * <p>The foll  owing schema frag   ment    specifies the expected     conte    nt contained     within t      his class.   
 * 
 * <pre>
 * &lt;complexType name="    de  leteResult">
 *   &lt;       complexContent>
 *       &lt;restrict     ion base="{http://www.w  3.org/200  1/XM LSchema}an       y Type     ">   
 *       &lt  ;sequence>
   *           &lt;element name="errors" type="{http://services.      smartmuni.com/so     ap}error" ma   xOccurs="unbounded"   min    O   ccurs="0"/    >
 *                      &lt;element n ame="id" type="{ht  tp://www.w3.org/2001/XMLSchema}s   tr ing" minOccur  s="0"/>
 *         &  lt;element name="succ   e   ss" type="{h  ttp://www.w3.org/2 001/XML           Schema       }   boole     an"/>
 *       &lt;/sequence>
 *        &lt;/     res        triction>
 *   &lt;/comp    lexC   on      tent>
 *    &lt    ;/com plexTyp    e>
   * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType   .FIELD  )
@X  mlType(n ame = "deleteRes   ult", propO            rder = {
    "errors",
          "id",
    "su  ccess"
})
public c   lass De      leteResult {   

    @Xm  lElement(nilla    b     le = true  )
      protected List<Error> er                 rors;
      pro tected String id  ;
    protected boolean suc   cess;

            /*   *
     *  Gets the value of the e rrors property      .
     * 
          * <p>
     * This acce     ssor   method re  tu rns a ref   eren     ce to the        l   i         ve l ist,
            * not a s     napshot. Therefore any modification you        ma          ke to the
            * retur   ne d list        wi         ll be present inside the JA  XB object.
            * This is why     there is not a <CODE>                  se   t   </   CODE> method for t  h         e errors prope   rty.
          * 
                * <  p>
         * For ex   ample,    t        o add a new   it  em,    do               as fol  l       ows:
            * <pre>
     *            getE                rr ors().add     (newI  tem);
       *  </pre>
      * 
             *    
               * <p>
             * Ob       jects  of     the follo     wing t                  ype(s)  are allo   w    ed in     the             li    st  
                          * {   @link E    rro  r }
     * 
      * 
     */  
      pu   b            lic     List<E rror> getErr   ors() {
           if (er   rors == null)        {
                e   rrors = new ArrayList<Error>();
                            }
            return       thi  s.errors;
         }

    /**
       * Ge  ts            the va   lu       e of     the   id    p    roperty.
             *  
        *    @return
                *               p   o    ssib          le obje    ct is      
             *     {@link String   }      
     *               
     */
    publ  ic     String getId() {
        ret urn id;
                        }

            /**
      * Sets the value o f t  he id   pro   perty.
     * 
        * @param value
         *     all owed object is
     *        {@link String }
     *           
     */
    public void setId(String va  lue) {
        thi      s.id = value;
    }

    /**
     * Gets the   value o    f the success pr    operty.
     * 
     */
    public boolean isSuccess() {
        retu      rn success;
    }

    /**
     * Set     s the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

}
