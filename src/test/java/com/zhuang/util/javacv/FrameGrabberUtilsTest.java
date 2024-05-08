package com.zhuang.util.javacv;

import org.junit.Test;

public class FrameGrabberUtilsTest {
    @Test
    public void test() {
        FrameGrabberUtils.grab("rtmp://120.238.22.9:1935/live/openUrl/XfoWJ56", "d:\\tmp\\test.mp4", 10);
    }
}
