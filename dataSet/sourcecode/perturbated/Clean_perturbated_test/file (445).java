
package DBLookUpClient;

import   javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 *   <p>   Java cla  ss for CONT_CNST_SVCS complex   ty  pe.
 * 
 *  <p>The follow  ing      schema fragment specifies the expec  ted content contained withi   n this c     lass.
 * 
 *        <pre>
 *    &lt;comp     lexType name="CONT_CNST_SV     CS">
 *      &lt;complexContent>
 *     &lt;restriction base="{http   ://  www.w3.or      g/2001      /X       MLSchema}  anyType">
 *                      &lt;sequence>
 *           &lt;eleme   nt      name="PAS    T  _24HRS" type="{http://www.w3.org/2001/XML  Schema}string"/>
 *            &lt;element name="PAST_1_TO_2_DAYS" type="{http://www.w3.o    rg/2001/X  MLSc    hema}stri    ng"/>
 *         &lt;element name="PAST_3_    TO_7_DAYS" type="{http:/    /www.w3   .org/2001/XMLSchema }string"/>
 *            &lt;element  name="P AST_8_TO_14_DAYS" type="       {http://w  ww.w3.org/2 001/XMLSchema}string"/>
 *         &lt;element name   ="PA  ST_15_TO_30_DA   YS" ty    pe=" {http://www.w3.org/2001/XMLSc  hem             a}st   ring"/>
 *         &lt;element name="PAST_31_TO   _60_    DAYS" type="{http  ://www.w3.org/2001/  X     MLSchema}s    tring"/>
 *         &lt;element name="  PAST_61_TO_90_DAYS" type="{http://www.w3.org/2001/XMLSc  hema}string"/>
 *         &lt;element name="PAST_9    1  _TO_1       8 0_DAYS" type="{http:/  /www.w3.o     rg/2001/XML Schema}string"/>
 *         &   lt;element name="TOT_S RCH" type=    "{http://www.w3.     org/20         01/X           MLSchema   }   str     ing"     />
 *       &lt;/sequ      ence>
   *             &lt;/restr  iction>
 *   &lt    ;/complexCont    ent>
 * &  lt;/c   omplexType>
 * </pr     e>
 * 
 * 
 */
