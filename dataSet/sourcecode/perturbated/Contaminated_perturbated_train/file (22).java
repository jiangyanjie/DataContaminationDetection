
package com.transportation.SIRI_IL.SOAP;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import     javax.xml.bind.annotation.XmlElement;
import       javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
          * Type for Abs      tract Call at st    op.
     * 
 * <p>Java cla   ss for AbstractCallStructure       complex ty pe.
   * 
 * <p>The following sc   hema fragme    nt specifies the  expected content contain  ed within this class.
 * 
 * <pre>
 * &lt;co  mplexType    name="AbstractCallStruc   ture">
 *   &lt;complexContent>
 *     &lt;re        striction ba se="{http:    //www.w3.org/     2001   / XM     LSche    ma}anyType">
 *            &lt;sequence>
 *         &lt;group   ref="{ht   tp://w w    w.s      ir   i.org.uk/si     ri}   StopPointInSequ en   ceGroup"/>
 *       &lt;/sequence>
 *         &lt;/restriction>
 *   &lt;/complexC   ontent>
 * &lt;/complexType>      
 * </pre>
 * 
 * 
 */     
@XmlAccessorType(Xml Acce             ssType.FIELD)
@XmlTyp e(name           = "AbstractCallStructure", propOr  der = {
       "stopPointRef",   
        "visitNumber",
       "order",
    "stopPointName"
}   )          
public class      AbstractCallSt ru  cture {

       @    XmlElement(         name = "StopPointRef", required = tr     ue)
    protected St   o   pPointRefStructure sto   pPoint   Ref;
    @XmlElement(      n ame = "VisitNumber")
    p       rotected BigInte  ger v isitNumber;    
    @XmlElement(name           =  "Order")
    @   Xm   lSchema  Type(name =   "positiv eInteger")
    protected Bi   gInteg  er order;
    @XmlElement(name = "StopPointName")
    pro  tected NaturalLa      nguageStringStru cture s            topPointName;
    
          /*   *
         * G   e           ts the    value    of th   e st        opPointRef pro               perty.
               *    
       * @ return
     *     possible object is
                       *     {    @li nk S t opPointR  efStr       ucture      }   
     *                
              */ 
    public S    topP   o intRefStructure ge  tStopPoint    Ref() {
                              return stopPointRef;   
        }

           /**                 
        * S  ets the value of the stop    P           ointRe       f     pr            operty.
        * 
                               * @param valu  e
      *     allowed objec   t is
       *     {@li nk StopPointRefStructure }
        *     
      */     
       public void   setStopPointRef(StopPointRef            Stru  cture                     value) {
             this.stopPointRef = value;
                    }

       /*  *    
      * Gets t  h e     value of the visitNumbe      r prope        r ty.
        * 
                  * @return
            *     possi  b  le o      bje   c    t   is
        *      {@li         nk      BigInt       eger }
     *           
             */
     pu      blic B            i  g  Integer ge  tVisitNumber(       ) {
                          retu  rn    visitNumber;
             }

    /* *
     *   S    ets    the value   of the visi     tNumber   prop     e  rty . 
                     * 
     * @  par  am v    alue
        *                       allo wed ob             jec     t     is
        *                     {@link BigInteger }
       *     
              */
                 public voi d setV    isi   tNum    b        er(Bi       g    Int   eger value) {          
            this.visi    tNumb      er = v    alue;
    }

     /**
         * G    ets the v a    lu  e o  f t      h      e ord              er p   roperty.    
             * 
     * @retur      n
             *     possible ob       ject is
         *                             {@l   ink BigInte        ger }
       *     
        */
    public B    ig   I  nt  eger   getOrder()    {
            return order;
    }
   
    /**
     * Sets      the       value of        the or   der propert  y.
     * 
     * @param    v    alue
        *             al  lowed object is
     *     {@   link BigInteger }
     *     
        */
    publ      ic void setO  rder(BigInteg     er va   lu    e   ) {   
        this.order = value;
    }

       /**
     *       G   ets the value of the s   topPointNa    me property.
     * 
        * @return
     *       possible object is
     *       {@link NaturalLanguageStringSt  ructure }   
     *     
     */
    pu  blic NaturalLanguageStringStructure getStopPointN     ame() {
                 return stopPointNa  me;
         }

    /**
     * Sets the value o f the stopPointName prope      r ty.
     * 
     * @param    value
     *     allowed object is
     *     {@link NaturalLanguageStringStructure }
     *     
     */
    public void setStopPointName(NaturalLanguageStringStructure value) {
        this.stopPointName = value;
    }

}
