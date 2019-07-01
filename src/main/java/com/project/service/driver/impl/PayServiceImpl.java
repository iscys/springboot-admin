package com.project.service.driver.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.project.mapper.admin.OrderMapper;
import com.project.model.school.ErrorModel;
import com.project.model.school.Order;
import com.project.service.driver.OrderErrorService;
import com.project.service.driver.PayService;
import com.project.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PayServiceImpl implements PayService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    OrderErrorService error;
    /**
     * 微信支付回调通知
     * @param notifyResult
     * @throws Exception
     */
    @Override
    public void payNotify(WxPayOrderNotifyResult notifyResult) throws Exception {

        String outTradeNo = notifyResult.getOutTradeNo();
        Integer wxFee = notifyResult.getTotalFee();
        Order order =new Order();
        order.setOrder_sn(outTradeNo);
        Order rstOrder =orderMapper.getOrderDetil(order);






        if(null!=rstOrder){
            String price = rstOrder.getPrice();
            Integer dbFee = BaseWxPayRequest.yuanToFen(price);
            if(dbFee.equals(wxFee)){
            order.setStatus("1");//设置为已经付款并持久化
                order.setPay_time(DateUtils.getTimeInSecond());
                orderMapper.updateOrder(order);

            }








            else{
                logger.error("微信回调钱数与数据库价钱不一致，订单：{}微信：{},db:{}",outTradeNo,wxFee,dbFee);
                try {
                    ErrorModel errorModel = new ErrorModel(outTradeNo, "微信回调钱数与数据库价钱不一致", notifyResult.toString());
                    error.saveErrorLog(errorModel);
                }catch (Exception e){}
            }















        }else{
            logger.error("获取不到订单：{}信息",outTradeNo);
            try {
                ErrorModel errorModel = new ErrorModel(outTradeNo, "获取不到订单的信息", notifyResult.toString());
                error.saveErrorLog(errorModel);
            }catch (Exception e){}


        }

    }
}
