package com.zhuang.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageUtilsTest {

    @Test
    public void get(){
        String base64ByUrl = ImageUtils.getBase64ByUrl("https://fastdfs-gateway.ys7.com/c9e5/1/capture/003j0m5Qee8XBsSkx180PrX1M9AE3sC.jpg?Expires=1684201112&OSSAccessKeyId=LTAIzI38nEHqg64n&Signature=dqM6sH9Gu8sEoUMwbeVDeb7EcQ4%3D");
        System.out.println(base64ByUrl);
    }

}
