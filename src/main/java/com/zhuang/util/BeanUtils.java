package com.zhuang.util;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class BeanUtils {

    private static List<Class> primitiveClasses = Arrays.asList(Number.class, String.class);

    public static void recursiveProperty(Object bean, Consumer<Object> consumer, Class... classes) {
        try {
            PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    Object objProperty = PropertyUtils.getNestedProperty(bean, name);
                    if (objProperty == null) continue;
                    if (classes.length > 0) {
                        if (Arrays.stream(classes).anyMatch(c -> c.equals(objProperty.getClass()))) {
                            consumer.accept(objProperty);
                        } else if (!primitiveClasses.stream().anyMatch(c -> c.isAssignableFrom(objProperty.getClass()))) {
                            recursiveProperty(objProperty, consumer, classes);
                        }
                    } else {
                        if (primitiveClasses.stream().anyMatch(c -> c.isAssignableFrom(objProperty.getClass()))) {
                            consumer.accept(objProperty);
                        } else {
                            recursiveProperty(objProperty, consumer, classes);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
