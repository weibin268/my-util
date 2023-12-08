package com.zhuang.util;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {

    @FunctionalInterface
    public interface PropertyHandler {
        void handle(PropertyContext propertyContext) throws InvocationTargetException, IllegalAccessException;
    }

    public static class PropertyContext {

        private Object bean;
        private BeanInfo beanInfo;
        private boolean primitive;
        private Field field;
        private PropertyDescriptor descriptor;
        private String fullName;
        private String name;
        private Object value;
        private Method readMethod;
        private Method writeMethod;

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

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
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

        public boolean isPrimitive() {
            return primitive;
        }

        public void setPrimitive(boolean primitive) {
            this.primitive = primitive;
        }

    }

    private static final List<Class<?>> primitiveClassList = Arrays.asList(Number.class, String.class, Date.class);

    public static void recursiveProperty(Object bean, PropertyHandler propertyHandler, Class<?>... propertyClasses) {
        recursiveProperty(bean, "", propertyHandler, propertyClasses);
    }

    public static void recursiveProperty(Object bean, String beanName, PropertyHandler propertyHandler, Class<?>... propertyClasses) {
        try {
            List<Class<?>> propertyClassList = Arrays.asList(propertyClasses);
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String name = propertyDescriptor.getName();
                if (name.equals("class")) continue;
                Field field = ReflectUtil.getField(bean.getClass(), name);
                if (field == null) continue;
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
                propertyContext.setField(field);
                propertyContext.setDescriptor(propertyDescriptor);
                propertyContext.setName(name);
                if (StrUtil.isNotEmpty(beanName)) {
                    propertyContext.setFullName(beanName + "." + name);
                } else {
                    propertyContext.setFullName(name);
                }
                propertyContext.setValue(objProperty);
                propertyContext.setReadMethod(readMethod);
                propertyContext.setWriteMethod(writeMethod);
                if (inClassList(objProperty, needHandleClasses)) {
                    propertyContext.setPrimitive(true);
                    propertyHandler.handle(propertyContext);
                } else if (Collection.class.isAssignableFrom(objProperty.getClass())) {
                    propertyContext.setPrimitive(false);
                    propertyHandler.handle(propertyContext);
                    Collection<?> collection = (Collection<?>) objProperty;
                    Integer index = 0;
                    for (Object o : collection) {
                        recursiveProperty(o, propertyContext.getFullName() + "." + index, propertyHandler, propertyClasses);
                        index++;
                    }
                } else if (objProperty.getClass().isArray()) {
                    propertyContext.setPrimitive(false);
                    propertyHandler.handle(propertyContext);
                    Object[] objects = (Object[]) objProperty;
                    Integer index = 0;
                    for (Object o : objects) {
                        recursiveProperty(o, propertyContext.getFullName() + "." + index, propertyHandler, propertyClasses);
                        index++;
                    }
                } else {
                    propertyContext.setPrimitive(false);
                    propertyHandler.handle(propertyContext);
                    recursiveProperty(objProperty, propertyContext.getFullName(), propertyHandler, propertyClasses);
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
