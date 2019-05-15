package com.mars.example.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;


/**
 * 识别mars的数据包
 *
 * @see com.mars.example.protocol.MarsMessageWrapper
 */
public class MarsVarint32FrameDecoder extends ByteToMessageDecoder {


    private final static int MAX_MESSAGE_LENGTH = 64 * 1024;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        in.markReaderIndex();
        int preIndex = in.readerIndex();
        int length = readRawVarint32(in);

        if (length > MAX_MESSAGE_LENGTH) {
            ctx.close();
            throw new RuntimeException("message is lag");
        }

        if (preIndex == in.readerIndex()) {
            return;
        }
        if (length < 0) {
            throw new CorruptedFrameException("negative length: " + length);
        }

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
        } else {
            out.add(in.readRetainedSlice(length));
        }
    }

    /**
     * Reads variable length 32bit int from buffer
     *
     * @return decoded int if buffers readerIndex has been forwarded else nonsense value
     */
    private static int readRawVarint32(ByteBuf buffer) {
        if (!buffer.isReadable()) {
            return 0;
        }
        buffer.markReaderIndex();
        byte tmp = buffer.readByte();
        if (tmp >= 0) {
            return tmp;
        } else {
            int result = tmp & 127;
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return 0;
            }
            if ((tmp = buffer.readByte()) >= 0) {
                result |= tmp << 7;
            } else {
                result |= (tmp & 127) << 7;
                if (!buffer.isReadable()) {
                    buffer.resetReaderIndex();
                    return 0;
                }
                if ((tmp = buffer.readByte()) >= 0) {
                    result |= tmp << 14;
                } else {
                    result |= (tmp & 127) << 14;
                    if (!buffer.isReadable()) {
                        buffer.resetReaderIndex();
                        return 0;
                    }
                    if ((tmp = buffer.readByte()) >= 0) {
                        result |= tmp << 21;
                    } else {
                        result |= (tmp & 127) << 21;
                        if (!buffer.isReadable()) {
                            buffer.resetReaderIndex();
                            return 0;
                        }
                        result |= (tmp = buffer.readByte()) << 28;
                        if (tmp < 0) {
                            throw new CorruptedFrameException("malformed varint.");
                        }
                    }
                }
            }
            return result;
        }
    }
}
