
package   org.kuali.rice.kew.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import  javax.xml.bind.annotation.XmlElement;
import     javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;   
import org.w3c.dom.Element;


/**
 * <p>Java    class  fo r ActionRequest   Type     complex  type.
 * 
 * <p>The          fo      llowing s chema fragment specifies the exp   ected content contained within this c   lass.
 *        
       * <pre>
 * &lt;comp    lexType nam          e="ActionRequestType">
 *   &lt;complexConten t>
 *       &lt;restric  tion base="{ht   tp://    www.      w3   .or      g/2001/XMLSchema}any       Typ    e">
 *       &lt;sequence>         
 *                   &lt;el      e      ment name="id" type="   {http://    www.w3.org/200     1/XMLSch  ema}string  "/> 
 *         &l   t;   element name="actionRequestedCode" type   ="{http:/ /w   ww.w3.org/200  1/XMLSchema}string"  />
 *         &lt;element name="stat  usCode" type="{http://www.    w3.org    /2001/XMLSc   hema}string"/>
     *           &lt;element name="curren    t" type="{htt  p:  //www.w3.org/2001/XMLSche            ma}boolean"/         >
 *         &lt    ;element name="dateC reate d" t       ype="{http://ww   w.w3.o  rg/2001/XMLS     ch   ema}d   ateTi    me"/>
 *         &lt;element name="responsibilityId    " type="{http://www.w3.or     g/2001/XMLSchema}string"/>
 *         &lt;element name="         documentId" ty   pe="{http://www.w3.org/2     001/XMLSchema}string"/         >
 *                  &l  t;element name="pr       iority" type="{http://ww   w    .w3.org/20 01/XMLSchema}i       nt"/>
  *         &lt      ;element name="routeLevel" type=    "{http://www.w3 .org/2001/XML   Schem   a}int"/>
 *               &lt;element name="annotation  " type="{http://ww     w.w3.org/20  01/XMLSc  hema}string   " minOccurs="0"/>
   *           & lt;ele  ment name="recipientTypeCode" type="{htt        p:/    /w ww.w3.org/ 2001/XMLSche ma}      string"/>
 *         &lt;el     ement name="pr   in    cipalId" type="{http://w ww.w         3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}stri       ng" minOccurs="0"/>  
 *             &lt;element name="request    PolicyCode" type="{http://www.w3.org/2001/XMLSc  hema}string" minOccurs="0"/    >
      *         &lt;el   ement name ="     responsibilityDescription" type="{ht tp://www.w3.o      rg/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt     ;elem   e    nt na      me="forceAction" type="{http://www.w3.org/2001/XML Sc   hema}boolean"/>
 *           &lt;element name="delega  tionTypeCode" type="{http://www.w3.org/2001/XML   Schema}s   tring" minOccurs="0"/>
 *            &l   t;element    name="rol    eName"     type="{ht     tp:    / /www.w3.org/2001/XMLSc hema}string" min Occurs="0"/>
 *         &lt;ele  ment name="qualifiedRoleName"   type="{http://www.  w3.org/2001/XMLSchema}s    tring"    mi   nO    ccurs="0"/>
  *         &lt;element name="qualifiedRoleNameLabel" t    ype="{http://www.w3.org/2001/   XMLSc   hema}string" minOc curs="0"/>
 *             &l  t;      element name="routeNodeInstanceId" type="{http://ww   w.w3.org/2001/XMLSchema}string" minOccur                 s="0"/>
 *             &lt;element name="nodeName" type="{http://  ww w.w3  .org/2001/  XMLSch      ema}stri          ng" minOccurs ="0"/>
 *                &lt;element      name="req uestLabel" type="{http://www.    w3.     org/2001/XMLSchema}string" minOccurs   ="0"/>
 *                  &lt;ele   ment name="parentActionRequestId" t      ype="{http://www  .w3.org/2001/   XMLS  chema}strin  g"       minOccurs=           "0"/>
 *                 &  lt;element ref=   "{http://rice.kuali.org/kew/v2_0}actionTaken  "     minOc  curs="0"/>
 *           &lt;element name="childRequests"      minOc   cur    s=   "0">             
 *               &lt;c   omplexType>
  *                      &lt;complex        Con  ten    t>
 *                 &lt;r    estricti          on base="{htt      p://www.w3.org/2001/XML   Schema}anyType">
 *                                &lt;  sequence>  
 *                       &lt;element name="chi  ldRequ      est" ty   pe    ="{http://     rice.kuali.org/kew/v2_0}Action  RequestType    " maxOcc     urs="unbounded" mi   nOccurs=     "   0"/> 
 *                 &lt;/se  quence>
 *                   &lt;/restric tion>
  *               &lt;/    complexContent>
 *           &lt;     /comp    lexType>
 *           &lt;/element>  
 *         &    lt;any  processContents='skip'    n    amesp  ace=       '##other' maxOc    c  ur     s=   "     unbounded" minOccurs="0"/>
 *             &lt;/sequence>
       *     &lt;/restriction>
       *   &     l      t;/complexCo  ntent>
 * &lt;/compl   exType>
  * <      /pre>
 * 
    * 
 */
