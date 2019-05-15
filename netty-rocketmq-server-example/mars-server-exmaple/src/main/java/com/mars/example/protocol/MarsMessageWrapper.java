package com.mars.example.protocol;


import com.mars.example.mars.MarsMessageCommand;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于mars的包装消息
 *
 * @link https://github.com/Tencent/mars/wiki/Mars-%E8%87%AA%E5%AE%9A%E4%B9%89%E6%89%A9%E5%B1%95
 */
@Slf4j
@Data
@Builder
public final class MarsMessageWrapper {


    /**
     * 客户端版本
     */
    public static final int CLIENT_VERSION = 1;


    /**
     * 头部长度
     */
    private int headLength;

    /**
     * 客户端版本
     */
    private int clientVersion;

    /**
     * 命令id
     */
    private MarsMessageCommand command;

    /**
     * 默认 seq = 0 会被认为是 push 数据包，可在 longlink_packer.cc 自定义。
     */
    private int seq;

    private byte[] options;

    private byte[] body;

}
