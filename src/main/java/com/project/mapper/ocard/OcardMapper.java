package com.project.mapper.ocard;

import com.project.model.OilCardTicket;
import com.project.utils.PageData;

import java.util.List;
import java.util.Map;

public interface OcardMapper {
    
    int getOilTicketTotal(PageData pd);

    List<Map<String, String>> getOilTicketList(PageData pd);

    List<OilCardTicket> getOcardTicketList(PageData pd);

    int getOcardTicketTotal(PageData pd);

    OilCardTicket getOcardTicketInfo(PageData pd);
}
