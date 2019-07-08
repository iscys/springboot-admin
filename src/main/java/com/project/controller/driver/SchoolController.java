package com.project.controller.driver;

import com.project.controller.BaseController;
import com.project.model.Const;
import com.project.model.ResultObject;
import com.project.model.school.*;
import com.project.service.admin.TeacherService;
import com.project.service.driver.SchoolService;
import com.project.utils.PageData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/school")
public class SchoolController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 小程序首页信息，轮播广告位
     */
    @PostMapping("/index")
    public ResultObject index() {

        try {
            PageData pd = this.getPageData();
            ResultObject result = schoolService.index(pd);
            return result;
        } catch (Exception e) {

            return ResultObject.error(null);
        }


    }

    /**
     * 驾校列表
     */
    @PostMapping("/driver")
    public ResultObject driver() {

        try {
            PageData pd = this.getPageData();
            ResultObject result = schoolService.driverList(pd);
            return result;
        } catch (Exception e) {

            return ResultObject.error(null);
        }


    }


    /**
     * 驾校详情信息
     */
    @PostMapping("/info")
    public ResultObject schoolDetail(SchoolModel schoolModel) {

        String id = schoolModel.getId();
        if (StringUtils.isEmpty(id)) {
            return ResultObject.build(Const.SHOOL_ID_NULL, Const.SHOOL_ID_NULL_MESSAGE, null);
        }
        try {
            ResultObject result = schoolService.getSchoolDetail(schoolModel);
            return result;
        } catch (Exception e) {
            logger.error("获取驾校详情信息异常{}",e.getMessage());
            return ResultObject.error(null);
        }
    }

    /**
     * 驾校图片相册
     */
    @PostMapping("/album")
    public ResultObject schoolAlbum(SchoolModel schoolModel) {

        String id = schoolModel.getId();
        if (StringUtils.isEmpty(id)) {
            return ResultObject.build(Const.SHOOL_ID_NULL, Const.SHOOL_ID_NULL_MESSAGE, null);
        }
        try {
            ResultObject result = schoolService.getSchoolAlbum(schoolModel);
            return result;
        } catch (Exception e) {
            return ResultObject.error(null);
        }
    }


    /**
     * 驾校套餐接口
     */

    @PostMapping("/subject")
    public ResultObject subject() {

        try {
            PageData pd = this.getPageData();
            ResultObject result = schoolService.subjectList(pd);
            return result;
        } catch (Exception e) {

            return ResultObject.error(null);
        }

    }


    /**
     * 教练列表接口
     */

    @PostMapping("/teacher")
    public ResultObject teacher() {

        try {
            PageData pd = this.getPageData();
            ResultObject result = schoolService.teacherList(pd);
            return result;
        } catch (Exception e) {

            return ResultObject.error(null);
        }

    }
    /**
     * 教练详情
     */
    @PostMapping("/teacherInfo")
    public ResultObject schoolDetail(Teacher teacher) {

        String id = teacher.getId();
        if (StringUtils.isEmpty(id)) {
            return ResultObject.build(Const.TEACHER_ID_NULL, Const.TEACHER_ID_NULL_MESSAGE, null);
        }
        try {
            ResultObject result = schoolService.getTeacherDetail(teacher);
            return result;
        } catch (Exception e) {
            return ResultObject.error(null);
        }
    }

    /**
     * 驾校名字列表
     * @return
     */
    @PostMapping("/schoolList")
    public ResultObject schoolList() {

        PageData pd = this.getPageData();
        try {
            List<SchoolModel> allSchoolList = teacherService.getAllSchoolList(pd);
            List<String> str =new ArrayList<>();
            if(!CollectionUtils.isEmpty(allSchoolList)){
                for(SchoolModel sh :allSchoolList)
                str.add(sh.getSchool_name());
            }
            HashMap<String,Object>  map =new HashMap<>();
            map.put("school_str",str);
            map.put("school_detail",allSchoolList);

            return ResultObject.success(map);
        } catch (Exception e) {
            return ResultObject.error(null);
        }
    }

    /**
     * 学员对驾校评价
     */
    @PostMapping ("/schfbk")
    public ResultObject feedback_school(FeedBackSchool fda){
        if(org.springframework.util.StringUtils.isEmpty(fda.getFrom_member_id())){
            return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
        }

        if(org.springframework.util.StringUtils.isEmpty(fda.getFeedback())){
            return ResultObject.build(Const.FEED_BACK_NULL,Const.FEED_BACK_NULL_MESSAGE,null);
        }

        if(org.springframework.util.StringUtils.isEmpty(fda.getSchool_id())){
            return ResultObject.build(Const.SHOOL_ID_NULL,Const.SHOOL_ID_NULL_MESSAGE,null);
        }

        try{
            ResultObject rest= schoolService.saveSchoolFeedback(fda);
            return rest;

        }catch (Exception e){
            logger.error("api保存用户评论驾校：{}",e.getMessage());
            return ResultObject.error(null);
        }

    }



    /**
     * 驾校评价列表
     */
    @PostMapping ("/schfbkList")
    public ResultObject feedback_school_list(){
        PageData pd = this.getPageData();
        String school_id = pd.getString("school_id");


        if(org.springframework.util.StringUtils.isEmpty(school_id)){
            return ResultObject.build(Const.SHOOL_ID_NULL,Const.SHOOL_ID_NULL_MESSAGE,null);
        }

        try{
            ResultObject rest= schoolService.getSchoolFeedbackList(pd);
            return rest;

        }catch (Exception e){
            logger.error("获取驾校评论列表异常：{}",e.getMessage());
            return ResultObject.error(null);
        }

    }









    /**
     * 学员对驾校评价
     */
    @PostMapping ("/techerfbk")
    public ResultObject feedback_teacher(FeedBackTeacher fda){
        if(org.springframework.util.StringUtils.isEmpty(fda.getFrom_member_id())){
            return ResultObject.build(Const.MEMBER_ID_NULL,Const.MEMBER_ID_NULL_MESSAGE,null);
        }

        if(org.springframework.util.StringUtils.isEmpty(fda.getFeedback())){
            return ResultObject.build(Const.FEED_BACK_NULL,Const.FEED_BACK_NULL_MESSAGE,null);
        }

        if(org.springframework.util.StringUtils.isEmpty(fda.getTeacher_id())){
            return ResultObject.build(Const.TEACHER_ID_NULL,Const.TEACHER_ID_NULL_MESSAGE,null);
        }

        try{
            ResultObject rest= schoolService.saveTeacherFeedback(fda);
            return rest;

        }catch (Exception e){
            logger.error("api保存用户评论教练异常：{}",e.getMessage());
            return ResultObject.error(null);
        }

    }



    /**
     * 教练评价列表
     */
    @PostMapping ("/techerfbkList")
    public ResultObject feedback_teacher_list(){
        PageData pd = this.getPageData();
        String teacher_id = pd.getString("teacher_id");


        if(org.springframework.util.StringUtils.isEmpty(teacher_id)){
            return ResultObject.build(Const.TEACHER_ID_NULL,Const.TEACHER_ID_NULL_MESSAGE,null);
        }

        try{
            ResultObject rest= schoolService.getTeacherFeedbackList(pd);
            return rest;

        }catch (Exception e){
            logger.error("获取教练评论列表异常：{}",e.getMessage());
            return ResultObject.error(null);
        }

    }

}