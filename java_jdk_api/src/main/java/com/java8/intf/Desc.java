package com.java8.intf;

import java.lang.annotation.*;




/*
 * 类，方法，参数，字段的描述注解
 *
 * @author Laishr
 * @version 1.0,2015/11/06
 * Copyright (c) 2015.
 */


@Target({ElementType.PACKAGE, ElementType.TYPE,
        ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD,
        ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

public @interface Desc {

    /**
     * 默认名称
     *
     * @return String
     */
    String value() default "";


    /**
     * 标识或代码
     *
     * @return int
     */
    String code() default "";

    /**
     * 名称
     * 通常指中文名称
     *
     * @return
     */

    String name() default "";


    /**
     * 详细信息
     *
     * @return
     */
    String detail() default "";

}
