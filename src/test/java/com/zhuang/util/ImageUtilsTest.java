package com.zhuang.util;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImageUtilsTest {

    @Test
    public void getBase64ByUrl() {
        String path = "https://pics1.baidu.com/feed/d62a6059252dd42aba8f801aaabe38b9c8eab80b.jpeg@f_auto?token=8b58cc25e5943bf751a0fc4aefda2007";
        String base64ByUrl = ImageUtils.getBase64ByUrl(path);
        System.out.println(base64ByUrl);
    }

    @Test
    public void addText() throws FileNotFoundException {
        String inputFileName = "/Users/zhuang/Desktop/IMG_20211006_192434 2.jpg";
        String outputFileName = "/Users/zhuang/Desktop/IMG_20211006_192434 2_out.jpg";
        ImageUtils.addText(new FileInputStream(inputFileName), new FileOutputStream(outputFileName), "2023年06月26日 星期一 00:03:54");
    }

}
