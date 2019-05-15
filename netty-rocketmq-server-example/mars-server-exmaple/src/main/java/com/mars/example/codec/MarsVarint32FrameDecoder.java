package com.mars.example.codec;

import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;


/**
 * 识别mars的数据包
 *
 * @see com.mars.example.protocol.MarsMessageWrapper
 */
public class MarsVarint32FrameDecoder extends ProtobufVarint32FrameDecoder {
}
