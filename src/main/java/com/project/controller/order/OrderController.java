package com.project.controller.order;

import com.project.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/order/orderList");
        return mv;
    }

}
