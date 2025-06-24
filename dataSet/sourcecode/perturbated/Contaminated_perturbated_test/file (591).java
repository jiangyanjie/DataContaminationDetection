
package ExperianLtdCompanySearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import  javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**  
 *  <p>Java clas  s for DebtorsDetails complex type.
          *     
 *  <p>Th         e following schema f  rag          ment specifies        the expected content contained wi   thin this class. 
 * 
 * <pre>
 * &lt;complexT    y   pe  name="DebtorsDetails">  
 *   &lt;complex    Content>
       *     &lt;re     stricti   on base="{h       ttp://www.w3.               org/2001/XMLSchema}anyType">
 *            &lt;sequence>
 *            &     lt;elemen   t name="Debto   rs" type="{http://   www.w3.org/2001/XMLSchema}long"     minOccurs="0"/>
 *         &lt;element n    ame=    "AccountsReceivableTradeDebtors" typ       e="{http://www.w3.org/2001/XMLSchema}long"        minOcc urs="0"/>
 *               &lt;element name="DebtorsSubsidiaryA   ssociateJoint   " type="{http://www.w3.org/2001/XMLSch  ema}long" m  inOccurs="0"/>
 *                &lt;element name="PrepaymentsAccruals" type="{ht t   p ://www  .w3.org/2001/XMLSchema}long"      minOccurs="0"/>
 *         &lt;ele        ment name="Debtors    GroupLoans" type="{http://www.w3.o   rg/2001/XMLSchema}long" minOccu    rs="0"/>
 *         &   lt;element name="Debtors  DirectorLoans"       type="{http://www.w3.org   /2001   /XMLSchema}long" m   inOccurs="0"/>
 *            &lt;  el  e    ment name="OtherDebtors" type="{h    ttp://w  ww.w3.org/2001/XMLSchema}long" minOc curs="0"/>
         *          &lt;/sequence>
   *     &l       t;/restrict   i  on>
 *   &lt;/complexContent>
 * &lt;/complexT   ype>
 * </pre>
 * 
 * 
 */
@X     ml      A  ccessorType(XmlAcc essType.F IELD)
    @   XmlType(name = "DebtorsDetails",   propOrder = {
    "debt   ors",
           "accountsReceivableTradeDebtors",
    "debtorsSubsidiaryAssociateJoint",
    "pr  epay  mentsAccruals",  
       "debtorsGroupL  oans",
      "debt  orsDirectorL  oans"     ,
       "otherDebt        ors"
  })
