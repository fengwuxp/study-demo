package com.netty.example.server.heartbeat;

import com.google.protobuf.MessageLite;
import com.netty.example.server.helper.MessageBuildHelper;
import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳消息处理
 */
@Slf4j
public class HeartbeatMessageProcessor implements MessageProcessor {


    private static final Byte PINT = 1;

    private static final Byte PONG = 1;

    @Override
    public void process(SignallingMessage.WrapperMessage wrapperMessage, ChannelHandlerContext channelHandlerContext) {
        SignallingMessage.PingMessage pingMessage = this.parseMessage(wrapperMessage);

        if (PINT.equals(pingMessage.getPing())) {
            ;
            SignallingMessage.WrapperMessage message = MessageBuildHelper.getPongMessage(
                    SignallingMessage.PongMessage
                            .newBuilder()
                            .setPong(PONG)
                            .build());
//            channelHandlerContext.write(MessageBuildHelper.getDefaultSignallingWrapperMessage(message));

        } else {
            log.error("ping message error，receive message = {}", pingMessage.getPing());
            //TODO
        }

    }
}
