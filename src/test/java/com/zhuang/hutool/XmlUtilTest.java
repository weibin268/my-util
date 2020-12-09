package com.zhuang.hutool;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.XmlUtil;
import org.junit.Test;

import java.util.Map;

public class XmlUtilTest {

    public class User {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    @Test
    public void test() {
        String strXml = "<user><name>zwb</name><age>18</age></user>";
        Map map = XmlUtil.xmlToMap(strXml);
        System.out.println(map);
        User user = BeanUtil.mapToBean(map, User.class, true, CopyOptions.create());
        System.out.println(user);
    }
}
