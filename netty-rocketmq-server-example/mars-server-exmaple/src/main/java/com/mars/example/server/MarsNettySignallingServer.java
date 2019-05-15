package com.mars.example.server;

import com.mars.example.codec.MarsMessageWrapperDecoder;
import com.mars.example.codec.MarsMessageWrapperEncoder;
import com.mars.example.codec.MarsVarint32FrameDecoder;
import com.netty.example.server.proto.SignallingMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于netty的 信令服务器
 */
@Slf4j
public class MarsNettySignallingServer {

    private int port;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ServerBootstrap serverBootstrap;

    private ChannelHandler channelHandler;

    public MarsNettySignallingServer(int port) {
        this.port = port;
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        channelHandler = new ChannelInitializerImpl<SocketChannel>();
    }


    public void start() {

        log.debug("start netty server, port: {}", this.port);

        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(channelHandler)
                .option(ChannelOption.SO_BACKLOG, 128)
//                .option(ChannelOption.SO_BACKLOG, 1024)  //设置TCP连接数缓存区大小
                .option(ChannelOption.SO_RCVBUF, 32 * 1024) //设置接收数据的缓冲区大小
                .childOption(ChannelOption.SO_SNDBUF, 32 * 1024) //设置数据的缓冲区大小
                .childOption(ChannelOption.SO_KEEPALIVE, true);//设置长连接
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("signalling server exception",e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    /**
     * @param <T>
     */
    public class ChannelInitializerImpl<T extends Channel> extends ChannelInitializer<T> {

        @Override
        protected void initChannel(T channel) throws Exception {

            //为通道进行初始化，数据传输过来的时候会进行拦截和执行

            //设置数据识别
            channel.pipeline().addLast(new MarsVarint32FrameDecoder());
//
//            //设置编解码
            channel.pipeline().addLast(new MarsMessageWrapperDecoder());
            channel.pipeline().addLast(new MarsMessageWrapperEncoder());


            channel.pipeline().addLast(new IdleStateHandler(120, 120, 180));
            channel.pipeline().addLast(new MarsSignallingServerHandler());
        }

    }


}
