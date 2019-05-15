package com.netty.example.server.monitor;

import org.springframework.stereotype.Component;

@Component
public class DefaultHealthMonitor implements HealthMonitor {

    @Override
    public void monitor() {

        //每隔几分钟获取一次服务的健康状态

    }
}
