package com.zhuang.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtils {

    public static float getFloat(byte[] bytes) {
        return getFloat(bytes, ByteOrder.BIG_ENDIAN);
    }

    public static float getFloat(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getFloat();
    }

    public static byte[] getBytes(float value) {
        return getBytes(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] getBytes(float value, ByteOrder bo) {
        return ByteBuffer.allocate(Float.BYTES).order(bo).putFloat(value).array();
    }

    public static int getInt(byte[] bytes) {
        return getInt(bytes, ByteOrder.BIG_ENDIAN);
    }

    public static int getInt(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getInt();
    }

    public static byte[] getBytes(int value) {
        return getBytes(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] getBytes(int value, ByteOrder bo) {
        return ByteBuffer.allocate(Integer.BYTES).order(bo).putInt(value).array();
    }

    public static String toHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString().toUpperCase();
    }

    public static byte[] toBytes(String s) {
        final int len = s.length();

        if (len % 2 != 0)
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);

        byte[] out = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(s.charAt(i));
            int l = hexToBin(s.charAt(i + 1));
            if (h == -1 || l == -1)
                throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);

            out[i / 2] = (byte) (h * 16 + l);
        }

        return out;
    }

    private static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') return ch - '0';
        if ('A' <= ch && ch <= 'F') return ch - 'A' + 10;
        if ('a' <= ch && ch <= 'f') return ch - 'a' + 10;
        return -1;
    }
}
