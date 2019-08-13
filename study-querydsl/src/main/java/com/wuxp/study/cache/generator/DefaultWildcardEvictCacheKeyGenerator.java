package com.wuxp.study.cache.generator;

import com.wuxp.study.cache.WildcardEvictCacheKeyGenerator;

import java.lang.reflect.Method;

public class DefaultWildcardEvictCacheKeyGenerator implements WildcardEvictCacheKeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return null;
    }
}
