package com.zhuang.util;

import com.zhuang.beans.User;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BeanUtilsTest {

    @Test
    public void test() {
        User user = new User();
        user.setName("zwb");
        user.setAge(18);
        user.setHeight(new BigDecimal("1.72"));
        User wife = new User();
        wife.setName("lxc");
        wife.setAge(17);
        wife.setHeight(new BigDecimal("1.60"));
        user.setWife(wife);
        BeanUtils.recursiveProperty(user, (a, b, c) -> {
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
        }, BigDecimal.class);

        System.out.println(user);
    }

}
