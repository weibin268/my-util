package com.zhuang.hutool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import org.junit.Test;

public class FileUtilTest {

    @Test
    public void test() {
        //System.out.println(System.getProperty("user.dir"));
        FileUtil.readUtf8Lines(System.getProperty("user.dir") + "/src/test/java/com/zhuang/hutool/FileUtilTest.java").forEach(System.out::println);
    }

    @Test
    public void test2() {
        FileUtil.readUtf8Lines(this.getClass().getResource("/txt/test.txt")).forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------");
        FileUtil.readUtf8Lines(ResourceUtil.getResource("txt/test.txt")).forEach(System.out::println);
    }

}
