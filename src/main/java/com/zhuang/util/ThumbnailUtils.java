package com.zhuang.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 缩略图工具类
 */
public class ThumbnailUtils {

    public static void setScale(InputStream inputStream, OutputStream outputStream, double scale) {
        setScaleAndQuality(inputStream, outputStream, scale, 1);
    }

    public static void setQuality(InputStream inputStream, OutputStream outputStream, double quality) {
        setScaleAndQuality(inputStream, outputStream, 1, quality);
    }

    public static void setScaleAndQuality(InputStream inputStream, OutputStream outputStream, double scale, double quality) {
        try {
            Thumbnails.of(inputStream).scale(scale).outputQuality(quality).toOutputStream(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
