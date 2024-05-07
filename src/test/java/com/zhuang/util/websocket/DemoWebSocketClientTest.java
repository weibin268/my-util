package com.zhuang.util.websocket;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

public class DemoWebSocketClientTest {

    @Test
    public void test() throws URISyntaxException, InterruptedException {
        DemoWebSocketClient client = new DemoWebSocketClient(new URI("ws://120.236.148.68:559/openUrl/8qloNyM"));
        client.connect();
        Thread.sleep(1000 * 10);
        client.close();
    }

}