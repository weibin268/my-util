package com.zhuang.jna;

import com.sun.jna.Native;
import org.junit.Test;

public class JnaTest {
    @Test
    public void test() {
        System.setProperty("jna.library.path", "/Users/zhuang/Documents/temp/");
        MyLib instance = (MyLib) Native.loadLibrary("test", MyLib.class);
        int add = instance.add(1, 2);
        System.out.println(add);
    }

    public interface MyLib {
        int add(int a, int b);
    }
}
