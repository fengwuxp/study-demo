package com.wuxp.study.cache.key;

import com.wuxp.study.cache.WildcardKey;

/**
 * 通配符的 put key
 */
public class PutWildcardKey implements WildcardKey {

    /**
     * 表达式
     */
    private String expression;

    public PutWildcardKey(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    @Override
    public final int hashCode() {
        return -1;
    }
}
