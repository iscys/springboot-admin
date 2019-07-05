package com.project.service.driver;


import com.project.model.ResultObject;
import com.project.model.school.FeedBackSchool;
import com.project.model.school.SchoolModel;
import com.project.model.school.Teacher;
import com.project.utils.PageData;

public interface SchoolService {


    ResultObject getSchoolDetail(SchoolModel schoolModel)throws Exception;

    ResultObject getSchoolAlbum(SchoolModel schoolModel)throws Exception;

    ResultObject index(PageData pd)throws Exception;

    ResultObject driverList(PageData pd)throws Exception;

    ResultObject subjectList(PageData pd) throws Exception;

    ResultObject teacherList(PageData pd) throws Exception;

    ResultObject getTeacherDetail(Teacher teacher) throws Exception;

    ResultObject saveSchoolFeedback(FeedBackSchool fda) throws Exception;

    ResultObject getSchoolFeedbackList(PageData pd) throws Exception;
}
