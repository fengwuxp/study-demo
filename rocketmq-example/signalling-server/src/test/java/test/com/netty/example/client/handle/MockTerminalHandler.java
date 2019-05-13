package test.com.netty.example.client.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class MockTerminalHandler extends ChannelInboundHandlerAdapter {


    /**
     * 设备号
     */
    private String deviceCode;

    private ChannelHandlerContext channelHandlerContext;

    public MockTerminalHandler(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.channelHandlerContext = ctx;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


    public void sendMessage(String message) {
        if (this.channelHandlerContext == null) {
            return;
        }

//        this.channelHandlerContext.write(this.buildMessage(message));
        this.channelHandlerContext.flush();
    }


//    private SignllingMessage.TaskPushMessage buildMessage(String message) {
//
//        return SignllingMessage.TaskPushMessage
//                .newBuilder()
//                .setContent(message)
//                .setDeviceCode(this.deviceCode)
//                .build();
//    }
}
