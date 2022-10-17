package com.zhuang.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtils {

    public static float getFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static byte[] getBytes(float value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putFloat(value);
        return byteBuffer.array();
    }

}
