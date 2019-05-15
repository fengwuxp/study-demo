package com.mars.example.mars;

/**
 * mars 命令列表页
 * <p>
 * MARS_CMD_ 开头的是mars固定的命令
 */
public final class MarsCommandConstant {


    /*------------------固定命令---------------------*/

    /**
     * 心跳包 cmdid = 6 可在 longlink_packer.cc 自定义
     */
    public static final int MARS_CMD_HEARTBEAT_VALUE = 6;

//    public static final int MARS_CMD_PONG_VALUE = 6;

    /**
     * 信令 signalling cmdid = 243, cmdid 的值可在 longlink_packer.cc 自定义
     */
    public static final int MARS_CMD_SIGNALLING_VALUE = 243;



    /*-------------------连接鉴权命令---------------*/

    public static final int AUTH_CMD_VALUE = 400;

    public static final int AUTH_DATA_TYPE_VALUE = AUTH_CMD_VALUE;

    /*-------------------任务命令---------------*/

    public static final int TASK_CMD_VALUE = 10000;

    public static final int TASK_DATA_TYPE_VALUE = TASK_CMD_VALUE;


}
