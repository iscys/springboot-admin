package com.project.service.wx;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

public interface WxService {
    void wxSubQrcode(WxMpXmlMessage wxMpXmlMessage) throws Exception;
}
