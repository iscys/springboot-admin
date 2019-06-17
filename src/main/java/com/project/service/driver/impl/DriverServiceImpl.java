package com.project.service.driver.impl;

import com.project.mapper.driver.DriverTestMapper;
import com.project.model.Const;
import com.project.model.JiaoXiaoApiModel;
import com.project.model.QuestionAndAns;
import com.project.model.ResultObject;
import com.project.service.driver.DriverService;
import com.project.utils.GsonUtils;
import com.project.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    Logger logger =LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DriverTestMapper driverMapper;

    @Override
    public ResultObject enter(QuestionAndAns qaa)throws Exception {
        String subject = qaa.getSubject();
        String model =qaa.getModel();
        String url = String.format(Const.JIAPXIAPI, model,subject);
        logger.info("API 地址：{}",url);
        String s = HttpUtils.INSTANCE.doGet(url);
        JiaoXiaoApiModel entryResult = GsonUtils.fromJson(s, JiaoXiaoApiModel.class);
        List<QuestionAndAns> result = entryResult.getResult();
        if(!CollectionUtils.isEmpty(result)){
            result.forEach(questionAndAns->{
                questionAndAns.setSubject(subject);
                questionAndAns.setModel(model);
                questionAndAns.setFormat_answer(QuestionAndAns.formatAnswer(questionAndAns.getAnswer()));
                System.out.println(questionAndAns);
                driverMapper.enterBank(questionAndAns);
            });
        }
        return ResultObject.success(null);
    }
}
