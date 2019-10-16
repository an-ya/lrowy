package com.lrowy.dao;

import com.lrowy.pojo.article.ArticleCategory;

import java.util.List;

public interface ArticleDao {
    public int saveArticleCategory(ArticleCategory articleCategory);
    public List<ArticleCategory> findArticleCategory();
    public int deleteArticleCategory(int articleCategoryId);
}
