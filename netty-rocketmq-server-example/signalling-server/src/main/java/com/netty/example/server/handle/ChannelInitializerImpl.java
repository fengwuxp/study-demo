package com.netty.example.server.handle;

import com.netty.example.server.proto.SignallingMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//channelInitializer
@Slf4j
@Component()
public class ChannelInitializerImpl<T extends Channel> extends ChannelInitializer<T> {


    @Override
    protected void initChannel(T channel) throws Exception {

        //为通道进行初始化，数据传输过来的时候会进行拦截和执行

        //设置数据识别
        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());

        //设置编解码
        channel.pipeline().addLast(new ProtobufDecoder(SignallingMessage.WrapperMessage.getDefaultInstance()));
        channel.pipeline().addLast(new ProtobufEncoder());

        channel.pipeline().addLast(new IdleStateHandler(120, 120, 180));
        channel.pipeline().addLast(this.getSignallingServerHandler());
    }


    @Bean
    public SignallingServerHandler getSignallingServerHandler() {
        return new SignallingServerHandler();
    }


}
