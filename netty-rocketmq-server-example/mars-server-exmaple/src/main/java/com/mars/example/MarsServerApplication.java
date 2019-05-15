package com.mars.example;

import com.mars.example.server.MarsNettySignallingServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarsServerApplication implements CommandLineRunner {

    @Value("${signalling.server.port}")
    private int port;


    public static void main(String[] args) {
        SpringApplication.run(MarsServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new MarsNettySignallingServer(this.port).start();
    }
}
