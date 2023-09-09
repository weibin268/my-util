package com.zhuang.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StringUtilsTest {


    @Test
    public void test() {
        List<String> list = StringUtils.getListByBeginAndEnd("010", "020");
        System.out.println(list);
    }

}