p   ublic class DebtorsDetails {

    @XmlElement(na   me = "Debtors")
    protected Long debtors;
    @XmlElement(name = "AccountsRecei      vableTradeDebt     ors" )
          p rotected Long accountsReceivableTradeDebtors;
      @X mlElement(name   = "DebtorsS     ubsidiaryAssociateJ oint  ")
    protected   Long debtors     SubsidiaryAssociateJoint;
    @XmlElement(name =   "Prep    aymentsAc  crua       ls")
    pr   o     tected L    ong pre       paymentsAcc ruals;
          @XmlEleme nt(name = "Deb    torsGroupLoans")
    protect     ed Lo    ng debtorsGro upL oans;
       @XmlElement(name = "De     btorsDirecto  rLoans  ")
    protected Long d       ebtorsDirectorLoa     ns;
    @XmlElement(name = "Othe      rDe btors")
        pr    otected   Lo    ng otherDeb        tors;

       /*   *
         * G     ets    the val    ue         of    the d  ebt     ors p  rop          erty.
          *     
                       *      @re    tu    rn 
                               *     po    ssible obj        ect is
            *                   {@link Long }       
     *                     
              */
    public     Long getDe   btors()  {
                 return d   eb      tors;  
    }

      /**
     *      Sets the value o     f             the deb            tors p      roperty.           
           *      
     * @    p    aram   val   ue
        *        allowed object is
     *        {@link L     ong }
          *                    
       */ 
         publi    c void setD  e             bto   rs         (Lo   ng     v alue) {
                     thi  s.          debtors = va  lue    ;
        }

    /   **
     * G    ets      the v    alue of the accountsReceivabl   e TradeD   ebtors propert   y.
           *   
     *      @   return
      *      p  os      sible obje ct is
       *     {@l    ink Long }
            *     
     *  /
       p            ublic Long getAcco   u   ntsRec eiv     ableTrad eDe  btors( ) {
               r    eturn accou ntsR   ece      iv       ab  le     TradeDebto   r           s;
               }

    /**
     * Sets the value    of the accountsReceivableTra  deDebtors pr  operty.
       * 
         * @ pa       ram va     lue
        *     allowed          obj  ect    i    s
              *      {@link Long   }
        *       
          */
    public    vo    i d setAccountsRece     ivableTra             deDeb     to rs(Lo n g v    alue   ) {
        th i s.acc  oun       t sRec  eivableTradeDebtors =   va            lue;
               }

    /**
     * G  ets the   value o    f t    he debtors        Su   bsidi     aryAssociateJoin                t property.
       * 
     *                       @return
                                *         possib              le object i   s 
                       *     {   @link Long }  
     *     
                       */
    p     ublic       Long getDebtorsSubsidiaryAssoci  ateJoi   nt    () {
        ret     urn   debto   rsSubs    idiaryAs    sociate    J           oint;
              }

    /*   *
                       * Sets   the value     of the debtorsSubsidia  ryAssociat     eJoint pro      p       er   ty.
          *      
                * @p          ar a              m v           a l   ue
            *           allowed object i   s
         *        {@lin    k Lo    ng    }
     *     
     */
                   public v     oid se    tD        eb     torsSubsidiaryAs      s     ociateJoint(Long value) {
        th            is     .debtor   sSubsidi          ary   Assoc   i     a          teJoint = value;
    }

    /**
           * Gets the                    val   ue of t  he             prepay           m entsA c cr      uals prop  e  rty.
           *     
           * @return
     *     possibl        e obj    e ct is
                       *          {@link                  L           on   g    }
                *             
      *  /
       public    Long g            etPrepaym     e    nts  Ac     cr  ua l      s() {
          return     prepa  yments Ac            cruals;
    }

    /**
          * Sets the      valu       e o    f the pr  e      paym ent   sAccruals pro  pe         rt     y.
     * 
        * @param value
     *     a    llowed obj  ect is
     *           {@link Long }
     *     
     */
            public void setPrepaymentsAccruals(Long val    ue)          {
                         this.    prepaymentsAc  cr    ual s =          value;
    }   

    /**
         * Gets the val    ue of the debto rsG    r     ou   pLoans propert           y.
     * 
         *     @retur    n
     *             possible object is
        *          {@link Long }
       *                   
       */
      public Lon g getDebtorsGroupLoans() {
        return debtorsGr          oupLoans;
         }

             /**
     * Sets the value   of the debto       rsGroupLoan   s property    .
     *    
     * @param    v    alue
     *                 allowe     d object is
     *     {@link Long }
     *        
     */
    pu    blic  void setDebtorsGrou  pLoans(Long value) {
          this.debtorsGroupLoans = value;
          }

    /**
            *      Gets the value of the debtorsDi   rectorLoans property.
     * 
     * @return
       *      p               ossible object is
     *        {@link L    ong }
       *       
        *     /
    publi      c Lon    g getDebtorsDirectorLoans(     ) {
         retur  n debtorsDirectorLoans;
       }

       /*  *
        * Se  ts the value of the debtorsDirectorLoans property.
     * 
      * @param value 
     *     a llowed object is   
     *           {@link Long }
        *     
     */
    public v   oid setDebtorsDirec  torLoans(Long value) {    
        this.debtor    sDirectorLoans = value;
    }

    /**
     * Gets the value     of the otherDebtors prope     rty.
      * 
     * @return
      *     possible objec  t is
        *     {@link Long }
     *     
     */
    public Long    getOtherDebtors() {
        return otherDebtors;
    }

    /**
     * Sets th      e value of the otherDebtors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOtherDebtors(Long value) {
        this.otherDebtors = value;
    }

}
