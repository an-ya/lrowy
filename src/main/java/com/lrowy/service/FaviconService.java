package com.lrowy.service;

import com.lrowy.dao.BookmarkDao;
import com.lrowy.pojo.bookmark.Bookmark;
import com.lrowy.pojo.bookmark.Favicon;
import com.lrowy.pojo.common.http.HttpResult;
import com.lrowy.utils.ImageUtil;
import com.lrowy.utils.UrlUtil;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class FaviconService {
    @Value("${web.upload-path}")
    private String uploadPath;

    @Autowired
    BookmarkDao bookmarkDao;
    @Resource
    private HttpAPIService httpAPIService;

    public Bookmark getFaviconUrl(Bookmark bookmark) {
        HttpResult hr;

        try {
            String url = bookmark.getUrl();
            String faviconUrl = "";
            String BaseUrl = UrlUtil.getBaseUrl(url);

            hr = httpAPIService.doGet(url);
            Document document = Jsoup.parse(hr.getEntityString(), hr.getCharset());
            Elements head = document.head().children();
            String rel;
            for (Element e : head) {
                if (e.tagName().equals("title") && e.text().length() > 0) {
                    bookmark.setTitle(e.text());
                }
                rel = e.attr("rel");
                if (e.tagName().equals("link") && (rel.equalsIgnoreCase("shortcut icon") || rel.equalsIgnoreCase("icon"))) {
                    String href = e.attr("href");
                    faviconUrl = UrlUtil.getRescFullPath(BaseUrl, href);
                    break;
                }
            }

            if (faviconUrl.equals("")) {
                faviconUrl = BaseUrl + "/favicon.ico";
            }
            hr = httpAPIService.doGet(faviconUrl);
            InputStream inputStream  = hr.getEntityContent();
            String suffix = faviconUrl.substring(faviconUrl.lastIndexOf(".") + 1);
            BufferedImage image = ImageUtil.readImageStream(suffix, inputStream);

            String directory = "favicon/";
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String iconName = uuid + "-favicon.png";
            String blurIconName = uuid + "-favicon-blur.png";
            File folder = new File(uploadPath + directory);
            if (!folder.exists()) {
                if (!folder.mkdirs()) return bookmark;
            }

            ImageIO.write(image, "png", new File(uploadPath + directory + iconName));
            ImageIO.write(ImageUtil.customGaussianBlur(image), "png", new File(uploadPath + directory + blurIconName));

            bookmark.setBaseUrl(BaseUrl);
            bookmark.setCreateDate(new Date());
            bookmarkDao.saveBookmark(bookmark);
            Favicon favicon = new Favicon();
            favicon.setDomain(UrlUtil.getDomainName(url));
            favicon.setTopDomain(UrlUtil.getTopDomainName(url));
            favicon.setFaviconOriginalUrl(faviconUrl);
            favicon.setFaviconUrl("/upload/" + directory + iconName);
            favicon.setFaviconBlurUrl("/upload/" + directory + blurIconName);
            bookmarkDao.saveFavicon(favicon);
            bookmark.setFavicon(favicon);
            bookmarkDao.saveBookmarkFavicon(bookmark);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookmark;
    }
}
