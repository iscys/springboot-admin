package com.project.mapper.admin;

import com.project.model.school.Article;
import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface ArticleMapper {
    Integer getArticleCount(PageData pd);

    List<HashMap<String, String>> getArticleList(PageData pd);

    void saveArticle(PageData pd);

    void updateArticle(PageData pd);


    Article getArticleDetail(Article article);
}
