package com.zhuang.util;

import com.zhuang.util.model.IdAndName;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AnnotationUtilsTest {

    @Test
    public void test() {

        Map<String, Object> valuesMap = new HashMap<>();
        final Class<IdAndName> idAndNameClass = IdAndName.class;
        AnnotationUtils.putAnnotation(idAndNameClass,Deprecated.class,valuesMap);

        System.out.println(idAndNameClass);
    }

}
