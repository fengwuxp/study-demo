package com.netty.example.server.handle;


import com.netty.example.server.ClientChannelContextHolder;
import com.netty.example.server.formatter.MessageObjectFormatter;
import com.netty.example.server.processor.IdleStateEventProcessor;
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
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SignallingServerHandler extends ChannelInboundHandlerAdapter {


    private Map<String, ClientChannelContextHolder> CLIENT_SESSION_MAP = new ConcurrentHashMap<>();


    private Map<Integer, MessageProcessor> messageProcessorMap = new HashMap<>();

    private IdleStateEventProcessor idleStateEventProcessor;

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
        log.debug("读取消息，{}", msg);


    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


        if (!DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.existConnection(ctx)) {
            //连接不存在
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

                    break;
                case WRITER_IDLE:

                    break;
                case ALL_IDLE:
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
