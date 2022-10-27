package com.zhuang.util.spring;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtilsTest {

    @Test
    public void test() {
        RestTemplate restTemplate = RestTemplateUtils.getRestTemplate();
        RestTemplateUtils.setStringHttpMessageConverterAsUtf8(restTemplate);
        String forObject = restTemplate.getForObject("https://www.baidu.com", String.class);
        System.out.println(forObject);
    }
}