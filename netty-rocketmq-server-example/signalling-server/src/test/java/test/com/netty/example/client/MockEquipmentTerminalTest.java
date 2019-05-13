package test.com.netty.example.client;


import com.netty.example.server.helper.MessageBuildHelper;
import com.netty.example.server.proto.SignallingMessage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MockEquipmentTerminalTest {


    @Test
    public void testTerminal() throws Exception {

        List<MockEquipmentTerminal> list = new ArrayList<>();

        String host = "127.0.0.1";
        int port = 19999;

        for (int i = 0; i < 10; i++) {
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
    public void testFormatter() throws Exception {


        SignallingMessage.ConnectionRequestMessage requestMessage = SignallingMessage.ConnectionRequestMessage
                .newBuilder()
                .setSessionIdentifier("123")
                .build();

        SignallingMessage.WrapperMessage message = MessageBuildHelper.getConnectionRequestMessage(requestMessage);
    }

}
