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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 文本消息回复
 */
@Service
public class TextHandler implements WxMpMessageHandler {

    @Autowired
    private WxMpper wxMapper;
    private Logger logger = LoggerFactory.getLogger(TextHandler.class);
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        logger.info("----处理文本消息-----");
        String create_time =String.valueOf(wxMessage.getCreateTime());
        String from_user =wxMessage.getFromUser();//用户openid
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(from_user);//获取用户的信息
        String unionId = wxMpUser.getUnionId();
        //关注成功发送文本消息
        //关注成功发送文本消息
        WxMpXmlOutTextMessage reText = WxMpXmlOutMessage.TEXT()
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .content("欢迎来到超级卖家\n" +
                        "我们是购物终端、创业孵化基地、社区服务中心为一体，其中涉及农业、旅游、互联网、零售、服务、教育等多个行业。整合移动互联网和各大商业模式，打造新型的服务模式和商业模式。\n" +
                        "会员体验资格请回复：姓名+手机号，等待程序上线，即可体验折扣加油优惠！")
                .build();
        return reText;

    }
}
