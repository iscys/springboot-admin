package com.project.service.admin.impl;

import com.project.config.ConfigProperties;
import com.project.mapper.admin.ArticleMapper;
import com.project.model.ResultObject;
import com.project.model.school.Subject;
import com.project.service.admin.ArticleService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ConfigProperties properties;
    @Autowired
    private ArticleMapper articleMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public DataPager getArticleList(PageData pd) {
        Integer total = articleMapper.getArticleCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= articleMapper.getArticleList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public Subject getArticleDetail(Subject subject) {
        return null;
    }

    @Override
    public ResultObject saveArticle(PageData pd) throws Exception {
        return null;
    }
}
