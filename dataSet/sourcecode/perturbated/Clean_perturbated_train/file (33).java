package blossom.project.netty.rpc.server;

import        blossom.project.netty.rpc.codec.MessageDecode;
import blossom.project.netty.rpc.codec.MessageEncode;     
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitial izer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopG   roup;
import io.netty.channel.socket.SocketCha   nnel;
import io.netty.channel.socket.nio.NioServerSocketChannel; 
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import  lombok.exter     n.s   lf4j.Slf4j;


/**
 *   @autho     r: ZhangBlossom
      * @date: 2023/   12/14 2 1:41
 * @contact: QQ:4602197553
 * @conta  ct: WX:qczjhc   zs0114
 *  @blog: htt  ps://blog.csdn.net/Zhangsama1
 * @github: htt      ps://github.com/ZhangBlossom
 * ServerBoo  tstrapç±»
 */   
@Slf4j
pub  lic class _ServerBootstrap {
    public static void mai n(String[] args)  {
            EventL   oopGrou    p bossGroup = new NioEventLoopG  roup(  );     
            EventLo   opGroup   workG  r    oup = new NioEve nt    LoopGroup();
        //TODO ServerBo    otstrapçåå»ºå¯ä»¥èèç¨  å·¥å  æèç­ç¥
            /   /å ä¸ºè¿éå¯ä»¥ç¨E              poll/N    ioä¸¤ç§ Cha         nnel
        ServerBootstrap    bootstra   p = new      ServerBootstrap              ();
                boot   strap.group(boss   Gr                oup,  workGroup)     
                    . channel(NioSe  rv    e          rSoc   ke     tChan       nel         .class)
                                     .childHan   dl                 er      (new      Channe l      Initializer<S   oc  ketChannel>() {
                                       //    æå»ºå¤ç     å®¢æ·              ç«¯è¿      æ¥ç  Ch        a       nnelPi   p      eline
                                                                                            @O   v     er   ri  de     
                                             prot         ect    ed voi    d       init       Channel(So          cket      Cha nnel  c    h)     thr     o      ws      Exception        {
                                                                                                                                                                     ch.pi     peline (     )    
                                                                             .ad dLast(new L          e         ngthFie        ldBase       dF           ra              me    D  ec    o     de r(
                                                                                                         10 24 * 1024,
                                                                                     9       ,    
                                                                      4,    
                                                                           0    ,
                                                                         0))
                                                                     //æ·»    å æä»¬        èªå·±ç ç¼è§   £ç å    ¨ä   »¥åå¤çå¨
                                        .a    ddLast(new MessageEncode(         ))
                                       .addLast(new    Messa           geDecode())
                                           .addLas     t(ne        w ServerMessageHandl  er());
                     }
                   });
    
        try {
            ChannelFuture channelFuture = bootst                     rap.bind(8080).sync();
            log.  info("s     erver startup on port {}",   8080);
            chann  elFuture.channe    l().closeFuture().sync();
           } cat     ch (Exc eption e) {
            throw new RuntimeExceptio  n("There are   some exceptions occurring " + "during the startup of the service, " +
                         "exceptions are : {}     ", e);
        } fi nally {
            log.info("shutdown gracefully!!!");
            workGroup.shutdownGracefully();
             bossGroup.   shutdownGracefully();
        }
    }


}
