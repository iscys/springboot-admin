package com.project.controller.ocard;

import com.project.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ocard")
public class OrderController extends BaseController {

    @RequestMapping("/orderList")
    public ModelAndView orderList(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/oilcard/orderList");
        return mv;
    }

}
