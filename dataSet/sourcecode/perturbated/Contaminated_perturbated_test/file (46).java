
package ExperianLtdCompanySearchWS;

import    java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/        **
 * <p>Java class for Curre ntDirectors complex typ   e.
 * 
 * <p>T    he      fo    llowing sche   ma fragment specifies     the e     xpected cont ent con   ta    ined within this class.
 * 
    * <pre>
 * &lt;complexType    nam e="   Curren            tDir   ectors">
 *   &lt;com  plexContent>
 *     &lt;  restriction base="{h  ttp://   www.w3.o   rg/2001/  XM     LSchema}anyType"   >
       *       &lt;sequence>
 *         &lt;element name="ForeignAd      dressFlag" type="{http:/    /www.w3.org/2001/XMLSchem     a}string" minOccurs="0"/>
 *          &lt   ;element name="DirectorCompanyF lag"  type="{http://www.w3.    org/2001/XMLSchema}string" minO  cc urs="0    "/>
 *         &lt;element n     ame=   "DirectorNumber" type  ="{http://www.w3.org/2001/XMLSchema}    string" minOccurs="0"/>
 *         &lt;element name="Appoi   ntmentDate" type="{http://s   chema.uk.experian.com/experian/bi/generic/  bsns/v100/basetype  s}CCYYMMDD" minO  ccurs="0"/>
 *         &lt;eleme  nt name="A                p   pointmentIndicator" type="{http://www.w      3.org/2001/XMLSche  ma}string" minOccurs= "0"/>
 *         &lt;element name="Dire  ctorshipLength" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;e lement nam   e="LatestReturns" type="{http://s chema  .uk.experian.co   m/experian/bi/generic/bsns/v100/basetypes}CCYYMMDD"   minOccurs="0"/>
 *         &lt;element name="NOCRef"       typ   e="{ http://w  ww.w3.org/2001/XMLSchema   }string" mi    nOccurs="0"/>
 *         &lt;element na    me="NumberConvictions" type="{http://www.w3.org/         2001/XMLSchema}int  " minOccurs="0"/  >
 *               &lt;element name="ConvictionDetails"  type="{http:/ /schema.uk.experian.com/experian/bi/generic/busin    ess/limited/  v100/baset  ypes}ConvictionDetails" maxOccurs="unbounded" minOccurs=     "0"/>
 *         &lt;element name="Dir ectorDetails" type="{http://sc  hema.uk.experian.com/      exper   ian/bi/generic/busines  s   /limited/v100/basety   pes}Direct   orDetails" mi   n      Occurs="0"/>
 *              &lt;/seq  uence>   
 *      &l t;/restriction>
 *   &l     t;/co   mplexContent>
 * &lt;/complexTyp e>
 * </pr  e>
 * 
 * 
 */
