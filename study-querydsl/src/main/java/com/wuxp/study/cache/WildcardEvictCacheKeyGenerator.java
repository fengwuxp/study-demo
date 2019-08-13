package com.wuxp.study.cache;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 统配形式的 evict cache 的 keyGenerator
 */
public interface WildcardEvictCacheKeyGenerator extends KeyGenerator {
}
