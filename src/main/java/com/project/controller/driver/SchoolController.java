package com.project.controller.driver;

import com.project.model.ResultObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    /**
     * 小程序首页信息
     */
    @PostMapping("/index")
    public ResultObject index(){
        return null;
    }

}
