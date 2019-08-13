package com.lrowy.controller;

import com.lrowy.dao.BookmarkCategoryDao;
import com.lrowy.dao.BookmarkDao;
import com.lrowy.pojo.User;
import com.lrowy.pojo.bookmark.BookmarkResult;
import com.lrowy.pojo.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BookmarkController extends BaseController {
    @Autowired
    BookmarkDao bookmarkDao;
    @Autowired
    BookmarkCategoryDao bookmarkCategoryDao;

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
}
