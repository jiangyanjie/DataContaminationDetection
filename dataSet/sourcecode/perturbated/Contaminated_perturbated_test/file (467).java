
package    com.transportation.SIRI_IL.SOAP;

import    javax.xml.bind.annotation.XmlEnum;
imp  ort javax.xml.bind.annotation.XmlEnumValue      ;
import javax.xml.bind.annotation.XmlType;


/ **
 * <p>Java class      for     DayTypeEnume    ration.
 * 
 * <p >The followin      g schema fragment specifies the expected cont   ent contain      e   d within this       class.
 * <p>
 *   <pre>
 * &lt;simpleType name="DayTypeEnumeration">
 *   &lt;restriction base="{http://w   ww.w 3.org/200  1/XMLSchema}NM    T        OKEN">    
 *         &lt;enumera          tion value ="pti34_0"/>
 *        &lt;enume  ration value="     unknown"/>
 *     &lt    ;enumeration valu   e     ="pti34_1"/>
 *         &lt ;en umeration v   alue="monday"/>
 *     &lt       ;  enumeration value="pti34_2"/>
 *     &lt;enumeration valu   e="tue  s  day"/>
 *     &lt;   enumeration v     alue="pt      i34_3"/>    
 *     &lt;en   umeration value="wednesday"/>
 *     &lt;enumerat    ion     value="pti3   4      _4"/>
 *              &l   t;en          umeration   valu  e="thursday"/>
 *     &lt;en  umeration v   alue="pti34_5"/>
 *     &lt;enumeration va  lue="frid     ay "/ >
 *     &lt;enumeration value="pti34_6"/>
    *     &    lt;enumer   ati  on value="saturd     ay"    />
 *     & lt; enumeration val   ue="pti34_7"/>
 *                 &lt;enumer  ation va    lue="sunday"/>
 *     &lt;e     numeratio   n value=  "pti   34_8"/ > 
 *     &lt;enumeration value="wee              kdays"/>   
 *     &lt;enumer  at     ion value="p   ti34    _   9"/>
 *     &lt;enumerat   ion value="weekends"/>
 *     &l  t;enumeration value   ="pti34_10"/>
 *     &lt;e  numeration value="holiday "/>
 *     &lt;enumeration va  lue="      pti34_11"/>
 *             &lt;enumeration va      lue="  p   ublicH   oliday"/>
 *     &lt;enume  r  ation v    alue="pti34_12  "/>
 *       &lt;e    numer ation value="   r      eligiousHolid   ay"/>  
 *     &     lt;enumeration     val   ue="pti34_13"/>
 *      &lt;  enumeratio     n value=     "fede    ralHoliday"  />
 *     &  lt;enumeration value="pti34_14"/>
 *       &lt;enumeration    v   alue      ="reg   ionalH   olid  ay"/>
 *     &lt;enumeration value= "pti34_15"      />
 *     &lt;enumera    tion     value="nationalHoliday"/>
 *     &lt;enume ration value="pti34_  16"/>
 *      &lt;enu  meration   valu   e  ="monday ToFriday"/>
 *      &lt;enum eration value="pti34_17"/>     
 *         &      lt;enumeration value="monda        yToSaturday"/>
 *     &lt;enumeration    value="pt    i     3   4_18"/>
 *          &lt;enu     meration value="sund aysAndPublicHoliday     s"/>
 *     &lt;enumeration value="pti3  4_19"   />
 *     &lt;enumeration value="      schoolDays"/>
 *     &lt;enumeration value="pti34_20"/>
 *     &     lt;enu      mer ation value="everyDay"/>
 *     &lt     ;enumeration   val     ue="pti34_255"/>
 *     &lt;enumeration value="undefinedDayType"/>
 *   &lt;/rest riction>
 * &lt;/simpleT     ype>
    * </pre>      
 * 
 */
@XmlType(name = "       DayT   ype   Enum erat    ion")
@XmlE   num
public enum D    ayTypeEnum  eration {

