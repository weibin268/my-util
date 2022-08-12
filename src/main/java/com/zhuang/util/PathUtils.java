package com.zhuang.util;

import java.nio.file.Paths;

public class PathUtils {

    public static String combine(String first, String... more) {
        return Paths.get(first, more).toString();
    }
    
}
