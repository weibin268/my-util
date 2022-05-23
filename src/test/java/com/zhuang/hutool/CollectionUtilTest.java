package com.zhuang.hutool;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CollectionUtilTest {

    @Test
    public void split() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<List<Integer>> split = CollectionUtil.split(integers, 2);
        System.out.println(split);
    }

}