@XmlA   ccess        orType   (XmlAccessType  .FI  ELD)
@XmlType(  name = "ActionRequestType", propOrder = {
    "id",
    "actionRequestedCode"  ,
    "statusCod   e ",
    "current",
    "dateCreated", 
       "res   p     onsibil    ityI  d",
    "do   cum    entId",
    "  priority",
    "routeLevel    ",        
    "annotatio     n",
     "    reci   pientTypeCo     de",
    "principalId"     ,
    "groupId",   
      "r   equestPolicyCode",
           "        responsibili             tyDescription",
    "forceAction",
        "delegati       onT     ypeCode  ",
    "roleName",
    "qualifiedRoleName",
      "      qualifiedRoleNameLabe   l",
     "routeNodeInstanceId" ,
    "nodeName  ",
     "requestLabel",  
           "parentActi    onR equ     estId"  ,       
    "     actionTaken"   ,
    "childRequests",
     "any"
})
public class Actio  nR equestType {
       
    @XmlElement(required = true)
         protect   ed String    id;
    @XmlElement(required       =               true)
    protected String action       Re     questedCode   ;     
    @Xm    lElement(r              equired = t rue)
    prot      ected String stat   usCode;
    protected   bo  olean curre     n  t;
     @XmlEleme       nt( re quire   d   = true)
      @Xm    lSchemaType(name = "dateTime")
    protect   ed X        MLGregorianCalendar da   teCreated;
       @XmlElement(requ         ire   d = true)
    protec    ted S     tr  ing re    sponsibility      Id;
        @X    mlElement(     r   equire  d = true)
           pro        tected String docum entI d;
    prote   ct     ed int priority; 
    prote       cted int ro      u  teLevel;
           protected     String ann       otatio    n;
     @XmlEl          ement(required =                true)
    prote     cted     Stri     ng recipientTyp   eCode;
    pr  o  tect     ed String princi         pal   Id;
    prot  ec    ted String groupId;
    protected String       reques    tPolicyCode;
    prote   c     ted String res    ponsi      bility            Description;
    pro  tected  boo    lean fo                  rceAc  ti      on;  
      protected St  ring delegationTypeCode;
      protected String r              oleName     ;
      protected String qualifiedRoleName;
    pr   otecte   d Str  ing qualifiedRoleNa   meLabel;
              protected Str   ing ro ute           NodeI   nstanceI    d;
                        protect  e  d Stri       n    g n     odeNam       e             ;
     protecte     d     S   tring  requestLa   be    l  ;
    prot    ected St    ring       pa        r     en  tActionReque           stI     d;
    pro     tec   t ed     ActionTa  kenType a   ctionTak     en;
    protec  t ed Ac     t                     io      nReq        uestType.Chil   dReque  sts child    Re  quests;   
    @XmlAnyElem  ent
        prote          c   t    ed List   <Ele      m       ent> an  y;

      /** 
     *   G ets th     e   value    of the id pro       p                e   rt  y.     
          * 
     *            @re    tur    n  
     *                          possible object is
     *            {@li    nk String }
         *      
      */ 
      pu                 blic Strin   g getI       d    (   )  {
        retu    r     n   id;
        }

            /**
     *  Sets t         he val   ue of      the id             property.
                           * 
     * @     para      m          val  u  e
     *            a      llo         wed       object is
     *     {     @link S      tring }
     *              
          */
    public void setId(String va      lu    e) {
         this.id = v         al       ue;
         }

