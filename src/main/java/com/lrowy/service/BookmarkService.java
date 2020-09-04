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
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookmarkService {
    @Value("${web.upload-path}")
    private String uploadPath;
    @Autowired
    private BookmarkDao bookmarkDao;
    @Resource
    private HttpAPIService httpAPIService;

    private String saveFavicon(String type, InputStream inputStream) throws IOException {
        BufferedImage image = ImageUtil.readImageStream(type, inputStream);

//        String directory = "favicon/";
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//        String iconName = uuid + "-favicon.png";
//        File folder = new File(uploadPath + directory);
//        if (!folder.exists()) if (!folder.mkdirs()) return "";
//
//        ImageIO.write(image, "png", new File(uploadPath + directory + iconName));
        return ImageUtil.encodeImgageToBase64(image);
    }

    private String getFaviconByUrl(String faviconUrl) {
        HttpResult hr;
        try {
            hr = httpAPIService.doGet(faviconUrl);
            InputStream inputStream  = hr.getEntityContent();
            String suffix = faviconUrl.substring(faviconUrl.lastIndexOf(".") + 1);
            return saveFavicon(suffix, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public BaseResponse<Bookmark> init(Bookmark bookmark) {
        BaseResponse<Bookmark> br = new BaseResponse<>();

        String url = bookmark.getUrl();
        if (url == null || url.equals("")) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
            return br;
        }

        HttpResult hr;
        String entityString;
        try {
            hr = httpAPIService.doGet(url);
            entityString = hr.getEntityString();
        } catch (Exception e) {
            bookmark.setTitle("");
            bookmark.setFavicon("");
            bookmark.setCreateDate(new Date());
            bookmarkDao.saveBookmark(bookmark);
            br.setResult(bookmark);
            e.printStackTrace();
            br.setInfo(SystemConstant.HTTPCLIENT_ERROR);
            return br;
        }

        Document document = Jsoup.parse(entityString, hr.getCharset());
        Elements head = document.head().children();
        for (Element e : head) if (e.tagName().equals("title") && e.text().length() > 0) bookmark.setTitle(e.text());
        Elements icons = new Elements();
        for (Element e : head) if (e.tagName().equals("link") && (e.attr("rel").equalsIgnoreCase("shortcut icon") || e.attr("rel").equalsIgnoreCase("icon"))) icons.add(e);

        String faviconUrl = "";
        String BaseUrl = "";
        try {
            BaseUrl = UrlUtil.getBaseUrl(url);
            boolean target = true;
            int maxSize = 0, size;
            String s;
            for (Element i : icons) {
                s = i.attr("sizes");
                if (s != null && !s.equals("")) {
                    target = false;
                    size = Integer.parseInt(s.substring(s.lastIndexOf("x") + 1));
                    if (size > maxSize) {
                        faviconUrl = UrlUtil.getRescFullPath(BaseUrl, i.attr("href"));
                        maxSize = size;
                    }
                } else {
                    if (target) faviconUrl = UrlUtil.getRescFullPath(BaseUrl, i.attr("href"));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (faviconUrl.equals("")) {
            faviconUrl = BaseUrl + "/favicon.ico";
        }

        bookmark.setFavicon(getFaviconByUrl(faviconUrl));
        bookmark.setCreateDate(new Date());
        bookmarkDao.saveBookmark(bookmark);
        br.setResult(bookmark);
        return br;
    }

    public BaseResponse<Bookmark> update(Bookmark bookmark) {
        BaseResponse<Bookmark> br = new BaseResponse<>();
        bookmarkDao.updateBookmark(bookmark);
        br.setResult(bookmark);
        return br;
    }

    public BaseResponse<Bookmark> uploadFavicon(int bookmarkId, MultipartFile file) {
        BaseResponse<Bookmark> br = new BaseResponse<>();

        Bookmark bookmark = bookmarkDao.findBookmarkByBookmarkId(bookmarkId);
        if (file == null || bookmarkId == 0 || bookmark == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
            return br;
        }

        try {
            String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String favicon = saveFavicon(suffix, file.getInputStream());
            bookmark.setFavicon(favicon);
            br.setResult(bookmark);
        } catch (IOException e) {
            e.printStackTrace();
            br.setInfo(SystemConstant.IO_ERROR);
        }
        return br;
    }

    public BaseResponse<Bookmark> changeFavicon(int bookmarkId, String faviconUrl) {
        BaseResponse<Bookmark> br = new BaseResponse<>();

        Bookmark bookmark = bookmarkDao.findBookmarkByBookmarkId(bookmarkId);
        if (faviconUrl == null || faviconUrl.equals("") || bookmarkId == 0 || bookmark == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
            return br;
        }

        try {
            HttpResult hr = httpAPIService.doGet(faviconUrl);
            try {
                InputStream inputStream = hr.getEntityContent();
                String suffix = faviconUrl.substring(faviconUrl.lastIndexOf(".") + 1);
                saveFavicon(suffix, inputStream);
                br.setResult(bookmark);
            } catch (IOException e) {
                e.printStackTrace();
                br.setInfo(SystemConstant.IO_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            br.setCode(SystemConstant.HTTPCLIENT_ERROR.getCode());
            br.setMsg(e.toString());
        }
        return br;
    }

    public BaseResponse<String> delete(Integer bookmarkId) {
        BaseResponse<String> br = new BaseResponse<>();
        if (bookmarkId == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
        } else {
            bookmarkDao.deleteBookmark(bookmarkId);
        }
        return br;
    }
}
