package com.project.service.wx.impl;

import com.project.mapper.wx.WxTuiGuangMapper;
import com.project.service.wx.WxTuiGuangService;
import com.project.utils.DataPager;
import com.project.utils.Layui;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxTuiGuangServiceImpl implements WxTuiGuangService {

    @Autowired
    private WxTuiGuangMapper mapper;
    /**
     * 得到推广信息列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Layui getTuiGuangList(PageData pd) throws Exception {

        int  total =mapper.getTuiGuangTotal(pd);
        DataPager dp =DataPager.page(1,50,pd,total);
        List<Map<String,String>> tuiGuangUserInfo=mapper.getTuiGuangList(pd);
        return Layui.success(dp.getTotalRecords(),tuiGuangUserInfo);
    }

    @Override
    public HashMap<String, String> getWxCodeTicket(PageData pd) {
        HashMap<String,String> ticket =mapper.getWxCodeTicket(pd);
        if(!CollectionUtils.isEmpty(ticket)){
          String wxCode = ticket.get("ticket");
          ticket.put("ticket","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+wxCode);
        }
        return ticket;
    }
}
