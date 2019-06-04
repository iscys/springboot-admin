package com.project.controller.ocard;

import com.project.controller.BaseController;
import com.project.service.ocard.OcardService;
import com.project.utils.Layui;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OcardService ocardService;

    /**
     * 油券路由
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView orderList(){
        logger.info("--油券路由---");
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/oilcard/ocard");
        return mv;
    }

    /**
     * 实体卡路由
     * @return ModelAndView
     */
    @RequestMapping("/ticketIndex")
    public ModelAndView ocardTicket(){
        logger.info("--油券实体卡路由---");
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/oilcard/ocardTicket");
        return mv;
    }

    /**
     * 油券列表
     * @return
     */
    @RequestMapping("/ocardList")
    @ResponseBody
    public Layui ocardList(){
        logger.info("--油券列表---");
        PageData pd = this.getPageData();
        Layui ticketList= ocardService.getOilTicketList(pd);

        return ticketList;
    }



    /**
     * 油券实体卡列表
     * @return
     */
    @RequestMapping("/ocardTicketList")
    @ResponseBody
    public Layui ocardTicketList(){
        logger.info("--油券实体卡列表---");
        PageData pd = this.getPageData();
        Layui ticketList= ocardService.getOcardTicketList(pd);
        return ticketList;
    }

}
