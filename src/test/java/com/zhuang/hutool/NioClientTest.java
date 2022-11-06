package com.zhuang.hutool;

import cn.hutool.socket.nio.NioClient;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class NioClientTest {

    @Test
    public void test() {

        NioClient nioClient = new NioClient(new InetSocketAddress("127.0.0.1", 1111));
        nioClient.listen();
        nioClient.setChannelHandler(socketChannel -> {
            ByteBuffer buff = ByteBuffer.allocate(100);
            socketChannel.read(buff);
            buff.flip();
            System.out.println(buff);
        });

    }

}
