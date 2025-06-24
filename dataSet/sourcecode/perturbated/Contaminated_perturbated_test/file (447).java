
package  com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
impor t javax.xml.bind.annotation.XmlType;


/**    
 * <p>Java class for      DatexPictogram  Enum  .
 * 
  * <p>The f           ollowin  g schema fragment sp    ecifies the expected content      contained within this           class.
 * <p>
 * <pre>
    * &lt;simpleTyp  e name="DatexPictogramEnum">
 *   &  lt;restriction base="{http://www.w3.o       rg/2001/XMLSchema}s    tring">
 *     &lt;enumeration value=     "advisorySpeed"/>
 *     &lt;enumeration value=  "   blankVoid"/>
 *          &lt;enumeration value="chainsOrSnowTyresRecommended  "/>
 *     &lt;enum  eration value="crossWind"/>
 *       &lt;enumeration value="drivingOfVehiclesLessThanXMetresAp   art    Pro   hibited"/>
 *     &lt;enumeration value="endO   fAdvisory Sp     eed"/> 
 *       &lt;enumeration value="endOf  ProhibitionOf  Overtaking"/>
 *     &lt;enumeration value="endOfProhibiti    onOfOverta ki    ngForGoodsVehicles"/>
 *     &lt;enumeration val   ue="en    d        OfSpeedLimit"/>
 *      &lt     ;enumer   ation value="exitClosed"/>  
           *     &l          t   ;enumeration value="fog"/>
 *     &lt;enumerat         ion val    ue="keepASafeDi   stance"/>
 *     &lt;enumeration value="  maximumSpeedLimit  "/>
 *           &lt;enumeration    value="noE  ntry"/>
 *     &lt;enumeratio      n value="noEntryForG       oodsVehicles"/>
 *       &l t;e   numeration value="noEntryForVehiclesExceedingXTonnesLadenMass"/>
 *     &lt;enumeration value="noEntryForVehiclesHavingAMassEx      ceedingXT    onnesOnOneAxle"/>
 *     &lt  ;enumer ation value="noEntryForVehiclesHavingAnO     verallHeig     htExceedingXMetres"/>
 *           &lt;enumeration value="noEntryForVehiclesHavingAnOverallLengthExceedi  ngXMetr         es"/>
 *     &lt;e   numeration value="noEntryForVehiclesCarryingDangerousGoods"/>
 *     &      lt;enumerati        o  n value="otherDange   r"/>
 *     &lt;enumeration value="overtak   ingBy     GoodsVehicl esProhibite        d"/>
 *     &l  t;enu     meration value="o   ve    rtakingProhi bited"/>
 *           &lt;enum eration    va    lue="roadCl   osedAhead"/>
 *     &lt;enumeration value="roadworks"/>
 *     &lt;enumer ation value   ="  slip       peryRoad"/>
 *     &lt;e numeratio   n val     ue ="snow"/    >
 *     &lt;    enumeration v  alue="snowTyresCo    mpulsory"/>
    *       &lt;enumeration value="traffic     Conge        stion"/>
 *      &lt;/   restriction>
 *     &lt;/simpleT   ype>
 * </pr   e>
 * 
 */
