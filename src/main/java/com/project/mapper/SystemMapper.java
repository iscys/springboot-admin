package com.project.mapper;

import com.project.model.Menu;
import com.project.model.User;

import java.util.HashMap;
import java.util.List;

public interface SystemMapper {
    List<Menu> getMenuList(HashMap<String, String> param);

    User checkUser(User user);
}
