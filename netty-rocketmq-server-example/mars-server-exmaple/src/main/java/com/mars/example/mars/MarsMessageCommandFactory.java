package com.mars.example.mars;


import com.mars.example.command.MessageCommandFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.mars.example.mars.MarsCommandConstant.*;

/**
 * 基于mars消息指令工厂
 */
@Slf4j
public class MarsMessageCommandFactory implements MessageCommandFactory<MarsMessageCommand> {

    /**
     * 心跳包包命令
     */
    private static final MarsMessageCommand MARS_CMD_PING_OR_PONG = new MarsMessageCommand(MARS_CMD_PING_VALUE);

    /**
     * 信令 signalling
     */
    private static final MarsMessageCommand MARS_CMD_SIGNALLING = new MarsMessageCommand(MARS_CMD_SIGNALLING_VALUE);


    private static final Map<Integer, Integer> MARS_DTO_TYPE_MAP = new HashMap<>();


    public static final MessageCommandFactory<MarsMessageCommand> MESSAGE_COMMAND_FACTORY = new MarsMessageCommandFactory();

    static {
        //初始类型映射
        MARS_DTO_TYPE_MAP.put(AUTH_CMD_VALUE, AUTH_DATA_TYPE_VALUE);
        MARS_DTO_TYPE_MAP.put(TASK_CMD_VALUE, TASK_DATA_TYPE_VALUE);
    }


    @Override
    public MarsMessageCommand factory(Integer commandId) {

        if (commandId == null) {
            return null;
        }

        switch (commandId) {
            case MARS_CMD_PING_VALUE:
                return MARS_CMD_PING_OR_PONG;
            case MARS_CMD_SIGNALLING_VALUE:
                return MARS_CMD_SIGNALLING;
            default:
                Integer dtoType = MARS_DTO_TYPE_MAP.get(commandId);
                if (dtoType == null) {
                    log.error("not support commandId {}", commandId);
                    return null;
                }
                return new MarsMessageCommand(commandId, dtoType);
        }

    }
}
