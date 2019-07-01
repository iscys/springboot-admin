package com.project.service.driver.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.project.config.ConfigProperties;
import com.project.mapper.admin.ApplyMapper;
import com.project.mapper.admin.OrderMapper;
import com.project.model.school.Apply;
import com.project.model.school.ErrorModel;
import com.project.model.school.Order;
import com.project.service.driver.OrderErrorService;
import com.project.service.driver.PayService;
import com.project.utils.DateUtils;
import com.project.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class PayServiceImpl implements PayService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    OrderErrorService error;
    @Autowired
    private ConfigProperties properties;
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
                Apply apply =new Apply();

                apply.setId(Integer.valueOf(order.getApply_id()));
                Apply applys =applyMapper.getDeatilApply(apply);

                HashMap<String,String> map  =new HashMap<>();
                map.put("idcard",applys.getIdcard());
                map.put("name",applys.getName());
                map.put("cardtype",String.valueOf(applys.getCardtype()));
                map.put("sex",String.valueOf(applys.getSex()));
                map.put("phone",applys.getPhone());
                map.put("addr",applys.getAddr());
                map.put("traintype",applys.getTraintype());
                map.put("applydate",applys.getApplydate());
                map.put("orgcode",applys.getOrgcode());
                map.put("photo",apply.getPhoto());
                String result = HttpUtils.INSTANCE.doPost(properties.getApplyUrl(), map);
                System.err.println(result);

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
