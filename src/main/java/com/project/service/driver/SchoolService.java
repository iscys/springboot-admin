package com.project.service.driver;


import com.project.model.ResultObject;
import com.project.model.school.SchoolModel;
import com.project.utils.PageData;

public interface SchoolService {


    ResultObject getSchoolDetail(SchoolModel schoolModel)throws Exception;

    ResultObject getSchoolAlbum(SchoolModel schoolModel)throws Exception;

    ResultObject index(PageData pd)throws Exception;
}
