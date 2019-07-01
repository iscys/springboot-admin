package com.project.service.driver;


import com.project.model.ResultObject;
import com.project.model.school.Apply;

public interface StudentApplyService {


    ResultObject applyAndCreateOrder(Apply apply) throws Exception;
}
