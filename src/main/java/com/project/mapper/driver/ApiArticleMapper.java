package com.project.mapper.driver;

import com.project.model.school.Article;
import com.project.model.school.FeedBackArticle;
import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface ApiArticleMapper {

    void saveArticleFeedback(FeedBackArticle fda);

    void updateViewArticle(FeedBackArticle fda);

    int getArticleCount(PageData pd);

    List<HashMap<String, String>> getArticleList(PageData pd);
}
