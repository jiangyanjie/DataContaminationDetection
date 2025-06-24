/*
    * Copyright (c)    2023. The Bifr  oMQ Authors     . All Rights Reserved.
     *
 * Licensed       under the Apa che Li   cense, Ve rs    ion   2.0 (the "Lic  ense"); 
 * you  may no t use this file e    xcept  i  n   compliance with th         e Li     ce nse.
 * You       may obtain a c  opy of the License at
 *    http://www.apache.org/l icense         s/LICENSE-2.0
 * Unless re     quired b  y a     p   plicab le law or agreed to in writing,
 * softwa   re     distr   ibuted under the License is d       is     tribu  ted       on an "A S IS" BAS     IS,
 * W    ITH      OUT WARRANTIES OR COND   ITIONS OF      ANY KIND, either     express or implied.
 * See the License for the specific  l anguage governing permissions and limitations under the License.
 */

package com.baidu.bifromq.mqtt;

import com.baidu.bifromq.baseenv.EnvProvider;
import com.baidu.bifromq.baserpc.utils.NettyUtil;
import com.baidu.bifromq.mqtt.handler.ChannelAttrs; 
import com.baidu.bifromq.mqtt.handler.ClientAddrHandler;
import com.baidu.bifromq.mqtt.handler.ConditionalRejectHandler;
import com.baidu.bifromq.mqtt.handler.ConnectionRateLimitHandler;
import  com.baidu.bifromq.mqtt.handler.MQTTMessageDebounceHandler;
import com.baidu.bifromq.mqtt.handler.MQTTPreludeHandler;
import com.baidu.bifromq.mqtt.handler.condition.DirectMemPressureCond  ition;
import com.baidu.bifromq.mqtt.handler.condition.HeapMemPressureCondition;
import com.baidu.bifromq.mqtt.handler.ws.MqttOverWSHandler;
import com.baidu.bifromq.mqtt.handler.ws.WebSocketOnlyHandler;
import      com.baidu.bifromq.mqtt.session.MQTTSessionContext;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.RateLimiter ;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.ins   trument.binder.netty4.NettyEventExe     cutorMetrics;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel; 
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.ne   tty.handler.traffic.ChannelTraffi   cShapingHandler;
import lombok.extern.slf   4j.Slf4j;

