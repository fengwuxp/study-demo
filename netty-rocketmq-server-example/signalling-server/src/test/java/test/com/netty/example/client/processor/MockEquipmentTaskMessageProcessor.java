package test.com.netty.example.client.processor;

import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.proto.TaskMessageOuterClass;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockEquipmentTaskMessageProcessor implements MessageProcessor {


    @Override
    public void process(SignallingMessage.WrapperMessage wrapperMessage, ChannelHandlerContext channelHandlerContext) {
        TaskMessageOuterClass.TaskMessage taskMessage = this.parseMessage(wrapperMessage);

        if (taskMessage == null) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("接收到服务端的任务消息，任务目标{}，任务内容：{}", taskMessage.getTarget(), taskMessage.getTaskIdsList());
        }
    }


}
