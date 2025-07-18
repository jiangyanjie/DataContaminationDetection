



package com.transportation.SIRI_IL.SOAP;






import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;













/**






 * <p>Java class for AccessSpaceTypeEnumeration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>


 * <pre>



 * &lt;simpleType name="AccessSpaceTypeEnumeration">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">



 *     &lt;enumeration value="concours"/>








 *     &lt;enumeration value="bookingHall"/>
 *     &lt;enumeration value="forecourt"/>



 *     &lt;enumeration value="underpass"/>





 *     &lt;enumeration value="overpass"/>
 *     &lt;enumeration value="passage"/>
 *     &lt;enumeration value="passageSection"/>



 *     &lt;enumeration value="gallery"/>



 *     &lt;enumeration value="garage"/>


 *     &lt;enumeration value="shop"/>
 *     &lt;enumeration value="waitingRoom"/>

 *     &lt;enumeration value="restaurant"/>
 *     &lt;enumeration value="other"/>

 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>




 * 





 */
@XmlType(name = "AccessSpaceTypeEnumeration", namespace = "http://www.ifopt.org.uk/ifopt")
@XmlEnum
public enum AccessSpaceTypeEnumeration {









    @XmlEnumValue("concours")
    CONCOURS("concours"),















    @XmlEnumValue("bookingHall")
    BOOKING_HALL("bookingHall"),
    @XmlEnumValue("forecourt")

    FORECOURT("forecourt"),


    @XmlEnumValue("underpass")



    UNDERPASS("underpass"),
    @XmlEnumValue("overpass")
    OVERPASS("overpass"),
    @XmlEnumValue("passage")
    PASSAGE("passage"),
    @XmlEnumValue("passageSection")
    PASSAGE_SECTION("passageSection"),
    @XmlEnumValue("gallery")
    GALLERY("gallery"),
    @XmlEnumValue("garage")



    GARAGE("garage"),
    @XmlEnumValue("shop")
    SHOP("shop"),




    @XmlEnumValue("waitingRoom")


    WAITING_ROOM("waitingRoom"),



    @XmlEnumValue("restaurant")





    RESTAURANT("restaurant"),
    @XmlEnumValue("other")




    OTHER("other");



    private final String value;










    AccessSpaceTypeEnumeration(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccessSpaceTypeEnumeration fromValue(String v) {
        for (AccessSpaceTypeEnumeration c: AccessSpaceTypeEnumeration.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
