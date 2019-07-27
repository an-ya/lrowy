package com.lrowy.service;

import com.jhlabs.image.GaussianFilter;
import com.lrowy.pojo.Bookmark;
import com.lrowy.pojo.common.http.HttpResult;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import net.sf.image4j.codec.ico.ICODecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class FaviconService {
    @Value("${web.upload-path}")
    private String uploadPath;

    @Resource
    private HttpAPIService httpAPIService;

    public Bookmark getFaviconUrl(Bookmark bookmark) {
        HttpResult hr;
        try {
            URL url = new URL(bookmark.getUrl());
            StringBuilder BaseUrl = new StringBuilder();
            BaseUrl.append(url.getProtocol());
            BaseUrl.append("://");
            BaseUrl.append(url.getHost());
            if (url.getPort() > 0) {
                BaseUrl.append(":");
                BaseUrl.append(url.getPort());
            }

            StringBuilder faviconUrl = new StringBuilder();
            try {
                hr = httpAPIService.doGet(BaseUrl.toString());
                Document document = Jsoup.parse(hr.getEntityString());
                Elements head = document.head().children();
                String rel;
                for (Element e : head) {
                    rel = e.attr("rel");
                    if (e.tagName().equals("link") && (rel.equalsIgnoreCase("shortcut icon") || rel.equalsIgnoreCase("icon"))) {
                        String href = e.attr("href");
                        if (!Pattern.matches("(http://|https://).*", href)) {
                            if (Pattern.matches("//.*", href)) {
                                faviconUrl.append(url.getProtocol());
                                faviconUrl.append(":");
                            } else if (Pattern.matches("/.*", href)) {
                                faviconUrl.append(BaseUrl);
                            } else {
                                faviconUrl.append(BaseUrl);
                                faviconUrl.append("/");
                            }
                        }
                        faviconUrl.append(href);
                        break;
                    }
                }
                hr = httpAPIService.doGet(faviconUrl.toString());
                String suffix = faviconUrl.substring(faviconUrl.lastIndexOf(".") + 1);
                System.out.println(faviconUrl);
                InputStream inputStream  = hr.getEntityContent();
                BufferedImage image;
                if (suffix.equals("ico")) {
                    List<BufferedImage> images = ICODecoder.read(inputStream);
                    int index = 0;
                    int maxSize = 0;
                    for ( int i = 0; i < images.size(); i++) {
                        if (images.get(i).getWidth() >= maxSize) {
                            index = i;
                            maxSize = images.get(i).getWidth();
                        }
                    }
                    image = images.get(index);
                } else {
                    image = ImageIO.read(inputStream);
                }
                String directory = "favicon/";
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String iconName = uuid + "-favicon.png";
                String blurIconName = uuid + "-favicon-blur.png";
                File folder = new File(uploadPath + directory);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                ImageIO.write(image, "png", new File(uploadPath + directory + iconName));

                BufferedImage ScaleImage = new BufferedImage(180, 180, BufferedImage.TYPE_INT_RGB);
                Graphics g = ScaleImage.getGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0,0,180,180);
                g.drawImage(image, 40, 40, 100, 100, null);

                BufferedImage blurImage = new BufferedImage(ScaleImage.getWidth(), ScaleImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                GaussianFilter gaussianFilter = new GaussianFilter();
                gaussianFilter.setRadius(40);
                gaussianFilter.filter(ScaleImage, blurImage);

                ImageIO.write(blurImage.getSubimage(40, 40, 100, 100), "png", new File(uploadPath + directory + blurIconName));
                bookmark.setFaviconUrl("/upload/" + directory + iconName);
                bookmark.setFaviconOriginalUrl(faviconUrl.toString());
                bookmark.setFaviconBlurUrl("/upload/" + directory + blurIconName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return bookmark;
    }
}
