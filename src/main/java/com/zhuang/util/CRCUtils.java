package com.zhuang.util;

public class CRCUtils {

    private static final int POLYNOMIAL_16 = 0xA001;

    public static int getCRC16(byte[] bytes) {
        int crc = 0xFFFF;
        for (byte b : bytes) {
            crc ^= (int) b & 0xFF;
            for (int i = 0; i < 8; i++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ POLYNOMIAL_16;
                } else {
                    crc = crc >>> 1;
                }
            }
        }
        return crc;
    }

    private static final short POLYNOMIAL_8 = 0x07; // CRC8多项式，也可以使用其他的多项式

    public static byte getCRC8(byte[] bytes) {
        short crc = 0x00; // CRC8校验值初始值
        for (byte b : bytes) {
            crc ^= b; // 异或
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x80) != 0) {
                    crc = (short) ((crc << 1) ^ POLYNOMIAL_8); // 左移并异或
                } else {
                    crc <<= 1; // 左移
                }
            }
        }
        return (byte) (crc & 0xFF); // 返回低8位
    }

}
