package com.project.mapper.admin;

import com.project.model.school.Banner;
import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface BannerMapper {
    List<Banner> getAllBanner(Banner banner);

    Integer getBannerCount(PageData pd);

    List<HashMap<String, String>> getBannerList(PageData pd);

    Banner getBannerDetail(Banner banner);

    void saveBanner(PageData pd);

    void updateBanner(PageData pd);
}
