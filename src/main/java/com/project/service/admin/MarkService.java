package com.project.service.admin;

import com.project.model.school.Mark;
import com.project.utils.DataPager;
import com.project.utils.PageData;

public interface MarkService {
    DataPager getMarkList(PageData pd);

    Mark getMarkDetail(Mark mark);
}
