package com.project.service.driver.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.project.config.ConfigProperties;
import com.project.mapper.admin.ApplyMapper;
import com.project.mapper.admin.OrderMapper;
import com.project.mapper.admin.SubjectOrderMapper;
import com.project.mapper.driver.UserMapper;
import com.project.model.Const;
import com.project.model.ResultObject;
import com.project.model.school.*;
import com.project.service.driver.OrderErrorService;
import com.project.service.driver.PayService;
import com.project.utils.DateUtils;
import com.project.utils.GsonUtils;
import com.project.utils.HttpUtils;
import com.project.utils.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private OrderErrorService errorService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SubjectOrderMapper subjectOrderMapper;



    @Override
    public ResultObject createPay(Order order) throws Exception {
        Order orderInfo = orderMapper.getOrderDetil(order);
        String member_id = orderInfo.getMember_id();
        if(!member_id.equals(order.getMember_id())){
            return ResultObject.build(Const.MEMBER_ERROR, Const.MEMBER_ERROR_MESSAGE,null);

        }

        if(orderInfo==null){
            return ResultObject.build(Const.ORDERINFO_NULL, Const.ORDERINFO_NULL_MESSAGE,null);
        }
        String order_sn = orderInfo.getOrder_sn();
        if(!orderInfo.getStatus().equals("0")){
            logger.warn("订单：{} 状态错误，无法进行微信平台下单");

            try {
                ErrorModel model = new ErrorModel(order_sn, "订单状态错误", orderInfo.toString());
                errorService.saveErrorLog(model);
            }catch (Exception e){}

            return ResultObject.build(Const.ORDER_STATUS_ERROR,Const.ORDER_STATUS_ERROR_MESSAGE,null);


        }
        User user =new User();
        user.setMember_id(member_id);
        User userInfo = userMapper.getUserInfo(user);
        if(null==userInfo){
            return ResultObject.build(Const.MEMBER_NULL,Const.MEMBER_NULL_MESSAGE,null);
        }
        user=null;
        String openid =userInfo.getOpenid();
        logger.info("订单：{} 开始组装微信支付信息",order_sn);

        WxPayUnifiedOrderRequest payOrder =new WxPayUnifiedOrderRequest();
        payOrder.setSpbillCreateIp(order.getClientIp());

        payOrder.setTradeType(WxPayConstants.TradeType.JSAPI);//小程序公众号支付

        payOrder.setOutTradeNo(order.getOrder_sn());
        payOrder.setOpenid(openid);
        payOrder.setNotifyUrl(properties.getDomain()+"/api/pay/notify");

        payOrder.setBody("驾校报名");

        payOrder.setAttach("驾校报名");
        payOrder.setTotalFee(BaseWxPayRequest.yuanToFen(orderInfo.getPrice()));
        Object wxPackage = null;
        try {
            wxPackage = wxPayService.createOrder(payOrder);
        } catch (WxPayException e) {
            logger.error("时间：{} ,订单号：{}微信统一下单失败,reason:{}", DateUtils.stableTime(),order_sn,e.getMessage());
            return ResultObject.build(Const.WX_PAY_EXCEPTION,Const.WX_PAY_EXCEPTION_MESSAGE,e.getMessage());
        }

        return ResultObject.success(wxPackage);
    }

    /**
     * 购买课时支付
     * @param order
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject createPaySubject(Order order) throws Exception {

        Order orderInfo = subjectOrderMapper.getOrderDetil(order);
        String member_id = orderInfo.getMember_id();
        if(!member_id.equals(order.getMember_id())){
            return ResultObject.build(Const.MEMBER_ERROR, Const.MEMBER_ERROR_MESSAGE,null);

        }

        if(orderInfo==null){
            return ResultObject.build(Const.ORDERINFO_NULL, Const.ORDERINFO_NULL_MESSAGE,null);
        }
        String order_sn = orderInfo.getOrder_sn();
        if(!orderInfo.getStatus().equals("0")){
            logger.warn("订单：{} 状态错误，无法进行微信平台下单");

            try {
                ErrorModel model = new ErrorModel(order_sn, "订单状态错误", orderInfo.toString());
                errorService.saveErrorLog(model);
            }catch (Exception e){}

            return ResultObject.build(Const.ORDER_STATUS_ERROR,Const.ORDER_STATUS_ERROR_MESSAGE,null);


        }
        User user =new User();
        user.setMember_id(member_id);
        User userInfo = userMapper.getUserInfo(user);
        if(null==userInfo){
            return ResultObject.build(Const.MEMBER_NULL,Const.MEMBER_NULL_MESSAGE,null);
        }
        user=null;
        String openid =userInfo.getOpenid();
        logger.info("订单：{} 开始组装微信支付信息",order_sn);

        WxPayUnifiedOrderRequest payOrder =new WxPayUnifiedOrderRequest();
        payOrder.setSpbillCreateIp(order.getClientIp());

        payOrder.setTradeType(WxPayConstants.TradeType.JSAPI);//小程序公众号支付

        payOrder.setOutTradeNo(order.getOrder_sn());
        payOrder.setOpenid(openid);
        payOrder.setNotifyUrl(properties.getDomain()+"/api/pay/notify");

        payOrder.setBody("课时购买");

        payOrder.setAttach("课时购买");
        payOrder.setTotalFee(BaseWxPayRequest.yuanToFen(orderInfo.getPrice()));
        Object wxPackage = null;
        try {
            wxPackage = wxPayService.createOrder(payOrder);
        } catch (WxPayException e) {
            logger.error("时间：{} ,订单号：{}微信统一下单失败,reason:{}", DateUtils.stableTime(),order_sn,e.getMessage());
            return ResultObject.build(Const.WX_PAY_EXCEPTION,Const.WX_PAY_EXCEPTION_MESSAGE,e.getMessage());
        }

        return ResultObject.success(wxPackage);
    }


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


                //通知第三方进行学员信息的录入
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
                if(applys.getCardtype()!=1){
                    map.put("birdate",apply.getBirdate());

                }

                String result = HttpUtils.INSTANCE.doPost(properties.getApplyUrl(), map);

                ThirdResult thirdResult = GsonUtils.fromJson(result, ThirdResult.class);




                //{Code :1 ,Message:success}
                if(StringUtils.isEmpty(thirdResult.getCode()) || !thirdResult.isSuccess()){
                    logger.error("学员信息插入第三方库失败，信息：{}",result);
                    //todo refund 第三方库插入失败，执行退款操作
                        logger.info("---微信退款--");
                        WxPayRefundRequest refund = new WxPayRefundRequest();
                        refund.setOutTradeNo(outTradeNo);
                        refund.setTotalFee(wxFee);
                        refund.setRefundFee(wxFee);
                        refund.setOutRefundNo(DateUtils.getTimeInMillis()+ ToolsUtils.sixCode());
                        try {
                            wxPayService.refund(refund);
                            logger.info("订单：{} 退款成功",outTradeNo);
                        }catch (Exception re){
                            logger.error("订单号:{} 退款异常：{}",outTradeNo,re.getMessage());

                            try {
                                ErrorModel model = new ErrorModel(outTradeNo, "退款异常失败", re.getMessage(),null);
                                errorService.saveErrorLog(model);
                            }catch (Exception e){}


                        }


                }else{
                    //success
                    try {
                        ErrorModel model = new ErrorModel(outTradeNo, "微信支付成功,并且学员信息录入成功", null);
                        errorService.saveErrorLog(model);
                    }catch (Exception e){}

                }
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
