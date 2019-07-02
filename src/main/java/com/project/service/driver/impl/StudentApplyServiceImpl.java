package com.project.service.driver.impl;


import com.project.mapper.admin.ApplyMapper;
import com.project.mapper.admin.OrderMapper;
import com.project.model.ResultObject;
import com.project.model.school.Apply;
import com.project.model.school.Order;
import com.project.service.driver.StudentApplyService;
import com.project.utils.DateUtils;
import com.project.utils.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StudentApplyServiceImpl implements StudentApplyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private OrderMapper orderMapper;


    /**
     * 1.学员信息录入
     * 2.生成订单信息
     * @param apply
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject applyAndCreateOrder(Apply apply) throws Exception {
        apply.setApplydate(DateUtils.stableYYYMMDD());
        logger.info("开始录入学员的信息");
        applyMapper.saveApply(apply);
        logger.info("--正在生成订单信息--");
        Order order =new Order();
        //生成订单
        order.setMember_id(apply.getMember_id());
        order.setOrder_sn(DateUtils.getTimeInMillis()+ ToolsUtils.sixCode());
        order.setApply_id(String.valueOf(apply.getId()));
        order.setTime(DateUtils.getTimeInSecond());//录入时间
        order.setSchool_id(apply.getSchool_id());//驾校ID
        order.setSubject_id(apply.getSubject_id());//套餐ID
        order.setPrice(apply.getPrice());//套餐ID

        orderMapper.saveOrder(order);


        return ResultObject.success(order);
    }


}
