package com.lrowy.service;

import com.lrowy.pojo.common.http.HttpResult;
import com.lrowy.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${web.upload-path}")
    private String uploadPath;
    @Resource
    private HttpAPIService httpAPIService;

    private void saveAvatar(int id, BufferedImage image) throws IOException {
        String directory = "avatar/";
        File folder = new File(uploadPath + directory);
        if (!folder.exists()) if (!folder.mkdirs()) return;
        if (image != null) {
            image = ImageUtil.imageTailor(image, 1.0f);
            ImageUtil.writeImage(image, "png", uploadPath + directory + id + ".png");
        }
    }

    public void saveAvatar(int id, MultipartFile file) throws IOException {
        saveAvatar(id, ImageUtil.readImage(file));
    }

    public void saveAvatar(int id, String url) throws Exception {
        HttpResult hr = httpAPIService.doGet(url);
        BufferedImage image = ImageUtil.readImage(url.substring(url.lastIndexOf(".") + 1), hr.getEntityContent());
        saveAvatar(id, image);
    }

    private String saveImage(MultipartFile file, String directory) {
        long size = file.getSize() / 1024;
        if (size > 1024) {
            return "{\"error\":{\"message\":\"上传的文件不应大于1M。\"}}";
        } else {
            File folder = new File(uploadPath + directory);
            if (!folder.exists()) if (!folder.mkdirs()) return "{\"error\":{\"message\":\"文件创建失败。\"}}";
            try {
                BufferedImage image = ImageUtil.readImage(file);
                String uuid = UUID.randomUUID().toString().replace("-", "");
                ImageUtil.writeImage(image, "png", uploadPath + directory + uuid + ".png");
                return "{\"uploaded\":1,\"url\":\"" + "/upload/" + directory + uuid + ".png" + "\"}";
            } catch (IOException e) {
                e.printStackTrace();
                return "{\"error\":{\"message\":\"图片读取失败。\"}}";
            }
        }
    }

    public String saveCommentImage(MultipartFile file) {
        return saveImage(file, "comment/");
    }

    public String saveArticleImage(MultipartFile file) {
        return saveImage(file, "article/");
    }
}
