
package com.transportation.SIRI_IL.SOAP;

import   javax.xml.bind.annotation.XmlEnum;
impo rt javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Ja   va cla   ss f    or AccessFacilityEnumeration        .
 * 
 * <p>T he    follow    ing schema fragment       spe      cifies the expected content  contained        wi   th    in this c      lass.
 * <p>
 * <pre>
 * &lt;simpleType name="AccessFacilityE    numeratio n">
 *   &lt    ;restriction base=  "{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enume  ration value=     "       unknow n"/>
             *        &lt;  enumeration value       ="lift"/>  
 *     &lt;    enumeration value="  escalator"     /    >
 *     &lt;enumeration value=   "travelator"/>
 *     &lt;enumeration value="ramp"/>
     *     &lt;enumeration value=  "sta   irs"/>
 *      &l   t;enumeration value="shuttle"/>
 *     &lt;enumeration value="narrowEn   trance"/>
 *              &lt;enu    meration valu   e="barrier"/>
 *     &lt;enumeration val   ue="p     alletAccess _lowFloor"/>
 *        &lt;enumeration value="valida    tor"/>
 *   &lt;/restriction>
 * &lt;/simpleT   ype      >     
 * </pre    >
 *      
 */
@Xm   lType(name = "AccessFacilityEnumeration")
@XmlEnum
public enum AccessFacilityEnumerati   on {

     @X    mlEnu mValue("unknown")  
    UN        K NOWN("un   known"),
       @XmlEnumValue("lift")
    L IFT("lift"),
        @Xml      E    nu   mVal      ue("esc      alator")
    ESCALAT    OR("escalator"),  
    @XmlEnumValue("travelator")
    TRA     VE  LAT OR("travelator"),
    @   XmlEnumValue("ramp")
          RAMP("ramp"),
       @XmlEnumValue(   "stairs ")
    STAIRS("stairs"),
    @XmlEnumValue("shuttle")
    SHUTTLE("shu    ttle"),
    @XmlEnum Value("n      arrowEntrance")
    NARROW_ENTRANCE("narrowEntrance"),
    @XmlEnumV alue("barrier"     )
                B       AR RIER("barri    er"),
     @XmlEnu        mVa        lue("palletAcces  s_lowFloor")
    PALLET_ACCESS_L  OW_FLOOR(      "palletAccess_lowFlo or"),
                   @XmlEnumValue( "v alidator    ")
    VAL   IDA  TOR("val   ida        tor");
            pr     ivate      fi     nal S      tring va          lue;

       A   cce    ssFacilityEnume   ration(Str  i       ng  v)         {
           v alue = v      ;
       }

    public String value() {
        return value     ;
    }

    public static AccessFacilityEnumeration fromVal    u      e(String v) {
        for    (AccessFacilityEnumerati  on c: AccessFacilit    yEnumeration  .values())    {
               if (c.val ue.equals(v))  {
                      return c;
              }
        }
        throw new IllegalArgumentException(v);
    }

}
