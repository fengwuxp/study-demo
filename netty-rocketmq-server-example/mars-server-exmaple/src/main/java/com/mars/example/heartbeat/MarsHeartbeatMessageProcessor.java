package com.mars.example.heartbeat;

import com.mars.example.helper.MarsSendMessageHelper;
import com.mars.example.mars.MarsMessageWrapperHelper;
import com.mars.example.protocol.MarsMessageWrapper;
import com.netty.example.server.helper.MessageBuildHelper;
import com.netty.example.server.helper.SendMessageHelper;
import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import static com.mars.example.mars.MarsCommandConstant.MARS_CMD_PING_VALUE;

/**
 * 基于心跳消息处理
 */
@Slf4j
public class MarsHeartbeatMessageProcessor implements MessageProcessor<MarsMessageWrapper> {


    @Override
    public void process(MarsMessageWrapper wrapperMessage, ChannelHandlerContext channelHandlerContext) {

        if (log.isDebugEnabled()) {
            log.debug("收到ping消息，commandId{}", wrapperMessage.getCommand());
        }

        //回复pong消息
        MarsSendMessageHelper.sendMessage(MarsMessageWrapperHelper.buildMessage(MARS_CMD_PING_VALUE, null),
                channelHandlerContext);

    }
}
