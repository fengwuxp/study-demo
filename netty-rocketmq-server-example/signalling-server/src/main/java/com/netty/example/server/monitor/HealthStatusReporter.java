package com.netty.example.server.monitor;


/**
 * 服务健康状态上报
 */
public interface HealthStatusReporter {


    /**
     * 上报服务的健康状态
     *
     * @param info
     */
    void report(HealthStatusInfo info);
}
