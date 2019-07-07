package com.project.controller.system;

import com.project.controller.BaseController;
import com.project.model.Const;
import com.project.model.Menu;
import com.project.model.ResultObject;
import com.project.model.User;
import com.project.service.system.SystemService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 页面初始化配置
 */
@RestController
@RequestMapping("sys")
public class SystemController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemService sysService;

    /**
     * 获取用户对应的菜单列表Tab
     * @param session
     * @return
     */
    @RequestMapping("menu")
    public List<Menu> menuList(HttpSession session){
        User user = (User) session.getAttribute(Const.USER);
        List<Menu> menuList = sysService.getMenuList(user);
        return menuList;
    }



    @RequestMapping("/index")
    public ModelAndView userList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        DataPager dataPager= sysService.getUserList(pd);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/adminUser/adminUser_list");
        return mv;
    }


    @RequestMapping("/to_add")
    public ModelAndView addUser(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        List<Menu> menuList = sysService.listMenu();
        mv.addObject("menuList",menuList);
        mv.setViewName("page/adminUser/adminUser_add");
        return mv;
    }


    @RequestMapping("/to_update")
    public ModelAndView to_update(User user){
            ModelAndView mv = this.getModelAndView();
            //PageData pd = this.getPageData();
            User adminUser= sysService.getUserDetail(user);
            String pri = adminUser.getPri();
            String[] split = pri.split(",");
            List<String> prives = Arrays.asList(split);
            HashMap<String,String> param =new HashMap<>();
            //父级菜单
            List<Menu> listMenu= sysService.listMenu(param);
            for(Menu menu:listMenu){
                String parentId = menu.getId();
                if(prives.contains(parentId)) {
                    menu.setPri("1");
                }
                param.put("parentId", parentId);
                List<Menu> subMenu= sysService.listMenu(param);
                for(Menu sm:subMenu){
                    String subId = sm.getId();
                    if(prives.contains(subId)) {
                        sm.setPri("1");
                    }
                }
                menu.setChildren(subMenu);

            }

        mv.addObject("menuList",listMenu);
        mv.addObject("pd",adminUser);
        mv.setViewName("page/adminUser/adminUser_update");
        return mv;
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultObject save(HttpSession session){
        PageData pd = this.getPageData();
        ModelAndView mv = this.getModelAndView();
        try {
            ResultObject result = sysService.saveSystemUser(pd);
            return result;
        }catch (Exception e){
            logger.error("保存系统用户成功：{}",e.getMessage());
            return ResultObject.error(null);

        }
    }



}
