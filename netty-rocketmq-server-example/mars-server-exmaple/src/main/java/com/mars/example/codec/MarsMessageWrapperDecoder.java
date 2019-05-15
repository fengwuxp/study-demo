package com.mars.example.codec;

import com.mars.example.mars.MarsMessageWrapperHelper;
import com.mars.example.protocol.MarsMessageWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * mars的消息解码器
 */
@Slf4j
public class MarsMessageWrapperDecoder extends MessageToMessageDecoder<ByteBuf> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

        MarsMessageWrapper marsMessageWrapper = MarsMessageWrapperHelper.parse(msg);
        if (marsMessageWrapper == null) {
            if (log.isDebugEnabled()) {
                log.debug("mars decoder 未解析到消息，message length ={}", msg.getInt(0));
            }
            return;
        }
        out.add(marsMessageWrapper);

    }
}
