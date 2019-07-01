package com.project.controller.driver;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.project.service.driver.PayService;
import com.project.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pay")
public class PayController {
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private PayService payService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @PostMapping("/notify")
    public String notify(@RequestBody String xmlData) {

        logger.info("时间：{}接收到微信支付回调通知", DateUtils.stableTime());
        WxPayOrderNotifyResult notifyResult =null;
        try {
            notifyResult= wxPayService.parseOrderNotifyResult(xmlData);
        }catch (WxPayException py){
            logger.error("校验解析微信回调信息异常：{}",xmlData);
            return WxPayNotifyResponse.fail(py.getReturnMsg());
        }

        logger.info("开始处理微信回调,订单号:{} ",notifyResult.getOutTradeNo());
        try {
            payService.payNotify(notifyResult);
        }catch (Exception e){
            logger.error("处理回调异常：{},数据包：{}",e.getMessage(),xmlData);
        }
        return WxPayNotifyResponse.success("处理成功");
    }









    private String returnXML(String return_code) {

        return "<xml><return_code><![CDATA["

                + return_code

                + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }




}
