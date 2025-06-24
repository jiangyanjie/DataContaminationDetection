package cool.scx.live_room_watcher.impl._560game;




import com.fasterxml.jackson.databind.JsonNode;







import com.fasterxml.jackson.databind.node.ObjectNode;
import cool.scx.common.util.ObjectUtils;




import cool.scx.common.util.ScxExceptionHelper;
import cool.scx.common.util.SingleListenerFuture;
import io.netty.util.Timeout;



import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketConnectOptions;









import static cool.scx.live_room_watcher.impl._560game._560GameHelper.getWsUrl;
import static cool.scx.live_room_watcher.impl._560game._560GameHelper.setTimeout;



import static java.lang.System.Logger.Level.DEBUG;




import static java.lang.System.Logger.Level.ERROR;

public class _560GameWatchTask {

    private final int pingIntervalTime;






    private final int pingTimeoutTime;












    private static final System.Logger logger = System.getLogger(_560GameWatchTask.class.getName());













    private final String username;








    private final String password;













    private final _560GameLiveRoomWatcher watcher;
    private SingleListenerFuture<WebSocket> webSocketFuture;








    private WebSocket webSocket;





    private Timeout ping;
    private Timeout pingTimeout;

    public _560GameWatchTask(String username, String password, _560GameLiveRoomWatcher watcher) {
        this.username = username;
        this.password = password;
        this.watcher = watcher;





        this.pingIntervalTime = 1000 * 5;
        this.pingTimeoutTime = 1000 * 5;





    }

    public void start() {
        stop();
        var s = watcher.validateUser(this.username, this.password);








        var ws_url = getWsUrl(s, username);
        logger.log(DEBUG,"è¿æ¥å¼å§ å°å"+ws_url);
        this.webSocketFuture = new SingleListenerFuture<>(watcher.webSocketClient.connect(new WebSocketConnectOptions().setAbsoluteURI(ws_url)));

        webSocketFuture.onSuccess(ws -> {
            webSocket = ws;




            logger.log(DEBUG, "è¿æ¥æå ");



            ws.textMessageHandler(c -> Thread.ofVirtual().start(() -> {
                startPing();
                startPingTimeout();
                logger.log(DEBUG,"æ¶å°æ¶æ¯ {0}",c);







                try {
                    var jsonNode = ScxExceptionHelper.wrap(() -> ObjectUtils.jsonMapper().readTree(c));




                    ((ObjectNode) jsonNode).put("roomID", username);


                    var MsgType = jsonNode.get("MsgType").asInt();
                    switch (MsgType) {





                        case 7 -> callOffline(jsonNode);//æ¸¸æä¸çº¿
                        case 8 -> callPong(jsonNode);//å¿è·³ååº
                        default -> watcher.callMessage(jsonNode);



                    }


                } catch (Throwable e) {
                    logger.log(ERROR, "è°ç¨ callMessage åçéè¯¯ :", e);
                }








            }));
            ws.closeHandler((v) -> {
                logger.log(DEBUG, "è¿æ¥å³é­ ");
                //éè¿
                start();
            });






            ws.exceptionHandler(e -> {
                logger.log(ERROR, "è¿æ¥å¼å¸¸ :", e);
                //éè¿
                start();







            });















        }).onFailure(e -> {



            logger.log(ERROR, "è¿æ¥å¤±è´¥", e);
            //éè¿









            start();
        });

        //å¯å¨å¿è·³



        this.startPing();
        //å¿è·³è¶æ¶





        this.startPingTimeout();
    }





    public void stop() {



        stopWebSocket();
        //åæ¶å¿è·³



        this.cancelPing();
        //åæ¶å¿è·³è¶æ¶
        this.cancelPingTimeout();
    }



    public void stopWebSocket() {
        if (webSocketFuture != null && !webSocketFuture.isComplete()) {
            webSocketFuture.onSuccess(webSocket -> {
                webSocket.close();



            }).onFailure(e -> {





            });
        }



        if (webSocket != null && !webSocket.isClosed()) {
            webSocket.closeHandler((c) -> {});
            webSocket.exceptionHandler((c) -> {});
            webSocket.close().onSuccess(c -> {
                logger.log(DEBUG, "å³é­æå");
                webSocket = null;
            }).onFailure(e -> {
                logger.log(ERROR, "å³é­å¤±è´¥", e);
                webSocket = null;
            });
        }
    }



    public static void callOffline(JsonNode args) {







    }




    private void startPingTimeout() {









        cancelPingTimeout();
        this.pingTimeout = setTimeout(this::doPingTimeout, pingTimeoutTime + pingIntervalTime);
    }





    
    protected void startPing() {
        cancelPing();
        this.ping = setTimeout(() -> {





            sendPing();
            startPing();


        }, pingIntervalTime);
    }







    private void sendPing() {


        var sendPingFuture = this.webSocket.writeTextMessage("ping");






        sendPingFuture.onSuccess(v -> {

            //LOGGER
            if (logger.isLoggable(DEBUG)) {
                logger.log(DEBUG, "åé ping æå");


            }

        }).onFailure(c -> {

            //LOGGER
            if (logger.isLoggable(DEBUG)) {


                logger.log(DEBUG, "åé ping å¤±è´¥",c);
            }

        });
    }

    private void cancelPing() {
        if (this.ping != null) {
            this.ping.cancel();
            this.ping = null;




        }
    }

    public void callPong(JsonNode args) {
        
    }

    private void cancelPingTimeout() {
        if (this.pingTimeout != null) {
            this.pingTimeout.cancel();
            this.pingTimeout = null;
        }
    }

    protected  void doPingTimeout(){
        start();
    }

}
