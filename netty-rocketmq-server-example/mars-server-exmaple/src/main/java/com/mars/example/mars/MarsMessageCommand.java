package com.mars.example.mars;

import com.mars.example.command.MessageCommand;
import lombok.Data;

/**
 * mars 的消息指令
 */
@Data
public class MarsMessageCommand implements MessageCommand {


    /**
     * 命令id
     */
    private final int commandId;


    /**
     * 命令对应的传输消息的数据类型，
     * -1 标识没有对应的消息类型
     */
    private final int dtoType;


    public MarsMessageCommand(int commandId, int dtoType) {
        this.commandId = commandId;
        this.dtoType = dtoType;
    }

    public MarsMessageCommand(int commandId) {
        this.commandId = commandId;
        this.dtoType = NONE_MESSAGE_BODY_TYPE;
    }


    @Override
    public boolean needMessageBody() {

        return dtoType == NONE_MESSAGE_BODY_TYPE;
    }
}
