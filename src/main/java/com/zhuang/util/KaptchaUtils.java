package com.zhuang.util;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import java.awt.image.BufferedImage;
import java.util.Properties;

import static com.google.code.kaptcha.Constants.*;

public class KaptchaUtils {

    public static DefaultKaptcha getInstance() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框 默认为true 我们可以自己设置yes，no
        properties.setProperty(KAPTCHA_BORDER, "yes");
        // 验证码文本字符颜色 默认为Color.BLACK
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        // 验证码图片宽度 默认为200
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");
        // 验证码图片高度 默认为50
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
        // 验证码文本字符大小 默认为40
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "38");
        // KAPTCHA_SESSION_KEY
        properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCode");
        // 验证码文本字符长度 默认为5
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        // 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
        // 验证码文本字符间距
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
        // 图片样式：水纹=com.google.code.kaptcha.impl.WaterRipple；鱼眼=com.google.code.kaptcha.impl.FishEyeGimpy；阴影=com.google.code.kaptcha.impl.ShadowGimpy；
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.zhuang.util.KaptchaUtils$NoGimpy");
        // 干扰实现类
        properties.setProperty(KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    public static class NoGimpy implements GimpyEngine {

        @Override
        public BufferedImage getDistortedImage(BufferedImage baseImage) {
            return baseImage;
        }
    }

}
