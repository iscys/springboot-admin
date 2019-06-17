package com.project.service.driver;

import com.project.model.QuestionAndAns;
import com.project.model.ResultObject;

public interface DriverService {
     ResultObject enter(QuestionAndAns qaa) throws Exception;

}
