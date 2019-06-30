package com.project.service.admin;

import com.project.model.ResultObject;
import com.project.model.school.Subject;
import com.project.utils.DataPager;
import com.project.utils.PageData;

public interface SubjectService {
    DataPager getSubjectList(PageData pd) ;

    ResultObject saveSubject(PageData pd) throws Exception;

    Subject getSubjectDetail(Subject subject);
}
