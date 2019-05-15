package com.mars.example.server;


import com.mars.example.heartbeat.MarsHeartbeatMessageProcessor;
import com.mars.example.mars.MarsMessageWrapperHelper;
import com.mars.example.protocol.MarsMessageWrapper;
import com.netty.example.server.heartbeat.DefaultIdleStateEventProcessor;
import com.netty.example.server.heartbeat.HeartbeatMessageProcessor;
import com.netty.example.server.heartbeat.IdleStateEventProcessor;
import com.netty.example.server.processor.ConnectionMessageProcessor;
import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.session.DefaultConnectionSessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.mars.example.mars.MarsCommandConstant.MARS_CMD_PING_VALUE;

@Slf4j
public class MarsSignallingServerHandler extends ChannelInboundHandlerAdapter {


    private final static Map<Integer, MessageProcessor<MarsMessageWrapper>> MESSAGE_PROCESSOR_MAP = new HashMap<>();

    private IdleStateEventProcessor idleStateEventProcessor = new DefaultIdleStateEventProcessor();

    static {
        MESSAGE_PROCESSOR_MAP.put(MARS_CMD_PING_VALUE, new MarsHeartbeatMessageProcessor());
    }

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

        MarsMessageWrapper marsMessageWrapper = (MarsMessageWrapper) msg;
        if (log.isDebugEnabled()) {
            log.debug("读取消息，{}", marsMessageWrapper);
        }

        int commandId = marsMessageWrapper.getCommand().getCommandId();
        MessageProcessor messageProcessor = MESSAGE_PROCESSOR_MAP.get(commandId);
        if (messageProcessor != null) {
//            messageProcessor.process(requestMessage, ctx);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("未处理的命令类型，{}", commandId);
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
        DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.remove(ctx);
        ctx.close();
    }

}
