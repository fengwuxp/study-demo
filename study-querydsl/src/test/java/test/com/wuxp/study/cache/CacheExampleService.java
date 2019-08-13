package test.com.wuxp.study.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CacheExampleService {

    static final String EXAMPLE_CACHE_NAME = "EXAMPLE_CACHE_NAME";


    @Cacheable(value = EXAMPLE_CACHE_NAME ,keyGenerator = "wildcardPutCacheKeyGenerator")
    public Example query(String name) {
        return new Example(RandomUtils.nextLong(),name);
    }

    @CacheEvict(value = EXAMPLE_CACHE_NAME ,keyGenerator = "wildcardEvictCacheKeyGenerator")
    public Example create(String name){
        return new Example(RandomUtils.nextLong(),name);
    }


    @Data
    @AllArgsConstructor
    public static class Example implements Serializable {

        private static final long serialVersionUID = 3939927538770173442L;

        private Long id;

        private String name;
    }

}
