package com.project.mapper.ocard;

import com.project.utils.PageData;

import java.util.List;
import java.util.Map;

public interface OcardMapper {
    
    int getOilTicketTotal(PageData pd);

    List<Map<String, String>> getOilTicketList(PageData pd);
}
