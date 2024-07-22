package com.zhuang.util;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

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
        System.out.println(ByteUtils.bytesToHex(bytes));
    }

    @Test
    public void toHex() {
        byte[] bodyBytes = "庄伟斌".getBytes(StandardCharsets.UTF_8);
        byte[] headerBytes = ByteUtils.getBytes(bodyBytes.length);
        System.out.println(ByteUtils.bytesToHex(headerBytes) + ByteUtils.bytesToHex(bodyBytes));
    }

    @Test
    public void hexToBytes() {
        byte[] bytes = ByteUtils.hexToBytes("FA12");
        System.out.println(bytes);
    }

    @Test
    public void bytesToBin() {
        String bin = ByteUtils.bytesToBin(ByteUtils.hexToBytes("1111"));
        System.out.println(bin);
    }

    @Test
    public void binToHex() {
        String bin = ByteUtils.binToHex("0000000100000011");
        System.out.println(bin);
    }

    @Test
    public void intToHex() {
        String hex = ByteUtils.intToHex(123456, 8);
        System.out.println(hex);
    }
}
