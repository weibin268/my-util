package com.zhuang.util.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class DemoWebSocketClient extends WebSocketClient {

    public DemoWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("websocket连接成功");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("收到消息：" + s);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/Volumes/HD2/Temp/test.mp4", true);
            fileOutputStream.write(s.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("websocket连接关闭");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("websocket连接错误 -> " + ExceptionUtil.stacktraceToString(e));
    }
}
