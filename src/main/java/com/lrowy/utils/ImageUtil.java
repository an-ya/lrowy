package com.lrowy.utils;

import com.jhlabs.image.GaussianFilter;
import net.sf.image4j.codec.bmp.BMPDecoder;
import net.sf.image4j.codec.ico.ICODecoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class ImageUtil {
    private static ByteArrayOutputStream byteArrayOutputStream = null;

    private static void InputStreamCache(InputStream inputStream) {
        byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1 ) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static InputStream getInputStream() {
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public static BufferedImage readImageStream(String type, InputStream inputStream) throws IOException {
        if (type.equals("ico")) {
            InputStreamCache(inputStream);
            try {
                List<BufferedImage> images = ICODecoder.read(getInputStream());
                int index = 0;
                int maxSize = 0;
                for ( int i = 0; i < images.size(); i++) {
                    if (images.get(i).getWidth() >= maxSize) {
                        index = i;
                        maxSize = images.get(i).getWidth();
                    }
                }
                return images.get(index);
            } catch (EOFException e) {
                // 防止出现文件类型不是ico的情况
                return ImageIO.read(getInputStream());
            }
        } else if (type.equals("bmp")) {
            return BMPDecoder.read(inputStream);
        } else {
            return ImageIO.read(inputStream);
        }
    }

    public static BufferedImage customGaussianBlur(BufferedImage image) {
        BufferedImage ScaleImage = new BufferedImage(180, 180, BufferedImage.TYPE_INT_RGB);
        Graphics g = ScaleImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,180,180);
        g.drawImage(image, 40, 40, 100, 100, null);

        BufferedImage blurImage = new BufferedImage(ScaleImage.getWidth(), ScaleImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        GaussianFilter gaussianFilter = new GaussianFilter();
        gaussianFilter.setRadius(60);
        gaussianFilter.filter(ScaleImage, blurImage);

        return blurImage.getSubimage(40, 40, 100, 100);
    }
}
