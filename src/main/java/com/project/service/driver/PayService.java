package com.project.service.driver;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.project.model.ResultObject;
import com.project.model.school.Order;

public interface PayService {
    void payNotify(WxPayOrderNotifyResult notifyResult) throws Exception;

    ResultObject createPay(Order order) throws Exception;

    ResultObject createPaySubject(Order order) throws Exception;
}
