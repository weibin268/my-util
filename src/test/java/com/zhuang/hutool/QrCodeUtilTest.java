package com.zhuang.hutool;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.junit.Test;

public class QrCodeUtilTest {

    @Test
    public void test() {
        String result = QrCodeUtil.generateAsBase64("zwb", new QrConfig(), ImgUtil.IMAGE_TYPE_PNG);
        System.out.println(result);
    }

}
