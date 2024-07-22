package com.zhuang.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ByteUtils {

    public static float getFloat(byte[] bytes) {
        return getFloat(bytes, ByteOrder.BIG_ENDIAN);
    }

    public static float getFloat(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getFloat();
    }

    public static short getShort(byte[] bytes) {
        return getShort(bytes, ByteOrder.BIG_ENDIAN);
    }

    public static short getShort(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getShort();
    }

    public static int getInt(byte[] bytes) {
        return getInt(bytes, ByteOrder.BIG_ENDIAN);
    }

    public static int getInt(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getInt();
    }

    public static long getLong(byte[] bytes) {
        return getLong(bytes, ByteOrder.BIG_ENDIAN);
    }

    public static long getLong(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getLong();
    }

    public static byte[] getBytes(float value) {
        return getBytes(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] getBytes(float value, ByteOrder bo) {
        return ByteBuffer.allocate(Float.BYTES).order(bo).putFloat(value).array();
    }

    public static byte[] getBytes(int value) {
        return getBytes(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] getBytes(int value, ByteOrder bo) {
        return ByteBuffer.allocate(Integer.BYTES).order(bo).putInt(value).array();
    }

    public static byte[] hexToBytes(String hex) {
        final int len = hex.length();
        if (len % 2 != 0)
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + hex);
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int h = hexToInt(hex.charAt(i));
            int l = hexToInt(hex.charAt(i + 1));
            if (h == -1 || l == -1)
                throw new IllegalArgumentException("contains illegal character for hexBinary: " + hex);
            out[i / 2] = (byte) (h * 16 + l);
        }
        return out;
    }

    public static String binToHex(String bin) {
        int remain = bin.length() % 8;
        if (remain != 0) {
            bin = repeat('0', 8 - remain) + bin;
        }
        int totalBytes = bin.length() / 8;
        int offset = 0;
        int index = 0;
        byte[] bytes = new byte[totalBytes];
        while (offset < bin.length()) {
            bytes[index] = Integer.valueOf(bin.substring(offset, offset + 8), 2).byteValue();
            offset = offset + 8;
            index++;
        }
        return bytesToHex(bytes);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString().toUpperCase();
    }

    public static String bytesToBin(byte[] bytes) {
        if (bytes == null) return null;
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String strBin = Integer.toBinaryString(b);
            int lack = 8 - strBin.length();
            if (lack > 0) {
                strBin = repeat('0', lack) + strBin;
            }
            sb.append(strBin);
        }
        return sb.toString();
    }

    private static int hexToInt(char ch) {
        if ('0' <= ch && ch <= '9') return ch - '0';
        if ('A' <= ch && ch <= 'F') return ch - 'A' + 10;
        if ('a' <= ch && ch <= 'f') return ch - 'a' + 10;
        return -1;
    }

    private static String repeat(char c, int count) {
        if (count <= 0) {
            return "";
        }
        char[] result = new char[count];
        Arrays.fill(result, c);
        return new String(result);
    }

    public static BytesReader getBytesReader(byte[] bytes) {
        return new BytesReader(bytes);
    }

    public static class BytesReader {
        private byte[] bytes;
        private int offset;

        public BytesReader(byte[] bytes) {
            this.bytes = bytes;
            this.offset = 0;
        }

        public byte[] getBytes(int length) {
            byte[] result = new byte[length];
            System.arraycopy(bytes, offset, result, 0, length);
            offset = offset + length;
            return result;
        }

        public byte getByte() {
            byte[] bytes1 = getBytes(1);
            return bytes1[0];
        }

        public short getShort() {
            return getShort(ByteOrder.BIG_ENDIAN);
        }

        public short getShort(ByteOrder bo) {
            byte[] bytes1 = getBytes(2);
            return ByteUtils.getShort(bytes1, bo);
        }

        public int getInt() {
            return getInt(ByteOrder.BIG_ENDIAN);
        }

        public int getInt(ByteOrder bo) {
            byte[] bytes1 = getBytes(4);
            return ByteUtils.getInt(bytes1, bo);
        }

        public long getLong() {
            return getLong(ByteOrder.BIG_ENDIAN);
        }

        public long getLong(ByteOrder bo) {
            byte[] bytes1 = getBytes(8);
            return ByteUtils.getLong(bytes1, bo);
        }
    }
}
