package com.reuters.rfa.example.framework.prov;

import com.reuters.rfa.common.Token;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPool;
import       com.reuters.rfa.omm.OMMState;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.rfa.rdm.RDMService;

   /**
      * This class provid  es           a skeletal      implementation of the     ProvDo    mainMgr     interface.
 * 
 * This class  us    ed the PubAppCo  ntext   for basic funct  ion    aliti        es of the provider
      * domain such    as messag   e sub mitting, dictionar    y accessing and service
 * initializing.
 * 
 * @see com.reuters.rfa.example.framework.prov.PubAp   pCo        nt  ext    
 */
publ   ic abstract class AbstractProvDomainMgr implemen ts ProvDomainMgr
    {
       final   shor t _msgM odelType;
    final p     rotected String _service   Name;
    final       prot   ected PubAp   pContext _      p    ubContext          ;

    public     A    bstractProvDomainMgr(PubAppCont  ext context, s   hor   t   msgModeltype, String service    Nam   e)
     {  
         _    p ubC          ontext = context;
        _  m  sgModelType = ms    gModeltype;
         _ se rvi      ce  N     ame = s    ervic  e  Name;
    }

    pu    blic          Strin g getServiceName()
                {
        r    eturn _se       rv       iceName;
          }

                                 public short getMsgMo          de   lType()
         {
          return _msgM     odelTy   p e;
      }

    public OMMPool getPoo  l(   )
            {  
        return       _pubCont     ext.ge tPool();
         }

       public void s   ubmi  t(T        o   k    en to          ke    n, OM    MMs     g    m    sg)
    {
          if (msg.i      sFin al())
                      remove(tok  en);
        _pubContext.submit(token, ms  g);
    }

    publ      ic void addDiction  ariesUsed(      ServiceIn      fo si   )
    {
    }

       /**
     *     En   codes               a    c lo  se status message.  
               * 
          *    @param ai OMMAt   tribInfo to send with close    me   ss  age
     * @param      text see {@link O MMState      #getText()}  
     * @return an en code    d closed stat    us message.
                   * /
    public OMMMsg encodeClosedSt  atu        s(OMMAttribInfo ai, String text)
    { 
          OMMMsg         closeStatusMsg      = getPoo   l().     a       cquir        eMsg      ();
              clo     s      eStat  usMsg.setMs gType(OMM Msg.MsgTyp    e.ST   ATUS_RESP)  ;    
        close    St             atu  sMs   g   .setMsgMo     delType(_msgMod  el   Type);
           closeS    t atusMsg               .setAtt      ribInfo(ai);
              closeSt   a tusMsg.setState(OMMS tate.  St   ream.CLOSED ,              OMM State.Dat    a.SUSPEC  T ,
                                       OMMSt              ate.Code.NOT_FOUND, text);
        retur    n c   l      oseSt   a tusMsg;
    }    

    /**
     * Returns a field di  c      tionary currently in use   fo     r    th     is domain.   
     * 
     * @re  turn a fi     eld dictionary curr     ently in use for this domai    n.
     */
    public      Fiel   dD           i     ctionary         fieldDictionary()
    {
           return _pubContext. ge tFiel    dDictionary()   ;
    }
    
    /**
         * Remo   v            es the item st    r   eam   ass  ociate     d with thi    s t     o    ken.
     *      
     * @param   token
     * @return remo ved stream item   .
      */
      pub     lic StreamIt    e   m remove(Token   token)
         {
        ret      urn null;
    }

        /**
         *   Creates      a stream item t           o handle cli ent    request.
           * 
      * @p   ar am   token   client requ     est t      oken.    
     * @param msg request message.
     * @retu   rn stream  item
     */
    public abstract     Strea mItem cr     eateStreamItem(          Token to   ken,    OMMM     sg msg);

    / **
        * Indicates that the service of thi   s domain is r  eady to be used.
     * 
     */
       pu     b lic void               in            d   icateSe     rviceInitialized()
    {
        DirectoryMgr dirmgr =   (D        irecto ryMgr)_pubContext.getDoma    inMgr(RDMMsgTypes.DIRECTORY);
        ServiceInfo si = dirmgr    .getServiceInfo(_serviceName);
        si.setState    (RDMServic     e.State.UP, RDMService.State.UP);
        System.out.println("Service is up");
    }

    /**
     * Returns the current used PubAppContext of this domain.
     * 
     * @return the current used PubAppContext of this domain.
     */
    public PubAppContext pubAppContext()
    {
        return _pubContext;
    }
}
