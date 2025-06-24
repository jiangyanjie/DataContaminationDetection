
package    org.kuali.rice.kew.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
imp      ort javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import   org.w3c.dom.Element;


/**
   * <p>Java class for ActionTa    kenType      complex type   .   
 *       
     * <p>The following schema fragment speci   fies the expected conte         n t contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Ac     tionTake nT     ype">
 *         &lt;complexContent>
 *     &lt;r   es    triction       base=      "{http://www.w3.o   rg/2001/XMLSchema}anyTyp e">
 *                  &l            t;sequence>
 *         &lt;elemen    t name="id" type="{http://www.w3.org/20   01/XMLS   chema   }st ring"/>
   *              &lt;element name="documentId" ty pe=" {http://   www.w3.org   /2001/XMLSchema   }string    "/>
 *             &lt;element name="principalId" type="{http://www.w3.org/2001/XMLSchema}stri n     g   "/>
 *         &lt;   element n ame="   delegatorPrincipalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;element name="delegatorGroupId" type="{http://www.w3.org/2001/XMLS chema}stri  ng"       m inOccurs="0"/>
 *         &lt;element name="act  ionTakenCode" t  yp        e="{http://www.w3.org/2001/XMLSc hema}s     tring"/>
 *           &lt;el     ement name="actionDate" type="{http://www.w3.org/2001/XMLSchema}d     a  teTi   me"/>
 *         &lt;ele       ment name="annot    ation" type="{h  ttp://www.w3.org/2001/XMLS    chema}string" mi  nOccurs="0"/>
 *              &lt;element name="current" ty         pe="{http://www.w3.org/2001 /XMLSchema}boolean"/>
 *           &lt;any p    rocessContents='skip' namespace='  ##other' m        axOccurs="un      bounded" mi  nOccurs="0"/>
 *                       &lt;/sequence>
     *          &lt    ;/restr  iction>
    *   &l    t;/compl     exContent>
 * &l     t    ;/complexType>
 * </pre>
 * 
 * 
 */
