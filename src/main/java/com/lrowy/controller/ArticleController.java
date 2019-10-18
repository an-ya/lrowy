package com.lrowy.controller;

import com.lrowy.dao.ArticleDao;
import com.lrowy.pojo.User;

import com.lrowy.pojo.article.Article;
import com.lrowy.pojo.article.ArticleCategory;
import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArticleController extends BaseController {
    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("/writer")
    public String writer(Model model) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);
        List<ArticleCategory> articleCategoryList = articleDao.findAllArticleCategory();
        model.addAttribute("articleCategoryList", articleCategoryList);
        return "/article/writer/normal";
    }

    @RequestMapping("/article/init")
    @ResponseBody
    public BaseResponse<Article> articleCategoryAdd(int articleId) {
        Article article = articleDao.findArticle(articleId);
        BaseResponse<Article> br = new BaseResponse<>();
        if (article == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
            br.setMsg(br.getMsg() + ":未找到指定编号的文章。");
        } else {
            br.setResult(article);
        }
        return br;
    }

    @RequestMapping("/article/add")
    @ResponseBody
    public BaseResponse<Article> articleCategoryAdd(@ModelAttribute Article article) {
        articleDao.saveArticle(article);
        BaseResponse<Article> br = new BaseResponse<>();
        br.setResult(article);
        return br;
    }

    @RequestMapping("/article/update")
    @ResponseBody
    public BaseResponse<Article> articleCategoryUpdate(@ModelAttribute Article article) {
        articleDao.updateArticle(article);
        BaseResponse<Article> br = new BaseResponse<>();
        br.setResult(article);
        return br;
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
