package com.project.service.admin;

import com.project.model.ResultObject;
import com.project.model.User;
import com.project.model.school.Mark;
import com.project.utils.DataPager;
import com.project.utils.PageData;

public interface AdminUserService {
    DataPager getAdminUserList(PageData pd);

    User getAdminUserDetail(User user);

    ResultObject saveAdminUser(PageData pd) throws Exception;
}
