package com.project.service.wx;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

public interface WxService {
    void wxSubQrcode(WxMpXmlMessage wxMpXmlMessage) throws Exception;

    WxMpXmlOutMessage toRouter(WxMpXmlMessage wxMpXmlMessage);
}
