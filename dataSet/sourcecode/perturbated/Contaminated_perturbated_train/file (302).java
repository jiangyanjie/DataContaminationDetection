
package com.transportation.SIRI_IL.SOAP;

import  javax.xml.bind.annotation.XmlEnum;
import    javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java cl ass for   AccidentTypeEnum.
 * 
 * <p>The following schema f     ragment specifies the expected       content containe   d withi n this class.
   * <p>
 * <pre>
 * &lt;si  mpleType name="A ccidentTy   peEnum">
 *   &lt;restr     iction base="{http://www.w3.    org/2001          /XMLS   che ma}string">
 *     &   lt;enumeration       value="ac cident"/>
 *                 &lt        ;enumeration value="accidentInves   tigatio   nWork"/>
 *     &lt;enumeratio  n valu     e="accidentInvolvi      ngBicycles"/>
 *     &lt;enumeration value="acc    iden tIn    volvingBuses"/>
 *     &lt;enumeration value="     accidentInvolvingHazardousMaterials"/>
 *       &lt;enumeration value="accidentInvo    lvi     ngHea    vyLorries"/>
      *     &lt;enumer ation value="ac    cident   InvolvingMopeds"/>
 *          &lt;enumeration value="accidentInvolvingMo  torcycles"/>
 *            &      lt;enume  ration valu e="   accidentInvolvingTrain"/>
 *      &lt;enumera    tion value="chemicalSpil lageAccident  "/>
 *      &lt;enumer    ation    value="collision"/>       
 *      &lt;enumeration value="collisionWithAnimal"/>
            *     &lt;enumeration value="collisionWithOb   struction"/>
 *     &lt;en umeration   value="collisionWithPerson"/>      
 *               &lt;enumera     tion value="earlierAcciden     t "/>
 *      &lt;enum   eration value="        fuelSpil lageAc  cident"/>
 *     &lt;enumerat   ion value="headOnCo llision"/>
 *            &lt;enumeration v   alue ="headOnOrSideCollision"/>
       *     &lt;enum eration    va lue="jackknifedArticulatedLorry"/>
 *     &lt;enumeration value="jackknifedCarava    n"/>
 *     &lt;enumerati    on value ="jackknifedT railer"/>
 *     &lt;enumeration v    alue    ="multipleVehicleColli    sion"/> 
 *     &lt;enumeration value="multivehicleA       ccident"/>
 *     &lt;     enumera   tion valu   e="oi  lSpil    lageAccid ent"/>
 *     &lt;enumeratio    n v     alue="overturnedHeavyLorry"/>
 *     &   lt;enumeration value="    overturnedTrailer"/>
 *     &l  t;enumeration    value="overturnedVehicle"/>
 *           &lt;enumeration       value="rearCollis ion"/>
 *     &lt;enumeration    value="secondaryAccide nt"/>
     *     &lt;enume    rati  on value      ="   seriousAccident"/>
 *      &lt;enume  ration value="sideCollision"/>
 *     &lt;enum  eration value="vehicleOffRoad"/>
 *     &lt;enumeration valu               e="    vehicleSpunAround"/>
 *         &lt;enumerat    ion value   =" othe    r"/> 
 *   &lt;/restri   ct ion>
      * &lt;/simp  leTyp   e>
 * </pre>
 * 
 */
@XmlType(n     ame =     "AccidentTypeEn   um", namespac   e = "http://datex2.eu/schema/1_0/1_   0        ")
     @X     mlEnum  
