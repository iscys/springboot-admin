package com.project.controller.admin;

import com.project.controller.BaseController;
import com.project.model.ResultObject;
import com.project.model.school.SchoolModel;
import com.project.model.school.Subject;
import com.project.service.admin.ArticleService;
import com.project.service.admin.SubjectService;
import com.project.service.admin.TeacherService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ArticleService articleService;
    @RequestMapping("/index")
    public ModelAndView teacherList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        DataPager dataPager= articleService.getArticleList(pd);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/article/article_list");
        return mv;
    }

    @RequestMapping("/to_add")
    public ModelAndView to_add(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        mv.addObject("pd",pd);
        mv.setViewName("page/article/article_add");
        return mv;
    }


    @RequestMapping("/to_update")
    public ModelAndView to_update(Subject subject){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        mv.addObject("pd",pd);
        Subject resT=articleService.getArticleDetail(subject);
        mv.addObject("article",resT);
        //  mv.addObject("pd",schoolModel);
        mv.setViewName("page/article/article_update");
        return mv;
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResultObject save(){
        PageData pd = this.getPageData();
        ModelAndView mv = this.getModelAndView();
        try {
            ResultObject result = articleService.saveArticle(pd);
            return result;
        }catch (Exception e){
            logger.error("保存修改教练配置异常：{}",e.getMessage());
            return ResultObject.error(null);

        }



    }

}
