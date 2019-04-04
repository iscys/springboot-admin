package com.project.controller.system;
import com.project.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class WelcomeController extends BaseController {


    @RequestMapping("/")
    public ModelAndView welCome(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("index");
        return mv;
    }
}
