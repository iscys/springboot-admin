package com.project.service.admin.impl;

import com.project.mapper.admin.MarkMapper;
import com.project.model.ResultObject;
import com.project.model.school.Mark;
import com.project.service.admin.MarkService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class MarkServiceImpl implements MarkService, InitializingBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HashMap<String,String> markList;

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

    @Override
    public List<Mark> allMark(PageData pd){

        return markMapper.getAllMark(pd);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HashMap<String,String> marikContains =new HashMap<String,String>();
        List<Mark> allMark = markMapper.getAllMark(new PageData());
        if(!CollectionUtils.isEmpty(allMark)){
            for(Mark m:allMark){
                marikContains.put(m.getId(),m.getMark());
            }
        }

        markList=marikContains;
        logger.info("标签cache 共{}条数据",markList.size());
    }


}
