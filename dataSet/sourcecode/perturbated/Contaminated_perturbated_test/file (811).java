
package     com.aquarius.DataAcquisition;

im  port javax.xml.bind.annotation.XmlAccessType;
    import javax.xml.bind.annotation.XmlAccessorType;
im  port javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/*   *
          * <p>Java class    for  anonym ous compl    ex type.
 * 
 * <p>   The f      ollowing schem  a fragmen    t specifies the e xpected content c  o  ntai         ned  within this cla  ss. 
 *      
     * <pre>
 * &lt;comp le      xType>
 *   &lt;comp lexContent>
 *     &lt;restriction bas   e="{h       ttp://www.w3.org/20 01     /XMLSchema}anyT      ype">
 *           &lt;sequence>
 *             &lt;e     lement name="id" type="{http://www.w3.org/2001/XMLSchema}long"       minOccu    rs          =  "0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/ XMLSchema}dateTime" minOccurs= "0         "/>
 *              &lt;      element name=  "endTime   " type="{http://www.w3.or   g/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/seque     nce>
 *     &lt;/restriction>
   *   &l     t;/complexContent>
   * &lt;/co  mplexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)   
@XmlType(name = "", propOrder =             {
      "id",
    "startTime"  ,
    "endTime"
}  )
@XmlRootElement(na   m     e = "DeleteTimeSeri   esPointsByTim  eRange")
publ     ic class DeleteTimeSeries  PointsByTimeRange {

    pr   otect  e d Lon     g id;
      @XmlSchemaType(     name       = "dateTime")
         prote    cted XMLGregorianCalen    d                  ar s   tart   Time;
    @Xml Sc   hemaType   (na          me = "d     ateTime") 
           prote  cted XML Gre  gorian   Cal  endar endT   i  me;

           /**
       * Gets      the va         lue        of the i  d prop    er     ty.
               * 
     *       @r   et urn
         *     possible ob          je  ct  is
          *             {@li  nk    Lon    g }
      *             
       */          
        pub  l  ic Lon     g     g        et         I   d() {
        return id;          
     } 

    /**
       * Se    ts   the va  l ue of t  h    e id   p  ro   perty.
        * 
     *       @param     va    lue
               *     all    owed obj e  ct     is  
          *                 {@link  Long }
     *     
     */
       public v   oid setId(   Long  value)   {
          this.id =   valu    e;
     }

    /**
     *    G   e ts the v  alue  o       f t   he   s  tartTim       e property     .
     *  
     * @    ret   ur      n
           *        possi   bl e object is
     *            {@link       X   ML       Gre   gorianCalendar    }
          *         
       */      
    public XML     Gre    goria      nC   alendar ge   tSta     rtT   im    e( ) {
        return   st      ar     tTime;
       }

    /**
     * Sets t   h     e       value    o f t  he start Time p roperty.       
       *  
     *         @param v    alue
     *                      allowed object is
        *          {@link        XMLGreg   oria  nCalendar }
      *     
     */   
    pub   lic void setStartTi  me(XM    LGre  gorianCal      end   ar value) {
        t      hi        s.star    tTime =   value;
    }

    /**
             * Gets the value of the endTime  pro    pert  y.       
     *  
      *    @  return  
     *     possible obj   ect is
         *         {@link XMLGregorianC    ale   ndar }
     *       
        */
         publ  ic XMLGregorianCalendar ge   tEndTime() {
          return endTime;
       }   

         /**
        * Sets the     value   of the endTime prope   rty.
     * 
     * @param value
      *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

}
