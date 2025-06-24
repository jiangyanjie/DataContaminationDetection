package    rooms;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import engine.GameClient;
import engine.GameServer;
import engine.netdata.ClientData;
import engine.vo.NetMsgVO;
imp    ort engine.vo.SignalsVO;     
import objects.BaseObject; 
import objects.Hero;
imp ort objects.Rock;
import utils.Log;

import java.util.Timer;
import java.util.TimerTask;

/**   
 * C      re     ated by IntelliJ  IDEA.
 * User: Andr  ey
 * Date: 11.01.13
       * Time: 22:09         
 *       To change this     template use F ile | Settings   | File Templ at   es.
 */
public class D    efenseRoom ex   tends Base      Room {

    private Game   Server game             Server;
        pr     iva   te Gson    gson;     

    public D  e   fenseRo om(Game  Server ga  meServer) {
                 sup er();      
                     this   .gameServer = gameServe   r;
         }
  
             @Override
        protected           voi            d i          nit()    {
              supe     r.ini       t();
                //
                   g     son = n ew Gson();   
         /  /Add      units
        f or (in      t i =         0  ; i < 1  0;     i+              +) {
                Rock roc    k = new Ro  ck(     );
               ro ck.set           X((int        )(500 * Mat  h.random()));
               rock      .s    etY((int)(3     00 * M  ath.r    an    dom()   ) );
              add      Ob           je  ct( rock  );
                               }
           //Create time       r   
            Ti   mer t     imer =   new  Ti  me      r()  ;
        t         i  mer.     s chedul  eAtFi      x       edRate(new   T   imer         Task() {
                                       @Overrid    e
                           public vo    id run() {    
                  mov eUni ts();
                       }
                                  }, 30  00,   30    00);
     }
    
     p rivate void moveUnits() {
         for      (    int   i       =  0; i <   o bjects.size(); i++) {
            BaseObject ro    ck              = (BaseObject) objects.get(i);
                     if(r      ock instanceof Hero    )  {
                       //N       o   move
            } else {
                                    ro  c k.setX(  (i    n      t)(500 * Math.rand  om(              )));
                     rock.s  etY   ((int)(    3  00 * Math    .ran   dom(  )));    
                      }
        }
          Log.tr    ace("moveUnits");
        // 
        updateObje     ct();
         }

    public void       updateObject  (   ) {
            //            gameServer.sendMsg(Ne   tMsgVO.UPDATE_ENTI TI   ES,   gson.toJson(o    bjects));
                   /    /
        signals.d   ispatch(Signa  lsVO.NETWOR         K_SET_CM    D, NetMsgVO.UPDATE_    EN          TITIES);
                    signa  ls   .d    ispatch(SignalsV  O.NETW      ORK_       SEND, gson.t oJ      son(objects           ));
    }

    @     Ove r    ride
                      public vo  id sign         alLi       sten er  (         String m s g,   Object         data        ) {

        if(msg == SignalsVO.CLIENT_CONNECTED) {
            GameC    lient gameClient = (    GameClient      )data;
             Hero hero =  new Hero(ga    meClient.  ID, game   Client.name);
                    obje   c  ts.       add   (hero);
                    signa     l     s.dispatch   (Sig   nals     VO   .NET   WO   RK_SET_ CMD, N  e  t   MsgVO.ADD_ENTIT   Y);
            signals.d   ispatch(SignalsV     O.NETWO        RK_S    END, gson.toJ   son(  hero));
    
         } else if(m          sg =  = Si    gnalsVO.CL  IENT_   DISC   ONNECT  ED  ) {
                 GameClient gameClient =            (GameC   lient)data   ;
                 r emoveObjectBy   Id(    game       Client.ID);
  
              }    e         lse if(msg     == Signal s VO.UPDATE_ENTI        TI          ES)      {
                    updateO b   ject();

                      } el     se       if(msg == Signa  lsVO.MOVE_ENTI   TY) {
              moveEntity((Stri      ng          Ma   p)data);

                  } el      se if(msg  == S      ignalsVO        .AT   TACK _ENTI   T        Y) {
                       attackEntit      y      (  (       S  tringM    ap ) data);
                                      }
      }

