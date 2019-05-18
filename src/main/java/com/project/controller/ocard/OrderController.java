package com.project.controller.ocard;

import com.project.controller.BaseController;
import com.project.utils.Layui;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ocard")
public class OrderController extends BaseController {

    @RequestMapping("/orderList")
    public ModelAndView orderList(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("page/oilcard/orderList");
        return mv;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Layui list(){
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
