package test.com.netty.example.client;


import com.netty.example.server.proto.SignallingMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;
import test.com.netty.example.client.handle.MockTerminalHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 模拟设备终端
 */
@Slf4j
public class MockEquipmentTerminal implements Runnable {


    /**
     * 设备编号
     */
    private String deviceCode;

    private String host;

    private int port;

    private EventLoopGroup workerGroup;

    private Bootstrap bootstrap;

    private MockTerminalHandler mockTerminalHandler;

    private HeartbeatTimer heartbeatTimer;

    public MockEquipmentTerminal(String host, int port, String deviceCode) {
        this.deviceCode = deviceCode;
        this.host = host;
        this.port = port;
        this.workerGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.mockTerminalHandler = new MockTerminalHandler(deviceCode);
        this.heartbeatTimer = new HeartbeatTimer();
    }

    @Override
    public void run() {
        this.connection();
    }

    protected void connection() {

        log.debug("connection server {}:{}", this.host, this.port);

        //是一个启动NIO服务的辅助启动类
        this.bootstrap.group(this.workerGroup)  //绑定1个工作线程组
                .channel(NioSocketChannel.class)  //设置NIO模式
                //初始化绑定服务通道
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        //为通道进行初始化，数据传输过拉里的时候会进行拦截和执行

                        //设置数据识别
                        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());

                        //设置编解码
                        channel.pipeline().addLast(new ProtobufDecoder(SignallingMessage.WrapperMessage.getDefaultInstance()));
                        channel.pipeline().addLast(new ProtobufEncoder());

                        channel.pipeline().addLast(mockTerminalHandler);
                    }
                });


        ChannelFuture channelFuture = bootstrap.connect(this.host, this.port).syncUninterruptibly();

        log.debug("equipment connection successful ,code {}", this.deviceCode);

        this.sendMessageConnectionMessage();

        //心跳应该在设备连接成功后进行处理，这里只是一个示例
        Timer timer = new Timer();
        timer.schedule(this.heartbeatTimer, 60 * 1000, 180 * 1000);

        //释放连接
        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.workerGroup.shutdownGracefully();
        }

    }

    public void sendMessageConnectionMessage() {
        this.mockTerminalHandler.sendMessageConnectionMessage();
    }


    public class HeartbeatTimer extends TimerTask {

        @Override
        public void run() {
            mockTerminalHandler.sendPingMessage();
        }
    }
}
