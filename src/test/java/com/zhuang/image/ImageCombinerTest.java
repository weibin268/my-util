package com.zhuang.image;

import com.freewayso.image.combiner.ImageCombiner;
import com.freewayso.image.combiner.enums.OutputFormat;
import org.junit.Test;

public class ImageCombinerTest {

    @Test
    public void test() throws Exception {
        //合成器（指定背景图和输出格式，整个图片的宽高和相关计算依赖于背景图，所以背景图的大小是个基准）
        ImageCombiner combiner = new ImageCombiner("file:///Users/zhuang/Desktop/in.png", OutputFormat.JPG);

        //加图片元素
        //combiner.addImageElement("http://xxx.com/image/product.png", 0, 300);

        //加文本元素
        combiner.addTextElement("周末大放送", 10000, 100, 960)
                .setAutoFitWidth(500);

        //执行图片合并
        combiner.combine();

        //可以获取流（并上传oss等）
        //InputStream is = combiner.getCombinedImageStream();

        //也可以保存到本地
        combiner.save("/Users/zhuang/Desktop/out.png");
    }
}
