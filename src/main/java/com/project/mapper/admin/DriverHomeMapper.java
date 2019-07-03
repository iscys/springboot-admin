package com.project.mapper.admin;


import com.project.model.school.Album;
import com.project.model.school.SchoolModel;
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
}
