package com.project.service.driver.impl;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.project.mapper.admin.DriverHomeMapper;
import com.project.mapper.driver.UserMapper;
import com.project.model.Const;
import com.project.model.ResultObject;
import com.project.model.school.SchoolModel;
import com.project.model.school.User;
import com.project.service.driver.UserService;
import com.project.utils.DateUtils;
import com.project.utils.ToolsUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxMaServiceImpl wxsmall;//微信小程序服务
    @Autowired
    private UserMapper userMapper;//微信小程序服务
    @Autowired
    private DriverHomeMapper homeMapper;

    @Override
    public ResultObject toLogin(User user) throws Exception {


        WxMaJscode2SessionResult wxsmallInfo = null;
        try {
            wxsmallInfo = wxsmall.jsCode2SessionInfo(user.getCode());
        } catch (WxErrorException e) {
            return ResultObject.build(Const.WX_ERROR, e.getMessage(), e.getMessage());
        }
        String openid = wxsmallInfo.getOpenid();
        String unionid = wxsmallInfo.getUnionid();
        User openidExist =new User();
        openidExist.setOpenid(openid);
        User userExist=userMapper.getUserInfo(openidExist);

        if(null!=userExist){

            applySchoolInfo(userExist);


            return ResultObject.success(userExist);
        }else{
            logger.info("开始保存新用户到信息");
            user.setOpenid(openid);
            user.setUnionid(StringUtils.isEmpty(unionid)?"":unionid);
            //生成邀请码8位
            String code_invite= ToolsUtils.generateShortUuid();
            user.setInvite_code(code_invite);
            //用户member_id
            user.setMember_id(ToolsUtils.idGenerate());
            user.setTime(DateUtils.getTimeInSecond());
            userMapper.saveNewUser(user);//保存新用户
            user.setIsBind("0");
            return ResultObject.success(user);

        }

    }

    @Override
    public ResultObject getUserInfo(User user) throws Exception {
        User userInfo = userMapper.getUserInfo(user);
        if(null ==userInfo){
            return ResultObject.build(Const.MEMBER_ERROR,Const.MEMBER_ERROR_MESSAGE,null);

        }
       applySchoolInfo(userInfo);

        return ResultObject.success(userInfo);
    }

    @Override
    public ResultObject bindSubject(User user) throws Exception {
        user.setSubject(user.getSubject().toUpperCase());
        userMapper.modifyUser(user);
        return ResultObject.success(null);
    }

    private void applySchoolInfo(User userInfo) {
        String school_id = userInfo.getSchool_id();
        if(StringUtils.isEmpty(school_id)){
            userInfo.setIsBind("0");
        }else{
            userInfo.setIsBind("1");
            SchoolModel sm =new SchoolModel();
            sm.setId(school_id);
            SchoolModel schoolDetail = homeMapper.getSchoolDetail(sm);
            userInfo.setSchool_name(schoolDetail.getSchool_name());
        }

    }

}
