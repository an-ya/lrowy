package com.lrowy.service;

import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.dao.BookmarkDao;
import com.lrowy.pojo.bookmark.Bookmark;
import com.lrowy.pojo.common.html.HTMLParser;
import com.lrowy.pojo.common.http.HttpResult;
import com.lrowy.pojo.common.response.BaseResponse;
import com.lrowy.utils.ImageUtil;
import com.lrowy.utils.UrlUtil;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

@Service
public class BookmarkService {
    @Value("${web.upload-path}")
    private String uploadPath;
    @Autowired
    private BookmarkDao bookmarkDao;
    @Resource
    private HttpAPIService httpAPIService;

    private String getFavicon(MultipartFile file) throws IOException {
        BufferedImage image = ImageUtil.readImage(file);
        return ImageUtil.encodeImageToBase64(image);
    }

    private String getFaviconByUrl(String url) throws Exception {
        HttpResult hr = httpAPIService.doGet(url);
        BufferedImage image = ImageUtil.readImage(url.substring(url.lastIndexOf(".") + 1), hr.getEntityContent());
        return ImageUtil.encodeImageToBase64(image);
    }

    public BaseResponse<Bookmark> init(Bookmark bookmark) {
        BaseResponse<Bookmark> br = new BaseResponse<>();
        String url = bookmark.getUrl();
        if (url == null || url.equals("")) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
        } else if (!UrlUtil.isUrl(url)) {
            br.setInfo(SystemConstant.MALFORMED_URL);
        } else {
            try {
                HttpResult hr = httpAPIService.doGet(url);
                HTMLParser htmlParser = new HTMLParser(hr.getEntityString(), hr.getCharset());
                bookmark.setTitle(htmlParser.getTitle());
                try {
                    String BaseUrl = UrlUtil.getBaseUrl(url);
                    String favicon = htmlParser.getFavicon();
                    String faviconUrl = UrlUtil.getFullPath(BaseUrl, favicon);
                    bookmark.setFavicon(getFaviconByUrl(faviconUrl));
                } catch (Exception e) {
                    e.printStackTrace();
                    bookmark.setFavicon("");
                    br.setCode(SystemConstant.SYSTEM_ERROR.getCode());
                    br.setMsg(e.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                bookmark.setTitle("");
                bookmark.setFavicon("");
                br.setInfo(SystemConstant.HTTPCLIENT_ERROR);
            }
            bookmark.setCreateDate(new Date());
            bookmarkDao.saveBookmark(bookmark);
        }
        return br;
    }

    public BaseResponse<Bookmark> refresh(int id) {
        BaseResponse<Bookmark> br = new BaseResponse<>();
        Bookmark bookmark = bookmarkDao.findBookmarkByBookmarkId(id);
        if (id == 0 || bookmark == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
        } else {
            String url = bookmark.getUrl();
            if (url == null || url.equals("") || !UrlUtil.isUrl(url)) {
                br.setInfo(SystemConstant.MALFORMED_URL);
            } else {
                try {
                    HttpResult hr = httpAPIService.doGet(url);
                    HTMLParser htmlParser = new HTMLParser(hr.getEntityString(), hr.getCharset());
                    bookmark.setTitle(htmlParser.getTitle());
                    bookmarkDao.updateBookmark(bookmark);
                    try {
                        String BaseUrl = UrlUtil.getBaseUrl(url);
                        String favicon = htmlParser.getFavicon();
                        String faviconUrl = UrlUtil.getFullPath(BaseUrl, favicon);
                        bookmark.setFavicon(getFaviconByUrl(faviconUrl));
                        bookmarkDao.updateFavicon(bookmark);
                    } catch (Exception e) {
                        e.printStackTrace();
                        br.setCode(SystemConstant.SYSTEM_ERROR.getCode());
                        br.setMsg(e.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    br.setInfo(SystemConstant.HTTPCLIENT_ERROR);
                }
                br.setResult(bookmark);
            }
        }
        return br;
    }

    public BaseResponse<String> update(Bookmark bookmark) {
        BaseResponse<String> br = new BaseResponse<>();
        bookmarkDao.updateBookmark(bookmark);
        return br;
    }

    public BaseResponse<String> delete(int id) {
        BaseResponse<String> br = new BaseResponse<>();
        if (id == 0) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
        } else {
            bookmarkDao.deleteBookmark(id);
        }
        return br;
    }

    public BaseResponse<Bookmark> uploadFavicon(int id, MultipartFile file) {
        BaseResponse<Bookmark> br = new BaseResponse<>();
        Bookmark bookmark = bookmarkDao.findBookmarkByBookmarkId(id);
        if (file == null || id == 0 || bookmark == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
        } else {
            try {
                bookmark.setFavicon(getFavicon(file));
                bookmarkDao.updateFavicon(bookmark);
            } catch (IOException e) {
                e.printStackTrace();
                br.setInfo(SystemConstant.IO_ERROR);
            }
        }
        return br;
    }

    public BaseResponse<Bookmark> changeFavicon(int id, String url) {
        BaseResponse<Bookmark> br = new BaseResponse<>();
        Bookmark bookmark = bookmarkDao.findBookmarkByBookmarkId(id);
        if (url == null || url.equals("") || id == 0 || bookmark == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
        } else if (!UrlUtil.isUrl(url)) {
            br.setInfo(SystemConstant.MALFORMED_URL);
        } else {
            try {
                bookmark.setFavicon(getFaviconByUrl(url));
                bookmarkDao.updateFavicon(bookmark);
            } catch (Exception e) {
                e.printStackTrace();
                br.setCode(SystemConstant.SYSTEM_ERROR.getCode());
                br.setMsg(e.toString());
            }
        }
        return br;
    }
}
