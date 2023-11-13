package com.zhuang.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MapUtilsTest {

    @Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", null);
        map.put("c", "");
        MapUtils.removeNullOrEmpty(map);
        System.out.println(map);
    }

}
