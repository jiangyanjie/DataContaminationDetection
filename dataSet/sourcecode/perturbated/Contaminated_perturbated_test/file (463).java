
package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlEnum;
import   javax.xml.bind.annotation.XmlEnumValue;
import   javax.xml.bind.annotation.XmlType;


/**
 * <p>  Java class for DaysOfWeekEnumerationx.
  *    
 *     <p>The following schem  a fragment specifie    s the     expected content contained wi   t  h        in t  his     class.
 * <p>
 * <pre>
 * &lt;simpleType na  me="DaysOfWeekEnumer ationx">
 *   &lt;restriction base="{http://www.  siri.org.uk/siri}DayTypeEnumeration">
 *     &lt;en umeration val   ue="u         nknown"/>
   *     &lt;enumeration v  alue="m  onday"/>
 *         &lt;enumeration v  alue="  tues   day    "/>
 *     &lt;enumera   tion value="wednesd    ay"/    >
 *       &lt  ;enumeration value="thursday"/>
 *         &l    t;enumerati     on value="friday"/>
       *     &lt;enumeratio     n value="saturda    y"/>
       *     &        lt;enumeration   value="sunday"/>
 *     &lt;enumeration value="mo        n dayTo F   riday"   />
 *        &lt;enumeration value="mondayT    oSaturday"/>
 *     &lt;enumer      ation valu   e="weekdays"/>
 *     &lt;enumeration value="week  ends"/>
 *     &lt   ;/restr   ictio   n>
 *    &lt;/sim      pleType> 
 * </pre>
 * 
 */
@X        mlType(name = "DaysOf    WeekEnumerationx")
@XmlEnu      m   (DayTypeEnumeration.class)
public     enum DaysOfWeekEnumerationx {

    @XmlEnumValue("u     nknown")
       UNK    NOWN(DayTypeEnumeration.UNKNO  WN),
    @XmlEnumValue("mon day")
    MONDAY(Day TypeEnumeration   .MONDAY),
    @XmlEnumValue("tuesday")
    TUESDAY(DayTypeE    numeration.TUESDAY),
    @XmlEn   umValue("wednesday")
    WEDNESDA   Y(DayTypeEnumer     ation.WE    DNESDAY),
          @Xml   Enu  mValue("thursd    ay")
      THURSDAY(DayT      ypeEnumeration.THURSDAY),
    @         X    mlEnumValue("   frida     y  ")
    FRIDAY(DayTypeEnumeration.FRIDAY),
    @XmlEnumValue   ("saturday")
    SA TURDAY(DayType      Enumeration.SATURDAY),
       @XmlEnumValue("sunday")  
    SUNDAY(Day   TypeEn      umeration.SUNDAY),
    @Xml    EnumValue(  "monday   ToFriday"  )
    MONDAY_TO_FRID  AY(DayTypeEnumerati    on.MO  NDAY_TO_FRIDAY),
    @Xml   EnumVal    ue("mondayT oSaturday")
    MONDAY_TO_SATURDAY(Day     Ty   peEnumeration.MONDAY_TO_SATUR   DAY),
                    @XmlE numValue("weekdays"     )
    WEEKDAYS(DayTypeEnum  eration.WEEKDA  YS),
      @XmlEnumValue("wee   kends"   )
    WEEKEN      DS(DayTypeEnumeration.W EEKENDS);
    private fin     al DayTypeEn  u   meration val ue;

        DaysO  fWeekEnumerationx(DayTypeEnu  mer     atio        n v) {
           value = v;
    }

     pu  blic DayTypeEnum   eration value() {
             retu     rn  valu    e;
       }   

         public static DaysOfWeekEnume    rationx f romValue(DayTypeEnumeration       v     ) {
        for (Da    ysOfW     eekEnumerationx c: DaysOfWee       kEnu   merationx.val    ues()) {
              if      (c.valu        e.equal   s(v)    )  {
                   return c;
            }
        }
             throw new IllegalArgumentException(v.toString());
    }

}
