package com.zhuang.util.algorithm;

import org.junit.Test;

public class SortUtilsTest {

    private static final int[] nums = new int[]{11, 4, 2, 6, 3, 9, 8, 7, 10, 1, 5};

    @Test
    public void test() {
        SortUtils.sortByInsertion(nums);
        SortUtils.printNums(nums);
    }

}