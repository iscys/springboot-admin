package com.project.service.admin;

import com.project.model.school.SchoolModel;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.web.multipart.MultipartFile;


public interface DriverHomeService  {

    DataPager getHomeList(PageData pd);

    SchoolModel getSchoolDetail(PageData pd);

    PageData save(PageData pd, MultipartFile school_icon, MultipartFile school_face, MultipartFile[] files) throws Exception;
}
