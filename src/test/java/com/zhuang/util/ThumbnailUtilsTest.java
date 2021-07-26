package com.zhuang.util;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ThumbnailUtilsTest {

    @Test
    public void setScaleAndQuality() throws FileNotFoundException {
        ThumbnailUtils.setScaleAndQuality(getClass().getResourceAsStream("/images/test.jpg"), new FileOutputStream("D:\\temp\\images\\test.jpg"), 0.5, 0.5);
    }

}
