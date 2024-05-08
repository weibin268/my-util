package com.zhuang.util.javacv;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import java.io.File;

public class FrameGrabberUtils {

    public static void grab(String streamUrl, String outputFilePath, int durationSeconds) {
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(streamUrl);
            grabber.setOption("rtsp_transport", "tcp");
            // 开始连接RTSP服务器
            grabber.start();
            File outputFile = new File(outputFilePath);
            // 如果已存在相同文件，则删除该文件
            if (outputFile.exists()) {
                outputFile.delete();
            }
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFilePath, grabber.getImageWidth(), grabber.getImageHeight());
            recorder.setAudioChannels(1);
            recorder.setVideoQuality(0);
            recorder.setFrameRate(25);
            recorder.setVideoBitrate(900 * 1024);
            recorder.setFormat("mp4");
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            // 开始录制视频帧到文件
            recorder.start();
            Frame frame;
            long startTime = System.currentTimeMillis();
            while ((frame = grabber.grab()) != null && (System.currentTimeMillis() - startTime) / 1000 < durationSeconds) {
                // 将每一帧录制到文件
                recorder.record(frame);
            }
            // 停止录制视频帧到文件
            recorder.close();
            // 停止连接RTSP服务器
            grabber.close();

        } catch (Exception ex) {
            throw new RuntimeException("FrameGrabberUtils.grab fail! -> msg=" + ex.getMessage(), ex);
        }
    }
}
