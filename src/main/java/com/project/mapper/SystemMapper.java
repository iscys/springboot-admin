package com.project.mapper;

import com.project.model.Menu;
import com.project.model.User;

import java.util.HashMap;
import java.util.List;

public interface SystemMapper {
    //菜单列表
    List<Menu> getMenuList(HashMap<String, String> param);

    //检测用户是不是存在
    User checkUser(User user);
}
