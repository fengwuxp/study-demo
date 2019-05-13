package com.netty.example.server.formatter;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.proto.TaskMessageOuterClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认的消息对象 formatter
 */
@Slf4j
public class DefaultMessageObjectFormatter implements MessageObjectFormatter {


    public static final MessageObjectFormatter MESSAGE_OBJECT_FORMATTER = new DefaultMessageObjectFormatter();


    @Override
    public Object parse(SignallingMessage.WrapperMessage wrapperMessage) {

        com.google.protobuf.GeneratedMessageV3.Builder builder = null;

        SignallingMessage.PayloadType payloadType = wrapperMessage.getHeader().getPayloadType();

        switch (payloadType) {
            case CONNECTION_REQUEST:
                builder = SignallingMessage.ConnectionRequestMessage.newBuilder();
                break;

            case CONNECTION_RESPONSE:
                builder = SignallingMessage.ConnectionResponseMessage.newBuilder();
                break;

            case PING:
                builder = SignallingMessage.PingMessage.newBuilder();
                break;

            case PONG:
                builder = SignallingMessage.PongMessage.newBuilder();
                break;

            case TASK:
                builder = TaskMessageOuterClass.TaskMessage.newBuilder();
                break;

            default:
                if (log.isDebugEnabled()) {
                    log.debug("为处理的消息类型{}", payloadType);
                }
        }

        try {
            JsonFormat.parser()
                    .merge(wrapperMessage.getPayload().toStringUtf8(), builder);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        if (builder == null) {
            return null;
        }

        return builder.build();
    }

}