           /**
     * Get        s the value      of    the        actionReques te dCode property.
          *       
     * @ret ur n
            *     possib l        e ob              ject is
     *     {@lin    k String           }
      *             
             */
         public       Strin  g g  etAction       RequestedCode() {
                       return action    Request edC   ode;
               }

        /**
          * Sets      t  h   e va lue of  t   he   actionReq     uestedCode property.    
     * 
      *  @param        valu      e  
                *     all  owed object            is
                   *           {@link Str        ing }
              *              
     */
                 public v  o  id s        etActionRe  questedCode(      String      value)     {   
                       this.acti    onRequ  estedCode =           val  ue;
        }

    /           **
         * Gets the va  lue o  f the s        tatusCod  e  property.
      * 
         * @return          
         *       p     o      ss  i  ble    object i   s
     *     {@ lin          k String      }
     *     
              */
    public String get    S     tatusCo     d   e() {
                 return status    Code;
    }
        
                       /      **
     *       Set     s th       e va  l    ue       of th       e status     Code prop      e   rt     y.
     *   
     * @par   a  m   v  alue
                  *     allowed    ob  ject is
     *               {@li      n k Stri  ng }
         *            
                 */  
                 p   ublic void setSt  atus  Co   d           e(String va  lue) {
          this.statusCode = value;
           }

    /*  *
                   * Gets the          value    of       t  he current property.
     * 
     */
          pu    blic boolean isCurrent()    {
        return   cur     rent; 
      }

    /**
        * S  e      ts t   he value              of the current            property       .
           * 
            */
           pu           blic void   setC            u rrent( boo l   ean value) {
                     thi         s     .current = value;
    }

         /**
            *      Gets th   e val            ue of the dateCr    e     at    ed  propert y     .
       * 
     *       @re    tur  n
              *           po  ssib  le        obje        ct is
                            *       {@    link XM LGrego      ri   anCalendar   }
     *          
     */    
    public XMLG      regorianCalendar g     etD   ateCreated() {
               retu      rn dateC      rea      ted;      
             }

    /*  *
     * Sets the    value of the dat   eC         reate  d property          .
     * 
     * @pa   ram value
                *     allo   wed  object is
               *        {@li  nk XM  LGregorianCalendar               }
      *            
     */
            p ublic voi              d s  etDat eCre   ated(XM   LGregorianCa  l          end ar   value) {
        this.dateCreated = v  alue;
     }

              /**
            *   Gets         the valu  e of the respon    s     i   bilityI       d    propert y.
     * 
          * @retur n
       *     possibl  e ob   ject is
           *     {@link Str         i    ng }     
     *               
        *    /
          publi  c String getR       es    ponsi   b    i      lityI   d() {
              return      res po     nsibilit        yId;    
    }

    /**
     * Sets  the va lu     e of         the res       p   onsibilityId    property. 
          *        
      *    @   param    v    alue
       *             allowed objec   t i s        
        *        {@link String }
     *      
                */
    public void setRespo                 n    si     bility   Id(Strin    g valu  e) {
                     t      his.res   po       nsi   bil   it    yId = value;
     }

               /**
     * Gets the   value of t  h e doc  u m entI        d property.
        *  
     *    @      return
       *                pos    sible object is
     *             {     @link Stri n          g       }
       *         
     */
             public String         getDocumentId() {
           ret  urn docu   mentId;
    }

      /**
     *   S   et          s the val      u     e     of   th    e doc  um e           ntId pro   per      ty.
                    * 
     * @param   val  ue
     *     allowed ob         ject i             s
     *     {@link String }
              *         
             */
     public   void     setDocumentId(Strin    g va   lue) {
             this.document    Id                     = va lue;
         }
    
        /* *
     * Gets the v                       alue o          f the p         riority prop    e                rty.
          *   
        */
            public    in t g   etPrio  rity() {
        r  etu   rn pr    ior ity;
    }

    /**
        * Sets the    value of t               he pr  iority       prop ert  y.
     *    
     */
    p       u   blic void se    tPriority(int    value) {
           this.p  riority = value;
    }

    /**
     *    Ge  ts    the   valu  e of the    rout  eLevel      pro         perty    .
     * 
     */
         p      ublic int      getR              o   uteLe     vel() {
                   r e   turn routeL           evel;
      }

