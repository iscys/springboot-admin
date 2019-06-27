package com.project.service.admin.impl;

import com.project.mapper.admin.DriverHomeMapper;
import com.project.model.school.SchoolModel;
import com.project.service.admin.DriverHomeService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DriverHomeServiceImpl implements DriverHomeService {

    @Autowired
    private DriverHomeMapper homeMapper;
    /**
     * 驾校列表信息
     * @param pd
     * @return
     */
    @Override
    public DataPager getHomeList(PageData pd) {
        Integer total =homeMapper.getHomeCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= homeMapper.getHomeList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public SchoolModel getSchoolDetail(PageData pd) {
       SchoolModel model= homeMapper.getSchoolDetail(pd);
        return model;
    }
}
