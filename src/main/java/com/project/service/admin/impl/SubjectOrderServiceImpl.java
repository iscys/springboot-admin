package com.project.service.admin.impl;

import com.project.mapper.admin.OrderMapper;
import com.project.mapper.admin.SubjectOrderMapper;
import com.project.service.admin.OrderService;
import com.project.service.admin.SubjectOrderService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SubjectOrderServiceImpl implements SubjectOrderService {

    @Autowired
    private SubjectOrderMapper orderMapper;
    @Override
    public DataPager getOrderList(PageData pd) {
        Integer total = orderMapper.getOrderCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= orderMapper.getOrderList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }


}
