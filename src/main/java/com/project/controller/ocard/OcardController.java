package com.project.controller.ocard;

import com.project.controller.BaseController;
import com.project.model.OilCardTicket;
import com.project.model.ResultObject;
import com.project.service.ocard.OcardService;
import com.project.utils.DataPager;
import com.project.utils.Layui;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
        logger.info("--油券列表---");
        PageData pd = this.getPageData();
        DataPager dataPager= ocardService.getOilTicketList(pd);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/oilcard/ocard");
        return mv;
    }


    /**
     * 油券路由
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addInfo(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/oilcard/ocard_add");
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

    /**
     * 油券实体卡信息
     * @return
     */
    @RequestMapping("/ocardTicketInfo")
    @ResponseBody
    public ModelAndView ocardTicketInfo(){
        logger.info("--油券实体卡详情---");
        PageData pd = this.getPageData();
        ModelAndView mv = this.getModelAndView();
        OilCardTicket ticket = ocardService.getOcardTicketInfo(pd);
        mv.addObject("ticket",ticket);
        mv.setViewName("page/oilcard/ocardTicketInfo");
        return mv;
    }

    @PostMapping("/updateExpress")
    @ResponseBody
    public ResultObject updateExpress(){
        logger.info("--更新配送人信息---");
        PageData pd = this.getPageData();
        ModelAndView mv = this.getModelAndView();
        try {
            ResultObject result = ocardService.updateExpress(pd);
            return result;
        }catch(Exception e){
            return ResultObject.error(null);
        }


    }


    /**
     * 油券路由
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public String update(MultipartFile[] files,MultipartFile file,String name,HttpServletRequest req){
        System.out.println("111");
        System.out.println(file.getResource().getFilename());

        return "111111";

    }

}
