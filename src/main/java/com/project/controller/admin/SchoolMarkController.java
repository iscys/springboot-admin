package com.project.controller.admin;

import com.project.controller.BaseController;
import com.project.model.school.Mark;
import com.project.model.school.SchoolModel;
import com.project.service.admin.MarkService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/mark")
public class SchoolMarkController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MarkService markService;

    @RequestMapping("/index")
    public ModelAndView markList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        DataPager dataPager= markService.getMarkList(pd);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/mark/mark_list");
        return mv;
    }
    @RequestMapping("/to_update")
    public ModelAndView to_update(Mark mark){
        ModelAndView mv = this.getModelAndView();
        //PageData pd = this.getPageData();
        Mark marks=markService.getMarkDetail(mark);
        mv.addObject("pd",marks);
        mv.setViewName("page/mark/mark_update");
        return mv;
    }
    @RequestMapping("/to_add")
    public ModelAndView addSchool(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/mark/mark_add");
        return mv;
    }
}
