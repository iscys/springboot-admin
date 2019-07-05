package com.project.service.driver.impl;

import com.project.mapper.admin.ArticleMapper;
import com.project.mapper.driver.ApiArticleMapper;
import com.project.model.Const;
import com.project.model.ResultObject;
import com.project.model.school.Article;
import com.project.model.school.FeedBackArticle;
import com.project.service.driver.ApiArticleService;
import com.project.utils.Page;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ApiArticleServiceImpl implements ApiArticleService {
    @Autowired
    private ApiArticleMapper apiArticleMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public ResultObject saveArticleFeedback(FeedBackArticle fda) throws Exception {
        if(!StringUtils.isEmpty(fda.getTo_member_id())){
            fda.setType("2");//to-from 评论
        }
        apiArticleMapper.saveArticleFeedback(fda);
        return ResultObject.success(null);
    }

    @Override
    public ResultObject updateViewArticle(FeedBackArticle fda) {
        apiArticleMapper.updateViewArticle(fda);
        return ResultObject.success(null);
    }

    @Override
    public ResultObject getArticleList(PageData pd) {
        String pageNum = pd.getString("pageNum");
        if(StringUtils.isEmpty(pageNum)||pageNum.equals("0")){
            pageNum ="1";
            pd.put("pageNum",pageNum);
        }
        int pageSize = Const.DEFAULT_PAGESIZE;
        int total =apiArticleMapper.getArticleCount(pd);

        Page page =new Page(Integer.valueOf(pageNum),total,pageSize);
        int startIndex = page.getStartIndex();
        int pageSize1 = page.getPageSize();
        pd.put("startIndex",startIndex);
        pd.put("pageSize",pageSize1);
        List<HashMap<String,String>> lists =apiArticleMapper.getArticleList(pd);

        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",page.getPageNum());//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        res.put("article_list",lists);
        return ResultObject.success(res);
    }

    @Override
    public ResultObject getArticleDetail(FeedBackArticle fed) throws Exception {

        Article article =new Article();
        article.setId(fed.getArticle_id());
        Article articleDetail = articleMapper.getArticleDetail(article);

        return ResultObject.success(articleDetail);
    }
}
