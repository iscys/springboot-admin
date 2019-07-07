package com.project.service.system;

import com.project.model.Menu;
import com.project.model.ResultObject;
import com.project.model.User;

import java.util.List;

public interface SystemService {

    List<Menu> getMenuList(User user);

    User checkUser(User user);

    List<Menu> listMenu();
}
