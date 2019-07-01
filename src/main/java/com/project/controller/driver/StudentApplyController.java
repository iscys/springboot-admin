package com.project.controller.driver;

import com.project.config.ConfigProperties;
import com.project.model.Const;
import com.project.model.ResultObject;
import com.project.model.school.Apply;
import com.project.service.driver.StudentApplyService;
import com.project.utils.GsonUtils;
import com.project.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 驾校报名接口Api
 */
@RestController
@RequestMapping("/api/student")
public class StudentApplyController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private StudentApplyService applyService;

    @RequestMapping("/apply")
    public ResultObject applay(Apply apply){
        String school_id = apply.getSchool_id();
        String subject_id = apply.getSubject_id();
        String member_id = apply.getMember_id();

        if(StringUtils.isEmpty(apply.getCardtype())){
            return ResultObject.build(Const.CARDTYPE_NULL,Const.CARDTYPE_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(school_id)){
            return ResultObject.build(Const.SHOOL_ID_NULL,Const.SHOOL_ID_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(subject_id)){
            return ResultObject.build(Const.SUBJECT_ID_NULL,Const.SUBJECT_ID_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(member_id)){
            return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(apply.getPhone())){
            return ResultObject.build(Const.PHONE_NULL,Const.PHONE_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(apply.getAddr())){
            return ResultObject.build(Const.ADDR_NULL,Const.ADDR_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(apply.getIdcard())){
            return ResultObject.build(Const.IDCARD_NULL,Const.IDCARD_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(apply.getPhoto())){
            return ResultObject.build(Const.PHOTO_NULL,Const.PHOTO_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(apply.getTraintype())){
            return ResultObject.build(Const.TRAINTYPE_NULL,Const.TRAINTYPE_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(apply.getOrgcode())){
            return ResultObject.build(Const.ORGCODE_NULL,Const.ORGCODE_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(apply.getOrgcode())){
            return ResultObject.build(Const.ORGCODE_NULL,Const.ORGCODE_NULL_MESSAGE,null);
        }





        try {
            ResultObject result = applyService.applyAndCreateOrder(apply);
            return ResultObject.success(result);
        }catch (Exception e){
            return ResultObject.error(null);
        }


    }

    public static void main(String[] args) {



    }
}
