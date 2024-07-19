package com.zhuang.util;

/**
 * hutool相关的实现类型（CRC16、CRC8）
 */
public class CRCUtils {

    private static final int POLYNOMIAL_16 = 0xA001;
    private static final short POLYNOMIAL_8 = 0x07; // CRC8多项式，也可以使用其他的多项式
    private static final short POLYNOMIAL_7 = 0xe5; // CRC7多项式，也可以使用其他的多项式

    public static int getCRC16(byte[] bytes) {
        return getCRC16(bytes, 0, bytes.length);
    }

    public static int getCRC16(byte[] bytes, int pos, int length) {
        int crc = 0xFFFF;
        for (int i = pos; i < length; i++) {
            byte b = bytes[i];
            crc ^= (int) b & 0xFF;
            for (int j = 0; j < 8; j++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ POLYNOMIAL_16;
                } else {
                    crc = crc >>> 1;
                }
            }
        }
        return crc;
    }

    public static byte getCRC8(byte[] bytes) {
        return getCRC8(bytes, POLYNOMIAL_8);
    }

    public static byte getCRC8(byte[] bytes, short polynomial) {
        return getCRC8(bytes, 0, bytes.length, polynomial);
    }

    public static byte getCRC8(byte[] bytes, int pos, int length, short polynomial) {
        short crc = 0x00; // CRC8校验值初始值
        for (int i = pos; i < length; i++) {
            byte b = bytes[i];
            crc ^= b; // 异或
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x80) != 0) {
                    crc = (short) ((crc << 1) ^ polynomial); // 左移并异或
                } else {
                    crc <<= 1; // 左移
                }
            }
        }
        return (byte) (crc & 0xFF); // 返回低8位
    }

    public static byte getCRC7(byte[] bytes) {
        return getCRC8(bytes, POLYNOMIAL_7);
    }

}
