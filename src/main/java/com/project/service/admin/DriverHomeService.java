package com.project.service.admin;

import com.project.controller.BaseController;
import com.project.model.school.SchoolModel;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


public interface DriverHomeService  {

    DataPager getHomeList(PageData pd);

    SchoolModel getSchoolDetail(PageData pd);
}
