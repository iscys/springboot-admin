package com.project.service.system.impl;
import com.project.model.Menu;
import com.project.mapper.SystemMapper;
import com.project.model.MessageModel;
import com.project.model.User;
import com.project.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMapper systemMapper;
    /**
     * 菜单menu
     * @return
     */
    @Override
    public List<Menu> getMenuList(User user) {
        //权限数组
        String priStr = user.getPri();
        String[] priArray = priStr.split(",");
        List<String> priList = Arrays.asList(priArray);
        HashMap<String,String> param =new HashMap<>();
        //父级菜单
        List<Menu> listMenu= systemMapper.getMenuList(param);
        Iterator<Menu> parIter = listMenu.iterator();
        while(parIter.hasNext()){
            Menu parentMenu = parIter.next();
            String parentId = parentMenu.getId();
            if(priList.contains(parentId)) {
                param.put("parentId", parentId);
                //子菜单
                List<Menu> subMenuList = systemMapper.getMenuList(param);
                Iterator<Menu> subIter = subMenuList.iterator();
                while(subIter.hasNext()){
                    Menu sub = subIter.next();
                    String subid = sub.getId();
                    if(!priList.contains(subid)) {
                        subIter.remove();
                    }
                }
                parentMenu.setChildren(subMenuList);
            }else{
                parIter.remove();
            }
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
    /**
     * 获取所有菜单
     */

    public List<Menu> listMenu(){
        List<Menu> menuList =systemMapper.getAllMenuList();
        return menuList;
    }





}
