










package com.transportation.SIRI_IL.SOAP;








import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;






















/**
 * <p>Java class for AccidentCauseEnum.





 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>




 * &lt;simpleType name="AccidentCauseEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="avoidanceOfObstacles"/>

 *     &lt;enumeration value="driverDistraction"/>



 *     &lt;enumeration value="driverDrugAbuse"/>


 *     &lt;enumeration value="driverIllness"/>
 *     &lt;enumeration value="exceedingSpeedsLimits"/>
 *     &lt;enumeration value="excessAlcohol"/>
 *     &lt;enumeration value="excessiveDriverTiredness"/>
 *     &lt;enumeration value="impermissibleManoeuvre"/>



 *     &lt;enumeration value="limitedVisibility"/>
 *     &lt;enumeration value="notKeepingASafeDistance"/>
 *     &lt;enumeration value="onTheWrongSideOfTheRoad"/>
 *     &lt;enumeration value="pedestrianInRoad"/>




 *     &lt;enumeration value="poorLaneAdherence"/>




 *     &lt;enumeration value="poorMergeEntryOrExitJudgement"/>


 *     &lt;enumeration value="poorRoadSurfaceCondition"/>
 *     &lt;enumeration value="poorSurfaceAdherence"/>
 *     &lt;enumeration value="undisclosed"/>
 *     &lt;enumeration value="unknown"/>
 *     &lt;enumeration value="vehicleFailure"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>








 * 






































 */
@XmlType(name = "AccidentCauseEnum", namespace = "http://datex2.eu/schema/1_0/1_0")
@XmlEnum
public enum AccidentCauseEnum {



    /**
     * Avoidance of obstacles on the roadway.
     * 
     */
    @XmlEnumValue("avoidanceOfObstacles")
    AVOIDANCE_OF_OBSTACLES("avoidanceOfObstacles"),






    /**
     * Driver distraction.
     * 








     */









    @XmlEnumValue("driverDistraction")
    DRIVER_DISTRACTION("driverDistraction"),






    /**
     * Driver under the influence of drugs.




     * 





     */
    @XmlEnumValue("driverDrugAbuse")






    DRIVER_DRUG_ABUSE("driverDrugAbuse"),

    /**
     * Driver illness.



     * 



     */


    @XmlEnumValue("driverIllness")


    DRIVER_ILLNESS("driverIllness"),




    /**

     * Loss of vehicle control due to excessive vehicle speed.
     * 
     */
    @XmlEnumValue("exceedingSpeedsLimits")
    EXCEEDING_SPEEDS_LIMITS("exceedingSpeedsLimits"),








    /**
     * Driver abilities reduced due to driving under the influence of alcohol.  Alcohol levels above nationally accepted limit. 



     * 
     */
    @XmlEnumValue("excessAlcohol")
    EXCESS_ALCOHOL("excessAlcohol"),

    /**






     * Excessive tiredness of the driver.
     * 
     */
    @XmlEnumValue("excessiveDriverTiredness")
    EXCESSIVE_DRIVER_TIREDNESS("excessiveDriverTiredness"),

    /**





     * A driving manoeuvre which was not permitted.







     * 

     */
    @XmlEnumValue("impermissibleManoeuvre")
    IMPERMISSIBLE_MANOEUVRE("impermissibleManoeuvre"),

    /**









     * Limited or impared visibility.





     * 
     */
























    @XmlEnumValue("limitedVisibility")
    LIMITED_VISIBILITY("limitedVisibility"),








    /**


     * Not keeping a safe distance from the vehicle infront.




     * 
     */
    @XmlEnumValue("notKeepingASafeDistance")
    NOT_KEEPING_A_SAFE_DISTANCE("notKeepingASafeDistance"),

    /**
     * Driving on the wrong side of the road.
     * 
     */






    @XmlEnumValue("onTheWrongSideOfTheRoad")









    ON_THE_WRONG_SIDE_OF_THE_ROAD("onTheWrongSideOfTheRoad"),


    /**
     * Pedestrian in the roadway.







     * 
     */

    @XmlEnumValue("pedestrianInRoad")



    PEDESTRIAN_IN_ROAD("pedestrianInRoad"),





    /**
     * Not keeping to lane.
     * 


     */







    @XmlEnumValue("poorLaneAdherence")



    POOR_LANE_ADHERENCE("poorLaneAdherence"),





    /**

     * Poor judgement when merging at an entry or exit point of a carriageway or junction.


     * 
     */




    @XmlEnumValue("poorMergeEntryOrExitJudgement")


    POOR_MERGE_ENTRY_OR_EXIT_JUDGEMENT("poorMergeEntryOrExitJudgement"),

    /**
     * Poor road surface condition.
     * 




     */
    @XmlEnumValue("poorRoadSurfaceCondition")



    POOR_ROAD_SURFACE_CONDITION("poorRoadSurfaceCondition"),

    /**






     * Poor road surface adherence.
     * 
     */

    @XmlEnumValue("poorSurfaceAdherence")
    POOR_SURFACE_ADHERENCE("poorSurfaceAdherence"),

    /**
     * Undisclosed cause.
     * 
     */







    @XmlEnumValue("undisclosed")
    UNDISCLOSED("undisclosed"),

    /**
     * Unknown cause.
     * 
     */
    @XmlEnumValue("unknown")
    UNKNOWN("unknown"),



    /**
     * Malfunction or failure of vehicle function.
     * 
     */





    @XmlEnumValue("vehicleFailure")
    VEHICLE_FAILURE("vehicleFailure"),

    /**
     * Other than as defined in this enumeration.
     * 
     */
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    AccidentCauseEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccidentCauseEnum fromValue(String v) {
        for (AccidentCauseEnum c: AccidentCauseEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
