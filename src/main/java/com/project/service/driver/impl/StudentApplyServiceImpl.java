package com.project.service.driver.impl;


import com.project.mapper.admin.*;
import com.project.mapper.driver.UserMapper;
import com.project.model.Const;
import com.project.model.ResultObject;
import com.project.model.school.*;
import com.project.service.driver.StudentApplyService;
import com.project.utils.DateUtils;
import com.project.utils.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private SubjectOrderMapper subjectOrderMapper;
    @Autowired
    private DriverHomeMapper homeMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 1.学员信息录入
     * 2.生成订单信息
     * @param apply
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject applyAndCreateOrder(Apply apply) throws Exception {
        String sch_id=apply.getSchool_id();
        SchoolModel schoolModel =new SchoolModel();
        schoolModel.setId(sch_id);
        logger.info("检测驾校id:{}是否存在",apply.getSchool_id());
        SchoolModel schoolDetail = homeMapper.getSimpleSchool(schoolModel);

        if(null==schoolDetail ||StringUtils.isEmpty(schoolDetail.getSchool_code())){
            return ResultObject.build(Const.SCHOOL_ERROR,Const.SCHOOL_ERROR_MESSAGE,null);
        }

        //检查此人是否在平台下报名了
        logger.info("检测学员在平台下是否报名");
        Apply check =new Apply();
        check.setIdcard(apply.getIdcard());
        check.setTraintype(apply.getTraintype());
        Apply detailApply = applyMapper.getDeatilApply(check);
        if(null !=detailApply){
            Order order =new Order();
            order.setApply_id(String.valueOf(detailApply.getId()));
            order.setStatus("1");
            Order orderDetil = orderMapper.getOrderDetil(order);
            if(null !=orderDetil){
                logger.info("身份：{} 的学员已经报名了驾照类型：{}",apply.getIdcard(),apply.getTraintype());

                return ResultObject.build(Const.APPLY_EXIST,Const.APPLY_EXIST_MESSAGE,null);

            }
        }


        apply.setOrgcode(schoolDetail.getSchool_code());
        apply.setApplydate(DateUtils.stableYYYMMDD());
        logger.info("开始录入驾校报名学员的信息");
        applyMapper.saveApply(apply);
        logger.info("--正在生成驾校报名订单信息--");
        Order order =new Order();
        //生成订单
        order.setMember_id(apply.getMember_id());
        order.setOrder_sn(DateUtils.getTimeInMillis()+ ToolsUtils.sixCode());
        order.setApply_id(String.valueOf(apply.getId()));
        order.setTime(DateUtils.getTimeInSecond());//录入时间
        order.setSchool_id(apply.getSchool_id());//驾校ID
        order.setPrice(apply.getPrice());

        orderMapper.saveOrder(order);
        OrderVO voOrder =new OrderVO();
        BeanUtils.copyProperties(order,voOrder);

        return ResultObject.success(voOrder);
    }

    @Override
    public ResultObject applySubjectOrder(ApplySubject applySubject) throws Exception {

        String school_id=applySubject.getSchool_id();
        String member_id=applySubject.getMember_id();
        /**
         * 此人必须绑定了驾校
         */
        User user =new User();
        user.setMember_id(member_id);
        User userInfo = userMapper.getUserInfo(user);
        if(null==userInfo||StringUtils.isEmpty(userInfo.getSchool_id())){
            return ResultObject.build(Const.NO_BIND_JIAXIAO,Const.NO_BIND_JIAXIAO_MESSAGE,null);

        }
        /**
         * 驾校报名ID 要与套餐一致
         */
        if(!school_id.equals(userInfo.getSchool_id())){
            return ResultObject.build(Const.BIND_SHOOL_NO_MATCH,Const.BIND_SHOOL_NO_MATCH_MESSAGE,null);

        }
        /**
         * 驾照类型必须匹配
         */
        Subject subject =new Subject();
        subject.setId(applySubject.getSubject_id());
        subject.setSchool_id(school_id);
        Subject subjectDetail = subjectMapper.getSubjectDetail(subject);
        if(null==subjectDetail){
            return ResultObject.build(Const.SUBJECT_ERROR,Const.SUBJECT_ERROR_MESSAGE,null);
        }
        if(!userInfo.getSubject().equals(subjectDetail.getSubject())){
            return ResultObject.build(Const.SUBJECT_NO_MATCH,Const.SUBJECT_NO_MATCH_MESSAGE,null);

        }

        String db_subname= subjectDetail.getSubject_name();
        String db_subject =subjectDetail.getSubject();
        /**
         * 必须先购买主课时，才能够单买课时
         */
        if(Integer.valueOf(db_subname)%2==0){
            Order order =new Order();
            order.setStatus("1");
            order.setSubject(db_subject);
            order.setMember_id(member_id);
            int zhukeshi =Integer.valueOf(db_subname)-1;
            order.setSubject_name(String.valueOf(zhukeshi));
            Order orderDetil = subjectOrderMapper.getOrderDetil(order);

            if(null==orderDetil){
                return ResultObject.build(Const.SUBJECT_ORDER_ERROR,Const.SUBJECT_ORDER_ERROR_MESSAGE,null);

            }
        }


        logger.info("用户：{} 正在购买科目学时订单信息",applySubject.getMember_id());
        Order order =new Order();
        //生成订单
        order.setMember_id(member_id);
        order.setOrder_sn(DateUtils.getTimeInMillis()+ ToolsUtils.sixCode());
        order.setTime(DateUtils.getTimeInSecond());//录入时间
        order.setSchool_id(applySubject.getSchool_id());//驾校ID
        order.setPrice(applySubject.getPrice());//价钱
        order.setSubject_id(applySubject.getSubject_id());//套餐
        order.setSubject_name(subjectDetail.getSubject_name());
        order.setNum(applySubject.getNum());
        order.setSubject(db_subject);
        subjectOrderMapper.saveOrder(order);
        OrderVO voOrder =new OrderVO();
        BeanUtils.copyProperties(order,voOrder);
        return ResultObject.success(voOrder);
    }


}
