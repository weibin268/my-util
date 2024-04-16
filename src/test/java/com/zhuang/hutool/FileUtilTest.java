package com.zhuang.hutool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import org.junit.Test;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtilTest {

    @Test
    public void readUtf8Lines() {
        //System.out.println(System.getProperty("user.dir"));
        FileUtil.readUtf8Lines(System.getProperty("user.dir") + "/src/test/java/com/zhuang/hutool/FileUtilTest.java").forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------");
        FileUtil.readUtf8Lines(this.getClass().getResource("/txt/test.txt")).forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------");
        FileUtil.readUtf8Lines(ResourceUtil.getResource("txt/test.txt")).forEach(System.out::println);
    }

    @Test
    public void walkFiles() {
        FileUtil.walkFiles(Paths.get("D:\\fileupload"), 5, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                System.out.println("正在访问文件 -> " + file + "");
                System.out.println("正在访问文件 -> " + attrs.lastModifiedTime() + "");
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    public void mkParentDirs(){
        FileUtil.mkParentDirs("/Users/zhuang/Documents/temp/a/b/test.txt");
    }
}
