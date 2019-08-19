package test.com.wuxp.study.repository;


import com.wuxp.study.model.Person;
import com.wuxp.study.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.wuxp.study.SpringBootElasticsearchApplicationTests;

import java.util.Date;
import java.util.Optional;

//@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootElasticsearchApplicationTests.class})
//@Transactional(rollbackFor = {Throwable.class})
@Slf4j
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSave() {
        Person entity = new Person();
        entity.setAge(11);
        entity.setBirthday(new Date());
        entity.setId(1L);
        entity.setName("张三");
        entity.setCountry("中国");
//        entity.setRemark("创建");
        Person person = personRepository.save(entity);

        Assert.assertNotNull(person);
        log.debug("{}", person);
    }

    @Test
    public void testQuery(){
        Person person = personRepository.findById(1L).orElse(null);
        Assert.assertNotNull(person);
        log.debug("{}", person);
    }
}
