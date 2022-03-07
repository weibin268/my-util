package com.zhuang.util;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class OpenOfficeUtilsTest {

    @Test
    public void test() {
        OpenOfficeUtils.convert(new File("D:\\temp\\openoffice-test.xlsx"), new File("D:\\temp\\openoffice-test.pdf"));
    }

}
