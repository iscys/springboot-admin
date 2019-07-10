package com.project.service.system.impl;
import com.project.model.Menu;
import com.project.mapper.SystemMapper;
import com.project.model.MessageModel;
import com.project.model.ResultObject;
import com.project.model.User;
import com.project.service.system.SystemService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import com.project.utils.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.util.*;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMapper systemMapper;
    /**
     * 用户匹配菜单menu
     * @return
     */
    @Override
   // @Transactional(propagation=Propagation.NEVER)

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
    @Transactional(propagation=Propagation.REQUIRED)

    public User checkUser(User user) {
        user.setPassword(MessageModel.getMD5String(user.getPassword()));
        User isExist = systemMapper.checkUser(user);
        return isExist;
    }
    /**
     * 获取所有菜单
     */

    public List<Menu> listMenu(){
        HashMap<String,String> param =new HashMap<>();

        //父级菜单
        List<Menu> listMenu= systemMapper.getMenuList(param);
        for(Menu menu:listMenu){
            String parentId = menu.getId();
            param.put("parentId", parentId);
            List<Menu> subMenu= systemMapper.getMenuList(param);
            menu.setChildren(subMenu);

        }
        return listMenu;
    }


    public List<Menu> listMenu(HashMap<String,String> param){
        return systemMapper.getMenuList(param);
    }





    @Override
    public ResultObject saveSystemUser(PageData pd) throws Exception {
        String password = pd.getString("password");
        pd.put("password", MessageModel.getMD5String(password));
        if(null==pd.get("id")) {
            systemMapper.saveSystemUser(pd);
        }else{
            systemMapper.updateSystemUser(pd);

        }
        return ResultObject.success(null);
    }

    @Override
    public DataPager getUserList(PageData pd) {
        Integer total = systemMapper.getAdminUserCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= systemMapper.getAdminUserList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public User getUserDetail(User user) {
        User adminUserDetail = systemMapper.getAdminUserDetail(user);


        return adminUserDetail;
    }


}
