package com.project.controller.admin;

import com.project.controller.BaseController;
import com.project.model.ResultObject;
import com.project.model.school.Banner;
import com.project.model.school.Mark;
import com.project.service.admin.BannerService;
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

@Controller
@RequestMapping("/banner")
public class BannerController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BannerService bannerService;

    @RequestMapping("/index")
    public ModelAndView markList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        DataPager dataPager= bannerService.getBannerList(pd);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/banner/banner_list");
        return mv;
    }
    @RequestMapping("/to_update")
    public ModelAndView to_update(Banner banner){
        ModelAndView mv = this.getModelAndView();
        //PageData pd = this.getPageData();
        Banner banners=bannerService.getBannerDetail(banner);
        mv.addObject("pd",banners);
        mv.setViewName("page/banner/banner_update");
        return mv;
    }
    @RequestMapping("/to_add")
    public ModelAndView addSchool(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/banner/banner_add");
        return mv;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultObject save(MultipartFile banner){
        PageData pd = this.getPageData();
        ModelAndView mv = this.getModelAndView();
        try {
            ResultObject result = bannerService.saveBanner(banner,pd);
            return result;
        }catch (Exception e){
            logger.error("保存修改banner配置异常：{}",e.getMessage());
            return ResultObject.error(null);

        }



    }
}
