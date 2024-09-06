package com.zhuang.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import org.junit.Test;

public class DllDemoTest {

    @Test
    public void add() {
        System.setProperty("jna.library.path", "D:\\zhuang\\git\\my-cppdll-demo\\MyCppDemo\\x64\\Debug");
        int add = DllDemo.INSTANCE.add(1, 2);
        System.out.println(add);

        String concat = DllDemo.INSTANCE.concat("a", "b");
        System.out.println(concat);
    }

    public interface DllDemo extends Library {
        DllDemo INSTANCE = Native.load("DllDemo", DllDemo.class);

        int add(int a, int b);

        String concat(String a, String b);
    }
}
