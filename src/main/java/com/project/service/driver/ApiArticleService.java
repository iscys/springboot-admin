package com.project.service.driver;

import com.project.model.ResultObject;
import com.project.model.school.Article;
import com.project.model.school.FeedBackArticle;
import com.project.utils.PageData;

public interface ApiArticleService {
    ResultObject saveArticleFeedback(FeedBackArticle fda)throws Exception;

    ResultObject updateViewArticle(FeedBackArticle fda);

    ResultObject getArticleList(PageData pd)throws Exception;

    ResultObject getArticleDetail(FeedBackArticle fda) throws Exception;
}
