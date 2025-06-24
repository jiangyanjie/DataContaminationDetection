
package   com.transportation.SIRI_IL.SOAP;

impo rt javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
impo  rt javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * The DATEX II logical model compr    ising exchange, conten t payload and management sub-models.
 * 
 * <p>Java  class for D2LogicalModel compl   ex    type.
 * 
 * <p>T   he foll                          owing schema fragment s   pecifies     t    he expected conte n   t contained within this class.
 *  
 * <pre>  
 * &l    t;co      mplexType name="D2Logical     Model">
 *   &lt;complexContent>
   *     &lt;restriction b  ase="       {http://www.w3.org/2001/    XMLSchema}anyType">
 *            &l  t;sequence>
 *         &lt;element name="exchange"    type   ="{http:    //dat   ex2.eu/schema/1_0       /1_0}Exch ange"/>
 *         &  lt;element name="p ayloadPub   licati on"   type="{http://datex2.eu/schema/1_   0/1_0}PayloadPublicat   ion" minOccurs="0  "/>
 *               &lt;elem  ent name="d2LogicalModelExtension" type="{http    ://datex2.eu/schema/    1_0/1_0}ExtensionType" minOccur    s="       0"/>
 *       &lt;/sequence>
 *         &lt;attribute name="modelBaseVersion"    use="   required" type="{http://www.w3.org    /2001/XMLSch ema}anySimp leT     ype" fi  x  ed="1.0"   />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 *  &l             t;/complexT y    pe>
 * </pre>
  * 
 * 
 */
@XmlAcc  essorT   ype(XmlAcc   essType.FIELD)
@XmlType(name = "D2LogicalModel", namespace = " ht    tp://d     at   ex2.eu/schema/  1_0/1_0"   , propOrder = {
    "exchange",
    "payloadPublication"   ,
        "d2LogicalMod elExten   sio    n"
})
public class D2LogicalM       odel {   

    @XmlElement(   required = true)
    protected Exchange exchange;
           protected PayloadPublicati   on payloadPublica  tion;
        pro  tected Extensi   onType d2Logica lModelExte              nsion;
             @Xm     lAttr  ibute(name                = "modelBaseVersion", require          d = true)
        @XmlSchemaType(name            = "anySimpleType")
    pr        ote          cted Str       in  g model     Base    Ver    si      on; 

    /**
     * Gets the value of    the e xchang  e prope    rty         .
           * 
     * @re     t  urn
       *            possible   obj       ec     t i  s    
     *       {@    link E  xc  ha   n   ge     }     
     *     
     */
      public                Exchan ge get        E              xch        ange() {
         retu              rn e xch    ange;
    }

             /**
     * Sets the v      alue of the     exchange pro   perty.
      * 
     * @            param value
         *           al    lowed objec        t is
     *            { @                 l     i  nk Ex chang e }
                     *                           
     */
         pu      blic       void setExcha nge(Exchang   e val   u  e)               {
        this.exchan    g e = value    ;
    }

    /**
       * Ge  ts the va      lue          of the pa    yl   oad  P   ubli     c          ation p   rop erty.
     * 
            * @retur       n
        *       possible o  bject is
               *     {@link    Payl      oadPub   lica  tion             }
     *     
       */
    pub lic                PayloadPublica    tion           getPa     ylo       adPubli     cation() {
        return p   ay      loadPublication;
    }

    /**
        * Sets   the   value o f the p      ayloadPu     blication p   rope   rt  y      .
         * 
        *      @param value
        *          allow   ed object      is 
     *        {      @link PayloadPublicati           on  }
     *                         
      */
    pub    lic voi  d setPa  y  loadPubl         i  cati   on(PayloadPu   bl i   catio      n v      alue) {
                      thi s.payloadP     ublica  tion = va  lue;
    }   

    /**
     *     G   e   ts the   va  lue of the          d  2            Log       icalMo         delExtension proper            t     y.
     * 
     *     @      re    turn
     *      p            ossi ble  ob    ject is
          *                 {@link Ex    tensi  onType }
       *                   
     */
               publ     i           c      E  x t  e  ns     io    nType ge  tD2LogicalM  ode      lExtension()  {
        re   turn d2L    ogic   alMode  lE  x   tension;
    }  

      /**
     * Sets th     e value of the d2LogicalMode   lE      xtension property.
           * 
          * @    param value
         *     allowed object is
              *     {@l  i       nk ExtensionType }
           *     
     */
    public void setD2LogicalModelExtension(Extensi  onType valu   e) {
            this.d2Logical     ModelExtension = va  lue;
      }

    /**
           *        Ge      ts the v  alue of the   mod elBaseVersion prop  erty.
     * 
             * @return
     *             possible obj  e    ct       is
     *     {  @link St     ring }
            *        
     */
          public   String getMo   delBaseVersion() {
             if (mo delBase      Version == null)   {
            return "1.0";
        } e   lse {
                 return modelB aseVersio     n;
        }
    }

    /**
       * Sets the value of the modelBaseVersion property.
     *     
     * @param value
     *     allowed object is
           *     {@link String }
     *     
     */
    public void setModelBaseVersion(String value) {
        this.modelBaseVersion = value;
    }

}