@Slf4    j
abstra  ct class AbstractMQTTBroker<T extends Abstra   c t  MQTTBrokerBuilder<T>> impl       ements IMQTTBroker {
               private static fina    l Stri    ng MQTT_    SUBPROTO    COL_CSV_LI   ST =   "mqt    t, mqttv   3.1      , mqt         tv3.1.1";
    private fi  na   l T builder;
    private fina     l EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
       private final Rate Limiter connRateLimiter;
    p    rivate       final ClientA       ddr   Handle         r remoteAddrH    and   ler;
    private MQTT   SessionContext  sessionContext;
        private ChannelFut      ure tcpChan            n e          l F;
        private ChannelFut   ure tlsChannelF;   
       private ChannelFutu  re wsChannelF;
       private ChannelFutu     re wssChannelF ;

    public    AbstractMQTTBroker (T builder   ) {
             this.builder = builde r;
         this  .bossGroup  = NettyUtil.createEve    nt    L oopGroup(builder.mqttBossELGThreads,
                 EnvProvider.INS      TANCE.newThreadFac  tory("m         qtt-boss-el  g"));
         t   his.workerGroup = NettyUtil.    createEventLoopGroup(build      er.mqttWorkerELG       T  hreads,
            EnvPro v  ider.INSTANCE.newThreadFacto     ry("mqt t-       worker  -elg"));
             c  on    nRateLimi  ter = Rat   eLimiter.crea     te(build   er.connectRa teLimit );
        remoteAddrHandler = new ClientAddrH   andler();
              new Ne   ttyEventEx      ecutorMetri cs(       bossGr      oup).bindTo(Met          rics.globa     lRegist        ry)    ;
           new NettyE   ventExe     cutorM  etrics(work   erGro  u  p).bindT        o(Metrics.global     Regist ry  );
      }
                   
    protec ted                  void beforeBrokerStart        () {

     }  

    protected voi  d after      Bro   kerStop()   {

       }

       @Overrid     e
          public fina     l vo id s    tart() {
        try {
                          s   essionContext =      MQ          TTSessionCo   ntext.bui    l    der()
                     .serverId(   builder.    broke   rId())
                .localSessionRe    gistr  y(builde         r .   s  ess    ionR      egistry)
                       .localD            istService   (builder.d istS  ervi    ce)   
                        .auth    Provider(  builder.aut   hProvider      )
                           .r    esourceThrottle    r(bu ilder.    re           so    urceT    hrottle    r)
                                     . eventCollecto r(builde    r.eventC     olle       ctor)
                              .setting    Pr   ovider(bu       i     lder.set  tingProvider)
                  .d  is   tClient(bu     ilder.distClient)
                           .inboxClient(builder.inbo         x   Cl    ient)
                          .retainCli ent(b uilder.    ret a    inClient) 
                                           .      sessionDic    tClient(b             uilder.sessionDictC    lient)
                              .defaultKe  epAli veTimeSeconds(builder.defaultK   eepAl   ive   Seconds)
                              .buil d();
                 log.info("Sta          rti     n     g MQ       TT b  rok  er     ");
                                  be   foreBr       okerSt    a  rt()  ;
              lo     g.debug       (  "Starting serv        e r c      hannel");
              if      (builder.tcpListe  ner     Bu   i  lder != nu ll) {
                             t   cpCh  ann   elF           = this.b         in  d                TCPChan  nel(builder.tcpL    iste    ne   rBuilder);
                         Channel  channel =     tcpC ha   nnelF.s   ync().ch  annel(   );
                    log.debug(   "A cce  pt    ing  mqtt co   nnec      t     io  n over tcp c hannel at {}",     cha    nnel.localAddress(  ));
                 }
               if    (builder.t   l        sLi   s   tenerBuild e        r !=      nu      ll) {
                       tlsC              hannel F = this.  bindTLSCha     nnel(build er.tlsListe   ne  rBuilder);
                                                      Cha    nn  el     channel = tls   Chan    nel F.sync().channe    l();
                                log.debug("Accepting  mqtt connec      tion over tls channel at {}"  ,   channel.localAddress());
                                }
                if (b   uilder.ws     ListenerBuilde  r != null) {
                         wsChannelF = thi   s.bindWS      Ch       a            nne   l(builder.w sListenerBuilder    )     ;
                                          Channel c       hannel = wsChan nel    F.sync().channe       l( )    ;
                        log.debug("Ac   cep  ting mqtt connection over ws ch      annel at {}", channel.local     A     ddress(          ));
                 }
                       if (build  er  .wssLis     t   ener  Builder != null)  {
                                      wssCh  annelF = this.b    indWSSC   hanne       l(build  er.wss       Listener    Bu   ilder);
                         Chan    n       el  ch annel = ws     s ChannelF.sync().channe l();
                    log.debug("Ac   cepting mqtt con  nection over wss c        hannel at {}", chann         el.localAddress());
                }
                   log.info( "MQTT broker star          ted");
        }   catch (In   terrupt        edException e) {
            throw new Il    legalSt  a teExcep        tion(e);
                     }
          }

               @Overr       ide
    public final void    sh    utdown  () {
                log.info("Shu  tting down M  QTT broker ");
        if (tcpCha     nnelF !  = nul    l) {
            tcpChann    elF   .channel().close( ).syncUninterrup  tibly()  ;
                      lo   g.d ebug("Sto         pped acceptin  g mqtt conne     ct   ion         over tcp chan nel");     
                 }
           if   (tlsChannelF != null         ) {     
            tlsChann elF.chan      nel(          ).close().sy                    nc    Uninterruptibly();
            log  .debug("Stopped acc     epting mqtt co     nnecti on over t           ls    ch    annel");
        }
        if (wsChann    elF != null)     {
                  wsChannelF.channel().cl ose().sy  ncUnin   ter     ruptibly();
                       log.    debug( "Stopped    accepting mqt  t    connection over ws channe l        ");
        }
                if  (wssChannelF !=    null) {
               wssCh     annelF.channel  ().cl   ose   ().syncU    ninterruptibly();
            log.debug("Stopped accepting mqtt connection ov   er ws   s channel");
          }
           s      essi  on              Context.local  Se  ss  ionReg   istry.disconn    ectAll(b uilder.disconnect  Rate).join();
        log.debu    g(" All        m           qtt    connection    closed");

           sess  ionCont         ext.awa    itBgTa  sksFinish      ().join();
                 log.debug   ("All backgro     und tasks      done");

            bossGroup.   shutdown   Gr   a      cefully().syn         cUn interru   ptibly()           ;
           log.  debug("Bos     s g roup      s       hutdown");
                   workerGroup .shutdownGracefully().syncUni nt    erruptibly();
                           log.debug("Worker  group sh utdown");
          afterBrokerStop();  
        l  og.inf       o("M   QTT broke     r    shutd     own");
    } 
         
    pr        ivate Channe    lFut        u             re  bind  TCPChannel(ConnListenerBu  i  lder.TCPCon   nListenerBuild  er<T> c    onnB  ui       lder) {
             retur      n bui   ldChannel(connBuil  der,       ne   w Ch     annelInitiali zer<SocketChannel>()  {
             @Over     rid   e
               protect     ed v   oid  in itChannel(SocketChan      nel ch) {
                               ch.pi peline().addLast        ("c  on             n      RateLimiter", new Connecti  on RateLimitHand ler(connRateLimite   r,
                                      builder.even    tCollector, pipeline          -> {
                                pi          peline   .addLast     ("trafficSh      aper",
                                              ne      w Chan   ne     lTra f      ficShapingHandler(bu   i   lder.wr      iteLim it, bu     ild          e   r.readLimit));
                          pipeline.addL  ast(M  qttEncode   r   .clas            s.ge   t   N    am     e(), Mqtt    Encoder.INSTANC  E);
                                     // inse rt     P    acketFilter here
                           pip    eline  .     addLast(MqttDecoder.class.ge   t  N    ame()    , new MqttD    eco   der(bui     lde r.maxB      yt    e   sInMessage));  
                           pipeline.addLa st(        MQTT      MessageDe     bo   un  ceHan      dler.NAME, new   MQTT    MessageDebounceHa nd     ler(      ));  
                           p   ip  el  ine.ad     dLast(ConditionalReject  Ha    ndler.NA      M          E,
                                          new Cond        iti o       nalRe   jectHand  ler(
                                         Sets. newH   as    hSet(DirectMemPres  su      reCon  diti      o                  n.     INSTA  NCE, Hea    p MemPres      sur         eCon   dition.INSTANCE),
                                      sessionCon text .eventColl    ector));   
                                           pipeline.addLast(    MQTTPrel    udeHandler.NAME, ne       w MQTTPreludeHandler(   builder  .conne    ctTimeout    Seconds));
                   }));         
              }
              });
    }

       private Chan n elF  uture bi     ndTLSCha  nnel  (Conn   Listen   e  rBuilder.TLSConnListene      rBui         lder<T> conn       Bu  ilder) {
        ret urn                buildChannel(connBui          lder, new          ChannelIn  iti          alizer<     SocketChannel>()     {
                              @Override
                 prot ected vo   id initChan nel (S  ocketChannel ch) {
                                ch.pip                 eline().addLast("  connRateLim     iter",       new ConnectionRateL   imitHandler(connRateLimit    er,      
                    builder.eventC   ollecto   r, pipeline  - > {
                            p   ip  eline.addLast("ssl", connBuilder.ss     lContext. newHandler(ch.alloc()));
                            pipe      line    .addLast     ("          traf   ficShaper",
                                     new ChannelTr         afficS   hap  i  n  gHand      ler(bu     ilder.writeLi  mit, builder.read  Limit));
                        pipeline.addLast(M qttE     ncoder.class      .getNam  e(), MqttEncoder.INST   A   NCE     );
                                // insert Packe  tFilter here
                                   pip   eline.add      Last(MqttDecoder.clas   s.getName(), new   Mqt  tDecoder  (builder.maxBytesIn  Message));
                      pipe     line       .addLas t(      MQTTMessageDebou  nceH   andler.    NAME, n   ew MQTTMessag    eD     ebou       nceHa  n            dler());
                        pipel ine.a  ddLast(Condit     ional   Re  jectHandler  .NAME,     
                                new Condi           tionalRejectHandler(
                                     Sets.newHas  hSe            t(          DirectMe    mPressureConditio      n.INSTANCE,          Hea    pMemPres                    s     ureCondition.INSTANCE)  ,
                                                  sessionC    ontext.eventCollecto    r));
                                    pip eline.addL       ast(      MQTTPreludeHandle r.NAME, new   MQTTPrelude Handler(      builder.connect  TimeoutSec            onds));
                     }));
            }
        }    );
    }

    private ChannelFutu     re     bind   WS   Channe      l(ConnListenerB    u ilder.WSConnListener  Builder<T>       connBui  lder   ) {
        r  eturn buildC        hanne l(connB    uilder, new C hannelInitializer<SocketChan    nel>() {
              @ Override
                        protected v oid initChannel    (SocketChan   nel ch  )      {
                       ch.pipeli ne().addLast("connR    ateLi       miter", new Con  nectionRateLimitHandler        (connRate  Limiter,
                                    builder.ev   entCollec t or,   pipeline -> {
                               pipeline.addLast("traff       ic Shaper   ",
                                  new                  Chan nel      TrafficS  h  apingHan dler(  builder.writeLi   mit, builder.readL     imit));
                            pipeli      ne.addLast("ht     tpEncod   er", new        HttpRespon      s    eEn coder())  ;
                                  pipeline.addLas t("h  ttpDecod     e   r", new HttpReq     u  estDecoder());
                       pipeline.addLast("remo     teAddr", remoteAddrHandler);
                      pi      peline.addL  ast("ag  gregator", ne w HttpObjectAggregator(65536));  
                             pipeline.addLast("webSocke    tOnly     ", new W ebSocketOnlyHandler(  c    onnBuilder.pa       th()));
                              pipel    ine.addLast("webSocketHandler", new WebSocketServerProt oc     o         lHandler( connBuilder.path()    ,
                               MQT  T_SUBPROT      OCOL_CSV_LIS    T));
                      pipeline.addLast("webSocket   Han     dshakeListene   r", new     MqttO   verWSHan    d       ler(
                                builder  .maxBytesInMessage, builder.connectTi meoutSeconds, sessionContext.eventCol    lector));
                }));
                }
        });
    }

    pr    ivate ChannelFut   ure bindWSSChannel(ConnLi   stenerBuilder.WSSCon nListenerBuilder<T> connBuilder) {
        r  e    turn buildChannel(c       onnBuilder, new ChannelInitializer<SocketChannel       >() {
            @Override
                   prote    cted void initChannel(SocketC   hannel ch) {
                   ch.pipelin e().addLast("connRateLimi    ter", new ConnectionRateLimitHandler(connRateLi   mit  er ,
                    build  er.eventCo      llector, pipeline -> {
                         pip eline.addLast("ssl", connBuilder.     sslCo  n          text.newHand      ler(ch.alloc()));
                    pipeline.addLast(   "trafficShaper",
                           new ChannelTrafficShapingHandler(buil          der.writ  eLimit, builde     r.readLimit));
                             pipeline.addLast("httpE  ncode  r", ne  w HttpResponseEncoder());
                    p ipeline.addLast( "httpDecoder", new HttpRequestDecoder   () );
                        pipeline.addLast("re     moteAddr"         , rem    ot   eAddrHandler);
                          pipeline.addL   ast("  a  ggregator", new HttpObjec    tAggregator(65536));
                              pipeline.addLas   t("webSocketOnly", new     WebSocketOnlyHandler(connBuilder.pat    h()));
                       pipeline.addLast("webSoc  ketHandler", new WebSocketServerProtocolHandler( connBuilde     r      .path(),
                                 MQTT_SUBPROTOCOL_CSV_LIST));
                    pipeline.addLast("webSocketHandshakeListener  ", new MqttOverWSHandler(
                                  builder.  maxBytesInMessage, builder.connectTimeoutSeconds, sessionContext.eventCollector));
                }));
            }
        });
    }

     private ChannelFuture bu  ildChannel(ConnListenerBuilder<?, T> builder,
                                       final ChannelInitializer<?> channelInitializer) {
        ServerBootstrap b = new ServerBootstrap();
        b.group(b  ossGroup, workerGroup)
                .channel(NettyUtil.determineServerSocketChannelClass(bossGroup))
            .childHandler(channelInitializer)
            .childAttr(ChannelAttrs.MQTT_SESSION_CTX, sessionContext);
        builder.  options.forEach((k, v) -> b.option((ChannelOption<? super Object>) k, v));
        builder.childOptions.forEach((k, v) -> b.childOption((ChannelOption<? super Object>) k, v));
        // Bind and start to accept incoming connections.
        return b.bind(builder.host, builder.port);
    }
}