@XmlAcce  sso rType(XmlAcce     ssT   ype.F   IELD)
@XmlType(name = "CurrentD        ir    ectors", propOrder = {
    "foreignAddressFlag      ",
    "directorCompan   yFlag",
    "di   rectorNumber",
       "appointme   ntD     ate",
    "appointmentIndic  ator",
    "directorshipLength  ",
    "latestReturns",
    "no   cRef",
    "numberConvictions",
           "conv    ictionDetails",
    "directorDetails"
})
     public class CurrentDirector        s     {

     @XmlElem  ent(name     = "F   oreignAddressFlag")
         protected String foreignAdd  ressFlag;
    @XmlEl      em   en     t(     name = "D   irector     Co     mpa   nyFlag")   
    prote   cted String directorCompanyFlag;
    @XmlElement(n         ame = "DirectorNumber")
    prote      cted      Str    ing      directo     r   Number;
    @XmlEle  men t(name = "Appointm    entDate")
    protected CCYYMMDD appointmentDate;
    @XmlElement(name = "Appo  intmentInd ic ator")
    protected       String appoi      ntmentIndicator;  
      @XmlE        lemen   t(na           m       e = "Dire ctorshipLength")
    protec       ted St      ring di       rectorshipLengt h;
    @XmlElement(name =  "Lates  tReturns")
    pr      otected CCYYMMD D latest      Returns;
    @Xml  E   lement(name = "NOCRef")
    p          rotected St     rin   g nocRef;
       @X    mlElemen t(name = "NumberCo     nvictions      ")
        pro   tect   ed Integer nu  mbe rConvi      ction   s;
    @XmlElement(    name = "Conv ictionDet ai   ls" )
    protected  List<Convi ctio    nDe ta    ils> con  vi    ctionDeta      ils;
    @Xm               lEl   ement(name = "Direct   orD  e            tails")
    protected Directo r  Deta   ils    direct  o r    Details        ;

          /**
     * Gets the value of   the for    eignA        ddre     s   sFl   ag prope rty.
     * 
     * @retu     rn   
             *                  possi   b                   le object is
     *     {@link Str            ing      }
         *     
     */
       public String    g   etFore       ign   Addr      essFlag() {
        ret u  rn for   eignAd dressFlag;   
    }

    /**
      * Sets   the                 valu     e o    f the f  oreign Addres    sF                        lag p   roperty   .
           *       
     * @param value
               *     allo                wed objec  t is
        *     {@link String }     
                  *      
         */
    pu blic void s   etForeignAd  dressFlag(S     tring    value) {
               t   his.for      e            ignAddre    ssF  l   a              g  = value;
    }
    
       /**
     *   Gets the value     o   f     the directo     rCo      mpa    nyFlag prop      erty.
               * 
                 * @ret     ur   n
     *                        possible ob  ject is
                  *                  {@link String }
      *         
      */
    pu  blic S t ring     g etDirecto rComp  anyFl     ag  () {
        return di    rect   orCom   panyFlag;
    }

    /**
     * Set   s       the value of the directorC     o    mpa      n  yFl     ag p   r  op       erty.       
     * 
         * @     p ara m value
          *      allow  e  d            object is
     *       { @      link  String           }
        *      
     */
           publi  c void        s   etDirec to    rComp an yFlag(Str           ing value   ) {
             thi  s.di r              ectorCo      mpa     nyFl    ag = value;
      }      

               /**
     * G  ets the               valu    e of the director     Numbe       r prope  rty.
         *        
       * @return     
      *      pos    s       ible objec  t is
               *          {@li     nk Stri   ng }
     *       
     *  /
    public Str     in                  g ge     tDi rec torNu mb       er(   ) {
        retu       rn          direct  orNumber;
      }

                 /     **
            *   Sets the value of   th    e dire  ctorNu  mb er property  .
            * 
     *       @param v   alu      e     
             *       allow   ed    o  bjec    t is
         *         {@lin  k St           r  ing }
                   *     
     */
        public void setDirector    Number   (String              value)    {
          this.directorNu      mber = val ue;    
          }

        /      **
      * Gets                   th e val   ue of the            app   ointm    entDate property.
            * 
     *     @r     etu   rn
     *     po           ssi ble                object is
              *     {@link CC   YYMMDD }  
        *                 
           */
    public     CCYYMM     DD getAppo  intm   entDate()      {
        return app  ointme    ntD ate;
       }

         /**
                     * Se t   s     the value    of t       he appoin   t  men     tDate property.
     * 
        *  @param       value         
     *            allo              wed ob                ject               is     
     *        {   @link CCYYMMDD }   
      *      
            */
       public voi d set           App  ointm en     tD  ate(CCY YMMDD value) {
        t      his .appo       in       tmentDate =         value;     
       }

        /* *
                   * Gets t     he v    a    lue of   the a  pp     ointmentI    n  dicator pr   operty.
     * 
        * @ret     urn
           *                           pos    si     ble       o   bj   ec     t is
       *        {@   link St               ring }
          *      
        */
            public    String      getAp    point     men       tIndicator() {
              return appointmentInd icato     r;
    }

         /**
                   * Sets t         he valu e   of the appointme     n    tIndicator property.
               * 
           * @p   ar  a           m        v    alue  
          *     allo  wed object   is            
            *      {@link S    tr   i     ng }   
            *            
     */
          pub           l       ic        void       set    Appo         intment    Indicator(String     value ) {
          t     his.appointmen         tIndicator   = value       ;
    }

        /**    
          *   Gets   the value   of t   he dir       ectorshipLeng   th property. 
     * 
                            *        @return
     *       possible object is
         *     {@link  S     t  ring       }
     *     
     */
    publ  ic S   tring getDirectorsh     ipL   eng  th  () {
         retu r n directors  hipLeng            th;
      }

     /**
     * Se    ts t   he       value of t he directorshipLen      gt   h   p     rope     rt   y.
      * 
          * @param va lue  
                         *                          al      lowed obj   ect is
     *     {@link String    }
     *     
     */  
            p  ubli  c            v    oi d    setDirector  shipLe       ngth  (S   tring v  alue) {
                th      is.dir    ecto     rshipLe  ngth = value;
          }

       /**
        * Gets the    va  lu  e of      the           late  st             Retu          rns pro       perty.
                 * 
     *  @retur          n
            *     possible      obj   ect is
                                *          {@               lin    k CCYYMMDD         }     
                *     
          */
    public    CCYY  MMDD getLatestReturns() {   
            retur    n latestReturns;
        }

        /**
           *      Sets the value of th    e late           stRet  ur     n  s          pro   pe    rty.
        * 
     * @param     va    lue
     *     all      owed     objec t        is
           *     {@link CCYYMMDD }  
       *     
     */ 
    p  ublic v      oid s      etLatestReturns(CCYYMMDD va     lue) {
         this.latestReturn    s = value   ;
    }

    /**
     * G      e ts the valu   e of the nocR         e  f property.
     * 
     * @return
         *     possible    object         is
             *          {      @link St r       in g         }
     *     
                                */
    public S    tr ing getNOCRef      () {
            return nocRef;
    }

    /**
           * Sets the   value      o f the n       o   cRef prope  rty.
     * 
          * @param    value
     *     al lowed object is   
        *     {@link Stri ng }
     *     
       */
    public void setNOCRef(String    value) {
        this         .nocRef = value;
    }

       /**
     * Gets the   va       lue    of the numberConvictio  n        s prope    r   ty.
     *    
     * @return
     *     possib  le ob      ject is
        *        {@link Integer }
     *     
     */
     public Inte   ger getNumberConv     ictions() {
          return numberConvictions;
      }

    /**
     * Sets the     value of the number      Co  nvi  ctions property.
           *    
     *    @par   am value
     *         allowed obj     ect is
         *     {   @l  ink Int     eger }
      *                   
     */   
    pu       bli      c void setNumberConvictions(Integer value) {
             this.numberConvictions = value;
    }

    /**
     * Gets the v   alue of the convicti  onDetail      s property.
     *                  
          * <     p>
     * T  hi s accessor method returns a re feren  ce to the live list,
     * not a snapshot. Therefore     any modifica tion you    make to the 
     * r  eturned list will be presen     t inside               t    he     JAXB object.
     * This is why there      is not a <CODE>set</C    ODE    > method for     the convictionDetails proper ty.
     * 
     * <p>
     * For example, to add a ne      w item, d   o a  s follows:
     * <pr    e>
     *    getConvictionDetail   s().add(newItem);
     * </pre>
     * 
     * 
     * <p       >
     * Objects of the follow   ing type(s) are allowed i  n   the list
     * {@    link      ConvictionDetails }
     * 
     * 
       *    /
      pub    lic List<ConvictionDetails> getConviction   Detail   s() {
          if (   convictionDetails == null) {
            convictionDetails = new ArrayList<ConvictionDetails>();
                            }
           return this.convictionDetails;
    }

    /**
     * Gets the value of the directorDetai     ls property.
     * 
              * @return
     *     possible object is
           *       {@link DirectorDetails }
     *     
     */
    public DirectorDetails getD    ire      ctorDetails() {
        return directorDetails;
    }

    /**
     *  Sets the value of the directorDetails property.
     * 
     * @p   aram value
     *     allowed object is
     *     {@link DirectorDetails }
     *     
     */
    public void setDirectorDetails(DirectorDetails value) {
        this.directorDetails = value;
    }

}
