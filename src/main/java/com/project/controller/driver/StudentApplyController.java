package com.project.controller.driver;

import com.baidu.aip.ocr.AipOcr;
import com.project.config.ConfigProperties;
import com.project.model.Const;
import com.project.model.IDCard.IDFront;
import com.project.model.ResultObject;
import com.project.model.school.Apply;
import com.project.model.school.ApplySubject;
import com.project.model.school.Teacher;
import com.project.model.school.ThirdResult;
import com.project.service.driver.StudentApplyService;
import com.project.utils.DateUtils;
import com.project.utils.GsonUtils;
import com.project.utils.HttpUtils;
import com.project.utils.ToolsUtils;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;


@RestController
@RequestMapping("/api/student")
public class StudentApplyController  {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private StudentApplyService applyService;
    /**
     * 驾校报名接口
     */
    @RequestMapping("/apply")
    public ResultObject applay(Apply apply) throws Exception{
        String school_id = apply.getSchool_id();
        String member_id = apply.getMember_id();
        String price = apply.getPrice();

        if(StringUtils.isEmpty(price)){
            return ResultObject.build(Const.PRICE_NULL,Const.PRICE_NULL_MESSAGE,null);
        }
        if(apply.getCardtype()==0){
            return ResultObject.build(Const.CARDTYPE_NULL,Const.CARDTYPE_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(school_id)){
            return ResultObject.build(Const.SHOOL_ID_NULL,Const.SHOOL_ID_NULL_MESSAGE,null);
        }



        if(StringUtils.isEmpty(member_id)){
            return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(apply.getPhone())){
            return ResultObject.build(Const.PHONE_NULL,Const.PHONE_NULL_MESSAGE,null);
        }

        if(!ToolsUtils.checkMobileNumber(apply.getPhone())){
            return ResultObject.build(Const.PHONE_EROR,Const.PHONE_EROR_MESSAGE,null);
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

        if(apply.getCardtype()!=1){

            if(StringUtils.isEmpty(apply.getBirdate())){

                return ResultObject.build(Const.BIRDATE_NULL,Const.BIRDATE_NULL_MESSAGE,null);
            }

            if(!DateUtils.checkAdult(DateUtils.parseYYYYMMDD(apply.getBirdate()))){

                return ResultObject.build(Const.NO_ADULT,Const.NO_ADULT_MESSAGE,null);
            }

        }





        try {
            ResultObject result = applyService.applyAndCreateOrder(apply);
            return ResultObject.success(result);
        }catch (Exception e){
            logger.error("驾校报名错误：{}",e.getMessage());
            return ResultObject.error(null);
        }


    }
/**
    public static void main(String[] args) {

        HashMap<String,String> map  =new HashMap<>();
        map.put("idcard","142702199412105453");
        map.put("name","陈岳松");
        map.put("cardtype","1");
        map.put("sex","1");
        map.put("phone","18500485759");
        map.put("addr","山西运城");
        map.put("traintype","C2");
        map.put("applydate","20190628");
        map.put("orgcode","610100001");
        map.put("photo","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");




      //  String result = HttpUtils.INSTANCE.doPost("http://203.86.28.33:10089/File/SaveStudent", map);
        //System.out.println(result);


    }
    **/

    /**
     * 学员购买课时
     */
    @RequestMapping("/applySubject")
    public ResultObject applySubject(ApplySubject applySubject){
        if(StringUtils.isEmpty(applySubject.getMember_id())){
            return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(applySubject.getPrice())){
            return ResultObject.build(Const.PRICE_NULL,Const.PRICE_NULL_MESSAGE,null);
        }

        if(StringUtils.isEmpty(applySubject.getSchool_id())){
            return ResultObject.build(Const.SHOOL_ID_NULL,Const.SHOOL_ID_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(applySubject.getSubject_id())){
            return ResultObject.build(Const.SUBJECT_ID_NULL,Const.SUBJECT_ID_NULL_MESSAGE,null);
        }


        try {
            ResultObject result = applyService.applySubjectOrder(applySubject);
            return ResultObject.success(result);
        }catch (Exception e){
            logger.error("驾校购买课时生成订单错误：{}",e.getMessage());
            return ResultObject.error(null);
        }


    }


    public static void main(String[] args)throws Exception {
        String appid ="16726104";
        String apiKey ="gkk0uqFVgEPefG7oYWMEqMSE";
        String apiSecret ="XkWuFCtvLTD2bQVS53u09cAfZxWNtSo5";

        AipOcr aip =new AipOcr(appid,apiKey,apiSecret);

        // 可选：设置网络连接参数
        aip.setConnectionTimeoutInMillis(2000);
        aip.setSocketTimeoutInMillis(60000);

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "false");

        //String idCardSide = "back";
        String idCardSide = "front";
        File file =new File("/Users/iscys/Desktop/21560748145_.pic.jpg");
        byte[] b=FileUtils.readFileToByteArray(file);
        // 参数为本地路径
        String image = "/Users/iscys/Desktop/21560748145_.pic.jpg";
        JSONObject res = aip.idcard(b, idCardSide, options);
        System.out.println(res.toString());
        IDFront idFront = GsonUtils.fromJson(res.toString(), IDFront.class);
        System.err.println(idFront.getWords_result().get公民身份号码().getWords());
    }
}