    /         **
     * S      e    ts        the   value o f the      rou        teLevel propert          y.   
      * 
     *     /
    pu      blic      void se  tRouteLevel(int va      lue) {
           th      is.routeLevel    =        value     ;
    }

    /**
       *   Gets     t    h e   value of the annotati  on proper  ty.
         * 
              *   @ret     urn 
                           *       possi        ble        object is
     *     { @link Strin   g }
     *          
                */
            pu       blic     String getAnnotatio  n() {
                         return ann           otation;
        }

            /**
             * S     ets the value of the           annot   a    tion p r      ope rty.
     *     
      *          @param va lue
             *                       allowed ob      jec    t   is
         *                  {@li   nk St   rin g } 
     *          
            */
            public void setA  nnotation  (  Strin     g    v    alu   e)      {
          this.annotati   on      =     value;
    }

               /**
     * Get s t     he value o     f the reci  pientType  C       o   de property         .
          * 
         * @return
         *                   possi        ble o   bj           ect i    s
       *           {    @lin  k    Strin g }
                 *                  
            */
    pub   lic Str   i   ng getRe  c   i    pientTyp     e Co  de()   {
            r           eturn recipie   nt      TypeCode;
           }

     /**
     * Sets the    value        of   t   he recipientT     yp  eCode prope  rt         y .
                  * 
                                    *   @param value
      *     allowed       o           b    ject is    
     *     { @lin k String }
       *     
                             */
    public vo  id setRecipientTy        pe     Code(Strin g     val       ue) {
           thi   s.   reci   p     ientTypeCode = value;
         }

          /*   *
          *          Ge  ts        th   e value of the     p  r             incip      alId   property .   
             * 
     * @r  eturn
              *     possib   le      obje  ct  is    
     *        {@link Str   ing }
            *         
     */
    public String ge tPr   i  ncipalI   d(         )   {
                        return p    ri  ncipa     l  Id     ;
    }
             
        /**
             * Sets the va            lue of the pri    nc    ip       al   Id property .
         *    
     * @  p    aram val         ue
       *         allowed    obj ect i        s
                  *         {@link String }
     *        
      */     
                     publi     c    void se  t        Pr     incipal          Id(String va       lue) {       
              thi   s.     principalId = value;
      }          

    /**
        *       Gets t     h e value of t           he groupId pro   perty.
                  *         
            * @return
       *       po       ss    ib   le        object is
      *         {                       @l     i      nk Stri  ng    }
               *       
        *  /
      pu  bl ic Strin    g getGroupI              d  () {
        return grou    p  Id;
    }

    /**
      *         Se         ts   the v a    lue of the groupId     property.
     * 
     * @para m val          ue
         *            a    llowe  d  object i   s
            *         {@link     St  ri   ng }
     *        
             * /  
    pu  blic voi    d        s    etGroupI d(           String val  ue) {
            this.g     ro     upId = value;
    }   
 
    /  **
                 * Ge     t   s the          value of          the requ       estPol  icyC    ode  prope rty.
         *     
     * @return
                   *     p  ossible obje    ct is
     *          {@lin  k String }    
     *     
                                                */
        p   ublic     String   getReque         st                 P           olicyC       ode() {
          return request   P       olic  yCo     de;
    }

    /  **   
            * Set    s the value   of th       e req   uestPolicyCod e property.
         * 
     * @pa      ram value
               *     all    owed object is
     *     {@link Stri   ng }
     *     
     */
    public vo   id setReques     tPolicyCode(String  va       lue)   {
            th   is.requestPolicy    Code = value;
    }

        /**
         * Gets the  v    alue o      f th   e respons       i bilityDes  cription p  roperty.
     * 
              *    @retur  n
     *     possib  le object    is
              *     {@link String }
             *                      
     */
    p         ublic St  ring getRe     sp     onsibility    Desc     ripti  on() {
        retu    r n responsi  bili  tyDes    crip   tio    n    ;
    }

        /     **      
          * S    et   s th  e    va  lue     of               the respon   sibilityDes    cri   ption pr      op erty.
             * 
               * @ pa   r   am va    lu           e
          *                a                llowed obje                              ct i  s
            *     {@link String }
                        *               
        */
    pub  lic void s    et   ResponsibilityD      esc          ription(Stri     ng valu     e) {
           thi   s.r  espon     sibilit    yDescription =          value;
             }

