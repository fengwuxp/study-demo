package com.netty.example.server;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;


@Data
public class ClientChannelContextHolder {

    private ChannelHandlerContext ctx;

    private String deviceCode;


    public ClientChannelContextHolder(ChannelHandlerContext ctx, String deviceCode) {
        this.ctx = ctx;
        this.deviceCode = deviceCode;
    }
}
