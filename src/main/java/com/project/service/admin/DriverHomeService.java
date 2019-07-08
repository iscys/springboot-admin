package com.project.service.admin;

import com.project.model.school.SchoolModel;
import com.project.model.school.SchoolSupport;
import com.project.model.school.SubjectType;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface DriverHomeService  {

    DataPager getHomeList(PageData pd);

    SchoolModel getSchoolDetail(SchoolModel schoolModel);

    PageData save(PageData pd, MultipartFile school_icon, MultipartFile school_face, MultipartFile[] files) throws Exception;

    List<SubjectType> allSubjectType();

    SchoolSupport getSchoolSupport(SchoolSupport tmpSupport);
}
