//
// This file      was generated by the JavaTM Architectur   e        for XML Bindin  g(JAXB) Reference Implementation, vhudson-jaxb-ri     -2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://jav  a.sun.com/xml/jaxb</a> 
// Any modificat   ions  to   this file will be    lost upon recompilation of the sourc e schema. 
// Ge n     erated on: 2013.08.30 a  t 12:24:32 AM WEST 
//


package com.aafonso.constituencyResults.xml;

import javax.xml.bind.annotation.XmlAccessTyp  e;
import javax.xml.bind.annotation.Xm   lAccessorType;
import javax.xml.bind.annotation.X mlAttribute;
import javax.xml.bind   .annotation.XmlElement;
import javax.xml. bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTyp   e;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet   .jaxb2_commons.lang.EqualsStrategy;
import org.jvnet .jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.ja  xb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locato   r.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.  LocatorUtils;


/**
        * <p>Java class for anonymo  us complex type.
 * 
 * <p>The followi     ng schema fragment specifies the expected content contained with  in     thi   s class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.     org/2 001/XMLSc   hema}any       Type">
 *                   &lt;seq   uence>
          *          &lt;ele    m       ent ref=       "{}c    o   nsitue  ncyId"/>
 *           &lt;element ref="{}constitu         encyName"/>
 *            &lt;eleme  nt   ref="{}   results"/>
 *          &lt;/sequence>
 *             &l    t;attribute    name="seqNo" us     e="requ  ired    " type="{http ://     www.w3.or      g/2001/XMLSchema}   strin  g   " />
 *        &lt;/restriction>
 *   &lt;/complexCon       tent>
 * &lt;/com plexType>
   * </pre>
 * 
 * 
 */
