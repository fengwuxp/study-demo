package com.netty.example.server.config;


import com.netty.example.server.heartbeat.HeartbeatMessageProcessor;
import com.netty.example.server.processor.ConnectionMessageProcessor;
import com.netty.example.server.processor.MessageProcessor;
import com.netty.example.server.proto.SignallingMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SignallingConfig {


    @Bean
    public Map<SignallingMessage.PayloadType, MessageProcessor> messageProcessorMap() {
        Map<SignallingMessage.PayloadType, MessageProcessor> messageProcessorMap = new HashMap<>();
        messageProcessorMap.put(SignallingMessage.PayloadType.CONNECTION_REQUEST, new ConnectionMessageProcessor());
        messageProcessorMap.put(SignallingMessage.PayloadType.PING, new HeartbeatMessageProcessor());
        return messageProcessorMap;
    }

}
