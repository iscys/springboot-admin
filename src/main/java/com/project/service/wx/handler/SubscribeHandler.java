package com.project.service.wx.handler;

import com.project.mapper.wx.WxMpper;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理微信关注事件
 */
@Service
@Transactional
public class SubscribeHandler implements WxMpMessageHandler {

    @Autowired
    private WxMpper wxMapper;

    private Logger logger = LoggerFactory.getLogger(SubscribeHandler.class);
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        logger.info("----用户关注事件触发---");
        //通过扫描二维码进入的 qrscene_8900
        String eventKey = wxMessage.getEventKey();//业务员编号，如果是二维码扫描进来的
        String create_time =String.valueOf(wxMessage.getCreateTime());
        String from_user =wxMessage.getFromUser();//用户openid
        HashMap<String, String> achieve =new HashMap<>();
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(from_user);//获取用户的信息
                String unionid = wxMpUser.getUnionId();
                achieve.put("openid", from_user);
                achieve.put("time", create_time);
                achieve.put("unionid", !StringUtils.isEmpty(unionid) ? unionid : "");
            if(!StringUtils.isEmpty(eventKey)) { //说明是通过扫描二维码进入的
                achieve.put("sceneId", eventKey);
                achieve.put("type", "1");
                //保存推广业绩
                logger.info("---保存推广信息开始---");
                wxMapper.saveTuiGuangAchievement(achieve);
                logger.info("---保存推广信息成功---");
            }
            HashMap<String,String> userInfo =new HashMap<>();
            userInfo.put("openid",from_user);
            userInfo.put("unionid", !StringUtils.isEmpty(unionid) ? unionid : "");
            userInfo.put("time", create_time);
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

        //关注成功发送文本消息
        WxMpXmlOutTextMessage reText = WxMpXmlOutMessage.TEXT()
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .content("欢迎来到超级卖家 \ue050 \n" +
                        "我们是购物终端、创业孵化基地、社区服务中心为一体，其中涉及农业、旅游、互联网、零售、服务、教育等多个行业。整合移动互联网和各大商业模式，打造新型的服务模式和商业模式。\n" +
                        "会员体验资格请回复：姓名+手机号，等待程序上线，即可体验折扣加油优惠！")
                .build();
        return reText;
    }
}
