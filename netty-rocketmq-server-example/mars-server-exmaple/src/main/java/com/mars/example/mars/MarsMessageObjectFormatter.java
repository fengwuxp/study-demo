package com.mars.example.mars;

import com.google.protobuf.InvalidProtocolBufferException;
import com.mars.example.protocol.MarsMessageWrapper;
import com.netty.example.server.formatter.MessageObjectFormatter;
import com.netty.example.server.proto.SignallingMessage;
import lombok.extern.slf4j.Slf4j;

import static com.mars.example.mars.MarsCommandConstant.AUTH_CMD_VALUE;
import static com.mars.example.mars.MarsCommandConstant.TASK_CMD_VALUE;

@Slf4j
public class MarsMessageObjectFormatter implements MessageObjectFormatter<MarsMessageWrapper> {

    @Override
    public Object parse(MarsMessageWrapper wrapperMessage) {

        int commandId = wrapperMessage.getCommand().getCommandId();
        byte[] body = wrapperMessage.getBody();


        switch (commandId) {
            case AUTH_CMD_VALUE:

                try {
                    return SignallingMessage.ConnectionRequestMessage.parseFrom(body);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }

            case TASK_CMD_VALUE:


                break;
        }

        return null;
    }
}
