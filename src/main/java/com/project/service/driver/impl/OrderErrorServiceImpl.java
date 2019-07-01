package com.project.service.driver.impl;

import com.project.mapper.driver.OrderErrorMapper;
import com.project.model.school.ErrorModel;
import com.project.service.driver.OrderErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderErrorServiceImpl implements OrderErrorService {
    @Autowired
    private OrderErrorMapper mapper;
    @Override
    public void saveErrorLog(ErrorModel errorModel) {

        mapper.saveError(errorModel);

    }
}
