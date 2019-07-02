package com.project.controller.driver;

import com.project.model.ResultObject;
import com.project.model.school.User;
import com.project.service.driver.UserService;
import com.project.utils.ToolsUtils;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResultObject login(User user, HttpServletRequest request){

        String ip= ToolsUtils.getClientIp(request);
        logger.info("用户ip 为：{}" ,ip);
        user.setLast_ip(ip);
        if(StringUtils.isEmpty(user.getPhone())){
            return  ResultObject.build(Constant.PHONE_NULL,Constant.PHONE_NULL_MESSAGE,null);
        }
        if(!ToolsUtils.checkMobileNumber(user.getPhone())){
            return  ResultObject.build(Constant.PHONE_ERROR,Constant.PHONE_ERROR_MESSAGE,null);
        }
        logger.info("用户：{} 进行注册",user.getPhone());
        if(StringUtils.isEmpty(user.getPhone_code())){
            return  ResultObject.build(Constant.PHONE_CODE_NULL,Constant.PHONE_CODE_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getCode())){
            return  ResultObject.build(Constant.WX_CODE_NULL,Constant.WX_CODE_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return  ResultObject.build(Constant.PASSWORD_NULL,Constant.PASSWORD_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getOrigin())){
            return  ResultObject.build(Constant.ORIGIN_NULL, Constant.ORIGIN_NULL_MESSAGE,null);
        }

        try {
            ResultObject result = userService.toLogin(user);
            return result;
        }catch (Exception e){
            logger.error("用户注册异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }
}
