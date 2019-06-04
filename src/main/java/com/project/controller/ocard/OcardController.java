package com.project.controller.ocard;

import com.project.controller.BaseController;
import com.project.service.ocard.OcardService;
import com.project.utils.Layui;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ocard")
public class OcardController extends BaseController {


    @Autowired
    private OcardService ocardService;

    @RequestMapping("/index")
    public ModelAndView orderList(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/oilcard/ocard");
        return mv;
    }

    /**
     * 实体卡
     * @return
     */
    @RequestMapping("/ticketIndex")
    public ModelAndView ocardTicketList(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/oilcard/ocardTicket");
        return mv;
    }

    @RequestMapping("/ocardList")
    @ResponseBody
    public Layui ocardList(){

        PageData pd = this.getPageData();
        Layui ticketList= ocardService.getOilTicketList(pd);

        return ticketList;
    }

}
