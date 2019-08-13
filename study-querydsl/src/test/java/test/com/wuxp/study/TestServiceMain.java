package test.com.wuxp.study;

import com.wuxp.study.querydsl.config.StudyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.text.MessageFormat;


@EnableScheduling
@SpringBootApplication(
        scanBasePackages = {"com.wuxp", "test.com.wuxp"})
@Import(StudyConfig.class)
@Configuration
public class TestServiceMain {

    public static void main(String[] args) {

        System.out.println(MessageFormat.format("{0} WORK DIR:{1}", TestServiceMain.class.getSimpleName(), new File("").getAbsolutePath()));

        SpringApplication.run(TestServiceMain.class, args);

    }
}