package com.project.service.admin.impl;

import com.project.mapper.admin.SubjectMapper;
import com.project.service.admin.SubjectService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public DataPager getSubjectList(PageData pd) {
        Integer total = subjectMapper.getSubjectCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= subjectMapper.getSubjectList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }
}
