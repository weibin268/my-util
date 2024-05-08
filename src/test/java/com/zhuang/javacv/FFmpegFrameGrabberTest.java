package com.zhuang.javacv;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.junit.Test;

import java.io.File;

public class FFmpegFrameGrabberTest {

    @Test
    public void test() throws FrameGrabber.Exception, FrameRecorder.Exception, InterruptedException {
        String filePath = "d:\\tmp\\test.mp4";
        int maxDurationSeconds = 30;
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("rtsp://120.238.22.9:554/openUrl/E99OI1O");
        grabber.setOption("rtsp_transport", "tcp");
        // 开始连接RTSP服务器
        grabber.start();
        File outputFile = new File(filePath);
        // 如果已存在相同文件，则删除该文件
        if (outputFile.exists()) {
            outputFile.delete();
        }
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(filePath, grabber.getImageWidth(), grabber.getImageHeight());
        recorder.setAudioChannels(1);
        recorder.setVideoQuality(0);
        recorder.setFrameRate(grabber.getFrameRate());
        //recorder.setVideoBitrate(grabber.getVideoBitrate());
        recorder.setFormat("mp4");
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        // 开始录制视频帧到文件
        recorder.start();
        Frame frame;
        long startTime = System.currentTimeMillis();
        while ((frame = grabber.grab()) != null && (System.currentTimeMillis() - startTime) / 1000 < maxDurationSeconds) {
            // 将每一帧录制到文件
            recorder.record(frame);
        }
        // 停止录制视频帧到文件
        recorder.close();
        // 停止连接RTSP服务器
        grabber.close();
    }
}
