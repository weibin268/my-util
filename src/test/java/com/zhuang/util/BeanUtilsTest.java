package com.zhuang.util;

import com.zhuang.model.User;
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
        User son1 = new User();
        son1.setName("zxc");
        son1.setAge(10);
        son1.setHeight(new BigDecimal("1.2"));
        user.getSons().add(son1);
        User son2 = new User();
        son2.setName("zxy");
        son2.setAge(8);
        son2.setHeight(new BigDecimal("1.0"));
        user.getSons().add(son2);

        BeanUtils.recursiveProperty(user, c -> {
            System.out.println(c.getName() + ":" + c.getValue());
            if (c.getValue() instanceof BigDecimal) {
                c.write(((BigDecimal) c.getValue()).stripTrailingZeros());
            }
        });

        System.out.println(user);
    }

}
