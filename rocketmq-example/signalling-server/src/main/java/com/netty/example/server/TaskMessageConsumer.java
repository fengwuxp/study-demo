package com.netty.example.server;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "${example.rocketmq.trans-topic}", consumerGroup = "string_trans_consumer")
public class TaskMessageConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {

    }
}


