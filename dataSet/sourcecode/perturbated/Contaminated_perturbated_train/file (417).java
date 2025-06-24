
package com.transportation.SIRI_IL.SOAP;

import   java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
impo  rt javax.xml.bind.annotation.XmlType;


/*  *
 * Type   for  list          of a  ctions.
 * 
 * <p>Java c lass fo    r Action    sSt  ructure   c  omplex type.
 * 
 * <p>The follow ing schema fragment specifies the expected cont   ent cont  ain     ed within this class.
 * 
 * <pre>
 * &lt;com        plexType       name="ActionsStructure">
 *   &lt;c      omplexContent>
 *     &lt;restr     iction base="{http:  //www.w3  .       org/2001 /     XMLSchema}anyT  ype">
 *                &lt;se  quence minOccurs="0">
 *         &lt;group ref="{http://www.siri.org.uk/siri}ActionsGro       up"/>
 *         &lt;elemen     t name="Extensions" type=     "{http    ://www.     w3.org/2001/XMLSchema}anyTyp  e" minOccu  rs="              0"   />
 *       &lt;/sequence>
 *     &lt;/re   striction>
 *   &lt;/complexContent>
    * &lt;/complexType>
   * </   pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessTyp     e.FIELD)
@Xml Ty          pe(    n           ame = "ActionsStruct     ure", pro    pOrder = {
         "publishToW  e  bAction",
    "publishToMobileAction",
        "publishToTvAction",      
        "      publishToAlertsAction",
    "manualAc tion",
    "n    otifyByEmailAction",
        "      notifyB ySmsActio   n",
    "notifyByPagerAction",
     "notifyUserActi   on",
    "ex tensions"
})
public class ActionsStructure {

          @Xml   Element(name = "Publis   h   ToWebAction")
    protected List<  PublishToWebActionStructure>   p   ublishToW  eb  A   ction;
    @XmlElement(name = "    PublishToM  o    bileAction")
    protected List    <Publish  ToM     obileAc    tio  nStructur      e> publishToMobileActio   n;
      @XmlElement(name = "P  ublish    ToTvAction")
    prot  ected P   ublishToTvActionStructure publishToTvAction;
    @XmlElement(name = "Pub    lish     ToAlertsAction")
    p   rotected L    ist<PublishToAlertsActio  nStructur  e> publ  is      hToAlertsA    c    tion;
    @XmlElement(name   = "ManualAct  ion")
              protected List<ManualAction> manualAction;
    @XmlEleme nt(name = "NotifyByEmailAction")
    protected L  ist<NotifyByEmailActionStructure> noti  fyByEmailAction;
    @XmlElement(name = "NotifyBy   SmsAction"    )
    protected NotifyBySmsActio    nS tructure notif      yBySmsAction;
      @XmlElement(name = "Not ifyByPagerAction")
         protected List<Noti  fyByPagerAc tionStructure> notifyByPagerAction;
    @XmlElement(name          = "Not   ify    Use      rAction")  
        protected List<    N     otifyUse rActionStructu  re> not     ifyU      serA  cti on;
    @XmlElement(name =      "Extensions")
    protect  ed      Obje    ct extensions;

       /**
               * Gets the value of the pub   lishTo  WebAction         property    .
     * 
            * <p>
     *     This acce  ssor method returns a reference to the l  ive l ist,
     * not a snapshot. Therefore any mo  dification you make t  o t  he
            *   ret    urn    ed           list will     be prese   nt     inside the JAX  B o    b  j           ect.
     *        This    is wh   y the  re i          s not a <CODE>set</   COD  E> me     tho   d for   the pu      blishToWe bA    ction pr operty.
     * 
        *  <p>       
     *   For example, to             add a new item, do as     fo  llows:
         * <pre>
                   *               getPublis     hToWebA  ction().add(n     ewItem);
           *   </  pre>
           *  
     *   
             * <p>
            *    Objects o    f the following type(s) a re allowed i   n          the list
     * {@link P     u       blishT oWebActionSt  ructu   re   }
     *   
         * 
             */
    pu       blic        List<      PublishToWeb   Actio  nStr      uctu re> getPublishToWebActi   o    n() {
        if    (publishToWebAc  tion == null) {
                 publishT   oWeb   Acti      on       = ne      w        Arr  ayList  <P        ub    lishToWebAction          St                ructure>();
        }
         r eturn this.pu   b  lishToWebA      c     ti   o  n;
         }

    /*  *
     * Gets the value of the       publ   is        hToMobileAction prope   rty.      
     *     
     * <p>
     * This       accessor   meth od                returns a refer      ence to t   he live list,
     * not a      snapshot.   The          refo   re any modific  at     ion    you mak   e t   o the
     * returne    d list will be p        resent ins  i        d e the JAXB obje   ct.
     * This is why the         re i   s not a <CODE      >set     </C   OD     E>      method f   o       r t         he publ   ish         ToMobileAction prope    r                ty.
     *    
          * <p>
     *  For e    xam  ple,        to ad          d a n ew item, do as foll      ows:
       * <pre>
     *         get  Publish           ToMobileAction().a  dd(n     ewItem);
     * </pre          >
     *    
         * 
     * <p>
            * Ob  jects of the    following type   (     s) are  allowed in      t  he list
           * {@link        PublishToM  obileAc ti    onStruct    ure   }
           * 
     * 
                */
    pu  b   lic       List   < Publis  hTo M     obil   eActionSt  ructure> getPub lish         ToMobileAct     ion      () {    
              i       f   (publishToMob       ileAction == nu      ll) {
                pub   lishToMobile  Ac   ti   on = new    ArrayList<Publish    ToMobileActionStru  cture>   ();
         }
                retu      r      n this.publishT          oMob      ileAc      ti   on;   
    }    

          /**
          * Gets   the value of the publishToT      vAction prop       er   ty.
     * 
              * @return
     *           possi          ble object is
     *     {@link PublishT  oTvAc                    tion Structu     r    e     }
                   *         
     */
    p  ub l      ic PublishT       oTvActio nStruc    ture getPubl   ish   ToT    vAc  tion() {
               return     p    ublishToTvA ctio  n;
    }
       
       /**
     * Sets the va  l          ue of the publish  To       TvAc    tion proper    ty.
            * 
        *       @param v     alue
     *             allowed object is
          *        {@link Publi       shToT  vAc   tionStructu   re     }
     *     
              */
    public    vo   id setPublish         ToTvA  ction(PublishToTvAc   tionS   tructure value) {
           th           is.publi  shToTvAction = va   lue;
    }   

    /*  *
     *     G    ets the value    o   f   the pu     blishToAlertsAction property.
             * 
     * <   p>
     * This acc essor method             returns a  reference to the     live list,  
            * not a     sn     a    pshot.      Ther  e    fore an  y modif ication y      ou m    ak    e to the
     *    ret      u  rned list will be present insi      de the JAX B o      bje       ct        .
            * This is why there is not         a <CODE>se  t</    C  ODE> method for      the           pu      b lishToAl    er     tsA  ction                  pr      o per      ty.
         *  
             * <p   >      
              * For examp     le     , to add a         new item , do a     s    foll   ow   s   :
     *     <pr    e >
     *           getPublishT     oAlerts   Ac tion().add(newItem);  
     * </pre>
     * 
       * 
     * <p>
     * Objects of the following    type(s) are allowed in the li   st
     * {@link Pu blishToAle   rtsAction Stru     cture }         
     * 
        *     
     *  /
          pu   bli   c List<Publi sh        ToAlertsActionStructure>   getPub  lishToAlertsAction() {
                if (pu      blish     ToAlertsAction == null) {     
             publis       hTo  AlertsAc   tio   n =     new Arra yList    <  Publi shT oAl  ertsActio              nSt   ru    ctur     e>();
        } 
        r    eturn th      i s.publishToAlertsActio  n  ;
      }    

    /**
     * Gets    the  value of the manualAction property.
            * 
                * <p>    
     * Thi   s accessor    method     ret   urns a re       ference to the   live list,
     * not a snapshot. T   h   e refore any  modif       ic ation      you make to the  
         *  returned list wil  l be pr           esent ins       ide   the     JAXB obje  ct.
     *         This       is why there is n  ot a   <CODE>set</CODE> method for t       he m     anualAction p    roperty.
     *     
     * <p>
     * For exam    ple, to add a new item, do as    fol    lows:
            * <pre     >
     *    ge   tManual  Action(    ).   ad   d(newItem)  ;
     * </pre>
        * 
     *         
                           *       <p>
     * O    bjects of    t    h e followi  ng type(s) a      re    al      lowed in t   h e list          
     * {@l  ink  Ma  nual   Act  ion }   
         * 
           *  
     */
      pub      lic    List<Manual   Action> get    Manu alAction()   {
                             if       (ma       nualAction        ==  null)           {
                  manualAction = new ArrayLis    t<ManualAc          tion>();
        }
        ret ur   n  t his.manualAct          ion;
    }

     /**
      *      G  ets   the v    alue of the n   otifyByEmailAc   tion property.
          * 
     * < p>
        * This accessor    method       re  turns a re ference to the l   ive li  st,
     * n   ot a            snap    shot.   The r  efore any     modi    f   ication y  ou make to the
      * re turned list       w     ill be prese   nt        inside the JAXB ob   ject.
     * T  his     is w  hy t here is not a <CO   DE>se    t<    /CODE> metho               d for the  notify ByEmailAction property.
            *   
      * <p>
     * For    exam   p le, to add a n    e  w item, do      as follows:
        * <pre>
     *                   getNotify   ByEm    ailAction().add(n     ewI  tem)  ;
        * </pre>
         * 
            * 
     * <p>
      *     Objects of the fol      l   o    wing    type(s) are allowed in  t      he     lis  t
                   *   {@l    ink No   tifyByEmailActionStructure }
     * 
       * 
     */
    public   L       ist<         NotifyB    y    Ema       ilActio       nS  tructure  > getNoti          fyByEma  i  lA  ct         ion  ()    {
        if (notif         yByEmailA       ction == null) {
                        n    otifyByEmailAction =    n   ew A     rrayList<     NotifyByEm      ailActionStructu re>();
        }
         re        turn this.n      o  ti   fyByEma   ilA   ction;
     }

        /**
           * Gets th    e value of t   he notifyByS msAc         tio   n property.
     * 
     *  @r  eturn
       *                        possible ob         ject is
          *        {    @link     NotifyBySms     ActionStructure    }
     *          
     */
    publ  ic       NotifyBySmsA   ctionStr    ucture    getNotifyBySmsAction()    {
        return notif      yBySmsAction;  
    }

    /**   
             * Se  ts the value of the n      otifyBySmsAction pr o pe rty.
     *    
     * @param   value
     *     allowed object is
          *            {@link NotifyBySmsActionStru   cture }
            *        
     */
     public void se  tNotifyBySmsAction(   Notify   BySmsActionStructure value) {
        t   his.not   ify  BySmsAction =   va      lu      e;   
    }

    /**
     *    Gets          the value   of the      notify  ByPagerAction property.  
     * 
        *  <p>
       * This   a           ccess     or method returns  a      referenc            e to the live list,
     * n   ot a        snapsho   t. Therefore any modific   ation you make to the
          * ret   urned list will b  e p   re  sent inside the JAXB object.
     * This is  why there is not a <   CODE     >set<      /CODE>   method for the notifyByPagerAction property.
           * 
     * <  p>   
     * For example, to    add a ne  w item,    do        as       follows:    
     * <pre>
         *    getNotifyByP    agerAction().add(n  ewItem);
       * </pre    >
          * 
       * 
      * <p >
       * Objects of the follow ing     type(s)  ar  e  allowed in the list
     * {@lin   k NotifyByPagerActionStructure }
      * 
         * 
       */
             public List<Notify   ByPagerActionStru   cture> getNotifyByPagerAction() {
        i    f (noti    fyB     yPagerAction  == null)     {
            noti    fyByPagerAction = new ArrayList<Notify  B    yP     a  gerActionStructure>();
                      }
        return this.notifyByPagerAction;
         }

       /**
     * Gets the value of the notifyUserA      ction      property.
     * 
     * <p >
     * This accessor      me  th  od    returns a referenc   e to the live l  ist,
     * not a snapshot. Therefore any modificatio        n you  mak  e to the
     * returne   d     l       ist    will be presen    t inside    t    he JAXB object.
     * This is why there is not a <CODE>set</C ODE> method for the no tifyUserAction    property.
     * 
     * <p>       
     * For example, to add a  new item, do as follows:
     *     <pre>
     *    g      e   tNotifyUserAction().add(ne   wItem);
     * </pre>
     * 
     * 
     *   <p>
     * Objects of the fo   llowing type(s) are allowed  in the list
     * {@link No tifyUserActionStructure   }
     * 
     * 
     */
    public List<NotifyUser   ActionStructure> getNotif     yUserAction() {
                  if (notifyUserAction == null) {    
            notifyUserAction = new ArrayList<   NotifyUserActionStructure>();
        }
        return this.notifyUserAction;
    }

    /**
     * Gets the value of the extension s property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    publ  ic Object getExtensions() {
        return extensions;
    }

    /**
     * Sets the value of the extensions property.
            * 
     * @param value
           *     allowed object is
     *     {@link Object }
     *     
     */
    public void setExtensions(Object value) {
        this.extensions = value;
    }

}
