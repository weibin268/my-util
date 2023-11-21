package com.zhuang.util.jackson;

import com.zhuang.model.User4Jackson;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class JacksonUtilsTest {

    @Test
    public void test() {
        User4Jackson user = new User4Jackson();
        user.setName("zwb");
        user.setAge(18);
        user.setHeight(new BigDecimal("1.10"));
        user.setBirthday(new Date());
        //user.setJobTitle("软件工程师");
        String s = JacksonUtils.toJsonStr(user);
        System.out.println(s);
    }

}
