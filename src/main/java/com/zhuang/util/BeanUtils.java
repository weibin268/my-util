package com.zhuang.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BeanUtils {

    @FunctionalInterface
    public interface PropertyHandler {
        void handle(PropertyContext propertyContext) throws InvocationTargetException, IllegalAccessException;
    }

    public static class PropertyContext {

        private Object bean;
        private String name;
        private Object value;
        private PropertyDescriptor descriptor;
        private Method readMethod;
        private Method writeMethod;
        private BeanInfo beanInfo;

        public void write(Object value) {
            try {
                writeMethod.invoke(bean, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Object getBean() {
            return bean;
        }

        public void setBean(Object bean) {
            this.bean = bean;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public PropertyDescriptor getDescriptor() {
            return descriptor;
        }

        public void setDescriptor(PropertyDescriptor descriptor) {
            this.descriptor = descriptor;
        }

        public Method getReadMethod() {
            return readMethod;
        }

        public void setReadMethod(Method readMethod) {
            this.readMethod = readMethod;
        }

        public Method getWriteMethod() {
            return writeMethod;
        }

        public void setWriteMethod(Method writeMethod) {
            this.writeMethod = writeMethod;
        }

        public BeanInfo getBeanInfo() {
            return beanInfo;
        }

        public void setBeanInfo(BeanInfo beanInfo) {
            this.beanInfo = beanInfo;
        }
    }

    private static final List<Class<?>> primitiveClassList = Arrays.asList(Number.class, String.class);

    public static void recursiveProperty(Object bean, PropertyHandler propertyHandler, Class<?>... propertyClasses) {
        try {
            List<Class<?>> propertyClassList = Arrays.asList(propertyClasses);
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String name = propertyDescriptor.getName();
                if (name.equals("class")) continue;
                Method readMethod = propertyDescriptor.getReadMethod();
                Method writeMethod = propertyDescriptor.getWriteMethod();
                Object objProperty = readMethod.invoke(bean);
                if (objProperty == null) continue;
                if (propertyClasses.length > 0 && !inClassList(objProperty, propertyClassList) && isPrimitiveClass(objProperty))
                    continue;
                List<Class<?>> needHandleClasses = new ArrayList<>();
                if (propertyClasses.length > 0) {
                    needHandleClasses.addAll(propertyClassList);
                } else {
                    needHandleClasses.addAll(primitiveClassList);
                }
                PropertyContext propertyContext = new PropertyContext();
                propertyContext.setBean(bean);
                propertyContext.setBeanInfo(beanInfo);
                propertyContext.setDescriptor(propertyDescriptor);
                propertyContext.setName(name);
                propertyContext.setValue(objProperty);
                propertyContext.setReadMethod(readMethod);
                propertyContext.setWriteMethod(writeMethod);
                if (inClassList(objProperty, needHandleClasses)) {
                    propertyHandler.handle(propertyContext);
                } else if (Collection.class.isAssignableFrom(objProperty.getClass())) {
                    Collection<?> collection = (Collection<?>) objProperty;
                    for (Object o : collection) {
                        recursiveProperty(o, propertyHandler, propertyClasses);
                    }
                } else if (objProperty.getClass().isArray()) {
                    Object[] objects = (Object[]) objProperty;
                    for (Object o : objects) {
                        recursiveProperty(o, propertyHandler, propertyClasses);
                    }
                } else {
                    recursiveProperty(objProperty, propertyHandler, propertyClasses);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean inClassList(Object obj, List<Class<?>> classList) {
        return classList.stream().anyMatch(c -> c.isAssignableFrom(obj.getClass()));
    }

    private static boolean isPrimitiveClass(Object obj) {
        return primitiveClassList.stream().anyMatch(c -> c.isAssignableFrom(obj.getClass()));
    }

}
