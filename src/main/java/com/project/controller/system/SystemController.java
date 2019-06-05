package com.project.controller.system;

import com.project.model.Const;
import com.project.model.Menu;
import com.project.model.User;
import com.project.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 页面初始化配置
 */
@RestController
@RequestMapping("sys")
public class SystemController {
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


}
