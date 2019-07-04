package com.project.mapper.admin;

import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface ArticleMapper {
    Integer getArticleCount(PageData pd);

    List<HashMap<String, String>> getArticleList(PageData pd);
}
