package com.netty.example.server.formatter;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;

import java.io.UnsupportedEncodingException;

/**
 * 消息对象解析
 */
public interface MessageObjectFormatter<T> {


    Object parse(T wrapperMessage);


    default ByteString print(MessageOrBuilder builder) {
        String print = null;
        ByteString payload = null;
        try {
            print = JsonFormat.printer()
                    .print(builder);
            payload = ByteString.copyFrom(print, "UTF-8");
        } catch (InvalidProtocolBufferException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return payload;
    }

}
