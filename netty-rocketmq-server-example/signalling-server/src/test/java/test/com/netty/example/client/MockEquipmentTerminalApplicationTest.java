package test.com.netty.example.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@SpringBootApplication
public class MockEquipmentTerminalApplicationTest implements CommandLineRunner {

    @Value("${signalling.server.port}")
    private int port;

    private String host = "127.0.0.1";


    public static void main(String[] args) {
        SpringApplication.run(MockEquipmentTerminalApplicationTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<MockEquipmentTerminal> list = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            MockEquipmentTerminal mockEquipmentTerminal = new MockEquipmentTerminal(host, port, i + "");
            list.add(mockEquipmentTerminal);
        }

        for (MockEquipmentTerminal terminal : list) {
            Thread.sleep(Math.round(5) * 1000);
            new Thread(terminal).start();
        }
        log.info("启动成功");

        Thread.sleep(3600 * 1000);
    }
}
