package test.com.netty.example.client.handle;

import com.netty.example.server.heartbeat.DefaultIdleStateEventProcessor;
import com.netty.example.server.heartbeat.IdleStateEventProcessor;
import com.netty.example.server.helper.MessageBuildHelper;
import com.netty.example.server.helper.SendMessageHelper;
import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.session.DefaultConnectionSessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import test.com.netty.example.client.processor.MockEquipmentConnectionMessageProcessor;
import test.com.netty.example.client.processor.MockEquipmentPongMessageProcessor;
import test.com.netty.example.client.processor.MockEquipmentTaskMessageProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
public class MockTerminalHandler extends ChannelInboundHandlerAdapter {


    /**
     * 设备号
     */
    private String deviceCode;

    private ChannelHandlerContext channelHandlerContext;


    private final static Map<SignallingMessage.PayloadType, MessageProcessor<SignallingMessage.WrapperMessage>> MESSAGE_PROCESSOR_MAP = new HashMap<>();

    private IdleStateEventProcessor idleStateEventProcessor = new DefaultIdleStateEventProcessor();

    static {
        MESSAGE_PROCESSOR_MAP.put(SignallingMessage.PayloadType.CONNECTION_RESPONSE, new MockEquipmentConnectionMessageProcessor());
        MESSAGE_PROCESSOR_MAP.put(SignallingMessage.PayloadType.PONG, new MockEquipmentPongMessageProcessor());
        MESSAGE_PROCESSOR_MAP.put(SignallingMessage.PayloadType.TASK, new MockEquipmentTaskMessageProcessor());
    }


    public MockTerminalHandler(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.channelHandlerContext = ctx;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SignallingMessage.WrapperMessage requestMessage = (SignallingMessage.WrapperMessage) msg;
        if (log.isDebugEnabled()) {
            log.debug("读取消息，{}", msg);
        }

        SignallingMessage.PayloadType payloadType = requestMessage.getHeader().getPayloadType();
        MessageProcessor<SignallingMessage.WrapperMessage> messageProcessor = MESSAGE_PROCESSOR_MAP.get(payloadType);
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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("设备{}，连接发生异常，准备关闭连接", this.deviceCode);
        }
        ctx.close();

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        super.channelInactive(ctx);


        if (log.isDebugEnabled()) {
            log.debug("设备{}，连接channelInactive", this.deviceCode);
        }

        //使用过程中断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> {
            if (log.isDebugEnabled()) {
                log.debug("设备{}，尝试重连", this.deviceCode);
            }
            //使用过程

        }, 2L, TimeUnit.MICROSECONDS);

        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("设备{}，连接channelUnregistered，尝试重新连接", this.deviceCode);
        }

        //TODO 区分首次连接和重连
        ChannelHandlerContext channelHandlerContext = ctx.fireChannelRegistered();
        //连接
         this.sendMessageConnectionMessage();
    }




    public void sendMessageConnectionMessage() {

        SignallingMessage.WrapperMessage connectionRequestMessage = MessageBuildHelper.getConnectionRequestMessage(SignallingMessage.ConnectionRequestMessage
                .newBuilder()
                .setSessionIdentifier(this.deviceCode)
                .build());

        SendMessageHelper.sendMessage(connectionRequestMessage, channelHandlerContext);
    }


    public void sendPingMessage() {
        SignallingMessage.WrapperMessage pingMessage = MessageBuildHelper.getPingMessage(SignallingMessage.PingMessage
                .newBuilder()
                .setPing(1)
                .build());

        log.debug("设备{}，发送心跳信息", this.deviceCode);
        SendMessageHelper.sendMessage(pingMessage, channelHandlerContext);
    }


}
