package com.netty.example.server.subscription;

import com.netty.example.server.proto.TaskMessageOuterClass;
import com.netty.example.server.session.DefaultConnectionSessionManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;


////@Service
//@RocketMQTransactionListener()
//@Slf4j
//public class TaskMessageLocalTransactionConsumer implements RocketMQLocalTransactionListener {
//
//
//    @Override
//    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//
//
//        TaskMessageOuterClass.TaskMessage payload = (TaskMessageOuterClass.TaskMessage) msg.getPayload();
//
//        String target = payload.getTarget();
//
//
//        ChannelHandlerContext connection = DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.getConnection(target);
//        if (connection == null) {
//            return RocketMQLocalTransactionState.ROLLBACK;
//        }
//
//        //推送任务
////        connection.write()
//
//
//        return RocketMQLocalTransactionState.UNKNOWN;
//    }
//
//    @Override
//    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
//
//        //
//
//        return RocketMQLocalTransactionState.COMMIT;
//    }
//}
