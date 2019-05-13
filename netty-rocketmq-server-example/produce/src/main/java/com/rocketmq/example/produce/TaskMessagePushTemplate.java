package com.rocketmq.example.produce;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.MessageExtBatch;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 推送任务消息消息
 */
@Component
@Slf4j
public class TaskMessagePushTemplate {


    private String taskTopic = "task-topic";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public void batchPush() {


        //TODO 实现真正的批量发送
        // @link https://www.jianshu.com/p/cd2244b483be

        log.debug("开始发送任务消息");

        List<GenericMessage> list = new ArrayList<>();

        for (int i = 0; i < 11; i++) {

            TaskMessage taskMessage = TaskMessage.builder().actionType(ActionType.NEW)
                    .target(i + "")
                    .taskIds(new Long[]{1L, 5L, 7L})
                    .build();
            list.add(new GenericMessage(JSON.toJSONString(taskMessage)));

        }
        SendResult sendResult = rocketMQTemplate.syncSend(taskTopic, list);
        if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
            //TODO
        }

        //异步发送
        for (int i = 0; i < 11; i++) {

            TaskMessage taskMessage = TaskMessage.builder().actionType(ActionType.NEW)
                    .target(i + "")
                    .taskIds(new Long[]{1L, 5L, 7L})
                    .build();

            rocketMQTemplate.asyncSend(taskTopic, JSON.toJSONString(taskMessage), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    if (log.isDebugEnabled()) {
                        log.debug("异步发送消息，发送成功，id：{}", sendResult.getMsgId());
                    }
                }

                @Override
                public void onException(Throwable e) {
                    log.error("异步发送消息，发送失败", e);
                }
            });
        }


    }


    @Data
    @Builder
    static class TaskMessage {

        private String target;

        private ActionType actionType;

        private Long[] taskIds;
    }

    enum ActionType {

        /**
         * <pre>
         * 新任务
         * </pre>
         *
         * <code>NEW = 1;</code>
         */
        NEW(1),
        /**
         * <pre>
         * 启动任务
         * </pre>
         *
         * <code>START = 2;</code>
         */
        START(2),
        /**
         * <pre>
         * 停止任务
         * </pre>
         *
         * <code>STOP = 3;</code>
         */
        STOP(3),
        /**
         * <pre>
         * 移除任务
         * </pre>
         *
         * <code>REMOVE = 6;</code>
         */
        REMOVE(6),
        /**
         * <pre>
         * 更新任务
         * </pre>
         *
         * <code>UPDATE = 7;</code>
         */
        UPDATE(7);

        private int value;

        ActionType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ActionType{");
            sb.append("value=").append(value);
            sb.append('}');
            return sb.toString();
        }
    }
}