@Xml        AccessorType(Xml      AccessTyp  e.FIELD)
@ XmlT  ype(name = "ActionTakenType", propOrder    = {
    "id",
    "   documen  tId"   ,
         "principalId",
        "delegatorP   rincipalId",
    "dele    gatorGroupI   d",
    "actio   nTakenC  ode      ",
       "   actionDate",    
    "an   notation",
    "current",
    "any"
})
 public c  lass ActionTakenType {

    @XmlElement(required = true)
    protec      ted S    t       r   ing      id;
    @    XmlElement(required = true)
    protected String docum      entId;
    @XmlElement(required = true)
    prote     c   ted     String pri  ncipalId;
    protected   Strin     g del   egat         or Principal     Id;   
    protec   ted S     tring     delegatorGroup   Id;
              @XmlEleme        nt(req  uir   ed = true   )
    protect ed String   action T      akenCode;
    @XmlEle    ment(requi    red = tru     e)
        @Xm           lSche          maType(name     =      "d  ateTime")   
      protected XML   Gr       egorianCalen    dar act              ion   Date;  
      protecte  d String annota tion;
         pr       otected bo                ol     ean c  u    rrent;
    @Xml    AnyElem   ent
      pr     ot   ecte        d L    ist<E  l     ement   >    a   ny;
    
        /*     *
              *    Gets            the v     al   ue of the id propert              y.
      * 
     *         @      ret  urn    
               *       possible obje  ct  is
         *        {@l    ink String }
                    *          
     */
                          pub      li       c Stri    ng         getId()                         {
           return i      d;
    }

    /**
          * Sets th   e va                  lue of th  e id property.
              *        
             * @pa    ram val     ue
             *       allo  wed obj    ect is
     *     {@li  nk S   tring      }
      *              
           */
    public v   oid setId(String valu           e) {  
                  t             his.id = value;
    }

       /**
          * Gets th    e value   of         the             d            ocumentId p   ropert    y    .
     *         
               * @return
     *                        possible objec   t is   
       *     {@li    nk          String }
     *     
                    */
        pub li  c Strin   g      getDocumentId()  {
              retur  n       doc u   mentId;
    }

         /**
           * Se  ts the va          l  u    e of th   e documentId        pro     perty.     
     * 
         * @par    am value
     *     allowed       o   bject is
                            *               {@lin        k Str       ing }
                    *       
         */
          publi   c v          oi     d set     DocumentId   (Stri     ng v alue) {  
           this.docu  mentId      = value;
    }

        /    **   
              *          Gets t      he va    lue    of th               e     principa       l     Id prop      erty.
                *     
     * @return
        *           po   ss  i      ble ob   j   e c    t is
                          *     {@l        ink String              }
     *           
        */
    p    ubl               ic String getPrin  cipalId() {
          return principa  lId;
    }

          /**
       * S    ets    th   e value o      f  the principal  Id prope     r       t     y.
                      *     
     *  @param     value
     *        a    llo  wed obje          ct    is
        *         {@li  nk String }
              *     
        *     /
    pub     lic  void set Principal    Id(S     t   ring         value) {
                 t   hi     s.principalId = v     a    lue;
           }

     /**
            * Ge     ts the value           of the   delegat  or       Principal        I   d prope            rt  y.  
     * 
       * @   return
            *           poss   ible obje           ct is
     *     {@link Str     ing           }
      *     
     */
               publi          c        Str    ing getDelegatorPri  nc      ipalId()   {    
          return d     elegatorP     rincipalId;
    }

    /**
              * Set     s the    value of the     delegat   o       r   P    rin              cipalId pro pe   rty.
           * 
         * @pa   ram value
              *     all ow           ed object is
      *       {@link Str     in   g }
           *            
       */
    pu    bli  c voi     d set   Delegat     orPri      nc          ipal       Id(S tring   valu   e) {
         this.de   l    egato     rPrinci   palId = valu       e;
    }

      /**
        *   Gets the va  lue of  the de    legatorGro     upId property             .
                     * 
           *    @return
                *     pos   sible obje  ct   is  
     *      {@l  ink St    ring  }
         *               
        *   /
    public Stri         ng       get   Del    egatorGroupId()     {
          return delega  to  rGroupI           d   ;
     }

     /**
                       *  Set    s the va       lue    o f th e delegatorGro upId property  .
                     * 
        * @param value
         *     al lowe  d object i  s
     *       {@link Str                           ing }
     *     
              *  /
     pu         blic vo       id setDelega     torGroupId  (Stri ng value) {
                                      thi  s.delegatorGroup             Id =        val     u   e;
    }

    /**
        *  Ge t   s th  e val     ue           of the      a c             ti    o  n    Tak  e   nC ode proper           ty.
              * 
            * @retur                 n
         *     possible obje ct   is
     *                {@li    nk   Str  ing }
        *           
        */
    public String g etActionTakenCode() {
        return actionTakenCod   e               ;
    }

    /**
              * Sets t    he va lue of the a    ctionTakenCode    property.
      * 
       * @p    a ram value
          *       allowed o    bject            is
          *      {@l   ink                          String  }
          *          
     */
    publ    ic        void      set          ActionTakenCode(S   trin     g    value      )             {
        t  his.actionTakenCode =     value;    
    }
        
     /**
         * Ge t   s the value of the  actionDate  propert y.
          * 
          *    @return
          *            p   o       ssibl     e object  i  s
     *         {@l      ink XMLGregorianCalendar }
     *          
      */
         p ublic XMLG       regorianCalendar get       ActionDate() {
              return     actionDate;
        }

      /*  *
          * Sets        th    e    valu   e of  the ac  tionDate property.
       * 
     * @param val  ue
         *     allowed  object is
     *     { @link XMLGr     egoria           nCalendar }
        *     
     */
    p  ublic voi        d setActionDate(      XMLGreg  o rianCalendar v     alue)    {
        this.actionDate = value;
    }

    /**
        * Gets the va     l   ue of the annotation property.
     * 
         * @return
     *        possible object is
     *      {@lin     k String }
     *                          
           */
     public   St   ring getAn   no    tation() {
                 return annotat    ion;
         }

      /**
     *     Set s the value of th   e annotation property.
     *  
     *     @param value
         *     allowed object is
     *              {@link   String }
     *         
     */
    public void setAnnotation(String value) {
        this.annot    ation = value;
    }

    / **
     * Gets the value of the current property.
     * 
     */
     p   ub     lic boolean   isCur rent( ) {
        return current;
    }

    /**
      * S  ets t   he value of the cur   rent property.
     * 
     */
    public void setCurrent(boolean value       ) {
          this.curr     ent = value;
     }
       
    /**
     * Gets the  value of the any property.
          * 
     * <p>     
     * This accessor method returns a reference to the l  ive list,
     * not a snapshot. T         herefore any modification you make to the
     * returned list will be present i  nside the JAXB object.
     * This is why there is no     t a <CODE>set</CODE> method for the any property.
        * 
     * <p>
            * For examp  le, to add   a new item, do as follows:
     * <pre >
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the follo  wing type(s) are allowed  in the list
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
