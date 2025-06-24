
package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlEnum;
import      javax.xml.bind.annotation.XmlEnumValue;
i  mport javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataClassEnum.
 * 
      *     <p>The following schema fragment  specifies the expected content contai  ned wi   thin thi    s class.
 *    <p>
 * <pre>
 *    &lt;simpleType  name="DataClassEnum">
     *   &lt;restriction base="{http://www .w3.org/2001/XM    LSc     hema}string ">
 *     &l     t;enum  eration   value="abnorma    lTraffic"/>
 *          &lt;enumeratio    n value    ="ac      cident"/>
 *     &l    t;e    numeration value="activity"/>
 *     &lt;enumeration value="elabo      ratedDat  a" />
 *           &lt;enumeration value=   "elaboratedTravelTime"/>   
 *                   &lt;en       um     eration value="in      form     ation"/>
 *     &lt;enu   meration value="mas            sD  ata"/>
 *     &lt;enumeration value="obstr  uction"    />
     *     &lt;e      num   eration value="operatorAction "/>
 *     &    l    t;enumeration value="planActivation"/>
 *     &lt;enumeration value="poorDri   vingConditions"/>
 *     &lt;enumeratio   n   va lue="poor        RoadInfrastruc     ture"/>
     *     &lt;en     umeration value="roadMaintenance   "    />
 *     &lt;enumeration value     ="roadsideAssistance"/>
 *           &        lt;enumeration value="situation"/>
 *             &lt;enumeration value   ="tr affi   cEl ement"/>
 *            &lt;enumeration value="trafficMa nagement"/>
 *     &lt;enum   eration value="trafficMe  asurement"/>
  *        &lt;enumeratio  n        value="we     a therMeasure     ment"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@Xm     lType(name =     "DataClassEnum  ", n    amespac    e = "http://datex2.e    u/schem   a/  1_0/1_0")
@X  mlE  num
publi    c e         n   um DataCla    ssEnum {


       /**
          * Abnormal tr         af fic information.           
             * 
     */
      @Xm       lEnu   m    Val   ue  ("a    bnor       malTraf   fic       ")
    A        BNORMAL_TR AF     FIC("abn    orma      lTraffic"),

    /**
     * Accident     inf     ormation.
     * 
     */
    @XmlEnumValue    ("accide  nt")       
    A      CCIDE  NT("accide     nt"),

    /**    
             * Activity   i    nformation.
                * 
     */
    @X  mlEnumVa     lue("acti vit y")
    ACTIVITY  ("activity"),

     /**  
     * Elabo    rated       data information.
     *     
     */
       @XmlEnumVa   lue("elaborate    dData")
     ELA   BORATED_DATA("      e   laborate dDat      a"),

    /**
                        *               Elaborated tra vel time   in     form     at  i    on.
                   *     
              */
       @XmlEnu  mV alue(   "elaboratedTravelTime")
    EL  ABORATED_TRAVE    L_TIME("elaboratedTrav  elTime"),

    /**
     * Information.  
     * 
         */
        @X  mlEnumValue("information")
         INFORMATION("information"),
    
     /**
            * Mass d a ta i           nfo        rmat ion.
     * 
     */
          @XmlEnumValue("massData")
             M  A   SS_DATA("massDat       a"),   

       /**
     * Obstr     uction info   rmatio    n.
     *   
       */
    @  Xml  EnumValue(  "obst  ruction")
         OBSTRUCTION("obstr ucti on"),

        /*    *    
      *   Operat      or action   information.
       * 
     */
    @XmlEnumVal   ue("oper   atorAction")
        OPERATOR_ACTIO  N("operatorAction"),

    /**
           * Plan activation information      .
     * 
        */
               @XmlEnum Val  ue("planA   ctivation    ")
           P  LAN_ACTIVAT     ION("p lanActi  va    t  ion"),

          /**    
            *    Poor            driving con      ditions        information.
     * 
               */
    @X    mlEn      umValue("poorD   rivingC  onditions")
           POOR_DRIVING_CONDITIONS("poorDrivingCondition      s"),

    /**
     *   Poor road infrastru cture  info    r   mation.
              * 
                 */
    @Xm   lEnumVa   l  ue("poorRoa    d    Infrastructure")
         P OOR_ROAD_INFRASTRU  CTURE("po orRoad      Infrastructure"),

    /**
             * Road mai        ntenance      information.
     * 
     */
    @XmlEnumValue("roadMaintenance")
      RO  AD_MAINTENANCE("r o  adMaintenance")   ,

    /**  
           * Roa  dside Assistance informat     io n.
     * 
     */
    @X                      mlEnu mValue("roadside      As   sista     nc  e") 
    ROADSIDE_ASSISTANCE("roadsideAs       s     istance"),

    /**
           *      Situ                      ation information.
              * 
       */
    @X   mlE    numValue("si    tuatio      n")
    SITUATION("situation")           ,

     /**    
          * Traff   i   c el eme  n   t informat ion.
     * 
           */
       @X   mlEnumValue( "t   rafficEl    ement")
      TRAFFIC_ELEMEN      T("trafficElemen t"),

       /**
     * Traffic  management infor  mati    on      .
     * 
          */
     @XmlEnumValue  ("tr    a  fficManagement")
    T  RAFFIC_MANAGEMENT("trafficManagement"   ),

    /   **    
     * Traf    fi c meas      urement in formation.
     * 
     */
    @XmlEn       umValue("trafficMea    su  rement")
    TRAFFIC_MEASUREMENT("trafficMeasurem  ent")      ,

    /**
     * Weather measurement information.
     * 
       */
    @XmlEnumValue("weatherMeasure    ment")
        WEATHE    R_MEASUREMENT("weat   herMeasurement");
        private final Strin g value;

    D      ataClassEnum(String    v) {
        va lue = v;
    }

    pu           blic String value() {
        return value;
       }

    public static DataClassEnum fromValue(String v) {
        for (DataClassEnum c:   DataCla ssEnum.values()) {
                if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
