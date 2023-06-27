package com.zhuang.hutool;

import cn.hutool.core.img.FontUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.IoUtil;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImgUtilTest {

    @Test
    public void test() throws FileNotFoundException {
        String inputFileName = "/Users/zhuang/Desktop/IMG_20211006_192434 2.jpg";
        String outputFileName = "/Users/zhuang/Desktop/IMG_20211006_192434 2_out.jpg";
        BufferedImage inputImage = ImgUtil.toImage(IoUtil.readBytes(new FileInputStream(inputFileName)));
        Double fontHeight = inputImage.getHeight() * 0.045;
        Font sansSerifFont = FontUtil.createSansSerifFont(fontHeight.intValue());
        int x = (inputImage.getWidth() / 2) - Double.valueOf(fontHeight * 1.5).intValue();
        int y = (inputImage.getHeight() / 2) - Double.valueOf(fontHeight * 1.5).intValue();
        ImgUtil.pressText(inputImage, new FileOutputStream(outputFileName), "星期一", Color.green, sansSerifFont, -x, -y, 1f);
    }

}
