package com.wuxp.study.nocas;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;

import static com.alibaba.nacos.sys.env.Constants.FUNCTION_MODE_PROPERTY_NAME;
import static com.alibaba.nacos.sys.env.Constants.STANDALONE_MODE_PROPERTY_NAME;

/**
 * @author wuxp
 */
@SpringBootApplication(scanBasePackages = {"com.alibaba.nacos"})
@ServletComponentScan
@EnableScheduling
@ActiveProfiles("dev")
public class NacosConsoleApplication {

    public static void main(String[] args) {
        System.setProperty(STANDALONE_MODE_PROPERTY_NAME, Boolean.TRUE.toString());
        System.setProperty(FUNCTION_MODE_PROPERTY_NAME, "config");
        SpringApplication.run(NacosConsoleApplication.class, args);
    }

}
