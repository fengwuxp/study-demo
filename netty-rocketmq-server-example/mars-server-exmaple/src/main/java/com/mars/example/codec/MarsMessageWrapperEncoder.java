package com.mars.example.codec;

import com.mars.example.mars.MarsMessageWrapperHelper;
import com.mars.example.protocol.MarsMessageWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * mars 消息编码器
 */
@Slf4j
public class MarsMessageWrapperEncoder extends MessageToMessageEncoder<MarsMessageWrapper> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MarsMessageWrapper msg, List<Object> out) throws Exception {
        byte[] bytes = MarsMessageWrapperHelper.formatter(msg);
        if (bytes == null) {
            if (log.isDebugEnabled()) {
                log.debug("消息编码失败，{}", msg);
            }
            return;
        }
        out.add(wrappedBuffer(bytes));
    }
}
