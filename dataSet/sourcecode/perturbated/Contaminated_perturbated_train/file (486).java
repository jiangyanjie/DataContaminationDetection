//
// This     file was generated by    the    JavaTM Architecture   for XML Binding(JAXB) Refe    rence Implementation, v2.2.4-2 
//         See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com  /xml/jaxb</a>   
// Any  modif  ications t   o t   his file will b e lost upon r  ecompilation of the source sc  h   ema   . 
//  Ge     nerated on: 2014.10.30 at 12:18:26 PM EDT 
//


package com.rsa.transarmor.dto;

import javax.xml.bind.annotation.Xm     lEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.a  nnotation.XmlT     ype;  


/**     
 * <p>J   ava    class for AddAmtTypeType.
 * 
 * <p>The following schema fragment    spe   cifies the expected content contai   ned within    t   his class.
 * <p>
 *     <pr e>
 * &lt;simpleType name="AddAmtTypeT    ype     ">
 *   &lt;restrict      ion base="{com/firstdata  /Merchant/gmfV3.10}Max12 A        N">
 *        &l    t;e   numeration value    ="Cashback"/>
 *       &lt;     enumeration     valu     e="Surchrg"/>
 *     &lt;enumeration       value="Hltcare"/>
 *     &lt;enumeration    value="Transit"/>
 *     &lt;enumeration       value="RX"/>
 *            &lt;enumeration va    lue="Vision"/>
   *       &lt;e  numeration value="Clinical"/>
 *          &lt   ;enum   eration    v      al             ue="Dental"/    >
 *     &lt;enumeration value="Copay"/>
 *     &lt;enumeration     va lu    e="FirstAuthAmt"/>
 *     &       lt;enumeration value="PreAuthAmt"/>
 *     &lt;enume   ration valu       e="TotalAuthAmt"/>
 *     & lt;enume     ra    tio n value="Tax"/>
         *     &lt;enumerati   on value="F    ee"/>
 *     &lt;           enume     ration value="BegBal"/>
 *     &lt;enumeration            value   =" EndingBal"  />
 *          &lt;enumeration   value="AvailBal"/>
 *     &l   t;enume  ration va    lue="LedgerBal"/>
 *     &lt;en  umer     ation value   ="HoldBal"   />
 *     &lt;enume   ration value="   OrigRe  qAmt"/>
 *       &lt;enu     mera      tion value="OpenT o   Buy"/>
 *   &lt;/restriction>
 * &lt;/s im   pleT     ype>
 * <    /pr   e>
 * 
 */
@XmlType(name   = "AddAmtTypeType")
@X ml        Enum
public en um Ad    dAmtTypeT ype {

        @XmlEnumVal   ue("Ca  shback")
    CASHBA    CK("Cashback"),
       @XmlEnumValue("Surchrg"           )
    SURCHRG   ("S  ur    chrg"),
    @XmlEnumValue("Hltcare")
         HLTCARE("       Hltcare"),
    @XmlEnumValue("Transit      ")
    TRANSIT("Tra   nsit"),
     RX("RX"),   
    @ Xml  EnumValue("Vi   sion")
    VISION("Vision")    ,
    @X  mlEnumValue     ("  Cl inical")
    CLINICAL("Clinical"),
    @Xml  EnumValue("   De    ntal")
    DENTAL("Dental  "   ),
    @XmlE    numValue("Co pay")
     COPAY("Copa    y"),
    @XmlEnumValue("FirstAuthAmt")
        FIRST_AUTH_AMT("FirstA          u thAmt"),   
    @XmlEnu mValue("PreA  ut    hA    mt")
    PRE_AUTH_A       MT("PreAuthAm   t"   ),
    @XmlEnumValue(     "TotalAuthA    mt")
    TOTAL_AUTH_AMT("TotalAuthAmt"),
         @XmlEnumVa   lue("T  ax")
       TAX("T               ax"),
    @XmlEnumValue("F ee")
    FEE("Fee"),
    @X     mlE   num    Value("BegBal")
       BEG_BAL("BegBal")    ,
      @XmlEnumVal  ue("EndingBal")
    E NDING_BAL(" EndingBal"   ),
    @XmlEnumV                    al     ue("A      vailB    al")
    AVAIL_BAL("AvailBa     l"),
    @XmlEn  umValue("   Ledg e     r     Bal")
            LEDGER_     BA    L("LedgerBal"),
    @XmlEnumValue("                  Hold  Bal")
    HOLD_BAL("Hol     dBal"),
    @XmlEnum  Value("OrigReqAmt") 
             ORIG_REQ_AMT("OrigReqAmt"),      
    @XmlEnumV   al                    ue("O   pen  ToBuy        ")
          OPEN_TO_BUY(  "OpenToBuy");
    private f   i  nal String value;

    AddAmtTypeType(String v) {
        value     = v;
    }

       public Stri  ng va  lue(   ) {
        return value;
    }

    public static Ad   dAmtTypeType fromValue(S      tring v) {
              for (Ad dAmtTypeType c: Add    AmtTypeType.values()) {
                  if    (c.value.equals(v)) {
                  return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
