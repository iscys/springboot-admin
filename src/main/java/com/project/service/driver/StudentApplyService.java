package com.project.service.driver;


import com.project.model.ResultObject;
import com.project.model.school.Apply;
import com.project.model.school.ApplySubject;

public interface StudentApplyService {


    ResultObject applyAndCreateOrder(Apply apply) throws Exception;

    ResultObject applySubjectOrder(ApplySubject applySubject) throws Exception;
}
