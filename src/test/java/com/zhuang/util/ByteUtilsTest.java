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

    @Test
    public void getBytes4Float() {
        float v = 1.12345f;
        byte[] bytes = ByteUtils.getBytes(v);
        float f = ByteUtils.getFloat(bytes);
        System.out.println(f);
    }

    @Test
    public void getBytes4Int() {
        byte[] bytes = ByteUtils.getBytes(-1);
        System.out.println(ByteUtils.toHexString(bytes));
    }
}