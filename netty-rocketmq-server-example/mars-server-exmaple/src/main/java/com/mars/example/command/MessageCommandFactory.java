package com.mars.example.command;


/**
 * 消息指令的工厂
 * @param <T>
 */
public interface MessageCommandFactory<T extends MessageCommand> {


    T factory(Integer commandId);

}
