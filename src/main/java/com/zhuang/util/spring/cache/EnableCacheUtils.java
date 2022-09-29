package com.zhuang.util.spring.cache;

import com.zhuang.util.spring.cache.CacheUtils;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(CacheUtils.class)
public @interface EnableCacheUtils {

}
