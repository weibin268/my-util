package com.zhuang.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import com.zhuang.util.ByteUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

    @Test
    public void AdcpToolForWin() {
        System.setProperty("jna.library.path", "D:\\zhuang\\doc\\ADCP\\C++\\Debug");
        AdcpToolForWin kernel32 = (AdcpToolForWin) Native.loadLibrary("AdcpToolForWin", AdcpToolForWin.class);
        PointerByReference b = kernel32.AdcpToolRequest("AutoRating", " {\n" +
                "            velocity:[], \n" +
                "            velocityOnline:[], \n" +
                "            waterOnline:[], \n" +
                "            model:1\n" +
                "        }");
        System.out.println(b.getPointer().getString(0, "GBK"));
    }

    public interface AdcpToolForWin extends Library {
        PointerByReference AdcpToolRequest(String method, String jsonArgs);
    }
}


