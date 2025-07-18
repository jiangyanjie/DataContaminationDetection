







package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlEnum;


import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;









/**
 * <p>Java class for AccessibilityFeatureEnumeration.
 * 




 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>


 * <pre>



 * &lt;simpleType name="AccessibilityFeatureEnumeration">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="lift"/>
 *     &lt;enumeration value="stairs"/>
 *     &lt;enumeration value="seriesOfStairs"/>
 *     &lt;enumeration value="escalator"/>
 *     &lt;enumeration value="travelator"/>
 *     &lt;enumeration value="ramp"/>



 *     &lt;enumeration value="shuttle"/>









 *     &lt;enumeration value="barrier"/>
 *     &lt;enumeration value="narrowEntrance"/>




 *     &lt;enumeration value="confinedSpace"/>






 *     &lt;enumeration value="queueManagement"/>
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="unknown"/>









 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccessibilityFeatureEnumeration", namespace = "http://www.ifopt.org.uk/ifopt")



@XmlEnum



public enum AccessibilityFeatureEnumeration {










    @XmlEnumValue("lift")


    LIFT("lift"),


    @XmlEnumValue("stairs")



    STAIRS("stairs"),
    @XmlEnumValue("seriesOfStairs")
    SERIES_OF_STAIRS("seriesOfStairs"),








    @XmlEnumValue("escalator")


    ESCALATOR("escalator"),





    @XmlEnumValue("travelator")
    TRAVELATOR("travelator"),







    @XmlEnumValue("ramp")
    RAMP("ramp"),





    @XmlEnumValue("shuttle")
    SHUTTLE("shuttle"),





    @XmlEnumValue("barrier")
    BARRIER("barrier"),
    @XmlEnumValue("narrowEntrance")
    NARROW_ENTRANCE("narrowEntrance"),
    @XmlEnumValue("confinedSpace")
    CONFINED_SPACE("confinedSpace"),


    @XmlEnumValue("queueManagement")
    QUEUE_MANAGEMENT("queueManagement"),
    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("unknown")
    UNKNOWN("unknown"),


    @XmlEnumValue("other")







    OTHER("other");
    private final String value;

    AccessibilityFeatureEnumeration(String v) {
        value = v;
    }

    public String value() {
        return value;
    }


    public static AccessibilityFeatureEnumeration fromValue(String v) {


        for (AccessibilityFeatureEnumeration c: AccessibilityFeatureEnumeration.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
