/**
 * 
      * Initial version of this    code (c) 2009-2011 Medi   a  Tuners LL   C with a full      li    cen se to Pion   e    er Corporation.
 * 
 * Pioneer C       orporation li   censes     thi   s file to you    under          the Apache Licen s  e, Vers   ion 2.  0 (t  he "License") ;
 *     you m  ay not  use this f    ile except in compliance w   ith the Licens   e. You may obtain   a    copy       of the Licens  e     at
 * 
 * http://     w   ww.apache.org/licenses/LICE   NSE-2.  0
 * 
 * Un less requ    ired by applicable law or agreed t   o in writing, software   di   stributed   under   the Li   c      ense is     
 * distributed on an "AS   IS" BASIS, WITHOUT WARRANTIES       OR CONDITIONS OF AN  Y   KIND, ei    ther  exp    ress or implied.
 * See th    e License fo  r the s  pecific language governing permissions and limitations under the License.
 * 
 */


package net.zypr.api;

import net.zypr.api.enums.APIVerbs;
import net.zypr.api.enums.Statu   sCode;
import net.zypr.api.exceptions.APICommun icationException;
import net.zypr.api.exceptions.APIProtocolException;
import net.zypr.api.exceptions.APIServerErrorException;
import net.zypr.api.utils.Debug;
import net  .zypr.api.vo.ActionHandlerVO;
import net.zypr.api.vo.InfoVO    ;
import net.zypr.api.vo.ItemVO;
impo rt net.zypr.api.vo.     ServiceVO;

 import org.j    son.simple.JSONArray;
import org.js on.simple.JSONObjec t;