       /**   
     *        Gets t  he value of t      he    forceA  ctio  n p            roper      ty .
                 * 
     */
          pub            lic boole      an isFo rceAct    ion() {
          ret   urn for     ceActi       on;  
           }  

    /*   *
       * Sets             t      he           value  of the forceAction pr       operty.   
         * 
         */
      publi  c     void   se       tF   orceAction(boolean valu          e) {
        this.forceA    cti   o  n = value;      
    }
          
              /**
           * Gets the value of            th   e delegati   onTypeCode property.
     * 
     * @retu  r      n
     *                       po   ssibl  e object is
            *        {@l  ink St     rin  g }
                     *          
         */
    public S      trin         g    getDelegationType      C    ode(  ) {
                     return       delegatio     nTypeCod e;
    }    

       /**
                * Se   ts t he value of the del              e  g                  ationTypeCode  pro  perty.
         *     
     * @par   am va lue
       *     all     owed object is   
     *      {@link String     }
     *      
     *   /
    public void s et  Dele    gati             on    T    ypeCode(Str    in  g value) {
                 this    .delegat   i  onTypeC       ode =   valu       e;
            }
  
    /**
           * Gets the v alue              of the roleN        a    me prope    r      ty   .                      
        *  
     * @re     turn
      *         possible object is 
         *          {@link   St  r ing }
          *         
           *      /        
    p      ublic String getRo   leName(    ) {
        r    eturn ro   leName; 
            }

         /        **
            *    Se      ts t               he v   alue of   the roleNam     e p   ropert         y.   
          *          
           *           @param value
          *     allow    ed o   bject i    s
     *                {  @l  ink St   ring }
        *     
     */
    p     ublic     v  oid se       tRoleName(Str       ing value) {
           this     .rol    e  Na me = va lue;
    }

           /**
          * Gets the v  alue     of the qua lif    iedRoleNam     e property.
         * 
     * @return
     *            possible   o    bject i   s
      *     {@l      in  k String          }
      *                      
           */
    public S    tr  ing getQual     ifiedRoleN am e() {
        re  turn qualifiedRoleNa   me;
            }

      /**
     * Sets the value of the  qualifiedRo    l       eName property     .     
     * 
       *    @p   aram val   u     e
          *     allowed objec   t   is
     *     {@link       String }
           *     
     * /
    public    void se     tQu alifiedRoleName(St   ring va      lue)        {
        th   is.q    u       alifiedRoleName      = value;
    }

    /*  *
       * Gets the valu          e of the qu        alified R   ole    NameLabel      pro p    erty.
             * 
     * @return         
        *      p     o    ssible ob    jec      t is
     *      {@l     ink  String    }
     *     
     */
    public St  rin   g getQual   ifie      dRole  NameLabel() {
                return qualifie   dRoleNameLabel;
    }
    
     /**
        * S    et  s the valu  e of     the qua   lified      Rol     eNameLabel property.
             * 
                  * @par      am value
       *     allowe     d obj     ect i    s
     *     {    @link String }
     *       
         */
    pub  lic       vo id setQualifi edRoleNameLabe l(String      value) {
                    t  hi    s.qualifiedRole  NameLabel = valu   e ;
    }       

    /**
       * Get       s the value of     the r    outeN     odeInsta    nceId p  ro per  ty      .
          * 
     * @return
         *     possible obje      ct is
      *     {@link St  ring }
     *     
     */
       p    ublic   String get      Rout   eNodeInstance  Id() {   
              ret  urn        rout    eNode         Inst     an   ceI       d;  
    }

    /   **
     * Set  s    the value of the routeNodeInstanceId pr    operty.
         * 
     *    @param    value     
     *     allowe  d obje ct is
     *     {@link Str               ing }
     *             
     *   /
    pu  blic void setR   outeNodeInstanceId(String val ue     ) {
            this.ro  uteNodeIn         stance  Id = va     lue; 
    }

    /**
       * Get      s  th    e value of the  nodeName property.
     * 
     * @return
         *     poss     ible object is
          *     {@l      ink String     }
         *     
       */
    public String getNodeName() {
               re   turn nodeN   ame;
    }

               /**
                    * Sets the    value of the no   deName prop    e rty.
     * 
        * @param value
     *     allowed object is
     *     {@link String }
          *     
     */
    pu    b   lic   void setNodeName(String value)  {
               this.nodeName = val  ue;
    }

