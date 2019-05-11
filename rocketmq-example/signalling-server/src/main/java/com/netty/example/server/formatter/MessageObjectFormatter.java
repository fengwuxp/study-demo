package com.netty.example.server.formatter;

import com.netty.example.server.proto.SignallingMessage;

/**
 * 消息对象解析
 */
public interface MessageObjectFormatter {


    Object parse(SignallingMessage.WrapperMessage wrapperMessage);


    Object print(Object o);
}
