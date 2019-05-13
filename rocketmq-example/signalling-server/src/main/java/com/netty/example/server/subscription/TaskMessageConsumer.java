package com.netty.example.server.subscription;

import com.netty.example.server.push.ExampleTaskMessagePusher;
import com.netty.example.server.push.MessagePusher;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;


/**
 * 这是一个消费者的示例
 */
@Service
@Slf4j
@RocketMQMessageListener(topic = "${signalling.rocketmq.task-topic}", consumerGroup = "task-consumer")
public class TaskMessageConsumer implements RocketMQListener<String> {


    private MessagePusher<String> taskMessagePusher = new ExampleTaskMessagePusher();

    @Override
    public void onMessage(String message) {

        if (log.isDebugEnabled()) {
            log.debug("收到一个任务消息，{}", message);
        }

        //发送任务消息
        boolean b = this.taskMessagePusher.push(message);
        if (!b) {

            throw new RuntimeException("任务消息推送失败");
        }

    }
}