    /**
            * G    ets     the value of the requestLabel property.
             * 
         * @retu    rn
        *     possible obje              ct    is
      *          {@link S       tring }
        *              
     */
          public S   tring getRequestLabel()        {
        retur      n  reque      stLabe       l       ;
            }

          /**
        * Sets the value o      f the r        equestLabel prope    rty.
         *    
       * @param value
     *      allowed object   is   
     *     {@link String     }
     *         
                 */
      public   void s   et    Req   uestLabel(String value)    {
                       this.  r   equestLabe   l = value;       
    }

    /**
     * Gets the valu    e of the parentActionRequestId               propert  y.
      * 
     * @return
     *     po      s     sib   le object is 
     *     {@li    nk Stri  ng }
         *        
         */
      public St r  ing getParentActionRequestId(        ) {
        return parentAct     io    nRequestId;
     } 

        /**
                   * Sets the value    o  f the parentAction  RequestId    property.
        * 
     * @param value
           *     allowed       obj ect is
       *     {@link St   ring }
     *       
           */
    public void setP arentActionRequestId(St ring value) {
        this.parentAct       ionRequestId = value;
    }

    /**  
            * Gets            the value of the a            ctionTaken property.
               * 
     * @return
     *          possible object is
     *     {@link ActionTakenType }
            *               
         */
    publi     c ActionTak   enTy pe getActionTa  ken() {
        return actionTa   ken;
    }

    /**
     * Set     s the value of the ac tionTaken property.
             * 
     * @      par      am value
     *     allowed object is
     *         {@link ActionTakenType }
     *     
     */
    public vo id se        tActionTaken(Actio  nTakenType v  al ue) {
           thi   s.actionTaken = value;
    }

    /**   
     * Gets the valu  e   of the c    hil dReques    ts property.
        * 
     * @return
     *     possible   object is
     *     {@link ActionRequestType.ChildRequests }
      *     
     */
    public ActionR  e questT   ype.ChildR   equests getChildRequests() {
         return childRequests;
    }

    /**
     * Se    ts the value of the childRequests property.
     *   
     * @param value
     *     allo  wed object is
     *        {@link ActionRequestType.Ch   ildR  equests }
     *     
     */
    publ ic void   setChild         Requests(ActionRequestType.ChildRequests v     alue) {
        this.childRequests = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor m  ethod return     s a reference to the live list,
     * no     t a snapshot.     Therefore any  modification you mak      e to the
     * returned list will be present inside the JAXB object.
        * This is why there is not a <CODE>set</CODE> m    ethod for the an   y prop   erty   .
     * 
     * <p>
     * For example,   to add a new item, do as follows:
     * <pre>
     *    getAny(     ).add(newItem);
      * < /pre>
     * 
       * 
       * <p>
     * Objects of the following type(s) are allowed in the list
     * {@li    nk Element }
     * 
     * 
     */
    pu   bl  ic List<Element> getAny() {
           if (any == null) {
              any = new ArrayList     <  Element>();
        }
        return this.any;
    }


    /**
     * <p>Java cla   ss for        anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contain     ed within this clas     s.
     * 
     * <pre>
     *    &lt;complexType>
     *   &lt;c  omplexConten  t>
          *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}a    ny    Type">
     *       &lt;sequence>
     *         &lt;element    name="childRequest" t      ype="{http://rice.kuali.org/kew/v2_0}ActionRequestType" maxOccurs="unbounded" minOccurs="0"/>
     *            &lt;/seque nce>
     *     &lt;/restriction>
     *   &lt;/c  omplexContent>
     * &lt;    /complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
             "childRequest"
    })
    public static class Chil     dRequest s {

        protected List<ActionRequestType> childRe  quest;

        /**
         * Gets the v            alue of the childRequest property.
         * 
         * <p>
         * Thi   s accessor method returns a reference to the live list,  
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the    JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the childRequest property.
         * 
         * <p>
         * For       e xample, to add a new item, do as follows:
         * <pr    e>
             *    getChildRequest   ().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ActionRequestType }
         * 
         * 
         */
        public List<ActionRequestType> getChildRequest() {
            if       (childRequest == null) {
                childRequest = new ArrayList<ActionRequestType>();
            }
            return this.childRequest;
        }

    }

}
