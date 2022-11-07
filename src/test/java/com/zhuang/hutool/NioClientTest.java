package com.zhuang.hutool;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.socket.nio.NioClient;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class NioClientTest {

    @Test
    public void test() {

        NioClient nioClient = new NioClient(new InetSocketAddress("127.0.0.1", 2222));
        nioClient.setChannelHandler(socketChannel -> {
            ByteBuffer buff = ByteBuffer.allocate(100);
            socketChannel.read(buff);
            buff.flip();
            System.out.println(buff);
        });
        nioClient.listen();
        ByteBuffer allocate = ByteBuffer.allocate(1);
        allocate.put((byte) 11);
        allocate.compact();
        nioClient.write(allocate);

        ThreadUtil.sleep(5, TimeUnit.SECONDS);
    }

}
