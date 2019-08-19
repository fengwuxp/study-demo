package test.com.wuxp.study;

import com.wuxp.study.config.RestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;

@SpringBootApplication(
        scanBasePackages = {"com.wuxp"})
@Import(RestConfig.class)
@Configuration
public class SpringBootElasticsearchApplicationTests {

    public static void main(String[] args) {
		System.out.println(SpringBootElasticsearchApplicationTests.class.getSimpleName() + " WORK DIR:" + new File("").getAbsolutePath());

		SpringApplication.run(SpringBootElasticsearchApplicationTests.class, args);
    }

}
