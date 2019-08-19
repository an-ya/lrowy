package com.lrowy.controller;

import com.lrowy.dao.BookmarkDao;
import com.lrowy.pojo.User;
import com.lrowy.pojo.bookmark.Bookmark;
import com.lrowy.pojo.bookmark.BookmarkResult;
import com.lrowy.pojo.common.response.BaseResponse;
import com.lrowy.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BookmarkController extends BaseController {
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private BookmarkDao bookmarkDao;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    @RequestMapping("/bookmark")
    public String index(Model model) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);

        BookmarkResult br = new BookmarkResult();
        br.setShortcuts(bookmarkDao.findShortcut());
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
        br.setShortcuts(bookmarkDao.findShortcut());
        br.setBookmarks(bookmarkDao.findBookmark());
        br.setBookmarkCategories(bookmarkDao.findBookmarkCategory());
        res.setResult(br);
        return res;
    }

    @RequestMapping(value = "/bookmark/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Bookmark> add(@ModelAttribute Bookmark bookmark) {
        return bookmarkService.init(bookmark);
    }

    @RequestMapping(value = "/bookmark/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Bookmark> update(@ModelAttribute Bookmark bookmark) {
        return bookmarkService.update(bookmark);
    }

    @RequestMapping(value = "/bookmark/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> delete(Integer bookmarkId) {
        return bookmarkService.delete(bookmarkId);
    }

    @RequestMapping(value = "/bookmark/testUploadFavicon", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Bookmark> testUploadFavicon(Integer bookmarkId, MultipartFile file) {
        return bookmarkService.uploadFavicon(bookmarkId, file);
    }
}
