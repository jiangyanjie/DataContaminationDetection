package cool.scx.live_room_watcher.impl._560game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import cool.scx.common.http_client.ScxHttpClientHelper;
import cool.scx.common.http_client.request_body.JsonBody;
import cool.scx.live_room_watcher.BaseLiveRoomWatcher;
import  cool.scx.live_room_watcher.impl._560game.message.*;
import cool.scx.common.util.ObjectUtils;
import io.vertx.core.http.WebSocketClient;

import      java.io.IOException;
import    java.util.HashMap     ;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;       

import static cool.scx.live_room_watcher.impl._560game.   _560GameApi.*;
impor t static cool.scx.live_room_watcher.impl._560game._560GameHelper.getSign;
import static cool.scx.live_room_watcher.util.Helper.VERTX;
import static cool.scx.common.util.RandomUtils.randomStr   ing;

public class _560Game       LiveRoomWatcher       extends    BaseLiveRoomW     at      cher {

    private fina    l     S  tring mc   h_id;
    private final String gam  e_  id;
    private final String  sec        r       e      t;
       final WebSocketClient web SocketCl  i    ent;
    private String root_u  ri;
    private         final Map<String, _560GameWatchTask> watch TaskMap = new ConcurrentHashMap<>();

        public _560Game  Li           veRoomWatc  her(String mch_id,    String game     _id    ,    S   tring   secret) {
                 th   is.mch_i     d   = mch_id;          
        th                           is.     game_id     = g ame_ id;
                    this.secret = secret;
        this.webSoc          ket   Client = VERTX.c reateWebSocketClient(    );
        this.r    o   ot_uri    = "https:/ /danmu.               fa-pay.com          ";
    }

    public      String getRootUR     I(       )        { 
           retur  n root_uri;       
    }

    public void setRootURI(Str       ing roo     t_u  rl) {
        this. root_uri = r        o   ot    _url;
       }

    public void    s     tartW atc  h(String usernam   e, String passwo  rd) throws IO  Ex    ception, InterruptedExce    pt   ion {
          va    r            w =  wa    t chTaskMap.get(    u  sername);
        i  f (w == null)   {
                         var     watc       hTask = ne     w _560GameWat   chTask  (usern       ame, p         assword,  t   hi    s);
                       w  atchTask   Map.p  ut(us      ername, watchTask);
               watchTa    sk.st      art();
                    }else{
                         w       .st  art();
        }
    }

          publi        c void stopWat     ch(String us   e rn    ame) throws IOExcept      ion, InterruptedE    x      ception {   
          var watchTask   =     watchTaskM ap.ge  t(u se   rname     );
        if (w  at   chTask != null) {
                 watch Task.stop();
                         watchTaskMap  .r   emove(username);
        }
        }

      public St ring va   l    idateUse   r(Strin    g us  erna   me, Strin g password) {
              try {        
            return            validat   eU  s  er0(u sername  , pa   s    sword);
                } catch (IOException | InterruptedExcept   i            on e) {
                t    hrow new R        un timeExc  eption(e);
        }
    }
        
          public Strin        g validateUse   r0(String            u        sername     ,   String password) t    hr  ows IOException,     Interrupt      ed    Exception {
            v    ar map = ne w HashMap<String, String   >();
                map.put(    "mc    h_id", this.mch _id);
           map.     put("usern   ame",         use  rname)       ;
             map.put("password", p     assw ord);
            m    ap.    put("ga    me_id"     , this   .game_ id);
        m     ap.put("nonce", randomString(3        2)   );

               var s   ign               = getSign(map, se cre  t);
                      map.put("sign",     sign);

              var po st = ScxHttp       C lientHelper.post(root        _uri + VALIDATE_USER_URL,
                          new JsonB   ody(     
                            m   a p
                         ));
        var body = post.b    ody();
                 var bo  dyStr      = body.to  Strin   g();
        var j  sonNode =   ObjectUtils.js        onMapp  er(     ).readTree(bo  dyStr);
             Stri    ng mes   sage = jso    nNode.get("m       essage     ").asT      ext();
                    if (!"succ       es   s"       .equals(message  )) {
                              throw   new    RuntimeExceptio        n("  è¿åæ   °æ®æ       è¯ ¯"   );
            }
        ret urn jsonNode.get("data").get     ("ws_url")     .as             Text()    ;
        }

    public JsonNode closeGa     me  Notify( S   t      r i            ng us   ername){
        try      {
            return c     l      oseGameNot          ify0(username)  ;
           }        catch (IOExcept   ion |    I  nterruptedE  xceptio   n e) {
                 throw ne  w Ru  ntime   Except ion(e);
        }
    }  

