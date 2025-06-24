
package    DBLookUpClient;

import javax.xml.bind.annotation.XmlAccessType;
imp    ort javax.xml.bind.annotation.XmlAccessorType;
imp     ort javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/     **
 * <p>Java cl ass for CONT_CNST_  SVC      S com   plex type.
   * 
 *  <p>The following schema f  ragment specifies the expected content contained within           th     is class.
 * 
 * <pre>
 * &lt;complexType name="CONT_CNST_SVCS"> 
 *   &lt;comple   xContent>
 *     &lt;restriction base="{http://w    w       w.w   3.org/20     01/XM    LSc     hema}a     nyType">
 *        &  lt;sequence>  
 *             &lt;ele     ment n  ame="PAST_24HRS" type="{http://www.w3.o   rg/20      01/XMLSchema}string"/>
 *                      &lt;element name="PAST_1_TO_2_DA  YS" type="{http://www.w3.org/      2001/X    MLS che    ma}string"/>
 *         &lt;  element name="PAST_3_TO_7_DAYS" typ  e="     {http://www.  w3.org/20 01  /XMLSchema}string"/>
          *              &lt;element name="PAST_8_TO_14_DAYS" type="{ht  tp://www.w3.org/2001   /XM  LSchema}s  tring"/>
 *              &lt;element name="PA  ST_15_TO_   30_DAYS" type="{http://   www.w3.org/2001/XMLSchema}      s  tr      ing"    />
 *                    &lt;el    ement name="PAST_3  1_      TO_60_DAYS        "     type="{     http://www.w3.org/200    1/XMLSchema}string"/>
 *         &lt  ;element na   me="PAST_61_TO_90_DAYS" type="{http://www.w3. org/2001/XMLSchema}string"/     >
 *         &    lt;eleme   nt name="PAST_9      1_TO_180_DAYS" t   ype="{http://www.w3.org/2001/X  MLSchema}      strin       g"/  >
 *         &lt;element        name="T    OT_SRC          H" typ   e="{http://www.     w3.org/2001/  XMLS     ch           em  a}st ring"/>
 *       &lt;/sequence>
 *      &lt;/r estriction>
 *   &lt;/complexC    o  ntent>
 *    &lt;/comp     lexT     ype>
 * </pre>
 * 
 * 
 */
@XmlAcc     essorTyp   e(XmlAcce s   sT  ype.F  IEL  D)
@XmlType(name =    "CONT _CNST_SVCS", propO       rder  = {
    "past24HR        S",
    "past1TO2DAYS",
    "past3TO7DAYS",
    "p ast8TO14DAYS",
    "past15    TO30DAYS",
    "past31TO60D   AYS",
    "pas t61TO9      0DAYS", 
     "p          ast91TO180DAYS",
    "to  tsrch"
})
public class CONTCNSTSVCS    {

    @XmlElem    ent(n    ame = "PA  ST_24HRS",   required =    true, nillable = true)
     protec  ted String past     24HRS;
       @X        mlElement(name = "PAST_1_TO_         2_DAYS", required = true, nillable          = true)
    protected String     past1TO2DAYS;
       @XmlElement  (name = "P  AST_3_TO_7_DAYS", req     uired =     tr    ue, nillable = true)
    protec ted String past3  T    O7DAYS;
    @XmlElement(name = "PAST_8_TO_14_D      AYS", requir ed = true, nilla            ble     = true)
    protected String past8TO14DAYS;
    @XmlElement(n      ame     = "PAST_15_TO_30_DAYS", requi red = true, nillable = true)
    protect ed String past15TO30DAYS;   
    @Xm     lElemen    t(name  = "PA   ST_31_TO_60_DAY   S", required = true, nilla ble = t      rue)   
             protected String past31TO60DAYS  ;
    @XmlElement (name = "PAST_6   1                  _TO_90_DA  YS", require   d = t     rue , nil      lable  = tru     e)
       prot   ected S t    r   ing    p        ast 61TO    90DAYS;
    @X    mlEleme  nt(na me = "P      AS  T_91_TO_180_DAYS", req   uired = true, ni    llable = true)
        p   rote   cted Stri ng   past91TO18  0DA  Y           S;
    @XmlEl  eme    nt(name   = "TOT_SR         CH    "          ,        r     equire   d = true,       nillable      = true)
                    protect                 e d String   totsr    ch;

                /**
                *         Gets          th  e value of t   he  past 24         HRS proper    ty.
                        * 
               * @r etur  n
          *      possib  le   object      is
          *                  {@l  ink Stri     n     g }
             *       
     */
    p   u       blic S tring getPAST24H RS   () {                      
                  r eturn              past2 4HRS;
    }

    / **
          * Sets the va   lu e of th         e past24HR      S prope   rty.   
        * 
     * @ p  aram     value
       *     allow    ed       objec     t is
         *             {   @link String }
     *        
             */
      public v  oid s    etPAST2             4HRS         (String    valu e         ) {
          this   .past24H   R S = va     lu   e;  
      }

