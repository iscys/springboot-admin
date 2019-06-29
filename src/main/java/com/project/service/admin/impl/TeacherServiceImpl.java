package com.project.service.admin.impl;


import com.project.mapper.admin.TeacherMapper;
import com.project.service.admin.TeacherService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public DataPager getTeacherList(PageData pd) {
        Integer total = teacherMapper.getTeacherCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= teacherMapper.getTeacherList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }
}
