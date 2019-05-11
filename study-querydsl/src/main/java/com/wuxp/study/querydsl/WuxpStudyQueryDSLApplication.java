package com.wuxp.study.querydsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.wuxp.study"})
public class WuxpStudyQueryDSLApplication {

    public static void main(String[] args) {
        SpringApplication.run(WuxpStudyQueryDSLApplication.class, args);
    }

}