public    enu   m Acc    identT            ypeEnum   {


    /**
           * Accidents are situations in wh              ich one or m  o    r   e vehicles lose control and do not recov  er.  They incl   ude col   lisions between vehicle    (s) or   other roa   d user(s), between     vehi       cle(s) and   f   ixed obs   tac  le(s), or they result fro    m a vehi   cle running off           t    he     road  .
     * 
       */
    @XmlEnumVa  lu  e("   accide  nt")
    ACCIDENT("accident")        ,

        /**
          * Authorised investigation work connected      to an earli       er rep   or   t   ed   a         ccident   that may disrupt  traffi     c    .
                * 
     */
    @Xml      EnumValue("    accid  entIn   vestigationWork")
    ACCI    DENT_INVE    ST  IGATION_W  ORK("accidentInvestigati  o     nWork"),

      /**
     * Inc   lude     s all accidents involving at       least one bicyc    le.
     * 
         */
           @XmlE   numVal     ue("accidentInvolvingBicycles")
    ACCIDENT_INVOLVING_BICYCLES( "accidentInv  olvingBicycles"),

      /**
     * Include   s all accidents involving at least one p  assenger vehi    cle.
          * 
     */
    @XmlEn      umV          alue("accidentInvo  lvingBuses")
                       AC CID      E      NT_INVOLVING_BUSES("a    cci    de   ntInv    o  lvingBuses"),

    /**
     * Includes all accidents in    volvin     g   at least one vehicle believed to be carrying materials, whi   ch could present an a       dditional hazard to road users.
          * 
     */
    @XmlEnumValue("acciden tInvolvingHazardousMate     rials")
    ACCIDENT_INVOLVING_H A  ZARDOUS_  MA  TERIA LS("accidentIn  volvingHazardo    usMateri als"),

        /**
     * Includes all accident    s involving at least   on    e heavy go     ods vehicle.
          * 
     */
              @XmlEnumV     alue("accidentInvolvi   ngHeavyLorries")
    ACCIDENT_IN   VOL  VING       _H  EAVY_LORRIES("accidentInvo       lvingHeav   yLorr   ies"),

    /**
     * Include       s all accidents      involving at least one moped.
         *   
       */    
    @XmlEn   um       Value(  "acci    dentInvo  lvingMopeds")
    ACCIDENT_INVOLVING_MOPEDS("acci   dentIn      volvingM opeds"  ),

    /**
              * Includes all accide   nts invol    v ing at le  ast one motor cycle.
       * 
       */
    @XmlEnumValue("accid     en      tInvolvingMotorcycles")
    ACCIDENT_INVOL   VIN     G_MOTO   R  CYCLES("ac cidentInvolv   ingMotorcy  c  les"),

             /**
       * I  ncludes all ac   cid   ents i     nvo   l     ving collision with a train.
      *      
     */
    @Xm     lEn   um    V alue("  acc  ide   ntInvolvin g  Trai  n")
                  ACCIDENT_IN         VO         L   VI      NG_TRAI  N     ("acc i   d      entInvolvingTra i   n") ,

    /**
     * Includes al l situations res                  ulting in a spillage of chem icals o  n t he carriageway.
               * 
     */
    @XmlEnumV alu             e("che      m   icalSpi llageAcc      ident")
    CHEMIC    A L_SPILLAGE  _ACCI     DENT     ("chemicalS    p  illageAccid       ent"),

    /**
        * Collision of vehicle with another    object of unspec  ifi   ed type   .
     * 
     */
    @XmlEnumValue("collision")
    COLLISION("c  ollision"),

    /**
     * Collision    of vehi    cle              with one or more  an     imals.
     * 
     */
      @      XmlEn   umValue("collisionWithAn    imal")
    COLLISION_WITH_ANIM    AL("c ol  lis ionWit   hAnimal"),

    /**
     * Co     llision of veh    icle with an obje   ct of a stationary    na ture.
     *   
                   *   /
             @  XmlEnu   m    Val   ue("co    llision  Wit  hObstruction")
    C    OLLI  SION_WITH_OBSTRUCTION("collisio                nWi  thOb structio n"),

    /**
        * Collision    of vehicle wi     th one  or more pedestrians.
     * 
          */
    @XmlEnumVa               lue("col   lisio  nWithPerso     n")     
          COLL    ISION_WITH_PERSON("collisionWithPer  son"),

    /**
     * An earlier reported  accident that is causing      d       is  rup   tion to traffic or is resulting in f    ur  ther acc  iden       ts.
     * 
     */
        @XmlEnu  mValue("earlierAccident")
    EARLIER    _ACCIDENT("e  arl i erA  ccident"),
 
    /**
         *         Includes all situations   resulting in a spill   age of fuel    on the carriage   way.
       * 
     */
        @XmlE nu   mV          a lue("f   uelS    pillageAccid     ent")
        FUEL  _SPILLAGE_ACCID      ENT("fuel SpillageA    ccident"),

    /**
         * Collisio     n of   vehic  le wit  h anoth er vehic    le head    on.
     * 
     */
    @XmlE numValue("headOn     Collision")
    HEAD_ON_COL  LISION("he   adOnCollision"),

    /**
     * Colli sion o   f vehi        cle with another veh   icle e  ither head on or s          ideways.
     * 
          */   
    @XmlEnumValue("head OnOrSideCollisio       n")
    HEAD_ON_OR_S     IDE_   COL    LISION("hea       dOnO     rSideCollision"),

       /**
     *    Includes all situations   res    ult     ing in a hea     vy    goods vehic  le fo    ld     ing         tog       ether in an ac  cidental skidding movement on the carriageway .
     *      
       */
                    @XmlE   numV     alue(  "jack   knifedArti              cula    tedLorry")
    JACKKNIFED_ARTICULA   T    ED _LORRY("jackkn       ifedA rticulatedLor ry")  ,

               /**
                  *   Includes all situations  re     sul   ting        in a vehicle and cara   van fol  ding tog   ether in an accident  al skiddi  ng movement  on the carriageway.
              * 
     */
    @  XmlE   numValue("    ja         ckknifedC aravan ")   
    JAC  KKNIF   ED_CARAVAN("jackknifedCaravan"),

    /**
          * Includes all situations resulting i   n a ve      hicle     and trailer folding t  oge   the   r i n an acc ide    ntal skidd      ing movement on the carria  geway.
             *           
               */   
       @XmlEnumValu  e(  "jackk  nifedTrailer")
            JACK    KNIFE         D_T  RAILER("jackknifedTrailer"),

    /**
     * M       ultiple vehicles involved in a collision.
     * 
     */
       @Xm   lEnumValue("multipleVehicleCollision")
    MULTIPLE_V  E  HICLE_COLLISION("    multipleVe      hicleColli            sion"),

      /**
     * Includes all accide   nts invo   lvi ng three                          or more v  e     hicles.
     * 
     */
       @Xml  Enu mValue("multi        vehicleAcci   dent")
         MULTIV EHICLE_ACCIDENT("multi    vehicleAcciden        t  "),
  
      /      **
     * Includes all situat    ions resulting in a spillage o    f oil on the carriageway.
     * 
     */
    @   XmlEnumValu    e("oilSpillageAccident")
          OIL_SP  ILLAGE_ACCID    ENT("oilSpillageAccide  nt"),

       /**
     * Incl  udes a      ll situatio   ns res u l             ting in      the ove   rturning of a      he    av  y g   oods vehicle on the   carria  gewa    y.
     * 
     */    
    @XmlEnumValue("overturnedHeav       yLorry")
    OVERTURNE    D_HEAV      Y_LORRY("overturnedHea     v yLorry"   ),    

          /**
        * Includ    es all  sit   uation  s resulting in     th   e    overturnin g of              a trailer.
     * 
     */
              @X      mlEn        um   Value("overtu  rnedTrailer")
    OVERTURNED_TRAIL   ER("overturne  dTrailer"),

        /**
       * Includ  es all situations resulting in the overturning of a veh icle    (of       unspecifi    ed type) on the car    riageway.
     *    
     */
    @XmlEnu     mValue("o  vert  ur     nedV  ehicle")
    OVERTUR NED_VEHICL     E( "over  turnedVe  hicl  e"),

    /**
     * Includes al   l accide     nts    whe   re  one or mor e    vehi  c les have c     ollid ed with the  rear of one or    m    ore other vehicles.
        * 
        */
    @XmlEnumValue   ("rearCollision"      )
    REAR_C OL   LISION("rearCollis i     on")    ,

         /**
     * Includes all sit  uations   resultin  g from    ve   hicles a   voiding       or bei      ng distracted by earlier accidents.
     *     
     */
    @XmlEnumValue("secondaryAccident")
    SECONDA      RY_ACCIDENT(     "seco  n            d aryAccident"), 

    /**
     * Includes all accidents be lieved to involve fatal ity or injury expected to require    overnight hospi   talisation.
       * 
               */
    @XmlEnumValue("serio  usAccident")
    SERIOUS     _ACCIDENT("seri    ousAccident"),
  
    /      **
        * Incl     udes all accidents where on    e o   r more vehicles have collided with t  he s     ide of one or more other vehic  les.
     * 
        *    /
    @XmlEnumValue("sideCollision")
     SIDE_COLLISION("sideCollision"),

    /**
         * Includes all ac      cidents where one     or more   vehicle s have left the roadway.
         * 
      */
    @X    mlEnumValue("vehicleOffRoad")
    VEHI    CLE_OFF_ROAD("vehicleOffRoad"),   

    /**
               * Includes   all accidents where a vehicle has skidded and h as come to r  est not fac ing its   inte nded line   of travel.
     * 
     */
    @XmlEnu  mValue("vehicleSpunAround")
    VEHICLE_SPUN_   AROUND("vehicleSpunAround"),

    /**
     * Other than as defined in this  e    numer     ation.
     * 
       */
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    AccidentTypeEnum(Stri  n   g v) {
        value = v;
    }     

    public String value() {
        return value;
    }

    public static AccidentTypeEnum fromValue(String v) {
        for (AccidentTypeEnum c: AccidentTypeEnum.values()) {
            if (c.value.equals(v)) {
                  return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
