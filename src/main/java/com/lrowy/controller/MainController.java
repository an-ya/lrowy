package com.lrowy.controller;

import com.lrowy.dao.BookmarkDao;
import com.lrowy.pojo.Bookmark;
import com.lrowy.pojo.User;
import com.lrowy.pojo.common.response.BaseResponse;
import com.lrowy.service.FaviconService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController extends BaseController {
    @Autowired
    BookmarkDao bookmarkDao;
    @Autowired
    private FaviconService fs;

    @RequestMapping("/")
    public String i() {
        return "forward:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);

        List<Bookmark> bm = bookmarkDao.findBookmark();
//        bm.add(fs.getFaviconUrl(new Bookmark("https://blog.csdn.net/qq_37385726/article/details/82020214")));
//        bm.add(fs.getFaviconUrl(new Bookmark("https://www.jianshu.com/u/107bd58d5bbb")));
//        bm.add(fs.getFaviconUrl(new Bookmark("https://blog.52itstyle.vip")));
//        bm.add(fs.getFaviconUrl(new Bookmark("https://imgchr.com/")));

        model.addAttribute("bookmark", bm);
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<User> login() {
        BaseResponse<User> br = new BaseResponse<>();
        User user = new User();
        user.setName("AnyaZZ");
        userLogin(user);
        br.setResult(user);
        return br;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> logout() {
        BaseResponse<String> br = new BaseResponse<>();
        userLogout();
        return br;
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<User> init() {
        BaseResponse<User> br = new BaseResponse<>();
        User user = isLogin() ? getUser() : null;
        br.setResult(user);
        return br;
    }

    @RequestMapping(value = "/bookmark/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> bookmarkAdd(String url) {
        Bookmark b = fs.getFaviconUrl(new Bookmark("https://blog.csdn.net/qq_37385726/article/details/82020214"));
        bookmarkDao.save(b);
        return new BaseResponse<>();
    }
}
