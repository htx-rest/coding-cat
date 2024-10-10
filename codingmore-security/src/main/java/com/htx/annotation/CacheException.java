package com.htx.annotation;

import java.lang.annotation.*;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/9
 * @Desc: 自定义注解，有该注解的缓存方法会抛出异常
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
