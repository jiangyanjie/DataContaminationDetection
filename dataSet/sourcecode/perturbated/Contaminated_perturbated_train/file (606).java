
package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import   javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Ty pe for Connect  ion Timetable Capabilitie    s.
 * 
  * <p>Java class f or ConnectionTimetableServiceCapabilitiesStructure complex typ    e     .
 *   
 * <p>The      f    ollowing schema fragment specifies   the expected content  c  on  tained within this class.
 * 
 * <pre>
     * &lt;complexType name="ConnectionTimetableServiceCapabili         tiesStructure">
   *                 &lt;complexCo    ntent>
 *     &lt;extension base="  {http://www.siri    .org.uk/siri   }A  bstractCa   pabil  itiesStructure">
 *       &lt;se      quence> 
 *         & lt;eleme         nt name="TopicF    i   lteri     ng"  minOccurs="0">
 *                             & lt;compl  exType>
 *                           &lt;complexConte    nt>
 *                            &l   t;re     strict    ion base=   "{http://w  ww.         w3.o  r g/2001/ XMLSchema}a     nyT   ype">
 *                     & lt                  ;s          eque   nc     e>       
 *                               &lt;element r      ef=  "{ht   tp:/  /www.sir   i  .org.uk              /s iri}  F     ilterByLin eRef"/>
      *                   &lt;el         ement r    ef="{htt   p://www.       s     i       ri    .    org.u             k/siri}FilterByCo  nnect ionLinkRef    "/      >    
 *                                     &l  t;/seque        nce>
 *                                      &lt;/restr    iction>
 *                                   &lt;/complex Content>     
 *                     &lt;/   comple     xType>     
 *                             &lt  ;/ele m ent>
    *               &  lt;element name="Reques   tPolic     y" mi nOccurs =         "0">
 *                              &lt;complexType>
      *                &lt;complexContent>
 *               &l     t;exte   n  sion base="{http://ww         w.si     r   i       .  org.uk/siri   }Cap     abilityReques     tPo  licyStructure     " >
 *                   &lt;sequ   ence        >
 *                       &lt    ;element name= "Foreig  nJourne ysOnly" type=    "{http://www.w3.org/2001/XML   Schema}boolean" mi   nOccu    rs="0"/>
    *                     &l  t;/sequence>
 *               &lt;/extension>
 *                 &lt;  /complexCont  ent>
 *           &lt;/complexType>
 *         &  lt;/eleme   nt>
 *         &lt;element name="Subscripti   on  Policy" type="{http://www. siri.org.uk/siri }Capability     Subscripti    onPolicySt ructure" min  Occurs="0"/>
 *         & lt;elemen t na    me="AccessControl" type   ="{http://www.siri.org.uk/siri}Connectio   nCapabilit   y             AccessControlSt   ructure" minOccurs="0"/>  
 *                   &lt  ;element   ref=     "{http://www.sir    i.or g.uk/siri}Extensions" minO    ccurs="0"/>
 *       &      lt;/sequ    ence>
 *     &lt;/extension> 
 *   &lt;/complexContent>
 * &lt;/complexType>      
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name =     "ConnectionTi   metableServiceCapabi litiesStruct    ure", propOrder = {
      "topicFilt ering",
      "re  questPolicy",
      "subscriptionPoli        cy",
    "accessControl",
              "extensions"
})
public class ConnectionTimet     able   Ser   viceCapabilitiesStructure
    ext    ends AbstractCapabilitiesSt   ructure
{

    @ Xml  Element(name = "TopicFiltering")
    protected ConnectionTimetableServiceCapabi li  tiesS  tructure.T  o       p    icFiltering topicF iltering;
    @X        mlElement(name =     "RequestPolicy")
      protected Conne     ct  ionTimetableServ         iceCapabi         litiesStructure.RequestPolicy requestPoli    cy;
    @XmlE lement(na     me = "    Sub     scriptionPolicy")
    protected CapabilitySu    bscriptionPol  icyStruc    t           u   re subs    criptionPolic       y;
         @   XmlElement    (name =    "AccessC on    trol")
    p       rotected C  onnect    ionCapabilityAccessCont rolStructur   e a    ccessCont  rol;
    @XmlEl    e   m   ent(name = "   Ex  tensions")
           protected Ext    e      nsionsStru      cture ext   ensions;

       /**
     * Gets t       he va lue of the t  op   icFilte ring pro      perty.
       * 
     * @r eturn
         *               possib     le            o  bject   is
          *     {@lin  k ConnectionTimetableS     erviceCapabilitiesStructure.       Top  icFiltering }
        *         
      */
    p  ublic Conn      ectionTimetableSe     rviceCa  pabilities  St     ru   cture.TopicF   iltering getTo  picFil     tering() {
        return        topicFiltering;
    }

    /**
     *    Se          ts th  e va   lue           of the top   i  cFil  tering       prop    ert   y.
     * 
     *   @param value
            *         allowed obj     ect    is
       *       {@li nk   Con   nectionT  imetab         leServic  eCapa   bil       iti   es   Structure.TopicFilte       ring }
     *         
     */
         public vo      id setTop    icFilterin    g(Connec  tion       Ti   metableS  erviceCap  ab  ilitiesStructure.TopicFilt    ering value) {
        this.topicFilteri   ng = value;
    }

    /**
       *       Gets the valu     e   of the re  questPolicy property.
           * 
     * @return
     *                 possi   ble obj ect      is
     *     {@li    nk C  onne     ctionTi   meta    bleS     erv    iceCapabi  lities     Structure.RequestPol    icy        }
       *       
          */
           public Connec   tionTimet  a b    leServi c    eCapabilitiesStructure.Request          Policy getReque stPoli      cy() {
         retu rn requ estPo     l icy;
        }

            /**
                * Set     s          the val ue of the requestPol         icy pro        pert     y.
       * 
              * @param value 
     *     allowed    object  is
                *     {@         link Conn ectio   nTimetable   ServiceCap    abilitiesStructure.Req  u es    tPolicy }
     *     
         */
    publ     ic void setRequ    estP      olicy(Co    nn    ectionTimetableSer    vi     ceCa   pabiliti e  sStructure.RequestPoli    cy value) {
                 this.re      qu      es                    tPoli cy = va   lu    e;
    }

    /**
     * Get      s the value of the subscriptionPoli  cy pr  o    perty.
            * 
                      * @r             e  tur  n  
     *     possible object is 
     *          {@li nk Ca     pabilitySu bscriptionPoli     c              yStruc    t    u       re }
     *     
                */ 
    public Capabilit   yS      ubscriptionPolicy   Str   ucture ge    tSu bscription   Policy    () {
         ret         urn    subscript  ionPolicy;
    }

    /**
     * Sets the value of the sub               scriptio      nPolicy property.
        * 
     *   @param v  alue
        *     al  lowed              object is
        *         {@link  Capabili   tySubscription                       Pol   icyStructure }
             *     
     */
          pub  lic void set     Subscriptio              n    Po    lic   y(CapabilitySubsc  ript  i   o   nPolicyStructure value) {
             this.subs criptionPolicy =        value;
    }

         /**
        * Gets the valu   e of     the ac cessControl property.
            * 
        * @r    eturn     
            *            pos   sible object is
     *                                  {@link ConnectionCapabilityAc     ce      ssControlSt   ructure   }
      *        
       */
    public Connecti  onCa  pabilityAccessContr  olStructure getAc     c essCo         ntro     l() {
          ret urn accessControl;
    }
   
        /**
     *  Sets the    value of the accessCont     rol p        roperty.
     * 
     * @param     v    a  lue
           *               allowe   d ob        je  ct is
     *        {@link      Co  n  ne       ctio    nCapabilityA   ccessCo      ntrolStruct    ure }
         *     
     */
    publ   ic   v    oid setA         ccess           Con        tr     o         l(ConnectionCa            pabil        ityA   ccessCont  rolStructure v  alue)     {
                 t           his  .access            C       ontrol = va  lue;
                }

               /**
       * Gets the va  lue of    th e extensions  prop        erty.
            * 
     *    @return
             *     pos   si     bl e objec   t i s
           *        {@link      Ex tensionsS                   t   r  u    c   ture }
                     *     
               */
          p   u   blic Extension   s   Structure                ge  t Ex      tens   ions() {
             re   turn   extension     s;
        }  

        /**
          *  S    e     ts the value of the      ext   e n sion  s         pro   perty.
     * 
             * @   param value
     *       allowed object      is
     *     {@l    in k E  xtension sStructur   e }
                *     
                    */
       pu  b  lic void      setEx   tensions(E      xtensi  ons  Structu     re val          ue) {
                         this.             ex  tens    ions =  v al        ue;
    }    


    /**
     *  <p>Java             class for anonymous complex type.
     * 
          * <   p>T  he foll   owing schema fragm  ent specifies the ex pect             ed     c     onte    nt contained within this cl  a    ss.
     * 
     * <pre>
     * &lt;complexType>
               *        &lt;c    omplexConte    nt>
     *              &lt;extens     ion   b  ase="      {http://www.siri .org.u     k/siri}C          apabilityReq     uestPolicySt   ru       cture">    
                       *             &  l  t    ;s    equen          ce>
             *                &   l        t;el      ement           n     ame="ForeignJ ourn     ey         sOn             ly" type="{   http://www.w3.org/2001/X     MLSchema}   b  oole     an"     minOccurs="0"   />
     *         &lt  ;      /seque nce>
     *             &lt;/extension>
      *        &lt       ;/comp  lexC    ontent>
             * &lt;/complexT  ype>  
                * <     /pr  e>
         * 
         * 
          */
    @X    mlAcces   sorT      ype(XmlAc ce   ssType.FIELD )  
       @        XmlT    ype(  name = "", propOrd    er = {
              "forei   gnJ   o     urneysOnly"      
     })
         pu   blic static class RequestPolicy
        extends Cap  abi                 l   ityRequest    Polic  yS     t ructu  re
       {

        @X  mlEl     ement(name          = "Forei gn  Journey  sOnly", defaultVal                u    e      = "fals    e"  )
                   protec   ted Bo    olean foreignJourneys   Onl   y;
     
                   /**
         * Ge  ts the value of the foreignJourneysO     nly property.           
                    * 
           * @re   t urn
             *     possible object is 
          *      {@li  nk Bool  ean }
          *            
         */
        public Boolean isForeign  Jour     neysOnly() {
                 retu  rn fo   reign    JourneysO nly;
                }

          /**
         * Sets    the value of th  e foreign JourneysOnl    y property.
            *        
         * @param    value 
             *       al   lowed object i     s
         *                  {@    link Boolean   }
         *     
                      */
                   public v  oid setF   oreig   nJourneysOnly(Boolean value) {
                      t      his  .foreignJo   urney     sOnly       = value;
               }
   
        }


    /**
     * <p>   J     ava    class for anony                  mous complex type.   
     *      
     * <p>The following schema fragme    nt specifies the expected content con   taine  d within this class.
     * 
     * <pre>  
     * &lt;complexType>
     *        &lt;complexContent>
     *     &lt;restr    iction base="{http:/     /www.w3.org    /2001        /XMLSch     ema}anyType">
     *          &lt;seq    uen   ce>
     *             &lt;element ref="{   http://www.siri.org.u    k/siri}FilterByL       ineRef"/>
     *         &lt;element ref="{http://w          ww.si  ri.org.uk/siri}FilterByConnectionLinkRef"/>
         *       &lt;/seq  ue     nce>
     *     &lt;/restriction>
     *   &lt;/complex    Cont  ent>
     * &lt   ;/complexType>
     * </pre>     
     * 
     * 
      */
    @Xm       lAccessorType(XmlAcce   ssTy    pe.FIELD)
    @X    m   lType(name = "", propOrder = {
         "filterByLineRef",
        "filterByConnectionLinkRef"
      })
    public st     atic class TopicFiltering {

          @XmlElement(name =   "FilterByLineRef", defaultValue = "true")
             pro  tected boolean filterByL   ineR  ef;
        @X   mlElement(name = "FilterB    yConnectionLinkR   ef", defaultValue = "true")
        p rotect    ed boole    an filte  rByConnecti   onL inkRef;

        /     **
         * Gets the value of th     e filterByLineRef property.
         * 
         */
        public boolean isFilterByLineRef() {
            return filterByLineRef;
        }

        /**
         * Sets the value of the filterByLineRef property.
         * 
         */
        public void setFilterByLineRef(boolean value) {
            this.filterByLineRef = value;
        }

        /**
         * Whether results can be filtered by Connectio  link. Default true
         * 
         */
        public boolean isFilterByConnection      LinkRef() {
            retu rn filterByConnectionLink   Ref;
        }

        /**
         * Sets the value of the filterByCo  nnectionLinkRef property.
         * 
         */
        public void setFilterByConnectionLinkRef(boolean value) {
            this.filterByConnectionLinkRef = value;
        }

    }

}
