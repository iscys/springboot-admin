package com.project.controller.admin;

import com.project.controller.BaseController;
import com.project.utils.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/subject")
public class SubjectController extends BaseController {

    @RequestMapping("/index")
    public ModelAndView homeList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        //DataPager dataPager= homeService.getHomeList(pd);
        //mv.addObject("dataPager",dataPager);
        // mv.addObject("pd",pd);
        mv.setViewName("page/teacher/teacher_list");
        return mv;
    }

}
