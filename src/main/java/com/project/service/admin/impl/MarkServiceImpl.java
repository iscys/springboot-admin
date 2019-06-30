package com.project.service.admin.impl;

import com.project.mapper.admin.MarkMapper;
import com.project.model.ResultObject;
import com.project.model.school.Mark;
import com.project.service.admin.MarkService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class MarkServiceImpl implements MarkService {

    @Autowired
    private MarkMapper markMapper;
    @Override
    public DataPager getMarkList(PageData pd) {
        Integer total = markMapper.getMarkCount(pd);
        DataPager dataPager = DataPager.page_self(1,50,pd,total);
        List<HashMap<String,String>> result= markMapper.getMarkList(pd);
        dataPager.setRecords(result);
        dataPager.setFormId("Form");
        return dataPager;
    }

    @Override
    public Mark getMarkDetail(Mark mark) {
        return markMapper.getMarkDetail(mark);
    }

    @Override
    public ResultObject saveMark(PageData pd) throws Exception{
        if(null ==pd.get("id")) {
            markMapper.saveMark(pd);
        }else{
            markMapper.updateMark(pd);
        }
        return ResultObject.success(null);

    }

}
