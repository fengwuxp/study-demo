package com.netty.example.server.session;

import com.netty.example.server.proto.SignallingMessage;
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
    SignallingMessage.ConnectionStatus join(String sessionIdentifier, ChannelHandlerContext channelHandlerContext);


    void remove(String sessionIdentifier);

    void remove(ChannelHandlerContext channelHandlerContext);


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


    /**
     * 获取会话标识
     *
     * @param channelHandlerContext
     * @return
     */
    String getSessionIdentifier(ChannelHandlerContext channelHandlerContext);


    /**
     * 获取连接的总数
     *
     * @return
     */
    int getConnectionTotal();


}
