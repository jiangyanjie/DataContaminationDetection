
package org.kuali.rice.kew.v2_0;

import       javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import   javax.xml.bind.annotation.XmlType      ;


/**
   * <p>Java class f  or acknowledgeResponse     complex type.
 *     
 * <p>The    following sch      ema fragment specifi     es        the expected content contained wi  thin this clas   s.
 * 
 * <pre>
 * &lt;complexType name      ="acknowledgeResponse">
 *   &lt;complexContent>
 *     &lt  ;restriction     base="{http://www      .w3.org/20     01/XMLS chema}anyT ype">
 *          &lt;seq    uence>
 *         &lt;element ref="{http://rice.ku     ali.org/kew/v2_0  }doc   umentActio           nResult"/>
    *       &l      t;/sequenc  e>
 *      &l  t  ;/restriction>
 *         &lt;/com  plexContent>
 * &lt   ;/complexType>
 * </pre>
 * 
 * 
    */
@   XmlAcces   sorType(Xml  AccessType.FIELD)
 @Xml    Type(name = "acknowledg    eResponse", propOrde   r = {
    "docume  ntActionResult"
})
public class AcknowledgeRes  ponse {

       @X   ml   Element(required =    true)
       protected Documen   tAct       ionR   esultTyp    e documentActio           n   Result      ;

    /**
       * Gets     the value of the      docu     mentAction   Result p            roperty .   
      * 
      *       @return
     *       p      o   ssible object         is  
       *     {       @li     nk Docum     entA   ction      R      esultType }
          *     
     */
     public DocumentActionResul       tType ge  tDocumentActio   nResul       t(   )     {
         retu  rn          documentActi      onRe sult;
    }

      /**
          * Se     ts  the valu e of the  d ocumentActionResul  t prop      erty.
     * 
     * @p       ar   am value
          *     allow    ed object is
     *               {@link DocumentActionResultType     }
     *     
     */
    public  void setDocumentActionResult(DocumentActi    onResultType value) {
        this.documentA  ctionResult = value;
    }

}
