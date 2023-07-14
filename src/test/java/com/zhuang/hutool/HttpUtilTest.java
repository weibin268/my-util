package com.zhuang.hutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.junit.Test;

public class HttpUtilTest {

    @Test
    public void get() {
        String result = HttpUtil.get("https://www.baidu.com");
        System.out.println(result);
    }

    @Test
    public void createPost4Form() {
        HttpRequest post = HttpUtil.createPost("http://localhost:8888");
        post.header("token", "test");
        post.form("name", "zwb");
        String body = post.execute().body();
        System.out.println(body);
    }

    @Test
    public void createPost4Json() {
        HttpRequest post = HttpUtil.createPost("http://localhost:8888");
        post.header("token", "test");
        String body = post.body("{age:1}").execute().body();
        System.out.println(body);

    }
}
