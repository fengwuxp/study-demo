package com.netty.example.server.push;


import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.netty.example.server.proto.TaskMessageOuterClass;
import com.netty.example.server.session.DefaultConnectionSessionManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 任务消息推送者
 */
@Slf4j
public class ExampleTaskMessagePusher implements MessagePusher<String> {


    @Override
    public boolean push(String message) {
        TaskMessageOuterClass.TaskMessage.Builder builder = null;
        try {
            builder = TaskMessageOuterClass.TaskMessage.newBuilder();
            JsonFormat.parser().merge(message, builder);
        } catch (InvalidProtocolBufferException e) {
            log.error("任务消息解析失败", e);
        }
        if (builder == null) {
            return false;
        }

        TaskMessageOuterClass.TaskMessage taskMessage = builder.build();

        String identifier = taskMessage.getTarget();

        if (StringUtils.hasText(identifier)) {
            if (log.isDebugEnabled()) {
                log.debug("任务推送目标未设置");
            }
            return false;
        }

        ChannelHandlerContext connection = DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.getConnection(identifier);

        if (connection == null) {
            if (log.isDebugEnabled()) {
                log.debug("任务推送目标设备{}，未连接", identifier);
            }
            return false;
        }

        //TODO 判断连接的有效性

        //推送任务消息
        connection.write(taskMessage);
        connection.flush();

        return true;

    }
}
