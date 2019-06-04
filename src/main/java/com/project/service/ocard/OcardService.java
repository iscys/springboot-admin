package com.project.service.ocard;

import com.project.model.OilCardTicket;
import com.project.utils.Layui;
import com.project.utils.PageData;

import java.util.List;

public interface OcardService {

    Layui getOilTicketList(PageData pd);

    Layui getOcardTicketList(PageData pd);

    OilCardTicket getOcardTicketInfo(PageData pd);
}
