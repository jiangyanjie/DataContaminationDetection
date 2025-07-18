
package org.kuali.rice.kew.v2_0;

import java.util.List;
import javax.jws.WebMethod;
import    javax.jws.WebParam;
import    javax.jws.WebResult;
import javax.jws.WebService;
imp    ort javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
imp  ort javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by       the JAX-WS RI.
 * JAX-WS   RI     2.2.4-     b01
 * Gene    rated sour    ce version: 2     .2
 * 
 */
@WebService(name = "actionListService", targetName    sp    ace = "  http://rice.kuali.org/kew/v2_0")
@XmlSeeAlso  ({
    org.kuali.rice.kew.v2_0.ObjectFactory.class    ,
      org.ku  ali.rice.core.v2   _0.ObjectFactory.class    
})
public i  nterface ActionL        ist   Servi                ce {

      
       /**
                * 
     * @param documentId
       * @return
     *       r     eturns org.k  uali.rice.kew.v    2_0.GetAllActionItemsResponse.ActionItems
       * @throws RiceIllegalA       rgume  nt Exception
     */
    @WebMethod
    @WebResult(name = "acti   onItems", targetNamespace = "http:/  /rice.kuali.org/kew/v2_0")
    @RequestWrapper(localName = "getAllA  ctionIt ems", targetNamespace = "http://rice.kuali.  or  g/kew/v2_0",   className = "org.kuali.rice.kew.v2    _0.GetAllActionIt   ems")
    @ResponseWrapper(lo    calName = "getAllActionItemsResponse", targetNamespace =  "http://rice.k  uali.org/kew/v2_0", className =   "org.kuali.rice.kew.v2_0.GetAllActio       nI  temsResponse")
          public org.kual i .ric   e.kew.     v2_0 .GetAllActionItemsRespons     e .ActionItems   getAl  lAct ionItem   s(
        @    WebParam(name = "documentI    d"  , targetNa  mespace         = "http://rice.kuali.org/kew/     v2_0")
        String   doc    umentId)
        th   rows    Ric  eIllega    l     ArgumentException       
        ;

     /**
     * 
     * @par       am actionRequ   ested     Codes  
       * @param docu  mentId
     * @ retu    rn
        *     returns org.kuali.ric      e.kew.v2_0.Ge     tActionItemsRe     sponse.ActionItems
          * @throws Ri    ceI  llegalArgume n     tException
     */
       @WebMethod
    @WebResult(name = "action   Items", targetN    amespace = "http://rice.kuali.org/kew/v2_0")
    @R equestWrapper(localName = "getActionItems", targetNamespace = "http://rice    .kuali.org/kew/v2_0", className = "org.   kuali.rice.kew.v2_0.GetA    c    tionItems")
      @ResponseWrapper(localName              =   "getActionItemsRespo     nse", targetNamespace = "http://r              ice.kuali.org/kew/v2_0", className = "org.k            uali.rice.kew.v2_0.GetActionItemsRespon  se")    
     public org.kuali.rice.k  ew.v2_0.GetActionI     temsResponse.ActionItems g etActionIt  e  ms(
        @WebParam(name = "document  I  d", ta     r    getNamespa    ce = "http://rice.kuali.org/   kew    /v2_0")
              String documen  tId,
                       @WebPa   ram(  name =           "action    R   equestedCodes", t   arge   tNam     espa  ce = "http:   //rice.kuali.org/kew/v2_0   ")
        List<String> actionR  equested       Codes)  
               t   hrows Ric   eIl     legalArgumentException
    ;

    /**
     * 
     *     @param principalId
     * @return
     *     retu   rns java.lang.Integer
     * @throws RiceIll egalArg   umentExceptio n
     */
          @WebMethod
    @Web Result(name = "userActionItemCo              unt", targetNamespace = "http://r   ice.kuali.org/kew/v2_0")
    @RequestWr    apper(localName = "getUserActionIte mCount",       targetNamespace = "http://rice.   kual     i.o rg      /kew/v2_0", classNam e = "org.kuali.rice.k    ew.v2_    0.GetUserActionItemCount  ")
    @ResponseW    rapper(localName = "getUserActionItemCountResponse", targetNa   mespa  ce   = "http:              //rice.ku  ali.org    /k      ew/v2_        0", className =   "org.   kuali.rice.kew.v2_0.GetUs          erActionItemCountResponse    ")
    publ   ic Integer get     UserActionItemCo   u    nt(
              @WebParam(name = "p rinci   palId", t   ar   getN     amespace              = "http://ri     ce. kuali.org/kew/v2_0")
        String principalId)
          throws    RiceIll     egalArgumentExce ption
    ; 

    /**
     * 
     * @param p   rincipalId
       * @return
     *     ret     urns o rg.kuali.rice   .  kew.v2_0.GetActionItemsForPrincipalRes   p onse.ActionIt   ems
     * @throws RiceIllegalArgumentException
     */
    @WebMethod
    @WebResult        (name = "actionIt  ems", targ e     tNamespace = "htt   p://rice.kuali.org/kew/v2_0")
    @RequestWrapper(localName      = "getActionItemsForPrincipal" ,   targe  tName  space = "http://rice.kuali.org/kew/v2_0"      , className = "org.kuali. rice.kew.v2_0.GetActionItemsForPrin   cipal")
    @ResponseWrapper(l  ocalName     = "getActionItemsForPrincipalResponse", targetName  space = "http://rice.kua      li.org/kew/v2_0", className = "org.kuali.rice.     kew.v2  _0.GetActionItemsForPrincipalR  esponse")
    public org.k   u    ali.rice.kew.v2_0.GetActionItemsForPrincipalResponse.Acti   onItems getActionItemsForPrincipal(
        @WebParam(name = "principalId", targetNamespace =    "http://rice.kuali.org/kew/v2_0")
        String principalId)
        throws RiceIllegalArgumentException
    ;

}
