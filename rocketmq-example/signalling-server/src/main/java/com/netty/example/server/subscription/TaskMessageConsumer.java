package com.netty.example.server.subscription;

import com.netty.example.server.proto.TaskMessageOuterClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

@Slf4j
@RocketMQMessageListener(topic = "${signalling.rocketmq.task-topic}", consumerGroup = "task-consumer")
public class TaskMessageConsumer implements RocketMQListener<TaskMessageOuterClass.TaskMessage> {


    @Override
    public void onMessage(TaskMessageOuterClass.TaskMessage message) {


    }
}
