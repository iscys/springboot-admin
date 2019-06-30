package com.project.mapper.admin;

import com.project.model.school.Mark;
import com.project.utils.DataPager;
import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface MarkMapper {
    Integer getMarkCount(PageData pd);

    List<HashMap<String, String>> getMarkList(PageData pd);

    Mark getMarkDetail(Mark mark);

    void updateMark(PageData pd);

    void saveMark(PageData pd);
}
