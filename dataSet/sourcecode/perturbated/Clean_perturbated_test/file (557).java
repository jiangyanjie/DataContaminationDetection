//
//   This file was    genera          ted by the JavaTM Architect    ure for XML Binding(JAXB) Reference Im    plementation, vJA       XB 2.1.1  0 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xm   l     /jax        b</a> 
// Any modificati ons to this file   will be lo    st upon recompilation of the sour ce schem    a. 
//    Generated on: 2010.02.  24 at   10:55:05 AM CST 
//


package org.atdl4j.fixatdl.layout;

import java.util.Arr ayList;
import java.util.L ist;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.Xml  AccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.Xm    lSeeAlso;
import javax.xml.bind.annotation.XmlType;
    impo   rt or    g.atdl4j.fixatdl.fl        ow.StateR        uleT;


/**
 * <p>Java class    for Con        trol    _t complex type.
    * 
 * <p>The followi ng schema fragm ent    specifies the ex  pected content contained   with    in this class.
 * 
 * <pre>
 * &lt;comp  lex   Ty    pe name="Control_  t">    
 *      &lt;complexConte        nt>
 *       &lt;restriction      base="{http://  www.w3.org/20   01/X      MLSchema}anyType">
 *          &lt;sequence>
 *         &lt;element ref="{http://www.fi    xprotocol.org/FIXa  tdl-1-1/Flow}StateRule  " maxOccurs="unbounded"    minOccurs="     0"/>
   *                    &l    t;        element name="HelpText" typ  e="{http://www.  fixprotocol.org/FIXatdl-1-1       /L                 ayout  }      HelpText_t" mi  nO   ccurs="0"/>
 *       &lt;   /seq       u  ence>
 *           &lt;attri       bute name="ID" use="    required">
 *         &   lt;simpleTyp      e>
 *                 &lt;restriction base="{ht  tp://www .w3.org/  2001/XM     LSch      em a}strin    g">
 *                  &l    t;patter       n value=      "[A-Za-z][A-za-z0-9_]{0    ,   255}"/>
 *                    &lt;/re   st    r   icti    on>
 *           &lt;/simpleT  ype>
 *                      &lt;/a    t   t    ribut e>
     *              &lt  ;attribute name="paramete      rRef"     >
 *                          &lt;simpleType>
 *                           &l    t;restriction base="{h    t   tp://www.w3.    o  rg/2001      /XM   LSche   ma}string">      
 *                  &lt;patte rn value="[A-Za-z]  [A  -za-z  0-9_]{0,255}"   />
 *           &lt;/re     striction>
 *          &lt;/simpleType>
     *       &lt;/    attri  bute>
 *        &l   t;attribute name="label" type="{http://www  .w3.org/2001/XM   LSc   hema}string" />
                *          &lt;att     ribute name=     "initFixFie   ld" type="{http://w ww.w3.org/  2001/XML   Schem    a}string"     />
 *       &lt;   attribute nam   e="init  Policy"        >
 *            &lt;s    im    pleTy      pe>
 *              &lt;restric    tio  n base="{http://www.w3.o  rg/2001/XMLS     chem a   }string">
 *                  &lt;enumerati      on value="UseValue"/>
 *             &    lt;enumerati  on    value="U seF   i xFi    eld"/>
        *                        &lt;/restriction>
 *               &lt    ;/simpleType>
 *          &l           t;/attribute>     
 *       &lt;attribute name="to    o         ltip" type="{ht        tp://www.w3.o   rg/2001/XMLSchema}stri  ng" />
 *            &lt;attribute  name="d   isableForTemplate" type="{http://www.w3.    org/    2001/XMLSchem  a}boolean"      />
 *        &lt;/   restriction>
 *   &lt;    /complexCont   e    nt>
 *   &lt;/complexType>       
 *     <      /pre>
 * 
 * 
 */
@XmlAccessorT   ype(XmlAcces   s Type.FIELD)
 @XmlType(name = "Control_t", propOrder = {
    "st        a   teR   ule"  ,
    "hel    pTe  x   t"
})
@XmlSeeAlso({
     Dou  bleSpinnerT.class,
    M           ultiSelectListT.cl     ass,
    Lab     el   T.class,
           CheckBox    ListT.class,
        Rad    ioB  utt   onT.class,
    Single  SelectList   T.class,
    CheckBoxT.class,
    HiddenFieldT.class,
    EditableDro  pDownListT.class,
    DropDownListT.class,
       RadioButtonListT.class,
    ClockT.c lass,
       SingleSpinnerT     .clas       s,
          TextF   ieldT.cla  ss,
    SliderT.class   
})
public abstract class Contr   olT {

    @XmlElement(n  ame = "St   ateRu   le", na         mespa     ce = "http://www.fixpro               toc    o   l.o        rg/FIXat     dl-1-1/Flow")
      protec ted List<StateRuleT> stateRule;
    @    Xml Element(name =   "Hel   pText" )
    pro    tec     ted  String helpText;
    @XmlAttribu te (name = "    ID", re   quired =      true)  
    prote    ct  ed S             tring id;
     @   Xm lAttribute
        p       ro     t  ected String parameterR   ef     ;
    @XmlAttribute
              protecte     d     String    label;
      @   XmlAttrib  ute
     protecte      d S      tri   ng initF ixFie    ld;
          @XmlAt  trib    ute
    protected String initPolicy;
     @XmlAttribu         te  
                protect         e   d      String          tooltip ;
    @Xm  l     Att          rib  ute
    protec ted Bool  e  an disableForTemp la      te;

