
package org.kuali.rice.kim.v2_0;

import java.util.ArrayList;
import    java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
imp  ort javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
imp    ort javax.xml.datatype.XMLGregorianCalenda  r;
import org.kuali.rice.core.v2_0.StringMapEntryListType;
import org.w3c.dom.Element;    

   
/**
 * <  p>Java       class for De legateMe  mberType com   plex type.
   * 
 *  <p>The follow    ing schema f  ragment specif       ies the expected     content contained within this c las    s.
 * 
 * <pre>
    *  &lt;complexType name="DelegateMemberTyp  e"  >
 *          &lt;complex    Content>
 *     &lt;restriction base      ="{      http://www.  w3.org/2001/XMLSchema}anyType">
 *               &lt;sequence>
     *         &lt;element name="del    egationMemberId" type="{http://www.w3.o   rg/2001/XMLSchema}       string" minOccurs="0"/> 
 *         &lt;element name  ="delegationId" t   ype="{http://www.w3.org/2001/XMLSchema}string" minOcc   urs="0"/       >
 *                    &lt;element name="memberId" type="{http://www.w   3.org/2001/XMLSchema}st ri  ng"  minOc              curs="0"/>
 *         &lt;element name="r  oleMemberId" type="{http:/   /www.w3.org/2001/XMLSchema}string" minOccu   rs="0"/>
     *               &lt;element name="typeCode" type="{h     ttp://www.w3.or    g/2001/XMLSchem    a}string" min   O   ccurs="0"/>
 *         &lt;element name="attributes" type="{htt     p  ://rice.ku      ali.org/core/v2_0}StringMapEn     tryListType" minOccu     rs="0"/>
 *           &lt;element name="activeFromDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"       />
 *           &lt;element name=    "activeToDate" type         ="{http://www.  w3    .org/2001/X     MLSchema}dateTime" minO cc  urs="0"/>
 *         &lt; element name="versionNu    mber" type="{http:/  /www.w3.org/200    1/XMLSchema}       l   ong" minOccurs="0"/>
 *         &l t;any processConte   nts='skip  ' namespace='##other'      maxOccurs="unbounded"  m  inOccurs="0"/>
 *               &l  t;/sequence>
 *     &lt;/restriction>
 *   &lt;/c ompl  exContent>
       * &lt;/complexTyp  e>
   * </pre  >
 * 
 * 
 */
@XmlAccessorType(   Xm lAccessType  .F IE   LD           )
@XmlType(na me = "Delega teMemberType", propO    rder =  {
    "delegationMe  m berId",
    "del    eg ationId",
    "membe    rId     ",
    "rol   eMemberId",
    "  typeCode" ,
    "attributes",
    "activeFromD ate      ",
    "activeToDate",
      "versionNumber",
    "any"
})
public class DelegateMemberType {

              protected String delegatio n    MemberId;
    protected String del    egationId;
        pro tected String membe   rId;
    prote  cted Str    ing  roleM   e   m berId;
       pr otect   ed Strin     g typ   eCo  de;
    protect     ed StringMapEntryListType attributes;
     @XmlSchemaT     ype     (name    =          "d ateTime")
      protected X   MLGregoria   nCalenda   r acti      veFrom Date   ;
    @XmlSchemaType( name = "dateT im       e")
    pro    tected XMLG regor     ianCale   ndar activeTo          Da    te ;         
    p   rotected Long version       N      umbe       r;
    @X   mlAnyEl            e                 ment
        protec   ted List<Element> an     y;

             /**
         * Gets     the value of t       he      dele      gatio   n Me mberI  d     p       ro   pe rty.
            * 
           * @ret     urn
     *          possibl   e object i      s
     *     {@lin  k String     }
     *     
          */
          publ    i  c St           ring getD        elegationMember    Id(  ) {
                          retu  rn dele    g     ationMemberI  d;     
      }

            /**
           *      S    e    t   s t   h  e value of     th  e        delegatio  nMe    m      berId pro p       e  rty.
                     * 
     * @     p       ar    am v   alue
        *             allowed object i   s  
     *     {@l in        k String }
             *         
         *  /
       p ub   lic void setD   eleg   ationMembe     r   Id    (String value) {
             this.  delegati onMember    Id =   v al  ue;
             }
        
    /**
                 * G ets th       e    val      ue of the    d     elegationId p              rope   rty.
                  * 
     * @return  
                *             p       ossible obje    ct is
             *        {        @link Stri ng }
     *                  
         */
     pu   b    lic        Str    ing getD        elegationId() {
                                                         r  e   tur      n       del    egatio n   Id;
       }

