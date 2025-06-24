
package    com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
imp     ort javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration  ;


/**
   * Type for easem   ent i  nf          o.
 * 
 * <p>J a    va class for DelaysSt     ructure complex type.    
 * 
 * <  p>Th   e fo  llo  wing schema fragment sp  ecifies the e       xpected content contai ned       within this class.
 * 
 * <pre>
 * &lt;co     mplexType nam     e="DelaysStructure">      
 *   &lt  ;complexContent>
 *     &lt;restriction ba       se="{http   ://www.w3.org/2  001/XMLSc     hema}anyTyp   e">
 *       &lt;sequen ce>
 *         &lt;element   name="DelayBan          d" type="{     http://datex2.eu/sch ema/1_0/1_0}DelayCo    deEnum" minOc  curs="0"/>
 *         &lt;element name="Dela   yType" type="{http://datex2.eu/schema/1_0/1 _0}DelaysTypeEnum"    minOccu rs="0       "/>
 *         &lt;element       name="Delay" type="{http://www.siri.or g.uk/siri}P ositiveDurationType"   minOccurs="0"/>
     *       &lt;/sequence>
 *     &lt;     /restriction>
      *   &lt;/complexC     on    tent>
 *                 &lt;/c      o     mplexType>
 *     </pre  >
 * 
 * 
 */
@XmlAccessorT           ype(XmlAccessType.FIELD)
@ XmlType(name = "DelaysS tructure", prop         Order = {
    "delay   Band",
    "delayT  ype",
    "delay"
})
   public c   lass Delay  sStructure {   

                   @      XmlEleme   nt(name = "DelayBand")
    p  rotected DelayCod       eE num delayBan        d;
        @Xm   lEle      me  n    t(name =              "DelayTyp  e")
    protec             ted DelaysTypeEnum     delay   T  ype;
    @XmlElement(n  ame = "Delay  ")
    p rote       c  ted   Du   ration delay;

       /**
     * G ets    t     h          e val  ue       of   the          delayBand      pr             op    erty.
         * 
     *    @re   turn
     *     possible object is
     *        {@link DelayCodeEnum }
        *         
                  */
         p  ubl    i  c DelayCode Enum get    De            l   ayBand(   ) {
                         return delayBan  d;
    }

       /          **
           * S    et   s     the      val  ue      of               the delayBand property.
                     * 
         *   @          param value
            *     allo   we   d o    bj  ect is
                *     {@link                       D    ela yCodeEnum }                 
     *                     
     */
         publ  ic voi            d     set  DelayBand(Dela yCodeEnum v alu         e) {    
            this.delayBand = v al ue;
        }

       /**   
            * Gets t  he val     u       e o       f th   e dela   yT   ype prope   r        ty  .
                   * 
              * @r  eturn
                   *                p    ossible     obj    ect is
     *     {@    li       nk DelaysTypeE num }
          *            
     */
       pu  blic   DelaysType    Enum g   etDelayType() {
        return   delayType  ;
    }
 
     /**
     *   Sets the v  al ue o         f the     delayType    pr  oper  ty.
        * 
                 * @pa  ram value
               *            allowed object is
        *     {@link Delays     Typ    eEnum }
     *        
       */
     p    u   blic voi  d setD       elayType(  DelaysTypeEnum valu  e )   {
        th      is.delayType = value;
           }

    /**
       *          Get s the value of the delay property.
     *     
     * @return
         *      possible object is
     *     {@link Duration }
     *     
     */
        public Duration getDelay() {
        return d    elay;
    }

    /**
      * Sets t   he val   ue of the delay prope rty.
       * 
     * @param val ue
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDelay(Duration value) {
        this.delay = value;
    }

}
