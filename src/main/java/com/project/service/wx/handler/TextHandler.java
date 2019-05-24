package com.project.service.wx.handler;

import com.project.mapper.wx.WxMpper;
import com.project.utils.ToolsUtils;
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
 * 文本消息回复
 */
@Service
@Transactional
public class TextHandler implements WxMpMessageHandler {

    @Autowired
    private WxMpper wxMapper;
    private Logger logger = LoggerFactory.getLogger(TextHandler.class);
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        logger.info("----处理文本消息-----");


        HashMap<String,String> userInfo =new HashMap<>();
        String from_user =wxMessage.getFromUser();//用户openid
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(from_user);//获取用户的信息
        String unionid = wxMpUser.getUnionId();
        userInfo.put("openid",from_user);
        userInfo.put("unionid",unionid);
        logger.info("---检测用户是否存在---");
        HashMap<String,String> user = wxMapper.getTuiGuangUserInfo(userInfo);
        if(CollectionUtils.isEmpty(user)) {
            logger.info("---用户在数据库中不存在，保存持久化---");
            String create_time =String.valueOf(wxMessage.getCreateTime());
            userInfo.put("time", create_time);
            userInfo.put("nickname",wxMpUser.getNickname());
            userInfo.put("headimgurl",wxMpUser.getHeadImgUrl());
            userInfo.put("sex", String.valueOf(wxMpUser.getSex()));
            userInfo.put("country", wxMpUser.getCountry());
            userInfo.put("city", wxMpUser.getCity());
            userInfo.put("province", wxMpUser.getProvince());
            userInfo.put("qrScene", wxMpUser.getQrScene());
            wxMapper.saveTuiGuangUserInfo(userInfo);
            logger.info("---保存用户成功---");
        }
        //绑定用户手机以及姓名 ：姓名+手机号的格式
        String content = wxMessage.getContent();
        logger.info("用户发过来的信息为=>"+content);
        if(StringUtils.isNotBlank(content)&&content.indexOf("+")!=-1 &&content.length()>13){
            int spl =content.indexOf("+");
            String username =content.substring(0,spl);
            String phone =content.substring(spl+1,content.length());
            boolean checkPhone= ToolsUtils.checkMobileNumber(phone);
            if(StringUtils.isEmpty(username)&&username.length()<2){
                //关注成功发送文本消息
                WxMpXmlOutTextMessage reText = WxMpXmlOutMessage.TEXT()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .content("姓名不正确哦~")
                        .build();
                return reText;
            }
            if(!checkPhone){//手机不合法
                //关注成功发送文本消息
                WxMpXmlOutTextMessage reText = WxMpXmlOutMessage.TEXT()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .content("手机号码不正确哦~")
                        .build();
                return reText;

            }
            logger.info("--开始绑定用户手机以及姓名--");
            userInfo.put("phone", phone);
            userInfo.put("name", username);
            wxMapper.updateTuiGuangUserInfo(userInfo);
            logger.info("--用户绑定成功--");
            WxMpXmlOutTextMessage reText = WxMpXmlOutMessage.TEXT()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .content("绑定成功~")
                    .build();
            return reText;
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

    public static void main(String[] args) {
      String content ="陈岳松+15034083689";
        int spl =content.indexOf("+");
        String username =content.substring(0,spl);
        String phone =content.substring(spl+1,content.length());
      System.out.println(username);
        System.out.println(phone);
    }
}