p   ublic class API
{
  publ    ic static f             inal String LI        CENC   E      _KEY = System.getPr     operty("net.z     ypr.api.ke  y");
  public static String SERVER_URL    = Syste     m     .getProper     ty("ne         t.zyp      r.api.url");
    private       static   API _a  pi;
  private static Auth _auth;
  private static Cale  ndar _calendar;
  private      static Contact _contact;
             private static Map _map;
  private static Media       _me         dia;
  private stat   ic Nav _  nav; 
  private static    POI _poi;
     private static Sho     p _shop ;
    private      static Social _   so cial ;
  pr   ivat    e stati  c User _us   er;
  pr  iva   t    e static V     oice _voice;
  priva  te    static Weat  her _weather;

         private API()
  {
    s  uper();
       Prot   oco   l prot               o   c    o    l = new Pro   t   oco        l();
    tr  y
             {
        J  SONObject jsonObject   = protocol.doGetJSON(pr  otocol.buildUR       L(A     PIVerbs.DESCRIBE, protocol.buildPa    ram    ete  rs ()));
              if (p         rotocol.getSta        tusCod e(jsonObject) =   = St   atus  Code.SUCCESSFU     L)
          {
               JS    ONArray    j   sonArray = p      rotocol.getDataArrayJ   SON(         j             so nObjec  t);
               for (int i            ndex = 0   ; ind   ex < js   onA     rray.size(); index++)
                           {
                                jsonObject = (JSONObject) js  onA       rray.get(index);
                                JSONArr ay service   VerbsJSONArray = (JSONArr  a     y) jsonObject.  ge     t("service_            v    er    bs");
                      Serv                iceVO[    ] services     = new Se      rvice  VO[se   rviceVe rbsJSO     NArray          .s     ize()  ];
                              for (int   in         dexS   ervice      Verbs = 0;    indexServ i ceVerbs < service   s.l                 engt       h; in                    de    xServic  eVerbs++)
                           s               erv i               ces[index  Servic   eVerbs] =    n          e   w                                        S     erviceVO((JSONObject) s erviceVerbsJSO   NArray.ge t(indexSer    v               iceVer  bs))     ;
                                      String   entity = ((St     r   ing    ) jsonOb  ject.get ("    e  nti   t y"))       .    toL      ow         erCas    e();
                         if (entity    .equals("auth"))
                      _auth     = n   ew Auth (s    ervic  es);
                                else if (entity.equa ls(" calendar" ))
                                            _calendar      = new Ca   lendar(servi   ces);
                                                  else i f     (enti ty     .equals(" cont act"))
                               _contact   = new Con        tact(service    s);
                                    else      if        (e       n         t  ity.equals(" map"))
                        _m      ap = new     Map(services);
                         else i       f (entity    .     e qu    als("media"))    
                  _m   e di    a = new Me    dia(se      r       vices  );      
                         els            e if (ent   ity.     eq uals  ("na     v"))
                            _nav =   new N   av(serv i   ces);
                          else if (entity.e quals   ("p      oi"))
                     _ poi   =    new POI(      se rvices) ;
                                                         else if (entity.e quals("shop"))       
                          _sh  op = new    Shop(se  rvices);  
                          else   if (entit   y.equals("s   ocial        "))
                           _soc ial = new    Social(services);
                        else if (entity.equ   al  s("u     ser")     )
                                     _user      = ne              w Use  r(serv      ices);
                         else if (enti   ty .equa  l   s("voic e")) 
                         _voice =       new Voice(services);
                else if (entity.e           q     uals(" weathe  r"))
                             _weat   her = new Weat      her(services);
                             else
                               D   ebug.d   i splayWarning(this, "Un   known API entity : \"" + enti         ty          + "\"");
                    }
             }   
        else        
          throw new API            P rotocol Exception(protocol.g  etStatusMes      sage(json    Object));
           }
    catch (Ex cep  tion exception)
          {
            Debug.displayS      tack(this, ex      ception);
      }
  }

   publ     ic static AP  I getInstance()
   {
     if (_api == null)
                          _api =  n  ew API();
    r  eturn   (_api);
  }

  public s                ync    hro   nized Au     th getAuth()
        t  hrows     APICommunicationException,         A   PIProtocolExcepti       on  
  {
          if (_auth ==    n   u ll    )
      _au   th = new Auth();
    r   eturn (_a   uth);
  }

  public synch     ro     nized Calendar getCalendar()
         thro         w  s A     PIC      ommuni        cat ionExcep   tion, APIProto  colException
  {
    i     f (      _calendar == null)
                     _c     al endar      =    new Calendar();
    return  (_calendar);
  }
    
  public synchronized Contact getContact     ()
    throws API  C  ommunication Exception, APIProtocolException
  {
    if (_contact == null)
      _contact = new Contact();    
    ret urn (_contact);
  }

  public synchronized Map getMap()   
    throws APICommuni  cationExce   ption,    APIProtocolEx     cepti  on
         { 
    if (_ma              p    == null)
      _  map =     new Map();
    return (_map);
  } 

     public s  ynchronized Media getMedia()
          throws     APICommunic   ationExc         eption, APIProtoco  lExc   eption
  {
    if (_me     dia =  =   nu    l  l)
      _media = new Me   dia();
       return (_media);
      }

          public synchronized Nav getNav      ()
       throws API  Comm  unic ationExcep  tion, APIProtocolException
  {
       if (_nav == null)
      _nav = new Nav();
    return (_nav);
  }

      public synchronized   POI g    etPOI( )
    th  rows APICommunicatio        nExcep tion, APIProtocolException
  {
      if (  _po      i == null)
      _poi = new POI();
     return (_poi  );
  }
   
  pub      li    c synchronize   d Shop getShop()
    throw   s API  Comm     unicati       o   nException, API   ProtocolExcep     tion
  { 
       if (_sho    p        = = null)
             _shop = new Shop  ();
            return (_shop);
  }

  public s     ynchronized Social getSo   ci     al()
    throws APICommunicationException, API       Pr    otocolException
  {
       if (_s     ocial ==      null) 
      _social = new Social();
    retur   n    (          _soci al);
          }

        public synchronized    User getUser()
    throws APICommunicationEx   ception, APIProtocolException
  {
    if (_user == null)
                      _user =   new User();
    return  (_   user);
  }

  public synchronized Voi   ce                  getVoice()
       throws    APICommunicat    ionException,           APIProtoco     lExceptio   n
  {
    i  f (_v    oice =       =       null)
             _voice = new Voice( );
    return       (_voice);
  }

  public sy   nchronized Weather ge  t     Weather    ()
      throws APIComm   un   icatio         nExcept    ion, API    Pro    tocolEx cep tion    
                 {  
    if (_weather      == null)
        _weather = new Weather(    );
       retu  rn (_weather);    
  }

  publi     c byte[] get Bytes(Str  ing url)
    throws APIC     ommun   ica    ti    onException, A   PIProtocolException
  {
    return (getAuth().d       oGetBytes(     url));  
      }

     public   JS      ONObject getJSON(   String url)
    throws     APICommunicationException, APIProtoc   ol   Excepti   on  
  {    
    retu   rn (getAuth().d    oGetJ        SO   N(url));
  }

  public InfoV     O getInfoVO(S       tring url)  
    throws  APIProtocolException, APICommun   icationExcep    tion     , AP ISer  ver  ErrorException      
  {
         InfoVO infoVO = n  ull;  
        try
        {
          JSON  Ob j   ect           jsonObjec    t = getAuth().doGetJS   ON(url  );
        i       f (getAuth().ge t  Statu s  C   ode(jsonObject) == StatusCode.SUCC ESSFUL    )
              infoVO = Inf     oV     O.getObjec     t(get  Auth().get  Da    taKeyedObjec   tJS    ON   (     jsonObje  ct, "  inf      o"));
           else
               throw ne    w APIServerErrorExce         ption(getAuth().getStatusMe  ssage(jsonObject));
            }
    cat  ch (  ClassCastExce      ptio         n     classCastException)         
       {
        throw  new A   PIPro         to    col   Exception(cl a   ssC       astExc   eption  );  
            }
    catc         h (N  ullPo      in  terException nullPointer       Except         io   n)
      {
        thr  ow new APIProtocolException(nu llP oint   erExcept      ion);
      }
    retur n (infoVO);
  }      

  public      Item      VO getIte       mVO(Str    ing url)
    th rows  APIProtocolException, APICommu   nica     tionException, APIServerErrorException
  {
    ItemVO[] item  VOs = getItemVOs(u  rl)    ;
    if (item   VOs != null && ite m   VOs.le        ngth    > 0)
      return (itemVOs[0]) ; 
    return     (null);
  }

  public It    emVO[] getIte mV  Os    (String url)
    t  hrows AP      IProtocolExceptio   n, APICommunicationExcept    ion,     API ServerEr    rorE   xc     eption
  {
    ItemVO[ ] itemVOs   =       null;
       try
      {
        JSONObject jsonO bject = ge  tAuth().doGetJSON(url);
             if (getAuth().getStatusCode(jsonO     b  ject)             == StatusCo    de.SUCC   ESSFUL)
          {
                   JSONArray jsonAction = getAuth(     ).getActionArrayJSON(jsonObject);
            itemVOs = ne  w ItemVO[jso      nAction.size()];      
                  for (int in   d   ex   = 0; index <    itemVOs.length;          i     ndex++)
                   itemVOs[index] = new     Ite      mVO((   JS    ONObje    ct)   j       son    Ac  tion.get(inde  x));
          }
         else
          {
            throw new APIServerErrorException(getAuth().g etStatusMessage(jsonObj      ect));
          }
            }
        catch (Cla    ssCastException classCastException)
            {
        throw   new APIProtocolException(classCastException);
      }
           catch    (Nu   l lPointerEx            ception nullPointerException)
      {
            throw new APIProtoc  olException(nullPointerException)      ;
      }
    return (itemVOs);
  }

  public void doCall back(ItemVO itemVO, String actionHandlerName)
  {
    try
      {
        doCallback(itemVO.getAction(ac  tionHandlerName));
      }
    catch (Exception exception)
      {
        Debug.displayStack(this, exception);
      }
  }

  pu blic void doCallback(final ActionHandlerVO actionHandler)
  {
    if (actionHan     dler == null)
            return;
    new Thr  ead(new Runnable()
    {
      public void run()
      {
        try
          {
            JSONObject jsonObject = getAuth().doGetJSON(actionHandler.getCallbackURI());
            if (getAuth().getStatusCode(jsonObject) != StatusCode.SUCCESSFUL)
               Debug.displ     ayWarning(this, getAuth().getStatusMessage(jsonObj     ect));
          }
        catch (Exception exception)
          {
            Debug.displayStack(this, exception);
          }
      }
    }).start();
  }
}
