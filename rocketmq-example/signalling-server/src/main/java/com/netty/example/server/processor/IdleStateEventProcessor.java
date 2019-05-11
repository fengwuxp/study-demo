package com.netty.example.server.processor;

import io.netty.channel.ChannelHandlerContext;

/**
 * channel 空闲状态 处理
 */
public interface IdleStateEventProcessor {


    /**
     * 读空闲时处理
     * @param channelHandlerContext
     */
    void readIdleProcess(ChannelHandlerContext channelHandlerContext);

    /**
     * 写空闲时处理
     * @param channelHandlerContext
     */
    void writeIdleProcess(ChannelHandlerContext channelHandlerContext);

    /**
     * all空闲时处理
     * @param channelHandlerContext
     */
    void allIdleProcess(ChannelHandlerContext channelHandlerContext);
}
