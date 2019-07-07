package com.project.mapper;

import com.project.model.Menu;
import com.project.model.User;
import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface SystemMapper {
    //菜单列表
    List<Menu> getMenuList(HashMap<String, String> param);

    //检测用户是不是存在
    User checkUser(User user);

    Integer getAdminUserCount(PageData pd);

    List<HashMap<String, String>> getAdminUserList(PageData pd);

    User getAdminUserDetail(User user);

    List<Menu> getAllMenuList();

    void updateSystemUser(PageData pd);

    void saveSystemUser(PageData pd);
}
