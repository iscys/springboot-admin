package com.project.controller.system;

import com.project.controller.BaseController;
import com.project.model.Const;
import com.project.model.Menu;
import com.project.model.ResultObject;
import com.project.model.User;
import com.project.service.system.SystemService;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
