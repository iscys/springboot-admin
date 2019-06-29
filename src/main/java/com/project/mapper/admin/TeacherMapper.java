package com.project.mapper.admin;

import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface TeacherMapper {
    Integer getTeacherCount(PageData pd);

    List<HashMap<String, String>> getTeacherList(PageData pd);

    void saveTeacher(PageData pd);

    void updateTeacher(PageData pd);
}
