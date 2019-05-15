package com.mars.example.command;

/**
 * 消息指令
 */
public interface MessageCommand {

    /**
     * 没有消息同的命令消息
     */
    int NONE_MESSAGE_BODY_TYPE = -1;

    /**
     * 该命令是否需要消息体
     *
     * @return
     */
    boolean needMessageBody();

}
