package rooms;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import engine.GameClient;
import engine.GameServer;
import engine.netdata.ClientData;
import engine.vo.NetMsgVO;
import engine.vo.SignalsVO;
import objects.BaseObject;
import objects.Hero;
import objects.Rock;
import utils.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 11.01.13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class DefenseRoom extends BaseRoom {

    private GameServer gameServer;
    private Gson gson;

    public DefenseRoom(GameServer gameServer) {
        super();
        this.gameServer = gameServer;
    }

    @Override
    protected void init() {
        super.init();
        //
        gson = new Gson();
        //Add units
        for (int i = 0; i < 10; i++) {
            Rock rock = new Rock();
            rock.setX((int)(500 * Math.random()));
            rock.setY((int)(300 * Math.random()));
            addObject(rock);
        }
        //Create timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveUnits();
            }
        }, 3000, 3000);
    }
    
    private void moveUnits() {
        for (int i = 0; i < objects.size(); i++) {
            BaseObject rock = (BaseObject) objects.get(i);
            if(rock instanceof Hero) {
                //No move
            } else {
                rock.setX((int)(500 * Math.random()));
                rock.setY((int)(300 * Math.random()));
            }
        }
        Log.trace("moveUnits");
        //
        updateObject();
    }

    public void updateObject() {
        //gameServer.sendMsg(NetMsgVO.UPDATE_ENTITIES, gson.toJson(objects));
        //
        signals.dispatch(SignalsVO.NETWORK_SET_CMD, NetMsgVO.UPDATE_ENTITIES);
        signals.dispatch(SignalsVO.NETWORK_SEND, gson.toJson(objects));
    }

    @Override
    public void signalListener(String msg, Object data) {

        if(msg == SignalsVO.CLIENT_CONNECTED) {
            GameClient gameClient = (GameClient)data;
            Hero hero = new Hero(gameClient.ID, gameClient.name);
            objects.add(hero);
            signals.dispatch(SignalsVO.NETWORK_SET_CMD, NetMsgVO.ADD_ENTITY);
            signals.dispatch(SignalsVO.NETWORK_SEND, gson.toJson(hero));

        } else if(msg == SignalsVO.CLIENT_DISCONNECTED) {
            GameClient gameClient = (GameClient)data;
            removeObjectById(gameClient.ID);

        } else if(msg == SignalsVO.UPDATE_ENTITIES) {
            updateObject();

        } else if(msg == SignalsVO.MOVE_ENTITY) {
            moveEntity((StringMap)data);

        } else if(msg == SignalsVO.ATTACK_ENTITY) {
            attackEntity((StringMap) data);
        }
    }

    private void attackEntity(StringMap data) {
        Double ID = (Double)data.get("id");
        Double X = (Double)data.get("x");
        Double Y = (Double)data.get("y");
        Double ANGLE = (Double)data.get("a");
        for (int i = 0; i < objects.size(); i++) {
            BaseObject obj = objects.get(i);
            if(obj.getUID() != ID) {
                if(intersect(X,Y,obj)) {
                    removeObjectById(obj.getUID());
                    i--;
                    Log.trace("Kill obj "+obj.getName());
                }
            }
        }
    }

    private void moveEntity(StringMap data) {
        Double ID = (Double)data.get("id");
        Double X = (Double)data.get("x");
        Double Y = (Double)data.get("y");

        for (int i = 0; i < objects.size(); i++) {
            BaseObject obj = objects.get(i);
            if(obj.getUID() == ID) {
                obj.setX(X);
                obj.setY(Y);
                updateObject();
                return;
            }
        }
    }

    private void removeObjectById(double id) {
        for (int i = 0; i < objects.size(); i++) {
            BaseObject obj = objects.get(i);
            if(obj.getUID() == id) {
                objects.remove(i);
                signals.dispatch(SignalsVO.NETWORK_SET_CMD, NetMsgVO.REMOVE_ENTITY);
                signals.dispatch(SignalsVO.NETWORK_SEND, gson.toJson(id));
                return;
            }
        }
    }

    private boolean intersect(Double X, Double Y, BaseObject obj) {
        Double ix = X - obj.getX();
        Double iy = Y - obj.getY();
        Double distance = Math.sqrt( ix * ix + iy * iy );
        if(distance <= 30) return true;
        return false;
    }

    //Line - circle
    /* private boolean objectCollide() {
        //line segment goes from (x1,y1) to (x2,y2)
        //the test point is at (x,y)
        float A = x - x1; //vector from one end point to the test point
        float B = y - y1;

        float C = x2 - x1; //vector from one end point to the other end point
        float D = y2 - y1;

        float dot = A * C + B * D; //some interesting math coming from the geometry of the algorithm
        float len_sq = C * C + D * D;
        float param = dot / len_sq;
        float xx,yy; //the coordinates of the point on the line segment closest to the test point
        // //the parameter tells us which point to pick
        // //if it is outside 0 to 1 range, we pick one of the endpoints
        // //otherwise we pick a point inside the line segment
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
