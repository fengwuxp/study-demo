package test.com.netty.example.client.handle;

import com.google.protobuf.MessageLite;
import com.netty.example.server.heartbeat.DefaultIdleStateEventProcessor;
import com.netty.example.server.heartbeat.IdleStateEventProcessor;
import com.netty.example.server.helper.MessageBuildHelper;
import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import test.com.netty.example.client.processor.MockEquipmentConnectionMessageProcessor;
import test.com.netty.example.client.processor.MockEquipmentPongMessageProcessor;
import test.com.netty.example.client.processor.MockEquipmentTaskMessageProcessor;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class MockTerminalHandler extends ChannelInboundHandlerAdapter {


    /**
     * 设备号
     */
    private String deviceCode;

    private ChannelHandlerContext channelHandlerContext;


    private final static Map<SignallingMessage.PayloadType, MessageProcessor> MESSAGE_PROCESSOR_MAP = new HashMap<>();

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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


    public void sendMessageConnectionMessage() {

        SignallingMessage.WrapperMessage connectionRequestMessage = MessageBuildHelper.getConnectionRequestMessage(SignallingMessage.ConnectionRequestMessage
                .newBuilder()
                .setSessionIdentifier(this.deviceCode)
                .build());

        this.sendMessage(connectionRequestMessage);
    }


    public void sendPingMessage() {
        SignallingMessage.WrapperMessage pingMessage = MessageBuildHelper.getPingMessage(SignallingMessage.PingMessage
                .newBuilder()
                .setPing(1)
                .build());

        log.debug("设备{}，发送心跳信息", this.deviceCode);
        this.sendMessage(pingMessage);
    }


    private void sendMessage(MessageLite messageLite) {
        if (this.channelHandlerContext == null) {
            return;
        }
        this.channelHandlerContext.write(messageLite);
        this.channelHandlerContext.flush();
    }


}
