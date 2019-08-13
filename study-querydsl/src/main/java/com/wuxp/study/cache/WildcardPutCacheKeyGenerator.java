package com.wuxp.study.cache;

import org.springframework.cache.interceptor.KeyGenerator;


/**
 * 统配形式的 put cache 的 keyGenerator
 */
public interface WildcardPutCacheKeyGenerator extends KeyGenerator {
}
