package com.project.service.admin;


import com.project.model.ResultObject;
import com.project.model.school.Banner;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {

    DataPager getBannerList(PageData pd);

    Banner getBannerDetail(Banner banner);

    ResultObject saveBanner(MultipartFile banner,PageData pd) throws Exception;
}