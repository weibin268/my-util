package com.zhuang.util;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class BeanUtils {

    @FunctionalInterface
    public interface propertyHandler {
        void handle(Object bean, String propertyName, Object propertyValue);
    }

    private static List<Class> primitiveClasses = Arrays.asList(Number.class, String.class);

    public static void recursiveProperty(Object bean, propertyHandler propertyHandler, Class... propertyClasses) {
        try {
            PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    Object objProperty = PropertyUtils.getNestedProperty(bean, name);
                    if (objProperty == null) continue;
                    if (propertyClasses.length > 0) {
                        if (Arrays.stream(propertyClasses).anyMatch(c -> c.equals(objProperty.getClass()))) {
                            propertyHandler.handle(bean, name, objProperty);
                        } else if (!primitiveClasses.stream().anyMatch(c -> c.isAssignableFrom(objProperty.getClass()))) {
                            recursiveProperty(objProperty, propertyHandler, propertyClasses);
                        }
                    } else {
                        if (primitiveClasses.stream().anyMatch(c -> c.isAssignableFrom(objProperty.getClass()))) {
                            propertyHandler.handle(bean, name, objProperty);
                        } else {
                            recursiveProperty(objProperty, propertyHandler, propertyClasses);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
