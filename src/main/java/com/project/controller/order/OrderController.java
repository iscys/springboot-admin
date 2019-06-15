package com.project.controller.order;

import com.project.controller.BaseController;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @RequestMapping("/index")
    public ModelAndView index(){
        DataPager dataPager=new DataPager(1,890,50);
        dataPager.setRecords(1);
        dataPager.setFormId("Form");
        ModelAndView mv = this.getModelAndView();
        mv.addObject("dataPager",dataPager);
        mv.setViewName("page/order/orderList");
        return mv;
    }

}
