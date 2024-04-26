package com.zhuang.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import org.junit.Test;

public class JnaTest {
    @Test
    public void DeleteFileA() {
        //System.setProperty("jna.library.path", "d:\\temp\\c");
        Kernel32 kernel32 = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
        boolean b = kernel32.DeleteFileA("d:\\tmp\\c\\test.so");
        System.out.println(b);
    }

    public interface Kernel32 extends Library {
        boolean DeleteFileA(String fileName);
    }
}
