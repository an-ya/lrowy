package com.lrowy.service;

import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.common.response.BaseResponse;
import com.lrowy.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {
    @Value("${web.upload-path}")
    private String uploadPath;

    public BaseResponse<String> saveAvatar(MultipartFile file, int id) throws IOException {
        BaseResponse<String> br = new BaseResponse<>();
        String directory = "avatar/";
        File folder = new File(uploadPath + directory);
        if (!folder.exists()) if (!folder.mkdirs()) {
            br.setInfo(SystemConstant.MKDIR_ERROR);
            return br;
        }
        BufferedImage image = ImageUtil.readMultipartFile(file);
        if (image == null) {
            br.setInfo(SystemConstant.IMAGE_TYPE_ERROR);
        } else {
            image = ImageUtil.imageTailor(image, 1.0f);
            ImageUtil.write(image, "png", uploadPath + directory + id + ".png");
        }
        return br;
    }
}