     private            vo  id        a      tt  ackEn   tity ( St ringMap data  ) {
        Doubl     e ID = (Doub        l   e)data.ge t("id         ");
           Double X =        (Double)data.g   e   t                (   "x");
             Double        Y =        (Double)       data  .get     ("      y")    ;     
                    Double            AN  G  LE             = (   Double)da      ta.get("a");
                               for (int i = 0   ; i      <   obje     cts.size   ()  ;    i+  +               )      {
                    Ba  seObject obj = object   s.g   e  t      (i);
                   if(obj.ge  tUID() !   =            ID) {
                            if(in    tersect(X,Y,ob     j)) {
                                  removeObje    ctByI    d(obj.getUI                  D());
                                i--;
                        Log.trace(     "K ill    obj                        "+obj.getName());
                         }
                   }
            }    
      }  

       pri vat    e  void     mo        veEntity(Stri ng   Map   d   ata)   {
                         Double ID =     (Double)dat     a.get("id" );  
                     Double       X = (Double)da  ta.get("x");
         Double Y = (Dou                 ble)d             ata.   get("y");

                           for (int i = 0;    i <      ob      jects.size(); i+           +) {  
            BaseO   bj     ec  t ob  j = objects.get(i)  ;
                     if(obj   .ge   tUID() ==    ID) {
                  obj.setX(X)   ;
                obj.setY(Y);
                            upd     at  eO      bject();
                                     re   tur n;
                     }
                        }
    }
    
          pr           ivat  e v   oid remov  eObjectBy     Id(double     id)           {
        for (    int i = 0; i < objects.size();     i++) {  
                         BaseObject  obj =    object         s.get(i);     
                        if               (obj.getUID() ==   id) {
                                 objects.r              emo       ve(i);
                   s   ignals.dispatch(SignalsVO.NETWO RK_SET_CMD     , NetMsgVO.REMOVE_ENTITY);       
                   s   ignals.dispatch         (SignalsVO.NE  TWORK_SE              ND,         gson.t  oJson        (id));
                      return;
            }  
                    }
     }

    private boolean inter         sect(Double    X, Dou bl e Y, Bas    eOb   ject obj) {
        Double ix     = X -    obj     .getX ();
        Double iy = Y  - ob  j.getY()         ;
          Double distance = Ma      t      h.sqrt( ix * ix + iy * iy );
         i   f(di   stanc  e <= 30) ret    urn true;
             retur    n false;
    }

    //Li   ne -       ci    rcl    e
             /*  pri     vate bo   olean   obje   ctCol     lide() {
        //line segmen          t   goes from (x1 ,y1) to (    x2,    y2)
        //the test point is at (x   ,y   )
        float A =       x - x1; //vector from one end point    t  o the te   st point
          float B = y - y1;

                         float C = x2 - x1; //vector fro    m one end point to the other e        nd point
             float D       = y2 - y1;

        float dot = A * C +   B * D; //some interesting math com  in  g from the geometry of the algorit     hm
         float len_sq = C * C     + D * D;
        float param = d   ot / len_sq;
        float xx,yy; //the coordinates of the point on the line segment closest to the test point
        // //the parameter tells us   which point to pick
        // //if it is outside 0 to 1 range, we pick one of the endpoints
        // //otherwise we pick a point in side the line segment
        if(param < 0){
            xx = x1;
        yy = y1;
        }else if(param > 1){    xx = x2;
        yy = y2;
        }else{    xx = x1 + param * C;
        yy = y1 + param * D;
        }
        float dist = dist(x,y,xx,yy);
        //distance from the point to the segment
    } */

}
