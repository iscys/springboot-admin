package com.project.controller.admin;

import com.project.controller.BaseController;
import com.project.model.ResultObject;
import com.project.model.school.SchoolModel;
import com.project.service.admin.TeacherService;
import com.project.utils.DataPager;
import com.project.utils.DateUtils;
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
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/index")
    public ModelAndView teacherList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        DataPager dataPager= teacherService.getTeacherList(pd);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/teacher/teacher_list");
        return mv;
    }

    @RequestMapping("/to_add")
    public ModelAndView to_add(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        List<SchoolModel> slist= teacherService.getAllSchoolList(pd);
        //DataPager dataPager= homeService.getHomeList(pd);
        //mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.addObject("list",slist);

        mv.setViewName("page/teacher/teacher_add");
        return mv;
    }

    @RequestMapping("/to_update")
    public ModelAndView to_update(SchoolModel school){
        ModelAndView mv = this.getModelAndView();
        //PageData pd = this.getPageData();
        //SchoolModel schoolModel=homeService.getSchoolDetail(school);
      //  mv.addObject("pd",schoolModel);
        mv.setViewName("page/school/teacher_update");
        return mv;
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResultObject save(MultipartFile teacher_img){
        PageData pd = this.getPageData();
        return ResultObject.success(null);



    }




}
