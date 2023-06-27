package com.zhuang.util;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImageUtilsTest {

    @Test
    public void getBase64ByUrl() {
        String base64ByUrl = ImageUtils.getBase64ByUrl("https://192.168.2.5/evo-pic/f535bf07-5835-11ec-98bc-2cea7fd38988/20230514/1/dsf_8acd3c37-f229-11ed-a5ee-2cea7fd38988_27669558_27787919.jpg?token=1:6f1dc011-ca80-4b40-be40-8ed0ccc24c46&oss_addr=192.168.2.5:8925");
        System.out.println(base64ByUrl);
    }

    @Test
    public void addText() throws FileNotFoundException {
        String inputFileName = "/Users/zhuang/Desktop/IMG_20211006_192434 2.jpg";
        String outputFileName = "/Users/zhuang/Desktop/IMG_20211006_192434 2_out.jpg";
        ImageUtils.addText(new FileInputStream(inputFileName), new FileOutputStream(outputFileName), "庄伟斌");
    }

}
