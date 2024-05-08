package com.zhuang.util.javacv;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import java.io.File;

@Slf4j
public class FrameGrabberUtils {

    public static String grab(String videoUrl, String outputFile, int durationSeconds) {
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoUrl);
            grabber.setOption("rtsp_transport", "tcp");
            // 开始连接RTSP服务器
            grabber.start();
            File of = new File(outputFile);
            // 如果已存在相同文件，则删除该文件
            if (of.exists()) {
                of.delete();
            }
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, grabber.getImageWidth(), grabber.getImageHeight());
            recorder.setAudioChannels(1);
            recorder.setVideoQuality(0);
            recorder.setFrameRate(25);
            recorder.setVideoBitrate(900 * 1024);
            recorder.setFormat("mp4");
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            // 开始录制视频帧到文件
            recorder.start();
            log.info("begin grab video -> videoUrl={}, outputFile={}", videoUrl, outputFile);
            long endTime = grabber.getTimestamp() + durationSeconds * 1000000;
            Frame frame;
            while ((frame = grabber.grab()) != null && grabber.getTimestamp() < endTime) {
                // 将每一帧录制到文件
                recorder.record(frame);
            }
            log.info("end grab video -> videoUrl={}, outputFile={}", videoUrl, outputFile);
            // 停止录制视频帧到文件
            recorder.close();
            // 停止连接RTSP服务器
            grabber.close();
            return null;

        } catch (Exception ex) {
            log.error(StrUtil.format("FrameGrabberUtils.grab fail! -> videoUrl={}, outputFile={}, msg={}", videoUrl, outputFile, ex.getMessage()), ex);
            return ex.getMessage();
        }
    }
}