    /**
     *    Get    s   th   e value  of            the past  1TO 2DAYS property.
        * 
       * @re  tur      n
     *      possible o     bj  ec  t is
     *     {@           link Strin              g }
     *                  
       */
     pub            lic Str  ing getP    AST 1TO2DA          Y  S   ()    {
        r   e      turn pas              t1TO2DAYS;   
      }
        
    /     *     *          
     * Sets th     e valu  e o     f t  he         past1TO2DAYS proper    t      y.
     *     
     * @         param val   ue
               *       a        l    lowed object is
     *     {@link String }
        *          
     */
    public       void        s     etPA      ST1    TO2DAYS(String va    lue) {
         this   .p             ast1TO2  DAYS    = value;
           }

           /**
                 * Gets th  e val ue of         the p  as  t3TO7DAYS prop    erty.
          * 
             * @ re    turn
      *         poss  ible object is
      *                 {@   l i     nk Strin    g   }
     *          
                          */
     public   St   ring      getP   AST3TO7DAYS()            {
        return             past3TO7D    AYS;
    }           

                /**
             * Sets     t    he value   of the p   ast3TO       7DAYS propert          y.
          * 
        * @param value
            *     all owed   ob     j   ect is
     *         {@link           String }
     *              
         */
            public void setPAST3TO7DAYS(St ri   ng value) {         
         this.pa st3T  O7DAYS        = value    ;
    }

        /*   *
                  * Gets     th  e value of the              past8TO14      DAYS property.
     * 
         * @return
      *                                  possible obj  ect        is
     *       {@link String         }
     *      
                   */
    p         ublic    S tr   i ng      getP AST8TO14     DAYS()   {
        return pa  st 8TO14DA      Y     S   ;    
    }
   
       /* *
         *     S ets          the   val ue o   f the       past     8 TO14D   AYS prope  r              ty.
         * 
         *        @p     ara   m val ue
                           *     allow       e               d object is   
           *             {@link   S  tri ng }
                  *      
     *   /
    p    u        bl     i c void setPAST8TO  1  4                          DAYS(Stri    ng value) {
               t   his.     past8TO1  4DA         YS = value;
    }

    /   *    *
       * Gets t   he valu     e     of      the past1   5T O30   DAYS p     roperty.
        * 
           * @re  turn
     *     p o    ssible objec        t is
             *     {       @link String  }     
       *          
     */
     public String ge   t    PAST15TO30DA          Y          S()  {
                          return p ast1   5    TO30DAYS;
    }
 
    /**  
     * Sets th    e value o    f    t   he past15T                O 30DAYS pro perty.
              * 
           * @param v     alu       e       
     *          allowed object  is
      *        {@link S      tring }
                          *       
     * /
       pub      lic        void setPAST15TO30DAYS(String v  al u   e  )  {
        this.past15TO30DAY     S = value;
       }   

    /**
        * Gets the valu e of the     past 31TO60DAYS pr   operty.
           *         
     * @return
     *     pos     s  ible object  is
     *         {@link   String         }
         *     
           */
             publi   c String getPAST31TO  60DAYS  ()         {
        retu  rn past31TO60DAYS;
    }

    /**
         *     Sets the    v   alue of the past31TO60DAYS pr      o     p      erty.
     * 
                * @param value
              *     allo wed object is
                 *     {@link String }
     *       
        */
      pub lic void setPAST31TO60DAYS(String value) {
         this.past31TO6 0D       AYS = value;
         }

    /**
     * Gets the value o       f the pa         st61TO90DAYS   pr         operty.
     * 
     *       @return
        *     possible object is
        *       {@link String }
       *       
     */
    publi    c String get     PAST61TO90DAYS() {
        ret          ur        n    past61TO90DAYS;
    }

                 / **  
         *                 Sets the value of the past61TO90DA     YS property.
              *   
     *  @param value
         *     allowe  d object is
     *           {@link String }
        *     
     */  
    publi   c void setPAST61TO90DAYS(String value) {
        this.past61TO90DAYS   = value;
        }

    /*     *
     * Ge ts the value of    the p    ast91TO180DAYS property.
     * 
     * @ret       urn
        *     possible object is
     *           {@link String }
     *     
     */
    public S    tring getPAST91T   O180DAY  S()   {        
         return past91TO180DAYS;
        }

         /**
     * Sets the value of the past91TO180DAYS property.
        * 
     * @param v alue
        *     all   owed object is
     *     {@link      Strin    g }
     *     
     */
    public void setPAST91TO180DAYS(String value) {
          this.past9    1TO180DAYS = value;
    }

    /**    
     * Gets the value of the totsrch property.
     * 
     * @return
      *     possible object    is
     *     {@link String }
     *       
     */
    public String getTOTSRCH() {
        return totsrch;
         }

    /**
     * Sets the value of the totsrch property.
     * 
     * @pa   ram value     
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOTSRCH(String value) {
        this.totsrch = value;
    }

}
