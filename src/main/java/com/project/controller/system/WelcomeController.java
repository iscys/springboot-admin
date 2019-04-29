package com.project.controller.system;
import com.project.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@PropertySource(value = {"classpath:custConfig.properties"})
public class WelcomeController extends BaseController {

    @Value("${person.name}")
    private String name;
    @RequestMapping("/")
    public ModelAndView welCome(){
        System.out.println(name);
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("index");
        return mv;
    }
}
