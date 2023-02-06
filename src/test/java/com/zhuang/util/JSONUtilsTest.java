package com.zhuang.util;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

public class JSONUtilsTest {

    @Test
    public void test() {
        List<JSONUtils.DiffInfo> diffInfoList = JSONUtils.getJsonDiff("{person:{name:\"a\"}}", "{person:{name:\"b\",age:18}}");
        System.out.println(JSON.toJSONString(diffInfoList));
    }

}
