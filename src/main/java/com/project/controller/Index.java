package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class Index {



    @RequestMapping("/user")
    public ModelAndView user(){

        ModelAndView mv =new ModelAndView();
        mv.setViewName("user");
        return mv;
    }

    @RequestMapping("/set")
    public ModelAndView set(){

        ModelAndView mv =new ModelAndView();
        mv.setViewName("set");
        return mv;
    }

    @RequestMapping("/home")
    public ModelAndView home(){

        ModelAndView mv =new ModelAndView();
        mv.setViewName("home");
        return mv;
    }
}
