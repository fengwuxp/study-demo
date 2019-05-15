package test.com.netty.example.client.processor;

import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


/**
 * 连接消息处理
 */
@Slf4j
public class MockEquipmentConnectionMessageProcessor implements MessageProcessor<SignallingMessage.WrapperMessage> {


    @Override
    public void process(SignallingMessage.WrapperMessage wrapperMessage, ChannelHandlerContext channelHandlerContext) {

        SignallingMessage.ConnectionResponseMessage requestMessage = this.parseMessage(wrapperMessage);

        if (requestMessage == null) {
            return;
        }

        log.debug("设备连接成功，开始处理业务...");


    }


}
