package com.netty.example.server.subscription;

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
