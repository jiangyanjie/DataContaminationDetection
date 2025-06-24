
package    com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
imp    ort javax.xml.bind.annotation.XmlAccessorType; 
import javax.xml.bind.annotation.XmlElement;
import   javax.xml.bind.annotation.XmlSchemaType;
i      mport javax.xml.bind.annotation  .XmlType;
import javax.xml.datatype.XMLGregorianCalen dar;


    /**
 *       Type for time fi                 lter for c  on encting journ          eys
 *    
 * <p>Jav      a clas     s for ConnectingTim  eFilterStru   cture complex ty        pe.
 * 
 * <p>The following sch   ema     fragment    specifies the ex  pected content contained within this class.
 * 
 * <pre>
 *  &lt;complexType name="ConnectingTimeFilter          Struct   ure">
 *        &lt;complexContent>
 *     &lt;   r estriction base="{h  ttp  :/    /www.w3.org/2001/XMLSchema}anyTyp e">
 *           &l    t;sequence>
 *         &lt;elem  ent name="LineRef" type="{http://www.siri.org.u   k/sir i}  LineRefStructure"/>
 *              &lt;element name="DirectionRef" type="{htt p://www.sir  i.org.uk/siri}DirectionRefStructur    e"/>
 *                    &lt;element name="EarliestArriva    lTime" type="{http://www.    w3.org/2001/XMLSc hema   }dateTime" minOcc    urs="0"/>
 *             &lt;element name="LatestArrivalTime" type="{http://www.w3.o      rg/2001/XMLSchema}date   Time" minO    cc urs="0"/>
 *         &lt;/s equence>
      *        &lt    ;/restr   iction>
 *   &lt;/complexConte nt>
 * &l   t;/complexType>
 * </pre>
 * 
 * 
 *  /
@XmlAccesso   rType(XmlAccessType.FIELD)
     @  XmlType(        name = "Con  nectingTim eFilterStruc    ture"  , propOrder    = {
    "lineRef",
     "dire     ctionRe   f",
          "ear  liestArri   valTim   e" ,
    "latestArrivalTime"
})
public   cla    ss ConnectingTimeFilt  erStru  cture {

    @XmlElement(na      me = "Li  neRef", required =      true     )
    protected LineRefStructure     lineRef;
           @XmlE  lement           (name = "Direct    ionRef", requir ed = true)
      protec ted Dir     ec tionR   efStru  cture directionRef   ;
    @XmlElement(name    = "EarliestArr iv   alTime")
     @XmlSchemaType(name = "dateTime")
     prot   ected XMLGreg    orianCalendar ea  rlies          tArrivalT            ime;
    @XmlElement(name =   "LatestAr     rival   Time ")
       @XmlSchemaTyp     e  (name            = "    d     ateTi   me"    )  
                 p    rotect ed XM       LGregorianCa   lendar     latestAr r     iva  l   Ti  me;
   
     /**
         *    Gets          th     e value of t       he                 l in  eRef  property  .
            * 
     * @return
          *            poss ible object is
               *     {   @l  in      k LineRefStruc     t       u   re }   
     *          
     */
             pub  li   c LineRefStructu       re ge       tLineRef() {
           return li neRe   f;
    }
     
        /**
             * Sets the  va lue of    t     he lineRef p     ro   pert  y   .
         * 
        * @param value
          *       allowed object is
     *         {@link Li    n   eRefStructur e  }
     *     
     */
        public     void setLineRef(LineRe   fSt ructur e v   alu     e) {
                   this .l in eRef    = value;
    }
  
     /**      
               * Gets t  he value of th  e directi  onRef prop     erty.
         * 
     * @return
      *        possible       o     bject   is
          *             {@l   ink Direction     RefStruc   ture }     
        *            
           */
    public Di  rec                 tionR  e   fSt     ructure getDirectionRef() {
              r   eturn direc        tionRef;
    }

    /**
                   * Sets the val   ue of    t     he  d      ire  c           ti           onR  ef pro  p    erty.    
     * 
     * @para     m value
         *              allow       ed obj  ect      is
     *                  {@link Dir     ectionRefStruc    tu     re }
            *     
      */
                 public voi    d setDirectionRef(D       irecti      on           RefStructure value) {
        this.direction    Ref =    value;
            }

    /  *     *   
      * Gets       the v    alue of the earliestA  r r ivalTime property.
              * 
     *     @retur   n
      *                          pos         sible object is
     *       {@link X    MLGreg  ori      a    nCalendar }
     *     
        */
       publi      c      XMLGreg     or ianCalendar getEar               liest    ArrivalTime() {
          return ea rl  ie     st       ArrivalTime;
    }

    /**
         *  Sets th             e   v alue o  f the earliestArrivalTime property.     
     * 
     *    @p   aram     value
     *     a   llowed object is
     *     {@link X       ML     Grego   rianCalendar }
      *     
     */    
    public   void  setEar    liestArrivalTime(XMLGregori    anCalendar value) {
        this.earliestArrivalT     ime = value;
    }

      /**
     * Gets    the value of the latestA  rr           iva lTi  me   pr    operty.
     * 
     * @r  eturn
      *     possible o bject is
     *     {@lin  k XMLGregorianCalendar }
     *     
     */  
    public XM    LGregorianCalendar getLat    estArr ivalTime() {  
          return latestArrivalTime;
         }

    /    **
     * Sets the value of the latestArrivalTime property.
     * 
           * @param value
     *     allo wed object is
     *       {@link XMLGregorianCalendar }
     *     
     */
    public void setLat   estArrivalTime(XMLGregorianCalendar value) {
        this.latestArrivalTime = value;
    }

}
