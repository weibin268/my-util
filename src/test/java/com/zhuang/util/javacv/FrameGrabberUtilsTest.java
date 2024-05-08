package com.zhuang.util.javacv;

import org.junit.Test;

public class FrameGrabberUtilsTest {
    @Test
    public void test() {
        FrameGrabberUtils.grab("rtsp://120.238.22.9:554/openUrl/vQejs6k", "d:\\tmp\\test.mp4", 10);
    }
}
