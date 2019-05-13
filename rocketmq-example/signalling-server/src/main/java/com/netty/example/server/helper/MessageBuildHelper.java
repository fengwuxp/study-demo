package com.netty.example.server.helper;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import com.netty.example.server.formatter.DefaultMessageObjectFormatter;
import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.proto.TaskMessageOuterClass;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;

import java.io.UnsupportedEncodingException;

public final class MessageBuildHelper {


    private static final IdGenerator ID_GENERATOR = new AlternativeJdkIdGenerator();


    private static final String DEFAULT_REFERER = "signalling server";

    MessageBuildHelper() {
    }


    public static SignallingMessage.WrapperMessage getConnectionRequestMessage(SignallingMessage.ConnectionRequestMessage connectionRequestMessage) {

        return getDataWrapperMessage(connectionRequestMessage, SignallingMessage.PayloadType.CONNECTION_REQUEST);
    }

    public static SignallingMessage.WrapperMessage getConnectionResponseMessage(SignallingMessage.ConnectionResponseMessage  connectionResponseMessage) {
        return getDataWrapperMessage(connectionResponseMessage, SignallingMessage.PayloadType.CONNECTION_RESPONSE);
    }


    public static SignallingMessage.WrapperMessage getPingMessage(SignallingMessage.PingMessage pingMessage) {
        return getSignallingWrapperMessage(pingMessage, SignallingMessage.PayloadType.PING);
    }

    public static SignallingMessage.WrapperMessage getPongMessage(SignallingMessage.PongMessage pongMessage) {
        return getSignallingWrapperMessage(pongMessage, SignallingMessage.PayloadType.PONG);
    }


    public static SignallingMessage.WrapperMessage getPushTaskMessage(TaskMessageOuterClass.TaskMessage taskMessage) {
        return getSignallingWrapperMessage(taskMessage, SignallingMessage.PayloadType.PONG);
    }



    public static SignallingMessage.WrapperMessage getDataWrapperMessage(MessageOrBuilder messageOrBuilder, SignallingMessage.PayloadType payloadType) {
        ByteString payload = DefaultMessageObjectFormatter.MESSAGE_OBJECT_FORMATTER.print(messageOrBuilder);
        if (payload == null) return null;

        return SignallingMessage.WrapperMessage
                .newBuilder()
                .setHeader(getMessageHeader(SignallingMessage.MessageType.DATA, payloadType))
                .setPayload(payload)
                .build();
    }


    public static SignallingMessage.WrapperMessage getSignallingWrapperMessage(MessageOrBuilder messageOrBuilder, SignallingMessage.PayloadType payloadType) {
        ByteString payload = DefaultMessageObjectFormatter.MESSAGE_OBJECT_FORMATTER.print(messageOrBuilder);
        if (payload == null) return null;


        return SignallingMessage.WrapperMessage
                .newBuilder()
                .setHeader(getMessageHeader(SignallingMessage.MessageType.SIGNALLING, payloadType))
                .setPayload(payload)
                .build();
    }


    public static SignallingMessage.MessageHeader getMessageHeader(SignallingMessage.MessageType messageType,
                                                                   SignallingMessage.PayloadType payloadType) {

        return SignallingMessage.MessageHeader
                .newBuilder()
                .setId(ID_GENERATOR.generateId().toString())
                .setExpireTimes(-1L)
                .setIsEncryption(false)
                .setNeedAck(false)
                .setPriority(0)
                .setType(messageType)
                .setReferer(DEFAULT_REFERER)
                .setPayloadType(payloadType)
                .build();

    }


}