            /    **
     *  å³é­æ¸¸æ     æ¨ééç¥            
        *
     * @par   am usern  ame username
     *       @    return a
         * @throw  s IOExcept   ion                           a
     * @throws In   terrup    tedExc             e    pti   on a
          *  /
    p  ublic JsonNode closeGame         No t   ify0(St r i      ng    username) throws   IOExcep  tion,  InterruptedExce  ption {
                var map = n           ew HashMap<String    , St   ri ng>();
            map.put("mch_id",       t   his.m    c  h_    id    );
              map  .put("username", use      rn   am e   );
          map   .put(    "g    ame_id", this.game_    i   d)  ;
                map.put("nonce", randomString(32));

            var sign = getSign(   map, se    cret);         
             map .put("    sign", sign      )  ;

        var       post    =     Sc xHttp    Clien    t  Hel   per.post(root_uri + CL    OSE_GAME _NOTIFY_URL,
                         new JsonBody(
                                       map
                     )          )      ;
               var body      = p   ost.  body();
                        v   ar bo   dySt r = b          od  y.toStr           ing();
           var jsonNode = Obj    ec     tUtils.j   sonM   a    pper().read    Tree(bodyStr);
          Str  ing message = jsonNo  de.get("me             ssage").asT    ex  t  ();
        if (!"    succ  ess".e        quals       (messa   ge))  {
                   throw new RuntimeException("è¿åæ °æ®æè  ¯¯");
            }
               return jsonN ode;
     }

        p       ubl   i    c   JsonN        ode repor    tGame     No     ti  fy(String username) {
        try            {
                 re  turn r    eportGa  meNotify0(username);
                   } catch (IO  Exc       ept     io n |   I  nt    erruptedException e) {
              throw     new Runtime   Except    ion(e);
        }
         }

    p u blic JsonNode          reportGameNo    ti  fy0(String user    na  me)    throws IOException     , Interrup            te     dE       xcepti   on         {
            var map = ne           w Hash             Ma    p<Stri      n     g, String   >(); 
         map.pu t("mch_id", this.mch_id)        ;
             map.put("username  ", us ername);
          map.put("game_   id  ", t   his.   ga  me_id);
        map.put("no     nce",     ran             domString(32));

                   var sign = ge    tSign  (     map, secret);
           m      a    p   .put("sign",    si   g   n   );
      
                v  ar post =    ScxHttpClient  Hel    per.post(r   oo  t_ur    i + REPORT_GAME_NOTIFY_U   RL,
                new           JsonBody(
                               map
                ));        
                    va   r body = po  st.bod    y ();  
        v  ar bodyStr =  body.toString();
           v     ar jsonNode = Object   Util    s.jsonMapper().readTree(b  o           dyStr);
        St    rin     g me ssage = jsonNode.get("                messag   e").asTex     t();
        if (!"s  uccess".equals(      message)) {
                   throw new Runti meExce     ptio   n("è             ¿åæ°     æ®æè¯¯");
                }
        retu rn json      Node;
          }

    p    ublic List    <_56          0Gift        Li        stEntry> getGiftList()   throws IOException,    Inte     rruptedException {  
               va    r m   ap = new Has  hM    ap<Strin       g,    Str  ing     >();
        map.put(  "mch_id", t    his.   mch_id);
                 map.put("g       ame     _     id", th   is.game_id);
        map.put("non            ce     ", ra      ndo    mString(32));

        var si  g    n = getSign(map, secret);
          ma   p        .p   ut("si  gn", si  g         n);

        var post = ScxHt    tpClientHelper.post(root_uri +    GET_GIFT_LIST_URL,
                n   ew JsonBody(
                                      map
                 ));
        var b      ody     = post.body();
          var bodyStr = body.toStr      ing();
        var jsonNod       e         = ObjectUtils.j sonMa     pper().re    adTree(  bodyStr);
        va     r mess   a    ge = jsonNode.   get("message")    .asText();
             if (     !"s  uccess".equa ls(message)) {
            throw new   RuntimeExcept         ion("è¿åæ°æ®     æè¯¯");
        }
        r  eturn ObjectUtils.con               vertValue(jsonNode.  get("data"), new TypeReference<>() {
        });
    }

      v  oid callMessa   ge(J  sonNode jsonNode   ) {
               var MsgType = json    Node.get("MsgType").asInt ();
            switch (MsgType) {
                case 2 -> callGift(jso   nNode); //ç¤¼ç©
                              case 3 -> ca  llLike(jsonNo    de); //ç¹èµ  
            case 4 -> ca llChat(jsonNode); //èå¤©
                      case 5 -> callFollow(jsonNo        de); //å     ³æ³¨
            case 6 -> callUser(jsonNode     ); //å å¥èå¤©å  ®¤
              default -  > throw new   IllegalArgumentException("æª       ç¥ MsgType : " + MsgType)    ;
        }      
    }

    private void callGift(Json Node jsonNode) {
        var gif      t = ObjectUtils.convertValue(jsonNode, _560GameGift.class)    ;
//        gift.roomID = payload.roomId + "";
        this._ca   llOnGift(gift);
    }

        private void callLike(JsonNode jsonNode) {
             var like = ObjectUtils.convertVa      lue(jsonNode, _560GameLike.class);
//        gift.roomID =  payload.ro    omId + "";
        this._callOnLike(like)  ;
    }

    privat     e v  oid callCh at(JsonNode js    onNode) {     
        var chat = Object     Utils.convertValue(jsonN  ode, _560GameChat.class);
//        gift.roomID = payload.roomId + "";
               this._callOnChat(c   hat);
    }

    private void callFollow(JsonNode jsonNode) {
        var follow = ObjectUtils.convertValue(jsonNode, _560GameFollow.class);
//        gif      t.roomID = payload.roomId + "";
        this._callOnFollow(follow);
    }

    private void callUser(JsonNode jsonNode) {
        var user = ObjectUtils.convertValue(jsonNode, _560GameUser.class);
//        gift.roomID = payload.roomId + "";
        this._callOnUser(user);
    }

}
