package com.zhuang.util;

import com.zhuang.beans.User;
import org.junit.Test;

public class BeanUtilsTest {

    @Test
    public void test() {
        User user = new User();
        user.setName("zwb");
        user.setAge(10);
        user.setWife(new User());
        BeanUtils.recursiveProperty(user, c -> {
            System.out.print(":");
            System.out.println(c);
        });
    }

}
