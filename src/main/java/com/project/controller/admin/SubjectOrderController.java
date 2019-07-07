package com.project.controller.admin;

import com.project.controller.BaseController;
import com.project.model.Const;
import com.project.model.User;
import com.project.model.school.SchoolModel;
import com.project.service.admin.OrderService;
import com.project.service.admin.SubjectOrderService;
import com.project.service.admin.TeacherService;
import com.project.utils.DataPager;
import com.project.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
@RequestMapping("/subjectOrder")
public class SubjectOrderController extends BaseController {

    @Autowired
    private SubjectOrderService orderService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/index")
    public ModelAndView markList(HttpServletRequest req, HttpSession session){

        PageData pd = this.getPageData();
        User user = (User) session.getAttribute(Const.USER);
        String school_id = user.getSchool_id();

        if(school_id.equals("0")){

        }else{
            pd.put("school_id",school_id);
        }

        ModelAndView mv = this.getModelAndView();
        DataPager dataPager= orderService.getOrderList(pd);
        List<SchoolModel> slist= teacherService.getAllSchoolList(pd);
        mv.addObject("list",slist);
        mv.addObject("dataPager",dataPager);
        mv.addObject("pd",pd);
        mv.setViewName("page/subjectOrder/sbjectOrder_list");
        return mv;
    }
}
