package com.project.service.admin;


import com.project.model.ResultObject;
import com.project.model.school.SchoolModel;
import com.project.model.school.Teacher;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService  {

    DataPager getTeacherList(PageData pd);

    List<SchoolModel> getAllSchoolList(PageData pd);

    ResultObject saveTeacher(MultipartFile teacher_img, PageData pd)throws Exception;

    Teacher getTeacherDetail(Teacher teacher);
}
