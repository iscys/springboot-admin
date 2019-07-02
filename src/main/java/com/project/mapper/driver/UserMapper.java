package com.project.mapper.driver;


import com.project.model.school.User;

public interface UserMapper {
    User getUserInfo(User invit);
    void saveNewUser(User user);
    void modifyUser(User user);


}
