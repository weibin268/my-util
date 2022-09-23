package com.zhuang.hutool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.checksum.CRC16;
import cn.hutool.core.io.checksum.crc16.CRC16CCITT;
import org.junit.Test;

public class CRC16Test {

    @Test
    public void test(){
        byte[] bytes = FileUtil.readBytes("C:\\Users\\admin\\Documents\\Tencent Files\\448075543\\FileRecv\\Crc16Modbus.cs");
        CRC16CCITT crc16 = new CRC16CCITT();
        crc16.update(bytes);
        System.out.println(crc16.getHexValue());
    }

}
