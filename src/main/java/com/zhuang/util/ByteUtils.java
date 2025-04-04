package com.zhuang.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteUtils {

    private static final ByteOrder DEFAULT_BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    public static short getUByte(byte byte1) {
        return ByteBuffer.wrap(new byte[]{0, byte1}).order(ByteOrder.BIG_ENDIAN).getShort();
    }

    public static float getFloat(byte[] bytes) {
        return getFloat(bytes, DEFAULT_BYTE_ORDER);
    }

    public static float getFloat(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getFloat();
    }

    public static short getShort(byte[] bytes) {
        return getShort(bytes, DEFAULT_BYTE_ORDER);
    }

    public static short getShort(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getShort();
    }

    public static int getUShort(byte[] bytes) {
        return getUShort(bytes, DEFAULT_BYTE_ORDER);
    }

    public static int getUShort(byte[] bytes, ByteOrder bo) {
        int uShortLength = 2;
        int intLength = 4;
        if (bytes.length != uShortLength) {
            throw new RuntimeException("bytes length must be " + uShortLength + "!");
        }
        byte[] newBytes = new byte[intLength];
        System.arraycopy(bytes, 0, newBytes, (bo == ByteOrder.LITTLE_ENDIAN ? 0 : uShortLength), uShortLength);
        return ByteBuffer.wrap(newBytes).order(bo).getInt();
    }

    public static int getInt(byte[] bytes) {
        return getInt(bytes, DEFAULT_BYTE_ORDER);
    }

    public static int getInt(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getInt();
    }

    public static long getUInt(byte[] bytes) {
        return getUInt(bytes, DEFAULT_BYTE_ORDER);
    }

    public static long getUInt(byte[] bytes, ByteOrder bo) {
        int uIntLength = 4;
        int longLength = 8;
        if (bytes.length != uIntLength) {
            throw new RuntimeException("bytes length must be " + uIntLength + "!");
        }
        byte[] newBytes = new byte[longLength];
        System.arraycopy(bytes, 0, newBytes, (bo == ByteOrder.LITTLE_ENDIAN ? 0 : uIntLength), uIntLength);
        return ByteBuffer.wrap(newBytes).order(bo).getLong();
    }

    public static long getLong(byte[] bytes) {
        return getLong(bytes, DEFAULT_BYTE_ORDER);
    }

    public static long getLong(byte[] bytes, ByteOrder bo) {
        return ByteBuffer.wrap(bytes).order(bo).getLong();
    }

    public static byte shortToUByte(short value) {
        return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(value).array()[1];
    }

    public static byte[] shortToBytes(short value) {
        return shortToBytes(value, DEFAULT_BYTE_ORDER);
    }

    public static byte[] shortToBytes(short value, ByteOrder bo) {
        return ByteBuffer.allocate(Short.BYTES).order(bo).putShort(value).array();
    }

    public static byte[] uShortToBytes(int value) {
        return uShortToBytes(value, DEFAULT_BYTE_ORDER);
    }

    public static byte[] uShortToBytes(int value, ByteOrder bo) {
        byte[] bytes4Int = ByteBuffer.allocate(Integer.BYTES).order(bo).putInt(value).array();
        byte[] bytes4Short = new byte[2];
        System.arraycopy(bytes4Int, bo == ByteOrder.BIG_ENDIAN ? bytes4Short.length : 0, bytes4Short, 0, bytes4Short.length);
        return bytes4Short;
    }

    public static byte[] intToBytes(int value) {
        return intToBytes(value, DEFAULT_BYTE_ORDER);
    }

    public static byte[] intToBytes(int value, ByteOrder bo) {
        return ByteBuffer.allocate(Integer.BYTES).order(bo).putInt(value).array();
    }

    public static byte[] uIntToBytes(long value) {
        return uIntToBytes(value, DEFAULT_BYTE_ORDER);
    }

    public static byte[] uIntToBytes(long value, ByteOrder bo) {
        byte[] bytes4Long = ByteBuffer.allocate(Long.BYTES).order(bo).putLong(value).array();
        byte[] bytes4Int = new byte[4];
        System.arraycopy(bytes4Long, bo == ByteOrder.BIG_ENDIAN ? bytes4Int.length : 0, bytes4Int, 0, bytes4Int.length);
        return bytes4Int;
    }

    public static byte[] longToBytes(long value) {
        return longToBytes(value, DEFAULT_BYTE_ORDER);
    }

    public static byte[] longToBytes(long value, ByteOrder bo) {
        return ByteBuffer.allocate(Long.BYTES).order(bo).putLong(value).array();
    }

    public static byte[] floatToBytes(float value) {
        return floatToBytes(value, DEFAULT_BYTE_ORDER);
    }

    public static byte[] floatToBytes(float value, ByteOrder bo) {
        return ByteBuffer.allocate(Float.BYTES).order(bo).putFloat(value).array();
    }

    public static byte[] doubleToBytes(double value) {
        return doubleToBytes(value, DEFAULT_BYTE_ORDER);
    }

    public static byte[] doubleToBytes(double value, ByteOrder bo) {
        return ByteBuffer.allocate(Double.BYTES).order(bo).putDouble(value).array();
    }

    public static byte[] hexToBytes(String hex) {
        final int len = hex.length();
        if (len % 2 != 0)
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + hex);
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int h = hexCharToInt(hex.charAt(i));
            int l = hexCharToInt(hex.charAt(i + 1));
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
            sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return sb.toString();
    }

    public static String intToHex(int i) {
        return intToHex(i, -1);
    }

    public static String intToHex(int i, int minLength) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() < minLength) {
            hexString = repeat('0', minLength - hexString.length()) + hexString;
        }
        return hexString.toUpperCase();
    }

    public static byte[] reverse(byte[] bytes) {
        byte[] reversedBytes = new byte[bytes.length];
        for (byte i = 0; i < bytes.length; i++) {
            reversedBytes[bytes.length - 1 - i] = bytes[i];
        }
        return reversedBytes;
    }

    public static BytesReader getBytesReader(byte[] bytes) {
        return new BytesReader(bytes);
    }

    public static BytesWriter getBytesWriter() {
        return new BytesWriter();
    }

    public static class BytesReader {
        private byte[] bytes;
        private int offset;
        private int markedOffset;

        public BytesReader(byte[] bytes) {
            this.bytes = bytes;
            this.offset = 0;
            this.markedOffset = 0;
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

        public short getUByte() {
            return ByteUtils.getUByte(getByte());
        }

        public short getShort() {
            return getShort(DEFAULT_BYTE_ORDER);
        }

        public short getShort(ByteOrder bo) {
            byte[] bytes1 = getBytes(2);
            return ByteUtils.getShort(bytes1, bo);
        }

        public int getInt() {
            return getInt(DEFAULT_BYTE_ORDER);
        }

        public int getInt(ByteOrder bo) {
            byte[] bytes1 = getBytes(4);
            return ByteUtils.getInt(bytes1, bo);
        }

        public int getUShort() {
            return getUShort(DEFAULT_BYTE_ORDER);
        }

        public int getUShort(ByteOrder bo) {
            byte[] bytes1 = getBytes(2);
            return ByteUtils.getUShort(bytes1, bo);
        }

        public long getLong() {
            return getLong(DEFAULT_BYTE_ORDER);
        }

        public long getLong(ByteOrder bo) {
            byte[] bytes1 = getBytes(8);
            return ByteUtils.getLong(bytes1, bo);
        }

        public long getUInt() {
            return getUInt(DEFAULT_BYTE_ORDER);
        }

        public long getUInt(ByteOrder bo) {
            byte[] bytes1 = getBytes(4);
            return ByteUtils.getUInt(bytes1, bo);
        }

        public float getFloat() {
            return getFloat(DEFAULT_BYTE_ORDER);
        }

        public float getFloat(ByteOrder bo) {
            byte[] bytes1 = getBytes(4);
            return ByteUtils.getFloat(bytes1, bo);
        }

        public void skipBytes(int length) {
            offset = offset + length;
        }

        public void markOffset() {
            markedOffset = offset;
        }

        public void resetOffset() {
            offset = markedOffset;
        }

        public int readableBytes() {
            return bytes.length - offset;
        }
    }

    public static class BytesWriter {
        private int totalLength = 0;
        List<byte[]> bytesList = new ArrayList<>();

        public BytesWriter putBytes(byte[] bytes) {
            totalLength = totalLength + bytes.length;
            this.bytesList.add(bytes);
            return this;
        }

        public BytesWriter putByte(byte byte1) {
            putBytes(new byte[]{byte1});
            return this;
        }

        public BytesWriter putUByte(short short1) {
            return putByte(ByteUtils.shortToUByte(short1));
        }

        public BytesWriter putUShort(int i) {
            return putUShort(i, DEFAULT_BYTE_ORDER);
        }

        public BytesWriter putUShort(int i, ByteOrder bo) {
            byte[] bytes = ByteUtils.uShortToBytes(i, bo);
            putBytes(bytes);
            return this;
        }

        public BytesWriter putInt(int i) {
            return putInt(i, DEFAULT_BYTE_ORDER);
        }

        public BytesWriter putInt(int i, ByteOrder bo) {
            byte[] bytes = ByteUtils.intToBytes(i, bo);
            putBytes(bytes);
            return this;
        }

        public BytesWriter putUInt(long l) {
            return putUInt(l, DEFAULT_BYTE_ORDER);
        }

        public BytesWriter putUInt(long l, ByteOrder bo) {
            byte[] bytes = ByteUtils.uIntToBytes(l, bo);
            putBytes(bytes);
            return this;
        }

        public BytesWriter putLong(long l) {
            return putLong(l, DEFAULT_BYTE_ORDER);
        }

        public BytesWriter putLong(long l, ByteOrder bo) {
            byte[] bytes = ByteUtils.longToBytes(l, bo);
            putBytes(bytes);
            return this;
        }

        public byte[] toBytes() {
            byte[] bytes = new byte[totalLength];
            int destPos = 0;
            for (byte[] bytes1 : bytesList) {
                System.arraycopy(bytes1, 0, bytes, destPos, bytes1.length);
                destPos = destPos + bytes1.length;
            }
            return bytes;
        }
    }

    private static int hexCharToInt(char ch) {
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
}
