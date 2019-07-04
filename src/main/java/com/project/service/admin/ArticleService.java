package com.project.service.admin;

import com.project.model.ResultObject;
import com.project.model.school.Subject;
import com.project.utils.DataPager;
import com.project.utils.PageData;

public interface ArticleService {
    DataPager getArticleList(PageData pd);

    Subject getArticleDetail(Subject subject);

    ResultObject saveArticle(PageData pd) throws Exception;
}
