package com.rocketmq.example;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DefaultMessageTransmitter implements MessageTransmitter<String> {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public boolean transmit(String data) {

        Message<String> message = new GenericMessage(data);

        rocketMQTemplate.send(this.genDestination(), message);

        return false;
    }

    @Override
    public boolean transmit(Collection<String> data) {

        List<Message<String>> messages = data.stream().map(GenericMessage::new).collect(Collectors.toList());


        rocketMQTemplate.syncSend(this.genDestination(), messages, 30 * 1000);

        return false;
    }


    /**
     * 根据 长连接的路由表生成
     *
     * @return
     */
    protected String genDestination() {

        //TODO topicName:tags
        return "task-topic:hostname";
    }
}
