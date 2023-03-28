package com.zhuang.util;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;

public class CRCUtilsTest {

    private static final String filePath = "C:\\Users\\admin\\Documents\\Tencent Files\\448075543\\FileRecv\\Crc16Modbus.cs";

    @Test
    public void getCRC16() {
        byte[] bytes = FileUtil.readBytes(filePath);
        int i = CRCUtils.getCRC16(bytes);
        System.out.println(i);
        System.out.println(Integer.toHexString(i));
    }

    @Test
    public void getCRC8() {
        byte[] bytes = FileUtil.readBytes(filePath);
        byte b = CRCUtils.getCRC8(bytes);
        System.out.println(b);
        System.out.println(String.format("%02x", b));
    }

}
