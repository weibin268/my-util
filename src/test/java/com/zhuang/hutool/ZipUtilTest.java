package com.zhuang.hutool;

import cn.hutool.core.util.ZipUtil;
import org.junit.Test;

import java.io.File;

public class ZipUtilTest {

    @Test
    public void test() {
        File zip = ZipUtil.zip("D:\\temp\\dist");
    }

}
