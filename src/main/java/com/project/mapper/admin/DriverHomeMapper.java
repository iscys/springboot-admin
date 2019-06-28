package com.project.mapper.admin;


import com.project.model.school.Album;
import com.project.model.school.SchoolModel;
import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface DriverHomeMapper  {

    Integer getHomeCount(PageData pd);

    List<HashMap<String, String>> getHomeList(PageData pd);

    SchoolModel getSchoolDetail(PageData pd);

    void save(PageData pd);

    void saveSchoolAlbum(Album album);
}
