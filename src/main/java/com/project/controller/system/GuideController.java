package com.project.controller.system;
import com.google.zxing.common.BitMatrix;
import com.project.common.qrcode.MatrixToImageWriter;
import com.project.common.qrcode.QrcodeUtils;
import com.project.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@RequestMapping("guide")
public class GuideController extends BaseController {

    @RequestMapping("/index")
    public ModelAndView test(){

        ModelAndView mv =new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/main")
    public ModelAndView main(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/main");
        return mv;
    }



    @RequestMapping("/login")
    public ModelAndView toLogin(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/login/login");
        return mv;
    }

    @RequestMapping("/qrcode")
    public void createQcode(HttpServletResponse response) throws Exception{

        String content ="http://www.baidu.com";
        QrcodeUtils.qrcodeToOutputStream(content,250,"jpg",response.getOutputStream());
    }


}
