package com.netty.example.server.processor;

import com.netty.example.server.formatter.DefaultMessageObjectFormatter;
import com.netty.example.server.formatter.MessageObjectFormatter;
import com.netty.example.server.proto.SignallingMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理器
 */
public interface MessageProcessor {

    MessageObjectFormatter MESSAGE_OBJECT_FORMATTER = new DefaultMessageObjectFormatter();

    /**
     * 处理消息
     *
     * @param wrapperMessage
     * @param channelHandlerContext
     */
    void process(SignallingMessage.WrapperMessage wrapperMessage, ChannelHandlerContext channelHandlerContext);


    default <T> T parseMessage(SignallingMessage.WrapperMessage wrapperMessage) {

        return (T)MESSAGE_OBJECT_FORMATTER.parse(wrapperMessage);
    }

}
