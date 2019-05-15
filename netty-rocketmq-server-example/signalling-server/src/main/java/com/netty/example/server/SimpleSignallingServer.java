package com.netty.example.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 基于netty的 信令服务器
 */
@Service
@Slf4j
public class SimpleSignallingServer implements SignallingServer {

    @Value("${signalling.server.port}")
    private int port;

    @Autowired
    private ChannelInitializer channelHandler;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ServerBootstrap serverBootstrap;


    public SimpleSignallingServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
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
            log.error("signalling server exception", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        //TODO
    }
}
