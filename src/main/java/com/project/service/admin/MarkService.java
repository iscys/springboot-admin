package com.project.service.admin;

import com.project.model.ResultObject;
import com.project.model.school.Mark;
import com.project.utils.DataPager;
import com.project.utils.PageData;

import java.util.List;

public interface MarkService {
    DataPager getMarkList(PageData pd);

    Mark getMarkDetail(Mark mark);

    ResultObject saveMark(PageData pd)throws Exception;

    List<Mark> allMark(PageData pd);
}
