package com.project.controller.driver;

import com.project.model.Const;
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

        if(StringUtils.isEmpty(user.getCode())){
            return  ResultObject.build(Const.WX_CODE_NULL,Const.WX_CODE_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(user.getSex())){
            return  ResultObject.build(Const.WX_SEX,Const.WX_SEX_MESSAGE,null);
        }

        if(StringUtils.isEmpty(user.getHeadimgurl())){
            return  ResultObject.build(Const.WX_IMG_NULL,Const.WX_IMG_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(user.getNickname())){
            return  ResultObject.build(Const.WX_NICKNAME_NULL,Const.WX_NICKNAME_NULL_MESSAGE,null);
        }


        try {
            ResultObject result = userService.toLogin(user);
            return result;
        }catch (Exception e){
            logger.error("用户登录注册异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }


    /**
     * 用户绑定需要报考的驾照类型
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/bindSubject")
    public ResultObject bindSubject(User user, HttpServletRequest request) {
        String member_id = user.getMember_id();
        String subject =user.getSubject();
        if(StringUtils.isEmpty(member_id)){
            return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(subject)){
            return ResultObject.build(Const.SUBJECT_NULL,Const.SUBJECT_NULL_MESSAGE,null);
        }
        try {
            ResultObject result = userService.bindSubject(user);
            return result;
        }catch (Exception e){
            logger.error("用户绑定驾照类型异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }

        /**
         * 获取用户信息接口
         * @param user
         * @param request
         * @return
         */

    @PostMapping("/userInfo")
    public ResultObject userInfo(User user, HttpServletRequest request){

        try {
            String member_id = user.getMember_id();
            if(StringUtils.isEmpty(member_id)){
                return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
            }
            ResultObject result = userService.getUserInfo(user);
            return result;
        }catch (Exception e){
            logger.error("获取用户信息异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }
    }
}
