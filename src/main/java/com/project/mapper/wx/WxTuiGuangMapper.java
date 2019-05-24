package com.project.mapper.wx;

import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WxTuiGuangMapper {
    int getTuiGuangTotal(PageData pd);

    List<Map<String, String>> getTuiGuangList(PageData pd);

    HashMap<String, String> getWxCodeTicket(PageData pd);
}
