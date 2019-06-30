package com.project.mapper.admin;

import com.project.model.ResultObject;
import com.project.model.school.Subject;
import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface SubjectMapper {
    Integer getSubjectCount(PageData pd);

    List<HashMap<String, String>> getSubjectList(PageData pd);

     void saveSubject(PageData pd);

    void updateSubject(PageData pd);

    Subject getSubjectDetail(Subject subject);
}
