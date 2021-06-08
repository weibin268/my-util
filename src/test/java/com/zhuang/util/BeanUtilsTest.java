package com.zhuang.util;

import com.zhuang.beans.User;
import org.junit.Test;

import java.math.BigDecimal;

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
        BeanUtils.recursiveProperty(user, c -> {
            System.out.println(c.getName() + ":" + c.getValue());
            c.write(((BigDecimal) c.getValue()).stripTrailingZeros());
        }, BigDecimal.class);

        System.out.println(user);
    }

}
