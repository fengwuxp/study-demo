package test.com.netty.example.client.processor;

import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MockEquipmentPongMessageProcessor implements MessageProcessor {

    @Override
    public void process(SignallingMessage.WrapperMessage wrapperMessage, ChannelHandlerContext channelHandlerContext) {

        SignallingMessage.PongMessage pongMessage = this.parseMessage(wrapperMessage);

        if (log.isDebugEnabled()) {
            log.debug("接收到服务端的pong消息，{}", pongMessage.getPong());
        }
    }
}
