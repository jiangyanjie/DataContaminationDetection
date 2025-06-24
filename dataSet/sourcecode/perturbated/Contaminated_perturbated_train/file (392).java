
package      com.transportation.SIRI_IL.SOAP;

i   mport java.util.ArrayList;
i    mport java.util.      List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import     javax.xml.bind.annotat ion.XmlElement;
import javax.xml.bind.annotation.XmlSc      hemaType;
import javax.xml.bind.annotation.XmlTyp    e;
import javax.xml.bind.annotation.adapters.CollapsedStri ngAdapter;
import javax.xml.bind.annotation.adapters  .Xm   lJavaTypeAdap   t  er;


/**
 * Typ  e f   or list of situa     tions.
 * 
 * <p>Java class for ActionDataStructure complex type.
 * 
 * <p>The following schema fragment specifie  s the expected content   contained within   this class.
 * 
 * <pre>
 *       &    lt;com          plexTy  pe name="      ActionDataStructure">     
 *   &lt;complexContent>
 *     &lt;  restriction ba   se="{   http://www.w     3.org/20   0   1/XMLSchem   a}anyType">
                      *       &lt       ;sequence>
 *            &lt;elem     ent name="N         ame" type="{http://www.w3.org   /2001/XMLSchema}NMTOKE   N"/>
 *         &lt;element name="Type" type="{http://w   w  w.w3.org/   2001/XMLS   chema}NMTOKEN"/>
 *         &lt;ele        ment name ="Value" type="{http://www.w3.org     /2001/XMLSchema}anyType" maxOcc   urs   ="un    bounded"/>
              *                       &l t;element name="Prompt     "   typ    e="{http:   //ww  w.siri.org.uk/siri}Natu ralLanguageSt    ringStructure" minOccurs="0"/>
 *       &lt;/sequence>
  *         &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/c     omplexT   ype>
 * </    p re>
 * 
 *      
 *  /
@XmlAccessorTy    pe(XmlAccess      Type.FIELD)
@XmlType(name = "ActionDataS       tructure", prop       Order = {
    "name",
       "     type",
    "value",
    "pro mpt"
})    
pub   lic class A ctionDataStr  uctur   e     {

    @XmlEle   ment(name         = "Name", required = true)
        @Xm    lJavaT    ypeAdapter(Co      ll    apsedStringA      da  pter.  class      )
    @XmlSchemaT    ype(name = "NMTOKEN")       
    prot     ected String name;
    @XmlElement(name = "Type", required = tru  e)
    @XmlJava   Typ  eA   dapter(Colla    psedStringAdapter.c   lass)
         @Xml   SchemaType(name   = "N MTOKEN     ")
    prot    e    cte           d Stri  ng type       ;
    @Xm   lEl    ement (name        = "Value" , requ      i   red = true)
        protected          List<Object> va  lue;
                  @Xml  El   emen t    (       name   =    "Pr   o     mpt")
             protec       ted Natura   lLanguageStri           ngStruct      ure   promp    t;

      /**
     *     Gets the value of      the n    am     e pr   operty.
                     *      
     * @return   
     *     possible object i s
           *          {@link String }
      *         
            */
    public   String get   Nam e  () {
                          re  turn name;
    }   
        
    /   **
              * Sets   the   value o f  the       na  me pro      pert  y.
            *       
     * @       param        value
        *     a       ll    ow ed  objec t   is
     *         {@link Str     ing          }
           *     
        */
             pub  lic void setNam            e(S         trin  g v   a  lue) {
        this.               name = v               a  lue;   
    }

    /**
     * Gets     th    e valu   e      of the  typ  e    prop    erty.
                   * 
             * @retur    n
       *            poss    ible object is
          *     {@  l  ink         Str   ing }
             *     
     */
            publi c String getType     () {      
         re       t  u   rn type;
    }

    /*   *
             * Sets the       valu       e of t    h e type pr    opert      y.
           *  
         * @par                 am value
                *     allowe  d obj    ec  t is
     *      {@li     nk Stri     ng }
     *          
           */
          p  ublic void setType(String valu  e)  {
        th        is.type = value;
    }

    /**
            * Gets t  he   va lu  e of the valu   e    pro       p er  t              y.
       * 
     * <p>
                 *    Th           is access      or method retur     ns a reference to the live list,
     * not a snaps  hot. Ther          ef   ore any      modifi cation        you      mak   e to the
        * retu         r   ned    list will be   presen    t insi       de the JA   XB    objec   t     .
     * This     is    why there is  not   a <CODE>set</COD      E> meth     o d   for the value property.
     * 
     *   <p>
       *    For example, to ad       d a new  item,  do     as follo  ws:
         *        <pre>
       *    getValu   e().add(n     ewItem);
     * </pre>
             * 
         * 
        * <p>
     * O bjects of t  he     following type(s)  are allowed in t        he l   ist    
     * {@li         nk Object }
     *         
     * 
             */
       public List<Obje      ct> ge tVa   lue() {
        if (va lue ==          null) {
            value = new ArrayList<Object>(     );
          }
              return    thi s.value;
    }

    /**
     * Gets the value of the    prompt     property.
     * 
     * @return
     *        possible objec   t is
     *     {       @link Natu   ralL      anguageS   tringStructu   re }
     *        
     */
    publi  c NaturalLanguageStringStructure getPrompt()   {
           return prompt;
    }

       /**
      * Sets the value of the    prompt      property.
     * 
     * @param value
       *     allowed object is
        *     {@link NaturalLanguageStringStructure }
     *     
     */
    public void setPrompt(NaturalLanguageStringStructure value) {
        this.prompt = value;
    }

}
