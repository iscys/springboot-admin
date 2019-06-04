package com.project.controller.system;
import com.project.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class WelcomeController extends BaseController {

    /**
     * 首页欢迎页
     * @return
     */
    @RequestMapping("/")
    public String welCome(){
        return "redirect:/guide/index";
    }
}
