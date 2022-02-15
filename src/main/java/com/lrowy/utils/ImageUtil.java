package com.lrowy.utils;

import net.sf.image4j.codec.bmp.BMPDecoder;
import net.sf.image4j.codec.ico.ICODecoder;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Objects;

public class ImageUtil {
    public static BufferedImage readImage(MultipartFile file) throws IOException {
        if (Objects.equals(file.getContentType(), "image/x-icon")) {
            List<BufferedImage> images = ICODecoder.read(file.getInputStream());
            int index = 0, maxSize = 0;
            for ( int i = 0; i < images.size(); i++) {
                if (images.get(i).getWidth() >= maxSize) {
                    index = i;
                    maxSize = images.get(i).getWidth();
                }
            }
            return images.get(index);
        } else if (Objects.equals(file.getContentType(), "application/x-bmp")) {
            return BMPDecoder.read(file.getInputStream());
        } else {
            return ImageIO.read(file.getInputStream());
        }
    }

    public static BufferedImage readImage(String format, InputStream inputStream) throws IOException {
        if (format.equals("ico")) {
            List<BufferedImage> images = ICODecoder.read(inputStream);
            int index = 0, maxSize = 0;
            for ( int i = 0; i < images.size(); i++) {
                if (images.get(i).getWidth() >= maxSize) {
                    index = i;
                    maxSize = images.get(i).getWidth();
                }
            }
            return images.get(index);
        } else if (format.equals("bmp")) {
            return BMPDecoder.read(inputStream);
        } else {
            return ImageIO.read(inputStream);
        }
    }

    public static void writeImage(BufferedImage image, String format, String path) throws IOException {
        ImageIO.write(image, format, new File(path));
    }

    public static BufferedImage imageTailor(BufferedImage image, float ratio) {
        int x, y, w = image.getWidth(), h = image.getHeight();
        float r = (float) w / (float) h;
        if (r >= ratio) {
            y = 0;
            x = (w - h) / 2;
            w = h;
        } else {
            x = 0;
            y = (h - w) / 2;
            h = w;
        }

        return image.getSubimage(x, y, w, h);
    }

    public static String encodeImageToBase64 (BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return "data:image/png;base64," + Base64.encodeBase64String(outputStream.toByteArray());
    }
}
