
package  DBFullReportsClient;

impor  t java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import      javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
    import javax.xml.bind.annotation.XmlSchemaType;
       import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.Xml   V  alue;


/**
 *      <p>J  ava class for ActitvityCo     desEntr yType complex type.
 * 
       *    <p>The follow  ing sche    ma   fragmen   t s    pecifies the    expected c    ontent conta  in  ed   with   in this class.
 * 
    * <pre>
 * &    lt;complexT       ype       na    me="ActitvityCodesEntryType">
  *     &lt      ;complexContent>
 *      &lt;restriction base="{http://www.w3.org/2001/XML  Sc   he          ma}any     Typ    e">
 *       & lt;se   que          nce>
  *               &lt;eleme   nt na   me="   ActivityCode" mi          nOccur      s       ="0"        >
 *                &lt;c    omplexType>
 *             &lt;sim    pleCo   ntent>
 *                         &l  t   ;extensi  on bas         e  ="&lt;http://www.w3.  or  g/200       1/XMLSch              ema      >str ing"  >
 *                                  &lt;at     tGroup ref=  "{htt   p://gateway.dnb.com/   getPr     oduct     }Enti        ty   TypeandPriority"/>    
          *                      &lt  ;    /exte   ns   io   n>
 *             &l      t;/simpleContent>
          *               &lt;/complexTy  pe>
 *         &lt;/e     lement>
   *               &lt;element  name="Acti  vityCod  eDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0   "/>
   *       &lt;/sequenc e>
 *               &lt;/restriction>
    *          &lt;  /complexCo   ntent>
 * &lt;      /complexType>
 * </pre>
 * 
 * 
   *    /
@X     mlAcces     s  orType(XmlAccessType.FIE    LD)
@XmlTyp   e(name = "ActitvityCodesEntryType" , propO         rder =      {
    "activityCode",
    "activityCodeDescript  ion   "
})
pu    blic cl    ass Actitvit  yCodes    EntryT       ype {

    @XmlElement(name = "ActivityCode")
    pr    ote   cted Acti   tvityCodesEntryType.Activi       tyCode activityCo   de;
    @XmlElement(   name = "     Activit    yCodeDescripti   on"   )
    protecte     d    Strin    g activi    tyCodeD escription;

      /**
        * Gets   the valu            e of th   e acti vityC     od   e p   ropert    y       .  
       *    
     *     @re  turn
               *     possible ob  ject is
         *         {@link Act    itvity  Cod    e       sEntryType    .Act   iv     ityCode }
     *     
     */
    pu       bli c ActitvityCodesEntryTyp    e.Ac   ti   vi   tyCod e       g    etActi     vi   tyCode   () {
                 return activ ityCode;
       }     

    /**
     * Sets the value of t  he activityCod     e prop e   r     t   y.
     *         
         * @pa    ram            val   ue
        *       allowed obje  ct is
     *     {@lin    k Act  itvityCodesEntr      yType.ActivityCode      }
       *                                     
             */
    public void set         ActivityC  ode  (ActitvityCod     esEntryType.Act        ivity   C   ode va    lu   e       ) {      
           this.activityC         ode =      v        alue;
            }      

       /**
     * Gets         the val  ue of the ac  tivit yCodeDe scripti on p   roperty.
     *        
      * @retu rn
        *           p  oss    ible      object        is
            *     {@     li     nk String }
                        *        
     */
    public    S      tring getActivityCod     eDescr       iption()        {
        re  turn    act    i      vityCodeDescription;
       }

              /**
     * S     ets the value of  the   ac   tiv     ityC         odeDes   cript ion property.
       *    
       *    @param val ue
            *     allow            ed object   is
     *     {@l     ink Str  ing }
     *     
             */
       public  void setA      ctivity    CodeDescription(St    ring value)     {
                      this.   activityCod eDe scr   iptio  n    =       value;
    }


    /  **
       * <p>Java clas  s for ano      ny  mous    complex            type.
       *  
       * <p>T     he following    schema  fragment sp    ecifies the          expected content contained wi  thin this class. 
           * 
     * <pre >
     * &lt;comple  xT     ype>
     *               &lt;simple     Conte      n t>
     *         &l   t;ext    ension base=         "&l       t;http://www.            w3.o r    g/2001/XMLSch     ema>stri    ng">
     *              &lt  ;              a   ttGroup ref="{http  : //gatew    ay.dnb.c     om/g           etProduct}En ti       ty                   Typ eand   Priority   "/>    
     *        &lt;/extension>
             *         &lt;/  s  imple   Content>
          * &lt; /complex    Type>
     * <   /pre>
           * 
             *       
     */
          @X         ml  Acce   ssorType(XmlAc cessType.F        IE   LD  )
               @Xm   lTy pe(name =  "", pro  p     O    rder          = {
                                 "value"
       })
         publi         c static cl     ass A               ct    iv   ityC      ode {   

                @XmlV       al  u  e
         protected String    value;
        @X      mlAttr   ibu  te
               @X      mlSc h ema    Typ   e(name = "an   ySi     m    pleT         y          pe")
                          protected      St     rin                g type;
           @               XmlAttri  bute
          pr                    ot           ected BigIntege  r    pr  iori ty;             
            @XmlAtt         ri b    ut   e
        protec              ted Bi  gInteger re  f  ere nceC   o    d e         ;
                            @X  m     l  At    tribute
          prot        ected String       ref           eren   c                 eCode  T   able  ;

           /*     *
                          * Ge      ts the value of            th         e        v      al  ue property.
             * 
                  * @return
             *     p  o       s sible   object is    
              *     {@li    nk String }
             *           
         */
                    public St ring getVa  lu     e() {
                                       r    eturn          va   lu  e;
        }

        /**        
                   * Set      s         the          v         alue               of the val     u e  prope  rty.
                  * 
                  *            @pa   r     am    value
           *           a   l  lowed        object            is
                       *     {    @l   ink String       }
                      *     
                   *   / 
              public vo    id        setValue(String               v         a     lue) {
                            this            .value = va lue;
          }
       
              /**  
                   *   Get  s         the       value      of the type property.
                    * 
               * @       retu rn
         *        po   ssib         le ob         ject is
                    *                 {   @lin             k String }
                   *         
         */        
                    p  u         blic S    tring get     T      yp  e() {     
                      return type;
                 }             

        /**
           *      Se   ts the value o      f the typ    e    propert    y.
            *   
         * @par  am value
               *            allow  ed         objec    t is
                     *                        {   @link String }
                      *     
         *     /
        pu b          l    ic             void   set   Type(Stri      ng   value) {
                        this.t        ype = va  l   ue;
                                 }

             /**
         *     G  ets the valu e o f the priori     ty    property.          
           *      
            *  @r  e   turn
                    *             possible o         bje   ct is
                 *           {@link B i         gInteger }   
          *          
         */
        public Bi    gIntege         r       getPrio  r     ity() {   
            return      priority;
                }

        /**
             * Sets the value of the priori   ty propert y      .
                   *  
           *   @     param v  alue
             *     allowed object is
           *     {@link BigInteger    }
                *       
          */
          p      ublic void setPriority  (BigInteger value) {  
              this.priority     = value;    
                  }

        /**          
                   * Gets the value of the re    ferenceCode property.
             * 
            * @      ret    urn
         *     poss           i  ble object is
         *     {@link BigInteger }      
         *     
         */
        public BigI   nteg   er getRe ferenceCode() {
            return refe      renceC     ode;
        }

        /**
         * Sets the value of the referenc    eCode propert     y.
         * 
         *    @param v   alue
         *     allowed object is
           *             {@link BigInteger }
         *         
         */
           public  vo  id setReferenceCode(BigInteger va  lu      e) {
               this.referenceCode = value;
        }
     
           /*  *
         * Ge     ts the value of the ref     erenceCodeTable property.
         * 
           *   @return
         *     possible object is
         *     {@link String }
         *     
         */
         public String getReferenceCode     Table() {
            retur    n referenceCodeTable;
        }

        /**
           * Sets the value of the         referenceCodeTable property.
               * 
               * @param value
         *     allowed object is
            *     {@link String }
         *     
         */
        public void setReferenceCodeTable(String value) {
            this.referenceCodeTable = value;
        }

    }

}