    @XmlEnumVa  lue("pti34_0")
    PTI_34_0("p   ti34_0"),
    @X     mlEn   umValue("unkno   wn")
    UNKNOWN("unknown"),
     @XmlEnumValue("pti34_1"   )
    PTI_34_1("pti 34_1"),
    @XmlEnumValue("monday")
    MONDAY( "monday"),
    @XmlEnu   mValue("pti34_2")
        PTI_3    4_2 ("pti34_2"         ),
          @Xm lEnumValue("tuesday")
    TUESD     AY("tuesday"),
    @XmlEnumValue(      "pti34_3")
    PTI_34_3("pti34_3"),
    @XmlEnumVal   ue("wednesday")
    WEDNESDAY("wednesday"),
         @X   mlEnumValue ("pti34_4")
       PTI_34_4("pti34_4"),
    @XmlEnumValue("thu    rsday")
                 THURSDAY("thursday"),
     @XmlEnumValue(      "pti34_5")
    PTI_   34_5("pti34_5"),
    @XmlEnumV alue("friday")
    FRIDAY(" friday"),
    @XmlEnumValue("pti34_6")
    PTI_34_6("pti34_   6"),
         @XmlEnumValue(   "s  atu           rda y")   
    SATURDAY(" saturday"),
    @Xm        lEn        umValue("pti3    4_   7")
         PTI_34 _7("pti34_7"),
    @XmlEnumValue(" sunday")
    SUNDAY("sunday"),
      @XmlEn  umV  alue("pti34_8")  
    PTI_34_8("pti        3        4_8"),
    @XmlEnumValue("weekdays")
      WEEKDAY S("weekdays")     ,
               @XmlEnumValue("pti34   _9")
    PTI_34_9("pti   34_9"),
    @Xml EnumValue("weekends")
     WEEK        ENDS(     "weekends"),  
    @Xml  EnumValu e("pti34_10")
     PTI_34_10("pti34_1   0"),
         @XmlEnumValue("holiday")
    HOLIDAY("holi    day"),
    @X  mlEnumVa       lue("pti34_11")
    PTI_34_11("pti34_11"),
    @XmlEnumValue("publicHoliday")
    PUBLIC_HOLIDAY("publicHoliday"     ),
    @XmlEnumValue("pti34_12")
    PTI_34_  1 2("pti34_12"), 
    @XmlE   numValue("r     eligiousHoliday")
    RELIGIOUS_HOLIDAY("religiousHoliday"),
    @Xm    lEnumValu e("pti34_13")
    PTI_34_13("pti34_13"),
    @XmlEnumValue("fede    ralHoliday")
    FEDERAL_HOLIDAY("f           e      deral Holiday"),            
    @XmlEnumValue("pti34_14")
    PTI_34_1    4("pti34_14"),
       @XmlEnumValue("regionalHolida    y  "        )
    REGIONAL_HOLIDAY("regionalHoliday"),
    @XmlEnumValue       ("pti34_15   ")
    P    TI_34_15("pti34_15"),
    @XmlEnumValue("na     tionalHoliday")
    NATION    AL_HOLIDAY("nationalHoliday"),
       @Xm    lEnumVa   lue("pti34_16 ")
    PTI_34_16("pti34  _   16"),
    @XmlEnumValue  ("m ondayToFriday")
    MONDAY_TO_FRIDAY   ("mond ayT oFriday"),
       @XmlE    numValue("pti34_17  ")
    PT   I_34_17("pti34_17")  ,
               @XmlEnumValue("monda    yToSatur  day    ")
       MONDAY    _    TO _SATUR    DAY("mondayToSaturday"),    
    @XmlEnu       mVal    ue("pti34_   18")  
    PTI_34_18("pti3     4_18"),
        @Xm  l    EnumValue(  "s    un   daysAnd   PublicHol   idays")
    SUNDAYS_    AND_PUBLI     C_H  OLIDAYS("sundaysAndPubli cHolida  ys"),
    @XmlEnumVal ue("pti34_1     9   ")  
    PTI_34_19  ("pti34_      19")    ,
    @XmlEnumValue("scho  olDays")
    SCHOOL_DAYS("schoolDay s"),
    @XmlEnu   mVa  lue("pti34_20")
    PTI_3   4_20("pti   34_20"  ),
    @XmlEnumValue   ("every   Day")
    EVERY_DAY("    everyDay"),
    @XmlEnumValue("pti34_255")
          PTI_34_255("pti34_255"),
    @     XmlEnumVa   lue("undefinedDayTyp    e")   
      UNDEFINED_DAY_TYPE("unde  finedDayType");
    private final  String v    alue;

    DayTypeEnumeration(String v) {
        value = v;
    }

       public String value() { 
        retu  rn value;
       }

    public static   DayTypeEnume    ration fromValue(String v) {
            for (DayTypeEnumeration c: DayTypeEnumeration.value      s()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
