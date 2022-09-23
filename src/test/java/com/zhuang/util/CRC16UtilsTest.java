package com.zhuang.util;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;

public class CRC16UtilsTest {

    @Test
    public void test() {
        byte[] bytes = FileUtil.readBytes("C:\\Users\\admin\\Documents\\Tencent Files\\448075543\\FileRecv\\Crc16Modbus.cs");
        int i = CRC16Utils.getValue(bytes);
        System.out.println(i);
        System.out.println(Integer.toHexString(i));
    }

}
