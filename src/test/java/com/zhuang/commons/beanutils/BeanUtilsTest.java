package com.zhuang.commons.beanutils;

import com.zhuang.beans.User;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class BeanUtilsTest {

    @Test
    public void test() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        User user = new User();
        user.setName("zwb");
        user.setAge(10);
        user.setWife(new User());
        user.getSons().add(user);
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(user);
        for (int i = 0; i < descriptors.length; i++) {
            String name = descriptors[i].getName();
            if (!"class".equals(name)) {
                System.out.println(name + ":" + propertyUtilsBean.getNestedProperty(user, name));
            }
        }
    }

}
