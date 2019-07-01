package com.project.service.driver;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;

public interface PayService {
    void payNotify(WxPayOrderNotifyResult notifyResult) throws Exception;
}
