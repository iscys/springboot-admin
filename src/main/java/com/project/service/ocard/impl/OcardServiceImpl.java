package com.project.service.ocard.impl;

import com.project.mapper.ocard.OcardMapper;
import com.project.service.ocard.OcardService;
import com.project.utils.DataPager;
import com.project.utils.Layui;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class OcardServiceImpl implements OcardService {

    @Autowired
    private OcardMapper ocardMapper;

    /**
     * 油券列表
     * @param pd
     * @return
     */
    @Override
    public Layui getOilTicketList(PageData pd) {

        int total=ocardMapper.getOilTicketTotal(pd);
        DataPager dp =DataPager.page(1,50,pd,total);
        List<Map<String,String>> ticketList=ocardMapper.getOilTicketList(pd);
        if(!CollectionUtils.isEmpty(ticketList)){
            return Layui.success(dp.getTotalRecords(),ticketList);
        }else{
            return Layui.empty();
        }

    }
}