      /*   *
      *   Set      s the      va     lue        of           the d   elegatio nId  p      rop        e r  ty.
                      * 
     *  @   param v                     a    lue
     *     all    owed obj  ect       i  s
     *          {@link            String }
        *       
            * /  
    pu   blic voi      d setD   elegat      ionId(String value) {
               this            .d         e     le       gation        Id = value;      
                  }                

      /  **
          * Ge            ts the value of     t     he memberI    d        pr  oper      ty  .
     * 
       * @retu   rn
           *       pos      sible ob  je ct is
       *          {         @link   Str ing }
          *     
       */
                     public S    trin  g           ge  tMemberId() {
               return m     emberId;
    }

            /**
             * Set        s the   value of t    he memb       e      r Id proper   ty.
             *        
            * @p    ar am     value      
          *            a  llow            ed object is
      *        {@link       Strin g    }
            *     
     */
    public voi  d s    etMemberId(   String va  lue ) {
           thi   s.memberId    =       val  u  e;
    }

          /*   *   
           * G   ets    t   he v   alue of the roleMem   berId  pr  operty.
        * 
                      * @return
           *               possibl  e object is
                     *     {@link Strin   g }
     *              
     */
       public   String        getRol      eMemb     er        Id( ) {            
           return rol      eMember   Id;  
    }

              /   **
     * Set          s the       va lue   of     th     e roleM   em    berId   property.
            * 
       * @p   aram  value
         *         allowe  d object is
        *         {@link String }
         *            
     */
     p   ub          li  c void    set         RoleMember Id(Stri   ng           value)  {
                        this.roleMemberId =     value;
    }

    /*   *
     * Gets the value             of t  he typeCod             e prope rty            .
       * 
       * @return
           *     p  o ssible objec    t is
        *         {@l   ink    Str in     g }
        *             
     */
               publi         c      S   tring ge   tTypeCo         de() {
               re      turn typeCode;
    }                  

    /     **
     * Sets the value o   f t he ty   peCod    e property.
           * 
                * @   param value       
            *     allowed object    is
              *     {@l  i   nk S      tring     }
     *             
     */
      pub      lic void     setTypeCod  e(String  v     alue) {
           this.  typeC  ode =    value;
    }

        /**
        * G    e       ts  the value o    f                 the a   ttri     butes      prope   r   t  y.
     * 
        * @re      turn
              *                possible object is
     *            { @l      ink S   trin  gMapEntryL  ist       Type      }
             *     
             */
    p          ublic String               MapEntr  yListType     getAttributes() {
        ret  u   rn  attri    butes;
    }

             /*  *
       * Sets th  e valu   e of the at  tribute       s p              rop    er   ty.
            * 
         * @pa     ram value       
              *           a   llo wed  o  bj       ec  t is
     *         {@l  ink Stri               n    gMap EntryListType }
              *              
     *          /
           pu    b              lic void     setAttributes(Str ingM     apEntryListType v      alue) {
        thi s.attri          b    utes = value;
       }

    /*    *
        * Ge      ts the value of the    a        ct    iveFromD   ate          property.
     * 
     *  @return
     *     poss ible object is
     *     {@link XMLGr   egori     anCa      lend    ar }
           *                      
        *  /
          public XMLG  regor     ianCalendar getAct   i veFromDate() {
            ret       urn ac  tiveFromDate;
       }       

    /**
       * Sets      th  e valu   e    of the acti           veF   romDate pr    operty.
     * 
        * @pa  r    am value
     *     allowed object is
     *     {@link    X  MLGregorianCalend ar   }
     *     
     */
    public void setActiveFromDate   (XML Gr  egorianCalendar   value)     {
            t   his.activeFromDa       te =     v alu        e;
             }
  
            /**
     * Ge  t  s th    e value of the activeToDate prope  r  ty.   
         * 
                   * @re           turn
     *     possible object is
     *           {@link XMLGregorianCalendar }
        *                 
     *      /
    p  ublic X  MLGregori  anCa     lendar getAct  i    veToD  ate() {
         return activeToDa   te;
       }

    /**
          * Sets the       value of the activeToDate pr operty .
     * 
     * @par  am           valu  e
     *        allowed object is
     *     {@link XML GregorianCalen    dar }
     *             
     */
    pub lic void setActiveToDat        e(X    MLGregorianCalendar value) {
                       t    his.active   ToDate = value;
        }

    /          **
     * Gets  the value of the v    ersionNumber proper     ty.
     * 
     * @return
     *               p   oss  i   ble ob ject is
          *       {@link Long }
     *     
     */
    public Long getVersionN  umbe   r() {
              return ver    sionNumber;
    }

    /**
         *    Sets the value of the versio    nNumber property.
     * 
     * @par   am value
     *     all   owed object is
      *                 {@link Long }
     *            
     */
    pu  blic void setVersionNumb    er(Long value) {
        this.      versionNumber = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a ref   erence to the live list,
     * not a snapshot. Therefore any modif            ication you   make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
         * 
     * <p>
     * For example, to add a new item, do as follows :
         * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
           * {@link Element }
     * 
     * 
     */
    public List<Element> getAny() {
        if (any == null) {
            any = new ArrayList<Element>();
        }
        return this.any;
    }

}
