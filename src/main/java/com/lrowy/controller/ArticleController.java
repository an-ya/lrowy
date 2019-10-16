package com.lrowy.controller;

import com.lrowy.dao.ArticleDao;
import com.lrowy.pojo.User;

import com.lrowy.pojo.article.ArticleCategory;
import com.lrowy.pojo.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ArticleController extends BaseController {
    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("/writer")
    public String writer(Model model) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);

        return "/article/writer/normal";
    }

    @RequestMapping("/articleCategory/add")
    @ResponseBody
    public BaseResponse<ArticleCategory> articleCategoryAdd(@ModelAttribute ArticleCategory articleCategory) {
        articleDao.saveArticleCategory(articleCategory);
        BaseResponse<ArticleCategory> br = new BaseResponse<>();
        br.setResult(articleCategory);
        return br;
    }
}
