
package com.transportation.SIRI_IL.SOAP;

import    java.util.ArrayList;
import     java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
im  port javax.xml.bind.annotation.XmlElement;
  import javax.xml.bind.annotation.XmlT    ype;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import   javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/** 
 * Type for Delive   r  y for Connect   ion Pr        otection.
 * 
 * <p>Jav     a class for ConnectionTimetableDeliverySt     ructure complex type.
 * 
 *     <p>T   he f  ollowing schem       a fragment      specifies the expected content contained within this c    l     ass.
 * 
 * <        pre>
 * &lt;complexType na    me="ConnectionTi metableDeliverySt ructure">
   *   &lt;com     plexContent>
 *     &lt;ex      tensi         on base="{ht    tp://www.siri.org.uk/siri}   AbstractSe  rvic      eDeliveryStructure     ">   
 *       &  lt;sequence  >
 *         &lt;group ref="{http://www.siri.org.uk/siri}ConnectionTimetableP      a  y    loadGroup"/   >
 *                 &lt;e    lement ref="{http://www.siri.org    .uk/si    ri    }  Exte     nsions" minOccurs="  0"/>
 *               &l   t;/sequence    >
 *       &lt;attribute name="version" use="requir  ed" type="{http://www.siri.org.uk/s    iri}V     ersionString" fi  xed="1      .3" />
 *       &lt;/extension>
   *      &lt;/complexContent>
 *    &lt;/complexType>
 * </pre>
 * 
     * 
 */
@XmlAccessorType(XmlAccessType.  FIELD)
@XmlType(name = "ConnectionTimetableDelivery   Str    ucture", propOrder = {
    "timetable    dFeederArr ival",
    "timetabledFeederArrivalCance       llation",
    "extensions"
})
public class ConnectionT  imetableD            eli    veryStructure
    extends AbstractServiceDeliveryS      tructure
{

    @XmlElem ent(name = "TimetabledFeede    rArrival")    
    protected List<TimetabledFeederArrivalStructure> timetabledFee   derA    rrival;
    @XmlElement(name = "TimetabledFe  ederArrivalCancellation")
    protected List<Timet abledFeederArrivalC     ancellationStructure> timetabledFeeder  ArrivalCancellation    ;
        @XmlElement(na      m      e = "Extensions")    
          protec  ted Exten    s    ions    S     tr   u cture extens      ions;
        @Xm      lAttri   bute(name = "version", required     = true)
    @XmlJavaTypeAd   apter(Col lapsedSt  ringAdapter   .   class    )
    protected String version;
   
    /**
     *  G  ets     the value of the timetabledFeederArrival         p     roperty.
     * 
                * <p>
     * This accessor             m   etho  d retur     ns a re    fe  rence                  to the live   list,
           * not a   sn           apshot. Th   eref ore       any         modif  ic    ati    on     you make           to the   
       *   ret   urned      list will be present inside t      he JAX        B ob     ject  .
     * This is why there is no       t     a <CODE  >set</CO          DE>     m  eth             od for  the time          tabl  edFeede      rArr           ival property.
     * 
       *    <      p>
          *     For exam  ple, to add a new ite   m   ,   do as follow    s:
     * <pre>
        *    g    e  tTimet      abledFeederArriva  l().a   dd(newIt   em);
                 * </pre>
          * 
      * 
     * <p>
     * Objects of the    followin g type(s) are allo     wed in        the lis t
     * {@link TimetabledFe ederArr   ivalStructure }
           *     
     * 
     */
    public List<Timetable  dFeederAr  rivalSt     ructure      > getTimetabl    edFeeder    Arr    iva     l()    {
                 i  f (timeta      b  l    edF          eederArrival == nu         ll) {
             t   imeta bl        edFeederA    rrival = n     ew ArrayLi st   <Tim  e      t   abledFeederArrival    Structure  >();
          }
             return this.tim    etab    ledFeederA      rriva   l     ;
       }   

      /**
     * Gets   the value of the timetabledFeederArriva      lCancellation propert      y.
                     *  
     * <p>
     *     This access      or me   thod returns a    refe rence to the live l ist,
            * not a      snapshot. Therefore       an       y modification     you make     to t    he
     *       returned list     wil   l be present inside the    JAXB       object.   
          *       T his is why there is         not    a <CODE>set</  C                  O D    E> method   f      o   r  the timetabledFeed  erArrivalCan c  ella   tion property.
                         *  
     * <p>     
     * For example, t     o add a n   ew item,          do as follows:
           * <pre>
         *         getT  imetabledFeede   rArrival               Cancel     latio     n().add    (newItem);
            *  <     /pre>
        *   
            * 
           * <p>
     * O  bjec        ts of the fo  llow       i  ng type (s) ar  e        allowed in the list
     * {@link TimetabledFeederArrivalC   ancellationStruc      ture }
     * 
                      * 
             */
       public        List<  TimetabledFe   ed   erArriv   alC ance    l  lation       S t           ructure > getTimetabledF     eede    rArrivalCancellatio  n       ()    {           
        if (tim   etab       ledFeederArr   i   valC   ance        l    lat    ion == null           ) {
                timetab   le  dF   eed    e   rAr        rivalCan cellation = new ArrayLis t<Timetabled     Fe   ed    erArrivalC       an   cell ationStructure     >();
              }
              ret    urn this.timetabledFeederArrivalCance    llat    i       on;
        }     

    /** 
        * Gets the  value of    th    e extensions            proper          t    y.
          *    
     * @retu  rn
      *      possible              obje       ct is
     *     {@link E  xt    ensionsStruct       ure }    
     *     
           */
         pu     bl     ic ExtensionsSt   ructu re getExtensions() {
             return exte  ns ions;
    }
      
       /**
     * Sets the value of    the e   xtensions property.
     * 
         * @ pa   ram value
     *       allowe      d obj ect is
     *       {@link ExtensionsSt   ructure }
     *     
     */
    public   voi        d     set    Ext  ensions    (ExtensionsStructu    re value) {
        thi     s.extensions =   val  ue;
    }

    /**     
     * Gets    the v     alue   of the version property.
     * 
     *   @return
         *     possible object is
     *     {@link String }
     *         
     */
    public String getVersion() {
        if     (vers        ion == null) {
               return "1.3";
        } else {
            return version;
        }
    }

    /**
     * Sets th   e      value of the version property.
        * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String valu  e) {
        this.version = value;
    }

    @Override
    public String toString() {
    	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
