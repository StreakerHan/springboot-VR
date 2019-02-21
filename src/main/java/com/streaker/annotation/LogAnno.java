package com.streaker.annotation;

/**
 * 定义一个方法级别的@Log注解，用于标注需要监控的方法：
 *
 * @author StreakerHan
 * @version 2018/9/14 10:46:11
 **/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnno {

    String value() default "";
}
