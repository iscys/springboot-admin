package com.project.mapper.admin;


import com.project.model.school.*;
import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface DriverHomeMapper  {

    Integer getHomeCount(PageData pd);

    List<HashMap<String, Object>> getHomeList(PageData pd);

    SchoolModel getSchoolDetail(SchoolModel sch);

    void save(PageData pd);

    void saveSchoolAlbum(Album album);

    void updateShool(PageData pd);

    List<Album> getSchoolAlbum(Album album);

    List<SchoolModel> getAllSchoolList(PageData pd);

    SchoolModel getSimpleSchool(SchoolModel schoolModel);

    void saveSchoolFeedBack(FeedBackSchool fda);

    int getSchoolFeedBackCount(PageData pd);

    List<HashMap<String, String>> getSchoolFeedBackList(PageData pd);

    List<HashMap<String, String>> getSchoolSubFeedBackList(PageData pd);

    void saveTeacherFeedBack(FeedBackTeacher fda);

    List<HashMap<String, String>> getTeacherSubFeedBackList(PageData pd);

    List<HashMap<String, String>> getTeacherFeedBackList(PageData pd);

    int getTeacherFeedBackCount(PageData pd);

    List<SubjectType> getAllSubjectType();

    void saveSubjectSupport(SchoolSupport support);

    void updateSubjectSupport(SchoolSupport support);

    SchoolSupport getSchoolSupport(SchoolSupport tmpSupport);
}
