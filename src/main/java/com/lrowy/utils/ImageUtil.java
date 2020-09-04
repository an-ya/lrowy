package com.lrowy.utils;

import net.sf.image4j.codec.bmp.BMPDecoder;
import net.sf.image4j.codec.ico.ICODecoder;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Objects;

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

    public static BufferedImage readImageStream(String format, InputStream inputStream) throws IOException {
        if (format.equals("ico")) {
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
        } else if (format.equals("bmp")) {
            return BMPDecoder.read(inputStream);
        } else {
            return ImageIO.read(inputStream);
        }
    }

    public static BufferedImage readMultipartFile(MultipartFile file) throws IOException {
        if (Objects.equals(file.getContentType(), "image/x-icon")) {
            InputStreamCache(file.getInputStream());
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
                return null;
            }
        } else if (Objects.equals(file.getContentType(), "application/x-bmp")) {
            return BMPDecoder.read(file.getInputStream());
        } else {
            return ImageIO.read(file.getInputStream());
        }
    }

    public static void write(BufferedImage image, String format, String path) throws IOException {
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

    public static String encodeImgageToBase64 (BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        return "data:image/png;base64," + encoder.encode(outputStream.toByteArray());
    }
}
