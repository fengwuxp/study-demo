package test.com.wuxp.study.cache;

import com.wuxp.study.querydsl.WuxpStudyQueryDSLApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.wuxp.study.TestServiceMain;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestServiceMain.class})
@Slf4j
public class CacheExampleServiceTest {

    @Autowired
    private CacheExampleService exampleService;


    @Test
    public void testQuery() {
        CacheExampleService.Example example = exampleService.query("张三");
        log.debug("{}", example);
        Assert.assertNotNull(example);
    }

    @Test
    public void testCreate() {
        CacheExampleService.Example example = exampleService.create("李四");
        log.debug("{}", example);
        Assert.assertNotNull(example);
    }
}
