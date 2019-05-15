package com.netty.example.server.processor;

import com.netty.example.server.formatter.DefaultMessageObjectFormatter;
import com.netty.example.server.formatter.MessageObjectFormatter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理器
 */
public interface MessageProcessor<M> {

    MessageObjectFormatter MESSAGE_OBJECT_FORMATTER = new DefaultMessageObjectFormatter();

    /**
     * 处理消息
     *
     * @param wrapperMessage
     * @param channelHandlerContext
     */
    void process(M wrapperMessage, ChannelHandlerContext channelHandlerContext);


    default <T> T parseMessage(M wrapperMessage) {

        return (T) MESSAGE_OBJECT_FORMATTER.parse(wrapperMessage);
    }

}
