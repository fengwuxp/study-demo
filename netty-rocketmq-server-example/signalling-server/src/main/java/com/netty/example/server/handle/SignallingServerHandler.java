package com.netty.example.server.handle;


import com.netty.example.server.heartbeat.DefaultIdleStateEventProcessor;
import com.netty.example.server.heartbeat.HeartbeatMessageProcessor;
import com.netty.example.server.heartbeat.IdleStateEventProcessor;
import com.netty.example.server.processor.ConnectionMessageProcessor;
import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.session.DefaultConnectionSessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SignallingServerHandler extends ChannelInboundHandlerAdapter {


    private final static Map<SignallingMessage.PayloadType, MessageProcessor> MESSAGE_PROCESSOR_MAP = new HashMap<>();

    private IdleStateEventProcessor idleStateEventProcessor = new DefaultIdleStateEventProcessor();

    static {
        MESSAGE_PROCESSOR_MAP.put(SignallingMessage.PayloadType.CONNECTION_REQUEST, new ConnectionMessageProcessor());
        MESSAGE_PROCESSOR_MAP.put(SignallingMessage.PayloadType.PING, new HeartbeatMessageProcessor());
    }

    /**
     * 当通道被激活的时候
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("通道被激活，channelId={}", ctx.channel().id());
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
        MessageProcessor messageProcessor = MESSAGE_PROCESSOR_MAP.get(payloadType);
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
            log.info("连接不存在，{}", ctx.channel().id());
            ctx.close();
            return;
        }

        if (evt instanceof IdleStateEvent) {
            // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
            IdleStateEvent e = (IdleStateEvent) evt;

            IdleState state = e.state();
//            log.error("设备 {}连接状态，state {}", clientChannelContextHolder.getDeviceCode(), state.name());

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
        log.debug("数据读取完成!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.error("发生异常", cause);
        ctx.close();
    }

}
