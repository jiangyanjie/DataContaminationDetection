package blossom.project.netty.rpc.client;










import blossom.project.netty.rpc.codec.MessageDecode;




import blossom.project.netty.rpc.codec.MessageEncode;
import blossom.project.netty.rpc.enums.ReqTypeEnum;



import blossom.project.netty.rpc.protocol.Header;
import blossom.project.netty.rpc.protocol.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;






import io.netty.channel.EventLoopGroup;










import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;










import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;





import java.net.InetSocketAddress;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/14 21:51
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1


 * @github: https://github.com/ZhangBlossom





 * Bootstrapå¯å¨ç±»


 */


public class _ClientBootstrap {
    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap
                .group(worker)
                //è®¾ç½®Nettyç ç¦ç¨Nagleç®æ³ å®ç°
                .channel(NioSocketChannel.class)






                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {













                ch.pipeline()









                        .addLast(new LengthFieldBasedFrameDecoder







                                (1024 * 1024,



                                        9,
                                        4,


                                        0,
                                        0))
                        .addLast(new MessageEncode())





                        .addLast(new MessageDecode())


                        .addLast(new ClientMessageHandler());
            }
        });



        try {
            ChannelFuture future = clientBootstrap
                    .connect(new InetSocketAddress("localhost", 8080)).sync();





            Channel channel = future.channel();

            //2ï¼ä½¿ç¨å¤çº¿ç¨åéæ¶æ¯
            //for (int i = 0; i < 10; i++) {
            //    final int index = i;

            //   å¯ä»¥èèä½¿ç¨çº¿ç¨æ± 
            //    new Thread(() -> {





            //        Message record = new Message();




            //        Header header = new Header();
            //        header.setReqId(System.currentTimeMillis());
            //        header.setReqType(ReqTypeEnum.ON.getCode());
            //        record.setHeader(header);
            //        String body = "this is the Client Message, which id is :" + index;


            //        record.setBody(body);
            //        channel.writeAndFlush(record);



            //    }).start();
            //}










            //1ï¼ä½¿ç¨å»¶è¿åéæ¶æ¯
            for (int i = 0; i < 100; i++) {
                Message message = new Message();







                Header header = new Header();
                header.setReqId(System.currentTimeMillis());
                header.setReqType(ReqTypeEnum.GET.getCode());
                message.setHeader(header);
                String body = "this is the Client Message, which id is :" + i;
                message.setBody(body);
                channel.writeAndFlush(message);
                //Thread.sleep(100);


            }

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {




            //ç»å¸çä¼éåæº
            worker.shutdownGracefully();
        }
    }
}
