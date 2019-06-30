package com.project.service.admin.impl;

import com.project.mapper.admin.SubjectMapper;
import com.project.model.ResultObject;
import com.project.model.school.Subject;
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

    @Override
    public ResultObject saveSubject(PageData pd) throws Exception {
        if(null ==pd.get("id")) {
            subjectMapper.saveSubject(pd);
        }else{
            subjectMapper.updateSubject(pd);
        }
        return ResultObject.success(null);

    }

    @Override
    public Subject getSubjectDetail(Subject subject) {

        return subjectMapper.getSubjectDetail(subject);
    }
}
