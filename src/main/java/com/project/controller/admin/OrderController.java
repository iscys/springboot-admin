package com.project.controller.admin;

import com.project.controller.BaseController;
import com.project.model.school.SchoolModel;
import com.project.service.admin.MarkService;
import com.project.service.admin.OrderService;
import com.project.service.admin.TeacherService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/index")
    public ModelAndView markList(HttpServletRequest req){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        DataPager dataPager= orderService.getOrderList(pd);
        List<SchoolModel> slist= teacherService.getAllSchoolList(pd);
        mv.addObject("list",slist);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/order/order_list");
        return mv;
    }
}
