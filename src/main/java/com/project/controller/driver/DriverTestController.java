package com.project.controller.driver;

import com.project.model.QuestionAndAns;
import com.project.model.ResultObject;
import com.project.service.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class DriverTestController {
    @Autowired
    private DriverService driverService;

    /**
     *初始化题库，将题库录入数据库中
     * model c1 c2 a1 a2 b1 b2
     * subject  1 (科目1) 4（ 科目4）
     */
    @PostMapping ("/enter")
    public ResultObject enter(QuestionAndAns qaa) throws Exception{

       return  driverService.enter(qaa);
    }


}
