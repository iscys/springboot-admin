package com.project.service.driver;

import com.project.model.ResultObject;
import com.project.model.school.User;

public interface UserService {
    ResultObject toLogin(User user) throws Exception;

    ResultObject getUserInfo(User user) throws Exception;

    ResultObject bindSubject(User user) throws Exception;
}
