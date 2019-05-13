package com.netty.example.server.processor;


import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.netty.example.server.helper.MessageBuildHelper;
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

        SignallingMessage.ConnectionRequestMessage requestMessage = this.parseMessage(wrapperMessage);

        if (requestMessage == null) {
            return;
        }
        SignallingMessage.ConnectionStatus connectionStatus;
        //会话标识,设备号
        String sessionIdentifier = requestMessage.getSessionIdentifier().intern();
        synchronized (sessionIdentifier) {
            connectionStatus = CONNECTION_SESSION_MANAGER.join(sessionIdentifier, channelHandlerContext);
        }

        if (connectionStatus == SignallingMessage.ConnectionStatus.SUCCESS) {
            //连接成功
            log.info("设备{},连接成功，当前已连接的设备数：{}", sessionIdentifier, CONNECTION_SESSION_MANAGER.getConnectionTotal());
            this.sendConnectionSuccessMessage(channelHandlerContext);
        } else if (connectionStatus == SignallingMessage.ConnectionStatus.REPEATED) {
            //重复连接,关闭掉这个链接
            log.warn("设备{},重复连接", sessionIdentifier);
            this.sendConnectionRepeatedMessage("重复连接", channelHandlerContext);
//            channelHandlerContext.close();
        } else {
            log.warn("设备{},未知的连接错误", sessionIdentifier);
            this.sendConnectionRepeatedError("未知错误", channelHandlerContext);
        }

    }


    private void sendConnectionSuccessMessage(ChannelHandlerContext channelHandlerContext) {

        SignallingMessage.ConnectionResponseMessage message = SignallingMessage.ConnectionResponseMessage
                .newBuilder()
                .setStatus(SignallingMessage.ConnectionStatus.SUCCESS)
                .build();
        this.sendMessage(MessageBuildHelper.getConnectionResponseMessage(message), channelHandlerContext);
    }


    private void sendConnectionRepeatedMessage(String errorMessage, ChannelHandlerContext channelHandlerContext) {


        SignallingMessage.ConnectionResponseMessage message = SignallingMessage.ConnectionResponseMessage
                .newBuilder()
                .setStatus(SignallingMessage.ConnectionStatus.REPEATED)
                .setErrorMessage(errorMessage)
                .build();
        this.sendMessage(MessageBuildHelper.getConnectionResponseMessage(message), channelHandlerContext);
    }

    private void sendConnectionRepeatedError(String errorMessage, ChannelHandlerContext channelHandlerContext) {

        SignallingMessage.ConnectionResponseMessage message = SignallingMessage.ConnectionResponseMessage
                .newBuilder()
                .setStatus(SignallingMessage.ConnectionStatus.ERROR)
                .setErrorMessage(errorMessage)
                .build();
        this.sendMessage(MessageBuildHelper.getConnectionResponseMessage(message), channelHandlerContext);

    }

    private void sendMessage(MessageLite messageLite, ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext
                .channel()
                .write(messageLite);
        channelHandlerContext.flush();
    }


}