    /**
     * G   ets     the value of the stateRule proper  ty   .
     * 
        * <p>
             * This access        o         r   me   thod r        eturns a r e      f     ere       n ce to th      e live list,   
       * not a sn a        pshot. T          here     fore a     ny m    odificatio    n you m    a    ke to the
                   * re    turned lis   t     will be pres   en t i            nside   the     JAXB      object.
                  * This       is     why the   re is not      a <CO      D   E>set</CODE> method for       the     stateRule propert    y.
       * 
      * <   p>
     *                  Fo r examp  le,    to add             a  n       e   w item, do    a    s foll       ows:
         *  <    p  re>
     *      getStat      eRule().a           dd(ne        wItem);
           * </pre     >
     * 
           *      
     * <p>
     * Ob      j     ects of th     e fo    llowing ty       pe(s) a re a   llow ed i    n the l ist 
     * {@li     nk Stat           eR     uleT }
                 * 
            *   
              */
      publ        ic L      ist<StateR     u      leT> getStateRule() {   
        i      f (stateRule == null) {
                       stateRule = ne       w Ar rayList<S tateRuleT>    ();
            }
         retur  n this.     st   ateRule;
         }

                   /*   *    
     * Get  s the      va   lue     of the helpTex  t p    roperty.
     *     
                  * @re  tur    n
                *              p      ossible    object     is
           *              {@link String }
                       *     
        */
            public Strin      g getHelpT ext(  ) {
                 re t                                 urn    help     Text;
              }

              /*  *
       *   S   ets    t      h   e v              al         ue     of the help  Text property .
               * 
         * @param v  alue
                 *     allowe     d   object     is 
       *     {@lin    k S  trin     g }
     *     
     */
        pu      bli c void setHelpText(S    tring va     lu    e) {
        th      i     s     .helpText =        value;
       }    

               /*   *
        * Gets          the value    of the id prop e rt       y.
           * 
               * @   ret             urn
     *     po  ssibl   e     obje          ct is
            *                {@link    String }
      *     
         */
       publi  c String getID()     {
            retu    rn i        d   ;  
    }     

                        /*  *
          * Sets the        value o  f the        id pro  per          ty.
     * 
     * @  param value
          *     al   lo        wed  o   bject                               is
       *                                  {    @  link String      }
              *        
           */         
        publi   c void setID   (String value)    {
           thi             s.i          d  =   v    alue;
    }   

      /* *
     *   Get s th   e va      lue of the          parameterRef   pro           pe      r   ty.
           * 
     * @retu          rn  
     *       possible        obj  ect is
          *     {         @   l    ink St         ring            }
     *        
     */
         public       St    ring getParameterR   ef() {
          retur     n par       ameter      Re  f  ;
    }

      /   **
        *    Sets t    h         e  val ue    of t  h   e pa   rameterR   e   f                 pr   operty.  
           * 
            * @param value
     *     allowed object is
      *     {  @link String }
     *     
      */
    pub lic vo   id se  tP    ar       ameterRef(Str    ing              value) {
                       this  .pa   r      ameterRe              f = valu        e;
         }
   
    /**
          * Gets the value o   f the label p  rop  erty.
     * 
     * @return
     *                      possib  le object      is
            *     {@link St   ring }
     *      
     */     
       public Str    in     g getLabel()    {
                r    et  urn la   bel;
    }

       /**
     * Sets t   he value of    t     h   e l    a         bel    pro  perty.
            * 
     *        @p  aram      value
            *             al  lowed object   i s
     *               {@link String }
     *       
     */
            public void setLabel(String value) {
                             this.label = value;
        } 

    /**
                * G      e   ts             the     val  u       e     of the initF  ix  Field     propert y     .
     * 
            * @return
       *          possibl     e object is
        *        {@lin k St  ring }
              *     
     */
    public S  tring     getInitF     ixFi      el          d() {
                 return initFixField;
       }

    /    **
      * Sets  the va     lue    of   the initFi   xField property  .
      * 
     * @param value
              *     allowed object is 
     *     {@link String }
     *     
     */
      public void   setInitFixField   (String value         ) {    
        this.initFi  xF           ield = value;
     }

    /**
         * Gets the value of the initPolic     y property.
     * 
     * @retu  r   n
     *     possibl       e object is
           *     {@link Stri         ng }
     *     
     */
    p   ublic Str  in      g getInitPolicy() {
        return initPol       icy;
        }

        /**
     * Sets the value   of the initPolicy propert   y.
     * 
     *     @pa   r  am value
     *     allowe d obj  ect is
     *     {@link String    }
        *         
     */
    publi      c void se  tInitPol   icy(Strin g      value) {
        this .initPolicy = value;
    }

       /**
     * Get s the value of the tool  t      ip property.
          * 
     * @return
      *     possible object i   s
     *     {@link String }
     *     
     */
       public String getTooltip() {
               return tooltip;
    }

    /**
         * Sets the value of the tooltip prope     rty.
     * 
     * @param value
        *        allowed object is
     *         {@link String }
     *      
     */
    public void setTooltip(String value) {
        this.toolti p = value;
    }

    /**
     * Gets the value of the disableForTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link    Boolean }
     *     
     */
    public Boolean     isDisableForTemplate() {
              return disableForTemplate;
      }

    /**
     * Sets the value of the disable ForTemplate property.
     * 
         * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisableForTemplate(Boolean value) {
        this.disableForTemplate = value;
    }

}
