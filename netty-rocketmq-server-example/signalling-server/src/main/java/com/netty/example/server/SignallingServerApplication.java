package com.netty.example.server;

import com.netty.example.server.monitor.HealthMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.netty.example.server"})
public class SignallingServerApplication implements CommandLineRunner {


    @Autowired
    private HealthMonitor healthMonitor;

    @Autowired
    private SignallingServer signallingServer;


    public static void main(String[] args) {

        SpringApplication.run(SignallingServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        healthMonitor.monitor();
        this.signallingServer.start();
    }
}
