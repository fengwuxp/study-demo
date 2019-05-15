package com.netty.example.server.helper;

import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.session.DefaultConnectionSessionManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SendMessageHelper {


    public static void sendMessage(SignallingMessage.WrapperMessage wrapperMessage,
                                   ChannelHandlerContext channelHandlerContext) {

        if (wrapperMessage == null) {
            return;
        }

        if (channelHandlerContext == null) {
            if (log.isDebugEnabled()) {
                log.debug("任务推送目标设备{}，未连接",
                        DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.getSessionIdentifier(channelHandlerContext));
            }
            return;
        }


        boolean isCanSendMessage = !channelHandlerContext.isRemoved() || !channelHandlerContext.channel().isActive();
        if (!isCanSendMessage) {
            log.warn("连接已失效");
            channelHandlerContext.close();
            DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.remove(channelHandlerContext);
            return;
        }

        channelHandlerContext.write(wrapperMessage);
        channelHandlerContext.flush();
    }

}
