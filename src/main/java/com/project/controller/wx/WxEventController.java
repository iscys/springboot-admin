package com.project.controller.wx;
import com.project.service.wx.WxService;
import com.project.service.wx.impl.WxServiceImpl;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 登录：
 *
 *
 * 报名：
 *  报名信息+支付成功=>第三方报名系统后台
 *
 * 驾校配置：
 *  驾校坐标
 *  驾校相册
 *  驾校二维码
 *  驾校套餐
 *  天眼查
 *
 *
 *
 *
 *
 *
 *
 */
@Controller
@RequestMapping("/wx")
public class WxEventController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxService wxService;//实现方法
    private Logger logger = LoggerFactory.getLogger(WxEventController.class);
    /*
     * 该方法与微信后台配置的url进行对接，是get的请求,是微信开发的第一步
     * 微信后台会给我们接口传入signature，timestamp，nonce，echostr
     * 进行sha1 加密与signature比较,TRUE 则返回echostr给微信
     * author:cys
     */
    @RequestMapping(value = "/wxopen", method = RequestMethod.GET)
    public void wxopen(HttpServletResponse response, String signature, String timestamp,
                       String nonce, String echostr) throws IOException {

        PrintWriter writer = response.getWriter();

        response.setContentType("text/html;charset=utf-8");
        //此方法对微信的验证进行了封装--我们无需自己实现
        wxMpService.checkSignature(timestamp, nonce, signature);

        //验证返回false时候
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            writer.println("非法请求");
            writer.close();
            return;
        }
        //验证返回true时候，返回微信echostr
        if (StringUtils.isNotBlank(echostr)) {
            // 说明是一个仅仅用来验证的请求，回显echostr
            writer.println(echostr);
            writer.close();
            return;
        } else {
            writer.close();
            return;
        }
    }

    @RequestMapping(value = "/wxopen", method = RequestMethod.POST)
    public void wxopen(HttpServletRequest request, HttpServletResponse response)throws Exception{
        logger.info("----接收到了微信的请求---");
        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        wxService.wxSubQrcode(wxMpXmlMessage);
        WxMpXmlOutMessage wxMpXmlOutMessage = wxService.toRouter(wxMpXmlMessage);
        response.setContentType("text/html;charset=utf-8");
        //发送文本消息
        PrintWriter writer = response.getWriter();
        if(wxMpXmlOutMessage!=null) {
            writer.write(wxMpXmlOutMessage.toXml());
        }
        writer.close();
    }




}
