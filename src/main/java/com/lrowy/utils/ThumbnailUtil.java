package com.lrowy.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

// Thumbnail压缩图片
// 参考https://www.cnblogs.com/linkstar/p/7412012.html
// 官网http://code.google.com/p/thumbnailator/
// github地址https://github.com/coobird/thumbnailator
public class ThumbnailUtil {
    public static void CompressPicture(String filePathName, String thumbnailFilePathName, float level) throws IOException {
        Thumbnails.of(filePathName).scale(1f).outputQuality(level).toFile(thumbnailFilePathName);
    }
}
