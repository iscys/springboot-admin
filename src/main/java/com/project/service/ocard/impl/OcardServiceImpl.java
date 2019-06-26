package com.project.service.ocard.impl;

import com.project.mapper.ocard.OcardMapper;
import com.project.model.OilCardTicket;
import com.project.model.ResultObject;
import com.project.service.ocard.OcardService;
import com.project.utils.DataPager;
import com.project.utils.DateUtils;
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
    public DataPager getOilTicketList(PageData pd) {
        int total = ocardMapper.getOilTicketTotal(pd);
        DataPager dp = DataPager.page_self(1, 50, pd, total);
        List<Map<String, String>> ticketList = ocardMapper.getOilTicketList(pd);
        if (!CollectionUtils.isEmpty(ticketList)) {
            for (Map<String, String> ticket : ticketList) {
                String used_time = String.valueOf(ticket.get("used_time"));
                if (used_time.equals("0")) {
                    ticket.put("used_time", "未使用");
                } else {
                    ticket.put("used_time", DateUtils.secondamp2date(Long.valueOf(used_time)));
                }
            }
        }

        dp.setRecords(ticketList);
        return dp;
    }
    /**
     * 油券实体卡列表
     * @param pd
     * @return
     */
    @Override
    public Layui getOcardTicketList(PageData pd) {
        int total=ocardMapper.getOcardTicketTotal(pd);
        if(total ==0){
            return Layui.empty();
        }
        DataPager dp =DataPager.page(1,50,pd,total);
        List<OilCardTicket> ticketList=ocardMapper.getOcardTicketList(pd);
        return  Layui.success(dp.getTotalRecords(),ticketList);
    }

    @Override
    public OilCardTicket getOcardTicketInfo(PageData pd) {
        return ocardMapper.getOcardTicketInfo(pd);
    }

    @Override
    public ResultObject updateExpress(PageData pd) {
        ocardMapper.updateExpress(pd);
        return ResultObject.success(null);
    }
}
