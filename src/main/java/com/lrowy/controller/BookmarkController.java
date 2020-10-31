package com.lrowy.controller;

import com.lrowy.dao.BookmarkDao;
import com.lrowy.pojo.user.User;
import com.lrowy.pojo.bookmark.Bookmark;
import com.lrowy.pojo.bookmark.BookmarkResult;
import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.common.response.BaseResponse;
import com.lrowy.service.BookmarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BookmarkController extends BaseController {
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private BookmarkDao bookmarkDao;

    @RequestMapping("/bookmark")
    public String index(Model model) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);

        BookmarkResult br = new BookmarkResult();
        br.setBookmarks(bookmarkDao.findBookmark());
        br.setBookmarkCategories(bookmarkDao.findBookmarkCategory());
        model.addAttribute("bookmarkResult", br);
        return "/bookmark";
    }

    @RequestMapping(value = "/bookmark/init", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<BookmarkResult> init() {
        BaseResponse<BookmarkResult> res = new BaseResponse<>();
        BookmarkResult br = new BookmarkResult();
        br.setBookmarks(bookmarkDao.findBookmark());
        br.setBookmarkCategories(bookmarkDao.findBookmarkCategory());
        res.setResult(br);
        return res;
    }

    @RequestMapping(value = "/bookmark/findById", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Bookmark> findById(Integer bookmarkId) {
        BaseResponse<Bookmark> br = new BaseResponse<>();
        if (bookmarkId == null || bookmarkId == 0) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
            return br;
        }
        Bookmark bookmark = bookmarkDao.findBookmarkByBookmarkId(bookmarkId);
        if (bookmark == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
        } else {
            br.setResult(bookmark);
        }
        return br;
    }

    @RequestMapping(value = "/bookmark/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Bookmark> add(@ModelAttribute Bookmark bookmark) {
        return bookmarkService.init(bookmark);
    }

    @RequestMapping(value = "/bookmark/refresh", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Bookmark> refresh(int bookmarkId) {
        return bookmarkService.refresh(bookmarkId);
    }

    @RequestMapping(value = "/bookmark/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> update(@ModelAttribute Bookmark bookmark) {
        return bookmarkService.update(bookmark);
    }

    @RequestMapping(value = "/bookmark/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> delete(int bookmarkId) {
        return bookmarkService.delete(bookmarkId);
    }

    @RequestMapping(value = "/bookmark/uploadFavicon", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Bookmark> uploadFavicon(int bookmarkId, MultipartFile file) {
        return bookmarkService.uploadFavicon(bookmarkId, file);
    }

    @RequestMapping(value = "/bookmark/changeFavicon", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Bookmark> changeFavicon(int bookmarkId, String faviconUrl) {
        return bookmarkService.changeFavicon(bookmarkId, faviconUrl);
    }
}
