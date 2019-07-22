package com.lrowy.utils;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Thumbnail压缩图片
// 参考https://www.cnblogs.com/linkstar/p/7412012.html
// 官网http://code.google.com/p/thumbnailator/
// github地址https://github.com/coobird/thumbnailator
public class ThumbnailUtil {
    public static void CompressPicture(String filePathName, String thumbnailFilePathName, float level) {
        try {
            Thumbnails.of(filePathName)
                    .scale(1f)
                    .outputQuality(level)
                    .toFile(thumbnailFilePathName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ChangeImageFormat(String filePathName, String distFilePathName) {
        BufferedImage bufferedImage;
        try {
            // read image file
            bufferedImage = ImageIO.read(new File(filePathName));
            // create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", new File(distFilePathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
