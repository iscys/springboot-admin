package com.project.controller.admin.dirverHome;

import com.project.controller.BaseController;
import com.project.model.ResultObject;
import com.project.model.school.SchoolModel;
import com.project.service.admin.DriverHomeService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 驾校列表以及配置
 * @author iscys
 * qq:1074171884
 */
@Controller
@RequestMapping("/home")
public class DriverHomeController extends BaseController {

    @Autowired
    private DriverHomeService homeService;

    @RequestMapping("/index")
    public ModelAndView homeList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        DataPager dataPager= homeService.getHomeList(pd);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/school/school_list");
        return mv;
    }

    @RequestMapping("/to_add")
    public ModelAndView addSchool(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/school/school_add");
        return mv;
    }

    @RequestMapping("/to_update")
    public ModelAndView to_update(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        SchoolModel schoolModel=homeService.getSchoolDetail(pd);
        mv.addObject("pd",schoolModel);
        mv.setViewName("page/school/school_update");
        return mv;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultObject save(MultipartFile school_icon, MultipartFile school_face, MultipartFile[] files){
        PageData pd = this.getPageData();
        try {
            PageData result = homeService.save(pd,school_icon,school_face,files);

            return ResultObject.success(null);
        }catch (Exception e){
            return ResultObject.error(null);
        }
    }
}
