package com.zhuang.hutool;

import cn.hutool.http.HttpUtil;
import org.junit.Test;

public class HttpUtilTest {

    @Test
    public void test() {
        String result = HttpUtil.get("https://www.baidu.com");
        System.out.println(result);
    }

}
