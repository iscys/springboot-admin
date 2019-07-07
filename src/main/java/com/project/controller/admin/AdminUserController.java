package com.project.controller.admin;

import com.project.controller.BaseController;
import com.project.model.Menu;
import com.project.model.ResultObject;
import com.project.model.User;
import com.project.model.school.Mark;
import com.project.service.admin.AdminUserService;
import com.project.service.system.SystemService;
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
@RequestMapping("/admin")
public class AdminUserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private SystemService systemService;

    @RequestMapping("/index")
    public ModelAndView AdminList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        DataPager dataPager= adminUserService.getAdminUserList(pd);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/adminUser/adminUser_list");
        return mv;
    }
    @RequestMapping("/to_update")
    public ModelAndView to_update(User user){
        ModelAndView mv = this.getModelAndView();
        //PageData pd = this.getPageData();
        User adminUser=adminUserService.getAdminUserDetail(user);
        mv.addObject("pd",adminUser);
        mv.setViewName("page/adminUser/adminUser_update");
        return mv;
    }
    @RequestMapping("/to_add")
    public ModelAndView addSchool(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        List<Menu> menuList = systemService.listMenu();
        mv.addObject("menuList",menuList);
        mv.setViewName("page/adminUser/adminUser_add");
        return mv;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultObject save(){
        PageData pd = this.getPageData();
        ModelAndView mv = this.getModelAndView();
        try {
            ResultObject result = adminUserService.saveAdminUser(pd);
            return result;
        }catch (Exception e){
            logger.error("保存系统用户成功：{}",e.getMessage());
            return ResultObject.error(null);

        }



    }
}
