package com.zhuang.util;

import com.zhuang.model.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtilsTest {


    @Test
    public void getListByBeginAndEnd() {
        List<String> list = StringUtils.getListByBeginAndEnd("010", "020");
        System.out.println(list);
    }

    @Test
    public void format() {
        String result = StringUtils.format("hello {}!", "zwb");
        System.out.println(result);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "zwb");
        result = StringUtils.format("hello {name}!", params);
        System.out.println(result);

        User user = new User();
        user.setName("zwb");
        result = StringUtils.format("hello ${name}!", user);
        System.out.println(result);
    }

    @Test
    public void format2() {
        System.out.println(StringUtils.format2("{}a{}b", "1", "2"));
    }

    @Test
    public void trim() {
        System.out.println(StringUtils.trim("000B00A00", '0'));
        System.out.println(StringUtils.trim("@000B00A00!.", '!', '0', '.', '@'));
        System.out.println(StringUtils.trimLeft("@000B00A00!.", '!', '0', '.', '@'));
        System.out.println(StringUtils.trimRight("@000B00A00!.", '!', '0', '.', '@'));

    }

}
