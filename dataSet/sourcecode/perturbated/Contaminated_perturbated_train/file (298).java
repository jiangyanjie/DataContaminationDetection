
package com.transportation.SIRI_IL.SOAP;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import    javax.xml.bind.annotation.XmlAccessorType;
imp     ort javax.xml.bind.annotation.XmlElement   ;
import javax.xml.bind.annotation.XmlType;
     

/**
 * Accide    nt   s are events in wh ich one or mor   e vehicles     lose con  trol an    d do not reco      ver.   They include collisions between vehicle(    s) or        other r  oad user(s), between v  ehicle(s) a       nd any obstacle(s), or   they result   f   ro          m a veh    icle running of    f the    road.
 * 
 *    <p>Java class for Ac  cident com   pl    ex type.     
 * 
 * <p>The following  sc   hema fragm   ent specifies the ex   p   ected content contain  ed wi    th     in    this c     lass.
 * 
 *        <pre>
 * &lt;c   omplexType name="Acciden  t">
 *   &lt;complex   Content>
 *     &lt;extension bas  e=" {http://datex2.eu/schema/1_0/  1_0} TrafficElement">
     *          &lt;sequence>
 *              &lt;element name="accidentCause" type= "{http://datex2.eu/schem   a/1_0/1_0}AccidentCauseEnum" minOccurs="   0"/>
 *         &lt;element name="accidentType" type="{htt       p://datex2.eu/schema/1_0/1_0}AccidentTypeEnum" maxOccurs="unbounded"/>
 *         &lt;element name="v  ehicleIn    volved" type=" {http://datex2.eu/schema/1 _0/1_0}Vehicle"  maxOccurs="un   bound    ed" minOccur    s="0"/>
        *         &lt;element name="overviewOfV ehiclesInvolved  " type="{h  ttp://datex2.eu/schema/1_0/1_0}Vehicle sInvolved" maxOccurs="unbounded" minOccur    s="0"/>
      *         &lt;element name="overviewOfPeopleInvolved" type="{http://datex2.eu/ s    c        hema/1_0/1_0  }PeopleInv       olved" maxO       ccur        s="un    bound        ed" minOccurs    ="0"/>
      *         &lt;element name="accidentExtens  ion" type="{ht       tp://datex2.eu/schema/    1_0/1_0}Ex   tens      ionType" minOccurs="     0"/>  
 *       &lt;/sequence>
 *      &lt;/extension>
 *      &lt;/complexContent>
 * &lt;/complexType>
    * </pre>
 * 
 * 
   */
