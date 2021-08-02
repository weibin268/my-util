package com.zhuang.util.spring;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SpringUtil.class)
public @interface EnableKafkaUtils {

}
