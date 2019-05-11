package test.com.netty.example.client;


import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;
import com.netty.example.server.proto.SignallingMessage;
import com.netty.example.server.proto.TaskMessageOuterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MockEquipmentTerminalTest {


    @Test
    public void testTerminal() throws Exception {

        List<MockEquipmentTerminal> list = new ArrayList<>();

        String host = "127.0.0.1";
        int port = 19999;

        for (int i = 0; i < 100; i++) {
            MockEquipmentTerminal mockEquipmentTerminal = new MockEquipmentTerminal(host, port, i + "");
            list.add(mockEquipmentTerminal);

        }

        for (MockEquipmentTerminal terminal : list) {
            Thread.sleep(Math.round(5) * 1000);
            new Thread(terminal).start();
        }


        Thread.sleep(100 * 1000);
    }

    @Test
    public void testFo() throws Exception {
        SignallingMessage.WrapperMessage commonMessage = SignallingMessage.WrapperMessage
                .newBuilder()
                .setId(1)
                .setNeedAck(false)
                .setExpireTimes(System.currentTimeMillis())
                .setPayload(ByteString.copyFrom("123".getBytes("UTf-8")))
                .setIsEncryption(false)
                .setPriority(1)
                .setType(SignallingMessage.MessageType.SIGNALLING)
                .build();

        SignallingMessage.WrapperMessage.Builder builder = SignallingMessage.WrapperMessage.newBuilder();
        JsonFormat.parser().merge(
                JsonFormat.printer().print(commonMessage),
                builder);
        SignallingMessage.WrapperMessage build = builder.build();

        System.out.println(build);
    }

}
