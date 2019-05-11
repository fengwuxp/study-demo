package com.netty.example.server.session;

import io.netty.channel.ChannelHandlerContext;

/**
 * 连接会话管理器
 */
public interface ConnectionSessionManager {


    /**
     * 加入连接
     *
     * @param sessionIdentifier
     * @param channelHandlerContext
     */
    ConnectionStatus join(String sessionIdentifier, ChannelHandlerContext channelHandlerContext);


    void remove(String sessionIdentifier);

    void remove(ChannelHandlerContext channelHandlerContext);

    /**
     * 获取连接
     *
     * @param channelHandlerContext
     */
    ChannelHandlerContext getConnection(ChannelHandlerContext channelHandlerContext);

    /**
     * 获取连接
     *
     * @param sessionIdentifier 会话标识
     */
    ChannelHandlerContext getConnection(String sessionIdentifier);

    /**
     * 连接是否存在
     *
     * @param channelHandlerContext
     * @return
     */
    boolean existConnection(ChannelHandlerContext channelHandlerContext);

    /**
     * 连接是否存在
     *
     * @param sessionIdentifier
     * @return
     */
    boolean existConnection(String sessionIdentifier);


    public enum ConnectionStatus {

        SUCCESS,

        //Repeated
        REPEATED
    }
}
