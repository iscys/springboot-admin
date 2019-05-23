package com.project.service.wx.impl;


import com.project.mapper.wx.WxMpper;
import com.project.service.wx.WxService;
import com.project.service.wx.handler.SubscribeHandler;
import com.project.service.wx.handler.TextHandler;
import com.project.service.wx.handler.UnsubscribeHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;

@Service
@Transactional
public class WxServiceImpl implements WxService, InitializingBean {
    private Logger logger =LoggerFactory.getLogger(WxServiceImpl.class);

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpper wxMapper;
    @Autowired
    private SubscribeHandler subscribeHandler;
    @Autowired
    private TextHandler textHandler;
    @Autowired
    private UnsubscribeHandler unsubscribeHandler;

    public static   WxMpMessageRouter router;
    @Override
    public void wxSubQrcode(WxMpXmlMessage wxMpXmlMessage)throws Exception {
    }




     public WxMpXmlOutMessage  toRouter(WxMpXmlMessage message){

         WxMpXmlOutMessage result = router.route(message);
         return result;
     }

    /**
     * router 路由，需要同步回复消息，需要设置async(false)
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        router = new WxMpMessageRouter(wxMpService);
        router.rule()
                .async(false)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(subscribeHandler)//处理关注规则
                .end()
                .rule()
                .async(false)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(unsubscribeHandler)
                .end()
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.TEXT)//处理文本消息
                .handler(textHandler)
                .end();


    }
}