@XmlT   yp  e  (name = "DatexP      ictog ramEnu   m", names   pace = "htt   p   ://da      tex2.e  u/schema/1_0/        1_0")
@XmlEnum
public enu    m Da    texPi ctogramEnum { 


       /   **
     * Adv    isory speed limit.
        * 
     */
    @XmlEnumValue(         "advisorySpeed")
           ADVISORY_S  PEED("ad     visorySpeed" ),
  
    /**
             *        Bla  nk or vo   id.
     * 
     * /
    @X mlEnumValue("blankVoid    ")
    B   LANK_VOID("blankVoi d"),

                       /**
        * Cha      i ns or snow tyre s are recommende  d.
     * 
                 */  
        @XmlEnumValue(      "c     hainsOrSnowTyr      es Recommended")
    CHAINS_O R_SNOW_TYRE       S_RECOMMENDED("chainsOrSno     wTyres  Recommended"),

     /**
       * Cr  oss   wind.
     * 
      */
        @XmlEnumValue("crossWind")
    CROS   S_WIND( "cro    ssWind"),

    /**
     * The d   rivin      g      of vehicles less   th  a        n  X metres apart is prohibited.
     * 
        */
    @XmlEn  umValue("drivingOfVehiclesLessThanXM   etresApartProhibited")
         DRIVIN       G_OF_VEHICLES_L  ESS_THAN_X_METRES_APA     RT_PR  OHIBITED("       drivingOfVehiclesLessThanXMetre s   A partProhibited"),

    /**
     * End of adviso  r  y speed  limit.
     * 
                    */
    @XmlEnumValue("endO fAdvisorySpeed"  )
                        END_OF_ADVIS     ORY_SPEED  (    "endOf     AdvisorySpe   ed"),
 
    /**
            * End of prohibition     of     overtaking.   
     * 
     */
    @XmlEnumVa           lue("endO  fProhib   itionOfOvertaking")
      E     ND_O   F_PROHIBI    TION_OF_OVERTAKING("end    O  fPro   hibitionO    fOv ertaking"),

    /          **
     * End   of    prohibition of overtak ing for goods vehicles.
     *   
     */
    @XmlEnumValue("endOfProhibi         tionO      fO      vertaki   ngForGoods    Vehicles")
           END_            OF_P ROHIBITION_OF_   OVERTAKING_FOR_GOODS_VEHICLES("endOfPro          hibit                io nOfOvertakingForG    o        od     sVehicles")    ,

       /**
     * End of m   andatory speed l imit.
            * 
              *  /
    @XmlEnumValue("end OfSpeedLi m    it       ")
    END      _OF_S  P EED _LI    MIT("endOfSpeedLimit"),

     /**
     * Exit clos     ed.
     * 
         */
       @XmlEn   umValue("e         x  itClose       d")
    EXIT_CLOSED("e     xitCl   os   e        d"),

               /**     
     * Fog.
                         * 
     */    
     @            Xm  lEnu   mValue("f og")
         FOG(" fog") ,     

    /*      *
           * K   ee       p a saf  e    distance.
     * 
     */
    @ XmlEnu mVal   ue(    "keepASafeDistance")
    KEEP          _A_SAFE     _DISTANCE("keepASaf   eDistance"),

    /**       
      * Mandatory spe     e             d limit.
            * 
       */
    @   XmlEnu mValue("maximumSpeedLimit")   
    MAXIMUM_SPEED_LIMIT("maximumSpeedLimit"),

    /**
     * No ent  r   y.
     * 
             */
    @XmlEnumValu   e("noEntry"    )
    NO_ENTRY("noEntry" ),

      /**
     * No entry for goods vehicles.
       * 
      */
    @XmlEnumValue("no    EntryForGoodsVehicl   es"    )
    NO_ENTRY_FOR_GOODS_VEHICLES("   noEntryForG   ood    sVehicles"),
 
    /**
              * No entry for v  ehicles exceed   ing X tonnes laden mass.
     * 
     */
    @     XmlEnu           mValue("noE  ntryForVehicle    sExceedingX   TonnesLadenMass")
    NO_ENTRY_FOR_VEHICLES_EXCEEDING_X_T  ONNES_LADEN_MASS("noEntryForV     ehicl   esExceedingXTonnesLadenM  ass")  ,

               /**
         * No entry for    v   ehicles having a mass exc eed ing X tonnes on a sing  le axl      e.
         * 
        */
    @XmlE       numValue("noE   nt ryForVehic    lesHavingAMassExce edingXTonnesOnOn    eAxle")
      NO_ENTRY_FOR_            VEHICLES_HA   VIN   G_       A_MASS       _E       XCE        ED   I     NG_X_  TON  NES_ON_ONE_AXLE("noE       ntryForVeh          iclesHavingA      Mass    Ex  ceedingXT  o  nnesOnOneAxle")     ,            

       /**
     * No entry for vehicles having an overall height exceeding X me         tres.
     * 
         */
    @Xm        l      Enum    Value("noEntryForVehiclesHavingAnOverallH  eig    htExceeding  XMetres"  )
    NO_ENTRY_FOR_VEHICLES _HAVING_AN    _OVERALL_HEIGHT_EXC E    EDING_    X_METRES("noE    ntryForVehiclesHavin   gAnOver          allHeightExceedingXMetres   "),

     /**
     * No entry       for vehicles having  an ov     erall length exceeding X metres.  
               * 
       */
    @XmlEnumValue ("noE  ntryForVehiclesHa vingAnO  ve   ra     llL   en   gthExceedingXMetres")    
    NO  _ENTRY_FOR_VEHICLES_HAVING_AN_OVE    RALL_LEN GTH_EXC EEDING_X_METRES("noEntryForV     e         hiclesHav  ingAnOverallLengthExceedin gXMetres ")   ,

         /**
     * No entry for vehicles   carrying dange       rous goods  .
     * 
     */
       @XmlEnumValu  e("noEntryForVe hiclesCarryingDangerousG     oo    ds")
                  NO_ENTRY_FOR_VEHICLE    S_CARRYING   _DANGE  ROUS_GOODS("noEntryForV   ehiclesC    a     rry     ingDangerousGoods  "),

    /**
              * Danger ahead.
     *        
          */
      @X     mlEnumValue("otherDanger")
    OTHER     _DA    NGER("otherDanger"), 

    /   **
     * O  vertaking    p  rohibited f   or     goods  vehicles.
        * 
        */
    @Xm  lEnumValue("overtaki    ngByGoodsVehiclesProhibited")
          OVERTAKI       NG_      BY_GOO   DS_VEHICLES_PROHIBITED("overtakingByGo   odsVehicles Prohibited"),

    /**
     * Ove     rtaking prohibited.
                       * 
              */
           @XmlEnumValu     e("ove    rt       akingProhibited")
    OVE      RTAKING_PROHIBITED("overtakingProhibited"),

    /**
       *    Road   closed ahead.
     * 
     */
    @Xm lEn      umValue("roadClosedAhead ")
    ROAD_CLOSED_AHEAD("roadClosedAhead"),

    /**
         *     R oadworks.
         * 
     */
    @XmlEnumValue("roadwork s")   
      ROADWORKS("roadworks"),

    /*   *
     * Sl    i    ppery r    oad.
     *     
         */
    @XmlEnumValue("slipperyRoad")
    SLIPPERY_ROAD(    "slipperyRoad"),

    /**
          * Snow.
     * 
     */
    @XmlEnumValue("snow")
    SNOW("snow    "),
   
    /**
     * Snow types compulsory.
     * 
     */
    @XmlE       numValue("snowTyresCom      pulsory")
    SNOW_TYRES_COMPULSORY("snowTyresCompulsory"),
   
    /**
     * Traffic     congestion and     pos   sible que   ues.   
     * 
     */
    @XmlEnumValu   e("trafficCongestion")
    TRAFFI        C_CONGESTION("trafficCongestion");
    private final String value;

    D   atexPictogramEnum(String   v) {
        value = v;
        }

    public String value() { 
        return value;
     }

    public static DatexPictog    ramEnum fromValue(String v) {
        for (DatexPictogramEnum c: DatexPic   tog   ramEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
