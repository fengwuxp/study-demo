package com.netty.example.server.handle;


import com.netty.example.server.heartbeat.HeartbeatMessageProcessor;
import com.netty.example.server.heartbeat.IdleStateEventProcessor;
import com.netty.example.server.processor.ConnectionMessageProcessor;
import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.session.DefaultConnectionSessionManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
@ChannelHandler.Sharable
public class SignallingServerHandler extends ChannelInboundHandlerAdapter {


    @Autowired
    private IdleStateEventProcessor idleStateEventProcessor;

    @Autowired
    private Map<SignallingMessage.PayloadType, MessageProcessor<SignallingMessage.WrapperMessage>> messageProcessorMap;


    /**
     * 当通道被激活的时候
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("通道被激活，channelId={}", ctx.channel().id());
        }
    }

    /**
     * 当通道里有数据进行读取的时候
     *
     * @param ctx NETTY服务的上下文
     * @param msg 实际读到的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        SignallingMessage.WrapperMessage requestMessage = (SignallingMessage.WrapperMessage) msg;
        if (log.isDebugEnabled()) {
            log.debug("读取消息，{}", msg);
        }

        SignallingMessage.PayloadType payloadType = requestMessage.getHeader().getPayloadType();
        MessageProcessor<SignallingMessage.WrapperMessage> messageProcessor = messageProcessorMap.get(payloadType);
        if (messageProcessor != null) {
            messageProcessor.process(requestMessage, ctx);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("未处理的数据类型，{}", payloadType);
            }

        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


        if (!DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.existConnection(ctx)) {
            //连接不存在
            log.info("连接不存在，设备={}", DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.getSessionIdentifier(ctx));
            ctx.close();
            return;
        }


        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            IdleState state = e.state();

            if (log.isDebugEnabled()) {
                log.debug("设备{}，连接空闲，state={}",
                        DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.getSessionIdentifier(ctx),
                        state);
            }

            switch (state) {
                case READER_IDLE:
                    this.idleStateEventProcessor.readIdleProcess(ctx);
                    break;
                case WRITER_IDLE:
                    this.idleStateEventProcessor.writeIdleProcess(ctx);
                    break;
                case ALL_IDLE:
                    this.idleStateEventProcessor.allIdleProcess(ctx);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("数据读取完成!");
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("通道连接发生异常，设备={}", DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.getSessionIdentifier(ctx), cause);
        }
        boolean needRemove = ctx.isRemoved() && !ctx.channel().isActive();
        if (needRemove) {
            DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.remove(ctx);
            ctx.close();
        }

    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);

        if (log.isDebugEnabled()) {
            log.debug("通道连接断开，设备={}", DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.getSessionIdentifier(ctx));
        }

        DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.remove(ctx);
    }
}
