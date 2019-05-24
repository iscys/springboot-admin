package com.project.controller.wx;

import com.project.controller.BaseController;
import com.project.service.wx.WxTuiGuangService;
import com.project.utils.Layui;
import com.project.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/gzh")
public class WxTuiGuangController extends BaseController {

    @Autowired
    private WxTuiGuangService tuiGuangService;

    private Logger logger = LoggerFactory.getLogger(WxTuiGuangController.class);
    @RequestMapping("/skipTuiGuang")
    public ModelAndView skipTuiGuang(){
        logger.info("---进入微信推广页面---");
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/gzh/tuiguang");
        return mv;
    }


    @RequestMapping("/tuiguangList")
    @ResponseBody
    public Layui tiguangList(){

        PageData pd = this.getPageData();
        try {
            Layui ticketList = tuiGuangService.getTuiGuangList(pd);
            return ticketList;
        }catch (Exception e){
            return Layui.error();
        }

    }
    @RequestMapping("/qrcode")
    public ModelAndView showQrCode(){
        logger.info("---查看微信推广二维码---");
        PageData pd = this.getPageData();
        HashMap<String,String> ticket=tuiGuangService.getWxCodeTicket(pd);
        ModelAndView mv = this.getModelAndView();
        mv.addObject("ticket",ticket);
        mv.setViewName("page/gzh/qrcode");
        return mv;
    }
}
