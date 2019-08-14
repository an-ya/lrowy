package com.lrowy.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lrowy.dao.BookmarkCategoryDao;
import com.lrowy.dao.BookmarkDao;
import com.lrowy.pojo.User;
import com.lrowy.pojo.bookmark.Bookmark;
import com.lrowy.pojo.bookmark.BookmarkResult;
import com.lrowy.pojo.common.response.BaseResponse;
import com.lrowy.service.FaviconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BookmarkController extends BaseController {
    @Autowired
    private FaviconService fs;
    @Autowired
    BookmarkDao bookmarkDao;
    @Autowired
    BookmarkCategoryDao bookmarkCategoryDao;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    @RequestMapping("/bookmark")
    public String index(Model model) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);

        BookmarkResult br = new BookmarkResult();
        br.setShortcutList(bookmarkDao.findShortcut());
        br.setBookmarkCategoryList(bookmarkCategoryDao.findBookmarkCategory());
        model.addAttribute("bookmarkResult", br);
        return "/bookmark";
    }

    @RequestMapping(value = "/bookmark/init", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<BookmarkResult> init() {
        BaseResponse<BookmarkResult> res = new BaseResponse<>();
        BookmarkResult br = new BookmarkResult();
        br.setShortcutList(bookmarkDao.findShortcut());
        br.setBookmarkCategoryList(bookmarkCategoryDao.findBookmarkCategory());
        res.setResult(br);
        return res;
    }

    @RequestMapping(value = "/bookmark/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Bookmark> add(@ModelAttribute Bookmark bookmark) {
        BaseResponse<Bookmark> br = new BaseResponse<>();
        br.setResult(fs.getFaviconUrl(bookmark));
        return br;
    }
}
