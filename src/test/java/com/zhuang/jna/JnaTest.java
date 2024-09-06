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
        boolean b = Kernel32.INSTANCE.DeleteFileA("d:\\tmp\\c\\test.so");
        System.out.println(b);
    }

    public interface Kernel32 extends Library {
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);
        boolean DeleteFileA(String fileName);
    }

    @Test
    public void AdcpToolForWin() {
        System.setProperty("jna.library.path", "D:\\zhuang\\doc\\ADCP\\C++\\Debug");
        PointerByReference b = AdcpToolForWin.INSTANCE.AdcpToolRequest("AutoRating", " {\n" +
                "            velocity:[], \n" +
                "            velocityOnline:[], \n" +
                "            waterOnline:[], \n" +
                "            model:1\n" +
                "        }");
        System.out.println(b.getPointer().getString(0, "GBK"));
    }

    public interface AdcpToolForWin extends Library {
        AdcpToolForWin INSTANCE =  Native.load("AdcpToolForWin", AdcpToolForWin.class);
        PointerByReference AdcpToolRequest(String method, String jsonArgs);
    }
}


