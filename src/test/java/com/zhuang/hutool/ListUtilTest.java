package com.zhuang.hutool;

import cn.hutool.core.collection.ListUtil;
import org.junit.Test;

import java.util.Arrays;

public class ListUtilTest {

    @Test
    public void test() {
        ListUtil.reverse(ListUtil.sortByPinyin(Arrays.asList("庄", "斌", "伟"))).forEach(System.out::println);
    }

}