@XmlAcce  ssorTy   pe(XmlAccessType.F    IELD          )
@XmlT  y   pe (name = "CONT_   CNST_SVCS", pr   o    pOrder = {
           "past24HRS",
    "past1TO2DAYS       ",
    "past3TO7DAYS",
    "past8T O14DAYS",
    "past15   TO30    DAYS",
    "past   31TO60DAYS",
     "past61TO90DAYS",    
        "past91T    O180DAYS",
    "totsrc   h"
})
publ  ic clas s CONT   CNSTSVCS {

    @XmlElement(name  = "PAST_24HRS" , require     d = true, nillable = true  )
    p  rotected String past24    HRS;
       @    Xml    Element(name =      "PAST_1_TO_2_DAYS", re   quired = tr     ue, nillable = true)
    p    rotected String     past1  TO2DAYS        ;
              @XmlElement(nam  e = "PAST_3_TO_7_D        AYS",      required = true, ni     llable = true)
    p    ro tected String past3TO7D  AYS;
        @XmlEle           ment(name    = "PAST_8_TO _14_DAYS", required = true,         nillable =  t       ru    e)
    protected S tring past8TO14DAYS;
    @Xm     l               Element(n     ame    =      "PA    ST_    15_TO_30_DAYS", r     equired     =   true, nillable   = true)
        protected String pas  t15TO    30DAYS   ;       
      @XmlElement(name =   "PAST_31_TO_60_DAYS",    required = t   rue, nillabl     e = true)
    prote    cted Stri    ng past31T O60DAYS;
          @X           m  lElemen  t(name = "PAST_61_TO_90_DAYS",     requi           red = t         rue,  ni        l       lable =    t    rue)
    pr      otected Strin g   p         a st61           T    O90DA       YS;  
    @X   mlElement(name = "PAST_   9    1_TO_18        0_DAYS"  ,   r         equired = true, nil    lable = t   rue)
    prot         ec t   ed S   tring          past91TO180DAYS;
    @ XmlEleme     nt(nam    e =    "TOT_SRCH", re   quired   = true , ni         ll  a  ble =   true)
    protected S        tring    t     otsr     ch;
 
    /**
       * Ge  ts      the value o    f t     he p  ast  24H      RS prop   er      ty   .     
     *           
     *      @return
           *       poss              ible ob   ject         is
                    *     {   @l               ink St   ring }
             *           
                */
              pu       blic St  ring getPA    S T24H    RS() {
                return past24HRS;     
    }
  
      /**
        *           Se    ts the value   of    the  pa    st24HRS property.
       *     
     * @p    aram v    alue
     *                   a             llowed object is
     *               {         @link     Str i ng   }
     *     
     *      /
        public void  setPAST24HR          S(String     va    lue) {
                  this  .past24     H   RS =    va  lue  ;
                }

    /**
     * Ge   ts  the val      ue of the p  ast1TO2DAYS p  rop    er ty.
              * 
     * @return
     *     pos  si     ble obj  ect is
     *     {@  link                           Stri         ng }
           *                  
     *              / 
    pub  lic Str ing getPAST1TO2DAYS()           {
          re       turn p  a   st1T  O2D    AYS;
           }

         /**
     *      Sets     the   value      of th   e past1       TO        2           D   AYS property.
       *  
     * @param va lue
     *        a   llowed obje   ct is
            *            {    @l     ink Str         ing }
       *       
         */
      public void setPA    ST1TO2DAYS(    String value) {
           t      his.past1T  O 2DAYS = va lue    ;
              }

         /   **
     * Gets t h  e valu  e   of t      he past3T      O   7D                 AYS property.
     * 
                * @ret         urn
     *                     possible object is
     *           {             @l   ink   St   r  ing   }
           *           
              */
    public String getPA  ST       3T   O7 DAYS                      (    )   {
                             re   tur     n past3TO7   DAYS;
            }
     
    /* *
          *                  Sets the        va  lu    e of the past3TO7DAYS property.
     * 
     * @p  aram value
         *          allow    ed         o  bjec   t is
     *        {@link         Strin      g }
         *        
                              */   
          pu   blic void setP      A        ST3TO7DAYS(String value) {
                  t   his.past3TO7DAY S =       value;
             }

               /**   
                * Gets th   e   va lue of     the past8TO14D AY S property.  
     * 
             * @return
     *     pos     sib       le o   bject i    s
          *     {@  li   n   k S  tring }      
                       *        
     */
       public String   getPAST8  TO14DAY S()    {
          re tur n past8TO14DAYS;
        }     

         /         **       
     * Sets the value of the       past8TO14    DAYS proper    ty.
                    * 
         * @param value
                      *     allowed ob       ject is
     *      {@li    nk S    tri       ng }
     *     
                     */
          pub   l      ic void set    PA      ST8TO14DA     Y            S(String       value ) {
               this    .past8TO14DAY S = value;
              }

        /*        *
       *   Gets the valu   e of the past15T   O30DAYS pr    o p     erty.
     * 
         *    @ret      urn
                      *     pos       si  bl           e obj  ect   is        
            *                {@l  i      n k Str   ing     }           
     *             
     * /
    publ   ic String g   et   PAST15TO30DAYS()     {
            retur   n past15TO30DA         YS;
                    } 

      /**
                        * Sets the value of   the pa      s  t15TO3       0D    AYS property.
     *  
     * @par     am value
     *                allowed object is
        *                {     @link     Str    ing }
         *       
     */   
                 p      ub          lic                 void setP  A   ST15TO30DAYS(String val      ue) {
            thi        s.past15T  O30   DA     Y S = valu   e;
     }

      /**
     * Gets the val ue o     f    the past3     1TO6     0DAYS   property.
     * 
      * @return
     *     p       oss            ible o   bject is
     *     {@link Str        ing }
     *       
     */
    public String getP AST31T       O6    0    DAYS() {
          ret    urn past31TO  60DAYS;   
    } 

       /** 
            * Sets the value of  the p   ast31TO6  0DAYS propert     y.
         * 
     * @param val         ue
          *         allo    wed object is
     *             {@link     Stri  ng }
       *     
     */
    p    ublic void setPAST31TO60DAYS(String value) {          
        this.p  ast31TO60DAYS = value;
    }

           /**
     *   Ge ts the valu e    of the past61TO9   0DAYS prop      erty.
     * 
           * @  ret     urn
     *        possible objec      t is
     *     {    @link Str    ing }
        *     
     */
        public String getPAST61TO90DAYS()   {
        re  turn p  ast61T         O90DAYS;
    }           
   
      /**
     * Sets the value of t          he pas   t61TO90DAYS property.
     * 
     * @para     m value
       *     allowed object i     s
            *        {@link String }
     *     
     */
    public void setPAST6 1TO   90DAYS(String valu      e    ) {
             this.past61TO90DAYS = value;
    }

    /**
     * Ge    ts the    valu       e of the pas    t91TO180      DAYS prop     er  ty.
     * 
     * @ret ur   n  
     *     possible object is
     *     {@link String }
     *     
     */   
    public String getPAST91T  O180DAYS() {
           return past91TO180DAYS;
    }

    /**
     * Sets the val       ue   of the past91TO180DAYS property.
     * 
          * @para    m value
     *           a    llowed object is
     *     {@link String }
     *     
         */
    public void setPAST91T   O180DAYS(String value) {
        this.past91TO180DAYS = value;
    }

    /**
     * Gets the value of the totsrch property.
         * 
     * @return
     *     possible object is
     *     {@link Stri   ng }
     *     
     */
    pu blic String getTOTSRCH() {
        retur    n totsrch;
    }

    /**
     * Sets the value of the    totsrch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOTSRCH(String value) {
        this.totsrch = value;
    }

}
