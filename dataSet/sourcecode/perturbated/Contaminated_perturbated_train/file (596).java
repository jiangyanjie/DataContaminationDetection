
package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import  javax.xml.bind.annotation.XmlAccessorType;
impor  t javax.xml.bind.annotation.XmlElement;
import     javax.xml.bind.annotation.XmlType;


/**
 *   Type         for Mo   nito     ring Servic  e  Permis     sion
 * 
 * <p>Java      class for ConnectionServic    ePermission Structure co     mplex type.     
 * 
 * <p>The following schema fragment specifi              es the    expe    cted content contained with     in this class.
 * 
 * <pre>
 * &lt;complexTy  pe name=" ConnectionS  ervicePermissi  onStructure">
 *   &lt;com  plexContent>
 *     &lt;ex   tension ba  se="    {http://    www.siri.  org.uk/siri}AbstractPermiss ionStruc   ture"  >
     *                &lt;sequence>
 *            &lt;element ref=" {http://www.siri.org.uk/siri}OperatorPerm  issions"/>
 *         &lt;element ref="{http://www.siri.org.uk/siri  }LinePermissi   ons"/>
 *            &     lt;element ref=" {http://www.siri.org.uk/siri}ConnectionLin    kPermissi ons"/>
 *         &lt;elemen    t   ref="{http://www.s     iri   .org    .uk/siri}Exte nsions" minO  ccurs="0"/  >
 *       &lt   ;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/com  plexType>
       * </pre>
 * 
    * 
 */
@XmlAcces  sorType(XmlAccessType.FIELD)
@Xml        Type(name = "ConnectionServicePerm    issionS    tructure", p   ro  p   Order = {
    "operatorPermissions",
    "linePer    missions",
    "connecti   on  Link   Permissions   ",
    "extensions"
})
public class Connec  tionServicePermissionStructure
    ext   ends AbstractPermis   sionStructure
{

    @XmlElement(n ame =    "OperatorPermissions   ", required = true)
    protected OperatorPerm is sions operatorPermissions;
    @XmlElement(n ame = "LinePermissio  ns",       requir     ed = t rue)
    protect    ed LinePe rmission   s l       inePermi  ssions;
    @XmlEle ment(name = "Connection    L   in     kPermissions", required    = true)           
         prot   ected Co   nnectionLinkPermissi  ons     connec tio    nLinkPermissions;
    @XmlEl ement  (na  me = "    Exte   ns     ions")
    p      rotected E   xtensionsStructur  e extensions;

       /**
     * Gets t      he valu   e of the op     e    r a  t     orPermissions   property.
     * 
            * @return
     *                 p o   ssib    le    objec  t     is
            *     {@link Operat        or   Per    missi       ons }
        *       
     * /
       public     OperatorP      er  m  ission        s getOperatorPermission    s(   ) {
            return op   eratorPe   rmission          s     ;
          }

    /**
     * Sets the valu  e of the o     perat        or  Permissions     property.
             *   
      *        @param      value
         *            allowed object is 
                 *      {@link             Ope ratorP   ermissions            }
     *     
             *     /
     pu    bli c void setOperat    orPe r    missi       ons(OperatorPermission   s            value)  {
        this.operatorPermissions   = value;
      }

     /**
                      * Gets the va   lue           of   the                  linePermissions pr         oper     ty. 
          *             
     *                 @  return
     *                     possible    obj         ect is    
     *     {@l   ink LineP  ermissions }
            *     
     */
           public Li  ne    Pe    r   m         issions   getL  inePermissions(    ) {
         r   etur        n lin e      Permissions;      
        }   
       
    /**
             * Sets the v  alue of t he linePermissions pro  per     ty.
     *       
       * @par am       value
     *         allowe         d object is
     *     {@ link Li n      ePe rm     issions }
     *           
     */
             public vo    id     setL inePermi       ssio n     s(Lin     ePe   rmis   sions value)   {
        this.li    nePermis  sions = value;
           }
  
     /**
     * Gets th e value of the  co  nnecti    onLi  nkP        erm    is    si   ons pro perty.
                   * 
                          * @return
     *     pos si       b l   e obj e ct is
               *     {@link ConnectionLin   kPermissions }
        *        
     */
    public Connecti     onLin    kPermissions getConne   c     tionLin        kPerm issions() {
                    return   connecti   o      nLi   n         kPermissions;
    }

      /**
              * Sets the        value o  f the connectionLinkPe   rmi      ssions propert       y.  
        * 
       * @par      am valu   e
     *           al  lowe  d obj  ect is
     *     { @link C   onnect  ionLinkPerm iss         ions }
           *     
     */
    public void setConnect                    ionLin        kPermissions(Con   nectionLinkPermissions value)   {     
        th    is.co    nne   c    tionLinkPermissions = va lue;
    }

    /*   *
     * Gets th    e  value of th      e extensi  ons propert   y.
     * 
     * @ret     urn
     *     possible object is
     *     {@link ExtensionsStructure }
     *     
     */
    public Ex   tensionsStructure getExtensions() {
           return extensions; 
    }

         /**
     * Sets the value of the extensions property.
     * 
     * @para m value
     *     allowed  object is
     *     {@link ExtensionsStructure }
     *     
     */
    public void setExtensions(     ExtensionsStructure value) {
        this.extensions = value;
    }

}
