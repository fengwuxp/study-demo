package com.netty.example.server.heartbeat;

import com.netty.example.server.session.DefaultConnectionSessionManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;



@Component
@Slf4j
public class DefaultIdleStateEventProcessor implements IdleStateEventProcessor {

    @Override
    public void readIdleProcess(ChannelHandlerContext channelHandlerContext) {

        String sessionIdentifier = DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.getSessionIdentifier(channelHandlerContext);
        if (log.isDebugEnabled()) {
            log.debug("会话{}，read idle", sessionIdentifier);
        }

        if (StringUtils.hasText(sessionIdentifier)) {
            DefaultConnectionSessionManager.CONNECTION_SESSION_MANAGER.remove(sessionIdentifier);
        }

        //TODO

        channelHandlerContext.close();

    }

    @Override
    public void writeIdleProcess(ChannelHandlerContext channelHandlerContext) {
        this.readIdleProcess(channelHandlerContext);
    }

    @Override
    public void allIdleProcess(ChannelHandlerContext channelHandlerContext) {
        this.readIdleProcess(channelHandlerContext);
    }
}
