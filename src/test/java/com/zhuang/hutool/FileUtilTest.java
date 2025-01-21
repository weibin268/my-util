package com.zhuang.hutool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuang.util.ByteUtils;
import org.junit.Test;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Base64;
import java.util.List;

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

    @Test
    public void pack(){
        String s = FileUtil.readString("D:\\zhuang\\git\\my-util\\src\\test\\resources\\txt\\pack_msg.txt", "UTF-8");
        JSONObject jsonObject = JSON.parseObject(s);
        s = jsonObject.getJSONObject("data").getJSONObject("bin").getString("value");
        s = ByteUtils.bytesToHex(Base64.getDecoder().decode(s));

        //System.out.println("-----------------------------------------------");
        List<String> a = FileUtil.readLines("D:\\zhuang\\git\\my-util\\src\\test\\resources\\txt\\pack_sub.txt", "UTF-8");
        a = CollUtil.reverse(a);
        int i = 1;
        for (String string : a) {
            int preLength = "7E7E0166202500321234F6021F1605F05F".length();
            if (i == 1) {
                preLength = "7E7E0166202500321234F6041D1605F001E865250119123009F1F166202500324BF0F02501191230FF25F6".length();
            }
            string = string.substring(preLength, string.length() - 6);
            System.out.println(string);
            if(!s.contains(string)){
                System.out.println(s.contains(string));
            }
            s = s.replace(string, "("+i+")");
            i++;
        }
        System.out.println("sss-> " + s);
    }
}
