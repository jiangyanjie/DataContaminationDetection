
package   com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CountryEnum.
      * 
  * <p    >The     following schema fragment specifies the expected content contained within   thi    s cla    ss.
 * <p> 
 * <pre>
 * &lt;simpleTy   pe    name="CountryEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSche       ma}str ing">
     *           &lt;enumer     ation value="at"/>      
 *     &lt;enumeration  val         ue= "be"/>
 *               &lt;e   numeration     value="bg"/>
 *     &lt;enumeration value="ch"/>
 *             &lt;enumerat io     n valu  e=      "cs"/>
 *     &lt;enumeration value="c   y"/>
 *     &l  t;enumeration      value="cz"/>    
 *         &lt;en    umeration  value=        "d       e"/>
 *      &lt;en umer  ation value="dk"  />
 *     &lt;enumerati   on valu  e  ="ee"      />
 *         &lt;enumera      tion    v alu  e="es"/>
 *       &lt;enumeration va        lue="fi"  />
       *     &lt;en   um     eration value="fo"/   >
 *        &lt ;enu          merati    on   valu     e="    fr"/>
 *        &lt;  enum  eration value="gb  "/>
 *       &lt;en      umeration valu  e="gg"/>
 *     &lt;enumeratio  n val    ue="   gi"/   >
 *     &lt;enumerat ion value="gr"/>
 *              &lt;enumeration value="hr"/>
 *     &lt;enumera   tion     value="hu"/>
 *           &lt;enu      meratio  n value="ie"/>
 *       &lt;en       umerat  io   n value   ="im"/>
 *       &lt;e        numeration va     lue="is"/>
 *      &lt;enumeration        val   ue=  "it"/ >
 *     &lt;enumeration   value="je"/>
 *     &lt;enumeration value="li"/>
          *     &lt;enumeration value="lt"/>
 *     &lt;   e numeration        value="lu"/>
 *       &lt;enumerati     on va  lue="   lv"/>
 *        &lt;enumeration          value="ma"/>
 *     &lt;enumerati  on      value=  "mc     "/>        
 *              &lt;enumer         ation val             ue="m  k"/>
 *     &   lt;enum    eration value="mt"/>
     *     &lt;   enumeratio n value="nl"/>
      *            &lt;enumerat     ion value="no"/>
       *     &l   t;enume   ration value="p  l"/> 
 *     &l  t;enumeration val ue="p       t"/>
 *         &lt  ;enumeration value="ro"/>
 *     &lt;enumeration      value="se"/>
 *        &lt;enumerat              ion value="si"/>
 *     &l   t; enumeration value="sk"/  >
 *     &l t;    enu    meration value="s       m"/>      
 *     &lt;e     numeration   value  ="tr"/>
 *     &lt;enumeration value="va"/    >
 *     &lt;enumeration  value="oth  er"/>
 *   &lt;/restriction>  
   * &lt;/    simpleType>
 * </pr   e>    
  *      
 */
   @XmlType(name =  "C       ount     r yEnum", n   ame        space = "http:           //dat  ex2.eu  /sc   he   ma/   1_0/1_0")
