package com.project.service.wx.impl;


import com.project.mapper.wx.WxMpper;
import com.project.service.wx.WxService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;

@Service
@Transactional
public class WxServiceImpl implements WxService {
    private Logger logger =LoggerFactory.getLogger(WxServiceImpl.class);

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpper wxMapper;
    @Override
    public void wxSubQrcode(WxMpXmlMessage wxMpXmlMessage)throws Exception {

        //通过扫描二维码进入的 qrscene_8900
        String eventKey = wxMpXmlMessage.getEventKey();//业务员编号
        String create_time =String.valueOf(wxMpXmlMessage.getCreateTime());
        String from_user =wxMpXmlMessage.getFromUser();//用户openid
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(from_user);
        //unsubscribe 检测是取消关注事件
        check_isUnsubscribe(wxMpXmlMessage,wxMpUser);
        if(!StringUtils.isEmpty(eventKey)&&wxMpXmlMessage.getEvent().equals("subscribe")){
            //  String accessToken = wxMpService.getAccessToken();
            String unionid =wxMpUser.getUnionId();
            HashMap<String,String> achieve =new HashMap<>();
            achieve.put("sceneId",eventKey);
            achieve.put("openid",from_user);
            achieve.put("time",create_time);
            achieve.put("unionid",!StringUtils.isEmpty(unionid)?unionid:"");
            achieve.put("type","1");
            //保存推广业绩
            logger.info("---保存推广信息开始---");
            wxMapper.saveTuiGuangAchievement(achieve);
            logger.info("---保存推广信息成功---");
            HashMap<String,String> userInfo =new HashMap<>();
            userInfo=achieve;
            userInfo.put("nickname",wxMpUser.getNickname());
            userInfo.put("headimgurl",wxMpUser.getHeadImgUrl());
            userInfo.put("sex", String.valueOf(wxMpUser.getSex()));
            userInfo.put("country", wxMpUser.getCountry());
            userInfo.put("city", wxMpUser.getCity());
            userInfo.put("province", wxMpUser.getProvince());
            userInfo.put("qrScene", wxMpUser.getQrScene());
            logger.info("---检测用户是否存在---");
            HashMap<String,String> user = wxMapper.getTuiGuangUserInfo(userInfo);
            if(CollectionUtils.isEmpty(user)) {
                logger.info("---是新用户，保存信息持久化---");
                wxMapper.saveTuiGuangUserInfo(userInfo);
                logger.info("---保存新用户成功---");
            }else{
                logger.info("---用户之前已经进行了关注服务号，更新用户信息---");
                wxMapper.updateTuiGuangUserInfo(userInfo);
            }
        }

    }

    public  void check_isUnsubscribe(WxMpXmlMessage wxMpXmlMessage,WxMpUser mpUser) {

        if(wxMpXmlMessage.getEvent().equals("unsubscribe")){
            logger.info("openid =>"+mpUser.getOpenId()+"用户触发了取消关注的事件");
            //逻辑删除业务员的推广
            String unionid = mpUser.getUnionId();
            String openid =mpUser.getOpenId();
            HashMap<String,String> param =new HashMap<>();
            param.put("openid",openid);
            param.put("unionid",unionid);
            wxMapper.cancelTuiguang(param);


        }

    }
}
