package com.netty.example.server.heartbeat;

import com.netty.example.server.helper.MessageBuildHelper;
import com.netty.example.server.helper.SendMessageHelper;
import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳消息处理
 */
@Slf4j
public class HeartbeatMessageProcessor implements MessageProcessor<SignallingMessage.WrapperMessage> {


    private static final Integer PINT = 1;

    private static final Integer PONG = 1;

    @Override
    public void process(SignallingMessage.WrapperMessage wrapperMessage, ChannelHandlerContext channelHandlerContext) {
        SignallingMessage.PingMessage pingMessage = this.parseMessage(wrapperMessage);

        if (log.isDebugEnabled()) {
            log.debug("收到ping消息，{}", pingMessage.getPing());
        }
        if (PINT.equals(pingMessage.getPing())) {
            SendMessageHelper.sendMessage(MessageBuildHelper.getPongMessage(SignallingMessage.PongMessage
                    .newBuilder()
                    .setPong(PONG)
                    .build()), channelHandlerContext);
        } else {
            log.error("ping message error，receive message = {}", pingMessage.getPing());
            //TODO
        }

    }
}
