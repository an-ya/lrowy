package com.lrowy.service;

import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.dao.BookmarkDao;
import com.lrowy.pojo.bookmark.Bookmark;
import com.lrowy.pojo.bookmark.Favicon;
import com.lrowy.pojo.common.http.HttpResult;
import com.lrowy.pojo.common.response.BaseResponse;
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
import java.util.List;
import java.util.UUID;

@Service
public class BookmarkService {
    @Value("${web.upload-path}")
    private String uploadPath;
    @Autowired
    private BookmarkDao bookmarkDao;
    @Resource
    private HttpAPIService httpAPIService;

    public BaseResponse<Bookmark> init(Bookmark bookmark) {
        HttpResult hr;
        BaseResponse<Bookmark> br = new BaseResponse<>();

        try {
            String url = bookmark.getUrl();
            String faviconUrl = "";
            String BaseUrl = UrlUtil.getBaseUrl(url);
            boolean extraNetFlag = bookmark.getExtraNetFlag() == 1;

            hr = httpAPIService.doGet(url, extraNetFlag);
            if (hr.getCode() != 200) {
                br.setCode(SystemConstant.HTTPCLIENT_ERROR.getCode());
                br.setMsg(SystemConstant.HTTPCLIENT_ERROR.getDescription() + ",错误代码:" + hr.getCode() + ",发生错误的url:" + url);
            }
            Document document = Jsoup.parse(hr.getEntityString(), hr.getCharset());
            Elements head = document.head().children();
            String rel;
            for (Element e : head) {
                if (e.tagName().equals("title") && e.text().length() > 0) {
                    bookmark.setTitle(e.text());
                }
            }

            List<Favicon> fs = bookmarkDao.findFaviconByDomain(UrlUtil.getDomainName(url));
            Favicon favicon = new Favicon();
            if (fs.size() == 0) {
                for (Element e : head) {
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
                hr = httpAPIService.doGet(faviconUrl, extraNetFlag);
                if (hr.getCode() == 200) {
                    InputStream inputStream  = hr.getEntityContent();
                    String suffix = faviconUrl.substring(faviconUrl.lastIndexOf(".") + 1);
                    BufferedImage image = ImageUtil.readImageStream(suffix, inputStream);

                    String directory = "favicon/";
                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    String iconName = uuid + "-favicon.png";
                    String blurIconName = uuid + "-favicon-blur.png";
                    File folder = new File(uploadPath + directory);
                    if (!folder.exists()) {
                        if (!folder.mkdirs()) {
                            br.setInfo(SystemConstant.MKDIR_ERROR);
                            return br;
                        }
                    }

                    ImageIO.write(image, "png", new File(uploadPath + directory + iconName));
                    ImageIO.write(ImageUtil.customGaussianBlur(image), "png", new File(uploadPath + directory + blurIconName));
                    favicon.setFaviconOriginalUrl(faviconUrl);
                    favicon.setFaviconUrl("/upload/" + directory + iconName);
                    favicon.setFaviconBlurUrl("/upload/" + directory + blurIconName);
                }

                favicon.setDomain(UrlUtil.getDomainName(url));
                favicon.setTopDomain(UrlUtil.getTopDomainName(url));
                bookmarkDao.saveFavicon(favicon);
            } else {
                favicon = fs.get(0);
            }

            bookmark.setBaseUrl(BaseUrl);
            bookmark.setCreateDate(new Date());
            bookmarkDao.saveBookmark(bookmark);
            bookmark.setFavicon(favicon);
            bookmarkDao.saveBookmarkFavicon(bookmark);
            br.setResult(bookmark);
        } catch (Exception e) {
            e.printStackTrace();
            br.setCode(SystemConstant.SYSTEM_ERROR.getCode());
            br.setMsg(e.toString());
        }

        return br;
    }

    public BaseResponse<Bookmark> update(Bookmark bookmark) {
        BaseResponse<Bookmark> br = new BaseResponse<>();
        bookmarkDao.updateBookmark(bookmark);
        br.setResult(bookmark);
        return br;
    }
}
