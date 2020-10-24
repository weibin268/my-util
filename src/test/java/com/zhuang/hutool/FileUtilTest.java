package com.zhuang.hutool;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;

public class FileUtilTest {

    @Test
    public void test() {
        //System.out.println(System.getProperty("user.dir"));
        FileUtil.readUtf8Lines(System.getProperty("user.dir") + "/src/test/java/com/zhuang/hutool/FileUtilTest.java").forEach(System.out::println);
    }

}
