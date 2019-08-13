package com.wuxp.study.cache.generator;

import com.wuxp.study.cache.WildcardPutCacheKeyGenerator;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class DefaultWildcardPutCacheKeyGenerator implements WildcardPutCacheKeyGenerator {


    @Override
    public Object generate(Object target, Method method, Object... params) {

        return "1";
    }
}
