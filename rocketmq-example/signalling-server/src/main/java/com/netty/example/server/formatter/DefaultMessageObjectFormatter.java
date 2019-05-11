package com.netty.example.server.formatter;

import com.netty.example.server.proto.SignallingMessage;

public class DefaultMessageObjectFormatter implements MessageObjectFormatter {

    @Override
    public Object parse(SignallingMessage.WrapperMessage wrapperMessage) {
        return null;
    }

    @Override
    public Object print(Object o) {
        return null;
    }
}
