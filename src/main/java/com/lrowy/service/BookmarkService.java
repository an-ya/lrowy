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

    private boolean deleteFaviconFile(String path) {
        String filename = uploadPath + path.substring(path.lastIndexOf("/upload/") + 8);
        File file = new File(filename);
        if (file.exists()) return file.delete();
        return false;
    }

    private void saveFavicon(Favicon favicon, String type, InputStream inputStream) throws IOException {
        BufferedImage image = ImageUtil.readImageStream(type, inputStream);

        String directory = "favicon/";
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String iconName = uuid + "-favicon.png";
        String blurIconName = uuid + "-favicon-blur.png";
        File folder = new File(uploadPath + directory);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        ImageIO.write(image, "png", new File(uploadPath + directory + iconName));
        ImageIO.write(ImageUtil.customGaussianBlur(image), "png", new File(uploadPath + directory + blurIconName));
        favicon.setFaviconUrl("/upload/" + directory + iconName);
        favicon.setFaviconBlurUrl("/upload/" + directory + blurIconName);
    }

    public BaseResponse<Bookmark> init(Bookmark bookmark) {
        HttpResult hr;
        BaseResponse<Bookmark> br = new BaseResponse<>();

        try {
            String faviconUrl = "";
            String url = bookmark.getUrl();
            String BaseUrl = UrlUtil.getBaseUrl(url);
            boolean extraNetFlag = bookmark.getExtraNetFlag() == 1;
            Favicon favicon = bookmarkDao.findFaviconByDomain(UrlUtil.getDomainName(url));

            hr = httpAPIService.doGet(url, extraNetFlag);
            if (hr.getCode() != 200) {
                br.setCode(SystemConstant.HTTPCLIENT_ERROR.getCode());
                br.setMsg(SystemConstant.HTTPCLIENT_ERROR.getDescription() + ",错误代码:" + hr.getCode() + ",发生错误的url:" + url);
            } else {
                Document document = Jsoup.parse(hr.getEntityString(), hr.getCharset());
                Elements head = document.head().children();
                String rel;
                for (Element e : head) {
                    if (e.tagName().equals("title") && e.text().length() > 0) {
                        bookmark.setTitle(e.text());
                    }
                }
                if (favicon == null) {
                    favicon = new Favicon();
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
                        saveFavicon(favicon, suffix, inputStream);
                        favicon.setFaviconOriginalUrl(faviconUrl);
                    }

                    favicon.setDomain(UrlUtil.getDomainName(url));
                    favicon.setTopDomain(UrlUtil.getTopDomainName(url));
                    bookmarkDao.saveFavicon(favicon);
                }
            }

            bookmark.setBaseUrl(BaseUrl);
            bookmark.setCreateDate(new Date());
            bookmarkDao.saveBookmark(bookmark);
            bookmark.setFavicon(favicon);
            bookmarkDao.saveBookmarkFavicon(bookmark);
            br.setResult(bookmark);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            br.setInfo(SystemConstant.NETWORK_ERROR);
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

    public BaseResponse<Bookmark> uploadFavicon(Integer bookmarkId, MultipartFile file) {
        BaseResponse<Bookmark> br = new BaseResponse<>();
        if (bookmarkId == null || file == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
            return br;
        }
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        try {
            Favicon favicon = bookmarkDao.findFaviconByBookmarkId(bookmarkId);
            saveFavicon(favicon, suffix, file.getInputStream());
            bookmarkDao.saveFavicon(favicon);
            br.setResult(bookmarkDao.findBookmarkByBookmarkId(bookmarkId));
        } catch (IOException e) {
            e.printStackTrace();
            br.setCode(SystemConstant.SYSTEM_ERROR.getCode());
            br.setMsg(e.toString());
        }
        return br;
    }

    public BaseResponse<String> delete(Integer bookmarkId) {
        BaseResponse<String> br = new BaseResponse<>();
        if (bookmarkId == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
            return br;
        }
        Favicon favicon = bookmarkDao.findFaviconByBookmarkId(bookmarkId);
        if (favicon == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
            return br;
        }
        bookmarkDao.deleteBookmark(bookmarkId);
        bookmarkDao.deleteBookmarkFavicon(bookmarkId);
        if (bookmarkDao.deleteFavicon(favicon.getFaviconId()) > 0) {
            if (!deleteFaviconFile(favicon.getFaviconUrl()) || !deleteFaviconFile(favicon.getFaviconBlurUrl())) br.setInfo(SystemConstant.DELETEFILE_ERROR);
        }
        return br;
    }
}