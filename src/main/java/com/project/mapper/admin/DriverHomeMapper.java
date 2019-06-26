package com.project.mapper.admin;


import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface DriverHomeMapper  {

    Integer getHomeCount(PageData pd);

    List<HashMap<String, String>> getHomeList(PageData pd);
}
