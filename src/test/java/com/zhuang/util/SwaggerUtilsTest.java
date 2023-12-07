package com.zhuang.util;

import com.zhuang.model.User4Swagger;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

public class SwaggerUtilsTest {

    @Test
    public void test() {
        User4Swagger user = new User4Swagger();
        user.setName("zwb");
        user.setAge(18);
        user.setHeight(new BigDecimal("1.72"));
        User4Swagger wife = new User4Swagger();
        wife.setName("lxc");
        wife.setAge(17);
        wife.setHeight(new BigDecimal("1.60"));
        user.setWife(wife);
        User4Swagger son1 = new User4Swagger();
        son1.setName("zxc");
        son1.setAge(10);
        son1.setHeight(new BigDecimal("1.2"));
        user.getSons().add(son1);
        User4Swagger son2 = new User4Swagger();
        son2.setName("zxy");
        son2.setAge(8);
        son2.setHeight(new BigDecimal("1.0"));
        user.getSons().add(son2);

        Map<String, String> propertyNameMap = SwaggerUtils.getPropertyNameMap(user);
        System.out.println(propertyNameMap);
    }

}
