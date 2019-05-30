package com.mars.example.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 识别mars的数据包
 *
 * @see com.mars.example.protocol.MarsMessageWrapper
 */
@Slf4j
public class MarsVarint32FrameDecoder extends ByteToMessageDecoder {


    private final static int MAX_MESSAGE_LENGTH = 64 * 1024;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        in.markReaderIndex();

        int headerLength = in.readInt();
        int clientVersion = in.readInt();
        int cmdId = in.readInt();
        int seq = in.readInt();
        int bodyLength = in.readInt();

        if (log.isDebugEnabled()) {
            log.debug("接收的包：headerLength={},clientVersion={},cmdId={},seq={},bodyLength={}",
                    headerLength, clientVersion, cmdId, seq, bodyLength);
        }

        int length = headerLength + bodyLength;

        if (length > MAX_MESSAGE_LENGTH) {
            ctx.close();
            throw new RuntimeException("message is too big");
        }

        if (length < 0) {
            throw new CorruptedFrameException("negative length: " + length);
        }

        if (log.isDebugEnabled()) {
            log.debug("接收的包长度为：{}", length);
        }

        in.resetReaderIndex();
        out.add(in.readRetainedSlice(length));

    }


}
