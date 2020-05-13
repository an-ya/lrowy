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
import java.io.InputStream;
import java.util.Map;

@Service
public class ImageService {
    @Value("${web.upload-path}")
    private String uploadPath;
    @Resource
    private HttpAPIService httpAPIService;

    private void saveAvatar(BufferedImage image, int id) throws IOException {
        String directory = "avatar/";
        File folder = new File(uploadPath + directory);
        if (!folder.exists()) if (!folder.mkdirs()) return;
        if (image != null) {
            image = ImageUtil.imageTailor(image, 1.0f);
            ImageUtil.write(image, "png", uploadPath + directory + id + ".png");
        }
    }

    public void saveAvatar(MultipartFile file, int id) throws IOException {
        saveAvatar(ImageUtil.readMultipartFile(file), id);
    }

    void saveAvatar(String url, int id) throws Exception {
        HttpResult hr;
        hr = httpAPIService.doGet(url, false);
        if (hr.getCode() == 200) {
            InputStream inputStream  = hr.getEntityContent();
            String suffix =  url.substring(url.lastIndexOf(".") + 1);
            BufferedImage image = ImageUtil.readImageStream(suffix, inputStream);
            saveAvatar(image, id);
        }
    }
}
