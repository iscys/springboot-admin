package com.project.service.wx;

import com.project.utils.Layui;
import com.project.utils.PageData;

import java.util.HashMap;

public interface WxTuiGuangService {
    Layui getTuiGuangList(PageData pd) throws Exception;

    HashMap<String, String> getWxCodeTicket(PageData pd);
}
