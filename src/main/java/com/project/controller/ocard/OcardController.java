package com.project.controller.ocard;

import com.project.controller.BaseController;
import com.project.service.ocard.OcardService;
import com.project.utils.Layui;
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


    @Autowired
    private OcardService ocardService;

    @RequestMapping("/index")
    public ModelAndView orderList(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/oilcard/ocard");
        return mv;
    }

    @RequestMapping("/ocardList")
    @ResponseBody
    public Layui ocardList(){


        List<Map<String,String>> data= new ArrayList<>();
        HashMap<String,String> map =new HashMap<String,String>();
        map.put("id","1");
        map.put("username","111");
        map.put("experience","90");
        map.put("sex","那么");
        data.add(map);
        return Layui.success(1,data);
    }

}
