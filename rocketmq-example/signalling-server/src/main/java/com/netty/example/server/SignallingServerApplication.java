package com.netty.example.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SignallingServerApplication implements CommandLineRunner {



    @Value("${signalling.server.port}")
    private int port;

    public static void main(String[] args) {

        SpringApplication.run(SignallingServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        new SignallingServer(this.port).start();
    }
}
