//package com.zhuang.javacv;
//
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.Frame;
//import org.junit.Test;
//
//import java.io.File;
//
//public class FFmpegFrameGrabberTest {
//
//    @Test
//    public void test() throws FFmpegFrameGrabber.Exception, FFmpegFrameRecorder.Exception {
//        String filePath = "";
//        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("");
//        grabber.setOption("rtsp_transport", "tcp");
//        grabber.start(); // 开始连接RTSP服务器
//        File outputFile = new File(filePath);
//        if (outputFile.exists()) {
//            outputFile.delete(); // 如果已存在相同文件，则删除该文件
//        }
//        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(filePath, grabber.getImageWidth(), grabber.getImageHeight());
//        recorder.setAudioChannels(1);
//        recorder.setVideoQuality(0);
//        recorder.setFrameRate(25);
//        recorder.setFormat("mp4");
//        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//        recorder.start(); // 开始录制视频帧到文件
//        Frame frame;
//        while ((frame = grabber.grab()) != null) {
//            recorder.record(frame); // 将每一帧录制到文件
//        }
//        recorder.stop(); // 停止录制视频帧到文件
//        grabber.stop(); // 停止连接RTSP服务器
//    }
//}
