package com.netty.example.server.session;

import com.netty.example.server.proto.SignallingMessage;
import com.sun.org.apache.regexp.internal.RE;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 默认的连接管理器
 */
@Slf4j
public class DefaultConnectionSessionManager implements ConnectionSessionManager {


    public static final ConnectionSessionManager CONNECTION_SESSION_MANAGER = new DefaultConnectionSessionManager();


    private final Map<String, ChannelHandlerContext> channelHandlerContextMap = new ConcurrentHashMap<>();


    private static final String SESSION_IDENTIFIER = "SESSION_IDENTIFIER";


    protected static final AttributeKey SESSION_IDENTIFIER_KEY = AttributeKey.newInstance(SESSION_IDENTIFIER);

    @Override
    public SignallingMessage.ConnectionStatus join(String sessionIdentifier, ChannelHandlerContext channelHandlerContext) {

        if (this.existConnection(sessionIdentifier)) {
            //连接已经存在
            log.error("会话标识为{}的连接已经存在", sessionIdentifier);
//            return SignallingMessage.ConnectionStatus.REPEATED;
        }

        Attribute attribute = channelHandlerContext.channel().attr(SESSION_IDENTIFIER_KEY);
        attribute.set(sessionIdentifier);
        this.channelHandlerContextMap.put(sessionIdentifier, channelHandlerContext);

        return SignallingMessage.ConnectionStatus.SUCCESS;

    }

    @Override
    public void remove(String sessionIdentifier) {
        this.channelHandlerContextMap.remove(sessionIdentifier);
    }

    @Override
    public void remove(ChannelHandlerContext channelHandlerContext) {
        String key = getSessionIdentifier(channelHandlerContext);
        this.remove(key);

    }


    @Override
    public ChannelHandlerContext getConnection(ChannelHandlerContext channelHandlerContext) {

        String key = getSessionIdentifier(channelHandlerContext);

        return this.getConnection(key);
    }

    @Override
    public ChannelHandlerContext getConnection(String sessionIdentifier) {
        ChannelHandlerContext channelHandlerContext = this.channelHandlerContextMap.get(sessionIdentifier);
        if (channelHandlerContext == null) {
            return null;
        }
        boolean needRemove = channelHandlerContext.isRemoved() && !channelHandlerContext.channel().isActive();

        if (needRemove) {
            //已经被移除
            this.channelHandlerContextMap.remove(sessionIdentifier);
            channelHandlerContext.close();
            return null;
        }
        return channelHandlerContext;
    }

    @Override
    public boolean existConnection(ChannelHandlerContext channelHandlerContext) {
        String key = getSessionIdentifier(channelHandlerContext);

        return this.existConnection(key);
    }

    @Override
    public boolean existConnection(String sessionIdentifier) {
        if (sessionIdentifier == null) {
            return false;
        }
        return this.channelHandlerContextMap.containsKey(sessionIdentifier);
    }

    @Override
    public String getSessionIdentifier(ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext.channel().hasAttr(SESSION_IDENTIFIER_KEY)) {
            return null;
        }
        Attribute attr = channelHandlerContext.channel().attr(SESSION_IDENTIFIER_KEY);
        return (String) attr.get();
    }

    @Override
    public int getConnectionTotal() {
        return this.channelHandlerContextMap.size();
    }
}
