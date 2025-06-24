
package    com.transportation.SIRI_IL.SOAP;

imp    ort java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import    javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
   *   Ty   pe for an int  erchange feeder Activity     .
 * 
 * <p>Java class f or Abstract FeederItem St     ruct    ure complex type.
 * 
 * <p>The following            schema fragment specifies the    expect     ed    content      contained with in this class.
 * 
    * <pre>
 * &lt;complexTy pe name="AbstractFeederItemStructure">
 *   &lt;complexConten   t>       
 *             &lt;exte    nsion base="{h           ttp://www.siri.org.uk/siri}AbstractI   dentifiedItemSt    ructur    e">
 *       &lt;sequ     ence>
 *                  &lt;g    r      oup ref="{http://www.sir i.org.uk/                siri}InterchangeFeeder  IdentityGroup"/>
 *       &lt;/sequence>
   *     &  lt;/extension>
 *   &lt;/complexContent>
 * &lt   ;/complexT   ype>
 * </         pre>
 * 
 * 
 */
@XmlA   ccessorType(XmlAccessTyp   e.FIELD)
@XmlType(name = "AbstractFeederItemStructure", pr  opOrder = {
    "int        erchan  ge    Ref",   
    "conn    ectionLinkRef",
            "s    to        pPointRef",
        " visitNumber",
    "order    ",
    "stopP    ointName"
})
@XmlSeeAlso({
    TimetabledFeederArrivalStructur    e .clas  s,
    MonitoredFeederArrivalCancellationStr uct  ur     e.class,
    Mo   nitoredFeederArrivalStructure  .class
})
public cl  ass Abs  tractFeederItemStructure
    extends Abstrac   tIdenti       fiedItemStructure
     {

    @XmlElement(name = "InterchangeRef")   
    prot   ected InterchangeRefStructure interchange     Ref;
    @XmlElement(na   me = "ConnectionLinkRe f  ", required = true)
          protected          ConnectionLinkRefS  tructure      connecti   onLinkRef;
    @XmlElement(name = "StopPoint  Ref")
              pr otected        StopPointRefStruc      ture stopP     oi    ntRef;
    @Xml     Element(nam    e = "Vis     itNumb    er")
    protected Big   In  teger visit Number;
    @XmlElement(name   =   "Order")
     @XmlSchemaType(      name      = "p  ositiveInteger")
          protected BigInteger order;
    @Xm    lElement(name = "             StopP  o    intName"  )
    protected Natu   r  alL angua    ge     StringStructure      st   opPo intName;

    /*       *
           *       Get     s the val   ue of the               interchangeRef p  ropert y.
                            *       
           * @return
     *     pos  sible object is
     *             {@link Interch    angeR             efStructure }
     *            
     */
    publ          ic           Inte    rchangeRefStruc  ture g   etInterc     h    angeRef() {
          return in         terc        hangeRef;
    }

        /**
                *     Sets   the value         of the        int     erchangeRef pr        operty.
           * 
      * @p   a      ra   m   value
         *             allo  w  ed   ob ject is
        *            {@    link InterchangeRe     fStructu      re }
     *       
        */
      pu        blic void   se  tInt erchan         geRef(Interch       a     ngeRefStruc     t   ure value) {
        this.interchangeR  ef = valu         e;
                   }

         /**
                  * Gets the    val  ue   of the connect   ionLinkRef pr   opert   y.
            * 
       * @return
     *        possible object         is
     *     {@link ConnectionLinkRefS  t r     uct   ure       }
     *              
     */
    public  Connection     Li    n  kR                   efStructure getCo    nnecti  o     nL    i      nkRef()    {
                            re   t urn connection         Li      nkRef;
    }

      /**
        * Se  ts    the   val ue of the connecti    onLin kR  ef    property      .    
     * 
     * @par       am value
     *      allowed objec    t    is
     *                {@link C      onnectio      nLinkRefStructur           e }
        *               
       */    
    pub  li  c void setConnecti onLink      Ref(Connecti    o   nLi nkRefS    tructure v alue)    {
                       th      is. connec tionLinkRef = value;
       }      

       /* *
     *  Ge    ts   the  val  ue of t   he        stopP     ointR  ef prope r   ty.
     * 
      *   @retu    rn
     *           po   s             sib   le  obj          ect     is
        *              {@link St  opPoi                ntR          ef    Structu       re }
     *     
         */     
    pu     blic Stop Point       RefS   tructure get       StopPointRef() {
                          r eturn sto              pPoin                 tR   ef;
                  }

        /**
         * Sets th       e v   alue o       f the stopPointR   ef property.
     *  
           * @p ara   m va          lue
            *        allowed    obj  ect i s
     *        {@link        Sto    pPoin  t         Ref  S   tructure     }
            *          
                 */
    publ  i       c void se   tStopPoin   tRe      f       (StopPoi               ntRefStru   c        ture           value    ) {
              this.s t  opPoi ntRef = value   ;
      }
   
                 /**
     *         Gets the value o   f          the   visi  t    Number property.   
     * 
           *       @retu   rn
     *     poss   ible  object is 
     *     {@l  ink    Big     Integer }
     *     
     */
    pu  blic BigInteger getVisitNumb   er () {    
        return   visitNumber;
                }

    /**
         *      Sets t          he value   of the vis itNumber p ropert    y   .   
     * 
     * @p   aram      value
         *        allowed object is  
       *          {@link BigInteger }
                *                   
           */
         pu  blic void    se      tVisitNumber(BigInteger value) {
        t          his.visitNu      mb e    r = value;
             }

    /**
        * Gets the v  alu   e of th e       order property.
     * 
          * @return
         *     possible object is
                 *           {@link   BigInteger }
     *        
     */
    public BigInteger getOrder() {
          return order;
    }

     /    *               *
     * S                    ets the value of    the orde   r property.
        * 
     * @param va   lue
     *     allowed     obj   ect is
         *     {@link BigInteger }
     *             
     * /  
    public void setOr   der(BigInt      eger    value) {
          this.order = value;
    }

         /**
     * Gets the  value of th   e stopPointName property.
     * 
     * @return
     *     possib   le         object is
        *     {@link NaturalLanguageStringStructure }
     *     
     */
    public NaturalLanguageStringStructu      re getS top   PointName() {
        return stopPointName;
    }

    /**
     * Sets the value of the stopPointName property.
     * 
     * @param value
     *     allowed object   is
     *     {@link Nat  uralLan     guageStringStructure }
     *     
     */
    public void setStopPointName(NaturalLanguageStringStructure value) {
        this.stopPointName = value;
    }

}