@XmlAcce  ssorType(XmlAccessType.FIELD)
@XmlTy   pe(nam e = "Accident", namespace = "http://    da   tex2.eu/schema/1_0/1_0" , p  ropOrder = {
         "accidentCause",
    "ac cidentType",
    "vehicleInv         olved",
    "overviewOfVehic lesI    nvolv   ed",
    "overviewO      fPeopleInvolved"   ,
    "accidentExtension"
})
pu      b  lic class Accident
    extends  TrafficElemen   t 
     {

    protected Accident    CauseEnu m accide   n    tCause;
    @        XmlElem ent(required = t    rue)
    pro tec        ted   List<AccidentTypeEnum> accidentType;
    protected List<V      ehi    cle> vehicleInvolved ;
    p     rotected L    ist<Veh     iclesInvol   ved>  ove   r     v  iewOfV   ehiclesInvolved;
    protect  ed List<PeopleInvo  l       ve d> overviewOfPe              opleI           nvo l  ved;
    protec   ted E xtensionType a   ccide    nt  Exte        nsi     o   n;

          /**
           * Gets the     val     ue of th   e accidentCause proper  ty.
       * 
        * @return
     *      possi  bl       e      o   bject is
            *     {@link Accident  C           auseEn  um }   
       *     
     */
           public     Accid  en   tCaus     eEnum        getAcc   ide    ntCause()    {
             return accide  ntCause;
      }

    /**
                    * Sets the va     l  ue of the accidentCau   se p rope    rty.
     * 
     *     @pa    ram value
                    *                   a  llowed ob    j ect is  
     *     {@link AccidentCauseEnum }
     *     
            */
      p   ublic voi  d    setAccidentCause  (A   c  cide   nt CauseEnum value)  {
                 this.ac      ci      d   entCau  se = value    ;
          }

              /**   
                *         G     ets  the     value of the   accidentType   property.
        *   
     * <p>
        * This ac     cessor met hod           return  s    a reference to the live     list,
       *         not a snapshot.       There   for      e any modif  ication    yo u      make    to the
     *  return   ed list will be present ins    ide t   he JAXB object.
     *         This is    wh   y    t  h ere   is       not a <CODE>set</CODE>      metho    d   for the            a      ccidentType prope       rt  y.
     * 
     * < p>
            * For    exa    mple, to      ad        d a new i  tem, d    o     as follo  ws:
     *    <  p re> 
     *    g   etAccidentT ype(      ).add(newIt em);
     * </p     r         e>
     *         
           * 
          *     <p>
       * Objects        of            t   he f o        llowing type(s) are  allowed in the li  s    t
         * {@link AccidentTypeEn   um }
     *         
     * 
                  */
      public List<Acci  de ntTypeEn     um> getAccidentType() {
                           if (accidentTy     pe    ==      null)    {
                 ac    c      identType =    new ArrayLi        st<Accid entTypeEnum>();
          }
            return this.a   cciden    t  T    y   pe;     
      }

         /**
         * Ge   ts t  he v           alue o       f th  e v    ehi                       cleInvolved pro p   erty. 
       * 
       * <p>
         *      This accessor      m      eth  od returns        a reference to the live list,
       *     not a     snapshot. Therefore    any     m        odif    ic   atio   n you make to the
      * returned list will be present inside t           he JAXB obj    e             ct.
     *      This     is why ther   e is        not a  <CODE>s   e      t</CODE> me   t   ho            d for the vehicleInv olve  d property.
            * 
     *      <p>
           * For example,  to add        a new ite     m, d     o                  as follows:
               *  <pre>    
        *    getVehic   l     eInvolved()        .add(newItem);  
     * </pr           e>
     * 
             * 
     * <p>
      *    Objects of the followin    g type(s) are al    lowed in the     list
              * {@    lin    k Vehi     cle      }
               * 
        * 
               */
      public List<Vehicl    e> get V          ehicleInvolved() {
            if (v               ehicleInvolv ed ==      n    ul       l) {
                v    e hicleInvolved = new Ar              rayList< Vehicle>();
            }
           r      eturn this.vehicleIn           volv  ed;
        }
           
         /**    
         * Gets the       valu  e of the        overviewOfVehiclesInvolved property.
                   * 
          *    <p> 
     * Th    is      a          ccessor me      thod      returns a referenc    e to the l  ive li           s        t, 
     * n    ot a snaps        hot. Therefo   re any     mod       ification you make to the
           * returned lis  t        will      b   e present  inside th           e         JAXB object. 
           * Th  i   s is why there is    not a   <CO   D     E>set</CODE> me   th  od for the ove    rviewO  fVeh            icl   esIn volved property.
                            * 
       * <   p>
     * For exam             ple, to add a ne       w   ite m, do as   follows:
     * <pr        e   >
     *         ge   tOverv iewOf  Ve       hicles    In  volved()    .add(newItem);
     * </pre>
       * 
              *   
     * <p>     
          *         O     bjects       of the fo    llowing t  yp  e(s) are allowed in the list
               * {@link VehiclesInvolved }
     * 
     * 
     *     /
    publ     ic List<Ve    hicl   esInvolved> getOverviewOfVe            h   iclesInvolved  () {
                  if ( overviewOfVehiclesInvolved ==    null) {
                                     overv iewOfVehiclesInvolved = new ArrayList<V    eh   iclesInvolved>();
        }
                  ret urn th   is    .ov        er   viewOfVehiclesInvolv       ed;
    }

    /**
     * Gets t  he value of the   over      viewOfPeopleInvolved property.
     * 
     * <p>
         * This accessor me   thod returns a reference to the live   list,
           * not    a snap    shot. Therefore any modificat   io   n you     m  ak   e to the   
     * returne    d    list will be pres     e   nt     ins    id    e the JAXB object.
     * This is why there is n        ot a <      CODE>set</CODE>        met   hod for the overviewOfPeopl    eInvolved property.
     * 
     * <p>
     * For example, to ad    d a new item, do      as follows:
     * <pre>
     *    getOverviewOfPeopleInvolved().add(ne  w    Item)  ;
     * </pre>  
     *      
     * 
        * <p   >
     * Objects of  the follow   ing type(s) are allowed in t   he list
         * {@link   Peo pleInvolved    }
            * 
     * 
     */
    public L      ist<Peo pleInvolved> getOverviewOfPeopleInvolved() {
        if (overviewOfPeopleInvolved == null) {
            overviewOfPeopleInvolved = new ArrayList<Peopl  eInvolved>();  
         }
        return   this.over     viewOfPeopleInvolved;
    }

    /**
       * Gets the value of the acc  identExtension property.
     * 
     * @return
     *     possible object is
     *         {@link E    xtensionType }
     *     
     */
    public ExtensionType getAccidentExtension() {
        return acci    dentExtension;
    }

    /**
     *   Sets the value of the accidentExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *     
     */
    public void setAccidentExtension(ExtensionType value) {
        this.accidentExtension = value;
    }

}