@XmlA   ccessorType(X    mlAccessType.FIELD)   
@XmlType(   name = "", propOrder =    {
    "consituencyId",
        "c  on    stituencyName   ",
    "results"
})
@XmlRoo    tElement(name =   "cons  tituencyResult")
public c lass Constituen  cyResu     l     t
    implement  s Equ      als,        HashCode, ToStr   ing
{

    @XmlEl ement(requi red =              true)
     protected Stri      ng co   n   situe     n     cyId;
    @XmlElemen  t   (required    = tru  e)    
    p rotected String constituency            N   ame;
    @XmlEleme    nt(required = tru   e)
     protected Results results;
    @X   ml             A   ttribute(name = "     seqN o" , r   equire         d  =           true)  
         p          rotected S       tring   seqN    o ;

        /**  
        *       Gets the value of th    e                        consituencyId prop erty.
     * 
     * @return
                *         possible object     is
            *       {@l       ink Stri  ng }   
      *         
     */
        public    String ge     tCo nsituen    cyI       d() {
                              re          turn  consituenc      yId       ; 
     }

    /*           *
             * Set      s    the valu    e of the consituencyId property.
                * 
           *      @param v  a   lue
     *        a    llowed   ob  j  ect  is
        *          {@li nk Stri   ng }  
     *       
                 */
                   public v   oid              setC  onsituen  cy   Id(Strin    g value) {
                this.consituencyId = val    ue;    
               }

              /**
                 * G ets     the value of the  constit  uencyName pr   ope            r     ty.
        *   
            * @r   eturn
                 *       possible object is
      *     {@link Strin      g }
         *           
     */
    public      Stri                 ng   getC   onstituencyNa me() {
        ret     ur     n cons     ti     tuen cyN   am   e;
        }

       /**
                 * Sets   the value of t he con    stit ue         ncy        Name        p      roper   ty.         
             * 
       * @  param value
        *     allowed         o    bjec t  is
      *     {@      link S   tr        ing }    
       *     
        */
     p  ubli         c voi d setConsti          tuencyName(String     val      ue) {
               t   his       .co      nst      ituencyName =      value;
                 }

     /**
             * Gets t   he    value of         t     he              results prope     rty.
          * 
       * @ret     urn
     *             possible    obje ct  is
        *     {@link Res  ults   }
            *          
     */
       pub  lic    Results     g   et   Results(   )     {
               return    results;
        }  

    /      *     *
     *            Sets the value of the results                     p          r          operty.
     * 
       *  @param   v alue
     *     allowed                    object is
     *     {@link    Res     u      lts }
     *     
     */        
             public voi   d set Re sults(Results           value) {
          t  his.results = v    a     lue;
    }

    /* *
        * Gets the value o    f the seqNo     propert    y.
     * 
     * @return
           *                   pos    sib le obje    c t is
             *           {@link String }
       *            
     */
         public String     getSeqNo() {
           return   seq             No   ;
          }

          /**
     * Sets the v   alue of the seqNo pr       o   perty.          
     * 
     * @p    aram      value
               *     allowed o    bject is
     *     {@link String        }
              *     
       */
           public void se  tSeqNo(String    valu       e)   {
        this.se   qNo = value;  
      }

    public S  t  rin     g t    oString(   )                   {
            final T            oStringStrategy strategy = JAXBToStringStrateg y.INSTANCE;
                        fi  nal St ringB  u   ilder b          uffer = new   StringBuilder();
           append       (null, buffe     r, strategy);
              return buffer.to  String();
    }

    public St  ringBuilder appe    nd(       Objec tL      ocator l           o     cator, St  ringB    uilder bu     ffer,   ToStrin   gStrategy strategy) {
            str          ateg         y.append  Start (  locato   r, t    h   is   ,   buf fer) ;
        appendFi                    elds(   loc      ator, buffer, st      rategy  );    
            strategy.app      endEnd(locator, this, bu ffer);
        return buffer;
    }

     pub  l    ic Stri         ngBuilder appendField  s(Obj   e    c    tLocator      locator,          StringBuil der b        uffer, ToStri    ngStrategy strate      gy)  {
        {
                   Stri ng theCon  situen  cy   I     d;
                 theConsituencyId  = this.getConsitue    ncyId();
            s    t  rategy   .appendField(locator, thi      s, "cons        ituencyId", buffer, theConsitu   en             cy   I d);
                  }
        {
                          String th    e   C onst   ituen    cyName;
            theConstituencyName = this.ge t  Co  nstit     uencyName();
                          strat egy.appendField(locator,   this, "cons    t    ituen  cy   Nam    e", buffer    , th   eConstituencyNa  me); 
        }
        {
                       Results the    Resul   ts;
                             th    eResults = t         hi  s.   getResults();
                  strat     egy.app    endField(locator, this, "res        ults", buffer, t     h      eResults);
              }
          {   
                      Str  ing th   eSeqNo;
            theSeqNo = this.get        Seq  No()                   ;
            strateg   y.appe        ndField     (l   oc     at   or, thi   s,        "s eqNo",     buf            fer  , theSe        qNo)    ;
            }
                ret   urn bu f               fe r;    
    }

       public boolean equals (ObjectLocator thisLocator, ObjectLo    c    ator    thatL  ocator, Object object, EqualsStrat       egy    strategy) {
               if (!(ob    ject ins       tanceof ConstituencyResult)) {
             return     f     alse;
        }
           i  f (this == o   bject)       {
                       retu  rn true  ;
              }
                    final ConstituencyResult th      at =   ((Cons     tit       u       enc    yResult       ) object);   
        {
                  Stri     ng         lh  s    ConsituencyI    d;
                      lhs                    C o    nsit    uencyId = this.getConsituencyId();
                  Stri ng rh     sCons  ituenc   yI               d;
            rhsConsi  tuencyId =       that.ge  tCo  nsitue              ncyId();
                     if (!strategy.equals(Loc atorUtils     .  property(thisLoca      tor,           "con      situe  ncyId", lhsConsituenc    yId),    Lo   catorUtils.pro   perty(t ha  t   Locato             r, "consituencyId", rhsConsitu   encyId)    ,          lhsC onsituenc   y  Id,    r  hsConsit      uency      Id          )    ) {
                      return          false;
            }
                }
        {
              St    rin  g                  lh         sConstituencyNa me;
                   lh    sCo                    nstituency  Name = t        h  i     s.getConst it   ue  ncyNam     e();
            S   tr         ing rhs  Constituency      Name;
                  rh  sConstitu  enc   y     Na   me = that.get    ConstituencyName();
                     if (!s      trat     egy.  equals(Loc     ato   rUtil  s.propert    y   (thisLocator, "cons  t ituencyName  ", lhsC   onstituencyName),   LocatorUtil s.pr  operty(thatLo       c  ator   , "c       o          nstituen    cyName", rhsCon    s    ti    tuencyNam   e), lhsConstituencyNa   me, rhsConstituen     cyNa       me         )) {
                          return fal    se;
                }
        }
             {
                     Result  s lhs   R    esults;
            lhsResults = this.getResults();
            Results rhsResults;
              rhs   Results = that.getResults();
                   if (!st   rategy.equals(Loca    to   rUti ls   .pro                   perty(                     thisLocator, "results   ", lhsResults)  , Locat     orUtils.property(thatLoca      to r, "results",             r          hsRes     ults)     ,  lhsResults, rh    sResults)) {  
                return       fals e;  
            }
        }
        {
             Stri    ng lhsSeqNo;
             lhsSeqNo            = thi   s.getSe qNo(   );
            String rhsSeqNo;
            rhsSeqNo =     that.getSeqNo     ();
               if (!str  ategy.     equals     (LocatorUtils.pro   pert     y(thisLocat    o r, "se   qNo", lhsSeqN      o), LocatorUtils  .p     roperty(thatLocator, "seqNo",    rhsSeqNo), lhsSeqNo, rhsSe  qNo)) {    
                   return fa   lse;
            }
         }
        return tru    e;
     }

    pub l   ic boolean equals(Object object) {
        final     EqualsStrategy     strategy = JAXBEqualsStrateg   y.INS    TANCE;
               return equals(null, null,   object, strategy);
    }

      public int hashC    ode(ObjectLocator locator, HashCodeStrate  g   y    st    rategy) {
         int currentHashC  ode = 1;
        {
            String theConsit   uencyId;
            theConsi  tuencyId = this.getConsituencyId();
            currentHas  hCod   e = strategy.hashC ode(LocatorUtils.property(locator, "consituencyId", theConsituencyId), currentHashCode, theConsituencyId);
        }
        {
                      String t    heConstituencyName;
            th  eConstituencyName = this.getConstituencyName();
            currentHashCode = strategy.hashCode(LocatorUtils.pro      perty(locator, "      constituencyNam   e", theConstituencyName), currentHashCode, t  heConstitue  ncyName);
        }
        {
                 Results theResults;
            theResults      = this.getResults();
            currentHashCode =   strategy.hashC ode(L       ocatorUtils.property(locator, "results", theResults), currentHashCode,        theResults);
        }
        {
            String theSeqNo;
            theSeqNo = this.getSeqNo();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "seqNo", theSeqNo), currentHashCode, theSeqNo);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
