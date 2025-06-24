
package com.transportation.SIRI_IL.SOAP;

import    java.math.BigInteger;
imp    ort javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
imp   ort javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar ;


/**
 * Type for filter     for conencting journeys
 * 
 * <p>Java class    for ConnectingJourneyFilterStructure    com  p         lex type.
 * 
 * <p>The follow ing schema fragment specifies the expected content co   ntained within t    h is clas s.
 * 
 * <pre>
 * &lt;complexType name="ConnectingJou  rn  eyFilterStructure">
 *   &lt;   complexContent>
 *     &lt;restriction base="{htt   p://      www.        w    3.org/2001/X MLSchema}anyType"     >    
 *           &lt;sequence>
 *           &lt;element name="DatedVehicl eJourneyRef"   type="{http://ww   w.s     iri.org.uk/siri}Dated   VehicleJourne     y    Cod  eT   ype"/>
 *         &lt;element ref="{http://www.siri.org.uk/siri}Vis     itN    umber" minOccu   rs="0"/>
 *                  &lt;elem  ent name="TimetabledArrivalTime" typ   e=   "{http:   //www.w3.org/2001       /XMLSchema}date   Time"   /   >
 *       &lt;/sequen       ce>
 *     &lt;/restricti           on>
  *   &lt;/complexContent>
 * &lt;/comple      xType>
 * </pr    e>
 * 
 * 
 */
@XmlAccessorTyp   e(XmlAccessType     .FIELD)
@XmlType(name = "Connecting     J    ou   r   neyFilterStructu   re", propOrder = {
    "datedV  ehicleJourneyRef   ",
             "visitNumber",
    "timetabledArrival  Time"
})
public      c  lass ConnectingJourneyFilterStructure {

    @XmlE    lement(name = "Date dVehicleJourne yRe    f", required = tr   ue    )
      @XmlJavaTypeAdapter(CollapsedStringAdapter.cla    ss)       
    protected String datedVehicleJour   neyRef  ;   
      @Xm lElement(name = "V        isitNumber")
      prote     cted BigInteger visit Numbe    r;
    @X    m         lEl      em         ent(    name = "Time   tabledArrivalT     ime", r  equir   ed = true)
            @XmlSchem   a T  ype(n   ame = "d        ateTime")           
    pro   t ected       XMLGregorianCalendar timet ab       ledArr   ivalTime;

        /**
     * Gets the value of   t   h            e da    tedVe hicleJourneyRe  f pr  operty.   
            *       
        * @return
     *             possible o     bject             is
     *     {@link St    ring }
           *                 
         */  
    public           String        getDatedV           ehicleJourneyRef         () {
        return da   tedVehicl    eJ ou   rneyRef;   
    }          

    /*  *
     * Sets the value of           the datedVeh        icleJourneyR          ef property.
        *       
             * @param valu      e
                              *               allowed     obj ect is
        *     {@    link   Strin     g }
                          *         
     *  /
    p      ublic   void setDatedVehicleJ      ourn    eyR   ef(S         trin            g  val   ue )         {
               th        i     s.dat   edVehicleJou      rneyRef =      valu        e    ;
    }

        /*    *
             * Gets   the value of t he vi   sitNumber proper         ty.
       *  
      * @return
         *     pos   sible object is
       *     {@link    B  igIntege   r }
               *       
                 *         /       
             public BigIn    tege      r getVisitNumber() {
        re         tur          n visitNumbe      r;
    }

     /   **
             * Sets     the    value of t      he visi   tNumber      property.
                     * 
             * @param    value
         *     allowed ob            j  ect        is    
             *     {@          link BigInteg        er }
        *     
     */
       publ    ic voi  d se    tVisi  tNumb  er(BigInteger value) {
        th is.visitNumber   = value;   
    }

    /* *
     * Gets th     e value of the timetabledArri     v     alT    ime proper      ty.
               *   
           * @retur n
     *         possible object is
             *     {@link XMLGregor    ianCalendar }
     *      
            */
    public XMLGregorianCale     ndar getTimetabledArrivalTime() {
        return timetabledArrivalTime;
    }

    /*    *
     * Sets the value of the tim  etable   dArrivalTime property.
     * 
     * @param value
     *      allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimetabledArrivalTime(XMLGregorianCalendar value) {
        this.timetabledArrivalTime = value;
    }

}
