package com.netty.example.server.processor;


import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.session.ConnectionSessionManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import static com.netty.example.server.session.DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER;

/**
 * 连接消息处理
 */
@Slf4j
public class ConnectionMessageProcessor implements MessageProcessor {


    @Override
    public void process(SignallingMessage.WrapperMessage wrapperMessage, ChannelHandlerContext channelHandlerContext) {

        SignallingMessage.ConnectionMessage message = this.parseMessage(wrapperMessage);

        if (message == null) {
            return;
        }
        ConnectionSessionManager.ConnectionStatus connectionStatus;
        String sessionIdentifier = message.getSessionIdentifier().intern();
        synchronized (sessionIdentifier) {
            connectionStatus = CONNECTION_SESSION_MANAGER.join(sessionIdentifier, channelHandlerContext);
        }

        if (connectionStatus == ConnectionSessionManager.ConnectionStatus.SUCCESS) {
            //连接成功
            log.info("设备{},连接成功", sessionIdentifier);
        } else if (connectionStatus == ConnectionSessionManager.ConnectionStatus.REPEATED) {
            //重复连接,关闭掉这个链接
            log.warn("设备{},重复连接", sessionIdentifier);
            channelHandlerContext.close();
        }


    }
}
