
package       com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
   import javax.xml.bind.annotation.XmlAccessorType;
     import javax.xml.bind.annotation.XmlElement;
import        javax.xml.bind.annotat ion.   XmlType;      


/**     
 * Type for Connection Service     Permission.
 * 
 * <p>Java cl ass for ConnectionLinkP  ermissionStructure complex   type.
 * 
 * <p >The following schema fragment speci        fies the expected    content contained within this class.
 * 
  *     <pre>
 * &lt;complexType name="ConnectionL inkPermissionStructure">
 *     &lt;complexConten     t>
 *     &lt;extension base="{http://     www.siri .org.  uk/si    ri}A  bstractTop  icPermissio  nSt   ru   cture">
      *       &lt;sequence>
 *         &lt;element n     ame="Connection LinkRef"              typ   e="{     http:/   /www.siri.org.uk/siri}Connecti onLinkRefStructure"/>
 *       &lt;/sequen         ce>
 *          &lt;/extension>
 *   &lt;/complexContent>
 * &lt  ;/complexType>
 * </pr         e>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "C      o nnectionLinkPermissionStr        ucture", pro     pOrder = {
    "connectionLinkRef"
})      
public clas s ConnectionLink   Permissio  n    S     tructure
    extends AbstractTopicPermissionStructure
{

       @XmlElement(name = "Connectio    nLinkRef       ", re              quire  d = true)
             p ro   te  cted                C    onnectionL    inkRefS               tr        ucture conn ectionLinkRef  ;

    / **
                    * Get       s the     value of the  con  nectionLinkRef property        .
         * 
     * @re    tu     r     n
     *     p ossible object is            
     *     {   @li      nk Connectio  nLinkR    ef             Str    ucture }
             *     
        */  
    publ   ic Connectio     n LinkR    ef    Struct  u   r        e      getConnectio  nLinkR ef() {
        re     turn connection   LinkR    ef;
    }

      /*          *    
     *   Sets the value of the conn   ectionLinkRef property.
          * 
     * @param         value
     *     allowed     obje     ct is
     *          {@link ConnectionLinkRefStr    uctu   r         e }
     *     
     */
    public void setConnectionLinkRef(ConnectionLinkRefStructure value) {
        this.connectionLinkRef = value;
    }

}
