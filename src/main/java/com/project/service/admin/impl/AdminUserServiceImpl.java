package com.project.service.admin.impl;

import com.project.mapper.SystemMapper;
import com.project.model.ResultObject;
import com.project.model.User;
import com.project.model.school.Mark;
import com.project.service.admin.AdminUserService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private SystemMapper adminUserMapper;

    @Override
    public DataPager getAdminUserList(PageData pd) {
        Integer total = adminUserMapper.getAdminUserCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= adminUserMapper.getAdminUserList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public User getAdminUserDetail(User user) {
        return adminUserMapper.getAdminUserDetail(user);
    }

    @Override
    public ResultObject saveAdminUser(PageData pd) throws Exception {
        return null;
    }
}
