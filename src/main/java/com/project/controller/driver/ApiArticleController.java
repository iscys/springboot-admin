package com.project.controller.driver;

import com.project.controller.BaseController;
import com.project.model.Const;
import com.project.model.ResultObject;
import com.project.model.school.Article;
import com.project.model.school.FeedBackArticle;
import com.project.service.driver.ApiArticleService;
import com.project.utils.PageData;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class ApiArticleController extends BaseController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiArticleService apiArticleService;

    /**
     * 用户评论文章，没用
     */
    @PostMapping ("/feedback")
    public ResultObject feedback(FeedBackArticle fda){
        if(StringUtils.isEmpty(fda.getFrom_member_id())){
            return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(fda.getFeedback())){
            return ResultObject.build(Const.FEED_BACK_NULL,Const.FEED_BACK_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(fda.getArticle_id())){
            return ResultObject.build(Const.ATRICLE_ID_NULL,Const.ATRICLE_ID_NULL_MESSAGE,null);
        }

        try{
           ResultObject rest= apiArticleService.saveArticleFeedback(fda);
           return rest;

        }catch (Exception e){
            logger.error("保存用户评论文章异常：{}",e.getMessage());
            return ResultObject.error(null);
        }

    }


    /**
     * 浏览量
     */
    @PostMapping ("/upView")
    public ResultObject view(FeedBackArticle fda){

        if(StringUtils.isEmpty(fda.getArticle_id())){
            return ResultObject.build(Const.ATRICLE_ID_NULL,Const.ATRICLE_ID_NULL_MESSAGE,null);
        }
        try{
            ResultObject rest= apiArticleService.updateViewArticle(fda);
            return rest;

        }catch (Exception e){
            logger.error("改变浏览量异常：{}",e.getMessage());
            return ResultObject.error(null);
        }

    }


    /**
     * 文章列表
     * @return
     */
    @PostMapping ("/list")
    public ResultObject list(){

        try{
            PageData pd = this.getPageData();
            ResultObject rest= apiArticleService.getArticleList(pd);
            return rest;

        }catch (Exception e){
            logger.error("api获取文章列表异常：{}",e.getMessage());
            return ResultObject.error(null);
        }

    }


    /**
     * 文章详情
     * @return
     */
    @PostMapping ("/info")
    public ResultObject info(FeedBackArticle fda){
        if(StringUtils.isEmpty(fda.getArticle_id())){
            return ResultObject.build(Const.ATRICLE_ID_NULL,Const.ATRICLE_ID_NULL_MESSAGE,null);
        }

        try{

            ResultObject rest= apiArticleService.getArticleDetail(fda);
            return rest;

        }catch (Exception e){
            logger.error("api获取详情异常：{}",e.getMessage());
            return ResultObject.error(null);
        }

    }



}
