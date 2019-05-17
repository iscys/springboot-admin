package com.project.service.impl;
import com.project.model.Menu;
import com.project.mapper.SystemMapper;
import com.project.model.MessageModel;
import com.project.model.User;
import com.project.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMapper systemMapper;
    /**
     * 菜单menu
     * @return
     */
    @Override
    public List<Menu> getMenuList() {

        HashMap<String,String> param =new HashMap<>();
        //父级菜单
        List<Menu> listMenu= systemMapper.getMenuList(param);
        for(Menu parentMenu:listMenu){
            //获取子集菜单
            String parentId = parentMenu.getId();
            param.put("parentId",parentId);
            List<Menu> subMenuList = systemMapper.getMenuList(param);
            parentMenu.setChildren(subMenuList);
        }
        return listMenu;
    }

    /**
     * 校验账号密码
     * @param user
     * @return
     */
    @Override
    public User checkUser(User user) {
        user.setPassword(MessageModel.getMD5String(user.getPassword()));
        User isExist = systemMapper.checkUser(user);
        return isExist;
    }





}
