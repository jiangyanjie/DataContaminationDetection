
package com.transportation.SIRI_IL.SOAP;

import    javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**   
 * <p>Java cl   ass for DelayCode          Enum.
 * 
 *   <   p>The followin    g sche     ma fragment  sp     ecifies the expecte       d  cont   en      t contained within this    class.
 * <p>           
 * <pre>
 * &lt;simpleTyp     e name="DelayCodeEnum">
 *   &lt;res    tr          iction base="{htt   p://www.w3.org/2001/XMLSchema}string">
   *     &lt;enumeration value="delayBetweenOneHourAndThreeHours"/>
 *     &lt;enum   eration value="delayBetweenThirtyMinutesAndOn     e  Hour"/>
 *     &lt;enumeration va   lue  ="        delayBetweenThreeHoursAndSix Hours"/>    
 *     &lt;enumeration value="delayLessThanThirtyMinutes"/>
 *       &lt;enumeration v  a  lue="delayLongerThanSix   Hours   "  />
 *     &lt;enumeration value="negligible"/ >
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre    >
      * 
 */
@XmlType(n     ame = "DelayCodeEn      um", nam   espace =    "http://datex2.e   u/schem    a/1_0/1_0")    
@   XmlEnum
public enu   m Delay     CodeEnu   m {


        /**
     * Delay between on  e   hour     and three hours.
      * 
     */
    @Xm    lEnumValue("delayBetween  OneH    ourA     ndThreeHour          s")
    DELAY_B   ETWEEN_ONE_HOUR_A  ND_THREE_HOURS("delayBetwee   nOneHourAndThree  Hours")      ,

    /  * *
     * Delay      between thirty   minutes and     one ho ur.
        * 
     */
         @XmlEnumValue("delayBetweenThirtyM inutesAndOneHour")
     DELAY_BETWEEN_THIRT      Y_  MINUTES_AND_ONE_HOUR ("delayBetweenTh   irtyM      inutesAndOne Hour"),

          /**
     * Delay betw   een           three         h   ours an     d       six hours.
     * 
     *    /
    @XmlEnumValue("delayB       etweenThreeHoursAn            dSixHou  rs")
    DELAY_BETWEEN_THR  EE_HOURS_AN D_SI     X_     HOURS("delayBetweenThreeHoursAnd    SixHo urs       "),

    /**      
              * Del   ay less t    han thirty   minutes.
     * 
     *    /
            @XmlEnumValue("delayL  essThanThirtyMinutes")
    DELAY_LESS_TH   AN  _THIRTY   _M  INUT ES("delayLessThanT   hirt     yMinutes"),
   
        /**
     * Delay    longer than six h  ours.
         *     
     */
    @XmlEnumValue("del       ayLongerThanSixH     ours")
       DEL  AY_LON      GER_ THAN_SI        X_HOURS    ("   de       lay         L   o      ng      erTh anSixHo   urs"),

    /  **
     *  Neglig    ible d   ela    y.
     * 
     */
    @Xml  EnumValue("        neg     ligible")
       NEGLIGIBLE("negligible")    ;
    private final      String v           al      ue;    

    De      la  yCodeEnu       m(Str ing     v)  {
                     value = v;
    }

    public String value  () {
                  return value;
    }

    public    static De layCodeEnum fromValue(String      v) {
             for  (DelayCodeEnum c: DelayCo    deEnum.values()) {
               if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
