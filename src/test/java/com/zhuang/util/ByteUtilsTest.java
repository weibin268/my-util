package com.zhuang.util;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

public class ByteUtilsTest {

    @Test
    public void getFloat() {
        try (InputStream inputStream = new FileInputStream("/Users/zhuang/Desktop/PP00004593_20220108104100float.data")) {
            while (inputStream.available() > 0) {
                byte[] bytes = new byte[4];
                inputStream.read(bytes);
                float aFloat = ByteUtils.getFloat(bytes);
                System.out.println(aFloat);
                System.out.println(BigDecimal.valueOf(aFloat));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}