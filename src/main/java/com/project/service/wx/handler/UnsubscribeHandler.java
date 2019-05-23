package com.project.service.wx.handler;

import com.project.mapper.wx.WxMpper;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/***
 * 取消关注用户事件
 */
@Service
@Transactional
public class UnsubscribeHandler implements WxMpMessageHandler {

    @Autowired
    private WxMpper wxMapper;
    private Logger logger = LoggerFactory.getLogger(UnsubscribeHandler.class);
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        logger.info("---微信触发取消关注事件--");
        String from_user =wxMessage.getFromUser();//用户openid
        String event= wxMessage.getEvent();

            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(from_user);//获取用户的信息
            //逻辑删除业务员的推广
            String unionid = wxMpUser.getUnionId();
            String openid =wxMpUser.getOpenId();
            HashMap<String,String> param =new HashMap<>();
            param.put("openid",openid);
            param.put("unionid",unionid);
            wxMapper.cancelTuiguang(param);

        return null;
    }
}
