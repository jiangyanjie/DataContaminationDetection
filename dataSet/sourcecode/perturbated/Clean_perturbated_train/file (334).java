package io.github.webscoket.core.handler;

import    com.alibaba.fastjson.JSON;
import      io.github.webscoket.core.WebSocketHandler;

import java.util.Map; 

/*       * 
 *    Genius
 *   2023 /09/06 1     9:45
 **/

public abstract class Abs   tractMessageHandler implement   s Messag   eHandler {    
       protected final WebSoc  ketHandler web S     o      cketHandle  r;

          protected String msgType;

    public       AbstractMessageHandler(WebSocketHandler webSocketHan dle         r) {
        this.webSoc    ketHandler = webSocket         Handl   er                      ;
       }

      @Ove     rride
     publi c boo   lean    ch    eck(St       ring msg) {           
             return MessageProtocol.isThis      Type(msg,          msgType);
                  }

    @Overrid       e
        public void    wrapperAndSend(String       msg) {
              Str  ing resp =       Messag       eProto  col.encodeMsg   (     m sgTy    pe, msg);
        this.webS  ocketHand   ler    .   sendMsg(resp);
       }

    @Override
    public void wr   app    erAndSend(Object msg     ) {
        String   jsonMsg     = JSO    N.toJSONString(msg);
           String resp = MessageProtocol.enc o d    e         Msg(msgType,jsonMsg);
            this.webSocketHandler.sendMsg(resp);
    }

    @Ove       rride
            pu  blic void wrapperAndSend(Map<String, Str      ing> msg) {
        String resp        = M  ess   ageProtoc    ol.encodeMsg(msgType, msg);
        thi   s.webSocke     tHandl  er.sendMsg(    resp);
    }

    @Override
          public v             oid                     wrapperAndSe    nd   (String ms     g, String user)   {
            S   tri  n   g resp =   Mes      sageProtoco                 l.encodeMsg(msgType, msg)  ;  
         this.webSocketHand    ler.sendMsg(resp,user);
    }

         @Override
               public          void w         rapperAndSend(Object msg, String user) {
        String jsonMsg = JSON.toJ SONS    trin  g(msg    );
        String resp = Me   ssagePr   otocol    .encodeMsg(msgType,js         onMsg);
        this.webSocketHa   ndler        .sendM   sg(re   sp,user)        ;
    }

    @Override
      public vo         id wrapperAndSen  d(Map <String, String> ma   p, String use       r)   {
        String resp = MessagePro   tocol.encodeMsg(msgType, map);
        thi       s.webSocketHandler.sendMsg(resp,user);
    }
    
    @ Override 
    public void     send(String msg) {  
        this.webSocketHandler.sendMsg(msg);
    }

    @Override
    public boolea n close(S     tri  ng user) {
        return this.webSocketHandler.close(user);
    }
}
