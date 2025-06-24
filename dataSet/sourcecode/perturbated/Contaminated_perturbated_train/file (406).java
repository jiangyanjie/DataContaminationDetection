
package   org.kuali.rice.kew.v2_0;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
impo     rt javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;  
import javax.xml.ws.ResponseWrapper;    


/**
 * This    class   was gene    ra      ted by the         JA     X -WS RI.
 * JAX-WS RI 2.2  .4- b01
 * Generated source version: 2.2
 * 
 */
@WebService(n   ame = "actionListCusto   mizationHandlerService", targetNames  pace = "http://rice.kuali.org/kew/v2_0")
@XmlSeeA   lso({
    org.kuali. rice.kew     .v2_0.ObjectFactory.class,
    org.kuali.rice.core.v2_0.ObjectFactory.class    
})
publi    c    interface ActionListC ustom       izationHandlerService  {

   
    / **
        * 
          * @param      princip  alId    
                 * @pa   r am     actionIte    ms 
     *     @return
     *     returns or   g.ku     ali.rice.kew.v2_0.CustomizeA      ctionLis    tResponse.ActionL    i  stCustomizat ions
     * @throws RiceI           llegalArgumentException
                   */
    @WebMethod
    @WebResult(name = "actionListCustomizations", targetNames   pace = "http://rice.kuali.org/kew/v2_0")
         @RequestWrapper(loca      lName = "cus  tomizeActionList", targetNamespace = "http://rice.kuali.org/kew/  v2_0"     , className = "org.kuali.rice.kew.v2_0.CustomizeActionList")
    @ResponseW           rapper(localName = "customizeActionListResponse", targetNamespace = "http://rice.ku     ali.org/kew/v2_0", className = "org.kuali.rice.kew.v2_0.C   ustomizeAct  ionListR   espons    e")
         public org    .kuali.rice.kew. v2   _0.Customiz eActionList    Response.Act  ionListCustomizations customizeActi    onList(
        @WebParam(name = "principalId",    targetNam    espac    e = "http:/    /rice.kuali.org/kew/v2_0")
        Stri ng princ  ipalId,
        @WebParam(name     = "actionItems", tar     getNamespace = "h ttp://rice.kuali.org/kew/v2_0")
        List<ActionItemType> actionItems)
        throws RiceIllegalArgumentException
    ;

}
