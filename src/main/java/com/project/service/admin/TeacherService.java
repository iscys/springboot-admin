package com.project.service.admin;


import com.project.model.school.SchoolModel;
import com.project.utils.DataPager;
import com.project.utils.PageData;

import java.util.List;

public interface TeacherService  {

    DataPager getTeacherList(PageData pd);

    List<SchoolModel> getAllSchoolList(PageData pd);
}
