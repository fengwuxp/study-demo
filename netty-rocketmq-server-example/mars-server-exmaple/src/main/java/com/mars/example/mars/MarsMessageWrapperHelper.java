package com.mars.example.mars;

import com.google.protobuf.MessageLite;
import com.mars.example.protocol.MarsMessageWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.MessageFormat;

import static com.mars.example.protocol.MarsMessageWrapper.CLIENT_VERSION;

@Slf4j
public final class MarsMessageWrapperHelper {

    /**
     * 固定跳过的头部长度
     */
    private static final int FIXED_HEADER_SKIP = 4 + 4 + 4 + 4 + 4;


    /**
     * 解析消息
     *
     * @param byteBuf
     * @return
     */
    public static MarsMessageWrapper parse(ByteBuf byteBuf) {

        if (byteBuf == null) {
            return null;
        }

        try {
            final InputStream inputStream = new ByteBufInputStream(byteBuf);
            return decode(inputStream);
        } catch (InvalidMarsHeaderException e) {
            log.error("decode MarsMessageWrapper error", e);
        }
        return null;
    }

    /**
     * 将消息转化为字节数组
     *
     * @param marsMessageWrapper
     * @return
     */
    public static byte[] formatter(MarsMessageWrapper marsMessageWrapper) {

        try {
            return encode(marsMessageWrapper);
        } catch (InvalidMarsHeaderException e) {
            log.error("encode MarsMessageWrapper error", e);
        }
        return null;
    }


    public static MarsMessageWrapper buildMessage(int commandId, MessageLite messageLite) {
        MarsMessageCommand command = MarsMessageCommandFactory.MESSAGE_COMMAND_FACTORY.factory(commandId);

        if (command == null) {
            throw new RuntimeException("");
        }

        byte[] body = null;
        int length = 0;

        if (messageLite != null) {
            body = messageLite.toByteArray();
        }

        return MarsMessageWrapper.builder()
                .command(command)
                .headLength(length + FIXED_HEADER_SKIP)
                .clientVersion(CLIENT_VERSION)
                .body(body)
                .build();

    }


    /**
     * Decode MarsMessageWrapper from InputStream
     *
     * @param inputStream close input stream yourself
     * @return 编码是否成功
     * @throws InvalidMarsHeaderException
     */
    private static MarsMessageWrapper decode(final InputStream inputStream) throws InvalidMarsHeaderException {
        final DataInputStream dis = new DataInputStream(inputStream);


        try {
            int headLength = dis.readInt();
            int clientVersion = dis.readInt();
            int cmdId = dis.readInt();
            int seq = dis.readInt();
            int bodyLen = dis.readInt();
            byte[] body = null;

            if (clientVersion != CLIENT_VERSION) {
                throw new InvalidMarsHeaderException(MessageFormat.format("invalid client version in header, clientVersion: {0} packageLength: {1}", clientVersion, headLength + bodyLen));
            }

            if (log.isDebugEnabled()) {
                log.debug("dump clientVersion={}, cmdId={}, seq={},packageLength={}",
                        clientVersion, cmdId,
                        seq, (headLength + bodyLen));
            }

            MarsMessageCommand command = MarsMessageCommandFactory.MESSAGE_COMMAND_FACTORY.factory(cmdId);

            // read body?
            if (bodyLen > 0) {
                body = new byte[bodyLen];
                dis.readFully(body);
            } else {
                // no body?!
                if (command.needMessageBody()) {
                    throw new InvalidMarsHeaderException(MessageFormat.format("invalid header body, cmdId:{0}", cmdId));
                }
            }

            return MarsMessageWrapper.builder()
                    .headLength(headLength)
                    .clientVersion(clientVersion)
                    .command(command)
                    .seq(seq)
                    .body(body)
                    .build();

        } catch (IOException e) {
            log.error("decode mars message exception", e);
        }


        return null;
    }

    /**
     * 编码
     *
     * @param marsMessageWrapper
     * @return
     * @throws InvalidMarsHeaderException
     */
    private static byte[] encode(MarsMessageWrapper marsMessageWrapper) throws InvalidMarsHeaderException {
        if (marsMessageWrapper == null) {
            return null;
        }

        byte[] body = marsMessageWrapper.getBody();
        MarsMessageCommand marsCommand = marsMessageWrapper.getCommand();

        if (!marsCommand.needMessageBody()) {
            throw new InvalidMarsHeaderException("invalid header body");
        }

        byte[] options = marsMessageWrapper.getOptions();


        final int headerLength = FIXED_HEADER_SKIP + (options == null ? 0 : options.length);
        final int bodyLength = (body == null ? 0 : body.length);
        final int packLength = headerLength + bodyLength;
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(packLength);

        try {
            final DataOutputStream dos = new DataOutputStream(byteArrayOutputStream);

            dos.writeInt(headerLength);
            dos.writeInt(CLIENT_VERSION);
            dos.writeInt(marsCommand.getCommandId());
            dos.writeInt(marsMessageWrapper.getSeq());
            dos.writeInt(bodyLength);

            if (options != null) {
                dos.write(options);
            }

            if (body != null) {
                dos.write(body);
            }
        } catch (IOException e) {
            log.error("encode mars message exception", e);
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                log.error("encode mars message exception", e);
            }
        }

        return byteArrayOutputStream.toByteArray();
    }


    /**
     * 无效的头部异常
     */
    public static class InvalidMarsHeaderException extends Exception {

        InvalidMarsHeaderException(String message) {
            super(message);
        }
    }


}
