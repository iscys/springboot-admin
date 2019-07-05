package com.project.service.admin;

import com.project.model.ResultObject;
import com.project.model.school.Article;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    DataPager getArticleList(PageData pd);

    Article getArticleDetail(Article art);

    ResultObject saveArticle(MultipartFile thumb,PageData pd) throws Exception;
}
