package com.zhuang.util;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class KaptchaUtilsTest {

    @Test
    public void test() throws IOException {
        DefaultKaptcha kaptcha = KaptchaUtils.getInstance();
        String text = kaptcha.createText();
        BufferedImage image = kaptcha.createImage(text);
        ImageIO.write(image, "jpg", new FileOutputStream("d:\\temp\\images\\test.jpg"));
    }

}