@XmlEnum
publi      c enum Co  untryE      nu  m       {


         /**
      *           Austria
     *     
     */
          @XmlEn      u mValue("at  ")          
                     AT("at"),

       /**
                  * Be     lg           i           um  
                  * 
         */
      @XmlE  numV    a       lue        ("be     ")
    B  E   ("be"),

    /**  
           *    Bulgaria      
              * 
              */
                              @XmlEnum    Value("bg")
    BG("bg"),

          /**
      *                   S    w     itz              erl   and
      * 
     */
    @       XmlEnumValue("ch")
         C      H("ch"),

         /**       
          * S erbia and        Mon    te   negro
              *   
     */
    @Xm lE  numV     alue("cs")
      CS  (      " cs"),      

     /**
              * Cypru    s
        * 
     */
    @XmlEn      umVal       ue("cy   ")
             CY("cy"),

                     /**    
                           * Czech Repub       li c
     * 
     */
           @X   m   lE   numValu               e("cz")
     CZ("cz   "),
  
    /**
     *         Ger                many
     *    
        */
    @X   ml     E       nu mV           alue("de")
         D E      ("de")           ,

    / **
        * Denma rk   
        *       
     *       /
    @Xm   l     EnumValue("  dk")
        DK              ("dk   "),

    /**
     * Estonia
        * 
                               */     
    @Xml  EnumV     alue( "ee")
        EE("ee"),

    /     **
         * Sp   ain
         * 
           */
              @  Xml     En  umValue("    es")
           ES("es"),   

    /**
        *    Finland
     *   
     */
             @Xml           Enum     Valu              e("f       i    "    )            
    F  I("fi"),

          /*   *
                        * Faro   e       Islands
         * 
     */  
                     @Xm       lEnumVa        lue("fo")
              F  O  ("  fo")  ,

    /**
     * France
     * 
       */
    @XmlEnumVa    lue("fr")
                FR      ("fr  "),

      /* *
        *          G     reat Brit a     in   
                     * 
               */
    @Xml      EnumV        a      lue("gb     ")
                    GB  ("gb") ,       

    /**  
                 * Guernse   y
     * 
     */
      @X     m   lEnumV     alue     ("g   g  " )
                         GG("gg"),

          /**  
          *    Gibralta
          * 
     */
    @XmlEnu               mVal    ue("gi")
              G   I("gi   "),

    /**
               * G  re     ece   
              * 
                  */
       @XmlEn        umVal ue("gr") 
             GR(   "g             r" ),

        /**            
         * C      r oa      tia
     * 
       */
    @Xml    Enu   mValue("hr"  )   
               HR(    "hr"),

      /*  *
       *  Hungary
        *     
             */
             @Xml    EnumValue("h     u           ")
                HU("hu"),

            /**  
           * Ireland
     * 
     * /
    @X      mlEn   umValu e("ie")
    IE("ie")      ,  

       /**
                                *                   I     sle Of Man     
       * 
               */
    @XmlEnumValue("im")
    IM("i    m"),

    /** 
           *    Iceland
     * 
           */
              @XmlEn     umValue("is")     
      I   S("is"),

               /   **
     * Ital    y      
                          * 
       */
    @XmlEnumValue( "it")
          IT   (" it"),
      
                /**
              * Jersey
     *     
        */
           @Xml      EnumValue("je")
    JE("je"),

    /** 
                         * Lichten s t       ei  n
         * 
                  */
             @XmlEnumValue("li")
         LI("li"),

    /**
        * Lithuania
             * 
     *      /
    @XmlEnumValue("l       t")
       LT    ("        lt"),  
 
    /**                
     * Luxemb         ourg
           * 
     */
    @XmlEnumVal    ue("lu")
    LU("lu   "),

    /**
            * L atvi      a
               * 
     */
       @XmlEnum  V a   lue("lv")
           LV(    "lv"),

    /**
     *    Moro     cco
      *       
          */
    @XmlEnumValue   ("ma" )
    MA ("ma"),

    /   **
     * Monaco
     * 
           * /
      @XmlEnumValue("mc")
         MC("mc"),

    /**
       * Mac          edonia
        * 
      */
    @Xml   EnumV a   lue( "mk")
    MK("  mk"  ),

                 /**
     * Malta
     * 
     */
    @XmlEnumV    alue("mt    ")
    MT    ("mt"),

      /**
         * Netherlands
             *                      
     */
    @XmlEnumValue("nl")
    NL("nl"),

    /**
               * Norway
         * 
     *     /
    @XmlEnum      V    alue("no")
        NO("no")     ,

    /**
     * P        oland
     * 
              * /
    @Xml    EnumV alue("pl      ")
    PL(     "pl"),

    /**
     * Portugal
       * 
     */
    @XmlEnum  Value("pt")
    PT("pt"),

    /**
     * Romania
     * 
     */
    @XmlEnumValue("r  o")
    RO      ("ro"),

    /    **
     *   Sw eden
     * 
     */
    @XmlEn   umValue("se")
    S E("se"),

       /  **
         * Slovenia
     * 
     */ 
    @XmlEnumVal  ue("si")
    SI("si"),

    /**  
     * Slovakia
       * 
     *  /
    @XmlEnumValue("sk")
       SK("sk"),

    /**   
     * San Ma  rino
     * 
     */
    @XmlEnumValue("sm")
    SM("sm"),

    /**
         * Tu r  key
     * 
     */
        @XmlEn    umValue("tr")
    TR("tr"),

      /**
     * Vati       c   an City State
     * 
     */
        @XmlEnumValue("va")
    VA ("va"),

    /**
           * Other than as defined in this enumeration.    
     * 
     */  
        @Xml     EnumVal  ue("other")
    OTHER("other");
    private final String value;

    CountryEnum(String v) {
        value = v;
    }

    public St  ring value() {
           return value;
    }

    public static CountryEnum fromValue(String v) {
        for (CountryEnum c: CountryEnum.values()) {
            if (c.value.equa    ls(v)) {
                return c;
                }
        }
        throw new IllegalArgumentException(v);
    }

}
