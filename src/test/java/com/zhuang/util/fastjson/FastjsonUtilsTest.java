package com.zhuang.util.fastjson;

import com.zhuang.model.User4Fastjson;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class FastjsonUtilsTest {
    @Test
    public void test() {
        User4Fastjson user = new User4Fastjson();
        user.setName("zwb");
        user.setAge(18);
        user.setHeight(new BigDecimal("1.10"));
        user.setBirthday(new Date());
        user.setJobTitle("软件工程师");
        String s = FastjsonUtils.toJsonStr(user);
        System.out.println(s);
    }

}
